package it.gruppoinit.pal.gp.pay.connector;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.commons.lang.StringUtils;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:ws-in-users.properties")
public class UserDatabaseCallBackHandler implements CallbackHandler {

    private static final Logger log = LoggerFactory.getLogger(UserDatabaseCallBackHandler.class);
    @Autowired
    private Environment env;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof WSPasswordCallback) {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
		String username = pc.getIdentifier();
		log.debug("username {}", username);
		String passwordFound = env.getProperty(username);
		if (StringUtils.isNotBlank(passwordFound)) {
		    log.debug("password found for user {} from property file", username);
		    pc.setPassword(passwordFound);
		}
	    } else {
		throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
	    }
	}
    }
}
