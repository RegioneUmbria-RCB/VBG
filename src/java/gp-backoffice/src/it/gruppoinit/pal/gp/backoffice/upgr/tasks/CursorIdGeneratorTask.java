package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.gruppoinit.upgr.core.SetupRunException;

/**
 * <pre>
 * Questo task serve per popolare le colonne ID con valori staccati dalle sequence di VBG. E' utile quando si aggiunge
 * una colonna che entra a far parte della chiave primaria e che deve essere popolata prima della creazione della chiave
 * stessa. Il task richiede nell'XML i seguenti 3 parametri: 
 * 1) tableName: Obbligatorio. nome della tabella in cui valorizzare gli ID 
 * 2) idColumnName: Opzionale. Nome della colonna ID da popolare (di default viene utilizzato 'ID').
 * 3) idComuneColumnName: Opzionale. Nome della colonna in cui è memorizzato l'id comune (di default viene utilizzato IDCOMUNE). 
 * 4) oldPkColumns: Opzionale. Stringa che contiene l'elenco dei campi in chiave primaria separati da virgole. 
 * La presenza di questo parametro è fondamentale per consentire l'utilizzo di ResultSet aggiornabili in MySQL e MS SQL Server 
 * 5) sequenceName: Opzionale. Nome della sequence con cui interrogare la tabella SEQUENCETABLE. Di default viene utilizzato 'tableName.idColumnName'.
 * 
 * NOTA: Il task per poter funzionare richiede che il DB e il driver utilizzato supportino ResultSet.CONCUR_UPDATABLE
 * Per ottenere dei ResutSet CONCUR_UPDATABLE in SQL Server e MySQL occorre che tutti i campi in chiave primaria siano in select. 
 * Nel caso di MySQL è anche necessario che i campi in chiave primaria non compaiano più di una volta. 
 * Quindi se IDCOMUNE fa parte della PK occorre non ripeterlo nel valore del parametro 'oldPkColumns'.
 * </pre>
 */
@Component("upgrCursorIdGeneratorTask")
public class CursorIdGeneratorTask extends IdGeneratorTask {

    //private static final Logger log = LoggerFactory.getLogger(CursorIdGeneratorTask.class);
    private Session runningSession;
    private String sequenceName;
    private String tableName;

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.backoffice.upgr.tasks.IdGeneratorTask#run(org.hibernate.Session)
     */
    @Override
    public int run(Session session) throws SetupRunException {

	this.runningSession = session;
	int numUpdated = 0;
	CursorIdGeneratorResultSetWork work = null;
	tableName = getParameterValue("tableName");
	String newIdColumnName = getParameterValue("idColumnName");
	if (newIdColumnName == null) {
	    newIdColumnName = "ID";
	}
	String idComuneColumnName = getParameterValue("idComuneColumnName");
	if (idComuneColumnName == null) {
	    idComuneColumnName = "IDCOMUNE";
	}
	//occorre mettere in select anche i campi che formano la PK per consentire i ResultSet.CUNCUR_UPDATABLE anche su MySQL e MS SQL Server
	String oldPkColumns = getParameterValue("oldPkColumns");
	sequenceName = getParameterValue("sequenceName");
	if (sequenceName == null || sequenceName.length() == 0) {
	    sequenceName = tableName + "." + newIdColumnName;
	}
	activityLogInfo("Inizio generazione degli ID per tableName: {}, idColumnName: {}, sequenceName: {}",
		new Object[] { tableName, newIdColumnName, sequenceName });
	try {
	    if (tableName != null) {
		session.setCacheMode(CacheMode.IGNORE);
		//leggo i record da aggiornare
		StringBuilder sb = new StringBuilder("SELECT ");
		sb.append(idComuneColumnName).append(", ");
		sb.append(newIdColumnName);
		if (oldPkColumns != null && oldPkColumns.length() > 0) {
		    sb.append(", ").append(oldPkColumns);
		}
		sb.append(" FROM ").append(tableName);
		sb.append(" WHERE ").append(newIdColumnName);
		sb.append(" IS NULL order by ").append(idComuneColumnName);
		work = new CursorIdGeneratorResultSetWork(this);
		work.setSql(sb.toString());
		session.doWork(work);
	    } else {
		handleErrorCondition("Impossibile individuare la tabella in cui impostare gli ID.");
	    }
	} catch (HibernateException e) {
	    handleErrorCondition(e, "Errore durante una operazione di lettura o scrittura sul DB.");
	} catch (Exception e) {
	    handleErrorCondition(e, "Errore generico nella creazione degli ID.");
	}
	this.runningSession = null;
	if (work != null) {
	    numUpdated = work.countUpdatedRows();
	}
	activityLogDebug("Generazione degli ID per {} completata. Aggiornati {} record", new Object[] { tableName, numUpdated });
	return numUpdated;
    }

    public int getSequenceCurrentValue(String idComune) {

	return super.getOrCreateSequenceCurrval(runningSession, sequenceName, idComune);
    }

    public void updateSequenceCurrentValue(String idComune, int currentValue) {

	super.updateSequence(runningSession, sequenceName, idComune, currentValue);
    }

    public void logProgress(int numUpdated) {

	activityLogDebug("Tabella: {}. ID aggiornati: {}", new Object[] { tableName, numUpdated });
    }
}
