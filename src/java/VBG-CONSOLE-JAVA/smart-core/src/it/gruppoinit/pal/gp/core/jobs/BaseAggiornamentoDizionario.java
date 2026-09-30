package it.gruppoinit.pal.gp.core.jobs;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.context.ContextLoader;

public class BaseAggiornamentoDizionario {

    private static final Logger log = LoggerFactory.getLogger(BaseAggiornamentoDizionario.class);
    protected static String ALIAS_DA_ESCLUDERE = "dizionario.cart.alias.esclusi";

    protected static boolean isAliasDaProcessare(String alias, Properties configProps) {

	if (configProps == null || configProps.size() == 0) {
	    return true;
	}
	if (StringUtils.isBlank(alias)) {
	    return false;
	}
	String listaAlias = configProps.getProperty(ALIAS_DA_ESCLUDERE);
	if (StringUtils.isBlank(listaAlias)) {
	    return false;
	}
	return listaAlias.indexOf(alias) < 0;
    }

    protected Properties loadConfig() {

	Properties p = new Properties();
	InputStream in = null;
	try {
	    in = this.getClass().getClassLoader().getResourceAsStream("jobs.properties");
	    p.load(in);
	} catch (Exception e) {
	    log.error("Errore durante il caricamento del file jobs.properties: {}", e.getMessage());
	    throw new RuntimeException("Errore durante il caricamento della configurazione del database: " + e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		}
	    }
	}
	return p;
    }

    protected boolean setORMHelper(String idcomunealias, String idente, String software) {

	try {
	    ExternalDBResolver externalDBResolver = (ExternalDBResolver) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("externalDBResolver");
	    Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	    ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	} catch (Exception e) {
	    log.error("errore durante il recupero dell'idcomune {}", e);
	    return false;
	}
	ORMHelper.setIdcomuneAlias(idcomunealias);
	if (StringUtils.isNotBlank(software)) {
	    ORMHelper.setSoftware(software);
	}
	ORMHelper.setIdente(idente);
	try {
	    this.login();
	} catch (Exception e) {
	    log.error("errore durante la login {}", e);
	    return false;
	}
	return true;
    }

    protected void login() {

	UserDetails user;
	UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("userSecurityService");
	user = userSecurityService.loadAnyUser();
	UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	// update the current context to the target user
	SecurityContextHolder.getContext().setAuthentication(authRequest);
    }
}
