/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocpeoplehrefService;

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
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("alberoprocpeoplehref")
public class AlberoprocpeoplehrefController extends BaseController<Alberoprocpeoplehref> {

    @Autowired
    private AlberoprocpeoplehrefService alberoprocpeoplehrefService;
    @Autowired
    private AlberoprocService alberoprocService;

    @RequestMapping
    public ModelMap list(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	/*
	 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	 */
	ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
	List<Alberoprocpeoplehref> alberoprocpeoplehrefList = alberoprocpeoplehrefService.findByAlberoProc(alberoproc);
	ModelMap model = new ModelMap(alberoprocpeoplehrefList);
	boolean export = createJMesaExport(request, response, alberoprocpeoplehrefList);
	if (export)
	    return null;
	model.addAttribute("alberoprocpeoplehrefList", alberoprocpeoplehrefList);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoprocpeoplehref") Alberoprocpeoplehref alberoprocpeoplehref, BindingResult result, SessionStatus status) {

	Alberoprocpeoplehref objToDelete = alberoprocpeoplehrefService.findById(alberoprocpeoplehref.getId());
	Integer codiceAlberoproc = objToDelete.getAlberoproc().getId().getCodice();
	try {
	    alberoprocpeoplehrefService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoprocpeoplehref);
	    return "alberoprocpeoplehref/form";
	}
	status.setComplete();
	return "redirect:list.htm?alberoproc.id.codice=" + codiceAlberoproc;
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoprocpeoplehref") Alberoprocpeoplehref alberoprocpeoplehref, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(alberoprocpeoplehref);
	try {
	    alberoprocpeoplehrefService.insert(alberoprocpeoplehref);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocpeoplehref, e);
	    fixRenderEntityProperty(alberoprocpeoplehref);
	    return "alberoprocpeoplehref/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocpeoplehref.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoprocpeoplehref") Alberoprocpeoplehref alberoprocpeoplehref, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(alberoprocpeoplehref);
	try {
	    alberoprocpeoplehrefService.update(alberoprocpeoplehref);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocpeoplehref, e);
	    fixRenderEntityProperty(alberoprocpeoplehref);
	    return "alberoprocpeoplehref/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocpeoplehref.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("alberoproc.id.codice") Integer codiceAlberoproc, Model model) {

	Alberoprocpeoplehref alberoprocpeoplehref = new Alberoprocpeoplehref();
	PkId idAlberoproc = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(idAlberoproc);
	alberoprocpeoplehref.setAlberoproc(alberoproc);
	fixRenderEntityProperty(alberoprocpeoplehref);
	model.addAttribute("alberoprocpeoplehref", alberoprocpeoplehref);
	setPageAttributes(model);
	return "alberoprocpeoplehref/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoprocpeoplehref alberoprocpeoplehref = alberoprocpeoplehrefService.findById(id);
	fixRenderEntityProperty(alberoprocpeoplehref);
	model.addAttribute("alberoprocpeoplehref", alberoprocpeoplehref);
	setPageAttributes(model);
	return "alberoprocpeoplehref/form";
    }

    @Override
    protected void fixMergeEntityProperty(Alberoprocpeoplehref entity) {

	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Alberoprocpeoplehref entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
