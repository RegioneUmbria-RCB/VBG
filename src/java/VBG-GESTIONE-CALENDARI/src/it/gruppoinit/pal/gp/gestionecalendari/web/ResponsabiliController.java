package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.helper.EntityUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.gestionecalendari.web.command.ResponsabiliCommand;
import it.gruppoinit.pal.gp.gestionecalendari.web.validator.ResponsabiliValidator;

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
public class ResponsabiliController {

    private static final Logger log = LoggerFactory.getLogger(ResponsabiliController.class);
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ResponsabiliService responsabiliService;

    @RequestMapping
    public String listPerComune(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codicecomune") String codicecomune) {

	log.info("listPerComune");
	fixRender(model, codicecomune);
	return "responsabili/listPerComune";
    }

    @RequestMapping
    public String viewPerComune(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codicecomune") String codicecomune, @RequestParam(value = "codiceresp", required = false) String codiceresp) {

	log.info("viewPerComune: codicecomune={}, codiceresp={}", codicecomune, codiceresp);
	ResponsabiliCommand command = new ResponsabiliCommand();
	Comuni comuni = new Comuni(codicecomune);
	comuni = comuniService.findByCodiceComune(comuni);
	command.setComuni(comuni);
	if (StringUtils.isNotBlank(codiceresp)) {
	    Responsabili responsabili = responsabiliService.findById(new PkId(Integer.valueOf(codiceresp)));
	    command.setResponsabili(responsabili);
	} else {
	    command.setResponsabili(new Responsabili());
	}
	model.addAttribute("responsabiliCommand", command);
	return "responsabili/formPerComune";
    }

    @RequestMapping
    public String aggiungiPerComune(Model model, @ModelAttribute("responsabiliCommand") ResponsabiliCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response, @RequestParam("codicecomune") String codicecomune) {

	log.info("aggiungiPerComune: codicecomune={}", codicecomune);
	try {
	    new ResponsabiliValidator().validate(command.getResponsabili(), result);
	    if (result.hasErrors()) {
		Comuni comuni = new Comuni(codicecomune);
		comuni = comuniService.findByCodiceComune(comuni);
		command.setComuni(comuni);
		return "responsabili/formPerComune";
	    }
	    if (EntityUtils.isNestedPropertyBlank(command.getResponsabili().getId(), "codice")) {
		responsabiliService.insertResponsabilePerComune(command.getResponsabili(), codicecomune);
	    } else {
		Responsabili _resp = populateResponsabili(command.getResponsabili());
		responsabiliService.update(_resp);
	    }
	} catch (Exception e) {
	    log.error("aggiungiPerComune: codicecomune={}", codicecomune, e);
	    Comuni comuni = new Comuni(codicecomune);
	    comuni = comuniService.findByCodiceComune(comuni);
	    command.setComuni(comuni);
	    addErrors(model, e);
	    return "responsabili/formPerComune";
	}
	status.setComplete();
	return "redirect:listPerComune.htm?codicecomune=" + codicecomune;
    }

    @RequestMapping
    public String eliminaPerComune(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codicecomune") String codicecomune, @RequestParam("codiceresp") String codiceresp) {

	log.info("eliminaPerComune: codicecomune={}, codiceresp={}", codicecomune, codiceresp);
	responsabiliService.deleteResponsabilePerComune(codiceresp, codicecomune);
	return "redirect:listPerComune.htm?codicecomune=" + codicecomune;
    }

