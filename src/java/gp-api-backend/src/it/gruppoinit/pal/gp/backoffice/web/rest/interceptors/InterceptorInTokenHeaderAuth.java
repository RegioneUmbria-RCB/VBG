package it.gruppoinit.pal.gp.backoffice.web.rest.interceptors;

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
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.userdetails.UserDetails;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.ContestoType;

public class InterceptorInTokenHeaderAuth extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = LoggerFactory.getLogger(InterceptorInTokenHeaderAuth.class);
    private String header = "Authorization";
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private UserSecurityService userSecurityService;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public InterceptorInTokenHeaderAuth() {

	super(Phase.PRE_LOGICAL);
    }

    public InterceptorInTokenHeaderAuth(String phase) {

	super(phase);
    }

    @Override
    public void handleFault(Message message) {

	super.handleFault(message);
	message.put(Message.RESPONSE_CODE, 401);
	HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
	try {
	    ORMHelper.destroyORMHelper();
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	} catch (IOException e) {
	    log.error("InterceptorInTokenHeaderAuth#handleFault: ", e);
	}
    }

    @Override
    public void handleMessage(Message message) throws Fault {

	log.debug("InterceptorInTokenHeaderAuth IN");
	HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	if (request != null) {
	    if (!StringUtils.isBlank(request.getQueryString()) && request.getQueryString().contains("_wadl")) {
		return;
	    }
	    if (!request.getMethod().equals("OPTIONS")) {
		String token = request.getHeader(header);
		try {
		    if (StringUtils.isNotBlank(token)) {
			log.debug("InterceptorInTokenHeaderAuth auth: {}", token);
			setORMHelper(token, request);
			return;
		    }
		} catch (Exception e) {
		    log.error("InterceptorInTokenHeaderAuth: {}", e);
		}
		throw new BadCredentialsException("Errore nell'autenticazione");
	    }
	}
	log.debug("InterceptorInTokenHeaderAuth OUT");
    }

    protected void setORMHelper(String token, HttpServletRequest request) {

	log.debug("InterceptorInTokenHeaderAuth setORMHelper token {}", token);
	CheckTokenResponse r = getTokenInfo(token);
	boolean autenticato = (r != null && r.getTokenInfo() != null && r.isValid());
	if (autenticato) {
	    log.debug("InterceptorInTokenHeaderAuth setORMHelper token chiamato setto ORMHELPER");
	    Properties connProps = externalDBResolver.getConnectionProperties(r.getTokenInfo().getAlias());
	    ORMHelper.setToken(token);
	    ORMHelper.setIdcomune(r.getTokenInfo().getIdcomune());
	    ORMHelper.setIdcomuneAlias(r.getTokenInfo().getAlias());
	    ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	    ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	    UsernamePasswordAuthenticationToken authToken = null;
	    UserDetails user = null;
	    if (r.getTokenInfo().getContesto().equals(ContestoType.APP)) {
		user = userSecurityService.loadAdministratorUser();
	    } else if (r.getTokenInfo().getContesto().equals(ContestoType.OPE)) {
		user = userSecurityService.loadUserByUsername(r.getTokenInfo().getUserid());
	    }
	    if (user != null) {
		authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
		authToken.setDetails(authenticationDetailsSource.buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
	    } else {
		throw new RuntimeException("Errore nell'autenticazione");
	    }
	} else {
	    throw new RuntimeException("Errore nell'autenticazione");
	}
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
}
