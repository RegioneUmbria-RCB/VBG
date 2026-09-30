package it.gruppoinit.pal.gp.core.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.ws.client.SecurityWSClient;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListRequest;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public abstract class BaseJob {

    public static final Logger log = LoggerFactory.getLogger(BaseJob.class);

    /**
     * protected final static String PARAM_ERRORMAIL_ABILITA = "ERRORMAIL_ABILITA"; protected final static String
     * PARAM_ERRORMAIL_TO = "ERRORMAIL_A"; protected final static String PARAM_ERRORMAIL_CC = "ERRORMAIL_CC"; protected
     * final static String PARAM_ERRORMAIL_SUBJECT = "ERRORMAIL_OGGETTO";
     */
    protected void setORMHelper(String alias, String software) {

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
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    protected void login(String userid) {

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
	} catch (Exception e) {
	    log.error("login(userid:{}) idcomunealias:{}\n{}", new Object[] { userid, ORMHelper.getIdcomuneAlias(), e });
	    throw new RuntimeException("login: " + e.getMessage());
	}
    }

    protected void resetThreadLocalVars() {

	ORMHelper.destroyORMHelper();
	SigeproBusinessRules.buildDefaultRules();
    }

    protected UserDetails getUser(String userId) {

	UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		.getBean("userSecurityService");
	try {
	    return userSecurityService.loadUserByUsername(userId);
	} catch (UsernameNotFoundException e) {
	    return userSecurityService.loadAdministratorUser();
	}
    }

    @SuppressWarnings("unchecked")
    public <T> T getBeanOfType(String type) throws ClassNotFoundException {

	Class<?> forName = Class.forName(type);
	Map<String, Collection<T>> ret = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(forName);
	if (ret.isEmpty()) {
	    throw new RuntimeException("Non è stato trovato il bean della classe " + type.getClass());
	}
	return (T) ret.values().iterator().next();
    }

    public abstract Map<String, String> getParam();

    public List<String> getAliasDaProcessare(JobExecutionContext ctx, String nomeParametroAliasDaElaborare) {

	String listaAliasDaElaborare = (String) ctx.getJobDetail().getJobDataMap().get(nomeParametroAliasDaElaborare);
	SecurityWSClient securityWSClient = (SecurityWSClient) ContextLoader.getCurrentWebApplicationContext().getBean("securityWSClient");
	try {
	    if (StringUtils.isNotEmpty(listaAliasDaElaborare)) {
		return Arrays.asList(listaAliasDaElaborare.split(";"));
	    } else {
		List<String> aliasList = new ArrayList<String>();
		GetSecurityListRequest securityListRequest = new GetSecurityListRequest();
		GetSecurityListResponse securityListResponse;
		securityListResponse = securityWSClient.getWsPort().getSecurityList(securityListRequest);
		log.debug("AggiornaDettaglioPosizioniDebitorieJob: Chiamata alla security effettuata");
		for (SecurityListType securityListRespItem : securityListResponse.getSecurity()) {
		    if (securityListRespItem.isAttivo()) {
			aliasList.add(securityListRespItem.getAlias());
		    }
		}
		return aliasList;
	    }
	} catch (Exception e) {
	    log.error("Errore: " + e.getMessage(), e);
	}
	log.info("AggiornaDettaglioPosizioniDebitorieJob: end");
	return new ArrayList<String>();
    }
}
