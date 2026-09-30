package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorocerca;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BachecalavorocercaService;

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
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("bachecalavorocerca")
public class BachecalavorocercaController extends BaseController<Bachecalavorocerca> {

    @Autowired
    private BachecalavorocercaService bachecalavorocercaService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Bachecalavorocerca> bachecalavorocercaList = bachecalavorocercaService.findAll(null, null);
	ModelMap model = new ModelMap(bachecalavorocercaList);
	boolean export = createJMesaExport(request, response, bachecalavorocercaList);
	if (export) {
	    return null;
	}
	model.addAttribute("bachecalavorocercaList", bachecalavorocercaList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Bachecalavorocerca bachecalavorocerca = new Bachecalavorocerca();
	fixRenderEntityProperty(bachecalavorocerca);
	model.addAttribute("bachecalavorocerca", bachecalavorocerca);
	setPageAttributes(model);
	return "bachecalavorocerca/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("bachecalavorocerca") Bachecalavorocerca bachecalavorocerca, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavorocerca);
	try {
	    bachecalavorocercaService.insert(bachecalavorocerca);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavorocerca, e);
	    fixRenderEntityProperty(bachecalavorocerca);
	    return "bachecalavorocerca/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bachecalavorocerca.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Bachecalavorocerca bachecalavorocerca = bachecalavorocercaService.findById(id);
	fixRenderEntityProperty(bachecalavorocerca);
	model.addAttribute("bachecalavorocerca", bachecalavorocerca);
	setPageAttributes(model);
	return "bachecalavorocerca/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("bachecalavorocerca") Bachecalavorocerca bachecalavorocerca, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavorocerca);
	try {
	    bachecalavorocercaService.update(bachecalavorocerca);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavorocerca, e);
	    fixRenderEntityProperty(bachecalavorocerca);
	    return "bachecalavorocerca/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bachecalavorocerca.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("bachecalavorocerca") Bachecalavorocerca bachecalavorocerca, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Bachecalavorocerca objToDelete = bachecalavorocercaService.findById(bachecalavorocerca.getId());
	try {
	    bachecalavorocercaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bachecalavorocerca);
	    return "bachecalavorocerca/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Bachecalavorocerca entity) {

	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Bachecalavorocerca entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
