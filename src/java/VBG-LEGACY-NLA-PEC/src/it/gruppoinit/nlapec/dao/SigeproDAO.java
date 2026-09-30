package it.gruppoinit.nlapec.dao;

import it.gruppoinit.nlapec.util.AddressUtil;
import it.gruppoinit.nlapec.util.InfoIstanzaBean;
import it.gruppoinit.nlapec.util.PECMessage;
import it.gruppoinit.nlapec.util.SigeproPECInbox;
import it.init.sigepro.rte.types.PersonaFisicaType;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SigeproDAO {

    private static Logger log = LoggerFactory.getLogger(SigeproDAO.class);
    private static final String PEC_INBOX_SELECT_ALL = "select * from pec_inbox where idcomune=? and software=?";
    private static final String PEC_INBOX_INSERT = "INSERT INTO PEC_INBOX " +
						   //
						   "(IDCOMUNE,ID,PEC_SUBJECT,PEC_FROM,PEC_TO,PEC_DATE,SOFTWARE,FLAG_PROCESSATA,CODICEISTANZA,CODICEMOVIMENTO,CODICEOPERATORE,IDPROTOCOLLO,NUMEROPROTOCOLLO,DATAPROTOCOLLO,CODICEOGGETTOPROTOCOLLO,FLAG_CANCELLATA,FLAG_LETTA,IN_LOGINNAME,FK_RESP_EVIDENZA,PEC_TOCC,SOFTWARE_PROT,CODICECOMUNE_PROT,ACCOUNT_ID) " +
						   //
						   "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String CHECK_VERTICALIZZAZIONE_ATTIVA = "SELECT verticalizzazioni.software FROM verticalizzazioni INNER JOIN softwareattivi ON verticalizzazioni.idcomune=softwareattivi.idcomune AND verticalizzazioni.software=softwareattivi.fk_software WHERE verticalizzazioni.attivo=? AND verticalizzazioni.modulo=? AND verticalizzazioni.software!=? AND verticalizzazioni.idcomune=?";
    private static final String PARAMETRI_TIPOLOGIE_PEC = "SELECT * FROM verticalizzazioniparametri WHERE idcomune=? AND software=? AND modulo=?";
    private static final String CHECK_PEC_GIA_PROCESSATA = "SELECT * from pec_inbox where id=? and idcomune=? AND software=? AND flag_processata=?";
    private static final String IS_PRESENT = "SELECT * FROM pec_inbox WHERE idcomune=? and id=?";
    private static final String UPDATE_FLAG_PROCESSATA = "UPDATE pec_inbox SET flag_processata=? WHERE idcomune=? and id=?";
    private static final String UPDATE_CODICE_PRATICA = "UPDATE pec_inbox SET codiceistanza=? WHERE idcomune=? and id=?";
    private static final String NLA_RICEZIONE_PEC = "NLA-RICEZIONE-PEC";
    private static final String GET_PF = "SELECT * FROM anagrafe WHERE idcomune=? and codiceanagrafe=?";
    private static final String TIPOLOGIE_PEC = "('PEC_RICEVUTE_CONTROLLO','PEC_COMUNICA_CAMERACOM','PEC_AREARISERVATA','PEC_CITTADINO','PEC_ENTITERZI','PEC_NON_FORMATTATA','CHECK_REPLY')";
    private static final String IS_COMUNE_ASSOCIATO = "SELECT count(*) AS numerocomuni FROM comuniassociati where idcomune=?";
    private static final String GET_CODICE_COMUNE_ASSOCIATO = "SELECT * FROM comuniassociatisoftware WHERE idcomune=? and software=? and codice_accreditamento=?";
    private static final String GET_INFO_PRATICA = "SELECT istanze.numeroistanza as numeroistanza, istanze.lavori as oggetto, istanze.data as datapratica, movimentimail.codicemovimento as codicemovimento, movimenti.codiceistanza as codiceistanza, movimenti.tipomovimento as tipomovimento, movimenti.codiceinventario as codinventario FROM movimentimail INNER JOIN movimenti ON movimentimail.idcomune=movimenti.idcomune AND movimentimail.codicemovimento=movimenti.codicemovimento INNER JOIN istanze ON movimenti.idcomune=istanze.idcomune AND movimenti.codiceistanza=istanze.codiceistanza WHERE movimentimail.idcomune=? AND movimentimail.message_id=?";

    public List<SigeproPECInbox> leggiPECInbox(Properties connectionProps, String software) {

	log.debug("leggiPECInbox(): {} [{},{}]", new Object[] { PEC_INBOX_SELECT_ALL, connectionProps.getProperty("db_idcomune"), software });
	List<SigeproPECInbox> list = new ArrayList<SigeproPECInbox>();
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(PEC_INBOX_SELECT_ALL);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, software);
	    rs = pstmt.executeQuery();
	    SigeproPECInbox inbox = null;
	    while (rs.next()) {
		inbox = new SigeproPECInbox();
		inbox.setMessageId(rs.getString("ID"));
		inbox.setFrom(rs.getString("PEC_FROM"));
		inbox.setTo(rs.getString("PEC_TO"));
		inbox.setSubject(rs.getString("PEC_SUBJECT"));
		inbox.setDate(rs.getDate("PEC_DATE"));
		list.add(inbox);
	    }
	    return list;
	} catch (SQLException e) {
	    log.error("leggiPECInbox(): ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public int insertPECInbox(Properties connectionProps, String software, PECMessage pecMessage, String loginNameMail, Integer idAccount) {

	log.debug("insertPECInbox(): {} [{},{},{},{},{},{},{}]",
		new Object[] { PEC_INBOX_INSERT, connectionProps.getProperty("db_idcomune"), pecMessage.getId(),
			AddressUtil.getAsString(pecMessage.getFrom()), AddressUtil.getAsString(pecMessage.getTo()), pecMessage.getSubject(),
			pecMessage.getDate(), software });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(PEC_INBOX_INSERT);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, pecMessage.getId());
	    pstmt.setString(3, pecMessage.getSubject());
	    pstmt.setString(4, AddressUtil.getAsString(pecMessage.getFrom()));
	    pstmt.setString(5, AddressUtil.getAsString(pecMessage.getTo()));
	    // pstmt.setDate(6, new java.sql.Date(pecMessage.getDate().getTime()));
	    pstmt.setTimestamp(6, new java.sql.Timestamp(pecMessage.getDate().getTime()));
	    pstmt.setString(7, software);
	    //
	    pstmt.setInt(8, 1);
	    pstmt.setNull(9, java.sql.Types.INTEGER);
	    pstmt.setNull(10, java.sql.Types.INTEGER);
	    pstmt.setNull(11, java.sql.Types.INTEGER);
	    pstmt.setNull(12, java.sql.Types.VARCHAR);
	    pstmt.setNull(13, java.sql.Types.VARCHAR);
	    pstmt.setNull(14, java.sql.Types.DATE);
	    pstmt.setNull(15, java.sql.Types.INTEGER);
	    pstmt.setInt(16, 0);
	    pstmt.setInt(17, 1);
	    pstmt.setString(18, loginNameMail);
	    pstmt.setNull(19, java.sql.Types.INTEGER);
	    pstmt.setString(20, AddressUtil.getAsString(pecMessage.getCc()));
	    pstmt.setNull(21, java.sql.Types.VARCHAR);
	    pstmt.setNull(22, java.sql.Types.VARCHAR);
	    pstmt.setInt(23, idAccount);
	    int result = pstmt.executeUpdate();
	    return result;
	} catch (SQLException e) {
	    log.error("insertPECInbox() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(null, pstmt, conn);
	}
    }

    public boolean isProcessed(Properties connectionProps, String software, String messageId) {

	log.debug("isProcessed(): {} [{},{},{}]",
		new Object[] { CHECK_PEC_GIA_PROCESSATA, messageId, connectionProps.getProperty("db_idcomune"), software });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	boolean ret = false;
	try {
	    pstmt = conn.prepareStatement(CHECK_PEC_GIA_PROCESSATA);
	    String idMsg = messageId;
	    if (idMsg.startsWith("<")) {
		idMsg = idMsg.substring(1);
	    }
	    if (idMsg.endsWith(">")) {
		idMsg = idMsg.substring(0, idMsg.length() - 1);
	    }
	    pstmt.setString(1, idMsg);
	    pstmt.setString(2, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(3, software);
	    pstmt.setInt(4, 1);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		ret = true;
	    }
	    return ret;
	} catch (SQLException e) {
	    log.error("checkVerticalizzazioneAttiva() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public ArrayList<String> checkVerticalizzazioneAttiva(Properties connectionProps) {

	log.debug("checkVerticalizzazioneAttiva(): {} [{},{},{},{}]",
		new Object[] { CHECK_VERTICALIZZAZIONE_ATTIVA, "1", "NLA_RICEZIONE_PEC", "TT", connectionProps.getProperty("db_idcomune") });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ArrayList<String> listaSoftwareAttivi = new ArrayList<String>();
	try {
	    pstmt = conn.prepareStatement(CHECK_VERTICALIZZAZIONE_ATTIVA);
	    pstmt.setInt(1, 1);
	    pstmt.setString(2, "NLA-RICEZIONE-PEC");
	    pstmt.setString(3, "TT");
	    pstmt.setString(4, connectionProps.getProperty("db_idcomune"));
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String softwareAttivo = rs.getString("software");
		listaSoftwareAttivi.add(softwareAttivo);
	    }
	    return listaSoftwareAttivi;
	} catch (SQLException e) {
	    log.error("checkVerticalizzazioneAttiva() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public Map<String, String> getParametriTipologiePEC(Properties connectionProps, String software) {

	log.debug("getParametriTipologiePEC(): {} [{},{},{}]",
		new Object[] { PARAMETRI_TIPOLOGIE_PEC, connectionProps.getProperty("db_idcomune"), software, NLA_RICEZIONE_PEC });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Map<String, String> parametriTipologieProcessamentoPEC = new HashMap<String, String>();
	try {
	    pstmt = conn.prepareStatement(PARAMETRI_TIPOLOGIE_PEC + " and parametro in " + TIPOLOGIE_PEC);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, software);
	    pstmt.setString(3, "NLA-RICEZIONE-PEC");
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String parametro = rs.getString("parametro");
		String valore = rs.getString("valore");
		parametriTipologieProcessamentoPEC.put(parametro, valore);
	    }
	    return parametriTipologieProcessamentoPEC;
	} catch (SQLException e) {
	    log.error("getParametriTipologiePEC() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public Map<String, String> getAltriParametriVerticalizzazione(Properties connectionProps, String software) {

	log.debug("getParametriTipologiePEC(): {} [{},{},{}]",
		new Object[] { PARAMETRI_TIPOLOGIE_PEC, connectionProps.getProperty("db_idcomune"), software, NLA_RICEZIONE_PEC });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Map<String, String> parametriTipologieProcessamentoPEC = new HashMap<String, String>();
	try {
	    pstmt = conn.prepareStatement(PARAMETRI_TIPOLOGIE_PEC + " and parametro not in " + TIPOLOGIE_PEC);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, software);
	    pstmt.setString(3, "NLA-RICEZIONE-PEC");
	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String parametro = rs.getString("parametro");
		String valore = rs.getString("valore");
		parametriTipologieProcessamentoPEC.put(parametro, valore);
	    }
	    return parametriTipologieProcessamentoPEC;
	} catch (SQLException e) {
	    log.error("getParametriTipologiePEC() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    private Connection getDBConnection(Properties connectionProps) {

	String driverClassName = connectionProps.getProperty("db_driver");
	String dbConnString = connectionProps.getProperty("db_cnnstring");
	String userName = connectionProps.getProperty("db_username");
	String password = connectionProps.getProperty("db_password");
	try {
	    Class.forName(driverClassName).newInstance();
	    Connection result = DriverManager.getConnection(dbConnString, userName, password);
	    return result;
	} catch (Exception e) {
	    log.error("getDBConnection() : ", e);
	    throw new RuntimeException(e);
	}
    }

    private void closeDBConnection(ResultSet rs, PreparedStatement pstmt, Connection conn) {

	try {
	    if (null != rs) {
		rs.close();
	    }
	} catch (Exception e) {
	    log.error("chiusura rs ", e);
	}
	try {
	    if (null != pstmt) {
		pstmt.close();
	    }
	} catch (Exception e) {
	    log.error("chiusura pstmt ", e);
	}
	if (null != conn) {
	    try {
		conn.close();
	    } catch (SQLException e) {
		log.error("chiusura conn", e);
	    }
	}
    }

    private boolean isPresent(Properties connectionProps, String software, String messageId, PECMessage pecMessage) {

	log.debug("isPresent(): {} [{}{}]", new Object[] { IS_PRESENT, connectionProps.getProperty("db_idcomune"), messageId });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(IS_PRESENT);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    String idMsg = messageId;
	    if (idMsg.startsWith("<")) {
		idMsg = idMsg.substring(1);
	    }
	    if (idMsg.endsWith(">")) {
		idMsg = idMsg.substring(0, idMsg.length() - 1);
	    }
	    pstmt.setString(2, idMsg);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		return true;
	    }
	    return false;
	} catch (SQLException e) {
	    log.error("isPresent() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    private int updateFlag_processata(Properties connectionProps, String software, String messageId, PECMessage pecMessage) {

	log.debug("updateFlag_processata(): {} [{},{}]",
		new Object[] { UPDATE_FLAG_PROCESSATA, connectionProps.getProperty("db_idcomune"), messageId });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(UPDATE_FLAG_PROCESSATA);
	    pstmt.setInt(1, 1);
	    pstmt.setString(2, connectionProps.getProperty("db_idcomune"));
	    String idMsg = messageId;
	    if (idMsg.startsWith("<")) {
		idMsg = idMsg.substring(1);
	    }
	    if (idMsg.endsWith(">")) {
		idMsg = idMsg.substring(0, idMsg.length() - 1);
	    }
	    pstmt.setString(3, idMsg);
	    int result = pstmt.executeUpdate();
	    return result;
	} catch (SQLException e) {
	    log.error("insertPECInbox() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(null, pstmt, conn);
	}
    }

    public int setProcessed(Properties connectionProps, String software, String messageId, PECMessage pecMessage, String loginNameMail,
	    BigInteger idAccount) {

	int result = 0;
	if (isPresent(connectionProps, software, messageId, pecMessage)) {
	    result = updateFlag_processata(connectionProps, software, messageId, pecMessage);
	} else {
	    result = insertPecInbox(connectionProps, software, messageId, pecMessage, loginNameMail, idAccount);
	}
	return result;
    }

    private int insertPecInbox(Properties connectionProps, String software, String messageId, PECMessage pecMessage, String loginNameMail,
	    BigInteger idAccount) {

	log.debug("insertPECInbox(): {} [{},{},{},{},{},{},{}]",
		new Object[] { PEC_INBOX_INSERT, connectionProps.getProperty("db_idcomune"), messageId, AddressUtil.getAsString(pecMessage.getFrom()),
			AddressUtil.getAsString(pecMessage.getTo()), pecMessage.getSubject(), pecMessage.getDate(), software });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(PEC_INBOX_INSERT);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    String idMsg = messageId;
	    if (idMsg.startsWith("<")) {
		idMsg = idMsg.substring(1);
	    }
	    if (idMsg.endsWith(">")) {
		idMsg = idMsg.substring(0, idMsg.length() - 1);
	    }
	    pstmt.setString(2, idMsg);
	    pstmt.setString(3, pecMessage.getSubject());
	    pstmt.setString(4, AddressUtil.getAsString(pecMessage.getFrom()));
	    pstmt.setString(5, AddressUtil.getAsString(pecMessage.getTo()));
	    // pstmt.setDate(6, new java.sql.Date(pecMessage.getDate().getTime()));
	    pstmt.setTimestamp(6, new java.sql.Timestamp(pecMessage.getDate().getTime()));
	    pstmt.setString(7, software);
	    //
	    pstmt.setInt(8, 1);
	    pstmt.setNull(9, java.sql.Types.INTEGER);
	    pstmt.setNull(10, java.sql.Types.INTEGER);
	    pstmt.setNull(11, java.sql.Types.INTEGER);
	    pstmt.setNull(12, java.sql.Types.VARCHAR);
	    pstmt.setNull(13, java.sql.Types.VARCHAR);
	    pstmt.setNull(14, java.sql.Types.DATE);
	    pstmt.setNull(15, java.sql.Types.INTEGER);
	    pstmt.setInt(16, 0);
	    pstmt.setInt(17, 1);
	    pstmt.setString(18, loginNameMail);
	    pstmt.setNull(19, java.sql.Types.INTEGER);
	    pstmt.setString(20, AddressUtil.getAsString(pecMessage.getCc()));
	    pstmt.setNull(21, java.sql.Types.VARCHAR);
	    pstmt.setNull(22, java.sql.Types.VARCHAR);
	    if (idAccount != null) {
		Integer _idAccount = idAccount.intValue();
		pstmt.setInt(23, _idAccount);
	    } else {
		pstmt.setNull(23, java.sql.Types.INTEGER);
	    }
	    int result = pstmt.executeUpdate();
	    return result;
	} catch (SQLException e) {
	    log.error("insertPECInbox() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(null, pstmt, conn);
	}
    }

    public PersonaFisicaType getAnagrafePF(Properties connectionProps, String idAnagrafe) {

	log.debug("getAnagrafePF(): {} [{},{}]", new Object[] { GET_PF, connectionProps.getProperty("db_idcomune"), idAnagrafe });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	PersonaFisicaType pf = null;
	try {
	    pstmt = conn.prepareStatement(GET_PF);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, idAnagrafe);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		pf = new PersonaFisicaType();
		pf.setCognome(rs.getString("nominativo"));
		pf.setNome(rs.getString("nome"));
		pf.setCodiceFiscale(rs.getString("codicefiscale"));
	    }
	    return pf;
	} catch (SQLException e) {
	    log.error("getAnagrafePF() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public boolean isComuneAssociato(Properties connectionProps, String idcomune) {

	log.debug("isComuneAssociato(): {} [{}]", new Object[] { IS_COMUNE_ASSOCIATO, connectionProps.getProperty("db_idcomune") });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    pstmt = conn.prepareStatement(IS_COMUNE_ASSOCIATO);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    rs = pstmt.executeQuery();
	    int num = 0;
	    if (rs.next()) {
		num = rs.getInt("numerocomuni");
	    }
	    if (num > 1) {
		return true;
	    } else {
		return false;
	    }
	} catch (SQLException e) {
	    log.error("isComuneAssociato(): ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public String getCodiceComuneAssociato(Properties connectionProps, String software, String codiceAccreditamento) {

	log.debug("getCodiceComuneAssociato(): {} [{},{},{}]",
		new Object[] { GET_CODICE_COMUNE_ASSOCIATO, software, connectionProps.getProperty("db_idcomune") });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String codiceComuneAssociato = "";
	try {
	    pstmt = conn.prepareStatement(GET_CODICE_COMUNE_ASSOCIATO);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, software);
	    pstmt.setString(3, codiceAccreditamento.trim());
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		codiceComuneAssociato = rs.getString("codicecomune");
	    }
	    return codiceComuneAssociato;
	} catch (SQLException e) {
	    log.error("getCodiceComuneAssociato(): ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }

    public int setCodicePratica(Properties connectionProps, String software, String messageId, PECMessage pecMessage, String codicePratica) {

	log.debug("setCodicePratica(): {} [{},{}]", new Object[] { UPDATE_CODICE_PRATICA, connectionProps.getProperty("db_idcomune"), messageId });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	try {
	    pstmt = conn.prepareStatement(UPDATE_CODICE_PRATICA);
	    pstmt.setInt(1, Integer.valueOf(codicePratica));
	    pstmt.setString(2, connectionProps.getProperty("db_idcomune"));
	    String idMsg = messageId;
	    if (idMsg.startsWith("<")) {
		idMsg = idMsg.substring(1);
	    }
	    if (idMsg.endsWith(">")) {
		idMsg = idMsg.substring(0, idMsg.length() - 1);
	    }
	    pstmt.setString(3, idMsg);
	    int result = pstmt.executeUpdate();
	    return result;
	} catch (SQLException e) {
	    log.error("insertPECInbox() : ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(null, pstmt, conn);
	}
    }

    public InfoIstanzaBean getInfoPratica(Properties connectionProps, String identificatoreMsgIdVBG, String software) {

	log.debug("getInfoPratica(): {} [{},{},{}]",
		new Object[] { GET_INFO_PRATICA, identificatoreMsgIdVBG, software, connectionProps.getProperty("db_idcomune") });
	Connection conn = getDBConnection(connectionProps);
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	InfoIstanzaBean infoIstanza = null;
	try {
	    pstmt = conn.prepareStatement(GET_INFO_PRATICA);
	    pstmt.setString(1, connectionProps.getProperty("db_idcomune"));
	    pstmt.setString(2, identificatoreMsgIdVBG);
	    rs = pstmt.executeQuery();
	    if (rs.next()) {
		infoIstanza = new InfoIstanzaBean();
		infoIstanza.setNumeroIstanza(rs.getString("numeroistanza"));
		infoIstanza.setOggetto(rs.getString("oggetto"));
		java.sql.Date dateSQL = rs.getDate("datapratica");
		Date d = new Date();
		d.setTime(dateSQL.getTime());
		infoIstanza.setDataPratica(d);
		infoIstanza.setCodiceIstanza(String.valueOf(rs.getInt("codiceistanza")));
		infoIstanza.setCodiceMovimento(String.valueOf(rs.getInt("codicemovimento")));
		int codProcedimento = rs.getInt("codinventario");
		if (codProcedimento != 0) {
		    infoIstanza.setCodiceInventarioProcedimento(String.valueOf(codProcedimento));
		}
		infoIstanza.setTipoMovimento(rs.getString("tipomovimento"));
		infoIstanza.setIdcomune(connectionProps.getProperty("db_idcomune"));
		infoIstanza.setSoftware(software);
	    }
	    return infoIstanza;
	} catch (SQLException e) {
	    log.error("getCodiceComuneAssociato(): ", e);
	    throw new RuntimeException(e.getMessage());
	} finally {
	    this.closeDBConnection(rs, pstmt, conn);
	}
    }
}
