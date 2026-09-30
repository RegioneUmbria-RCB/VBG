package it.gruppoinit.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gov.impresainungiorno.schema.suap.ente.CooperazioneSUAPEnte;
import it.gov.impresainungiorno.schema.suap.pratica.AdempimentoSUAP;
import it.gov.impresainungiorno.schema.suap.pratica.AnagraficaPersona;
import it.gov.impresainungiorno.schema.suap.pratica.ImpiantoProduttivo.DatiCatastali;
import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.constants.AttivitaDaEseguireEnum;
import it.gruppoinit.constants.Costants;
import it.gruppoinit.constants.WebConstants;
import it.gruppoinit.domain.helper.ParametriHelper;
import it.gruppoinit.domain.nla.Allegato;
import it.gruppoinit.service.AttivitaDaEseguireResult;
import it.gruppoinit.service.DettaglioPraticaAndAttivitaTypeHelperService;
import it.gruppoinit.service.MappingElementICToBOService;
import it.gruppoinit.utilities.LoggerArchiviocomunicazioni;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.definitions.StcWSClient;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.PersonaFisicaType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.SchedaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;

@Service
public class DettaglioPraticaAndAttivitaTypeHelperServiceImpl implements DettaglioPraticaAndAttivitaTypeHelperService {

    private static final Logger log = LoggerFactory.getLogger(DettaglioPraticaAndAttivitaTypeHelperServiceImpl.class);
    private MappingElementICToBOService mappingElementICToBOService;
    private StcWSClient stcWSClient;

    @Autowired
    public void setMappingElementICToBOService(MappingElementICToBOService mappingElementICToBOService) {

	this.mappingElementICToBOService = mappingElementICToBOService;
    }

    @Autowired
    public void setStcWSClient(StcWSClient stcWSClient) {

	this.stcWSClient = stcWSClient;
    }

    public StcWSClient getStcWSClient() {

	return stcWSClient;
    }

