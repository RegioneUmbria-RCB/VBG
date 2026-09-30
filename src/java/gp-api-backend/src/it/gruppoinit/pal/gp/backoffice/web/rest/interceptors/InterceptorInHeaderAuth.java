package it.gruppoinit.pal.gp.backoffice.web.rest.interceptors;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

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

import com.sun.syndication.io.impl.Base64;

public class InterceptorInHeaderAuth extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = LoggerFactory.getLogger(InterceptorInHeaderAuth.class);
    private String header = "Authorization";
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private UserSecurityService userSecurityService;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

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
		String auth = request.getHeader(header);
		try {
		    if (StringUtils.isNotBlank(auth)) {
			log.debug("InterceptorInHeaderAuth auth: {}", auth);
			// FIXME PER PRODUZIONE 	
			setORMHelper(auth, request);
			// setORMHelperTest(request);
			return;
		    }
		} catch (Exception e) {
		    log.error("InterceptorInHeaderAuth: {}", e);
		}
		throw new BadCredentialsException("Errore nell'autenticazione");
	    }
	}
	log.debug("InterceptorInHeaderAuth OUT");
    }

    private void setORMHelperTest(HttpServletRequest request) {

	Properties connProps = externalDBResolver.getConnectionProperties("L219");
	ORMHelper.setIdcomune("L219");
	ORMHelper.setIdcomuneAlias("L219");
	ORMHelper.setSoftware("CO");
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	UsernamePasswordAuthenticationToken authToken = null;
	UserDetails user = userSecurityService.loadUserByUsername("admin");
	authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	authToken.setDetails(authenticationDetailsSource.buildDetails(request));
	SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    protected void setORMHelper(String headerauth, HttpServletRequest request) {

	log.debug("InterceptorInHeaderAuth setORMHelper decodifico");
	String decode = Base64.decode(headerauth);
	log.debug("InterceptorInHeaderAuth setORMHelper decodificato {}", decode);
	Serializer serializer = new JsonSerializer();
	log.debug("InterceptorInHeaderAuth setORMHelper deserializzo");
	Map<String, String> o = (Map<String, String>) serializer.deserialize(decode);
	log.debug("InterceptorInHeaderAuth setORMHelper deserializzato {} chiamo getTokenInfo", o);
	CheckTokenResponse r = getTokenInfo(o.get("token"));
	boolean autenticato = (r != null && r.getTokenInfo() != null && r.isValid());
	if (autenticato) {
	    log.debug("InterceptorInHeaderAuth setORMHelper token chiamato setto ORMHELPER");
	    Properties connProps = externalDBResolver.getConnectionProperties(r.getTokenInfo().getAlias());
	    ORMHelper.setToken(o.get("token"));
	    ORMHelper.setIdcomune(r.getTokenInfo().getIdcomune());
	    ORMHelper.setIdcomuneAlias(r.getTokenInfo().getAlias());
	    ORMHelper.setSoftware(o.get("software"));
	    ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	    UsernamePasswordAuthenticationToken authToken = null;
	    UserDetails user = userSecurityService.loadUserByUsername(r.getTokenInfo().getUserid());
	    authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    authToken.setDetails(authenticationDetailsSource.buildDetails(request));
	    SecurityContextHolder.getContext().setAuthentication(authToken);
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
