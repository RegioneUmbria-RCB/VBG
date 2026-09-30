package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

/**
 * 
 * <pre>
 *  		&lt;java-task id="UPGR_ONERI_RATEIZZAZIONI_GUID" spring-bean-id="UpgrOneriRateizzazioniGUIDTask"
 * 				java-class="it.gruppoinit.pal.gp.backoffice.upgr.tasks.UpgrOneriRateizzazioniGUIDTask"
 * 				fail-on-error="false" autocommit="true">
 * 				
 * 		&lt;/java-task>
 * </pre>
 * 
 * @author riccardob
 *
 */
@Component("UpgrOneriRateizzazioniGUIDTask")
public class UpgrOneriRateizzazioniGUIDTask extends BaseJavaTask {

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpgrOneriRateizzazioniGUIDTask.run: inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	int rowCount = 0;
	String sql = "SELECT idcomune, codiceistanza, fkidtipocausale FROM istanzeoneri WHERE flag_onere_rateizzato = ? and guid_rateizzazione is null and codiceistanza is not null and fkidtipocausale is not null group by idcomune, codiceistanza, fkidtipocausale ORDER BY idcomune, codiceistanza";
	SQLQuery query = session.createSQLQuery(sql);
	query.setInteger(0, 1);
	//	
	List list = query.list();
	String sqlUpdate = "update istanzeoneri set guid_rateizzazione=? where idcomune=? and codiceistanza=? and fkidtipocausale=?";
	query.addScalar("guid_rateizzazione", Hibernate.STRING);
	query.addScalar("idcomune", Hibernate.STRING);
	query.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
	query.addScalar("fkidtipocausale", Hibernate.BIG_DECIMAL);
	query.addScalar("flag_onere_rateizzato", Hibernate.INTEGER);
	SQLQuery queryUpdate = null;
	//UPDATE
	queryUpdate = session.createSQLQuery(sqlUpdate);
	queryUpdate.addScalar("guid_rateizzazione", Hibernate.STRING);
	queryUpdate.addScalar("idcomune", Hibernate.STRING);
	queryUpdate.addScalar("codiceistanza", Hibernate.BIG_DECIMAL);
	queryUpdate.addScalar("fkidtipocausale", Hibernate.BIG_DECIMAL);
	for (Object values : list) {
	    Object[] vals = (Object[]) values;
	    String idcomune = (String) vals[0];
	    BigDecimal codiceistanza = (BigDecimal) vals[1];
	    BigDecimal fkidtipocausale = (BigDecimal) vals[2];
	    queryUpdate.setString(0, UUID.randomUUID().toString());
	    queryUpdate.setString(1, idcomune);
	    queryUpdate.setInteger(2, codiceistanza.intValue());
	    queryUpdate.setInteger(3, fkidtipocausale.intValue());
	    queryUpdate.executeUpdate();
	    // insert
	    session.flush();
	    this.commitTransaction();
	    session.flush();
	}
	ORMHelper.setIdcomune(origIdcomune);
	activityLogInfo("UpgrOneriRateizzazioniGUIDTask.run: Fine aggiornamento");
	return rowCount;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