    @Override
    public DettaglioPraticaType getDettaglioPraticaType(CooperazioneSUAPEnte cooperazioneSUAPEnte, RiepilogoPraticaSUAP r,
	    List<Allegato> listAllegati, ParametriHelper parametriHelper, File rpsuap_file) {

	DettaglioPraticaType dettaglioPraticaType = new DettaglioPraticaType();
	log.debug("getDettaglioPraticaType# Start Creo DettaglioPraticaType.......");
	//////////////////////// SEZIONE INTESTAZIONE E INFO GENERICHE PRATICA /////////////////////////////////
	String codicePratica = r.getIntestazione().getCodicePratica();
	/**
	 * //////////////////////////////////////////// SOLO PER TEST ///////////////////////////////////////////////
	 * ////////////////////////////////////////////////////////// ///////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * codicePratica = StringUtils.substring(codicePratica, 0, codicePratica.length() - 1); int i =
	 * RandomUtils.nextInt(100); codicePratica += String.valueOf(i);
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////
	 * /////////////////////////////////////////////////////////////////////////////////////////////////////////
	 */
	///////////////////////////////////////
	dettaglioPraticaType.setCodicePraticaTelematica(codicePratica);
	dettaglioPraticaType.setIdPratica(codicePratica);
	///??
	dettaglioPraticaType.setNumeroPratica(codicePratica);
	// Ad oggi non recuperati...
	dettaglioPraticaType.setDataPratica(Utilities.getToday());
	dettaglioPraticaType.setOraDataPratica(Utilities.getOrariosistema());
	XMLGregorianCalendar dataProt = null;
	if (cooperazioneSUAPEnte.getIntestazione().getProtocolloPraticaSuap() != null
		&& cooperazioneSUAPEnte.getIntestazione().getProtocolloPraticaSuap().getDataRegistrazione() != null) {
	    dataProt = cooperazioneSUAPEnte.getIntestazione().getProtocolloPraticaSuap().getDataRegistrazione();
	    dettaglioPraticaType.setDataPratica(dataProt);
	    String oraprot = Utilities.getOrario(Utilities.getDate(dataProt));
	    dettaglioPraticaType.setOraDataPratica(oraprot);
	    if (StringUtils.isNotBlank(cooperazioneSUAPEnte.getIntestazione().getProtocolloPraticaSuap().getNumeroRegistrazione())) {
		////////////////////////SEZIONE PROTOCOLLO /////////////////////////////////
		dettaglioPraticaType.setDataProtocolloGenerale(dataProt);
		dettaglioPraticaType
			.setNumeroProtocolloGenerale(cooperazioneSUAPEnte.getIntestazione().getProtocolloPraticaSuap().getNumeroRegistrazione());
	    }
	}
	dettaglioPraticaType.setDomicilioElettronico(StringUtils.defaultIfEmpty(r.getIntestazione().getDomicilioElettronico(), ""));
	dettaglioPraticaType.setOggetto(StringUtils.defaultIfEmpty(cooperazioneSUAPEnte.getIntestazione().getOggettoPratica().getValue(), ""));
	String marker = "devono rispettare tali formati.";
	int index = cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione().indexOf(marker);
	if (index != -1) {
	    String result = cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione().substring(index + marker.length());
	    dettaglioPraticaType.setAnnotazioni(result);
	}
	//Modifica richiesta da DAvide Farinella
	//	dettaglioPraticaType.setAnnotazioni(
	//		StringUtils.defaultIfEmpty(StringUtils.abbreviate(cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione(), 399), ""));
	log.debug("getDettaglioPraticaType# SEZIONI ANAGRAFICHE...................");
	// ///////////////////////////////// SEZIONE AZIENDA RICHIENDENTE ///////////////////////////////////////
	if (r.getIntestazione().getImpresa() != null) {
	    log.debug("getDettaglioPraticaType# populate AZIENDA RICHIEDENTE...");
	    PersonaGiuridicaType personaGiuridicaType = new PersonaGiuridicaType();
	    mappingElementICToBOService.populatePersonaGiuridicaType(r.getIntestazione().getImpresa(), personaGiuridicaType);
	    dettaglioPraticaType.setAziendaRichiedente(personaGiuridicaType);
	    RichiedenteType richiedenteType = new RichiedenteType();
	    mappingElementICToBOService.populateRichiedenteType(r.getIntestazione().getImpresa().getLegaleRappresentante(),
		    r.getIntestazione().getImpresa().getLegaleRappresentante().getCarica(), richiedenteType);
	    dettaglioPraticaType.setRichiedente(richiedenteType);
	} else if (r.getIntestazione().getRichiedente() != null) {
	    AnagraficaPersona anagraficaPersona = r.getIntestazione().getRichiedente();
	    // ///////////////////////////////// SEZIONE  RICHIENDENTE ///////////////////////////////////////    
	    log.debug("getDettaglioPraticaType# populate RICHIEDENTE..."); // DA FARE
	    RichiedenteType richiedenteType = new RichiedenteType();
	    mappingElementICToBOService.popolateRichiedenteType(anagraficaPersona, richiedenteType);
	    dettaglioPraticaType.setRichiedente(richiedenteType);
	}
	// ///////////////////////////////// SEZIONE  INTERMEDIARIO ///////////////////////////////////////    
	if (r.getIntestazione().getDichiarante() != null) {
	    log.debug("getDettaglioPraticaType# populate INTERMEDIARIO...");
	    AnagrafeType intermediario = new AnagrafeType();
	    PersonaFisicaType personaFisicaTypeIntermediario = new PersonaFisicaType();
	    mappingElementICToBOService.populatePersonaFisicaTypeByEstrimiDichiarante(r.getIntestazione().getDichiarante(),
		    personaFisicaTypeIntermediario);
	    mappingElementICToBOService.populateAnagrafeTypeFisica(personaFisicaTypeIntermediario, intermediario);
	    dettaglioPraticaType.setIntermediario(intermediario);
	}
	/////////////////////////////////// SEZIONE PROCEDIMENTI ///////////////////////////////////////////////////////
	ProcedimentoType procedimentoType = new ProcedimentoType();
	List<AdempimentoSUAP> anAdempimentoSUAPs = r.getStruttura().getModulo();
	for (AdempimentoSUAP adempimentoSUAP : anAdempimentoSUAPs) {
	    procedimentoType = new ProcedimentoType();
	    procedimentoType.setCodice(adempimentoSUAP.getCod());
	    //procedimentoType.setPrincipale(true);
	    procedimentoType.setDescrizione(adempimentoSUAP.getNome());
	    dettaglioPraticaType.getProcedimenti().add(procedimentoType);
	}
	////////////////////////////////////// SEZIONE COMUNE ////////////////////////////////////////////////////////
	/////////////////////////////////// SEZIONE LOCALIZZAZIONE  E COMUNE ///////////////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# SEZIONI LOCALIZZAZIONE...................");
	if (r.getIntestazione().getImpiantoProduttivo() != null) {
	    log.debug("getDettaglioPraticaType# populate LOCALIZZAZIONE...");
	    LocalizzazioneNelComuneType indirizzoPratica = new LocalizzazioneNelComuneType();
	    if (r.getIntestazione().getImpiantoProduttivo().getIndirizzo() != null) {
		mappingElementICToBOService.populateLocalizzazioneNelComuneType(r.getIntestazione().getImpiantoProduttivo().getIndirizzo(),
			indirizzoPratica);
		if (r.getIntestazione().getImpiantoProduttivo().getDatiCatastali() != null
			&& !r.getIntestazione().getImpiantoProduttivo().getDatiCatastali().isEmpty()) {
		    log.debug("getDettaglioPraticaType# Popolo dati catastali dell'indirizzo = {}",
			    r.getIntestazione().getImpiantoProduttivo().getIndirizzo().getDenominazioneStradale());
		    List<DatiCatastali> dataiCatastali = r.getIntestazione().getImpiantoProduttivo().getDatiCatastali();
		    for (DatiCatastali datocatastale : dataiCatastali) {
			// popolo tutti i dati catasti, la modellazione prevede che per ogno oggetto ci sia una lista di particelle e una di sub,
			//la nostra struttura è piatta e quindi dobbiamo normalizzare e duplicare gli oggetti per tutti le particelle e sub
			List<String> mappalis = datocatastale.getMappale();
			for (String mappale : mappalis) {
			    List<String> subs = datocatastale.getSubalterno();
			    for (String sub : subs) {
				RiferimentoCatastaleType riferimentoCatastaleType = new RiferimentoCatastaleType();
				String sezione = "";
				if (datocatastale.getSezione() != null) {
				    sezione = StringUtils.defaultIfEmpty(datocatastale.getSezione().getValue(), "");
				}
				mappingElementICToBOService.populateRiferimentoCatastaleType(datocatastale.getTipo(), sezione,
					datocatastale.getFoglio(), mappale, sub, riferimentoCatastaleType);
				indirizzoPratica.getRiferimentoCatastale().add(riferimentoCatastaleType);
			    }
			}
		    }
		}
		if (r.getIntestazione().getImpiantoProduttivo().getIndirizzo().getComune() != null) {
		    log.debug("getDettaglioPraticaType# populate COMUNE");
		    ComuneType comuneType = new ComuneType();
		    mappingElementICToBOService.populateComuneType(r.getIntestazione().getImpiantoProduttivo().getIndirizzo().getComune(),
			    comuneType);
		    dettaglioPraticaType.setCodiceComune(comuneType);
		}
	    }
	    dettaglioPraticaType.getLocalizzazione().add(indirizzoPratica);
	}
	/////////////////////////////////// SEZIONE DOCUMENTI ///////////////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# SEZIONI DOCUMENTI.......................");
	DocumentiType documentiType = null;
	for (Allegato allegato : listAllegati) {
	    documentiType = new DocumentiType();
	    mappingElementICToBOService.populateDocumentiType(allegato, documentiType);
	    // il datahanderl di questo documento è già stato consumato, quindi devo utilizzare il file temporaneo 
	    // creato per ricrearlo
	    String prefisso = cooperazioneSUAPEnte.getIntestazione().getCodicePratica();
	    String nomeFileConfronto = prefisso + "." + WebConstants.PREFIX_ALLEGATO_SUAP + "." + WebConstants.ESTENSIONE_XML;
	    String nomeFile = allegato.getNomeFile();
	    if (nomeFile.toUpperCase().equals(nomeFileConfronto.toUpperCase())) {
		DataSource fds = new FileDataSource(rpsuap_file);
		DataHandler handler = new DataHandler(fds);
		documentiType.getAllegati().getFile().setBinaryData(handler);
	    }
	    dettaglioPraticaType.getDocumenti().add(documentiType);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////// SEZIONE SCHEDE DINAMICHE ///////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# SEZIONI SCHEDE DINAMICHE ........................");
	List<SchedaType> schedaTypes = new ArrayList<SchedaType>();
	mappingElementICToBOService.populateSchedeType(listAllegati, schedaTypes);
	dettaglioPraticaType.getSchede().addAll(schedaTypes);
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	log.debug("getDettaglioPraticaType# End Creo DettaglioPraticaType.......");
	return dettaglioPraticaType;
    }

    @Override
    public DettaglioAttivitaType getDettaglioAttivitaType(CooperazioneSUAPEnte cooperazioneSUAPEnte, File rpsuap_file, List<Allegato> listAllegati,
	    ParametriHelper parametriHelper) {

	log.debug("getDettaglioAttivitaType# Creo DettaglioAttivitaType");
	DettaglioAttivitaType datiAttivita = new DettaglioAttivitaType();
	datiAttivita.setIdAttivita(cooperazioneSUAPEnte.getIntestazione().getCodicePratica() + "-" + System.currentTimeMillis());
	datiAttivita.setDataAttivita(Utilities.getToday());
	datiAttivita.setOraDataAttivita(Utilities.getOrariosistema());
	datiAttivita.setEsito(true);
	String marker = "devono rispettare tali formati.";
	int index = cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione().indexOf(marker);
	if (index != -1) {
	    String result = cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione().substring(index + marker.length());
	    datiAttivita.setParere(result);
	}
	//	datiAttivita.setParere(
	//		StringUtils.defaultIfEmpty(StringUtils.abbreviate(cooperazioneSUAPEnte.getIntestazione().getTestoComunicazione(), 399), ""));
	XMLGregorianCalendar dataProt = null;
	if (cooperazioneSUAPEnte.getIntestazione().getProtocollo() != null
		&& cooperazioneSUAPEnte.getIntestazione().getProtocollo().getDataRegistrazione() != null) {
	    dataProt = cooperazioneSUAPEnte.getIntestazione().getProtocollo().getDataRegistrazione();
	    if (StringUtils.isNotBlank(cooperazioneSUAPEnte.getIntestazione().getProtocollo().getNumeroRegistrazione())) {
		////////////////////////SEZIONE PROTOCOLLO /////////////////////////////////
		datiAttivita.setDataProtocolloGenerale(dataProt);
		datiAttivita.setNumeroProtocolloGenerale(cooperazioneSUAPEnte.getIntestazione().getProtocollo().getNumeroRegistrazione());
	    }
	}
	//datiAttivita.setIdAttivita(reRichiestaPraticaResponse.getDettaglioPratica().getDettaglioPratica());
	//datiAttivita.setIdPratica(richiestaPraticaCollegataResponse.getDettaglio().getDettaglioPratica().getIdPratica());
	datiAttivita.setIdPratica(cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	TipoAttivitaType attivitaType = new TipoAttivitaType();
	attivitaType.setCodice(parametriHelper.getVerticalizzazioniHelper().getCOD_MOV_RIENTRO_INTEGRAZ_DOC());
	//attivitaType.setDescrizione(parametriHelper.getVerticalizzazioniHelper().getCOD_MOV_RIENTRO_INTEGRAZ_DOC());
	datiAttivita.setTipoAttivita(attivitaType);
	/////////////////////////////////// SEZIONE DOCUMENTI ///////////////////////////////////////////////////////
	log.debug("getDettaglioAttivitaType# SEZIONI DOCUMENTI.......................");
	DocumentiType documentiType = null;
	for (Allegato allegato : listAllegati) {
	    documentiType = new DocumentiType();
	    mappingElementICToBOService.populateDocumentiType(allegato, documentiType);
	    // il datahanderl di questo documento è già stato consumato, quindi devo utilizzare il file temporaneo 
	    // creato per ricrearlo
	    String prefisso = cooperazioneSUAPEnte.getIntestazione().getCodicePratica();
	    String nomeFileConfronto = prefisso + "." + WebConstants.PREFIX_ALLEGATO_SUAP + "." + WebConstants.ESTENSIONE_XML;
	    String nomeFile = allegato.getNomeFile();
	    if (nomeFile.toUpperCase().equals(nomeFileConfronto.toUpperCase())) {
		DataSource fds = new FileDataSource(rpsuap_file);
		DataHandler handler = new DataHandler(fds);
		documentiType.getAllegati().getFile().setBinaryData(handler);
	    }
	    datiAttivita.getDocumenti().add(documentiType);
	}
	return datiAttivita;
    }

    @Override
    public AttivitaDaEseguireResult selectAttivitaDaSvolgere(CooperazioneSUAPEnte cooperazioneSUAPEnte, ParametriHelper parametriHelper)
	    throws Exception {

	AttivitaDaEseguireResult result = new AttivitaDaEseguireResult();
	log.debug("selectAttivitaDaSvolgere# Eseguo logica per decidere l'operazione da svolgere. Inserimento pratica o inserimento attività");
	SportelloType sportelloinfocamere = Utilities.getSportelloInfocamere(parametriHelper.getDeployProperties());
	SportelloType sportellobackoffice = Utilities.getSportelloBackoffice(parametriHelper.getDeployProperties(),
		parametriHelper.getEnteBackoffice(), parametriHelper.getSportelloBackoffice());
	AttivitaDaEseguireEnum attivitaDaEseguireEnum = AttivitaDaEseguireEnum.ATTIVITA_NON_CODIFICATA;
	DettaglioPraticaType dettaglioPraticaPresente = null;
	try {
	    LoggerArchiviocomunicazioni.logComunicazioneGenerico("Ricerco pratica in VBG (Dettaglio pratica). Codice Pratica = {}",
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	    log.debug("selectAttivitaDaSvolgere# Ricerco pratica in VBG (Dettaglio pratica). Codice Pratica = {}",
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica());
	    RichiestaPraticaCollegataResponse rpc = stcWSClient.richiestaPraticaCollegata(sportelloinfocamere, sportellobackoffice,
		    cooperazioneSUAPEnte.getIntestazione().getCodicePratica(), parametriHelper.getTokenStc());
	    // ATTENZIONE AL CASO PRATICA ENTRATE CON ERRORE, NON RITORNATO CON DETTAGLIO PRATICA. MA STC BLOCCA INSERIMENTO , VERRA GESTITO
	    // A SEGUITO DELL'INSERIMENTO VERO E PROPRIO
	    if (!rpc.getDettaglioErrore().isEmpty()) {
		String codiceerrore = rpc.getDettaglioErrore().get(0).getNumeroErrore();
		String errore = rpc.getDettaglioErrore().get(0).getDescrizione();
		log.debug("Pratica non presente nel backoffice. Codice Pratica = {}. Messaggio = {}[{}]",
			cooperazioneSUAPEnte.getIntestazione().getCodicePratica(), errore, codiceerrore);
		LoggerArchiviocomunicazioni.logComunicazioneGenerico("Pratica non presente nel backoffice. Codice Pratica = {}. Messaggio = {}[{}]",
			cooperazioneSUAPEnte.getIntestazione().getCodicePratica(), errore, codiceerrore);
		if (codiceerrore.equals(String.valueOf(Costants.CODICE_ERRORE_RICERCA_PRATICA_STC))) {
		    log.debug("selectAttivitaDaSvolgere# CASO 1: Imposto attivita = {}", AttivitaDaEseguireEnum.INSERIMENTO_PRATICA.getValue());
		    LoggerArchiviocomunicazioni.logComunicazioneGenerico("CASO 1: Imposto attivita = {}",
			    AttivitaDaEseguireEnum.INSERIMENTO_PRATICA.getValue());
		    attivitaDaEseguireEnum = AttivitaDaEseguireEnum.INSERIMENTO_PRATICA;
		} else if (codiceerrore.equals(String.valueOf(Costants.CODICE_ERRORE_RICERCA_PRATICA_SIGEPRO))) {
		    log.debug("selectAttivitaDaSvolgere# CASO 3: Imposto attivita = {}",
			    AttivitaDaEseguireEnum.PRATICA_REGISTRA_MA_NON_PRESENTE.getValue());
		    LoggerArchiviocomunicazioni.logComunicazioneGenerico("CASO 3: Imposto attivita = {}",
			    AttivitaDaEseguireEnum.PRATICA_REGISTRA_MA_NON_PRESENTE.getValue());
		    attivitaDaEseguireEnum = AttivitaDaEseguireEnum.PRATICA_REGISTRA_MA_NON_PRESENTE;
		} else {
		    log.error("selectAttivitaDaSvolgere# {}[{}] ", errore, codiceerrore);
		}
	    } else {
		dettaglioPraticaPresente = rpc.getDettaglio().getDettaglioPratica();
		log.debug("Pratica presente nel backoffice. Numero pratica = {}, Codice Pratica = {}, Protocollo backoffice = {},",
			dettaglioPraticaPresente.getNumeroPratica(), dettaglioPraticaPresente.getCodicePraticaTelematica(),
			dettaglioPraticaPresente.getNumeroProtocolloGenerale());
		LoggerArchiviocomunicazioni.logComunicazioneGenerico(
			"Pratica presente nel backoffice. Numero pratica = {}, Codice Pratica = {}, Protocollo backoffice = {},",
			dettaglioPraticaPresente.getNumeroPratica(), dettaglioPraticaPresente.getCodicePraticaTelematica(),
			dettaglioPraticaPresente.getNumeroProtocolloGenerale());
		LoggerArchiviocomunicazioni.logComunicazioneGenerico("CASO 2: Imposto attivita = {}",
			AttivitaDaEseguireEnum.INSERIMENTO_ATTIVITA.getValue());
		attivitaDaEseguireEnum = AttivitaDaEseguireEnum.INSERIMENTO_ATTIVITA;
	    }
	} catch (Exception e) {
	    throw e;
	}
	result.setAttivitaDaEseguireEnum(attivitaDaEseguireEnum);
	result.setPratica(dettaglioPraticaPresente);
	return result;
    }
}
