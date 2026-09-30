package it.gruppoinit.pal.gp.pay.connector.mip.ws.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.JSONUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.ws.schema.OAuth2Resp;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayConnectorConfigValuesService;

public class ApiClient {

    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private String secretKey;
    private String clientKey;
    private String utente;
    private String password;
    private String urlAuth;
    private Map<String, String> paramMap;
    private String authHeader = null;
    private String jwtComgeAssertionFirmato;
    private Long expiresIn = null;
    private Long expiresAt;
    private PayConnectorConfigValuesService payConnectorConfigValuesService;
    private static final long EXPIRY_BUFFER_MILLIS = TimeUnit.MINUTES.toMillis(2);

    public enum HttpMethods {

	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	GET("GET");

	private String val;

	private HttpMethods(String name) {

	    this.val = name;
	}

	public String value() {

	    return this.val;
	}

	public static HttpMethods fromValue(String v) {

	    return valueOf(v);
	}
    }

    /**
     * 
     * @param urlAuth
     * @param secretKey
     * @param clientKey
     * @param utente
     *            può essere null
     * @param password
     *            può essere null
     * @throws PayConfigurationException
     */
    public ApiClient(String urlAuth, String secretKey, String clientKey, String utente, String password,
	    PayConnectorConfigValuesService payConnectorConfigValuesService) throws PayConfigurationException {

	super();
	this.secretKey = secretKey;
	this.clientKey = clientKey;
	this.utente = utente;
	this.password = password;
	this.urlAuth = urlAuth;
	this.payConnectorConfigValuesService = payConnectorConfigValuesService;
	this.paramMap = getMapFromParams();
    }

    private void getAuthenticationToken(String httpMethod) throws PayException {

	long currentTimeMillis = System.currentTimeMillis();
	log.debug("getAuthenticationToken expiresIn: {}, expiresAt: {}, authHeader: {}, currentTimeMillis: {}",
		new Object[] { this.expiresIn, this.expiresAt, this.authHeader, currentTimeMillis });
	if (this.jwtComgeAssertionFirmato == null) {
	    this.jwtComgeAssertionFirmato = new ServiziJwtGenerator().generateJwtForAppCaller(this.payConnectorConfigValuesService);
	}
	if (this.authHeader != null && this.expiresAt != null && currentTimeMillis < this.expiresAt - EXPIRY_BUFFER_MILLIS) {
	    log.debug("getAuthenticationToken token ancora valido riuso quello {}", this.authHeader);
	    return;
	}
	try {
	    String jsonBodyReq = null;
	    OAuth2Resp json = null;
	    if (StringUtils.upperCase(httpMethod).equals("POST")) {
		jsonBodyReq = new JSONObject(paramMap).toString();
		log.debug("getAuthenticationToken jsonBodyReq: {}", jsonBodyReq);
		json = call(this.urlAuth, StringUtils.upperCase(httpMethod), jsonBodyReq, false, OAuth2Resp.class);
	    } else {
		String queryString = URLEncodedUtils.format(getListFromParams(), "utf-8");
		log.debug("getAuthenticationToken queryString{}, jsonBodyReq: {}", queryString, jsonBodyReq);
		json = call(this.urlAuth + queryString, StringUtils.upperCase(httpMethod), jsonBodyReq, false, OAuth2Resp.class);
	    }
	    this.authHeader = "Bearer " + json.getAccessToken();
	    this.expiresIn = json.getExpiresIn();
	    this.expiresAt = currentTimeMillis + this.expiresIn * 1000L;
	    this.jwtComgeAssertionFirmato = new ServiziJwtGenerator().generateJwtForAppCaller(this.payConnectorConfigValuesService);
	} catch (Exception e) {
	    log.error("getAuthenticationToken: " + e.getMessage(), e);
	    throw new PayException(e);
	}
    }

    public <T> T call(String urlWs, String httpMethod, String jsonBodyReq, boolean includeAuthHeader, Class<T> clazz) throws PayException {

	log.debug("call urlWS: {}, httpMethod: {}, jsonBodyReq: {}, includeAuthHeader: {}",
		new Object[] { urlWs, httpMethod, jsonBodyReq, includeAuthHeader });
	WebClient client = WebClient.create(urlWs);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).encoding(StandardCharsets.UTF_8.name());
	HTTPConduit conduit = null;
	conduit = WebClient.getConfig(client).getHttpConduit();
	WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor(10240));
	WebClient.getConfig(client).getOutInterceptors().add(new LoggingOutInterceptor(10240));
	conduit.getClient().setConnectionTimeout(600000);
	conduit.getClient().setReceiveTimeout(600000);
	if (includeAuthHeader) {
	    getAuthenticationToken("POST");
	    if (this.jwtComgeAssertionFirmato != null) {
		client.header("x-comge-jwt-assertion", this.jwtComgeAssertionFirmato);
	    }
	    client.header("Authorization", this.authHeader);
	}
	log.debug("client.getHeaders(): {}", client.getHeaders());
	Response response = null;
	switch (HttpMethods.fromValue(StringUtils.upperCase(httpMethod))) {
	case POST:
	    response = client.post(jsonBodyReq);
	    break;
	case PUT:
	    response = client.put(jsonBodyReq);
	    break;
	case DELETE:
	    response = client.delete();
	    break;
	case GET:
	    response = client.get();
	    break;
	default:
	    throw new PayException("metodo " + httpMethod + " non implementato");
	}
	Integer status = response.getStatus();
	if (status.equals(200)) {
	    log.debug("SUCCESSO_CHIAMATA {}, {}", status, response.getStatusInfo().getReasonPhrase());
	    InputStream is = ((InputStream) response.getEntity());
	    try {
		String content = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		log.debug("content {}", content);
		return JSONUtils.unmarshal(clazz, content, false);
	    } catch (IOException | JAXBException e) {
		log.error("Unmarshalling fallita: " + e.getMessage(), e);
	    }
	} else {
	    log.debug("uri ws wsgetIUVChiamanteEsterno: {} ", client.getCurrentURI());
	    this.gestisciErroreHttp(response, urlWs);
	}
	return null;
    }

    private Map<String, String> getMapFromParams() {

	Map<String, String> bodyReq = new HashMap<>();
	if ((clientKey != null && secretKey != null)) {
	    bodyReq.put("Key", clientKey);
	    bodyReq.put("Secret", secretKey);
	    if (this.utente == null || this.password == null) {
		bodyReq.put("Utente", utente);
		bodyReq.put("Password", password);
	    }
	}
	return bodyReq;
    }

    private List<NameValuePair> getListFromParams() {

	List<NameValuePair> list = new ArrayList<>();
	for (Entry<String, String> qparam : this.paramMap.entrySet()) {
	    list.add(new BasicNameValuePair(qparam.getKey(), qparam.getValue()));
	}
	return list;
    }

    private void gestisciErroreHttp(Response response, String methodName) throws PayException {

	String errorMessage = MessageFormat.format("[{0}]CHIAMATA NON RIUSCITA RESPONSE STATUS: {1} ({2})", methodName,
		response.getStatusInfo().getReasonPhrase(), response.getStatus());
	PayException payException = null;
	log.error(errorMessage);
	InputStream is = ((InputStream) response.getEntity());
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
