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

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

public class ConnectorPwdCallBackHandler implements CallbackHandler {

    private static final Logger log = LoggerFactory.getLogger(ConnectorPwdCallBackHandler.class);
    private String connector;
    @Autowired
    private PayConnectorService payConnectorService;

    public ConnectorPwdCallBackHandler() {

    }

    public ConnectorPwdCallBackHandler(String connector) {

	this.connector = connector;
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

	for (int i = 0; i < callbacks.length; i++) {
	    if (callbacks[i] instanceof WSPasswordCallback) {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
		// set the password given a username
		if(StringUtils.isNotEmpty(connector)) {
		    PayConnectorConfig connCfg = this.payConnectorService.findById(connector);
		    if(connCfg != null) {
			if(pc.getIdentifier().equals(connCfg.getInWsUsr())) {
			    pc.setPassword(connCfg.getInWsPwd());
			} else {
			    log.error("Username non riconosciuto: {}", pc.getIdentifier());
			}
		    }
		    else {
			log.error("handle - autenticazione WS fallita! nessun connettore configurato con il codice " + connector);
		    }
		}
	    } else {
		throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
	    }
	}
    }

    public String getConnector() {

	return connector;
    }

    public void setConnector(String connector) {

	this.connector = connector;
    }
}
