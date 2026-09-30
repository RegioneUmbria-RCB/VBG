package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Ateco;
import it.gruppoinit.pal.gp.core.service.AtecoService;

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
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("ateco")
public class AtecoController extends BaseController<Ateco> {

    @Autowired
    private AtecoService atecoService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Ateco> atecoList = atecoService.findAll(null, null);
	ModelMap model = new ModelMap(atecoList);
	boolean export = createJMesaExport(request, response, atecoList);
	if (export) {
	    return null;
	}
	model.addAttribute("atecoList", atecoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Ateco ateco = new Ateco();
	fixRenderEntityProperty(ateco);
	model.addAttribute("ateco", ateco);
	setPageAttributes(model);
	return "ateco/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("ateco") Ateco ateco, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(ateco);
	try {
	    atecoService.insert(ateco);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ateco, e);
	    fixRenderEntityProperty(ateco);
	    return "ateco/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ateco.getId() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Ateco ateco = atecoService.findById(codice);
	fixRenderEntityProperty(ateco);
	model.addAttribute("ateco", ateco);
	setPageAttributes(model);
	return "ateco/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("ateco") Ateco ateco, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(ateco);
	try {
	    atecoService.update(ateco);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ateco, e);
	    fixRenderEntityProperty(ateco);
	    return "ateco/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ateco.getId() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("ateco") Ateco ateco, BindingResult result, SessionStatus status) {

	Ateco objToDelete = atecoService.findById(ateco.getId());
	try {
	    atecoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(ateco);
	    return "ateco/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Ateco entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Ateco entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
