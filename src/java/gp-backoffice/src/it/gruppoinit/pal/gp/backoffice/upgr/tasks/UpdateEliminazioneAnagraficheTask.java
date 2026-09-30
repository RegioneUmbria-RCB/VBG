package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component("UpdateEliminazioneAnagraficheTask")
public class UpdateEliminazioneAnagraficheTask extends BaseJavaTask {

    @Autowired
    private AnagrafeService anagrafeService;

    /**
     * @param runFromDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) inizia l'esecuzione dalla data/ora. ES:<br />
     *            <b>&lt;param name="runFromDate" value="18/07/2011 - 16:06:01"&gt;&lt;/param&gt;</b>
     * 
     * @param runToDate
     *            (opzionale, Formato: dd/MM/yyyy - HH:mm:ss) termina l'esecuzione alla data/ora. ES:<br />
     *            <b>&lt;param name="runToDate" value="18/07/2011 - 16:07:30"&gt;&lt;/param&gt;</b>
     * 
     *            <pre>
     *  &lt;java-task id="UPGR_EliminazioneAnagraficheTask" 
     *  		spring-bean-id="upgrUpdateAnagrafeStoricoTask" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateEliminazioneAnagraficheTask" 
     * 			fail-on-error="false">         
     *         &lt;param name="runFromDate" value="26/01/2012 - 09:00:01">&lt;/param>
     *         &lt;param name="runToDate" value="26/01/2012 - 17:10:30">&lt;/param>
     * 
     * &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    @SuppressWarnings("unchecked")
    public int run(Session session) throws SetupRunException {

	String runFromDate = getParameterValue("runFromDate");
	String runToDate = getParameterValue("runToDate");
	Date runFrom = null;
	Date runTo = null;
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
	String origIdcomune = ORMHelper.getIdcomune();
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name(), false);
	serviceValidationRules.setCustomRule(ServiceValidationRules.CustomRuleEnum.doEntityValidation.name(), false);
	SigeproBusinessRules.setClassRules(ServiceValidationRules.class, serviceValidationRules);
	try {
	    activityLogInfo("UpdateAnagrafeStoricoTask: Inizio aggiornamento anagrafe");
	    // Ciclo le Anagrafiche
	    String hql = "select this_.id.codice, this_.id.idcomune from Anagrafe this_ order by this_.id.idcomune,this_.id.codice asc";
	    Query query = session.createQuery(hql);
	    activityLogInfo("Inizio la bonifica delle anagrafiche");
	    List<Object[]> listi = query.list();
	    Anagrafe anagrafe = null;
	    for (Object valoriQuery : listi) {
		if (!checkexecutionTime(runTo, executeWithTime)) {
		    return 0;
		}
		Object[] result = (Object[]) valoriQuery;
		Integer codiceAnagrafe = (Integer) result[0];
		String idcomune = (String) result[1];
		// devo settare l'idcomune per forza su ORMHelper
		ORMHelper.setIdcomune(idcomune);
		anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
		if (anagrafe != null) {
		    try {
			anagrafeService.delete(anagrafe);
			session.flush();
			this.commitTransaction();
			session.flush();
			session.clear();
		    } catch (DataAccessException e) {
			errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}",
				new Object[] { anagrafe.getDescrizioneRichiedente(), e.getMessage() });
			String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
				+ anagrafe.getDescrizioneRichiedente();
			handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		    } catch (BusinessValidationException e) {
			errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}",
				new Object[] { anagrafe.getDescrizioneRichiedente(), e.getMessage() });
			String errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
				+ anagrafe.getDescrizioneRichiedente();
			handleErrorWithBusinessValidationErrorMessage(e, errMsg);
		    } catch (Exception e) {
			String errMsg = "";
			errorLogError("Non è possibile cancellare l'anagrafica[{}] a causa di  {}",
				new Object[] { anagrafe.getDescrizioneRichiedente(), e.getMessage() });
			errMsg = "Errore nella cancellazione dell'anagrafica disabilitata [" + anagrafe.getId() + "], "
				+ anagrafe.getDescrizioneRichiedente();
			handleErrorCondition(e, errMsg);
		    }
		}
	    }
	} catch (Exception e) {
	    handleErrorCondition(e);
	} finally {
	    ORMHelper.setIdcomune(origIdcomune);
	    SigeproBusinessRules.buildDefaultRules();
	}
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }

    private void handleErrorWithBusinessValidationErrorMessage(Exception e, String errorMessage) {

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

    private boolean checkexecutionTime(Date runTo, boolean executeWithTime) {

	boolean run = true;
	if (executeWithTime) {
	    if (runTo.getTime() <= new Date().getTime()) {
		run = false;
		activityLogInfo("Terminato il tempo di esecuzione del task impostato a " + runTo);
	    }
	}
	return run;
    }
}
