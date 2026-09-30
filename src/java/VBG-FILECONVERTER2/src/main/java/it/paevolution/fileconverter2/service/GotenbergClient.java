package it.paevolution.fileconverter2.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.paevolution.fileconverter2.service.BaseService.FileInTypesEnum;
import it.paevolution.fileconverter2.util.Configurazione;
import it.paevolution.fileconverter2.util.Utils;

/**
 * Gotenberg is a Docker-powered stateless API for converting HTML, Markdown and Office documents to PDF.
 * <p>
 * This java client can be used to send a HTML file to the docker service to receive a PDF file based on the Gotenberg
 * API. This implementation uses pure java based on JDK 1.8 and did not depend on any external library. You can use this
 * client on your own code base or as a scaffold for a custom implementation.
 * <p>
 * The data is expected in UTF-8 encoding
 * 
 * @author rsoika
 * @version 1.0
 * @see https://github.com/thecodingmachine/gotenberg/
 * @see https://www.codejava.net/java-se/networking/upload-files-by-sending-multipart-request-programmatically
 * 
 */
public class GotenbergClient {

    private static final String LINE_FEED = "\r\n";
    private static final Logger log = LoggerFactory.getLogger(GotenbergClient.class);
    private String gotenbertEndpoint;
    private boolean isPDFA;
    private String pdfAVersion;
    private String fileName;

    public GotenbergClient(Configurazione configurazione) {

	this.gotenbertEndpoint = configurazione.getGotenbergBaseUrl();
	if (!gotenbertEndpoint.endsWith("/")) {
	    gotenbertEndpoint = gotenbertEndpoint + "/";
	}
	this.isPDFA = configurazione.isGotenbergPdfA();
	this.pdfAVersion = configurazione.getGotenbergPdfAVersion();
	
	this.fileName = "temp-doc-out-g-" + System.currentTimeMillis();
    }

    public byte[] convertDoc(InputStream is, FileInTypesEnum contentType, String outputExtension) throws DocumentConvertionException {

	if (contentType.equals(FileInTypesEnum.HTML)) {
	    return convertHTML(is);
	}
	return convertDoc(is, outputExtension);
    }

