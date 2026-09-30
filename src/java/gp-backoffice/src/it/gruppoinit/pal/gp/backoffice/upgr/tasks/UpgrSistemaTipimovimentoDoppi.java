package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.sistema.upgr.IMySQLTipimovimentoUtfBinService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_SISTEMA_TIPI_MOVIMENTI_DOPPI" spring-bean-id="upgrUpgrSistemaTipimovimentoDoppi"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrSistemaTipimovimentoDoppi"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpgrSistemaTipimovimentoDoppi")
public class UpgrSistemaTipimovimentoDoppi extends BaseJavaTask {

    @Autowired
    private IMySQLTipimovimentoUtfBinService service;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	try {
	    List<String> upgr = service.upgrTipiMovimentiDoppi();
	    if (upgr.size() > 0) {
		StringBuffer messaggio = new StringBuffer();
		for (String m : upgr) {
		    messaggio.append(StringUtils.defaultString(m)).append("\n");
		}
		handleErrorCondition(messaggio.toString());
	    }
	} catch (Exception e) {
	    handleErrorCondition(e, "Errore durante l'operazione di sistemazione dei tipimmovimenti doppi: " + e.getMessage());
	}
	return 0;
    }
}
