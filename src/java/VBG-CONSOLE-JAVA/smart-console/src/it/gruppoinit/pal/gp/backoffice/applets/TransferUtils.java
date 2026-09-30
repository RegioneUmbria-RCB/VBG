package it.gruppoinit.pal.gp.backoffice.applets;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TransferUtils {

    private long contentLength;
    private long contentLoaded;

    public String downloadFile(String url) throws Exception {

	// §§§BEGIN§§§
	URL u = new URL(url);
	URLConnection uc = u.openConnection();
	//per la progress bar
	contentLength = uc.getContentLength();
	contentLoaded = 0;
	String nomeFile = uc.getHeaderField("Content-Disposition");
	String ext = nomeFile.substring(nomeFile.lastIndexOf("."), nomeFile.length());
	if (ext != null) {
	    ext = ext.toLowerCase();
	}
	InputStream raw = uc.getInputStream();
	InputStream in = new BufferedInputStream(raw);
	byte[] data = new byte[(int) contentLength];
	int offset = 0;
	while (offset < contentLength) {
	    int bytesRead = in.read(data, offset, data.length - offset);
	    if (bytesRead == -1)
		break;
	    offset += bytesRead;
	    //per la progress bar
	    contentLoaded = offset;
	}
	in.close();
	if (offset != contentLength) {
	    throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
	}
	File outFile = File.createTempFile("FILE_TEMPORANEO_", ext);
	FileOutputStream out = new FileOutputStream(outFile);
	out.write(data);
	out.flush();
	out.close();
	return outFile.getPath();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public void uploadFile(String urlString, String file) throws Exception {

	// §§§BEGIN§§§
	File f = new File(file);
	//per la progress bar
	contentLength = (int) f.length();
	contentLoaded = 0;
	FileInputStream fis = new FileInputStream(f);
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary = "*****";
	URL url = new URL(urlString);
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setDoInput(true);
	conn.setDoOutput(true);
	conn.setUseCaches(false);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Connection", "Keep-Alive");
	conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
	DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	dos.writeBytes(twoHyphens + boundary + lineEnd);
	dos.writeBytes("Content-Disposition: form-data;name=\"fileUpload\";filename=\"" + f.getName() + "\"" + lineEnd);
	dos.writeBytes(lineEnd);
	// create a buffer of maximum size
	int bytesRead, bufferSize, bytesAvailable;
	byte[] buffer;
	int maxBufferSize = 1 * 1024 * 1024;
	bytesAvailable = fis.available();
	bufferSize = Math.min(bytesAvailable, maxBufferSize);
	buffer = new byte[bufferSize];
	// read file and write it into form...
	bytesRead = fis.read(buffer, 0, bufferSize);
	while (bytesRead > 0) {
	    dos.write(buffer, 0, bufferSize);
	    bytesAvailable = fis.available();
	    bufferSize = Math.min(bytesAvailable, maxBufferSize);
	    bytesRead = fis.read(buffer, 0, bufferSize);
	    //per la progress bar
	    contentLoaded += bytesRead;
	}
	//per la progress bar
	contentLoaded = contentLength;
	// send multipart form data necesssary after file data...
	dos.writeBytes(lineEnd);
	dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
	// close streams
	fis.close();
	dos.flush();
	dos.close();
	// read & parse the response
	InputStream is = conn.getInputStream();
	StringBuilder response = new StringBuilder();
	byte[] respBuffer = new byte[4096];
	while (is.read(respBuffer) >= 0) {
	    response.append(new String(respBuffer).trim());
	}
	is.close();
	// §§§END§§§
    }

    public long getContentLength() {

	return contentLength;
    }

    public long getContentLoaded() {

	return contentLoaded;
    }

    public static void main(String[] args) throws Exception {

	// §§§BEGIN§§§
	TransferUtils t = new TransferUtils();
	String path = t
		.downloadFile("http://devel3.init.gruppoinit.it/vbg/file/ajaxDownload.htm?fileId=356441&Token=c0540047-cb20-4b48-9de8-28145cc07d83");
	System.out.println(path);
	//t.uploadFile("http://devel3.init.gruppoinit.it/vbg/file/ajaxUpdate.htm?fileId=356428&Token=780aad74-f4ee-406c-aa39-77ad30f06cf8","C:/Temp/prova.txt");
	// §§§END§§§
    }
}
