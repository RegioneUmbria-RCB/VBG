package it.gruppoinit.pal.gp.gestionecalendari.web.filter;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.theme.SessionThemeResolver;
import org.springframework.web.util.WebUtils;

/**
 * Filtro per l'inizializzazione dell'applicativo.<br />
 * L'inizializzazione avviene specificando nella request il parametro {@link WebConstants#IDCOMUNE_ALIAS}.<br />
 * Il parametro è memorizzato sia in sessione che in una variabile ThreadLocal.<br />
 * Oltre a questo sono settati token, idcomune, hibernate_session_key, url_first_request.<br />
 * Il metodo verifica anche la validità della sessione
 */
public class IdComuneFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IdComuneFilter.class);
    private ExternalDBResolver externalDBResolver;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest request = (HttpServletRequest) req;
	HttpServletResponse response = (HttpServletResponse) resp;
	String requestURI = request.getRequestURI();
	String qs = request.getQueryString();
	try {
	    logger.debug("START: URL:{}, QS:{}", requestURI, qs);
	    if (!isValidSession(request)) {
		if (isValidRequest(request)) {
		    createNewSession(request, response);
		} else {
		    logger.warn("END KO: request NOT VALID (URL:{}, QS:{}), forward to sessionTimeout.jsp", requestURI, qs);
		    request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
		    return;
		}
	    } else {
		setSessionToken(request);
	    }
	    setORMHelper(request);
	    logger.debug("END OK: URL:{}, QS:{}", requestURI, qs);
	    // richiesta valida chiamo il resto dei filtri
	    chain.doFilter(req, resp);
	    return;
	} catch (Exception e) {
	    logger.error("END KO: ERROR (URL:{}, QS:{}), forward to error.jsp", new Object[] { requestURI, qs, e });
	    throw new ServletException(e);
	} finally {
	    // rimuovo la variabile ThreadLocal
	    destroyORMHelper();
	}
    }

    /**
     * Recupera idcomune dall'ExternalDBResolver.<br />
     * Crea una nuova sessione http.<br />
     * Crea il cookie per tornare alla login in caso di sessione scaduta.<br />
     * Inserisce idcomune,idcomunealias,hibernate_session_key,token nella sessione.<br />
     * Chiama la funzione interna {@link IdComuneFilter#setURLFirstRequestSessionAttribute(HttpServletRequest)}
     */
    private void createNewSession(HttpServletRequest request, HttpServletResponse response) throws Exception {

	String idcomunealias = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	String token = request.getParameter(WebConstants.TOKEN);
	HttpSession session = request.getSession(true);
	try {
	    Properties p;
	    if (StringUtils.isNotBlank(token)) {
		logger.info("createNewSession: SESSION CREATED with id={} for token={}", session.getId(), token);
		p = externalDBResolver.checkToken(token);
		idcomunealias = p.getProperty(WebConstants.IDCOMUNE_ALIAS);
		p = externalDBResolver.getConnectionProperties(idcomunealias);
		session.setAttribute(WebConstants.TOKEN, token);
	    } else {
		logger.info("createNewSession: SESSION CREATED with id={} for idcomunealias={}", session.getId(), idcomunealias);
		p = externalDBResolver.getConnectionProperties(idcomunealias);
	    }
	    String idcomune = p.getProperty(WebConstants.IDCOMUNE);
	    session.setAttribute(WebConstants.IDCOMUNE, idcomune);
	    session.setAttribute(WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	    session.setAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY, ORMHelper.getHibernateKeyFromProps(p));
	    // set returnTo attribute in session
	    setURLFirstRequestSessionAttribute(request);
	    // set the name of the theme if exists
	    setThemeName(request, idcomunealias);
	    // set cookie
	    setCookie(request, response, idcomunealias);
	} catch (Exception e) {
	    logger.error("createNewSession: INVALIDATE SESSION (error populating session with id={}, idcomunealias={}, token={})", new Object[] {
		    session.getId(), idcomunealias, token, e });
	    session.invalidate();
	    throw e;
	}
    }

    private void setThemeName(HttpServletRequest request, String idcomunealias) {

	String themeName = "theme" + idcomunealias;
	InputStream is = this.getClass().getClassLoader().getResourceAsStream(themeName + ".properties");
	if (is != null) {
	    WebUtils.setSessionAttribute(request, SessionThemeResolver.THEME_SESSION_ATTRIBUTE_NAME, themeName);
	    try {
		is.close();
	    } catch (IOException e) {
	    }
	}
    }

    private void setSessionToken(HttpServletRequest request) {

	String token = request.getParameter(WebConstants.TOKEN);
	if (StringUtils.isBlank((String) request.getSession().getAttribute(WebConstants.TOKEN))) {
	    if (StringUtils.isNotBlank(token)) {
		request.getSession().setAttribute(WebConstants.TOKEN, token);
	    }
	}
    }

    private boolean isValidRequest(HttpServletRequest request) {

	String idcomunealias = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	String token = request.getParameter(WebConstants.TOKEN);
	if (StringUtils.isNotBlank(token)) {
	    logger.debug("isValidRequest: TRUE");
	    return true;
	} else {
	    if (StringUtils.isNotBlank(idcomunealias)) {
		logger.debug("isValidRequest: TRUE");
		return true;
	    }
	}
	logger.debug("isValidRequest: FALSE");
	return false;
    }

    /**
     * inserisco nella sessione l'url della prima chiamata all'applicativo
     * 
     * @param request
     */
    private void setURLFirstRequestSessionAttribute(HttpServletRequest request) {

	String urlFirstRequest = "";
	try {
	    StringBuffer requestURL = request.getRequestURL();
	    String qS = request.getQueryString();
	    if (StringUtils.isNotBlank(qS)) {
		requestURL.append("?");
		requestURL.append(qS);
	    }
	    urlFirstRequest = URLEncoder.encode(requestURL.toString(), "UTF-8");
	    logger.debug("setURLFirstRequestSessionAttribute: {}", urlFirstRequest);
	    request.getSession().setAttribute(WebConstants.URL_FIRST_REQUEST, urlFirstRequest);
	} catch (Exception e) {
	    logger.error("setURLFirstRequestSessionAttribute()", e);
	}
    }

    private void setCookie(HttpServletRequest request, HttpServletResponse response, String idcomunealias) {

	Cookie cookie = new Cookie("gestionecalendari", WebConstants.IDCOMUNE_ALIAS + "=" + idcomunealias);
	cookie.setPath(request.getContextPath());
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
	logger.debug("setCookie: name={}, value={}", cookie.getName(), cookie.getValue());
    }

    private boolean isValidSession(HttpServletRequest request) {

	HttpSession session = request.getSession();
	if (session == null) {
	    logger.debug("isValidSession: FALSE (session is null)");
	    return false;
	}
	if (StringUtils.isBlank((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS))) {
	    logger.debug("isValidSession: FALSE (session attribute idcomunealias is null)");
	    session.invalidate();
	    return false;
	}
	logger.debug("isValidSession: TRUE");
	return true;
    }

    private void setORMHelper(HttpServletRequest request) {

	logger.debug("setORMHelper");
	HttpSession session = request.getSession();
	ORMHelper.setIdcomune((String) session.getAttribute(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setHibernateSFKey((String) session.getAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY));
	ORMHelper.setToken((String) session.getAttribute(WebConstants.TOKEN));
    }

    private void destroyORMHelper() {

	logger.debug("destroyORMHelper");
	ORMHelper.destroyORMHelper();
    }

    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
    }

    public void destroy() {

    }
}
