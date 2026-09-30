package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.exceptions.ProblemException;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.AvvisoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DatiUpdatePosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.DettaglioPosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.EsitoValidazioneType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.OkMessage;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.Problem;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RichiestaPagamentoImmediatoType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.RichiestaPosizioneDebitoriaRendicontazioneAscotType;
import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.SubscriptionInfoType;

public class FvgPayClient {

    private static final String PAGOPA_SOTTOSCRIZIONI = "/pagopa/{id_beneficiario}/{id_servizio}/subscriptions";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_PAGAMENTO_IMMEDIATO = "/pagopa/pagamento-immediato";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_STATO_PAGAMENTO_IMMEDIATO = "/pagopa/pagamento-immediato/{id_ente}/{id_servizio}/{id_debito}";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_BOLLETTINO = "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}/pdf";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_AGGIORNA = "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_STATO = "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_RICONCILIAZIONE_ASCOT = "/pagopa/posizioni-debitorie/riconciliazione-ascot";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_RICONCILIAZIONE_ASCOT_VALIDAZIONE = "/pagopa/posizioni-debitorie/validazione/riconciliazione-ascot";
    private static final String PAGOPA_POSIZIONI_DEBITORIE_ELIMINAZIONE = "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}";;
    private String password;
    private String utente;
    private String baseUrl;
    private String authorizationType;
    private String idEnte;
    private String idServizio;
    private static final String SUCCESSO_CHIAMATA = "CHIAMATA RIUSCITA RESPONSE STATUS: {} {}";
    private static final Logger log = LoggerFactory.getLogger(FvgPayClient.class);
    private final String MediaTypeProblem = "application/problem+json";

    public FvgPayClient(FvgPayClientParams params) {

	this.password = params.getBasicAuthParams().getPassword();
	this.utente = params.getBasicAuthParams().getUtente();
	this.baseUrl = params.getBaseUrl();
	this.idEnte = params.getIdEnte();
	this.idServizio = params.getIdServizio();
	this.authorizationType = "BASIC";
    }

