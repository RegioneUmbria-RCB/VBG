package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CcValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCoeffcontributoService;
import it.gruppoinit.pal.gp.core.service.CcValiditacoefficientiService;
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
@SessionAttributes("ccvaliditacoefficienti")
public class CcValiditacoefficientiController extends BaseController<CcValiditacoefficienti> {

    @Autowired
    private CcValiditacoefficientiService ccvaliditacoefficientiService;
    @Autowired
    CcCoeffcontributoService cccoeffcontributoService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<CcValiditacoefficienti> ccvaliditacoefficientiList = ccvaliditacoefficientiService.findAll(null, null);
	ModelMap model = new ModelMap(ccvaliditacoefficientiList);
	boolean export = createJMesaExport(request, response, ccvaliditacoefficientiList);
	if (export) {
	    return null;
	}
	model.addAttribute("ccvaliditacoefficientiList", ccvaliditacoefficientiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	CcValiditacoefficienti ccvaliditacoefficienti = new CcValiditacoefficienti();
	ccvaliditacoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(ccvaliditacoefficienti);
	model.addAttribute("ccvaliditacoefficienti", ccvaliditacoefficienti);
	setPageAttributes(model);
	return "ccvaliditacoefficienti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("ccvaliditacoefficienti") CcValiditacoefficienti ccvaliditacoefficienti, BindingResult result,
	    SessionStatus status) {

	if (result.hasErrors()) {
	    return "ccvaliditacoefficienti/form";
	}
	fixMergeEntityProperty(ccvaliditacoefficienti);
	ccvaliditacoefficienti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    ccvaliditacoefficientiService.insert(ccvaliditacoefficienti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccvaliditacoefficienti, e);
	    fixRenderEntityProperty(ccvaliditacoefficienti);
	    return "ccvaliditacoefficienti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ccvaliditacoefficienti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	CcValiditacoefficienti ccvaliditacoefficienti = ccvaliditacoefficientiService.findById(id);
	fixRenderEntityProperty(ccvaliditacoefficienti);
	model.addAttribute("ccvaliditacoefficienti", ccvaliditacoefficienti);
	setPageAttributes(model);
	return "ccvaliditacoefficienti/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("ccvaliditacoefficienti") CcValiditacoefficienti ccvaliditacoefficienti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "ccvaliditacoefficienti/form";
	}
	fixMergeEntityProperty(ccvaliditacoefficienti);
	try {
	    ccvaliditacoefficientiService.update(ccvaliditacoefficienti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccvaliditacoefficienti, e);
	    fixRenderEntityProperty(ccvaliditacoefficienti);
	    return "ccvaliditacoefficienti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + ccvaliditacoefficienti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("ccvaliditacoefficienti") CcValiditacoefficienti ccvaliditacoefficienti, BindingResult result,
	    SessionStatus status) {

	CcValiditacoefficienti objToDelete = ccvaliditacoefficientiService.findById(ccvaliditacoefficienti.getId());
	try {
	    ccvaliditacoefficientiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, ccvaliditacoefficienti, e);
	    fixRenderEntityProperty(ccvaliditacoefficienti);
	    return "ccvaliditacoefficienti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(CcValiditacoefficienti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CcValiditacoefficienti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
