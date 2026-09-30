package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

/**
 * 
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("mailtipo")
public class MailtipoController extends BaseController<Mailtipo> {

    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Mailtipo> mailtipoList = mailtipoService.findAll(null, null);
	ModelMap model = new ModelMap(mailtipoList);
	boolean export = createJMesaExport(request, response, mailtipoList);
	if (export) {
	    return null;
	}
	model.addAttribute("mailtipoList", mailtipoList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Mailtipo mailtipo = new Mailtipo();
	mailtipo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(mailtipo);
	model.addAttribute("mailtipo", mailtipo);
	setPageAttributes(model);
	return "mailtipo/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("mailtipo") Mailtipo mailtipo, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(mailtipo);
	mailtipo.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    mailtipoService.insert(mailtipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mailtipo, e);
	    fixRenderEntityProperty(mailtipo);
	    return "mailtipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mailtipo.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Mailtipo mailtipo = mailtipoService.findById(id);
	fixRenderEntityProperty(mailtipo);
	model.addAttribute("mailtipo", mailtipo);
	setPageAttributes(model);
	return "mailtipo/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("mailtipo") Mailtipo mailtipo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(mailtipo);
	try {
	    mailtipoService.update(mailtipo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mailtipo, e);
	    fixRenderEntityProperty(mailtipo);
	    return "mailtipo/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mailtipo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mailtipo") Mailtipo mailtipo, BindingResult result, SessionStatus status) {

	Mailtipo objToDelete = mailtipoService.findById(mailtipo.getId());
	try {
	    mailtipoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(mailtipo);
	    return "mailtipo/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Mailtipo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Mailtipo entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
