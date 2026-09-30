package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Normegenerali;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.NormegeneraliService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.HashSet;
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
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("normegenerali")
public class NormegeneraliController extends BaseController<Normegenerali> {

    @Autowired
    private NormegeneraliService normegeneraliService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Set<Software> softwareList = new HashSet<Software>();
	Set<Responsabilisoftware> responsabilisoftwareList = responsabile.getSoftwareAbilitati();
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwareList) {
	    softwareList.add(responsabilisoftware.getSoftware());
	}
	List<Normegenerali> normegeneraliList = normegeneraliService.findByFilter(softwareList);
	ModelMap model = new ModelMap(normegeneraliList);
	boolean export = createJMesaExport(request, response, normegeneraliList);
	if (export) {
	    return null;
	}
	model.addAttribute("normegeneraliList", normegeneraliList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	Normegenerali normegenerali = new Normegenerali();
	fixRenderEntityProperty(normegenerali);
	model.addAttribute("normegenerali", normegenerali);
	setPageAttributes(model);
	return "normegenerali/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("normegenerali") Normegenerali normegenerali, BindingResult result, SessionStatus status) {

	try {
	    normegeneraliService.insert(normegenerali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, normegenerali, e);
	    fixRenderEntityProperty(normegenerali);
	    setPageAttributes(model);
	    return "normegenerali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + normegenerali.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Normegenerali normegenerali = normegeneraliService.findById(id);
	fixRenderEntityProperty(normegenerali);
	model.addAttribute("normegenerali", normegenerali);
	setPageAttributes(model);
	return "normegenerali/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("normegenerali") Normegenerali normegenerali, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Oggetti oggetto = oggettiService.findById(normegenerali.getOggetti().getId());
	normegenerali.setOggetti(oggetto);
	try {
	    normegeneraliService.update(normegenerali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, normegenerali, e);
	    fixRenderEntityProperty(normegenerali);
	    setPageAttributes(model);
	    return "normegenerali/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + normegenerali.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("normegenerali") Normegenerali normegenerali, BindingResult result, SessionStatus status) {

	Normegenerali objToDelete = normegeneraliService.findById(normegenerali.getId());
	try {
	    normegeneraliService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(normegenerali);
	    setPageAttributes(model);
	    return "normegenerali/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Normegenerali entity) {

	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Normegenerali entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
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
