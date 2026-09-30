package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Impianti;
import it.gruppoinit.pal.gp.core.domain.Impiantiprocedure;
import it.gruppoinit.pal.gp.core.domain.ImpiantiprocedureId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.service.ImpiantiService;
import it.gruppoinit.pal.gp.core.service.ImpiantiprocedureService;
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
@SessionAttributes( { "impianti", "impiantiprocedure" })
public class ImpiantiController extends BaseController<Impianti> {

    @Autowired
    private ImpiantiService impiantiService;
    @Autowired
    private ImpiantiprocedureService impiantiprocedureService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Impianti> impiantiList = impiantiService.findAll(null, null);
	ModelMap model = new ModelMap(impiantiList);
	boolean export = createJMesaExport(request, response, impiantiList);
	if (export) {
	    return null;
	}
	model.addAttribute("impiantiList", impiantiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Impianti impianti = new Impianti();
	impianti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(impianti);
	model.addAttribute("impianti", impianti);
	setPageAttributes(model);
	return "impianti/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("impianti") Impianti impianti, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(impianti);
	impianti.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    impiantiService.insert(impianti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, impianti, e);
	    fixRenderEntityProperty(impianti);
	    return "impianti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + impianti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Impianti impianti = impiantiService.findById(id);
	fixRenderEntityProperty(impianti);
	model.addAttribute("impianti", impianti);
	setPageAttributes(model);
	return "impianti/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("impianti") Impianti impianti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(impianti);
	try {
	    impiantiService.update(impianti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, impianti, e);
	    fixRenderEntityProperty(impianti);
	    return "impianti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + impianti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("impianti") Impianti impianti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	Impianti objToDelete = impiantiService.findById(impianti.getId());
	try {
	    impiantiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(impianti);
	    return "impianti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String createDettaglio(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Impianti impianti = impiantiService.findById(id);
	Impiantiprocedure dettaglio = new Impiantiprocedure();
	dettaglio.setImpianti(impianti);
	dettaglio.getId().setCodiceimpianto(impianti.getId().getCodice());
	dettaglio.setTipiprocedure(new Tipiprocedure());
	model.addAttribute("impiantiprocedure", dettaglio);
	setPageAttributes(model);
	return "impianti/formDettaglio";
    }

    @RequestMapping
    public String insertDettaglio(@ModelAttribute("impiantiprocedure") Impiantiprocedure impiantiprocedure, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    ImpiantiprocedureId id = impiantiprocedure.getId();
	    id.setCodiceprocedura(impiantiprocedure.getTipiprocedure().getId().getCodice());
	    impiantiprocedureService.insert(impiantiprocedure);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, impiantiprocedure, e);
	    return "impianti/formDettaglio";
	}
	return "redirect:view.htm?codice=" + impiantiprocedure.getImpianti().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String deleteDettaglio(@RequestParam("codice") Integer codice, @RequestParam("tipoprocedura") Integer tipoprocedura, Model model,
	    @ModelAttribute("impianti") Impianti impianti, BindingResult result, SessionStatus status, HttpServletRequest request) {

	ImpiantiprocedureId id = new ImpiantiprocedureId();
	id.setCodiceimpianto(codice);
	id.setCodiceprocedura(tipoprocedura);
	Impiantiprocedure objToDelete = impiantiprocedureService.findById(id);
	try {
	    impiantiprocedureService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "impianti/formDettaglio";
	}
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Impianti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Impianti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
