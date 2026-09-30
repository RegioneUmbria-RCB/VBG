package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.sigepro.cart.service.utils.AttachmentsUtils;

@Controller
public class UploadDatiDinamiciController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(UploadDatiDinamiciController.class);
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;

    @RequestMapping
    public ModelAndView ajaxUploadFile(HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	//il file viene postato da solo nell'unico campo file del suo specifico form
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
		//salvo il file nella tabella oggetti
		Oggetti oggetto = new Oggetti();
		oggetto.setNomefile(uploaded.getOriginalFilename());
		byte[] bytes = uploaded.getBytes();
		oggetto.setOggetto(bytes);
		oggetto.setDimensioneFile(bytes.length);
		oggettiService.insert(oggetto);
		if (log.isDebugEnabled()) {
		    log.debug("ajaxUploadFile - caricato nuovo file nella tabella oggetti. Nuovo ID: {}",
			    new String[] { oggetto.getId().getCodice().toString() });
		}
		//collego l'oggetto appena inserito alla domanda in corso in FO_DOMANDE_OGGETTI
		model.put("codiceOggetto", oggetto.getId().getCodice());
		model.put("fileName", oggetto.getNomefile());
		model.put("length", bytes.length);
		model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	    } else {
		model.put("Errori", "Nessun file caricato");
	    }
	} catch (Exception e) {
	    StringBuilder sbErr = new StringBuilder("Si è verificato un errore durante il caricamento del file");
	    if (StringUtils.isNotBlank(e.getMessage())) {
		sbErr.append(": ").append(e.getMessage());
	    }
	    log.error("ajaxUploadFile - " + sbErr.toString(), e);
	    model.put("Errori", sbErr.toString());
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public ModelAndView ajaxDeleteFile(@RequestParam("codiceOggetto") Integer codiceOggetto, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    HttpServletRequest request) throws IOException {

	Map<String, Object> model = new HashMap<String, Object>();
	//viene richiesta la cancellazione del file per codiceoggetto
	if (codiceOggetto != null) {
	    Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	    if (null != oggetto) {
		/*
		 * nel BO il file precedentemente caricato è referenziato in DOCUMENTIISTANZA e quindi occorre 
		 * eliminare il record di DOCUMENTIISTANZA prima della cancellazione da OGGETTI
		 */
		/*
		List<Documentiistanza> docs = documentiistanzaService.findByIstanza(codiceIstanza, true);
		for (Documentiistanza doc : docs) {
		    Oggetti oggettoDoc = doc.getOggetto();
		    if (null != oggettoDoc) {
			PkId pkOggettoDoc = oggettoDoc.getId();
			if (pkOggettoDoc.getCodice().equals(codiceOggetto)) {
			    documentiistanzaService.delete(doc);
			    if (log.isDebugEnabled()) {
				log.debug("ajaxDeleteFile - cancellato record dalla tabella documentiistanza con ID: {}", new String[] { doc.getId()
					.getCodice().toString() });
			    }
			    break;
			}
		    }
		}
		oggettiService.delete(oggetto);
		if (log.isDebugEnabled()) {
		    log.debug("ajaxDeleteFile - cancellato record dalla tabella oggetti con ID: {}", new String[] { oggetto.getId().getCodice()
			    .toString() });
		}
		*/
		//L'effettiva cancellazione del record da OGGETTI e da DOCUMENTIISTANZA avviene solo al salvataggio della scheda dinamica
		List<Integer> toDelete = (List) request.getSession().getAttribute(WebConstants.DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY);
		if (toDelete == null) {
		    toDelete = new ArrayList<Integer>();
		    request.getSession().setAttribute(WebConstants.DYN2_CAMPO_UPLOAD_DELETEFILES_SESSION_KEY, toDelete);
		}
		toDelete.add(codiceOggetto);
	    } else {
		log.error("ajaxDeleteFile - file non trovato. codiceoggetto={}", codiceOggetto);
		model.put("Errori", "File non trovato");
	    }
	    /*
	    model.put("codiceOggetto", oggetto.getId().getCodice());
	    model.put("fileName", oggetto.getNomefile());
	    model.put("length", bytes.length);
	    model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	    */
	}
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping
    public void ajaxDownloadFile(@RequestParam("codiceOggetto") Integer codiceOggetto, HttpServletResponse response) throws IOException {

	//Map<String, Object> model = new HashMap<String, Object>();
	//viene richiesta la cancellazione del file per codiceoggetto
	if (codiceOggetto != null) {
	    Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	    if (null != oggetto) {
		if (log.isDebugEnabled()) {
		    log.debug("ajaxDownloadFile - richiesto download del file dalla tabella oggetti con ID: {}",
			    new String[] { oggetto.getId().getCodice().toString() });
		}
		byte[] data = oggetto.getOggetto();
		response.setContentLength(data.length);
		String mime = AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile());
		response.setContentType(mime);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + oggetto.getNomefile() + "\"");
		ServletOutputStream sos = response.getOutputStream();
		AttachmentsUtils.writeBytesToStream(data, sos);
		sos.flush();
	    } else {
		//TODO gestire il caso in cui non si trova il file da scaricare
		response.setStatus(HttpStatus.SC_NOT_FOUND);
	    }
	    /*
	    model.put("codiceOggetto", oggetto.getId().getCodice());
	    model.put("fileName", oggetto.getNomefile());
	    model.put("length", bytes.length);
	    model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	    */
	}
    }

    @RequestMapping
    public ModelAndView ajaxReadFile(@RequestParam("codiceOggetto") Integer codiceOggetto) throws IOException {

	Map<String, Object> model = new HashMap<String, Object>();
	if (codiceOggetto != null) {
	    Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	    if (oggetto == null) {
		throw new RuntimeException("Il documento con codiceoggetto " + codiceOggetto + " non è stato trovato");
	    }
	    if (log.isDebugEnabled()) {
		log.debug("ajaxUploadFile - caricato nuovo file nella tabella oggetti. Nuovo ID: {}",
			new String[] { oggetto.getId().getCodice().toString() });
	    }
	    byte[] bytes = oggetto.getOggetto();
	    model.put("codiceOggetto", oggetto.getId().getCodice());
	    model.put("nomeFile", oggetto.getNomefile());
	    model.put("size", bytes.length);
	    model.put("mime", AttachmentsUtils.getMimeTypeForFileName(oggetto.getNomefile()));
	} else {
	    //TODO manca il parametro codiceOggetto
	}
	return new ModelAndView("jsonView", model);
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }
}
