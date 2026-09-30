package it.gruppoinit.service;

import it.gruppoinit.domain.AlberoInterventiBean;
import it.gruppoinit.domain.AmministrazioniBean;
import it.gruppoinit.domain.CampiDinamiciBean;
import it.gruppoinit.domain.CampiDinamiciProprietaBean;
import it.gruppoinit.domain.CampiDinamiciScript;
import it.gruppoinit.domain.InformazioniAlberoInterventiBean;
import it.gruppoinit.domain.InventarioprocedimentoBean;
import it.gruppoinit.domain.InventarioprocedimentoSoftwareBean;
import it.gruppoinit.domain.ListaSchedeDinamicheBean;
import it.gruppoinit.domain.SchedeDinamicheBean;
import it.gruppoinit.domain.SchedeDinamicheDettaglioBean;
import it.gruppoinit.domain.SchedeDinamicheDettaglioTestiBean;
import it.gruppoinit.domain.SchedeDinamicheScriptBean;
import it.gruppoinit.domain.SubEndoBean;
import it.gruppoinit.domain.TipiCausaliOneriBean;
import it.gruppoinit.domain.TipiEndoBean;
import it.gruppoinit.domain.TipiFamiglieEndoBean;
import it.gruppoinit.domain.TipiSoggettoBean;
import it.gruppoinit.service.helper.CodiceDescrizioneBean;
import it.gruppoinit.service.helper.TipimovHelper;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;
import it.gruppoinit.utils.Utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleOFFLineService {

    private static final String SOFTWARE_TT = "TT";
    private static final int CODICE_MASTER = 91000000;
    private SigeproSecurityWSClient sigeproSecurityWSClient;
    private String aliasOrigine;
    private String softwareOrigine;
    private Map<String, TipimovHelper> ammTipiMov = new HashMap<String, TipimovHelper>();
    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");
    private GetDbConnectionInfoResponse propsOrigine = null;
    private String idComuneOrigine = null;

    public ConsoleOFFLineService(String aliasOrigine, String softwareOrigine, SigeproSecurityWSClient sigeproSecurityWSClient) {

	this.aliasOrigine = aliasOrigine;
	this.softwareOrigine = softwareOrigine;
	this.sigeproSecurityWSClient = sigeproSecurityWSClient;
	propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	idComuneOrigine = propsOrigine.getIdComune();
    }

    private Connection getConnection() throws SQLException {

	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	cOrigine.setReadOnly(true);
	return cOrigine;
    }

    private List<CodiceDescrizioneBean> trovaAmministrazioniAlberoprocEndo(String aliasOrigine, String scCodiceOrigine, Connection cOrigine,
	    String idcomuneOrigine) throws SQLException {

	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	String query = "select alberoproc.sc_id, alberoproc.sc_descrizione, alberoproc.sc_padre, alberoproc.sc_stato_controllo, alberoproc.sc_ordine, alberoproc.fkidazione, alberoproc.sc_codice, alberoproc.atrib_tipologiaintervento, alberoproc.sc_attivo, vw_alberoproc.sc_descrizione as descrizione_completa "
		+ //
		" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where "
		+ //
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
		query = "select alberoproc.sc_id "
			+ //
			" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where "
			+ //
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
	Utils.closeObjects(psCodiceSubEndo, psCodiceEndo, rs, ps);
	if (codiciInventario.size() == 0) {
	    return cdbs;
	}
	String sql = "SELECT amministrazioni.amministrazione,amministrazioni.codiceancitel FROM inventarioprocedimenti  inner join amministrazioni on"
		+ " amministrazioni.idcomune=inventarioprocedimenti.idcomune and amministrazioni.codiceamministrazione=inventarioprocedimenti.amministrazione "
		+ " WHERE inventarioprocedimenti.idcomune = ? AND " // 
		+ " inventarioprocedimenti.codiceinventario in  (";
	String inQM = "";
	if (codiciInventario != null && codiciInventario.size() > 0) {
	    for (Integer cm : codiciInventario) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	}
	sql += inQM
		+ ") and inventarioprocedimenti.codiceinventario>=? group by amministrazioni.amministrazione,amministrazioni.codiceancitel order by amministrazioni.amministrazione";
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
	Utils.closeObjects(rs, ps);
	return cdbs;
    }

    public List<TipiCausaliOneriBean> getTipicausalioneri() throws SQLException {

	List<TipiCausaliOneriBean> result = new ArrayList<TipiCausaliOneriBean>();
	Connection cOrigine = getConnection();
	StringBuilder query = new StringBuilder("SELECT CO_ID,CO_DESCRIZIONE,CO_SERICHIEDEENDO,CO_DISABILITATO FROM TIPICAUSALIONERI ");
	query.append("WHERE IDCOMUNE = ? AND SOFTWARE in(?,?) and CO_ID>=? ORDER BY CO_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idComuneOrigine);
	ps.setString(2, "TT");
	ps.setString(3, softwareOrigine);
	ps.setInt(4, CODICE_MASTER);
	rs = ps.executeQuery();
	while (rs.next()) {
	    Integer coId = rs.getInt(1);
	    String coDescrizione = rs.getString(2);
	    String coRichiedeEndo = rs.getString(3);
	    Integer coDisabilitato = rs.getInt(4);
	    if (coDisabilitato == null) {
		coDisabilitato = 0;
	    }
	    TipiCausaliOneriBean b = new TipiCausaliOneriBean(coId, coDescrizione, coRichiedeEndo, coDisabilitato);
	    result.add(b);
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return result;
    }

    public List<TipiSoggettoBean> getTipiSoggetto() throws SQLException {

	List<TipiSoggettoBean> result = new ArrayList<TipiSoggettoBean>();
	Connection cOrigine = getConnection();
	StringBuilder query = new StringBuilder("SELECT CODICETIPOSOGGETTO,TIPOSOGGETTO,FLAGQUALITA,UTILIZZO,RICHIEDIANAGRAFECOLL," //
		+ "FLG_SPECIFICADESCRIZIONE,FLG_LEGALERAP,ORDINE,ATRIB_QUALIFICASOGGETTO,FLAG_MOSTRA_DETT_ISTANZA FROM TIPISOGGETTO ");
	query.append("WHERE IDCOMUNE = ? AND SOFTWARE =? AND CODICETIPOSOGGETTO>=? ORDER BY CODICETIPOSOGGETTO");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idComuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	rs = ps.executeQuery();
	while (rs.next()) {
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
	    result.add(new TipiSoggettoBean(codice, tiposoggetto, flagQualita, utilizzo, richiediAnagrafeColle, flgSpecDesc, flgLegaleR, ordine,
		    atribQualifica, flgMostraDettIstanza));
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return result;
    }

    public List<CodiceDescrizioneBean> getAmministrazioni(String scCodice) throws SQLException {

	List<CodiceDescrizioneBean> cdbs = new ArrayList<CodiceDescrizioneBean>();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
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
	Utils.closeObjects(rs, ps, cOrigine);
	return cdbs;
    }

    public List<InventarioprocedimentoBean> getEndoprocedimenti(List<Integer> codiciEndoProcedimenti) throws Exception {

	List<InventarioprocedimentoBean> cdbs = new ArrayList<InventarioprocedimentoBean>();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = new String(
		"SELECT naturaendobase.naturabase, procedimento, amministrazione, dataaggiornamento, codicetipo, disabilitato, ordine, codiceancitel, flag_pubblica,inventarioprocedimenti.software,inventarioprocedimenti.codiceinventario " //
			+ "FROM inventarioprocedimenti left OUTER JOIN naturaendobase ON naturaendobase.codicenatura = inventarioprocedimenti.codicenatura "
			+ "WHERE inventarioprocedimenti.IDCOMUNE = ? and inventarioprocedimenti.codiceinventario>=? ");
	if (codiciEndoProcedimenti != null && codiciEndoProcedimenti.size() > 0) {
	    query += " AND  inventarioprocedimenti.codiceinventario in (";
	    String inQM = "";
	    for (int i = 0; i < codiciEndoProcedimenti.size(); i++) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
	}
	query += " ORDER BY inventarioprocedimenti.codiceinventario";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciEndoProcedimenti != null && codiciEndoProcedimenti.size() > 0) {
	    for (Integer codiceAmm : codiciEndoProcedimenti) {
		ps.setInt(pos++, codiceAmm);
	    }
	}
	rs = ps.executeQuery();
	while (rs.next()) {
	    String naturabase = rs.getString(1);
	    String procedimento = rs.getString(2);
	    Integer amministrazione = null;
	    if (rs.getObject(3) != null) {
		amministrazione = rs.getInt(3);
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
	    Integer codiceInventario = rs.getInt(11);
	    InventarioprocedimentoBean ip = new InventarioprocedimentoBean(naturabase, procedimento, amministrazione, dataaggiornamento, codiceTipo,
		    disabilitato, ordine, codiceancitel, flagPubblica, software, codiceInventario);
	    popolaListe(ip, cOrigine, idcomuneOrigine, software);
	    cdbs.add(ip);
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return cdbs;
    }

    private void popolaListe(InventarioprocedimentoBean ip, Connection cOrigine, String idcomuneOrigine, String software) throws SQLException {

	String sqlModuliOrgine = "select modulosoftware from inventarioprocedimentisoftware where idcomune=? and codiceinventario=?";
	PreparedStatement psModuliOrgine = null;
	ResultSet rsModuliOrgine = null;
	psModuliOrgine = cOrigine.prepareStatement(sqlModuliOrgine, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	psModuliOrgine.setString(1, idcomuneOrigine);
	psModuliOrgine.setInt(2, ip.getCodice());
	rsModuliOrgine = psModuliOrgine.executeQuery();
	List<InventarioprocedimentoSoftwareBean> ipSoftwareBeans = null;
	Set<String> moduliOrigine = new HashSet<String>();
	while (rsModuliOrgine.next()) {
	    moduliOrigine.add(rsModuliOrgine.getString(1));
	}
	if (moduliOrigine.size() > 0) {
	    ipSoftwareBeans = new ArrayList<InventarioprocedimentoSoftwareBean>(moduliOrigine.size());
	    for (String m : moduliOrigine) {
		ipSoftwareBeans.add(new InventarioprocedimentoSoftwareBean(m));
	    }
	    ip.setIpSoftwareBeans(ipSoftwareBeans);
	}
	Utils.closeObjects(rsModuliOrgine, psModuliOrgine);
	///////
	String sql = "select codiceinventario_d, flag_pubblica,flag_necessario,codicecomune from inventarioproc_endo where idcomune=? and fk_cid_idcomune=? and codiceinventario_t=?";
	ResultSet rs = null;
	PreparedStatement ps = cOrigine.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	// select dei record per codiceinventario_t
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, idcomuneOrigine);
	ps.setInt(3, ip.getCodice());
	rs = ps.executeQuery();
	List<SubEndoBean> subs = new ArrayList<SubEndoBean>();
	while (rs.next()) {
	    Integer codiceinventario_d = rs.getInt(1);
	    Integer flag_pubblica = rs.getInt(2);
	    Integer flag_necessario = rs.getInt(3);
	    String codiceComune = StringUtils.defaultIfBlank(rs.getString(4), null);
	    subs.add(new SubEndoBean(codiceinventario_d, flag_pubblica, flag_necessario, codiceComune));
	}
	if (subs.size() > 0) {
	    ip.setSubEndos(subs);
	}
	Utils.closeObjects(rs, ps);
	///////
    }

    public List<TipiEndoBean> getTipiEndo(List<Integer> codiciTipiEndo) throws Exception {

	List<TipiEndoBean> cdbs = new ArrayList<TipiEndoBean>();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = "SELECT TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA,CODICEFAMIGLIAENDO,CODICE FROM TIPIENDO WHERE IDCOMUNE=? AND ";
	query += " CODICE>=?  ";
	if (codiciTipiEndo != null && codiciTipiEndo.size() > 0) {
	    query += " AND  CODICE in (";
	    String inQM = "";
	    for (int i = 0; i < codiciTipiEndo.size(); i++) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
	}
	query += " ORDER BY CODICE";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciTipiEndo != null && codiciTipiEndo.size() > 0) {
	    for (Integer codiceAmm : codiciTipiEndo) {
		ps.setInt(pos++, codiceAmm);
	    }
	}
	rs = ps.executeQuery();
	while (rs.next()) {
	    String tipo = rs.getString(1);
	    Integer ordine = rs.getInt(2);
	    String software = rs.getString(3);
	    String note = rs.getString(4);
	    Integer flagPubblica = rs.getInt(5);
	    Integer codiceFamiglia = null;
	    if (rs.getObject(6) != null) {
		codiceFamiglia = rs.getInt(6);
	    }
	    Integer codice = rs.getInt(7);
	    cdbs.add(new TipiEndoBean(tipo, ordine, software, note, flagPubblica, codiceFamiglia, codice));
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return cdbs;
    }

    public List<TipiFamiglieEndoBean> getTipiFamiglieEndo(List<Integer> codiciFamiglieEndo) throws SQLException {

	List<TipiFamiglieEndoBean> cdbs = new ArrayList<TipiFamiglieEndoBean>();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = "SELECT TIPO,ORDINE,SOFTWARE,NOTE,FLAG_PUBBLICA,CODICE FROM TIPIFAMIGLIEENDO WHERE IDCOMUNE=? AND ";
	query += " CODICE>=?  ";
	if (codiciFamiglieEndo != null && codiciFamiglieEndo.size() > 0) {
	    query += " AND  CODICE in (";
	    String inQM = "";
	    for (int i = 0; i < codiciFamiglieEndo.size(); i++) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
	}
	query += " ORDER BY CODICE";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	int pos = 2;
	ps.setInt(pos++, CODICE_MASTER);
	if (codiciFamiglieEndo != null && codiciFamiglieEndo.size() > 0) {
	    for (Integer codiceAmm : codiciFamiglieEndo) {
		ps.setInt(pos++, codiceAmm);
	    }
	}
	rs = ps.executeQuery();
	while (rs.next()) {
	    String tipo = rs.getString(1);
	    Integer ordine = rs.getInt(2);
	    String software = rs.getString(3);
	    String note = rs.getString(4);
	    Integer flagPubblica = rs.getInt(5);
	    Integer codice = rs.getInt(6);
	    cdbs.add(new TipiFamiglieEndoBean(codice, tipo, ordine, software, note, flagPubblica));
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return cdbs;
    }

    public List<AmministrazioniBean> getAmministrazioni(List<Integer> codiciAmministrazioni) throws SQLException {

	List<AmministrazioniBean> cdbs = new ArrayList<AmministrazioniBean>();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = "SELECT CODICEAMMINISTRAZIONE,AMMINISTRAZIONE,UFFICIO,REFERENTE,INDIRIZZO,CITTA,CAP,PROVINCIA,TELEFONO1,TELEFONO2,FAX,EMAIL,WEB,FLAG_SILENZIODINIEGO,"
		+ "CODICEANCITEL,PROGRESSIVOEXPORT,PARTITAIVA,STC_IDENTE,STC_IDSPORTELLO,PEC,FLAG_DISABILITATO,STC_IDNODO FROM AMMINISTRAZIONI WHERE IDCOMUNE=? AND ";
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
	while (rs.next()) {
	    AmministrazioniBean ab = new AmministrazioniBean();
	    //	    Integer codiceAmministrazione = rs.getInt(1);
	    ab.setCodiceAmministrazione(rs.getInt(1));
	    //	    String amministrazione = rs.getString(2);
	    ab.setAmministrazione(rs.getString(2));
	    //	    String ufficio = rs.getString(3);
	    ab.setUfficio(rs.getString(3));
	    //	    String referente = rs.getString(4);
	    ab.setReferente(rs.getString(4));
	    //	    String indirizzo = rs.getString(5);
	    ab.setIndirizzo(rs.getString(5));
	    //	    String citta = rs.getString(6);
	    ab.setCitta(rs.getString(6));
	    //	    String cap = rs.getString(7);
	    ab.setCap(rs.getString(7));
	    //	    String provincia = rs.getString(8);
	    ab.setProvincia(rs.getString(8));
	    //	    String telefono1 = rs.getString(9);
	    ab.setTelefono1(rs.getString(9));
	    //	    String telefono2 = rs.getString(10);
	    ab.setTelefono2(rs.getString(10));
	    //	    String fax = rs.getString(11);
	    ab.setFax(rs.getString(11));
	    //	    String email = rs.getString(12);
	    ab.setEmail(rs.getString(12));
	    //	    String web = rs.getString(13);
	    ab.setWeb(rs.getString(13));
	    //	    Integer flagSilenzio = rs.getInt(14);
	    ab.setFlagSilenzio(rs.getInt(14));
	    //	    String codiceAncitel = rs.getString(15);
	    ab.setCodiceAncitel(rs.getString(15));
	    //	    String progressivoExp = rs.getString(16);
	    ab.setProgressivoExp(rs.getString(16));
	    //	    String piva = rs.getString(17);
	    ab.setPiva(rs.getString(17));
	    //	    String stcIdEnte = rs.getString(18);
	    ab.setStcIdEnte(rs.getString(18));
	    //	    String stcIdSportello = rs.getString(19);
	    ab.setStcIdSportello(rs.getString(19));
	    //	    String pec = rs.getString(20);
	    ab.setPec(rs.getString(20));
	    //	    Integer flagDisabilitato = rs.getInt(21);
	    ab.setFlagDisabilitato(rs.getInt(21));
	    //	    String stcIdNodo = rs.getString(22);
	    ab.setStcIdNodo(rs.getString(22));
	    cdbs.add(ab);
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return cdbs;
    }

    public InformazioniAlberoInterventiBean getInformazioniInterventi(Boolean escludiDisabilitati, String scCodiceOrigine) throws Exception {

	if (scCodiceOrigine == null) {
	    scCodiceOrigine = StringUtils.defaultString(scCodiceOrigine);
	}
	InformazioniAlberoInterventiBean b = new InformazioniAlberoInterventiBean();
	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = "select alberoproc.sc_id, alberoproc.sc_descrizione, alberoproc.sc_padre, alberoproc.sc_stato_controllo, alberoproc.sc_ordine, alberoproc.fkidazione, alberoproc.sc_codice, alberoproc.atrib_tipologiaintervento, alberoproc.sc_attivo, vw_alberoproc.sc_descrizione as descrizione_completa "
		+ //
		" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where "
		+ //
		" alberoproc.idcomune =? and alberoproc.software =? and alberoproc.sc_id >=? and alberoproc.sc_codice like ? order by alberoproc.sc_id";
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setString(2, softwareOrigine);
	ps.setInt(3, CODICE_MASTER);
	ps.setString(4, scCodiceOrigine + "%");
	rs = ps.executeQuery();
	// select codiceinventario from alberoproc_endo where idcomune='E256' AND FKSCID=72760
	Set<Integer> codiciInventario = new HashSet<Integer>();
	Set<Integer> codiciInterventi = new HashSet<Integer>();
	//
	List<AlberoInterventiBean> interventi = new ArrayList<AlberoInterventiBean>();
	while (rs.next()) {
	    Integer codice = rs.getInt(1);
	    codiciInterventi.add(codice);
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
	    //	    
	    AlberoInterventiBean e = new AlberoInterventiBean(codice, softwareOrigine, scCodice, scDescrizione, scPadre, statoControllo, scOrdine,
		    fkidazione, atribTipologiaIntervento, scAttivo, descrizioneCompleta);
	    interventi.add(e);
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
		query = "select alberoproc.sc_id "
			+ //
			" from alberoproc inner join vw_alberoproc on alberoproc.idcomune=vw_alberoproc.idcomune and alberoproc.sc_id=vw_alberoproc.sc_id where "
			+ //
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
		    codiciInterventi.add(codice);
		    // 
		}
	    }
	}
	cercaEndoprocedimenti(cOrigine, codiciInterventi, codiciInventario, idcomuneOrigine);
	b.setAlberoInterventiBean(interventi);
	b.setCodiciEndoProcedimenti(codiciInventario);
	populateInfoEndo(b, cOrigine, escludiDisabilitati, idcomuneOrigine);
	Utils.closeObjects(rs, ps, cOrigine);
	return b;
    }

    private void cercaEndoprocedimenti(Connection cOrigine, Set<Integer> codiciInterventi, Set<Integer> codiciInventario, String idcomuneOrigine)
	    throws SQLException {

	if (codiciInterventi.size() == 0) {
	    return;
	}
	String queryCodiceEndo = "select codiceinventario from alberoproc_endo where idcomune=?  and FKSCID in (";
	String inQM = "";
	for (int i = 0; i < codiciInterventi.size(); i++) {
	    inQM += ",?";
	}
	inQM = inQM.replaceFirst(",", "");
	queryCodiceEndo += inQM + ")";
	PreparedStatement psCodiceEndo = null;
	psCodiceEndo = cOrigine.prepareStatement(queryCodiceEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ResultSet rsCodiceEndo = null;
	psCodiceEndo.setString(1, idcomuneOrigine);
	int pos = 2;
	for (Integer codice : codiciInterventi) {
	    psCodiceEndo.setInt(pos++, codice);
	}
	rsCodiceEndo = psCodiceEndo.executeQuery();
	while (rsCodiceEndo.next()) {
	    Integer codiceEndo = rsCodiceEndo.getInt(1);
	    codiciInventario.add(codiceEndo);
	}
	String queryCodiceSubEndo = "select codiceinventario_d from inventarioproc_endo where idcomune=? AND codiceinventario_t in(";
	inQM = "";
	for (int i = 0; i < codiciInterventi.size(); i++) {
	    inQM += ",?";
	}
	inQM = inQM.replaceFirst(",", "");
	queryCodiceSubEndo += inQM + ")";
	PreparedStatement psCodiceSubEndo = null;
	psCodiceSubEndo = cOrigine.prepareStatement(queryCodiceSubEndo.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	// cerco nella destinazione se esiste il record
	ResultSet rsCodiceSubEndo = null;
	psCodiceSubEndo.setString(1, idcomuneOrigine);
	pos = 2;
	for (Integer codice : codiciInterventi) {
	    psCodiceSubEndo.setInt(pos++, codice);
	}
	rsCodiceSubEndo = psCodiceSubEndo.executeQuery();
	// cerco nella destinazione se esiste il record
	while (rsCodiceSubEndo.next()) {
	    Integer CodiceSubEndo = rsCodiceSubEndo.getInt(1);
	    codiciInventario.add(CodiceSubEndo);
	}
	Utils.closeObjects(rsCodiceSubEndo);
	//
    }

    private void populateInfoEndo(InformazioniAlberoInterventiBean b, Connection cOrigine, Boolean escludiDisabilitati, String idcomuneOrigine)
	    throws Exception {

	if (b == null || b.getCodiciEndoProcedimenti() == null || b.getCodiciEndoProcedimenti().size() == 0) {
	    return;
	}
	Set<Integer> codiciFamiglieEndo = new HashSet<Integer>();
	Set<Integer> codiciTipiEndo = new HashSet<Integer>();
	Set<Integer> codiciSchedeEndo = new HashSet<Integer>();
	Set<Integer> codiciAmministrazioniEndo = new HashSet<Integer>();
	String sql = "SELECT tipifamiglieendo.codice AS codicefamiglia , tipiendo.codice AS codicetipo , inventarioprocedimenti.codiceinventario,inventarioprocedimenti.amministrazione " //
		+ " ,INVENTARIOPROCDYN2MODELLIT.FK_D2MT_ID FROM inventarioprocedimenti left JOIN tipiendo ON tipiendo.idcomune = inventarioprocedimenti.idcomune AND tipiendo.codice = inventarioprocedimenti.codicetipo " //
		+ " LEFT JOIN tipifamiglieendo ON tipifamiglieendo.idcomune = tipiendo.idcomune AND tipifamiglieendo.codice = tipiendo.codicefamigliaendo " //
		+ " LEFT OUTER JOIN inventarioprocedimentisoftware ON inventarioprocedimentisoftware.idcomune = inventarioprocedimenti.idcomune " //
		+ " AND inventarioprocedimentisoftware.codiceinventario = inventarioprocedimenti.codiceinventario " //
		+ " LEFT JOIN INVENTARIOPROCDYN2MODELLIT ON    INVENTARIOPROCDYN2MODELLIT.IDCOMUNE=inventarioprocedimenti.IDCOMUNE AND  INVENTARIOPROCDYN2MODELLIT.CODICEINVENTARIO=inventarioprocedimenti.CODICEINVENTARIO" //
		+ " WHERE inventarioprocedimenti.idcomune = ? AND ( inventarioprocedimenti.software = ? " //
		+ " OR ( inventarioprocedimentisoftware.idcomune = ? AND inventarioprocedimentisoftware.modulosoftware = ? ) ) ";
	sql += " AND  inventarioprocedimenti.codiceinventario in (";
	String inQM = "";
	for (int i = 0; i < b.getCodiciEndoProcedimenti().size(); i++) {
	    inQM += ",?";
	}
	inQM = inQM.replaceFirst(",", "");
	sql += inQM + ")";
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
	int pos = 5;
	for (Integer codiceEndo : b.getCodiciEndoProcedimenti()) {
	    ps.setInt(pos++, codiceEndo);
	}
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
	Utils.closeObjects(rs, ps);
	b.setCodiciAmministrazioniEndo(codiciAmministrazioniEndo);
	b.setCodiciFamiglieEndo(codiciFamiglieEndo);
	b.setCodiciTipiEndo(codiciTipiEndo);
	b.setCodiciSchedeEndo(codiciSchedeEndo);
    }

    public ListaSchedeDinamicheBean getListaSchedeDinamiche(List<Integer> codiciModelli) throws Exception {

	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	ListaSchedeDinamicheBean result = new ListaSchedeDinamicheBean();
	String query = "SELECT ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,"
		+ "FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA FROM DYN2_MODELLIT" //
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
	result.setSchedes(new ArrayList<SchedeDinamicheBean>());
	while (rs.next()) {
	    //	    //	 "SELECT ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA FROM DYN2_MODELLIT WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
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
	    SchedeDinamicheBean sdb = new SchedeDinamicheBean(codice, software, descrizione, d2bcid, scriptCodce, modellomultiplo, flgStoricizza,
		    flagReadOnlyWeb, modelloFrontoffice, codiceScheda);
	    Set<Integer> codiciTesti = processDyn2ModelliD(cOrigine, idcomuneOrigine, sdb);
	    processDyn2ModelliDTesti(cOrigine, idcomuneOrigine, codiciTesti, sdb);
	    processDyn2ModelliTScript(cOrigine, idcomuneOrigine, sdb);
	    result.getSchedes().add(sdb);
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return result;
    }

    private void processDyn2ModelliTScript(Connection cOrigine, String idcomuneOrigine, SchedeDinamicheBean sdb) throws SQLException {

	StringBuilder query = new StringBuilder("SELECT EVENTO,SCRIPT  FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID=?");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, sdb.getCodice());
	rs = ps.executeQuery();
	List<SchedeDinamicheScriptBean> s = new ArrayList<SchedeDinamicheScriptBean>();
	while (rs.next()) {
	    //	 "SELECT FK_D2MT_ID,EVENTO,SCRIPT  FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID>=? ORDER BY FK_D2MT_ID"
	    String evento = rs.getString(1);
	    String scriptBase64 = Utils.readBlobAsString(rs.getBlob(2));
	    s.add(new SchedeDinamicheScriptBean(evento, scriptBase64));
	    // exists
	}
	if (s.size() > 0) {
	    sdb.setScripts(s);
	}
	Utils.closeObjects(rs, ps);
    }

    private Set<Integer> processDyn2ModelliD(Connection cOrigine, String idcomuneOrigine, SchedeDinamicheBean sdb) throws SQLException {

	Set<Integer> codiciTesti = new HashSet<Integer>();
	StringBuilder query = new StringBuilder(
		"SELECT ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA " // 
			+ "FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? and FK_D2MT_ID=? ORDER BY ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, CODICE_MASTER);
	ps.setInt(3, sdb.getCodice());
	rs = ps.executeQuery();
	List<SchedeDinamicheDettaglioBean> list = new ArrayList<SchedeDinamicheDettaglioBean>();
	while (rs.next()) {
	    //	 "SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    Integer fkd2cid = Utils.getIntegerOrNull(2, rs);
	    Integer fkd2mdtid = Utils.getIntegerOrNull(3, rs);
	    if (fkd2mdtid != null) {
		codiciTesti.add(fkd2mdtid);
	    }
	    Integer posverticale = rs.getInt(4);
	    Integer posorizzontale = rs.getInt(5);
	    Integer flagMultiplo = rs.getInt(6);
	    Integer fkRegolaAttivo = Utils.getIntegerOrNull(7, rs);
	    Integer flagObbligatorio = rs.getInt(8);
	    Integer flagSpezza = rs.getInt(9);
	    // exists
	    list.add(new SchedeDinamicheDettaglioBean(codice, fkd2cid, fkd2mdtid, posverticale, posorizzontale, flagMultiplo, fkRegolaAttivo,
		    flagObbligatorio, flagSpezza));
	}
	if (list.size() > 0) {
	    sdb.setDettaglios(list);
	}
	Utils.closeObjects(rs, ps);
	return codiciTesti;
    }

    private void processDyn2ModelliDTesti(Connection cOrigine, String idcomuneOrigine, Set<Integer> codiciD2mdTesti, SchedeDinamicheBean sdb)
	    throws SQLException {

	String query = "SELECT ID,FK_D2BTT_ID,TESTO FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID>=?  ";
	if (codiciD2mdTesti != null && codiciD2mdTesti.size() > 0) {
	    query += "  AND ID in (";
	    String inQM = "";
	    for (Integer cm : codiciD2mdTesti) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
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
	List<SchedeDinamicheDettaglioTestiBean> detts = new ArrayList<SchedeDinamicheDettaglioTestiBean>();
	while (rs.next()) {
	    //	 "SELECT ID,FK_D2BTT_ID,TESTO FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    String d2btt = rs.getString(2);
	    String testo = rs.getString(3);
	    detts.add(new SchedeDinamicheDettaglioTestiBean(codice, d2btt, testo));
	    // exists
	}
	if (detts.size() > 0) {
	    sdb.setTestis(detts);
	}
	Utils.closeObjects(rs, ps);
    }

    public List<CampiDinamiciBean> getListaCampiDinamici(List<Integer> codiciCampi) throws Exception {

	GetDbConnectionInfoResponse propsOrigine = sigeproSecurityWSClient.getConnectionProperties(aliasOrigine);
	Connection cOrigine = sigeproSecurityWSClient.getConnection(aliasOrigine);
	String idcomuneOrigine = propsOrigine.getIdComune();
	String query = "SELECT ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID FROM DYN2_CAMPI WHERE "
		+ "IDCOMUNE = ? AND ID>=?  ";
	if (codiciCampi != null && codiciCampi.size() > 0) {
	    query += "  AND ID in (";
	    String inQM = "";
	    for (Integer cm : codiciCampi) {
		inQM += ",?";
	    }
	    inQM = inQM.replaceFirst(",", "");
	    query += inQM + ")";
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
	List<CampiDinamiciBean> result = new ArrayList<CampiDinamiciBean>();
	while (rs.next()) {
	    //	 "SELECT ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID FROM DYN2_CAMPI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = rs.getInt(1);
	    String software = rs.getString(2);
	    String nomecampo = rs.getString(3);
	    String etichetta = rs.getString(4);
	    String descrizione = rs.getString(5);
	    String tipoDato = rs.getString(6);
	    Integer obbligatorio = rs.getInt(7);
	    String scriptCodeBase64 = rs.getString(8);
	    if (StringUtils.isNotBlank(scriptCodeBase64)) {
		scriptCodeBase64 = Base64.encodeBase64String(scriptCodeBase64.getBytes("UTF-8"));
	    }
	    String scriptUpdateCodeBase64 = rs.getString(9);
	    if (StringUtils.isNotBlank(scriptUpdateCodeBase64)) {
		scriptUpdateCodeBase64 = Base64.encodeBase64String(scriptUpdateCodeBase64.getBytes("UTF-8"));
	    }
	    String fkd2bcId = rs.getString(10);
	    CampiDinamiciBean cd = new CampiDinamiciBean(codice, software, nomecampo, etichetta, descrizione, tipoDato, obbligatorio,
		    scriptCodeBase64, scriptUpdateCodeBase64, fkd2bcId);
	    processDyn2CampiScript(cOrigine, idcomuneOrigine, cd);
	    processDyn2CampiSProprieta(cOrigine, idcomuneOrigine, cd);
	    result.add(cd);
	}
	Utils.closeObjects(rs, ps, cOrigine);
	return result;
    }

    private void processDyn2CampiScript(Connection cOrigine, String idcomuneOrigine, CampiDinamiciBean cd) throws SQLException {

	StringBuilder query = new StringBuilder("SELECT EVENTO,SCRIPT FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, cd.getCodice());
	rs = ps.executeQuery();
	List<CampiDinamiciScript> scripts = new ArrayList<CampiDinamiciScript>();
	while (rs.next()) {
	    //	 "SELECT FK_D2C_ID,EVENTO,SCRIPT FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID>=? ORDER BY FK_D2C_ID"
	    String evento = rs.getString(1);
	    String scriptBase64 = Utils.readBlobAsString(rs.getBlob(2));
	    scripts.add(new CampiDinamiciScript(evento, scriptBase64));
	}
	if (scripts.size() > 0) {
	    cd.setScripts(scripts);
	}
	Utils.closeObjects(rs, ps);
    }

    private void processDyn2CampiSProprieta(Connection cOrigine, String idcomuneOrigine, CampiDinamiciBean cd) throws SQLException {

	StringBuilder query = new StringBuilder(
		"SELECT PROPRIETA,VALORE FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	ps.setString(1, idcomuneOrigine);
	ps.setInt(2, cd.getCodice());
	List<CampiDinamiciProprietaBean> props = new ArrayList<CampiDinamiciProprietaBean>();
	rs = ps.executeQuery();
	while (rs.next()) {
	    //	 "SELECT FK_D2C_ID,PROPRIETA,VALORE FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID"
	    String proprieta = rs.getString(1);
	    String valore = rs.getString(2);
	    props.add(new CampiDinamiciProprietaBean(proprieta, valore));
	    // exists
	}
	if (props.size() > 0) {
	    cd.setProps(props);
	}
	Utils.closeObjects(rs, ps);
    }
}
