package it.gruppoinit.pal.gp.core.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.security.EasyPaSecurityRequestParams;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class OAuth2SecurityRestTokenManager {

    private static final Logger log = LoggerFactory.getLogger(OAuth2SecurityRestTokenManager.class);
    private static final long DEFAULT_TOKEN_DURATION_MILLIS = 60 * 60 * 1000L;
    private long tokenGenerationTime = 0;
    private String token = null;
    private long tokenDurationMillis = 0;
    private PayConnectorWsEndpoint securityService;
    private IOAuth2Params params;
    private static final String DEFAULT_TOKEN_TYPE = "Bearer";
    private String tokenType = DEFAULT_TOKEN_TYPE;

    private OAuth2SecurityRestTokenManager() {

	super();
	this.tokenDurationMillis = DEFAULT_TOKEN_DURATION_MILLIS;
    }

    public static void main(String[] args) {

	PayConnectorWsEndpoint as = new PayConnectorWsEndpoint();
	as.setEndpointUrl("");
	as.setUtente("");
	as.setPassword("");
	EasyPaSecurityRequestParams p = new EasyPaSecurityRequestParams("", "", "", "", "");
	try {
	    OAuth2SecurityRestTokenManager m = new OAuth2SecurityRestTokenManager(6000, as, p);
	    String c = m.refreshToken();
	    System.out.println(c);
	} catch (PayException e) {
	    e.printStackTrace();
	}
    }

    public OAuth2SecurityRestTokenManager(PayConnectorWsEndpoint securityService, IOAuth2Params params) throws PayException {

	this();
	this.params = params;
	this.securityService = securityService;
	this.validateParams();
    }

    public OAuth2SecurityRestTokenManager(long tokenDuration, PayConnectorWsEndpoint securityService, IOAuth2Params params) throws PayException {

	this();
	this.params = params;
	this.securityService = securityService;
	this.tokenDurationMillis = tokenDuration;
	this.validateParams();
    }

    private void validateParams() throws PayException {

	if (this.params == null || !this.params.validateParams()) {
	    String message = "Configurazione del connettore EasyPa non valida i parametri per autenticare i servizi rest non sono validi" +
		    this.params;
	    throw new PayException(message);
	}
    }

    public String getSecurityToken() throws PayException {

	if (!checkTokenValidity()) {
	    this.token = refreshToken();
	}
	return this.token;
    }

    public String getTokenType() {

	return tokenType;
    }

    public boolean checkTokenValidity() {

	if (token == null) {
	    return false;
	} else {
	    long nowMillis = new Date().getTime();
	    return nowMillis - tokenGenerationTime < tokenDurationMillis;
	}
    }

    private String refreshToken() throws PayException {

	if (securityService == null) {
	    throw new PayConfigurationException(
		    "Impossibile generare iul token di sicurezza: non è configurtato l'endpont per il servizio apposito.");
	}
	String tkn = null;
	try {
	    URL secUrl = new URL(securityService.getEndpointUrl());
	    HttpURLConnection conn = (HttpURLConnection) secUrl.openConnection();
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    String encodedKeys = securityService.getUtente() + ":" + securityService.getPassword();
	    encodedKeys = CryptoUtils.base64Encode(encodedKeys.getBytes());
	    conn.setRequestProperty("Authorization", "Basic " + encodedKeys);
	    String reqParams = this.params.buildQueryString();
	    OutputStream outStream = conn.getOutputStream();
	    DataOutputStream out = new DataOutputStream(outStream);
	    out.writeBytes(reqParams);
	    out.flush();
	    out.close();
	    int responseCode = conn.getResponseCode();
	    BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    int read = 0;
	    while ((read = bis.read(buf)) > -1) {
		baos.write(buf, 0, read);
	    }
	    String jsonOut = new String(baos.toByteArray(), StandardCharsets.UTF_8);
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		JSONObject json = new JSONObject(jsonOut);
		this.tokenDurationMillis = json.getInt("expires_in") * 1000L;
		this.token = json.getString("access_token");
		this.tokenType = json.getString("token_type");
		this.tokenGenerationTime = new Date().getTime();
		tkn = this.token;
	    } else {
		String ret = conn.getResponseMessage();
		throw new PayException(ret + "\n" + jsonOut);
	    }
	} catch (Exception e) {
	    String msg = "errore nella generazione di un nuovo token di sicurezza";
	    log.error("SecurityTokenManager.refreshToken() - {}", msg, e);
	    throw new PayException(msg, e);
	}
	return tkn;
    }
}
