package it.gruppoinit.nlapec.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    static final int BUFFER = 2048;

    @SuppressWarnings("rawtypes")
    public static File extractFileFromZip(File zipFile, String zipEntryName) {

	File fileOut = null;
	String attDir = getAttachmentsFolderPath();
	try {
	    BufferedOutputStream dest = null;
	    BufferedInputStream is = null;
	    ZipEntry entry;
	    ZipFile zip = new ZipFile(zipFile);
	    Enumeration e = zip.entries();
	    while (e.hasMoreElements()) {
		entry = (ZipEntry) e.nextElement();
		if (entry.getName().equalsIgnoreCase(zipEntryName)) {
		    log.debug("extractFileFromZip(): extracting: {} from zip: {}", entry, zipFile);
		    is = new BufferedInputStream(zip.getInputStream(entry));
		    int count;
		    byte data[] = new byte[BUFFER];
		    String filename = entry.getName();
		    fileOut = new File(attDir + filename);
		    log.debug("extractFileFromZip(): saving: {}", fileOut);
		    FileOutputStream fos = new FileOutputStream(fileOut);
		    dest = new BufferedOutputStream(fos, BUFFER);
		    while ((count = is.read(data, 0, BUFFER)) != -1) {
			dest.write(data, 0, count);
		    }
		    dest.flush();
		    dest.close();
		    is.close();
		}
	    }
	    zip.close();
	} catch (Exception e) {
	    log.error("extractFileFromZip(): {}", e.getMessage());
	}
	return fileOut;
    }

    public static File createZipArchive(String zipArchiveName, File dir) {

	log.debug("createZipArchive({})", zipArchiveName);
	String zipFile = "";
	try {
	    String attDir = getAttachmentsFolderPath();
	    zipFile = attDir + zipArchiveName;
	    BufferedInputStream origin = null;
	    FileOutputStream dest = new FileOutputStream(zipFile);
	    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
	    //out.setMethod(ZipOutputStream.DEFLATED);
	    byte data[] = new byte[BUFFER];
	    // get a list of files from the specified directory
	    String files[] = dir.list();
	    for (int i = 0; i < files.length; i++) {
		log.debug("createZipArchive(): Adding: {}", files[i]);
		File _entryFile = new File(dir, files[i]);
		FileInputStream fi = new FileInputStream(_entryFile);
		origin = new BufferedInputStream(fi, BUFFER);
		ZipEntry entry = new ZipEntry(files[i]);
		out.putNextEntry(entry);
		int count;
		while ((count = origin.read(data, 0, BUFFER)) != -1) {
		    out.write(data, 0, count);
		}
		origin.close();
	    }
	    out.close();
	} catch (Exception e) {
	    log.error("createZipArchive(): {}", e.getMessage());
	}
	return new File(zipFile);
    }

    public static byte[] getBytesFromFile(File file) throws IOException {

	FileInputStream fis = new FileInputStream(file);
	return getBytesFromInputStream(fis);
    }

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {

	// Get the size of the file
	long length = is.available();
	if (length > Integer.MAX_VALUE) {
	    // File is too large
	    log.error("getBytesFromInputStream(): file is too large: {}", length);
	}
	// Create the byte array to hold the data
	byte[] bytes = new byte[(int) length];
	// Read in the bytes
	int offset = 0;
	int numRead = 0;
	while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
	    offset += numRead;
	}
	// Ensure all the bytes have been read in
	if (offset < bytes.length) {
	    throw new IOException("Could not completely read file ");
	}
	// Close the input stream and return bytes
	is.close();
	return bytes;
    }

    /**
     * salva un file all'interno della cartella specfificata dal File dir, se questo è nullo allora salva il file dentro
     * la cartella attachments
     * 
     * @param filename
     * @param input
     * @param dir
     * @return
     * @throws IOException
     */
    public static File saveFile(String filename, InputStream input, File dir) throws IOException {

	String attDir = getAttachmentsFolderPath();
	if (dir != null) {
	    attDir = dir.getAbsolutePath();
	}
	File file = new File(attDir, filename);
	log.debug("saveFile(): {}", file);
	FileOutputStream fos = new FileOutputStream(file);
	BufferedOutputStream bos = new BufferedOutputStream(fos);
	BufferedInputStream bis = new BufferedInputStream(input);
	int aByte;
	while ((aByte = bis.read()) != -1) {
	    bos.write(aByte);
	}
	bos.flush();
	bos.close();
	bis.close();
	return file;
    }

    /**
     * salva un file all'interno della cartella attachments
     * 
     * @param filename
     * @param input
     * @return
     * @throws IOException
     */
    public static File saveFile(String filename, InputStream input) throws IOException {

	return saveFile(filename, input, null);
    }

    public static File saveFile(String filename, byte[] inputBytes, File dir) throws IOException {

	String attDir = getAttachmentsFolderPath();
	if (dir != null) {
	    attDir = dir.getAbsolutePath();
	}
	File file = new File(attDir, filename);
	log.debug("saveFile(): {}", file);
	FileOutputStream fos = new FileOutputStream(file);
	BufferedOutputStream bos = new BufferedOutputStream(fos);
	ByteArrayInputStream bais = new ByteArrayInputStream(inputBytes);
	BufferedInputStream bis = new BufferedInputStream(bais);
	int aByte;
	while ((aByte = bis.read()) != -1) {
	    bos.write(aByte);
	}
	bos.flush();
	bos.close();
	bis.close();
	bais.close();
	return file;
    }

    /**
     * cancella tutti i file presenti nella cartella specificata presente all'interno della cartella attachments
     * 
     * @return
     */
    public static boolean deleteAllFiles(File dir) {

	boolean success = false;
	try {
	    boolean tempSuccess = true;
	    String path = getAttachmentsFolderPath();
	    if (dir != null) {
		path = dir.getAbsolutePath();
	    }
	    File _dir = new File(path);
	    log.debug("deleteAllFiles({})", _dir);
	    String[] files = _dir.list();
	    if (files != null) {
		for (String file : files) {
		    File f = new File(_dir, file);
		    boolean esitoDelete = f.delete();
		    tempSuccess = tempSuccess && esitoDelete;
		    if (!esitoDelete) {
			log.error("deleteFile({}): {}", f, esitoDelete);
		    }
		}
	    }
	    success = tempSuccess;
	} catch (Exception e) {
	    log.error("deleteAllFiles({}): {}", dir, e.getMessage());
	}
	log.debug("deleteAllFiles(): {}", success);
	return success;
    }

    /**
     * cancella tutti i file presenti nella cartella attachments
     * 
     * @return
     */
    public static boolean deleteAllFiles() {

	return deleteAllFiles(null);
    }

    /**
     * metodo per la creazione di una directory "attachments" all'interno della directory WEB-INF della webapp
     * 
     * @return
     */
    public static String getAttachmentsFolderPath() {

	URL classPath = FileUtil.class.getResource("/");
	String basePath = classPath.getPath();
	int stop = basePath.length() - "classes/".length();
	basePath = basePath.substring(0, stop);
	basePath += "attachments/";
	File dir = new File(basePath);
	if (!dir.exists()) {
	    boolean success = dir.mkdir();
	    if (!success) {
		log.error("getBaseFolderPath(): unable to create attachments dir!");
	    } else {
		log.debug("getAttachmentsFolderPath(): {}", dir);
	    }
	}
	return basePath;
    }

    /**
     * crea una cartella all'interno della cartella attachments
     * 
     * @param folderName
     * @return
     */
    public static File createFolder(String folderName) {

	String path = getAttachmentsFolderPath();
	File dir = new File(path, folderName);
	if (!dir.exists()) {
	    boolean success = dir.mkdir();
	    if (!success) {
		log.error("createDirInsideAttachmentsFolder(): unable to create folder: {}", folderName);
	    }
	}
	return dir;
    }

    public static void main(String[] args) throws Exception {

	System.out.println("getAttachmentsFolderPath: " + FileUtil.getAttachmentsFolderPath());
	File dir = FileUtil.createFolder("temp");
	System.out.println("createFolder: " + dir.getAbsolutePath());
	File file = new File("C:\\test.txt");
	FileInputStream fis = new FileInputStream(file);
	System.out.println("saveFile: " + FileUtil.saveFile(file.getName(), fis, dir));
	System.out.println("createZipArchive: " + FileUtil.createZipArchive("zippone.zip", dir));
	System.out.println("deleteAllFiles: " + FileUtil.deleteAllFiles(dir));
	System.out.println("deleteAllFiles: " + FileUtil.deleteAllFiles());
    }
}
