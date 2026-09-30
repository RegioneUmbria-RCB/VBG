package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.MovimentiAtti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MovimentiAttiService;
import it.gruppoinit.pal.gp.core.ws.client.NlaAttiWsClient;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("movimentiatti")
public class MovimentiAttiController extends BaseController<MovimentiAtti> {

    private static final Logger log = LoggerFactory.getLogger(MovimentiAttiController.class);
    @Autowired
    private MovimentiAttiService movimentiattiService;
    @Autowired
    private NlaAttiWsClient nlaAttiWsClient;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<MovimentiAtti> movimentiattiList = movimentiattiService.findAll(null, null);
	ModelMap model = new ModelMap(movimentiattiList);
	boolean export = createJMesaExport(request, response, movimentiattiList);
	if (export) {
	    return null;
	}
	model.addAttribute("movimentiattiList", movimentiattiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	MovimentiAtti movimentiatti = new MovimentiAtti();
	fixRenderEntityProperty(movimentiatti);
	model.addAttribute("movimentiatti", movimentiatti);
	setPageAttributes(model);
	return "movimentiatti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("movimentiatti") MovimentiAtti movimentiatti, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(movimentiatti);
	try {
	    movimentiattiService.insert(movimentiatti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiatti, e);
	    fixRenderEntityProperty(movimentiatti);
	    return "movimentiatti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + movimentiatti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MovimentiAtti movimentiatti = movimentiattiService.findById(id);
	fixRenderEntityProperty(movimentiatti);
	model.addAttribute("movimentiatti", movimentiatti);
	setPageAttributes(model);
	return "movimentiatti/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("movimentiatti") MovimentiAtti movimentiatti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(movimentiatti);
	try {
	    movimentiattiService.update(movimentiatti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiatti, e);
	    fixRenderEntityProperty(movimentiatti);
	    return "movimentiatti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + movimentiatti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("movimentiatti") MovimentiAtti movimentiatti, BindingResult result, SessionStatus status) {

	MovimentiAtti objToDelete = movimentiattiService.findById(movimentiatti.getId());
	try {
	    movimentiattiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(movimentiatti);
	    return "movimentiatti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxRicercaAtto(@RequestParam("codicemovimento") Integer codicemovimento, Model model, HttpServletRequest request) {

	MovimentiAtti movimentiAtti = movimentiattiService.findByMovimento(codicemovimento);
	boolean attoPresente = false;
	String messaggio = "";
	if (movimentiAtti != null && movimentiAtti.getDataRicezioneAtto() != null) {
	    attoPresente = true;
	    //	    model.addAttribute("movimentiAtti", movimentiAtti);
	    messaggio = "Atto già ricevuto, controllare tra gli allegati del movimento";
	    model.addAttribute("movimentiAtti", movimentiAtti);
	} else {
	    try {
		log.debug("ajaxRicercaAtto# Invio il ricerca atto per codice movimento {}", codicemovimento);
		messaggio = nlaAttiWsClient.ricercaAtto(codicemovimento);
	    } catch (Exception e) {
		log.error("ajaxRicercaAtto# {}", e.getMessage());
		messaggio = e.getMessage();
	    }
	}
	model.addAttribute("messaggio", messaggio);
	model.addAttribute("attoPresente", attoPresente);
	
	return "movimentiatti/ajaxAtto";
    }

    @Override
    protected void fixMergeEntityProperty(MovimentiAtti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MovimentiAtti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
