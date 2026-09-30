package it.gruppoinit.pal.firma.servlet;

import it.gruppoinit.pal.firma.FileInfoDH;
import it.gruppoinit.pal.firma.FileManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;

/**
 * Servlet implementation class DownloadSignedFileServlet
 */
public class DownloadSignedFileServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private FileManager fileManager;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadSignedFileServlet() {

	super();
	fileManager = new FileManager();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.info("GET in corso...");
	String sessionId = request.getParameter("sessionId");
	String fileId = request.getParameter("fileId");
	String errorMsg = null;
	try {
	    FileInfoDH fileInfoDH = fileManager.download(sessionId, fileId);
	    if (BooleanUtils.isTrue(fileInfoDH.getIsSigned())) {
		// Setting correct content type
		response.setContentType("application/pkcs7-mime");
		response.setHeader("Content-disposition", "attachment; filename=" + fileInfoDH.getFileName());
		ServletOutputStream os = response.getOutputStream();
		InputStream is = fileInfoDH.getDh().getInputStream();
		byte[] contentBytes = IOUtils.toByteArray(is);
		sendContent(contentBytes, os);
		os.flush();
		is.close();
		log.info("Sent \"" + fileInfoDH.getFileName() + "\" (" + fileId + ") to client " + request.getRemoteAddr() + " in session "
			+ sessionId);
	    } else {
		throw new Exception("Il file '" + fileInfoDH.getFileName() + "' non è ancora stato firmato");
	    }
	} catch (Exception e) {
	    log.error(e.getMessage());
	    errorMsg = e.getMessage();
	}
	if (errorMsg != null)
	    sendErrorMsg(response, errorMsg);
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
	    log.error(e.getMessage());
	}
    }
}
