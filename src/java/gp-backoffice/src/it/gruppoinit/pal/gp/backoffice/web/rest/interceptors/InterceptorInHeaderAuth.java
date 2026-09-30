package it.gruppoinit.pal.gp.backoffice.web.rest.interceptors;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

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
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	} catch (IOException e) {
	    log.error("InterceptorInHeaderAuth#handleFault: {}", e);
	}
    }

    @Override
    public void handleMessage(Message message) throws Fault {

	log.debug("InterceptorInHeaderAuth IN");
	HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	log.debug("request {}", request);
	if (request != null) {
	    log.debug("request !=null {}, {}", request, request.getMethod());
	    if (log.isDebugEnabled()) {
		log.debug("getHeaderMap(request)={}", getHeaderMap(request));
	    }
	    if (!request.getMethod().equals("OPTIONS")) {
		String auth = request.getHeader(header);
		try {
		    if (StringUtils.isNotBlank(auth)) {
			log.debug("InterceptorInHeaderAuth auth: {}", auth);
			// FIXME PER PRODUZIONE 	
			setORMHelper(auth, request);
			//setORMHelperTest(request, "E256", "E256", "SS");
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

    private void setORMHelperTest(HttpServletRequest request, String alias, String idcomune, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(idcomune);
	ORMHelper.setIdcomuneAlias(alias);
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	UsernamePasswordAuthenticationToken authToken = null;
	UserDetails user = userSecurityService.loadUserByUsername("admin");
	authToken = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	authToken.setDetails(authenticationDetailsSource.buildDetails(request));
	SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    protected void setORMHelper(String headerauth, HttpServletRequest request) {

	log.debug("InterceptorInHeaderAuth setORMHelper decodifico");
	String decode = new String(Base64.decodeBase64(headerauth));
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

    private Map<String, String> getHeaderMap(HttpServletRequest request) {

	Map<String, String> headers = new HashMap<String, String>();
	Enumeration<String> headerNames = request.getHeaderNames();
	while (headerNames != null && headerNames.hasMoreElements()) {
	    String key = headerNames.nextElement();
	    Enumeration<String> headerValues = request.getHeaders(key);
	    StringBuilder value = new StringBuilder();
	    if (headerValues != null && headerValues.hasMoreElements()) {
		value.append(headerValues.nextElement());
		// If there are multiple values for the header, do comma-separated concat
		// as per RFC 2616:
		// https://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2
		while (headerValues.hasMoreElements()) {
		    value.append(",").append(headerValues.nextElement());
		}
	    }
	    headers.put(key, value.toString());
	}
	return headers;
    }
}
