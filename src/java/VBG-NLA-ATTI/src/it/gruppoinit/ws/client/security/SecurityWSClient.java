package it.gruppoinit.ws.client.security;

import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;
import it.gruppoinit.sigeprosecurity.schema.LoginRequest;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.gruppoinit.ws.client.BaseWsClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.handler.WSHandlerConstants;

public class SecurityWSClient extends BaseWsClient {

    private SigeproSecurity port;
    private String wsUrl;
    private long timeout = 15000;
    private String username;
    private String password;
    private String alias;

    public SecurityWSClient() {

	super();
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getWsUrl() {

	return wsUrl;
    }

    public void setWsUrl(String wsUrl) {

	this.wsUrl = wsUrl;
    }

    public long getTimeout() {

	return timeout;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public String getUsername() {

	return username;
    }

    public void setUsername(String username) {

	this.username = username;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public SigeproSecurity getWsPort() throws Exception {

	if (port == null) {
	    SigeproSecurityService client = new SigeproSecurityService(new URL(wsUrl));
	    this.port = client.getSigeproSecuritySoap11();
	    Client proxy = ClientProxy.getClient(port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	    httpClientPolicy.setConnectionTimeout(this.timeout); // Line #2  
	    httpClientPolicy.setReceiveTimeout(this.timeout); // Line #3  
	    conduit.setClient(httpClientPolicy);
	    Endpoint securityEndpoint = proxy.getEndpoint();
	    Map<String, Object> outProps = new HashMap<String, Object>();
	    outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
	    outProps.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordDigest"); // WSConstants.PASSWORD_DIGEST ???
	    outProps.put(WSHandlerConstants.USER, this.username);
	    outProps.put(WSHandlerConstants.PW_CALLBACK_REF, new UsernamePasswordCallback(this.username, this.password));
	    WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	    securityEndpoint.getOutInterceptors().add(wssOut);
	    return port;
	} else {
	    return this.port;
	}
    }

    public String getToken() throws Exception {

	LoginRequest lr = new LoginRequest();
	lr.setAlias(alias);
	lr.setContesto(ContestoType.APP);
	lr.setPassword(password);
	lr.setUsername(username);
	lr.setIpAddress("nla-atti");
	LoginResponse s = getWsPort().login(lr);
	return s.getToken();
    }

    public String getIdcomune(String token) throws Exception {

	CheckTokenRequest checkTokenRequest = new CheckTokenRequest();
	checkTokenRequest.setToken(token);
	checkTokenRequest.setTokenInfo(true);
	CheckTokenResponse s = getWsPort().checkToken(checkTokenRequest);
	if (s.getTokenInfo() == null) {
	    throw new RuntimeException("A) Non è stato possibile recuperare l'identificativo comune con token " + token);
	}
	if (StringUtils.isBlank(s.getTokenInfo().getIdcomune())) {
	    throw new RuntimeException("B) Non è stato possibile recuperare l'identificativo comune con token " + token);
	}
	return s.getTokenInfo().getIdcomune();
    }
}
