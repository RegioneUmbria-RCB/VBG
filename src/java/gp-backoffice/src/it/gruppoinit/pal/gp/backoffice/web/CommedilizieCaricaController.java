package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CommedilizieCarica;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CommedilizieCaricaService;

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
@SessionAttributes("commediliziecarica")
public class CommedilizieCaricaController extends BaseController<CommedilizieCarica> {

    @Autowired
    private CommedilizieCaricaService commediliziecaricaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<CommedilizieCarica> commediliziecaricaList = commediliziecaricaService.findAll(null, null);
	ModelMap model = new ModelMap(commediliziecaricaList);
	boolean export = createJMesaExport(request, response, commediliziecaricaList);
	if (export) {
	    return null;
	}
	model.addAttribute("commediliziecaricaList", commediliziecaricaList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	CommedilizieCarica commediliziecarica = new CommedilizieCarica();
	fixRenderEntityProperty(commediliziecarica);
	model.addAttribute("commediliziecarica", commediliziecarica);
	setPageAttributes(model);
	return "commediliziecarica/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("commediliziecarica") CommedilizieCarica commediliziecarica, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(commediliziecarica);
	try {
	    commediliziecaricaService.insert(commediliziecarica);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commediliziecarica, e);
	    fixRenderEntityProperty(commediliziecarica);
	    return "commediliziecarica/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commediliziecarica.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieCarica commediliziecarica = commediliziecaricaService.findById(id);
	fixRenderEntityProperty(commediliziecarica);
	model.addAttribute("commediliziecarica", commediliziecarica);
	setPageAttributes(model);
	return "commediliziecarica/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("commediliziecarica") CommedilizieCarica commediliziecarica, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(commediliziecarica);
	try {
	    commediliziecaricaService.update(commediliziecarica);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commediliziecarica, e);
	    fixRenderEntityProperty(commediliziecarica);
	    return "commediliziecarica/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commediliziecarica.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("commediliziecarica") CommedilizieCarica commediliziecarica, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieCarica objToDelete = commediliziecaricaService.findById(commediliziecarica.getId());
	try {
	    commediliziecaricaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commediliziecarica);
	    return "commediliziecarica/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieCarica entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieCarica entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
