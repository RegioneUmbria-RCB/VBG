package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.IVerticalizzazioneBollettazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneInizializzazioneEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazionePosizioneElabEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStartEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.bus.eventi.ProceduraInvioNodoBollettazioneStopEvento;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioNuovaRigaRettifica;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRettificaAnnullata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaAggiuntaManualmente;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaEliminata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaRettificata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioValidazioneRiga;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.PosizioneDebitoriaBollettazioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.PosizioniDebitorieDaBollettazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRataBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRateizzazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.IBollettazioneRateizzazioneService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.RataBean;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.bollettazione.IBollCfgTipoRateService;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class CalcoloBollettazioneServiceImpl implements CalcoloBollettazioneService {

    private Logger logger = LoggerFactory.getLogger(CalcoloBollettazioneServiceImpl.class);
    protected BollettazioneDAO bollettazioneDAO;
    private ResponsabiliruoliService responsabiliruoliService;
    private BollCfgTipoService bollCfgTipoService;
    private NodoPagamentiService nodoPagamentiService;
    private PosizioniDebitorieDaBollettazioneService posDebitoriaService;
    protected CalcoloIntervalloFactory calcoloIntervalloFactory = new CalcoloIntervalloFactory();
    private UserSecurityService userSecurityService;
    private IBollettazioneRateizzazioneService bollettazioneRateizzazioneService;
    private IEventPublisher eventPublisher;
    private IVerticalizzazioneBollettazioneService verticalizzazioneBollettazioneService;

    @Autowired
    public CalcoloBollettazioneServiceImpl(BollettazioneDAO bollettazioneDao, ResponsabiliruoliService responsabiliruoliService,
	    BollCfgTipoService bollCfgTipoService, NodoPagamentiService nodoPagamentiService,
	    PosizioniDebitorieDaBollettazioneService posDebitoriaService, UserSecurityService userSecurityService,
	    IBollCfgTipoRateService bollCfgTipoRateService, IEventPublisher eventPublisher,
	    IVerticalizzazioneBollettazioneService verticalizzazioneBollettazioneService) {

	this.bollettazioneDAO = bollettazioneDao;
	this.responsabiliruoliService = responsabiliruoliService;
	this.bollCfgTipoService = bollCfgTipoService;
	this.nodoPagamentiService = nodoPagamentiService;
	this.posDebitoriaService = posDebitoriaService;
	this.userSecurityService = userSecurityService;
	this.bollettazioneRateizzazioneService = new BollettazioneRateizzazioneService(bollCfgTipoRateService);
	this.eventPublisher = eventPublisher;
	this.verticalizzazioneBollettazioneService = verticalizzazioneBollettazioneService;
    }

    @Override
    public List<ElementoListaBollettazione> findByCodiceResponsabile(Integer codiceResponsabile, Integer firstResult, Integer maxResult) {

	List<Integer> codiciRuoli = responsabiliruoliService.findCodiciRuoloByResponsabile(codiceResponsabile);
	List<BollGestTestata> testate = this.bollettazioneDAO.findTestateByCodiciRuoli(codiciRuoli, firstResult, maxResult);
	return toElementoListaBollettazione(testate);
    }

    public int countByCodiceResponsabile(Integer codiceResponsabile) {

	List<Integer> codiciRuoli = responsabiliruoliService.findCodiciRuoloByResponsabile(codiceResponsabile);
	return this.bollettazioneDAO.countTestateByCodiciRuoli(codiciRuoli);
    }

    private List<ElementoListaBollettazione> toElementoListaBollettazione(List<BollGestTestata> bollGestTestate) {

	List<ElementoListaBollettazione> result = new ArrayList<ElementoListaBollettazione>();
	for (BollGestTestata bollGestTestata : bollGestTestate) {
	    ElementoListaBollettazione elb = new ElementoListaBollettazione();
	    elb.setAllaData(bollGestTestata.getAllaData());
	    elb.setDallaData(bollGestTestata.getDallaData());
	    elb.setDataCreazione(bollGestTestata.getDataInserimento());
	    elb.setDescrizione(bollGestTestata.getDescrizione());
	    elb.setId(bollGestTestata.getId().getCodice());
	    elb.setPeriodo(bollGestTestata.getPeriodo());
	    elb.setStato(bollGestTestata.getStato());
	    elb.setTipo(bollGestTestata.getBollCfgTipo().getDescrizione());
	    result.add(elb);
	}
	return result;
    }

    @Override
    public DettaglioBollettazione findById(Integer idBollettazione) {

	BollGestTestata bollGestTestata = this.bollettazioneDAO.getByIdForBollettazione(BollGestTestata.class, idBollettazione);
	if (null != bollGestTestata) {
	    return new DettaglioBollettazione(
		    this.bollettazioneRateizzazioneService.getPianoRateizzazioneByIdTipologia(bollGestTestata.getBollCfgTipo().getId().getCodice()),
		    bollGestTestata, this.bollettazioneDAO.findBollGestDettaglioDTOByTestata(idBollettazione));
	}
	return null;
    }

    @Override
    public boolean checkAccesso(Integer codiceResponsabile, Integer codiceBollettazione) {

	//TODO: DA IMPLEMENTARE 
	return true;
    }

    @Override
    public List<RigaBollettazione> findDettaglioByBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica) {

	BollGestTestata testata = this.bollettazioneDAO.getByIdForBollettazione(BollGestTestata.class, idBollettazione);
	Set<BollettazioneRataBean> rateizzazione = this.bollettazioneRateizzazioneService
		.getPianoRateizzazioneByIdTipologia(testata.getBollCfgTipo().getId().getCodice());
	List<BollGestDettaglioDTO> righe = this.bollettazioneDAO.findRigheByIdBollettazioneAndAnagrafe(idBollettazione, idAnagrafica);
	List<RigaBollettazione> result = new ArrayList<RigaBollettazione>();
	for (BollGestDettaglioDTO bollGestDettaglio : righe) {
	    result.add(new RigaBollettazione(bollGestDettaglio, rateizzazione, testata.getFlagRaggruppaUtenza()));
	}
	return result;
    }

    @Override
    public DettaglioRigaBollettazione findDettaglioRigaByBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica, Integer idRiga) {

	BollGestDettaglio riga = this.bollettazioneDAO.getByIdForBollettazione(BollGestDettaglio.class, idRiga);
	return new DettaglioRigaBollettazione(riga);
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazione(Integer idRiga) {

	return this.bollettazioneDAO.findDettaglioRateizzazione(idRiga);
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazionePerAnagrafica(Integer idBollettazione, Integer idAnagrafica) {

	return this.bollettazioneDAO.findDettaglioRateizzazionePerAnagrafica(idBollettazione, idAnagrafica);
    }

    @Override
    public void deleteDettaglio(Integer idDettaglio, String userName) {

	BollGestDettaglio entity = this.bollettazioneDAO.getByIdForBollettazione(BollGestDettaglio.class, idDettaglio);
	if (entity != null) {
	    if (cancellazioneConsentita(entity)) {
		BollGestDettaglio bollGestDettaglio = entity.getBollGestDettaglio();
		if (bollGestDettaglio != null) {
		    bollGestDettaglio.setFlagValidata(Boolean.FALSE);
		    bollGestDettaglio.setFlagRettificata(Boolean.FALSE);
		    bollGestDettaglio.aggiungiNoteSistema(new MessaggioRettificaAnnullata(userName));
		    this.bollettazioneDAO.save(entity);
		}
		entity.setFlagEliminata(Boolean.TRUE);
		entity.aggiungiNoteSistema(new MessaggioRigaEliminata(userName));
		this.bollettazioneDAO.save(entity);
	    } else {
		throw new BusinessValidationException("Operazione non consentita!");
	    }
	}
    }

    private boolean cancellazioneConsentita(BollGestDettaglio entity) {

	if (entity.getFlagConguaglio()) {
	    return true;
	}
	if (entity.getFlagInsAuto()) {
	    return false;
	}
	if (entity.getDettPosizioneDebitoria() != null) {
	    return false;
	}
	//TODO: implementare verifica dei ruoli operatore???
	return true;
    }

    @Override
    public void rettificaRiga(Integer idBollettazione, Integer idAnagrafica, BigDecimal importoSenzaIVA, Integer iva, BigDecimal importo,
	    Integer idRiga, String userName) {

	BollGestDettaglio oldEntity = this.bollettazioneDAO.getByIdForBollettazione(BollGestDettaglio.class, idRiga);
	BollGestDettaglio newEntity = (BollGestDettaglio) SerializationUtils.clone(oldEntity);
	// settiamo le properties alla nuova entity
	newEntity.setId(new PkId());
	newEntity.setImportoSenzaIva(importoSenzaIVA);
	newEntity.setIva(iva);
	newEntity.setImportoTotale(importo);
	newEntity.setBollGestDettaglio(oldEntity);
	newEntity.setFlagInsAuto(Boolean.FALSE);
	newEntity.setDettPosizioneDebitoria(null);
	newEntity.setNoteSistema("");
	newEntity.setBollGestTestata(oldEntity.getBollGestTestata());
	newEntity.aggiungiNoteSistema(new MessaggioNuovaRigaRettifica(userName, oldEntity.getDescrizione()));
	this.bollettazioneDAO.save(newEntity);
	oldEntity.setFlagRettificata(Boolean.TRUE);
	oldEntity.setFlagValidata(Boolean.FALSE);
	oldEntity.aggiungiNoteSistema(new MessaggioRigaRettificata(userName, importo, oldEntity.getImportoTotale()));
	this.bollettazioneDAO.save(oldEntity);
	this.bollettazioneDAO.copiaRiferimentiIstanzeOneriSuBollGestDettaglio(oldEntity, newEntity);
	this.bollettazioneDAO.copiaRiferimentiConcessioniSuBollGestDettaglio(oldEntity, newEntity);
    }

    @Override
    public void aggiungiRiga(Integer idBollettazione, Integer idAnagrafica, Integer idConto, String userName, String descrizione,
	    BigDecimal importoSenzaIVA, Integer iva, BigDecimal importo, String noteUtente) {

	Anagrafe anagrafe = this.bollettazioneDAO.getByIdForBollettazione(Anagrafe.class, idAnagrafica);
	BollGestTestata bollGestTestata = this.bollettazioneDAO.getByIdForBollettazione(BollGestTestata.class, idBollettazione);
	Conti conti = this.bollettazioneDAO.getByIdForBollettazione(Conti.class, idConto);
	BollGestDettaglio bollGestDettaglio = new BollGestDettaglio(anagrafe, bollGestTestata, conti, descrizione, importoSenzaIVA, iva, importo);
	bollGestDettaglio.aggiungiNoteSistema(new MessaggioRigaAggiuntaManualmente(userName));
	bollGestDettaglio.setNoteUtente(noteUtente);
	this.bollettazioneDAO.save(bollGestDettaglio);
    }

    @Override
    public List<CreazioneBollCfgTipo> findBollCfgTipoByCodiceResponsabile(Integer codiceResponsabile) {

	return bollCfgTipoService.findByResponsabile(codiceResponsabile);
    }

    @Override
    public void delete(Integer idBollettazione) {

	if (isDeleteAllowed(idBollettazione)) {
	    this.bollettazioneDAO.delete(idBollettazione);
	}
    }

    private boolean isDeleteAllowed(Integer idBollettazione) {

	if (this.bollettazioneDAO.esistonoRigheInviateASistemaPagamenti(idBollettazione)) {
	    throw new BusinessValidationException("Non è possibile cancellare una bollettazione con posizioni debitorie associate.");
	}
	return true;
    }

    @Override
    public void validaInteraBollettazione(Integer idBollettazione, Boolean validato, String autore) {

	List<BollGestDettaglio> righe = this.bollettazioneDAO.findRigheValidabili(idBollettazione);
	for (BollGestDettaglio bollGestDettaglio : righe) {
	    this.validaDettaglioBollettazione(bollGestDettaglio, validato, autore);
	}
    }

    @Override
    public void validaDettaglioBollettazione(Integer idRiga, Boolean valido, String autore) {

	BollGestDettaglio entity = this.bollettazioneDAO.getByIdForBollettazione(BollGestDettaglio.class, idRiga);
	this.validaDettaglioBollettazione(entity, valido, autore);
    }

    private void validaDettaglioBollettazione(BollGestDettaglio dettaglio, Boolean valido, String autore) {

	dettaglio.setFlagValidata(valido);
	dettaglio.aggiungiNoteSistema(new MessaggioValidazioneRiga(autore, valido));
	this.bollettazioneDAO.save(dettaglio);
    }

    @Override
    public String inviaNodoPagamenti(Integer idBollettazione) {

	DettaglioBollettazione boll = this.findById(idBollettazione);
	ProceduraInvioNodoBollettazioneInizializzazioneEvento eventoInit = new ProceduraInvioNodoBollettazioneInizializzazioneEvento(idBollettazione);
	this.eventPublisher.publish(eventoInit);
	logger.debug("Inizio il calcolo delle posizioni debitorie per idBollettazione {}", idBollettazione);
	List<PosizioneDebitoriaBollettazioneBean> posizioni = this.posDebitoriaService.build(boll);
	logger.debug("Finito il calcolo delle posizioni debitorie per idBollettazione {}", idBollettazione);
	BollettazioneAuditLogger loggerBollettazione = new BollettazioneAuditLogger();
	String report = "";
	long delayInvioNodo = verticalizzazioneBollettazioneService.delayInvioPosizioni();	
	boolean eseguiDelayInvioNodo = delayInvioNodo > 0;
	logger.debug("delayInvioNodo {} per idBollettazione {}", delayInvioNodo, idBollettazione);
	String statoBollettazione = StatoBollettazioneEnum.CHIUSA.getValore();
	boolean errore = false;
	logger.debug("Pubblico evento per avvio procedura invio nodo pagamenti {}", idBollettazione);
	ProceduraInvioNodoBollettazioneStartEvento evento = new ProceduraInvioNodoBollettazioneStartEvento(idBollettazione, posizioni.size());
	this.eventPublisher.publish(evento);
	logger.debug("Inizio l'invio delle posizioni debitorie per idBollettazione {}", idBollettazione);
	String identificativoOPerazione = "BOLL_" + ORMHelper.getIdcomune() + "-" + idBollettazione + "-" + UUID.randomUUID().toString();
	for (PosizioneDebitoriaBollettazioneBean posizioneDebitoriaBean : posizioni) {
	    if (posizioneDebitoriaBean.getIdDettaglioPosizioneDebitoria() == null) {
		List<Integer> idPosizioniInserite = null;
		try {
		    idPosizioniInserite = this.nodoPagamentiService.registraNuovaPosizioneDebitoria(posizioneDebitoriaBean,
			    boll.isFlagCaricamentoMassivo(), identificativoOPerazione);
		} catch (FunzioneBusinessRemotaException e) {
		    errore = true;
		    statoBollettazione = StatoBollettazioneEnum.APERTA.getValore();
		    report += loggerBollettazione.messaggioReportInvioNodoPagamenti(posizioneDebitoriaBean, e);
		    logger.error("ERRORE NELL'INVIO AL NODO DEI PAGAMENTI: {}", e.getMessage(), e);
		}
		if (idPosizioniInserite != null && !idPosizioniInserite.isEmpty()) {
		    this.updateRighe(idPosizioniInserite, posizioneDebitoriaBean, idBollettazione);
		}
	    }
	    logger.debug("Pubblico evento per elaborazione avvenuta per la procedura invio nodo pagamenti {}", idBollettazione);
	    ProceduraInvioNodoBollettazionePosizioneElabEvento eventoPosizionelaborata = new ProceduraInvioNodoBollettazionePosizioneElabEvento(
		    idBollettazione, posizioneDebitoriaBean.getSoggettoDebitore().getCodiceAnagrafe());
	    this.eventPublisher.publish(eventoPosizionelaborata);
	    if (eseguiDelayInvioNodo) {
		try {
		    Thread.sleep(delayInvioNodo);
		} catch (InterruptedException e) {
		    logger.error("Errore in Thread.sleep(" + delayInvioNodo + ");", e);
		}
	    }
	}
	logger.debug("Finito l'invio delle posizioni debitorie per idBollettazione {}", idBollettazione);
	if (errore) {
	    report = loggerBollettazione.messaggioAperturaReportInvioNodoPerBollettazione(boll,
		    userSecurityService.getCurrentlyAuthenticatedUserDetails().toString()) + report;
	    loggerBollettazione.scriviReportInvioNodoPagamenti(report);
	}
	this.bollettazioneDAO.updateStatoTestata(idBollettazione, statoBollettazione);
	ProceduraInvioNodoBollettazioneStopEvento eventoStop = new ProceduraInvioNodoBollettazioneStopEvento(idBollettazione);
	this.eventPublisher.publish(eventoStop);
	return StringUtils.defaultString(report);
    }

    private void updateRighe(List<Integer> idDettPosizioneDebitoria, PosizioneDebitoriaBollettazioneBean posizione, Integer idBollettazione) {

	List<RataBean> rate = posizione.getRate();
	if (rate.size() == 1) {
	    this.bollettazioneDAO.updateRigheSetRiferimentiPosizioneDebitoria(idDettPosizioneDebitoria.get(0), posizione.getIdRigheBollettazione());
	} else {
	    BollGestTestata bollGestTestata = this.bollettazioneDAO.getByIdForBollettazione(BollGestTestata.class, idBollettazione);
	    int index = 0;
	    for (RataBean rata : rate) {
		BollGestDettRate bollRata = new BollGestDettRate();
		for (Integer idRiga : posizione.getIdRigheBollettazione()) {
		    BollGestDettaglio bollGestDettaglio = this.bollettazioneDAO.getByIdForBollettazione(BollGestDettaglio.class, idRiga);
		    bollRata.setBollGestDettaglio(bollGestDettaglio);
		    bollRata.setDettPosizioneDebitoria(new DettPosizioneDebitoria(idDettPosizioneDebitoria.get(index)));
		    bollRata.setId(new PkId());
		    bollRata.setNumeroRata(rata.getNumerorata());
		    bollRata.setScadenza(rata.getDataScadenza());
		    bollRata.setImportoTotale(rata.getImportoTotale());
		    bollRata.setBollGestTestata(bollGestTestata);
		    this.bollettazioneDAO.inserisciRata(bollRata);
		    this.bollettazioneDAO.flush();
		}
		index++;
	    }
	}
	this.bollettazioneDAO.commit();
	this.bollettazioneDAO.flush();
    }

    @Override
    public void aggiornaStatoPagamento(Integer idBollettazione, Integer idAnagrafica) {

	try {
	    Set<VerificaPosizioneDebitoriaBean> rifIdDettaglioPosizioniDebitorie = this
		    .findPosizioniDebitorieByBollettazioneEAnagrafe(idBollettazione, idAnagrafica);
	    aggiornaStatoPagamentoByPosizioneDebitoria(rifIdDettaglioPosizioniDebitorie);
	    // this.nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettaglioPosizioniDebitorie, cfEnteCreditore);
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(
		    "Si è verificato un errore nel tentativo di contattare il sistema remoto per l'aggiornamento degli stati di pagamento: FunzioneBusinessRemotaException {}",
		    e.getMessage(), e);
	    throw new RuntimeException(
		    "Si è verificato un errore nel tentativo di contattare il sistema remoto per l'aggiornamento degli stati di pagamento; RIPROVARE IN SEGUITO");
	}
    }

    private void aggiornaStatoPagamentoByPosizioneDebitoria(Set<VerificaPosizioneDebitoriaBean> rifIdDettaglioPosizioniDebitorie)
	    throws FunzioneBusinessRemotaException {

	// Creo la mappa con chiave il cfEnteCreditore e valori il set di idPosizioneDebitoria
	Map<String, Set<Integer>> m = new HashMap<String, Set<Integer>>();
	for (VerificaPosizioneDebitoriaBean dettPosizioneDebitoria : rifIdDettaglioPosizioniDebitorie) {
	    String cfec = dettPosizioneDebitoria.getCfEnteCreditore();
	    Set<Integer> idPosizioniDeb = new HashSet<Integer>();
	    if (!m.containsKey(cfec)) {
		for (VerificaPosizioneDebitoriaBean dettPosizioneDebitoria2 : rifIdDettaglioPosizioniDebitorie) {
		    if (dettPosizioneDebitoria2.getCfEnteCreditore().equals(cfec)) {
			idPosizioniDeb.add(dettPosizioneDebitoria2.getDettPosizioneDebitoriaId());
		    }
		}
	    }
	    m.put(cfec, idPosizioniDeb);
	}
	// richiamo il metodo aggiorna per gli elementi della mappa
	Iterator<String> it = m.keySet().iterator();
	while (it.hasNext()) {
	    String cfEnteCred = (String) it.next();
	    this.nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(m.get(cfEnteCred), cfEnteCred);
	}
    }

    @Override
    public void aggiornaStatoPagamento(Integer idBollettazione) {

	try {
	    Set<VerificaPosizioneDebitoriaBean> rifIdDettaglioPosizioniDebitorie = this
		    .findDettaglioPosizioniDebitorieByBollettazione(idBollettazione);
	    aggiornaStatoPagamentoByPosizioneDebitoria(rifIdDettaglioPosizioniDebitorie);
	} catch (FunzioneBusinessRemotaException e) {
	    logger.error(
		    "Si è verificato un errore nel tentativo di contattare il sistema remoto per l'aggiornamento degli stati di pagamento: FunzioneBusinessRemotaException {}",
		    e.getMessage(), e);
	    throw new RuntimeException(
		    "Si è verificato un errore nel tentativo di contattare il sistema remoto per l'aggiornamento degli stati di pagamento; RIPROVARE IN SEGUITO");
	}
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione, Integer idAnagrafica) {

	return this.bollettazioneDAO.findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(idBollettazione, idAnagrafica);
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione) {

	return this.bollettazioneDAO.findDettaglioPosizioniDebitorieByBollettazione(idBollettazione);
    }

    @Override
    public CreazioneBollTestata inizializzaCreazioneBollTestata(Integer codiceResponsabile) {

	List<CreazioneBollCfgTipo> lBollCfgTipo = findBollCfgTipoByCodiceResponsabile(codiceResponsabile);
	CreazioneBollTestata creazioneBollTestata = new CreazioneBollTestata();
	if (!lBollCfgTipo.isEmpty()) {
	    CreazioneBollCfgTipo creazioneBollCfgTipo = lBollCfgTipo.get(0);
	    creazioneBollTestata.setBollCfgTipoId(creazioneBollCfgTipo.getBollCfgTipoId());
	    creazioneBollTestata.setIntervalloDate(getIntervalloDateDaBollCfgTipo(creazioneBollCfgTipo.getBollCfgTipoId(), new Date()));
	}
	return creazioneBollTestata;
    }

    @Override
    public IntervalloDate getIntervalloDateDaBollCfgTipo(Integer codiceTipo, Date dataOperazione) {

	BollCfgTipo bollCfgTipo = bollCfgTipoService.findById(new PkId(codiceTipo));
	String periodo = bollCfgTipo.getPeriodo();
	PeriodiEnum p = PeriodiEnum.fromValue(periodo);
	CalcoloIntervalloStrategy intervalloStrategy = calcoloIntervalloFactory.get(p);
	IntervalloDate intervalloDate = intervalloStrategy.getIntervallo(dataOperazione);
	return intervalloDate;
    }

    @Override
    public void aggiornaDataScadenza(Integer idBollettazione, Date dataScadenza) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile aggiornare la data di scadenza senza specificare l'identificativo della bollettazione da aggiornare");
	}
	if (dataScadenza == null) {
	    throw new IllegalArgumentException("La data di scadenza della bollettazione non può essere vuota");
	}
	this.bollettazioneDAO.aggiornaDataScadenza(ORMHelper.getIdcomune(), idBollettazione, dataScadenza);
    }

    @Override
    public String getHtmlForBollettazione(Integer idBollettazione, Integer codiceanagrafe, boolean isPdf, String alias, String software,
	    boolean soloValidati) {

	InputStream in = null;
	try {
	    logger.debug("getHtmlForBollettazione idBollettazione: {}, codiceanagrafe: {} eseguo la query", codiceanagrafe, idBollettazione);
	    List<Object[]> summaryList = bollettazioneDAO.getSummaryForBollettazione(idBollettazione, codiceanagrafe, soloValidati);
	    logger.debug("getHtmlForBollettazione idBollettazione: {}, codiceanagrafe: {} query eseguita", codiceanagrafe, idBollettazione);
	    SummaryBollettazione records = new SummaryBollettazione();
	    if (summaryList != null && !summaryList.isEmpty()) {
		BigDecimal totale = new BigDecimal(0);
		for (Object[] r : summaryList) {
		    totale = totale.add((BigDecimal) r[7]);
		}
		String nomeutente;
		if (summaryList.get(0)[2] == null) {
		    nomeutente = summaryList.get(0)[1] + "";
		} else {
		    nomeutente = summaryList.get(0)[2] + " " + summaryList.get(0)[1];
		}
		String descrizionebollettazione = (String) summaryList.get(0)[0];
		records.setNomeutente(nomeutente.toUpperCase());
		records.setDescrizionebollettazione(descrizionebollettazione);
		NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.ITALY);
		records.setTotale(formato.format(totale));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<SummaryBollettazioneRecord> dettagli = new ArrayList<SummaryBollettazioneRecord>();
		for (Object[] r : summaryList) {
		    SummaryBollettazioneRecord r1 = new SummaryBollettazioneRecord();
		    r1.setManifestazione((String) r[3]);
		    r1.setData(sdf.format((Date) r[4]));
		    r1.setPosteggio((String) r[5]);
		    r1.setDescrizione((String) r[6]);
		    r1.setEuro(formato.format((BigDecimal) r[7]));
		    r1.setAutorizzazione((String) r[8]);
		    dettagli.add(r1);
		}
		records.setRecordList(dettagli);
	    } else {
		logger.debug("getHtmlForBollettazione idBollettazione: {}, codiceanagrafe: {} Nessun record trovato", codiceanagrafe,
			idBollettazione);
		throw new Exception("Nessun record trovato");
	    }
	    logger.debug("getHtmlForBollettazione idBollettazione: {}, codiceanagrafe: {} creo la trasformazione", codiceanagrafe, idBollettazione);
	    JAXBContext context = JAXBContext.newInstance(SummaryBollettazione.class);
	    Marshaller marshaller = context.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
	    ByteArrayOutputStream baoxml = new ByteArrayOutputStream();
	    marshaller.marshal(records, baoxml);
	    byte[] res = baoxml.toByteArray();
	    //String xmlprova = new String(res);
	    //System.out.println(xmlprova);
	    URL pathXSL = null;
	    if (!StringUtils.isBlank(alias) && !StringUtils.isBlank(software)) {
		pathXSL = this.getClass().getClassLoader().getResource(alias + "-" + software + "-bollettazione.xsl");
	    }
	    if (pathXSL == null) {
		logger.debug("sto caricando il file it/gruppoinit/pal/gp/core/xslt/bollettazione.xsl");
		pathXSL = this.getClass().getClassLoader().getResource("it/gruppoinit/pal/gp/core/xslt/bollettazione.xsl");
	    }
	    if (logger.isDebugEnabled()) {
		logger.debug("getHtmlForBollettazione idBollettazione: {}, codiceanagrafe: {}, file individuato: {}",
			new Object[] { codiceanagrafe, idBollettazione, pathXSL });
	    }
	    in = pathXSL.openStream();
	    byte[] xslBytes = IOUtils.toByteArray(in);
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Transformer transformer = tFactory.newTransformer(new StreamSource(new ByteArrayInputStream(xslBytes)));
	    StringWriter writer = new StringWriter();
	    javax.xml.transform.stream.StreamResult outResult = new javax.xml.transform.stream.StreamResult(writer);
	    transformer.setParameter("tiporeport", isPdf ? "pdf" : "html");
	    transformer.setParameter("idbollettazione", idBollettazione);
	    transformer.setParameter("codiceanagrafe", codiceanagrafe);
	    transformer.transform(new StreamSource(new ByteArrayInputStream(res)), outResult);
	    return writer.getBuffer().toString();
	} catch (Exception e) {
	    logger.error("getHtmlForBollettazione idBollettazione: " + idBollettazione + ", codiceanagrafe: " + codiceanagrafe, e);
	    throw new RuntimeException("Errore durante il caricamento: " + e.getMessage(), e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
    }

    @Override
    public byte[] getPdfReportForBollettazione(Integer idBollettazione, Integer codiceAnagrafe, String alias, String software, boolean soloValidati)
	    throws Exception {

	String html = this.getHtmlForBollettazione(idBollettazione, codiceAnagrafe, true, alias, software, soloValidati);
	ConvertBinaryResponse resp = null;
	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	ConvertBinaryRequest cbr = new ConvertBinaryRequest(ORMHelper.getToken(), html.getBytes("UTF-8"), "HTML", "PDF");
	resp = fileConverterWsClient.convertBinary(cbr);
	return resp.getBinaryData();
    }

    @Override
    public Date getDataScadenzaBollettazione(Integer idBollettazione) {

	return this.bollettazioneDAO.getByIdForBollettazione(BollGestTestata.class, idBollettazione).getDataScadenza();
    }
}
