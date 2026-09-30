package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.exception.SessionTimeoutException;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Filtro utilizzato per intercettare il parametro token dalla request e da questo ricavare il valore della variabile
 * idcomunealias tramite ExternalDBResolver.<br />
 * Il valore recuperato è inserito nella request per essere utilizzato da IdComuneFilter
 */
public class TokenFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
    private ExternalDBResolver externalDBResolver;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	String reqToken = "";
	String sesToken = "";
	String token = "";
	boolean disableCheckToken = false;
	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	log.debug("doFilter(): Request URI: {}", httpServletRequest.getRequestURI());
	reqToken = httpServletRequest.getParameter(WebConstants.TOKEN);
	HttpSession session = httpServletRequest.getSession(false);
	if (session != null) {
	    sesToken = (String) session.getAttribute(WebConstants.TOKEN);
	    if (StringUtils.isNotBlank(sesToken)) {
		if (StringUtils.isNotBlank(reqToken)) {
		    if (!reqToken.equals(sesToken)) {
			log.error("doFilter(): Nella request è presente un token diverso da quello della session: reqToken={}", reqToken);
			request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
			return;
		    }
		}
		//eseguo una checkTokenValidity invece di una checkToken (più lenta)
		disableCheckToken = true;
		boolean isTokenValid = this.checkTokenValidity(httpServletRequest, sesToken);
		//isTokenValid = externalDBResolver.checkTokenValidity(sesToken);
		if (!isTokenValid) {
		    log.error("doFilter(): Token di sessione non valido: {}", sesToken);
		    request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
		    return;
		}
		token = sesToken;
	    } else {
		if (StringUtils.isNotBlank(reqToken)) {
		    session.setAttribute(WebConstants.TOKEN, reqToken);
		    token = reqToken;
		}
	    }
	} else {
	    token = reqToken;
	}
	if (!disableCheckToken) {
	    // valido il token, in caso positivo recupero idcomunealias per il filtro IdComuneFilter
	    if (StringUtils.isNotBlank(token)) {
		try {
		    Properties props = externalDBResolver.checkToken(token);
		    request.setAttribute(WebConstants.IDCOMUNE_ALIAS, props.getProperty(WebConstants.IDCOMUNE_ALIAS));
		} catch (SessionTimeoutException e) {
		    request.getRequestDispatcher("/sessionTimeout.jsp").forward(request, response);
		    return;
		}
	    }
	}
	// chiamo il resto dei filtri
	chain.doFilter(request, response);
    }

    /**
     * Recupera l'istanza dell'externalDBResolver dal contesto di Spring
     */
    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
    }

    public void destroy() {

    }

    /**
     * metodo che esegue il metodo checkTokenValidity di ExternalDBResolver se in sigeprosecurity è valorizzato il
     * parametro CHECK_TOKEN_TIMEOUT allora la check token è eseguita solo se è trascorso un tempo superiore a quello
     * impostato tramite il parametro (espresso in minuti)
     * 
     * @param request
     * @param token
     * @return
     */
    private boolean checkTokenValidity(HttpServletRequest request, String token) {

	boolean isValid = true;
	boolean doCheckToken = true;
	Integer checkTokenTimeoutMins = null;
	//eseguire la checkToken solo ad intervalli prestabiliti dal parametro recuperato da sigeprosecurity CHECK_TOKEN_TIMEOUT
	//salvare in sessione e gestire controllo
	String checkTokenTimeoutParam = WebConstants.getSecurityParamValue(SecurityParams.CHECK_TOKEN_TIMEOUT);
	try {
	    checkTokenTimeoutMins = Integer.valueOf(checkTokenTimeoutParam);
	    if (checkTokenTimeoutMins > 0) {
		Calendar lastCheckToken = (Calendar) request.getSession(false).getAttribute("LAST_CHECK_TOKEN");
		if (lastCheckToken != null) {
		    Calendar now = new GregorianCalendar();
		    Calendar lastCheckTokenPlusTimeout = new GregorianCalendar();
		    lastCheckTokenPlusTimeout.setTime(lastCheckToken.getTime());
		    lastCheckTokenPlusTimeout.add(Calendar.MINUTE, checkTokenTimeoutMins);
		    if (log.isTraceEnabled()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			log.trace("Last CheckToken: {}", sdf.format(lastCheckToken.getTime()));
			log.trace("Timeout: {} minuti", checkTokenTimeoutMins);
			log.trace("Next CheckToken: {}", sdf.format(lastCheckTokenPlusTimeout.getTime()));
			log.trace("Now: {}", sdf.format(now.getTime()));
		    }
		    if (now.compareTo(lastCheckTokenPlusTimeout) < 0) {
			doCheckToken = false;
		    } else {
			request.getSession(false).setAttribute("LAST_CHECK_TOKEN", new GregorianCalendar());
		    }
		} else {
		    request.getSession(false).setAttribute("LAST_CHECK_TOKEN", new GregorianCalendar());
		}
	    }
	} catch (NumberFormatException e) {
	    log.warn("Il valore del parametro di sigeprosecurity CHECK_TOKEN_TIMEOUT non è corretto");
	}
	if (doCheckToken) {
	    log.debug("do check token");
	    isValid = externalDBResolver.checkTokenValidity(token);
	}
	return isValid;
    }
}
