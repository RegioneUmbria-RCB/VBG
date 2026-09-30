package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.service.TitoliService;

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

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("titoli")
public class TitoliController extends BaseController<Titoli> {

    @Autowired
    private TitoliService titoliService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Titoli> titoliList = titoliService.findAll(null, null);
	ModelMap model = new ModelMap(titoliList);
	boolean export = createJMesaExport(request, response, titoliList);
	if (export) {
	    return null;
	}
	model.addAttribute("titoliList", titoliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Titoli titoli = new Titoli();
	fixRenderEntityProperty(titoli);
	model.addAttribute("titoli", titoli);
	setPageAttributes(model);
	return "titoli/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("titoli") Titoli titoli, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(titoli);
	try {
	    titoliService.insert(titoli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, titoli, e);
	    fixRenderEntityProperty(titoli);
	    return "titoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + titoli.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Titoli titoli = titoliService.findById(id);
	fixRenderEntityProperty(titoli);
	model.addAttribute("titoli", titoli);
	setPageAttributes(model);
	return "titoli/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("titoli") Titoli titoli, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(titoli);
	try {
	    titoliService.update(titoli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, titoli, e);
	    fixRenderEntityProperty(titoli);
	    return "titoli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + titoli.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("titoli") Titoli titoli, BindingResult result, SessionStatus status) {

	Titoli objToDelete = titoliService.findById(titoli.getId());
	try {
	    titoliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(titoli);
	    return "titoli/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Titoli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Titoli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