    public EsitoValidazioneType validaCaricamentoPosizioneDebitoriaAscot(RichiestaPosizioneDebitoriaRendicontazioneAscotType richiestaPosDeb)
	    throws ProblemException {

	WebClient client = null;
	try {
	    client = createWebClient(baseUrl, PAGOPA_POSIZIONI_DEBITORIE_RICONCILIAZIONE_ASCOT_VALIDAZIONE, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	String sw = new String();
	EsitoValidazioneType esitoValidazione = null;
	try {
	    sw = JSONUtils.marshal(richiestaPosDeb, false);
	    log.debug("validazione pagamento ascot {}", sw);
	} catch (JAXBException e) {
	    gestisciEccezione(e, "validaCaricamentoPosizioneDebitoriaAscot");
	}
	Response response = client.post("[" + sw.toString() + "]");
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201) || status.equals(202)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [validazione pagamento ascot]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		esitoValidazione = JSONUtils.unmarshal(EsitoValidazioneType.class, is, false);
		log.debug("esitoValidazione {}", esitoValidazione);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "validaCaricamentoPosizioneDebitoriaAscot");
	    }
	} else {
	    log.debug("uri ws [validazione pagamento ascot]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "validaCaricamentoPosizioneDebitoriaAscot");
	}
	return esitoValidazione;
    }

    public OkMessage sottoscrizione(SubscriptionInfoType sottoscrizione) throws ProblemException {

	// "/pagopa/{id_beneficiario}/{id_servizio}/subscriptions"
	WebClient client = null;
	String path = PAGOPA_SOTTOSCRIZIONI.replace("{id_beneficiario}", idEnte).replace("{id_servizio}", idServizio);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	String sw = new String();
	OkMessage okMessage = null;
	try {
	    sw = JSONUtils.marshal(sottoscrizione, false);
	    log.debug("registrazione sottoscrizione {}", sw);
	} catch (JAXBException e) {
	    log.debug("Errore nel marshalling del sottoscrizione immediato", e);
	}
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201) || status.equals(202)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [pagamento immediato]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		okMessage = JSONUtils.unmarshal(OkMessage.class, is, false);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "sottoscrizione");
	    }
	} else {
	    log.debug("uri ws [registrazione pagamento immediato]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "pagamentoImmediato");
	}
	return okMessage;
    }

    //Pagamento OTF
    public OkMessage pagamentoImmediato(RichiestaPagamentoImmediatoType richiestaPagamentoImmediatoType) throws ProblemException {

	// "/pagopa/pagamento-immediato"
	WebClient client = null;
	try {
	    client = createWebClient(baseUrl, PAGOPA_POSIZIONI_DEBITORIE_PAGAMENTO_IMMEDIATO, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	String sw = new String();
	OkMessage okMessage = null;
	try {
	    sw = JSONUtils.marshal(richiestaPagamentoImmediatoType, false);
	    log.debug("registrazione pagamento immediato {}", sw);
	} catch (JAXBException e) {
	    gestisciEccezione(e, "pagamentoImmediato");
	}
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201) || status.equals(202)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [pagamento immediato]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		okMessage = JSONUtils.unmarshal(OkMessage.class, is, false);
	    } catch (JAXBException e) {
		log.debug("[pagamento immediato] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [registrazione pagamento immediato]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "pagamentoImmediato");
	}
	return okMessage;
    }

    //Verifica pagamento OTF
    public DettaglioPosizioneDebitoriaType verificaPagamentoOTF(String idDebito) throws ProblemException {

	// "/pagopa/pagamento-immediato/{id_ente}/{id_servizio}/{id_debito}"
	WebClient client = null;
	String path = PAGOPA_POSIZIONI_DEBITORIE_STATO_PAGAMENTO_IMMEDIATO.replace("{id_ente}", idEnte).replace("{id_servizio}", idServizio)
		.replace("{id_debito}", idDebito);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	Response response = client.get();
	Integer status = response.getStatus();
	DettaglioPosizioneDebitoriaType posizioneDebitoriaType = null;
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [dettaglio posizione debitoria]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		posizioneDebitoriaType = JSONUtils.unmarshal(DettaglioPosizioneDebitoriaType.class, is, false);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "verificaPagamentoOTF");
	    }
	} else {
	    log.debug("uri ws [dettaglio posizione debitoria]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "verificaPagamentoOTF");
	}
	return posizioneDebitoriaType;
    }

    //Genera avviso
    public AvvisoType generaBollettino(String idDebito) throws ProblemException {

	// "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}/pdf"
	WebClient client = null;
	String path = PAGOPA_POSIZIONI_DEBITORIE_BOLLETTINO.replace("{id_ente}", idEnte).replace("{id_servizio}", idServizio).replace("{id_debito}",
		idDebito);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	client.accept("application/pdf");
	Response response = client.get();
	Integer status = response.getStatus();
	AvvisoType avvisoType = null;
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [generaBollettino]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		avvisoType = JSONUtils.unmarshal(AvvisoType.class, is, false);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "generaBollettino");
	    }
	} else {
	    log.debug("uri ws [verifica pagamento OTF]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "generaBollettino");
	}
	return avvisoType;
    }
    //    //Lista posizioni debitorie
    //    public PosizioniDebitorieSelezionateType getListaPosizioniDebitorie() throws ProblemException {
    //
    //	WebClient client;
    //	try {
    //	    client = createWebClient(baseUrl, "", false);
    //	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
    //	    throw new ProblemException(e1);
    //	}
    //	Response response = client.get();
    //	Integer status = response.getStatus();
    //	PosizioniDebitorieSelezionateType debitorieSelezionateType = null;
    //	if (status.equals(200)) {
    //	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
    //	    log.debug("url ws [lista pendenze]: {} ", client.getCurrentURI());
    //	    InputStream is = ((InputStream) response.getEntity());
    //	    try {
    //		debitorieSelezionateType = JSONUtils.unmarshal(PosizioniDebitorieSelezionateType.class, is, false);
    //	    } catch (JAXBException e) {
    //		gestisciEccezione(e, "getListaPendenze");
    //	    }
    //	} else {
    //	    log.debug("uri ws [lista pendenze]: {} ", client.getCurrentURI());
    //	    gestisciErroreHttp(response, "getListaPendenze");
    //	}
    //	return debitorieSelezionateType;
    //    }

    //Aggiorna posizione debitoria
    public void aggiornaPosizioneDebitoria(String idDebito, DatiUpdatePosizioneDebitoriaType updatePos) throws ProblemException {

	// "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}"
	WebClient client = null;
	String path = PAGOPA_POSIZIONI_DEBITORIE_AGGIORNA.replace("{id_ente}", idEnte).replace("{id_servizio}", idServizio).replace("{id_debito}",
		idDebito);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	String str = new String();
	try {
	    str = JSONUtils.marshal(updatePos, false);
	    log.debug("aggiornaPendenza: {}", str);
	    Response response = client.put(str);
	    Integer status = response.getStatus();
	    if (status.equals(200) || status.equals(201)) {
		log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
		log.debug("url ws [aggiorna pendenza]: {} ", client.getCurrentURI());
	    } else {
		log.error("uri ws [aggiorna pendenza]: {} ", client.getCurrentURI());
		gestisciErroreHttp(response, "aggiornaPosizioneDebitoria");
	    }
	} catch (JAXBException e) {
	    log.error("Errore nel marshalling del oggetto aggiornaPosizioneDebitoria", e);
	}
    }

    //Annulla posizione debitoria
    public OkMessage annullaPosizione(String idDebito) throws ProblemException {

	// "/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}"
	WebClient client = null;
	String path = PAGOPA_POSIZIONI_DEBITORIE_ELIMINAZIONE.replace("{id_ente}", idEnte).replace("{id_servizio}", idServizio).replace("{id_debito}",
		idDebito);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	Response response = client.delete();
	Integer status = response.getStatus();
	OkMessage okMessage = null;
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [annulla posizione]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		okMessage = JSONUtils.unmarshal(OkMessage.class, is, false);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "annullaPosizione");
	    }
	} else {
	    log.debug("uri ws [annulla posizione]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "annullaPosizione");
	}
	return okMessage;
    }

    //stato posizione debitoria
    public DettaglioPosizioneDebitoriaType getStatoPosizioneDebitoria(String idDebito) throws ProblemException {

	//"/pagopa/posizioni-debitorie/{id_ente}/{id_servizio}/{id_debito}"
	WebClient client = null;
	String path = PAGOPA_POSIZIONI_DEBITORIE_STATO.replace("{id_ente}", idEnte).replace("{id_servizio}", idServizio).replace("{id_debito}",
		idDebito);
	try {
	    client = createWebClient(baseUrl, path, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	Response response = client.get();
	Integer status = response.getStatus();
	DettaglioPosizioneDebitoriaType debitoriaType = null;
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [stato posizione debitoria]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		debitoriaType = JSONUtils.unmarshal(DettaglioPosizioneDebitoriaType.class, is, false);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "getStatoPosizioneDebitoria");
	    }
	} else {
	    log.debug("uri ws [stato posizione debitoria]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getStatoPosizioneDebitoria");
	}
	return debitoriaType;
    }

    public OkMessage registraPagamento(RichiestaPosizioneDebitoriaRendicontazioneAscotType richiestaPosDeb) throws ProblemException {

	WebClient client = null;
	try {
	    client = createWebClient(baseUrl, PAGOPA_POSIZIONI_DEBITORIE_RICONCILIAZIONE_ASCOT, false);
	} catch (IOException | GeneralSecurityException | URISyntaxException e1) {
	    throw new ProblemException(e1);
	}
	String sw = new String();
	OkMessage okMessage = null;
	try {
	    sw = JSONUtils.marshal(richiestaPosDeb, false);
	    log.debug("registrazione pagamento ascot {}", sw);
	} catch (JAXBException e) {
	    gestisciEccezione(e, "registraPagamento");
	}
	Response response = client.post("[" + sw.toString() + "]");
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201) || status.equals(202)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [pagamento ascot]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		okMessage = JSONUtils.unmarshal(OkMessage.class, is, false);
		log.debug("okMessage {}", okMessage);
	    } catch (JAXBException e) {
		gestisciEccezione(e, "registraPagamento");
	    }
	} else {
	    log.debug("uri ws [registrazione pagamento ascot]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "registraPagamento");
	}
	return okMessage;
    }

    private WebClient createWebClient(String baseUrl, String path, boolean isAsync) throws IOException, GeneralSecurityException, URISyntaxException {

	WebClient client = WebClient.create(baseUrl + path);
	log.debug("createWebClient {}{}", baseUrl, path);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).encoding(StandardCharsets.UTF_8.name());
	HTTPConduit conduit = null;
	if (isAsync) {
	    WebClient.getConfig(client).getRequestContext().put("use.async.http.conduit", true);
	    WebClient.getConfig(client).getRequestContext().put("use.httpurlconnection.method.reflection", true);
	}
	conduit = WebClient.getConfig(client).getHttpConduit();
	WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor(10240));
	WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor(10240));
	try {
	    URL serviceUrl = new URL(baseUrl + path);
	    if (this.authorizationType.equalsIgnoreCase("SSL")) {
		log.debug("ssl authentication");
		if (serviceUrl.getProtocol().equalsIgnoreCase("HTTPS")) {
		    TLSClientParameters params = conduit.getTlsClientParameters();
		    if (params == null) {
			params = new TLSClientParameters();
			conduit.setTlsClientParameters(params);
		    }
		    conduit.setTlsClientParameters(params);
		    log.debug("TLS/SSL params set");
		}
	    } else {
		log.debug("basic authentication");
		conduit.setAuthorization(basicAuthorization());
	    }
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}
	return client;
    }

    private AuthorizationPolicy basicAuthorization() {

	AuthorizationPolicy authPolicy = new AuthorizationPolicy();
	authPolicy.setUserName(this.utente);
	authPolicy.setPassword(this.password);
	authPolicy.setAuthorizationType("Basic");
	return authPolicy;
    }

    private void gestisciErroreHttp(Response response, String methodName) throws ProblemException {

	InputStream is = ((InputStream) response.getEntity());
	Problem errore = null;
	if (response.getMediaType() != null && (MediaTypeProblem.equalsIgnoreCase(response.getMediaType().toString())
		|| MediaType.APPLICATION_JSON.equalsIgnoreCase(response.getMediaType().toString()))) {
	    try {
		errore = JSONUtils.unmarshal(Problem.class, is, false);
		log.error("{}", errore);
		throw new ProblemException(errore);
	    } catch (JAXBException e) {
		log.debug(" Errore nel umarshalling dell'oggetto Problem:  {}", methodName, e);
		throw new ProblemException("Errore nella conversione dell'oggetto problem " + e.getMessage(), e);
	    }
	} else {
	    String text = null;
	    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
		text = scanner.useDelimiter("\\A").next();
	    }
	    log.error("gestisciErroreHttp: {}", text);
	    throw new ProblemException(text);
	}
    }

    private void gestisciEccezione(Exception e, String metodo) throws ProblemException {

	log.error("[{}] Errore: {}", metodo, e.getMessage(), e);
	throw new ProblemException("Errore nella conversione dell'oggetto problem " + e.getMessage(), e);
    }
}
