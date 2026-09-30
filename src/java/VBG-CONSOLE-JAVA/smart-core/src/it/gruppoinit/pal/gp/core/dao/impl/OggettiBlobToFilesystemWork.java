package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemError;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
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
public class OggettiBlobToFilesystemWork implements Work {

    private static final Logger activityLog = LoggerFactory.getLogger("it.gruppoinit.docfs.activity");
    private static final Logger exceptionLog = LoggerFactory.getLogger("it.gruppoinit.docfs.exception");
    //private static final int BUFFER_SIZE = 1024 * 512;
    private String docRootPath;
    private boolean setBlobNull;
    OggettiFileSystemDAOImpl statusHandler;

    //private OggettiFileSystemStatusBean status;
    public OggettiBlobToFilesystemWork(String docRootPath, boolean setBlobNull, OggettiFileSystemDAOImpl statusHandler) {

	this.docRootPath = docRootPath;
	this.setBlobNull = setBlobNull;
	this.statusHandler = statusHandler;
    }

    @Override
    public void execute(Connection c) throws SQLException {

	// §§§BEGIN§§§
	if (activityLog.isInfoEnabled())
	    activityLog.info("Inizio procedura di trasferimento dei documenti BLOB su filesystem.");
	long totStart = new Date().getTime();
	StringBuilder query = new StringBuilder("SELECT IDCOMUNE, CODICEOGGETTO, NOMEFILE, PERCORSO, DIMENSIONE_FILE, OGGETTO FROM OGGETTI ");
	query.append("WHERE OGGETTO IS NOT NULL AND PERCORSO IS NULL AND IDCOMUNE = ? ORDER BY CODICEOGGETTO DESC ");
	PreparedStatement ps = null;
	ResultSet rs = null;
	ps = c.prepareStatement(query.toString(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	ps.setString(1, ORMHelper.getIdcomune());
	rs = ps.executeQuery();
	long totEnd = new Date().getTime();
	if (activityLog.isDebugEnabled())
	    activityLog.debug("Esecuzione della query su OGGETTI completata: tempo impiegato {} millisecondi", totEnd - totStart);
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
		if (activityLog.isInfoEnabled())
		    activityLog.info("Inizio elaborazione ogetto. Codice = {}", codiceOggetto);
		String nomeFile = rs.getString("NOMEFILE");
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
			activityLog.debug(
				"handleBlobDocument() - Oggetto {}. Elaborazione dei nomi del file e delle directory terminata in {} millisecondi",
				new Object[] { codiceOggetto.intValue(), end - start });
			start = end;
			Blob blob = rs.getBlob("OGGETTO");
			end = new Date().getTime();
			activityLog.debug("handleBlobDocument() - Oggetto {}. Recupero del Blob dal ResultSet eseguita in {} millisecondi",
				new Object[] { codiceOggetto.intValue(), end - start });
			start = end;
			boolean doCopy = false;
			if (!destFile.exists()) {
			    try {
				doCopy = destFile.createNewFile();
			    } catch (IOException e) {
				retErr = statusHandler.buildError(codiceOggetto.intValue(),
					"Errore di scrittura su file system. Impossibile creare il file {0} associato al codice oggetto {1}.",
					new Object[] { destFile.getName(), codiceOggetto.intValue() });
			    }
			} else {
			    try {
				doCopy = isDocumentModified(blob, destFile);
			    } catch (Exception e) {
				statusHandler.buildError(codiceOggetto.intValue(),
					"Si è verificato un''errore durante la verifica delle modifiche al documento per il codice oggetto {0}.",
					new Object[] { codiceOggetto.intValue() });
			    }
			    if (!(doCopy)) {
				activityLog
					.info("Il file di destinazione {} associato al codice oggetto {} esiste già e non risulta modificato. Il rcord della tabella OGGETTI sarà aggiornato ma file esistente non sarà sovrascritto.",
						new Object[] { destFile.getPath(), codiceOggetto.intValue() });
			    } else {
				activityLog
					.info("Il file di destinazione {} associato al codice oggetto {} esiste già ma risulta modificato. Il file esistente sarà sovrascritto.",
						new Object[] { destFile.getPath(), codiceOggetto.intValue() });
			    }
			}
			if (doCopy) {
			    byte[] blobData = blob.getBytes(1L, (int) blob.length());
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled())
				activityLog
					.debug("Recuperati {} bytes di dati dal campo BLOB del documento avente codice {}. Tempo impiegato {} millisecondi.",
						new Object[] { blobData.length, codiceOggetto.intValue(), end - start });
			    start = end;
			    try {
				statusHandler.writeBytesToFileChannel(blob.getBinaryStream(), destFile);
			    } catch (FileNotFoundException e) {
				retErr = statusHandler
					.buildError(
						codiceOggetto.intValue(),
						"Errore di scrittura su file system. Il file di destinazione {0} associato al codice oggetto {1} non esiste.",
						new Object[] { destFile.getPath(), codiceOggetto.intValue() }, e);
			    } catch (IOException e) {
				retErr = statusHandler.buildError(codiceOggetto.intValue(),
					"Errore di scrittura su file system durante per il file {0} associato al codice oggetto {1}.", new Object[] {
						destFile.getPath(), codiceOggetto.intValue() }, e);
			    }
			    bytesCopied = blobData.length;
			    //retErr = copyDocumentToFile(blob.getBinaryStream(), destFile, codiceOggetto.intValue());
			}
			boolean updateRecord = retErr == null;
			if (updateRecord) {
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled()) {
				activityLog
					.debug("handleBlobDocument() - Oggetto {}: spostamento del documento dal campo BLOB al file {} completato con successo. Tempo impiegato: {}",
						new Object[] { codiceOggetto.intValue(), destFile.getPath(), end - start });
			    }
			    start = end;
			    try {
				if (setBlobNull) {
				    rs.updateNull("OGGETTO");
				}
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
					    "handleBlobDocument() - Oggetto {}. Aggiornamento del record nel DB completato in {} millisecondi",
					    new Object[] { codiceOggetto.intValue(), end - start });
				}
				start = end;
			    } catch (SQLException e) {
				exceptionLog.error(
					"handleBlobDocument() - Errore di scrittura nel database. Impossibile aggiornare il PERCORSO per l'oggetto "
						+ codiceOggetto, e);
				//boolean restored = destFile.delete();
				retErr = statusHandler
					.buildError(
						codiceOggetto.intValue(),
						"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l''oggetto {0}. Il file creato: {1} risulta inutile e dovrà essere cancellato a mano.",
						new Object[] { codiceOggetto.intValue(), destFile.getPath() }, e);
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
	    } catch (Exception e) {
		Integer objCode = codiceOggetto != null ? codiceOggetto.intValue() : 0;
		retErr = statusHandler.buildError(objCode,
			"Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.", new Object[] { objCode });
	    }
	    processedCount++;
	    rowEnd = new Date().getTime();
	    activityLog.info("Elaborazione documento {} completata. {} bytes trasferiti nel file {}. Tempo impiegato {}", new Object[] {
		    codiceOggetto.intValue(), bytesCopied, destFile.getPath(), rowEnd - rowStart });
	    statusHandler.getStatus().setCountHandled(processedCount);
	    if (retErr != null) {
		statusHandler.getStatus().addError(retErr);
	    }
	}
	activityLog.info("Termine procedura di trasferimento dei documenti BLOB su filesystem. Elaborati {} documenti in {} millisecondi.",
		new Object[] { processedCount, totEnd - totStart });
	// §§§END§§§
    }

    private OggettiFileSystemError copyDocumentToFile(InputStream in, File destFile, Integer codiceOggetto) {

	// §§§BEGIN§§§
	OggettiFileSystemError retErr = null;
	FileOutputStream out = null;
	BufferedOutputStream bout = null;
	try {
	    out = new FileOutputStream(destFile);
	    bout = new BufferedOutputStream(out);
	    byte[] buffer = new byte[OggettiFileSystemDAOImpl.BUFFER_SIZE];
	    int bytesRead = 0;
	    while ((bytesRead = in.read(buffer)) > 0) {
		bout.write(buffer, 0, bytesRead);
	    }
	    bout.flush();
	} catch (FileNotFoundException e) {
	    retErr = statusHandler.buildError(codiceOggetto,
		    "Errore di scrittura su file system. Il file di destinazione {} associato al codice oggetto {} non esiste.", new Object[] {
			    destFile.getPath(), codiceOggetto }, e);
	} catch (IOException e) {
	    retErr = statusHandler.buildError(codiceOggetto,
		    "Errore di scrittura su file system durante la scrittura dell file {} associato al codice oggetto {}.",
		    new Object[] { destFile.getPath(), codiceOggetto }, e);
	} finally {
	    try {
		in.close();
		if (bout != null) {
		    bout.close();
		}
	    } catch (IOException e1) {
		exceptionLog.error(
			"Errore I/O nella chiusura del flusso in scrittura sul file {}. ATTENZIONE!!! Il file potrebbe essere rimasto lockato.",
			destFile.getPath(), e1);
	    }
	}
	return retErr;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private boolean isDocumentModified(Blob docBlob, File docFile) throws Exception {

	boolean retVal = true;
	// §§§BEGIN§§§
	String dbMD5 = statusHandler.computeMD5Hash(docBlob.getBinaryStream());
	String fsMD5 = statusHandler.computeMD5Hash(new FileInputStream(docFile));
	retVal = !dbMD5.equals(fsMD5);
	// §§§END§§§
	return retVal;
    }
}
