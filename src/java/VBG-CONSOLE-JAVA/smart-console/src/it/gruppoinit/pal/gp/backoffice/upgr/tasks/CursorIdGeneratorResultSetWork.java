package it.gruppoinit.pal.gp.backoffice.upgr.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.jdbc.Work;

public class CursorIdGeneratorResultSetWork implements Work {

    private CursorIdGeneratorTask sequenceProviderTask;
    private String sql;
    private String currentIdComune;
    private int rowCount = 0;

    public CursorIdGeneratorResultSetWork(CursorIdGeneratorTask tsk) {

	this.sequenceProviderTask = tsk;
    }

    @Override
    public void execute(Connection connection) throws SQLException {

	if (sequenceProviderTask == null) {
	    throw new RuntimeException("Riferimento al task per la lettura della sequence non impostato.");
	}
	PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	ResultSet rs = ps.executeQuery();
	String idComune = null;
	int seqVal = 0;
	rowCount = 0;
	while (rs.next()) {
	    rowCount++;
	    //NOTA: lo statement SQL che viene passato per questa query DEVE avere come prima colonna in select l'IDCOMUNE e come seconda l'ID da aggiornare
	    idComune = rs.getString(1);
	    //cambio di comune ==> cambio sequence e aggiorno la vecchia sequence
	    if (!idComune.equals(currentIdComune)) {
		if (currentIdComune != null) {
		    sequenceProviderTask.updateSequenceCurrentValue(currentIdComune, seqVal);
		}
		seqVal = sequenceProviderTask.getSequenceCurrentValue(idComune);
		currentIdComune = idComune;
	    }
	    seqVal++;
	    rs.updateInt(2, seqVal);
	    rs.updateRow();
	    if (rowCount % 200 == 0) {
		//un log ogni 200 record
		sequenceProviderTask.logProgress(rowCount);
	    }
	}
	if (currentIdComune != null) {
	    sequenceProviderTask.updateSequenceCurrentValue(currentIdComune, seqVal);
	}
	sequenceProviderTask.logProgress(rowCount);
    }

    /**
     * @return the sql
     */
    public String getSql() {

	return sql;
    }

    /**
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql) {

	this.sql = sql;
    }

    public int countUpdatedRows() {

	return rowCount;
    }
}
