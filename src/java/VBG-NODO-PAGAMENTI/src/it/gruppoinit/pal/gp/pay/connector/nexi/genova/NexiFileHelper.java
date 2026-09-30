/**
 * 
 */
package it.gruppoinit.pal.gp.pay.connector.nexi.genova;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.FileHelperFactory;
import it.gruppoinit.pal.gp.core.utils.IFileHelper;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.CartelleScambioFiles;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.PdfFile;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.TipiDocumento;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.TipiVerifica;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordException;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordEsiti;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;

/**
 * @author Franco.Leone Classe che implementa le specifiche NEXI per la generazione e il recupero dei files previsti dal
 *         connettore
 *
 */
public class NexiFileHelper {

    private static final Logger log = LoggerFactory.getLogger(NexiFileHelper.class);
    private static final String FILE_NAME_TYPE_SEPARATOR = "_";
    private static final String DATE_FORMAT = "yyyyMMddhhmmss";
    private static final String PDF_SUBFOLDER = "pdf";
    private static final char SEPARATORE_ID_RATE = ' ';
    private String connectorId = null;
    private String tipologiaEntrata = null;
    private String idLotto = null;
    private Date dataGenerazione = null;
    private String urlGeneraPdf = null;
    private Map<TipiDocumento, String> fileNamesMap = new HashMap<TipiDocumento, String>();
    // private PayConnectorWsEndpoint serviceParams = null;
    private String endpointURL;
    private String endpointUserName;
    private String endpointPassword;
    private IFileHelper fh;

    /**
     * @throws PayException
     * 
     */
    public NexiFileHelper(String endpointURL, String endpointUsername, String endpointPassword, String tipologiaEntrata, String idLotto)
	    throws PayException {

	if (StringUtils.isBlank(tipologiaEntrata)) {
	    throw new PayException("Per la generazione dei files dei tracciati NEXI deve essere specificata la tipologia di entrata");
	}
	if (StringUtils.isBlank(endpointURL)) {
	    throw new PayException("Indirizzo del srvizio per lo scambio dei flussi non specificato");
	}
	log.debug("NexiFileHelper {},{},{}", endpointURL, tipologiaEntrata, idLotto);
	PayProfiliEntiCreditori enteCorrente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (enteCorrente == null) {
	    this.connectorId = "NEXI";
	} else {
	    this.connectorId = enteCorrente.getPayConnector().getCodice();
	}
	this.endpointURL = endpointURL;
	this.endpointUserName = endpointUsername;
	this.endpointPassword = endpointPassword;
	this.tipologiaEntrata = tipologiaEntrata;
	this.idLotto = StringUtils.defaultString(idLotto);
	this.dataGenerazione = new Date();
	this.inizializeFileNames();
	try {
	    this.fh = FileHelperFactory.getFileHelper(this.endpointURL, endpointUserName, this.endpointPassword);
	} catch (IOException e) {
	    String msg = MessageFormat.format("errore nella creazione del servizio di scambio files all'URL {0} : {1}", endpointURL, e.toString());
	    log.error("NexiFileHelper() - " + msg, e);
	    throw new PayException(msg, e);
	}
    }

    public String getTipologiaEntrata() {

	return tipologiaEntrata;
    }

    public String getIdLotto() {

	return idLotto;
    }

    public Date getDataGenerazione() {

	return dataGenerazione;
    }

    public void setDataGenerazione(Date data) {

	this.dataGenerazione = data;
    }

    public void seDataGenerazione(Date dataGen) {

	this.dataGenerazione = dataGen;
    }

    public String getFileName(TipiDocumento tipoDoc) {

	return this.fileNamesMap.get(tipoDoc);
    }

    public String getUrlGeneraPdf() {

	return urlGeneraPdf;
    }

    public void setUrlGeneraPdf(String urlGeneraPdf) {

	this.urlGeneraPdf = urlGeneraPdf;
    }

    public void setTipologiaEntrata(String tipologiaEntrata) {

	this.tipologiaEntrata = tipologiaEntrata;
    }

