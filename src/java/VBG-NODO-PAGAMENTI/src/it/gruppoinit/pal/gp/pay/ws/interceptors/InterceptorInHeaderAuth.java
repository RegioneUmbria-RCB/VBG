package it.gruppoinit.pal.gp.pay.ws.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

@Configuration(value = "interceptorInHeaderAuth")
@PropertySource("classpath:ws-in-users.properties")
public class InterceptorInHeaderAuth extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = LoggerFactory.getLogger(InterceptorInHeaderAuth.class);
    @Autowired
    private Environment env;

    public InterceptorInHeaderAuth() {

	super(Phase.PRE_LOGICAL);
    }

    public InterceptorInHeaderAuth(String phase) {

	super(phase);
    }

    @Override
    public void handleFault(Message message) {

	super.handleFault(message);
	message.put(Message.RESPONSE_CODE, new Integer(401));
	HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
	try {
	    ORMHelper.destroyORMHelper();
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	} catch (IOException e) {
	    log.error("InterceptorInHeaderAuth#handleFault: " + e.getMessage(), e);
	}
    }

    @Override
    public void handleMessage(Message message) throws Fault {

	SecurityContextHolder.clearContext();
	// This is set by CXF
	AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
	// If the policy is not set, the user did not specify
	// credentials. A 401 is sent to the client to indicate
	// that authentication is required
	if (policy == null) {
	    sendErrorResponse();
	    return;
	}
	String passwordFound = env.getProperty(policy.getUserName());
	// Verify the password
	if (StringUtils.isBlank(passwordFound) || !passwordFound.equals(policy.getPassword())) {
	    log.error("Invalid username or password for user: {}", policy.getUserName());
	    sendErrorResponse();
	}
    }

    private void sendErrorResponse() {

	throw new SecurityException("Autenticazione fallita");
    }
}
