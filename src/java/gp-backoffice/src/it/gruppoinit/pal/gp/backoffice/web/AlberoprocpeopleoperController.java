/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeopleoperService;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("alberoprocpeopleoper")
public class AlberoprocpeopleoperController extends BaseController<Alberoprocpeopleoper> {

    @Autowired
    private AlberoprocpeopleoperService alberoprocpeopleoperService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	/*
	 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	 */
	ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
	List<Alberoprocpeopleoper> alberoprocpeopleoperList = alberoprocpeopleoperService.findByAlberoProc(alberoproc);
	ModelMap model = new ModelMap(alberoprocpeopleoperList);
	boolean export = createJMesaExport(request, response, alberoprocpeopleoperList);
	if (export)
	    return null;
	model.addAttribute("alberoprocpeopleoperList", alberoprocpeopleoperList);
	model.addAttribute("alberoproc", alberoproc);
	setAttributes(request);
	return model;
    }

    private void setAttributes(HttpServletRequest request) {

	Verticalizzazioni verticalizzazioni_PEOPLE = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	if (verticalizzazioni_PEOPLE != null) {
	    if (verticalizzazioni_PEOPLE.getAttivo() == 1) {
		request.setAttribute("vert_people_attivo", true);
	    } else {
		request.setAttribute("vert_people_attivo", false);
	    }
	} else {
	    request.setAttribute("vert_people_attivo", false);
	}
	Verticalizzazioni verticalizzazioni_SIEDER = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	if (verticalizzazioni_SIEDER != null) {
	    if (verticalizzazioni_SIEDER.getAttivo() == 1) {
		request.setAttribute("vert_sieder_attivo", true);
	    } else {
		request.setAttribute("vert_sieder_attivo", false);
	    }
	} else {
	    request.setAttribute("vert_sieder_attivo", false);
	}
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoprocpeopleoper") Alberoprocpeopleoper alberoprocpeopleoper, BindingResult result,
	    SessionStatus status) {

	Alberoprocpeopleoper objToDelete = alberoprocpeopleoperService.findById(alberoprocpeopleoper.getId());
	Integer codiceAlberoproc = objToDelete.getAlberoproc().getId().getCodice();
	try {
	    alberoprocpeopleoperService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoprocpeopleoper);
	    return "alberoprocpeopleoper/form";
	}
	status.setComplete();
	return "redirect:list.htm?alberoproc.id.codice=" + codiceAlberoproc;
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoprocpeopleoper") Alberoprocpeopleoper alberoprocpeopleoper, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(alberoprocpeopleoper);
	try {
	    alberoprocpeopleoperService.insert(alberoprocpeopleoper);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocpeopleoper, e);
	    fixRenderEntityProperty(alberoprocpeopleoper);
	    return "alberoprocpeopleoper/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocpeopleoper.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoprocpeopleoper") Alberoprocpeopleoper alberoprocpeopleoper, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(alberoprocpeopleoper);
	try {
	    alberoprocpeopleoperService.update(alberoprocpeopleoper);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocpeopleoper, e);
	    fixRenderEntityProperty(alberoprocpeopleoper);
	    return "alberoprocpeopleoper/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocpeopleoper.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("alberoproc.id.codice") Integer codiceAlberoproc, Model model) {

	Alberoprocpeopleoper alberoprocpeopleoper = new Alberoprocpeopleoper();
	PkId idAlberoproc = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(idAlberoproc);
	alberoprocpeopleoper.setAlberoproc(alberoproc);
	fixRenderEntityProperty(alberoprocpeopleoper);
	model.addAttribute("alberoprocpeopleoper", alberoprocpeopleoper);
	setPageAttributes(model);
	return "alberoprocpeopleoper/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoprocpeopleoper alberoprocpeopleoper = alberoprocpeopleoperService.findById(id);
	fixRenderEntityProperty(alberoprocpeopleoper);
	model.addAttribute("alberoprocpeopleoper", alberoprocpeopleoper);
	setPageAttributes(model);
	setAttributes(request);
	return "alberoprocpeopleoper/form";
    }

    @Override
    protected void fixMergeEntityProperty(Alberoprocpeopleoper entity) {

	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Alberoprocpeopleoper entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
