package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Quesiti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.QuesitiCommand;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.QuesitiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.IOException;
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
@SessionAttributes("quesiti")
public class QuesitiController extends BaseController<Quesiti> {

    @Autowired
    private QuesitiService quesitiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

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
	List<Quesiti> quesitiList = quesitiService.findByFilter(softwareList);
	ModelMap model = new ModelMap(quesitiList);
	boolean export = createJMesaExport(request, response, quesitiList);
	if (export) {
	    return null;
	}
	model.addAttribute("quesitiList", quesitiList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	QuesitiCommand quesiti = new QuesitiCommand();
	fixRenderEntityProperty(quesiti.getEntity());
	setCommandAttributes(quesiti);
	model.addAttribute("quesiti", quesiti);
	setPageAttributes(model);
	return "quesiti/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("quesiti") QuesitiCommand quesiti, BindingResult result, SessionStatus status) {

	try {
	    quesitiService.insert(quesiti.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, quesiti.getEntity(), true, e);
	    fixRenderEntityProperty(quesiti.getEntity());
	    setCommandAttributes(quesiti);
	    setPageAttributes(model);
	    return "quesiti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + quesiti.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Quesiti entity = quesitiService.findById(id);
	QuesitiCommand quesiti = new QuesitiCommand();
	fixRenderEntityProperty(entity);
	quesiti.setEntity(entity);
	setCommandAttributes(quesiti);
	model.addAttribute("quesiti", quesiti);
	setPageAttributes(model);
	return "quesiti/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("quesiti") QuesitiCommand quesiti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    SessionDetails sessionDetails = getSessionDetails(request);
	    quesitiService.update(quesiti.getEntity(), sessionDetails);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, quesiti.getEntity(), true, e);
	    fixRenderEntityProperty(quesiti.getEntity());
	    setCommandAttributes(quesiti);
	    setPageAttributes(model);
	    return "quesiti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + quesiti.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("quesiti") QuesitiCommand quesiti, BindingResult result, SessionStatus status) {

	Quesiti objToDelete = quesitiService.findById(quesiti.getEntity().getId());
	try {
	    quesitiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(quesiti.getEntity());
	    setPageAttributes(model);
	    return "quesiti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam("codice") Integer codice, @RequestParam("letto") Boolean letto, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Quesiti quesito = quesitiService.findById(id);
	quesito.setLetto(letto);
	try {
	    quesitiService.update(quesito);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public String creaFaq(@ModelAttribute("quesiti") QuesitiCommand quesiti, BindingResult result, SessionStatus status) {

	try {
	    quesitiService.insertFaq(quesiti.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, quesiti.getEntity(), true, e);
	    fixRenderEntityProperty(quesiti.getEntity());
	    return "quesiti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + quesiti.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @Override
    protected void fixMergeEntityProperty(Quesiti entity) {

	if (entity.getFaqclassi() != null && entity.getFaqclassi().getId() != null && entity.getFaqclassi().getId().getCodice() == null) {
	    entity.setFaqclassi(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(""))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Quesiti entity) {

	if (entity.getFaqclassi() == null) {
	    entity.setFaqclassi(new Faqclassi());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    private void setCommandAttributes(QuesitiCommand command) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	List<Software> listSoftware = softwareService.findSoftwareAbilitati(responsabile);
	command.setSoftwares(listSoftware);
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
