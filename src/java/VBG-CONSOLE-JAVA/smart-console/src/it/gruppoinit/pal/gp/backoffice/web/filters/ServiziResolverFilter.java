package it.gruppoinit.pal.gp.backoffice.web.filters;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServiziResolverFilter implements Filter {

    public static Logger logger = LoggerFactory.getLogger(ServiziResolverFilter.class);
    private ExternalDBResolver externalDBResolver;
    private SdeproxyService sdeproxyService;
    private Properties deployproperties;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	HttpServletRequest req = (HttpServletRequest) request;
	String path = req.getServletPath();
	String redirTo = "";
	if (matchServizio(path)) {
	    String idente = getIdEnte(path);
	    String alias = recuperoAliasCorrettodaIdente(idente);
	    ORMHelper.setIdcomuneAlias(alias);
	    redirTo = "welcome/start.htm?idcomunealias=" + alias + "&idente=" + idente + "&ts_=" + System.currentTimeMillis();
	}
	if (StringUtils.isNotBlank(redirTo)) {
	    redirTo = req.getSession().getServletContext().getContextPath() + "/" + redirTo;
	    req.getSession().invalidate();
	    HttpServletResponse httpResponse = (HttpServletResponse) response;
	    httpResponse.sendRedirect(redirTo);
	    return;
	} else {
	    chain.doFilter(request, response);
	}
    }

    private String getIdEnte(String path) {

	Matcher m = getPatternServizio().matcher(path.toLowerCase());
	while (m.find()) {
	    return m.group(1).toUpperCase();
	}
	throw new RuntimeException("path non valido: " + path);
    }

    private String recuperoAliasCorrettodaIdente(String idEnte) {

	String defaultAlias = deployproperties.getProperty("ws.token.default.alias");
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
	throw new SecurityException("Ente non configurato " + idEnte);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
	externalDBResolver = (ExternalDBResolver) wac.getBean("externalDBResolver");
	sdeproxyService = (SdeproxyService) wac.getBean("sdeproxyServiceImpl");
	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResourceAsStream(WebConstants.CONFIG_FILES_FOLDER + WebConstants.DEPLOY_PROPS);
	    deployproperties = new Properties();
	    deployproperties.load(in);
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
    }

    private boolean matchServizio(String path) {

	return getPatternServizio().matcher(path.toLowerCase()).matches();
    }

    private Pattern getPatternServizio() {

	if (this.PATTERN_SERVIZIO_ == null) {
	    String pattern = "[/]([a-z][0-9]{3}|c[a-z][0-9]{3}|[0-9]{6}|ttr[a-z]{0,5}|cons[a-z]{0,8}|cns[a-z]{0,8}#PATTERN_AGGIUNTIVI#)([/]{0,1})";
	    String patternAggiuntivi = deployproperties.getProperty("servizi.resolver.filter.path.ammessi");
	    if (StringUtils.isBlank(patternAggiuntivi)) {
		patternAggiuntivi = "";
	    }
	    pattern = pattern.replace("#PATTERN_AGGIUNTIVI#", patternAggiuntivi).toLowerCase();
	    PATTERN_SERVIZIO_ = Pattern.compile(pattern);
	}
	return PATTERN_SERVIZIO_;
    }

    private Pattern PATTERN_SERVIZIO_ = null;
}
