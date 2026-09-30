package it.gruppoinit.service.impl;

import it.gruppoinit.domain.helper.MovimentiAtti;
import it.gruppoinit.service.MovimentiAttiService;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWebServiceClient;
import it.gruppoinit.utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovimentiAttiServiceImpl implements MovimentiAttiService {

    private static final Logger log = LoggerFactory.getLogger(MovimentiAttiServiceImpl.class);
    @Autowired
    private SigeproSecurityWebServiceClient sigeproSecurityWebServiceClient;

    @Override
    public void insert(MovimentiAtti movimentiAtti) {

	log.debug("insert# Calcolo l'id massimo per fare un nuovo inserimento");
	int codice = findMaxId(movimentiAtti.getIdcomune()) + 1;
	log.debug("insert# Recupero le informazione dalla security e creo l'oggetto connection");
	String token = sigeproSecurityWebServiceClient.loginAPP();
	Connection connection = sigeproSecurityWebServiceClient.getConnectionDB(token);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String insertMovimentiAttiTableSQL = "INSERT INTO MOVIMENTI_ATTI"
		+ "(IDCOMUNE, ID, DATA_RICHIESTA_ATTO,DATA_RICEZIONE_ATTO, ID_DOCUMENTO, NUMERO, ANNO, TIPO_DOCUMENTO, STATO,FK_MOVIMENTO) "
		+ "VALUES" + "(?,?,?,?,?,?,?,?,?,?)";
	log.debug("insert# query : {}", insertMovimentiAttiTableSQL);
	try {
	    pstmt = connection.prepareStatement(insertMovimentiAttiTableSQL);
	    pstmt.setString(1, movimentiAtti.getIdcomune());
	    pstmt.setInt(2, codice);
	    if (movimentiAtti.getDataRichiestaAtto() != null) {
		pstmt.setDate(3, Utilities.getDate(movimentiAtti.getDataRichiestaAtto()));
	    }
	    if (movimentiAtti.getDataRicezioneAtto() != null) {
		pstmt.setDate(4, Utilities.getDate(movimentiAtti.getDataRicezioneAtto()));
	    } else {
		pstmt.setDate(4, null);
	    }
	    pstmt.setInt(5, movimentiAtti.getIdDocumento());
	    pstmt.setInt(6, movimentiAtti.getNumero());
	    pstmt.setInt(7, movimentiAtti.getAnno());
	    pstmt.setString(8, movimentiAtti.getTipoDocumento());
	    pstmt.setInt(9, movimentiAtti.getStato());
	    pstmt.setInt(10, movimentiAtti.getMovimenti());
	    int i = pstmt.executeUpdate();
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante l'inserimento del record MOVIMENTI_ATTI.Query: " + insertMovimentiAttiTableSQL, e,
		    getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
    }

    @Override
    public int findMaxId(String idcomune) {

	log.debug("insert# Recupero le informazione dalla security e creo l'oggetto connection");
	String token = sigeproSecurityWebServiceClient.loginAPP();
	Connection connection = sigeproSecurityWebServiceClient.getConnectionDB(token);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String findMaxIdMovimentiAttiTableSQL = "select max(id) from MOVIMENTI_ATTI where idcomune=?";
	log.debug("findMaxId# query : {}", findMaxIdMovimentiAttiTableSQL);
	int max = 0;
	try {
	    pstmt = connection.prepareStatement(findMaxIdMovimentiAttiTableSQL);
	    pstmt.setString(1, idcomune);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		max = rs.getInt(1);
	    }
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante la ricerca del max id per la tabella MOVIMENTI_ATTI.Query: "
		    + findMaxIdMovimentiAttiTableSQL, e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return max;
    }

    @Override
    public MovimentiAtti findByMovimento(int codice, String idcomune) {

	log.debug("findByMovimento# Recupero le informazione dalla security e creo l'oggetto connection");
	String token = sigeproSecurityWebServiceClient.loginAPP();
	Connection connection = sigeproSecurityWebServiceClient.getConnectionDB(token);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	/**
	 * private PkId id; private Movimenti movimenti; private Date dataRichiestaAtto; private Date dataRicezioneAtto;
	 * private Integer idDocumento; private Integer numero; private Integer anno; private String tipoDocumento;
	 * private Integer stato;
	 */
	String findMaxIdMovimentiAttiTableSQL = "select * " + "from MOVIMENTI_ATTI where IDCOMUNE =? and FK_MOVIMENTO= ?";
	log.debug("findByMovimento# query : {}", findMaxIdMovimentiAttiTableSQL);
	MovimentiAtti movimentiAtti = null;
	try {
	    movimentiAtti = new MovimentiAtti();
	    pstmt = connection.prepareStatement(findMaxIdMovimentiAttiTableSQL);
	    pstmt.setString(1, idcomune);
	    pstmt.setInt(2, codice);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		Integer id = rs.getInt("ID");
		movimentiAtti.setId(id);
		Integer idDocumento = rs.getInt("ID_DOCUMENTO");
		movimentiAtti.setIdDocumento(idDocumento);
		Integer numero = rs.getInt("NUMERO");
		movimentiAtti.setNumero(numero);
		Integer anno = rs.getInt("ANNO");
		movimentiAtti.setAnno(anno);
		String tipodoc = rs.getString("TIPO_DOCUMENTO");
		movimentiAtti.setTipoDocumento(tipodoc);
		Integer stato = rs.getInt("STATO");
		movimentiAtti.setStato(stato);
		movimentiAtti.setMovimenti(codice);
		movimentiAtti.setIdcomune(idcomune);
	    }
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante la ricerca del record della tabella MOVIMENTI_ATTI con fk_movimento :" + codice
		    + ".Query: " + findMaxIdMovimentiAttiTableSQL, e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
	return movimentiAtti;
    }

    @Override
    public void update(MovimentiAtti movimentiAtti) {

	String token = sigeproSecurityWebServiceClient.loginAPP();
	Connection connection = sigeproSecurityWebServiceClient.getConnectionDB(token);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	/**
	 * UPDATE table_name SET column1=value1,column2=value2,... WHERE some_column=some_value;
	 */
	String updateMovimentiAttiTableSQL = "UPDATE MOVIMENTI_ATTI SET "
		+ "DATA_RICEZIONE_ATTO=?, ID_DOCUMENTO=?, NUMERO=?, ANNO=?, TIPO_DOCUMENTO=?, STATO=? " + "WHERE IDCOMUNE=? AND ID=?";
	log.debug("update# query : {}", updateMovimentiAttiTableSQL);
	try {
	    pstmt = connection.prepareStatement(updateMovimentiAttiTableSQL);
	    // Dati da aggiornare
	    pstmt.setDate(1, Utilities.getDate(new Date()));
	    pstmt.setInt(2, movimentiAtti.getIdDocumento());
	    pstmt.setInt(3, movimentiAtti.getNumero());
	    pstmt.setInt(4, movimentiAtti.getAnno());
	    pstmt.setString(5, movimentiAtti.getTipoDocumento());
	    pstmt.setInt(6, movimentiAtti.getStato());
	    // FILTRI
	    pstmt.setString(7, movimentiAtti.getIdcomune());
	    pstmt.setInt(8, movimentiAtti.getId());
	    int i = pstmt.executeUpdate();
	} catch (SQLException e) {
	    Utilities.logAndThrowException("Errore durante l'update del record MOVIMENTI_ATTI.Query: " + updateMovimentiAttiTableSQL, e, getClass());
	} finally {
	    Utilities.gracefullyReleaseResources(null, pstmt, rs);
	}
    }

    @Override
    public void insertCodiceOggetto(Integer codiceOggetto, Integer codice, String idcomune) {

	/**
	 * String token = sigeproSecurityWebServiceClient.loginAPP(); Connection connection =
	 * sigeproSecurityWebServiceClient.getConnectionDB(token); PreparedStatement pstmt = null; ResultSet rs = null;
	 * 
	 * String updateMovimentiAttiTableSQL = "UPDATE MOVIMENTI_ATTI" + "codiceoggetto" + "WHERE IDCOMUNE=?, ID=?";
	 * log.debug("update# query : {}", updateMovimentiAttiTableSQL); try { pstmt =
	 * connection.prepareStatement(updateMovimentiAttiTableSQL); // Dati da aggiornare pstmt.setInt(1,
	 * codiceOggetto); // FILTRI pstmt.setString(2, idcomune); pstmt.setInt(3, codice); int i =
	 * pstmt.executeUpdate(); } catch (SQLException e) {
	 * Utilities.logAndThrowException("Errore durante insertCodiceOggetto del record MOVIMENTI_ATTI.Query: " +
	 * updateMovimentiAttiTableSQL, e, getClass()); } finally { Utilities.gracefullyReleaseResources(null, pstmt,
	 * rs); }
	 */
	// Non è stata implementatato perchè è da decidere se il riferimento all'oggetto deve essere messo su MOVIMENTI_ATTI o MOVIMENTI_ALLEGATI
	throw new NotImplementedException("Metodo non implementato");
    }
}
