package it.gruppoinit.pdd.ri.features.registroimprese.auth;

import javax.xml.ws.BindingProvider;

public class BasicAuthenticator implements IAuthenticator {

    private Object port;
    private String userName;
    private String password;

    public BasicAuthenticator(Object port, String userName, String password) {

	this.port = port;
	this.userName = userName;
	this.password = password;
    }

    @Override
    public void authenticate() {

	BindingProvider bp = (BindingProvider) this.port;
	bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, this.userName);
	bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, this.password);
    }
}
