package it.gruppoinit.pal.gp.core.utils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

public class BaseEnvironment {

    private static final Logger log = LoggerFactory.getLogger(BaseEnvironment.class);
    private ExternalDBResolver externalDBResolver;
    protected UserSecurityService userSecurityService;
    protected VerticalizzazioniService verticalizzazioniService;
    protected VerticalizzazioniparametriService verticalizzazioniparametriService;
    protected VerticalizzazioniparametribaseService verticalizzazioniparametribaseService;
    private SdeproxyService sdeproxyService;

    @Autowired
    public void setSdeproxyService(SdeproxyService sdeproxyService) {

	this.sdeproxyService = sdeproxyService;
    }

    @Autowired
    public void setExternalDBResolver(ExternalDBResolver externalDBResolver) {

	this.externalDBResolver = externalDBResolver;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setVerticalizzazioniparametribaseService(VerticalizzazioniparametribaseService verticalizzazioniparametribaseService) {

	this.verticalizzazioniparametribaseService = verticalizzazioniparametribaseService;
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
	this.login(props.getProperty(WebConstants.USER_ID));
    }

    /**
     * metodo per inizializzare i ThreadLocal necessari per la connessione al db e per eseguire la login
     * 
     * @param alias
     */
    protected void setORMHelper(String alias) {

	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    protected void setORMHelperSoftware(String alias, String software) {

	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    protected void login(String userid) {

	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
		log.debug("login(userid:{}) recuperato dalla verticalizzazione WS_LOGIN", userid);
	    }
	    UserDetails user;
	    try {
		user = userSecurityService.loadUserByUsername(userid);
	    } catch (UsernameNotFoundException e) {
		user = userSecurityService.loadAdministratorUser();
	    }
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	    log.info("login(userid:{})", userid);
	} catch (Exception e) {
	    log.error("login(userid:{}) idcomunealias:{}", new Object[] { userid, ORMHelper.getIdcomuneAlias(), e });
	    throw new RuntimeException("BACKOFFICE BASE ENV: Errore durante la login: " + e.getMessage());
	}
    }

    /**
     * Attenzione!!! Attualmente usato solo per le regole Dal token risalgo all'alias che deve coincidere con IDENTE di
     * sdeproxy. Da questo prendo la COLONNA ALIAS_ENTE ristacco un token che mi da l'idcomune corretto
     * 
     * @param software
     * @param token
     */
    protected void setConsoleORMHelper(String software, String token) {

	setORMHelper(software, token);
	Properties props = externalDBResolver.checkToken(token);
	String idEnte = props.getProperty(WebConstants.IDCOMUNE_ALIAS);
	// alias è IDENTE di sdeproxy;
	Sdeproxy sde = sdeproxyService.findByIdEnte(idEnte);
	if (sde == null) {
	    log.error("Configurazione SDE non trovata per l'ente {}", idEnte);
	    throw new RuntimeException("Configurazione SDE non trovata per l'ente " + idEnte);
	}
	String alias = sde.getAliasEnte();
	setORMHelperSoftware(alias, software);
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
