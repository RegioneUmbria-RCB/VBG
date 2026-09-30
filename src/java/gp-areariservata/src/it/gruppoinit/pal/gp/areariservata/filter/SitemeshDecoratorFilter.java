package it.gruppoinit.pal.gp.areariservata.filter;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.io.IOException;

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

import com.opensymphony.module.sitemesh.mapper.SessionDecoratorMapper;

public class SitemeshDecoratorFilter implements Filter {

    public static final String AREARISERVATA_NETURL = "AreariservataNETURL";
    public static final String APPMITT_REQUEST_PARAM_MICROSOFT = "MS";
    public static final String APPMITT_REQUEST_PARAM = "APPMITT";
    private static final Logger log = LoggerFactory.getLogger(SitemeshDecoratorFilter.class);
    private static final String APPMITT_REQUEST_PARAM_JAVA = "JAVA";

    @Override
    public void destroy() {

    }

    /**
     * il filtro controlla se nella request esiste un parametro denominato APPMIT. Se presente allora setta il decorator
     * a livello di session (vedi {@link SessionDecoratorMapper}). APPMITT accetta i seguenti valori
     * <ul>
     * <li>MS - chiamata da area riservata Microsoft</li>
     * <li>JAVA - Chiamata da area riservata JAVA</li>
     * </ul>
     * Il layout del decorator impostato sarà /WEB-INF/jsp/decorators/areariservata&lt;APPMIT>.jsp<br />
     * Il filtro mette in sessione anche gli attributi {@link WebConstants#RETURNTO} ed in caso di provenienza da area
     * riservata la variabile di sessione baseAreaRiservataMsUrl che serve al decorator per impostare i fogli di stile e
     * le immagini dell'area riservata .NET
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	log.debug("doFilter(): Request URI: {}", httpServletRequest.getRequestURI());
	String mittente = httpServletRequest.getParameter(APPMITT_REQUEST_PARAM);
	String returnTo = httpServletRequest.getParameter(WebConstants.RETURNTO);
	HttpSession session = httpServletRequest.getSession(false);
	if (session != null) {
	    if (StringUtils.isNotBlank(mittente)) {
		if (mittente.equalsIgnoreCase(APPMITT_REQUEST_PARAM_MICROSOFT) || mittente.equalsIgnoreCase(APPMITT_REQUEST_PARAM_JAVA)) {
		    if (mittente.equalsIgnoreCase(APPMITT_REQUEST_PARAM_MICROSOFT)) {
			session.setAttribute("decorator", "areariservata" + mittente);
			log.debug("decorator: areariservata" + mittente);
		    }
		    if (StringUtils.isNotBlank(returnTo)) {
			returnTo = returnTo + "&" + WebConstants.TOKEN + "=" + session.getAttribute(WebConstants.TOKEN);
			log.debug("ReturnTo: " + returnTo);
			if (mittente.equalsIgnoreCase(APPMITT_REQUEST_PARAM_MICROSOFT)) {
			    String baseAreaRiservataMsUrl = returnTo.toLowerCase().substring(0, (returnTo.toLowerCase().indexOf("reserved") - 1));
			    session.setAttribute("baseAreaRiservataMsUrl", baseAreaRiservataMsUrl);
			    setCookieAreaRiservataNET((HttpServletRequest) request, (HttpServletResponse) response, returnTo);
			}
			session.setAttribute(WebConstants.RETURNTO, returnTo);
		    }
		}
	    }
	}
	// chiamo il resto dei filtri
	chain.doFilter(request, response);
    }

    private void setCookieAreaRiservataNET(HttpServletRequest request, HttpServletResponse response, String returnTo) {

	Cookie cookie = new Cookie(AREARISERVATA_NETURL, returnTo);
	cookie.setPath(request.getContextPath());
	cookie.setMaxAge(-1);
	if (JdkVersion.getMajorJavaVersion() == JdkVersion.JAVA_16) {
	    cookie.setPath(";Path=" + request.getContextPath() + ";HttpOnly;");
	} else {
	    cookie.setPath(request.getContextPath());
	}
	response.addCookie(cookie);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

	// WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
    }
}
