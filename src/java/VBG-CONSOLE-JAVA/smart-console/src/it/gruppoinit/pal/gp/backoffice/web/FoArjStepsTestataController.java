package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.FoArjStepsBaseService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

@Controller
@SessionAttributes("foArjStepsTestata")
public class FoArjStepsTestataController extends BaseController<FoArjStepsTestata> {

    @Autowired
    private FoArjStepsTestataService foArjStepsTestataService;
    @Autowired
    private FoArjStepsService foArjStepsService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private FoArjStepsBaseService foArjStepsBaseService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<FoArjStepsTestata> list = foArjStepsTestataService.findAll(null, null);
	ModelMap model = new ModelMap(list);
	boolean export = createJMesaExport(request, response, list);
	if (export)
	    return null;
	model.addAttribute("list", list);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("foArjStepsTestata") FoArjStepsTestata foArjStepsTestata, BindingResult result,
	    SessionStatus status) {

	FoArjStepsTestata objToDelete = foArjStepsTestataService.findById(foArjStepsTestata.getId());
	try {
	    foArjStepsTestataService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(foArjStepsTestata);
	    prepareViewPage(foArjStepsTestata, model);
	    return "foarjstepstestata/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String deleteStep(@RequestParam("codiceTestata") Integer codiceTestata, @RequestParam("codice") Integer codice) {

	FoArjStepsTestata foArjStepsTestata = foArjStepsTestataService.findById(new PkId(codiceTestata));
	FoArjSteps entity = foArjStepsService.findById(new PkId(codice));
	String status = "05";
	if (foArjStepsTestata.getId().getCodice().compareTo(entity.getFoArjStepsTestata().getId().getCodice()) != 0) {
	    FlashMessages.getWarnings().add("Errore in cancellazione di uno stepa: dati passati non coerenti");
	    status = "03";
	} else {
	    try {
		foArjStepsService.delete(entity);
	    } catch (Exception e) {
		FlashMessages.getWarnings().add("Errore in cancellazione di uno step: " + e.getMessage());
		status = "03";
	    }
	}
	return "redirect:view.htm?codice=" + codiceTestata + "&status_msg=" + status;
    }

    @RequestMapping
    public String updateOrdineStep(@RequestParam("codiceTestata") Integer codiceTestata, @RequestParam("codiceStep") Integer codiceStep,
	    @RequestParam("isUp") Boolean isUp) {

	String status = "02";
	if (isUp == null) {
	    isUp = Boolean.FALSE;
	}
	foArjStepsService.updateStepSpostaOrdine(ORMHelper.getIdcomune(), codiceTestata, codiceStep, isUp.booleanValue());
	return "redirect:view.htm?codice=" + codiceTestata + "&status_msg=" + status;
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("foArjStepsTestata") FoArjStepsTestata foArjStepsTestata, BindingResult result,
	    SessionStatus status) {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	foArjStepsTestata.setSoftware(software);
	fixMergeEntityProperty(foArjStepsTestata);
	try {
	    foArjStepsTestataService.insert(foArjStepsTestata);
	    List<FoArjStepsBase> list = foArjStepsBaseService.findAll(null, null);
	    for (FoArjStepsBase foArjStepsBase : list) {
		FoArjSteps entity = new FoArjSteps();
		entity.setFoArjStepsTestata(foArjStepsTestata);
		entity.setFoArjStepsBase(foArjStepsBase);
		entity.setOrdine(foArjStepsBase.getOrdineDefault());
		entity.setTitolo(foArjStepsBase.getTitolo());
		entity.setDescrizione(foArjStepsBase.getDescrizione());
		entity.setAbilitato(Boolean.TRUE);
		foArjStepsService.insert(entity);
	    }
	} catch (Exception e) {
	    foArjStepsTestata.getId().setCodice(null);
	    copyErrorsToBindingResult(result, foArjStepsTestata, e);
	    fixRenderEntityProperty(foArjStepsTestata);
	    return "foarjstepstestata/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foArjStepsTestata.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("foArjStepsTestata") FoArjStepsTestata foArjStepsTestata, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	foArjStepsTestata.setSoftware(software);
	fixMergeEntityProperty(foArjStepsTestata);
	try {
	    foArjStepsTestataService.update(foArjStepsTestata);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foArjStepsTestata, e);
	    fixRenderEntityProperty(foArjStepsTestata);
	    prepareViewPage(foArjStepsTestata, model);
	    return "foarjstepstestata/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foArjStepsTestata.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	FoArjStepsTestata foArjStepsTestata = new FoArjStepsTestata();
	fixRenderEntityProperty(foArjStepsTestata);
	model.addAttribute("foArjStepsTestata", foArjStepsTestata);
	setPageAttributes(model);
	return "foarjstepstestata/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	FoArjStepsTestata foArjStepsTestata = foArjStepsTestataService.findById(id);
	model.addAttribute("foArjStepsTestata", foArjStepsTestata);
	setPageAttributes(model);
	prepareViewPage(foArjStepsTestata, model);
	return "foarjstepstestata/form";
    }

    public void prepareViewPage(FoArjStepsTestata foArjStepsTestata, Model model) {

	String messaggioConfigurazione = foArjStepsService.verificaConfigurazioneStep(foArjStepsTestata.getId().getIdcomune(), foArjStepsTestata
		.getId().getCodice());
	if (StringUtils.isNotBlank(messaggioConfigurazione)) {
	    FlashMessages.getInfos().add(messaggioConfigurazione);
	}
	List<FoArjSteps> stepsAttivi = foArjStepsService
		.findByTestata(foArjStepsTestata.getId().getIdcomune(), foArjStepsTestata.getId().getCodice());
	model.addAttribute("listaStepsConfigurati", stepsAttivi);
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(FoArjStepsTestata entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoArjStepsTestata entity) {

    }
}
