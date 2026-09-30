package it.gruppoinit.dss.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletDiskFileUpload;
import org.apache.commons.fileupload2.jakarta.servlet5.JakartaServletFileUpload;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.dss.validation.SignatureValidationService;
import it.gruppoinit.dss.validation.ValidationApiResponse;
import it.gruppoinit.dss.validation.ValidationResultDTO;

public class ValidationServlet extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 5474473045610861548L;
    private Logger log = Logger.getLogger(this.getClass().getName());
    private final static long MAX_UPLOAD_SIZE = 10000L;
    private final static long UPLOADED_DIR_SIZE_FACTOR = 2;
    private long maxUploadSize = MAX_UPLOAD_SIZE;
    private long maxUploadDirSize = MAX_UPLOAD_SIZE * UPLOADED_DIR_SIZE_FACTOR;
    JakartaServletFileUpload upload;

    @Override
    public void init() throws ServletException {

	super.init();
	try {
	    String mus = this.getInitParameter("maxUploadSize");
	    if (mus != null) {
		maxUploadSize = Long.valueOf(mus).longValue();
		log.fine("MaxUploadSize set from servlet \"maxUploadSize\" init parameter");
	    } else
		log.info("\"maxUploadSize\" servlet init parameter not present");
	} catch (NumberFormatException nfe) {
	    log.severe(nfe.getMessage());
	}
	try {
	    String mus = this.getInitParameter("uploadedDirSizeFactor");
	    if (mus != null) {
		maxUploadDirSize = Long.valueOf(mus).longValue() * maxUploadSize;
		log.fine("uploadedDirSizeFactor set from servlet \"maxUploadSize\" init parameter");
	    } else
		log.info("\"uploadedDirSizeFactor\" servlet init parameter not present");
	} catch (NumberFormatException nfe) {
	    log.severe(nfe.getMessage());
	}
	upload = new JakartaServletDiskFileUpload();
	// maximum accepted size, upload is stopped (during
	// upload.parseRequest()) if exceeded
	upload.setFileSizeMax(maxUploadSize);
	log.fine("MaxUploadSize set to: " + maxUploadSize + " bytes");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.info("GET in corso...");
	HttpSession session = request.getSession(false);
	String fileName = request.getPathInfo().substring(1);
	String errorMsg = null;
	if (session == null) {
	    log.warning("session == null!");
	    sendErrorMsg(response, "Sessione scaduta o cookies disabilitati.");
	    return;
	}
	if (fileName == null) {
	    log.warning("fileName == null! - error getting pathInfo");
	    sendErrorMsg(response, "Errore nel recupero del path info.");
	    return;
	}
	String subDir = session.getId();
	String realFileName = (String) session.getAttribute(fileName);
	String fs = System.getProperty("file.separator");
	File f = new File(getServletContext().getRealPath("/WEB-INF/files") + fs + subDir + fs + realFileName);
	if (f.exists()) {
	    // Setting correct content type
	    response.setContentType(getServletContext().getMimeType(fileName));
	    ServletOutputStream os = response.getOutputStream();
	    try {
		FileInputStream fis = new FileInputStream(f);
		byte[] contentBytes = IOUtils.toByteArray(fis);
		sendContent(contentBytes, os);
		os.flush();
		fis.close();
		log.info("Sent \"" + realFileName + "\" (" + fileName + ") to client " + request.getRemoteAddr() + " in session " + subDir);
	    } catch (Exception e) {
		log.severe(e.getMessage());
		errorMsg = "Errore nell'estrazione del contenuto: " + e.getMessage();
	    }
	} else {
	    errorMsg = "Il file non è stato trovato";
	    log.warning("file '" + f.getPath() + "' not found!");
	}
	if (errorMsg != null)
	    sendErrorMsg(response, errorMsg);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	boolean isApiRequest = isValidationApiRequest(request);
	StringBuffer header = new StringBuffer("");
	Vector<ProcessedItem> processedItems = new Vector<ProcessedItem>();
	Vector<String> errors = new Vector<String>();
	response.setContentType(isApiRequest ? "application/json" : "text/html");
	response.setCharacterEncoding("UTF-8");
	log.info("POST in corso..." + (isApiRequest ? " [API]" : ""));
	HttpSession session = request.getSession(false);
	// We set maximum session duration of a session after second request
	// equal to MaxInactiveInterval, so that the possibility to upload new
	// files
	// is at maximum 2 * MaxInactiveInterval long.
	// When session is invalidated, all related uploaded file are cleaned by
	// the session listener.
	long duration = -1;
	if (session != null) {
	    duration = session.getLastAccessedTime() - session.getCreationTime();
	    if (duration > (session.getMaxInactiveInterval() * 1000)) {
		session.invalidate();
		duration = 0;
		session = request.getSession(true);
	    }
	} else
	    session = request.getSession(true);
	if (session != null) {
	// Check that we have a file upload request
	    boolean isMultipart = JakartaServletFileUpload.isMultipartContent(request);
	    if (isMultipart) {
		String sessionId = session.getId();
		header.append("Sessione " + sessionId + ((duration > 0) ? (": " + duration / 1000 + " secondi") : ""));
		// Parse the request
		try {
		    List<FileItem> items = upload.parseRequest(request);
		    if (items.isEmpty()) {
			errors.add("Nessun elemento da elaborare!!");
			log.warning("No element to process");
		    }
		    for (FileItem item : items) {
			ProcessedItem pi = null;
			if (item.isFormField()) {
			    pi = processFormField(item);
			} else {
			    pi = processUploadedFile(item, sessionId);
			    if (pi != null) {
				session.setAttribute(pi.getFileName(), pi.getWritedFileName());
				log.info("Processed uploaded file \"" + pi.getFileName() + "\" from client " + request.getRemoteAddr()
					+ " in session " + sessionId);
				//esiste solo per p7m e p7d
				if (pi.getContentFileName() != null) {
				    session.setAttribute(pi.getContentFileName(), pi.getContentWritedFileName());
				    log.info("Saved clear file \"" + pi.getContentFileName() + "\" sent from client " + request.getRemoteAddr()
					    + " in session " + sessionId);
				}
			    }
			}
			if (pi != null)
			    processedItems.add(pi);
		    }
		} catch (Exception e) {
		    if (e.getClass().getSimpleName().contains("FileSizeLimitExceeded")) {
			log.severe(e.getMessage());
			errors.add("Il file supera il limite di " + upload.getFileSizeMax() + " bytes");
		    } else {
			log.severe(e.getMessage());
			errors.add(e.getMessage());
		    }
		}
	    }
	    request.setAttribute("processedItems", processedItems);
	}
	if (isApiRequest) {
	    sendValidationApiResponse(response, processedItems, errors);
	    return;
	}
	request.setAttribute("headerVerifica", header);
	request.setAttribute("errors", errors);
	this.getServletContext().getRequestDispatcher("/validazione.jsp").forward(request, response);
    }

    /**
     * Ritorna true se la richiesta è per l'API (POST /validazione/api).
     */
    private boolean isValidationApiRequest(HttpServletRequest request) {
	String pathInfo = request.getPathInfo();
	return pathInfo != null && "/api".equals(pathInfo.trim());
    }

    /**
     * Scrive la risposta JSON per l'API di validazione (stesso contratto per chiamate esterne).
     */
    private void sendValidationApiResponse(HttpServletResponse response, Vector<ProcessedItem> processedItems, Vector<String> errors) throws IOException {
	ValidationApiResponse apiResponse = new ValidationApiResponse();
	if (!errors.isEmpty()) {
	    ValidationApiResponse.ValidationApiResultItem errItem = new ValidationApiResponse.ValidationApiResultItem();
	    errItem.setFileName(null);
	    errItem.setValid(false);
	    errItem.setValidationErrorMessage(String.join("; ", errors));
	    apiResponse.getResults().add(errItem);
	}
	for (ProcessedItem pi : processedItems) {
	    if (!pi.isFileUpload()) continue;
	    ValidationApiResponse.ValidationApiResultItem item = new ValidationApiResponse.ValidationApiResultItem();
	    item.setFileName(pi.getFileName());
	    ValidationResultDTO dto = pi.getValidationResult();
	    if (dto == null) {
		item.setValid(pi.getErrors() != null && !pi.getErrors().isEmpty());
		item.setValidationErrorMessage(pi.getErrors() != null && !pi.getErrors().isEmpty() ? String.join("; ", pi.getErrors()) : "Nessun risultato di validazione");
	    } else {
		item.setValid(!dto.hasError());
		item.setValidationErrorMessage(dto.getValidationErrorMessage());
		item.setSummary(dto.getSimpleReportSummary());
		item.setSimpleReportXml(dto.getSimpleReportXml());
		item.setDetailedReportXml(dto.getDetailedReportXml());
	    }
	    apiResponse.getResults().add(item);
	}
	ObjectMapper mapper = new ObjectMapper();
	mapper.writeValue(response.getOutputStream(), apiResponse);
    }

    private ProcessedItem processUploadedFile(FileItem item, String subDir) {

	String baseFileName = null;
	ProcessedItem pi = null;
	// Process a file upload
	if (!item.isFormField()) {
	    pi = new ProcessedItem(item.getFieldName(), !item.isFormField());
	    String fileName = item.getName();
	    // IE insists to provide full path, get only file name (via commons IO).
	    if (fileName != null) {
		baseFileName = FilenameUtils.getName(fileName);
		pi.setFileName(baseFileName);
		pi.setSize(item.getSize());
		pi.setInMemory(item.isInMemory());
		pi.setContentType(item.getContentType());
	    }
	    if (baseFileName != null) {
		File filesDir = new File(getServletContext().getRealPath("/WEB-INF/files"));
		File sd = new File(filesDir, subDir);
		sd.mkdirs();
		// check maximum size for directory containing uploaded files
		if (FileUtils.sizeOfDirectory(filesDir) > maxUploadDirSize) {
		    log.warning("MaxUploadDirSize of " + maxUploadDirSize + " bytes exceeded, not accepting more files at the moment");
		    pi.getErrors().add("Impossibile verificare ulteriori file, attendere qualche minuto!");
		} else {
		    String writedFileName;
		    File uploadedFile = null;
		    try {
			byte[] fileBytes = IOUtils.toByteArray(item.getInputStream());
			writedFileName = getMD5Checksum(new ByteArrayInputStream(fileBytes));
			uploadedFile = new File(sd, writedFileName);
			FileOutputStream fos = new FileOutputStream(uploadedFile);
			fos.write(fileBytes);
			fos.close();
			pi.setWritedFileName(writedFileName);
			pi.setFullFileName(uploadedFile.getAbsolutePath());
		    } catch (Exception e) {
			log.severe(e.getMessage());
			pi.getErrors().add(e.getMessage());
		    }
		    if (pi.getErrors().isEmpty()) {
			try {
			    byte[] documentBytes = IOUtils.toByteArray(new FileInputStream(uploadedFile));
			    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
			    SignatureValidationService validationService = (SignatureValidationService) wac.getBean("dss.validation.validationservice");
			    ValidationResultDTO result = validationService.validate(documentBytes, baseFileName, true, false);
			    pi.setValidationResult(result);
			    if (result.hasError()) {
				pi.getErrors().add(result.getValidationErrorMessage());
			    } else if (result.getExtractedContent() != null && result.getExtractedContent().length > 0) {
				pi.setContentFileName(result.getExtractedContentFileName());
				ByteArrayInputStream bais = new ByteArrayInputStream(result.getExtractedContent());
				String writedContent = getMD5Checksum(bais);
				File writedContentFile = new File(sd, writedContent);
				FileOutputStream fos = new FileOutputStream(writedContentFile);
				fos.write(result.getExtractedContent());
				fos.flush();
				fos.close();
				pi.setContentWritedFileName(writedContent);
			    }
			} catch (Exception e) {
			    e.printStackTrace();
			    pi.getErrors().add("Errore nell'inizializzazione della verifica: " + e.getMessage());
			}
		    }
		}
	    }
	}
	return pi;
    }

    private ProcessedItem processFormField(FileItem item) {

	ProcessedItem pi = null;
	// Process a regular form field
	if (item.isFormField()) {
	    pi = new ProcessedItem(item.getFieldName(), !item.isFormField());
	    pi.setString(item.getString());
	}
	return pi;
    }

    private static String getMD5Checksum(InputStream fis) throws Exception {

	byte[] b = createChecksum(fis);
	String result = "";
	for (int i = 0; i < b.length; i++) {
	    result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
	}
	return result;
    }

    private static byte[] createChecksum(InputStream fis) throws Exception {

	byte[] buffer = new byte[1024];
	MessageDigest complete = MessageDigest.getInstance("MD5");
	int numRead;
	do {
	    numRead = fis.read(buffer);
	    if (numRead > 0) {
		complete.update(buffer, 0, numRead);
	    }
	} while (numRead != -1);
	fis.close();
	return complete.digest();
    }

    private void sendContent(byte[] bytes, OutputStream os) throws IOException {

	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
	byte[] buffer = new byte[4096]; // A buffer to hold file contents
	int bytes_read; // How many bytes in buffer
	// Read a chunk of bytes into the buffer, then write them out,
	// looping until we reach the end of the file (when read() returns -1).
	// Note the combination of assignment and comparison in this while
	// loop. This is a common I/O programming idiom.
	while ((bytes_read = bais.read(buffer)) != -1) { // Read bytes until EOF
	    os.write(buffer, 0, bytes_read); // write bytes
	}
    }

    private void sendErrorMsg(HttpServletResponse response, String errorMsg) {

	response.setContentType("text/html");
	try {
	    response.getWriter().println("<h3>Errore</h3><p>" + errorMsg + "</p>");
	} catch (IOException e) {
	    log.severe(e.getMessage());
	}
    }
}
