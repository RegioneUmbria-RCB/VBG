package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.utils.FileHelperFactory;
import it.gruppoinit.pal.gp.core.utils.IFileHelper;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordException;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordEsiti;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;

public class MipFileHelper {

    private EnumMap<TipiDocumento, String> fileNamesMap = new EnumMap<>(TipiDocumento.class);
    private String tipologiaEntrata = null;
    private static final String FILE_NAME_TYPE_SEPARATOR = "_";
    private static final String DATE_FORMAT = "yyyyMMddhhmmss";
    private static final String PDF_SUBFOLDER = "pdf";
    private static final char SEPARATORE_ID_RATE = ' ';
    private static final Logger log = LoggerFactory.getLogger(MipFileHelper.class);
    private String idLotto = null;
    private Date dataGenerazione = null;
    private IFileHelper fh;
    private String urlGeneraPdf = null;
    private String connectorId = null;

    public Date getDataGenerazione() {

	return dataGenerazione;
    }

    public String getConnectorId() {

	return connectorId;
    }

    public MipFileHelper(String tipologiaEntrata, BaseFolderCaricamento config, String urlGeneraPdf, String idLotto) throws PayException {

	this.idLotto = idLotto;
	this.tipologiaEntrata = tipologiaEntrata;
	this.urlGeneraPdf = urlGeneraPdf;
	this.dataGenerazione = new Date();
	PayProfiliEntiCreditori enteCorrente = PayConfigurationHelper.getProfiloEnteCreditore();
	if (enteCorrente == null) {
	    this.connectorId = "NEXI";
	} else {
	    this.connectorId = enteCorrente.getPayConnector().getCodice();
	}
	this.inizializeFileNames();
	try {
	    this.fh = FileHelperFactory.getFileHelper(config.getEndpointURL(), config.getEndpointUsername(), config.getEndpointPassword());
	} catch (IOException e) {
	    String msg = MessageFormat.format("errore nella creazione del servizio di scambio files alla URL {0} : {1}", config.getEndpointURL(),
		    e.toString());
	    log.error("NexiFileHelper() - " + msg, e);
	    throw new PayException(msg, e);
	}
    }

    public MipFileHelper(String tipologiaEntrata, BaseFolderCaricamento config) throws PayException {

	this(tipologiaEntrata, config, null, null);
    }

    public Set<File> trovaFileCartellaPagati() throws IOException {

	StringBuilder sbFileNamePattern = new StringBuilder(this.tipologiaEntrata) //
		.append(FILE_NAME_TYPE_SEPARATOR) //
		.append("*") //
		.append(TipiDocumento.NOTIFICA_PAGAMENTO.fileName()) //
		.append(FILE_NAME_TYPE_SEPARATOR) //
		.append("*.") //
		.append(TipiDocumento.NOTIFICA_PAGAMENTO.extension());
	log.info("trovaFileEsiti - cerco i file  {} nella folder {}", sbFileNamePattern, TipiVerifica.RENDICONTAZIONE_PAGAMENTI.subFolderName());
	return this.fh.mGetFilesByFilter(sbFileNamePattern.toString(), TipiVerifica.RENDICONTAZIONE_PAGAMENTI.subFolderName());
    }

    public Set<File> trovaFileCartellaEsiti() throws IOException {

	StringBuilder sbFileNamePattern = new StringBuilder(this.tipologiaEntrata) // +
		.append(FILE_NAME_TYPE_SEPARATOR) //
		.append("*") //
		.append(TipiDocumento.ESITO_CARICAMENTO.fileName()) //
		.append(FILE_NAME_TYPE_SEPARATOR).append("*.") //
		.append(TipiDocumento.ESITO_CARICAMENTO.extension());
	log.info("trovaFileEsiti - cerco i file  {} nella folder {}", sbFileNamePattern, TipiVerifica.ESITO_CARICAMENTO.subFolderName());
	return this.fh.mGetFilesByFilter(sbFileNamePattern.toString(), TipiVerifica.ESITO_CARICAMENTO.subFolderName());
    }

