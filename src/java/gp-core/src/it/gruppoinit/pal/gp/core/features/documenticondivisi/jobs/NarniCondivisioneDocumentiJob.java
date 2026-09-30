package it.gruppoinit.pal.gp.core.features.documenticondivisi.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
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
import it.gruppoinit.pal.gp.core.features.documenticondivisi.IDocumentiCondivisiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.jobs.GenericParam;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;

public class NarniCondivisioneDocumentiJob extends GenericParam implements StatefulJob {

    private static final Logger log = LoggerFactory.getLogger(NarniCondivisioneDocumentiJob.class);
    private final static String PARAM_NUMERO_MASSIMO_DI_DOCUMENTI = "NUMERO_MASSIMO_DI_DOCUMENTI";
    private final static String PARAM_NUMERO_MASSIMO_DI_DOCUMENTI_DESC = "Indicare il numero massimo di documenti da inviare in condivisione documentale. " +
	    "Se lasciato vuoto verranno presi in considerazione, in una unica soluzione, tutti i documenti inviabili; l'operazione potrebbe rallentare l'applicativo. " +
	    "Si consiglia un numero massimo di 100 documenti a trasmissione";

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	log.info("execute NarniCondivisioneDocumentiJob");
	try {
	    String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	    String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	    String parNumeroMassimoDocs = (String) ctx.getJobDetail().getJobDataMap().get(PARAM_NUMERO_MASSIMO_DI_DOCUMENTI);
	    Integer numeroMassimoDocumenti = StringUtils.isBlank(parNumeroMassimoDocs) ? null : Integer.parseInt(parNumeroMassimoDocs);
	    ORMHelper.setIdcomuneAlias(idcomunealias);
	    ExternalDBResolverWS dbResolverWS = (ExternalDBResolverWS) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	    Properties properties = dbResolverWS.getConnectionProperties(idcomunealias);
	    setORMHelperSoftware(properties, idcomunealias, software);
	    //verificare rispetto alla lista dei documenti in stato DA INVIARE, quali effettivamente possono essere trasmessi al documentale
	    NarniDocumentiCondivisiDAO documentiCondivisiDAO = (NarniDocumentiCondivisiDAO) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("narniDocumentiCondivisiDAOImpl");
	    documentiCondivisiDAO.setNumeroMassimoDocumenti(numeroMassimoDocumenti);
	    //ricavare per ogni documento sia il file che il rispettivo file.meta ( metadati )
	    //spostare il tutto su FTP
	    //segnare i documenti come trasmessi
	    IDocumentiCondivisiService service = (IDocumentiCondivisiService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("documentiCondivisiServiceImpl");
	    service.elaboraDocumenti(documentiCondivisiDAO);
	    log.info("execute end NarniCondivisioneDocumentiJob");
	} catch (Exception e) {
	    //segnare il log anche nella tabella dei logs
	    log.error("execute error NarniCondivisioneDocumentiJob ", e);
	    throw new JobExecutionException(e.getMessage());
	} finally {
	    ORMHelper.destroyORMHelper();
	}
    }

    protected void setORMHelperSoftware(Properties connProps, String idcomunealias, String software) {

	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(idcomunealias);
	ORMHelper.setSoftware(software);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
	ORMHelper.setToken(connProps.getProperty(WebConstants.TOKEN));
	this.login(connProps.getProperty(WebConstants.USER_ID));
    }

    private void login(String userid) {

	try {
	    //se la verticalizzazione WS_LOGIN è attiva utilizzo il valore del parametro CODICE_OPERATORE come userid
	    VerticalizzazioniService verticalizzazioniService = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("verticalizzazioniServiceImpl");
	    UserSecurityService userSecurityService = (UserSecurityService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("userSecurityService");
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

    @Override
    public Map<String, String> getParam() {

	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_NUMERO_MASSIMO_DI_DOCUMENTI, PARAM_NUMERO_MASSIMO_DI_DOCUMENTI_DESC);
	return m;
    }
}
