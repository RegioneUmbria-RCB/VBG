package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.CacheMode;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Component;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;
import it.gruppoinit.upgr.hibernate.PreparedStatementWork;

/**
 * <pre>
 * Questo task serve per popolare le colonne ID con valori staccati dalle sequence di VBG. E' utile quando si
 * aggiunge una colonna che entra a far parte della chiave primaria e che deve essere popolata prima della creazione
 * della chiave stessa. Il task richiede nell'XML i seguenti 3 parametri: 
 * 1) entityClass: Obbligatorio. Nome dell'entità di Hibernate da cui vengono recuperati i metadati come il nome della tabella e il nome della sequence 
 * da staccare (nella classe deve essere utilizzata la annotation GenericGenerator con il PkIdGenerator di VBG). 
 * 2) idColumnName: Opzionale. Nome della colonna ID da popolare (di default viene utilizzato 'ID'). 
 * 3) oldPkColumns: Opzionale. Elenco dei nomi delle colonne che facevano parte della precedente chiave primaria, separati da spazi o
 * virgole queste colonne devono costituire una chiave naturale che consenta l'identificazione univoca dei record da
 * popolare. Se il parametro viene omesso il record sarà identificato solo per IDCOMUNE. Il campo IDCOMUNE viene
 * utilizzato comunque per identificare il record da aggiornare isieme a quelli passati nel parametro, perciò se
 * IDCOMUNE fa parte della chiave naturale può comunque essere omesso dal valore del parametro.
 * 
 * NOTA: Non testato so PostgresSQL!!!!
 * </pre>
 */
@Component("upgrIdGeneratorTask")
public class IdGeneratorTask extends BaseJavaTask {

    private static final String SELECT_MAX_CURRVAL_VAL_FROM_SEQUENCETABLE_WHERE_SEQUENCENAME_AND_IDCOMUNE = "SELECT MAX(CURRVAL) VAL FROM SEQUENCETABLE WHERE SEQUENCENAME = ? AND IDCOMUNE = ?";
    private static final String UPDATE_SEQUENCETABLE_SET_CURRVAL_WHERE_SEQUENCENAME_AND_IDCOMUNE = "UPDATE SEQUENCETABLE SET CURRVAL = ? WHERE SEQUENCENAME = ? AND IDCOMUNE = ?";
    private static final String INSERT_INTO_SEQUENCETABLE_IDCOMUNE_SEQUENCENAME_CURRVAL_VALUES = "INSERT INTO SEQUENCETABLE (IDCOMUNE, SEQUENCENAME, CURRVAL) VALUES (?, ?, ?)";
    //private static final Logger log = LoggerFactory.getLogger(IdGeneratorTask.class);

