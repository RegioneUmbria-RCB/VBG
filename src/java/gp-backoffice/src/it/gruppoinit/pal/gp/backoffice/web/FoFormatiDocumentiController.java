package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoFormatiDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoFormatiDocumentiService;
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
@SessionAttributes("foformatidocumenti")
public class FoFormatiDocumentiController extends BaseController<FoFormatiDocumenti> {

    @Autowired
    private FoFormatiDocumentiService foformatidocumentiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<FoFormatiDocumenti> foformatidocumentiList = foformatidocumentiService.findAll(null, null);
	ModelMap model = new ModelMap(foformatidocumentiList);
	boolean export = createJMesaExport(request, response, foformatidocumentiList);
	if (export) {
	    return null;
	}
	model.addAttribute("foformatidocumentiList", foformatidocumentiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	FoFormatiDocumenti foformatidocumenti = new FoFormatiDocumenti();
	foformatidocumenti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(foformatidocumenti);
	model.addAttribute("foformatidocumenti", foformatidocumenti);
	setPageAttributes(model);
	return "foformatidocumenti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("foformatidocumenti") FoFormatiDocumenti foformatidocumenti, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(foformatidocumenti);
	foformatidocumenti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    foformatidocumentiService.insert(foformatidocumenti);
	} catch (Exception e) {
	    //copyErrorsToBindingResult(foformatidocumentiService.getValidationMessages(), result, foformatidocumenti, e.getMessage());
	    copyErrorsToBindingResult(result, foformatidocumenti, e);
	    fixRenderEntityProperty(foformatidocumenti);
	    return "foformatidocumenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foformatidocumenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	FoFormatiDocumenti foformatidocumenti = foformatidocumentiService.findById(id);
	fixRenderEntityProperty(foformatidocumenti);
	model.addAttribute("foformatidocumenti", foformatidocumenti);
	setPageAttributes(model);
	return "foformatidocumenti/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("foformatidocumenti") FoFormatiDocumenti foformatidocumenti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(foformatidocumenti);
	try {
	    foformatidocumentiService.update(foformatidocumenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foformatidocumenti, e);
	    fixRenderEntityProperty(foformatidocumenti);
	    return "foformatidocumenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foformatidocumenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("foformatidocumenti") FoFormatiDocumenti foformatidocumenti, BindingResult result, SessionStatus status) {

	FoFormatiDocumenti objToDelete = foformatidocumentiService.findById(foformatidocumenti.getId());
	try {
	    foformatidocumentiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(foformatidocumenti);
	    return "foformatidocumenti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(FoFormatiDocumenti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoFormatiDocumenti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
