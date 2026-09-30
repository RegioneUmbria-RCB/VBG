package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;
import it.gruppoinit.upgr.xmldomain.JavaTaskParameter;

/**
 * viene eseguita la query che sta nella tabella task_elaborazione_istanze i campi tirati fuori in ordine saranno
 * codiceistanza,idcomune,software
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_CALCOLA_DATA_VALIDITA_CON_QUERY" spring-bean-id="upgrTaskCalcoloDataValiditaConQuery"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.TaskCalcoloDataValiditaConQuery"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 			&lt;param name="querySelezioneIstanza" value="select istanze.codiceistanza as codiceistanza,istanze.idcomune as idcomune,istanze.software as software from istanze left join (select idcomune,codiceistanza from movimenti where idcomune = 'G713' and tipomovimento in ('TT00045','TT00048') group by idcomune, codiceistanza) mov on istanze.idcomune= mov.idcomune and istanze.codiceistanza = mov.codiceistanza where istanze.idcomune='G713' and data &gt; to_date('20/04/2015','dd/MM/yyyy') and mov.codiceistanza  is null"&gt;&lt;/param&gt;
 * 			&lt;param name="userid" value="admin" /&gt;
 * 			
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrTaskCalcoloDataValiditaConQuery")
public class TaskCalcoloDataValiditaConQuery extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(TaskElaborazioneIstanze.class);
    @Autowired
    private IstanzeService istanzeService;

    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name(), false);
	SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	String errorMessage = "";
	try {
	    activityLogInfo("Inizio la bonifica delle istanze");
	    JavaTaskParameter juserid = findParam("userid"); //"Select query From task_elaborazione_istanze";
	    JavaTaskParameter jsql = findParam("querySelezioneIstanza"); //"Select query From task_elaborazione_istanze";
	    String querySQL = jsql.getValue();
	    verificaSQL(querySQL);
	    SQLQuery query = session.createSQLQuery(querySQL);
	    query.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
	    query.addScalar("idcomune", Hibernate.STRING);
	    query.addScalar("software", Hibernate.STRING);
	    List list = query.list();
	    login(juserid.getValue());
	    for (Object values : list) {
		Object[] vals = (Object[]) values;
		BigDecimal codiceIstanza = (BigDecimal) vals[0];
		String idcomune = (String) vals[1];
		String software = (String) vals[2];
		ORMHelper.setIdcomune(idcomune);
		ORMHelper.setSoftware(software);
		activityLogInfo("INIZIO calcolaDataValidita l'istanza [{}-{}]", new Object[] { codiceIstanza.intValue(), software });
		try {
		    istanzeService.calcolaDataValidita(codiceIstanza.intValue());
		} catch (Exception e) {
		    errorLogWarn("Errore in calcolaDataValidita dell'istanza " + codiceIstanza + ": " + e.getMessage() + "\n");
		}
		this.commitTransaction();
		session.flush();
		session.clear();
		activityLogInfo("FINE del calcolaDataValidita dell'istanza: {}", new Object[] { codiceIstanza });
	    }
	    SigeproBusinessRules.buildDefaultRules();
	    activityLogInfo("Terminata il calcolaDataValidita delle istanze");
	} catch (Exception e) {
	    if (e instanceof BusinessValidationException || e instanceof EntityValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    errorMessage += invalidValue.getMessage() +
			    ",[" +
			    invalidValue.getBeanClass() +
			    "],[" +
			    invalidValue.getPropertyPath() +
			    "],[" +
			    invalidValue.getPropertyName() +
			    "]," +
			    "\n";
		}
		//log.error("run: BusinessValidationError {}, [{}]", errorMessage, e.getMessage());
	    }
	    if (StringUtils.isBlank(errorMessage)) {
		errorMessage = e.getMessage();
	    }
	    errorMessage = MessageFormat.format("BusinessValidationError {0}, [{1}]", new Object[] { errorMessage, e.getMessage() });
	    handleErrorCondition(e, errorMessage);
	    //throw new RuntimeException(errorMessage, e);
	} finally {
	    ORMHelper.setIdcomune(origIdcomune);
	}
	return 0;
    }

    private void verificaSQL(String querySQL) {

	boolean rilanciaEccezione = false;
	if (querySQL.toLowerCase().indexOf("drop") >= 0) {
	    rilanciaEccezione = true;
	}
	if (querySQL.toLowerCase().indexOf("truncate") >= 0) {
	    rilanciaEccezione = true;
	}
	if (querySQL.toLowerCase().indexOf("insert") >= 0) {
	    rilanciaEccezione = true;
	}
	if (querySQL.toLowerCase().indexOf("create") >= 0) {
	    rilanciaEccezione = true;
	}
	if (querySQL.toLowerCase().indexOf("update") >= 0) {
	    rilanciaEccezione = true;
	}
	if (querySQL.toLowerCase().indexOf("delete") >= 0) {
	    rilanciaEccezione = true;
	}
	if (rilanciaEccezione) {
	    throw new SecurityException("Query [" + querySQL + "] non ammessa");
	}
    }

    @Override
    public void initialize() throws SetupRunException {

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
