package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.features.commissioni.upgr.CdsReport;
import it.gruppoinit.pal.gp.core.features.commissioni.upgr.UpgrFromCdsService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;
/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_CDS_TO_COMMISSIONI_TASK" spring-bean-id="upgrUpgrCdsToCommissioniTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrCdsToCommissioniTask"
 * 				fail-on-error="false" autocommit="true">				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpgrCdsToCommissioniTask")
public class UpgrCdsToCommissioniTask extends BaseJavaTask {

    @Autowired
    private UpgrFromCdsService upgrFromCdsService;

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	List<CdsReport> report = upgrFromCdsService.eseguiUpgr();
	if (report.size() > 0) {
	    StringBuilder messaggio = new StringBuilder("ATTENZIONE!!! Non è stato possibile convertire le seguenti cds a commissioni:\n<br />");
	    for (CdsReport cdsReport : report) {
		messaggio.append("idcomune: ") // 
			.append(cdsReport.getIdcomune()) //
			.append(", cds: ") //
			.append(cdsReport.getCodiceCds()) //
			.append(", istanza: ")// 
			.append(cdsReport.getCodiceIstanza());
		for (String err : cdsReport.listaErrori()) {
		    messaggio.append("\n<br/>").append(err);
		}
	    }
	    handleErrorCondition(messaggio.toString());
	}
	return 0;
    }
}
