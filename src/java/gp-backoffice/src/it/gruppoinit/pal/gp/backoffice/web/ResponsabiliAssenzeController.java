package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliAssenze;
import it.gruppoinit.pal.gp.core.service.ResponsabiliAssenzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

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
@SessionAttributes("responsabiliassenze")
public class ResponsabiliAssenzeController extends BaseController<ResponsabiliAssenze> {

    @Autowired
    private ResponsabiliAssenzeService responsabiliassenzeService;
    @Autowired
    private ResponsabiliService responsabiliService;

    @RequestMapping
    public ModelMap list(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	List<ResponsabiliAssenze> responsabiliassenzeList = responsabiliassenzeService.findByResponsabile(codice, null, null);
	ModelMap model = new ModelMap(responsabiliassenzeList);
	boolean export = createJMesaExport(request, response, responsabiliassenzeList);
	if (export) {
	    return null;
	}
	model.addAttribute("responsabiliassenzeList", responsabiliassenzeList);
	model.addAttribute("codiceResponsabile", codice);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codice") Integer codice, Model model) {

	Responsabili responsabili = responsabiliService.findById(new PkId(codice));
	ResponsabiliAssenze responsabiliassenze = new ResponsabiliAssenze();
	responsabiliassenze.setResponsabili(responsabili);
	fixRenderEntityProperty(responsabiliassenze);
	model.addAttribute("responsabiliassenze", responsabiliassenze);
	model.addAttribute("codiceResponsabile", responsabili.getId().getCodice());
	setPageAttributes(model);
	return "responsabiliassenze/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("responsabiliassenze") ResponsabiliAssenze responsabiliassenze, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(responsabiliassenze);
	try {
	    responsabiliassenzeService.insert(responsabiliassenze);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabiliassenze, e);
	    fixRenderEntityProperty(responsabiliassenze);
	    return "responsabiliassenze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + responsabiliassenze.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	ResponsabiliAssenze responsabiliassenze = responsabiliassenzeService.findById(id);
	fixRenderEntityProperty(responsabiliassenze);
	model.addAttribute("responsabiliassenze", responsabiliassenze);
	model.addAttribute("codiceResponsabile", responsabiliassenze.getResponsabili().getId().getCodice());
	setPageAttributes(model);
	return "responsabiliassenze/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("responsabiliassenze") ResponsabiliAssenze responsabiliassenze, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(responsabiliassenze);
	try {
	    responsabiliassenzeService.update(responsabiliassenze);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabiliassenze, e);
	    fixRenderEntityProperty(responsabiliassenze);
	    return "responsabiliassenze/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + responsabiliassenze.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("responsabiliassenze") ResponsabiliAssenze responsabiliassenze, BindingResult result, SessionStatus status) {

	ResponsabiliAssenze objToDelete = responsabiliassenzeService.findById(responsabiliassenze.getId());
	try {
	    responsabiliassenzeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, responsabiliassenze, e);
	    fixRenderEntityProperty(responsabiliassenze);
	    return "responsabiliassenze/form";
	}
	status.setComplete();
	return "redirect:list.htm?codice=" + objToDelete.getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(ResponsabiliAssenze entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ResponsabiliAssenze entity) {

	if (entity.getResponsabili() == null) {
	    entity.setResponsabili(new Responsabili());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
