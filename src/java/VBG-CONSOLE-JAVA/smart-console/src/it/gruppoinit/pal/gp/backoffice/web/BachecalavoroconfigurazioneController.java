package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Bachecalavoroconfigurazione;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.service.BachecalavoroconfigurazioneService;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author Luca Proietti
 */
//DAELIMINARE @Controller
@SessionAttributes("bachecalavoroconfigurazione")
public class BachecalavoroconfigurazioneController extends BaseController<Bachecalavoroconfigurazione> {

    @Autowired
    private BachecalavoroconfigurazioneService bachecalavoroconfigurazioneService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Bachecalavoroconfigurazione> bachecalavoroconfigurazioneList = bachecalavoroconfigurazioneService.findAll(null, null);
	ModelMap model = new ModelMap(bachecalavoroconfigurazioneList);
	boolean export = createJMesaExport(request, response, bachecalavoroconfigurazioneList);
	if (export) {
	    return null;
	}
	model.addAttribute("bachecalavoroconfigurazioneList", bachecalavoroconfigurazioneList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Bachecalavoroconfigurazione bachecalavoroconfigurazione = new Bachecalavoroconfigurazione();
	fixRenderEntityProperty(bachecalavoroconfigurazione);
	model.addAttribute("bachecalavoroconfigurazione", bachecalavoroconfigurazione);
	setPageAttributes(model);
	return "bachecalavoroconfigurazione/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("bachecalavoroconfigurazione") Bachecalavoroconfigurazione bachecalavoroconfigurazione,
	    BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavoroconfigurazione);
	try {
	    bachecalavoroconfigurazioneService.insert(bachecalavoroconfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavoroconfigurazione, e);
	    fixRenderEntityProperty(bachecalavoroconfigurazione);
	    return "bachecalavoroconfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?" + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (bachecalavoroconfigurazioneService.findById(ORMHelper.getIdcomune()) != null) {
	    Bachecalavoroconfigurazione bachecalavoroconfigurazione = bachecalavoroconfigurazioneService.findById(ORMHelper.getIdcomune());
	    fixRenderEntityProperty(bachecalavoroconfigurazione);
	    model.addAttribute("bachecalavoroconfigurazione", bachecalavoroconfigurazione);
	    setPageAttributes(model);
	    return "bachecalavoroconfigurazione/form";
	} else {
	    return "redirect:create.htm";
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("bachecalavoroconfigurazione") Bachecalavoroconfigurazione bachecalavoroconfigurazione,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bachecalavoroconfigurazione);
	try {
	    bachecalavoroconfigurazioneService.update(bachecalavoroconfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bachecalavoroconfigurazione, e);
	    fixRenderEntityProperty(bachecalavoroconfigurazione);
	    return "bachecalavoroconfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("bachecalavoroconfigurazione") Bachecalavoroconfigurazione bachecalavoroconfigurazione,
	    BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Bachecalavoroconfigurazione objToDelete = bachecalavoroconfigurazioneService.findById(bachecalavoroconfigurazione.getIdcomune());
	try {
	    bachecalavoroconfigurazioneService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(bachecalavoroconfigurazione);
	    return "bachecalavoroconfigurazione/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Bachecalavoroconfigurazione entity) {

	if (entity.getOggettiByFkBachecalavconf1Oggetti() != null && entity.getOggettiByFkBachecalavconf1Oggetti().getId() != null
		&& entity.getOggettiByFkBachecalavconf1Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkBachecalavconf1Oggetti(null);
	}
	if (entity.getOggettiByFkBachecalavconf2Oggetti() != null && entity.getOggettiByFkBachecalavconf2Oggetti().getId() != null
		&& entity.getOggettiByFkBachecalavconf2Oggetti().getId().getCodice() == null) {
	    entity.setOggettiByFkBachecalavconf2Oggetti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Bachecalavoroconfigurazione entity) {

	if (entity.getOggettiByFkBachecalavconf1Oggetti() == null) {
	    entity.setOggettiByFkBachecalavconf1Oggetti(new Oggetti());
	}
	if (entity.getOggettiByFkBachecalavconf2Oggetti() == null) {
	    entity.setOggettiByFkBachecalavconf2Oggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
