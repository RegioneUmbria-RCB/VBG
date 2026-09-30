package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RiFormegiuridiche;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;

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
 * 
 */
@SessionAttributes("formegiuridiche")
@Controller
public class FormegiuridicheController extends BaseController<Formegiuridiche> {

    @Autowired
    private FormegiuridicheService formegiuridicheService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Formegiuridiche> formegiuridicheList = formegiuridicheService.findAll(null, null);
	ModelMap model = new ModelMap(formegiuridicheList);
	boolean export = createJMesaExport(request, response, formegiuridicheList);
	if (export)
	    return null;
	model.addAttribute("formegiuridicheList", formegiuridicheList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("formegiuridiche") Formegiuridiche formegiuridiche, BindingResult result, SessionStatus status) {

	Formegiuridiche objToDelete = formegiuridicheService.findById(formegiuridiche.getId());
	try {
	    formegiuridicheService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(formegiuridiche);
	    return "formegiuridiche/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("formegiuridiche") Formegiuridiche formegiuridiche, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(formegiuridiche);
	try {
	    formegiuridicheService.insert(formegiuridiche);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, formegiuridiche, e);
	    fixRenderEntityProperty(formegiuridiche);
	    return "formegiuridiche/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + formegiuridiche.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("formegiuridiche") Formegiuridiche formegiuridiche, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(formegiuridiche);
	try {
	    formegiuridicheService.update(formegiuridiche);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, formegiuridiche, e);
	    fixRenderEntityProperty(formegiuridiche);
	    return "formegiuridiche/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + formegiuridiche.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Formegiuridiche formegiuridiche = new Formegiuridiche();
	fixRenderEntityProperty(formegiuridiche);
	model.addAttribute("formegiuridiche", formegiuridiche);
	setPageAttributes(model);
	return "formegiuridiche/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Formegiuridiche formegiuridiche = formegiuridicheService.findById(id);
	fixRenderEntityProperty(formegiuridiche);
	model.addAttribute("formegiuridiche", formegiuridiche);
	setPageAttributes(model);
	return "formegiuridiche/form";
    }

    @Override
    protected void fixMergeEntityProperty(Formegiuridiche entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Formegiuridiche entity) {

	if (entity != null) {
	    if (entity.getRiFormegiuridiche() == null) {
		entity.setRiFormegiuridiche(new RiFormegiuridiche());
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
