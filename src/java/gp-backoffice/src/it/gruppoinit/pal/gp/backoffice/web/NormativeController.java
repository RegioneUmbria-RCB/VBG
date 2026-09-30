package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.NormativeService;

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
@SessionAttributes("normative")
public class NormativeController extends BaseController<Normative> {

    @Autowired
    private NormativeService normativeService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Normative> normativeList = normativeService.findAll(null, null);
	ModelMap model = new ModelMap(normativeList);
	boolean export = createJMesaExport(request, response, normativeList);
	if (export) {
	    return null;
	}
	model.addAttribute("normativeList", normativeList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Normative normative = new Normative();
	fixRenderEntityProperty(normative);
	model.addAttribute("normative", normative);
	setPageAttributes(model);
	return "normative/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("normative") Normative normative, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(normative);
	try {
	    normativeService.insert(normative);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, normative, e);
	    fixRenderEntityProperty(normative);
	    return "normative/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + normative.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Normative normative = normativeService.findById(id);
	fixRenderEntityProperty(normative);
	model.addAttribute("normative", normative);
	setPageAttributes(model);
	return "normative/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("normative") Normative normative, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(normative);
	try {
	    normativeService.update(normative);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, normative, e);
	    fixRenderEntityProperty(normative);
	    return "normative/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + normative.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("normative") Normative normative, BindingResult result, SessionStatus status) {

	Normative objToDelete = normativeService.findById(normative.getId());
	try {
	    normativeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(normative);
	    return "normative/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Normative entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Normative entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
