package it.gruppoinit.pal.gp.core.ws.client;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsernamePasswordCallback implements CallbackHandler {

    private static final Logger log = LoggerFactory.getLogger(UsernamePasswordCallback.class);
    private String username;
    private String password;

    public UsernamePasswordCallback(String username, String password) {

	super();
	this.username = username;
	this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof WSPasswordCallback) {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
		// set the password given a username
		if (username.equals(pc.getIdentifier())) {
		    pc.setPassword(password);
		} else {
		    log.error("Username non riconosciuto: {}", username);
		}
	    } else {
		throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
	    }
	}
    }
}
