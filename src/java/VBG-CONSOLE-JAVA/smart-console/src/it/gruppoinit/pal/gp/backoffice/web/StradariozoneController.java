package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.service.StradariozoneService;

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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("stradariozone")
public class StradariozoneController extends BaseController<Stradariozone> {

    @Autowired
    private StradariozoneService stradariozoneService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Stradariozone> stradariozoneList = stradariozoneService.findAll(null, null);
	ModelMap model = new ModelMap(stradariozoneList);
	boolean export = createJMesaExport(request, response, stradariozoneList);
	if (export) {
	    return null;
	}
	model.addAttribute("stradariozoneList", stradariozoneList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Stradariozone stradariozone = new Stradariozone();
	fixRenderEntityProperty(stradariozone);
	model.addAttribute("stradariozone", stradariozone);
	setPageAttributes(model);
	return "stradariozone/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("stradariozone") Stradariozone stradariozone, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(stradariozone);
	try {
	    stradariozoneService.insert(stradariozone);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradariozone, e);
	    fixRenderEntityProperty(stradariozone);
	    return "stradariozone/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + stradariozone.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Stradariozone stradariozone = stradariozoneService.findById(id);
	fixRenderEntityProperty(stradariozone);
	model.addAttribute("stradariozone", stradariozone);
	setPageAttributes(model);
	return "stradariozone/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("stradariozone") Stradariozone stradariozone, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(stradariozone);
	try {
	    stradariozoneService.update(stradariozone);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradariozone, e);
	    fixRenderEntityProperty(stradariozone);
	    return "stradariozone/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + stradariozone.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("stradariozone") Stradariozone stradariozone, BindingResult result, SessionStatus status) {

	Stradariozone objToDelete = stradariozoneService.findById(stradariozone.getId());
	try {
	    stradariozoneService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(stradariozone);
	    return "stradariozone/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Stradariozone entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Stradariozone entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
