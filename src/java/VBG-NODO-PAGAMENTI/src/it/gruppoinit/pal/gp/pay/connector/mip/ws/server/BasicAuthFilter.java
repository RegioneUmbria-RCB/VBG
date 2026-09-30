package it.gruppoinit.pal.gp.pay.connector.mip.ws.server;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

public class BasicAuthFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(BasicAuthFilter.class);
    private String connector;
    @Autowired
    private PayConnectorService payConnectorService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

	String authorization = requestContext.getHeaderString("Authorization");
	if (authorization != null) {
	    String[] parts = authorization.split(" ");
	    if (parts.length != 2 || !"Basic".equals(parts[0])) {
		requestContext.abortWith(createFaultResponse("L'ottenimento del Authorization header è andato in fallimento"));
		return;
	    }
	    String decodedValue = null;
	    try {
		decodedValue = new String(Base64Utility.decode(parts[1]));
	    } catch (Base64Exception ex) {
		requestContext.abortWith(createFaultResponse("Il decoding del Authorization header è andato in fallimento"));
		return;
	    }
	    String[] namePassword = decodedValue.split(":");
	    if (isAuthenticated(namePassword[0], namePassword[1])) {
		// let request to continue
	    } else {
		// authentication failed, request the authetication, add the realm name if needed to the value of WWW-Authenticate 
		requestContext.abortWith(
			Response.status(401).header("WWW-Authenticate", "Basic").entity("Autorizazzione fallito: credenziali non validi").build());
	    }
	} else {
	    log.error("Nessun header di autorizazzione trovato");
	    requestContext.abortWith(Response.status(401).header("WWW-Authenticate", "No Authorization header found")
		    .entity("Nessun header di autorizazzione trovato").build());
	}
    }

    private boolean isAuthenticated(String usr, String pwd) {

	if (StringUtils.isNotEmpty(connector)) {
	    PayConnectorConfig connCfg = this.payConnectorService.findById(connector);
	    if (connCfg != null) {
		if (usr.equals(connCfg.getInWsUsr()) && pwd.equals(connCfg.getInWsPwd())) {
		    return true;
		} else {
		    log.error("Username e password non riconosciuti: {}", usr);
		    return false;
		}
	    } else {
		log.error("handle - autenticazione WS fallita! nessun connettore configurato con il codice " + connector);
	    }
	}
	return false;
    }

    private Response createFaultResponse(String message) {

	return Response.status(401).header("WWW-Authenticate", "Basic realm=\"service.com\"").entity(message).build();
    }

    public String getConnector() {

	return connector;
    }

    public void setConnector(String connector) {

	this.connector = connector;
    }
}
