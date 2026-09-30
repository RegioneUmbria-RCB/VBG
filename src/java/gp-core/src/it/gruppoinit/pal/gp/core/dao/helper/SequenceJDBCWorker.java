package it.gruppoinit.pal.gp.core.dao.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

import org.hibernate.LockMode;
import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequenceJDBCWorker implements Work {

    private static final Logger log = LoggerFactory.getLogger(SequenceJDBCWorker.class);
    private Dialect dialect;
    private String sequenceName;
    private String tableName;
    private String idColumn;
    private Integer numRecordDaRiservare;
    private Integer risultato;

    public Integer getRisultato() {

	return risultato;
    }

    public SequenceJDBCWorker(Dialect dialect, String sequenceName, String tableName, String idColumn, Integer numRecordDaRiservare) {

	this.dialect = dialect;
	this.sequenceName = sequenceName;
	this.tableName = tableName;
	this.idColumn = idColumn;
	this.numRecordDaRiservare = numRecordDaRiservare;
    }

    @Override
    public void execute(Connection conn) throws SQLException {

	int result;
	//.. esiste la sequenza in sequence table per la tabella.id? e l'idcomune 
	PreparedStatement selectPS = conn.prepareStatement(buildQuerySelect(dialect));
	try {
	    selectPS.setString(1, ORMHelper.getIdcomune());
	    selectPS.setString(2, sequenceName);
	    ResultSet selectRS = selectPS.executeQuery();
	    PreparedStatement selectMaxPS = conn.prepareStatement(buildSelectMaxQuery(idColumn, tableName));
	    selectMaxPS.setString(1, ORMHelper.getIdcomune());
	    selectMaxPS.setInt(2, PkIdGenerator.MAX_HI_VALUE);
	    ResultSet selectMaxRS = selectMaxPS.executeQuery();
	    if (selectMaxRS.next()) {
		// .. assegno il valore alla variabile result
		risultato = selectMaxRS.getInt(1) + 1;
	    } else {
		risultato = 1;
	    }
	    selectMaxRS.close();
	    selectMaxPS.close();
	    result = risultato + numRecordDaRiservare;
	    if (!selectRS.next()) {
		PreparedStatement insertPS = null;
		try {
		    // .. inserisco il valore della max in sequence table
		    insertPS = conn.prepareStatement(insertQuerySequence());
		    insertPS.setString(1, ORMHelper.getIdcomune());
		    insertPS.setString(2, sequenceName);
		    insertPS.setLong(3, result);
		    insertPS.execute();
		    conn.commit();
		} finally {
		    if (insertPS != null) {
			insertPS.close();
		    }
		}
	    } else {
		PreparedStatement updatePS = conn.prepareStatement(updateQuery());
		try {
		    // questo controllo limita le sequenze a MAX_HI_VALUE così codici maggiori possono essere utilizzati da un MASTER, 
		    // in caso di superamento non strappiamo la sequenza e diamo un errore chiaro
		    if (result >= PkIdGenerator.MAX_HI_VALUE) {
			log.error(
				"Superato il limite massimo [{}] per ottenere una sequenza per il campo [{}] e idcomune [{}]. Contattare l'assistenza.",
				new Object[] { PkIdGenerator.MAX_HI_VALUE, sequenceName, ORMHelper.getIdcomune() });
			throw new SQLException(
				"Superato il limite massimo [" + PkIdGenerator.MAX_HI_VALUE + "] per ottenere una sequenza per il campo [" +
					       sequenceName + "] e idcomune [" + ORMHelper.getIdcomune() + "]. Contattare l'assistenza.");
		    }
		    updatePS.setLong(1, result);
		    updatePS.setString(2, ORMHelper.getIdcomune());
		    updatePS.setString(3, sequenceName);
		    updatePS.executeUpdate();
		    conn.commit();
		} catch (SQLException sqle) {
		    log.error("could not updateQuery hi value in: " + tableName, sqle);
		    throw sqle;
		} finally {
		    updatePS.close();
		}
	    }
	    selectRS.close();
	} catch (SQLException sqle) {
	    log.error("could not read or init a hi value", sqle);
	    throw sqle;
	} finally {
	    selectPS.close();
	}
    }

    private String buildQuerySelect(Dialect dialect) {

	String query = "select currval from sequencetable where idcomune=?  and sequencename=?";
	HashMap<String, LockMode> lockMap = new HashMap<String, LockMode>();
	lockMap.put("sequencetable", LockMode.UPGRADE);
	return dialect.applyLocksToSql(query, lockMap, Collections.singletonMap("sequencetable", new String[] { "currval" }));
    }

    private String buildSelectMaxQuery(String idColumn, String table) {

	return "select max(" + idColumn + ") from " + table + " where idcomune = ? and " + idColumn + " < ?";
    }

    private String insertQuerySequence() {

	return "insert into sequencetable(idcomune,sequencename,currval)values(?,?,?)";
    }

    private String updateQuery() {

	return "update sequencetable set currval=? where idcomune=? and sequencename=? ";
    }
}
