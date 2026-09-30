package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_WARNING_QUERY_IATTIVITA" spring-bean-id="UpdateWarningQueryIattivitaTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateWarningQueryIattivitaTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateWarningQueryIattivitaTask")
public class UpdateWarningQueryIattivitaTask extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateWarningQueryIattivitaTask.run: inizio verifica");
	int rowCount = 0;
	String sql = "SELECT IDCOMUNE,SOFTWARE FROM verticalizzazioniparametri WHERE modulo=? AND PARAMETRO=?";
	SQLQuery query = session.createSQLQuery(sql);
	query.setString(0, "I_ATTIVITA");
	query.setString(1, "QUERYDENOMINAZIONE");
	List list = query.list();
	StringBuilder messaggio = new StringBuilder();
	boolean presenti = false;
	for (Object values : list) {
	    presenti = true;
	    Object[] vals = (Object[]) values;
	    String idcomune = (String) vals[0];
	    String software = (String) vals[1];
	    messaggio.append(idcomune).append(" - ").append(software).append(",<br />\n");
	    rowCount++;
	}
	if (presenti) {
	    messaggio.append(
		    "<br />Eseguire la query <b>SELECT * FROM verticalizzazioniparametri WHERE modulo='I_ATTIVITA' AND parametro='QUERYDENOMINAZIONE'</b>") //
		    .append("e per ogni record aggiungere l'alias DENOMINAZIONE per il campo da estrarre. ") //
		    .append("<br /> Es. da \"SELECT ISTANZE.NUMEROISTANZA as denominazione FROM ISTANZE....\" a ") //
		    .append(" \"SELECT ISTANZE.NUMEROISTANZA as DENOMINAZIONE FROM ISTANZE....\".");
	    handleErrorCondition(
		    "ATTENZIONE!!! Aggiungere l'alias (es \" as DENOMINAZIONE\") sulle query del parametro di verticalizzazione I_ATTIVITA.QUERYDENOMINAZIONE per i seguenti IDCOMUNE-SOFTWARE:\n<br />" +
				 messaggio.toString());
	}
	activityLogInfo("UpdateWarningQueryIattivitaTask.run: Fine aggiornamento");
	return rowCount;
    }
}
