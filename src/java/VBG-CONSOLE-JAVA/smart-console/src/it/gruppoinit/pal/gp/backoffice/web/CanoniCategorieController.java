package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CanoniCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CanoniCategorieService;
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
@SessionAttributes("canonicategorie")
public class CanoniCategorieController extends BaseController<CanoniCategorie> {

    @Autowired
    private CanoniCategorieService canonicategorieService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CanoniCategorie> canonicategorieList = canonicategorieService.findAll(null, null);
	ModelMap model = new ModelMap(canonicategorieList);
	boolean export = createJMesaExport(request, response, canonicategorieList);
	if (export) {
	    return null;
	}
	model.addAttribute("canonicategorieList", canonicategorieList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CanoniCategorie canonicategorie = new CanoniCategorie();
	canonicategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(canonicategorie);
	model.addAttribute("canonicategorie", canonicategorie);
	setPageAttributes(model);
	return "canonicategorie/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("canonicategorie") CanoniCategorie canonicategorie, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(canonicategorie);
	canonicategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    canonicategorieService.insert(canonicategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, canonicategorie, e);
	    fixRenderEntityProperty(canonicategorie);
	    return "canonicategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + canonicategorie.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CanoniCategorie canonicategorie = canonicategorieService.findById(id);
	fixRenderEntityProperty(canonicategorie);
	model.addAttribute("canonicategorie", canonicategorie);
	setPageAttributes(model);
	return "canonicategorie/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("canonicategorie") CanoniCategorie canonicategorie, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(canonicategorie);
	try {
	    canonicategorieService.update(canonicategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, canonicategorie, e);
	    fixRenderEntityProperty(canonicategorie);
	    return "canonicategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + canonicategorie.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("canonicategorie") CanoniCategorie canonicategorie, BindingResult result, SessionStatus status) {

	CanoniCategorie objToDelete = canonicategorieService.findById(canonicategorie.getId());
	try {
	    canonicategorieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(canonicategorie);
	    return "canonicategorie/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(CanoniCategorie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CanoniCategorie entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
