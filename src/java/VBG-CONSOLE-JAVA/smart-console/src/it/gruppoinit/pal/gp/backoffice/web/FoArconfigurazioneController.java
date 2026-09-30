/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("foArconfigurazione")
public class FoArconfigurazioneController extends BaseController<FoArconfigurazione> {

    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public String delete(@ModelAttribute("foArconfigurazione") FoArconfigurazione foArconfigurazione, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	FoArconfigurazione objToDelete = foArconfigurazioneService.findById(foArconfigurazione.getId());
	String softwareID = ORMHelper.getSoftware();
	Software softwarePage = softwareService.findById(softwareID);
	model.addAttribute("softwarePage", softwarePage);
	model.addAttribute("sw", softwarePage);
	if (objToDelete != null && softwareID.equals(objToDelete.getId().getSoftware())) {
	    try {
		foArconfigurazioneService.delete(objToDelete);
	    } catch (Exception e) {
		copyErrorsToBindingResult(result, objToDelete, e);
		fixRenderEntityProperty(foArconfigurazione);
		prepareView(request);
		setPageAttributes(model);
		return "foarconfigurazione/form";
	    }
	}
	status.setComplete();
	return "redirect:view.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("foArconfigurazione") FoArconfigurazione foArconfigurazione, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	String softwareID = ORMHelper.getSoftware();
	Software softwarePage = softwareService.findById(softwareID);
	model.addAttribute("softwarePage", softwarePage);
	model.addAttribute("sw", softwarePage);
	fixMergeEntityProperty(foArconfigurazione);
	// Recupero del scheda assiociata
	if (!EntityUtils.isNestedPropertyBlank(foArconfigurazione.getDyn2Modellit(), "id.codice")) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(foArconfigurazione.getDyn2Modellit().getId().getCodice()));
	    foArconfigurazione.setDyn2Modellit(dyn2Modellit);
	}
	try {
	    foArconfigurazioneService.insert(foArconfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foArconfigurazione, e);
	    fixRenderEntityProperty(foArconfigurazione);
	    model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	    prepareView(request);
	    setPageAttributes(model);
	    return "foarconfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("foArconfigurazione") FoArconfigurazione foArconfigurazione, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	// Recupero del scheda assiociata
	if (!EntityUtils.isNestedPropertyBlank(foArconfigurazione.getDyn2Modellit(), "id.codice")) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(foArconfigurazione.getDyn2Modellit().getId().getCodice()));
	    foArconfigurazione.setDyn2Modellit(dyn2Modellit);
	}
	if (foArconfigurazione.getCodiceoggettoFirma() != null && foArconfigurazione.getCodiceoggettoFirma().getId().getCodice() != null) {
	    Oggetti oggettiFirma = oggettiService.findById(foArconfigurazione.getCodiceoggettoFirma().getId());
	    foArconfigurazione.setCodiceoggettoFirma(oggettiFirma);
	}
	if (foArconfigurazione.getCodiceoggettoSottoscriz() != null && foArconfigurazione.getCodiceoggettoSottoscriz().getId().getCodice() != null) {
	    Oggetti oggettiSottoscriz = oggettiService.findById(foArconfigurazione.getCodiceoggettoSottoscriz().getId());
	    foArconfigurazione.setCodiceoggettoSottoscriz(oggettiSottoscriz);
	}
	if (foArconfigurazione.getOggettoWorkflow() != null && foArconfigurazione.getOggettoWorkflow().getId().getCodice() != null) {
	    Oggetti oggettoWorkflow = oggettiService.findById(foArconfigurazione.getOggettoWorkflow().getId());
	    foArconfigurazione.setOggettoWorkflow(oggettoWorkflow);
	}
	if (foArconfigurazione.getOggettoMenuxml() != null && foArconfigurazione.getOggettoMenuxml().getId().getCodice() != null) {
	    Oggetti oggettoMenuxml = oggettiService.findById(foArconfigurazione.getOggettoMenuxml().getId());
	    foArconfigurazione.setOggettoMenuxml(oggettoMenuxml);
	}
	Software softwarePage = softwareService.findById(ORMHelper.getSoftware());
	model.addAttribute("softwarePage", softwarePage);
	model.addAttribute("sw", softwarePage);
	FoArconfigurazioneId foArconfigurazioneId = foArconfigurazione.getId();
	foArconfigurazione.setId(foArconfigurazioneId);
	fixMergeEntityProperty(foArconfigurazione);
	try {
	    foArconfigurazioneService.update(foArconfigurazione);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foArconfigurazione, e);
	    fixRenderEntityProperty(foArconfigurazione);
	    model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	    prepareView(request);
	    setPageAttributes(model);
	    return "foarconfigurazione/form";
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=02";
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	FoArconfigurazione foArconfigurazione = new FoArconfigurazione();
	Software softwarePage = softwareService.findById(ORMHelper.getSoftware());
	FoArconfigurazioneId foArconfigurazioneId = new FoArconfigurazioneId();
	foArconfigurazione.setId(foArconfigurazioneId);
	fixRenderEntityProperty(foArconfigurazione);
	model.addAttribute("foArconfigurazione", foArconfigurazione);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_CREATE);
	model.addAttribute("softwarePage", softwarePage);
	model.addAttribute("sw", softwarePage);
	setPageAttributes(model);
	prepareView(request);
	return "foarconfigurazione/form";
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	String softwareID = ORMHelper.getSoftware();
	/*
	 * Controllo se è presente nella tabella FoArconfigurazione un oggetto con il software corrente
	 */
	Software softwarePage = softwareService.findById(softwareID);
	Software software = softwarePage;
	FoArconfigurazione foArconfigurazione = foArconfigurazioneService.findBySoftware(softwarePage);
	if (foArconfigurazione == null) {
	    /*
	     * Controllo se è presente nella tabella FoArconfigurazione un oggetto con il software TT
	     */
	    software = softwareService.findById(WebConstants.SOFTWARE_TT);
	    foArconfigurazione = foArconfigurazioneService.findBySoftware(software);
	    if (foArconfigurazione == null) {
		return "redirect:create.htm";
	    }
	}
	fixRenderEntityProperty(foArconfigurazione);
	foArconfigurazione.getId().setSoftware(softwareID);
	model.addAttribute("foArconfigurazione", foArconfigurazione);
	model.addAttribute("sw", software);
	model.addAttribute("softwarePage", softwarePage);
	setPageAttributes(model);
	model.addAttribute(WebConstants.DISPATCH, WebConstants.DISPATCH_VIEW);
	prepareView(request);
	return "foarconfigurazione/form";
    }

    @Override
    protected void fixMergeEntityProperty(FoArconfigurazione entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoArconfigurazione entity) {

	if (entity.getCodiceoggettoFirma() == null) {
	    entity.setCodiceoggettoFirma(new Oggetti());
	}
	if (entity.getCodiceoggettoSottoscriz() == null) {
	    entity.setCodiceoggettoSottoscriz(new Oggetti());
	}
	if (entity.getOggettoWorkflow() == null) {
	    entity.setOggettoWorkflow(new Oggetti());
	}
	if (entity.getOggettoMenuxml() == null) {
	    entity.setOggettoMenuxml(new Oggetti());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getFoArjStepsTestata() == null) {
	    entity.setFoArjStepsTestata(new FoArjStepsTestata());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean arjAttiva = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA_AREA_RISERVATA_JAVA_ATTIVA, "S");
	model.addAttribute("AREA_RISERVATA_JAVA_ATTIVA", arjAttiva);
    }

    private void prepareView(HttpServletRequest request) {

	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, request);
    }
}
