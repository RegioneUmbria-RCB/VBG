package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.upgr.IUpgrDettPosizioniComuneSoftwareService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * Il task cerca di recuperare per le posizioni debitorie i dati delle colonne codicecomune,software per quei record che
 * non hanno settato il campo codice comune e software
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_DEETTPOSIZIONE_COMUNE_SOFTWARE" spring-bean-id="upgrDettPosizioneDebitoriaComuneSoftwareTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrDettPosizioneDebitoriaComuneSoftwareTask"
 * 			fail-on-error="true" autocommit="true"&gt;
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrDettPosizioneDebitoriaComuneSoftwareTask")
public class UpgrDettPosizioneDebitoriaComuneSoftwareTask extends BaseJavaTask {

    @Autowired
    private IUpgrDettPosizioniComuneSoftwareService upgrDettPosizioniComuneSoftwareService;

    @Override
    public void initialize() throws SetupRunException {

	// Non serve codice di inizializzazione
    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	activityLogInfo("Inizio la sistemazione delle posizioni debitorie senza codicec comune e software");
	String idcomuneOrig = ORMHelper.getIdcomune();
	String softwareOrig = ORMHelper.getSoftware();
	try {
	    List<String> errs = upgrDettPosizioniComuneSoftwareService.sistemaCodiceComuneESoftware();
	    if (!errs.isEmpty()) {
		StringBuilder message = new StringBuilder("Verificare :");
		for (String err : errs) {
		    message = message.append("\n").append(err);
		}
		handleErrorCondition(message.toString());
	    }
	} catch (Exception e) {
	    handleErrorCondition(e, e.getMessage());
	}
	activityLogInfo("Terminata la sistemazione delle posizioni debitorie senza codicec comune e software");
	ORMHelper.setIdcomune(idcomuneOrig);
	ORMHelper.setSoftware(softwareOrig);
	return 0;
    }
}
