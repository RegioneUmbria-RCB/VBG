package it.gruppoinit.nlaproxy.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pdd.ri.features.configurazione.ConfigurazioneService;

@Controller
@SessionAttributes(value = { "nuovoCertificatoModel" })
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
    @Autowired
    private ConfigurazioneService configurazioneService;

    @RequestMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	model.addAttribute("nuovoCertificatoModel", new NuovoCertificatoModel());
	request.setAttribute("config", this.configurazioneService.caricaConfigurazione());
	return "config/index";
    }

    @RequestMapping
    public String add(Model model, @ModelAttribute("nuovoCertificatoModel") NuovoCertificatoModel nuovoCertificatoModel, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    nuovoCertificatoModel.valida();
	    String codiceCatastale = nuovoCertificatoModel.getCodiceCatastale();
	    MultipartFile certificato = nuovoCertificatoModel.getCertificato();
	    String password = nuovoCertificatoModel.getPassword();
	    String alias = nuovoCertificatoModel.getAlias();
	    String[] codici = codiceCatastale.split(",");
	    if (codici.length == 1) {
		this.configurazioneService.add(codiceCatastale, certificato, password, alias);
	    } else {
		this.configurazioneService.addRange(codici, certificato, password, alias);
	    }
	} catch (Exception e) {
	    String message = "Errore durante l'aggiunta di una configurazione: " + e.getMessage();
	    logger.error(message, e);
	    response.sendError(500, message);
	}
	return "config/index";
    }

    @RequestMapping
    public String elimina(@RequestParam("codiceCatastale") String codiceCatastale, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	try {
	    this.configurazioneService.elimina(codiceCatastale);
	} catch (Exception e) {
	    String message = "Errore durante l'eliminazione di una configurazione: " + e.getMessage();
	    logger.error(message, e);
	    response.sendError(500, message);
	}
	return "config/index";
    }
}