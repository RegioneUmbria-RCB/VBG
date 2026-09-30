package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FaqclassiService;

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
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("faqclassi")
public class FaqclassiController extends BaseController<Faqclassi> {

    @Autowired
    private FaqclassiService faqclassiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Faqclassi> faqclassiList = faqclassiService.findAll(null, null);
	ModelMap model = new ModelMap(faqclassiList);
	boolean export = createJMesaExport(request, response, faqclassiList);
	if (export) {
	    return null;
	}
	model.addAttribute("faqclassiList", faqclassiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Faqclassi faqclassi = new Faqclassi();
	fixRenderEntityProperty(faqclassi);
	model.addAttribute("faqclassi", faqclassi);
	setPageAttributes(model);
	return "faqclassi/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("faqclassi") Faqclassi faqclassi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(faqclassi);
	try {
	    faqclassiService.insert(faqclassi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, faqclassi, e);
	    fixRenderEntityProperty(faqclassi);
	    return "faqclassi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + faqclassi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Faqclassi faqclassi = faqclassiService.findById(id);
	fixRenderEntityProperty(faqclassi);
	model.addAttribute("faqclassi", faqclassi);
	setPageAttributes(model);
	return "faqclassi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("faqclassi") Faqclassi faqclassi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(faqclassi);
	try {
	    faqclassiService.update(faqclassi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, faqclassi, e);
	    fixRenderEntityProperty(faqclassi);
	    return "faqclassi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + faqclassi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("faqclassi") Faqclassi faqclassi, BindingResult result, SessionStatus status) {

	Faqclassi objToDelete = faqclassiService.findById(faqclassi.getId());
	try {
	    faqclassiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(faqclassi);
	    return "faqclassi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Faqclassi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Faqclassi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
