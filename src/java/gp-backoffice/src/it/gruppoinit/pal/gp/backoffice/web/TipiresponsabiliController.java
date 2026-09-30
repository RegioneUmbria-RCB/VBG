package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.service.TipiresponsabiliService;

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
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("tipiresponsabili")
public class TipiresponsabiliController extends BaseController<Tipiresponsabili> {

    @Autowired
    private TipiresponsabiliService tipiresponsabiliService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiresponsabili> tipiresponsabiliList = tipiresponsabiliService.findAll(null, null);
	ModelMap model = new ModelMap(tipiresponsabiliList);
	boolean export = createJMesaExport(request, response, tipiresponsabiliList);
	if (export)
	    return null;
	model.addAttribute("tipiresponsabiliList", tipiresponsabiliList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiresponsabili") Tipiresponsabili tipiresponsabili, BindingResult result, SessionStatus status) {

	Tipiresponsabili objToDelete = tipiresponsabiliService.findById(tipiresponsabili.getId());
	try {
	    tipiresponsabiliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiresponsabili);
	    return "tipiresponsabili/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiresponsabili") Tipiresponsabili tipiresponsabili, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipiresponsabili);
	try {
	    tipiresponsabiliService.insert(tipiresponsabili);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiresponsabili, e);
	    fixRenderEntityProperty(tipiresponsabili);
	    return "tipiresponsabili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiresponsabili.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiresponsabili") Tipiresponsabili tipiresponsabili, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipiresponsabili);
	try {
	    tipiresponsabiliService.update(tipiresponsabili);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiresponsabili, e);
	    fixRenderEntityProperty(tipiresponsabili);
	    return "tipiresponsabili/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiresponsabili.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipiresponsabili tipiresponsabili = new Tipiresponsabili();
	fixRenderEntityProperty(tipiresponsabili);
	model.addAttribute("tipiresponsabili", tipiresponsabili);
	setPageAttributes(model);
	return "tipiresponsabili/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiresponsabili tipiresponsabili = tipiresponsabiliService.findById(id);
	fixRenderEntityProperty(tipiresponsabili);
	model.addAttribute("tipiresponsabili", tipiresponsabili);
	setPageAttributes(model);
	return "tipiresponsabili/form";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiresponsabili entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiresponsabili entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
