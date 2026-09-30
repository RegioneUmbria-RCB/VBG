package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiaree;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiareeService;

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
@SessionAttributes("tipiaree")
public class TipiareeController extends BaseController<Tipiaree> {

    @Autowired
    private TipiareeService tipiareeService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiaree> tipiareeList = tipiareeService.findAll(null, null);
	ModelMap model = new ModelMap(tipiareeList);
	boolean export = createJMesaExport(request, response, tipiareeList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiareeList", tipiareeList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipiaree tipiaree = new Tipiaree();
	tipiaree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipiaree);
	model.addAttribute("tipiaree", tipiaree);
	setPageAttributes(model);
	return "tipiaree/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiaree") Tipiaree tipiaree, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipiaree);
	tipiaree.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipiareeService.insert(tipiaree);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiaree, e);
	    fixRenderEntityProperty(tipiaree);
	    return "tipiaree/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiaree.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiaree tipiaree = tipiareeService.findById(id);
	fixRenderEntityProperty(tipiaree);
	model.addAttribute("tipiaree", tipiaree);
	setPageAttributes(model);
	return "tipiaree/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiaree") Tipiaree tipiaree, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(tipiaree);
	try {
	    tipiareeService.update(tipiaree);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiaree, e);
	    fixRenderEntityProperty(tipiaree);
	    return "tipiaree/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiaree.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiaree") Tipiaree tipiaree, BindingResult result, SessionStatus status) {

	Tipiaree objToDelete = tipiareeService.findById(tipiaree.getId());
	try {
	    tipiareeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiaree);
	    return "tipiaree/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiaree entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiaree entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