    public TracciatoRecordSet<TracciatoRecordEsiti> parseEsiti(String fileName, boolean rendicontazionePagamenti) throws PayException {

	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	if (this.fh == null)
	    throw new PayException("file helper non inizializzato, impossibile leggere gli esiti");
	TracciatoRecordSet<TracciatoRecordEsiti> esiti = new TracciatoRecordSet<>();
	//per i files che contengono l'esito dei pagamenti non c'è l'id del lotto nel nome file e devono essere cercati tutti i files
	// che si chiamano <Tipologia Entrata>_ Notifica_yyyymmddHHMMSS.txt tenendo conto che la data può essere una qualunque
	//i files degli esiti dei caricamenti si chiamano <Tipologia Entrata>_<IdLotto>_CodiceAvviso_yyyymmddHHMMSS.txt 
	//tenendo conto che la data può essere una qualunque
	//
	String error = null;
	//inizializzazione del file helper
	//parsing del file degli esiti
	try (InputStream in = this.fh.get(fileName, tipoVerifica.subFolderName())) {
	    esiti.readRecordsFromStream(in, TracciatoRecordEsiti.class);
	    if (log.isInfoEnabled()) {
		log.info("parseEsiti - completata lettura del file di esito {},  {} record letti con successo", fileName, esiti.getRecords().size());
	    }
	} catch (IOException e) {
	    error = MessageFormat.format("file degli esiti {0} non leggibile  : {1}", fileName, e.toString());
	    log.error("parseEsiti - " + error, e);
	    throw new PayException(error, e);
	} catch (TracciatoRecordException tre) {
	    error = MessageFormat.format("errore nel parsing dei tracciati degli esiti dal file {0}: {1}", fileName, tre.toString());
	    log.error("parseEsiti - " + error, tre);
	    throw new PayException(error, tre);
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
	fileName = fileName.replace(".txt", ".tmp");
	try (InputStream isTmp = this.fh.get(fileName, tipoVerifica.subFolderName())) {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    IOUtils.copyStream(isTmp, baos);
	    String rawData = baos.toString(IOUtils.DEFAULT_CHARSET);
	    infoRateProc.addAll(Arrays.asList(StringUtils.split(rawData, SEPARATORE_ID_RATE)));
	} catch (IOException e) {
	    //il file delle rate non è presente --> significa che non ne ho ancora processata nessuna e restituisco il set vuoto
	    log.info("getRateProcessate - il file delle rate già processate {} non è ancora presente nella cartella {}", fileName,
		    tipoVerifica.subFolderName());
	}
	return infoRateProc;
    }

    /**
     * spostamento del file di tracciato degli esiti nella cartella richiesta, cancellazione del file temporaneo che
     * contiene gli id delle rate già processate in precedenza e generazione del report di elaboarazione del tracciato
     * 
     * @param rendicontaz
     * @param scartato
     */
    public void spostaTracciatiEScriviReport(String fileName, boolean rendicontaz, boolean scartato) {

	TipiVerifica tipoVerifica = rendicontaz ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	CartelleScambioFiles copyDir = scartato ? CartelleScambioFiles.SCARTATI : CartelleScambioFiles.ELABORATI;
	try {
	    //in caso di esito positivo il file del tracciato degli esiti viene spostato su Elaborati se no su Scartati
	    this.fh.sposta(fileName, tipoVerifica.subFolderName(), tipoVerifica.subFolderName() + File.separator + copyDir.fileName());
	    if (log.isInfoEnabled()) {
		log.info("spostaTracciatiEScriviReport - completato spostamento del file degli esiti {} nella cartella {}", fileName,
			copyDir.fileName());
	    }
	    //cancellazione del file temporaneo che contiene i dati delle rate già processate
	    String tmpFileName = fileName.replace(".txt", ".tmp");
	    this.fh.delete(tmpFileName, tipoVerifica.subFolderName());
	    if (log.isInfoEnabled()) {
		log.info("spostaTracciatiEScriviReport - completata cancellazione del file temporaneo delle rate processate {} dalla cartella {}",
			tmpFileName, tipoVerifica.subFolderName());
	    }
	} catch (IOException ioe2) {
	    // errore nella copia del tracciato nella cartella degli elaborati/scartati. 
	    String msg = MessageFormat.format("errore nella copia del file degli esiti {0} nel path {1}. Errore: {2}", fileName, copyDir.fileName(),
		    ioe2.toString());
	    log.error("spostaTracciatiEScriviReport - " + msg, ioe2);
	}
    }

    public void aggiornaRateProcessate(Set<String> idRate, String fileName, boolean rendicontazionePagamenti) {

	TipiVerifica tipoVerifica = rendicontazionePagamenti ? TipiVerifica.RENDICONTAZIONE_PAGAMENTI : TipiVerifica.ESITO_CARICAMENTO;
	fileName = fileName.replace(".txt", ".tmp");
	String rowData = StringUtils.join(idRate, SEPARATORE_ID_RATE);
	try {
	    ByteArrayInputStream bais = new ByteArrayInputStream(rowData.getBytes(StandardCharsets.UTF_8));
	    fh.put(bais, fileName, tipoVerifica.subFolderName());
	} catch (IOException e) {
	    log.error("aggiornaRateProcessate - errore nella scrittura delle rate processate nel file temporaneo " + fileName + " nella cartella " +
		      tipoVerifica.subFolderName(),
		    e);
	}
    }

    private void inviaFlusso(TracciatoRecordSet<TracciatoRecordLotto> lotto, TracciatoRecordSet<TracciatoRecordDebito> debiti,
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
		//1. Scrivo i file
		this.scriviTracciato(lotto, TipiDocumento.LOTTO);
		this.scriviTracciato(debiti, TipiDocumento.DEBITO);
		this.scriviTracciato(rate, TipiDocumento.RATA);
		this.scriviTracciato(ripartizioni, TipiDocumento.RIPARTIZIONE);
		this.writeZippedPdfs();
		//2. Copio i file
		File tempPath = getTempDir();
		this.copiaFile(tempPath, TipiDocumento.LOTTO);
		this.copiaFile(tempPath, TipiDocumento.DEBITO);
		this.copiaFile(tempPath, TipiDocumento.RATA);
		this.copiaFile(tempPath, TipiDocumento.RIPARTIZIONE);
		this.copiaFile(tempPath, TipiDocumento.PDF);
		//3. Fine
		this.fh.close();
	    } catch (Exception e) {
		err = MessageFormat.format("Errore nell''invio del flusso: {0}", e);
		log.error("inviaFlusso - " + err, e);
		throw new PayException(err, e);
	    }
	} else {
	    throw new PayException(err);
	}
    }

    private void copiaFile(File tempPath, TipiDocumento tipoDoc) throws IOException {

	File sendMe = new File(tempPath, this.fileNamesMap.get(tipoDoc));
	this.fh.put(sendMe);
	if (log.isInfoEnabled()) {
	    log.info("inviaFlusso - dati riguardanti {} copiati", tipoDoc.name());
	}
    }

    private File writeZippedPdfs() throws PayException {

	File tempPath = getTempDir();
	File pdfDir = new File(tempPath, PDF_SUBFOLDER);
	File zipFile = new File(tempPath, this.fileNamesMap.get(TipiDocumento.PDF));
	try {
	    IOUtils.zipTo(pdfDir, FileUtils.openOutputStream(zipFile));
	} catch (IOException e) {
	    StringBuilder sbErr = new StringBuilder().append("errore nella scrittura del file zip ");
	    sbErr.append(" nome file: ").append(zipFile.getAbsolutePath());
	    log.error("writeZippedPdfs - " + sbErr.toString(), e);
	    throw new PayException(sbErr.toString(), e);
	}
	return zipFile;
    }

    private void scriviTracciato(TracciatoRecordSet<?> recordSet, TipiDocumento tipoTracciato) throws PayException {

	File tempDir = getTempDir();
	String fileName = this.fileNamesMap.get(tipoTracciato);
	File out = new File(tempDir, fileName);
	if (log.isDebugEnabled()) {
	    log.debug("scriviTracciato: scrittura del tracciato {} su file {} iniziata.", tipoTracciato.name(), out.getAbsolutePath());
	}
	try {
	    if (!out.exists() && !out.createNewFile()) {
		throw new RuntimeException("Impossibile creare il file " + fileName + " nella cartella " + tempDir.toPath().toString());
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

    /**
     * Riceve in input lo stream da cui leggere i dati di un file pdf, li copia in un file che viene aggiunto alla
     * cartella dei PDF da zippare e restituisce il nome del file generato secondo lo schema
     * <idLotto>-<idDebito>-debito.pdf
     * 
     * @param idDebito
     * @param pdfBytes
     * @throws PayException
     */
    private PdfFile addPdf(String idDebito, InputStream pdfBytes) throws PayException {

	if (StringUtils.isBlank(idDebito)) {
	    throw new PayException("per la generazione del documento di debito è necessario specificare id del debito ");
	}
	StringBuilder sb = new StringBuilder(idDebito).append(FILE_NAME_TYPE_SEPARATOR).append("debito.pdf");
	String pdfName = sb.toString();
	File pdfDir = new File(getTempDir(), PDF_SUBFOLDER);
	File pdf = new File(pdfDir, pdfName);
	try {
	    pdfDir.mkdirs();
	    if (!pdf.createNewFile()) {
		throw new RuntimeException("Impossibile creare il file " + pdfName + " nella cartella " + pdfDir);
	    }
	    IOUtils.copyStreamToFile(pdfBytes, pdf);
	    log.debug("addPdf - determino il numero di pagine del file {}", pdf);
	    PDDocument doc = PDDocument.load(pdf);
	    int count = doc.getNumberOfPages();
	    log.debug("addPdf - il numero di pagine del file {} è {}", pdf, count);
	    this.closePdf(doc);
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

    private void closePdf(PDDocument doc) {

	try {
	    doc.close();
	} catch (Exception e) {
	    log.error("closePdf - errore nella chiusura del doc", e);
	}
    }

    private File getTempDir() {

	File tmp = IOUtils.getSystemTempDir();
	tmp = new File(tmp, this.connectorId);
	tmp = new File(tmp, this.idLotto); //Timestamp condiviso 
	if (!tmp.exists()) {
	    tmp.mkdirs();
	}
	return tmp;
    }

    private void inizializeFileNames() {

	StringBuilder sb = fileNameStringBuilder();
	for (TipiDocumento td : TipiDocumento.values()) {
	    StringBuilder sb2 = new StringBuilder(sb.toString()).append(td.fileName()).append(".").append(td.extension());
	    this.fileNamesMap.put(td, sb2.toString());
	}
	log.debug("fileNamesMap {}", fileNamesMap);
    }

    private StringBuilder fileNameStringBuilder() {

	StringBuilder sb = new StringBuilder(tipologiaEntrata).append(FILE_NAME_TYPE_SEPARATOR);
	sb.append(this.idLotto).append(FILE_NAME_TYPE_SEPARATOR);
	if (this.dataGenerazione != null) {
	    DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	    sb.append(df.format(dataGenerazione)).append(FILE_NAME_TYPE_SEPARATOR);
	}
	return sb;
    }
}
