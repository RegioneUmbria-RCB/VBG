package it.gruppoinit.pal.gp.areariservata.web.filter;

import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet Filter implementation class ServiziResolverFilter
 */
public class ServiziResolverFilter implements Filter {

    public static Logger logger = LoggerFactory.getLogger(ServiziResolverFilter.class);
    private ExternalDBResolver externalDBResolver;
    private SdeproxyService sdeproxyService;
    private DeployProperties deployProperties;
    public static String SESSION_VAR_ACCESSO_SERVIZI_RESOLVER = "servizi.accedi-se-attiva-session";
    private boolean servizi_accedi_se_attiva_session = false;
    private boolean manutenzione = false;

    public static enum ServiziEnum {
	NUOVADOMANDA, INCOMPILAZIONE, LEMIEPRATICHE, COMUNICA
    }

    /**
     * Default constructor.
     */
    public ServiziResolverFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {

    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse res = (HttpServletResponse) response;
	// FRED WAS HERE
	String meeting = request.getParameter("meeting");
	if (meeting != null) {
	    String fred = "Fred dice: me ne vado ";
	    if (StringUtils.defaultString(meeting).equalsIgnoreCase("noia")) {
		fred += "a casa!";
	    } else {
		fred += "al bar!";
	    }
	    req.getSession().setAttribute("_WHERE_IS_FRED_", fred);
	}
	// FRED WAS HERE (END)
	String path = req.getServletPath();
	if (manutenzione) {
	    if (StringUtils.defaultString(path).indexOf("public_json") < 0) {
		goToManutenzione(req, res);
	    }
	}
	String redirTo = "";
	String appendQs = "?";
	String alias = "";
	if (matchServizio(ServiziEnum.NUOVADOMANDA, path)) {
	    String idente = getIdEnte(ServiziEnum.NUOVADOMANDA, path);
	    alias = recuperoAliasCorrettodaIdente(idente);
	    String codiceAttivita = StringUtils.defaultString(getCodiceAttivita(path));
	    String tipoAvvio = StringUtils.defaultString(getTipoavvio(path));
	    redirTo = "cart/start.htm?idcomunealias=" + alias + "&software=SS&codiceAttivita=" + codiceAttivita + "&idente=" + idente;
	    if (StringUtils.isNotBlank(tipoAvvio)) {
		redirTo = redirTo + "&tipoAvvio=" + tipoAvvio;
	    }
	    appendQs = "&";
	    if (servizi_accedi_se_attiva_session) {
		req.getSession().setAttribute(SESSION_VAR_ACCESSO_SERVIZI_RESOLVER, ServiziEnum.NUOVADOMANDA);
	    }
	} else if (matchServizio(ServiziEnum.INCOMPILAZIONE, path)) {
	    checkAccessoComunica(req);
	    String idente = getIdEnte(ServiziEnum.INCOMPILAZIONE, path);
	    alias = recuperoAliasCorrettodaIdente(idente);
	    redirTo = "fodomande/list.htm?idcomunealias=" + alias + "&software=SS&idente=" + idente;
	    appendQs = "&";
	    if (servizi_accedi_se_attiva_session) {
		req.getSession().setAttribute(SESSION_VAR_ACCESSO_SERVIZI_RESOLVER, ServiziEnum.INCOMPILAZIONE);
	    }
	} else if (matchServizio(ServiziEnum.LEMIEPRATICHE, path)) {
	    checkAccessoComunica(req);
	    String idente = getIdEnte(ServiziEnum.LEMIEPRATICHE, path);
	    alias = recuperoAliasCorrettodaIdente(idente);
	    redirTo = "istanze/goToList.htm?idcomunealias=" + alias + "&software=SS&idente=" + idente;
	    appendQs = "&";
	    if (servizi_accedi_se_attiva_session) {
		req.getSession().setAttribute(SESSION_VAR_ACCESSO_SERVIZI_RESOLVER, ServiziEnum.LEMIEPRATICHE);
	    }
	} else if (matchServizio(ServiziEnum.COMUNICA, path)) {
	    String idente = getIdEnte(ServiziEnum.COMUNICA, path);
	    String cfUtenteGuest = deployProperties.getServiziUserid();
	    if (StringUtils.isBlank(cfUtenteGuest)) {
		logger.error("Non è stato configurato l'utente per i servizi anonimi su deploy.properties ");
		throw new InvalidConfigurationException("Non è stato configurato l'utente per i servizi anonimi");
	    }
	    alias = recuperoAliasCorrettodaIdente(idente);
	    redirTo = "comunica/init.htm?idcomunealias=" + alias + "&software=SS&idente=" + idente;
	    appendQs = "&";
	    if (servizi_accedi_se_attiva_session) {
		req.getSession().setAttribute(SESSION_VAR_ACCESSO_SERVIZI_RESOLVER, ServiziEnum.COMUNICA);
	    }
	}
	if (StringUtils.isNotBlank(redirTo)) {
	    setCookieInizializzazione(req, res, alias);
	    redirTo = req.getSession().getServletContext().getContextPath() + "/" + redirTo;
	    String queryString = req.getQueryString();
	    if (StringUtils.isNotBlank(queryString)) {
		redirTo = redirTo + appendQs + queryString;
	    }
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    httpResponse.sendRedirect(redirTo);
	} else {
	    chain.doFilter(request, response);
	}
    }

