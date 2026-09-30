/**
 * 
 */
package it.gruppoinit.pal.gp.core.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe che raccoglie metodi statici di utilità per la gestione delle operazioni di lettura e scrittura su flussi o
 * per la gestione del filesystem
 * 
 * @author Franco.Leone
 *
 */
public class IOUtils {

    private static final int ZIP_READ_BUFFER_SIZE = 8192;
    private static final List<String> WRAPPING_EXTENSIONS = new ArrayList<>();
    public static final String DEFAULT_CHARSET = "UTF-8";
    static {
	WRAPPING_EXTENSIONS.add("p7m");
    }
    private static final Logger log = LoggerFactory.getLogger(IOUtils.class);
    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^\\x20-\\x7e]");
    private static final Pattern INVALID_FILENAME_PATTERN = Pattern.compile("[\"|*?:<>\\\\/]");

    private IOUtils() {

	//costruttore privato per accedere solo in modo statico
    }

    public static DataHandler bytesToDataHandler(byte[] binData) {

	if (binData == null) {
	    binData = new byte[0];
	}
	ByteArrayDataSource bads = new ByteArrayDataSource(binData, "application/octet-stream");
	return new DataHandler(bads);
    }

    /**
     * Read the bytes from the DataHandler
     * 
     * @param dh
     * @return byte[]
     * @throws IOException
     */
    public static byte[] dataHandlerToBytes(DataHandler dh) {

	try {
	    return org.apache.commons.io.IOUtils.toByteArray(dh.getInputStream());
	} catch (IOException e) {
	    log.error("dataHandlerToBytes: ", e);
	    throw new RuntimeException("", e);
	}
    }

    public static String marshallObject(Object obj) {

	StringWriter stringWriter = new StringWriter();
	try {
	    JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
	    Marshaller marshaller = jaxbContext.createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_CHARSET);
	    marshaller.marshal(obj, stringWriter);
	    return stringWriter.toString();
	} catch (Exception e1) {
	    log.error("marshallObject: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static Object unMarshallString(String xml, Class<?> clazz) {

	return unMarshallString(xml, clazz, DEFAULT_CHARSET);
    }

    public static Object unMarshallString(String xml, Class<?> clazz, String encoding) {

	try {
	    JAXBContext jc = JAXBContext.newInstance(clazz);
	    Unmarshaller u = jc.createUnmarshaller();
	    return u.unmarshal(new ByteArrayInputStream(xml.getBytes(encoding)));
	} catch (Exception e1) {
	    log.error("unMarshallString: {}", e1.getMessage());
	    throw new RuntimeException(e1);
	}
    }

    public static boolean checkIsValidDirectory(File dir) throws IOException {

	if (!dir.exists()) {
	    dir.mkdirs();
	}
	return Files.readAttributes(dir.toPath(), BasicFileAttributes.class).isDirectory();
    }

    public static String getFileExt(String nomeFile) {

	String[] dotSplitted = nomeFile.split("\\.");
	StringBuilder sbExt = new StringBuilder();
	boolean keepReading = true;
	int i = dotSplitted.length - 1;
	for (; i > 0 && keepReading; i--) {
	    if (sbExt.length() > 0) {
		sbExt.append('.');
	    }
	    sbExt.append(dotSplitted[i]);
	    if (!WRAPPING_EXTENSIONS.contains(dotSplitted[i].toLowerCase())) {
		keepReading = false;
	    }
	}
	return sbExt.toString();
    }

    public static void writeBytesToFile(byte[] data, File writeTo) throws IOException {

	writeBytesToStream(data, FileUtils.openOutputStream(writeTo));
    }

    public static String readFileAsString(FileInputStream readFromIS) throws IOException {

	return org.apache.commons.io.IOUtils.toString(readFromIS, StandardCharsets.UTF_8);
    }

    public static void writeBytesToStream(byte[] data, OutputStream out) throws IOException {

	org.apache.commons.io.IOUtils.write(data, out);
	out.close();
    }

    public static void copyStream(InputStream in, OutputStream out) throws IOException {

	org.apache.commons.io.IOUtils.copyLarge(in, out);
	out.close();
    }

    public static void copyStreamToFile(InputStream in, File outFile) throws IOException {

	copyStream(in, FileUtils.openOutputStream(outFile));
    }

    /**
     * Restituisce un oggetto {@link File} che rappresenta la directory temporanea utilizzata dal sistema in uso.
     * L'oggetto restituito punta alla directory indicata dalla proprietà di sistema java.io.tmpdir. Se la proprietà di
     * sistema non è impostata l'oggetto restituito punta alla cartella /temp, sottodirectory della directory corrente
     * (quella in cui è in'esecuzione il processo Java).
     * 
     * @return un oggetto {@link java.io.File} che punta alla directory temporanea di sistema.
     */
    public static File getSystemTempDir() {

	File systemTempDir = null;
	String tempPath = System.getProperty("java.io.tmpdir");
	systemTempDir = new File(tempPath);
	return systemTempDir;
    }

    /**
     * Crea un file zip con tutto il contenuto della cartella <code>sourceDir</code> e lo scrive nel flusso
     * <code>writeTo</code>.
     * 
     * @param writeTo
     *            : flusso in scrittura su un file ZIP.
     * @param sourceDir
     *            : directory il cui contenuto viene scritto nell'archivio zip
     * @return void
     * @throws IOException
     *             se si verifica un qualunque errore di lettura o scrittura dei files
     */
    public static void zipTo(File sourceDir, OutputStream writeTo) throws IOException {

	ZipOutputStream zos = new ZipOutputStream(writeTo);
	writeZipEntries(sourceDir, zos, null);
	zos.close();
    }

    public static void writeZipEntries(File entry, ZipOutputStream writeTo, File parentPath) throws IOException {

	if (entry.isFile()) {
	    zipFile(entry, writeTo);
	} else if (entry.isDirectory()) {
	    File[] dirContent = entry.listFiles();
	    for (File file : dirContent) {
		File entryFile = parentPath != null ? new File(parentPath, file.getName()) : new File(file.getName());
		if (file.isFile()) {
		    zipFile(file, writeTo);
		} else {
		    writeZipEntries(file, writeTo, entryFile);
		}
	    }
	}
    }

    private static void zipFile(File entry, ZipOutputStream writeTo) throws IOException {

	int bytesRead = 0;
	byte[] buffer = new byte[ZIP_READ_BUFFER_SIZE];
	try (FileInputStream fis = new FileInputStream(entry)) {
	    try (BufferedInputStream bis = new BufferedInputStream(fis)) {
		ZipEntry zentry = new ZipEntry(entry.getName());
		writeTo.putNextEntry(zentry);
		while ((bytesRead = bis.read(buffer)) > -1) {
		    writeTo.write(buffer, 0, bytesRead);
		}
		writeTo.closeEntry();
	    }
	}
    }

    public static void writeZipEntries(InputStream fileStream, ZipOutputStream writeTo, String nomeFile) throws IOException {

	int bytesRead = 0;
	byte[] buffer = new byte[ZIP_READ_BUFFER_SIZE];
	BufferedInputStream bis = new BufferedInputStream(fileStream);
	ZipEntry zentry = new ZipEntry(nomeFile);
	writeTo.putNextEntry(zentry);
	while ((bytesRead = bis.read(buffer)) > -1) {
	    writeTo.write(buffer, 0, bytesRead);
	}
	writeTo.closeEntry();
    }

    public static boolean isValidFileName(String fName) {

	return !(INVALID_FILENAME_PATTERN.matcher(fName).matches() || NON_ASCII_PATTERN.matcher(fName).matches());
    }

    public static String compressToBase64(String stringaDaComprimere, String nomeEntry) throws IOException {

	if (stringaDaComprimere == null || stringaDaComprimere.isEmpty()) {
	    return stringaDaComprimere;
	}
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ZipOutputStream zos = new ZipOutputStream(out);
	zos.putNextEntry(new ZipEntry(nomeEntry));
	byte[] bytes = stringaDaComprimere.getBytes();
	zos.write(bytes, 0, bytes.length);
	zos.closeEntry();
	zos.close();
	return Base64.encodeBase64String(out.toByteArray());
    }
}
