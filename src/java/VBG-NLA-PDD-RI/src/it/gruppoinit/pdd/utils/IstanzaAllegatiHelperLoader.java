package it.gruppoinit.pdd.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IstanzaAllegatiHelperLoader {

    private static final String POPOLATE_ALLEGATI_SQL = "popolateAllegati# sql={}";
    private static Logger log = LoggerFactory.getLogger(IstanzaAllegatiHelperLoader.class);
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public IstanzaAllegatiHelperLoader(Connection conn) {

	this.conn = conn;
    }

    public List<IstanzaAllegatiHelper> popolateAllegatiIstanzaAndProcure(String idcomune, String dbOwner, Integer codiceIstanza) {

	List<IstanzaAllegatiHelper> out = new ArrayList<>();
	try {
	    if (log.isDebugEnabled()) {
		log.debug("popolateAllegatiIstanzaAndProcure# sql={}", sqlDocumenti.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlDocumenti.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(null);
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    if (log.isDebugEnabled()) {
		log.debug("popolateAllegatiIstanzaAndProcure# sql={}", sqlProcure.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlProcure.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(rs.getInt("codiceinventario"));
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    // NON PASSO PIù I DOCUMENTI DEI MOVIMENTI
	    //	    log.debug("popolateAllegati# sql=" + sqlMovimenti.replaceAll("DB_SCHEMA", dbOwner))
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException(
			"Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza + "] a causa di " + e.getMessage(),
			e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza +
					       "] a causa di " + e.getCause().getMessage(),
			new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return out;
    }

    public List<IstanzaAllegatiHelper> popolateAllegati(String idcomune, String dbOwner, Integer codiceIstanza) {

	List<IstanzaAllegatiHelper> out = new ArrayList<>();
	try {
	    if (log.isDebugEnabled()) {
		log.debug(POPOLATE_ALLEGATI_SQL, sqlDocumenti.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlDocumenti.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(null);
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    if (log.isDebugEnabled()) {
		log.debug(POPOLATE_ALLEGATI_SQL, sqlIstanzeallegati.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlIstanzeallegati.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(rs.getInt("codiceinventario"));
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	    if (log.isDebugEnabled()) {
		log.debug(POPOLATE_ALLEGATI_SQL, sqlProcure.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlProcure.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(rs.getInt("codiceinventario"));
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    if (log.isDebugEnabled()) {
		log.debug(POPOLATE_ALLEGATI_SQL, sqlMovimenti.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlMovimenti.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(rs.getInt("codiceinventario"));
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException(
			"Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza + "] a causa di " + e.getMessage(),
			e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza +
					       "] a causa di " + e.getCause().getMessage(),
			new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return out;
    }

    public List<IstanzaAllegatiHelper> popolateDocumentiEndo(String idcomune, String dbOwner, Integer codiceIstanza, Integer codiceEndo) {

	List<IstanzaAllegatiHelper> out = new ArrayList<>();
	try {
	    if (log.isDebugEnabled()) {
		log.debug("popolateDocumentiEndo# sql={}", sqlIstanzeallegatiByEndo.replace("DB_SCHEMA", dbOwner));
	    }
	    pstmt = conn.prepareStatement(sqlIstanzeallegatiByEndo.replace("DB_SCHEMA", dbOwner));
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codiceIstanza);
	    pstmt.setInt(3, codiceEndo);
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		IstanzaAllegatiHelper result = new IstanzaAllegatiHelper();
		result.setId(rs.getInt("codicedocumento"));
		result.setCodiceInventario(null);
		result.setCodiceOggetto(rs.getInt("codiceoggetto"));
		result.setDescrizioneDocumento(rs.getString("documento"));
		result.setNomeFile(rs.getString("nomefile"));
		result.setTipo(rs.getString("tipodocumento"));
		out.add(result);
	    }
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	} catch (SQLException e) {
	    if (e.getCause() == null) {
		Utilities.logAndThrowException(
			"Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza + "] a causa di " + e.getMessage(),
			e, getClass());
	    } else {
		Utilities.logAndThrowException("Errore durante la query di recupero dati dell'istanza [" + idcomune + "," + codiceIstanza +
					       "] a causa di " + e.getCause().getMessage(),
			new RuntimeException(e.getCause()), getClass());
	    }
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return out;
    }

    private String sqlDocumenti = "select id as codicedocumento,documento, nomefile, di.codiceoggetto, omd.valore as tipodocumento, null as codiceinventario " +
				  " from DB_SCHEMA.documentiistanza di inner join DB_SCHEMA.oggetti og on " + //
				  " di.idcomune=og.idcomune and " + // 
				  " di.codiceoggetto=og.codiceoggetto left outer join DB_SCHEMA.oggetti_metadati omd on " + // 
				  " omd.idcomune=og.idcomune and " + // 
				  " omd.codiceoggetto=og.codiceoggetto and omd.chiave='TIPO_DOCUMENTO' " + //
				  " WHERE di.idcomune    =? " + //
				  " AND di.codiceistanza =? ";
    private String sqlIstanzeallegati = "select id as codicedocumento,allegatoextra as documento, nomefile, di.codiceoggetto, omd.valore as tipodocumento , codiceinventario " +
					" from DB_SCHEMA.istanzeallegati di inner join DB_SCHEMA.oggetti og on " + //
					" di.idcomune=og.idcomune and " + //
					" di.codiceoggetto=og.codiceoggetto left outer join DB_SCHEMA.oggetti_metadati omd on " + //
					" omd.idcomune=og.idcomune and " + //
					" omd.codiceoggetto=og.codiceoggetto and omd.chiave='TIPO_DOCUMENTO' " + //
					" where di.idcomune=? and codiceistanza=?";
    private String sqlIstanzeallegatiByEndo = "select id as codicedocumento,allegatoextra as documento, nomefile, di.codiceoggetto, omd.valore as tipodocumento , codiceinventario " +
					      " from DB_SCHEMA.istanzeallegati di inner join DB_SCHEMA.oggetti og on " + //
					      " di.idcomune=og.idcomune and " + //
					      " di.codiceoggetto=og.codiceoggetto left outer join DB_SCHEMA.oggetti_metadati omd on " + //
					      " omd.idcomune=og.idcomune and " + //
					      " omd.codiceoggetto=og.codiceoggetto and omd.chiave='TIPO_DOCUMENTO' " + //
					      " where di.idcomune=? and codiceistanza=? and di.codiceinventario= ?";
    private String sqlProcure = "select id as codicedocumento,'Documento di procura' as documento, nomefile, di.codiceoggettoprocura as codiceoggetto, omd.valore as tipodocumento , null as codiceinventario " +
				" from DB_SCHEMA.istanzeprocure di inner join DB_SCHEMA.oggetti og on " + //
				" di.idcomune=og.idcomune and " + //
				" di.codiceoggettoprocura=og.codiceoggetto left outer join DB_SCHEMA.oggetti_metadati omd on " + //
				" omd.idcomune=og.idcomune and  " + //
				" omd.codiceoggetto=og.codiceoggetto and omd.chiave='TIPO_DOCUMENTO' " + //
				" where di.idcomune=? and codiceistanza=?";
    private String sqlMovimenti = "SELECT id AS codicedocumento, " + //
				  "  di.DESCRIZIONE AS documento, " + //
				  "  nomefile, " + //
				  "  di.codiceoggetto, " + //
				  "  omd.valore AS tipodocumento, " + //
				  "  m.codiceinventario AS codiceinventario " + //
				  "FROM DB_SCHEMA.movimentiallegati di " + //
				  "INNER JOIN DB_SCHEMA.movimenti m " + //
				  "ON di.idcomune        =m.idcomune " + //
				  "AND di.CODICEMOVIMENTO=m.codicemovimento " + //
				  "INNER JOIN DB_SCHEMA.oggetti og " + //
				  "ON di.idcomune      =og.idcomune " + //
				  "AND di.codiceoggetto=og.codiceoggetto " + //
				  "LEFT OUTER JOIN DB_SCHEMA.oggetti_metadati omd " + //
				  "ON omd.idcomune      =og.idcomune " + //
				  "AND omd.codiceoggetto=og.codiceoggetto " + //
				  "AND omd.chiave       ='TIPO_DOCUMENTO' " + //
				  "WHERE m.idcomune     =? " + //
				  "AND m.codiceistanza  =?";
}
