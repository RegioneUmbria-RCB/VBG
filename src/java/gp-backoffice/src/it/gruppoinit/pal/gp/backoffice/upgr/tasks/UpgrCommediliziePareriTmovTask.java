package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.commissioni.upgr.UpgrFromCdsService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_COMM_EDILIZIE_PARERI_MOV_TASK" spring-bean-id="upgrUpgrCommediliziePareriTmovTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrCommediliziePareriTmovTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpgrCommediliziePareriTmovTask")
public class UpgrCommediliziePareriTmovTask extends BaseJavaTask {

    @Autowired
    private UpgrFromCdsService upgrFromCdsService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	try {
	    upgrFromCdsService.migraCommediTipopareriMov();
	} catch (Exception e) {
	    handleErrorCondition(e, e.getMessage());
	}
	return 0;
    }
}
