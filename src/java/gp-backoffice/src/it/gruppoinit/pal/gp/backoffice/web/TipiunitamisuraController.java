package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;
import it.gruppoinit.pal.gp.core.service.TipiunitamisuraService;

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
@SessionAttributes("tipiunitamisura")
public class TipiunitamisuraController extends BaseController<Tipiunitamisura> {

    @Autowired
    private TipiunitamisuraService tipiunitamisuraService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipiunitamisura> tipiunitamisuraList = tipiunitamisuraService.findAll(null, null);
	ModelMap model = new ModelMap(tipiunitamisuraList);
	boolean export = createJMesaExport(request, response, tipiunitamisuraList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipiunitamisuraList", tipiunitamisuraList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tipiunitamisura tipiunitamisura = new Tipiunitamisura();
	fixRenderEntityProperty(tipiunitamisura);
	model.addAttribute("tipiunitamisura", tipiunitamisura);
	setPageAttributes(model);
	return "tipiunitamisura/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiunitamisura") Tipiunitamisura tipiunitamisura, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tipiunitamisura);
	try {
	    tipiunitamisuraService.insert(tipiunitamisura);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiunitamisura, e);
	    fixRenderEntityProperty(tipiunitamisura);
	    return "tipiunitamisura/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiunitamisura.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipiunitamisura tipiunitamisura = tipiunitamisuraService.findById(id);
	fixRenderEntityProperty(tipiunitamisura);
	model.addAttribute("tipiunitamisura", tipiunitamisura);
	setPageAttributes(model);
	return "tipiunitamisura/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiunitamisura") Tipiunitamisura tipiunitamisura, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipiunitamisura);
	try {
	    tipiunitamisuraService.update(tipiunitamisura);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiunitamisura, e);
	    fixRenderEntityProperty(tipiunitamisura);
	    return "tipiunitamisura/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiunitamisura.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiunitamisura") Tipiunitamisura tipiunitamisura, BindingResult result, SessionStatus status) {

	Tipiunitamisura objToDelete = tipiunitamisuraService.findById(tipiunitamisura.getId());
	try {
	    tipiunitamisuraService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiunitamisura);
	    return "tipiunitamisura/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tipiunitamisura entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tipiunitamisura entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
