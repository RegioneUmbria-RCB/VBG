package it.gruppoinit.pal.gp.core.ws.client;

import java.net.URL;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CancellaAttivitaRequest;
import it.init.sigepro.rte.CheckTokenRequest;
import it.init.sigepro.rte.CheckTokenResponse;
import it.init.sigepro.rte.DirezioneSportelloRequest;
import it.init.sigepro.rte.DirezioneSportelloResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaDestinatariaRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaMittenteRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.definitions.Stc;
import it.init.sigepro.rte.types.SportelloType;

public class StcWsClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(StcWsClient.class);
    private VerticalizzazioniService verticalizzazioniService;
    private long timeout_connessione = 120000;
    private long timeout_risposta = 600000;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    /**
     * legge le configurazioni username e password dalle regole della verticalizzazione STC
     */
    private ParametriSTC initializeNLA() {

	ParametriSTC parametriSTC = new ParametriSTC();
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
	    Verticalizzazioniparametri parametroUser = verticalizzazioniService.getVerticalizzazioniparametri("STC", "NLA_USERNAME");
	    Verticalizzazioniparametri parametroPwd = verticalizzazioniService.getVerticalizzazioniparametri("STC", "NLA_PASSWORD");
	    Verticalizzazioniparametri parametroSTCWSUrl = verticalizzazioniService.getVerticalizzazioniparametri("STC", "STC_WS_URL");
	    if (parametroUser == null || StringUtils.isBlank(parametroUser.getValore())) {
		throw new RuntimeException(
			"Attenzione! Parametro NLA_USERNAME non configurato per la verticalizzazione STC e modulo " + ORMHelper.getSoftware());
	    }
	    if (parametroPwd == null || StringUtils.isBlank(parametroPwd.getValore())) {
		throw new RuntimeException(
			"Attenzione! Parametro NLA_PASSWORD non configurato per la verticalizzazione STC e modulo " + ORMHelper.getSoftware());
	    }
	    if (parametroSTCWSUrl == null || StringUtils.isBlank(parametroSTCWSUrl.getValore())) {
		throw new RuntimeException(
			"Attenzione! Parametro STC_WS_URL non configurato per la verticalizzazione STC e modulo " + ORMHelper.getSoftware());
	    }
	    parametriSTC.stcWsUser = parametroUser.getValore();
	    parametriSTC.stcWsPwd = parametroPwd.getValore();
	    parametriSTC.stcWsUrl = parametroSTCWSUrl.getValore();
	} else {
	    throw new RuntimeException("Attenzione! La verticalizzazione STC non è attiva");
	}
	return parametriSTC;
    }

    public String login() {

	ParametriSTC parametriSTC = initializeNLA();
	LoginRequest loginRequest = new LoginRequest();
	loginRequest.setUsername(parametriSTC.stcWsUser);
	loginRequest.setPassword(parametriSTC.stcWsPwd);
	LoginResponse loginResponse;
	try {
	    loginResponse = getStcWsPort(parametriSTC.stcWsUrl).login(loginRequest);
	    if (loginResponse.isResult()) {
		return loginResponse.getToken();
	    } else {
		log.error("login(): La chiamata al metodo login di sigeprosecurity è tornata false: [user={},pwd={},url={}]",
			new Object[] { parametriSTC.stcWsUser, parametriSTC.stcWsPwd, parametriSTC.stcWsUrl });
		throw new RuntimeException("Autenticazione fallita");
	    }
	} catch (Exception e) {
	    throw new RuntimeException("Errore durante la login ad STC: " + e.getMessage());
	}
    }

    public void checkToken(String token) {

	ParametriSTC parametriSTC = initializeNLA();
	CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	checkTokenRequest.setToken(token);
	CheckTokenResponse checkTokenResponse;
	try {
	    checkTokenResponse = getStcWsPort(parametriSTC.stcWsUrl).checkToken(checkTokenRequest);
	    if (!checkTokenResponse.isResult()) {
		log.error("checkToken({}): Errore durante la validazione del token: Token non valido", token);
		throw new RuntimeException("Errore durante la validazione del token: Token non valido");
	    }
	} catch (Exception e) {
	    log.error("checkToken({}): Errore durante la validazione del token: {}", token, e.getMessage());
	    throw new RuntimeException("Errore durante la validazione del token: " + e.getMessage());
	}
    }

    public DirezioneSportelloResponse getDirezioneSportello(SportelloType sportello) {

	ParametriSTC parametriSTC = initializeNLA();
	DirezioneSportelloRequest direzioneSportelloRequest = new DirezioneSportelloRequest();
	String token = this.login();
	direzioneSportelloRequest.setToken(token);
	direzioneSportelloRequest.setSportello(sportello);
	try {
	    DirezioneSportelloResponse direzioneSportelloResponse = getStcWsPort(parametriSTC.stcWsUrl).direzioneSportello(direzioneSportelloRequest);
	    return direzioneSportelloResponse;
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta direzione sportello: {} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta direzione sportello: " + e.getMessage() + soapFaultDetail);
	}
    }

    public NotificaAttivitaResponse notificaAttivita(NotificaAttivitaRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    NotificaAttivitaResponse response = getStcWsPort(parametriSTC.stcWsUrl).notificaAttivita(request);
	    return response;
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la notifica attività: {}", soapFaultDetail, e);
	    throw new RuntimeException("Errore durante la notifica attività: <br />" + soapFaultDetail);
	}
    }

    public RichiestaPraticaResponse richiestaPratica(RichiestaPraticaRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    RichiestaPraticaResponse response = getStcWsPort(parametriSTC.stcWsUrl).richiestaPratica(request);
	    return response;
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta pratica: {} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta pratica: " + e.getMessage() + soapFaultDetail);
	}
    }

    public AllegatoBinarioResponse allegatoBinario(AllegatoBinarioRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    AllegatoBinarioResponse response = getStcWsPort(parametriSTC.stcWsUrl).allegatoBinario(request);
	    return response;
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta allegato:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta allegato: " + e.getMessage() + soapFaultDetail);
	}
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(RichiestaPraticaCollegataRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    RichiestaPraticaCollegataResponse response = getStcWsPort(parametriSTC.stcWsUrl).richiestaPraticaCollegata(request);
	    return response;
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta pratica collegata:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta pratica collegata: " + e.getMessage() + soapFaultDetail);
	}
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(RichiestaPraticaCollegataDaAttivitaMittenteRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    return getStcWsPort(parametriSTC.stcWsUrl).richiestaPraticaCollegataDaAttivitaMittente(request);
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta pratica collegata in base all' attività mittente:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException(
		    "Errore durante la richiesta pratica collegata in base all' attività mittente: " + e.getMessage() + soapFaultDetail);
	}
    }

    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(
	    RichiestaPraticaCollegataDaAttivitaDestinatariaRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    return getStcWsPort(parametriSTC.stcWsUrl).richiestaPraticaCollegataDaAttivitaDestinataria(request);
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta pratica collegata in base all' attività destinataria:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException(
		    "Errore durante la richiesta pratica collegata in base all' attività destinataria: " + e.getMessage() + soapFaultDetail);
	}
    }

    public void cancellaAttivita(CancellaAttivitaRequest request) {

	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	request.setToken(token);
	try {
	    getStcWsPort(parametriSTC.stcWsUrl).cancellaAttivita(request);
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta di cancellazione attività:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta di cancellazione attività: " + e.getMessage() + soapFaultDetail);
	}
    }

    public InserimentoPraticaResponse inserimentoPratica(InserimentoPraticaRequest inserimentoPraticaRequest) {

	InserimentoPraticaResponse inserimentoPraticaResponse;
	ParametriSTC parametriSTC = initializeNLA();
	String token = this.login();
	inserimentoPraticaRequest.setToken(token);
	try {
	    Stc port = this.getStcWsPort(parametriSTC.stcWsUrl);
	    inserimentoPraticaResponse = port.inserimentoPratica(inserimentoPraticaRequest);
	} catch (Exception e) {
	    String soapFaultDetail = getSOAPFAULT(e);
	    log.error("Errore durante la richiesta inserimento pratica:{} {}", e.getMessage(), soapFaultDetail);
	    throw new RuntimeException("Errore durante la richiesta inserimento pratica: " + e.getMessage() + soapFaultDetail);
	}
	return inserimentoPraticaResponse;
    }

    private class ParametriSTC {

	private String stcWsUrl;
	private String stcWsUser;
	private String stcWsPwd;
    }

    public Stc getStcWsPort(String stcWsUrl) throws Exception {

	StcService client = new StcService(new URL(stcWsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	Stc port = client.getStcSoap11(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	long connectionTimeout = getParametriTimeoutConnessione();
	httpClientPolicy.setConnectionTimeout(connectionTimeout); // Line #2  
	long receiveTimeout = getParametriTimeoutRisposta();
	httpClientPolicy.setReceiveTimeout(receiveTimeout); // Line #3  
	conduit.setClient(httpClientPolicy);
	return port;
    }

    private long getParametriTimeoutConnessione() {

	long timeout = this.timeout_connessione;
	// Verifico se la verticalizzazione è attiva
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
	    String parametroDiTimeout = WebConstants.VERTICALIZZAZIONE_STC_TIMEOUT_CONNESSIONE;
	    Verticalizzazioniparametri valoreTimeout = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    parametroDiTimeout);
	    // Controllo se il valore di timeout è stato imposta in verticalizzazione
	    if (valoreTimeout == null || StringUtils.isBlank(valoreTimeout.getValore())) {
		if (log.isDebugEnabled()) {
		    log.debug("Il valore del timeout in verticalizzaione ({}) non è presente, verrà impostato quello di default {}",
			    new Object[] { parametroDiTimeout, this.timeout_connessione });
		}
	    } else {
		// Converto la stringa in un long
		try {
		    timeout = Long.valueOf(valoreTimeout.getValore());
		} catch (NumberFormatException e) {
		    log.error(
			    "Il valore del timeout in verticalizzaione ({}) non ha un valore corretto {} ({}), verrà impostato quello di default {}",
			    new Object[] { e, e.getMessage(), this.timeout_connessione });
		}
	    }
	}
	return timeout;
    }

    private long getParametriTimeoutRisposta() {

	long timeout = this.timeout_risposta;
	// Verifico se la verticalizzazione è attiva
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
	    String parametroDiTimeout = WebConstants.VERTICALIZZAZIONE_STC_TIMEOUT_RISPOSTA;
	    Verticalizzazioniparametri valoreTimeout = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    parametroDiTimeout);
	    // Controllo se il valore di timeout è stato imposta in verticalizzazione
	    if (valoreTimeout == null || StringUtils.isBlank(valoreTimeout.getValore())) {
		if (log.isDebugEnabled()) {
		    log.debug("Il valore del timeout in verticalizzaione ({}) non è presente, verrà impostato quello di default {}",
			    new Object[] { parametroDiTimeout, this.timeout_risposta });
		}
	    } else {
		// Converto la stringa in un long
		try {
		    timeout = Long.valueOf(valoreTimeout.getValore());
		} catch (NumberFormatException e) {
		    log.error(
			    "Il valore del timeout in verticalizzaione ({}) non ha un valore corretto {} ({}), verrà impostato quello di default {}",
			    new Object[] { e, e.getMessage(), this.timeout_risposta });
		}
	    }
	}
	return timeout;
    }
}
