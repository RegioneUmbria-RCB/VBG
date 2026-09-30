package it.gruppoinit.service;

import it.gruppoinit.service.helper.CodiceDescrizioneBean;
import it.gruppoinit.service.helper.StatusHelper;
import it.gruppoinit.service.helper.TipimovHelper;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;
import it.gruppoinit.utils.Utils;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendService {

    private static final String TIPOMOVNONSETTATO = "tipomovnonsettato";
    private static final String CONSOLE_DIZ_PREFIX = "CONSOLE#DIZ#";
    private static final String SOFTWARE_TT = "TT";
    private static final int CODICE_MASTER = 91000000;
    private SigeproSecurityWSClient sigeproSecurityWSClient;
    private String aliasOrigine;
    private String aliasDestinazione;
    private String softwareOrigine;
    private String softwareDestinazione;
    private String idOperazione;
    private Map<String, TipimovHelper> ammTipiMov = new HashMap<String, TipimovHelper>();
    private Map<Integer, Integer> ammRemoteAmmLoc = new HashMap<Integer, Integer>();
    private Map<Integer, String> ammCodAncitel = new HashMap<Integer, String>();
    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");

    public BackendService(String aliasOrigine, String aliasDestinazione, String softwareOrigine, String softwareDestinazione,
	    SigeproSecurityWSClient sigeproSecurityWSClient, String idOperazione) {

	if (StatusHelper.checkOperazioneInserita(idOperazione) == true) {
	    throw new SecurityException("L'operazione con identificativo " + idOperazione + " e' già stata avviata");
	}
	this.idOperazione = idOperazione;
	this.aliasOrigine = aliasOrigine;
	this.aliasDestinazione = aliasDestinazione;
	this.softwareOrigine = softwareOrigine;
	this.softwareDestinazione = softwareDestinazione;
	this.sigeproSecurityWSClient = sigeproSecurityWSClient;
    }

    public void copiaVoceAlbero(Boolean escludiDisabilitati, String scCodice) throws SQLException {

	if (activityLog.isInfoEnabled()) {
	    activityLog.info("Inizio procedura di copia dati.");
	}
	StatusHelper.aggiungiMessaggio(idOperazione, "Inizio cerco le connessioni origine / destinazione");
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	GetDbConnectionInfoResponse propsDestinazione = sigeproSecurityWSClient.getConnectionProperties(aliasDestinazione);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String idcomuneDestinazione = propsDestinazione.getIdComune();
	StatusHelper.aggiungiMessaggio(idOperazione, "Apro le connessioni origine / destinazione");
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	Connection cDestinazione = sigeproSecurityWSClient.getConnection(aliasDestinazione);
	cOrigine.setReadOnly(true);
	cDestinazione.setAutoCommit(false);
	// FACCIO LA QUERY DI LETTURA di ogni tabella
	// TIPICAUSALIONERI
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPICAUSALIONERI");
	// copia le info a partire da una voce dell'albero
	processTipiCausaliOneri(cOrigine, cDestinazione, "TIPICAUSALIONERI", idcomuneOrigine, idcomuneDestinazione);
	StatusHelper.aggiungiMessaggio(idOperazione, "TIPISOGGETTO");
	processTipisoggetto(cOrigine, cDestinazione, "TIPISOGGETTO", idcomuneOrigine, idcomuneDestinazione);
	// l'albero se non root va messo su root dell'ente di destinazione
	// tipisoggetto legati all'albero - no perché potrebbero arrivare pratiche di procedimento unico
	Set<Integer> codiciInventario = processTrovaEndoAlbero(cOrigine, cDestinazione, "alberoproc", idcomuneOrigine, idcomuneDestinazione,
		scCodice);
	// endoprocedimenti solo quelli all'alberoproc_endo e i subendo
	// amministrazioni degli endo
	// schede dinamiche degli endo
	processCopiaEndo(cOrigine, cDestinazione, "alberoproc", idcomuneOrigine, idcomuneDestinazione, codiciInventario, escludiDisabilitati);
	Utils.closeObjects(cDestinazione);
	Utils.closeObjects(cOrigine);
	StatusHelper.aggiungiMessaggio(idOperazione, "OPERAZIONE TERMINATA");
    }

    private void processCopiaEndo(Connection cOrigine, Connection cDestinazione, String string, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciInventario, Boolean escludiDisabilitati) throws SQLException {

	Set<Integer> codiciFamiglieEndo = new HashSet<Integer>();
	Set<Integer> codiciTipiEndo = new HashSet<Integer>();
	Set<Integer> codiciSchedeEndo = new HashSet<Integer>();
	Set<Integer> codiciAmministrazioniEndo = new HashSet<Integer>();
	for (Integer codiceEndo : codiciInventario) {
	    String sql = "SELECT tipifamiglieendo.codice AS codicefamiglia , tipiendo.codice AS codicetipo , inventarioprocedimenti.codiceinventario,inventarioprocedimenti.amministrazione " //
			 +
			 " ,INVENTARIOPROCDYN2MODELLIT.FK_D2MT_ID FROM inventarioprocedimenti left JOIN tipiendo ON tipiendo.idcomune = inventarioprocedimenti.idcomune AND tipiendo.codice = inventarioprocedimenti.codicetipo " //
			 +
			 " LEFT JOIN tipifamiglieendo ON tipifamiglieendo.idcomune = tipiendo.idcomune AND tipifamiglieendo.codice = tipiendo.codicefamigliaendo " //
			 +
			 " LEFT OUTER JOIN inventarioprocedimentisoftware ON inventarioprocedimentisoftware.idcomune = inventarioprocedimenti.idcomune " //
			 + " AND inventarioprocedimentisoftware.codiceinventario = inventarioprocedimenti.codiceinventario " //
			 +
			 " LEFT JOIN INVENTARIOPROCDYN2MODELLIT ON    INVENTARIOPROCDYN2MODELLIT.IDCOMUNE=inventarioprocedimenti.IDCOMUNE AND  INVENTARIOPROCDYN2MODELLIT.CODICEINVENTARIO=inventarioprocedimenti.CODICEINVENTARIO" //
			 + " WHERE inventarioprocedimenti.idcomune = ? AND ( inventarioprocedimenti.software = ? " //
			 + " OR ( inventarioprocedimentisoftware.idcomune = ? AND inventarioprocedimentisoftware.modulosoftware = ? ) ) "// 
			 + " AND inventarioprocedimenti.codiceinventario=? ";
	    if (escludiDisabilitati != null && escludiDisabilitati.booleanValue()) {
		sql += " and (inventarioprocedimenti.disabilitato is null or inventarioprocedimenti.disabilitato = ? )";
	    }
	    sql += " and inventarioprocedimenti.codiceinventario>=? order by inventarioprocedimenti.codiceinventario";
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    ps = cOrigine.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ps.setString(1, idcomuneOrigine);
	    ps.setString(2, softwareOrigine);
	    ps.setString(3, idcomuneOrigine);
	    ps.setString(4, softwareOrigine);
	    ps.setInt(5, codiceEndo);
	    int pos = 6;
	    if (escludiDisabilitati != null && escludiDisabilitati.booleanValue()) {
		ps.setInt(pos++, 0);
	    }
	    ps.setInt(pos++, CODICE_MASTER);
	    rs = ps.executeQuery();
	    while (rs.next()) {
		if (rs.getObject(1) != null) {
		    codiciFamiglieEndo.add(rs.getInt(1));
		}
		if (rs.getObject(2) != null) {
		    codiciTipiEndo.add(rs.getInt(2));
		}
		if (rs.getObject(3) != null) {
		    // codiceInventario
		}
		if (rs.getObject(4) != null) {
		    // codiceAmministrazione
		    codiciAmministrazioniEndo.add(rs.getInt(4));
		}
		if (rs.getObject(5) != null) {
		    // codiceAmministrazione
		    codiciSchedeEndo.add(rs.getInt(5));
		}
	    }
	    Utils.closeObjects(rs);
	    Utils.closeObjects(ps);
	}
	processAmministrazioni(cOrigine, cDestinazione, "AMMINISTRAZIONI", idcomuneOrigine, idcomuneDestinazione, codiciAmministrazioniEndo);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIFAMIGLIEENDO");
	processTipifamiglieEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciFamiglieEndo);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIENDO");
	processTipiEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciTipiEndo);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO INVENTARIOPROCEDIMENTI");
	processEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciInventario);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO MODELLI DINAMICI");
	processModelliDinamici(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciSchedeEndo);
    }

    public static void main(String[] args) {

	String scCodice = "010701070902";
	String scCodiceOrigine = "0107";
	String nuovoScCodice = "22";
	String scCodiceDaMettere = scCodice.replaceFirst(scCodiceOrigine, nuovoScCodice);
	System.out.println(scCodiceDaMettere);
	System.out.println(Arrays.toString("0107".split("(?<=\\G.{2})")));
    }

    public Set<Integer> processTrovaEndoAlbero(Connection cOrigine, Connection cDestinazione, String tabella, String idcomuneOrigine,
	    String idcomuneDestinazione, String scCodiceOrigine) throws SQLException {

	String query = "select alberoproc.sc_id, alberoproc.sc_descrizione, alberoproc.sc_padre, alberoproc.sc_stato_controllo, alberoproc.sc_ordine, alberoproc.fkidazione, alberoproc.sc_codice, alberoproc.atrib_tipologiaintervento, alberoproc.sc_attivo, vw_alberoproc.sc_descrizione as descrizione_completa " + //
		       " from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where " + //
		       " alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? and alberoproc.sc_codice like ? order by alberoproc.sc_id";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	ps.setString(4, scCodiceOrigine + "%");
	rs = ps.executeQuery();
	//// exists 
	//	StringBuilder queryExists = new StringBuilder("SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE = ? AND SC_ID=?");
	//	PreparedStatement psExists = null;
	//	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	//	ResultSet rsExists = null;
	////// max sc_codice
	//	StringBuilder queryMaxScCodice = new StringBuilder(
	//		"SELECT MAX(SC_CODICE) FROM ALBEROPROC WHERE IDCOMUNE = ? AND LENGTH(SC_CODICE)=? AND SOFTWARE=?");
	//	PreparedStatement psMaxScCodice = null;
	//	psMaxScCodice = cDestinazione.prepareStatement(queryMaxScCodice.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	//	ResultSet rsMaxScCodice = null;
	//	psMaxScCodice.setString(1, idcomuneDestinazione);
	//	psMaxScCodice.setInt(2, 2);
	//	psMaxScCodice.setString(3, softwareDestinazione);
	//	rsMaxScCodice = psMaxScCodice.executeQuery();
	//	String nuovoScCodice = "01";
	//	// cerco nella destinazione se esiste il record
	//	if (rsMaxScCodice.next()) {
	//	    String maxScCodice = rsMaxScCodice.getString(1);
	//	    if (StringUtils.isNotBlank(maxScCodice)) {
	//		Integer codice = Integer.parseInt(maxScCodice);
	//		codice++;
	//		if (codice < 10) {
	//		    nuovoScCodice = "0".concat(String.valueOf(codice));
	//		} else {
	//		    nuovoScCodice = String.valueOf(codice);
	//		}
	//	    }
	//	}
	//	Utils.closeObjects(rsMaxScCodice);
	//	Utils.closeObjects(psMaxScCodice);
	//
	// select codiceinventario from alberoproc_endo where idcomune='E256' AND FKSCID=72760
	StringBuilder queryCodiceEndo = new StringBuilder("select codiceinventario from alberoproc_endo where idcomune=? AND FKSCID=?");
	PreparedStatement psCodiceEndo = null;
	psCodiceEndo = cOrigine.prepareStatement(queryCodiceEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	// select * from inventarioproc_endo where idcomune='E256' and codiceinventario_t=20582
	StringBuilder queryCodiceSubEndo = new StringBuilder(
		"select codiceinventario_d from inventarioproc_endo where idcomune=? AND codiceinventario_t=?");
	PreparedStatement psCodiceSubEndo = null;
	psCodiceSubEndo = cOrigine.prepareStatement(queryCodiceSubEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	// UPDATE
	//	StringBuilder queryUpdate = new StringBuilder(
	//		"UPDATE ALBEROPROC SET SC_DESCRIZIONE=?,SC_PADRE=?,SC_STATO_CONTROLLO=?,SC_ORDINE=?,FKIDAZIONE=?,ATRIB_TIPOLOGIAINTERVENTO=?,SC_ATTIVO=?,DESCRIZIONE_COMPLETA=? WHERE IDCOMUNE = ? AND SC_ID=?");
	//	PreparedStatement psUpdate = null;
	//	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	//	StringBuilder queryInsert = new StringBuilder(
	//		"INSERT INTO ALBEROPROC (IDCOMUNE,SC_ID,SOFTWARE,SC_CODICE,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,ATRIB_TIPOLOGIAINTERVENTO,SC_ATTIVO,DESCRIZIONE_COMPLETA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	//	PreparedStatement psInsert = null;
	//	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	Set<Integer> codiciInventario = new HashSet<Integer>();
	//
	while (rs.next()) {
	    //	    SELECT SC_ID,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,SC_CODICE FROM ALBEROPROC ");
	    //		query.append("WHERE IDCOMUNE = ? AND SOFTWARE=? AND SC_ID>=? ORDER BY SC_ID"
	    Integer codice = rs.getInt(1);
	    //	    String scDescrizione = rs.getString(2);
	    //	    Integer scPadre = rs.getInt(3);
	    //	    String statoControllo = rs.getString(4);
	    //	    Integer scOrdine = rs.getInt(5);
	    //	    Integer fkidazione = null;
	    //	    if (rs.getObject(6) != null) {
	    //		fkidazione = rs.getInt(6);
	    //	    }
	    // String scCodice = rs.getString(7);
	    //	    String scCodiceDaMettere = scCodice.replaceFirst(scCodiceOrigine, nuovoScCodice);
	    //	    String atribTipologiaIntervento = rs.getString(8);
	    //	    Integer scAttivo = rs.getInt(9);
	    //	    String descrizioneCompleta = rs.getString(10);
	    // exists
	    //	    psExists.setString(1, idcomuneDestinazione);
	    //	    psExists.setInt(2, codice);
	    //	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    //	    if (rsExists.next()) {
	    //		Utils.closeObjects(rsExists);
	    //		// se esite lo modifico nella destinazione
	    //		// "UPDATE ALBEROPROC SET SC_CODICE=?,SC_DESCRIZIONE=?,SC_PADRE=?,SC_STATO_CONTROLLO=?,SC_ORDINE=?,FKIDAZIONE=?,ATRIB_TIPOLOGIAINTERVENTO=? WHERE IDCOMUNE = ? AND SC_ID=?"
	    //		// psUpdate.setString(1, scCodice);
	    //		psUpdate.setString(1, scDescrizione);
	    //		psUpdate.setInt(2, scPadre);
	    //		psUpdate.setString(3, statoControllo);
	    //		psUpdate.setInt(4, scOrdine);
	    //		if (fkidazione == null) {
	    //		    psUpdate.setNull(5, Types.INTEGER);
	    //		} else {
	    //		    psUpdate.setInt(5, fkidazione);
	    //		}
	    //		psUpdate.setString(6, atribTipologiaIntervento);
	    //		psUpdate.setInt(7, scAttivo);
	    //		psUpdate.setString(8, descrizioneCompleta);
	    //		psUpdate.setString(9, idcomuneDestinazione);
	    //		psUpdate.setInt(10, codice);
	    //		psUpdate.executeUpdate();
	    //		cDestinazione.commit();
	    //		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    //	    } else {
	    //		Utils.closeObjects(rsExists);
	    //		// "INSERT INTO ALBEROPROC (IDCOMUNE,SC_ID,SOFTWARE,SC_CODICE,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,ATRIB_TIPOLOGIAINTERVENTO)
	    //		psInsert.setString(1, idcomuneDestinazione);
	    //		psInsert.setInt(2, codice);
	    //		psInsert.setString(3, softwareDestinazione);
	    //		psInsert.setString(4, scCodiceDaMettere);
	    //		psInsert.setString(5, scDescrizione);
	    //		psInsert.setInt(6, scPadre);
	    //		psInsert.setString(7, statoControllo);
	    //		psInsert.setInt(8, scOrdine);
	    //		if (fkidazione == null) {
	    //		    psInsert.setNull(9, Types.INTEGER);
	    //		} else {
	    //		    psInsert.setInt(9, fkidazione);
	    //		}
	    //		psInsert.setString(10, atribTipologiaIntervento);
	    //		psInsert.setInt(11, scAttivo);
	    //		psInsert.setString(12, descrizioneCompleta);
	    //		psInsert.executeUpdate();
	    //		cDestinazione.commit();
	    //		// se non esiste inserisco i dati nella destinazione
	    //		activityLog.debug("inserisco {}-{}", tabella, codice);
	    //	    }
	    ////// max sc_codice
	    ResultSet rsCodiceEndo = null;
	    psCodiceEndo.setString(1, idcomuneOrigine);
	    psCodiceEndo.setInt(2, codice);
	    rsCodiceEndo = psCodiceEndo.executeQuery();
	    // cerco nella destinazione se esiste il record
	    while (rsCodiceEndo.next()) {
		Integer codiceEndo = rsCodiceEndo.getInt(1);
		codiciInventario.add(codiceEndo);
		ResultSet rsCodiceSubEndo = null;
		psCodiceSubEndo.setString(1, idcomuneOrigine);
		psCodiceSubEndo.setInt(2, codiceEndo);
		rsCodiceSubEndo = psCodiceSubEndo.executeQuery();
		// cerco nella destinazione se esiste il record
		while (rsCodiceSubEndo.next()) {
		    Integer CodiceSubEndo = rsCodiceSubEndo.getInt(1);
		    codiciInventario.add(CodiceSubEndo);
		}
		Utils.closeObjects(rsCodiceSubEndo);
		//
	    }
	    Utils.closeObjects(rsCodiceEndo);
	    // 
	}
	if (StringUtils.length(scCodiceOrigine) > 2) {
	    Utils.closeObjects(rs);
	    Utils.closeObjects(ps);
	    int lengthCodice = scCodiceOrigine.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodiceOrigine.substring(0, lengthCodice);
		//String[] codiciPadre = scCodiceOrigine.split("(?<=\\G.{2})");
		query = "select alberoproc.sc_id " + //
			" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where " + //
			" alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? and alberoproc.sc_codice = ? order by alberoproc.sc_id";
		ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, idcomuneOrigine);
		ps.setString(2, softwareOrigine);
		ps.setInt(3, CODICE_MASTER);
		ps.setString(4, sccodicePadre);
		rs = ps.executeQuery();
		while (rs.next()) {
		    ////// max sc_codice
		    int codice = rs.getInt(1);
		    ResultSet rsCodiceEndo = null;
		    psCodiceEndo.setString(1, idcomuneOrigine);
		    psCodiceEndo.setInt(2, codice);
		    rsCodiceEndo = psCodiceEndo.executeQuery();
		    // cerco nella destinazione se esiste il record
		    while (rsCodiceEndo.next()) {
			Integer codiceEndo = rsCodiceEndo.getInt(1);
			codiciInventario.add(codiceEndo);
			ResultSet rsCodiceSubEndo = null;
			psCodiceSubEndo.setString(1, idcomuneOrigine);
			psCodiceSubEndo.setInt(2, codiceEndo);
			rsCodiceSubEndo = psCodiceSubEndo.executeQuery();
			// cerco nella destinazione se esiste il record
			while (rsCodiceSubEndo.next()) {
			    Integer CodiceSubEndo = rsCodiceSubEndo.getInt(1);
			    codiciInventario.add(CodiceSubEndo);
			}
			Utils.closeObjects(rsCodiceSubEndo);
			//
		    }
		    Utils.closeObjects(rsCodiceEndo);
		    // 
		}
	    }
	}
	Utils.closeObjects(psCodiceSubEndo);
	Utils.closeObjects(psCodiceEndo);
	//	Utils.closeObjects(psUpdate);
	//	Utils.closeObjects(psInsert);
	//	Utils.closeObjects(rsExists);
	//	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	return codiciInventario;
    }

    public List<CodiceDescrizioneBean> getAmministrazioni(String aliasOrigine, String scCodice) throws SQLException {

	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	if (StringUtils.isNotBlank(scCodice)) {
	    cdbs = trovaAmministrazioniAlberoprocEndo(aliasOrigine, scCodice, cOrigine, idcomuneOrigine);
	    if (cdbs.size() > 0) {
		return cdbs;
	    }
	}
	StringBuilder query = new StringBuilder("SELECT AMMINISTRAZIONE,CODICEANCITEL FROM AMMINISTRAZIONI ");
	query.append("WHERE IDCOMUNE = ?  ORDER BY AMMINISTRAZIONE");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	rs = ps.executeQuery();
	while (rs.next()) {
	    String amministrazione = rs.getString(1);
	    String codiceAncitel = rs.getString(2);
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean(codiceAncitel, amministrazione);
	    cdbs.add(cdb);
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	Utils.closeObjects(cOrigine);
	return cdbs;
    }

    private List<CodiceDescrizioneBean> trovaAmministrazioniAlberoprocEndo(String aliasOrigine, String scCodiceOrigine, Connection cOrigine,
	    String idcomuneOrigine) throws SQLException {

	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	String query = "select alberoproc.sc_id, alberoproc.sc_descrizione, alberoproc.sc_padre, alberoproc.sc_stato_controllo, alberoproc.sc_ordine, alberoproc.fkidazione, alberoproc.sc_codice, alberoproc.atrib_tipologiaintervento, alberoproc.sc_attivo, vw_alberoproc.sc_descrizione as descrizione_completa " + //
		       " from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where " + //
		       " alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? and alberoproc.sc_codice like ? order by alberoproc.sc_id";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	ps.setString(4, scCodiceOrigine + "%");
	rs = ps.executeQuery();
	///////
	StringBuilder queryCodiceEndo = new StringBuilder("select codiceinventario from alberoproc_endo where idcomune=? AND FKSCID=?");
	PreparedStatement psCodiceEndo = null;
	psCodiceEndo = cOrigine.prepareStatement(queryCodiceEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	// select * from inventarioproc_endo where idcomune='E256' and codiceinventario_t=20582
	StringBuilder queryCodiceSubEndo = new StringBuilder(
		"select CODICEINVENTARIO_D from inventarioproc_endo where idcomune=? AND codiceinventario_t=?");
	PreparedStatement psCodiceSubEndo = null;
	psCodiceSubEndo = cOrigine.prepareStatement(queryCodiceSubEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	//////
	Set<Integer> codiciInventario = new HashSet<Integer>();
	while (rs.next()) {
	    //	    SELECT SC_ID,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,SC_CODICE FROM ALBEROPROC ");
	    //		query.append("WHERE IDCOMUNE = ? AND SOFTWARE=? AND SC_ID>=? ORDER BY SC_ID"
	    Integer codice = rs.getInt(1);
	    ////// max sc_codice
	    ResultSet rsCodiceEndo = null;
	    psCodiceEndo.setString(1, idcomuneOrigine);
	    psCodiceEndo.setInt(2, codice);
	    rsCodiceEndo = psCodiceEndo.executeQuery();
	    // cerco nella destinazione se esiste il record
	    while (rsCodiceEndo.next()) {
		Integer codiceEndo = rsCodiceEndo.getInt(1);
		codiciInventario.add(codiceEndo);
		ResultSet rsCodiceSubEndo = null;
		psCodiceSubEndo.setString(1, idcomuneOrigine);
		psCodiceSubEndo.setInt(2, codiceEndo);
		rsCodiceSubEndo = psCodiceSubEndo.executeQuery();
		// cerco nella destinazione se esiste il record
		while (rsCodiceSubEndo.next()) {
		    Integer CodiceSubEndo = rsCodiceSubEndo.getInt(1);
		    codiciInventario.add(CodiceSubEndo);
		}
		Utils.closeObjects(rsCodiceSubEndo);
		//
	    }
	    Utils.closeObjects(rsCodiceEndo);
	    // 
	}
	if (StringUtils.length(scCodiceOrigine) > 2) {
	    Utils.closeObjects(rs);
	    Utils.closeObjects(ps);
	    int lengthCodice = scCodiceOrigine.length();
	    int lengthTree = lengthCodice / 2;
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodiceOrigine.substring(0, lengthCodice);
		//String[] codiciPadre = scCodiceOrigine.split("(?<=\\G.{2})");
		query = "select alberoproc.sc_id " + //
			" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where " + //
			" alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? and alberoproc.sc_codice = ? order by alberoproc.sc_id";
		ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, idcomuneOrigine);
		ps.setString(2, softwareOrigine);
		ps.setInt(3, CODICE_MASTER);
		ps.setString(4, sccodicePadre);
		rs = ps.executeQuery();
		while (rs.next()) {
		    ////// max sc_codice
		    int codice = rs.getInt(1);
		    ResultSet rsCodiceEndo = null;
		    psCodiceEndo.setString(1, idcomuneOrigine);
		    psCodiceEndo.setInt(2, codice);
		    rsCodiceEndo = psCodiceEndo.executeQuery();
		    // cerco nella destinazione se esiste il record
		    while (rsCodiceEndo.next()) {
			Integer codiceEndo = rsCodiceEndo.getInt(1);
			codiciInventario.add(codiceEndo);
			ResultSet rsCodiceSubEndo = null;
			psCodiceSubEndo.setString(1, idcomuneOrigine);
			psCodiceSubEndo.setInt(2, codiceEndo);
			rsCodiceSubEndo = psCodiceSubEndo.executeQuery();
			// cerco nella destinazione se esiste il record
			while (rsCodiceSubEndo.next()) {
			    Integer CodiceSubEndo = rsCodiceSubEndo.getInt(1);
			    codiciInventario.add(CodiceSubEndo);
			}
			Utils.closeObjects(rsCodiceSubEndo);
			//
		    }
		    Utils.closeObjects(rsCodiceEndo);
		    // 
		}
	    }
	}
	Utils.closeObjects(psCodiceSubEndo);
	Utils.closeObjects(psCodiceEndo);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	if (codiciInventario.size() == 0) {
	    return cdbs;
	}
	String sql = "SELECT amministrazioni.amministrazione,amministrazioni.codiceancitel FROM inventarioprocedimenti  inner join amministrazioni on" +
		     " amministrazioni.idcomune=inventarioprocedimenti.idcomune and amministrazioni.codiceamministrazione=inventarioprocedimenti.amministrazione " +
		     " WHERE inventarioprocedimenti.idcomune = ? AND " // 
		     + " inventarioprocedimenti.codiceinventario in  (";
	String inQM = "";
	if (codiciInventario != null && codiciInventario.size() > 0) {
	    for (Integer cm : codiciInventario) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	}
	sql += inQM +
	       ") and inventarioprocedimenti.codiceinventario>=? group by amministrazioni.amministrazione,amministrazioni.codiceancitel order by amministrazioni.amministrazione";
	ps = cOrigine.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	for (Integer cm : codiciInventario) {
	    ps.setInt(pos++, cm);
	}
	ps.setInt(pos++, CODICE_MASTER);
	rs = ps.executeQuery();
	while (rs.next()) {
	    String amministrazione = rs.getString(1);
	    String codiceAncitel = rs.getString(2);
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean(codiceAncitel, amministrazione);
	    cdbs.add(cdb);
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	return cdbs;
    }

    public void doWork(Boolean escludiDisabilitati) throws SQLException {

	if (activityLog.isInfoEnabled()) {
	    activityLog.info("Inizio procedura di copia dati.");
	}
	StatusHelper.aggiungiMessaggio(idOperazione, "Inizio cerco le connessioni origine / destinazione");
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	GetDbConnectionInfoResponse propsDestinazione = sigeproSecurityWSClient.getConnectionProperties(aliasDestinazione);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String idcomuneDestinazione = propsDestinazione.getIdComune();
	StatusHelper.aggiungiMessaggio(idOperazione, "Apro le connessioni origine / destinazione");
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	Connection cDestinazione = sigeproSecurityWSClient.getConnection(aliasDestinazione);
	cOrigine.setReadOnly(true);
	cDestinazione.setAutoCommit(false);
	// FACCIO LA QUERY DI LETTURA di ogni tabella
	// TIPICAUSALIONERI
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPICAUSALIONERI");
	processTipiCausaliOneri(cOrigine, cDestinazione, "TIPICAUSALIONERI", idcomuneOrigine, idcomuneDestinazione);
	// AMMINISTRAZIONI
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO AMMINISTRAZIONI");
	processAmministrazioni(cOrigine, cDestinazione, "AMMINISTRAZIONI", idcomuneOrigine, idcomuneDestinazione, null);
	// ALBEROPROC
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO ALBEROPROC");
	processAlberoproc(cOrigine, cDestinazione, "ALBEROPROC", idcomuneOrigine, idcomuneDestinazione);
	// TIPISOGGETTO
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPISOGGETTO");
	processTipisoggetto(cOrigine, cDestinazione, "TIPISOGGETTO", idcomuneOrigine, idcomuneDestinazione);
	// TIPIFAMIGLIEENDO
	// TIPIENDO
	// INVENTARIOPROC
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO GLI ENDO");
	Set<Integer> schedeEndo = processEndoprocedimenti(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, escludiDisabilitati);
	// DYN2_CAMPI
	// DYN2_CAMPI_SCRIPT
	// DYN2_CAMPIPROPRIETA
	// DYN2_MODELLIT
	// DYN2_MODELLIDTESTI
	// DYN2_MODELLID
	processModelliDinamici(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, schedeEndo);
	Utils.closeObjects(cDestinazione);
	Utils.closeObjects(cOrigine);
	StatusHelper.aggiungiMessaggio(idOperazione, "OPERAZIONE TERMINATA");
    }

    private void processModelliDinamici(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciModelliT) throws SQLException {

	// trovare i campi e i dyn2ModellidTesti
	Set<Integer> codiciCampi = new HashSet<Integer>();
	Set<Integer> codiciModelliDTesti = new HashSet<Integer>();
	if (codiciModelliT != null && !codiciModelliT.isEmpty()) {
	    for (Integer codiceModello : codiciModelliT) {
		activityLog.debug("=====================================");
		activityLog.debug("processModelliDinamici processo il modello :{} ", codiceModello);
		StringBuilder query = new StringBuilder(
			"SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA " // 
							+ "FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? and FK_D2MT_ID=? ORDER BY ID");
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		ps.setString(1, idcomuneOrigine);
		ps.setInt(2, CODICE_MASTER);
		ps.setInt(3, codiceModello);
		rs = ps.executeQuery();
		while (rs.next()) {
		    //	 "SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
		    //Integer codice = rs.getInt(1);
		    //System.out.println("processDyn2ModelliD: " + codice);
		    // Integer fkd2mtid = rs.getInt(2);
		    Integer fkd2cid = Utils.getIntegerOrNull(3, rs);
		    Integer fkd2mdtid = Utils.getIntegerOrNull(4, rs);
		    if (fkd2cid != null) {
			codiciCampi.add(fkd2cid);
			activityLog.debug("\tmodello {}, trovato campo {} ", codiceModello, fkd2cid);
		    }
		    if (fkd2mdtid != null) {
			codiciModelliDTesti.add(fkd2mdtid);
		    }
		    //		    Integer posverticale = rs.getInt(5);
		    //		    Integer posorizzontale = rs.getInt(6);
		    //		    Integer flagMultiplo = rs.getInt(7);
		    //		    Integer fkRegolaAttivo = Utils.getIntegerOrNull(8, rs);
		    //		    Integer flagObbligatorio = rs.getInt(9);
		    //		    Integer flagSpezza = rs.getInt(10);
		}
		Utils.closeObjects(rs);
		Utils.closeObjects(ps);
		activityLog.debug("==============================");
	    }
	}
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO MODELLI DINAMICI");
	processDyn2Campi(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciCampi);
	processDyn2ModelliDTesti(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciModelliDTesti);
	processDyn2ModelliT(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciModelliT);
    }

    private void processDyn2ModelliD(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Integer codiceModello) throws SQLException {

	String tabella = "DYN2_MODELLID";
	StringBuilder query = new StringBuilder(
		"SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA " // 
						+ "FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? and FK_D2MT_ID=? ORDER BY ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, CODICE_MASTER);
	ps.setInt(3, codiceModello);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT ID FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE DYN2_MODELLID SET FK_D2MT_ID=?,FK_D2C_ID=?,FK_D2MDT_ID=?,POSVERTICALE=?,POSORIZZONTALE=?,FLG_MULTIPLO=?,FK_REGOLA_ATTIVO=?,FLG_OBBLIGATORIO=?,FLG_SPEZZA_TABELLA=? WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO DYN2_MODELLID (IDCOMUNE,ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	int pos = 1;
	while (rs.next()) {
	    //	 "SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    //System.out.println("processDyn2ModelliD: " + codice);
	    Integer fkd2mtid = rs.getInt(2);
	    Integer fkd2cid = Utils.getIntegerOrNull(3, rs);
	    Integer fkd2mdtid = Utils.getIntegerOrNull(4, rs);
	    Integer posverticale = rs.getInt(5);
	    Integer posorizzontale = rs.getInt(6);
	    Integer flagMultiplo = rs.getInt(7);
	    Integer fkRegolaAttivo = Utils.getIntegerOrNull(8, rs);
	    Integer flagObbligatorio = rs.getInt(9);
	    Integer flagSpezza = rs.getInt(10);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_MODELLID SET FK_D2MT_ID=?,FK_D2C_ID=?,FK_D2MDT_ID=?,POSVERTICALE=?,POSORIZZONTALE=?,FLG_MULTIPLO=?,FK_REGOLA_ATTIVO=?,FLG_OBBLIGATORIO=?,FLG_SPEZZA_TABELLA=? WHERE IDCOMUNE = ? AND ID=?"
		pos = 1;
		psUpdate.setInt(pos++, fkd2mtid);
		Utils.setIntOrNUll(fkd2cid, psUpdate, pos++);
		Utils.setIntOrNUll(fkd2mdtid, psUpdate, pos++);
		Utils.setIntOrNUll(posverticale, psUpdate, pos++);
		Utils.setIntOrNUll(posorizzontale, psUpdate, pos++);
		psUpdate.setInt(pos++, flagMultiplo);
		Utils.setIntOrNUll(fkRegolaAttivo, psUpdate, pos++);
		psUpdate.setInt(pos++, flagObbligatorio);
		psUpdate.setInt(pos++, flagSpezza);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_MODELLID (IDCOMUNE,ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA) VALUES (?,?,?,?,?,?,?,?,?,?,?)"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setInt(pos++, fkd2mtid);
		Utils.setIntOrNUll(fkd2cid, psInsert, pos++);
		Utils.setIntOrNUll(fkd2mdtid, psInsert, pos++);
		Utils.setIntOrNUll(posverticale, psInsert, pos++);
		Utils.setIntOrNUll(posorizzontale, psInsert, pos++);
		psInsert.setInt(pos++, flagMultiplo);
		Utils.setIntOrNUll(fkRegolaAttivo, psInsert, pos++);
		psInsert.setInt(pos++, flagObbligatorio);
		psInsert.setInt(pos++, flagSpezza);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processDyn2ModelliDTesti(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciD2mdTesti) throws SQLException {

	String tabella = "DYN2_MODELLIDTESTI";
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_MODELLIDTESTI");
	String query = "SELECT ID,FK_D2BTT_ID,TESTO FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID>=?  ";
	if (codiciD2mdTesti != null && codiciD2mdTesti.size() > 0) {
	    if (codiciD2mdTesti != null && codiciD2mdTesti.size() < 1000) {
		query += "  AND ID in (";
		String inQM = "";
		for (Integer cm : codiciD2mdTesti) {
		    inQM += ",?";
		}
		inQM = inQM.replaceFirst(",", "");
		query += inQM + ")";
	    } else {
		int num = codiciD2mdTesti.size();
		Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
		Double cicli = filter_getListaCodiceAttivita_length / 1000;
		int cicliDaMille = cicli.intValue();
		int resto = num - (cicliDaMille * 1000);
		query += " and ( 1=2 ";
		for (int i = 0; i < cicliDaMille; i++) {
		    String qm = StringUtils.repeat("?,", 1000);
		    qm = qm.substring(0, qm.length() - 1);
		    query += " or ID in (" + qm + ")";
		}
		if (resto > 0) {
		    String qm = StringUtils.repeat("?,", resto);
		    qm = qm.substring(0, qm.length() - 1);
		    query += " or ID in (" + qm + ")";
		}
		query += ")";
	    }
	}
	query += "  ORDER BY ID";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciD2mdTesti != null && codiciD2mdTesti.size() > 0) {
	    for (Integer cm : codiciD2mdTesti) {
		ps.setInt(pos++, cm);
	    }
	}
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT ID FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder("UPDATE DYN2_MODELLIDTESTI SET FK_D2BTT_ID=?,TESTO=? WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder("INSERT INTO DYN2_MODELLIDTESTI (IDCOMUNE,ID,FK_D2BTT_ID,TESTO) VALUES (?,?,?,? )");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	pos = 1;
	while (rs.next()) {
	    //	 "SELECT ID,FK_D2BTT_ID,TESTO FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    String d2btt = rs.getString(2);
	    String testo = rs.getString(3);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_MODELLIDTESTI SET FK_D2BTT_ID=?,TESTO=? WHERE IDCOMUNE = ? AND ID=?"
		pos = 1;
		psUpdate.setString(pos++, d2btt);
		psUpdate.setString(pos++, testo);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_MODELLIDTESTI (IDCOMUNE,ID,FK_D2BTT_ID,TESTO) VALUES (?,?,?,? )"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setString(pos++, d2btt);
		psInsert.setString(pos++, testo);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processDyn2ModelliT(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciModelli) throws SQLException {

	String tabella = "DYN2_MODELLIT";
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_MODELLIT");
	String query = "SELECT ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA," +
		       "FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA FROM DYN2_MODELLIT" //
		       + " WHERE IDCOMUNE = ? AND ID>=?   ";
	if (codiciModelli != null && codiciModelli.size() > 0) {
	    query += " and  ID in (";
	    String inQM = "";
	    for (int i = 0; i < codiciModelli.size(); i++) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
	}
	query += " ORDER BY ID";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciModelli != null && codiciModelli.size() > 0) {
	    for (Integer cm : codiciModelli) {
		ps.setInt(pos++, cm);
	    }
	}
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT ID FROM DYN2_MODELLIT WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE DYN2_MODELLIT SET SOFTWARE=?,DESCRIZIONE=?,FK_D2BC_ID=?,SCRIPTCODE=?,MODELLOMULTIPLO=?,FLG_STORICIZZA=?,FLG_READONLY_WEB=?,MODELLO_FRONTOFFICE=?,CODICE_SCHEDA=? WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO DYN2_MODELLIT (IDCOMUNE,ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	pos = 1;
	while (rs.next()) {
	    //	 "SELECT ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA FROM DYN2_MODELLIT WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    String software = rs.getString(2);
	    String descrizione = rs.getString(3);
	    String d2bcid = rs.getString(4);
	    String scriptCodce = rs.getString(5);
	    Integer modellomultiplo = rs.getInt(6);
	    Integer flgStoricizza = rs.getInt(7);
	    Integer flagReadOnlyWeb = rs.getInt(8);
	    Integer modelloFrontoffice = rs.getInt(9);
	    String codiceScheda = rs.getString(10);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_MODELLIT SET SOFTWARE=?,DESCRIZIONE=?,FK_D2BC_ID=?,SCRIPTCODE=?,MODELLOMULTIPLO=?,FLG_STORICIZZA=?,FLG_READONLY_WEB=?,MODELLO_FRONTOFFICE=?,CODICE_SCHEDA=? WHERE IDCOMUNE = ? AND ID=?"
		pos = 1;
		psUpdate.setString(pos++, software);
		psUpdate.setString(pos++, descrizione);
		psUpdate.setString(pos++, d2bcid);
		psUpdate.setString(pos++, scriptCodce);
		psUpdate.setInt(pos++, modellomultiplo);
		psUpdate.setInt(pos++, flgStoricizza);
		psUpdate.setInt(pos++, flagReadOnlyWeb);
		psUpdate.setInt(pos++, modelloFrontoffice);
		psUpdate.setString(pos++, codiceScheda);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_MODELLIT (IDCOMUNE,ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA) VALUES (?,?,?,?,?,?,?,?,?,?,?)"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setString(pos++, software);
		psInsert.setString(pos++, descrizione);
		psInsert.setString(pos++, d2bcid);
		psInsert.setString(pos++, scriptCodce);
		psInsert.setInt(pos++, modellomultiplo);
		psInsert.setInt(pos++, flgStoricizza);
		psInsert.setInt(pos++, flagReadOnlyWeb);
		psInsert.setInt(pos++, modelloFrontoffice);
		psInsert.setString(pos++, codiceScheda);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	    processDyn2ModelliTScript(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codice);
	    processDyn2ModelliD(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codice);
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processDyn2ModelliTScript(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Integer codiceModelloT) throws SQLException {

	String tabella = "DYN2_MODELLI_SCRIPT";
	StringBuilder query = new StringBuilder("SELECT FK_D2MT_ID,EVENTO,SCRIPT  FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID=?");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, codiceModelloT);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT * FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=? ");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder("UPDATE DYN2_MODELLI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement("INSERT INTO DYN2_MODELLI_SCRIPT (IDCOMUNE,FK_D2MT_ID,EVENTO,SCRIPT) VALUES (?,?,?,?)");
	//	
	int pos = 1;
	while (rs.next()) {
	    //	 "SELECT FK_D2MT_ID,EVENTO,SCRIPT  FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID>=? ORDER BY FK_D2MT_ID"
	    Integer codice = rs.getInt(1);
	    String evento = rs.getString(2);
	    Blob script = rs.getBlob(3);
	    InputStream fis = null;
	    if (script != null) {
		fis = script.getBinaryStream();
	    }
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    psExists.setString(3, evento);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_MODELLI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=?"
		pos = 1;
		// Utils.setBlobOrNUll(script, psUpdate, pos++);
		Utils.setBlobOrNUll(fis, psUpdate, pos++);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		psUpdate.setString(pos++, evento);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
		psUpdate.clearParameters();
	    } else {
		//		INSERT INTO DYN2_MODELLI_SCRIPT (IDCOMUNE,FK_D2MT_ID,EVENTO,SCRIPT) VALUES (?,?,?,?)"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setString(pos++, evento);
		Utils.setBlobOrNUll(fis, psInsert, pos++);
		// Utils.setBlobOrNUll(script, psInsert, pos++);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
		psInsert.clearParameters();
	    }
	    try {
		if (fis != null) {
		    fis.close();
		}
	    } catch (Exception e) {
		//
	    }
	    Utils.closeObjects(rsExists);
	    psExists.clearParameters();
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processDyn2CampiSProprieta(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Integer codiceCampo) throws SQLException {

	String tabella = "DYN2_CAMPIPROPRIETA";
	StringBuilder query = new StringBuilder(
		"SELECT FK_D2C_ID,PROPRIETA,VALORE FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, codiceCampo);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT FK_D2C_ID FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND PROPRIETA=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder("UPDATE DYN2_CAMPIPROPRIETA SET VALORE=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND PROPRIETA=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder("INSERT INTO DYN2_CAMPIPROPRIETA (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE) VALUES (?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	int pos = 1;
	while (rs.next()) {
	    //	 "SELECT FK_D2C_ID,PROPRIETA,VALORE FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID"
	    Integer codice = rs.getInt(1);
	    String proprieta = rs.getString(2);
	    String valore = rs.getString(3);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    psExists.setString(3, proprieta);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_CAMPIPROPRIETA SET VALORE=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND PROPRIETA=?"
		pos = 1;
		psUpdate.setString(pos++, valore);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		psUpdate.setString(pos++, proprieta);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_CAMPIPROPRIETA (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE) VALUES (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE)"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setString(pos++, proprieta);
		psInsert.setString(pos++, valore);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processDyn2CampiScript(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Integer codiceCampo) throws SQLException {

	String tabella = "DYN2_CAMPI_SCRIPT";
	StringBuilder query = new StringBuilder(
		"SELECT FK_D2C_ID,EVENTO,SCRIPT FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, codiceCampo);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT FK_D2C_ID FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND EVENTO=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder("UPDATE DYN2_CAMPI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND EVENTO=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder("INSERT INTO DYN2_CAMPI_SCRIPT (IDCOMUNE,FK_D2C_ID,EVENTO,SCRIPT ) VALUES (?,?,?,? )");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	int pos = 1;
	while (rs.next()) {
	    //	 "SELECT FK_D2C_ID,EVENTO,SCRIPT FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID>=? ORDER BY FK_D2C_ID"
	    Integer codice = rs.getInt(1);
	    String evento = rs.getString(2);
	    Blob script = rs.getBlob(3);
	    InputStream fis = null;
	    if (script != null) {
		fis = script.getBinaryStream();
	    }
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    psExists.setString(3, evento);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_CAMPI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND EVENTO=?"
		pos = 1;
		// psUpdate.setBinaryStream(pos++, fis);
		Utils.setBlobOrNUll(fis, psUpdate, pos++);
		// Utils.setBlobOrNUll(script, psUpdate, pos++);
		psUpdate.setString(pos++, idcomuneDestinazione);
		psUpdate.setInt(pos++, codice);
		psUpdate.setString(pos++, evento);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_CAMPI_SCRIPT (IDCOMUNE,FK_D2C_ID,EVENTO,SCRIPT ) VALUES (IDCOMUNE,FK_D2C_ID,EVENTO,SCRIPT)"
		pos = 1;
		psInsert.setString(pos++, idcomuneDestinazione);
		psInsert.setInt(pos++, codice);
		psInsert.setString(pos++, evento);
		Utils.setBlobOrNUll(fis, psInsert, pos++);
		// psInsert.setBinaryStream(pos++, fis);
		// Utils.setBlobOrNUll(script, psInsert, pos++);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	    try {
		if (fis != null) {
		    fis.close();
		}
	    } catch (Exception e) {
		//
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists, rs, ps);
    }

    private void processDyn2Campi(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciCampi) throws SQLException {

	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_CAMPI");
	String tabella = "DYN2_CAMPI";
	String query = "SELECT ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID FROM DYN2_CAMPI WHERE " +
		       "IDCOMUNE = ? AND ID>=?  ";
	if (codiciCampi != null && codiciCampi.size() > 0) {
	    if (codiciCampi != null && codiciCampi.size() < 1000) {
		query += "  AND ID in (";
		String inQM = "";
		for (Integer cm : codiciCampi) {
		    inQM += ",?";
		}
		inQM = inQM.replaceFirst(",", "");
		query += inQM + ")";
	    } else {
		int num = codiciCampi.size();
		Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
		Double cicli = filter_getListaCodiceAttivita_length / 1000;
		int cicliDaMille = cicli.intValue();
		int resto = num - (cicliDaMille * 1000);
		query += " and ( 1=2 ";
		for (int i = 0; i < cicliDaMille; i++) {
		    String qm = StringUtils.repeat("?,", 1000);
		    qm = qm.substring(0, qm.length() - 1);
		    query += " or ID in (" + qm + ")";
		}
		if (resto > 0) {
		    String qm = StringUtils.repeat("?,", resto);
		    qm = qm.substring(0, qm.length() - 1);
		    query += " or ID in (" + qm + ")";
		}
		query += ")";
	    }
	}
	query += " and SOFTWARE in(?,?) ORDER BY ID";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciCampi != null && codiciCampi.size() > 0) {
	    for (Integer cm : codiciCampi) {
		ps.setInt(pos++, cm);
	    }
	}
	ps.setString(pos++, SOFTWARE_TT);
	ps.setString(pos++, softwareOrigine);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT ID FROM DYN2_CAMPI WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE DYN2_CAMPI SET SOFTWARE=?,NOMECAMPO=?,ETICHETTA=?,DESCRIZIONE=?,TIPODATO=?,OBBLIGATORIO=?,SCRPTCODE=?,SCRIPTUPDATECODE=?,FK_D2BC_ID=? WHERE IDCOMUNE = ? AND ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO DYN2_CAMPI (IDCOMUNE,ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	while (rs.next()) {
	    //	 "SELECT ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID FROM DYN2_CAMPI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    String software = rs.getString(2);
	    String nomecampo = rs.getString(3);
	    String etichetta = rs.getString(4);
	    String descrizione = rs.getString(5);
	    String tipoDato = rs.getString(6);
	    Integer obbligatorio = rs.getInt(7);
	    String scriptCode = rs.getString(8);
	    String scriptUpdateCode = rs.getString(9);
	    String fkd2bcId = rs.getString(10);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE DYN2_CAMPI SET SOFTWARE=?,NOMECAMPO=?,ETICHETTA=?,DESCRIZIONE=?,TIPODATO=?,OBBLIGATORIO=?,SCRPTCODE=?,SCRIPTUPDATECODE=?,FK_D2BC_ID=? WHERE IDCOMUNE = ? AND ID=?"
		psUpdate.setString(1, software);
		psUpdate.setString(2, nomecampo);
		psUpdate.setString(3, etichetta);
		psUpdate.setString(4, descrizione);
		psUpdate.setString(5, tipoDato);
		psUpdate.setInt(6, obbligatorio);
		psUpdate.setString(7, scriptCode);
		psUpdate.setString(8, scriptUpdateCode);
		psUpdate.setString(9, fkd2bcId);
		psUpdate.setString(10, idcomuneDestinazione);
		psUpdate.setInt(11, codice);
		// update
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		INSERT INTO DYN2_CAMPI (IDCOMUNE,ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID) VALUES (IDCOMUNE,ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID)"
		psInsert.setString(1, idcomuneDestinazione);
		psInsert.setInt(2, codice);
		psInsert.setString(3, software);
		psInsert.setString(4, nomecampo);
		psInsert.setString(5, etichetta);
		psInsert.setString(6, descrizione);
		psInsert.setString(7, tipoDato);
		psInsert.setInt(8, obbligatorio);
		psInsert.setString(9, scriptCode);
		psInsert.setString(10, scriptUpdateCode);
		psInsert.setString(11, fkd2bcId);
		// insert
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	    processDyn2CampiScript(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codice);
	    processDyn2CampiSProprieta(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codice);
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private Set<Integer> processEndoprocedimenti(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Boolean escludiDisabilitati) throws SQLException {

	Set<Integer> codiciFamiglieEndo = new HashSet<Integer>();
	Set<Integer> codiciTipiEndo = new HashSet<Integer>();
	Set<Integer> codiciSchedeEndo = new HashSet<Integer>();
	Set<Integer> codiciEndo = new HashSet<Integer>();
	String sql = "SELECT tipifamiglieendo.codice AS codicefamiglia , tipiendo.codice AS codicetipo , inventarioprocedimenti.codiceinventario,INVENTARIOPROCDYN2MODELLIT.FK_D2MT_ID  " //
		     +
		     "FROM inventarioprocedimenti left JOIN tipiendo ON tipiendo.idcomune = inventarioprocedimenti.idcomune AND tipiendo.codice = inventarioprocedimenti.codicetipo " //
		     +
		     "LEFT JOIN tipifamiglieendo ON tipifamiglieendo.idcomune = tipiendo.idcomune AND tipifamiglieendo.codice = tipiendo.codicefamigliaendo " //
		     + "LEFT OUTER JOIN inventarioprocedimentisoftware ON inventarioprocedimentisoftware.idcomune = inventarioprocedimenti.idcomune " //
		     + "AND inventarioprocedimentisoftware.codiceinventario = inventarioprocedimenti.codiceinventario " //
		     + "LEFT JOIN INVENTARIOPROCDYN2MODELLIT ON  " + "INVENTARIOPROCDYN2MODELLIT.IDCOMUNE=inventarioprocedimenti.IDCOMUNE AND " +
		     "INVENTARIOPROCDYN2MODELLIT.CODICEINVENTARIO=inventarioprocedimenti.CODICEINVENTARIO " +
		     " WHERE inventarioprocedimenti.idcomune = ? AND ( inventarioprocedimenti.software = ? " //
		     + "OR ( inventarioprocedimentisoftware.idcomune = ? AND inventarioprocedimentisoftware.modulosoftware = ? ) ) "// 
		     + "AND inventarioprocedimenti.codiceinventario>=? ";
	if (escludiDisabilitati != null && escludiDisabilitati.booleanValue()) {
	    sql += " and (inventarioprocedimenti.disabilitato is null or inventarioprocedimenti.disabilitato = ? )";
	}
	sql += " order by inventarioprocedimenti.codiceinventario";
	activityLog.debug("================================");
	activityLog.debug("processEndoprocedimenti sql=\n\t{}", sql);
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setString(3, idcomuneOrigine);
	ps.setString(4, softwareOrigine);
	ps.setInt(5, CODICE_MASTER);
	if (escludiDisabilitati != null && escludiDisabilitati.booleanValue()) {
	    ps.setInt(6, 0);
	}
	rs = ps.executeQuery();
	activityLog.debug("idcomuneOrigine:{}, softwareOrigine: {}, CODICE_MASTER:{} ", idcomuneOrigine, softwareOrigine, CODICE_MASTER);
	activityLog.debug("================================");
	while (rs.next()) {
	    //	    SELECT CODICETIPOSOGGETTO,TIPOSOGGETTO,FLAGQUALITA,UTILIZZO,RICHIEDIANAGRAFECOLL," //
	    //		+ "FLG_SPECIFICADESCRIZIONE,FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA FROM TIPISOGGETTO ");
	    //	query.append("WHERE IDCOMUNE = ? AND SOFTWARE =? AND CODICETIPOSOGGETTO>=? ORDER BY SC_ID");
	    if (rs.getObject(1) != null) {
		codiciFamiglieEndo.add(rs.getInt(1));
	    }
	    if (rs.getObject(2) != null) {
		codiciTipiEndo.add(rs.getInt(2));
	    }
	    if (rs.getObject(3) != null) {
		codiciEndo.add(rs.getInt(3));
	    }
	    if (rs.getObject(4) != null) {
		codiciSchedeEndo.add(rs.getInt(4));
	    }
	}
	activityLog.debug("codiciSchedeEndo: {}", codiciSchedeEndo);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIFAMIGLIEENDO");
	processTipifamiglieEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciFamiglieEndo);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIENDO");
	processTipiEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciTipiEndo);
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO INVENTARIOPROCEDIMENTI");
	processEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciEndo);
	return codiciSchedeEndo;
    }

    private Integer trovaNaturaEndoDaNaturaBase(Connection cDestinazione, String idcomuneDestinazione, String naturaBase) throws SQLException {

	if (StringUtils.isBlank(naturaBase)) {
	    return null;
	}
	String sql = "SELECT codicenatura FROM NATURAENDO WHERE IDCOMUNE=? and naturabase=?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cDestinazione.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneDestinazione);
	ps.setString(2, naturaBase);
	rs = ps.executeQuery();
	Integer result = null;
	if (rs.next()) {
	    if (rs.getObject(1) != null) {
		result = rs.getInt(1);
	    }
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	return result;
    }

    private void processEndo(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciEndo) throws SQLException {

	if (codiciEndo == null || codiciEndo.isEmpty()) {
	    return;
	}
	activityLog.debug("codici endo da elaborare {}", codiciEndo);
	String tabella = "INVENTARIOPROCEDIMENTI";
	//// exists 
	StringBuilder queryExists = new StringBuilder(
		"SELECT CODICEINVENTARIO FROM INVENTARIOPROCEDIMENTI WHERE IDCOMUNE = ? AND CODICEINVENTARIO=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// CODICEINVENTARIO,PROCEDIMENTO,AMMINISTRAZIONE,DATAAGGIORNAMENTO,CODICETIPO,DISABILITATO,ORDINE,CODICENATURA,CODICEANCITEL,FLAG_PUBBLICA, SOFTWARE
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE INVENTARIOPROCEDIMENTI SET PROCEDIMENTO=?,AMMINISTRAZIONE=?,DATAAGGIORNAMENTO=?,CODICETIPO=?,DISABILITATO=?,ORDINE=?,CODICENATURA=?,CODICEANCITEL=?,FLAG_PUBBLICA=?,TIPOMOVIMENTO=?,SOFTWARE=? WHERE IDCOMUNE=? AND CODICEINVENTARIO=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO INVENTARIOPROCEDIMENTI (IDCOMUNE,CODICEINVENTARIO,PROCEDIMENTO,AMMINISTRAZIONE,DATAAGGIORNAMENTO,CODICETIPO,DISABILITATO,ORDINE,CODICENATURA,CODICEANCITEL,FLAG_PUBBLICA,SOFTWARE,TIPOMOVIMENTO) " //
						      + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuilder query = new StringBuilder(
		"SELECT naturaendobase.naturabase, procedimento, amministrazione, dataaggiornamento, codicetipo, disabilitato, ordine, codiceancitel, flag_pubblica,inventarioprocedimenti.software " //
						+
						"FROM inventarioprocedimenti left OUTER JOIN naturaendobase ON naturaendobase.codicenatura = inventarioprocedimenti.codicenatura WHERE inventarioprocedimenti.IDCOMUNE = ? AND inventarioprocedimenti.CODICEINVENTARIO=?");
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	Map<String, Integer> naturebaseCodici = new HashMap<String, Integer>();
	//	scia
	//	ordinario
	//	comunicazione
	
	for (Integer codice : codiciEndo) {
	    activityLog.debug("elaboro {}-{}", tabella, codice);
	    ps.setString(1, idcomuneOrigine);
	    ps.setInt(2, codice);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		String naturabase = rs.getString(1);
		String procedimento = rs.getString(2);
		Integer amministrazione = null;
		Integer ammLocale = null;
		if (rs.getObject(3) != null) {
		    amministrazione = rs.getInt(3);
		    ammLocale = ammRemoteAmmLoc.get(amministrazione);
		    if (ammLocale != null) {
			amministrazione = ammLocale;
		    }
		}
		Date dataaggiornamento = null;
		if (rs.getObject(4) != null) {
		    dataaggiornamento = rs.getDate(4);
		}
		Integer codiceTipo = null;
		if (rs.getObject(5) != null) {
		    codiceTipo = rs.getInt(5);
		}
		Integer disabilitato = rs.getInt(6);
		Integer ordine = rs.getInt(7);
		String codiceancitel = rs.getString(8);
		Integer flagPubblica = rs.getInt(9);
		String software = rs.getString(10);
		Utils.closeObjects(rs);
		// devo trovare la natura endo
		Integer codiceNaturaDest = naturebaseCodici.get(naturabase);
		int fakeCodice = -10000;
		if (codiceNaturaDest == null) {
		    Integer nat = trovaNaturaEndoDaNaturaBase(cDestinazione, idcomuneDestinazione, naturabase);
		    if (nat == null) {
			nat = Integer.valueOf(fakeCodice);
		    }
		    naturebaseCodici.put(naturabase, nat);
		} else {
		    if (codiceNaturaDest.equals(fakeCodice)) {
			codiceNaturaDest = null;
		    }
		}
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codice);
		rsExists = psExists.executeQuery();
		// cerco nella destinazione se esiste il record
		String tipomovimento = null;
		if (ammCodAncitel.get(amministrazione) != null) {
		    String codiceAncitelAmministrazione = ammCodAncitel.get(amministrazione);
		    if (StringUtils.isNotBlank(codiceAncitelAmministrazione)) {
			tipomovimento = getTipomovFromNatura(naturabase, codiceAncitelAmministrazione);
		    }
		}
		activityLog.debug(
			"dati Endoprocedimento codice {},procedimento {}, amministrazione {}, ammLocale {}, codiceTipo {}, codiceAncitel {}, software {}, tipomovimento {}",
			new Object[] { codice, procedimento, amministrazione, ammLocale, codiceTipo, codiceancitel, software, tipomovimento });
		if (rsExists.next()) {
		    Utils.closeObjects(rsExists);
		    // se esite lo modifico nella destinazione
		    // UPDATE INVENTARIOPROCEDIMENTI SET PROCEDIMENTO=?,AMMINISTRAZIONE=?,DATAAGGIORNAMENTO=?,CODICETIPO=?,DISABILITATO=?,ORDINE=?,CODICENATURA=?,
		    // CODICEANCITEL=?,FLAG_PUBBLICA=?,TIPOMOVIMENTO=? WHERE IDCOMUNE=? AND CODICEINVENTARIO=?
		    psUpdate.setString(1, procedimento);
		    if (amministrazione == null) {
			psUpdate.setNull(2, Types.INTEGER);
		    } else {
			psUpdate.setInt(2, amministrazione);
		    }
		    Utils.setDateOrNUll(dataaggiornamento, psUpdate, 3);
		    if (codiceTipo == null) {
			psUpdate.setNull(4, Types.INTEGER);
		    } else {
			psUpdate.setInt(4, codiceTipo);
		    }
		    psUpdate.setInt(5, disabilitato);
		    psUpdate.setInt(6, ordine);
		    if (codiceNaturaDest == null) {
			psUpdate.setNull(7, Types.INTEGER);
		    } else {
			psUpdate.setInt(7, codiceNaturaDest);
		    }
		    psUpdate.setString(8, codiceancitel);
		    psUpdate.setInt(9, flagPubblica);
		    if (StringUtils.isNotBlank(tipomovimento)) {
			psUpdate.setString(10, tipomovimento);
		    } else {
			psUpdate.setNull(10, Types.VARCHAR);
		    }
		    psUpdate.setString(11, software);
		    psUpdate.setString(12, idcomuneDestinazione);
		    psUpdate.setInt(13, codice);
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    activityLog.debug("aggiorno {}-{}", tabella, codice);
		} else {
		    Utils.closeObjects(rsExists);
		    //INVENTARIOPPROCEDIMENTI (IDCOMUNE,CODICEINVENTARIO,PROCEDIMENTO,AMMINISTRAZIONE,DATAAGGIORNAMENTO,CODICETIPO,DISABILITATO,ORDINE,CODICENATURA,CODICEANCITEL,FLAG_PUBBLICA,
		    // SOFTWARE) " //
		    psInsert.setString(1, idcomuneDestinazione);
		    psInsert.setInt(2, codice);
		    psInsert.setString(3, procedimento);
		    if (amministrazione == null) {
			psInsert.setNull(4, Types.INTEGER);
		    } else {
			psInsert.setInt(4, amministrazione);
		    }
		    if (dataaggiornamento == null) {
			psInsert.setNull(5, Types.DATE);
		    } else {
			psInsert.setDate(5, dataaggiornamento);
		    }
		    if (codiceTipo == null) {
			psInsert.setNull(6, Types.INTEGER);
		    } else {
			psInsert.setInt(6, codiceTipo);
		    }
		    psInsert.setInt(7, disabilitato);
		    psInsert.setInt(8, ordine);
		    if (codiceNaturaDest == null) {
			psInsert.setNull(9, Types.INTEGER);
		    } else {
			psInsert.setInt(9, codiceNaturaDest);
		    }
		    psInsert.setString(10, codiceancitel);
		    psInsert.setInt(11, flagPubblica);
		    psInsert.setString(12, software);
		    if (StringUtils.isNotBlank(tipomovimento)) {
			psInsert.setString(13, tipomovimento);
		    } else {
			psInsert.setNull(13, Types.VARCHAR);
		    }
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codice);
		}
		if (software.equalsIgnoreCase(SOFTWARE_TT)) {
		    // verifico/inserisco su inventarioprocedimentisoftware destinazione
		    inserisciSuInvprocSoftware(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codice, amministrazione,
			    tipomovimento);
		}
	    }
	}
	// inserisciSubEndo(cOrigine, cDestinazione, idcomuneOrigine, idcomuneDestinazione, codiciEndo);
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }
    //    private void inserisciSu bEndo(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
    //	    Set<Integer> codiciEndo) throws SQLException {
    //
    //	String sqlDelete = "delete from inventarioproc_endo where idcomune=? and codiceinventario_t=?";
    //	PreparedStatement psDelete = cDestinazione.prepareStatement(sqlDelete);
    //	String sql = "select codiceinventario_d, flag_pubblica,flag_necessario,codicecomune from inventarioproc_endo where idcomune=? and fk_cid_idcomune=? and codiceinventario_t=?";
    //	ResultSet rs = null;
    //	ResultSet rsMax = null;
    //	PreparedStatement ps = cOrigine.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    //	//
    //	String sqlInsert = "INSERT INTO inventarioproc_endo(idcomune,id,codiceinventario_t,codiceinventario_d,flag_pubblica,flag_necessario,codicecomune) VALUES (?,?,?,?,?,?,?)";
    //	PreparedStatement psInsert = cDestinazione.prepareStatement(sqlInsert);
    //	String sqlMax = "select max(id) as massimo from inventarioproc_endo where idcomune=?";
    //	PreparedStatement psMax = cDestinazione.prepareStatement(sqlMax, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    //	int max = 1;
    //	for (Integer codiceInventario : codiciEndo) {
    //	    // cancello le configurazioni esistenti 
    //	    psDelete.setString(1, idcomuneDestinazione);
    //	    psDelete.setInt(2, codiceInventario);
    //	    psDelete.executeUpdate();
    //	    cDestinazione.commit();
    //	    psDelete.clearParameters();
    //	    // select dei record per codiceinventario_t
    //	    ps.setString(1, idcomuneOrigine);
    //	    ps.setString(2, idcomuneOrigine);
    //	    ps.setInt(3, codiceInventario);
    //	    rs = ps.executeQuery();
    //	    while (rs.next()) {
    //		Integer codiceinventario_d = rs.getInt(1);
    //		Integer flag_pubblica = rs.getInt(2);
    //		Integer flag_necessario = rs.getInt(3);
    //		String codiceComune = StringUtils.defaultIfBlank(rs.getString(4), null);
    //		psMax.setString(1, idcomuneDestinazione);
    //		rsMax = psMax.executeQuery();
    //		if (rsMax.next()) {
    //		    max = rsMax.getInt(1);
    //		    max += 1;
    //		}
    //		psMax.clearParameters();
    //		Utils.closeObjects(rsMax);
    //		psInsert.setString(1, idcomuneDestinazione);
    //		psInsert.setInt(2, max);
    //		psInsert.setInt(3, codiceInventario);
    //		psInsert.setInt(4, codiceinventario_d);
    //		psInsert.setInt(5, flag_pubblica);
    //		psInsert.setInt(6, flag_necessario);
    //		if (StringUtils.isNotBlank(codiceComune)) {
    //		    psInsert.setString(7, codiceComune);
    //		} else {
    //		    psInsert.setNull(7, Types.VARCHAR);
    //		}
    //		psInsert.executeUpdate();
    //		psInsert.clearParameters();
    //		cDestinazione.commit();
    //	    }
    //	    ps.clearParameters();
    //	    Utils.closeObjects(rs);
    //	}
    //	Utils.closeObjects(psDelete);
    //	Utils.closeObjects(psInsert);
    //	Utils.closeObjects(psMax);
    //	Utils.closeObjects(ps);
    //	Utils.closeObjects(rs);
    //	//	Utils.closeObjects(ps);
    //    }

    private String getTipomovFromNatura(String naturabase, String codiceAncitelAmministrazione) {

	TipimovHelper tm = ammTipiMov.get(codiceAncitelAmministrazione);
	if (tm != null) {
	    if ("scia".equalsIgnoreCase(naturabase)) {
		return tm.getScia();
	    }
	    if ("ordinario".equalsIgnoreCase(naturabase)) {
		return tm.getOrdinario();
	    }
	    if ("comunicazione".equalsIgnoreCase(naturabase)) {
		return tm.getComunicazione();
	    }
	}
	return null;
    }

    private void inserisciSuInvprocSoftware(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Integer codiceInventario, Integer codiceAmministrazione, String tipomovimento) throws SQLException {

	String sqlModuliOrgine = "select modulosoftware from inventarioprocedimentisoftware where idcomune=? and codiceinventario=?";
	PreparedStatement psModuliOrgine = null;
	ResultSet rsModuliOrgine = null;
	psModuliOrgine = cOrigine.prepareStatement(sqlModuliOrgine, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	psModuliOrgine.setString(1, idcomuneOrigine);
	psModuliOrgine.setInt(2, codiceInventario);
	rsModuliOrgine = psModuliOrgine.executeQuery();
	Set<String> moduliOrigine = new HashSet<String>();
	while (rsModuliOrgine.next()) {
	    moduliOrigine.add(rsModuliOrgine.getString(1));
	}
	Utils.closeObjects(rsModuliOrgine);
	Utils.closeObjects(psModuliOrgine);
	String sql = "select id, modulosoftware,tipomovimento from inventarioprocedimentisoftware where idcomune=? and codiceinventario=?";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cDestinazione.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneDestinazione);
	ps.setInt(2, codiceInventario);
	// ps.setString(3, softwareDestinazione);
	rs = ps.executeQuery();
	int max = 1;
	Map<String, Integer> moduliTrovatiDest = new HashMap<String, Integer>();
	Map<Integer, String> sMov = new HashMap<Integer, String>();
	while (rs.next()) {
	    Integer id = rs.getInt(1);
	    String software = rs.getString(2);
	    String tipoMov = StringUtils.defaultIfBlank(rs.getString(3), TIPOMOVNONSETTATO);
	    sMov.put(id, tipoMov);
	    moduliTrovatiDest.put(software, id);
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
	if (moduliTrovatiDest.containsKey(softwareDestinazione)) {
	    // AGGIORNO IL MOVIMENTO SOLO SE NON PRESENTE
	    Integer id = moduliTrovatiDest.get(softwareDestinazione);
	    String tipoMov = sMov.get(id);
	    if (tipoMov.equalsIgnoreCase(TIPOMOVNONSETTATO)) {
		String sqlupdate = "update inventarioprocedimentisoftware set codiceamministrazione=?, tipomovimento=? where idcomune=? and id=?";
		ps = cDestinazione.prepareStatement(sqlupdate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		Utils.setIntOrNUll(codiceAmministrazione, ps, 1);
		if (StringUtils.isNotBlank(tipomovimento)) {
		    ps.setString(2, tipomovimento);
		} else {
		    ps.setNull(2, Types.VARCHAR);
		}
		ps.setString(3, idcomuneDestinazione);
		ps.setInt(4, id);
		ps.executeUpdate();
		cDestinazione.commit();
	    } else {
		String sqlupdate = "update inventarioprocedimentisoftware set codiceamministrazione=? where idcomune=? and id=?";
		ps = cDestinazione.prepareStatement(sqlupdate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		Utils.setIntOrNUll(codiceAmministrazione, ps, 1);
		ps.setString(2, idcomuneDestinazione);
		ps.setInt(3, id);
		ps.executeUpdate();
		cDestinazione.commit();
	    }
	} else {
	    String sqlMax = "select max(id) as massimo from inventarioprocedimentisoftware where idcomune=?";
	    ps = cDestinazione.prepareStatement(sqlMax, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ps.setString(1, idcomuneDestinazione);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		max = rs.getInt(1);
		max += 1;
	    }
	    Utils.closeObjects(rs);
	    Utils.closeObjects(ps);
	    String sqlInsert = "INSERT INTO inventarioprocedimentisoftware ( idcomune, codiceinventario, modulosoftware,id,codiceamministrazione,tipomovimento) VALUES ( ? , ? , ? , ? , ? , ? )";
	    ps = cDestinazione.prepareStatement(sqlInsert, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ps.setString(1, idcomuneDestinazione);
	    ps.setInt(2, codiceInventario);
	    ps.setString(3, softwareDestinazione);
	    ps.setInt(4, max);
	    Utils.setIntOrNUll(codiceAmministrazione, ps, 5);
	    if (StringUtils.isNotBlank(tipomovimento)) {
		ps.setString(6, tipomovimento);
	    } else {
		ps.setNull(6, Types.VARCHAR);
	    }
	    ps.executeUpdate();
	    cDestinazione.commit();
	}
	// inserisco tutti gli altri
	for (String key : moduliOrigine) {
	    if (!key.equalsIgnoreCase(softwareDestinazione)) {
		// devo verificare se esiste o no
		if (!moduliTrovatiDest.containsKey(key)) {
		    String sqlMax = "select max(id) as massimo from inventarioprocedimentisoftware where idcomune=?";
		    ps = cDestinazione.prepareStatement(sqlMax, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    ps.setString(1, idcomuneDestinazione);
		    rs = ps.executeQuery();
		    if (rs.next()) {
			max = rs.getInt(1);
			max += 1;
		    }
		    Utils.closeObjects(rs);
		    Utils.closeObjects(ps);
		    String sqlInsert = "INSERT INTO inventarioprocedimentisoftware ( idcomune, codiceinventario, modulosoftware, id) VALUES ( ? , ? , ? , ? )";
		    ps = cDestinazione.prepareStatement(sqlInsert, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		    ps.setString(1, idcomuneDestinazione);
		    ps.setInt(2, codiceInventario);
		    ps.setString(3, key);
		    ps.setInt(4, max);
		    ps.executeUpdate();
		    cDestinazione.commit();
		}
	    }
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processTipiEndo(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciTipiEndo) throws SQLException {

	if (codiciTipiEndo == null || codiciTipiEndo.isEmpty()) {
	    return;
	}
	String tabella = "TIPIENDO";
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT CODICE FROM TIPIENDO WHERE IDCOMUNE = ? AND CODICE=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE TIPIENDO SET TIPO=?,ORDINE=?,SOFTWARE=?,NOTE=?,FLAG_PUBBLICA=?,CODICEFAMIGLIAENDO=? WHERE IDCOMUNE=? AND CODICE=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO TIPIENDO (IDCOMUNE,CODICE,TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA,CODICEFAMIGLIAENDO) VALUES (?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuilder query = new StringBuilder(
		"SELECT TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA,CODICEFAMIGLIAENDO FROM TIPIENDO WHERE IDCOMUNE = ? AND CODICE=?");
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	for (Integer codice : codiciTipiEndo) {
	    ps.setString(1, idcomuneOrigine);
	    ps.setInt(2, codice);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		String tipo = rs.getString(1);
		Integer ordine = rs.getInt(2);
		String software = rs.getString(3);
		String note = rs.getString(4);
		Integer flagPubblica = rs.getInt(5);
		Integer codiceFamiglia = null;
		if (rs.getObject(6) != null) {
		    codiceFamiglia = rs.getInt(6);
		}
		Utils.closeObjects(rs);
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codice);
		rsExists = psExists.executeQuery();
		// cerco nella destinazione se esiste il record
		if (rsExists.next()) {
		    Utils.closeObjects(rsExists);
		    // se esite lo modifico nella destinazione
		    //		"UPDATE TIPIFAMIGLIEENDO SET TIPO=?,ORDINE=?,SOFTWARE=?,NOTE=?,FLAG_PUBBLICA=? WHERE IDCOMUNE = ? AND CODICE=?");
		    psUpdate.setString(1, tipo);
		    psUpdate.setInt(2, ordine);
		    psUpdate.setString(3, software);
		    psUpdate.setString(4, note);
		    psUpdate.setInt(5, flagPubblica);
		    if (codiceFamiglia != null) {
			psUpdate.setInt(6, codiceFamiglia);
		    } else {
			psUpdate.setNull(6, Types.INTEGER);
		    }
		    psUpdate.setString(7, idcomuneDestinazione);
		    psUpdate.setInt(8, codice);
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    activityLog.debug("aggiorno {}-{}", tabella, codice);
		} else {
		    Utils.closeObjects(rsExists);
		    //		"INSERT INTO TIPIFAMIGLIEENDO (IDCOMUNE,CODICE,TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA) VALUES (?,?,?,?,?,?,?)");
		    psInsert.setString(1, idcomuneDestinazione);
		    psInsert.setInt(2, codice);
		    psInsert.setString(3, tipo);
		    psInsert.setInt(4, ordine);
		    psInsert.setString(5, software);
		    psInsert.setString(6, note);
		    psInsert.setInt(7, flagPubblica);
		    if (codiceFamiglia != null) {
			psInsert.setInt(8, codiceFamiglia);
		    } else {
			psInsert.setNull(8, Types.INTEGER);
		    }
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codice);
		}
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processTipifamiglieEndo(Connection cOrigine, Connection cDestinazione, String idcomuneOrigine, String idcomuneDestinazione,
	    Set<Integer> codiciFamiglieEndo) throws SQLException {

	if (codiciFamiglieEndo == null || codiciFamiglieEndo.isEmpty()) {
	    return;
	}
	String tabella = "TIPIFAMIGLIEENDO";
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT CODICE FROM TIPIFAMIGLIEENDO WHERE IDCOMUNE = ? AND CODICE=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE TIPIFAMIGLIEENDO SET TIPO=?,ORDINE=?,SOFTWARE=?,NOTE=?,FLAG_PUBBLICA=? WHERE IDCOMUNE = ? AND CODICE=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO TIPIFAMIGLIEENDO (IDCOMUNE,CODICE,TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA) VALUES (?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	PreparedStatement ps = null;
	ResultSet rs = null;
	StringBuilder query = new StringBuilder(
		"SELECT TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA FROM TIPIFAMIGLIEENDO WHERE IDCOMUNE = ? AND CODICE=?");
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	for (Integer codice : codiciFamiglieEndo) {
	    ps.setString(1, idcomuneOrigine);
	    ps.setInt(2, codice);
	    rs = ps.executeQuery();
	    if (rs.next()) {
		String tipo = rs.getString(1);
		Integer ordine = rs.getInt(2);
		String software = rs.getString(3);
		String note = rs.getString(4);
		Integer flagPubblica = rs.getInt(5);
		Utils.closeObjects(rs);
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codice);
		rsExists = psExists.executeQuery();
		// cerco nella destinazione se esiste il record
		if (rsExists.next()) {
		    Utils.closeObjects(rsExists);
		    // se esite lo modifico nella destinazione
		    //		"UPDATE TIPIFAMIGLIEENDO SET TIPO=?,ORDINE=?,SOFTWARE=?,NOTE=?,FLAG_PUBBLICA=? WHERE IDCOMUNE = ? AND CODICE=?");
		    psUpdate.setString(1, tipo);
		    psUpdate.setInt(2, ordine);
		    psUpdate.setString(3, software);
		    psUpdate.setString(4, note);
		    psUpdate.setInt(5, flagPubblica);
		    psUpdate.setString(6, idcomuneDestinazione);
		    psUpdate.setInt(7, codice);
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    activityLog.debug("aggiorno {}-{}", tabella, codice);
		} else {
		    Utils.closeObjects(rsExists);
		    //		"INSERT INTO TIPIFAMIGLIEENDO (IDCOMUNE,CODICE,TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA) VALUES (?,?,?,?,?,?,?)");
		    psInsert.setString(1, idcomuneDestinazione);
		    psInsert.setInt(2, codice);
		    psInsert.setString(3, tipo);
		    psInsert.setInt(4, ordine);
		    psInsert.setString(5, software);
		    psInsert.setString(6, note);
		    psInsert.setInt(7, flagPubblica);
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codice);
		}
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }

    private void processTipisoggetto(Connection cOrigine, Connection cDestinazione, String tabella, String idcomuneOrigine,
	    String idcomuneDestinazione) throws SQLException {

	StringBuilder query = new StringBuilder("SELECT CODICETIPOSOGGETTO,TIPOSOGGETTO,FLAGQUALITA,UTILIZZO,RICHIEDIANAGRAFECOLL," //
						+
						"FLG_SPECIFICADESCRIZIONE,FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA FROM TIPISOGGETTO ");
	query.append("WHERE IDCOMUNE = ? AND SOFTWARE =? AND CODICETIPOSOGGETTO>=? ORDER BY CODICETIPOSOGGETTO");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT CODICETIPOSOGGETTO FROM TIPISOGGETTO WHERE IDCOMUNE = ? AND CODICETIPOSOGGETTO=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE TIPISOGGETTO SET TIPOSOGGETTO=?,FLAGQUALITA=?,UTILIZZO=?,RICHIEDIANAGRAFECOLL=?,FLG_SPECIFICADESCRIZIONE=?,FLG_LEGALERAP=?,ORDINE=?,ATRIB_QUALIFICASOGGETTO=?,FLAG_MOSTRA_DETT_ISTANZA=? WHERE IDCOMUNE = ? AND CODICETIPOSOGGETTO=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO TIPISOGGETTO (CODICETIPOSOGGETTO,TIPOSOGGETTO,IDCOMUNE,FLAGQUALITA,SOFTWARE,UTILIZZO,RICHIEDIANAGRAFECOLL,FLG_SPECIFICADESCRIZIONE,"//
						      +
						      "FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	while (rs.next()) {
	    //	    SELECT CODICETIPOSOGGETTO,TIPOSOGGETTO,FLAGQUALITA,UTILIZZO,RICHIEDIANAGRAFECOLL," //
	    //		+ "FLG_SPECIFICADESCRIZIONE,FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA FROM TIPISOGGETTO ");
	    //	query.append("WHERE IDCOMUNE = ? AND SOFTWARE =? AND CODICETIPOSOGGETTO>=? ORDER BY SC_ID");
	    Integer codice = rs.getInt(1);
	    String tiposoggetto = rs.getString(2);
	    Integer flagQualita = rs.getInt(3);
	    String utilizzo = rs.getString(4);
	    Integer richiediAnagrafeColle = rs.getInt(5);
	    Integer flgSpecDesc = rs.getInt(6);
	    Integer flgLegaleR = rs.getInt(7);
	    Integer ordine = rs.getInt(8);
	    Integer atribQualifica = null;
	    if (rs.getObject(9) != null) {
		atribQualifica = rs.getInt(9);
	    }
	    Integer flgMostraDettIstanza = rs.getInt(10);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE TIPISOGGETTO SET TIPOSOGGETTO=?,FLAGQUALITA=?,UTILIZZO=?,RICHIEDIANAGRAFECOLL=?,FLG_SPECIFICADESCRIZIONE=?,FLG_LEGALERAP=?,ORDINE=?,
		// ATRIB_QUALIFICASOGGETTO=?,FLAG_MOSTRA_DETT_ISTANZA=? WHERE IDCOMUNE = ? AND CODICETIPOSOGGETTO=?"
		psUpdate.setString(1, tiposoggetto);
		psUpdate.setInt(2, flagQualita);
		psUpdate.setString(3, utilizzo);
		psUpdate.setInt(4, richiediAnagrafeColle);
		psUpdate.setInt(5, flgSpecDesc);
		psUpdate.setInt(6, flgLegaleR);
		psUpdate.setInt(7, ordine);
		if (atribQualifica == null) {
		    psUpdate.setNull(8, Types.INTEGER);
		} else {
		    psUpdate.setInt(8, atribQualifica);
		}
		psUpdate.setInt(9, flgMostraDettIstanza);
		psUpdate.setString(10, idcomuneDestinazione);
		psUpdate.setInt(11, codice);
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//		"INSERT INTO TIPISOGGETTO (CODICETIPOSOGGETTO,TIPOSOGGETTO,IDCOMUNE,FLAGQUALITA,SOFTWARE,UTILIZZO,RICHIEDIANAGRAFECOLL,FLG_SPECIFICADESCRIZIONE,"//
		//		+ "FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA)
		psInsert.setInt(1, codice);
		psInsert.setString(2, tiposoggetto);
		psInsert.setString(3, idcomuneDestinazione);
		psInsert.setInt(4, flagQualita);
		psInsert.setString(5, softwareDestinazione);
		psInsert.setString(6, utilizzo);
		psInsert.setInt(7, richiediAnagrafeColle);
		psInsert.setInt(8, flgSpecDesc);
		psInsert.setInt(9, flgLegaleR);
		psInsert.setInt(10, ordine);
		if (atribQualifica == null) {
		    psInsert.setNull(11, Types.INTEGER);
		} else {
		    psInsert.setInt(11, atribQualifica);
		}
		psInsert.setInt(12, flgMostraDettIstanza);
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists, rs, ps);
    }

    private void processAlberoproc(Connection cOrigine, Connection cDestinazione, String tabella, String idcomuneOrigine, String idcomuneDestinazione)
	    throws SQLException {

	String query = "select alberoproc.sc_id, alberoproc.sc_descrizione, alberoproc.sc_padre, alberoproc.sc_stato_controllo, alberoproc.sc_ordine, alberoproc.fkidazione, alberoproc.sc_codice, alberoproc.atrib_tipologiaintervento, alberoproc.sc_attivo, vw_alberoproc.sc_descrizione as descrizione_completa " + //
		       " from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where " + //
		       " alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? order by alberoproc.sc_id";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT SC_ID FROM ALBEROPROC WHERE IDCOMUNE = ? AND SC_ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE ALBEROPROC SET SC_CODICE=?,SC_DESCRIZIONE=?,SC_PADRE=?,SC_STATO_CONTROLLO=?,SC_ORDINE=?,FKIDAZIONE=?,ATRIB_TIPOLOGIAINTERVENTO=?,SC_ATTIVO=?,DESCRIZIONE_COMPLETA=? WHERE IDCOMUNE = ? AND SC_ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO ALBEROPROC (IDCOMUNE,SC_ID,SOFTWARE,SC_CODICE,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,ATRIB_TIPOLOGIAINTERVENTO,SC_ATTIVO,DESCRIZIONE_COMPLETA) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	while (rs.next()) {
	    //	    SELECT SC_ID,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,SC_CODICE FROM ALBEROPROC ");
	    //		query.append("WHERE IDCOMUNE = ? AND SOFTWARE=? AND SC_ID>=? ORDER BY SC_ID"
	    Integer codice = rs.getInt(1);
	    String scDescrizione = rs.getString(2);
	    Integer scPadre = rs.getInt(3);
	    String statoControllo = rs.getString(4);
	    Integer scOrdine = rs.getInt(5);
	    Integer fkidazione = null;
	    if (rs.getObject(6) != null) {
		fkidazione = rs.getInt(6);
	    }
	    String scCodice = rs.getString(7);
	    String atribTipologiaIntervento = rs.getString(8);
	    Integer scAttivo = rs.getInt(9);
	    String descrizioneCompleta = rs.getString(10);
	    // exists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codice);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		// "UPDATE ALBEROPROC SET SC_CODICE=?,SC_DESCRIZIONE=?,SC_PADRE=?,SC_STATO_CONTROLLO=?,SC_ORDINE=?,FKIDAZIONE=?,ATRIB_TIPOLOGIAINTERVENTO=? WHERE IDCOMUNE = ? AND SC_ID=?"
		psUpdate.setString(1, scCodice);
		psUpdate.setString(2, scDescrizione);
		psUpdate.setInt(3, scPadre);
		psUpdate.setString(4, statoControllo);
		psUpdate.setInt(5, scOrdine);
		if (fkidazione == null) {
		    psUpdate.setNull(6, Types.INTEGER);
		} else {
		    psUpdate.setInt(6, fkidazione);
		}
		psUpdate.setString(7, atribTipologiaIntervento);
		psUpdate.setInt(8, scAttivo);
		psUpdate.setString(9, descrizioneCompleta);
		psUpdate.setString(10, idcomuneDestinazione);
		psUpdate.setInt(11, codice);
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codice);
	    } else {
		Utils.closeObjects(rsExists);
		//"INSERT INTO ALBEROPROC (IDCOMUNE,SC_ID,SOFTWARE,SC_CODICE,SC_DESCRIZIONE,SC_PADRE,SC_STATO_CONTROLLO,SC_ORDINE,FKIDAZIONE,ATRIB_TIPOLOGIAINTERVENTO)
		psInsert.setString(1, idcomuneDestinazione);
		psInsert.setInt(2, codice);
		psInsert.setString(3, softwareDestinazione);
		psInsert.setString(4, scCodice);
		psInsert.setString(5, scDescrizione);
		psInsert.setInt(6, scPadre);
		psInsert.setString(7, statoControllo);
		psInsert.setInt(8, scOrdine);
		if (fkidazione == null) {
		    psInsert.setNull(9, Types.INTEGER);
		} else {
		    psInsert.setInt(9, fkidazione);
		}
		psInsert.setString(10, atribTipologiaIntervento);
		psInsert.setInt(11, scAttivo);
		psInsert.setString(12, descrizioneCompleta);
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists, rs, ps);
    }

    private void processAmministrazioni(Connection cOrigine, Connection cDestinazione, String tabella, String idcomuneOrigine,
	    String idcomuneDestinazione, Set<Integer> codiciAmministrazioni) throws SQLException {

	String query = "SELECT CODICEAMMINISTRAZIONE,AMMINISTRAZIONE,UFFICIO,REFERENTE,INDIRIZZO,CITTA,CAP,PROVINCIA,TELEFONO1,TELEFONO2,FAX,EMAIL,WEB,FLAG_SILENZIODINIEGO," +
		       "CODICEANCITEL,PROGRESSIVOEXPORT,PARTITAIVA,STC_IDENTE,STC_IDSPORTELLO,PEC,FLAG_DISABILITATO,STC_IDNODO FROM AMMINISTRAZIONI WHERE IDCOMUNE=? AND ";
	query += " CODICEAMMINISTRAZIONE>=?  ";
	if (codiciAmministrazioni != null && codiciAmministrazioni.size() > 0) {
	    query += " AND  CODICEAMMINISTRAZIONE in (";
	    String inQM = "";
	    for (int i = 0; i < codiciAmministrazioni.size(); i++) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
	}
	query += " ORDER BY CODICEAMMINISTRAZIONE";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciAmministrazioni != null && codiciAmministrazioni.size() > 0) {
	    for (Integer codiceAmm : codiciAmministrazioni) {
		ps.setInt(pos++, codiceAmm);
	    }
	}
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder(
		"SELECT CODICEAMMINISTRAZIONE FROM AMMINISTRAZIONI WHERE IDCOMUNE=? AND CODICEAMMINISTRAZIONE=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder("UPDATE AMMINISTRAZIONI SET  AMMINISTRAZIONE=?, UFFICIO=?, REFERENTE=?, INDIRIZZO=?, CITTA=?, " //
						      +
						      "CAP=?, PROVINCIA=?, TELEFONO1=?, TELEFONO2=?, FAX=?, EMAIL=?, WEB=?, FLAG_SILENZIODINIEGO=?,  " //
						      +
						      "CODICEANCITEL=?, PROGRESSIVOEXPORT=?, PARTITAIVA=?, STC_IDNODO=?, STC_IDENTE=?, STC_IDSPORTELLO=?, PEC=?, " //
						      + "FLAG_DISABILITATO=? WHERE IDCOMUNE = ? AND CODICEAMMINISTRAZIONE=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO AMMINISTRAZIONI (IDCOMUNE,CODICEAMMINISTRAZIONE,AMMINISTRAZIONE, UFFICIO, REFERENTE, INDIRIZZO, CITTA, CAP, PROVINCIA, TELEFONO1, TELEFONO2, " //
						      + "FAX, EMAIL, WEB, FLAG_SILENZIODINIEGO, " //
						      +
						      " CODICEANCITEL, PROGRESSIVOEXPORT, PARTITAIVA, STC_IDENTE, STC_IDSPORTELLO, PEC, FLAG_DISABILITATO,STC_IDNODO) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	//// exists 
	StringBuilder queryExistsAncitel = new StringBuilder("SELECT VALORE, CHIAVE FROM CONF_APPLICATIVE WHERE IDCOMUNE=? AND CHIAVE in (?,?,?)");
	PreparedStatement psExistAncitel = null;
	psExistAncitel = cDestinazione.prepareStatement(queryExistsAncitel.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExistsAncitel = null;
	while (rs.next()) {
	    //	    CODICEAMMINISTRAZIONE,AMMINISTRAZIONE,UFFICIO,REFERENTE,INDIRIZZO,CITTA,CAP,PROVINCIA,TELEFONO1,TELEFONO2,FAX,EMAIL,WEB,FLAG_SILENZIODINIEGO,"
	    //			+ "	CODICEANCITEL,PROGRESSIVOEXPORT,PARTITAIVA,STC_IDENTE,STC_IDSPORTELLO,PEC,FLAG_DISABILITATO,STC_IDNODO FROM AMMINISTRAZIONI ");
	    //	query.append("WHERE IDCOMUNE = ? AND CODICEAMMINISTRAZIONE>=? ORDER BY CODICEAMMINISTRAZIONE");
	    Integer codiceAmministrazione = rs.getInt(1);
	    String amministrazione = rs.getString(2);
	    String ufficio = rs.getString(3);
	    String referente = rs.getString(4);
	    String indirizzo = rs.getString(5);
	    String citta = rs.getString(6);
	    String cap = rs.getString(7);
	    String provincia = rs.getString(8);
	    String telefono1 = rs.getString(9);
	    String telefono2 = rs.getString(10);
	    String fax = rs.getString(11);
	    String email = rs.getString(12);
	    String web = rs.getString(13);
	    Integer flagSilenzio = rs.getInt(14);
	    String codiceAncitel = rs.getString(15);
	    String progressivoExp = rs.getString(16);
	    String piva = rs.getString(17);
	    String stcIdEnte = rs.getString(18);
	    String stcIdSportello = rs.getString(19);
	    String pec = rs.getString(20);
	    Integer flagDisabilitato = rs.getInt(21);
	    String stcIdNodo = rs.getString(22);
	    // esists
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, codiceAmministrazione);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    psExistAncitel.setString(1, idcomuneDestinazione);
	    psExistAncitel.setString(2, CONSOLE_DIZ_PREFIX + "MOV_SCIA#" + codiceAncitel);
	    psExistAncitel.setString(3, CONSOLE_DIZ_PREFIX + "MOV_ORDINARIO#" + codiceAncitel);
	    psExistAncitel.setString(4, CONSOLE_DIZ_PREFIX + "MOV_COMUNICAZIONE#" + codiceAncitel);
	    //	    public static final String CONF_APPLICATIVE_MOV_SCIA = "MOV_SCIA";
	    //	    public static final String CONF_APPLICATIVE_MOV_ORDINARIO = "MOV_ORDINARIO";
	    //	    public static final String CONF_APPLICATIVE_MOV_COMUNICAZIONE = "MOV_COMUNICAZIONE";
	    rsExistsAncitel = psExistAncitel.executeQuery();
	    String tipomovScia = null;
	    String tipomovOrdinario = null;
	    String tipomovComunicazione = null;
	    while (rsExistsAncitel.next()) {
		String valore = rsExistsAncitel.getString(1);
		String chiave = rsExistsAncitel.getString(2);
		if (chiave.equals(CONSOLE_DIZ_PREFIX + "MOV_SCIA#" + codiceAncitel)) {
		    tipomovScia = valore;
		} else if (chiave.equals(CONSOLE_DIZ_PREFIX + "MOV_ORDINARIO#" + codiceAncitel)) {
		    tipomovOrdinario = valore;
		} else if (chiave.equals(CONSOLE_DIZ_PREFIX + "MOV_COMUNICAZIONE#" + codiceAncitel)) {
		    tipomovComunicazione = valore;
		}
	    }
	    if (StringUtils.isNotBlank(tipomovScia) || StringUtils.isNotBlank(tipomovOrdinario) || StringUtils.isNotBlank(tipomovComunicazione)) {
		TipimovHelper valore = new TipimovHelper();
		valore.setScia(tipomovScia);
		valore.setComunicazione(tipomovComunicazione);
		valore.setOrdinario(tipomovOrdinario);
		ammTipiMov.put(codiceAncitel, valore);
		activityLog.debug("tipo movimento  {} mappato con  {}", codiceAncitel);
	    }
	    Utils.closeObjects(rsExistsAncitel);
	    // verifico che non sia stata associata con il CODICEANCITEL (IN QUESTO CASO) L'ENTE HA ASSOCIATO UNA AMMINISTRAZIONE DIVERSA DA QUESTA REGIONALE
	    // exists
	    psExistAncitel.setString(1, idcomuneDestinazione);
	    psExistAncitel.setString(2, CONSOLE_DIZ_PREFIX + "AMM#" + codiceAncitel);
	    psExistAncitel.setString(3, CONSOLE_DIZ_PREFIX + "AMM#" + codiceAncitel);
	    psExistAncitel.setString(4, CONSOLE_DIZ_PREFIX + "AMM#" + codiceAncitel);
	    rsExistsAncitel = psExistAncitel.executeQuery();
	    Integer ammLocale = null;
	    if (rsExistsAncitel.next()) {
		String valore = rsExistsAncitel.getString(1);
		if (StringUtils.defaultString(valore).trim().matches("^-?(\\d)+$")) {
		    ammLocale = Integer.parseInt(valore);
		}
		activityLog.debug("amministrazione remota {} mappata con locale {}", codiceAmministrazione, ammLocale);
		ammRemoteAmmLoc.put(codiceAmministrazione, ammLocale);
	    }
	    Utils.closeObjects(rsExistsAncitel);
	    if (StringUtils.isNotBlank(codiceAncitel)) {
		if (ammLocale != null) {
		    ammCodAncitel.put(ammLocale, codiceAncitel);
		} else {
		    ammCodAncitel.put(codiceAmministrazione, codiceAncitel);
		}
	    }
	    ///////////////////////
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		//		StringBuilder queryUpdate = new StringBuilder("UPDATE AMMINISTRAZIONI SET  AMMINISTRAZIONE=?, UFFICIO=?, REFERENTE=?, INDIRIZZO=?, CITTA=?, " //		
		//			+ "CAP=?, PROVINCIA=?, TELEFONO1=?, TELEFONO2=?, FAX=?, EMAIL=?, WEB=?, FLAG_SILENZIODINIEGO=?,  " //
		//			+ "CODICEANCITEL=?, PROGRESSIVOEXPORT=?, PARTITAIVA=?, STC_IDNODO=?, STC_IDENTE=?, STC_IDSPORTELLO=?, PEC=?, " //
		//			+ "FLAG_DISABILITATO=? WHERE IDCOMUNE = ? AND CODICEAMMINISTRAZIONE=?");
		psUpdate.setString(1, amministrazione);
		psUpdate.setString(2, ufficio);
		psUpdate.setString(3, referente);
		psUpdate.setString(4, indirizzo);
		psUpdate.setString(5, citta);
		psUpdate.setString(6, cap);
		psUpdate.setString(7, provincia);
		psUpdate.setString(8, telefono1);
		psUpdate.setString(9, telefono2);
		psUpdate.setString(10, fax);
		psUpdate.setString(11, email);
		psUpdate.setString(12, web);
		psUpdate.setInt(13, flagSilenzio);
		psUpdate.setString(14, codiceAncitel);
		psUpdate.setString(15, progressivoExp);
		psUpdate.setString(16, piva);
		psUpdate.setString(17, stcIdNodo);
		psUpdate.setString(18, stcIdEnte);
		psUpdate.setString(19, stcIdSportello);
		psUpdate.setString(20, pec);
		psUpdate.setInt(21, flagDisabilitato);
		psUpdate.setString(22, idcomuneDestinazione);
		psUpdate.setInt(23, codiceAmministrazione);
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}-{}", tabella, codiceAmministrazione);
	    } else {
		Utils.closeObjects(rsExists);
		if (ammLocale == null) {
		    //		StringBuilder queryInsert = new StringBuilder(
		    //			"INSERT INTO AMMINISTRAZIONI (IDCOMUNE,CODICEAMMINISTRAZIONE,AMMINISTRAZIONE, UFFICIO, REFERENTE, INDIRIZZO, CITTA, CAP, 
		    // PROVINCIA, TELEFONO1, TELEFONO2, " //
		    //				+ "FAX, EMAIL, WEB, FLAG_SILENZIODINIEGO, " //
		    //				+ " CODICEANCITEL, PROGRESSIVOEXPORT, PARTITAIVA, STC_IDENTE, STC_IDSPORTELLO, PEC, FLAG_DISABILITATO,STC_IDNODO) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?,?)");
		    //		
		    psInsert.setString(1, idcomuneDestinazione);
		    psInsert.setInt(2, codiceAmministrazione);
		    psInsert.setString(3, amministrazione);
		    psInsert.setString(4, ufficio);
		    psInsert.setString(5, referente);
		    psInsert.setString(6, indirizzo);
		    psInsert.setString(7, citta);
		    psInsert.setString(8, cap);
		    psInsert.setString(9, provincia);
		    psInsert.setString(10, telefono1);
		    psInsert.setString(11, telefono2);
		    psInsert.setString(12, fax);
		    psInsert.setString(13, email);
		    psInsert.setString(14, web);
		    psInsert.setInt(15, flagSilenzio);
		    psInsert.setString(16, codiceAncitel);
		    psInsert.setString(17, progressivoExp);
		    psInsert.setString(18, piva);
		    psInsert.setString(19, stcIdEnte);
		    psInsert.setString(20, stcIdSportello);
		    psInsert.setString(21, pec);
		    psInsert.setInt(22, flagDisabilitato);
		    psInsert.setString(23, stcIdNodo);
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codiceAmministrazione);
		}
		Utils.closeObjects(rsExistsAncitel);
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists, psExistAncitel, rs, ps);
    }

    private void processTipiCausaliOneri(Connection cOrigine, Connection cDestinazione, String tabella, String idcomuneOrigine,
	    String idcomuneDestinazione) throws SQLException {

	StringBuilder query = new StringBuilder("SELECT CO_ID,CO_DESCRIZIONE,CO_SERICHIEDEENDO,CO_DISABILITATO FROM TIPICAUSALIONERI ");
	query.append("WHERE IDCOMUNE = ? AND SOFTWARE=? and CO_ID>=? ORDER BY CO_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	rs = ps.executeQuery();
	//// exists 
	StringBuilder queryExists = new StringBuilder("SELECT CO_ID FROM TIPICAUSALIONERI WHERE IDCOMUNE = ? AND CO_ID=?");
	PreparedStatement psExists = null;
	psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsExists = null;
	// UPDATE
	StringBuilder queryUpdate = new StringBuilder(
		"UPDATE TIPICAUSALIONERI SET CO_DESCRIZIONE=?,CO_SERICHIEDEENDO=?, CO_DISABILITATO=? WHERE IDCOMUNE = ? AND CO_ID=?");
	PreparedStatement psUpdate = null;
	psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	// INSERT
	StringBuilder queryInsert = new StringBuilder(
		"INSERT INTO TIPICAUSALIONERI (IDCOMUNE,CO_ID,CO_DESCRIZIONE,CO_SERICHIEDEENDO,CO_DISABILITATO,SOFTWARE) VALUES (?,?,?,?,?,?)");
	PreparedStatement psInsert = null;
	psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	//
	while (rs.next()) {
	    Integer coId = rs.getInt(1);
	    String coDescrizione = rs.getString(2);
	    String coRichiedeEndo = rs.getString(3);
	    Integer coDisabilitato = rs.getInt(4);
	    if (coDisabilitato == null) {
		coDisabilitato = 0;
	    }
	    psExists.setString(1, idcomuneDestinazione);
	    psExists.setInt(2, coId);
	    rsExists = psExists.executeQuery();
	    // cerco nella destinazione se esiste il record
	    if (rsExists.next()) {
		Utils.closeObjects(rsExists);
		// se esite lo modifico nella destinazione
		psUpdate.setString(1, coDescrizione);
		psUpdate.setString(2, coRichiedeEndo);
		psUpdate.setInt(3, coDisabilitato);
		psUpdate.setString(4, idcomuneDestinazione);
		psUpdate.setInt(5, coId);
		psUpdate.executeUpdate();
		cDestinazione.commit();
		activityLog.debug("aggiorno {}", coId);
	    } else {
		Utils.closeObjects(rsExists);
		psInsert.setString(1, idcomuneDestinazione);
		psInsert.setInt(2, coId);
		psInsert.setString(3, coDescrizione);
		psInsert.setString(4, coRichiedeEndo);
		psInsert.setInt(5, coDisabilitato);
		psInsert.setString(6, softwareDestinazione);
		psInsert.executeUpdate();
		cDestinazione.commit();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}", coId);
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists, rs, ps);
    }
}
