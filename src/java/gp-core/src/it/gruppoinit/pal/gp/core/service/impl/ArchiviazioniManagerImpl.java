package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzePerArchiviazioneDTO;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniManager;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.IstanzePerArchiviazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneDocumentaleFileAvvio;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneDocumentaleXMLHelper;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneArchiviazioneDocumentale;
import it.gruppoinit.pal.gp.core.utils.BaseEnvironment;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.File;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArchiviazioniManagerImpl extends BaseEnvironment implements ArchiviazioniManager {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioniManagerImpl.class);
    private ArchiviazioniService archiviazioniService;
    private IstanzePerArchiviazioneService istanzePerArchiviazioneService;
    private SoftwareService softwareService;
    private ContenttypesService contenttypesService;
    private final static String WS = "WS";
    public final static String ARCHIVIAZIONE_MULTI_ISTANZE = "ARCHIVIAZIONE_MULTI_ISTANZE";
    public final static String ARCHIVIAZIONE_PER_OGGETTO = "ARCHIVIAZIONE_PER_OGGETTO";

    //private DSSWSClient clientdss;
    @Override
    synchronized public void archiviazione(String idcomunealias, String[] software) throws Exception {

	//questo controllo serve per l'utilizzo da parte dello scheduler che potrebbe lavorare per comuni e moduli differenti
	for (String sw : software) {
	    if (StringUtils.isNotBlank(idcomunealias)) {
		setORMHelper(idcomunealias);
	    }
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC,
		    WebConstants.VERTICALIZZAZIONE_ARCHIVIAZIONE_DOC_LEGALDOC_ALGORITMO_ARCHIVIAZIONE, sw);
	    if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
		if (vp.getValore().equals("ARCHIVIAZIONE_MULTI_ISTANZE")) {
		    throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO ");
		    //		    log.info("Start archiviazione software {}, con algoritmo di tipo {}", sw, vp.getValore());
		    //		    this.eseguiArchiviazione(idcomunealias, sw);
		    //		    log.info("End archiviazione software {}, con algoritmo di tipo {}", sw, vp.getValore());
		} else if (vp.getValore().equals("ARCHIVIAZIONE_PER_OGGETTO")) {
		    log.info("Start archiviazione software {}, con algoritmo di tipo {}", sw, vp.getValore());
		    this.eseguiArchiviazionePerOggetto(idcomunealias, sw);
		    log.info("End archiviazione software {}, con algoritmo di tipo {}", sw, vp.getValore());
		}
	    } else {
		log.info("Algoritmo non impostato per il software {}", sw);
	    }
	}
    }

    @Override
    synchronized public void eseguiArchiviazione(String idcomunealias, String software) throws Exception {

	throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO ");
	//	log.info("eseguiArchiviazione: start");
	//	//questo controllo serve per l'utilizzo da parte dello scheduler che potrebbe lavorare per comuni e moduli differenti
	//	if (StringUtils.isNotBlank(idcomunealias)) {
	//	    if (StringUtils.isNotBlank(software)) {
	//		setORMHelperSoftware(idcomunealias, software);
	//	    } else {
	//		setORMHelper(idcomunealias);
	//	    }
	//	}
	//	Archiviazioni archiviazioni = new Archiviazioni();
	//	try {
	//	    VerticalizzazioneArchiviazioneDocumentale vad = new VerticalizzazioneArchiviazioneDocumentale(verticalizzazioniService, softwareService,
	//		    contenttypesService);
	//	    String prefissoNomePacchetto = "";
	//	    if (vad.getPrefissoNomePacchetto() != null && vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()) != null
	//		    && StringUtils.isNotBlank(vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()))) {
	//		prefissoNomePacchetto = vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()) + "_";
	//	    }
	//	    archiviazioni.setIdJob(prefissoNomePacchetto + getIdJob(null));
	//	    Software _software = softwareService.findById(ORMHelper.getSoftware());
	//	    archiviazioni.setSoftware(_software);
	//	    IstanzePerArchiviazioneFilter filter = new IstanzePerArchiviazioneFilter(vad);
	//	    //inserire nuovo record in ARCHIVIAZIONI
	//	    archiviazioniService.insert(archiviazioni);
	//	    if (!new File(vad.getDataFolderPath(), archiviazioni.getIdJob()).mkdir()) {
	//		log.error("eseguiArchiviazione: errore nella creazione della cartella {} nella cartella {}", archiviazioni.getIdJob(),
	//			vad.getDataFolderPath());
	//		throw new Exception("Errore nella creazione della cartella per l'archiviazione dei file.");
	//	    }
	//	    log.info("eseguiArchiviazione: id job={}", archiviazioni.getIdJob());
	//	    //recuperare le istanze da archiviare
	//	    Integer maxOggettiArchiviabili = vad.getMaxNumFiles();
	//	    Integer numOggettiArchiviatiTotali = 0;
	//	    boolean emptyResult = false;
	//	    ArchiviazioneDocumentaleFileAvvio fileAvvio = new ArchiviazioneDocumentaleFileAvvio(vad);
	//	    while (!emptyResult && maxOggettiArchiviabili > 0) {
	//		log.info("eseguiArchiviazione: maxOggettiArchiviabili={}", maxOggettiArchiviabili);
	//		List<IstanzePerArchiviazioneDTO> list = istanzePerArchiviazioneService.findIstanzePerArchiviazioneDocumentale(filter);
	//		log.debug("eseguiArchiviazione: istanze per archiviazione={} con maxResult={}", list.size(), filter.getMaxResult());
	//		if (list.isEmpty()) {
	//		    emptyResult = true;
	//		}
	//		for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : list) {
	//		    Integer numOggettiArchiviati = archiviazioniService.archiviaDocumentiIstanza(istanzePerArchiviazioneDTO.getId().getCodice(),
	//			    archiviazioni, maxOggettiArchiviabili, vad, fileAvvio);
	//		    if (numOggettiArchiviati == -1) {
	//			maxOggettiArchiviabili = 0;
	//			break;
	//		    }
	//		    maxOggettiArchiviabili -= numOggettiArchiviati;
	//		    numOggettiArchiviatiTotali += numOggettiArchiviati;
	//		    if (maxOggettiArchiviabili <= 0) {
	//			break;
	//		    }
	//		}
	//	    }
	//	    if (numOggettiArchiviatiTotali > 0) {
	//		log.debug("eseguiArchiviazione: creazione file legaldoc_connector_trigger_invio.xml");
	//		//creare file di avvio e spostarlo nella cartella di legaldocs
	//		String folderName = getJobFolderPath(vad.getDataFolderPath(), archiviazioni.getIdJob());
	//		ArchiviazioneDocumentaleXMLHelper.writeFileAvvioConservazioneSuCartella(fileAvvio, folderName);
	//	    }
	//	    //aggiornare record in ARCHIVIAZIONI con la data odierna
	//	    archiviazioni.setData(new Date());
	//	    archiviazioni.setNumero(numOggettiArchiviatiTotali);
	//	    archiviazioniService.update(archiviazioni);
	//	    log.info("eseguiArchiviazione: archiviati {} file", numOggettiArchiviatiTotali);
	//	} catch (Exception e) {
	//	    log.error("eseguiArchiviazione", e);
	//	    if (archiviazioni.getId().getCodice() != null) {
	//		String errore = StringUtils.defaultIfEmpty(e.getMessage(), "Errore non previsto");
	//		archiviazioni.appendErrore(errore);
	//		archiviazioniService.update(archiviazioni);
	//	    }
	//	    throw e;
	//	} finally {
	//	    if (StringUtils.isNotBlank(idcomunealias)) {
	//		resetThreadLocalVars();
	//	    }
	//	}
    }

    @Override
    synchronized public void eseguiArchiviazionePerOggetto(String idcomunealias, String software, Date da, Date a) throws Exception {

	log.info("eseguiArchiviazionePerOggetto: start");
	//questo controllo serve per l'utilizzo da parte dello scheduler che potrebbe lavorare per comuni e moduli differenti
	if (StringUtils.isNotBlank(idcomunealias)) {
	    if (StringUtils.isNotBlank(software)) {
		setORMHelperSoftware(idcomunealias, software);
	    } else {
		setORMHelper(idcomunealias);
	    }
	}
	Archiviazioni archiviazioni = null;
	String prefissoNomePacchetto = "";
	try {
	    log.debug("eseguiArchiviazionePerOggetto# Recupero parametri della verticalizzazione");
	    // TODO GESTIRE PARAMETRI OBSOLETI
	    VerticalizzazioneArchiviazioneDocumentale vad = new VerticalizzazioneArchiviazioneDocumentale(verticalizzazioniService, softwareService,
		    contenttypesService);
	    log.debug("eseguiArchiviazionePerOggetto# recupero il prefisso nome del pacchetto, se configurato in verticalizzazione");
	    // Fisso per ogni pacchetto. Varia a secondo del software. La configurazione,se esistente si trova in verticalizzazione
	    if (vad.getPrefissoNomePacchetto() != null && vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()) != null
		    && StringUtils.isNotBlank(vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()))) {
		prefissoNomePacchetto = vad.getPrefissoNomePacchetto().get(ORMHelper.getSoftware()) + "_";
		log.debug("archiviaDocumentiPerOggetto# prefisso pacchetto : {}", prefissoNomePacchetto);
	    }
	    // Creo filtro per recupero istanze da archiviare
	    log.debug("eseguiArchiviazionePerOggetto# Creo filtro per ricerca istanze......");
	    IstanzePerArchiviazioneFilter filter = new IstanzePerArchiviazioneFilter(vad);
	    if (da != null || a != null) {
		log.debug("eseguiArchiviazionePerOggetto# Filtro date impostato dal web da {} a {}", da, a);
		if (da != null) {
		    filter.setDallaData(da);
		}
		if (a != null) {
		    filter.setAllaData(a);
		}
	    } else {
		log.debug("eseguiArchiviazionePerOggetto# Filtro date impostato verticalizzazione. Da  {}", vad.getDallaData());
		filter.setDallaData(vad.getDallaData());
		if (vad.getAllaData() != null) {
		    log.debug("eseguiArchiviazionePerOggetto# A  {}", vad.getAllaData());
		    filter.setDallaData(vad.getAllaData());
		}
	    }
	    log.debug("eseguiArchiviazionePerOggetto# Ricerco istanze con oggetii da archiviare.........");
	    Set<IstanzePerArchiviazioneDTO> list = istanzePerArchiviazioneService.findIstanzeConOggettiPerArchiviazioneDocumentale(filter);
	    if (!list.isEmpty()) {
		log.debug("eseguiArchiviazionePerOggetto# tipo servizio usato per archiviazione = {} ",
			StringUtils.defaultIfEmpty(vad.getTIPO_SERVIZIO(), "Servizio non definito"));
		String sessionId = "";
		boolean go = true;
		if (vad.getTIPO_SERVIZIO().equalsIgnoreCase(WS)) {
		    go = false;
		    try {
			sessionId = ArchiviazioneDocumentaleXMLHelper.getIdSession(vad);
			go = true;
		    } catch (Exception e) {
			log.error("eseguiArchiviazionePerOggetto# {}", e);
			archiviazioni = archiviazioniService.insertArchiviazioneErroreGenerale(e.getMessage(), prefissoNomePacchetto
				+ getIdJob("_GENERALE_"));
		    }
		}
		if (go) {
		    log.debug("eseguiArchiviazionePerOggetto: istanze per archiviazione={} ", list.size());
		    for (IstanzePerArchiviazioneDTO istanzePerArchiviazioneDTO : list) {
			ArchiviazioneDocumentaleFileAvvio fileAvvio = new ArchiviazioneDocumentaleFileAvvio(vad);
			Integer numOggettiArchiviati = archiviazioniService.archiviaDocumentiPerOggetto(
				istanzePerArchiviazioneDTO.getCodiceistanza(), prefissoNomePacchetto, vad, fileAvvio, filter, sessionId);
		    }
		    if (StringUtils.isNotBlank(sessionId)) {
			log.debug("eseguiArchiviazionePerOggetto# Logout sessione archiviazione rest service");
			ArchiviazioneDocumentaleXMLHelper.logoutIdSession(vad, sessionId);
		    }
		}
	    } else {
		log.info("eseguiArchiviazionePerOggetto: istanze per archiviazione={} ", 0);
	    }
	} catch (Exception e) {
	    log.error("eseguiArchiviazionePerOggetto", e);
	    archiviazioni = new Archiviazioni();
	    archiviazioni = archiviazioniService.insertArchiviazioneErroreGenerale(e.getMessage(), prefissoNomePacchetto + getIdJob("_GENERALE_"));
	    throw e;
	} finally {
	    if (StringUtils.isNotBlank(idcomunealias)) {
		resetThreadLocalVars();
	    }
	}
    }

    @Override
    synchronized public void eseguiArchiviazionePerOggetto(String idcomunealias, String software) throws Exception {

	this.eseguiArchiviazionePerOggetto(idcomunealias, software, null, null);
    }

    //TODO metodo duplicato, spostare
    private String getJobFolderPath(String dataFolderPath, String jobId) {

	return dataFolderPath + File.separator + jobId;
    }

    private String getIdJob(String numeroIstanza) {

	StringBuffer b = new StringBuffer();
	String sep = "_";
	b.append(ORMHelper.getIdcomuneAlias());
	b.append(sep);
	b.append(ORMHelper.getSoftware());
	b.append(sep);
	if (StringUtils.isNotBlank(numeroIstanza)) {
	    b.append(numeroIstanza);
	    b.append(sep);
	}
	b.append(Utilities.getToday("yyyyMMdd"));
	b.append(sep);
	b.append(UUID.randomUUID().toString());
	return b.toString();
    }

    @Autowired
    public void setIstanzePerArchiviazioneService(IstanzePerArchiviazioneService istanzePerArchiviazioneService) {

	this.istanzePerArchiviazioneService = istanzePerArchiviazioneService;
    }

    @Autowired
    public void setArchiviazioniService(ArchiviazioniService archiviazioniService) {

	this.archiviazioniService = archiviazioniService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }
}
