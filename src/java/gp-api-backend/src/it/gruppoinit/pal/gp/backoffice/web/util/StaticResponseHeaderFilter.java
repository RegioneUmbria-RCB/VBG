package it.gruppoinit.pal.gp.backoffice.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * filtro per settare su ogni response l'header P3P che permette a IE di accettare cookie di terze parti quando si
 * utilizza sigepro2v all'interno del frame di sigeproMS
 * 
 * @author fabrizioc
 * 
 */
public class StaticResponseHeaderFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(StaticResponseHeaderFilter.class);
    private final static String HEADERNAME_INIT_PARAM = "headername";
    private final static String HEADERVALUE_INIT_PARAM = "headervalue";
    private String headername = null;
    private String headervalue = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

	headername = filterConfig.getInitParameter(HEADERNAME_INIT_PARAM);
	headervalue = filterConfig.getInitParameter(HEADERVALUE_INIT_PARAM);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

	HttpServletResponse httpResp = (HttpServletResponse) response;
	// set HTTP header on response
	httpResp.setHeader(headername, headervalue);
	if (log.isDebugEnabled())
	    log.debug("Set HTTP Response header: " + headername + ": " + headervalue);
	// pass on to other filters or the resource
	filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
