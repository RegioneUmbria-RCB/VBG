package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemError;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementazione di org.hibernate.jdbc.Work che si occupa di trasferire i documenti della tabella OGGETTI che si
 * trovano nel campo BLOB nelle sottodirectory del filesystem che vengono generate secondo la seguente regola: 1) PAD a
 * zeri a sinistra di CODICEOGGETTO fino a 10 caratteri (Es.: 12345 --> 0000012345) 2) substring dei primi 8 caratteri
 * del CODICEOGGETTO restituito al passo 1 (Es.: 0000012345 --> 00000123) 3) il path è dato da una directory che ha come
 * nome i primi 4 caratteri seguito da una sottodirectory definita dal quinto e sesto carattere e una altra
 * sottodirectory definita dagli ultimi due caratteri. In questo modo ognuna delle sottodirectory finali conterrà al
 * massimo 100 files. (Es.: 00000123 --> /0000/01/23) La sottodirectory in cui viene scritto il file viene infine
 * memorizzata nel campo PERCORSO. Se il flag setBlobNull è true il campo BLOB viene impostato a NULL dopo la scrittura
 * del documento sul filesystem.
 * 
 * @author francol
 * 
 */
public class OttimizzazioneOggettiFilesystemWork implements Work {

    private static final Logger activityLog = LoggerFactory.getLogger("it.gruppoinit.docfs.activity");
    private static final Logger exceptionLog = LoggerFactory.getLogger("it.gruppoinit.docfs.exception");
    private String docRootPath;
    OggettiFileSystemDAOImpl statusHandler;

    public OttimizzazioneOggettiFilesystemWork(String docRootPath, OggettiFileSystemDAOImpl statusHandler) {

	this.docRootPath = docRootPath;
	this.statusHandler = statusHandler;
    }

