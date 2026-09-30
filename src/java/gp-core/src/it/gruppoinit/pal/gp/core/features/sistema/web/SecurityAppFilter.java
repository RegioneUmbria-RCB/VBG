package it.gruppoinit.pal.gp.core.features.sistema.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import it.gruppoinit.pal.gp.core.features.sistema.SecurityUtils;

public class SecurityAppFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	if (request.getParameter("ricaricaMappaSecurity") != null) {
	    SecurityUtils.initSecurityAppUrl();
	}
	SecurityUtils.sanificaRequest(httpServletRequest);
	chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

	// non devo dismettere niente
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

	// non devo inizializzare niente
    }
}
