package it.gruppoinit.pal.firma.servlet;

import it.gruppoinit.pal.firma.FileInfo;
import it.gruppoinit.pal.firma.FileManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.axiom.attachments.CachedFileDataSource;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class UploadFileToSignServlet
 */
public class UploadFileToSignServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private ServletFileUpload upload;
    private FileManager fileManager;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileToSignServlet() {

	super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.info("POST in corso...");
	// Check that we have a file upload request
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	if (isMultipart) {
	    // Parse the request
	    try {
		List<DiskFileItem> items = upload.parseRequest(request);
		if (items.isEmpty()) {
		    log.error("No element to process");
		    throw new ServletException("La request non contiene file da processare");
		}
		String sessionId = "";
		// Process the uploaded items
		Iterator<DiskFileItem> iter = items.iterator();
		while (iter.hasNext()) {
		    DiskFileItem item = iter.next();
		    if (item.isFormField()) {
			String fieldName = item.getFieldName();
			if ("sessionId".equals(fieldName)) {
			    sessionId = item.getString();
			    break;
			}
		    }
		}
		iter = items.iterator();
		while (iter.hasNext()) {
		    DiskFileItem item = iter.next();
		    if (!item.isFormField()) {
			DataHandler dataHandler = getFileParameter(item);
			FileInfo fileInfo = fileManager.upload(dataHandler, getFileName(item), sessionId, null);
			sessionId = fileInfo.getSessionId();
			break;
		    }
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("{\"sessionId\":\"" + sessionId + "\"}");
		out.close();
		log.info("POST in corso...end!");
	    } catch (Exception e) {
		if (e instanceof org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException) {
		    log.error("Il file supera il limite di {} bytes", upload.getFileSizeMax());
		} else {
		    log.error(e.getMessage());
		}
		throw new ServletException();
	    }
	} else {
	    log.error("il contenuto della request non è di tipo multipart");
	    throw new ServletException();
	}
    }

    @Override
    public void init() throws ServletException {

	super.init();
	// Create a factory for disk-based file items
	DiskFileItemFactory factory = new DiskFileItemFactory();
	// Create a new file upload handler
	upload = new ServletFileUpload(factory);
	fileManager = new FileManager();
	// maximum accepted size, upload is stopped (during upload.parseRequest()) if exceeded
	//upload.setFileSizeMax(maxUploadSize);
	//log.debug("MaxUploadSize set to: {} bytes", maxUploadSize);
    }

    private String getFileName(DiskFileItem item) {

	String fileName = item.getName();
	// IE insists to provide full path, get only file name (via commons IO).
	fileName = FilenameUtils.getName(fileName);
	return fileName;
    }

    private DataHandler getFileParameter(DiskFileItem diskFileItem) throws Exception {

	DataSource dataSource;
	if (diskFileItem.isInMemory()) {
	    dataSource = new ByteArrayDataSource(diskFileItem.get());
	} else {
	    // TODO: must create the original DataSource,
	    //       because the cache file is deleted.
	    //       maybe, the diskFileItem is not referenced from any object.
	    dataSource = new CachedFileDataSource(diskFileItem.getStoreLocation());
	}
	DataHandler dataHandler = new DataHandler(dataSource);
	return dataHandler;
    }
}
