package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.infocamere.schema.legaldocs.ConservazioneResponseHelper;
import it.gruppoinit.pal.gp.core.dao.ArchiviazioniDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Archiviazioni;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniOggetti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniIstanzeService;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniOggettiService;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.IstanzePerArchiviazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneDocumentaleFileAvvio;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneDocumentaleXMLHelper;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiIstanza;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggetto;
import it.gruppoinit.pal.gp.core.service.helper.ArchiviazioneMetadatiOggettoList;
import it.gruppoinit.pal.gp.core.service.helper.DocumentoLegalDocHelper;
import it.gruppoinit.pal.gp.core.service.helper.IstanzePerArchiviazioneFilter;
import it.gruppoinit.pal.gp.core.service.helper.VerticalizzazioneArchiviazioneDocumentale;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class ArchiviazioniServiceImpl extends BaseServiceImpl<Archiviazioni, PkId> implements ArchiviazioniService {

    public static final Logger log = LoggerFactory.getLogger(ArchiviazioniServiceImpl.class);
    private ArchiviazioniDAO archiviazioniDAO;
    private IstanzePerArchiviazioneService istanzePerArchiviazioneService;
    private ArchiviazioniIstanzeService archiviazioniIstanzeService;
    private ArchiviazioniOggettiService archiviazioniOggettiService;
    private OggettiService oggettiService;
    private IstanzeService istanzeService;
    private VerticalizzazioniService verticalizzazioniService;
    private SoftwareService softwareService;
    private OggettiMetadatiService oggettiMetadatiService;
    private ContenttypesService contenttypesService;
    private final static String OFFLINE = "OFFLINE";
    private final static String WS = "WS";
    private final static String TOKEN_CONSERVAZIONE_LEGAL_DOC = "TOKEN_CONSERVAZIONE_LEGAL_DOC";

    @Override
    protected Class<Archiviazioni> getEntityClass() {

	return Archiviazioni.class;
    }

    @Override
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult) {

	return archiviazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Integer archiviaDocumentiIstanza(Integer codiceIstanza, Archiviazioni archiviazioneCorrente, Integer maxOggettiArchiviabili,
	    VerticalizzazioneArchiviazioneDocumentale vad, ArchiviazioneDocumentaleFileAvvio fileAvvio) throws Exception {

	throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO ");
	//	log.debug("archiviaDocumentiIstanza({}) start", codiceIstanza);
	//	Integer numOggettiArchiviati = 0;
	//	log.debug("archiviaDocumentiIstanza({}): recupero i metadati dell'istanza", codiceIstanza);
	//	ArchiviazioneMetadatiIstanza metadatiIstanza = istanzePerArchiviazioneService.findMetadatiIstanza(codiceIstanza);
	//	log.debug("archiviaDocumentiIstanza({}): inserisco un record in archiviazioniistanze", codiceIstanza);
	//	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	//	ArchiviazioniIstanze archiviazioniIstanze = new ArchiviazioniIstanze();
	//	archiviazioniIstanze.setArchiviazioni(archiviazioneCorrente);
	//	archiviazioniIstanze.setIstanze(istanze);
	//	log.debug("archiviaDocumentiIstanza({}): verifico se ci sono metadati obbligatori mancanti per l'istanza", codiceIstanza);
	//	String metadatiObbligatoriMancanti = metadatiIstanza.getMetadatiObbligatoriMancanti();
	//	if (StringUtils.isNotBlank(metadatiObbligatoriMancanti)) {
	//	    log.info("archiviaDocumentiIstanza({}): mancano dei metadati obbligatori, segno l'istanza come esclusa: {}", codiceIstanza,
	//		    metadatiObbligatoriMancanti);
	//	    archiviazioneCorrente.appendErrore(metadatiObbligatoriMancanti);
	//	    archiviazioniIstanze.setErrore(metadatiObbligatoriMancanti);
	//	    archiviazioniIstanze.setEsclusa(true);
	//	    archiviazioniIstanzeService.insert(archiviazioniIstanze);
	//	    archiviazioniIstanzeService.evict(archiviazioniIstanze);
	//	} else {
	//	    log.debug(
	//		    "archiviaDocumentiIstanza({}): metadati obbligatori presenti, recupero la lista di tutti gli oggetti archiviabili dell'istanza",
	//		    codiceIstanza);
	//	    ArchiviazioneMetadatiOggettoList archiviazioneMetadatiOggettoList = istanzePerArchiviazioneService.findOggettiArchiviabili(
	//		    metadatiIstanza, vad);
	//	    Set<ArchiviazioneMetadatiOggetto> oggArchiviabiliList = archiviazioneMetadatiOggettoList.getArchiviazioneMetadatiOggettoList();
	//	    log.debug("archiviaDocumentiIstanza({}): numero oggetti da archiviare = {}", codiceIstanza, oggArchiviabiliList.size());
	//	    if (oggArchiviabiliList.size() > maxOggettiArchiviabili) {
	//		log.info("archiviaDocumentiIstanza({}): numero oggetti da archiviare = {} supera maxOggettiArchiviabili={} return 0", new Object[] {
	//			codiceIstanza, oggArchiviabiliList.size(), maxOggettiArchiviabili });
	//		numOggettiArchiviati = -1;
	//	    } else {
	//		StringBuffer metadatiObbligatoriMancantiOggetto = new StringBuffer();
	//		log.debug("archiviaDocumentiIstanza({}): verifico se mancano dei metadati obbligatori per gli oggetti archiviabili", codiceIstanza);
	//		for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
	//		    metadatiObbligatoriMancantiOggetto.append(oggArchiviabile.getMetadatiObbligatoriMancanti(metadatiIstanza));
	//		}
	//		String erroriBloccanti = archiviazioneMetadatiOggettoList.getErroreBloccante();
	//		if (StringUtils.isNotEmpty(metadatiObbligatoriMancantiOggetto.toString()) || StringUtils.isNotBlank(erroriBloccanti)) {
	//		    log.info(
	//			    "archiviaDocumentiIstanza({}): mancano dei metadati obbligatori per gli oggetti archiviabili segno l'istanza come esclusa: {}",
	//			    codiceIstanza, metadatiObbligatoriMancantiOggetto.toString());
	//		    archiviazioneCorrente.appendErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
	//		    archiviazioniIstanze.setErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
	//		    archiviazioniIstanze.setEsclusa(true);
	//		    archiviazioniIstanzeService.insert(archiviazioniIstanze);
	//		    archiviazioniIstanzeService.evict(archiviazioniIstanze);
	//		} else {
	//		    log.debug("archiviaDocumentiIstanza({}): metadati obbligatori per gli oggetti archiviabili presenti", codiceIstanza);
	//		    if (StringUtils.isNotBlank(archiviazioneMetadatiOggettoList.getErrore())) {
	//			log.warn("archiviaDocumentiIstanza({}): alcuni oggetti sono stati esclusi dall'archiviazione: {}", codiceIstanza,
	//				archiviazioneMetadatiOggettoList.getErrore());
	//			archiviazioneCorrente.appendErrore(archiviazioneMetadatiOggettoList.getErrore());
	//			archiviazioniIstanze.setErrore(archiviazioneMetadatiOggettoList.getErrore());
	//		    }
	//		    archiviazioniIstanzeService.insert(archiviazioniIstanze);
	//		    log.debug("archiviaDocumentiIstanza({}): archivio gli oggetti...", codiceIstanza);
	//		    for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
	//			archiviaOggettoIstanza(archiviazioniIstanze, archiviazioneCorrente.getIdJob(), oggArchiviabile, metadatiIstanza, vad,
	//				fileAvvio);
	//		    }
	//		    numOggettiArchiviati = oggArchiviabiliList.size();
	//		    archiviazioniIstanzeService.evict(archiviazioniIstanze);
	//		}
	//	    }
	//	}
	//	log.debug("archiviaDocumentiIstanza({}): archiviati {} oggetti", codiceIstanza, numOggettiArchiviati);
	//	istanzeService.evict(istanze);
	//	return numOggettiArchiviati;
    }

    //    @Override
    //    public Integer archiviaDocumentiPerIstanza(Integer codiceIstanza, Archiviazioni archiviazioneCorrente,
    //	    VerticalizzazioneArchiviazioneDocumentale vad, ArchiviazioneDocumentaleFileAvvio fileAvvio) throws Exception {
    //
    //	log.debug("archiviaDocumentiPerIstanza({}) start", codiceIstanza);
    //	Integer numOggettiArchiviati = 0;
    //	log.debug("archiviaDocumentiPerIstanza({}): recupero i metadati dell'istanza", codiceIstanza);
    //	ArchiviazioneMetadatiIstanza metadatiIstanza = istanzePerArchiviazioneService.findMetadatiIstanza(codiceIstanza);
    //	log.debug("archiviaDocumentiPerIstanza({}): inserisco un record in archiviazioniistanze", codiceIstanza);
    //	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
    //	ArchiviazioniIstanze archiviazioniIstanze = new ArchiviazioniIstanze();
    //	archiviazioniIstanze.setArchiviazioni(archiviazioneCorrente);
    //	archiviazioniIstanze.setIstanze(istanze);
    //	log.debug("archiviaDocumentiPerIstanza({}): verifico se ci sono metadati obbligatori mancanti per l'istanza", codiceIstanza);
    //	String metadatiObbligatoriMancanti = metadatiIstanza.getMetadatiObbligatoriMancanti();
    //	if (StringUtils.isNotBlank(metadatiObbligatoriMancanti)) {
    //	    log.info("archiviaDocumentiPerIstanza({}): mancano dei metadati obbligatori, segno l'istanza come esclusa: {}", codiceIstanza,
    //		    metadatiObbligatoriMancanti);
    //	    archiviazioneCorrente.appendErrore(metadatiObbligatoriMancanti);
    //	    archiviazioniIstanze.setErrore(metadatiObbligatoriMancanti);
    //	    archiviazioniIstanze.setEsclusa(true);
    //	    archiviazioniIstanzeService.insert(archiviazioniIstanze);
    //	    archiviazioniIstanzeService.evict(archiviazioniIstanze);
    //	} else {
    //	    log.debug(
    //		    "archiviaDocumentiPerIstanza({}): metadati obbligatori presenti, recupero la lista di tutti gli oggetti archiviabili dell'istanza",
    //		    codiceIstanza);
    //	    ArchiviazioneMetadatiOggettoList archiviazioneMetadatiOggettoList = istanzePerArchiviazioneService.findOggettiArchiviabili(
    //		    metadatiIstanza, vad);
    //	    Set<ArchiviazioneMetadatiOggetto> oggArchiviabiliList = archiviazioneMetadatiOggettoList.getArchiviazioneMetadatiOggettoList();
    //	    log.debug("archiviaDocumentiPerIstanza({}): numero oggetti da archiviare = {}", codiceIstanza, oggArchiviabiliList.size());
    //	    StringBuffer metadatiObbligatoriMancantiOggetto = new StringBuffer();
    //	    log.debug("archiviaDocumentiPerIstanza({}): verifico se mancano dei metadati obbligatori per gli oggetti archiviabili", codiceIstanza);
    //	    for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
    //		metadatiObbligatoriMancantiOggetto.append(oggArchiviabile.getMetadatiObbligatoriMancanti(metadatiIstanza));
    //	    }
    //	    String erroriBloccanti = archiviazioneMetadatiOggettoList.getErroreBloccante();
    //	    if (StringUtils.isNotEmpty(metadatiObbligatoriMancantiOggetto.toString()) || StringUtils.isNotBlank(erroriBloccanti)) {
    //		log.info(
    //			"archiviaDocumentiIstanza({}): mancano dei metadati obbligatori per gli oggetti archiviabili segno l'istanza come esclusa: {}",
    //			codiceIstanza, metadatiObbligatoriMancantiOggetto.toString());
    //		archiviazioneCorrente.appendErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
    //		archiviazioniIstanze.setErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
    //		archiviazioniIstanze.setEsclusa(true);
    //		archiviazioniIstanzeService.insert(archiviazioniIstanze);
    //		archiviazioniIstanzeService.evict(archiviazioniIstanze);
    //	    } else {
    //		log.debug("archiviaDocumentiPerIstanza({}): metadati obbligatori per gli oggetti archiviabili presenti", codiceIstanza);
    //		if (StringUtils.isNotBlank(archiviazioneMetadatiOggettoList.getErrore())) {
    //		    log.warn("archiviaDocumentiPerIstanza({}): alcuni oggetti sono stati esclusi dall'archiviazione: {}", codiceIstanza,
    //			    archiviazioneMetadatiOggettoList.getErrore());
    //		    archiviazioneCorrente.appendErrore(archiviazioneMetadatiOggettoList.getErrore());
    //		    archiviazioniIstanze.setErrore(archiviazioneMetadatiOggettoList.getErrore());
    //		}
    //		archiviazioniIstanzeService.insert(archiviazioniIstanze);
    //		log.debug("archiviaDocumentiPerIstanza({}): archivio gli oggetti...", codiceIstanza);
    //		for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
    //		    archiviaOggettoIstanza(archiviazioniIstanze, archiviazioneCorrente.getIdJob(), oggArchiviabile, metadatiIstanza, vad, fileAvvio);
    //		}
    //		numOggettiArchiviati = oggArchiviabiliList.size();
    //		archiviazioniIstanzeService.evict(archiviazioniIstanze);
    //	    }
    //	}
    //	log.debug("archiviaDocumentiPerIstanza({}): archiviati {} oggetti", codiceIstanza, numOggettiArchiviati);
    //	istanzeService.evict(istanze);
    //	return numOggettiArchiviati;
    //    }
    @Override
    public Integer archiviaDocumentiPerOggetto(Integer codiceIstanza, String prefissoNomePacchetto, VerticalizzazioneArchiviazioneDocumentale vad,
	    ArchiviazioneDocumentaleFileAvvio fileAvvio, IstanzePerArchiviazioneFilter filter, String idSession) throws Exception {

	log.debug("archiviaDocumentiPerOggetto# Istanzio l'oggetto Archiviazioni...");
	Archiviazioni archiviazioni = new Archiviazioni();
	archiviazioni.setData(new Date());
	archiviazioni.setIdJob(prefissoNomePacchetto + getIdJob("COD_I_" + codiceIstanza.toString() + "_"));
	Software _software = softwareService.findById(ORMHelper.getSoftware());
	archiviazioni.setSoftware(_software);
	log.debug("archiviaDocumentiPerOggetto({}) start", codiceIstanza);
	Integer numOggettiArchiviati = 0;
	log.debug("archiviaDocumentiPerOggetto({}): recupero i metadati dell'istanza", codiceIstanza);
	ArchiviazioneMetadatiIstanza metadatiIstanza = istanzePerArchiviazioneService.findMetadatiIstanza(codiceIstanza);
	log.debug("archiviaDocumentiPerOggetto({}): inserisco un record in archiviazioniistanze", codiceIstanza);
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	ArchiviazioniIstanze archiviazioniIstanze = new ArchiviazioniIstanze();
	archiviazioniIstanze.setArchiviazioni(archiviazioni);
	archiviazioniIstanze.setIstanze(istanze);
	log.debug("archiviaDocumentiPerOggetto({}): verifico se ci sono metadati obbligatori mancanti per l'istanza", codiceIstanza);
	String metadatiObbligatoriMancanti = metadatiIstanza.getMetadatiObbligatoriMancanti();
	/////
	if (StringUtils.isNotBlank(metadatiObbligatoriMancanti)) {
	    // INSERISCO UNA RIGA SU ARCHIVIAZIONE E UNA COLLEGATA SU ARCHIVIAZIONI_ISTANZA, MOSTRA CHE PER QUELL'ISTANZA NON HO
	    // POTUTO FARE L'ARCHIVIAZIONE PER META DATI MANCANTI
	    log.info("archiviaDocumentiPerOggetto({}): mancano dei metadati obbligatori, segno l'istanza come esclusa: {}", codiceIstanza,
		    metadatiObbligatoriMancanti);
	    archiviazioni.appendErrore(metadatiObbligatoriMancanti);
	    archiviazioni.setCorretto(false);
	    archiviazioni.setNumero(0);
	    archiviazioniIstanze.setErrore(metadatiObbligatoriMancanti);
	    archiviazioniIstanze.setEsclusa(true);
	    this.insert(archiviazioni);
	    archiviazioniIstanze.setArchiviazioni(archiviazioni);
	    archiviazioniIstanzeService.insert(archiviazioniIstanze);
	    archiviazioniIstanzeService.evict(archiviazioniIstanze);
	} else {
	    log.debug(
		    "archiviaDocumentiPerOggetto({}): metadati obbligatori presenti, recupero la lista di tutti gli oggetti archiviabili dell'istanza",
		    codiceIstanza);
	    ArchiviazioneMetadatiOggettoList archiviazioneMetadatiOggettoList = istanzePerArchiviazioneService.findOggettiArchiviabili(
		    metadatiIstanza, filter, vad);
	    Set<ArchiviazioneMetadatiOggetto> oggArchiviabiliList = archiviazioneMetadatiOggettoList.getArchiviazioneMetadatiOggettoList();
	    log.debug("archiviaDocumentiPerOggetto({}): numero oggetti da archiviare = {}", codiceIstanza, oggArchiviabiliList.size());
	    StringBuffer metadatiObbligatoriMancantiOggetto = new StringBuffer();
	    log.debug("archiviaDocumentiPerOggetto({}): verifico se mancano dei metadati obbligatori per gli oggetti archiviabili", codiceIstanza);
	    for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
		metadatiObbligatoriMancantiOggetto.append(oggArchiviabile.getMetadatiObbligatoriMancanti(metadatiIstanza));
	    }
	    String erroriBloccanti = archiviazioneMetadatiOggettoList.getErroreBloccante();
	    if (StringUtils.isNotEmpty(metadatiObbligatoriMancantiOggetto.toString()) || StringUtils.isNotBlank(erroriBloccanti)) {
		// INSERISCO UNA RIGA SU ARCHIVIAZIONE E UNA COLLEGATA SU ARCHIVIAZIONI_ISTANZA, MOSTRA CHE PER QUELL'ISTANZA NON HO
		// POTUTO FARE L'ARCHIVIAZIONE PER META DATI MANCANTI SU UN OGGETTO O CI SONO ERRORI BLOCCANTI
		log.info(
			"archiviaDocumentiPerOggetto({}): mancano dei metadati obbligatori per gli oggetti archiviabili segno l'istanza come esclusa: {}",
			codiceIstanza, metadatiObbligatoriMancantiOggetto.toString());
		archiviazioni.setCorretto(false);
		archiviazioni.setNumero(0);
		archiviazioni.appendErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
		this.insert(archiviazioni);
		archiviazioniIstanze.setErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
		archiviazioniIstanze.setEsclusa(true);
		archiviazioniIstanzeService.insert(archiviazioniIstanze);
		archiviazioniIstanzeService.evict(archiviazioniIstanze);
	    } else {
		log.debug("archiviaDocumentiPerOggetto({}): metadati obbligatori per gli oggetti archiviabili presenti", codiceIstanza);
		if (StringUtils.isNotBlank(archiviazioneMetadatiOggettoList.getErrore())) {
		    log.warn("archiviaDocumentiPerOggettoSuCartella({}): alcuni oggetti sono stati esclusi dall'archiviazione: {}", codiceIstanza,
			    archiviazioneMetadatiOggettoList.getErrore());
		    archiviazioni.appendErrore(archiviazioneMetadatiOggettoList.getErrore());
		    archiviazioniIstanze.setErrore(archiviazioneMetadatiOggettoList.getErrore());
		}
		// archiviazioniIstanzeService.insert(archiviazioniIstanze);
		log.debug("archiviaDocumentiPerOggetto({}): archivio gli oggetti...", codiceIstanza);
		if (!oggArchiviabiliList.isEmpty() && StringUtils.isBlank(archiviazioneMetadatiOggettoList.getErrore())) {
		    for (ArchiviazioneMetadatiOggetto oggArchiviabile : oggArchiviabiliList) {
			archiviaOggetto(archiviazioni, archiviazioniIstanze, prefissoNomePacchetto, oggArchiviabile, metadatiIstanza, vad, fileAvvio,
				idSession);
		    }
		} else {
		    log.info("archiviaDocumentiPerOggetto({}): L'istanza con codice {} non ha documenti da mandare in conservazione", codiceIstanza,
			    codiceIstanza);
		    archiviazioni.setCorretto(false);
		    archiviazioni.setNumero(0);
		    String eIstanza = "L'istanza non ha oggetti archiviabili";
		    String eOggetto = "L'istanza non ha oggetti archiviabili";
		    if (archiviazioneMetadatiOggettoList != null && StringUtils.isNotBlank(archiviazioneMetadatiOggettoList.getErrore())) {
			log.info(
				"archiviaDocumentiPerOggetto({}): L'istanza con codice {} può contenere dei documenti ma nessuno compatibile con l'archiviazione",
				codiceIstanza, codiceIstanza);
			// l'errore non è bloccante,ma siccome tutti i documenti della pratica non sono archiviabili la mettiamo come non 
			// mandata in coservazione. permetterà all'operatore di verificare il perchè non ci siano documenti da mandare in conservazione
			// Es. c'è stato il caso di docuementi non correttamente salvati, tipo nomedocumento.pdf ughi.p7m il sistema li riconosce come non
			// archiviabili
			eIstanza = "L'istanza potrebbe contenere documenti,ma nessuno compatibile con l'archiviazione. Controllare l'errore riportato in istanze escluse";
			eOggetto = archiviazioneMetadatiOggettoList.getErrore();
		    } else {
			log.info("archiviaDocumentiPerOggetto({}): L'istanza con codice {} non contiene documenti ", codiceIstanza, codiceIstanza);
		    }
		    archiviazioni.setErrore(eIstanza);
		    this.insert(archiviazioni);
		    archiviazioniIstanze.setErrore(metadatiObbligatoriMancantiOggetto.toString() + erroriBloccanti);
		    archiviazioniIstanze.setEsclusa(true);
		    archiviazioniIstanze.setErrore(eOggetto);
		    archiviazioniIstanze.setIstanze(istanze);
		    archiviazioniIstanzeService.insert(archiviazioniIstanze);
		    ArchiviazioniOggetti archiviazioniOggetti = new ArchiviazioniOggetti();
		    archiviazioniOggetti.setArchiviazioni(archiviazioni);
		    archiviazioniOggetti.setArchiviazioniIstanze(archiviazioniIstanze);
		    for (ArchiviazioneMetadatiOggetto archiviazioneMetadatiOggetto : oggArchiviabiliList) {
			if (archiviazioneMetadatiOggetto.getCodiceOggetto() != null) {
			    Oggetti o = oggettiService.findById(new PkId(archiviazioneMetadatiOggetto.getCodiceOggetto()));
			    archiviazioniOggetti.setOggetti(o);
			    archiviazioniOggettiService.insert(archiviazioniOggetti);
			}
		    }
		}
		//numOggettiArchiviati = oggArchiviabiliList.size();
		archiviazioniIstanzeService.evict(archiviazioniIstanze);
	    }
	}
	log.debug("archiviaDocumentiPerOggetto({}): archiviati {} oggetti", codiceIstanza, numOggettiArchiviati);
	istanzeService.evict(istanze);
	return numOggettiArchiviati;
    }

    @Override
    public Archiviazioni insertArchiviazioneErroreGenerale(String errore, String prefissoNomePacchetto) {

	Archiviazioni archiviazioni = new Archiviazioni();
	archiviazioni.setCorretto(false);
	archiviazioni.setData(new Date());
	String _errore = StringUtils.defaultIfEmpty(errore, "Errore non previsto durante la ricerca delle istanze da archiviare");
	archiviazioni.appendErrore(_errore);
	archiviazioni.setIdJob(prefissoNomePacchetto);
	archiviazioni.setNumero(0);
	Software _software = softwareService.findById(ORMHelper.getSoftware());
	archiviazioni.setSoftware(_software);
	this.insert(archiviazioni);
	return archiviazioni;
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

    private void archiviaOggettoIstanza(ArchiviazioniIstanze archiviazioniIstanze, String jobId, ArchiviazioneMetadatiOggetto metaOgg,
	    ArchiviazioneMetadatiIstanza metaIstanza, VerticalizzazioneArchiviazioneDocumentale vad, ArchiviazioneDocumentaleFileAvvio fileAvvio)
	    throws Exception {

	throw new NotImplementedException("MODALITA' DI ARCHIVIAZIONE NON PIU' DISPONIBILE. IMPOSTARE ARCHIVIAZIONE_PER_OGGETTO ");
	//	log.debug("archiviaOggettoIstanza: oggetto = {}", metaOgg);
	//	try {
	//	    Oggetti oggetti = oggettiService.findById(new PkId(metaOgg.getCodiceOggetto()));
	//	    long millis = (new Date()).getTime();
	//	    StringBuffer _fileIndiceName = new StringBuffer();
	//	    _fileIndiceName.append(metaIstanza.getIdcomune()).append("_").append(metaIstanza.getTipologiaPratica()).append("_")
	//		    .append(metaOgg.getCodiceIstanza()).append("_").append(metaOgg.getCodiceOggetto()).append("_").append(millis);
	//	    String fileIndiceName = "INDEX_" + _fileIndiceName.toString() + ".xml";
	//	    //archiviare file e file indice nella cartella di legaldocs
	//	    String _folderName = getJobFolderPath(vad.getDataFolderPath(), jobId);
	//	    String fileName = metaOgg.getFileName(metaIstanza, vad);
	//	    File folderName = new File(_folderName);
	//	    String fileIndiceContent = metaOgg.getFileIndiceXML(metaIstanza, fileName, vad);
	//	    //SCRIVO L'ALLEGATO
	//	    writeFile(oggetti.getOggetto(), folderName.getAbsolutePath(), fileName);
	//	    //SCRIVO IL FILE DI INDICE
	//	    FileUtils.writeStringToFile(new File(folderName, fileIndiceName), fileIndiceContent, Charsets.ISO_8859_1.toString());
	//	    //AGGIORNO IL DB
	//	    ArchiviazioniOggetti archiviazioniOggetti = new ArchiviazioniOggetti();
	//	    archiviazioniOggetti.setArchiviazioni(archiviazioniIstanze.getArchiviazioni());
	//	    archiviazioniOggetti.setArchiviazioniIstanze(archiviazioniIstanze);
	//	    archiviazioniOggetti.setOggetti(oggetti);
	//	    archiviazioniOggettiService.insert(archiviazioniOggetti);
	//	    addDocumentToFileAvvio(fileAvvio, fileName, fileIndiceName, fileIndiceContent);
	//	    oggettiService.evict(oggetti);
	//	    archiviazioniOggettiService.evict(archiviazioniOggetti);
	//	} catch (Exception e) {
	//	    log.error("archiviaOggettoIstanza: oggetto = {}", metaOgg, e);
	//	    throw e;
	//	}
    }

    private void archiviaOggetto(Archiviazioni archiviazioni, ArchiviazioniIstanze archiviazioniIstanze, String prefissoPacchetto,
	    ArchiviazioneMetadatiOggetto metaOgg, ArchiviazioneMetadatiIstanza metaIstanza, VerticalizzazioneArchiviazioneDocumentale vad,
	    ArchiviazioneDocumentaleFileAvvio fileAvvio, String idSession) throws Exception {

	// CREO UN OGGETTO ARCHIVIAZIONI E ARCHIVIAZIONI_ISTANE, SERVIRà PER NOTIFICARE CHE UN SINGOLO DOCUMENTO E'
	// STATO MANDATO IN CONSERVAZIONE CORRETTAMENTE
	log.debug("archiviaOggetto# Setto id job all'oggetto archiviazione....");
	archiviazioni.setIdJob(prefissoPacchetto + getIdJob("COD_O_" + metaOgg.getCodiceOggetto().toString() + "_"));
	archiviazioni.setCorretto(null);
	archiviazioni.setErrore(null);
	archiviazioni.setId(null);
	archiviazioni.setArchiviazioniIstanzes(null);
	archiviazioni.setArchiviazioniOggettis(null);
	archiviazioni.setNumero(0);
	log.debug("archiviaOggetto# Inserisco l'oggetto archiviazione");
	this.insert(archiviazioni);
	archiviazioniIstanze.setArchiviazioni(archiviazioni);
	archiviazioniIstanze.setErrore(null);
	archiviazioniIstanze.setEsclusa(false);
	archiviazioniIstanze.setId(null);
	archiviazioniIstanzeService.insert(archiviazioniIstanze);
	log.info("archiviaOggetto: id job={}", archiviazioni.getIdJob());
	log.debug("archiviaOggetto({}) start", metaOgg.getCodiceOggetto());
	log.debug("archiviaOggetto: oggetto = {}", metaOgg);
	boolean isErroreCreazioneCartella = false;
	ConservazioneResponseHelper conservazioneResponseHelper = null;
	try {
	    log.debug("archiviaOggetto# Recupero l'oggetto da archiviare codice = {}", metaOgg.getCodiceOggetto());
	    Oggetti oggetti = oggettiService.findById(new PkId(metaOgg.getCodiceOggetto()));
	    String hashFileContetnt = DigestUtils.sha256Hex(oggetti.getOggetto());
	    metaOgg.setHashFile(hashFileContetnt);
	    String fileName = metaOgg.getFileName(metaIstanza, vad);
	    log.debug("archiviaOggetto# Nome file da archiviare = {}", fileName);
	    String hashfile = metaOgg.getHashFile();
	    log.debug("archiviaOggetto# hash file = {}", hashfile);
	    String fileIndiceName = createNomeFileIndex(metaIstanza, metaOgg);
	    log.debug("archiviaOggetto# Nome file indice = {}", fileIndiceName);
	    String fileIndiceContent = metaOgg.getFileIndiceXML(metaIstanza, fileName, vad);
	    log.debug("archiviaOggetto# Creato stringa xml file indice = {}");
	    String hashFileIndiceContetnt = DigestUtils.sha256Hex(fileIndiceContent);
	    log.debug("archiviaOggetto# Creato hash file indice = {}", hashFileIndiceContetnt);
	    inizializzaDocumentToFileAvvio(fileAvvio, fileName, hashfile, fileIndiceName, fileIndiceContent);
	    log.debug("archiviaOggetto# Inizializzazzine dei file riuscita...");
	    if (vad.getTIPO_SERVIZIO().equalsIgnoreCase(OFFLINE)) {
		throw new NotImplementedException("Modalità offline non più disponibile...");
		//		log.debug("archiviaOggetto# MODALITA {}", vad.getTIPO_SERVIZIO());
		//		if (!new File(vad.getDataFolderPath(), archiviazioni.getIdJob()).mkdir()) {
		//		    StringBuffer s = new StringBuffer("Errore nella creazione della cartella ").append(vad.getDataFolderPath()).append("\\")
		//			    .append(archiviazioni.getIdJob()).append(" per l'archiviazione dei file");
		//		    log.error("archiviaOggetto: {}", s.toString());
		//		    isErroreCreazioneCartella = true;
		//		    throw new Exception(s.toString());
		//		}
		//		//////
		//		//archiviare file e file indice nella cartella di legaldocs
		//		String _folderName = getJobFolderPath(vad.getDataFolderPath(), archiviazioni.getIdJob());
		//		File folderName = new File(_folderName);
		//		//SCRIVO L'ALLEGATO
		//		writeFile(oggetti.getOggetto(), folderName.getAbsolutePath(), fileName);
		//		//SCRIVO IL FILE DI INDICE
		//		FileUtils.writeStringToFile(new File(folderName, fileIndiceName), fileIndiceContent, Charsets.ISO_8859_1.toString());
		//		log.debug("archiviaOggetto: creazione file legaldoc_connector_trigger_invio.xml");
		//		//creare file di avvio e spostarlo nella cartella di legaldocs
		//		ArchiviazioneDocumentaleXMLHelper.writeFileAvvioConservazioneSuCartella(fileAvvio, _folderName);
		//		updateArchiviazioneAftrerConservazione(archiviazioni, archiviazioniIstanze, conservazioneResponseHelper, oggetti);
	    } else if (vad.getTIPO_SERVIZIO().equalsIgnoreCase(WS)) {
		log.debug("archiviaOggetto# MODALITA {}", vad.getTIPO_SERVIZIO());
		String legaldoc_connector_trigger_invio = ArchiviazioneDocumentaleXMLHelper.writeFileAvvioConservazioneSerizioRest(fileAvvio,
			hashFileIndiceContetnt);
		conservazioneResponseHelper = ArchiviazioneDocumentaleXMLHelper.invokeConservazioneDocumento(oggetti,
			legaldoc_connector_trigger_invio, fileIndiceContent, idSession, vad);
		if (conservazioneResponseHelper != null) {
		    if (conservazioneResponseHelper.isConservazioneSospesa()) {
			log.debug("archiviaOggetto# non è stata invocata la conservazione. sospesa per l'oggetto {}", oggetti.getId().getCodice());
			// metadato conservaizone sospesa
			log.debug("archiviaOggetto# Aggiungo metadato {} con valore {}", "CONSERVAZIONE_DOC_SOSPESA", "SI");
			OggettiMetadati metadati = new OggettiMetadati();
			OggettiMetadatiId id = new OggettiMetadatiId();
			id.setChiave("CONSERVAZIONE_DOC_SOSPESA");
			id.setCodiceoggetto(oggetti.getId().getCodice());
			id.setIdcomune(ORMHelper.getIdcomune());
			metadati.setId(id);
			metadati.setValore("SI");
			oggettiMetadatiService.insert(metadati);
			log.debug("archiviaOggetto# Elimino i record su su archiviazione e archiviazione istanze ", oggetti.getId().getCodice());
			archiviazioniIstanzeService.delete(archiviazioniIstanze);
			this.delete(archiviazioni);
		    } else if (conservazioneResponseHelper.getIndex() != null) {
			log.debug("archiviaOggetto# conservazione ok ... {},", conservazioneResponseHelper.getIndex().getSelfDescription().getID()
				.getValue());
			OggettiMetadati oggettiMetadati = new OggettiMetadati();
			OggettiMetadatiId id = new OggettiMetadatiId(metaOgg.getCodiceOggetto(), TOKEN_CONSERVAZIONE_LEGAL_DOC);
			oggettiMetadati.setId(id);
			oggettiMetadati.setValore(conservazioneResponseHelper.getIndex().getSelfDescription().getID().getValue());
			oggettiMetadatiService.insert(oggettiMetadati);
			updateArchiviazioneAftrerConservazione(archiviazioni, archiviazioniIstanze, conservazioneResponseHelper, oggetti);
		    } else if (conservazioneResponseHelper.getError() != null) {
			log.debug("archiviaOggetto# conservazione ko ... {}: {},", conservazioneResponseHelper.getError().getCode(),
				conservazioneResponseHelper.getError().getDescription());
			updateArchiviazioneAftrerConservazione(archiviazioni, archiviazioniIstanze, conservazioneResponseHelper, oggetti);
		    }
		} else {
		    log.debug(
			    "archiviaOggetto# non è stata invocata la conservazione, elimino i record su su archiviazione e archiviazione istanze per il codice oggetto = {}",
			    oggetti.getId().getCodice());
		    archiviazioniIstanzeService.delete(archiviazioniIstanze);
		    this.delete(archiviazioni);
		}
	    }
	} catch (Exception e) {
	    log.error("archiviaOggettoIstanza: oggetto = {}", metaOgg, e);
	    if (isErroreCreazioneCartella && EntityUtils.getNestedProperty(archiviazioni, "id.codice") != null) {
		this.delete(archiviazioni);
	    }
	    //throw e;
	}
    }

    private void updateArchiviazioneAftrerConservazione(Archiviazioni archiviazioni, ArchiviazioniIstanze archiviazioniIstanze,
	    ConservazioneResponseHelper conservazioneResponseHelper, Oggetti oggetti) {

	ArchiviazioniOggetti archiviazioniOggetti = new ArchiviazioniOggetti();
	archiviazioniOggetti.setArchiviazioni(archiviazioniIstanze.getArchiviazioni());
	archiviazioniOggetti.setArchiviazioniIstanze(archiviazioniIstanze);
	archiviazioniOggetti.setOggetti(oggetti);
	archiviazioniOggettiService.insert(archiviazioniOggetti);
	//////////////////////////////////////////////////////////////////////
	//addDocumentToFileAvvio(fileAvvio, fileName, fileIndiceName);///////
	//////////////////////////////////////////////////////////////////////
	oggettiService.evict(oggetti);
	archiviazioniOggettiService.evict(archiviazioniOggetti);
	//aggiornare record in ARCHIVIAZIONI con la data odierna
	archiviazioni.setData(new Date());
	if (conservazioneResponseHelper != null && conservazioneResponseHelper.getError() != null) {
	    archiviazioni.setNumero(0);
	    archiviazioni.setCorretto(false);
	    archiviazioni
		    .setErrore(conservazioneResponseHelper.getError().getCode() + ": " + conservazioneResponseHelper.getError().getDescription());
	} else {
	    archiviazioni.setNumero(1);
	    archiviazioni.setCorretto(true);
	}
	this.update(archiviazioni);
	log.info("archiviaOggetto: archiviati {} file", 1);
    }

    private String createNomeFileIndex(ArchiviazioneMetadatiIstanza metaIstanza, ArchiviazioneMetadatiOggetto metaOgg) {

	long millis = (new Date()).getTime();
	StringBuffer _fileIndiceName = new StringBuffer();
	_fileIndiceName.append(metaIstanza.getIdcomune()).append("_").append(metaIstanza.getTipologiaPratica()).append("_")
		.append(metaOgg.getCodiceIstanza()).append("_").append(metaOgg.getCodiceOggetto()).append("_").append(millis);
	String fileIndiceName = "INDEX_" + _fileIndiceName.toString() + ".xml";
	log.debug("createNomeFileIndex# Nome file index = {}", fileIndiceName);
	return fileIndiceName;
    }

    private void addDocumentToFileAvvio(ArchiviazioneDocumentaleFileAvvio fileAvvio, String fileName, String fileIndiceName, String fileIndiceContent) {

	DocumentoLegalDocHelper documentoLegalDocHelper = new DocumentoLegalDocHelper();
	ChiaveValoreBean<String, DocumentoLegalDocHelper> document = new ChiaveValoreBean<String, DocumentoLegalDocHelper>();
	document.setChiave(fileIndiceName);
	documentoLegalDocHelper.setFileName(fileName);
	String metaVal = DigestUtils.sha1Hex(fileIndiceContent.getBytes());
	documentoLegalDocHelper.setHashFile(metaVal);
	document.setValore(documentoLegalDocHelper);
	fileAvvio.getDocumentList().add(document);
    }

    private void inizializzaDocumentToFileAvvio(ArchiviazioneDocumentaleFileAvvio fileAvvio, String fileName, String hashFile, String fileIndiceName,
	    String fileIndiceContent) {

	DocumentoLegalDocHelper documentoLegalDocHelper = new DocumentoLegalDocHelper();
	ChiaveValoreBean<String, DocumentoLegalDocHelper> document = new ChiaveValoreBean<String, DocumentoLegalDocHelper>();
	if (!fileAvvio.getDocumentList().isEmpty()) {
	    fileAvvio.getDocumentList().clear();
	}
	document.setChiave(fileIndiceName);
	documentoLegalDocHelper.setFileName(fileName);
	documentoLegalDocHelper.setHashFile(hashFile);
	document.setValore(documentoLegalDocHelper);
	fileAvvio.getDocumentList().add(document);
    }

    private void writeFile(byte[] content, String folderName, String fileName) throws Exception {

	OutputStream os = null;
	try {
	    os = new FileOutputStream(new File(folderName, fileName));
	    os.write(content);
	} catch (Exception e) {
	    log.error("writeFile: folderName={}, fileName={}", new Object[] { folderName, fileName, e });
	    throw e;
	} finally {
	    if (os != null) {
		try {
		    os.close();
		} catch (IOException e) {
		    log.error("writeFile: error closing output stream.", e);
		}
	    }
	}
    }

    private String getJobFolderPath(String dataFolderPath, String jobId) {

	return dataFolderPath + File.separator + jobId;
    }

    @Override
    public List<Archiviazioni> findAll(Integer firstResult, Integer maxResult, Boolean isSoloConErrori) {

	return archiviazioniDAO.findAll(firstResult, maxResult, isSoloConErrori);
    }

    @Override
    public void insert(Archiviazioni entity) {

	if (validateEntity(entity)) {
	    archiviazioniDAO.insert(entity);
	}
    }

    @Override
    public Archiviazioni findById(PkId id) {

	return archiviazioniDAO.findById(id);
    }

    @Override
    public void update(Archiviazioni entity) {

	if (validateEntity(entity)) {
	    archiviazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Archiviazioni entity) {

	if (isDeleteAllowed(entity)) {
	    VerticalizzazioneArchiviazioneDocumentale vad = new VerticalizzazioneArchiviazioneDocumentale(verticalizzazioniService, softwareService,
		    contenttypesService);
	    archiviazioniDAO.delete(entity);
	    File jobFolderPath = new File(vad.getDataFolderPath(), entity.getIdJob());
	    try {
		FileUtils.deleteDirectory(jobFolderPath);
	    } catch (IOException e) {
		log.error("delete: errore durante la cancellazione della cartella '{}'", jobFolderPath.getName(), e);
	    }
	}
    }

    protected boolean isDeleteAllowed(Archiviazioni entity) {

	return true;
    }

    @Override
    public int countRecords() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return archiviazioniDAO.countRecord(ft);
    }

    @Autowired
    public void setIstanzePerArchiviazioneService(IstanzePerArchiviazioneService istanzePerArchiviazioneService) {

	this.istanzePerArchiviazioneService = istanzePerArchiviazioneService;
    }

    @Autowired
    public void setArchiviazioniIstanzeService(ArchiviazioniIstanzeService archiviazioniIstanzeService) {

	this.archiviazioniIstanzeService = archiviazioniIstanzeService;
    }

    @Autowired
    public void setArchiviazioniOggettiService(ArchiviazioniOggettiService archiviazioniOggettiService) {

	this.archiviazioniOggettiService = archiviazioniOggettiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setArchiviazioniDAO(ArchiviazioniDAO archiviazioniDAO) {

	this.archiviazioniDAO = archiviazioniDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }
}
