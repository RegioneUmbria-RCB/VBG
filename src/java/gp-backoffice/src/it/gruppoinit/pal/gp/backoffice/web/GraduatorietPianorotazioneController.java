package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.GraduatorietPianorotazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

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
@SessionAttributes("graduatorietpianorotazione")
public class GraduatorietPianorotazioneController extends BaseController<GraduatorietPianorotazione> {

    @Autowired
    private GraduatorietPianorotazioneService graduatorietpianorotazioneService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<GraduatorietPianorotazione> graduatorietpianorotazioneList = graduatorietpianorotazioneService.findAll(null, null);
	ModelMap model = new ModelMap(graduatorietpianorotazioneList);
	boolean export = createJMesaExport(request, response, graduatorietpianorotazioneList);
	if (export) {
	    return null;
	}
	model.addAttribute("graduatorietpianorotazioneList", graduatorietpianorotazioneList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	GraduatorietPianorotazione graduatorietpianorotazione = new GraduatorietPianorotazione();
	fixRenderEntityProperty(graduatorietpianorotazione);
	model.addAttribute("graduatorietpianorotazione", graduatorietpianorotazione);
	setPageAttributes(model);
	return "graduatorietpianorotazione/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("graduatorietpianorotazione") GraduatorietPianorotazione graduatorietpianorotazione, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(graduatorietpianorotazione);
	try {
	    graduatorietpianorotazioneService.insert(graduatorietpianorotazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatorietpianorotazione, e);
	    fixRenderEntityProperty(graduatorietpianorotazione);
	    return "graduatorietpianorotazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + graduatorietpianorotazione.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	GraduatorietPianorotazione graduatorietpianorotazione = graduatorietpianorotazioneService.findById(id);
	fixRenderEntityProperty(graduatorietpianorotazione);
	model.addAttribute("graduatorietpianorotazione", graduatorietpianorotazione);
	setPageAttributes(model);
	return "graduatorietpianorotazione/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("graduatorietpianorotazione") GraduatorietPianorotazione graduatorietpianorotazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(graduatorietpianorotazione);
	try {
	    graduatorietpianorotazioneService.update(graduatorietpianorotazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatorietpianorotazione, e);
	    fixRenderEntityProperty(graduatorietpianorotazione);
	    return "graduatorietpianorotazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + graduatorietpianorotazione.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("graduatorietpianorotazione") GraduatorietPianorotazione graduatorietpianorotazione, BindingResult result,
	    SessionStatus status) {

	GraduatorietPianorotazione objToDelete = graduatorietpianorotazioneService.findById(graduatorietpianorotazione.getId());
	try {
	    graduatorietpianorotazioneService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(graduatorietpianorotazione);
	    return "graduatorietpianorotazione/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(GraduatorietPianorotazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(GraduatorietPianorotazione entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
