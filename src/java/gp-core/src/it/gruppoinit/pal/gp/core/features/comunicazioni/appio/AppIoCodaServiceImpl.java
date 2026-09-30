package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaId;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveD;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMassiveDId;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimentiId;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStati;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaStatiId;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfig;
import it.gruppoinit.pal.gp.core.domain.AppIoServiziConfigParam;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServizi;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoAppIoServiziId;
import it.gruppoinit.pal.gp.core.features.anagrafe.TipoAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.model.TemplateMessaggioAppIo;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class AppIoCodaServiceImpl extends BaseServiceImpl<AppIoCoda, AppIoCodaId> implements IAppIoCodaService {

    private static final Logger log = LoggerFactory.getLogger(AppIoCodaServiceImpl.class);
    private static final String DA_PROCESSARE = "DA_PROCESSARE";
    @Autowired
    private IAppIoCodaDAO appIoCodaDAO;
    @Autowired
    private ITipimovimentoAppIoserviziService tipimovimentoAppIoserviziService;
    @Autowired
    private IAppIoCodaMovimentiService appIoCodaMovimentiService;
    @Autowired
    private IAppIoCodaStatiService appIoCodaStatiService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private IAppIoServiziConfigService appIoServiziConfigService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private IAppIoServiziConfigParamService appIoServiziConfigParamService;
    @Autowired
    private IAppIoCodaMassiveDDAO appIoCodaMassiveDDAO;

    @Override
    public void nuovoMessaggio(Integer codiceMovimento) {

	log.info("#AppIoCodaServiceImpl - nuovoMessaggio codiceMovimento {}", codiceMovimento);
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (movimento.getTipomovimento() == null && movimento.getTipomovimento().getId().getTipomovimento() == null) {
	    String mess = "Il tipomovimento non è stato correttamente impostato per il codice movimento " + codiceMovimento;
	    log.error(mess);
	    throw new RuntimeException(mess);
	}
	// Controllo se il mov è configurato in tipimovimento_app_io_servizi
	// Se configurato restituisco l'identificativo servizio 
	List<String> identServizioList = tipimovimentoAppIoserviziService.isConfiguratoTipomov(codiceMovimento);
	log.debug("identServizioList {}", identServizioList);
	if (identServizioList.isEmpty()) {
	    String mess = "Il tipomovimento [" + movimento.getTipomovimento().getId().getTipomovimento() +
			  "] non è stato configurato per le notifiche tramite AppIO per il codice movimento " + codiceMovimento;
	    log.debug(mess);
	    return;
	}
	// potrebbe restituire più identificativi dunque li ciclo
	Istanze istanza = movimento.getIstanza();
	for (String identServizio : identServizioList) {
	    log.debug("appIoCodaService: identificativo del servizio {}", identServizio);
	    AppIoServiziConfig ioServiziConfig = appIoServiziConfigService.findByIdServizioEIstanza(identServizio, istanza);
	    log.debug("appIoCodaService: identificativo del servizio {} attivo? {}", identServizio, ioServiziConfig.isAttivo());
	    if (ioServiziConfig.isAttivo()) {
		//richiedente istanza
		Anagrafe richiedente = istanza.getRichiedente() != null ? istanza.getRichiedente() : null;
		Set<Integer> anagraficheInserite = new HashSet<Integer>(); // non mando due volte la comunicazione il set contiene le anagrafiche già notificate
		if (richiedente != null && TipoAnagrafeEnum.F.value().equals(richiedente.getTipoanagrafe())) {
		    popolaTabelleAppIoCoda(codiceMovimento, movimento, istanza, identServizio, ioServiziConfig, richiedente);
		    anagraficheInserite.add(richiedente.getId().getCodice());
		}
		Anagrafe professionista = istanza.getProfessionista() != null ? istanza.getProfessionista() : null;
		if (professionista != null && TipoAnagrafeEnum.F.value().equals(professionista.getTipoanagrafe())
			&& !anagraficheInserite.contains(professionista.getId().getCodice())) {
		    popolaTabelleAppIoCoda(codiceMovimento, movimento, istanza, identServizio, ioServiziConfig, professionista);
		    anagraficheInserite.add(professionista.getId().getCodice());
		}
		//	    List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanza);
		//	    for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
		//		Anagrafe anagrafica = istanzerichiedenti.getRichiedente();
		//		if (anagrafica != null && TipoAnagrafeEnum.F.value().equals(anagrafica.getTipoanagrafe())
		//			&& !anagraficheInserite.contains(anagrafica.getId().getCodice())) {
		//		    popolaTabelleAppIoCoda(codiceMovimento, movimento, istanza, identServizio, ioServiziConfig, anagrafica);
		//		    anagraficheInserite.add(anagrafica.getId().getCodice());
		//		}
		//	    }
	    }
	}
    }

    private void popolaTabelleAppIoCoda(Integer codiceMovimento, Movimenti movimento, Istanze istanza, String identServizio,
	    AppIoServiziConfig ioServiziConfig, Anagrafe anagrafica) {

	AppIoCoda coda = popolaCoda(movimento, istanza, identServizio, ioServiziConfig, anagrafica);
	try {
	    this.insert(coda);
	    this.appIoCodaDAO.commitFlush();
	    this.inserisciMovimento(codiceMovimento, coda.getId().getGuid());
	    this.inserisciCodaStati(coda.getId().getGuid());
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante l'inserimento" + e.getMessage());
	}
    }

    private AppIoCoda popolaCoda(Movimenti movimento, Istanze istanza, String identServizio, AppIoServiziConfig ioServiziConfig,
	    Anagrafe anagrafica) {

	AppIoCoda coda = new AppIoCoda();
	AppIoCodaId idCoda = new AppIoCodaId();
	idCoda.setIdcomune(ORMHelper.getIdcomune());
	String guid = UUID.randomUUID().toString();
	idCoda.setGuid(guid);
	coda.setId(idCoda);
	coda.setCodiceFiscale(anagrafica.getCodicefiscale());
	coda.setIdentificativoServizio(identServizio);
	coda.setStatoData(Calendar.getInstance().getTime());
	coda.setStato(DA_PROCESSARE);
	String statoMess = "Il messaggio con [id_messaggio_mittente=" + guid + "] è stato presentato.";
	coda.setStatoMessaggio(statoMess);
	Date dataEleborazione = calcolaDataElaborazione(ioServiziConfig.getMaxMessaggiGiorno(), identServizio, coda);
	coda.setDataPrevistaElaborazione(dataEleborazione);
	// sostituzione parametri recuperati da AppIoServiziConfigParam
	TemplateMessaggioAppIo msg = resolveTemplate(ioServiziConfig, identServizio, movimento, istanza);
	// Segnaposto
	Mailtipo mt = new Mailtipo();
	mt.setCorpo(msg.getTemplateMessaggio());
	mt.setOggetto(msg.getTemplateOggetto());
	Mailtipo replaceOggettoCorpo = this.mailtipoService.replaceOggettoCorpo(mt, istanza, movimento);
	coda.setMessaggio(replaceOggettoCorpo.getCorpo());
	coda.setOggetto(replaceOggettoCorpo.getOggetto());
	return coda;
    }

    private TemplateMessaggioAppIo resolveTemplate(AppIoServiziConfig ioServiziConfig, String identServizio, Movimenti movimento, Istanze istanza) {

	String templateMessaggio = ioServiziConfig.getTemplateMessaggio();
	String templateOggetto = ioServiziConfig.getTemplateOggetto();
	if (movimento != null && movimento.getId() != null && movimento.getId().getCodice() != null) {
	    // 
	    String tipomovimento = movimento.getTipomovimento().getId().getTipomovimento();
	    TipimovimentoAppIoServiziId id = new TipimovimentoAppIoServiziId(ORMHelper.getIdcomune(), tipomovimento, identServizio);
	    TipimovimentoAppIoServizi tmovIoServizi = tipimovimentoAppIoserviziService.findById(id);
	    if (tmovIoServizi != null) {
		// 1. cerco le configurazioni di tipimovimento_app_io_servizi per vedere se sovrascrivere il corpo / oggetto del messaggio
		templateMessaggio = StringUtils.defaultIfEmpty(tmovIoServizi.getTemplateMessaggio(), templateMessaggio);
		templateOggetto = StringUtils.defaultIfEmpty(tmovIoServizi.getTemplateOggetto(), templateOggetto);
		// 2. cerco le configurazioni di tipimov_appioservizi_int tipimov_appioservizi_int
		TemplateMessaggioAppIo ret = appIoCodaMovimentiService.findByIdServizioMovimentoAndIntervento(identServizio, tipomovimento,
			movimento.getIstanza().getAlberoproc().getId().getCodice());
		templateMessaggio = StringUtils.defaultIfEmpty(ret.getTemplateMessaggio(), templateMessaggio);
		templateOggetto = StringUtils.defaultIfEmpty(ret.getTemplateOggetto(), templateOggetto);
		// 3. cerco le configurazioni di tipimov_appioservizi_int tipimov_appioservizi_endo
		if (movimento.getEndoprocedimento() != null && movimento.getEndoprocedimento().getId() != null
			&& movimento.getEndoprocedimento().getId().getCodice() != null) {
		    ret = appIoCodaMovimentiService.findByIdServizioMovimentoAndEndo(identServizio, tipomovimento,
			    movimento.getEndoprocedimento().getId().getCodice());
		    templateMessaggio = StringUtils.defaultIfEmpty(ret.getTemplateMessaggio(), templateMessaggio);
		    templateOggetto = StringUtils.defaultIfEmpty(ret.getTemplateOggetto(), templateOggetto);
		}
	    }
	}
	List<AppIoServiziConfigParam> parametri = this.appIoServiziConfigParamService.findByIdServizioEComune(identServizio,
		istanza.getComune().getCodicecomune());
	for (AppIoServiziConfigParam appIoServiziConfigParam : parametri) {
	    templateOggetto = templateOggetto.replace(appIoServiziConfigParam.getId().getParametro(), appIoServiziConfigParam.getDescrizione());
	    templateMessaggio = templateMessaggio.replace(appIoServiziConfigParam.getId().getParametro(), appIoServiziConfigParam.getDescrizione());
	}
	return new TemplateMessaggioAppIo(templateOggetto, templateMessaggio);
    }

    private Date calcolaDataElaborazione(BigDecimal maxMess, String identServizio, AppIoCoda coda) {

	// Se limite messaggi per servizio è stato raggiunto allora data_elaborazione 
	int maxMes = maxMess.intValue();
	Date returnValue = new Date();
	// va posticipata alla giornata successiva. 
	// Di default oggi stessa ora data creazione
	int totDaProc = this.countCodeDaProcessare(identServizio);
	if (totDaProc >= maxMes) {
	    returnValue = Utilities.addDays(returnValue, 1);
	}
	// setto l'ordine in base ai messaggi già presenti
	Integer contatoreOrdine = totDaProc + 1;
	coda.setOrdine(contatoreOrdine);
	return returnValue;
    }

    private int countCodeDaProcessare(String identServizio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("identificativoServizio", identServizio, String.class));
	fr.addFilterField(FilterUtils.equals("stato", DA_PROCESSARE,  String.class));
	ft.addRestriction(fr);
	return this.appIoCodaDAO.countRecord(ft);
    }

    private void inserisciMovimento(Integer codiceMovimento, String guid) {

	// inserisco su app_io_coda_movimenti
	AppIoCodaMovimenti codaMovimenti = popolaCodaMovimenti(codiceMovimento, guid);
	this.appIoCodaMovimentiService.insert(codaMovimenti);
    }

    private void inserisciCodaStati(String guidCoda) {

	//inserisco su app_io_coda_stati (stato iniziale DA_PROCESSARE)
	AppIoCodaStati codaStati = popolaCodaStati(guidCoda);
	this.appIoCodaStatiService.insert(codaStati);
    }

    private AppIoCodaStati popolaCodaStati(String guidCoda) {

	AppIoCoda coda = this.findCodaByGuid(guidCoda);
	AppIoCodaStati codaStati = new AppIoCodaStati();
	AppIoCodaStatiId idCodaStati = new AppIoCodaStatiId(ORMHelper.getIdcomune(), coda.getId().getGuid(), coda.getStato(), coda.getStatoData());
	codaStati.setId(idCodaStati);
	codaStati.setMessaggio(coda.getMessaggio());
	codaStati.setStatoAppIo(coda.getStato());
	codaStati.setAppIoCoda(coda);
	return codaStati;
    }

    private AppIoCodaMovimenti popolaCodaMovimenti(Integer codiceMovimento, String guid) {

	AppIoCodaMovimenti codaMovimenti = new AppIoCodaMovimenti();
	AppIoCodaMovimentiId idCodaMov = new AppIoCodaMovimentiId(ORMHelper.getIdcomune(), codiceMovimento, guid);
	codaMovimenti.setId(idCodaMov);
	AppIoCoda coda = findCodaByGuid(guid);
	codaMovimenti.setAppIoCoda(coda);
	return codaMovimenti;
    }

    private AppIoCoda findCodaByGuid(String guid) {

	AppIoCodaId idCoda = new AppIoCodaId();
	idCoda.setGuid(guid);
	idCoda.setIdcomune(ORMHelper.getIdcomune());
	return this.findById(idCoda);
    }

    @Override
    public void inviaAppIo(AppIoServiziConfig ioServiziConfig, String idservizio, String oggetto, String corpo, Anagrafe a,
	    Integer idDettaglioComunicazione) {

	AppIoCoda coda = new AppIoCoda();
	AppIoCodaId idCoda = new AppIoCodaId();
	idCoda.setIdcomune(ORMHelper.getIdcomune());
	String guid = UUID.randomUUID().toString();
	idCoda.setGuid(guid);
	coda.setId(idCoda);
	coda.setCodiceFiscale(a.getCodicefiscale());
	coda.setIdentificativoServizio(idservizio);
	coda.setStatoData(Calendar.getInstance().getTime());
	coda.setStato("DA_PROCESSARE");
	String statoMess = "Il messaggio con [id_messaggio_mittente=" + guid + "] è stato presentato.";
	coda.setStatoMessaggio(statoMess);
	Date dataEleborazione = calcolaDataElaborazione(ioServiziConfig.getMaxMessaggiGiorno(), idservizio, coda);
	coda.setDataPrevistaElaborazione(dataEleborazione);
	// sostituzione parametri recuperati da AppIoServiziConfigParam
	coda.setMessaggio(corpo);
	coda.setOggetto(oggetto);
	insert(coda);
	AppIoCodaMassiveDId appIoCodaMassiveDId = new AppIoCodaMassiveDId(idDettaglioComunicazione, guid);
	AppIoCodaMassiveD appIoCodaMassiveD = new AppIoCodaMassiveD();
	appIoCodaMassiveD.setId(appIoCodaMassiveDId);
	appIoCodaMassiveD.setAppIoCoda(coda);
	try {
	    appIoCodaMassiveDDAO.insert(appIoCodaMassiveD);
	} catch (Exception e) {
	    log.error("Error: ", e);
	}

    }

    @Override
    public void insert(AppIoCoda entity) {

	this.appIoCodaDAO.insert(entity);
    }

    @Override
    public void update(AppIoCoda entity) {

	this.appIoCodaDAO.update(entity);
    }

    @Override
    public void delete(AppIoCoda entity) {

	this.appIoCodaDAO.delete(entity);
    }

    @Override
    public List<AppIoCoda> findAll(Integer firstResult, Integer maxResult) {

	return this.appIoCodaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AppIoCoda findById(AppIoCodaId id) {

	return this.appIoCodaDAO.findById(id);
    }

    @Override
    protected Class<AppIoCoda> getEntityClass() {

	return AppIoCoda.class;
    }
}