    public void openConnection() throws PayException {

	if (this.fh == null)
	    throw new PayException("file helper non impostato");
	try {
	    this.fh.open();
	} catch (IOException e) {
	    String msg = MessageFormat.format("errore nella connessione al servizio di scambio dei flussi all'URL {0}: {1}", this.endpointURL,
		    e.toString());
	    log.error("openConnection - " + msg, e);
	    throw new PayException(msg, e);
	}
    }

    public void closeConnection() {

	if (this.fh != null) {
	    try {
		this.fh.close();
	    } catch (IOException e) {
		String msg = MessageFormat.format("errore nella disconnessione dal servizio di scambio dei flussi all'URL {0}: {1}", this.endpointURL,
			e.toString());
		log.error("closeConnection - " + msg, e);
		//throw new PayException(msg, e);
	    }
	}
    }

    public static void main(String[] args) throws IOException {

	Integer idPosizione = 1197;
	WebClient client = WebClient.create("http://10.10.45.64:8080/api-backend/services/rest-auth-token/bollettazione/lettera-accompagnamento")
		.path("{cf_ente_creditore}", "GE").path("{id_posizione_debitoria}", idPosizione);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(20000);
	conduit.getClient().setReceiveTimeout(35000);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_OCTET_STREAM);
	client.header("Authorization", "4149a750-472c-4c72-a17a-397053158205");
	Response response = null;
	response = client.get();
	StatusType st = response.getStatusInfo();
	if (!st.getFamily().equals(Family.SUCCESSFUL)) {
	    System.out.println(st.getStatusCode() + "-" + st.getReasonPhrase());
	    throw new RuntimeException(st.getStatusCode() + "-" + st.getReasonPhrase());
	}
	InputStream is = ((InputStream) response.getEntity());
	IOUtils.copyStreamToFile(is, new File("c:/temp/genova/avviso_" + idPosizione + ".pdf"));
    }

    /**
     * Metodo che si occupa di generare il PDF del documento di debito per la posizione debitoria passata in input. Il
     * metodo deve: 1) se il toke è null invocare security per staccare il token di autenticazione 2) se il token è già
     * presente deve essere validato su security 3) invocare il servizio del back per la generazione della lettera di
     * accompagnamento appendendo all'URL l'idcomune e l'id della posizione 4) invocare addPdf con l'id del debito e lo
     * stream che contiene i bytes del pdf restituito dal BO 5) restituire la stringa restituita da addPdf 6) lanciare
     * PayException nel caso di errori di comunicazione coi servizi o mancanza di parametri necessari al funzionamento
     * 
     * @param pos
     * @param idDebito
     * @return
     * @throws PayException
     */
    public PdfFile generaPdfDebito(String token, PayPosizioniDebitorie pos, String idDebito) throws PayException {

	if (StringUtils.isBlank(this.urlGeneraPdf)) {
	    throw new PayConfigurationException(
		    "Impossibile generare il pdf del debito perché il parametro DOCUMENTI_SERVICE non è configurato per il connettore NEXI");
	}
	Integer idPos = pos.getId().getCodice();
	//simulazione per test conn id valido TODO eliminare
	//idPos = 2400;
	WebClient client = WebClient.create(this.urlGeneraPdf)
		.path("{cf_ente_creditore}", PayConfigurationHelper.getProfiloEnteCreditore().getCfCodiceProfilo())
		.path("{id_posizione_debitoria}", idPos);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(20000);
	conduit.getClient().setReceiveTimeout(35000);
	client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_OCTET_STREAM);
	client.header("Authorization", token);
	Response response = null;
	try {
	    response = client.get();
	} catch (Exception e) {
	    String message = MessageFormat.format(
		    "Errore nella chiamata al servizio di download lettera di accompagnamento: {0} per la posizione {1}", e.getMessage(),
		    PkId.toStringId(pos.getId()));
	    log.error(message, e);
	    throw new PayException(message, e);
	}
	StatusType st = response.getStatusInfo();
	if (st.getFamily().equals(Family.SUCCESSFUL)) {
	    InputStream is = ((InputStream) response.getEntity());
	    return this.addPdf(idDebito, is);
	}
	// gestire redirect?
	/*
	 * else if(st.getFamily()..equals(Family.REDIRECTION)) {
	 * 
	 * }
	 */
	//errori di tipo 4xx tipo not found, unauthorized etc gestiti come errori temporanei ripristinabili, gli altri esiti sono gestiti come errori irreversibili
	else {
	    String message = MessageFormat.format(
		    "Errore HTTP {0} {1} nella chiamata al servizio di download lettera di accompagnamento per la posizione {2}", st.getStatusCode(),
		    st.getReasonPhrase(), PkId.toStringId(pos.getId()));
	    log.error("generaPdfDebito - " + message);
	    throw new PayException(message, true, st.getStatusCode() + "");
	}
    }

    /**
     * Riceve in input lo stream da cui leggere i dati di un file pdf, li copia in un file che viene aggiunto alla
     * cartella dei PDF da zippare e restituisce il nome del file generato secondo lo schema
     * <idLotto>-<idDebito>-debito.pdf
     * 
     * @param idDebito
     * @param pdfBytes
     * @throws PayException
     */
    public PdfFile addPdf(String idDebito, InputStream pdfBytes) throws PayException {

	if (StringUtils.isBlank(idDebito)) {
	    throw new PayException("per la generazione del documento di debito è necessario specificare id del debito ");
	}
	StringBuilder sb = new StringBuilder(idDebito).append(FILE_NAME_TYPE_SEPARATOR).append("debito.pdf");
	String pdfName = sb.toString();
	File pdfDir = new File(getTempDir(), PDF_SUBFOLDER);
	File pdf = new File(pdfDir, pdfName);
	try {
	    pdfDir.mkdirs();
	    pdf.createNewFile();
	    IOUtils.copyStreamToFile(pdfBytes, pdf);
	    log.debug("addPdf - determino il numero di pagine del file {}", pdf);
	    PDDocument doc = PDDocument.load(pdf);
	    int count = doc.getNumberOfPages();
	    log.debug("addPdf - il numero di pagine del file {} è {}", pdf, count);
	    try {
		doc.close();
	    } catch (Exception e) {
		log.error("addPdf - errore nella chiusura del doc", e);
	    }
	    return new PdfFile(pdfName, count);
	} catch (Exception ioe) {
	    String msg = "errore nella copia dei dati del pdf nella cartella da zippare. File di destinazione: " + pdf.getAbsolutePath();
	    log.error("addPdf - " + msg, ioe);
	    throw new PayException(msg, ioe);
	} finally {
	    if (pdfBytes != null) {
		try {
		    pdfBytes.close();
		} catch (IOException e) {
		    log.error("addPdf - chiusura pdfBytes ", e);
		}
	    }
	}
    }

    public void scriviTracciato(TracciatoRecordSet<?> recordSet, TipiDocumento tipoTracciato) throws PayException {

	String fileName = getFileName(tipoTracciato);
	File out = new File(getTempDir(), fileName);
	if (log.isDebugEnabled()) {
	    log.debug("scriviTracciato: scrittura del tracciato {} su file {} iniziata.", tipoTracciato.name(), out.getAbsolutePath());
	}
	try {
	    if (!out.exists()) {
		out.createNewFile();
	    }
	    recordSet.writeRecordsToFile(out);
	    if (log.isDebugEnabled()) {
		log.debug("scriviTracciato: scrittura del tracciato {} su file {} completata.", tipoTracciato.name(), out.getAbsolutePath());
	    }
	} catch (IOException e) {
	    StringBuilder sbErr = new StringBuilder().append("errore nella scrittura dei tracciati di tipo ").append(tipoTracciato.name());
	    sbErr.append(" nome file: ").append(fileName);
	    log.error(sbErr.toString(), e);
	    throw new PayException(sbErr.toString(), e);
	}
    }

    public File writeZippedPdfs() throws PayException {

	File pdfDir = new File(getTempDir(), PDF_SUBFOLDER);
	File zipFile = new File(getTempDir(), getFileName(TipiDocumento.PDF));
	try {
	    IOUtils.zipTo(pdfDir, FileUtils.openOutputStream(zipFile));
	} catch (IOException e) {
	    StringBuilder sbErr = new StringBuilder().append("errore nella scrittura del file zip per il lotto ").append(idLotto);
	    sbErr.append(" nome file: ").append(zipFile.getAbsolutePath());
	    log.error("writeZippedPdfs - " + sbErr.toString(), e);
	    throw new PayException(sbErr.toString(), e);
	}
	return zipFile;
    }

    public void inviaFlusso(TracciatoRecordSet<TracciatoRecordLotto> lotto, TracciatoRecordSet<TracciatoRecordDebito> debiti,
	    TracciatoRecordSet<TracciatoRecordRata> rate, TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni) throws PayException {

	if (this.fh == null)
	    throw new PayException("file helper non inizializzato, impossibile inviare il flusso");
	String err = null;
	if (lotto == null || lotto.getRecords().isEmpty()) {
	    err = "dati del lotto mancanti";
	} else if (debiti == null || debiti.getRecords().isEmpty()) {
	    err = "dati dei debiti mancanti";
	} else if (rate == null || rate.getRecords().isEmpty()) {
	    err = "dati delle rate mancanti";
	} else if (ripartizioni == null || ripartizioni.getRecords().isEmpty()) {
	    err = "dati delle ripartizioni mancanti";
	}
	if (err == null) {
	    try {
		String relativePath = "";
		this.scriviTracciato(lotto, TipiDocumento.LOTTO);
		this.scriviTracciato(debiti, TipiDocumento.DEBITO);
		this.scriviTracciato(rate, TipiDocumento.RATA);
		this.scriviTracciato(ripartizioni, TipiDocumento.RIPARTIZIONE);
		this.writeZippedPdfs();
		File sendMe = new File(getTempDir(), this.getFileName(TipiDocumento.LOTTO));
		this.fh.put(sendMe, relativePath);
		if (log.isInfoEnabled()) {
		    log.info("inviaFlusso - dati del lotto {} copiati su {}/{}", idLotto, this.endpointURL, relativePath);
		}
		sendMe = new File(getTempDir(), this.getFileName(TipiDocumento.DEBITO));
		this.fh.put(sendMe, relativePath);
		if (log.isInfoEnabled()) {
		    log.info("inviaFlusso - dati dei debiti del lotto {} copiati su {}/{}", idLotto, this.endpointURL, relativePath);
		}
		sendMe = new File(getTempDir(), this.getFileName(TipiDocumento.RATA));
		this.fh.put(sendMe, relativePath);
		if (log.isInfoEnabled()) {
		    log.info("inviaFlusso - dati delle rate del lotto {} copiati su {}/{}", idLotto, this.endpointURL, relativePath);
		}
		sendMe = new File(getTempDir(), this.getFileName(TipiDocumento.RIPARTIZIONE));
		this.fh.put(sendMe, relativePath);
		if (log.isInfoEnabled()) {
		    log.info("inviaFlusso - dati del lotto {} copiati su {}/{}", idLotto, this.endpointURL, relativePath);
		}
		sendMe = new File(getTempDir(), this.getFileName(TipiDocumento.PDF));
		this.fh.put(sendMe, relativePath);
		if (log.isInfoEnabled()) {
		    log.info("inviaFlusso - dati dei pdf del lotto {} copiati su {}/{}", idLotto, this.endpointURL, relativePath);
		}
		this.fh.close();
	    } catch (Exception e) {
		err = MessageFormat.format("Errore nell''invio del flusso {0}: {1}", this.idLotto, e);
		log.error("inviaFlusso - " + err, e);
		throw new PayException(err, e);
	    }
	} else {
	    throw new PayException(err);
	}
    }

    public Set<File> trovaFileCartellaEsiti() throws IOException {

	// esiti caricamento 
	// ==== cartella ==> Root\PG_<Nome Procedura Gestionale>\Esiti 
	// ===== nome file  MER_VAR_AVV_APPMER_38300000000000002082_CodiceAvviso_20221220152041.txt maschera ricerca file <TIPOLOGIA_ENTRATA>_*_CodiceAvviso_*.txt 
	StringBuilder sbFileNamePattern = new StringBuilder(this.tipologiaEntrata).append(FILE_NAME_TYPE_SEPARATOR).append("*");
	sbFileNamePattern.append(TipiDocumento.ESITO_CARICAMENTO.fileName()).append(FILE_NAME_TYPE_SEPARATOR).append("*.")
		.append(TipiDocumento.ESITO_CARICAMENTO.extension());
	log.info("trovaFileEsiti - cerco i file  {} nella folder {}", sbFileNamePattern.toString(), TipiVerifica.ESITO_CARICAMENTO.subFolderName());
	Set<File> filesEsiti = this.fh.mGetFilesByFilter(sbFileNamePattern.toString(), TipiVerifica.ESITO_CARICAMENTO.subFolderName());
	return filesEsiti;
    }

    public Set<File> trovaFileCartellaPagati() throws IOException {

	// notifiche pagamento
	// ==== cartella ==> Root\PG_<Nome Procedura Gestionale>\Pagati 
	// ===== nome file  MER_VAR_AVV_APPMER_NODO_Notifica_20230117105303.txt maschera ricerca file <TIPOLOGIA_ENTRATA>_*_Notifica_*.txt 
	StringBuilder sbFileNamePattern = new StringBuilder(this.tipologiaEntrata).append(FILE_NAME_TYPE_SEPARATOR).append("*");
	sbFileNamePattern.append(TipiDocumento.NOTIFICA_PAGAMENTO.fileName()).append(FILE_NAME_TYPE_SEPARATOR).append("*.")
		.append(TipiDocumento.NOTIFICA_PAGAMENTO.extension());
	log.info("trovaFileEsiti - cerco i file  {} nella folder {}", sbFileNamePattern.toString(),
		TipiVerifica.RENDICONTAZIONE_PAGAMENTI.subFolderName());
	Set<File> filesEsiti = this.fh.mGetFilesByFilter(sbFileNamePattern.toString(), TipiVerifica.RENDICONTAZIONE_PAGAMENTI.subFolderName());
	return filesEsiti;
    }

    public Set<File> trovaFileEsiti(boolean rendicontazionePagamenti) throws IOException {

	StringBuilder sbFileNamePattern = new StringBuilder(this.tipologiaEntrata).append(FILE_NAME_TYPE_SEPARATOR);
	if (!rendicontazionePagamenti) {
	    sbFileNamePattern.append(this.idLotto).append(FILE_NAME_TYPE_SEPARATOR);
	}
	TipiDocumento docEsito = rendicontazionePagamenti ? TipiDocumento.NOTIFICA_PAGAMENTO : TipiDocumento.ESITO_CARICAMENTO;
	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	sbFileNamePattern.append(docEsito.fileName()).append(FILE_NAME_TYPE_SEPARATOR).append("*.").append(docEsito.extension());
	log.info("trovaFileEsiti - cerco i file  {} nella folder {}", sbFileNamePattern.toString(), tipoVerifica.subFolderName());
	Set<File> filesEsiti = this.fh.mGetFilesByFilter(sbFileNamePattern.toString(), tipoVerifica.subFolderName());
	return filesEsiti;
    }

    public TracciatoRecordSet<TracciatoRecordEsiti> parseEsiti(String fileName, boolean rendicontazionePagamenti) throws PayException {

	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	if (this.fh == null)
	    throw new PayException("file helper non inizializzato, impossibile leggere gli esiti");
	//TipiDocumento tipoEsito = rendicontazionePagamenti ? TipiDocumento.NOTIFICA_PAGAMENTO : TipiDocumento.ESITO_CARICAMENTO;
	TracciatoRecordSet<TracciatoRecordEsiti> esiti = new TracciatoRecordSet<>();
	//per i files che contengono l'esito dei pagamenti non c'è l'id del lotto nel nome file e devono essere cercati tutti i files
	// che si chiamano <Tipologia Entrata>_ Notifica_yyyymmddHHMMSS.txt tenendo conto che la data può essere una qualunque
	//i files degli esiti dei caricamenti si chiamano <Tipologia Entrata>_<IdLotto>_CodiceAvviso_yyyymmddHHMMSS.txt 
	//tenendo conto che la data può essere una qualunque
	//
	String error = null;
	//inizializzazione del file helper
	//parsing del file degli esiti
	InputStream in = null;
	try {
	    in = this.fh.get(fileName, tipoVerifica.subFolderName());
	    esiti.readRecordsFromStream(in, TracciatoRecordEsiti.class);
	    if (log.isInfoEnabled()) {
		log.info("parseEsiti - completata lettura del file di esito {} dall'url {}, {} record letti con successo", fileName, this.endpointURL,
			esiti.getRecords().size());
	    }
	} catch (IOException e) {
	    error = MessageFormat.format("file degli esiti {0} non leggibile all'URL {1} : {2}", fileName, this.endpointURL, e.toString());
	    log.error("parseEsiti - " + error, e);
	    throw new PayException(error, e);
	} catch (TracciatoRecordException tre) {
	    error = MessageFormat.format("errore nel parsing dei tracciati degli esiti dal file {0} all'URL {1} : {2}", fileName, this.endpointURL,
		    tre.toString());
	    log.error("parseEsiti - " + error, tre);
	    throw new PayException(error, tre);
	} finally {
	    try {
		in.close();
	    } catch (IOException e) {
		//errore nella chiusura del flusso di lettura dei tracciati
		log.error("parseEsiti - errore nella chiusura del file che contiene i tracciati degli esiti {} all'URL {}", fileName,
			this.endpointURL);
	    }
	}
	return esiti;
    }

    /**
     * Restituisce gli idRata già processati che sono stati precedentemente salvati nello specifico file temporameno
     * 
     * @param fileName
     * @param rendicontazionePagamenti
     * @return
     * @throws PayException
     */
    public Set<String> getRateProcessate(String fileName, boolean rendicontazionePagamenti) {

	Set<String> infoRateProc = new HashSet<>();
	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	fileName = getNomeFileTemporaneoRateProcessate(fileName);
	InputStream isTmp = null;
	try {
	    isTmp = this.fh.get(fileName, tipoVerifica.subFolderName());
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    IOUtils.copyStream(isTmp, baos);
	    String rawData = baos.toString(IOUtils.DEFAULT_CHARSET);
	    infoRateProc.addAll(Arrays.asList(StringUtils.split(rawData, SEPARATORE_ID_RATE)));
	} catch (IOException e) {
	    //il file delle rate non è presente --> significa che non ne ho ancora processata nessuna e restituisco il set vuoto
	    log.info("getRateProcessate - il file delle rate già processate {} non è ancora presente nella cartella {}", fileName,
		    tipoVerifica.subFolderName());
	} finally {
	    if (isTmp != null) {
		try {
		    isTmp.close();
		} catch (IOException e) {
		    log.error("getRateProcessate - impossibile chiudere il flusso di lettura del file temporaneo delle rate già processate: {}",
			    fileName);
		}
	    }
	}
	return infoRateProc;
    }

    public void aggiornaRateProcessate(Set<String> idRate, String fileName, boolean rendicontazionePagamenti) {

	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	fileName = getNomeFileTemporaneoRateProcessate(fileName);
	String rowData = StringUtils.join(idRate, SEPARATORE_ID_RATE);
	try {
	    ByteArrayInputStream bais = new ByteArrayInputStream(rowData.getBytes(IOUtils.DEFAULT_CHARSET));
	    fh.put(bais, fileName, tipoVerifica.subFolderName());
	} catch (IOException e) {
	    log.error("aggiornaRateProcessate - errore nella scrittura delle rate processate nel file temporaneo " + fileName + " nella cartella " +
		      tipoVerifica.subFolderName(),
		    e);
	}
    }

    /**
     * spostamento del file di tracciato degli esiti nella cartella richiesta, cancellazione del file temporaneo che
     * contiene gli id delle rate già processate in precedenza e generazione del report di elaboarazione del tracciato
     * 
     * @param rendicontaz
     * @param scartato
     * @param recordLetti
     * @param reportMessage
     */
    public void spostaTracciatiEScriviReport(String fileName, boolean rendicontaz, boolean scartato, int recordLetti, String reportMessage) {

	TipiVerifica tipoVerifica = rendicontaz ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	CartelleScambioFiles copyDir = scartato ? CartelleScambioFiles.SCARTATI : CartelleScambioFiles.ELABORATI;
	String reportName = this.getFileName(TipiDocumento.REPORT);
	try {
	    //in caso di esito positivo il file del tracciato degli esiti viene spostato su Elaborati se no su Scartati
	    this.fh.sposta(fileName, tipoVerifica.subFolderName(), tipoVerifica.subFolderName() + File.separator + copyDir.fileName());
	    if (log.isInfoEnabled()) {
		log.info("spostaTracciatiEScriviReport - completato spostamento del file degli esiti {} nella cartella {}", fileName,
			copyDir.fileName());
	    }
	    //cancellazione del file temporaneo che contiene i dati delle rate già processate
	    String tmpFileName = this.getNomeFileTemporaneoRateProcessate(fileName);
	    this.fh.delete(tmpFileName, tipoVerifica.subFolderName());
	    if (log.isInfoEnabled()) {
		log.info("spostaTracciatiEScriviReport - completata cancellazione del file temporaneo delle rate processate {} dalla cartella {}",
			tmpFileName, tipoVerifica.subFolderName());
	    }
	} catch (IOException ioe2) {
	    // errore nella copia del tracciato nella cartella degli elaborati/scartati. 
	    String msg = MessageFormat.format("errore nella copia del file degli esiti {0} all'URL {1} nel path {2}. Errore: ", fileName.toString(),
		    this.endpointURL, copyDir.fileName(), ioe2.toString());
	    log.error("spostaTracciatiEScriviReport - " + msg, ioe2);
	}
	//scrittura del file di report dell'elaborazione del tracciato
	String reportText = MessageFormat.format(
		"Lettura dei tracciati degli esiti dal file {0} all'URL {1} completata con esito {2}. {3} righe importate. {4}", fileName.toString(),
		this.endpointURL, !scartato ? "positivo" : "negativo", recordLetti, StringUtils.defaultString(reportMessage));
	try {
	    this.writeReportElaborazioneTracciato(recordLetti, !scartato, reportName, reportText);
	} catch (IOException ioe2) {
	    // errore nella scrittura del report nella cartella Report. 
	    String msg = MessageFormat.format("errore nella scrittura del report degli esiti {0} all'URL {1} nel path /Reports", reportName,
		    this.endpointURL, ioe2.toString());
	    log.error("spostaTracciatiEScriviReport - " + msg, ioe2);
	}
    }

    private void inizializeFileNames() {

	StringBuilder sb = fileNameStringBuilder();
	for (TipiDocumento td : TipiDocumento.values()) {
	    StringBuilder sb2 = new StringBuilder(sb.toString()).append(td.fileName()).append(".").append(td.extension());
	    this.fileNamesMap.put(td, sb2.toString());
	}
	log.debug("fileNamesMap {}", new Object[] { fileNamesMap });
    }

    private StringBuilder fileNameStringBuilder() {

	StringBuilder sb = new StringBuilder(tipologiaEntrata).append(FILE_NAME_TYPE_SEPARATOR);
	if (StringUtils.isNotEmpty(this.idLotto)) {
	    sb.append(idLotto).append(FILE_NAME_TYPE_SEPARATOR);
	}
	if (this.dataGenerazione != null) {
	    DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	    sb.append(df.format(dataGenerazione)).append(FILE_NAME_TYPE_SEPARATOR);
	}
	return sb;
    }

    private File getTempDir() {

	File tmp = IOUtils.getSystemTempDir();
	tmp = new File(tmp, this.connectorId);
	tmp = new File(tmp, this.idLotto);
	if (!tmp.exists()) {
	    tmp.mkdirs();
	}
	return tmp;
    }

    private String getNomeFileTemporaneoRateProcessate(String fileName) {

	// i dati delle rate processate si trovano in un file temporaneo che ha lo stesso nome del file degli esiti ma con estensione .tmp anziché .txt
	return fileName.replace(".txt", ".tmp");
    }

    private void writeReportElaborazioneTracciato(int importedRecords, boolean esito, String inputFileName, String message) throws IOException {

	/*
	 * TODO scrivere il file di report per la lettura del file di tracciato con i dati passati in input, tenendo
	 * conto che il filehelper viene passato già inizializzato e connesso alla cartella di elaborazione
	 */
    }
}
