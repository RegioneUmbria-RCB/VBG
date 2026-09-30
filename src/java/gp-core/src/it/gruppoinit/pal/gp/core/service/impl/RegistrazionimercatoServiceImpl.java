package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.VwConcessioniattiveDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Registrazionimercato;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazionimercatoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class RegistrazionimercatoServiceImpl extends BaseServiceImpl<Registrazioni, PkId> implements RegistrazionimercatoService {

    private static final Logger log = LoggerFactory.getLogger(RegistrazionimercatoServiceImpl.class);
    private VwConcessioniattiveDAO vwConcessioniattiveDAO;
    @Autowired
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;

    @Autowired
    public void setVwConcessioniattiveDAO(VwConcessioniattiveDAO vwConcessioniattiveDAO) {

	this.vwConcessioniattiveDAO = vwConcessioniattiveDAO;
    }

    @Autowired
    public void setContiService(ContiService contiService) {

	this.contiService = contiService;
    }

    private RegistrazioniCausaliService registrazioniCausaliService;
    private AnagrafeService anagrafeService;
    private ContiService contiService;
    private RegistrazioniService registrazioniService;
    private MercatiUsoService mercatiUsoService;
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    private RegistrazioniImportiService registrazioniImportiService;
    private MercatiDService mercatiDService;
    private MercatiConsorziService mercatiConsorziService;
    private SoftwareService softwareService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setRegistrazioniCausaliService(RegistrazioniCausaliService registrazioniCausaliService) {

	this.registrazioniCausaliService = registrazioniCausaliService;
    }

    @Autowired
    public void setRegistrazioniService(RegistrazioniService registrazioniService) {

	this.registrazioniService = registrazioniService;
    }

    @Autowired
    public void setMercatiConsorziService(MercatiConsorziService mercatiConsorziService) {

	this.mercatiConsorziService = mercatiConsorziService;
    }

    @Autowired
    public void setMercatiCfgAttivitaService(MercatiCfgAttivitaService mercatiCfgAttivitaService) {

	this.mercatiCfgAttivitaService = mercatiCfgAttivitaService;
    }

    @Autowired
    public void setRegistrazioniImportiService(RegistrazioniImportiService registrazioniImportiService) {

	this.registrazioniImportiService = registrazioniImportiService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }
    //    private void createRegistrazioniInternal(Mercati mercati, Registrazionimercato registrazionimercato, boolean isAnnuali) {
    //
    //	// §§§BEGIN§§§
    //	List<VwConcessioniattive> vwconList = new ArrayList<VwConcessioniattive>();
    //	List<Registrazioni> msgAnswer = new ArrayList<Registrazioni>();
    //	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
    //	List<MercatipresenzeT> listaGiornateMercato = registrazionimercato.getMercatipresenzeTList();
    //	if (!isAnnuali) {
    //	    if (listaGiornateMercato.size() == 0) {
    //		throw new RuntimeException("Non è stato configurato il calendario per il mercato selezionato");
    //	    }
    //	}
    //	boolean imputaConsorzio = false;
    //	if (BooleanUtils.isTrue(mercati.getFlagConsorzio())) {
    //	    if (BooleanUtils.isTrue(registrazionimercato.getImputaConsorzio())) {
    //		imputaConsorzio = true;
    //	    }
    //	}
    //	Set<MercatiD> listaPosteggi = mercati.getMercatiDs();
    //	for (MercatiD mercatiD : listaPosteggi) {
    //	    // .. inserisco la registrazioni solo se il posteggio è abilitato
    //	    if (!BooleanUtils.isTrue(mercatiD.getDisabilitato())) {
    //		VwConcessioniattive concessioneAttiva = vwConcessioniattiveDAO.findByMercatoUsoPosteggio(mercati.getId().getCodice(),
    //			registrazionimercato.getMercatiUso().getId().getCodice(), mercatiD.getId().getCodice());
    //		if (null != concessioneAttiva) {
    //		    Registrazioni registrazione = null;
    //		    if (!isAnnuali) {
    //			registrazione = registraCanonePosteggio(mercatiD, registrazionimercato.getMercatiUso(), listMcfgAttivita,
    //				registrazionimercato.getAnno(), registrazionimercato.getDataRegistrazione(),
    //				registrazionimercato.getMercatipresenzeTList(), registrazionimercato.getRegistrazioniCausali(),
    //				registrazionimercato.getUtenteLoggato(), mercati, concessioneAttiva, imputaConsorzio);
    //		    } else {
    //			registrazione = registraCanoneAnnualePosteggio(mercatiD, registrazionimercato.getMercatiUso(), registrazionimercato.getAnno(),
    //				registrazionimercato.getRegistrazioniCausali(), registrazionimercato.getUtenteLoggato(), mercati, concessioneAttiva,
    //				registrazionimercato.getDataRegistrazione(), registrazionimercato.getTipoCalcoloAnnualeScadenzaRate(),
    //				imputaConsorzio);
    //		    }
    //		    if (null != registrazione) {
    //			msgAnswer.add(registrazione);
    //		    }
    //		    vwconList.add(concessioneAttiva);
    //		}
    //	    }
    //	}
    //	if (vwconList.isEmpty()) {
    //	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //	    InvalidValue iv = new InvalidValue("alert.registrazionimercato.concessioninonattive", mercati.getClass(), "", null, mercati);
    //	    _ivs.add(iv);
    //	    this.throwValidationMessages(_ivs);
    //	}
    //	if (msgAnswer.isEmpty()) {
    //	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //	    InvalidValue iv = new InvalidValue("alert.registrazionimercato.registrazioninoninserite", mercati.getClass(), "", null, mercati);
    //	    _ivs.add(iv);
    //	    this.throwValidationMessages(_ivs);
    //	}
    //	if (imputaConsorzio) {
    //	    buildRegistrazioneConsorzio(mercati, registrazionimercato, msgAnswer);
    //	}
    //	// §§§END§§§
    //	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    //    }

    private void buildRegistrazioneConsorzio(Mercati mercati, Registrazionimercato registrazionimercato, List<Registrazioni> registrazionis) {

	Integer anno = registrazionimercato.getAnno();
	List<MercatiConsorzi> mcs = mercatiConsorziService.findByCodiceMercato(mercati.getId().getCodice());
	Anagrafe a = null;
	if (mcs.size() == 0) {
	    throw new RuntimeException("Non è stato configurato nessun consorzio per il mercato e non è possibile associare l'anagrafe.");
	}
	if (mcs.size() == 1) {
	    a = mcs.get(0).getConsorzio();
	} else {
	    List<MercatiConsorzi> mcs2 = mercatiConsorziService.findByCodiceMercatoAndDataRiferimento(mercati.getId().getCodice(),
		    registrazionimercato.getDataRegistrazione());
	    a = mcs.get(0).getConsorzio();
	    for (MercatiConsorzi mc : mcs2) {
		a = mc.getConsorzio();
		break;
	    }
	}
	boolean isRegFatta = false;
	Map<Integer, List<RegistrazioniImporti>> mri = new HashMap<Integer, List<RegistrazioniImporti>>();
	Registrazioni reg = new Registrazioni();
	for (Registrazioni r : registrazionis) {
	    if (!isRegFatta) {
		reg.setAnagrafe(a);
		reg.setAnno(r.getAnno());
		reg.setDataRegistrazione(r.getDataRegistrazione());
		reg.setDescrizione("Pagamento canone consorzio anno " + anno.intValue() + "");
		reg.setMercatiD(r.getMercatiD());
		reg.setMercatiUso(r.getMercatiUso());
		reg.setRegistrazioniCausali(r.getRegistrazioniCausali());
		reg.setProgressivo(r.getProgressivo());
		reg.setSoftware(r.getSoftware());
		reg.setResponsabili(r.getResponsabili());
		reg.setResponsabiliSistema(r.getResponsabiliSistema());
		isRegFatta = true;
	    }
	    Set<RegistrazioniImporti> regimps = r.getRegistrazioniImportis();
	    for (RegistrazioniImporti registrazioniImporti : regimps) {
		Integer nrRata = registrazioniImporti.getNrRata();
		if (mri.get(nrRata) == null) {
		    List<RegistrazioniImporti> rigs = new ArrayList<RegistrazioniImporti>();
		    rigs.add(registrazioniImporti);
		    mri.put(nrRata, rigs);
		} else {
		    List<RegistrazioniImporti> rigs = mri.get(nrRata);
		    rigs.add(registrazioniImporti);
		}
	    }
	}
	registrazioniService.insert(reg);
	List<RegistrazioniImporti> importiFinali = new ArrayList<RegistrazioniImporti>();
	for (Map.Entry<Integer, List<RegistrazioniImporti>> entry : mri.entrySet()) {
	    Integer nrRata = entry.getKey();
	    List<RegistrazioniImporti> rimps = entry.getValue();
	    RegistrazioniImporti importoFinale = new RegistrazioniImporti();
	    importoFinale.setRegistrazioni(reg);
	    importoFinale.setNrRata(nrRata);
	    BigDecimal importo = BigDecimal.ZERO;
	    Integer iva = null;
	    Conti conto = null;
	    BigDecimal interessi = null;
	    Date scadenza = null;
	    for (RegistrazioniImporti registrazioniImporti : rimps) {
		importo = importo.add(registrazioniImporti.getImporto());
		if (registrazioniImporti.getIva() != null) {
		    iva = registrazioniImporti.getIva();
		}
		if (registrazioniImporti.getConti() != null) {
		    conto = registrazioniImporti.getConti();
		}
		if (registrazioniImporti.getInteressi() != null) {
		    interessi = registrazioniImporti.getInteressi();
		}
		if (registrazioniImporti.getScadenza() != null) {
		    scadenza = registrazioniImporti.getScadenza();
		}
	    }
	    importoFinale.setScadenza(scadenza);
	    importoFinale.setInteressi(interessi);
	    importoFinale.setConti(conto);
	    importoFinale.setIva(iva);
	    importoFinale.setImporto(importo);
	    registrazioniImportiService.insert(importoFinale);
	}
    }
    //    @Override
    //    public void createRegistrazioni(Mercati mercati, Registrazionimercato registrazionimercato) {
    //
    //	createRegistrazioniInternal(mercati, registrazionimercato, false);
    //    }
    //
    //    @Override
    //    public void createRegistrazioniAnnuali(Mercati mercati, Registrazionimercato registrazionimercato) {
    //
    //	createRegistrazioniInternal(mercati, registrazionimercato, true);
    //    }
    //    private Registrazioni registraCanonePosteggio(MercatiD posteggio, MercatiUso mercatiUso, List<MercatiCfgAttivita> listMcfgAttivita, Integer anno,
    //	    Date dataRegistrazione, List<MercatipresenzeT> mercatipresenzeTList, RegistrazioniCausali registrazioniCausali,
    //	    Responsabili utenteLoggato, Mercati mercato, VwConcessioniattive concessioneAttiva, boolean imputaConsorzio) {
    //
    //	// §§§BEGIN§§§
    //	// Integer anno = registrazionimercato.getAnno();
    //	Integer codiceMercato = mercato.getId().getCodice();
    //	Integer codiceUso = mercatiUso.getId().getCodice();
    //	Integer codicePosteggio = posteggio.getId().getCodice();
    //	String codicePosteggioTxt = posteggio.getCodiceposteggio();
    //	String descrizioneUso = mercatiUso.getDescrizione();
    //	String descrizioneMercato = mercato.getDescrizione();
    //	/*
    //	Boolean flagRegContAssenza = mercato.getFlagRegContAssenza();
    //	if (null == flagRegContAssenza) {
    //	    flagRegContAssenza = new Boolean(false);
    //	}
    //	*/
    //	Software software = new Software();
    //	software.setCodice(ORMHelper.getSoftware());
    //	if (log.isDebugEnabled()) {
    //	    log.debug("registraCanonePosteggio(): codice mercato = " + codiceMercato);
    //	    log.debug("registraCanonePosteggio(): codice uso = " + codiceUso);
    //	    log.debug("registraCanonePosteggio(): codice posteggio = " + codicePosteggio);
    //	    log.debug("registraCanonePosteggio(): codice posteggio testo= " + codicePosteggioTxt);
    //	    log.debug("registraCanonePosteggio(): descrizione uso = " + descrizioneUso);
    //	}
    //	// Date dataRegistrazione = registrazionimercato.getDataRegistrazione();
    //	// n° registrazioni per coefficiente periodicità sia che sia FISSO che CALCOLATO
    //	String descrizioneRegistrazione = "";
    //	Registrazioni registrazione = null;
    //	int giorni = mercatipresenzeTList.size();
    //	descrizioneRegistrazione = "";
    //	registrazione = new Registrazioni();
    //	registrazione.setRegistrazioniCausali(registrazioniCausali);
    //	// progressivo = registrazioniService.findProgressivo(anno);
    //	// if (log.isDebugEnabled()) {
    //	// log.debug("registraCanonePosteggio(): progressivo=" + progressivo);
    //	// }
    //	// registrazione.setProgressivo(progressivo);
    //	registrazione.setMercatiUso(mercatiUso);
    //	registrazione.setDataRegistrazione(dataRegistrazione);
    //	registrazione.setAnno(anno.shortValue());
    //	registrazione.setDataSistema(dataRegistrazione);
    //	registrazione.setResponsabili(utenteLoggato);
    //	// TODO lo vogliamo far inputare all'utente?
    //	registrazione.setResponsabiliSistema(utenteLoggato);
    //	registrazione.setMercatiD(posteggio);
    //	registrazione.setAnagrafe(concessioneAttiva.getOccupante());
    //	descrizioneRegistrazione = registrazioniCausali.getDescrizione() +
    //		" (" +
    //		anno +
    //		"), mercato " +
    //		descrizioneMercato +
    //		", posteggio: " +
    //		codicePosteggioTxt +
    //		", " +
    //		descrizioneUso;
    //	if (log.isDebugEnabled()) {
    //	    log.debug("registraCanonePosteggio(): descrizione registrazioni=" + descrizioneRegistrazione);
    //	}
    //	registrazione.setDescrizione(StringUtils.abbreviate(descrizioneRegistrazione, 255));
    //	if (descrizioneRegistrazione.length() > 255) {
    //	    registrazione.setNote(descrizioneRegistrazione);
    //	}
    //	registrazione.setSoftware(software);
    //	// inserire la registrazioni
    //	// e calcolare le righe di importo
    //	if (!imputaConsorzio) {
    //	    registrazioniService.insert(registrazione);
    //	}
    //	PosteggioImportoHelper posteggioImportoHelper = calcoloCostoPosteggiService.calcolaCostoPosteggio(null, posteggio, listMcfgAttivita, anno,
    //		giorni, concessioneAttiva, WebConstants.MERCATO_CONTESTO_CONCESSIONARI, null, null, dataRegistrazione);
    //	BigDecimal importoRegistrazione = posteggioImportoHelper.getImporto();
    //	// Ho l'importo complessivo e l'importo delle varie righe di conto
    //	List<RigaImporto> righeCostoPosteggio = posteggioImportoHelper.getListaImporti();
    //	List<RegistrazioniImporti> RegImportiList = registrazioniService.getRegistrazioniImportiRateizzati(registrazione, importoRegistrazione,
    //		righeCostoPosteggio);
    //	if (!imputaConsorzio) {
    //	    for (RegistrazioniImporti registrazioniImporti : RegImportiList) {
    //		registrazioniImportiService.insert(registrazioniImporti);
    //	    }
    //	} else {
    //	    registrazione.getRegistrazioniImportis().addAll(RegImportiList);
    //	}
    //	return registrazione;
    //	// §§§END§§§
    //	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    //    }

    @Override
    protected Class<Registrazioni> getEntityClass() {

	return Registrazioni.class;
    }

    @Override
    public void delete(Registrazioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Registrazioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Registrazioni findById(PkId id) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Registrazioni entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Registrazioni entity) {

	throw new NotImplementedException();
    }
    //    private Registrazioni registraCanoneAnnualePosteggio(MercatiD posteggio, MercatiUso mercatiUso, Integer anno,
    //	    RegistrazioniCausali registrazioniCausali, Responsabili utenteLoggato, Mercati mercato, VwConcessioniattive concessioneAttiva,
    //	    Date dataRegistrazione, Integer tipoCalcoloAnnualeScadenzaRate, boolean imputaConsorzio) {
    //
    //	// §§§BEGIN§§§
    //	// Integer anno = registrazionimercato.getAnno();
    //	Integer codiceMercato = mercato.getId().getCodice();
    //	Integer codiceUso = mercatiUso.getId().getCodice();
    //	Integer codicePosteggio = posteggio.getId().getCodice();
    //	String codicePosteggioTxt = posteggio.getCodiceposteggio();
    //	String descrizioneUso = mercatiUso.getDescrizione();
    //	String descrizioneMercato = mercato.getDescrizione();
    //	/*
    //	Boolean flagRegContAssenza = mercato.getFlagRegContAssenza();
    //	if (null == flagRegContAssenza) {
    //	    flagRegContAssenza = new Boolean(false);
    //	}
    //	*/
    //	Software software = new Software();
    //	software.setCodice(ORMHelper.getSoftware());
    //	if (log.isDebugEnabled()) {
    //	    log.debug("registraCanoneAnnualePosteggio(): codice mercato = " + codiceMercato);
    //	    log.debug("registraCanoneAnnualePosteggio(): codice uso = " + codiceUso);
    //	    log.debug("registraCanoneAnnualePosteggio(): codice posteggio = " + codicePosteggio);
    //	    log.debug("registraCanoneAnnualePosteggio(): codice posteggio testo= " + codicePosteggioTxt);
    //	    log.debug("registraCanoneAnnualePosteggio(): descrizione uso = " + descrizioneUso);
    //	}
    //	// Date dataRegistrazione = registrazionimercato.getDataRegistrazione();
    //	// n° registrazioni per coefficiente periodicità sia che sia FISSO che CALCOLATO
    //	String descrizioneRegistrazione = "";
    //	Registrazioni registrazione = null;
    //	descrizioneRegistrazione = "";
    //	registrazione = new Registrazioni();
    //	registrazione.setRegistrazioniCausali(registrazioniCausali);
    //	// progressivo = registrazioniService.findProgressivo(anno);
    //	// if (log.isDebugEnabled()) {
    //	// log.debug("registraCanonePosteggio(): progressivo=" + progressivo);
    //	// }
    //	// registrazione.setProgressivo(progressivo);
    //	registrazione.setMercatiUso(mercatiUso);
    //	registrazione.setDataRegistrazione(dataRegistrazione);
    //	registrazione.setAnno(anno.shortValue());
    //	registrazione.setDataSistema(dataRegistrazione);
    //	registrazione.setResponsabili(utenteLoggato);
    //	// TODO lo vogliamo far inputare all'utente?
    //	registrazione.setResponsabiliSistema(utenteLoggato);
    //	registrazione.setMercatiD(posteggio);
    //	registrazione.setAnagrafe(concessioneAttiva.getOccupante());
    //	descrizioneRegistrazione = registrazioniCausali.getDescrizione() + " (" + anno + "), mercato " + descrizioneMercato + ", posteggio: "
    //		+ codicePosteggioTxt + ", " + descrizioneUso;
    //	if (log.isDebugEnabled()) {
    //	    log.debug("registraCanonePosteggio(): descrizione registrazioni=" + descrizioneRegistrazione);
    //	}
    //	registrazione.setDescrizione(StringUtils.abbreviate(descrizioneRegistrazione, 255));
    //	if (descrizioneRegistrazione.length() > 255) {
    //	    registrazione.setNote(descrizioneRegistrazione);
    //	}
    //	registrazione.setSoftware(software);
    //	// inserire la registrazioni
    //	// e calcolare le righe di importo
    //	if (!imputaConsorzio) {
    //	    registrazioniService.insert(registrazione);
    //	}
    //	PosteggioImportoHelper posteggioImportoHelper = mercatiDService.calcolaCostoPosteggioAnnuale(posteggio, anno,
    //		WebConstants.MERCATO_CONTESTO_CONCESSIONARI);
    //	// Ho l'importo complessivo e l'importo delle varie righe di conto
    //	List<RigaImporto> righeCostoPosteggio = posteggioImportoHelper.getListaImporti();
    //	List<RegistrazioniImporti> RegImportiList = registrazioniService.getRegistrazioniAnnuali(registrazione, righeCostoPosteggio,
    //		tipoCalcoloAnnualeScadenzaRate);
    //	if (!imputaConsorzio) {
    //	    for (RegistrazioniImporti registrazioniImporti : RegImportiList) {
    //		registrazioniImportiService.insert(registrazioniImporti);
    //	    }
    //	} else {
    //	    registrazione.getRegistrazioniImportis().addAll(RegImportiList);
    //	}
    //	return registrazione;
    //	// §§§END§§§
    //	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    //    }

    @Override
    public Registrazioni registraCostoPosteggioDaWizard(Mercati mercato, MercatiUso uso, MercatiD posteggio, Anagrafe anagrafe,
	    RegistrazioniCausali rc, List<ChiaveValoreBean<Integer, BigDecimal>> meseImportis, Integer anno, Conti conto, TipiScadenza tipiScadenza) {

	posteggio = mercatiDService.findById(new PkId(posteggio.getId().getCodice()));
	anagrafe = anagrafeService.findById(new PkId(anagrafe.getId().getCodice()));
	conto = contiService.findById(new PkId(conto.getId().getCodice()));
	rc = registrazioniCausaliService.findById(new PkId(rc.getId().getCodice()));
	uso = mercatiUsoService.findById(new PkId(uso.getId().getCodice()));
	Software s = softwareService.findById(ORMHelper.getSoftware());
	Registrazioni result = new Registrazioni();
	result.setAnagrafe(anagrafe);
	result.setMercatiD(posteggio);
	result.setMercatiUso(uso);
	result.setAnno(anno.shortValue());
	result.setDataRegistrazione(Calendar.getInstance().getTime());
	result.setDataSistema(Calendar.getInstance().getTime());
	result.setRegistrazioniCausali(rc);
	result.setSoftware(s);
	Responsabili utente = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	result.setResponsabili(utente);
	result.setResponsabiliSistema(utente);
	registrazioniService.insert(result);
	int numeroRata = 1;
	for (ChiaveValoreBean<Integer, BigDecimal> cvb : meseImportis) {
	    if (cvb.getValore() != null) {
		if (cvb.getValore().compareTo(BigDecimal.ZERO) > 0) {
		    RegistrazioniImporti ri = new RegistrazioniImporti();
		    ri.setConti(conto);
		    ri.setIva(conto.getIva());
		    ri.setImporto(cvb.getValore());
		    ri.setRegistrazioni(result);
		    ri.setNrRata(numeroRata++);
		    Calendar c = Calendar.getInstance();
		    c.set(Calendar.MONTH, (cvb.getChiave() - 1));// in calendar i mesi partono da 0;
		    c.set(Calendar.DATE, 2);
		    c.set(Calendar.YEAR, anno);
		    Date scadenza = new DataScadenzaResolver(TipoScadenzaEnum.fromValue(tipiScadenza.getId()), c.getTime(), ri.getNrRata(), null)
			    .calcolaScadenza();
		    ri.setScadenza(scadenza);
		    registrazioniImportiService.insert(ri);
		}
	    }
	}
	return result;
    }
}
