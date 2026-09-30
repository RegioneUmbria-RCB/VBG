package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiDService;
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
@SessionAttributes("istanzeaccessoattid")
public class IstanzeAccessoAttiDController extends BaseController<IstanzeAccessoAttiD> {

    @Autowired
    private IstanzeAccessoAttiDService istanzeaccessoattidService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<IstanzeAccessoAttiD> istanzeaccessoattidList = istanzeaccessoattidService.findAll(null, null);
	ModelMap model = new ModelMap(istanzeaccessoattidList);
	boolean export = createJMesaExport(request, response, istanzeaccessoattidList);
	if (export) {
	    return null;
	}
	model.addAttribute("istanzeaccessoattidList", istanzeaccessoattidList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	IstanzeAccessoAttiD istanzeaccessoattid = new IstanzeAccessoAttiD();
	fixRenderEntityProperty(istanzeaccessoattid);
	model.addAttribute("istanzeaccessoattid", istanzeaccessoattid);
	setPageAttributes(model);
	return "istanzeaccessoattid/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("istanzeaccessoattid") IstanzeAccessoAttiD istanzeaccessoattid, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(istanzeaccessoattid);
	try {
	    istanzeaccessoattidService.insert(istanzeaccessoattid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattid, e);
	    fixRenderEntityProperty(istanzeaccessoattid);
	    return "istanzeaccessoattid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattid.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IstanzeAccessoAttiD istanzeaccessoattid = istanzeaccessoattidService.findById(id);
	fixRenderEntityProperty(istanzeaccessoattid);
	model.addAttribute("istanzeaccessoattid", istanzeaccessoattid);
	setPageAttributes(model);
	return "istanzeaccessoattid/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeaccessoattid") IstanzeAccessoAttiD istanzeaccessoattid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(istanzeaccessoattid);
	try {
	    istanzeaccessoattidService.update(istanzeaccessoattid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattid, e);
	    fixRenderEntityProperty(istanzeaccessoattid);
	    return "istanzeaccessoattid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattid.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeaccessoattid") IstanzeAccessoAttiD istanzeaccessoattid, BindingResult result, SessionStatus status) {

	IstanzeAccessoAttiD objToDelete = istanzeaccessoattidService.findById(istanzeaccessoattid.getId());
	try {
	    istanzeaccessoattidService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzeaccessoattid);
	    return "istanzeaccessoattid/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(IstanzeAccessoAttiD entity) {

    }

    @Override
    protected void fixRenderEntityProperty(IstanzeAccessoAttiD entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
