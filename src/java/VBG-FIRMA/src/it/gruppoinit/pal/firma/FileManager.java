package it.gruppoinit.pal.firma;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileManager {

    private static final Logger log = LoggerFactory.getLogger(FileManager.class);
    private int maxUploadSize;
    private int uploadSizeFactor;
    public static final String uploadDir = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "webapp-firma-files";

    public FileManager() {

	InputStream is = null;
	try {
	    Properties prop = new Properties();
	    is = this.getClass().getClassLoader().getResourceAsStream("deploy.properties");
	    prop.load(is);
	    if (StringUtils.isNotBlank(prop.getProperty("maxUploadSize"))) {
		maxUploadSize = Integer.valueOf(prop.getProperty("maxUploadSize"));
	    }
	    if (StringUtils.isNotBlank(prop.getProperty("uploadSizeFactor"))) {
		uploadSizeFactor = Integer.valueOf(prop.getProperty("uploadSizeFactor"));
	    }
	} catch (Exception e) {
	    log.error("Errore durante la creazione del FileManager", e);
	} finally {
	    if (is != null) {
		try {
		    is.close();
		} catch (Exception e) {
		}
	    }
	}
	log.info("FileManager creato: uploadDir={}, maxUploadSize={} Mbytes, uploadSizeFactor={}", new Object[] { uploadDir, maxUploadSize,
		uploadSizeFactor });
    }

    public FileInfo upload(DataHandler dataHandler, String fileName, String sessionId, String clientFileId) {

	log.info("upload: sessionId={}, fileName={}, clientFileId={}", new Object[] { sessionId, fileName, clientFileId });
	try {
	    File sessionDir;
	    if (StringUtils.isBlank(sessionId)) {
		sessionId = SessionManager.startNewSession();
		sessionDir = createSessionDir(sessionId);
	    } else {
		sessionDir = getSessionDir(sessionId);
	    }
	    SessionManager.checkSession(sessionId);
	    long size = FileUtils.sizeOfDirectory(sessionDir);
	    if (size > (maxUploadSize * uploadSizeFactor * 1000 * 1000)) {
		log.error("upload: Raggiunta dimensione massima della cartella: {} Mbytes", maxUploadSize * uploadSizeFactor);
		throw new RuntimeException("Raggiunta dimensione massima della cartella.");
	    } else {
		String fileId = UUID.randomUUID().toString();
		File uploadedFile = new File(sessionDir, fileId);
		FileOutputStream fos = new FileOutputStream(uploadedFile);
		dataHandler.writeTo(fos);
		fos.close();
		log.debug("upload: uploaded file fileId={}, size={}", fileId, uploadedFile.length());
		if (uploadedFile.length() == 0) {
		    throw new RuntimeException("il file è vuoto.");
		}
		FileInfo fileInfo = new FileInfo(sessionId, fileId, clientFileId, fileName, Boolean.FALSE);
		SessionManager.put(fileInfo);
		return fileInfo;
	    }
	} catch (Exception e) {
	    log.error("upload", e);
	    throw new RuntimeException("Errore durante il caricamento del file: " + e.getMessage());
	}
    }

    public FileInfoDH download(String sessionId, String fileId) {

	log.info("download: sessionId={}, fileId={}", sessionId, fileId);
	SessionManager.checkSession(sessionId);
	FileInfo fileInfo = SessionManager.get(sessionId, fileId);
	String fs = System.getProperty("file.separator");
	File f = new File(getSessionDir(sessionId) + fs + fileId);
	if (f.exists()) {
	    log.debug("download: downloaded file fileId={}, size={}", fileId, f.length());
	    FileDataSource fileDataSource = new FileDataSource(f);
	    DataHandler dh = new DataHandler(fileDataSource);
	    FileInfoDH fileInfoDH = new FileInfoDH(sessionId, fileId, fileInfo.getClientFileId(), fileInfo.getFileName(), fileInfo.getIsSigned());
	    fileInfoDH.setDh(dh);
	    return fileInfoDH;
	} else {
	    log.error("download: File non trovato: sessionId={}, fileId={}", sessionId, fileId);
	    throw new RuntimeException("File non trovato");
	}
    }

    public void update(DataHandler dataHandler, String fileName, String sessionId, String fileId, Boolean isSigned) {

	log.info("update: sessionId={}, fileId={}, fileName={}", new Object[] { sessionId, fileId, fileName });
	try {
	    SessionManager.checkSession(sessionId);
	    File sessionDir = getSessionDir(sessionId);
	    File uploadedFile = new File(sessionDir, fileId);
	    log.debug("update:(before) fileId={},size={}", fileId, uploadedFile.length());
	    FileOutputStream fos = new FileOutputStream(uploadedFile);
	    dataHandler.writeTo(fos);
	    fos.close();
	    log.debug("update:(after) fileId={},size={}", fileId, uploadedFile.length());
	    FileInfo fileInfo = SessionManager.get(sessionId, fileId);
	    fileInfo.setFileName(fileName);
	    fileInfo.setIsSigned(isSigned);
	    SessionManager.put(fileInfo);
	} catch (Exception e) {
	    log.error("update", e);
	    throw new RuntimeException("Errore durante il caricamento del file: " + e.getMessage());
	}
    }

    public List<FileInfo> list(String sessionId) {

	log.debug("list: sessionId={}", sessionId);
	List<FileInfo> list = new ArrayList<FileInfo>();
	Map<String, FileInfo> fileMap = SessionManager.list(sessionId);
	if (fileMap != null) {
	    for (Entry<String, FileInfo> entry : fileMap.entrySet()) {
		list.add(entry.getValue());
	    }
	}
	return list;
    }

    private File createSessionDir(String sessionId) {

	log.info("createSessionDir: sessionId={}", sessionId);
	File sessionDir = new File(uploadDir, sessionId);
	boolean dirCreated = sessionDir.mkdirs();
	if (!dirCreated) {
	    throw new RuntimeException("Errore durante la creazione della cartella: " + sessionId);
	}
	return sessionDir;
    }

    private File getSessionDir(String sessionId) {

	log.info("getSessionDir: sessionId={}", sessionId);
	File sessionDir = new File(uploadDir, sessionId);
	return sessionDir;
    }
}
