package it.gruppoinit.nlapec.service.wsnlapec;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import it.gruppoinit.nlapec.schema.wsnlapec.DettaglioReportType;
import it.gruppoinit.nlapec.schema.wsnlapec.ListaMessaggiRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.ListaMessaggiResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.MessaggiNonLettiRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.MessaggiNonLettiResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ProcessaMessaggiRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.ProcessaMessaggiResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaAllegatiMessaggioRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaAllegatiMessaggioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioBinarioRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioBinarioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioInviatoBinarioRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.ScaricaMessaggioInviatoBinarioResponse;
import it.gruppoinit.nlapec.schema.wsnlapec.SetFlagLetturaMessaggioRequest;
import it.gruppoinit.nlapec.schema.wsnlapec.SetFlagLetturaMessaggioResponse;
import it.gruppoinit.nlapec.service.PECReader;
import it.gruppoinit.nlapec.service.helper.GestoreCasellaMailHelper;
import it.gruppoinit.nlapec.service.sigepro.SigeproService;
import it.gruppoinit.nlapec.service.sigepro.SigeproWebServiceClient;
import it.gruppoinit.nlapec.service.sigeprosecurity.SigeproSecurityWebServiceClient;
import it.gruppoinit.nlapec.service.stc.StcWebServiceClient;
import it.gruppoinit.nlapec.util.NLAPecConstant;
import it.gruppoinit.nlapec.util.ReportDettaglioBean;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.ActionType;
import it.gruppoinit.sigepro.schemas.messages.mailconfig.MailConfigResponse2;

@Endpoint
public class GestoreCasellaMailWebService {

    private static final Logger log = LoggerFactory.getLogger(GestoreCasellaMailWebService.class);
    private static final String MESSAGES_NAMESPACE = "http://gruppoinit.it/nlapec";
    private static final String LISTA_MESSAGGI = "ListaMessaggiRequest";
    private static final String PROCESSA_MESSAGGI = "ProcessaMessaggiRequest";
    private static final String SCARICA_ALLEGATI_MESSAGGIO = "ScaricaAllegatiMessaggioRequest";
    private static final String MESSAGGI_NON_LETTI = "MessaggiNonLettiRequest";
    private static final String SCARICA_MESSAGGIO_BINARIO = "ScaricaMessaggioBinarioRequest";
    private static final String SET_FLAG_LETTURA_MESSAGGIO = "SetFlagLetturaMessaggioRequest";
    private static final String SCARICA_MESSAGGIO_INVIATO_BINARIO = "ScaricaMessaggioInviatoBinarioRequest";
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;
    private SigeproWebServiceClient sigeproWebServiceClient;
    private GestoreCasellaMailHelper helper;
    private SigeproService sigeproService;
    private PECReader pecReader;
    private StcWebServiceClient stcWebServiceClient;
    private String baseTmpPath;