    @Override
    public int run(Session session) throws SetupRunException {

	int numUpdated = 0;
	String entityName = getParameterValue("entityClass");
	String newIdColumnName = getParameterValue("idColumnName");
	if (newIdColumnName == null) {
	    newIdColumnName = "ID";
	}
	String oldPkColumns = getParameterValue("oldPkColumns");//"IDCOMUNE, CODICEISTANZA, CODICEDOCUMENTO";
	if (oldPkColumns == null) {
	    oldPkColumns = "";
	}
	activityLogInfo("Inizio generazione deglil ID per entityClass: {}, idColumnName: {}, oldPkColumns: {}",
		new Object[] { entityName, newIdColumnName, oldPkColumns });
	try {
	    //Pattern p = Pattern.compile("[\\s,]+");
	    //Matcher m = p.matcher(oldPkColumns);
	    String[] oldColumnsArray = oldPkColumns.split("[\\s,]+");
	    ClassMetadata cmeta = session.getSessionFactory().getClassMetadata(entityName);
	    if (cmeta != null) {
		Class entityClass = cmeta.getMappedClass(EntityMode.POJO);
		if (entityClass != null) {
		    Table ann = ((AnnotatedElement) entityClass).getAnnotation(Table.class);
		    String tableName = ann.name();
		    String sequenceName = null;
		    //recupero il nome della sequence da staccare
		    Method[] methods = entityClass.getMethods();
		    for (Method method : methods) {
			if (method.isAnnotationPresent(GenericGenerator.class)) {
			    GenericGenerator gg = method.getAnnotation(GenericGenerator.class);
			    Parameter[] ggParams = gg.parameters();
			    for (Parameter parameter : ggParams) {
				if (parameter.name().equals(PkIdGenerator.SEGMENT_VALUE_PARAM)) {
				    sequenceName = parameter.value();
				    break;
				}
			    }
			    break;
			}
		    }
		    if (sequenceName != null) {
			session.setCacheMode(CacheMode.IGNORE);
			//leggo i record da aggiornare
			StringBuilder sb = new StringBuilder("SELECT IDCOMUNE IDC,");
			StringBuilder sbUpdate = new StringBuilder("UPDATE ");
			sbUpdate.append(tableName).append(" SET ").append(newIdColumnName).append(" = ? WHERE IDCOMUNE = ? AND");
			for (String column : oldColumnsArray) {
			    sb.append(" ");
			    sb.append(column);
			    sb.append(",");
			    sbUpdate.append(" ").append(column).append(" = ? AND");
			}
			sb.deleteCharAt(sb.length() - 1);
			sbUpdate.delete(sbUpdate.length() - 3, sbUpdate.length());
			sb.append(" FROM ").append(tableName);
			sb.append(" WHERE ").append(newIdColumnName).append(" IS NULL order by IDCOMUNE");
			SQLQuery q = null;
			int startFrom = 0;
			int maxRows = 200;
			boolean readOn = true;
			String idComune = "";
			int intVal = 0;
			int numUpdatedPerComune = 0;
			List<Object[]> results = null;
			//SQLQuery qUpdate = null;
			StringBuilder sbLog = null;
			PreparedStatementWork updateWork = new PreparedStatementWork();
			updateWork.setSqlStatement(sbUpdate.toString());
			//long totMem = Runtime.getRuntime().totalMemory();
			while (readOn) {
			    /*
			    long freeMem = Runtime.getRuntime().freeMemory();
			    if (log.isDebugEnabled()) {
			    log.debug("Total Memory: " + totMem + ", Free Memory: " + freeMem);
			    }
			    if (freeMem / totMem < 0.2) {
			    if (log.isDebugEnabled()) {
			        log.debug("Running GC !!!!!! ");
			        System.gc();
			    }
			    }
			    */
			    session.getSessionFactory().evictQueries();
			    session.getSessionFactory().evict(entityClass);
			    q = session.createSQLQuery(sb.toString());
			    q.setCacheable(false);
			    q.setCacheMode(CacheMode.IGNORE);
			    q.setFirstResult(startFrom);
			    q.setMaxResults(maxRows);
			    results = q.list();
			    if (results.size() < maxRows) {
				readOn = false;
			    }
			    for (Object[] row : results) {
				if (!idComune.equals(row[0])) {
				    if (idComune.length() > 0) {
					//cambio sequence quindi aggiorno il CURRVAL della sequence che smetto di utilizzare
					if (activityLog.isDebugEnabled()) {
					    sbLog = new StringBuilder("Tabella ");
					    sbLog.append(tableName).append(": inseriti ").append(numUpdatedPerComune).append(" nuovi valori in ")
						    .append(newIdColumnName);
					    sbLog.append(" per IDCOMUNE = ").append(idComune);
					    activityLogDebug(sbLog.toString());
					}
					updateSequence(session, sequenceName, idComune, intVal);
				    }
				    //recupero il valore di partenza per la sequence del nuovo id comune
				    idComune = (String) row[0];
				    numUpdatedPerComune = 0;
				    intVal = getOrCreateSequenceCurrval(session, sequenceName, idComune);
				}
				//aggiorno i record della tabella di destinazione valorizzando gli ID
				Object[] statementParams = new Object[oldColumnsArray.length + 2];
				statementParams[0] = ++intVal;
				statementParams[1] = row[0];
				for (int i = 0; i < oldColumnsArray.length; i++) {
				    statementParams[i + 2] = row[i + 1];
				}
				updateWork.setParameters(statementParams);
				session.doWork(updateWork);
				int updated = updateWork.getLastResult();
				/*
				qUpdate = session.createSQLQuery(sbUpdate.toString());
				qUpdate.setCacheable(false);
				qUpdate.setCacheMode(CacheMode.IGNORE);
				intVal = getValidSequenceVal(session, ++intVal, tableName, newIdColumnName, idComune);
				qUpdate.setInteger(0, intVal);
				qUpdate.setParameter(1, row[0]);
				for (int i = 0; i < oldColumnsArray.length; i++) {
				    qUpdate.setParameter(i + 2, row[i + 1]);
				}
				int updated = qUpdate.executeUpdate();
				*/
				numUpdated += updated;
				numUpdatedPerComune += updated;
				/*
				if (log.isDebugEnabled()) {
				    sbLog = new StringBuilder("Tabella ");
				    sbLog.append(tableName).append(": inserito ").append(newIdColumnName).append(" = ").append(intVal);
				    sbLog.append(" per IDCOMUNE = ").append(row[0]);
				    for (int i = 0; i < oldColumnsArray.length; i++) {
					sbLog.append(", ").append(oldColumnsArray[i]).append(" = ").append(row[i + 1]);
				    }
				    log.debug(sbLog.toString());
				}
				*/
			    }
			    session.flush();
			    session.clear();
			    activityLogDebug("Tabella {}, ID aggiornati: {}", new Object[] { tableName, numUpdated });
			    //startFrom += maxRows;
			}
			//aggiorno il valore in SEQUENCETABLE per l'ultima sequence utilizzata
			if (StringUtils.isNotBlank(idComune))
			    updateSequence(session, sequenceName, idComune, intVal);
		    } else {
			handleErrorCondition("Impossibile individuare la sequence da staccare.");
		    }
		} else {
		    handleErrorCondition("Nessuna classe Java associata all'entità Hibernate " + entityName);
		}
	    } else {
		handleErrorCondition("Nessuna classe Java associata all'entità Hibernate " + entityName);
	    }
	} catch (HibernateException e) {
	    handleErrorCondition(e, "Errore durante una operazione di lettura o scrittura sul DB.");
	} catch (SecurityException e) {
	    handleErrorCondition(e, "Impossibile accedere alla classe Java: " + entityName);
	}
	activityLogInfo("Generazione ID completata, aggiornati {} record.", new Object[] { numUpdated });
	return numUpdated;
    }

