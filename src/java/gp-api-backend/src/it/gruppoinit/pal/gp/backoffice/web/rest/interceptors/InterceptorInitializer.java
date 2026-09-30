package it.gruppoinit.pal.gp.backoffice.web.rest.interceptors;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.AmbienteType;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoRequest;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;

public class InterceptorInitializer extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = LoggerFactory.getLogger(InterceptorInHeaderAuth.class);
    private String header = "Authorization";
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private UserSecurityService userSecurityService;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public InterceptorInitializer() {

	super(Phase.PRE_LOGICAL);
    }

    public InterceptorInitializer(String phase) {

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
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	} catch (IOException e) {
	    log.error("InterceptorInHeaderAuth#handleFault: {}", e);
	}
    }

    @Override
    public void handleMessage(Message message) throws Fault {

	log.debug("InterceptorInHeaderAuth IN");
	HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	if (request != null) {
	    if (!request.getMethod().equals("OPTIONS")) {
		try {
		    setORMHelper("", request);
		    return;
		} catch (Exception e) {
		    log.error("InterceptorInHeaderAuth: {}", e);
		}
	    }
	}
	log.debug("InterceptorInHeaderAuth OUT");
    }

    protected void setORMHelper(String headerauth, HttpServletRequest request) {

	Properties deployProperties = WebConstants.getDeployProperties();
	String idcomuneAlias = deployProperties.getProperty("default.idcomunealias");
	String software = deployProperties.getProperty("default.software");
	GetDbConnectionInfoResponse r = getAliasInfo(idcomuneAlias);
	log.debug("InterceptorInHeaderAuth setORMHelper token chiamato setto ORMHELPER");
	Properties connProps = externalDBResolver.getConnectionProperties(idcomuneAlias);
	ORMHelper.setIdcomune(r.getIdComune());
	ORMHelper.setIdcomuneAlias(idcomuneAlias);
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }

    protected CheckTokenResponse getTokenInfo(String token) {

	CheckTokenRequest req = new CheckTokenRequest();
	req.setToken(token);
	req.setTokenInfo(true);
	CheckTokenResponse ti = null;
	try {
	    ti = securityWSClient.getWsPort().checkToken(req);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	return ti;
    }

    protected GetDbConnectionInfoResponse getAliasInfo(String alias) {

	GetDbConnectionInfoResponse ti = null;
	try {
	    GetDbConnectionInfoRequest req = new GetDbConnectionInfoRequest();
	    req.setAlias(alias);
	    req.setAmbiente(AmbienteType.JAVA);
	    ti = securityWSClient.getWsPort().getDbConnectionInfo(req);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	return ti;
    }
}