    @PayloadRoot(localPart = LISTA_MESSAGGI, namespace = MESSAGES_NAMESPACE)
    public ListaMessaggiResponse listaMessaggi(ListaMessaggiRequest request) {

	log.info("WS - listaMessaggi()...[" + request.getSoftware() + "," + request.getToken() + "] at " + ((Date) (new Date())).getTime());
	ListaMessaggiResponse response = new ListaMessaggiResponse();
	try {
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    log.debug(" Url WsServiceMailConfig = {} ", urlWsServiceMailConfig);
	    //	    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
	    //		    request.getSoftware(), ActionType.READ);
	    //	   
	    log.debug("WS - listaMessaggi() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}", new Object[] {
		    urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(), ActionType.READ });
	    MailConfigResponse2 mailConfigResponse2 = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    log.debug("mailConfigResponse = [EMAIL_SENDER={}],[USER={}],[HOST_URL={}],[PROTOCOL={}],[PORT={}]",
		    new Object[] { mailConfigResponse2.getSenderEmailAddress(), mailConfigResponse2.getUser(), mailConfigResponse2.getUrl(),
			    mailConfigResponse2.getProtocol().toString(), mailConfigResponse2.getPort() });
	    //
	    response = helper.getListaMessaggi(request.getListaFiltri(), mailConfigResponse2,
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    // setto idAccount utilizzato nella response
	    response.setIdaccount(mailConfigResponse2.getIdAccount());
	    log.info("WS - listaMessaggi()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo listaMessaggi : {}", e.getMessage());
	    throw new RuntimeException("listaMessaggi: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = PROCESSA_MESSAGGI, namespace = MESSAGES_NAMESPACE)
    public ProcessaMessaggiResponse processaMessaggi(ProcessaMessaggiRequest request) {

	log.info("WS - processaMessaggi()...[" + request.getSoftware() + "," + request.getToken() + "] at " + ((Date) (new Date())).getTime());
	ProcessaMessaggiResponse response = new ProcessaMessaggiResponse();
	response.setAlias("");
	response.setAvviso("");
	response.setDescrizioneEnte("");
	response.setErrore("");
	DettaglioReportType report = new DettaglioReportType();
	report.setNumeroMessaggiConErrore(0);
	report.setNumeroMessaggiTotali(0);
	report.setSoftware(request.getSoftware());
	report.setTipologiaMessaggiDaProcessare("");
	response.setListaDettagli(report);
	try {
	    Properties props = sigeproSecurityWebServiceClient.getTokenInfo(request.getToken());
	    //String idComune = props.getProperty("idcomune");
	    String idComuneAlias = props.getProperty("idcomunealias");
	    response.setAlias(idComuneAlias);
	    Properties connectionDBProps = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias, request.getToken());
	    List<String> listaSoftwareAttivi = sigeproService.checkVerticalizzazioneAttiva(connectionDBProps);
	    if (helper.isSoftwareAttivo(request.getSoftware(), listaSoftwareAttivi)) {
		Map<String, String> parametriTipologieProcessamentoPEC = sigeproService.getParametriTipologiePEC(connectionDBProps,
			request.getSoftware());
		Map<String, String> altriParametriVerticalizzazione = sigeproService.getAltriParametriVerticalizzazione(connectionDBProps,
			request.getSoftware());
		if (parametriTipologieProcessamentoPEC != null && parametriTipologieProcessamentoPEC.size() > 0) {
		    Set<String> ketSet = parametriTipologieProcessamentoPEC.keySet();
		    for (Iterator<String> iterator = ketSet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String valore = parametriTipologieProcessamentoPEC.get(key);
			String tmp = key + "=" + valore + ";" + report.getTipologiaMessaggiDaProcessare();
			report.setTipologiaMessaggiDaProcessare(tmp);
		    }
		    String stcToken = stcWebServiceClient.login();
		    ReportDettaglioBean dettaglioReport = new ReportDettaglioBean();
		    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
		    String urlWsServiceNotificaMail = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MOVIMENTIMAIL_WSDL);
		    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
		    String urlWsServiceEventi = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_NOTIFICA_EVENTI);
		    String urlWsServiceOggetti = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_INSERIMENTO_OGGETTI_WSDL);
		    //		    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
		    //			    request.getSoftware(), ActionType.READ);
		    log.debug("WS - processaMessaggi() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
			    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
				    ActionType.READ });
		    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
			    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
		    // setto id account utilizzato
		    response.setIdaccount(mailConfigResponse.getIdAccount());
		    pecReader.readPECInbox(mailConfigResponse, idComuneAlias, request.getSoftware(), request.getToken(), urlWsServiceNotificaMail,
			    urlWsServiceOggetti, parametriTipologieProcessamentoPEC, dettaglioReport, stcToken, baseTmpPath, connectionDBProps,
			    altriParametriVerticalizzazione, urlWsServiceEventi);
		    report.setNumeroMessaggiConErrore(dettaglioReport.getNumeroMessageConErrori());
		    report.setNumeroMessaggiTotali(dettaglioReport.getNumeroMessaggiTotali());
		    if (dettaglioReport.getDettaglio() != null) {
			for (Iterator iterator = dettaglioReport.getDettaglio().iterator(); iterator.hasNext();) {
			    String dettaglio = (String) iterator.next();
			    if (dettaglio != null && dettaglio.startsWith("ERRORE")) {
				report.getListaErrori().add(dettaglio);
			    }
			}
		    }
		} else {
		    response.setAvviso("Nessuna tipologia di processamento è stata attivata per il software " + request.getSoftware());
		}
	    } else {
		response.setAvviso("La verticalizzazione NLA-RICEZIONE-PEC non è attiva per il software " + request.getSoftware());
	    }
	    log.info("WS - processaMessaggi()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo processaMessaggi : {}", e.getMessage());
	    throw new RuntimeException("processaMessaggi: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = SCARICA_ALLEGATI_MESSAGGIO, namespace = MESSAGES_NAMESPACE)
    public ScaricaAllegatiMessaggioResponse scaricaAllegatiMessaggio(ScaricaAllegatiMessaggioRequest request) {

	log.info("WS - scaricaAllegatiMessaggio()...[" +
		request.getSoftware() +
		"," +
		request.getToken() +
		"," +
		request.getIdentificativoMessaggio() +
		"] at " +
		((Date) (new Date())).getTime());
	ScaricaAllegatiMessaggioResponse response = new ScaricaAllegatiMessaggioResponse();
	try {
	    //Properties props = sigeproSecurityWebServiceClient.getTokenInfo(request.getToken());
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    //	    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
	    //		    request.getSoftware(), ActionType.READ);
	    log.debug("WS - scaricaAllegatiMessaggio() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
		    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
			    ActionType.READ });
	    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    // setto id account utilizzato
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    response = helper.getAllegatiMessaggio(request.getIdentificativoMessaggio(), mailConfigResponse, baseTmpPath,
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    log.info("WS - scaricaAllegatiMessaggio()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "," +
		    request.getIdentificativoMessaggio() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo scaricaAllegatiMessaggio : {}", e.getMessage());
	    throw new RuntimeException("scaricaAllegatiMessaggio: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = MESSAGGI_NON_LETTI, namespace = MESSAGES_NAMESPACE)
    public MessaggiNonLettiResponse messaggiNonLetti(MessaggiNonLettiRequest request) {

	log.info("WS - messaggiNonLetti()...[" + request.getSoftware() + "," + request.getToken() + "] at " + ((Date) (new Date())).getTime());
	MessaggiNonLettiResponse response = new MessaggiNonLettiResponse();
	try {
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    //	    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
	    //		    request.getSoftware(), ActionType.READ);
	    log.debug("WS - messaggiNonLetti() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
		    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
			    ActionType.READ });
	    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    // setto id account utilizzato
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    int num = helper.getNumeroMessaggiNonLetti(mailConfigResponse,
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    response.setNumMessaggi(num);
	    response.setProprietarioCasella(mailConfigResponse.getSenderEmailAddress());
	    log.info("WS - messaggiNonLetti()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo messaggiNonLetti : {}", e.getMessage());
	    throw new RuntimeException("messaggiNonLetti: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = SCARICA_MESSAGGIO_BINARIO, namespace = MESSAGES_NAMESPACE)
    public ScaricaMessaggioBinarioResponse scaricaMessaggioBinario(ScaricaMessaggioBinarioRequest request) {

	log.info("WS - scaricaMessaggioBinario()...[" +
		request.getSoftware() +
		"," +
		request.getToken() +
		"," +
		request.getIdentificativoMessaggio() +
		"] at " +
		((Date) (new Date())).getTime());
	ScaricaMessaggioBinarioResponse response = new ScaricaMessaggioBinarioResponse();
	try {
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    //	    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
	    //		    request.getSoftware(), ActionType.READ);
	    log.debug("WS - scaricaMessaggioBinario() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
		    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
			    ActionType.READ });
	    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    // setto id account utilizzato
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    response = helper.getMessaggioBinario(mailConfigResponse, request.getIdentificativoMessaggio(),
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    log.info("WS - scaricaMessaggioBinario()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "," +
		    request.getIdentificativoMessaggio() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo scaricaMessaggioBinario : {}", e.getMessage());
	    throw new RuntimeException("scaricaMessaggioBinario: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = SCARICA_MESSAGGIO_INVIATO_BINARIO, namespace = MESSAGES_NAMESPACE)
    public ScaricaMessaggioInviatoBinarioResponse scaricaMessaggioInviatoBinario(ScaricaMessaggioInviatoBinarioRequest request) {

	log.debug("WS - scaricaMessaggioInviatoBinario()...[" +
		request.getSoftware() +
		"," +
		request.getToken() +
		"," +
		request.getIdentificativoBackofficeMessaggio() +
		"," +
		request.getFolderName() +
		"] at " +
		((Date) (new Date())).getTime());
	ScaricaMessaggioInviatoBinarioResponse response = new ScaricaMessaggioInviatoBinarioResponse();
	try {
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    //	    MailConfigResponse mailConfigResponse = sigeproWebServiceClient.mailConfig(urlWsServiceMailConfig, request.getToken(),
	    //		    request.getSoftware(), ActionType.READ);
	    log.debug(
		    "WS - scaricaMessaggioInviatoBinario() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
		    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
			    ActionType.READ });
	    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    // setto id account utilizzato
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    response = helper.getMessaggioInviatoBinario(mailConfigResponse, request.getIdentificativoBackofficeMessaggio(),
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    log.debug("WS - scaricaMessaggioInviatoBinario()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "," +
		    request.getIdentificativoBackofficeMessaggio() +
		    "," +
		    request.getFolderName() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo scaricaMessaggioBinario : {}", e.getMessage());
	    throw new RuntimeException("scaricaMessaggioBinario: " + e.getMessage());
	}
    }

    @PayloadRoot(localPart = SET_FLAG_LETTURA_MESSAGGIO, namespace = MESSAGES_NAMESPACE)
    public SetFlagLetturaMessaggioResponse setFlagLetturaMessaggio(SetFlagLetturaMessaggioRequest request) {

	log.info("WS - setFlagLetturaMessaggio()...[" +
		request.getSoftware() +
		"," +
		request.getToken() +
		"," +
		request.getIdentificativoMessaggio() +
		"] at " +
		((Date) (new Date())).getTime());
	SetFlagLetturaMessaggioResponse response = new SetFlagLetturaMessaggioResponse();
	try {
	    Map<String, String> wsBaseUrlMap = sigeproSecurityWebServiceClient.getParams(NLAPecConstant.WSHOSTURL_JAVA);
	    String urlWsServiceMailConfig = helper.getUrlWsService(wsBaseUrlMap, NLAPecConstant.SERVICES_MAILCONFIG_WSDL);
	    log.debug("WS - setFlagLetturaMessaggio() - Url WsServiceMailConfig = {},token = {}, software = {}, idAccount = {}, ActionType = {}",
		    new Object[] { urlWsServiceMailConfig, request.getToken(), request.getSoftware(), null, request.getIdaccount(),
			    ActionType.READ });
	    MailConfigResponse2 mailConfigResponse = sigeproWebServiceClient.mailConfig2(urlWsServiceMailConfig, request.getToken(),
		    request.getSoftware(), null, request.getIdaccount(), ActionType.READ);
	    // setto id account utilizzato
	    response.setIdaccount(mailConfigResponse.getIdAccount());
	    response = helper.setFlagLetturaMessaggio(mailConfigResponse, request.getIdentificativoMessaggio(), request.isFlagValue(),
		    getAltriParametriVerticalizazzione(request.getToken(), request.getSoftware()));
	    log.info("WS - setFlagLetturaMessaggio()...[" +
		    request.getSoftware() +
		    "," +
		    request.getToken() +
		    "," +
		    request.getIdentificativoMessaggio() +
		    "] at " +
		    ((Date) (new Date())).getTime() +
		    " END.");
	    return response;
	} catch (Exception e) {
	    log.error("Errore nel metodo setFlagLetturaMessaggio : {}", e.getMessage());
	    throw new RuntimeException("setFlagLetturaMessaggio: " + e.getMessage());
	}
    }

    public SigeproSecurityWebServiceClient getSigeproSecurityWebServiceClient() {

	return sigeproSecurityWebServiceClient;
    }

    public void setSigeproSecurityWebServiceClient(SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient) {

	this.sigeproSecurityWebServiceClient = sigeproSecurityWebServiceClient;
    }

    public SigeproWebServiceClient getSigeproWebServiceClient() {

	return sigeproWebServiceClient;
    }

    public void setSigeproWebServiceClient(SigeproWebServiceClient sigeproWebServiceClient) {

	this.sigeproWebServiceClient = sigeproWebServiceClient;
    }

    public GestoreCasellaMailHelper getHelper() {

	return helper;
    }

    public void setHelper(GestoreCasellaMailHelper helper) {

	this.helper = helper;
    }

    public String getBaseTmpPath() {

	return baseTmpPath;
    }

    public void setBaseTmpPath(String baseTmpPath) {

	this.baseTmpPath = baseTmpPath;
    }

    public SigeproService getSigeproService() {

	return sigeproService;
    }

    public void setSigeproService(SigeproService sigeproService) {

	this.sigeproService = sigeproService;
    }

    public StcWebServiceClient getStcWebServiceClient() {

	return stcWebServiceClient;
    }

    public void setStcWebServiceClient(StcWebServiceClient stcWebServiceClient) {

	this.stcWebServiceClient = stcWebServiceClient;
    }

    public PECReader getPecReader() {

	return pecReader;
    }

    public void setPecReader(PECReader pecReader) {

	this.pecReader = pecReader;
    }

    private Map<String, String> getAltriParametriVerticalizazzione(String token, String software) {

	Properties props = sigeproSecurityWebServiceClient.getTokenInfo(token);
	String idComuneAlias = props.getProperty("idcomunealias");
	Properties connectionDBProps = sigeproSecurityWebServiceClient.getConnectionProperties(idComuneAlias, token);
	Map<String, String> altriParametriVerticalizzazione = sigeproService.getAltriParametriVerticalizzazione(connectionDBProps, software);
	return altriParametriVerticalizzazione;
    }
}
