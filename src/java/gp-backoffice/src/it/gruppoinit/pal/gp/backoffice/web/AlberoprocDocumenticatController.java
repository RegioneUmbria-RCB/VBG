/**
 * 
 */
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
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

/**
 * @author lucap
 * 
 */
@Controller
@SessionAttributes("alberoprocdocumenticat")
public class AlberoprocDocumenticatController extends BaseController<AlberoprocDocumenticat> {

    @Autowired
    private AlberoprocDocumenticatService alberoprocDocumenticatService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<AlberoprocDocumenticat> alberoprocdocumenticatList = alberoprocDocumenticatService.findAll(null, null);
	ModelMap model = new ModelMap(alberoprocdocumenticatList);
	boolean export = createJMesaExport(request, response, alberoprocdocumenticatList);
	if (export)
	    return null;
	model.addAttribute("alberoprocdocumenticatList", alberoprocdocumenticatList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("alberoprocdocumenticat") AlberoprocDocumenticat alberoprocDocumenticat, BindingResult result,
	    SessionStatus status) {

	AlberoprocDocumenticat objToDelete = alberoprocDocumenticatService.findById(alberoprocDocumenticat.getId());
	try {
	    alberoprocDocumenticatService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoprocDocumenticat);
	    return "alberoprocdocumenticat/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoprocdocumenticat") AlberoprocDocumenticat alberoprocDocumenticat, BindingResult result,
	    SessionStatus status) {

	// recupero il software corrente e lo setto in alberoprocdocumenticat
	Software software = softwareService.findById(ORMHelper.getSoftware());
	alberoprocDocumenticat.setSoftware(software);
	// recupero l'oggetto corrente e lo inserisco in alberoprocDocumenticat
	Oggetti oggetto = oggettiService.findById(alberoprocDocumenticat.getOggetto().getId());
	alberoprocDocumenticat.setOggetto(oggetto);
	fixMergeEntityProperty(alberoprocDocumenticat);
	try {
	    alberoprocDocumenticatService.insert(alberoprocDocumenticat);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenticat, e);
	    fixRenderEntityProperty(alberoprocDocumenticat);
	    return "alberoprocdocumenticat/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocDocumenticat.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoprocdocumenticat") AlberoprocDocumenticat alberoprocDocumenticat, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il software corrente e lo setto in alberoprocdocumenticat
	Software software = softwareService.findById(ORMHelper.getSoftware());
	alberoprocDocumenticat.setSoftware(software);
	// recupero l'oggetto corrente e lo inserisco in alberoprocDocumenticat
	Oggetti oggetto = oggettiService.findById(alberoprocDocumenticat.getOggetto().getId());
	alberoprocDocumenticat.setOggetto(oggetto);
	fixMergeEntityProperty(alberoprocDocumenticat);
	try {
	    alberoprocDocumenticatService.update(alberoprocDocumenticat);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenticat, e);
	    fixRenderEntityProperty(alberoprocDocumenticat);
	    return "alberoprocdocumenticat/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoprocDocumenticat.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	AlberoprocDocumenticat alberoprocDocumenticat = new AlberoprocDocumenticat();
	alberoprocDocumenticat.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(alberoprocDocumenticat);
	model.addAttribute("alberoprocdocumenticat", alberoprocDocumenticat);
	setPageAttributes(model);
	return "alberoprocdocumenticat/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocDocumenticat alberoprocDocumenticat = alberoprocDocumenticatService.findById(id);
	fixRenderEntityProperty(alberoprocDocumenticat);
	model.addAttribute("alberoprocdocumenticat", alberoprocDocumenticat);
	setPageAttributes(model);
	return "alberoprocdocumenticat/form";
    }

    @Override
    protected void fixMergeEntityProperty(AlberoprocDocumenticat entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null) || entity.getSoftware().getCodice().equals("")) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlberoprocDocumenticat entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
