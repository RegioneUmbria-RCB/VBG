package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Bachecalavorooffro;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BachecalavorooffroService;

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
@SessionAttributes("bachecalavorooffro")
public class BachecalavorooffroController extends BaseController<Bachecalavorooffro> {

    @Autowired
    private BachecalavorooffroService bachecalavorooffroService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Bachecalavorooffro> bachecalavorooffroList = bachecalavorooffroService.findAll(null, null);
	ModelMap model = new ModelMap(bachecalavorooffroList);
	boolean export = createJMesaExport(request, response, bachecalavorooffroList);
	if (export) {
	    return null;
	}
	model.addAttribute("bachecalavorooffroList", bachecalavorooffroList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Bachecalavorooffro bachecalavorooffro = new Bachecalavorooffro();
	fixRenderEntityProperty(bachecalavorooffro);
	model.addAttribute("bachecalavorooffro", bachecalavorooffro);
	setPageAttributes(model);
	return "bachecalavorooffro/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("bachecalavorooffro") Bachecalavorooffro bachecalavorooffro, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavorooffro);
	try {
	    bachecalavorooffroService.insert(bachecalavorooffro);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavorooffro, e);
	    fixRenderEntityProperty(bachecalavorooffro);
	    return "bachecalavorooffro/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bachecalavorooffro.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Bachecalavorooffro bachecalavorooffro = bachecalavorooffroService.findById(id);
	fixRenderEntityProperty(bachecalavorooffro);
	model.addAttribute("bachecalavorooffro", bachecalavorooffro);
	setPageAttributes(model);
	return "bachecalavorooffro/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("bachecalavorooffro") Bachecalavorooffro bachecalavorooffro, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavorooffro);
	try {
	    bachecalavorooffroService.update(bachecalavorooffro);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavorooffro, e);
	    fixRenderEntityProperty(bachecalavorooffro);
	    return "bachecalavorooffro/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + bachecalavorooffro.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("bachecalavorooffro") Bachecalavorooffro bachecalavorooffro, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Bachecalavorooffro objToDelete = bachecalavorooffroService.findById(bachecalavorooffro.getId());
	try {
	    bachecalavorooffroService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bachecalavorooffro);
	    return "bachecalavorooffro/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Bachecalavorooffro entity) {

	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
	if (entity.getComuni() != null && entity.getComuni().getCodicecomune() == null) {
	    entity.setComuni(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Bachecalavorooffro entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getComuni() == null) {
	    entity.setComuni(new Comuni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
