package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component("upgrUpdateOggettiMetadatiFileTsdTask")
public class UpdateOggettiMetadatiFileTsdTask extends BaseJavaTask {

    @Override
    public void initialize() throws SetupRunException {

	// TODO Auto-generated method stub
    }

    /**
     * verifica se ci sono oggetti con estenzione .tsd senza il metadato file-content-type ed eventualmente lo inserisce
     */
    @Override
    public int run(Session session) throws SetupRunException {

	int rowCount = 1;
	activityLogInfo("upgrUpdateOggettiMetadatiFileTsdTask.run: inizio aggiornamento");
	Query query = session.createSQLQuery("SELECT IDCOMUNE, CODICEOGGETTO FROM OGGETTI WHERE lower(NOMEFILE) LIKE '%.tsd'");
	List oggettis = query.list();
	activityLogInfo("upgrUpdateOggettiMetadatiFileTsdTask.run: cliclo record MAIL_CONFIG trovati e aggiorno i nuovi campi");
	for (Object object : oggettis) {
	    Object[] result = (Object[]) object;
	    //Object[] result = (BigDecimal[]) object;
	    StringBuffer sql = new StringBuffer("SELECT CODICEOGGETTO ");
	    sql = sql.append(" FROM OGGETTI_METADATI ");
	    sql = sql.append(" WHERE OGGETTI_METADATI.CHIAVE='FILE_CONTENT_TYPE' ");
	    sql = sql.append(" AND upper(OGGETTI_METADATI.VALORE) = 'APPLICATION/TIMESTAMPED-DATA' ");
	    sql = sql.append(" AND IDCOMUNE = ? ");
	    sql = sql.append(" AND CODICEOGGETTO =? ");
	    Query queryid = session.createSQLQuery(sql.toString());
	    queryid.setString(0, (String) result[0]);
	    queryid.setBigDecimal(1, (BigDecimal) result[1]);
	    List oggettisCheckMetadataFilecontent = queryid.list();
	    if (oggettisCheckMetadataFilecontent.isEmpty()) {
		// 385704
		StringBuffer sqlInsert = new StringBuffer(" INSERT INTO oggetti_metadati (idcomune, codiceoggetto, CHIAVE, VALORE) VALUES (?,?,?,?)");
		Query queryUpdate = session.createSQLQuery(sqlInsert.toString());
		queryUpdate.setString(0, (String) result[0]);
		queryUpdate.setBigDecimal(1, (BigDecimal) result[1]);
		queryUpdate.setString(2, "FILE_CONTENT_TYPE");
		queryUpdate.setString(3, "application/timestamped-data");
		try {
		    queryUpdate.executeUpdate();
		} catch (Exception e) {
		    activityLogInfo("upgrUpdateOggettiMetadatiFileTsdTask# Errore Inserimento metadato FILE_CONTENT_TYPE = application/timestamped-data per codice oggetto =  "
			    + object);
		}
	    }
	    session.flush();
	}
	this.commitTransaction();
	activityLogInfo("upgrUpdateOggettiMetadatiFileTsdTask.run: Fine aggiornamento");
	return rowCount;
    }
}
