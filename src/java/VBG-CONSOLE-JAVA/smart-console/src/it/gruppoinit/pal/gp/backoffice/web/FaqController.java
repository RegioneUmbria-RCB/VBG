package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.FaqService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
@SessionAttributes("faq")
public class FaqController extends BaseController<Faq> {

    @Autowired
    private FaqService faqService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	List<String> softwareList = new ArrayList<String>();
	Set<Responsabilisoftware> responsabilisoftwareList = responsabile.getSoftwareAbilitati();
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwareList) {
	    softwareList.add(responsabilisoftware.getSoftware().getCodice());
	}
	List<Faq> faqList = faqService.findByFilter(softwareList, null, null);
	ModelMap model = new ModelMap(faqList);
	boolean export = createJMesaExport(request, response, faqList);
	if (export) {
	    return null;
	}
	model.addAttribute("faqList", faqList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Faq faq = new Faq();
	fixRenderEntityProperty(faq);
	model.addAttribute("faq", faq);
	setPageAttributes(model);
	return "faq/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("faq") Faq faq, BindingResult result, SessionStatus status) {

	try {
	    faqService.insert(faq);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, faq, e);
	    fixRenderEntityProperty(faq);
	    setPageAttributes(model);
	    return "faq/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + faq.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Faq faq = faqService.findById(id);
	fixRenderEntityProperty(faq);
	model.addAttribute("faq", faq);
	setPageAttributes(model);
	return "faq/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("faq") Faq faq, BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    faqService.update(faq);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, faq, e);
	    fixRenderEntityProperty(faq);
	    setPageAttributes(model);
	    return "faq/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + faq.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("faq") Faq faq, BindingResult result, SessionStatus status) {

	Faq objToDelete = faqService.findById(faq.getId());
	try {
	    faqService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(faq);
	    setPageAttributes(model);
	    return "faq/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Faq entity) {

	if (entity.getFaqclassi() != null && entity.getFaqclassi().getId() != null && entity.getFaqclassi().getId().getCodice() == null) {
	    entity.setFaqclassi(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Faq entity) {

	if (entity.getFaqclassi() == null) {
	    entity.setFaqclassi(new Faqclassi());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	List<Software> listSoftware = softwareService.findSoftwareAbilitati(responsabile);
	model.addAttribute("listSoftware", listSoftware);
    }
}
