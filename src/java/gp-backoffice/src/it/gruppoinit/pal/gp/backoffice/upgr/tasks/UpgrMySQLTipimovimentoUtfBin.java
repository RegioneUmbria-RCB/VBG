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
 *  		&lt;java-task id="UPGR_MY_SQL_TIPIMOV_UTF8_BIN" spring-bean-id="upgrUpgrMySQLTipimovimentoUtfBinTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrMySQLTipimovimentoUtfBin"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpgrMySQLTipimovimentoUtfBinTask")
public class UpgrMySQLTipimovimentoUtfBin extends BaseJavaTask {

    @Autowired
    private IMySQLTipimovimentoUtfBinService service;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session arg0) throws SetupRunException {

	try {
	    List<String> upgrCollateUtf8 = service.upgrCollateUtf8();
	    if (upgrCollateUtf8.size() > 0) {
		StringBuffer messaggio = new StringBuffer();
		for (String m : upgrCollateUtf8) {
		    messaggio.append(StringUtils.defaultString(m)).append("\n");
		}
		handleErrorCondition(messaggio.toString());
	    }
	} catch (Exception e) {
	    handleErrorCondition(e, "Errore durante una operazione modifica collate delle tabelle referenziate da tipimovimento: " + e.getMessage());
	}
	return 0;
    }
}
