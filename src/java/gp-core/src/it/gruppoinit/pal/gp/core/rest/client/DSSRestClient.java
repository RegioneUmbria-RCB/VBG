package it.gruppoinit.pal.gp.core.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.impl.MetadataMap;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.FileOriginaleDSSBean;
import it.gruppoinit.pal.gp.core.rest.client.models.dss.ValidationResultDTO;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class DSSRestClient {

    private static final String ERRORE_CONFIGURAZIONE = "Parametro SECURITY.WSHOSTURL_FIRMADIGITALE_REST non configurato correttamente";
    private static final Logger log = LoggerFactory.getLogger(DSSRestClient.class);
    private long connectionTimeOut = 8000; // 8 secondi
    private long receivetimeout = 240000; // 4 minuti

    public ValidationResultDTO checkFirmaReport(DataHandler dh, String nomefile, boolean verificaAllaData, boolean estraiFileNonFirmato)
	    throws DSSClientException {

	try {
	    log.debug("checkFirmaReport verifica del file {}", nomefile);
	    String url = getRootUrl() + "/api/checkfirma/report";
	    log.debug("checkFirmaReport url verifica {}", url);
	    WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout, url);
	    c.type("multipart/form-data");
	    c.accept(MediaType.APPLICATION_JSON);
	    List<Attachment> attachments = new ArrayList<Attachment>();
	    // File
	    Attachment fileAttachment = new Attachment("documento", dh.getInputStream(),
		    new ContentDisposition("form-data; name=\"documento\"; filename=\"" + nomefile + "\""));
	    attachments.add(fileAttachment);
	    //Altri parametri
	    attachments.add(getFormTextParam("verificaAllaData", String.valueOf(verificaAllaData)));
	    attachments.add(getFormTextParam("estraiFileNonFirmato", String.valueOf(estraiFileNonFirmato)));
	    log.debug("checkFirmaReport prima della chiamata alla url verifica {}", url);
	    Response response = c.post(attachments, Response.class);
	    InputStream is = (InputStream) response.getEntity();
	    if (response.getStatus() == 200) {
		log.debug("checkFirmaReport esito chiamata OK {}", url);
		return Utilities.unMarshallJsonStream(is, ValidationResultDTO.class, false);
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("checkFirma checkFirmaReport: {}", s);
		throw new DSSClientException("Errore verifica firma:\n" + s);
	    }
	} catch (Exception e) {
	    log.error("Errore interno checkFirmaReport", e);
	    throw new DSSClientException("Errore nella verifica del file: " + e.getMessage());
	}
    }

    public FileOriginaleDSSBean checkFirmaScaricaFileNonFirmato(DataHandler dh, String nomefile) throws DSSClientException {

	try {
	    log.debug("checkFirmaScaricaFileNonFirmato inizio chiamata per il file {}", nomefile);
	    String url = getRootUrl() + "/api/checkfirma/scaricaFileNonFirmato";
	    log.debug("checkFirmaScaricaFileNonFirmato url invocato {}", url);
	    WebClient c = getRestWebClient(this.connectionTimeOut, this.receivetimeout, url);
	    c.type("multipart/form-data");
	    List<Attachment> attachments = new ArrayList<Attachment>();
	    // File
	    Attachment fileAttachment = new Attachment("documento", dh.getInputStream(),
		    new ContentDisposition("form-data; name=\"documento\"; filename=\"" + nomefile + "\""));
	    attachments.add(fileAttachment);
	    Response response = c.post(attachments, Response.class);
	    FileOriginaleDSSBean bean = new FileOriginaleDSSBean();
	    bean.setFileName(new ContentDisposition((String) response.getMetadata().getFirst("Content-Disposition")).getParameter("filename"));
	    bean.setContentType((String) response.getMetadata().getFirst("Content-Type"));
	    InputStream is = (InputStream) response.getEntity();
	    if (response.getStatus() == 200) {
		log.debug("checkFirmaScaricaFileNonFirmato esito chiamata OK {}", url);
		bean.setContent(IOUtils.toByteArray(is));
		return bean;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		}
		log.error("checkFirmaScaricaFileNonFirmato: {}", s);
		throw new DSSClientException("Errore verifica firma:\n" + s);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    log.error("Errore interno checkFirmaScaricaFileNonFirmato", e);
	    throw new DSSClientException("Errore interno", e);
	}
    }

    private Attachment getFormTextParam(String param, String valore) throws IOException {

	MetadataMap<String, String> mapHeaders = new MetadataMap<String, String>();
	mapHeaders.putSingle("Content-Disposition", "form-data; name=\"" + param + "\"");
	mapHeaders.putSingle("Content-Type", MediaType.APPLICATION_JSON);
	return new Attachment("estraiFileNonFirmato", new DataHandler(new ByteArrayDataSource(valore, MediaType.APPLICATION_JSON)), mapHeaders);
    }

    private WebClient getRestWebClient(long connectionTimeOut, long receivetimeout, String url) {

	WebClient client = WebClient.create(url);
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }

    private String getRootUrl() {

	// "http://dss-webapp-2:8080/dss-webapp-2"
	String url = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_FIRMADIGITALE_REST);
	if (StringUtils.isBlank(url)) {
	    log.error(ERRORE_CONFIGURAZIONE);
	    throw new InvalidConfigurationException(ERRORE_CONFIGURAZIONE);
	}
	if (url.endsWith("/")) {
	    return url.substring(0, url.lastIndexOf("/"));
	}
	return url;
    }
}
