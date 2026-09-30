package it.gruppoinit.pal.gp.backoffice.jws;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

public class TransferUtils2 {

    private long contentLength;
    private long contentLoaded;
    private boolean debug = false;

    public TransferUtils2(boolean debug) {

	this.debug = debug;
    }

    public String downloadFile(String url) throws Exception {

	URL u = new URL(url);
	URLConnection uc = u.openConnection();
	HttpURLConnection httpConn = (HttpURLConnection) uc;
	int responseCode = httpConn.getResponseCode();
	contentLength = uc.getContentLength();
	contentLoaded = 0;
	log("downloadFile: responseCode=" + responseCode);
	if (responseCode != 200 && responseCode != 302) {
	    System.err.println("Errore durante il download del file. (" + responseCode + " - " + httpConn.getResponseMessage() + ")");
	    try {
		InputStream is = httpConn.getErrorStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[5096];
		while (bis.read(buffer) != -1) {
		    baos.write(buffer);
		}
		System.err.println("Error Stream: " + baos.toString("UTF-8"));
	    } catch (Exception e) {
		// error reading error stream
	    }
	    throw new Exception("Errore durante il download del file. (" + responseCode + " - " + httpConn.getResponseMessage() + ")");
	}
	if (responseCode == 302) {
	    String url2 = httpConn.getHeaderField("Location");
	    log("Redirect to: " + url2);
	    URL u2 = new URL(url2);
	    File f;
	    try {
		f = new File(u2.toURI());
	    } catch (URISyntaxException e) {
		f = new File(u2.getPath());
	    }
	    contentLength = f.length();
	    log("Content Length: " + contentLength + " bytes");
	    String fileNameExt = "";
	    String fileName = f.getName();
	    fileNameExt = fileName.substring(fileName.lastIndexOf("."), fileName.length());
	    fileNameExt = fileNameExt.toLowerCase();
	    File outfile = File.createTempFile("FILE_TEMPORANEO_", fileNameExt);
	    FileOutputStream fout = new FileOutputStream(outfile);
	    FileInputStream fin = new FileInputStream(f);
	    FileChannel fcout = fout.getChannel();
	    FileChannel fcin = fin.getChannel();
	    ByteBuffer buffer = ByteBuffer.allocate(1024);
	    while (true) {
		buffer.clear();
		int r = fcin.read(buffer);
		if (r == -1) {
		    break;
		}
		contentLoaded += r;
		buffer.flip();
		fcout.write(buffer);
	    }
	    fcin.close();
	    fcout.close();
	    log("Content Downloaded: " + contentLoaded + " bytes");
	    log("Saved to: " + outfile.getPath());
	    return outfile.getPath();
	} else {
	    log("Content Length: " + contentLength + " bytes");
	    String contentDispHeader = uc.getHeaderField("Content-Disposition");
	    if (contentDispHeader == null) {
		throw new Exception("Errore durante il download del file. (" + responseCode + " - " + httpConn.getResponseMessage() + ")");
	    }
	    String fileNameExt = contentDispHeader.substring(contentDispHeader.lastIndexOf("."), contentDispHeader.length());
	    fileNameExt = fileNameExt.toLowerCase();
	    if (fileNameExt.endsWith("\"")) {
		fileNameExt = fileNameExt.replaceAll("\"", "");
	    }
	    File outfile = File.createTempFile("FILE_TEMPORANEO_", fileNameExt);
	    FileOutputStream fout = new FileOutputStream(outfile);
	    FileChannel fcout = fout.getChannel();
	    ByteBuffer buffer = ByteBuffer.allocate(1024);
	    ReadableByteChannel rbcin = Channels.newChannel(uc.getInputStream());
	    while (true) {
		buffer.clear();
		int r = rbcin.read(buffer);
		if (r == -1) {
		    break;
		}
		contentLoaded += r;
		buffer.flip();
		fcout.write(buffer);
	    }
	    rbcin.close();
	    fcout.close();
	    log("Content Downloaded: " + contentLoaded + " bytes");
	    log("Saved to: " + outfile.getPath());
	    return outfile.getPath();
	}
    }

