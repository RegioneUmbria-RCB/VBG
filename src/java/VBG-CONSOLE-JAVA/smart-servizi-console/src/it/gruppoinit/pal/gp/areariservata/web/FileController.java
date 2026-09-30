package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.fileconverter.ConvertBinaryRequest;
import it.gruppoinit.fileconverter.ConvertBinaryResponse;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ReportValidazioneFirmaDigitale;
import it.gruppoinit.pal.gp.core.service.FirmaDigitaleService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;
import it.init.sigepro.rte.types.ErroreType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Secured("ROLE_USER")
@Controller
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private FirmaDigitaleService firmaDigitaleService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping
    public ModelAndView ajaxUpload(HttpServletRequest request, @RequestParam(value = "verificaFirma", required = false) Boolean verificaFirma) {

	log.debug("ajaxUpload");
	Map<String, Object> model = new HashMap<String, Object>();
	try {
	    MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
	    MultipartFile uploaded = null;
	    Map fileMap = multiReq.getFileMap();
	    Iterator<String> keys = fileMap.keySet().iterator();
	    if (keys.hasNext()) {
		String key = keys.next();
		uploaded = (MultipartFile) fileMap.get(key);
	    }
	    if (uploaded != null) {
		Oggetti oggetto = new Oggetti();
		oggetto.setNomefile(uploaded.getOriginalFilename());
		byte[] bytes = uploaded.getBytes();
		if (BooleanUtils.isTrue(verificaFirma)) {
		    DataHandler dh = Utilities.bytesToDataHandler(bytes);
		    ReportValidazioneFirmaDigitale report = firmaDigitaleService.validaFile(oggetto.getNomefile(), dh);
		    if (!report.getWarnings().isEmpty()) {
			String warnings = "<ul>";
			for (ErroreType e : report.getWarnings()) {
			    warnings += "<li>" + e.getDescrizione() + ":" + e.getNumeroErrore() + "</li>";
			}
			warnings += "</ul>";
			log.warn("ajaxUpload: Avvertimenti durante il controllo della firma digitale del file=[{}], err=[{}]", oggetto.getNomefile(),
				warnings);
			model.put("Warnings", "La Firma Digitale del file \"" + uploaded.getOriginalFilename()
				+ "\" ha generato i seguenti avvertimenti: " + warnings);
		    }
		    if (!report.getErrors().isEmpty()) {
			for (ErroreType e : report.getErrors()) {
			    log.error("ajaxUpload: errore durante il controllo della firma digitale del file=[{}], err=[{}]", oggetto.getNomefile(),
				    e.getDescrizione());
			}
			model.put("Errori", "La Firma Digitale del file \"" + uploaded.getOriginalFilename() + "\" non è valida");
			return new ModelAndView("jsonView", model);
		    }
		}
		oggetto.setOggetto(bytes);
		oggetto.setDimensioneFile(bytes.length);
		oggettiService.insert(oggetto);
		log.debug("ajaxUpload: codiceoggetto={}, filename={}", oggetto.getId().getCodice(), uploaded.getOriginalFilename());
		model.put("codiceOggetto", oggetto.getId().getCodice());
		model.put("fileName", oggetto.getNomefile());
		model.put("length", bytes.length);
		model.put("length_hr", FileUtils.byteCountToDisplaySize(bytes.length));
		model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	    } else {
		log.error("ajaxUpload: nessun file nella request");
		model.put("Errori", "Nessun file caricato");
	    }
	} catch (Exception e) {
	    log.error("ajaxUpload", e);
	    model.put("Errori", "Errore durante il caricamento del file");
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView ajaxDelete(@RequestParam("codiceOggetto") Integer codiceOggetto, HttpServletRequest request) throws IOException {

	log.debug("ajaxDelete: codiceoggetto={}", codiceOggetto);
	Map<String, Object> model = new HashMap<String, Object>();
	try {
	    Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	    if (null != oggetto) {
		//oggettiService.delete(oggetto);
		List<Integer> toDelete = (List) request.getSession().getAttribute(WebConstants.DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY);
		if (toDelete == null) {
		    toDelete = new ArrayList<Integer>();
		    request.getSession().setAttribute(WebConstants.DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY, toDelete);
		}
		toDelete.add(codiceOggetto);
	    } else {
		log.error("ajaxDelete: file non trovato. codiceoggetto={}", codiceOggetto);
		model.put("Errori", "File non trovato");
	    }
	} catch (Exception e) {
	    log.error("ajaxDelete", e);
	    model.put("Errori", "Errore durante la cancellazione");
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public void ajaxDownload(@RequestParam("codiceOggetto") Integer codiceOggetto, HttpServletResponse response) throws IOException {

	log.debug("ajaxDownload: codiceoggetto={}", codiceOggetto);
	Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	if (null != oggetto) {
	    byte[] data = oggetto.getOggetto();
	    response.setContentLength(data.length);
	    String mime = AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile());
	    response.setContentType(mime);
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + oggetto.getNomefile() + "\"");
	    ServletOutputStream sos = response.getOutputStream();
	    AttachmentsUtils.writeBytesToStream(data, sos);
	    sos.flush();
	} else {
	    log.error("ajaxDownload: file non trovato. codiceoggetto={}", codiceOggetto);
	    response.setStatus(HttpStatus.SC_NOT_FOUND);
	}
    }

    @RequestMapping
    public void ajaxConvertAndDownload(@RequestParam("codiceOggetto") Integer codiceOggetto, @RequestParam("tipoDownolad") String tipoDownolad,
	    HttpServletResponse response) throws IOException {

	log.debug("ajaxConvertAndDownload: codiceoggetto={}, tipoDownolad={}", codiceOggetto, tipoDownolad);
	Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	if (null != oggetto) {
	    String nomeFile = oggetto.getNomefile();
	    byte[] data = oggetto.getOggetto();
	    String mime = AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile());
	    if (StringUtils.isNotBlank(tipoDownolad)) {
		try {
		    ConvertBinaryResponse resp = this.convert(data, mime, tipoDownolad);
		    data = resp.getBinaryData();
		    nomeFile = resp.getFileName();
		    mime = resp.getMimeType();
		} catch (Exception e) {
		    log.error("ajaxConvertAndDownload: errore durante la conversione del file {} in {} \n{}", new Object[] { codiceOggetto,
			    tipoDownolad, e });
		}
	    }
	    response.setContentLength(data.length);
	    response.setContentType(mime);
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + nomeFile + "\"");
	    ServletOutputStream sos = response.getOutputStream();
	    AttachmentsUtils.writeBytesToStream(data, sos);
	    sos.flush();
	} else {
	    log.error("ajaxDownload: file non trovato. codiceoggetto={}", codiceOggetto);
	    response.setStatus(HttpStatus.SC_NOT_FOUND);
	}
    }

    @RequestMapping
    public ModelAndView ajaxRead(@RequestParam("codiceOggetto") Integer codiceOggetto) throws IOException {

	log.debug("ajaxRead: codiceoggetto={}", codiceOggetto);
	Map<String, Object> model = new HashMap<String, Object>();
	Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	if (oggetto != null) {
	    byte[] bytes = oggetto.getOggetto();
	    model.put("codiceOggetto", oggetto.getId().getCodice());
	    model.put("nomeFile", oggetto.getNomefile());
	    model.put("size", bytes.length);
	    model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	} else {
	    log.error("ajaxRead: file non trovato. codiceoggetto={}", codiceOggetto);
	    model.put("Errori", "File non trovato");
	}
	return new ModelAndView("jsonView", model);
    }

    private ConvertBinaryResponse convert(byte[] rawContent, String contentType, String conversionType) throws Exception {

	log.debug("convert: {} in {}", contentType, conversionType);
	try {
	    FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	    ConvertBinaryRequest req = new ConvertBinaryRequest(ORMHelper.getToken(), rawContent, contentType, conversionType);
	    ConvertBinaryResponse resp = fileConverterWsClient.convertBinary(req);
	    return resp;
	} catch (Exception e) {
	    log.error("convert", e);
	    throw e;
	}
    }
}
