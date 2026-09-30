package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

@Component("upgrUpdateTipologiaProtocolloTask")
public class UpdateTipologiaProtocolloTask extends BaseJavaTask {

    //private static final Logger log = LoggerFactory.getLogger(UpdateTipologiaProtocolloTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	activityLogInfo("UpdateTipologiaProtocolloTask.run: Inizio l'aggiornamento");
	String sql = "select TP_ID,TP_DESCRIZIONE,IDCOMUNE from PROT_TIPOLOGIAPROTOCOLLO ORDER BY IDCOMUNE,TP_ID ASC";
	Query query = session.createSQLQuery(sql);
	List list = query.list();
	String idcomune = "";
	BigDecimal codice = BigDecimal.ZERO;
	String descrizione = "";
	if (list.size() > 0) {
	    for (Object object : list) {
		Object[] result = (Object[]) object;
		idcomune = (String) result[2];
		descrizione = (String) result[1];
		codice = (BigDecimal) result[0];
		sql = "select CODICE,DESCRIZIONE,IDCOMUNE from PROTOCOLLO_TIPIDOCUMENTO where idcomune = :idcomune and codice = :codice";
		query = session.createSQLQuery(sql);
		query.setString("idcomune", idcomune);
		query.setInteger("codice", codice.intValue());
		List recordPresenti = query.list();
		if (recordPresenti.size() <= 0) {
		    sql = "insert into PROTOCOLLO_TIPIDOCUMENTO (CODICE,DESCRIZIONE,IDCOMUNE) VALUES (:codice,:descrizione,:idcomune)";
		    query = session.createSQLQuery(sql);
		    query.setString("idcomune", idcomune);
		    query.setInteger("codice", codice.intValue());
		    query.setString("descrizione", descrizione);
		    activityLogDebug("Inserisco in PROTOCOLLO_TIPIDOCUMENTO: ({},{},{})", new Object[] { idcomune, codice, descrizione });
		    int rowCount = query.executeUpdate();		    
		}
	    }
	}
	activityLogInfo("UpdateTipologiaProtocolloTask.run: Fine dell'aggiornamento");
	return 0;
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