    private byte[] convertHTML(InputStream inputStream) throws DocumentConvertionException {

	try {
	    byte[] pdfResult;
	    // creates a unique boundary based on time stamp
	    String boundary = boundary();
	    //URL url = new URL(gotenbertEndpoint + "forms/chromium/convert/html")
	    URL url = URI.create(gotenbertEndpoint + "forms/chromium/convert/html").toURL();
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	    httpConn.setUseCaches(false);
	    httpConn.setDoOutput(true); // indicates POST method
	    httpConn.setDoInput(true);
	    httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	    OutputStream outputStream = httpConn.getOutputStream();
	    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
	    // add html file...
	    writer.append("--" + boundary).append(LINE_FEED);
	    writer.append("Content-Disposition: form-data; name=\"fileUpload\"; filename=\"index.html\"").append(LINE_FEED);
	    writer.append("Content-Type: text/html").append(LINE_FEED).append(LINE_FEED);
	    // writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED).append(LINE_FEED);
	    writer.flush();
	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
		outputStream.write(buffer, 0, bytesRead);
	    }
	    outputStream.flush();
	    inputStream.close();
	    writer.append(LINE_FEED);
	    writer.flush();
	    if (isPDFA) {
		writePDFAParams(writer, boundary);
	    }
	    writer.append("--" + boundary + "--").append(LINE_FEED);
	    writer.close();
	    // checks server's status code first
	    int status = httpConn.getResponseCode();
	    log.debug("http con response code = {}", status);
	    if (status == HttpURLConnection.HTTP_OK) {
		InputStream in = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		in = httpConn.getInputStream();
		byte[] block = new byte[1024];
		int length;
		while ((length = in.read(block)) != -1) {
		    bout.write(block, 0, length);
		}
		in.close();
		pdfResult = bout.toByteArray();
		httpConn.disconnect();
	    } else {
		throw new IOException("Server connection failed - status: " + status);
	    }
	    return pdfResult;
	} catch (IOException e) {
	    log.error("Errore nella conversione del file", e);
	    throw new DocumentConvertionException(e);
	}
    }

    private byte[] convertDoc(InputStream inputStream, String outputExtension) throws DocumentConvertionException {

	try {
	    byte[] pdfResult;
	    // creates a unique boundary based on time stamp
	    String boundary = boundary();
	    //URL url = new URL(gotenbertEndpoint + "forms/libreoffice/convert")
	    URL url = URI.create(gotenbertEndpoint + "forms/libreoffice/convert").toURL();
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	    httpConn.setUseCaches(false);
	    httpConn.setDoOutput(true); // indicates POST method
	    httpConn.setDoInput(true);
	    httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	    OutputStream outputStream = httpConn.getOutputStream();
	    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
	    // add html file...
	    writer.append("--" + boundary).append(LINE_FEED);
	    writer.append("Content-Disposition: form-data; name=\"fileUpload\"; filename=\"" + fileName + "." + outputExtension + "\"")
		    .append(LINE_FEED);
	    writer.append("Content-Type: " + Utils.getMimeFromExtension(outputExtension)).append(LINE_FEED);
	    writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
	    writer.append(LINE_FEED);
	    writer.flush();
	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;
	    while ((bytesRead = inputStream.read(buffer)) != -1) {
		outputStream.write(buffer, 0, bytesRead);
	    }
	    outputStream.flush();
	    inputStream.close();
	    writer.append(LINE_FEED);
	    writer.flush();
	    if (isPDFA) {
		writePDFAParams(writer, boundary);
	    }
	    writer.append("--" + boundary + "--").append(LINE_FEED);
	    writer.close();
	    // checks server's status code first
	    int status = httpConn.getResponseCode();
	    log.debug("http con response code = {}", status);
	    if (status == HttpURLConnection.HTTP_OK) {
		InputStream in = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		in = httpConn.getInputStream();
		byte[] block = new byte[1024];
		int length;
		while ((length = in.read(block)) != -1) {
		    bout.write(block, 0, length);
		}
		in.close();
		pdfResult = bout.toByteArray();
		httpConn.disconnect();
	    } else {
		throw new IOException("Server connection failed - status: " + status);
	    }
	    return pdfResult;
	} catch (IOException e) {
	    log.error("Errore nella conversione del file", e);
	    throw new DocumentConvertionException(e);
	}
    }

    private void writePDFAParams(PrintWriter writer, String boundary) {

	// --form pdfa=PDF/A-1b
	writer.append("--" + boundary).append(LINE_FEED);
	writer.append("Content-Disposition: form-data; name=\"pdfa\"").append(LINE_FEED);
	writer.append("Content-Type: text/plain").append(LINE_FEED);
	writer.append(LINE_FEED);	
	writer.append(pdfAVersion);	
	writer.append(LINE_FEED);
	writer.flush();
	// --form pdfua=true
	writer.append("--" + boundary).append(LINE_FEED);
	writer.append("Content-Disposition: form-data; name=\"pdfua\"").append(LINE_FEED);
	writer.append("Content-Type: text/plain").append(LINE_FEED);
	writer.append(LINE_FEED);
	writer.append("true");
	writer.append(LINE_FEED);
	writer.flush();
	//
	//	writer.append("--" + boundary).append(LINE_FEED);
	//	writer.append("Content-Disposition: form-data; name=\"metadata\"").append(LINE_FEED);
	//	writer.append("Content-Type: application/json").append(LINE_FEED);
	//	writer.append(LINE_FEED);
	//	writer.append("{\"Creator\": \"Personio\", \"Producer\": \"Workzag\", \"Title\": \"Titolo del doc\" }");
	//	writer.append(LINE_FEED);
	//	writer.flush();
    }

    private String boundary() {

	return "------------------------" + System.currentTimeMillis();
    }
}