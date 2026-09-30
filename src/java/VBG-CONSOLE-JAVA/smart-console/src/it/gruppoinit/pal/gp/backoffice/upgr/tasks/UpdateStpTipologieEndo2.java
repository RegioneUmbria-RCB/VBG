package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Component;

@Component("upgrUpdateStpTipologieEndo2")
public class UpdateStpTipologieEndo2 extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	String hql = "SELECT s.id.idcomune FROM StpEndoTipo2 s GROUP BY s.id.idcomune ORDER BY s.id.idcomune";
	Query query = session.createQuery(hql);
	activityLogInfo("Inizio l'inserimento dei record nella tabella stp_tipologia_endo2");
	List<String> listi = query.list();
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) session.getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "insert into " + schemaName + ".STP_TIPOLOGIE_ENDO2 (IDCOMUNE, ID, DESCRIZIONE) VALUES (?,?,?)";
	SQLQuery insertQuery = null;
	for (String idcomune : listi) {
	    insertQuery = session.createSQLQuery(sql);
	    insertQuery.setString(0, idcomune);
	    insertQuery.setInteger(1, 1);
	    insertQuery.setString(2, "Avvio");
	    insertQuery.executeUpdate();
	    insertQuery = session.createSQLQuery(sql);
	    insertQuery.setString(0, idcomune);
	    insertQuery.setInteger(1, 2);
	    insertQuery.setString(2, "Variazione");
	    insertQuery.executeUpdate();
	    insertQuery = session.createSQLQuery(sql);
	    insertQuery.setString(0, idcomune);
	    insertQuery.setInteger(1, 3);
	    insertQuery.setString(2, "Subingresso");
	    insertQuery.executeUpdate();
	    insertQuery = session.createSQLQuery(sql);
	    insertQuery.setString(0, idcomune);
	    insertQuery.setInteger(1, 4);
	    insertQuery.setString(2, "Comunicazione");
	    insertQuery.executeUpdate();
	    insertQuery = session.createSQLQuery(sql);
	    insertQuery.setString(0, idcomune);
	    insertQuery.setInteger(1, 5);
	    insertQuery.setString(2, "Chiusura");
	    insertQuery.executeUpdate();
	}
	session.flush();
	this.commitTransaction();
	activityLogInfo("terminato l'aggiornamento dei record STP_ENDO_TIPO1");
	session.clear();
	return 0;
    }
}
