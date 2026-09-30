package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("upgrUpdateMailconfigTask2")
public class UpdateMailConfigTask2 extends BaseJavaTask {

    @Autowired
    private MailConfigService mailConfigService;

    /**
     * <pre>
     * Recupero tutti i record di MAIL_CONFIG dell'installazione e per ogni record imposto:
     * 1.ID 			: incrementale per ogni record
     * 2.FLAG_PRINCIPALE 	: 1
     * 3.FLAG_DISABILITATO	: 0
     * 4.DESCRIZIONE		: "Account" + idcomune + "-" + software 
     * 
     * Il valore incrementale dell'ID non verrà azzerato al cambiamento dell'idcomune quindi si creano dei buchi sulla
     * sequence table (non crea problemi)
     * 
     * </pre>
     */
    @Override
    public int run(Session session) throws SetupRunException {

	int rowCount = 1;
	//String origIdComune = ORMHelper.getIdcomune();
	activityLogInfo("upgrUpdateMailconfigTask2.run: inizio aggiornamento");
	Query query = session.createSQLQuery("SELECT IDCOMUNE,SOFTWARE FROM MAIL_CONFIG WHERE ID IS NULL");
	List mailconfigs = query.list();
	activityLogInfo("upgrUpdateMailconfigTask2.run: cliclo record MAIL_CONFIG trovati e aggiorno i nuovi campi");
	for (Object object : mailconfigs) {
	    Object[] result = (Object[]) object;
	    StringBuffer sql = new StringBuffer("UPDATE MAIL_CONFIG SET ");
	    sql.append(" ID = ?,");
	    sql.append(" FLAG_DISABILITATO = ?,");
	    sql.append(" FLAG_PRINCIPALE = ?,");
	    sql.append(" DESCRIZIONE = ?");
	    sql.append(" WHERE IDCOMUNE = ? AND SOFTWARE= ?");
	    Query queryid = session.createSQLQuery(sql.toString());
	    queryid.setInteger(0, rowCount);
	    queryid.setInteger(1, 0);
	    queryid.setInteger(2, 1);
	    String descr = "Account " + (String) result[0] + "-" + (String) result[1];
	    queryid.setString(3, descr);
	    // CONDIZIONI
	    queryid.setString(4, (String) result[0]);
	    queryid.setString(5, (String) result[1]);
	    queryid.executeUpdate();
	    rowCount++;
	    session.flush();
	}
	this.commitTransaction();
	activityLogInfo("upgrUpdateMailconfigTask2.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
