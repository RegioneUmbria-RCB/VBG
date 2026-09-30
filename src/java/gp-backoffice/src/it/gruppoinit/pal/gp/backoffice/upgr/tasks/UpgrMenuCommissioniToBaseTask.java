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
 *  		&lt;java-task id="UPGR_MENU_COMMISSIONI_TO_BASE_TASK" spring-bean-id="upgrUpgrMenuCommissioniToBaseTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrMenuCommissioniToBaseTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpgrMenuCommissioniToBaseTask")
public class UpgrMenuCommissioniToBaseTask extends BaseJavaTask {

    @Autowired
    private UpgrFromCdsService upgrFromCdsService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	upgrFromCdsService.migraMenuSuSoftwareTT();
	return 0;
    }
}
