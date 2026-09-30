package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Fidejussionestati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FidejussionestatiService;
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
//DAELIMINARE @Controller
@SessionAttributes("fidejussionestati")
public class FidejussionestatiController extends BaseController<Fidejussionestati> {

    @Autowired
    private FidejussionestatiService fidejussionestatiService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Fidejussionestati> fidejussionestatiList = fidejussionestatiService.findAll(null, null);
	ModelMap model = new ModelMap(fidejussionestatiList);
	boolean export = createJMesaExport(request, response, fidejussionestatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("fidejussionestatiList", fidejussionestatiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Fidejussionestati fidejussionestati = new Fidejussionestati();
	fidejussionestati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(fidejussionestati);
	model.addAttribute("fidejussionestati", fidejussionestati);
	setPageAttributes(model);
	return "fidejussionestati/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("fidejussionestati") Fidejussionestati fidejussionestati, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(fidejussionestati);
	fidejussionestati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    fidejussionestatiService.insert(fidejussionestati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, fidejussionestati, e);
	    fixRenderEntityProperty(fidejussionestati);
	    return "fidejussionestati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + fidejussionestati.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Fidejussionestati fidejussionestati = fidejussionestatiService.findById(id);
	fixRenderEntityProperty(fidejussionestati);
	model.addAttribute("fidejussionestati", fidejussionestati);
	setPageAttributes(model);
	return "fidejussionestati/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("fidejussionestati") Fidejussionestati fidejussionestati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(fidejussionestati);
	try {
	    fidejussionestatiService.update(fidejussionestati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, fidejussionestati, e);
	    fixRenderEntityProperty(fidejussionestati);
	    return "fidejussionestati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + fidejussionestati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("fidejussionestati") Fidejussionestati fidejussionestati, BindingResult result, SessionStatus status) {

	Fidejussionestati objToDelete = fidejussionestatiService.findById(fidejussionestati.getId());
	try {
	    fidejussionestatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(fidejussionestati);
	    return "fidejussionestati/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Fidejussionestati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Fidejussionestati entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
