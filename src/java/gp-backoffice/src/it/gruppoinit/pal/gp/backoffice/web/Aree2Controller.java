package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree2;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.Aree2Service;
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
@SessionAttributes("aree2")
public class Aree2Controller extends BaseController<Aree2> {

    @Autowired
    private Aree2Service aree2Service;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Aree2> aree2List = aree2Service.findAll(null, null);
	ModelMap model = new ModelMap(aree2List);
	boolean export = createJMesaExport(request, response, aree2List);
	if (export) {
	    return null;
	}
	model.addAttribute("aree2List", aree2List);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Aree2 aree2 = new Aree2();
	aree2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(aree2);
	model.addAttribute("aree2", aree2);
	setPageAttributes(model);
	return "aree2/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("aree2") Aree2 aree2, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(aree2);
	aree2.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    aree2Service.insert(aree2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, aree2, e);
	    fixRenderEntityProperty(aree2);
	    return "aree2/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + aree2.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Aree2 aree2 = aree2Service.findById(id);
	fixRenderEntityProperty(aree2);
	model.addAttribute("aree2", aree2);
	setPageAttributes(model);
	return "aree2/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("aree2") Aree2 aree2, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(aree2);
	try {
	    aree2Service.update(aree2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, aree2, e);
	    fixRenderEntityProperty(aree2);
	    return "aree2/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + aree2.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("aree2") Aree2 aree2, BindingResult result, SessionStatus status) {

	Aree2 objToDelete = aree2Service.findById(aree2.getId());
	try {
	    aree2Service.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(aree2);
	    return "aree2/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Aree2 entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Aree2 entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
