package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniItalianiService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AjaxController {

    private static final Logger log = LoggerFactory.getLogger(AjaxController.class);
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ComuniItalianiService comuniItalianiService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private CittadinanzaService cittadinanzaService;

    @RequestMapping
    public void getComune(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.info("getComune: {}", term);
	List<Comuni> list = comuniService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Comuni comune : list) {
		buffer.append("{\"id\":\"").append(comune.getCf()).append("\",\"label\":\"").append(comune.getComune()).append("\",\"value\":\"")
			.append(comune.getComune()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getComuneItaliani(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.info("getComune: {}", term);
	List<ComuniItaliani> list = comuniItalianiService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (ComuniItaliani comuniItaliani : list) {
		buffer.append("{\"id\":\"").append(comuniItaliani.getCf()).append("\",\"label\":\"").append(comuniItaliani.getComune())
			.append("\",\"pr\":\"").append(comuniItaliani.getSiglaprovincia()).append("\",\"value\":\"")
			.append(comuniItaliani.getComune()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getCittadinanza(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.info("getComune: {}", term);
	List<Cittadinanza> list = cittadinanzaService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Cittadinanza cittadinanza : list) {
		buffer.append("{\"id\":\"").append(cittadinanza.getCodice()).append("\",\"ext\":\"").append(cittadinanza.getFlgPaeseComunitario())
			.append("\",\"label\":\"").append(cittadinanza.getCittadinanza()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void getComuneassociato(@RequestParam("term") String term, HttpServletResponse response) throws IOException {

	log.info("getComune: {}", term);
	List<Comuniassociati> list = comuniassociatiService.findByDescrizione(term);
	StringBuffer buffer = new StringBuffer("[");
	if (list != null && !list.isEmpty()) {
	    for (Comuniassociati comune : list) {
		buffer.append("{\"id\":\"").append(comune.getId().getCodicecomune()).append("\",\"label\":\"").append(comune.getComune().getComune())
			.append("\",\"value\":\"").append(comune.getComune().getComune()).append("\"},");
	    }
	    buffer.deleteCharAt(buffer.length() - 1);
	}
	buffer.append("]");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void ajaxGetTracciatoXSD(HttpServletResponse response) {

	log.info("ajaxGetTracciatoXSD");
	try {
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition", "filename=\"tracciato.xsd\"");
	    InputStream is = this.getClass().getClassLoader().getResourceAsStream("tracciato.xsd");
	    IOUtils.copy(is, response.getOutputStream());
	} catch (Exception e) {
	    log.error("ajaxGetTracciatoXSD", e);
	}
    }
}
