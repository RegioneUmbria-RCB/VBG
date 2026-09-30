package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.upgr.core.SetupRunException;
import it.gruppoinit.upgr.core.task.BaseJavaTask;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5PasswordWork implements Work {

    private static final Logger log = LoggerFactory.getLogger("it.gruppoinit.upgr.activity");
    //nome della tabella su cui si effettuano gli aggiornamenti. OBBLIGATORIO
    private String tableName;
    //nome della colonna password
    private String passwordColumnName = "PASSWORD";
    //elenco dei campi che costituiscono la PK separati da virgola
    private String keyColumnNames;
    //stringa che contiene la chiamata alla funzione che restituisce la lunghezza del campo password
    private String lengthFunctionCall = "LENGTH(PASSWORD)";
    /*
     * la clausola che esclude le password vuote, nulle e quelle di 32 caratteri viene generata in automatico,
     * questa è una clausola WHERE aggiuntiva che viene aggiunta così com'è alla fine della query 
     */
    private String whereClause;
    private int updatedRows = 0;

    @Override
    public void execute(Connection connection) throws SQLException {

	updatedRows = 0;
	DatabaseMetaData dbmd = connection.getMetaData();
	if (!dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
	    throw new SetupRunException(
		    "Impossibile aggiornare le password direttamente dal ResultSet perchè il database in uso non supporta ResultSet aggiornabili.");
	}
	if (tableName != null) {
	    StringBuilder sbQuery = new StringBuilder("SELECT ");
	    sbQuery.append(passwordColumnName);
	    if(getKeyColumnNames() != null && getKeyColumnNames().length() > 0){
		sbQuery.append(", ").append(getKeyColumnNames());
	    }
	    sbQuery.append(" FROM ").append(tableName);
	    sbQuery.append(" WHERE ").append(passwordColumnName).append(" IS NOT NULL AND ");
	    sbQuery.append(lengthFunctionCall).append(" > 0 AND ");
	    sbQuery.append(lengthFunctionCall).append(" <> 32 ");
	    if (whereClause != null) {
		sbQuery.append(whereClause);
	    }
	    PreparedStatement ps = connection.prepareStatement(sbQuery.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	    ResultSet rs = ps.executeQuery();
	    String pwd = null;
	    while (rs.next()) {
		updatedRows++;
		pwd = rs.getString(1);
		rs.updateString(1, Utilities.getHashText(pwd, Utilities.ALGORITHM_MD5, false));
		rs.updateRow();
		if (updatedRows % 200 == 0) {
		    logProgress(updatedRows);
		}
	    }
	    logProgress(updatedRows);
	} else {
	    throw new RuntimeException("Impossibile aggiornare le password perchè non è stata impostata la tabella su cui fare gli aggiornamenti.");
	}
    }

    private void logProgress(int numRecord) {

	if(log.isInfoEnabled())log.info("Tabella {}, password aggiornate: {}", new Object[] { tableName, numRecord });
    }

    /**
     * @return the tableName
     */
    public String getTableName() {

	return tableName;
    }

    /**
     * @param tableName
     *            the tableName to set
     */
    public void setTableName(String tableName) {

	this.tableName = tableName;
    }

    /**
     * @return the passwordColumnName
     */
    public String getPasswordColumnName() {

	return passwordColumnName;
    }

    /**
     * @param passwordColumnName
     *            the passwordColumnName to set
     */
    public void setPasswordColumnName(String passwordColumnName) {

	this.passwordColumnName = passwordColumnName;
    }

    /**
     * @return the whereClause
     */
    public String getWhereClause() {

	return whereClause;
    }

    /**
     * @param whereClause
     *            the whereClause to set
     */
    public void setWhereClause(String whereClause) {

	this.whereClause = whereClause;
    }

    /**
     * @return the lengthFunctionCall
     */
    public String getLengthFunctionCall() {

	return lengthFunctionCall;
    }

    /**
     * @param lengthFunctionCall
     *            the lengthFunctionCall to set
     */
    public void setLengthFunctionCall(String lengthFunctionName) {

	this.lengthFunctionCall = lengthFunctionName;
    }

    public int coutUpdatedRows() {

	return updatedRows;
    }

    
    /**
     * @return the keyColumnNames
     */
    public String getKeyColumnNames() {
    
        return keyColumnNames;
    }

    
    /**
     * @param keyColumnNames the keyColumnNames to set
     */
    public void setKeyColumnNames(String keyColumnNames) {
    
        this.keyColumnNames = keyColumnNames;
    }
}
