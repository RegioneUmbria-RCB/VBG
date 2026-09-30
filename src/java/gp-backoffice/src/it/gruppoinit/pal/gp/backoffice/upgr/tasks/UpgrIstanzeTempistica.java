package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpgrIstanzeTempistica")
public class UpgrIstanzeTempistica extends BaseJavaTask {

    @Autowired
    private IstanzeService istanzeService;

    @Override
    public void initialize() throws SetupRunException {

    }

    /**
     * 
     * 
     * 
     * 
     * parametri ammessi:
     * 
     * @param runFromDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) inizia l'esecuzione dalla data/ora. ES:<br />
     *            <b>&lt;param name="runFromDate" value="18/07/2011 - 16:06:01"&gt;&lt;/param&gt;</b><br />
     * <br />
     * @param runToDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) termina l'esecuzione alla data/ora. ES:<br />
     *            <b>&lt;param name="runToDate" value="18/07/2011 - 16:07:30"&gt;&lt;/param&gt;</b><br />
     * <br />
     * @param elaboraTutte
     *            (opzionale, Formato: true/false) elabora anche quello con ggAggiuntivi<>null (DEFAULT false) ES:<br />
     *            <b>&lt;param name="elaboraTutte" value="false"&gt;&lt;/param&gt;</b>
     * 
     *            <pre>
     *  &lt;java-task id="UPGR_ISTANZETEMPISTICA" 
     *  		spring-bean-id="upgrUpgrIstanzeTempistica" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrIstanzeTempistica" 
     * 			fail-on-error="false">
     *         &lt;param name="runFromDate" value="26/01/2012 - 09:00:01">&lt;/param>
     *         &lt;param name="runToDate" value="26/01/2012 - 17:10:30">&lt;/param>
     *         &lt;param name="elaboraTutte" value="true">&lt;/param>
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    public int run(Session session) throws SetupRunException {

	String origIdcomune = ORMHelper.getIdcomune();
	String runFromDate = getParameterValue("runFromDate");
	String runToDate = getParameterValue("runToDate");
	String elaboraTutte = getParameterValue("elaboraTutte");
	boolean elaboraTutteBool = false;
	if (StringUtils.defaultString(elaboraTutte).equalsIgnoreCase("true")) {
	    elaboraTutteBool = true;
	}
	Date runFrom = null;
	Date runTo = null;
	boolean run = true;
	boolean executeWithTime = false;
	if (runFromDate != null && runToDate != null) {
	    SimpleDateFormat sdf2 = new SimpleDateFormat(WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN);
	    try {
		runFrom = sdf2.parse(runFromDate);
		runTo = sdf2.parse(runToDate);
	    } catch (ParseException e) {
		String errMsg = MessageFormat.format("Il parametro 'runFromDate' o 'runToDate' non è formattato correttamente [{0},{1}]",
			new Object[] { runFromDate, runToDate });
		throw new RuntimeException(errMsg);
	    }
	    executeWithTime = true;
	}
	if (runFromDate != null) {
	    while (true) {
		if (runTo != null) {
		    if (runTo.getTime() <= new Date().getTime()) {
			activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
			return 0;
		    }
		}
		if (runFrom.getTime() <= new Date().getTime()) {
		    break;
		}
	    }
	}
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name(), false);
	SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	String hql = "select this_.id.codice, this_.id.idcomune, software.codice from Istanze this_ left join this_.istanzeTempistica it inner join this_.software software ";
	if (!elaboraTutteBool) {
	    hql += " where it.ggaggiuntivi is null ";
	}
	hql += "order by this_.id.idcomune, software.codice, this_.data desc";
	Query query = session.createQuery(hql);
	activityLogInfo("Inizio il calcolo della tempistica per le istanze");
	List<Object[]> listi = query.list();
	int rsSize = listi.size();
	activityLogInfo(rsSize + " istanze da elaborare");
	int i = 0;
	int c = 0;
	Integer codiceIstanza = null;
	String idcomune = null;
	String software = null;
	Object[] result = null;
	for (Object valoriQuery : listi) {
	    i++;
	    if (run) {
		if (!checkexecutionTime(runTo, executeWithTime)) {
		    SigeproBusinessRules.buildDefaultRules();
		    return 0;
		}
		result = (Object[]) valoriQuery;
		codiceIstanza = (Integer) result[0];
		idcomune = (String) result[1];
		software = (String) result[2];
		// devo settare l'idcomune per forza su ORMHelper
		ORMHelper.setIdcomune(idcomune);
		ORMHelper.setSoftware(software);
		activityLogInfo("elaboro l'istanza [" + idcomune + "-" + codiceIstanza + "]");
		if (codiceIstanza != null) {
		    try {
			istanzeService.elabora(codiceIstanza, true);
			this.commitTransaction();
			session.flush();
		    } catch (Exception e) {
			handleErrorWithBusinessValidationErrorMessage(e, "Errore nel calcolo della tempistica per la istanza " + codiceIstanza + " "
				+ idcomune);
		    }
		}
	    }
	    if (i % 50 == 0) {
		activityLogInfo("===============>Elaborate " + i + " istanze di " + rsSize);
	    }
	    session.clear();
	}
	activityLogInfo("Terminata l'elaborazione di " + rsSize + " istanze");
	this.commitTransaction();
	session.flush();
	session.clear();
	ORMHelper.setIdcomune(origIdcomune);
	SigeproBusinessRules.buildDefaultRules();
	return 0;
    }

    protected void handleErrorWithBusinessValidationErrorMessage(Exception e, String errorMessage) {

	StringBuilder sbErr = new StringBuilder();
	if (errorMessage == null && e != null) {
	    errorMessage = e.getMessage();
	}
	if (errorMessage != null) {
	    sbErr.append(errorMessage);
	}
	if (e instanceof BusinessValidationException || e instanceof EntityValidationException) {
	    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
	    sbErr.append(". BusinessValidationError: ");
	    for (InvalidValue invalidValue : ivs) {
		sbErr.append(invalidValue.getMessage()).append(",[");
		sbErr.append(invalidValue.getBeanClass()).append("],[");
		sbErr.append(invalidValue.getPropertyPath()).append("],[");
		sbErr.append(invalidValue.getPropertyName()).append("],[");
		sbErr.append(invalidValue.getValue()).append("]\r\n");
	    }
	}
	handleErrorCondition(e, sbErr.toString());
    }

    protected boolean checkexecutionTime(Date runTo, boolean executeWithTime) {

	boolean run = true;
	if (executeWithTime) {
	    if (runTo.getTime() <= System.currentTimeMillis()) {
		run = false;
		activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
	    }
	}
	return run;
    }
}
