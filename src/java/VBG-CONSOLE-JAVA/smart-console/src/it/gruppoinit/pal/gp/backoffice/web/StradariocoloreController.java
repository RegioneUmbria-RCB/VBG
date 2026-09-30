package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.web.StradariocoloreCommand;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;

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
@SessionAttributes("stradariocolore")
public class StradariocoloreController extends BaseController<Stradariocolore> {

    @Autowired
    private StradariocoloreService stradariocoloreService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Stradariocolore> stradariocoloreList = stradariocoloreService.findAll();
	ModelMap model = new ModelMap(stradariocoloreList);
	boolean export = createJMesaExport(request, response, stradariocoloreList);
	if (export) {
	    return null;
	}
	model.addAttribute("stradariocoloreList", stradariocoloreList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Stradariocolore entity = new Stradariocolore();
	StradariocoloreCommand stradariocolore = new StradariocoloreCommand();
	fixRenderEntityProperty(stradariocolore.getEntity());
	stradariocolore.setEntity(entity);
	stradariocolore.setDisplayMode(StradariocoloreCommand.NEW);
	model.addAttribute("stradariocolore", stradariocolore);
	setPageAttributes(model);
	return "stradariocolore/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("stradariocolore") StradariocoloreCommand stradariocolore, BindingResult result,
	    SessionStatus status) {

	Stradariocolore entity = stradariocolore.getEntity();
	fixMergeEntityProperty(stradariocolore.getEntity());
	try {
	    stradariocoloreService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradariocolore.getEntity(), true, e);
	    fixRenderEntityProperty(stradariocolore.getEntity());
	    stradariocolore.setEntity(entity);
	    stradariocolore.setDisplayMode(StradariocoloreCommand.NEW);
	    model.addAttribute("stradariocolore", stradariocolore);
	    return "stradariocolore/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodicecolore() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") String codice, Model model, HttpServletRequest request) {

	StradariocoloreId id = new StradariocoloreId(codice);
	Stradariocolore entity = stradariocoloreService.findById(id);
	StradariocoloreCommand stradariocolore = new StradariocoloreCommand();
	stradariocolore.setEntity(entity);
	stradariocolore.setDisplayMode(StradariocoloreCommand.VIEW);
	fixRenderEntityProperty(stradariocolore.getEntity());
	model.addAttribute("stradariocolore", stradariocolore);
	setPageAttributes(model);
	return "stradariocolore/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("stradariocolore") StradariocoloreCommand stradariocolore, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Stradariocolore entity = stradariocolore.getEntity();
	stradariocolore.setEntity(entity);
	fixMergeEntityProperty(entity);
	try {
	    stradariocoloreService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradariocolore.getEntity(), true, e);
	    fixRenderEntityProperty(stradariocolore.getEntity());
	    stradariocolore.setEntity(entity);
	    stradariocolore.setDisplayMode(StradariocoloreCommand.VIEW);
	    model.addAttribute("stradariocolore", stradariocolore);
	    return "stradariocolore/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodicecolore() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("stradariocolore") StradariocoloreCommand stradariocolore, BindingResult result,
	    SessionStatus status) {

	Stradariocolore objToDelete = stradariocoloreService.findById(stradariocolore.getEntity().getId());
	try {
	    stradariocoloreService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradariocolore.getEntity(), true, e);
	    fixRenderEntityProperty(stradariocolore.getEntity());
	    stradariocolore.setEntity(objToDelete);
	    stradariocolore.setDisplayMode(StradariocoloreCommand.VIEW);
	    model.addAttribute("stradariocolore", stradariocolore);
	    return "stradariocolore/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Stradariocolore entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Stradariocolore entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
