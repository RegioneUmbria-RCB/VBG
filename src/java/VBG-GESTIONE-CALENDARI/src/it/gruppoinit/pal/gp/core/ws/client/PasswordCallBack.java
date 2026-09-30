package it.gruppoinit.pal.gp.core.ws.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

public class PasswordCallBack implements CallbackHandler {

    private String password = "";
    private String username = "";

    public PasswordCallBack(String username, String password) {

	this.password = password;
	this.username = username;
    }

    @SuppressWarnings("unused")
    private PasswordCallBack() {

    }

    /**
     * @see javax.security.auth.callback.CallbackHandler#handle(javax.security.auth.callback.Callback[])
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof WSPasswordCallback) {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
		// set the password given a username
		if (this.username.equals(pc.getIdentifier())) {
		    pc.setPassword(this.password);
		}
	    } else {
		throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
	    }
	}
    }
}
