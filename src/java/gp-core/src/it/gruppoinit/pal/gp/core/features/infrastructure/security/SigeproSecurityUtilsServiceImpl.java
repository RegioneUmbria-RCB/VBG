package it.gruppoinit.pal.gp.core.features.infrastructure.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.exception.SecurityException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenRequest;
import it.gruppoinit.sigeprosecurity.schema.CheckTokenResponse;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

@Service
public class SigeproSecurityUtilsServiceImpl implements SigeproSecurityUtilsService {

    private static final Logger log = LoggerFactory.getLogger(SigeproSecurityUtilsServiceImpl.class);
    @Autowired
    private SecurityWSClient securityWSClient;
    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    public List<String> getAliasAttivi() {

	try {
	    List<String> aliasList = new ArrayList<String>();
	    GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
	    GetSecurityListResponse securityListResponse;
	    securityListResponse = securityWSClient.getWsPort().getSecurityList(securityListRequest);
	    log.debug("getAliasDaProcessare: Chiamata alla security effettuata");
	    for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		if (securityListRespItem.isAttivo()) {
		    aliasList.add(securityListRespItem.getAlias());
		}
	    }
	    return aliasList;
	} catch (Exception e) {
	    log.error("Errore: " + e.getMessage(), e);
	}
	log.info("AggiornaDettaglioPosizioniDebitorieJob: end");
	return new ArrayList<String>();
    }

    @Override
    public void setORMHelper(String alias) throws SecurityException {

	setORMHelper(alias, WebConstants.SOFTWARE_TT);
    }

    @Override
    public void setORMHelper(String alias, String software) throws SecurityException {

	ExternalDBResolver externalDBResolver = (ExternalDBResolver) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	Properties connProps = externalDBResolver.getConnectionProperties(alias);
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	if (StringUtils.isNotBlank(software)) {
	    ORMHelper.setSoftware(software);
	} else {
	    ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	}
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login();
    }

    @Override
    public void resetThreadLocalVars() {

	ORMHelper.destroyORMHelper();
	SigeproBusinessRules.buildDefaultRules();
    }

    private void login() throws SecurityException {

	String userid = null;
	try {
	    VerticalizzazioniService verticalizzazioniService = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("verticalizzazioniServiceImpl");
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
		log.debug("login(userid:{}) recuperato dalla verticalizzazione WS_LOGIN", userid);
	    }
	    UserDetails user = this.getUser(userid);
	    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	    // update the current context to the target user
	    SecurityContextHolder.getContext().setAuthentication(authRequest);
	    log.debug("login(userid:{})", userid);
	} catch (UsernameNotFoundException e) {
	    log.error("login(userid:{}) idcomunealias:{}\n{}", new Object[] { userid, ORMHelper.getIdcomuneAlias(), e });
	    throw new SecurityException("login: " + e.getMessage());
	}
    }

    protected UserDetails getUser(String userId) {

	if (userId == null) {
	    return userSecurityService.loadAdministratorUser();
	}
	try {
	    return userSecurityService.loadUserByUsername(userId);
	} catch (UsernameNotFoundException e) {
	    return userSecurityService.loadAdministratorUser();
	}
    }

    @Override
    public CheckTokenResponse infoToken(String token) {

	CheckTokenResponse checkTokenResponse = null;
	try {
	    CheckTokenRequest req = new CheckTokenRequest();
	    req.setToken(token);
	    req.setTokenInfo(true);
	    checkTokenResponse = securityWSClient.getWsPort().checkToken(req);
	} catch (Exception e) {
	    log.error("checkToken({}): {}", token, e.getMessage());
	    throw new RuntimeException("Errore durante la verifica del token: " + token);
	}
	return checkTokenResponse;
    }
}
