package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;

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
@SessionAttributes("tempificazioni")
public class TempificazioniController extends BaseController<Tempificazioni> {

    @Autowired
    private TempificazioniService tempificazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tempificazioni> tempificazioniList = tempificazioniService.findAll(null, null);
	ModelMap model = new ModelMap(tempificazioniList);
	boolean export = createJMesaExport(request, response, tempificazioniList);
	if (export) {
	    return null;
	}
	model.addAttribute("tempificazioniList", tempificazioniList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Tempificazioni tempificazioni = new Tempificazioni();
	fixRenderEntityProperty(tempificazioni);
	model.addAttribute("tempificazioni", tempificazioni);
	setPageAttributes(model);
	return "tempificazioni/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tempificazioni") Tempificazioni tempificazioni, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(tempificazioni);
	try {
	    tempificazioniService.insert(tempificazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tempificazioni, e);
	    fixRenderEntityProperty(tempificazioni);
	    return "tempificazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tempificazioni.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tempificazioni tempificazioni = tempificazioniService.findById(id);
	fixRenderEntityProperty(tempificazioni);
	model.addAttribute("tempificazioni", tempificazioni);
	setPageAttributes(model);
	return "tempificazioni/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("tempificazioni") Tempificazioni tempificazioni, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tempificazioni);
	try {
	    tempificazioniService.update(tempificazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tempificazioni, e);
	    fixRenderEntityProperty(tempificazioni);
	    return "tempificazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tempificazioni.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("tempificazioni") Tempificazioni tempificazioni, BindingResult result, SessionStatus status) {

	Tempificazioni objToDelete = tempificazioniService.findById(tempificazioni.getId());
	try {
	    tempificazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tempificazioni);
	    return "tempificazioni/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Tempificazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Tempificazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