    public void uploadFile(String url, String file) throws Exception {

	// §§§BEGIN§§§
	File f = new File(file);
	contentLength = f.length();
	contentLoaded = 0;
	log("Content Length: " + contentLength + " bytes");
	FileInputStream fis = new FileInputStream(f);
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	URL u = new URL(url);
	HttpURLConnection conn = (HttpURLConnection) u.openConnection();
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setUseCaches(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Connection", "Keep-Alive");
	conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	FileChannel fcin = fis.getChannel();
	WritableByteChannel wbcout = Channels.newChannel(conn.getOutputStream());
	ByteBuffer buffer = ByteBuffer.allocate(1024);
	StringBuffer prolog = new StringBuffer();
	prolog.append(twoHyphens + boundary + lineEnd);
	prolog.append("Content-Disposition: form-data;name=\"fileUpload\";filename=\"" + f.getName() + "\"" + lineEnd);
	prolog.append(lineEnd);
	buffer.put(prolog.toString().getBytes("UTF-8"));
	buffer.flip();
	wbcout.write(buffer);
	long temp = 0;
	while (true) {
	    buffer.clear();
	    int r = fcin.read(buffer);
	    if (r == -1) {
		break;
	    }
	    if (temp == 0) {
		temp = r;
	    } else {
		contentLoaded += r;
	    }
	    buffer.flip();
	    wbcout.write(buffer);
	}
	StringBuffer end = new StringBuffer();
	end.append(lineEnd);
	end.append(twoHyphens + boundary + twoHyphens + lineEnd);
	buffer.put(end.toString().getBytes("UTF-8"));
	buffer.flip();
	wbcout.write(buffer);
	fcin.close();
	wbcout.close();
	f.delete();
	// read & parse the response
	int status = conn.getResponseCode();
	log("Response Code: " + status);
	if (status != 200) {
	    throw new Exception("Errore durante il caricamento del file. (" + status + " - " + conn.getResponseMessage() + ")");
	}
	InputStream is = conn.getInputStream();
	StringBuilder response = new StringBuilder();
	byte[] respBuffer = new byte[4096];
	while (is.read(respBuffer) >= 0) {
	    response.append(new String(respBuffer).trim());
	}
	contentLoaded += temp;
	log("Content Uploaded: " + contentLoaded + " bytes");
	log("RESPONSE: '" + response.toString() + "'");
	is.close();
	// §§§END§§§
    }

    public long getContentLength() {

	return contentLength;
    }

    public long getContentLoaded() {

	return contentLoaded;
    }

    private void log(String msg) {

	if (debug) {
	    System.out.println("DEBUG: " + msg);
	}
    }

    public static void main(String[] args) throws Exception {

	// §§§BEGIN§§§
	String url = "http://devel3.init.gruppoinit.it/vbg";
	String fileId = "2269";
	String token = "effc2e6a-b50c-4da8-a6ec-fac5a8370cac";
	String path = "";
	Date start = new Date();
	System.out.println(start);
	TransferUtils2 t2 = new TransferUtils2(true);
	path = t2.downloadFile(url + "/file/ajaxDownload.htm?fileId=" + fileId + "&Token=" + token);
	System.out.println(path);
	Date stop = new Date();
	System.out.println("TIME: " + (stop.getTime() - start.getTime()));
	/*
	Date start1 = new Date();
	String outFile = "C:/Temp/SIGEPRO_SPOLETO_PRE_1_10.zip";
	t2.uploadFile(url + "/file/ajaxUpdate.htm?fileId=" + fileId + "&Token=" + token, outFile);
	Date stop1 = new Date();
	System.out.println("TIME: " + (stop1.getTime() - start1.getTime()));
	*/
	// §§§END§§§
    }
}
