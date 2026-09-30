package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.InfosuapService;

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
 * @author lucap
 */
@Controller
@SessionAttributes("infosuap")
public class InfosuapController extends BaseController<Infosuap> {

    @Autowired
    private InfosuapService infosuapService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Infosuap> infosuapList = infosuapService.findAll(null, null);
	ModelMap model = new ModelMap(infosuapList);
	boolean export = createJMesaExport(request, response, infosuapList);
	if (export) {
	    return null;
	}
	model.addAttribute("infosuapList", infosuapList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Infosuap infosuap = new Infosuap();
	fixRenderEntityProperty(infosuap);
	model.addAttribute("infosuap", infosuap);
	setPageAttributes(model);
	return "infosuap/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("infosuap") Infosuap infosuap, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(infosuap);
	try {
	    infosuapService.insert(infosuap);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, infosuap, e);
	    fixRenderEntityProperty(infosuap);
	    return "infosuap/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + infosuap.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Infosuap infosuap = infosuapService.findById(id);
	fixRenderEntityProperty(infosuap);
	model.addAttribute("infosuap", infosuap);
	setPageAttributes(model);
	return "infosuap/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("infosuap") Infosuap infosuap, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(infosuap);
	try {
	    infosuapService.update(infosuap);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, infosuap, e);
	    fixRenderEntityProperty(infosuap);
	    return "infosuap/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + infosuap.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("infosuap") Infosuap infosuap, BindingResult result, SessionStatus status) {

	Infosuap objToDelete = infosuapService.findById(infosuap.getId());
	try {
	    infosuapService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(infosuap);
	    return "infosuap/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Infosuap entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Infosuap entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