    @Override
    public void execute(Connection c) throws SQLException {

	// §§§BEGIN§§§
	if (activityLog.isInfoEnabled())
	    activityLog.info("OttimizzazioneOggettiFilesystemWork.execute() - Inizio procedura di trasferimento dei documenti BLOB su filesystem.");
	long totStart = new Date().getTime();
	StringBuilder query = new StringBuilder("SELECT CODICEOGGETTO, NOMEFILE, PERCORSO, DIMENSIONE_FILE FROM OGGETTI ");
	query.append("WHERE OGGETTO IS NULL AND PERCORSO IS NULL AND IDCOMUNE = ? ORDER BY CODICEOGGETTO DESC ");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = c.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	ps.setString(1, ORMHelper.getIdcomune());
	rs = ps.executeQuery();
	long totEnd = new Date().getTime();
	if (activityLog.isDebugEnabled())
	    activityLog.debug("OttimizzazioneOggettiFilesystemWork.execute() - Esecuzione della query su OGGETTI completata: tempo impiegato {} millisecondi", totEnd - totStart);
	long start = 0L;
	long end = 0L;
	long rowStart = 0L;
	long rowEnd = 0L;
	int processedCount = 0;
	while (rs.next()) {
	    OggettiFileSystemError retErr = null;
	    rowStart = new Date().getTime();
	    BigDecimal codiceOggetto = null;
	    File destFile = null;
	    long bytesCopied = 0L;
	    try {
		start = new Date().getTime();
		codiceOggetto = rs.getBigDecimal("CODICEOGGETTO");
		String nomeFile = rs.getString("NOMEFILE");
		File rootFile = new File(this.docRootPath, nomeFile);
		if (rootFile.isFile()) {
		    String destPath = Utilities.buildPathFromCodiceOggetto(codiceOggetto.intValue());
		    File destDir = new File(this.docRootPath, destPath);
		    boolean pathExists = destDir.exists();
		    if (!pathExists) {
			pathExists = destDir.mkdirs();
			if (!pathExists) {
			    retErr = statusHandler
				    .buildError(
					    codiceOggetto.intValue(),
					    "Errore di scrittura su file system. Impossibile creare la directory {0} per l''archiviazione del documento associato al codice oggetto {1}",
					    new Object[] { destDir.getPath(), codiceOggetto.intValue() });
			}
		    }
		    if (pathExists) {
			destFile = statusHandler.getDestinationFile(destDir, nomeFile, codiceOggetto.intValue());
			if (destDir.canWrite()) {
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled())
				activityLog
					.debug("OttimizzazioneOggettiFilesystemWork.execute() - Oggetto {}. Elaborazione dei nomi del file e delle directory terminata in {} millisecondi",
						new Object[] { codiceOggetto.intValue(), end - start });
			    start = end;
			    boolean doCopy = false;
			    boolean doUpdateRecord = false;
			    if (!destFile.exists()) {
				//try {
				    doCopy = true;
				    /*
				} catch (IOException e) {
				    retErr = statusHandler.buildError(codiceOggetto.intValue(),
					    "Errore di scrittura su file system. Impossibile creare il file {0} associato al codice oggetto {1}.",
					    new Object[] { destFile.getName(), codiceOggetto.intValue() });
				}
				*/
			    } else {
				try {
				    if(isFileModified(rootFile, destFile)){
					destFile.delete();
					doUpdateRecord = true;
					doCopy = true;
				    }
				} catch (Exception e) {
				    statusHandler.buildError(codiceOggetto.intValue(),
					    "Si è verificato un''errore durante la verifica delle modifiche al documento per il codice oggetto {0}.",
					    new Object[] { codiceOggetto.intValue() });
				}
				if (!(doCopy)) {
				    activityLog
					    .info("Il file di destinazione {} associato al codice oggetto {} esiste già e non risulta modificato. Il record della tabella OGGETTI sarà aggiornato ma il file esistente non sarà sovrascritto.",
						    new Object[] { destFile.getPath(), codiceOggetto.intValue() });
				} else {
				    activityLog
					    .info("Il file di destinazione {} associato al codice oggetto {} esiste già ma risulta modificato. Il file esistente sarà sovrascritto.",
						    new Object[] { destFile.getPath(), codiceOggetto.intValue() });
				}
			    }
			    if (doCopy) {
				/*
				FileInputStream fis = new FileInputStream(rootFile);
				statusHandler.writeBytesToFileChannel(fis, destFile);
				*/
				//rootFile.renameTo(destFile);
				doUpdateRecord = rootFile.renameTo(destFile);
				bytesCopied = destFile.length();
				if (rootFile.isFile()) {
				    rootFile.delete();
				}
			    }
			    if (doUpdateRecord) {
				end = new Date().getTime();
				if (activityLog.isDebugEnabled()) {
				    activityLog
					    .debug("OttimizzazioneOggettiFilesystemWork.execute() - Oggetto {}: spostamento del documento dalla root al percorso {} completato con successo. Tempo impiegato: {}",
						    new Object[] { codiceOggetto.intValue(), destFile.getPath(), end - start });
				}
				start = end;
				try {
				    if (!destFile.getName().equals(nomeFile)) {
					rs.updateString("NOMEFILE", destFile.getName());
				    }
				    rs.updateString("PERCORSO", destPath);
				    rs.updateRow();
				    c.commit();
				    statusHandler.getStatus().setCountMoved(statusHandler.getStatus().getCountMoved() + 1);
				    end = new Date().getTime();
				    if (activityLog.isDebugEnabled()) {
					activityLog.debug(
						"OttimizzazioneOggettiFilesystemWork.execute() - Oggetto {}. Aggiornamento del record nel DB completato in {} millisecondi",
						new Object[] { codiceOggetto.intValue(), end - start });
				    }
				    start = end;
				} catch (SQLException e) {
				    exceptionLog.error(
					    "OttimizzazioneOggettiFilesystemWork.execute() - Errore di scrittura nel database. Impossibile aggiornare il PERCORSO per l'oggetto "
						    + codiceOggetto, e);
				    destFile.renameTo(rootFile);
				    boolean restored = rootFile.isFile();
				    if (restored && destFile.isFile()) {
					destFile.delete();
				    }
				    if (restored) {
					retErr = statusHandler
						.buildError(
							codiceOggetto.intValue(),
							"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l''oggetto {0}. Il file {1} che era stato spostato in {2} è stato riportato alla posizione di partenza",
							new Object[] { codiceOggetto.intValue(), rootFile.getPath(), destFile.getPath() }, e);
				    } else {
					retErr = statusHandler
						.buildError(
							codiceOggetto.intValue(),
							"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l''oggetto {0}. Inoltre è fallito il ripristino della posizione originale del file. I dati nel DB non corrispondono alla effettiva posizione dei documenti su file system. Occorre spostare manualmente il file {1} in {2}.",
							new Object[] { codiceOggetto.intValue(), destFile.getPath(), rootFile.getPath() }, e);
				    }
				}
			    }
			} else {
			    retErr = statusHandler
				    .buildError(
					    codiceOggetto.intValue(),
					    "Errore di scrittura su file system. Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.",
					    new Object[] { destDir.getPath(), codiceOggetto.intValue() });
			}
		    }
		} else {
		    retErr = statusHandler.buildError(codiceOggetto.intValue(),
			    "Errore di lettura da file system. Il file {0} associato al codice oggetto {1} non esiste o è una directory.",
			    new Object[] { rootFile.getPath(), codiceOggetto.intValue() });
		}
	    } catch (Exception e) {
		Integer objCode = codiceOggetto != null ? codiceOggetto.intValue() : 0;
		retErr = statusHandler.buildError(objCode,
			"Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.", new Object[] { objCode });
	    }
	    processedCount++;
	    if (retErr == null) {
		rowEnd = new Date().getTime();
		activityLog.info("OttimizzazioneOggettiFilesystemWork.execute() - Elaborazione documento {} completata. {} bytes trasferiti nel file {}. Tempo impiegato {} ", new Object[] {
			codiceOggetto.intValue(), bytesCopied, destFile.getPath(), rowEnd - rowStart });
	    }
	    statusHandler.getStatus().setCountHandled(processedCount);
	    if (retErr != null) {
		statusHandler.getStatus().addError(retErr);
	    }
	}
	activityLog.info("OttimizzazioneOggettiFilesystemWork.execute() - Termine procedura di ottimizzazione dei documenti su filesystem. Elaborati {} documenti in {} millisecondi.", new Object[] {
		processedCount, totEnd - totStart });
	// §§§END§§§
    }

    private boolean isFileModified(File rootFile, File docFile) throws Exception {

	boolean retVal = true;
	// §§§BEGIN§§§
	String dbMD5 = statusHandler.computeMD5Hash(new FileInputStream(rootFile));
	String fsMD5 = statusHandler.computeMD5Hash(new FileInputStream(docFile));
	retVal = !dbMD5.equals(fsMD5);
	// §§§END§§§
	return retVal;
    }
}
