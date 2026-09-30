package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * Crea i calendari per l'anno in corso per i mercati specificati dai filtri
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_INSERISCI_PRESENZE_MERCATI" spring-bean-id="upgrUpdateInserisciPresenzaConcessionariMercatiTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateInserisciPresenzaConcessionariMercatiTask"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 			&lt;param name="alias" value="E256"&gt;&lt;/param&gt;
 * 			&lt;param name="software" value="CO"&gt;&lt;/param&gt;
 * 			&lt;param name="codiciMercato" value="1,2"&gt;&lt;/param&gt;
 * 			&lt;param name="dallaData" value="01/07/2022"&gt;&lt;/param&gt;
 * 			&lt;param name="allaData" value="31/08/2022"&gt;&lt;/param&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateInserisciPresenzaConcessionariMercatiTask")
public class UpdateInserisciPresenzaConcessionariMercatiTask extends BaseJavaTask {

    private static final Logger log = LoggerFactory.getLogger(UpdateInserisciPresenzaConcessionariMercatiTask.class);

    @Override
    public void initialize() throws SetupRunException {

	// TODO Auto-generated method stub
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	log.info("UpdateInserisciPresenzaConcessionariMercatiTask start.....");
	String id = String.valueOf(System.currentTimeMillis());
	String idcomunealias = getParameterValue("alias");
	String software = getParameterValue("software");
	String dallaDataStr = getParameterValue("dallaData");
	String allaDataStr = getParameterValue("allaData");
	Date dallaData = Utilities.getDate(dallaDataStr, WebConstants.DATE_FORMAT_PATTERN).getTime();
	Date allaData = Utilities.getDate(allaDataStr, WebConstants.DATE_FORMAT_PATTERN).getTime();
	String codiciMercato = StringUtils.trim(getParameterValue("codiciMercato"));
	List<String> codici = new ArrayList<String>(Arrays.asList(codiciMercato.split(",")));
	LoggerUpdaterecord.log("######################################################################");
	try {
	    LoggerUpdaterecord.log(id +
		    "==>UpdateInserisciPresenzaConcessionariMercatiTask ALIAS:" +
		    idcomunealias +
		    ", Software:" +
		    software +
		    ", AT: " +
		    Utilities.formatDate(new Date(), true));
	    setORMHelper(idcomunealias, software);
	    MercatipresenzeTService mercatipresenzeTService = (MercatipresenzeTService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("mercatipresenzeTServiceImpl");
	    mercatipresenzeTService.inizializzaGiornateMercati(toIntegerList(codici), dallaData, allaData);
	    log.info("Aggiornamento completato.....");
	} catch (Exception e) {
	    log.error("execute", e);
	    LoggerUpdaterecord
		    .log(id + "==>UpdateInserisciPresenzaConcessionariMercatiTask STOP WITH ERROR AT: " + Utilities.formatDate(new Date(), true));
	    LoggerUpdaterecord.log(id + "==>######################################################################");
	    throw new RuntimeException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
	LoggerUpdaterecord.log(id + "==>UpdateInserisciPresenzaConcessionariMercatiTask STOP AT: " + Utilities.formatDate(new Date(), true));
	LoggerUpdaterecord.log(id + "==>##########################################################################");
	log.info("UpdateInserisciPresenzaConcessionariMercatiTask end.....");
	return 0;
    }

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

    private void login(String userid) {

	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    Verticalizzazioniparametri vParam = verticalizzazioniService.getVerticalizzazioniparametri("WS_LOGIN", "USERID_OPERATORE");
	    if (vParam != null) {
		userid = vParam.getValore();
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
	} catch (Exception e) {
	    throw new RuntimeException("BACKOFFICE BASE ENV: Errore durante la login: " + e.getMessage());
	}
    }

    private List<Integer> toIntegerList(List<String> codici) {

	List<Integer> c = new ArrayList<Integer>();
	for (String string : codici) {
	    c.add(Integer.parseInt(string.trim()));
	}
	return c;
    }

    private UserSecurityService userSecurityService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }
}
