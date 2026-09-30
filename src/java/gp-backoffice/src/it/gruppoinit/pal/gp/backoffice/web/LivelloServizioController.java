package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LivelloServizioService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("livelloservizio")
public class LivelloServizioController extends BaseController<LivelloServizio> {

    @Autowired
    private LivelloServizioService livelloservizioService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<LivelloServizio> livelloservizioList = livelloservizioService.findAll(null, null);
	ModelMap model = new ModelMap(livelloservizioList);
	boolean export = createJMesaExport(request, response, livelloservizioList);
	if (export) {
	    return null;
	}
	model.addAttribute("livelloservizioList", livelloservizioList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	LivelloServizio livelloservizio = new LivelloServizio();
	fixRenderEntityProperty(livelloservizio);
	model.addAttribute("livelloservizio", livelloservizio);
	setPageAttributes(model);
	return "livelloservizio/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("livelloservizio") LivelloServizio livelloservizio, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(livelloservizio);
	try {
	    livelloservizioService.insert(livelloservizio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, livelloservizio, e);
	    fixRenderEntityProperty(livelloservizio);
	    return "livelloservizio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + livelloservizio.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	LivelloServizio livelloservizio = livelloservizioService.findById(id);
	fixRenderEntityProperty(livelloservizio);
	model.addAttribute("livelloservizio", livelloservizio);
	setPageAttributes(model);
	return "livelloservizio/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("livelloservizio") LivelloServizio livelloservizio, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(livelloservizio);
	try {
	    livelloservizioService.update(livelloservizio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, livelloservizio, e);
	    fixRenderEntityProperty(livelloservizio);
	    return "livelloservizio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + livelloservizio.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("livelloservizio") LivelloServizio livelloservizio, BindingResult result, SessionStatus status) {

	LivelloServizio objToDelete = livelloservizioService.findById(livelloservizio.getId());
	try {
	    livelloservizioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(livelloservizio);
	    return "livelloservizio/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(LivelloServizio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(LivelloServizio entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
