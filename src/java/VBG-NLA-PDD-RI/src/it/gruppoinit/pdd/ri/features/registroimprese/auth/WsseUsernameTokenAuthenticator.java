package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;

import it.gruppoinit.sigeprosecurity.ws.SecurityPwdCallBackHandler;

public class WsseUsernameTokenAuthenticator implements IAuthenticator {

    private Object port;
    private String userName;
    private String password;

    public WsseUsernameTokenAuthenticator(Object port, String userName, String password) {

	this.port = port;
	this.userName = userName;
	this.password = password;
    }

    public void authenticate() {

	Client proxy = ClientProxy.getClient(this.port);
	Endpoint cxfEndpoint = proxy.getEndpoint();
	Map<String, Object> outProps = new HashMap<>();
	outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
	// Specify our username
	outProps.put(WSHandlerConstants.USER, this.userName);
	// Password type : plain text
	outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
	SecurityPwdCallBackHandler pwdCallback = new SecurityPwdCallBackHandler(this.userName, this.password);
	outProps.put(WSHandlerConstants.PW_CALLBACK_REF, pwdCallback);
	WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);
	cxfEndpoint.getOutInterceptors().add(wssOut);
    }
}
