package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.IUpgrCfEnteCreditoreVuotoService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrCfEnteCreditoreVuotoTask")
public class UpgrCfEnteCreditoreVuotoTask extends BaseJavaTask {

    @Autowired
    private IUpgrCfEnteCreditoreVuotoService service;

    @Override
    public void initialize() throws SetupRunException {

	//implementazione non necessaria
    }

    /**
     * 
     * 
     * 
     * 
     * non necessita di parametri, in quanto viene effettuata per le testate mancanti di tutti gli idcomune
     * 
     * <pre>
     *  &lt;java-task id="UPGR_CF_ENTE_CREDITORE_VUOTO" 
     *  		spring-bean-id="upgrUpgrCfEnteCreditoreVuotoTask" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrCfEnteCreditoreVuotoTask" 
     * 			fail-on-error="false">
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    public int run(Session arg0) throws SetupRunException {

	this.service.aggiornaCfEnteCreditoreVuoto();
	return 0;
    }
}
