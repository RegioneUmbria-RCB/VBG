package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityServiceLocator;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
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

    public SigeproSecurity getPort() throws ServiceException, MalformedURLException {

	if (port == null) {
	    URL urlWs = new URL(wsUrl);
	    PasswordCallBack pwCallback = new PasswordCallBack(username, password);
	    EngineConfiguration config = new FileProvider("client_deploy.wsdd");
	    SigeproSecurityServiceLocator locator = new SigeproSecurityServiceLocator(config);
	    SigeproSecurity port = locator.getsigeproSecuritySoap11(urlWs);
	    ((org.apache.axis.client.Stub) port)._setProperty(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
	    ((org.apache.axis.client.Stub) port)._setProperty(WSHandlerConstants.USER, username);
	    ((org.apache.axis.client.Stub) port)._setProperty(WSHandlerConstants.PW_CALLBACK_REF, pwCallback);
	    this.port = port;
	}
	return port;
    }
}
