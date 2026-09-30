package it.gruppoinit.pal.gp.backoffice.web.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZIPFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(GZIPFilter.class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

	if (req instanceof HttpServletRequest) {
	    HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    String ae = request.getHeader("accept-encoding");
	    if (ae != null && ae.indexOf("gzip") != -1) {
		if (logger.isTraceEnabled()) {
		    logger.trace("GZIPFilter.doFilter(): " + request.getRequestURI());
		}
		GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
		chain.doFilter(req, wrappedResponse);
		wrappedResponse.finishResponse();
		return;
	    }
	    chain.doFilter(req, res);
	}
    }

    public void init(FilterConfig filterConfig) {

	// noop
    }

    public void destroy() {

	// noop
    }
}
