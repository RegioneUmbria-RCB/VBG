package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;
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
@SessionAttributes("raggruppamentocausalioneri")
public class RaggruppamentocausalioneriController extends BaseController<Raggruppamentocausalioneri> {

    @Autowired
    private RaggruppamentocausalioneriService raggruppamentocausalioneriService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Raggruppamentocausalioneri> raggruppamentocausalioneriList = raggruppamentocausalioneriService.findAll(null, null);
	ModelMap model = new ModelMap(raggruppamentocausalioneriList);
	boolean export = createJMesaExport(request, response, raggruppamentocausalioneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneriList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Raggruppamentocausalioneri raggruppamentocausalioneri = new Raggruppamentocausalioneri();
	raggruppamentocausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(raggruppamentocausalioneri);
	model.addAttribute("raggruppamentocausalioneri", raggruppamentocausalioneri);
	setPageAttributes(model);
	return "raggruppamentocausalioneri/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("raggruppamentocausalioneri") Raggruppamentocausalioneri raggruppamentocausalioneri, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(raggruppamentocausalioneri);
	raggruppamentocausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    raggruppamentocausalioneriService.insert(raggruppamentocausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, raggruppamentocausalioneri, e);
	    fixRenderEntityProperty(raggruppamentocausalioneri);
	    return "raggruppamentocausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + raggruppamentocausalioneri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Raggruppamentocausalioneri raggruppamentocausalioneri = raggruppamentocausalioneriService.findById(id);
	fixRenderEntityProperty(raggruppamentocausalioneri);
	model.addAttribute("raggruppamentocausalioneri", raggruppamentocausalioneri);
	setPageAttributes(model);
	return "raggruppamentocausalioneri/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("raggruppamentocausalioneri") Raggruppamentocausalioneri raggruppamentocausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(raggruppamentocausalioneri);
	try {
	    raggruppamentocausalioneriService.update(raggruppamentocausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, raggruppamentocausalioneri, e);
	    fixRenderEntityProperty(raggruppamentocausalioneri);
	    return "raggruppamentocausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + raggruppamentocausalioneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("raggruppamentocausalioneri") Raggruppamentocausalioneri raggruppamentocausalioneri, BindingResult result,
	    SessionStatus status) {

	Raggruppamentocausalioneri objToDelete = raggruppamentocausalioneriService.findById(raggruppamentocausalioneri.getId());
	try {
	    raggruppamentocausalioneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(raggruppamentocausalioneri);
	    return "raggruppamentocausalioneri/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Raggruppamentocausalioneri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Raggruppamentocausalioneri entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
