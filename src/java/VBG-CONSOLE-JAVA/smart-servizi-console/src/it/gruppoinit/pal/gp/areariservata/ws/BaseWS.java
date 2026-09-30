package it.gruppoinit.pal.gp.areariservata.ws;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;

public class BaseWS {

    private static final Logger log = LoggerFactory.getLogger(BaseWS.class);
    private ExternalDBResolver externalDBResolver;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    /**
     * metodo per inizializzare i ThreadLocal necessari per la connessione al db e per eseguire la login
     * 
     * @param software
     * @param token
     */
    protected void setORMHelper(String software, String token) {

	Properties props = externalDBResolver.checkToken(token);
	Properties connProps = externalDBResolver.getConnectionProperties(props.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	if (StringUtils.isNotBlank(software)) {
	    ORMHelper.setSoftware(software);
	}
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(token);
	this.login();
    }

    private void login() {

	log.info("login()");
	try {
	    UserDetails user = userSecurityService.loadAdministratorUser();
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	} catch (Exception e) {
	    log.error("login()", e);
	    throw new RuntimeException("AREA RISERVATA NLA WS. Errore durante la login: " + e.getMessage());
	}
    }

    protected String getRootCause(Exception e) {

	StringBuffer rootCause = new StringBuffer(e.getMessage());
	if (e instanceof DataAccessException) {
	    DataAccessException dae = (DataAccessException) e;
	    Throwable t = dae.getRootCause();
	    if (t != null) {
		rootCause.append(" ").append(t.getMessage());
	    }
	} else if (e instanceof BaseValidationException) {
	    List<InvalidValue> validationMessages = ((BaseValidationException) e).getInvalidValues();
	    if (validationMessages != null) {
		for (InvalidValue invalidValue : validationMessages) {
		    rootCause.append(invalidValue).append(",");
		}
	    }
	}
	return rootCause.toString();
    }

    /**
     * <ul>
     * <li>ORMHelper.destroyORMHelper()</li>
     * <li>SigeproBusinessRules.buildDefaultRules()</li>
     * </ul>
     */
    protected void resetThreadLocalVars() {

	ORMHelper.destroyORMHelper();
	SigeproBusinessRules.buildDefaultRules();
    }
}