    protected int getSequenceStartval(Session session, String sequenceName, String idComune) {

	Integer intVal = 0;
	Set<String> installType = WebConstants.getCODICI_INSTALLAZIONE_MASTER();
	if (installType != null) {
	    if (installType.contains(idComune)) {
		intVal = PkIdGenerator.MAX_HI_VALUE;
	    }
	}
	return intVal;
    }

    protected int getOrCreateSequenceCurrval(Session session, String sequenceName, String idComune) throws SetupRunException {

	int retVal = 0;
	try {
	    SQLQuery qNextVal = session.createSQLQuery(SELECT_MAX_CURRVAL_VAL_FROM_SEQUENCETABLE_WHERE_SEQUENCENAME_AND_IDCOMUNE);
	    qNextVal.addScalar("VAL", Hibernate.INTEGER);
	    qNextVal.setString(0, sequenceName);
	    qNextVal.setString(1, idComune);
	    Object val = qNextVal.uniqueResult();
	    if (val == null) {
		createSequence(session, sequenceName, idComune, getSequenceStartval(session, sequenceName, idComune));
		val = 0;
	    }
	    retVal = Integer.decode(val.toString());
	} catch (HibernateException e) {
	    handleErrorCondition(e, "Errore nella lettura del valore corrente per la sequence " + sequenceName);
	}
	return retVal;
    }

    protected void createSequence(Session session, String sequenceName, String idComune, int startVal) throws SetupRunException {

	try {
	    SQLQuery createSequenceQ = session.createSQLQuery(INSERT_INTO_SEQUENCETABLE_IDCOMUNE_SEQUENCENAME_CURRVAL_VALUES);
	    createSequenceQ.setString(0, idComune);
	    createSequenceQ.setString(1, sequenceName);
	    createSequenceQ.setInteger(2, startVal);
	    int result = createSequenceQ.executeUpdate();
	    activityLogDebug("Creazione della sequence {} per il comune {}. Righe inserite in SEQUENCETABLE: {}.",
		    new Object[] { sequenceName, idComune, result });
	} catch (HibernateException e) {
	    handleErrorCondition(e, "Errore nella creazione della sequence " + sequenceName);
	}
    }

    protected void updateSequence(Session session, String sequenceName, String idComune, int currVal) throws SetupRunException {

	try {
	    SQLQuery qCurrVal = session.createSQLQuery(UPDATE_SEQUENCETABLE_SET_CURRVAL_WHERE_SEQUENCENAME_AND_IDCOMUNE);
	    qCurrVal.setInteger(0, currVal);
	    qCurrVal.setString(1, sequenceName);
	    qCurrVal.setString(2, idComune);
	    int result = qCurrVal.executeUpdate();
	    activityLogDebug("La sequence {} per il comune {} è stata aggiornata al valore corrente: {}. N° righe aggiornate: {}",
		    new Object[] { sequenceName, idComune, currVal, result });
	} catch (HibernateException e) {
	    handleErrorCondition(e,
		    "Errore nell'aggiornamento della sequence " + sequenceName + " al valore corrente di " + currVal + " per il comune " + idComune);
	}
    }

    @Override
    public void initialize() throws SetupRunException {

    }
}
