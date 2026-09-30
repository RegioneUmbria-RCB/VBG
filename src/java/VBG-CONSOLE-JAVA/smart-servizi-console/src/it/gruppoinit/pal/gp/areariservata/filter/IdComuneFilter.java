package it.gruppoinit.pal.gp.areariservata.filter;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Prodotto;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.ProdottoService;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import org.jfree.util.Log;
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
 * Oltre a questo sono settati software, token, idcomune, hibernate_session_key, url_first_request.<br />
 * Il metodo verifica anche la validità della sessione
 */
public class IdComuneFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IdComuneFilter.class);
    private ExternalDBResolver externalDBResolver;
    private SdeproxyService sdeproxyService;
    private ProdottoService prodottoService;

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
	    HttpSession session = request.getSession();
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

	String identesdeproxy = request.getParameter(WebConstants.IDENTE_SDEPROXY);
	String idcomunealias = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	String software = request.getParameter(WebConstants.SOFTWARE);
	String token = request.getParameter(WebConstants.TOKEN);
	HttpSession session = request.getSession(true);
	try {
	    Properties p;
	    if (StringUtils.isNotBlank(token)) {
		//FIXME: questa parte di codice serve per poter entrare nell'area riservata CART partendo da quella .NET
		//questo crea un problema di sicurezza perchè un eventuale token valido può creare una sessione valida.
		logger.info("createNewSession: SESSION CREATED with id={} for token={}", session.getId(), token);
		p = externalDBResolver.checkToken(token);
		idcomunealias = p.getProperty(WebConstants.IDCOMUNE_ALIAS);
		p = externalDBResolver.getConnectionProperties(idcomunealias);
		String userId = p.getProperty(WebConstants.USER_ID);
		externalDBResolver.invalidateToken(token);
		String newToken = externalDBResolver.getUserUTEToken(idcomunealias, userId, p.getProperty(WebConstants.TOKEN_INFO_CLIENT_IP));
		session.setAttribute(WebConstants.TOKEN, newToken);
	    } else {
		logger.info("createNewSession: SESSION CREATED with id={} for idcomunealias={}", session.getId(), idcomunealias);
		p = externalDBResolver.getConnectionProperties(idcomunealias);
	    }
	    String idcomune = p.getProperty(WebConstants.IDCOMUNE);
	    session.setAttribute(WebConstants.IDENTE_SDEPROXY, identesdeproxy);
	    Sdeproxy proxy = sdeproxyService.findById(identesdeproxy);
	    if (proxy == null) {
		throw new SecurityException("Configurazione non trovata per l'idente " + identesdeproxy);
	    }
	    session.setAttribute(WebConstants.ENTE_IN_SESSION_VARIABLE_NAME, proxy.getDescrizione());
	    session.setAttribute(WebConstants.IDCOMUNEBASE_SDEPROXY, proxy.getIdcomunebase());
	    session.setAttribute(WebConstants.IDCOMUNE, idcomune);
	    session.setAttribute(WebConstants.SOFTWARE, software);
	    session.setAttribute(WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	    session.setAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY, ORMHelper.getHibernateKeyFromProps(p));
	    List<Prodotto> prodottis = prodottoService.findAll(null, null);
	    if (prodottis.isEmpty()) {
		session.setAttribute(WebConstants.PRODOTTO, WebConstants.PRODOTTO_SMART_CONSOLLE);
	    } else {
		session.setAttribute(WebConstants.PRODOTTO, prodottis.get(0).getCodice());
	    }
	    //nome del contesto di deploy recuperato dalle variabili settate nel context della web app
	    if (session.getAttribute(WebConstants.CONFIG_APP_DEPLOY_CONTEXT) == null) {
		try {
		    Context initContext = new InitialContext();
		    Context envContext = (Context) initContext.lookup("java:comp/env");
		    String deployParam = (String) envContext.lookup(WebConstants.CONFIG_APP_DEPLOY_CONTEXT);
		    session.setAttribute(WebConstants.CONFIG_APP_DEPLOY_CONTEXT, StringUtils.defaultString(deployParam));
		} catch (NamingException e) {
		    Log.warn("createNewSession -  risorna JNDI non trovata: java:comp/env/" + WebConstants.CONFIG_APP_DEPLOY_CONTEXT);
		}
	    }
	    //set returnTo attribute in session
	    setURLFirstRequestSessionAttribute(request);
	    //set the name of the theme if exists
	    setThemeName(request, idcomunealias);
	    //set cookie
	    setCookie(request, response, idcomunealias, software, identesdeproxy);
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
		Properties p = externalDBResolver.checkToken(token);
		String userId = p.getProperty(WebConstants.USER_ID);
		externalDBResolver.invalidateToken(token);
		String idcomunealias = (String) request.getSession().getAttribute(WebConstants.IDCOMUNE_ALIAS);
		String newToken = externalDBResolver.getUserUTEToken(idcomunealias, userId, p.getProperty(WebConstants.TOKEN_INFO_CLIENT_IP));
		request.getSession().setAttribute(WebConstants.TOKEN, newToken);
		//		// request.getSession().setAttribute(WebConstants.TOKEN, token);
		//		throw new SecurityException("Token " + token + " non valido!!!");
	    }
	}
    }

    private boolean isValidRequest(HttpServletRequest request) {

	String idcomunealias = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	String identesdeproxy = request.getParameter(WebConstants.IDENTE_SDEPROXY);
	String software = request.getParameter(WebConstants.SOFTWARE);
	String token = request.getParameter(WebConstants.TOKEN);
	if (StringUtils.isNotBlank(token) && StringUtils.isNotBlank(software) && StringUtils.isNotBlank(identesdeproxy)) {
	    logger.debug("isValidRequest: TRUE");
	    return true;
	} else {
	    if (StringUtils.isNotBlank(idcomunealias) && StringUtils.isNotBlank(software) && StringUtils.isNotBlank(identesdeproxy)) {
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

    private void setCookie(HttpServletRequest request, HttpServletResponse response, String idcomunealias, String software, String identesdeproxy) {

	Cookie cookie = new Cookie("areariservata", WebConstants.IDCOMUNE_ALIAS + "=" + idcomunealias + "&" + WebConstants.SOFTWARE + "=" + software
		+ "&" + WebConstants.IDENTE_SDEPROXY + "=" + identesdeproxy);
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
	if (StringUtils.isBlank((String) session.getAttribute(WebConstants.IDENTE_SDEPROXY))) {
	    logger.debug("isValidSession: FALSE (session attribute idcomunealias is null)");
	    session.invalidate();
	    return false;
	} else {
	    String idente = request.getParameter(WebConstants.IDENTE_SDEPROXY);
	    if (StringUtils.isNotBlank(idente)) {
		if (!idente.equalsIgnoreCase((String) session.getAttribute(WebConstants.IDENTE_SDEPROXY))) {
		    session.invalidate();
		    return false;
		}
	    }
	}
	if (StringUtils.isBlank((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS))) {
	    logger.debug("isValidSession: FALSE (session attribute idcomunealias is null)");
	    session.invalidate();
	    return false;
	} else {
	    String idcomune = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	    if (StringUtils.isNotBlank(idcomune)) {
		if (!idcomune.equalsIgnoreCase((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS))) {
		    session.invalidate();
		    return false;
		}
	    }
	}
	if (StringUtils.isBlank((String) session.getAttribute(WebConstants.SOFTWARE))) {
	    logger.debug("isValidSession: FALSE (session attribute software is null)");
	    session.invalidate();
	    return false;
	}
	logger.debug("isValidSession: TRUE");
	return true;
    }

    private void setORMHelper(HttpServletRequest request) {

	logger.debug("setORMHelper");
	HttpSession session = request.getSession();
	ORMHelper.setIdente((String) session.getAttribute(WebConstants.IDENTE_SDEPROXY));
	ORMHelper.setIdcomunebase(((String) session.getAttribute(WebConstants.IDCOMUNEBASE_SDEPROXY)));
	ORMHelper.setIdcomune((String) session.getAttribute(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware((String) session.getAttribute(WebConstants.SOFTWARE));
	ORMHelper.setHibernateSFKey((String) session.getAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY));
	ORMHelper.setToken((String) session.getAttribute(WebConstants.TOKEN));
	//thread local per il CART
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
    }

    private void destroyORMHelper() {

	logger.debug("destroyORMHelper");
	ORMHelper.destroyORMHelper();
	//thread local per il CART
	CartServiceConfigurationHelper.setAliasEnte(null);
	CartServiceConfigurationHelper.setCurrentSoftware(null);
	CartServiceConfigurationHelper.setIdEnte(null);
    }

    /**
     * recupera dall'application context il bean externalDBResolver
     */
    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
	sdeproxyService = (SdeproxyService) wac.getBean("sdeproxyServiceImpl");
	prodottoService = (ProdottoService) wac.getBean("prodottoServiceImpl");
    }

    public void destroy() {

    }
}
