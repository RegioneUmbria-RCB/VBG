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

import it.gruppoinit.pal.gp.pay.connector.openweb.OpenWebSecurityParams;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;

public class OAuth2UserNamePasswordSecurityRestTokenManager {

    private static final Logger log = LoggerFactory.getLogger(OAuth2UserNamePasswordSecurityRestTokenManager.class);
    private PayConnectorWsEndpoint securityService;
    private OpenWebSecurityParams params;
    private String token = null;
    private static final String DEFAULT_TOKEN_TYPE = "Bearer";
    private String tokenType = DEFAULT_TOKEN_TYPE;
    private long tokenDurationMillis = 0;
    private long tokenGenerationTime = 0;

    public OAuth2UserNamePasswordSecurityRestTokenManager(PayConnectorWsEndpoint securityService, OpenWebSecurityParams params) {

	super();
	this.securityService = securityService;
	this.params = params;
    }

    public String getToken() throws PayException {

	if (!checkTokenValidity()) {
	    this.token = refreshToken();
	}
	return this.token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public String getTokenType() {

	return tokenType;
    }

    public void setTokenType(String tokenType) {

	this.tokenType = tokenType;
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
	URL secUrl;
	String tkn = null;
	try {
	    secUrl = new URL(securityService.getEndpointUrl());
	    HttpURLConnection conn = (HttpURLConnection) secUrl.openConnection();
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
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
		this.tokenGenerationTime = Long.parseLong(json.getString("created_at"));
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

    public static void main(String[] args) {

	PayConnectorWsEndpoint as = new PayConnectorWsEndpoint();
	as.setEndpointUrl("");
	as.setUtente("");
	as.setPassword("");
	OpenWebSecurityParams p = new OpenWebSecurityParams("", "", "");
	try {
	    OAuth2UserNamePasswordSecurityRestTokenManager m = new OAuth2UserNamePasswordSecurityRestTokenManager(as, p);
	    String c = m.getToken();
	    System.out.println(c);
	} catch (PayException e) {
	    e.printStackTrace();
	}
    }
}
