package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.rules.SecurityServiceRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * viene eseguita la query che sta nella tabella task_elaborazione_istanze i campi tirati fuori in ordine saranno
 * codiceistanza,idcomune,software
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_ELABORA_SNAPSHOTS_ATTIVITA" spring-bean-id="upgrUpdateIattivitaSnapshotTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateIattivitaSnapshotTask"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 			&lt;param name="listaAttivitaDaElaborare" value="1,2,"&gt;&lt;/param&gt;
 * 			&lt;param name="software" value="CO"&gt;&lt;/param&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateIattivitaSnapshotTask")
public class UpdateIattivitaSnapshotTask extends BaseJavaTask {

    @Autowired
    private IAttivitaSnapshotService iAttivitaSnapshotService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	String listaIattivitaDaElaborare = getParameterValue("listaAttivitaDaElaborare");
	String software = getParameterValue("software");
	ORMHelper.setSoftware(StringUtils.defaultString(software, WebConstants.SOFTWARE_TT));
	String[] iatts = listaIattivitaDaElaborare.split(",");
	SecurityServiceRules securityServiceRules = (SecurityServiceRules) SigeproBusinessRules.getClassRules(SecurityServiceRules.class);
	securityServiceRules.setpermettiUtenteLoggatoNullo(true);
	SigeproBusinessRules.setClassRules(SecurityServiceRules.class, securityServiceRules);
	try {
	    for (String iatt : iatts) {
		iatt = StringUtils.defaultString(iatt).trim();
		if (Utilities.isInteger(iatt)) {
		    try {
			iAttivitaSnapshotService.updateElaboraSnapshotAttivita(Integer.parseInt(iatt));
			activityLogInfo("Attivita " + iatt + " elaborata");
		    } catch (Exception e) {
			handleErrorCondition(e, "Errore nell'elaborazione dell'attività ".concat(iatt));
		    }
		}
	    }
	} catch (Exception e) {
	    handleErrorCondition(e, "Errore nell'elaborazione dell'attività snapshot");
	} finally {
	    securityServiceRules = (SecurityServiceRules) SigeproBusinessRules.getClassRules(SecurityServiceRules.class);
	    securityServiceRules.setpermettiUtenteLoggatoNullo(false);
	    SigeproBusinessRules.setClassRules(SecurityServiceRules.class, securityServiceRules);
	}
	return 0;
    }
}
