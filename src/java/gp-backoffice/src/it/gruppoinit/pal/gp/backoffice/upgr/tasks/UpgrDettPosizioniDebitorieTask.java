package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpgrDettPosizioniDebitorieTask")
public class UpgrDettPosizioniDebitorieTask extends BaseJavaTask {

    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

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
     *  &lt;java-task id="UPGR_DETT_POSIZIONI_DEBITORIE" 
     *  		spring-bean-id="upgrUpgrDettPosizioniDebitorieTask" 
     *  		java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrDettPosizioniDebitorieTask" 
     * 			fail-on-error="false">
     *  &lt;/java-task>
     * </pre>
     * 
     * 
     */
    @Override
    public int run(Session arg0) throws SetupRunException {

	this.dettPosizioneDebitoriaService.upgradeDettPosizioniDebitorieDaBlackList();
	return 0;
    }
}
