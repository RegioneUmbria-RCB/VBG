package it.gruppoinit.pal.gp.pay.connector.govpay.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.SSLSocketFactoryGenerator;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.FaultBean;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.NuovoPagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.Pagamento;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pagamenti.PagamentoCreato;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.ListaPatchOp;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.ListaPendenze;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.NuovaPendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PatchOp;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.Pendenza;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.PendenzaCreata;
import it.gruppoinit.pal.gp.pay.connector.govpay.rest.model.v2.pendenza.Profilo;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class GovPayClient {

    private static final String STORE_FORMAT_JKS = "jks";
    private String password;
    private String utente;
    private String baseUrl;
    private String authorizationType;
    private String certAlias;
    private String trustStoreLocation;
    private String trustStorePassword;
    private String keyStoreLocation;
    private String keyStorePassword;
    private String urlApiProfilo;
    private String urlApiPendenze;
    private String urlApiPagamenti;
    private String urlOverridePatchOperations;
    private static final Logger log = LoggerFactory.getLogger(GovPayClient.class);
    private static final String SUCCESSO_CHIAMATA = "CHIAMATA RIUSCITA RESPONSE STATUS: {} {}";

    public static GovPayClient getClient(GovPayClientParameters params) {

	if (params.getSslClientParameters() == null) {
	    return new GovPayClient(params.getBasicAuthParams(), params.getBaseUrl(), params.getUrlApiProfilo(), params.getUrlApiPendenze(),
		    params.getUrlApiPagamenti(), params.getConnectorConfigParams());
	}
	return new GovPayClient(params.getBaseUrl(), params.getSslClientParameters(), params.getUrlApiProfilo(), params.getUrlApiPendenze(),
		params.getUrlApiPagamenti(), params.getConnectorConfigParams());
    }

    private GovPayClient(Map<ConfigParamNames, String> connectorConfigParams) {

	super();
	if (connectorConfigParams != null) {
	    this.urlOverridePatchOperations = connectorConfigParams.get(ConfigParamNames.GOV_PAY_BASE_URL_PATCH_OPS);
	}
    }

    private GovPayClient(BasicAuthParams authParams, String baseUrl, String urlApiProfilo, String urlApiPendenze, String urlApiPagamenti,
	    Map<ConfigParamNames, String> connectorConfigParams) {

	this(connectorConfigParams);
	this.utente = authParams.getUtente();
	this.password = authParams.getPassword();
	this.baseUrl = baseUrl;
	this.authorizationType = "basic";
	this.urlApiProfilo = urlApiProfilo;
	this.urlApiPendenze = urlApiPendenze;
	this.urlApiPagamenti = urlApiPagamenti;
    }

    private GovPayClient(String baseUrl, SSLClientParameters sslParams, String urlApiProfilo, String urlApiPendenze, String urlApiPagamenti,
	    Map<ConfigParamNames, String> connectorConfigParams) {

	this(connectorConfigParams);
	this.baseUrl = baseUrl;
	this.certAlias = sslParams.getCertAlias();
	this.trustStoreLocation = sslParams.getTrustStoreLocation();
	this.trustStorePassword = sslParams.getTrustStorePassword();
	this.keyStoreLocation = sslParams.getKeyStoreLocation();
	this.keyStorePassword = sslParams.getKeyStorePassword();
	this.authorizationType = "ssl";
	this.urlApiProfilo = urlApiProfilo;
	this.urlApiPendenze = urlApiPendenze;
	this.urlApiPagamenti = urlApiPagamenti;
    }

    public Profilo getProfilo() throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(composeUrlProfilo() + "/profilo", false);
	Response response = client.get();
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [profilo]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(Profilo.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), Profilo.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[Profilo] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("url ws [profilo]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getProfilo");
	}
	return null;
    }

    public ListaPendenze getListaPendenze() throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(composeUrlPendenze(), false);
	Response response = client.get();
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [lista pendenza]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(ListaPendenze.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), ListaPendenze.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[Lista Pendenze] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [lista pendenza]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getListaPendenze");
	}
	return null;
    }

    public PendenzaCreata inserisciPendenza(String idA2A, String idPendenza, NuovaPendenza nuovaPendenza, boolean stampaAvviso)
	    throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	String qsAvviso = "?stampaAvviso=" + stampaAvviso;
	WebClient client = createWebClient(composeUrlPendenze() + "/" + idA2A + "/" + idPendenza + qsAvviso, false);
	StringWriter sw = new StringWriter();
	try {
	    JAXBContext jc = JAXBContext.newInstance(NuovaPendenza.class);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	    marshaller.marshal(nuovaPendenza, sw);
	    log.debug("inserisciPendenza {}", sw);
	} catch (JAXBException e) {
	    log.debug("Errore nel marshalling del oggetto NuovaPendenza", e);
	}
	Response response = client.put(sw.toString());
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [nuova pendenza]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(PendenzaCreata.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), PendenzaCreata.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[nuova Pendenze] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [inserimento pendenza]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "inserisciPendenza");
	}
	return null;
    }

    public Pendenza getDettaglioPendenza(String idA2A, String idPendenza)
	    throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(composeUrlPendenze() + "/" + idA2A + "/" + idPendenza, false);
	Response response = client.get();
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [dettaglio pendenza]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(Pendenza.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), Pendenza.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[dettaglio Pendenze] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [dettaglio pendenza]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getDettaglioPendenza");
	}
	return null;
    }

    private String getBaseUrlForPatchOperations() {

	return StringUtils.defaultIfBlank(this.urlOverridePatchOperations, this.baseUrl);
    }

    public void aggiornaPendenza(String idA2A, String idPendenza, List<PatchOp> patchOp)
	    throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(getBaseUrlForPatchOperations(), composeUrlPendenze() + "/" + idA2A + "/" + idPendenza, true);
	StringWriter sw = new StringWriter();
	try {
	    JAXBContext jc = JAXBContext.newInstance(ListaPatchOp.class);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	    marshaller.marshal(patchOp, sw);
	    log.debug("aggiornaPendenza: {}", sw);
	    Response response = client.invoke("PATCH", sw.toString());
	    Integer status = response.getStatus();
	    if (status.equals(200)) {
		log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
		log.debug("url ws [patch pendenza]: {} ", client.getCurrentURI());
	    } else {
		log.error("uri ws [patch pendenza]: {} ", client.getCurrentURI());
		gestisciErroreHttp(response, "aggiornaPendenza");
	    }
	} catch (JAXBException e) {
	    log.error("Errore nel marshalling del oggetto PatchOp", e);
	    throw new PayException(e);
	}
    }

    public PagamentoCreato avvioPagamento(NuovoPagamento nuovoPagamento)
	    throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(composeURLPagamenti(), false);
	StringWriter sw = new StringWriter();
	try {
	    JAXBContext jc = JAXBContext.newInstance(NuovoPagamento.class);
	    Marshaller marshaller = jc.createMarshaller();
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
	    marshaller.marshal(nuovoPagamento, sw);
	    log.debug("avvioPagamento: {}", sw);
	} catch (JAXBException e) {
	    log.debug("Errore nel marshalling del oggetto NuovoPagamento", e);
	}
	Response response = client.post(sw.toString());
	Integer status = response.getStatus();
	if (status.equals(200) || status.equals(201)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [avvio pagamento]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(PagamentoCreato.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), PagamentoCreato.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[avvio pagamento] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [avvio pagamento]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "avvioPagament");
	}
	return null;
    }

    public Pagamento getDettaglioPagamento(String id) throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	WebClient client = createWebClient(composeURLPagamenti() + "/" + id, false);
	Response response = client.get();
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [dettaglio pagamento]: {} ", client.getCurrentURI());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		JAXBContext jc = JAXBContext.newInstance(Pagamento.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		return unmarshaller.unmarshal(new StreamSource(is), Pagamento.class).getValue();
	    } catch (JAXBException e) {
		log.debug("[dettaglio pagamento] Errore nel umarshalling: {}", e.getMessage());
	    }
	} else {
	    log.debug("uri ws [dettaglio pagamento]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getDettaglioPagamento");
	}
	return null;
    }

    private WebClient createWebClient(String baseUrlOverride, String path, boolean isAsync)
	    throws IOException, GeneralSecurityException, URISyntaxException {

	WebClient client = WebClient.create(baseUrlOverride + path);
	log.debug("createWebClient {}{}", baseUrlOverride, path);
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
	    URL serviceUrl = new URL(baseUrlOverride + path);
	    if (this.authorizationType.equalsIgnoreCase("SSL")) {
		log.debug("ssl authentication");
		if (serviceUrl.getProtocol().equalsIgnoreCase("HTTPS")) {
		    TLSClientParameters params = conduit.getTlsClientParameters();
		    if (params == null) {
			params = new TLSClientParameters();
			conduit.setTlsClientParameters(params);
		    }
		    log.debug("setting ssl params: truststore location {} \nkeystore location {}", this.trustStoreLocation, this.keyStoreLocation);
		    params.setSSLSocketFactory(SSLSocketFactoryGenerator.getSSLSocketFactory(this.certAlias //
			    , this.keyStoreLocation //
			    , this.trustStoreLocation // 
			    , this.keyStorePassword //
			    , this.trustStorePassword //
			    , STORE_FORMAT_JKS));
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

    private WebClient createWebClient(String path, boolean isAsync) throws IOException, GeneralSecurityException, URISyntaxException {

	return createWebClient(this.baseUrl, path, isAsync);
    }

    private AuthorizationPolicy basicAuthorization() {

	AuthorizationPolicy authPolicy = new AuthorizationPolicy();
	authPolicy.setUserName(this.utente);
	authPolicy.setPassword(this.password);
	authPolicy.setAuthorizationType("Basic");
	return authPolicy;
    }

    private void gestisciErroreHttp(Response response, String methodName) throws PayException {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1}", methodName,
		response.getStatusInfo().getReasonPhrase());
	PayException payException = null;
	InputStream is = ((InputStream) response.getEntity());
	if (response.getMediaType() != null && MediaType.APPLICATION_JSON.equals(response.getMediaType().toString())) {
	    FaultBean errore = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(FaultBean.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
		errore = unmarshaller.unmarshal(new StreamSource(is), FaultBean.class).getValue();
		log.error(errorMessage);
		log.error(errore.getDettaglio());
		StringBuilder s = new StringBuilder();
		s.append(errore.getDescrizione());
		if (StringUtils.isNotEmpty(errore.getDettaglio())) {
		    s.append(" : ").append(errore.getDettaglio());
		}
		payException = new PayException(s.toString());
		payException.setErrorCode(errore.getCodice());
		throw payException;
	    } catch (JAXBException e) {
		throw new PayException(" Errore nel umarshalling del oggetto FaultBean:" + methodName, e);
	    }
	} else {
	    String text = null;
	    try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
		text = scanner.useDelimiter("\\A").next();
	    }
	    log.error("gestisciErroreHttp: {}", text);
	    payException = new PayException(text);
	    payException.setErrorCode(String.valueOf(response.getStatus()));
	    throw payException;
	}
    }

    public InputStream getAvviso(String idDominio, String numeroAvviso)
	    throws IOException, GeneralSecurityException, URISyntaxException, PayException {

	String url = composeURLPagamenti().replace("/pagamenti", "/avvisi") + "/" + idDominio + "/" + numeroAvviso;
	WebClient client = createWebClient(url, false);
	client.accept("application/pdf");
	Response response = client.get();
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug(SUCCESSO_CHIAMATA, status, response.getStatusInfo().getReasonPhrase());
	    log.debug("url ws [dettaglio pendenza]: {} ", client.getCurrentURI());
	    return ((InputStream) response.getEntity());
	} else {
	    log.debug("uri ws [dettaglio pendenza]: {} ", client.getCurrentURI());
	    gestisciErroreHttp(response, "getDettaglioPendenza");
	}
	return null;
    }

    private String composeUrlPendenze() {

	return urlApiPendenze;
    }

    private String composeURLPagamenti() {

	return urlApiPagamenti;
    }

    private String composeUrlProfilo() {

	return urlApiProfilo;
    }
}
