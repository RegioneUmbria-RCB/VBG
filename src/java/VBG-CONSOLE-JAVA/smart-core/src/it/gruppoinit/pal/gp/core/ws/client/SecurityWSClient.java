package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
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

    public SecurityWSClient() {

	super();
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
	    JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	    factory.setServiceClass(SigeproSecurity.class);
	    factory.setAddress(wsUrl);
	    this.port = (SigeproSecurity) factory.create();
	    Client proxy = ClientProxy.getClient(port);
	    HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	    BindingProvider bp = (BindingProvider) port;
	    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
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
}
