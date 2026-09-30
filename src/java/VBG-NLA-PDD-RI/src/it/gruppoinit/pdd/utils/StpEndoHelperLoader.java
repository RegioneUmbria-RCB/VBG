package it.gruppoinit.pdd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StpEndoHelperLoader {

    private static Logger log = LoggerFactory.getLogger(StpEndoHelperLoader.class);
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StpEndoHelperLoader(Connection conn) {

	this.conn = conn;
    }

    public StpEndoTipo1Helper findStpEndoTipo1Helper(String idcomune, String dbOwner, Integer codiceEndo) {

	String sqlendoTipo1 = "select idcomune,id,codiceinventario,codice_endo_regionale" + " from DB_SCHEMA.STP_ENDO_TIPO1" //
		+ " WHERE IDCOMUNE    =? " //
		+ " AND CODICEINVENTARIO =? ";
	StpEndoTipo1Helper stpEndoTipo1Helper = null;
	try {
	    log.debug("findStpEndoTipo1Helper# sql=" + sqlendoTipo1.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt = conn.prepareStatement(sqlendoTipo1.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceEndo);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		stpEndoTipo1Helper = new StpEndoTipo1Helper();
		stpEndoTipo1Helper.setIdComune(rs.getString("idcomune"));
		stpEndoTipo1Helper.setId(rs.getInt("id"));
		stpEndoTipo1Helper.setCodiceInventario(rs.getInt("codiceinventario"));
		stpEndoTipo1Helper.setCodiceEndoRegionale(rs.getString("codice_endo_regionale"));
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException("Errore durante la query di recupero dati di  StpEndoTipo1 [idcomune,codiceEndo] [" + idcomune + ","
			+ codiceEndo + "] a causa di " + e.getMessage(), e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati di  StpEndoTipo1 [idcomune,codiceEndo] [" + idcomune + ","
			+ codiceEndo + "] a causa di " + e.getCause().getMessage(), new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return stpEndoTipo1Helper;
    }

    public StpEndoTipo2Helper findStpEndoTipo2Helper(String idcomune, String dbOwner, Integer codiceIntervento) {

	/**
	 * select procedimento from STP_ENDO_TIPO2 LEFT JOIN INVENTARIOPROCEDIMENTI ON
	 * STP_ENDO_TIPO2.idcomune=INVENTARIOPROCEDIMENTI.idcomune AND
	 * STP_ENDO_TIPO2.CODICEINVENTARIO=INVENTARIOPROCEDIMENTI.CODICEINVENTARIO where STP_ENDO_TIPO2.idcomune='G713'
	 * and STP_ENDO_TIPO2.FK_SC_ID=38283;
	 **/
	String sqlendoTipo2 = "select STP_ENDO_TIPO2.idcomune as idComune,STP_ENDO_TIPO2.id as stpendotipo2Id,INVENTARIOPROCEDIMENTI.codiceinventario as codInventario "
		+ ",codice_endo_regionale,fk_sc_id,INVENTARIOPROCEDIMENTI.procedimento as descrizioneProcedimento" //
		+ " from DB_SCHEMA.STP_ENDO_TIPO2 LEFT JOIN DB_SCHEMA.INVENTARIOPROCEDIMENTI "//
		+ " ON STP_ENDO_TIPO2.idcomune=INVENTARIOPROCEDIMENTI.idcomune "//
		+ " AND STP_ENDO_TIPO2.CODICEINVENTARIO=INVENTARIOPROCEDIMENTI.CODICEINVENTARIO"//
		+ " WHERE STP_ENDO_TIPO2.idcomune    =? " //
		+ " AND FK_SC_ID =? ";
	StpEndoTipo2Helper stpEndoTipo2Helper = null;
	try {
	    log.debug("findStpEndoTipo2Helper# sql=" + sqlendoTipo2.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt = conn.prepareStatement(sqlendoTipo2.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIntervento);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		stpEndoTipo2Helper = new StpEndoTipo2Helper();
		stpEndoTipo2Helper.setIdComune(rs.getString("idComune"));
		stpEndoTipo2Helper.setId(rs.getInt("stpendotipo2Id"));
		stpEndoTipo2Helper.setCodiceInventario(rs.getInt("codInventario"));
		stpEndoTipo2Helper.setCodiceEndoRegionale(rs.getString("codice_endo_regionale"));
		stpEndoTipo2Helper.setCodiceIntervento(rs.getInt("fk_sc_id"));
		stpEndoTipo2Helper.setDescrizioneInventario(rs.getString("descrizioneProcedimento"));
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException("Errore durante la query di recupero dati di  StpEndoTipo2 [idcomune,codiceIntervento] [" + idcomune
			+ "," + codiceIntervento + "] a causa di " + e.getMessage(), e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati di  StpEndoTipo2 [idcomune,codiceIntervento] [" + idcomune
			+ "," + codiceIntervento + "] a causa di " + e.getCause().getMessage(), new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return stpEndoTipo2Helper;
    }

    public List<EndoIstanzaHelper> findEndoIstanzaHelper(String idcomune, String dbOwner, Integer codiceIstanza) {

	/**
	 * select * from ISTANZEPROCEDIMENTI LEFT JOIN INVENTARIOPROCEDIMENTI ON
	 * ISTANZEPROCEDIMENTI.idcomune=INVENTARIOPROCEDIMENTI.idcomune and
	 * ISTANZEPROCEDIMENTI.CODICEINVENTARIO=INVENTARIOPROCEDIMENTI.CODICEINVENTARIO where
	 * ISTANZEPROCEDIMENTI.idcomune='G713' and ISTANZEPROCEDIMENTI.CODICEISTANZA=175;
	 **/
	String sqlendoTipo2 = "select ISTANZEPROCEDIMENTI.idcomune as idComune,ISTANZEPROCEDIMENTI.CODICEISTANZA as codIstanza,"
		+ "ISTANZEPROCEDIMENTI.codiceinventario as codInventario, INVENTARIOPROCEDIMENTI.procedimento as descrizioneProcedimento" //
		+ " from DB_SCHEMA.ISTANZEPROCEDIMENTI LEFT JOIN DB_SCHEMA.INVENTARIOPROCEDIMENTI "//
		+ " ON DB_SCHEMA.ISTANZEPROCEDIMENTI.idcomune=DB_SCHEMA.INVENTARIOPROCEDIMENTI.idcomune "//
		+ " AND DB_SCHEMA.ISTANZEPROCEDIMENTI.CODICEINVENTARIO=DB_SCHEMA.INVENTARIOPROCEDIMENTI.CODICEINVENTARIO"//
		+ " WHERE ISTANZEPROCEDIMENTI.idcomune    =? " //
		+ " AND ISTANZEPROCEDIMENTI.CODICEISTANZA =? ";
	List<EndoIstanzaHelper> list = new ArrayList<EndoIstanzaHelper>();
	EndoIstanzaHelper endoIstanzaHelper = null;
	try {
	    log.debug("findEndoIstanzaHelper# sql=" + sqlendoTipo2.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt = conn.prepareStatement(sqlendoTipo2.replaceAll("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		endoIstanzaHelper = new EndoIstanzaHelper();
		endoIstanzaHelper.setIdcomune(rs.getString("idComune"));
		endoIstanzaHelper.setCodiceIstanza(rs.getInt("codIstanza"));
		endoIstanzaHelper.setCodiceEndo(rs.getInt("codInventario"));
		endoIstanzaHelper.setDescrizioneInventario(rs.getString("descrizioneProcedimento"));
		list.add(endoIstanzaHelper);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException("Errore durante la query di recupero dati degli endo dell'istanza [idcomune,codiceistanza] ["
			+ idcomune + "," + codiceIstanza + "] a causa di " + e.getMessage(), e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati degli endo dell'istanza [idcomune,codiceistanza] ["
			+ idcomune + "," + codiceIstanza + "] a causa di " + e.getCause().getMessage(), new RuntimeException(e.getCause()),
			getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return list;
    }
}
