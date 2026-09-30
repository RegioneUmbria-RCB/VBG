package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.EntiCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.EntiValidator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Secured("ROLE_ADMINISTRATOR")
@Controller
public class EntiController {

    private static final Logger log = LoggerFactory.getLogger(EntiController.class);
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ComuniService comuniService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("list");
	fixRender(model);
	return "enti/list";
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "codicecomune", required = false) String codicecomune) {

	log.info("view: codicecomune={}", codicecomune);
	EntiCommand command = new EntiCommand();
	if (StringUtils.isNotBlank(codicecomune)) {
	    Comuniassociati comuniassociati = comuniassociatiService.findById(new ComuniassociatiId(codicecomune));
	    command.setComuniassociati(comuniassociati);
	}
	model.addAttribute("entiCommand", command);
	return "enti/form";
    }

    @RequestMapping
    public String aggiungi(Model model, @ModelAttribute("entiCommand") EntiCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	log.info("aggiungi");
	try {
	    new EntiValidator().validate(command.getComuniassociati(), result);
	    if (result.hasErrors()) {
		return "enti/form";
	    }
	    Comuni comune = comuniService.findByCodiceComune(new Comuni(command.getComuniassociati().getId().getCodicecomune()));
	    command.getComuniassociati().setComune(comune);
	    comuniassociatiService.insert(command.getComuniassociati());
	} catch (Exception e) {
	    log.error("aggiungi", e);
	    addErrors(model, e);
	    return "enti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String elimina(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("codicecomune") String codicecomune) {

	log.info("elimina codicecomune={}", codicecomune);
	try {
	    Comuniassociati comuniassociati = comuniassociatiService.findById(new ComuniassociatiId(codicecomune));
	    comuniassociatiService.delete(comuniassociati);
	} catch (Exception e) {
	    log.error("elimina codicecomune={}", codicecomune, e);
	    addErrors(model, e);
	    fixRender(model);
	    return "enti/list";
	}
	return "redirect:list.htm";
    }

    private void fixRender(Model model) {

	List<Comuniassociati> list = comuniassociatiService.findAll();
	Comuni comune = new Comuni();
	model.addAttribute("comune", comune);
	model.addAttribute("c_menu", "enti");
	model.addAttribute("enti", list);
    }

    private void addErrors(Model model, Exception e) {

	model.addAttribute("error", e.getMessage());
    }
}