    @RequestMapping
    public String elimina(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("codiceresp") String codiceresp) {

	log.info("elimina: codiceresp={}", codiceresp);
	Responsabili resp = responsabiliService.findById(new PkId(Integer.valueOf(codiceresp)));
	responsabiliService.delete(resp);
	return "redirect:list.htm";
    }

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("list");
	List<Responsabili> resps = responsabiliService.findAll();
	model.addAttribute("resps", resps);
	model.addAttribute("c_menu", "resps");
	return "responsabili/list";
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam(value = "codiceresp", required = false) String codiceresp) {

	log.info("view: codiceresp={}", codiceresp);
	ResponsabiliCommand command = new ResponsabiliCommand();
	Comuni comuni = new Comuni();
	command.setComuni(comuni);
	if (StringUtils.isNotBlank(codiceresp)) {
	    Responsabili responsabili = responsabiliService.findById(new PkId(Integer.valueOf(codiceresp)));
	    command.setResponsabili(responsabili);
	} else {
	    command.setResponsabili(new Responsabili());
	}
	model.addAttribute("responsabiliCommand", command);
	return "responsabili/form";
    }

    @RequestMapping
    public String aggiungi(Model model, @ModelAttribute("responsabiliCommand") ResponsabiliCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	log.info("aggiungi");
	try {
	    new ResponsabiliValidator().validate(command.getResponsabili(), result);
	    if (result.hasErrors()) {
		return "responsabili/form";
	    }
	    if (EntityUtils.isNestedPropertyBlank(command.getResponsabili().getId(), "codice")) {
		responsabiliService.insert(command.getResponsabili());
	    } else {
		Responsabili _resp = populateResponsabili(command.getResponsabili());
		responsabiliService.update(_resp);
	    }
	} catch (Exception e) {
	    log.error("aggiungi", e);
	    addErrors(model, e);
	    return "responsabili/form";
	}
	String codiceresp = command.getResponsabili().getId().getCodice().toString();
	status.setComplete();
	return "redirect:view.htm?codiceresp=" + codiceresp;
    }

    @RequestMapping
    public String aggiungiComunePerResponsabile(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codicecomune") String codicecomune, @RequestParam("codiceresp") String codiceresp) {

	log.info("aggiungiComunePerResponsabile: codicecomune={}, codiceresp={}", codicecomune, codiceresp);
	responsabiliService.insertComunePerResponsabile(codiceresp, codicecomune);
	return "redirect:view.htm?codiceresp=" + codiceresp;
    }

    @RequestMapping
    public String aggiungiTuttiIComuniPerResponsabile(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codiceresp") String codiceresp) {

	log.info("aggiungiTuttiIComuniPerResponsabile: codiceresp={}", codiceresp);
	responsabiliService.insertTuttiIComuniPerResponsabile(codiceresp);
	return "redirect:view.htm?codiceresp=" + codiceresp;
    }

    @RequestMapping
    public String eliminaComunePerResponsabile(Model model, HttpServletRequest request, HttpServletResponse response,
	    @RequestParam("codicecomune") String codicecomune, @RequestParam("codiceresp") String codiceresp) {

	log.info("eliminaComunePerResponsabile: codicecomune={}, codiceresp={}", codicecomune, codiceresp);
	responsabiliService.eliminaComunePerResponsabile(codiceresp, codicecomune);
	return "redirect:view.htm?codiceresp=" + codiceresp;
    }

    private Responsabili populateResponsabili(Responsabili r) {

	Responsabili _resp = responsabiliService.findById(new PkId(r.getId().getCodice()));
	_resp.setResponsabile(r.getResponsabile());
	_resp.setDisabilitato(r.getDisabilitato());
	_resp.setReadonly(r.getReadonly());
	_resp.setGestioneFesteSagre(r.getGestioneFesteSagre());
	_resp.setGestioneFiereMostre(r.getGestioneFiereMostre());
	_resp.setGestioneAreepubbliche(r.getGestioneAreepubbliche());
	_resp.setGestioneInserimentoAnagrafiche(r.getGestioneInserimentoAnagrafiche());
	return _resp;
    }

    private void fixRender(Model model, String codicecomune) {

	Comuni comuni = new Comuni(codicecomune);
	comuni = comuniService.findByCodiceComune(comuni);
	List<Responsabilicomuni> responsabili = responsabilicomuniService.findByComune(comuni);
	model.addAttribute("comune", comuni);
	model.addAttribute("responsabili", responsabili);
    }

    private void addErrors(Model model, Exception e) {

	model.addAttribute("error", e.getMessage());
    }
}
