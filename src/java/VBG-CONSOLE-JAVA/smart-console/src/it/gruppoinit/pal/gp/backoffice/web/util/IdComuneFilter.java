package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	logger.debug("doFilter(): request URI: {}", httpServletRequest.getRequestURI());
	HttpSession session = null;
	String idcomunealiasInRequest = getIdcomunealiasFromRequest(httpServletRequest);
	if (StringUtils.isBlank(idcomunealiasInRequest)) {
	    // idcomunealias NON è presente nella request, controllo se è la prima request
	    session = httpServletRequest.getSession(false);
	    if (session == null) {
		// prima chiamata all'applicativo senza idcomunealias, lancio errore
		logger.warn("doFilter(): idcomunealias not found");
		request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
		return;
	    }
	} else {
	    // idcomunealias è presente nella request, controllo se è presente nella session
	    session = httpServletRequest.getSession(false);
	    if (session == null) {
		// prima chiamata all'applicativo, memorizzo idcomunealias
		session = createAndPopulateNewSession(httpServletRequest, httpServletResponse, idcomunealiasInRequest);
	    }
	}
	String idcomunealiasInSession = (String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS);
	if (StringUtils.isBlank(idcomunealiasInSession)) {
	    // session expired!
	    logger.warn("doFilter(): session expired");
	    request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
	    return;
	} else {
	    //confronto l'idcomune della request con quello della session per bloccare eventuali tentativi di
	    //aprire connessioni ad altri comuni da tab diversi dello stesso browser
	    if (StringUtils.isNotBlank(idcomunealiasInRequest)) {
		if (!idcomunealiasInRequest.equals(idcomunealiasInSession)) {
		    logger.warn("doFilter(): idcomunealias [on request={}], [on session={}] not permitted, throw exception!", idcomunealiasInRequest,
			    idcomunealiasInSession);
		    throw new RuntimeException("Attenzione! chiudi e riapri il browser per accedere con un idcomunealias differente.");
		}
	    }
	}
	try {
	    logger.debug("doFilter(): idcomunealias in session: {}. Setting ORMHelper...", session.getAttribute(WebConstants.IDCOMUNE_ALIAS));
	    // gestisco la variabile software
	    manageSoftware(httpServletRequest, session);
	    ORMHelper.setIdcomune((String) session.getAttribute(WebConstants.IDCOMUNE));
	    ORMHelper.setIdcomuneAlias((String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS));
	    ORMHelper.setHibernateSFKey((String) session.getAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY));
	    ORMHelper.setToken((String) session.getAttribute(WebConstants.TOKEN));
	    ORMHelper.setIdente((String) session.getAttribute(WebConstants.IDENTE_SDEPROXY));
	    ORMHelper.setIdcomunebase((String) session.getAttribute(WebConstants.IDCOMUNEBASE_SDEPROXY));
	    // chiamo il resto dei filtri
	    if (ORMHelper.getIdcomune().equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
		session.setAttribute(WebConstants.CONSOLLE_REGIONALE, Boolean.TRUE);
	    } else {
		session.setAttribute(WebConstants.CONSOLLE_REGIONALE, Boolean.FALSE);
	    }
	    chain.doFilter(request, response);
	    return;
	} finally {
	    // rimuovo la variabile ThreadLocal
	    ORMHelper.destroyORMHelper();
	    SigeproBusinessRules.buildDefaultRules();
	}
    }

    /**
     * recupera idcomunealias dalla request cercandolo prima come parametro e poi come attributo
     * 
     * @param request
     * @return idcomunealias o null
     */
    private String getIdcomunealiasFromRequest(HttpServletRequest request) {

	String idcomunealias = request.getParameter(WebConstants.IDCOMUNE_ALIAS);
	logger.debug("getIdcomunealiasFromRequest(): {} paramenter in request: {}", WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	if (StringUtils.isBlank(idcomunealias)) {
	    idcomunealias = (String) request.getAttribute(WebConstants.IDCOMUNE_ALIAS);
	    logger.debug("getIdcomunealiasFromRequest(): {} attribute in request: {}", WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	}
	return idcomunealias;
    }

    /**
     * Recupera idcomune dall'ExternalDBResolver.<br />
     * Crea una nuova sessione http.<br />
     * Crea il cookie per tornare alla login in caso di sessione scaduta.<br />
     * Inserisce idcomune,idcomunealias,hibernate_session_key,token nella sessione.<br />
     * Chiama la funzione interna {@link IdComuneFilter#setReturnToSessionAttribute(HttpServletRequest)}
     */
    private HttpSession createAndPopulateNewSession(HttpServletRequest request, HttpServletResponse response, String idcomunealias) {

	logger.debug("createAndPopulateNewSession(): get connection props for [{}]", idcomunealias);
	Properties p = externalDBResolver.getConnectionProperties(idcomunealias);
	String idcomune = p.getProperty(WebConstants.IDCOMUNE);
	Cookie cookie = new Cookie(WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	cookie.setPath(request.getContextPath());
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
	logger.debug("createAndPopulateNewSession(): set cookie with {}={}", WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	HttpSession session = request.getSession(true);
	String identesdeproxy = request.getParameter(WebConstants.IDENTE_SDEPROXY);
	String idcomunebase = "";
	if (StringUtils.isBlank(identesdeproxy)) {
	    if (StringUtils.isNotBlank(ORMHelper.getToken()) || StringUtils.isNotBlank((String) request.getParameter(WebConstants.TOKEN))) {
		ORMHelper.setIdcomuneAlias(idcomunealias);
		Sdeproxy idente = sdeproxyService.findByAlias(idcomunealias);
		if (idente == null) {
		    throw new SecurityException("Configurazione sdeproxy non trovata per l'alias " + idcomunealias);
		}
		if (idente != null) {
		    identesdeproxy = idente.getIdente();
		    idcomunebase = idente.getIdcomunebase();
		}
	    }
	} else {
	    Sdeproxy idente = sdeproxyService.findById(identesdeproxy);
	    if (idente == null) {
		throw new SecurityException("Configurazione non trovata per l'idente " + identesdeproxy);
	    }
	    identesdeproxy = idente.getIdente();
	    idcomunebase = idente.getIdcomunebase();
	}
	session.setAttribute(WebConstants.IDCOMUNEBASE_SDEPROXY, idcomunebase);
	session.setAttribute(WebConstants.IDENTE_SDEPROXY, identesdeproxy);
	Cookie cookieIdente = new Cookie(WebConstants.IDENTE_SDEPROXY, identesdeproxy);
	cookieIdente.setPath(request.getContextPath());
	cookieIdente.setMaxAge(-1);
	response.addCookie(cookieIdente);
	logger.debug("createAndPopulateNewSession(): start new session with jsessionid: {}", session.getId());
	request.getSession().setAttribute(WebConstants.IDCOMUNE, idcomune);
	logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.IDCOMUNE, idcomune);
	request.getSession().setAttribute(WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	request.getSession().setAttribute(WebConstants.HIBERNATE_SESSION_FACTORY_KEY, ORMHelper.getHibernateKeyFromProps(p));
	logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.HIBERNATE_SESSION_FACTORY_KEY,
		ORMHelper.getHibernateKeyFromProps(p));
	if (StringUtils.isNotBlank(request.getParameter(WebConstants.TOKEN))) {
	    request.getSession().setAttribute(WebConstants.TOKEN, request.getParameter(WebConstants.TOKEN));
	    logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.TOKEN, request.getParameter(WebConstants.TOKEN));
	}
	setReturnToSessionAttribute(request);
	return session;
    }

    /**
     * inserisco nella sessione l'url della prima chiamata all'applicativo
     * 
     * @param request
     */
    private void setReturnToSessionAttribute(HttpServletRequest request) {

	StringBuffer currentUrlBuffer = request.getRequestURL();
	String qS = request.getQueryString();
	String urlFirstRequest = "";
	String xForwardedProtocolHeader = StringUtils.defaultString(request.getHeader("X-Forwarded-Protocol")).trim().toLowerCase();
	if (StringUtils.isNotBlank(qS)) {
	    currentUrlBuffer.append("?");
	    currentUrlBuffer.append(qS);
	}
	try {
	    String reqUrl = currentUrlBuffer.toString();
	    if (StringUtils.isNotBlank(xForwardedProtocolHeader)) {
		if (logger.isDebugEnabled()) {
		    logger.debug("setReturnToSessionAttribute# passato header X-Forwarded-Protocol: {}", xForwardedProtocolHeader);
		    logger.debug("setReturnToSessionAttribute# older request url {}", reqUrl);
		}
		String scheme = reqUrl.substring(0, reqUrl.indexOf(':'));
		if (!xForwardedProtocolHeader.equalsIgnoreCase(scheme)) {
		    reqUrl = xForwardedProtocolHeader + reqUrl.substring(reqUrl.indexOf(':'));
		}
		if (logger.isDebugEnabled()) {
		    logger.debug("setReturnToSessionAttribute# newer request url {}", reqUrl);
		}
	    }
	    urlFirstRequest = URLEncoder.encode(reqUrl, "UTF-8");
	    request.getSession().setAttribute(WebConstants.URL_FIRST_REQUEST, urlFirstRequest);
	} catch (UnsupportedEncodingException e) {
	    logger.error("setReturnToSessionAttribute(): {}", e.getMessage());
	}
    }

    /**
     * metodo per la gestione del parametro software.<br />
     * se il parametro è presente nella request lo inserisce nell'ORMHelper e nella session.<br />
     * altrimenti lo ricerca nella session e se presente lo inserisce nell'ORMHelper.<br />
     * se non trovato allora inserisce nell'ORMHelper e nella session il valore di default (TT)
     * 
     * @param request
     * @param session
     */
    private void manageSoftware(HttpServletRequest request, HttpSession session) {

	logger.debug("manageSoftware(): entering...");
	// cerco il software nella request
	String softwareInRequest = request.getParameter(WebConstants.SOFTWARE);
	if (StringUtils.isNotBlank(softwareInRequest)) {
	    logger.debug("manageSoftware(): found software in request: {}", softwareInRequest);
	    ORMHelper.setSoftware(softwareInRequest);
	    session.setAttribute(WebConstants.SOFTWARE, softwareInRequest);
	    return;
	}
	// cerco il software nella session
	String softwareInSession = (String) session.getAttribute(WebConstants.SOFTWARE);
	if (StringUtils.isNotBlank(softwareInSession)) {
	    logger.debug("manageSoftware(): found software in session: {}", softwareInSession);
	    ORMHelper.setSoftware(softwareInSession);
	    return;
	}
	// software non trovato setto a TT
	logger.warn("manageSoftware(): software not found. set software to {}", WebConstants.SOFTWARE_TT);
	ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	session.setAttribute(WebConstants.SOFTWARE, WebConstants.SOFTWARE_TT);
    }

    /**
     * recupera dall'application context il bean externalDBResolver
     */
    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
	sdeproxyService = (SdeproxyService) wac.getBean("sdeproxyServiceImpl");
    }

    public void destroy() {

    }
}
