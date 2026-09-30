package it.gruppoinit.pal.gp.core.jobs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

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

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiManager;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolverWS;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.sigeprosecurity.schema.GetSecurityListResponse;
import it.gruppoinit.sigeprosecurity.schema.SecurityListType;

public class VerificaStatoPosizioniDebitorieJob extends GenericParam implements StatefulJob {

    public static final Logger log = LoggerFactory.getLogger(VerificaStatoPosizioniDebitorieJob.class);

    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {

	// Recupera le posizioni debitorie negli stati
	// per ogni posizione debitoria chiamo la verifica dello stato della posizione debitoria del nodo dei pagamenti
	String software = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.SOFTWARE);
	String idcomunealias = (String) ctx.getJobDetail().getJobDataMap().get(WebConstants.IDCOMUNE_ALIAS);
	String statiDaVerificare = (String) ctx.getJobDetail().getJobDataMap()
		.get(VerificaStatoPosizioniDebitorieJob.PARAM_OVERRIDE_STATI_DA_VERIFICARE);
	String alias = (String) ctx.getJobDetail().getJobDataMap().get(VerificaStatoPosizioniDebitorieJob.PARAM_SOLO_ALIAS_IN_LISTA);
	// ORMHelper.setSoftware(software);
	ORMHelper.setIdcomuneAlias(idcomunealias);
	ExternalDBResolverWS dbResolverWS = (ExternalDBResolverWS) ContextLoader.getCurrentWebApplicationContext().getBean("externalDBResolver");
	Collection<NodoPagamentiManager> beans = ContextLoader.getCurrentWebApplicationContext().getBeansOfType(NodoPagamentiManager.class).values();
	NodoPagamentiManager manager = null;
	while (beans.iterator().hasNext()) {
	    manager = beans.iterator().next();
	    if (manager != null) {
		break;
	    }
	}
	String idOp = UUID.randomUUID().toString();
	LoggerUpdaterecord.log("##VERIFICA STATO POSIZIONI START## " + idOp);
	StatiPosizioniDebitorieConverter spc = new StatiPosizioniDebitorieConverter();
	String[] statiDaVerificareArr = spc.getDefaultStatiNonConclusiviString();
	if (StringUtils.isNotBlank(statiDaVerificare)) {
	    statiDaVerificareArr = spc.convertiStatiDaStringa(statiDaVerificare);
	}
	Set<String> aliases = new HashSet<String>();
	if (StringUtils.isNotBlank(alias)) {
	    String[] split = alias.split(",");
	    for (String s : split) {
		String def = StringUtils.defaultString(s).trim();
		if (StringUtils.isNotBlank(def)) {
		    aliases.add(def);
		}
	    }
	}
	if (aliases.isEmpty()) {
	    GetSecurityListResponse attivazioni = dbResolverWS.getAttivazioni(true);
	    List<SecurityListType> security = attivazioni.getSecurity();
	    for (SecurityListType s : security) {
		aliases.add(s.getAlias());
	    }
	}
	LoggerUpdaterecord.log("##VERIFICA STATO POSIZIONI START## " + idOp + " ALIAS DA ELABORARE " + aliases.size());
	for (String a : aliases) {
	    LoggerUpdaterecord.log("##VERIFICA STATO POSIZIONI START## " + idOp + "  ELABORO ALIAS " + a);
	    idcomunealias = a;
	    log.info("VerificaStatoPosizioniDebitorie: processo l'alias :{}", idcomunealias);
	    try {
		Properties properties = dbResolverWS.getConnectionProperties(idcomunealias);
		setORMHelperSoftware(properties, idcomunealias, software);
		LoggerUpdaterecord
			.log("##VERIFICA STATO POSIZIONI START##  " + idOp + " ORMHELPER SETTATO PER ALIAS " + a + " aggiorno le posizioni");
		manager.aggiornaStatoPosizioniDebitorieInStati(statiDaVerificareArr);
		LoggerUpdaterecord.log("##VERIFICA STATO POSIZIONI START##  " + idOp + " ALIAS " + a + " elaborato");
	    } catch (Exception e) {
		log.error("errore in VerificaStatoPosizioniDebitorie alias :{}", idcomunealias, e);
	    } finally {
		try {
		    ORMHelper.destroyORMHelper();
		    SecurityContextHolder.clearContext();
		    // org.springframework.transaction.support.TransactionSynchronizationManager.clear();
		} catch (Exception e) {
		    log.error("" + e.getMessage(), e);
		}
	    }
	}
	LoggerUpdaterecord.log("##VERIFICA STATO POSIZIONI END##  " + idOp + " ");
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

	String doc = "Il parametro serve per sovrascrivere gli stati di default per verificare le posizioni debitorie per le quali chiedere la verifica stato. Quelle configurate di default sono: ";
	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] s = c.getStatiPosizioniNonConclusivi();
	String p = "";
	for (StatoPagamentoType statoPagamentoType : s) {
	    p += statoPagamentoType.name() + ", ";
	}
	doc += p;
	Map<String, String> m = new HashMap<String, String>();
	m.put(PARAM_OVERRIDE_STATI_DA_VERIFICARE, doc);
	m.put(PARAM_SOLO_ALIAS_IN_LISTA,
		"Se Specificato processa solamente gli alias inseriti come parametro, altrimenti processa tutti gli alias configurati in SECURITY");
	return m;
    }

    private final static String PARAM_OVERRIDE_STATI_DA_VERIFICARE = "OVERRIDE_STATI_DA_VERIFICARE";
    private final static String PARAM_SOLO_ALIAS_IN_LISTA = "SOLO_ALIAS_IN_LISTA";
}
