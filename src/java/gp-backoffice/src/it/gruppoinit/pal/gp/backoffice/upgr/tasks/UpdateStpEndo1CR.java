package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdateStpEndo1CR")
public class UpdateStpEndo1CR extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

    }

    @Override
    public int run(Session session) throws SetupRunException {

	String sql = "select se1.idcomune as idcomune, se1.id as id, codiceancitel as codiceancitel from inventarioprocedimenti ip inner join stp_endo_tipo1 se1 "
		+ "on ip.idcomune=se1.idcomune and ip.codiceinventario=se1.codiceinventario "
		+ " where se1.codice_endo_regionale is null order by se1.idcomune";
	SQLQuery q = session.createSQLQuery(sql);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("codiceancitel", Hibernate.STRING);
	activityLogInfo("Inizio l'aggiornamento dei record STP_ENDO_TIPO1");
	List<Object[]> listi = q.list();
	String hqlUpdate = "UPDATE StpEndoTipo1 s set s.codiceEndoRegionale=? where s.id.idcomune=? and s.id.codice=?";
	Query updateQuery = null;
	String idcomune = null;
	Integer id = null;
	String codiceAncitel = null;
	for (Object[] valori : listi) {
	    idcomune = (String) valori[0];
	    id = (Integer) valori[1];
	    codiceAncitel = (String) valori[2];
	    updateQuery = session.createQuery(hqlUpdate);
	    updateQuery.setString(0, codiceAncitel);
	    updateQuery.setString(1, idcomune);
	    updateQuery.setInteger(2, id);
	    updateQuery.executeUpdate();
	}
	session.flush();
	this.commitTransaction();
	activityLogInfo("terminato l'aggiornamento dei record STP_ENDO_TIPO1");
	session.clear();
	return 0;
    }
}
