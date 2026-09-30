package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upgrUpdateTipimovimentoDisTask")
public class UpdateTipimovimentoDisTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateTipimovimentoDisTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpgrTipimovimentoDisTask inizio aggiornamento");
	String origIdcomune = ORMHelper.getIdcomune();
	int rowCount = 0;
	Query query = null;
	// ELIMINO I MOVIMENTI CON DATA = NULL
	try {
	    activityLogInfo("run: UpgrTipimovimentoDisTask elimino i movimenti non eseguiti e disabilitati");
	    query = session.createQuery("delete from Movimenti m where m.data is null and m.flagDisabilitato = ?");
	    query.setBoolean(0, Boolean.TRUE);
	    rowCount = query.executeUpdate();
	} catch (Exception e) {
	    errorLogError("Errore nella cancellazione dei movimenti non eseguiti e disabilitati: " + e.getMessage(), e);
	}
	activityLogInfo("run: UpgrTipimovimentoDisTask eliminati {} movimenti non eseguiti e disabilitati", new Object[]{rowCount});
	String sqlQuery = "SELECT IDCOMUNE,CODICEISTANZA,CODICEINVENTARIO,CODICEAMMINISTRAZIONE,TIPOMOVIMENTO,DATASCAD "
		+ "FROM TIPIMOVIMENTO_DIS order by IDCOMUNE ASC";
	String insertQuery = "insert into movimenti (idcomune,codicemovimento,tipomovimento,codiceistanza,codiceinventario,codiceamministrazione,data_scadenza,flag_disabilitato)"
		+ " values (?,?,?,?,?,?,?,1)";
	query = session.createSQLQuery(sqlQuery);
	List rs = query.list();
	String idcomune = "";
	BigDecimal codiceIstanza = BigDecimal.ZERO;
	BigDecimal codiceInventario = BigDecimal.ZERO;
	BigDecimal codiceAmministrazione = BigDecimal.ZERO;
	BigDecimal codice = BigDecimal.ZERO;
	String tipoMovimento = "";
	Date dataScadenza = null;
	for (Object object : rs) {
	    Object[] result = (Object[]) object;
	    idcomune = (String) result[0];
	    codiceIstanza = (BigDecimal) result[1];
	    if (result[2] != null) {
		codiceInventario = (BigDecimal) result[2];
	    }
	    if (result[3] != null) {
		codiceAmministrazione = (BigDecimal) result[3];
	    }
	    tipoMovimento = (String) result[4];
	    dataScadenza = (Date) result[5];
	    String sql = "SELECT MAX(codicemovimento)  FROM movimenti WHERE IDCOMUNE=?";
	    Query queryid = session.createSQLQuery(sql);
	    queryid.setString(0, idcomune);
	    List<BigDecimal> max = queryid.list();
	    for (BigDecimal bigDecimal : max) {
		codice = bigDecimal.add(BigDecimal.ONE);
	    }
	    if (codice == null) {
		codice = BigDecimal.ONE;
	    }
	    activityLogDebug("run: UpgrTipimovimentoDisTask max codicemovimenti {}", new Object[]{codice});
	    query = session.createSQLQuery(insertQuery);
	    query.setString(0, idcomune);
	    query.setBigDecimal(1, codice);
	    query.setString(2, tipoMovimento);
	    query.setBigDecimal(3, codiceIstanza);
	    if (result[2] != null) {
		if (codiceInventario.intValue() != 0) {
		    query.setBigDecimal(4, codiceInventario);
		} else {
		    query.setParameter(4, null, Hibernate.BIG_DECIMAL);
		}
	    } else {
		query.setParameter(4, null, Hibernate.BIG_DECIMAL);
	    }
	    if (result[3] != null) {
		query.setBigDecimal(5, codiceAmministrazione);
	    } else {
		query.setBigDecimal(5, BigDecimal.ZERO);
	    }
	    query.setDate(6, dataScadenza);
	    query.executeUpdate();
	    activityLogDebug("run: UpgrTipimovimentoDisTask inserito il movimento {},{}", new Object[]{codice, idcomune});
	    codice = codice.add(BigDecimal.ONE);
	    String sbCurrVal = "UPDATE SEQUENCETABLE SET CURRVAL = ? WHERE SEQUENCENAME = ? and IDCOMUNE=?";
	    SQLQuery qCurrVal = session.createSQLQuery(sbCurrVal);
	    qCurrVal.setBigDecimal(0, codice);
	    qCurrVal.setString(1, "MOVIMENTI.CODICEMOVIMENTO");
	    qCurrVal.setString(2, idcomune);
	    rowCount = qCurrVal.executeUpdate();
	    activityLogDebug("sequenza aggiornata");
	}
	this.commitTransaction();
	ORMHelper.setIdcomune(origIdcomune);
	activityLogInfo("UpgrTipimovimentoDisTask fine aggiornamento");
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
