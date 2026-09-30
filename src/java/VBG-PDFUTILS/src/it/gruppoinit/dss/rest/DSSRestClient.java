package it.gruppoinit.dss.rest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.dss.rest.models.FileOriginaleDSSBean;


public class DSSRestClient {
    
    private static final Logger log = LoggerFactory.getLogger(DSSRestClient.class);
    private int connectionTimeOut = 12000;
    private int receivetimeout = 600000;
    public static final String FORMAT_DD_MM_YYYY = "dd-MM-yyyy";
    public static final String CHAR_SEPARATORE = "-";
    public static final int STOPTHREADFIRST = 1000;
    public static final int STOPTHREADLOOP = 2000;
    public static final int MAX_LOOP = 5;
    
    private String rootUrl;

    public DSSRestClient(String rootUrl) {
    	this.rootUrl = rootUrl;
    }
    
    public FileOriginaleDSSBean checkFirmaScaricaFileNonFirmato(DataHandler dh, String nomefile) {

        log.debug("checkFirmaScaricaFileNonFirmato called");

        String endpoint = getRootUrl() + "/api/checkfirma/scaricaFileNonFirmato";
        String boundary = "----Boundary" + System.currentTimeMillis();

        HttpURLConnection conn = null;

        try {
            URL url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            conn.setConnectTimeout(connectionTimeOut);
            conn.setReadTimeout(receivetimeout);

            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream out = conn.getOutputStream();

            // Parte del file
            StringBuilder sb = new StringBuilder();
            sb.append("--").append(boundary).append("\r\n");
            sb.append("Content-Disposition: form-data; name=\"documento\"; filename=\"")
              .append(nomefile)
              .append("\"\r\n");
            sb.append("Content-Type: application/octet-stream\r\n\r\n");

            out.write(sb.toString().getBytes("UTF-8"));

            InputStream fileInput = dh.getInputStream();
            byte[] buffer = new byte[8192];
            int len;

            while ((len = fileInput.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

            fileInput.close();

            out.write("\r\n".getBytes("UTF-8"));
            out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));

            out.flush();
            out.close();

            int status = conn.getResponseCode();

            InputStream responseStream;

            if (status == HttpURLConnection.HTTP_OK) {

                responseStream = conn.getInputStream();

                FileOriginaleDSSBean bean = new FileOriginaleDSSBean();

                String disposition = conn.getHeaderField("Content-Disposition");
                if (disposition != null) {
                    String filename = extractFilename(disposition);
                    bean.setFileName(filename);
                }

                bean.setContentType(conn.getContentType());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ((len = responseStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }

                bean.setContent(baos.toByteArray());

                responseStream.close();

                return bean;

            } else {

                responseStream = conn.getErrorStream();

                StringBuilder error = new StringBuilder();

                if (responseStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream, "UTF-8"));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        error.append(line).append('\n');
                    }

                    reader.close();
                }

                log.error("checkFirmaScaricaFileNonFirmato: {}", error);

                throw new RuntimeException("Errore verifica firma:\n" + error);
            }

        } catch (Exception e) {
            log.error("Errore interno", e);
            throw new RuntimeException("Errore interno", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    private String extractFilename(String contentDisposition) {

        if (contentDisposition == null) {
            return null;
        }

        String[] parts = contentDisposition.split(";");

        for (String part : parts) {
            part = part.trim();

            if (part.startsWith("filename=")) {
                String filename = part.substring("filename=".length());

                if (filename.startsWith("\"") && filename.endsWith("\"")) {
                    filename = filename.substring(1, filename.length() - 1);
                }

                return filename;
            }
        }

        return null;
    }

	public String getRootUrl() {
		return rootUrl;
	}   
    
}
