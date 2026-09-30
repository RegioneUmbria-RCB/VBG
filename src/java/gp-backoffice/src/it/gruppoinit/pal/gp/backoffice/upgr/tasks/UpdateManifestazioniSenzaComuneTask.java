package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * Aggiorna il campo codicecomune per quei mercati che non hanno la proprietà popolata e che non appartengano ad una
 * installazione comuniassociati
 *
 * <pre>
 * 
 * 		&lt;java-task id="UPGR_UPDATE_MANIFESTAZIONI_CODICECOMUNE" spring-bean-id="upgrUpdateManifestazioniSenzaComuneTask"
 * 			java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpdateManifestazioniSenzaComuneTask"
 * 			fail-on-error="true" autocommit="true"&gt;	
 * 		&lt;/java-task&gt;
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("upgrUpdateManifestazioniSenzaComuneTask")
public class UpdateManifestazioniSenzaComuneTask extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateManifestazioniSenzaComuneTask.run: inizio aggiornamento");
	int rowCount = 0;
	String sql = "Select idcomune, codicemercato From mercati where coalesce(codicecomune, ?) = ? ";
	sql = sql + " order by idcomune,codicemercato asc";
	SQLQuery query = session.createSQLQuery(sql);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("codicemercato", Hibernate.INTEGER);
	query.setString(0, "NON_CONFIGURATO");
	query.setString(1, "NON_CONFIGURATO");
	// Recupero id
	// Insert mappature
	List mercati = query.list();
	SQLQuery DETTAGLIO_COMUNI_ASSOCIATI = session.createSQLQuery("SELECT codicecomune FROM comuniassociati WHERE idcomune=? ");
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("codiceoggetto", Hibernate.INTEGER);
	Set<String> idcomuneNonProcessati = new HashSet<String>();
	SQLQuery UPDATE_COMUNI_ASSOCIATI = session
		.createSQLQuery("update mercati set codicecomune=? WHERE idcomune=? and codicemercato=? and codicecomune is null");
	if (mercati.size() > 0) {
	    for (Object object : mercati) {
		Object[] row = (Object[]) object;
		String idcomune = (String) row[0];
		Integer codiceMercato = (Integer) row[1];
		activityLogInfo("UpdateManifestazioniSenzaComuneTask.run: processo il codice mercato " + idcomune + "-" + codiceMercato);
		DETTAGLIO_COMUNI_ASSOCIATI.setString(0, idcomune);
		List o = DETTAGLIO_COMUNI_ASSOCIATI.list();
		if (o.size() == 1) {
		    String codiceComune = (String) o.get(0);
		    UPDATE_COMUNI_ASSOCIATI.setString(0, codiceComune);
		    UPDATE_COMUNI_ASSOCIATI.setString(1, idcomune);
		    UPDATE_COMUNI_ASSOCIATI.setInteger(2, codiceMercato);
		    UPDATE_COMUNI_ASSOCIATI.executeUpdate();
		    this.commitTransaction();
		    session.flush();
		} else {
		    idcomuneNonProcessati.add(idcomune);
		    activityLogInfo(
			    "UpdateManifestazioniSenzaComuneTask.run: " + idcomune + "-" + codiceMercato + " ha " + o.size() + " comuni associati ");
		}
	    }
	}
	if (idcomuneNonProcessati.size() > 0) {
	    String msg = "Non è stato possibile aggiornare i dati delle manifestazioni (codicecomune) per i seguenti IDCOMUNE: ";
	    for (String idcomune : idcomuneNonProcessati) {
		msg += idcomune + ", ";
	    }
	    msg += "\nAvvisare i referenti della Formazione.";
	    handleErrorCondition(msg);
	}
	activityLogInfo("UpdateManifestazioniSenzaComuneTask.run: Fine aggiornamento");
	return rowCount;
    }
}
