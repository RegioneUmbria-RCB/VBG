package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.OggettiFileSystemDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemError;
import it.gruppoinit.pal.gp.core.domain.web.OggettiFileSystemStatusBean;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.IntegerType;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OggettiFileSystemDAOImpl extends BaseDAOImpl<Oggetti, PkId> implements OggettiFileSystemDAO {

    /**
     * @return the status
     */
    public OggettiFileSystemStatusBean getStatus() {

	return status;
    }

    private static final Logger activityLog = LoggerFactory.getLogger("it.gruppoinit.docfs.activity");
    private static final Logger errorLog = LoggerFactory.getLogger("it.gruppoinit.docfs.error");
    private static final Logger exceptionLog = LoggerFactory.getLogger("it.gruppoinit.docfs.exception");
    public static final int BUFFER_SIZE = 1024 * 512;
    /*
     * l'oggetto status viene memorizzato come variabile membro perchè in ogni caso non è possibile lanciare più di una procedura per volta
     */
    private OggettiFileSystemStatusBean status;

    @Override
    public int ottimizzaFileSystem(String rootPath, OggettiFileSystemStatusBean status) {

	// §§§BEGIN§§§
	this.status = status;
	//log.trace("ottimizzaFileSystem() - inizio procedura di ottimizzazione dei documenti su filesystem.");
	OttimizzazioneOggettiFilesystemWork procedure = new OttimizzazioneOggettiFilesystemWork(rootPath, this);
	getSession().doWork(procedure);
	return status.getCountMoved();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return 0;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int spostaBlobSuFileSystem(String rootPath, boolean setBlobNull, int maxDocs, int maxMinutes, OggettiFileSystemStatusBean status,
	    Integer minCodiceOggetto) {

	// §§§BEGIN§§§
	long globalStart = new Date().getTime();
	this.status = status;
	int processed = 0;
	StatelessSession session = getSessionFactory().openStatelessSession();
	SQLQuery sql = session
		.createSQLQuery("select min(codiceoggetto) as minimo, max(codiceoggetto) as massimo from oggetti where idcomune = :idcomune");
	sql.addScalar("minimo", new IntegerType());
	sql.addScalar("massimo", new IntegerType());
	sql.setReadOnly(true);
	sql.setFetchSize(1);
	sql.setParameter("idcomune", ORMHelper.getIdcomune());
	ScrollableResults srMaster = sql.scroll(ScrollMode.FORWARD_ONLY);
	//Iterator<BigDecimal> resultsIter = sql.iterate();
	int minimo = 0;
	int massimo = 0;
	if (srMaster.next()) {
	    minimo = srMaster.getInteger(0);
	    massimo = srMaster.getInteger(1);
	}
	if (minCodiceOggetto != null) {
	    minimo = minCodiceOggetto.intValue();
	}
	status.setMinCodiceOggetto(minimo);
	status.setCountTotal(massimo);
	srMaster.close();
	File destFile = null;
	long start = 0L;
	long end = 0L;
	long rowStart = 0L;
	long rowEnd = 0L;
	long bytesCopied = 0L;
	if (maxMinutes < 1) {
	    maxMinutes = 24 * 60;
	}
	long maxMillis = maxMinutes * 60 * 1000;
	long millisElapsed = 0L;
	for (int codiceOggetto = minimo; codiceOggetto <= massimo; codiceOggetto++) {
	    if (!status.isRunning()) { // se da interfaccia la stoppano esco
		break;
	    }
	    start = new Date().getTime();
	    rowStart = start;
	    OggettiFileSystemError retErr = null;
	    Oggetti obj = findById(new PkId(codiceOggetto));
	    status.setCodiceOggettoCorrente(codiceOggetto);
	    if (obj != null && obj.getNonUsareContenutoBLOB() != null) {
		end = new Date().getTime();
		if (activityLog.isDebugEnabled())
		    activityLog.debug("spostaBlobSuFileSystem() - Oggetto {}. Recupero del record terminato in {} millisecondi", new Object[] {
			    codiceOggetto, end - start });
		start = end;
		try {
		    if (activityLog.isInfoEnabled())
			activityLog.info("Inizio elaborazione ogetto. Codice = {}", codiceOggetto);
		    String nomeFile = obj.getNomefile();
		    String destPath = Utilities.buildPathFromCodiceOggetto(codiceOggetto);
		    File destDir = new File(rootPath, destPath);
		    boolean pathExists = destDir.exists();
		    if (!pathExists) {
			pathExists = destDir.mkdirs();
			if (!pathExists) {
			    retErr = buildError(
				    codiceOggetto,
				    "Errore di scrittura su file system. Impossibile creare la directory {0} per l''archiviazione del documento associato al codice oggetto {1}",
				    new Object[] { destDir.getPath(), codiceOggetto });
			}
		    }
		    if (pathExists) {
			destFile = getDestinationFile(destDir, nomeFile, codiceOggetto);
			if (destDir.canWrite()) {
			    boolean doCopy = false;
			    if (!destFile.exists()) {
				try {
				    doCopy = destFile.createNewFile();
				} catch (IOException e) {
				    retErr = buildError(codiceOggetto,
					    "Errore di scrittura su file system. Impossibile creare il file {0} associato al codice oggetto {1}.",
					    new Object[] { destFile.getName(), codiceOggetto });
				}
			    } else {
				try {
				    /*
				     * se era già presente il file di destinazione, il vecchio codice verificava se il file aveva contenuti diversi 
				     * confrontando gli MD5 calcolati dal contenuto del BLOB e dal contenuto del file. 
				     * Il file veniva sovrascritto con il contenuto del BLOB solo se c'erano delle differenze.
				     * Si ritiene che sia più performante effettuare la copia in ogni caso, senza contare che 
				     * non si dovrebbe mai entrare in quersto if, a meno che qualcuno non vada direttamente dal DB
				     *  a impostare a null il campo oggetti.percorso.
				     */
				    //doCopy = isDocumentModified(data, destFile);
				    doCopy = true;
				} catch (Exception e) {
				    buildError(codiceOggetto,
					    "Si è verificato un''errore durante la verifica delle modifiche al documento per il codice oggetto {0}.",
					    new Object[] { codiceOggetto });
				}
				if (!(doCopy)) {
				    activityLog
					    .info("Il file di destinazione {} associato al codice oggetto {} esiste già e non risulta modificato. Il rcord della tabella OGGETTI sarà aggiornato ma file esistente non sarà sovrascritto.",
						    new Object[] { destFile.getPath(), codiceOggetto });
				} else {
				    activityLog
					    .info("Il file di destinazione {} associato al codice oggetto {} esiste già ma risulta modificato. Il file esistente sarà sovrascritto.",
						    new Object[] { destFile.getPath(), codiceOggetto });
				}
			    }
			    end = new Date().getTime();
			    activityLog
				    .debug("handleBlobDocument() - Oggetto {}. Elaborazione dei nomi del file e delle directory terminata in {} millisecondi",
					    new Object[] { codiceOggetto, end - start });
			    start = end;
			    if (doCopy) {
				byte[] data = obj.getNonUsareContenutoBLOB();
				end = new Date().getTime();
				activityLog.debug("handleBlobDocument() - Oggetto {}. Recupero del Blob dal database eseguita in {} millisecondi",
					new Object[] { codiceOggetto, end - start });
				start = end;
				try {
				    ByteArrayInputStream bais = new ByteArrayInputStream(data);
				    writeBytesToFileChannel(bais, destFile);
				    bytesCopied = data.length;
				} catch (FileNotFoundException e) {
				    retErr = buildError(
					    codiceOggetto,
					    "Errore di scrittura su file system. Il file di destinazione {0} associato al codice oggetto {1} non esiste.",
					    new Object[] { destFile.getPath(), codiceOggetto }, e);
				} catch (IOException e) {
				    retErr = buildError(codiceOggetto,
					    "Errore di scrittura su file system durante per il file {0} associato al codice oggetto {1}.",
					    new Object[] { destFile.getPath(), codiceOggetto }, e);
				}
				end = new Date().getTime();
				if (activityLog.isDebugEnabled()) {
				    activityLog
					    .debug("handleBlobDocument() - Oggetto {}: spostamento del documento dal campo BLOB al file {} completato con successo. Tempo impiegato: {}",
						    new Object[] { codiceOggetto, destFile.getPath(), end - start });
				}
				start = end;
			    }
			    boolean updateRecord = retErr == null;
			    if (updateRecord) {
				try {
				    if (setBlobNull) {
					obj.setNonUsareContenutoBLOB(null);
				    }
				    if (!destFile.getName().equals(nomeFile)) {
					obj.setNomefile(destFile.getName());
				    }
				    obj.setPercorso(destPath);
				    this.update(obj);
				    flush();
				    this.commit();
				    flush();
				    clear();
				    status.setCountMoved(status.getCountMoved() + 1);
				    end = new Date().getTime();
				    if (activityLog.isDebugEnabled()) {
					activityLog.debug(
						"handleBlobDocument() - Oggetto {}. Aggiornamento del record nel DB completato in {} millisecondi",
						new Object[] { codiceOggetto, end - start });
				    }
				    start = end;
				} catch (Exception e) {
				    exceptionLog.error(
					    "handleBlobDocument() - Errore di scrittura nel database. Impossibile aggiornare il PERCORSO per l'oggetto "
						    + codiceOggetto, e);
				    //boolean restored = destFile.delete();
				    retErr = buildError(
					    codiceOggetto,
					    "Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l''oggetto {0}. Il file creato: {1} risulta inutile e dovrà essere cancellato a mano.",
					    new Object[] { codiceOggetto, destFile.getPath() }, e);
				}
			    }
			} else {
			    retErr = buildError(
				    codiceOggetto,
				    "Errore di scrittura su file system. Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.",
				    new Object[] { destDir.getPath(), codiceOggetto });
			}
		    }
		} catch (Exception e) {
		    Integer objCode = codiceOggetto;
		    String destFilePath = destFile != null ? destFile.getPath() : "null";
		    retErr = buildError(objCode,
			    "Si è verificato un errore durante la copia nel file {0} del contenuto BLOB per l''oggetto {1}. Errore: {2}",
			    new Object[] { destFilePath, codiceOggetto, e });
		}
		processed++;
		status.setCountHandled(processed);
		if (retErr != null) {
		    status.addError(retErr);
		}
		rowEnd = new Date().getTime();
		activityLog.info("Elaborazione documento {} completata. {} bytes trasferiti nel file {}. Tempo impiegato {}", new Object[] {
			codiceOggetto, bytesCopied, destFile.getPath(), rowEnd - rowStart });
		millisElapsed = rowEnd - globalStart;
		if (millisElapsed > maxMillis) {
		    activityLog.info("La procedura è stata interrotta perché si è raggiunto il limite massimo di tempo ({} minuti).",
			    new Object[] { maxMinutes });
		    break;
		}
	    }
	}
	session.close();
	return status.getCountMoved();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return 0;@@@ENDALTERNATIVEEXIT@@@
    }

    public int verificaIncongruenze(OggettiFileSystemStatusBean status) {

	int retVal = 0;
	// §§§BEGIN§§§
	Criteria objectsCriterion = getSession().createCriteria(getEntityClass());
	objectsCriterion.add(Restrictions.and(Restrictions.isNotNull("percorso"), Restrictions.isNotNull("nonUsareContenutoBLOB")));
	//objectsCriterion.add(Restrictions.isNull("oggetto"));
	objectsCriterion.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	objectsCriterion.setProjection(Projections.rowCount());
	Integer numWarnings = (Integer) objectsCriterion.uniqueResult();
	if (numWarnings != null && numWarnings > 0) {
	    retVal++;
	    OggettiFileSystemError warning = buildError(null,
		    "Sono stati individuati {0} oggetti che sono memorizzati sia su filesystem sia su database.", new Object[] { numWarnings });
	    status.addWarning(warning);
	}
	// §§§END§§§
	return retVal;
    }

    @Override
    public Class<Oggetti> getEntityClass() {

	return Oggetti.class;
    }

    private OggettiFileSystemError handleObjectDocument(String rootPath, Oggetti oggetto, boolean setBlobNull) {

	// §§§BEGIN§§§
	// if (oggetto.getOggetto() == null || oggetto.getOggetto().length == 0) {
	long start = new Date().getTime();
	byte[] objData = oggetto.getNonUsareContenutoBLOB();
	long end = new Date().getTime();
	activityLog.debug("handleObjectDocument() - Oggetto {} lettura del contenuto del campo BLOB terminata. Tempo impiegato millisecondi: {}",
		new Object[] { oggetto.getId().getCodice(), end - start });
	if (oggetto.getNonUsareContenutoBLOB() == null || oggetto.getNonUsareContenutoBLOB().length == 0) {
	    return handleFilesystemDocument(rootPath, oggetto);
	} else {
	    return handleBlobDocument(rootPath, oggetto, setBlobNull);
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private OggettiFileSystemError handleFilesystemDocument(String rootPath, Oggetti oggetto) {

	// §§§BEGIN§§§
	activityLog.info("handleFilesystemDocument() - Inizio elaborazione. L'oggetto {} sarà letto dal filesystem.", oggetto.getId().getCodice());
	long start = new Date().getTime();
	long end = 0L;
	boolean docMoved = false;
	OggettiFileSystemError retError = null;
	File f = new File(rootPath, oggetto.getNomefile());
	if (f.isFile()) {
	    if (f.canWrite()) {
		String destPath = Utilities.buildPathFromCodiceOggetto(oggetto.getId().getCodice());//buildPathFromLastModificationDate(f);
		File destDir = new File(rootPath, destPath);
		boolean pathExists = destDir.exists();
		if (!pathExists) {
		    pathExists = destDir.mkdirs();
		    if (!pathExists) {
			retError = buildError(
				oggetto.getId().getCodice(),
				"Errore di scrittura su file system. Impossibile creare la directory {0} per l'archiviazione del documento associato al codice oggetto {1}",
				new Object[] { destDir.getPath(), oggetto.getId().getCodice() });
		    }
		}
		if (pathExists) {
		    File destFile = getDestinationFile(destDir, oggetto.getNomefile(), oggetto.getId().getCodice());
		    if (destDir.canWrite()) {
			if (!destFile.exists()) {
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled()) {
				activityLog
					.debug("handleFilesystemDocument() - Oggetto {}. Elaborazione dei nomi dei file e delle directory terminata in {} millisecondi",
						new Object[] { oggetto.getId().getCodice(), end - start });
			    }
			    start = end;
			    docMoved = f.renameTo(destFile);
			    if (docMoved) {
				end = new Date().getTime();
				if (activityLog.isDebugEnabled()) {
				    activityLog
					    .debug("handleFilesystemDocument() - Oggetto {}: spostamento del file da {} a {} completato con successo in {} millisecondi.",
						    new Object[] { oggetto.getId().getCodice(), f.getPath(), destFile.getPath(), end - start });
				}
				start = end;
				// Utilities.permessiFSLinux(destFile, 777);
				oggetto.setPercorso(File.separatorChar + destPath + File.separatorChar);
				try {
				    getHibernateTemplate().update(oggetto);
				    end = new Date().getTime();
				    if (activityLog.isDebugEnabled()) {
					activityLog
						.debug("handleFilesystemDocument() - Oggetto {}. Aggiornamento del percorso nel DB terminato in {} millisecondi",
							new Object[] { oggetto.getId().getCodice(), end - start });
				    }
				    start = end;
				    //update(oggetto);
				    getHibernateTemplate().flush();
				    forceCommit();
				    end = new Date().getTime();
				    if (activityLog.isDebugEnabled()) {
					activityLog
						.debug("handleFilesystemDocument() - Oggetto {}. Flush e Commit delle modifiche al DB eseguiti in {} millisecondi",
							new Object[] { oggetto.getId().getCodice(), end - start });
				    }
				} catch (Exception e) {
				    docMoved = false;
				    exceptionLog.error("Errore di scrittura nel database. Impossibile aggiornare il PERCORSO per l'oggetto "
					    + oggetto.getId().getCodice(), e);
				    if (e instanceof InvalidStateException) {
					InvalidStateException ise = (InvalidStateException) e;
					for (InvalidValue iv : ise.getInvalidValues()) {
					    exceptionLog.error("handleBlobDocument() - Errore di validazione nel campo {}, valore {}.", new Object[] {
						    iv.getPropertyName(), iv.getValue() });
					}
				    }
				    boolean restored = destFile.renameTo(f);
				    if (restored) {
					if (activityLog.isDebugEnabled()) {
					    activityLog.debug("Oggetto {}: ripristino del percorso originale {} completato con successo.",
						    new Object[] { oggetto.getId().getCodice(), f.getPath() });
					}
					retError = buildError(
						oggetto.getId().getCodice(),
						"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l'oggetto {0}. Il file è stato riportato alla sua posizione originale {1}",
						new Object[] { oggetto.getId().getCodice(), f.getPath() });
				    } else {
					retError = buildError(
						oggetto.getId().getCodice(),
						"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l'oggetto {0}. Non è stato possibile riportare il file alla sua posizione originale {1}. \r\nATTENZIONE!!!!!: i dati nel DB non corrispondono alla reale posizione del file sul file system!",
						new Object[] { oggetto.getId().getCodice(), f.getPath() });
				    }
				}
			    } else {
				retError = buildError(oggetto.getId().getCodice(),
					"Errore di scrittura su file system. Oggetto {0}: spostamento del file da {1} a {2} fallito.", new Object[] {
						oggetto.getId().getCodice(), f.getPath(), destFile.getPath() });
			    }
			} else {
			    retError = buildError(
				    oggetto.getId().getCodice(),
				    "Errore di scrittura su file system. Il file di destinazione {0} associato al codice oggetto {1} esiste già. L'operazione sarà annullata e il file esistente non sarà sovrascritto.",
				    new Object[] { destFile.getPath(), oggetto.getId().getCodice() });
			}
		    } else {
			retError = buildError(
				oggetto.getId().getCodice(),
				"Errore di scrittura su file system. Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.",
				new Object[] { destDir.getPath(), oggetto.getId().getCodice() });
		    }
		}
	    } else {
		retError = buildError(oggetto.getId().getCodice(),
			"Errore di scrittura su file system. Il file {0} associato al codice oggetto {1} non è accessibile in scrittura.",
			new Object[] { f.getPath(), oggetto.getId().getCodice() });
	    }
	} else {
	    retError = buildError(oggetto.getId().getCodice(),
		    "Errore di lettura da file system. Il file {0} associato al codice oggetto {1} non esiste o è una directory.",
		    new Object[] { f.getPath(), oggetto.getId().getCodice() });
	}
	return retError;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private OggettiFileSystemError handleBlobDocument(String rootPath, Oggetti oggetto, boolean setBlobNull) {

	// §§§BEGIN§§§
	long start = new Date().getTime();
	long end = 0L;
	activityLog.info("handleBlobDocument() - Inizio elaborazione BLOB. L'oggetto {} sarà letto dalla colonna BLOB della tabella OGGETTI.",
		oggetto.getId().getCodice());
	OggettiFileSystemError retErr = null;
	String destPath = Utilities.buildPathFromCodiceOggetto(oggetto.getId().getCodice());
	File destDir = new File(rootPath, destPath);
	boolean pathExists = destDir.exists();
	if (!pathExists) {
	    pathExists = destDir.mkdirs();
	    if (!pathExists) {
		retErr = buildError(
			oggetto.getId().getCodice(),
			"Errore di scrittura su file system. Impossibile creare la directory {0} per l'archiviazione del documento associato al codice oggetto {1}",
			new Object[] { destDir.getPath(), oggetto.getId().getCodice() });
	    }
	}
	if (pathExists) {
	    File destFile = getDestinationFile(destDir, oggetto.getNomefile(), oggetto.getId().getCodice());
	    if (destDir.canWrite()) {
		if (!destFile.exists()) {
		    end = new Date().getTime();
		    if (activityLog.isDebugEnabled())
			activityLog.debug(
				"handleBlobDocument() - Oggetto {}. Elaborazione dei nomi dei file e delle directory terminata in {} millisecondi",
				new Object[] { oggetto.getId().getCodice(), end - start });
		    start = end;
		    byte[] data = oggetto.getNonUsareContenutoBLOB();
		    end = new Date().getTime();
		    if (activityLog.isDebugEnabled())
			activityLog.debug("handleBlobDocument() - Oggetto {}. Ri-lettura campo BLOB eseguita in {} millisecondi", new Object[] {
				oggetto.getId().getCodice(), end - start });
		    start = end;
		    ByteArrayInputStream in = new ByteArrayInputStream(data);
		    boolean copiedToFile = false;
		    FileOutputStream out = null;
		    BufferedOutputStream bout = null;
		    try {
			destFile.createNewFile();
			out = new FileOutputStream(destFile);
			bout = new BufferedOutputStream(out);
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = 0;
			while ((bytesRead = in.read(buffer)) > 0) {
			    bout.write(buffer, 0, bytesRead);
			}
			bout.flush();
			copiedToFile = true;
			end = new Date().getTime();
			if (activityLog.isDebugEnabled()) {
			    activityLog
				    .debug("handleBlobDocument() - Oggetto {}: spostamento del documento dal campo BLOB al file {} completato con successo. Tempo impiegato: {}",
					    new Object[] { oggetto.getId().getCodice(), destFile.getPath(), end - start });
			}
			start = end;
			//Utilities.permessiFSLinux(destFile, 777);
		    } catch (FileNotFoundException e) {
			retErr = buildError(oggetto.getId().getCodice(),
				"Errore di scrittura su file system. Il file di destinazione {0} associato al codice oggetto {1} non esiste.",
				new Object[] { destFile.getPath(), oggetto.getId().getCodice() }, e);
		    } catch (IOException e) {
			retErr = buildError(oggetto.getId().getCodice(),
				"Errore di scrittura su file system durante la scrittura dell file {0} associato al codice oggetto {1}.",
				new Object[] { destFile.getPath(), oggetto.getId().getCodice() }, e);
		    } finally {
			try {
			    in.close();
			    if (bout != null) {
				bout.close();
			    }
			} catch (IOException e1) {
			    exceptionLog
				    .error("Errore I/O nella chiusura del flusso in scrittura sul file {}. ATTENZIONE!!! Il file potrebbe essere rimasto lockato.",
					    destFile.getPath(), e1);
			}
		    }
		    if (copiedToFile) {
			oggetto.setPercorso(File.separatorChar + destPath + File.separatorChar);
			if (setBlobNull) {
			    oggetto.setNonUsareContenutoBLOB(null);
			    oggetto.setOggetto(null);
			}
			try {
			    getHibernateTemplate().update(oggetto);
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled())
				activityLog
					.debug("handleBlobDocument() - Oggetto {}. Aggiornamento del record nel DB con Hibernate completato in {} millisecondi",
						new Object[] { oggetto.getId().getCodice(), end - start });
			    start = end;
			    getHibernateTemplate().flush();
			    forceCommit();
			    end = new Date().getTime();
			    if (activityLog.isDebugEnabled())
				activityLog.debug(
					"handleBlobDocument() - Oggetto {}. Flush e Commit delle modifiche al DB eseguiti in {} millisecondi",
					new Object[] { oggetto.getId().getCodice(), end - start });
			} catch (Exception e) {
			    exceptionLog.error(
				    "handleBlobDocument() - Errore di scrittura nel database. Impossibile aggiornare il PERCORSO per l'oggetto "
					    + oggetto.getId().getCodice(), e);
			    if (e instanceof InvalidStateException) {
				InvalidStateException ise = (InvalidStateException) e;
				for (InvalidValue iv : ise.getInvalidValues()) {
				    exceptionLog.error("handleBlobDocument() - Errore di validazione nel campo {}, valore {}.",
					    new Object[] { iv.getPropertyName(), iv.getValue() });
				}
			    }
			    boolean restored = destFile.delete();
			    if (restored) {
				retErr = buildError(
					oggetto.getId().getCodice(),
					"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l'oggetto {0}. Il file {1} che era stato creato è stato cancellato",
					new Object[] { oggetto.getId().getCodice(), destFile.getPath() }, e);
			    } else {
				retErr = buildError(
					oggetto.getId().getCodice(),
					"Errore di scrittura nel database. Non è stato possibile aggiornare nella base dati il PERCORSO per l'oggetto {0}. Inoltre è fallita la cancellazione del file {1} che era stato creato.",
					new Object[] { oggetto.getId().getCodice(), destFile.getPath() }, e);
			    }
			}
		    }
		} else {
		    retErr = buildError(
			    oggetto.getId().getCodice(),
			    "Errore di scrittura su file system. Il file di destinazione {0} associato al codice oggetto {1} esiste già. L'operazione sarà annullata e il file esistente non sarà sovrascritto.",
			    new Object[] { destFile.getPath(), oggetto.getId().getCodice() });
		}
	    } else {
		retErr = buildError(
			oggetto.getId().getCodice(),
			"Errore di scrittura su file system. Il percorso di destinazione {0} associato al codice oggetto {1} non è accessibile in scrittura.",
			new Object[] { destDir.getPath(), oggetto.getId().getCodice() });
	    }
	}
	return retErr;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public File getDestinationFile(File destinationDir, String nomeFile, Integer codiceOggetto) {

	// §§§BEGIN§§§
	StringBuilder sbNomeFile = new StringBuilder();
	// if (oggetto.getOggetto() != null && oggetto.getOggetto().length > 0) {
	if (!nomeFile.startsWith(codiceOggetto + "-")) {
	    sbNomeFile.append(codiceOggetto).append('-');
	}
	sbNomeFile.append(Utilities.cleanFilename(nomeFile));
	if (activityLog.isDebugEnabled())
	    activityLog.debug("getDestinationFile() - il file di destinazione avrà nome {} e si troverà nel percorso {}",
		    new Object[] { sbNomeFile.toString(), destinationDir.getPath() });
	File retFile = new File(destinationDir, sbNomeFile.toString());
	return retFile;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countDocumentsAtRoot() {

	int numDocs = 0;
	// §§§BEGIN§§§
	SQLQuery countSQL = getSession()
		.createSQLQuery(
			"select count(o.codiceoggetto) as fileCount from oggetti o where o.idcomune = :idcomune and o.oggetto is null and o.percorso is null");
	countSQL.setParameter("idcomune", ORMHelper.getIdcomune());
	countSQL.addScalar("fileCount", new BigIntegerType());
	//	Criteria countCrit = getSession().createCriteria(getEntityClass());
	//	countCrit.add(Restrictions.or(Restrictions.isNull("percorso"), Restrictions.eq("percorso", "")));
	//	countCrit.add(Restrictions.isNull("nonUsareContenutoBLOB"));
	//	countCrit.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	//	countCrit.setProjection(Projections.rowCount());
	//	Integer count = (Integer) countCrit.uniqueResult();
	BigInteger count = (BigInteger) countSQL.uniqueResult();
	if (count != null) {
	    numDocs = count.intValue();
	}
	// §§§END§§§
	return numDocs;
    }

    @Override
    public int countDocumentsInBlob() {

	int numDocs = 0;
	// §§§BEGIN§§§
	SQLQuery countSQL = getSession()
		.createSQLQuery(
			"select count(o.codiceoggetto) as fileCount from oggetti o where o.idcomune = :idcomune and o.oggetto is not null and o.percorso is null");
	countSQL.setParameter("idcomune", ORMHelper.getIdcomune());
	countSQL.addScalar("fileCount", new BigIntegerType());
	//	Criteria countCrit = getSession().createCriteria(getEntityClass());
	//	countCrit.add(Restrictions.and(
	//		Restrictions.and(Restrictions.isNotNull("nonUsareContenutoBLOB"), Restrictions.eq("id.idcomune", ORMHelper.getIdcomune())),
	//		Restrictions.or(Restrictions.isNull("percorso"), Restrictions.eq("percorso", ""))));
	//	countCrit.setProjection(Projections.rowCount());
	//	Integer count = (Integer) countCrit.uniqueResult();
	BigInteger count = (BigInteger) countSQL.uniqueResult();
	if (count != null) {
	    numDocs = count.intValue();
	}
	// §§§END§§§
	return numDocs;
    }

    public void writeBytesToFileChannel(InputStream in, File out) throws IOException {

	// §§§BEGIN§§§
	FileChannel outChnl = null;
	ReadableByteChannel inChnl = null;
	try {
	    inChnl = Channels.newChannel(in);
	    outChnl = new FileOutputStream(out).getChannel();
	    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
	    int read = 0;
	    while ((read = inChnl.read(buffer)) > -1) {
		buffer.flip();
		outChnl.write(buffer);
		buffer.clear();
	    }
	} catch (IOException e) {
	    throw e;
	} finally {
	    if (outChnl != null && outChnl.isOpen()) {
		outChnl.close();
	    }
	    if (inChnl != null && inChnl.isOpen()) {
		inChnl.close();
	    }
	}
	// §§§END§§§
    }

    public String computeMD5Hash(InputStream target) throws NoSuchAlgorithmException, IOException {

	// §§§BEGIN§§§
	byte[] mdbytes = null;
	ReadableByteChannel channel = null;
	try {
	    //StringBuilder sb = new StringBuilder();
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    channel = Channels.newChannel(target);
	    ByteBuffer bb = ByteBuffer.allocate(BUFFER_SIZE);
	    int nread = 0;
	    while ((nread = channel.read(bb)) != -1) {
		bb.flip();
		md.update(bb);
		bb.clear();
	    }
	    mdbytes = md.digest();
	    //convert the byte to hex format method 1
	    /*
	    for (int i = 0; i < mdbytes.length; i++) {
	      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    
	    System.out.println("Digest(in hex format):: " + sb.toString());
	    
	    //convert the byte to hex format method 2
	    StringBuffer hexString = new StringBuffer();
	    for (int i=0;i<mdbytes.length;i++) {
	    	String hex=Integer.toHexString(0xff & mdbytes[i]);
	         	if(hex.length()==1) hexString.append('0');
	         	hexString.append(hex);
	    }
	    System.out.println("Digest(in hex format):: " + hexString.toString());
	    */
	} catch (FileNotFoundException e) {
	    throw e;
	} catch (IOException e) {
	    throw e;
	} finally {
	    if (channel != null && channel.isOpen()) {
		channel.close();
	    }
	}
	return new String(mdbytes);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public String computeMD5Hash(byte[] docData) throws NoSuchAlgorithmException, IOException {

	// §§§BEGIN§§§
	byte[] mdbytes = null;
	ReadableByteChannel channel = null;
	//StringBuilder sb = new StringBuilder();
	MessageDigest md = MessageDigest.getInstance("MD5");
	md.update(docData);
	mdbytes = md.digest();
	return new String(mdbytes);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private boolean isDocumentModified(byte[] blobData, File docFile) throws Exception {

	boolean retVal = true;
	// §§§BEGIN§§§
	String dbMD5 = computeMD5Hash(blobData);
	String fsMD5 = computeMD5Hash(new FileInputStream(docFile));
	retVal = !dbMD5.equals(fsMD5);
	// §§§END§§§
	return retVal;
    }

    private void forceCommit() {

	// §§§BEGIN§§§
	Session ses = getSession();
	SQLQuery q = ses.createSQLQuery("COMMIT");
	q.executeUpdate();
	ses.flush();
	// §§§END§§§
    }

    public OggettiFileSystemError buildError(Integer codiceOggetto, String errorMessage, Object[] formatParams, Exception e) {

	// §§§BEGIN§§§
	OggettiFileSystemError error = new OggettiFileSystemError();
	if (errorMessage == null) {
	    errorMessage = "";
	}
	if (formatParams != null) {
	    errorMessage = MessageFormat.format(errorMessage, formatParams);
	}
	error.setErrorMessage(errorMessage);
	//error.setOggetto(oggetto);
	error.setCodiceOggetto(codiceOggetto);
	error.setException(e);
	if (e != null) {
	    exceptionLog.error(errorMessage, e);
	} else {
	    exceptionLog.error(errorMessage);
	}
	errorLog.error(errorMessage);
	return error;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    //    private OggettiFileSystemError buildError(String codiceOggetto, String errorMessage) {
    //
    //	return buildError(oggetto, errorMessage, null, null);
    //    }
    public OggettiFileSystemError buildError(Integer codiceOggetto, String errorMessage, Object[] formatParams) {

	// §§§BEGIN§§§
	return buildError(codiceOggetto, errorMessage, formatParams, null);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
