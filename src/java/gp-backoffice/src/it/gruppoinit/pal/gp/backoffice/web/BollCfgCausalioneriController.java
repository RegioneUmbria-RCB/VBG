package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneri;
import it.gruppoinit.pal.gp.core.domain.BollCfgCausalioneriId;
import it.gruppoinit.pal.gp.core.service.BollCfgCausalioneriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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
@SessionAttributes("bollcfgcausalioneri")
public class BollCfgCausalioneriController extends BaseController<BollCfgCausalioneri> {

    @Autowired
    private BollCfgCausalioneriService bollcfgcausalioneriService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<BollCfgCausalioneri> bollcfgcausalioneriList = bollcfgcausalioneriService.findAll(null, null);
	ModelMap model = new ModelMap(bollcfgcausalioneriList);
	boolean export = createJMesaExport(request, response, bollcfgcausalioneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("bollcfgcausalioneriList", bollcfgcausalioneriList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	BollCfgCausalioneri bollcfgcausalioneri = new BollCfgCausalioneri();
	fixRenderEntityProperty(bollcfgcausalioneri);
	model.addAttribute("bollcfgcausalioneri", bollcfgcausalioneri);
	setPageAttributes(model);
	return "bollcfgcausalioneri/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("bollcfgcausalioneri") BollCfgCausalioneri bollcfgcausalioneri, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(bollcfgcausalioneri);
	try {
	    bollcfgcausalioneriService.insert(bollcfgcausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgcausalioneri, e);
	    fixRenderEntityProperty(bollcfgcausalioneri);
	    return "bollcfgcausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgcausalioneri.getId().toString() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("fk_bollcfgtipo_id") Integer codice, @RequestParam("fkCoId") Integer causalioneri, Model model,
	    HttpServletRequest request) {

	BollCfgCausalioneriId id = new BollCfgCausalioneriId(codice, causalioneri);
	BollCfgCausalioneri bollcfgcausalioneri = bollcfgcausalioneriService.findById(id);
	fixRenderEntityProperty(bollcfgcausalioneri);
	model.addAttribute("bollcfgcausalioneri", bollcfgcausalioneri);
	setPageAttributes(model);
	return "bollcfgcausalioneri/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("bollcfgcausalioneri") BollCfgCausalioneri bollcfgcausalioneri, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(bollcfgcausalioneri);
	try {
	    bollcfgcausalioneriService.update(bollcfgcausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bollcfgcausalioneri, e);
	    fixRenderEntityProperty(bollcfgcausalioneri);
	    return "bollcfgcausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bollcfgcausalioneri.getId().toString() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("bollcfgcausalioneri") BollCfgCausalioneri bollcfgcausalioneri, BindingResult result, SessionStatus status) {

	BollCfgCausalioneri objToDelete = bollcfgcausalioneriService.findById(bollcfgcausalioneri.getId());
	try {
	    bollcfgcausalioneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bollcfgcausalioneri);
	    return "bollcfgcausalioneri/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(BollCfgCausalioneri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BollCfgCausalioneri entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