    private void goToManutenzione(HttpServletRequest request, HttpServletResponse response) throws IOException {

	request.getSession().setAttribute("_messaggio_manutenzione_settato_", Boolean.TRUE);
	String messaggioManutenzione = "Attenzione! Il servizio è in fase di manutenzione e sarà ripristinato nel più breve tempo possibile.";
	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResource(WebConstants.CONFIG_FILES_FOLDER + "manutenzione.txt").openStream();
	    messaggioManutenzione = IOUtils.toString(in, "UTF-8");
	} catch (Exception e) {
	    // logger.error("Errore durante il caricamento del file {}: {}", WebConstants.CONFIG_FILES_FOLDER + WebConstants.DB_PROPS, e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento della configurazione del database: " + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
	response.setContentType("text/html; charset=utf-8");
	response.getOutputStream().write(messaggioManutenzione.getBytes("UTF-8"));
	response.getOutputStream().close();
    }

    private void setCookieInizializzazione(HttpServletRequest request, HttpServletResponse response, String alias) {

	String returnTo = (String) request.getParameter(WebConstants.RETURNTO);
	if (StringUtils.isBlank(returnTo)) {
	    returnTo = ((HttpServletRequest) request).getHeader("referer");
	    if (StringUtils.isNotBlank(returnTo)) {
		Cookie cookie2 = new Cookie("app_chiamante_" + alias, returnTo);
		cookie2.setPath(request.getContextPath());
		cookie2.setMaxAge(-1);//The MaxAge of -1 signals that you want the cookie to persist for the duration of the session. You want to set MaxAge to 0 instead.
		response.addCookie(cookie2);
	    }
	} else {
	    Cookie cookie2 = new Cookie("app_chiamante_" + alias, returnTo);
	    cookie2.setPath(request.getContextPath());
	    cookie2.setMaxAge(-1);//The MaxAge of -1 signals that you want the cookie to persist for the duration of the session. You want to set MaxAge to 0 instead.
	    response.addCookie(cookie2);
	}
	String nascondiBottoniCrossSuPratiche = (String) request.getParameter("miepratichecustom");
	if (StringUtils.isNotBlank(nascondiBottoniCrossSuPratiche)) {
	    Cookie cookie2 = new Cookie("bottonicross_" + alias, "true");
	    cookie2.setPath(request.getContextPath());
	    cookie2.setMaxAge(-1);//The MaxAge of -1 signals that you want the cookie to persist for the duration of the session. You want to set MaxAge to 0 instead.
	    response.addCookie(cookie2);
	}
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
	sdeproxyService = (SdeproxyService) wac.getBean("sdeproxyServiceImpl");
	deployProperties = (DeployProperties) wac.getBean("deployPropertiesBean");
	String manutenzione = filterConfig.getInitParameter("manutenzione");
	if (StringUtils.defaultString(manutenzione).equalsIgnoreCase("true")) {
	    this.manutenzione = true;
	}
	String _servizi_accedi_se_attiva_session = deployProperties.getServiziAccediSeAttivaSession();
	if (StringUtils.defaultString(_servizi_accedi_se_attiva_session).equalsIgnoreCase("true")) {
	    servizi_accedi_se_attiva_session = true;
	}
    }

    private boolean matchServizio(ServiziEnum SERVIZIO, String path) {

	switch (SERVIZIO) {
	case NUOVADOMANDA:
	    return getPatternNuovaDomanda().matcher(path.toLowerCase()).matches();
	case INCOMPILAZIONE:
	    return getPatternInCompilazione().matcher(path.toLowerCase()).matches();
	case LEMIEPRATICHE:
	    return getPatternLeMiePratiche().matcher(path.toLowerCase()).matches();
	case COMUNICA:
	    return getPatternComunica().matcher(path.toLowerCase()).matches();
	default:
	    break;
	}
	return true;
    }

    private String getIdEnte(ServiziEnum SERVIZIO, String path) {

	Matcher m = null;
	switch (SERVIZIO) {
	case NUOVADOMANDA:
	    m = getPatternNuovaDomanda().matcher(path.toLowerCase());
	    break;
	case INCOMPILAZIONE:
	    m = getPatternInCompilazione().matcher(path.toLowerCase());
	    break;
	case LEMIEPRATICHE:
	    m = getPatternLeMiePratiche().matcher(path.toLowerCase());
	    break;
	case COMUNICA:
	    m = getPatternComunica().matcher(path.toLowerCase());
	    break;
	default:
	    break;
	}
	while (m.find()) {
	    return m.group(1).toUpperCase();
	}
	throw new RuntimeException("path non valido: " + path);
    }

