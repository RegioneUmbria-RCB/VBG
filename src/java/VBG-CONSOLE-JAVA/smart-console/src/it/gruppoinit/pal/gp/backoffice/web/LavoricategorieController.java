package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LavoricategorieService;
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
//DAELIMINARE @Controller
@SessionAttributes("lavoricategorie")
public class LavoricategorieController extends BaseController<Lavoricategorie> {

    @Autowired
    private LavoricategorieService lavoricategorieService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Lavoricategorie> lavoricategorieList = lavoricategorieService.findAll(null, null);
	ModelMap model = new ModelMap(lavoricategorieList);
	boolean export = createJMesaExport(request, response, lavoricategorieList);
	if (export) {
	    return null;
	}
	model.addAttribute("lavoricategorieList", lavoricategorieList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	Lavoricategorie lavoricategorie = new Lavoricategorie();
	lavoricategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(lavoricategorie);
	model.addAttribute("lavoricategorie", lavoricategorie);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoricategorie/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("lavoricategorie") Lavoricategorie lavoricategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoricategorie);
	lavoricategorie.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    lavoricategorieService.insert(lavoricategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoricategorie, e);
	    fixRenderEntityProperty(lavoricategorie);
	    return "lavoricategorie/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + lavoricategorie.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Lavoricategorie lavoricategorie = lavoricategorieService.findById(id);
	fixRenderEntityProperty(lavoricategorie);
	model.addAttribute("lavoricategorie", lavoricategorie);
	setPageAttributes(model);
	// §§§END§§§
	return "lavoricategorie/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("lavoricategorie") Lavoricategorie lavoricategorie, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(lavoricategorie);
	try {
	    lavoricategorieService.update(lavoricategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, lavoricategorie, e);
	    fixRenderEntityProperty(lavoricategorie);
	    return "lavoricategorie/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + lavoricategorie.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("lavoricategorie") Lavoricategorie lavoricategorie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Lavoricategorie objToDelete = lavoricategorieService.findById(lavoricategorie.getId());
	try {
	    lavoricategorieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(lavoricategorie);
	    return "lavoricategorie/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Lavoricategorie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Lavoricategorie entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
