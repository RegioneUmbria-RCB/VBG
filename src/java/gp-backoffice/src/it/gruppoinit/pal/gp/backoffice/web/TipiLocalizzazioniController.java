package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;

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

@Controller
@SessionAttributes("tipiLocalizzazioni")
public class TipiLocalizzazioniController extends BaseController<TipiLocalizzazioni> {

    @Autowired
    private TipiLocalizzazioniService tipiLocalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<TipiLocalizzazioni> tipiLocalizzazioniList = tipiLocalizzazioniService.findAll(null, null);
	ModelMap model = new ModelMap(tipiLocalizzazioniList);
	boolean export = createJMesaExport(request, response, tipiLocalizzazioniList);
	if (export)
	    return null;
	model.addAttribute("tipiLocalizzazioniList", tipiLocalizzazioniList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("tipiLocalizzazioni") TipiLocalizzazioni tipiLocalizzazioni, BindingResult result, SessionStatus status) {

	TipiLocalizzazioni objToDelete = tipiLocalizzazioniService.findById(tipiLocalizzazioni.getId());
	try {
	    tipiLocalizzazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipiLocalizzazioni);
	    return "tipilocalizzazioni/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipiLocalizzazioni") TipiLocalizzazioni tipiLocalizzazioni, BindingResult result, SessionStatus status) {

	// recupero il software corrente e lo setto in letteretipo
	fixMergeEntityProperty(tipiLocalizzazioni);
	try {
	    tipiLocalizzazioniService.insert(tipiLocalizzazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiLocalizzazioni, e);
	    fixRenderEntityProperty(tipiLocalizzazioni);
	    return "tipilocalizzazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiLocalizzazioni.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipiLocalizzazioni") TipiLocalizzazioni tipiLocalizzazioni, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipiLocalizzazioni);
	try {
	    tipiLocalizzazioniService.update(tipiLocalizzazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipiLocalizzazioni, e);
	    fixRenderEntityProperty(tipiLocalizzazioni);
	    return "tipilocalizzazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipiLocalizzazioni.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	TipiLocalizzazioni tipiLocalizzazioni = new TipiLocalizzazioni();
	fixRenderEntityProperty(tipiLocalizzazioni);
	model.addAttribute("tipiLocalizzazioni", tipiLocalizzazioni);
	setPageAttributes(model);
	return "tipilocalizzazioni/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	TipiLocalizzazioni tipiLocalizzazioni = tipiLocalizzazioniService.findById(id);
	fixRenderEntityProperty(tipiLocalizzazioni);
	model.addAttribute("tipiLocalizzazioni", tipiLocalizzazioni);
	setPageAttributes(model);
	return "tipilocalizzazioni/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(TipiLocalizzazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(TipiLocalizzazioni entity) {

    }
}
