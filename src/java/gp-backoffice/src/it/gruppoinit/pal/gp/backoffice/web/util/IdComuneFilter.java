package it.gruppoinit.pal.gp.backoffice.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.JdkVersion;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

/**
 * Filtro per l'inizializzazione dell'applicativo.<br />
 * L'inizializzazione avviene specificando nella request il parametro {@link WebConstants#IDCOMUNE_ALIAS}.<br />
 * Il parametro è memorizzato sia in sessione che in una variabile ThreadLocal.<br />
 * Oltre a questo sono settati software, token, idcomune, hibernate_session_key, url_first_request.<br />
 * Il metodo verifica anche la validità della sessione
 */
public class IdComuneFilter implements Filter {

    private static final String _OVERRIDE_PRODUCT_STYLE = "_OVERRIDE_PRODUCT_STYLE_";
    public static final String _MOSTRA_ERRORI_DETTAGLIATI = "_MOSTRA_ERRORI_DETTAGLIATI";
    private static final Logger logger = LoggerFactory.getLogger(IdComuneFilter.class);
    private ExternalDBResolver externalDBResolver;

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
	    if (StringUtils.isNotBlank(idcomunealiasInRequest) && !idcomunealiasInRequest.equals(idcomunealiasInSession)) {
		logger.warn("doFilter(): idcomunealias [on request={}], [on session={}] not permitted, throw exception!", idcomunealiasInRequest,
			idcomunealiasInSession);
		throw new RuntimeException("Attenzione! chiudi e riapri il browser per accedere con un idcomunealias differente.");
	    }
	    if (StringUtils.isNotBlank(idcomunealiasInRequest)) {
		setReturnToSessionAttribute(httpServletRequest);
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
	    ORMHelper.setProductName((String) session.getAttribute(WebConstants.SecurityParams.PRODUCT_NAME.name()));
	    ORMHelper.setProductStyle((String) session.getAttribute(WebConstants.SecurityParams.PRODUCT_STYLE.name()));
	    erroriDettagliati(httpServletRequest, session);
	    // chiamo il resto dei filtri
	    chain.doFilter(request, response);
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
	if (JdkVersion.getMajorJavaVersion() == JdkVersion.JAVA_16) {
	    cookie.setPath(";Path=" + request.getContextPath() + ";HttpOnly;");
	} else {
	    cookie.setPath(request.getContextPath());
	}
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
	logger.debug("createAndPopulateNewSession(): set cookie with {}={}", WebConstants.IDCOMUNE_ALIAS, idcomunealias);
	HttpSession session = request.getSession(true);
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
	String productName = StringUtils.defaultIfEmpty(WebConstants.getSecurityParamValue(WebConstants.SecurityParams.PRODUCT_NAME), "");
	if (StringUtils.isNotBlank(productName)) {
	    request.getSession().setAttribute(WebConstants.SecurityParams.PRODUCT_NAME.name(), productName);
	    logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.SecurityParams.PRODUCT_NAME.name(), productName);
	}
	// deve essere richiamato dopo il settaggio della session idcomunealias
	String productStyle = getProductStyle(request.getSession());
	if (StringUtils.isNotBlank(productStyle)) {
	    request.getSession().setAttribute(WebConstants.SecurityParams.PRODUCT_STYLE.name(), productStyle);
	    logger.debug("createAndPopulateNewSession(): set session attribute {}={}", WebConstants.SecurityParams.PRODUCT_STYLE.name(),
		    productStyle);
	}
	setReturnToSessionAttribute(request);
	return session;
    }

    private String getProductStyle(HttpSession session) {

	String pstyle = StringUtils.defaultIfEmpty(WebConstants.getSecurityParamValue(WebConstants.SecurityParams.PRODUCT_STYLE),
		WebConstants.PRODUCT_STYLE_LEGACY);
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:comp/env");
	    String vStyle = (String) envContext.lookup(WebConstants.SecurityParams.PRODUCT_STYLE.name());
	    if (StringUtils.isNotBlank(vStyle)) {
		String[] split = vStyle.split("\\|");
		String alias_corrente = (String) session.getAttribute(WebConstants.IDCOMUNE_ALIAS);
		String[] alias = split[1].split("-");
		boolean trovato = false;
		for (String a : alias) {
		    if (StringUtils.isNotBlank(a)) {
			if (alias_corrente.equals(a)) {
			    trovato = true;
			    break;
			}
		    }
		}
		if (trovato) {
		    pstyle = split[0];
		}
	    }
	} catch (NamingException e) {
	}
	return pstyle;
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
	String xForwardedProtocolHeader = StringUtils.defaultString(request.getHeader("X-Forwarded-Proto")).trim().toLowerCase();
	if (StringUtils.isNotBlank(qS)) {
	    currentUrlBuffer.append("?");
	    currentUrlBuffer.append(qS);
	}
	try {
	    String reqUrl = currentUrlBuffer.toString();
	    if (StringUtils.isNotBlank(xForwardedProtocolHeader)) {
		if (logger.isDebugEnabled()) {
		    logger.debug("setReturnToSessionAttribute# passato header X-Forwarded-Proto: {}", xForwardedProtocolHeader);
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
	    logger.debug("setReturnToSessionAttribute(): {}", urlFirstRequest);
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
    }

    public void destroy() {

    }

    private void erroriDettagliati(HttpServletRequest request, HttpSession session) {

	String errori = request.getParameter(_MOSTRA_ERRORI_DETTAGLIATI);
	if (StringUtils.isNotBlank(errori)) {
	    session.setAttribute(_MOSTRA_ERRORI_DETTAGLIATI, "true");
	}
    }
}
