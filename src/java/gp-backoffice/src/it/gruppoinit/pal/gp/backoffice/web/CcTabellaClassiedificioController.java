package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcTabellaClassiedificioService;
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
@SessionAttributes("cctabellaclassiedificio")
public class CcTabellaClassiedificioController extends BaseController<CcTabellaClassiedificio> {

    @Autowired
    private CcTabellaClassiedificioService cctabellaclassiedificioService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CcTabellaClassiedificio> cctabellaclassiedificioList = cctabellaclassiedificioService.findAll(null, null);
	ModelMap model = new ModelMap(cctabellaclassiedificioList);
	boolean export = createJMesaExport(request, response, cctabellaclassiedificioList);
	if (export) {
	    return null;
	}
	model.addAttribute("cctabellaclassiedificioList", cctabellaclassiedificioList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CcTabellaClassiedificio cctabellaclassiedificio = new CcTabellaClassiedificio();
	cctabellaclassiedificio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(cctabellaclassiedificio);
	model.addAttribute("cctabellaclassiedificio", cctabellaclassiedificio);
	setPageAttributes(model);
	return "cctabellaclassiedificio/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("cctabellaclassiedificio") CcTabellaClassiedificio cctabellaclassiedificio, BindingResult result,
	    SessionStatus status) {

	if (result.hasErrors()) {
	    return "cctabellaclassiedificio/form";
	}
	fixMergeEntityProperty(cctabellaclassiedificio);
	cctabellaclassiedificio.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    cctabellaclassiedificioService.insert(cctabellaclassiedificio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctabellaclassiedificio, e);
	    fixRenderEntityProperty(cctabellaclassiedificio);
	    return "cctabellaclassiedificio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cctabellaclassiedificio.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CcTabellaClassiedificio cctabellaclassiedificio = cctabellaclassiedificioService.findById(id);
	fixRenderEntityProperty(cctabellaclassiedificio);
	model.addAttribute("cctabellaclassiedificio", cctabellaclassiedificio);
	setPageAttributes(model);
	return "cctabellaclassiedificio/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("cctabellaclassiedificio") CcTabellaClassiedificio cctabellaclassiedificio, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "cctabellaclassiedificio/form";
	}
	fixMergeEntityProperty(cctabellaclassiedificio);
	try {
	    cctabellaclassiedificioService.update(cctabellaclassiedificio);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctabellaclassiedificio, e);
	    fixRenderEntityProperty(cctabellaclassiedificio);
	    return "cctabellaclassiedificio/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + cctabellaclassiedificio.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("cctabellaclassiedificio") CcTabellaClassiedificio cctabellaclassiedificio, BindingResult result,
	    SessionStatus status) {

	CcTabellaClassiedificio objToDelete = cctabellaclassiedificioService.findById(cctabellaclassiedificio.getId());
	try {
	    cctabellaclassiedificioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, cctabellaclassiedificio, e);
	    fixRenderEntityProperty(cctabellaclassiedificio);
	    return "cctabellaclassiedificio/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(CcTabellaClassiedificio entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcTabellaClassiedificio entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
