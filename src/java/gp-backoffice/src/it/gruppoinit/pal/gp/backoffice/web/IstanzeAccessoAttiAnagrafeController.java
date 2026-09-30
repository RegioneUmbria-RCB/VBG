package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiAnagrafeService;
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
@SessionAttributes("istanzeaccessoattianagrafe")
public class IstanzeAccessoAttiAnagrafeController extends BaseController<IstanzeAccessoAttiAnagrafe> {

    @Autowired
    private IstanzeAccessoAttiAnagrafeService istanzeaccessoattianagrafeService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<IstanzeAccessoAttiAnagrafe> istanzeaccessoattianagrafeList = istanzeaccessoattianagrafeService.findAll(null, null);
	ModelMap model = new ModelMap(istanzeaccessoattianagrafeList);
	boolean export = createJMesaExport(request, response, istanzeaccessoattianagrafeList);
	if (export) {
	    return null;
	}
	model.addAttribute("istanzeaccessoattianagrafeList", istanzeaccessoattianagrafeList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	IstanzeAccessoAttiAnagrafe istanzeaccessoattianagrafe = new IstanzeAccessoAttiAnagrafe();
	fixRenderEntityProperty(istanzeaccessoattianagrafe);
	model.addAttribute("istanzeaccessoattianagrafe", istanzeaccessoattianagrafe);
	setPageAttributes(model);
	return "istanzeaccessoattianagrafe/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("istanzeaccessoattianagrafe") IstanzeAccessoAttiAnagrafe istanzeaccessoattianagrafe, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityProperty(istanzeaccessoattianagrafe);
	try {
	    istanzeaccessoattianagrafeService.insert(istanzeaccessoattianagrafe);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattianagrafe, e);
	    fixRenderEntityProperty(istanzeaccessoattianagrafe);
	    return "istanzeaccessoattianagrafe/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattianagrafe.getId().toString() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("fkidaccessoatti") Integer codice, @RequestParam("codiceanagrafe") Integer anagrafe, Model model,
	    HttpServletRequest request) {

	IstanzeAccessoAttiAnagrafeId id = new IstanzeAccessoAttiAnagrafeId(codice, anagrafe);
	IstanzeAccessoAttiAnagrafe istanzeaccessoattianagrafe = istanzeaccessoattianagrafeService.findById(id);
	fixRenderEntityProperty(istanzeaccessoattianagrafe);
	model.addAttribute("istanzeaccessoattianagrafe", istanzeaccessoattianagrafe);
	setPageAttributes(model);
	return "istanzeaccessoattianagrafe/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeaccessoattianagrafe") IstanzeAccessoAttiAnagrafe istanzeaccessoattianagrafe, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(istanzeaccessoattianagrafe);
	try {
	    istanzeaccessoattianagrafeService.update(istanzeaccessoattianagrafe);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattianagrafe, e);
	    fixRenderEntityProperty(istanzeaccessoattianagrafe);
	    return "istanzeaccessoattianagrafe/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattianagrafe.getId().toString() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeaccessoattianagrafe") IstanzeAccessoAttiAnagrafe istanzeaccessoattianagrafe, BindingResult result,
	    SessionStatus status) {

	IstanzeAccessoAttiAnagrafe objToDelete = istanzeaccessoattianagrafeService.findById(istanzeaccessoattianagrafe.getId());
	try {
	    istanzeaccessoattianagrafeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzeaccessoattianagrafe);
	    return "istanzeaccessoattianagrafe/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(IstanzeAccessoAttiAnagrafe entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzeAccessoAttiAnagrafe entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
