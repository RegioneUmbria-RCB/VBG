package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Linkutili;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LinkutiliService;

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
 * @author francescop
 */
@Controller
@SessionAttributes("linkutili")
public class LinkutiliController extends BaseController<Linkutili> {

    @Autowired
    private LinkutiliService linkutiliService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Linkutili> linkutiliList = linkutiliService.findAll(null, null);
	ModelMap model = new ModelMap(linkutiliList);
	boolean export = createJMesaExport(request, response, linkutiliList);
	if (export) {
	    return null;
	}
	model.addAttribute("linkutiliList", linkutiliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Linkutili linkutili = new Linkutili();
	fixRenderEntityProperty(linkutili);
	model.addAttribute("linkutili", linkutili);
	setPageAttributes(model);
	return "linkutili/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("linkutili") Linkutili linkutili, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(linkutili);
	try {
	    linkutiliService.insert(linkutili);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, linkutili, e);
	    fixRenderEntityProperty(linkutili);
	    return "linkutili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + linkutili.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Linkutili linkutili = linkutiliService.findById(id);
	fixRenderEntityProperty(linkutili);
	model.addAttribute("linkutili", linkutili);
	setPageAttributes(model);
	return "linkutili/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("linkutili") Linkutili linkutili, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(linkutili);
	try {
	    linkutiliService.update(linkutili);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, linkutili, e);
	    fixRenderEntityProperty(linkutili);
	    return "linkutili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + linkutili.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("linkutili") Linkutili linkutili, BindingResult result, SessionStatus status) {

	Linkutili objToDelete = linkutiliService.findById(linkutili.getId());
	try {
	    linkutiliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(linkutili);
	    return "linkutili/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Linkutili entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Linkutili entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
