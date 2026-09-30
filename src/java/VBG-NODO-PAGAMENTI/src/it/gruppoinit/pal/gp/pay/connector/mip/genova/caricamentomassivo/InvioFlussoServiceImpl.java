package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.EnumMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.utils.FileHelperFactory;
import it.gruppoinit.pal.gp.core.utils.IFileHelper;
import it.gruppoinit.pal.gp.core.utils.IOUtils;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.BaseFolderCaricamento;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.TipiDocumento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.tracciati.TracciatoRecordSet;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordDebito;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordLotto;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRata;
import it.gruppoinit.pal.gp.pay.tracciati.nexi.genova.TracciatoRecordRipartizione;

@Service
public class InvioFlussoServiceImpl implements IInvioFlussoService {

    private static final Logger log = LoggerFactory.getLogger(InvioFlussoServiceImpl.class);
    private EnumMap<TipiDocumento, String> fileNamesMap = new EnumMap<>(TipiDocumento.class);
    private static final String PDF_SUBFOLDER = "pdf";

    @Override
    public void inviaFlusso(BaseFolderCaricamento config, TracciatoRecordSet<TracciatoRecordLotto> lotto,
	    TracciatoRecordSet<TracciatoRecordDebito> debiti, TracciatoRecordSet<TracciatoRecordRata> rate,
	    TracciatoRecordSet<TracciatoRecordRipartizione> ripartizioni, String connectorId, String idLotto) throws PayException {

	IFileHelper fh = null;
	try {
	    fh = FileHelperFactory.getFileHelper(config.getEndpointURL(), config.getEndpointUsername(), config.getEndpointPassword());
	} catch (IOException e) {
	    String msg = MessageFormat.format("errore nella creazione del servizio di scambio files alla URL {0} : {1}", config.getEndpointURL(),
		    e.toString());
	    log.error("NexiFileHelper() - " + msg, e);
	    throw new PayException(msg, e);
	}
	if (fh == null)
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
		this.scriviTracciato(lotto, TipiDocumento.LOTTO, connectorId, idLotto);
		this.scriviTracciato(debiti, TipiDocumento.DEBITO, connectorId, idLotto);
		this.scriviTracciato(rate, TipiDocumento.RATA, connectorId, idLotto);
		this.scriviTracciato(ripartizioni, TipiDocumento.RIPARTIZIONE, connectorId, idLotto);
		this.writeZippedPdfs(connectorId, idLotto);
		//2. Copio i file
		File tempPath = getTempDir(connectorId, idLotto);
		this.copiaFile(fh, tempPath, TipiDocumento.LOTTO);
		this.copiaFile(fh, tempPath, TipiDocumento.DEBITO);
		this.copiaFile(fh, tempPath, TipiDocumento.RATA);
		this.copiaFile(fh, tempPath, TipiDocumento.RIPARTIZIONE);
		this.copiaFile(fh, tempPath, TipiDocumento.PDF);
		//3. Fine
		fh.close();
	    } catch (Exception e) {
		err = MessageFormat.format("Errore nell''invio del flusso: {0}", e);
		log.error("inviaFlusso - " + err, e);
		throw new PayException(err, e);
	    }
	} else {
	    throw new PayException(err);
	}
    }

    private void scriviTracciato(TracciatoRecordSet<?> recordSet, TipiDocumento tipoTracciato, String connectorId, String idLotto)
	    throws PayException {

	File tempDir = getTempDir(connectorId, idLotto);
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

    private File writeZippedPdfs(String connectorId, String idLotto) throws PayException {

	File tempPath = getTempDir(connectorId, idLotto);
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

    private File getTempDir(String connectorId, String idLotto) {

	File tmp = IOUtils.getSystemTempDir();
	tmp = new File(tmp, connectorId);
	tmp = new File(tmp, idLotto);
	if (!tmp.exists()) {
	    tmp.mkdirs();
	}
	return tmp;
    }

    private void copiaFile(IFileHelper fh, File tempPath, TipiDocumento tipoDoc) throws IOException {

	File sendMe = new File(tempPath, this.fileNamesMap.get(tipoDoc));
	fh.put(sendMe);
	if (log.isInfoEnabled()) {
	    log.info("copiaFile - dati riguardanti {} copiati", tipoDoc.name());
	}
    }
}
