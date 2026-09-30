package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.MercatiCategorieService;
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

@Controller
@SessionAttributes(value = { "mercatiCategorie" })
public class MercatiCategorieController extends BaseController<MercatiCategorie> {

    @Autowired
    private MercatiCategorieService mercatiCategorieService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<MercatiCategorie> mercatiCategorieList = mercatiCategorieService.findAll(null, null);
	ModelMap model = new ModelMap(mercatiCategorieList);
	boolean export = createJMesaExport(request, response, mercatiCategorieList);
	if (export)
	    return null;
	model.addAttribute("mercatiCategorieList", mercatiCategorieList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercatiCategorie") MercatiCategorie mercatiCategorie, BindingResult result,
	    SessionStatus status) {

	MercatiCategorie objToDelete = mercatiCategorieService.findById(mercatiCategorie.getId());
	try {
	    mercatiCategorieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(mercatiCategorie);
	    return "mercaticategorie/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("mercatiCategorie") MercatiCategorie mercatiCategorie, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mercatiCategorie);
	try {
	    mercatiCategorieService.insert(mercatiCategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCategorie, e);
	    fixRenderEntityProperty(mercatiCategorie);
	    return "mercaticategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiCategorie.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("mercatiCategorie") MercatiCategorie mercatiCategorie, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mercatiCategorie);
	try {
	    mercatiCategorieService.update(mercatiCategorie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiCategorie, e);
	    fixRenderEntityProperty(mercatiCategorie);
	    return "mercaticategorie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatiCategorie.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	MercatiCategorie mercatiCategorie = new MercatiCategorie();
	Software software = softwareService.findById(ORMHelper.getSoftware());
	mercatiCategorie.setSoftware(software);
	fixRenderEntityProperty(mercatiCategorie);
	model.addAttribute("mercatiCategorie", mercatiCategorie);
	setPageAttributes(model);
	return "mercaticategorie/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiCategorie mercatiCategorie = mercatiCategorieService.findById(id);
	fixRenderEntityProperty(mercatiCategorie);
	model.addAttribute("mercatiCategorie", mercatiCategorie);
	setPageAttributes(model);
	return "mercaticategorie/form";
    }

    @Override
    protected void fixMergeEntityProperty(MercatiCategorie entity) {

    }

    @Override
    protected void fixRenderEntityProperty(MercatiCategorie entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
