package it.gruppoinit.pal.gp.pay.ws.interceptors;

import java.net.HttpURLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.binding.soap.interceptor.SoapHeaderInterceptor;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration(value = "basicAuthAuthorizationInterceptor")
@PropertySource("classpath:ws-in-users.properties")
public class BasicAuthAuthorizationInterceptor extends SoapHeaderInterceptor {

    protected static final Logger log = LoggerFactory.getLogger(BasicAuthAuthorizationInterceptor.class);
    @Autowired
    private Environment env;

    public BasicAuthAuthorizationInterceptor() {

	super();
	log.debug("inizializzo l'interceptor BasicAuthAuthorizationInterceptor");
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
	    sendErrorResponse(message, HttpURLConnection.HTTP_UNAUTHORIZED);
	    return;
	}
	String passwordFound = env.getProperty(policy.getUserName());
	// Verify the password
	if (StringUtils.isBlank(passwordFound) || !passwordFound.equals(policy.getPassword())) {
	    log.error("Invalid username or password for user: {}", policy.getUserName());
	    sendErrorResponse(message, HttpURLConnection.HTTP_FORBIDDEN);
	}
    }

    private void sendErrorResponse(Message message, int responseCode) {

	throw new SecurityException("Autenticazione fallita");
    }
}