    private String recuperoAliasCorrettodaIdente(String idEnte) {

	String defaultAlias = deployProperties.getIdcomuneAliasDefault();
	String tokenApplicativo = externalDBResolver.getToken(defaultAlias);
	ORMHelper.setToken(tokenApplicativo);
	ORMHelper.setIdcomuneAlias(defaultAlias);
	Sdeproxy p = sdeproxyService.findById(idEnte.toUpperCase());
	ORMHelper.setToken(null);
	ORMHelper.setIdcomuneAlias(null);
	if (p != null) {
	    return p.getAliasEnte();
	}
	logger.error("Ente non configurato {}", idEnte);
	throw new RuntimeException("Ente non configurato " + idEnte);
    }

    private static String getCodiceAttivita(String path) {

	String[] pack = path.split("\\/");
	if (pack.length > 3) {
	    return pack[3];
	}
	return null;
    }

    private static String getTipoavvio(String path) {

	String[] pack = path.split("\\/");
	if (pack.length > 4) {
	    return pack[4];
	}
	return null;
    }

    /**
     * se impostata a true la variabile di sessione WebConstants.ACCESSO_SERVIZIO_COMUNICA allora rilancia eccezione.
     * Serve per controllare che l'utente (ANONIMO PER I SERVIZI COMUNICA) non acceda ai servizi INCOMPILAZIONE,
     * LEMIEPRATICHE
     * 
     * @param req
     */
    private void checkAccessoComunica(HttpServletRequest req) {

	Boolean accessoComunica = (Boolean) req.getSession().getAttribute(WebConstants.ACCESSO_SERVIZIO_COMUNICA);
	if (BooleanUtils.isTrue(accessoComunica)) {
	    throw new SecurityException("Accesso alla funzionalità non consentito");
	}
    }

    private Pattern getPatternServizio() {

	if (this.PATTERN_SERVIZIO_ == null) {
	    String pattern = "[/]([a-z][0-9]{3}|c[a-z][0-9]{3}|[0-9]{6}|ttr[a-z]{0,5}|cons[a-z]{0,8}|cns[a-z]{0,8}#PATTERN_AGGIUNTIVI#)([/]{0,1})";
	    String patternAggiuntivi = deployProperties.getServiziResolverFilterPathAmmessi();
	    if (StringUtils.isBlank(patternAggiuntivi)) {
		patternAggiuntivi = "";
	    }
	    pattern = pattern.replace("#PATTERN_AGGIUNTIVI#", patternAggiuntivi).toLowerCase();
	    PATTERN_SERVIZIO_ = Pattern.compile(pattern);
	}
	return PATTERN_SERVIZIO_;
    }

    private Pattern PATTERN_SERVIZIO_ = null;

    private Pattern getPatternNuovaDomanda() {

	if (this.PATTERN_SERVIZIO_NUOVADOMANDA_ == null) {
	    this.PATTERN_SERVIZIO_NUOVADOMANDA_ = Pattern.compile(getPatternServizio() + "nuovadomanda([/]{0,1})(.*){0,1}");
	}
	return this.PATTERN_SERVIZIO_NUOVADOMANDA_;
    }

    private Pattern PATTERN_SERVIZIO_NUOVADOMANDA_ = null;

    private Pattern getPatternInCompilazione() {

	if (this.PATTERN_SERVIZIO_INCOMPILAZIONE_ == null) {
	    this.PATTERN_SERVIZIO_INCOMPILAZIONE_ = Pattern.compile(getPatternServizio() + "incompilazione([/]{0,1})");
	}
	return this.PATTERN_SERVIZIO_INCOMPILAZIONE_;
    }

    private Pattern PATTERN_SERVIZIO_INCOMPILAZIONE_ = null;

    private Pattern getPatternLeMiePratiche() {

	if (this.PATTERN_SERVIZIO_LEMIEPRATICHE_ == null) {
	    this.PATTERN_SERVIZIO_LEMIEPRATICHE_ = Pattern.compile(getPatternServizio() + "lemiepratiche([/]{0,1})");
	}
	return this.PATTERN_SERVIZIO_LEMIEPRATICHE_;
    }

    private Pattern PATTERN_SERVIZIO_LEMIEPRATICHE_ = null;

    private Pattern getPatternComunica() {

	if (this.PATTERN_SERVIZIO_COMUNICA_ == null) {
	    this.PATTERN_SERVIZIO_COMUNICA_ = Pattern.compile(getPatternServizio() + "comunica([/]{0,1})");
	}
	return this.PATTERN_SERVIZIO_COMUNICA_;
    }

    private Pattern PATTERN_SERVIZIO_COMUNICA_ = null;
}
