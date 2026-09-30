package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipimodelliService;

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
@SessionAttributes("tipimodelli")
public class TipimodelliController extends BaseController<Tipimodelli> {

    @Autowired
    private TipimodelliService tipimodelliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipimodelli> tipimodelliList = tipimodelliService.findAll(null, null);
	ModelMap model = new ModelMap(tipimodelliList);
	boolean export = createJMesaExport(request, response, tipimodelliList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipimodelliList", tipimodelliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipimodelli tipimodelli = new Tipimodelli();
	fixRenderEntityProperty(tipimodelli);
	model.addAttribute("tipimodelli", tipimodelli);
	setPageAttributes(model);
	return "tipimodelli/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipimodelli") Tipimodelli tipimodelli, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipimodelli);
	try {
	    tipimodelliService.insert(tipimodelli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodelli, e);
	    fixRenderEntityProperty(tipimodelli);
	    return "tipimodelli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodelli.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipimodelli tipimodelli = tipimodelliService.findById(id);
	fixRenderEntityProperty(tipimodelli);
	model.addAttribute("tipimodelli", tipimodelli);
	setPageAttributes(model);
	return "tipimodelli/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipimodelli") Tipimodelli tipimodelli, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipimodelli);
	try {
	    tipimodelliService.update(tipimodelli);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipimodelli, e);
	    fixRenderEntityProperty(tipimodelli);
	    return "tipimodelli/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipimodelli.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipimodelli") Tipimodelli tipimodelli, BindingResult result, SessionStatus status) {

	Tipimodelli objToDelete = tipimodelliService.findById(tipimodelli.getId());
	try {
	    tipimodelliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipimodelli);
	    return "tipimodelli/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tipimodelli entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipimodelli entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
