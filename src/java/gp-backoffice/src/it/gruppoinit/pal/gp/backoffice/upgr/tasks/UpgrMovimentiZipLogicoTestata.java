package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrMovimentiZipLogicoTestata")
public class UpgrMovimentiZipLogicoTestata extends BaseJavaTask {

    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;

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
     *  &lt;java-task id="UPGR_MOVIMENTIZIPLOGICO_TESTATA" 
     *  		spring-bean-id="upgrUpgrMovimentiZipLogicoTestata" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrMovimentiZipLogicoTestata" 
     * 			fail-on-error="false">
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    public int run(Session arg0) throws SetupRunException {

	this.movimentiZipLogicoService.upgrCreaTestate();
	return 0;
    }
}
