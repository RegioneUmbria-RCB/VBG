/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.EndoConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.EndoCausaliService;
import it.gruppoinit.pal.gp.core.service.EndoContiService;

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
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("endoConti")
public class EndoContiController extends BaseController<EndoConti> {

    @Autowired
    private EndoContiService endoContiService;
    @Autowired
    private EndoCausaliService endoCausaliService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<EndoConti> endoContiList = endoContiService.findAll(null, null);
	ModelMap model = new ModelMap(endoContiList);
	boolean export = createJMesaExport(request, response, endoContiList);
	if (export)
	    return null;
	model.addAttribute("endoContiList", endoContiList);
	return model;
    }

    @RequestMapping
    public String delete(@RequestParam("codice") Integer codiceendocausali, @ModelAttribute("endoConti") EndoConti endoConti, BindingResult result,
	    SessionStatus status) {

	EndoConti objToDelete = endoContiService.findById(endoConti.getId());
	try {
	    endoContiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(endoConti);
	    return "endoconti/form";
	}
	status.setComplete();
	return "redirect:../endocausali/view.htm?codice=" + codiceendocausali;
    }

    @RequestMapping
    public String insert(@ModelAttribute("endoConti") EndoConti endoConti, BindingResult result, SessionStatus status) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(endoConti);
	try {
	    endoContiService.insert(endoConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, endoConti, e);
	    fixRenderEntityProperty(endoConti);
	    return "endoconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + endoConti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("endoConti") EndoConti endoConti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(endoConti);
	try {
	    endoContiService.update(endoConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, endoConti, e);
	    fixRenderEntityProperty(endoConti);
	    return "endoconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + endoConti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("codiceendoCausali") Integer codiceEndoCausali, Model model) {

	EndoConti endoConti = new EndoConti();
	PkId idEndoCausali = new PkId(codiceEndoCausali);
	EndoCausali endoCausali = endoCausaliService.findById(idEndoCausali);
	endoConti.setEndoCausali(endoCausali);
	fixRenderEntityProperty(endoConti);
	model.addAttribute("endoConti", endoConti);
	setPageAttributes(model);
	return "endoconti/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	EndoConti endoConti = endoContiService.findById(id);
	fixRenderEntityProperty(endoConti);
	model.addAttribute("endoConti", endoConti);
	setPageAttributes(model);
	return "endoconti/form";
    }

    @Override
    protected void fixMergeEntityProperty(EndoConti entity) {

	if (entity.getEndoCausali() != null && entity.getEndoCausali().getId() != null && entity.getEndoCausali().getId().getCodice() == null) {
	    entity.setEndoCausali(null);
	}
	if (entity.getConti() != null && entity.getConti().getId() != null && entity.getConti().getId().getCodice() == null) {
	    entity.setConti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(EndoConti entity) {

	if (entity.getEndoCausali() == null) {
	    entity.setEndoCausali(new EndoCausali());
	}
	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
