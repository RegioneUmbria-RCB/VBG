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
import it.gruppoinit.service.helper.StatusHelper;
import it.gruppoinit.service.helper.TipimovHelper;
import it.gruppoinit.sigeprosecurity.schema.GetDbConnectionInfoResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;
import it.gruppoinit.utils.Utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.rowset.serial.SerialException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackendOFFLineService {

    private static final String TIPOMOVNONSETTATO = "tipomovnonsettato";
    private static final String CONSOLE_DIZ_PREFIX = "CONSOLE#DIZ#";
    private static final String SOFTWARE_TT = "TT";
    private SigeproSecurityWSClient sigeproSecurityWSClient;
    private String aliasDestinazione;
    private String softwareDestinazione;
    private String idOperazione;
    private Map<String, TipimovHelper> ammTipiMov = new HashMap<String, TipimovHelper>();
    private Map<Integer, Integer> ammRemoteAmmLoc = new HashMap<Integer, Integer>();
    private Map<Integer, String> ammCodAncitel = new HashMap<Integer, String>();
    Boolean escludiDisabilitati = null;
    private String scCodice = null;
    private static final Logger activityLog = LoggerFactory.getLogger("registrazione_attivita");
    private ConsoleRestClientService console;

    public BackendOFFLineService(String aliasOrigine, String aliasDestinazione, String softwareOrigine, String softwareDestinazione,
	    SigeproSecurityWSClient sigeproSecurityWSClient, String idOperazione, Boolean escludiDisabilitati, String scCodice,
	    String consoleRemotaWsUrl) {

	if (StatusHelper.checkOperazioneInserita(idOperazione) == true) {
	    throw new SecurityException("L'operazione con identificativo " + idOperazione + " e' già stata avviata");
	}
	this.idOperazione = idOperazione;
	this.aliasDestinazione = aliasDestinazione;
	this.softwareDestinazione = softwareDestinazione;
	this.sigeproSecurityWSClient = sigeproSecurityWSClient;
	this.escludiDisabilitati = escludiDisabilitati;
	this.scCodice = scCodice;
	console = new ConsoleRestClientService(aliasOrigine, softwareOrigine, idOperazione, idOperazione, consoleRemotaWsUrl);
    }

    public void copiaVoceAlbero() throws Exception {

	if (activityLog.isInfoEnabled()) {
	    activityLog.info("Inizio procedura di copia dati.");
	}
	StatusHelper.aggiungiMessaggio(idOperazione, "Inizio cerco le connessioni origine / destinazione");
	GetDbConnectionInfoResponse propsDestinazione = sigeproSecurityWSClient.getConnectionProperties(aliasDestinazione);
	String idcomuneDestinazione = propsDestinazione.getIdComune();
	StatusHelper.aggiungiMessaggio(idOperazione, "Apro la connessione destinazione");
	Connection cDestinazione = sigeproSecurityWSClient.getConnection(aliasDestinazione);
	cDestinazione.setAutoCommit(false);
	// FACCIO LA QUERY DI LETTURA di ogni tabella
	// TIPICAUSALIONERI
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPICAUSALIONERI");
	// copia le info a partire da una voce dell'albero
	processTipiCausaliOneri(cDestinazione, "TIPICAUSALIONERI", idcomuneDestinazione);
	StatusHelper.aggiungiMessaggio(idOperazione, "TIPISOGGETTO");
	processTipisoggetto(cDestinazione, "TIPISOGGETTO", idcomuneDestinazione);
	// l'albero se non root va messo su root dell'ente di destinazione
	// tipisoggetto legati all'albero - no perché potrebbero arrivare pratiche di procedimento unico
	StatusHelper.aggiungiMessaggio(idOperazione, "RECUPERO LE INFORMAZIONI DELL'ALBERO INTERVENTI. SC_CODICE: " + scCodice);
	InformazioniAlberoInterventiBean informazioniIntervento = processTrovaEndoAlbero("alberoproc");
	// se scCodice null o "" allora inserisco anche le voci dell'albero
	if (StringUtils.isBlank(scCodice)) {
	    StatusHelper.aggiungiMessaggio(idOperazione, "INSERISCO LE INFORMAZIONI DELL'ALBERO INTERVENTI.");
	    inserisciAlbero(informazioniIntervento, cDestinazione, idcomuneDestinazione);
	}
	// endoprocedimenti solo quelli all'alberoproc_endo e i subendo
	// amministrazioni degli endo
	// schede dinamiche degli endo
	StatusHelper.aggiungiMessaggio(idOperazione, "COPIO GLI ENDOPROCEDIMENTI.");
	processCopiaEndo(cDestinazione, "inventarioprocedimenti", idcomuneDestinazione, informazioniIntervento, escludiDisabilitati);
	Utils.closeObjects(cDestinazione);
	StatusHelper.aggiungiMessaggio(idOperazione, "OPERAZIONE TERMINATA");
    }

    private void inserisciAlbero(InformazioniAlberoInterventiBean informazioniIntervento, Connection cDestinazione, String idcomuneDestinazione)
	    throws Exception {

	List<AlberoInterventiBean> alberoInterventiBean = informazioniIntervento.getAlberoInterventiBean();
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
	for (AlberoInterventiBean rs : alberoInterventiBean) {
	    Integer codice = rs.getScId();
	    String scDescrizione = rs.getScDescrizione();
	    Integer scPadre = rs.getScPadre();
	    String statoControllo = rs.getScStatoControllo();
	    Integer scOrdine = rs.getScOrdine();
	    Integer fkidazione = rs.getFkidazione();
	    String scCodice = rs.getScCodice();
	    String atribTipologiaIntervento = rs.getAtribTipologiaintervento();
	    Integer scAttivo = rs.getScAttivo();
	    String descrizioneCompleta = rs.getDescrizioneCompleta();
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
		psUpdate.clearParameters();
		activityLog.debug("aggiorno {}-{}", "ALBEROPROC", codice);
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", "ALBEROPROC", codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private InformazioniAlberoInterventiBean processTrovaEndoAlbero(String tabella) {

	InformazioniAlberoInterventiBean tsbs = console.getInformazioniAlberoInterventi(scCodice, escludiDisabilitati);
	return tsbs;
    }

    private void processCopiaEndo(Connection cDestinazione, String tabella, String idcomuneDestinazione,
	    InformazioniAlberoInterventiBean informazioniIntervento, Boolean escludiDisabilitati) throws Exception {

	processAmministrazioni(cDestinazione, "AMMINISTRAZIONI", idcomuneDestinazione, informazioniIntervento.getCodiciAmministrazioniEndo());
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIFAMIGLIEENDO");
	processTipifamiglieEndo(cDestinazione, idcomuneDestinazione, informazioniIntervento.getCodiciFamiglieEndo());
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO TIPIENDO");
	processTipiEndo(cDestinazione, idcomuneDestinazione, informazioniIntervento.getCodiciTipiEndo());
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO INVENTARIOPROCEDIMENTI");
	processEndo(cDestinazione, idcomuneDestinazione, informazioniIntervento.getCodiciEndoProcedimenti());
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO MODELLI DINAMICI");
	processModelliDinamici(cDestinazione, idcomuneDestinazione, informazioniIntervento.getCodiciSchedeEndo());
    }

    private void processModelliDinamici(Connection cDestinazione, String idcomuneDestinazione, Set<Integer> codiciSchedeEndo) throws Exception {

	if (codiciSchedeEndo == null || codiciSchedeEndo.isEmpty()) {
	    return;
	}
	ListaSchedeDinamicheBean sdb = console.getListaSchedeDinamiche(codiciSchedeEndo);
	List<CampiDinamiciBean> campis = console.getCampiDinamici(sdb.getCodiciCampi());
	processDyn2Campi(cDestinazione, idcomuneDestinazione, campis);
	processDyn2ModelliT(cDestinazione, idcomuneDestinazione, sdb);
    }

    private void processDyn2ModelliT(Connection cDestinazione, String idcomuneDestinazione, ListaSchedeDinamicheBean sdb) throws Exception {

	processDyn2ModelliDTesti(cDestinazione, idcomuneDestinazione, sdb);
	String tabella = "DYN2_MODELLIT";
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_MODELLIT");
	List<SchedeDinamicheBean> schedes = sdb.getSchedes();
	if (schedes != null && schedes.size() > 0) {
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
	    int pos = 1;
	    for (SchedeDinamicheBean scheda : schedes) {
		//	 "SELECT ID,SOFTWARE,DESCRIZIONE,FK_D2BC_ID,SCRIPTCODE,MODELLOMULTIPLO,FLG_STORICIZZA,FLG_READONLY_WEB,MODELLO_FRONTOFFICE,CODICE_SCHEDA FROM DYN2_MODELLIT WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
		Integer codice = scheda.getCodice();
		String software = scheda.getSoftware();
		String descrizione = scheda.getDescrizione();
		String d2bcid = scheda.getD2bcid();
		String scriptCodce = scheda.getScriptCodce();
		Integer modellomultiplo = scheda.getModellomultiplo();
		Integer flgStoricizza = scheda.getFlgStoricizza();
		Integer flagReadOnlyWeb = scheda.getFlagReadOnlyWeb();
		Integer modelloFrontoffice = scheda.getModelloFrontoffice();
		String codiceScheda = scheda.getCodiceScheda();
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
		    psUpdate.clearParameters();
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
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codice);
		}
		processDyn2ModelliTScript(cDestinazione, idcomuneDestinazione, scheda);
		processDyn2ModelliD(cDestinazione, idcomuneDestinazione, scheda);
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
    }

    private void processDyn2ModelliD(Connection cDestinazione, String idcomuneDestinazione, SchedeDinamicheBean scheda) throws Exception {

	String tabella = "DYN2_MODELLID";
	List<SchedeDinamicheDettaglioBean> dettaglios = scheda.getDettaglios();
	if (dettaglios != null && dettaglios.size() > 0) {
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
	    for (SchedeDinamicheDettaglioBean rs : dettaglios) {
		//	 "SELECT ID,FK_D2MT_ID,FK_D2C_ID,FK_D2MDT_ID,POSVERTICALE,POSORIZZONTALE,FLG_MULTIPLO,FK_REGOLA_ATTIVO,FLG_OBBLIGATORIO,FLG_SPEZZA_TABELLA FROM DYN2_MODELLID WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
		Integer codice = rs.getCodice();
		//System.out.println("processDyn2ModelliD: " + codice);
		Integer fkd2mtid = scheda.getCodice();
		Integer fkd2cid = rs.getFkd2cid();
		Integer fkd2mdtid = rs.getFkd2mdtid();
		Integer posverticale = rs.getPosverticale();
		Integer posorizzontale = rs.getPosorizzontale();
		Integer flagMultiplo = rs.getFlagMultiplo();
		Integer fkRegolaAttivo = rs.getFkRegolaAttivo();
		Integer flagObbligatorio = rs.getFlagObbligatorio();
		Integer flagSpezza = rs.getFlagSpezza();
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
		    psUpdate.clearParameters();
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
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codice);
		}
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
    }

    private void processDyn2ModelliTScript(Connection cDestinazione, String idcomuneDestinazione, SchedeDinamicheBean scheda) throws SQLException {

	String tabella = "DYN2_MODELLI_SCRIPT";
	List<SchedeDinamicheScriptBean> dettaglios = scheda.getScripts();
	if (dettaglios != null && dettaglios.size() > 0) {
	    //// exists 
	    StringBuilder queryExists = new StringBuilder(
		    "SELECT FK_D2MT_ID FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=? ");
	    PreparedStatement psExists = null;
	    psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ResultSet rsExists = null;
	    // UPDATE
	    StringBuilder queryUpdate = new StringBuilder("UPDATE DYN2_MODELLI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=?");
	    PreparedStatement psUpdate = null;
	    psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	    // INSERT
	    StringBuilder queryInsert = new StringBuilder("INSERT INTO DYN2_MODELLI_SCRIPT (IDCOMUNE,FK_D2MT_ID,EVENTO,SCRIPT) VALUES (?,?,?,?)");
	    PreparedStatement psInsert = null;
	    psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	    //
	    int pos = 1;
	    Integer codiceScheda = scheda.getCodice();
	    for (SchedeDinamicheScriptBean s : dettaglios) {
		//	 "SELECT FK_D2MT_ID,EVENTO,SCRIPT  FROM DYN2_MODELLI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2MT_ID>=? ORDER BY FK_D2MT_ID"
		String evento = s.getEvento();
		// Blob script = Utils.decodeBase64ToBlob(s.getBase64Script());
		InputStream script = Utils.decodeBase64ToBlob(s.getBase64Script());
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codiceScheda);
		psExists.setString(3, evento);
		rsExists = psExists.executeQuery();
		// cerco nella destinazione se esiste il record
		if (rsExists.next()) {
		    Utils.closeObjects(rsExists);
		    // se esite lo modifico nella destinazione
		    // "UPDATE DYN2_MODELLI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2MT_ID=? AND EVENTO=?"
		    pos = 1;
		    Utils.setBlobOrNUll(script, psUpdate, pos++);
		    psUpdate.setString(pos++, idcomuneDestinazione);
		    psUpdate.setInt(pos++, codiceScheda);
		    psUpdate.setString(pos++, evento);
		    // update
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    psUpdate.clearParameters();
		    activityLog.debug("aggiorno {}-{}", tabella, codiceScheda);
		} else {
		    Utils.closeObjects(rsExists);
		    //		INSERT INTO DYN2_MODELLI_SCRIPT (IDCOMUNE,FK_D2MT_ID,EVENTO,SCRIPT) VALUES (?,?,?,?)"
		    pos = 1;
		    psInsert.setString(pos++, idcomuneDestinazione);
		    psInsert.setInt(pos++, codiceScheda);
		    psInsert.setString(pos++, evento);
		    Utils.setBlobOrNUll(script, psInsert, pos++);
		    // insert
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codiceScheda);
		}
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
    }

    private void processDyn2ModelliDTesti(Connection cDestinazione, String idcomuneDestinazione, ListaSchedeDinamicheBean sdb) throws SQLException {

	String tabella = "DYN2_MODELLIDTESTI";
	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_MODELLIDTESTI");
	List<SchedeDinamicheBean> schedes = sdb.getSchedes();
	if (schedes != null && schedes.size() > 0) {
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
	    int pos = 1;
	    for (SchedeDinamicheBean s : schedes) {
		if (s.getTestis() != null && s.getTestis().size() > 0) {
		    List<SchedeDinamicheDettaglioTestiBean> t = s.getTestis();
		    if (t != null && t.size() > 0) {
			for (SchedeDinamicheDettaglioTestiBean rs : t) {
			    //	 "SELECT ID,FK_D2BTT_ID,TESTO FROM DYN2_MODELLIDTESTI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
			    Integer codice = rs.getCodice();
			    String d2btt = rs.getD2btt();
			    String testo = rs.getTesto();
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
				psUpdate.clearParameters();
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
				psInsert.clearParameters();
				// se non esiste inserisco i dati nella destinazione
				activityLog.debug("inserisco {}-{}", tabella, codice);
			    }
			}
		    }
		}
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
    }

    private void processDyn2Campi(Connection cDestinazione, String idcomuneDestinazione, List<CampiDinamiciBean> campis) throws Exception {

	StatusHelper.aggiungiMessaggio(idOperazione, "PROCESSO DYN2_CAMPI");
	String tabella = "DYN2_CAMPI";
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
	for (CampiDinamiciBean bean : campis) {
	    //	 "SELECT ID,SOFTWARE,NOMECAMPO,ETICHETTA,DESCRIZIONE,TIPODATO,OBBLIGATORIO,SCRPTCODE,SCRIPTUPDATECODE,FK_D2BC_ID FROM DYN2_CAMPI WHERE IDCOMUNE = ? AND ID>=? ORDER BY ID"
	    Integer codice = bean.getCodice();
	    String software = bean.getSoftware();
	    String nomecampo = bean.getNomecampo();
	    String etichetta = bean.getEtichetta();
	    String descrizione = bean.getDescrizione();
	    String tipoDato = bean.getTipoDato();
	    Integer obbligatorio = bean.getObbligatorio();
	    String scriptCode = Utils.decodeBase64ToString(bean.getScriptCodeBase64());
	    String scriptUpdateCode = Utils.decodeBase64ToString(bean.getScriptUpdateCodeBase64());
	    String fkd2bcId = bean.getFkd2bcId();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	    processDyn2CampiScriptEProprieta(cDestinazione, idcomuneDestinazione, bean);
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private void processDyn2CampiScriptEProprieta(Connection cDestinazione, String idcomuneDestinazione, CampiDinamiciBean bean) throws Exception {

	String tabella = "DYN2_CAMPIPROPRIETA";
	Integer codiceCampo = bean.getCodice();
	if (bean.getProps() != null && bean.getProps().size() > 0) {
	    List<CampiDinamiciProprietaBean> rs = bean.getProps();
	    //// exists 
	    StringBuilder queryExists = new StringBuilder(
		    "SELECT FK_D2C_ID FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND PROPRIETA=?");
	    PreparedStatement psExists = null;
	    psExists = cDestinazione.prepareStatement(queryExists.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	    ResultSet rsExists = null;
	    // UPDATE
	    StringBuilder queryUpdate = new StringBuilder(
		    "UPDATE DYN2_CAMPIPROPRIETA SET VALORE=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND PROPRIETA=?");
	    PreparedStatement psUpdate = null;
	    psUpdate = cDestinazione.prepareStatement(queryUpdate.toString());
	    // INSERT
	    StringBuilder queryInsert = new StringBuilder("INSERT INTO DYN2_CAMPIPROPRIETA (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE) VALUES (?,?,?,?)");
	    PreparedStatement psInsert = null;
	    psInsert = cDestinazione.prepareStatement(queryInsert.toString());
	    //
	    int pos = 1;
	    for (CampiDinamiciProprietaBean cdp : rs) {
		//	 "SELECT FK_D2C_ID,PROPRIETA,VALORE FROM DYN2_CAMPIPROPRIETA WHERE IDCOMUNE = ? AND FK_D2C_ID=? ORDER BY FK_D2C_ID"
		String proprieta = cdp.getProprieta();
		String valore = cdp.getValore();
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codiceCampo);
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
		    psUpdate.setInt(pos++, codiceCampo);
		    psUpdate.setString(pos++, proprieta);
		    // update
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    psUpdate.clearParameters();
		    activityLog.debug("aggiorno {}-{}", tabella, codiceCampo);
		} else {
		    Utils.closeObjects(rsExists);
		    //		INSERT INTO DYN2_CAMPIPROPRIETA (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE) VALUES (IDCOMUNE,FK_D2C_ID,PROPRIETA,VALORE)"
		    pos = 1;
		    psInsert.setString(pos++, idcomuneDestinazione);
		    psInsert.setInt(pos++, codiceCampo);
		    psInsert.setString(pos++, proprieta);
		    psInsert.setString(pos++, valore);
		    // insert
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codiceCampo);
		}
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
	if (bean.getScripts() != null && bean.getScripts().size() > 0) {
	    tabella = "DYN2_CAMPI_SCRIPT";
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
	    for (CampiDinamiciScript rs : bean.getScripts()) {
		//	 "SELECT FK_D2C_ID,EVENTO,SCRIPT FROM DYN2_CAMPI_SCRIPT WHERE IDCOMUNE = ? AND FK_D2C_ID>=? ORDER BY FK_D2C_ID"
		String evento = rs.getEvento();
		InputStream script = Utils.decodeBase64ToBlob(rs.getScriptBase64());
		// exists
		psExists.setString(1, idcomuneDestinazione);
		psExists.setInt(2, codiceCampo);
		psExists.setString(3, evento);
		rsExists = psExists.executeQuery();
		// cerco nella destinazione se esiste il record
		if (rsExists.next()) {
		    Utils.closeObjects(rsExists);
		    // se esite lo modifico nella destinazione
		    // "UPDATE DYN2_CAMPI_SCRIPT SET SCRIPT=? WHERE IDCOMUNE = ? AND FK_D2C_ID=? AND EVENTO=?"
		    pos = 1;
		    Utils.setBlobOrNUll(script, psUpdate, pos++);
		    psUpdate.setString(pos++, idcomuneDestinazione);
		    psUpdate.setInt(pos++, codiceCampo);
		    psUpdate.setString(pos++, evento);
		    // update
		    psUpdate.executeUpdate();
		    cDestinazione.commit();
		    psUpdate.clearParameters();
		    activityLog.debug("aggiorno {}-{}", tabella, codiceCampo);
		} else {
		    Utils.closeObjects(rsExists);
		    //		INSERT INTO DYN2_CAMPI_SCRIPT (IDCOMUNE,FK_D2C_ID,EVENTO,SCRIPT ) VALUES (IDCOMUNE,FK_D2C_ID,EVENTO,SCRIPT)"
		    pos = 1;
		    psInsert.setString(pos++, idcomuneDestinazione);
		    psInsert.setInt(pos++, codiceCampo);
		    psInsert.setString(pos++, evento);
		    Utils.setBlobOrNUll(script, psInsert, pos++);
		    // insert
		    psInsert.executeUpdate();
		    cDestinazione.commit();
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codiceCampo);
		}
	    }
	    Utils.closeObjects(psUpdate);
	    Utils.closeObjects(psInsert);
	    Utils.closeObjects(rsExists);
	    Utils.closeObjects(psExists);
	}
    }

    public static void main(String[] args) throws UnsupportedEncodingException, SerialException, SQLException {

	String f = "fava";
	byte[] b = Base64.encodeBase64(f.getBytes("UTF-8"));
	f = new String(b);
	System.out.println(new String(b));
	b = Base64.decodeBase64(f);
	System.out.println(new String(b));
	//	Blob Utils.decodeBase64ToBlob = Utils.decodeBase64ToBlob(f);
	//	System.out.println(Utils.decodeBase64ToBlob);
    }

    private void processEndo(Connection cDestinazione, String idcomuneDestinazione, Set<Integer> codiciEndoProcedimenti) throws Exception {

	if (codiciEndoProcedimenti == null || codiciEndoProcedimenti.isEmpty()) {
	    return;
	}
	List<InventarioprocedimentoBean> rss = console.getListaEndoprodicedimenti(codiciEndoProcedimenti);
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
	Map<String, Integer> naturebaseCodici = new HashMap<String, Integer>();
	//	scia
	//	ordinario
	//	comunicazione
	for (InventarioprocedimentoBean rs : rss) {
	    String naturabase = rs.getNaturabase();
	    String procedimento = rs.getProcedimento();
	    Integer amministrazione = rs.getAmministrazione();
	    Integer ammLocale = null;
	    if (amministrazione != null) {
		ammLocale = ammRemoteAmmLoc.get(amministrazione);
		if (ammLocale != null) {
		    amministrazione = ammLocale;
		}
	    }
	    Date dataaggiornamento = rs.getDataaggiornamento();
	    Integer codiceTipo = rs.getCodiceTipo();
	    Integer disabilitato = rs.getDisabilitato();
	    Integer ordine = rs.getOrdine();
	    String codiceancitel = rs.getCodiceancitel();
	    Integer flagPubblica = rs.getFlagPubblica();
	    String software = rs.getSoftware();
	    Integer codice = rs.getCodice();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	    if (software.equalsIgnoreCase(SOFTWARE_TT)) {
		// verifico/inserisco su inventarioprocedimentisoftware destinazione
		inserisciSuInvprocSoftware(cDestinazione, idcomuneDestinazione, rs, amministrazione, tipomovimento);
	    }
	}
	// inserisciSubEndo(cDestinazione, idcomuneDestinazione, rss);
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private void inserisciSuInvprocSoftware(Connection cDestinazione, String idcomuneDestinazione, InventarioprocedimentoBean ip,
	    Integer codiceAmministrazione, String tipomovimento) throws Exception {

	if (ip.getIpSoftwareBeans() == null || ip.getIpSoftwareBeans().isEmpty()) {
	    return;
	}
	Integer codiceInventario = ip.getCodice();
	List<InventarioprocedimentoSoftwareBean> l = ip.getIpSoftwareBeans();
	Set<String> moduliOrigine = new HashSet<String>();
	for (InventarioprocedimentoSoftwareBean inventarioprocedimentoSoftwareBean : l) {
	    moduliOrigine.add(inventarioprocedimentoSoftwareBean.getModuloSoftware());
	}
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
		ps.clearParameters();
	    } else {
		String sqlupdate = "update inventarioprocedimentisoftware set codiceamministrazione=? where idcomune=? and id=?";
		ps = cDestinazione.prepareStatement(sqlupdate, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		Utils.setIntOrNUll(codiceAmministrazione, ps, 1);
		ps.setString(2, idcomuneDestinazione);
		ps.setInt(3, id);
		ps.executeUpdate();
		cDestinazione.commit();
		ps.clearParameters();
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
	    ps.clearParameters();
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
		    ps.clearParameters();
		}
	    }
	}
	Utils.closeObjects(rs);
	Utils.closeObjects(ps);
    }
    //    private void inserisciSubEndo(Connection cDestinazione, String idcomuneDestinazione, List<InventarioprocedimentoBean> rss) throws Exception {
    //
    //	String sqlDelete = "delete from inventarioproc_endo where idcomune=? and codiceinventario_t=?";
    //	PreparedStatement psDelete = cDestinazione.prepareStatement(sqlDelete);
    //	ResultSet rsMax = null;
    //	//
    //	String sqlInsert = "INSERT INTO inventarioproc_endo(idcomune,id,codiceinventario_t,codiceinventario_d,flag_pubblica,flag_necessario,codicecomune) VALUES (?,?,?,?,?,?,?)";
    //	PreparedStatement psInsert = cDestinazione.prepareStatement(sqlInsert);
    //	String sqlMax = "select max(id) as massimo from inventarioproc_endo where idcomune=?";
    //	PreparedStatement psMax = cDestinazione.prepareStatement(sqlMax, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
    //	int max = 1;
    //	for (InventarioprocedimentoBean ip : rss) {
    //	    if (ip.getSubEndos() != null && ip.getSubEndos().size() > 0) {
    //		List<SubEndoBean> sb = ip.getSubEndos();
    //		Integer codiceInventario = ip.getCodice();
    //		// cancello le configurazioni esistenti 
    //		psDelete.setString(1, idcomuneDestinazione);
    //		psDelete.setInt(2, codiceInventario);
    //		psDelete.executeUpdate();
    //		cDestinazione.commit();
    //		psDelete.clearParameters();
    //		// select dei record per codiceinventario_t
    //		for (SubEndoBean rs : sb) {
    //		    Integer codiceinventario_d = rs.getCodiceinventario_d();
    //		    Integer flag_pubblica = rs.getFlag_pubblica();
    //		    Integer flag_necessario = rs.getFlag_necessario();
    //		    String codiceComune = StringUtils.defaultIfBlank(rs.getCodiceComune(), null);
    //		    psMax.setString(1, idcomuneDestinazione);
    //		    rsMax = psMax.executeQuery();
    //		    if (rsMax.next()) {
    //			max = rsMax.getInt(1);
    //			max += 1;
    //		    }
    //		    psMax.clearParameters();
    //		    Utils.closeObjects(rsMax);
    //		    psInsert.setString(1, idcomuneDestinazione);
    //		    psInsert.setInt(2, max);
    //		    psInsert.setInt(3, codiceInventario);
    //		    psInsert.setInt(4, codiceinventario_d);
    //		    psInsert.setInt(5, flag_pubblica);
    //		    psInsert.setInt(6, flag_necessario);
    //		    if (StringUtils.isNotBlank(codiceComune)) {
    //			psInsert.setString(7, codiceComune);
    //		    } else {
    //			psInsert.setNull(7, Types.VARCHAR);
    //		    }
    //		    psInsert.executeUpdate();
    //		    cDestinazione.commit();
    //		    psInsert.clearParameters();
    //		}
    //	    }
    //	}
    //	Utils.closeObjects(psDelete);
    //	Utils.closeObjects(psInsert);
    //	Utils.closeObjects(psMax);
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

    private void processTipiEndo(Connection cDestinazione, String idcomuneDestinazione, Set<Integer> codiciTipiEndo) throws Exception {

	if (codiciTipiEndo == null || codiciTipiEndo.isEmpty()) {
	    return;
	}
	List<TipiEndoBean> rss = console.getListaTipiEndo(codiciTipiEndo);
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
	for (TipiEndoBean rs : rss) {
	    String tipo = rs.getTipo();
	    Integer ordine = rs.getOrdine();
	    String software = rs.getSoftware();
	    String note = rs.getNote();
	    Integer flagPubblica = rs.getFlagPubblica();
	    Integer codiceFamiglia = rs.getCodiceFamiglia();
	    Integer codice = rs.getCodice();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private void processTipifamiglieEndo(Connection cDestinazione, String idcomuneDestinazione, Set<Integer> codiciFamiglieEndo) throws Exception {

	if (codiciFamiglieEndo == null || codiciFamiglieEndo.isEmpty()) {
	    return;
	}
	List<TipiFamiglieEndoBean> rss = console.getListaTipifamiglieEndo(codiciFamiglieEndo);
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
	for (TipiFamiglieEndoBean rs : rss) {
	    Integer codice = rs.getCodice();
	    String tipo = rs.getTipo();
	    Integer ordine = rs.getOrdine();
	    String software = rs.getSoftware();
	    String note = rs.getNote();
	    Integer flagPubblica = rs.getFlagPubblica();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private void processAmministrazioni(Connection cDestinazione, String tabella, String idcomuneDestinazione, Set<Integer> codiciAmministrazioniEndo)
	    throws Exception {

	List<AmministrazioniBean> rss = console.getListaAmministrazioni(codiciAmministrazioniEndo);
	//	String query = "SELECT CODICEAMMINISTRAZIONE,AMMINISTRAZIONE,UFFICIO,REFERENTE,INDIRIZZO,CITTA,CAP,PROVINCIA,TELEFONO1,TELEFONO2,FAX,EMAIL,WEB,FLAG_SILENZIODINIEGO,"
	//		+ "CODICEANCITEL,PROGRESSIVOEXPORT,PARTITAIVA,STC_IDENTE,STC_IDSPORTELLO,PEC,FLAG_DISABILITATO,STC_IDNODO FROM AMMINISTRAZIONI WHERE IDCOMUNE=? AND ";
	//	query += " CODICEAMMINISTRAZIONE>=?  ";
	//	if (codiciAmministrazioni != null && codiciAmministrazioni.size() > 0) {
	//	    query += " AND  CODICEAMMINISTRAZIONE in (";
	//	    String inQM = "";
	//	    for (int i = 0; i < codiciAmministrazioni.size(); i++) {
	//		inQM += ",?";
	//	    }
	//	    inQM = inQM.replaceFirst(",", "");
	//	    query += inQM + ")";
	//	}
	//	query += " ORDER BY CODICEAMMINISTRAZIONE";
	//	PreparedStatement ps = null;
	//	ResultSet rs = null;
	//	ps = cOrigine.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
	//	ps.setString(1, idcomuneOrigine);
	//	int pos = 2;
	//	ps.setInt(pos++, CODICE_MASTER);
	//	if (codiciAmministrazioni != null && codiciAmministrazioni.size() > 0) {
	//	    for (Integer codiceAmm : codiciAmministrazioni) {
	//		ps.setInt(pos++, codiceAmm);
	//	    }
	//	}
	//	rs = ps.executeQuery();
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
	for (AmministrazioniBean rs : rss) {
	    //	    CODICEAMMINISTRAZIONE,AMMINISTRAZIONE,UFFICIO,REFERENTE,INDIRIZZO,CITTA,CAP,PROVINCIA,TELEFONO1,TELEFONO2,FAX,EMAIL,WEB,FLAG_SILENZIODINIEGO,"
	    //			+ "	CODICEANCITEL,PROGRESSIVOEXPORT,PARTITAIVA,STC_IDENTE,STC_IDSPORTELLO,PEC,FLAG_DISABILITATO,STC_IDNODO FROM AMMINISTRAZIONI ");
	    //	query.append("WHERE IDCOMUNE = ? AND CODICEAMMINISTRAZIONE>=? ORDER BY CODICEAMMINISTRAZIONE");
	    Integer codiceAmministrazione = rs.getCodiceAmministrazione();
	    String amministrazione = rs.getAmministrazione();
	    String ufficio = rs.getUfficio();
	    String referente = rs.getReferente();
	    String indirizzo = rs.getIndirizzo();
	    String citta = rs.getCitta();
	    String cap = rs.getCap();
	    String provincia = rs.getProvincia();
	    String telefono1 = rs.getTelefono1();
	    String telefono2 = rs.getTelefono2();
	    String fax = rs.getFax();
	    String email = rs.getEmail();
	    String web = rs.getWeb();
	    Integer flagSilenzio = rs.getFlagSilenzio();
	    String codiceAncitel = rs.getCodiceAncitel();
	    String progressivoExp = rs.getProgressivoExp();
	    String piva = rs.getPiva();
	    String stcIdEnte = rs.getStcIdEnte();
	    String stcIdSportello = rs.getStcIdSportello();
	    String pec = rs.getPec();
	    Integer flagDisabilitato = rs.getFlagDisabilitato();
	    String stcIdNodo = rs.getStcIdNodo();
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
		psUpdate.clearParameters();
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
		    psInsert.clearParameters();
		    // se non esiste inserisco i dati nella destinazione
		    activityLog.debug("inserisco {}-{}", tabella, codiceAmministrazione);
		}
		Utils.closeObjects(rsExistsAncitel);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
	Utils.closeObjects(psExistAncitel);
    }

    private void processTipisoggetto(Connection cDestinazione, String tabella, String idcomuneDestinazione) throws SQLException {

	List<TipiSoggettoBean> tsbs = console.getTipisoggetto();
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
	for (TipiSoggettoBean tsb : tsbs) {
	    Integer codice = tsb.getCodice();
	    String tiposoggetto = tsb.getTiposoggetto();
	    Integer flagQualita = tsb.getFlagQualita();
	    String utilizzo = tsb.getUtilizzo();
	    Integer richiediAnagrafeColle = tsb.getRichiediAnagrafeColl();
	    Integer flgSpecDesc = tsb.getFlgSpecDesc();
	    Integer flgLegaleR = tsb.getFlgLegaleR();
	    Integer ordine = tsb.getOrdine();
	    Integer atribQualifica = tsb.getAtribQualifica();
	    Integer flgMostraDettIstanza = tsb.getFlgMostraDettIstanza();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}-{}", tabella, codice);
	    }
	}
	Utils.closeObjects(psUpdate);
	Utils.closeObjects(psInsert);
	Utils.closeObjects(rsExists);
	Utils.closeObjects(psExists);
    }

    private void processTipiCausaliOneri(Connection cDestinazione, String string, String idcomuneDestinazione) throws SQLException {

	List<TipiCausaliOneriBean> rs = console.getTipicausaliOneri();
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
	for (TipiCausaliOneriBean tipiCausaliOneriBean : rs) {
	    Integer coId = tipiCausaliOneriBean.getCoId();
	    String coDescrizione = tipiCausaliOneriBean.getCoDescrizione();
	    String coRichiedeEndo = tipiCausaliOneriBean.getCoSerichiedeendo();
	    Integer coDisabilitato = tipiCausaliOneriBean.getCoDisabilitato();
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
		psUpdate.clearParameters();
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
		psInsert.clearParameters();
		// se non esiste inserisco i dati nella destinazione
		activityLog.debug("inserisco {}", coId);
	    }
	}
	Utils.closeObjects(psUpdate, psInsert, rsExists, psExists);
    }
}
