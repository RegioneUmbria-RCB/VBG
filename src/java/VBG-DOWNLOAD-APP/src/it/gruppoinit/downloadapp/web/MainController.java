package it.gruppoinit.downloadapp.web;

import it.gruppoinit.downloadapp.Utilities;
import it.gruppoinit.sigepro.definitions.movimenti.MovimentiWSClient;
import it.gruppoinit.sigepro.definitions.oggetti.OggettiWSClient;
import it.gruppoinit.sigepro.schemas.messages.movimenti.MovimentiDownloadZipLogicoResponse;
import it.gruppoinit.sigepro.schemas.messages.oggetti.OggettiFindResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private OggettiWSClient oggettiWSClient;
    @Autowired
    private MovimentiWSClient movimentiWSClient;

    @RequestMapping
    public void reload(Model model) throws Exception {

    }

    @RequestMapping
    public void list(Model model) throws Exception {

    }

    @RequestMapping
    public String get(Model model, @RequestParam("a") String alias, @RequestParam("m") String md5) throws Exception {

	return "main/get";
    }

    @RequestMapping
    public void view(Model model, @RequestParam("a") String alias, @RequestParam("m") String md5, @RequestParam("pin_document") String pin_document,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	OggettiFindResponse responseOggetti = null;
	try {
	    log.debug("Richiesto download del file {}", pin_document);
	    BigInteger cod = new BigInteger(pin_document);
	    responseOggetti = oggettiWSClient.find(cod, alias);
	} catch (Exception e) {
	    log.error("Errore nel recupero del codice oggetto {}, {}", pin_document, e);
	    throw new RuntimeException("Attenzione pin errato");
	}
	InputStream is = responseOggetti.getBinaryData().getInputStream();
	File tmpFile = File.createTempFile("DOWNLOAD-", "");
	FileOutputStream fos = new FileOutputStream(tmpFile);
	IOUtils.copy(is, fos);
	fos.close();
	is.close();
	FileInputStream f1 = new FileInputStream(tmpFile);
	if (Utilities.matchMd5File(f1, md5)) {
	    f1.close();
	    f1 = new FileInputStream(tmpFile);
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(responseOggetti.getMimeType());
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + responseOggetti.getFileName() + "\"");
	    request.removeAttribute("Error");
	    // response.setContentLength(b.length);
	    ServletOutputStream out = response.getOutputStream();
	    IOUtils.copy(f1, out);
	    try {
		f1.close();
		tmpFile.delete();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    out.flush();
	} else {
	    log.error("Errore nel recupero del codice oggetto {}, mac non valido {}", pin_document, md5);
	}
    }

    @RequestMapping
    public String getZipLogico(Model model, @RequestParam("a") String alias, @RequestParam("u") String uuidistanza,
	    @RequestParam("m") String md5_movimento_istanza) throws Exception {

	return "main/getZipLogico";
    }

    @RequestMapping
    public void viewZipLogico(Model model, @RequestParam("a") String alias, @RequestParam("u") String uuidistanza,
	    @RequestParam("m") String md5_movimento_istanza, @RequestParam("pin") Integer codice_movimento_pin, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String messaggio = alias + "-" + uuidistanza + "-" + codice_movimento_pin;
	if (Utilities.verificaDownloadZip(alias, uuidistanza, codice_movimento_pin + "", md5_movimento_istanza)) {
	    MovimentiDownloadZipLogicoResponse downloadZip = null;
	    try {
		log.debug("Richiesto download dello zip logico del movimento {}", messaggio);
		downloadZip = movimentiWSClient.downloadZip(alias, codice_movimento_pin, uuidistanza);
	    } catch (Exception e) {
		log.error("Errore nel recupero dello zip logico {}, {}", messaggio, e);
		throw new RuntimeException("Attenzione pin errato");
	    }
	    if (downloadZip == null) {
		log.error("Errore nel recupero dello zip {}, response nulla", messaggio);
		throw new RuntimeException("Attenzione pin errato");
	    }
	    String nomeFile = StringUtils.defaultString(downloadZip.getFileName(), "documento.zip");
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(StringUtils.defaultString(downloadZip.getMimeType(), "application/zip"));
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	    request.removeAttribute("Error");
	    ServletOutputStream out = response.getOutputStream();
	    InputStream is = downloadZip.getBinaryData().getInputStream();
	    IOUtils.copy(is, out);
	    out.flush();
	    try {
		is.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    log.error("Errore nel recupero dello zip {}, mac non valido {}", messaggio, md5_movimento_istanza);
	    throw new RuntimeException("Attenzione chiamata non corretta");
	}
    }

    public OggettiWSClient getOggettiWSClient() {

	return oggettiWSClient;
    }

    public void setOggettiWSClient(OggettiWSClient oggettiWSClient) {

	this.oggettiWSClient = oggettiWSClient;
    }
}
