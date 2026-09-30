package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
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
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("concessioniuso")
public class ConcessioniusoController extends BaseController<Concessioniuso> {

    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Concessioniuso> concessioniusoList = concessioniusoService.findAll(null, null);
	ModelMap model = new ModelMap(concessioniusoList);
	boolean export = createJMesaExport(request, response, concessioniusoList);
	if (export) {
	    return null;
	}
	model.addAttribute("concessioniusoList", concessioniusoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Concessioniuso concessioniuso = new Concessioniuso();
	concessioniuso.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(concessioniuso);
	model.addAttribute("concessioniuso", concessioniuso);
	setPageAttributes(model);
	return "concessioniuso/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("concessioniuso") Concessioniuso concessioniuso, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(concessioniuso);
	concessioniuso.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    concessioniusoService.insert(concessioniuso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, concessioniuso, e);
	    fixRenderEntityProperty(concessioniuso);
	    return "concessioniuso/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + concessioniuso.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Concessioniuso concessioniuso = concessioniusoService.findById(id);
	fixRenderEntityProperty(concessioniuso);
	model.addAttribute("concessioniuso", concessioniuso);
	setPageAttributes(model);
	return "concessioniuso/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("concessioniuso") Concessioniuso concessioniuso, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(concessioniuso);
	try {
	    concessioniusoService.update(concessioniuso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, concessioniuso, e);
	    fixRenderEntityProperty(concessioniuso);
	    return "concessioniuso/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + concessioniuso.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("concessioniuso") Concessioniuso concessioniuso, BindingResult result, SessionStatus status) {

	Concessioniuso objToDelete = concessioniusoService.findById(concessioniuso.getId());
	try {
	    concessioniusoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(concessioniuso);
	    return "concessioniuso/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Concessioniuso entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Concessioniuso entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
