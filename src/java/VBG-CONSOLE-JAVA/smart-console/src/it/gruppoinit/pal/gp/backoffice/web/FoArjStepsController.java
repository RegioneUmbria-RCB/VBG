package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.FoArjSteps;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsBase;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParams;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsParamsBase;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoArjStepsBaseService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsBaseService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsParamsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsService;
import it.gruppoinit.pal.gp.core.service.FoArjStepsTestataService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("foArjSteps")
public class FoArjStepsController extends BaseController<FoArjSteps> {

    public static final Logger log = LoggerFactory.getLogger(FoArjStepsController.class);
    @Autowired
    private FoArjStepsTestataService foArjStepsTestataService;
    @Autowired
    private FoArjStepsService foArjStepsService;
    @Autowired
    private FoArjStepsBaseService foArjStepsBaseService;
    @Autowired
    private FoArjStepsParamsService foArjStepsParamsService;
    @Autowired
    private FoArjStepsParamsBaseService foArjStepsParamsBaseService;

    @RequestMapping
    public String create(@RequestParam("codiceTestata") Integer codiceTestata, Model model) {

	FoArjStepsTestata foArjStepsTestata = foArjStepsTestataService.findById(new PkId(codiceTestata));
	FoArjSteps foArjSteps = new FoArjSteps();
	foArjSteps.setAbilitato(Boolean.TRUE);
	foArjSteps.setFoArjStepsTestata(foArjStepsTestata);
	fixRenderEntityProperty(foArjSteps);
	model.addAttribute("foArjStepsTestata", foArjStepsTestata);
	model.addAttribute("foArjSteps", foArjSteps);
	prepareCreate(model);
	setPageAttributes(model);
	return "foarjsteps/form";
    }

    private void prepareCreate(Model model) {

	List<FoArjStepsBase> arjStepsBases = foArjStepsBaseService.findAll(null, null);
	model.addAttribute("arjStepsBases", arjStepsBases);
    }

    private void prepareView(Model model, Integer codiceStep) {

	FoArjSteps step = foArjStepsService.findById(new PkId(codiceStep));
	String idFoArjStepsBase = step.getFoArjStepsBase().getNomeStep();
	List<FoArjStepsParams> listaParametri = foArjStepsParamsService.findByIdStep(step.getId().getIdcomune(), codiceStep);
	model.addAttribute("listaParametri", listaParametri);
	List<FoArjStepsParamsBase> stepParamBaseList = foArjStepsParamsBaseService.findByFoArjStepsBase(idFoArjStepsBase);
	List<FoArjStepsParamsBase> listaParametriRimasti = new ArrayList<FoArjStepsParamsBase>();
	for (FoArjStepsParamsBase stepParamBase : stepParamBaseList) {
	    listaParametriRimasti.add(stepParamBase);
	    for (FoArjStepsParams stepParam : listaParametri) {
		FoArjStepsParamsBase _stepParamBase = stepParam.getFoArjStepsParamsBase();
		if (_stepParamBase.getChiave().equalsIgnoreCase(stepParamBase.getChiave())) {
		    listaParametriRimasti.remove(stepParamBase);
		    break;
		}
	    }
	}
	model.addAttribute("listaParametriRimasti", listaParametriRimasti);
	List<FoArjStepsBase> arjStepsBases = foArjStepsBaseService.findAll(null, null);
	model.addAttribute("arjStepsBases", arjStepsBases);
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("foArjSteps") FoArjSteps foArjSteps, BindingResult result, SessionStatus status) {

	FoArjSteps objToDelete = foArjStepsService.findById(foArjSteps.getId());
	Integer codiceTestata = objToDelete.getFoArjStepsTestata().getId().getCodice();
	try {
	    foArjStepsService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(foArjSteps);
	    prepareView(model, objToDelete.getId().getCodice());
	    setPageAttributes(model);
	    return "foarjsteps/form";
	}
	status.setComplete();
	return "redirect:../foarjstepstestata/view.htm?codice=" + codiceTestata + "&status_msg=05";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("foArjSteps") FoArjSteps foArjSteps, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(foArjSteps);
	try {
	    foArjStepsService.insert(foArjSteps);
	} catch (Exception e) {
	    foArjSteps.getId().setCodice(null);
	    copyErrorsToBindingResult(result, foArjSteps, e);
	    fixRenderEntityProperty(foArjSteps);
	    prepareCreate(model);
	    setPageAttributes(model);
	    return "foarjsteps/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foArjSteps.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String insertParametro(@RequestParam("codiceStep") Integer codiceStep, @RequestParam("parametroId") Integer parametroId,
	    @RequestParam(value = "parametroValore", required = false) String parametroValore) {

	FoArjSteps foArjSteps = foArjStepsService.findById(new PkId(codiceStep));
	FoArjStepsParams entity = new FoArjStepsParams();
	entity.setFoArjSteps(foArjSteps);
	FoArjStepsParamsBase base = foArjStepsParamsBaseService.findById(parametroId);
	entity.setFoArjStepsParamsBase(base);
	if (StringUtils.isNotBlank(parametroValore)) {
	    entity.setValore(parametroValore);
	}
	String status = "01";
	try {
	    foArjStepsParamsService.insert(entity);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore in inserimento di un parametro: " + e.getMessage());
	    status = "03";
	}
	return "redirect:view.htm?codice=" + codiceStep + "&status_msg=" + status;
    }

    @RequestMapping
    public String deleteParametro(@RequestParam("codiceStep") Integer codiceStep, @RequestParam("codice") Integer codice) {

	FoArjSteps foArjSteps = foArjStepsService.findById(new PkId(codiceStep));
	FoArjStepsParams entity = foArjStepsParamsService.findById(new PkId(codice));
	String status = "05";
	if (foArjSteps.getId().getCodice().compareTo(entity.getFoArjSteps().getId().getCodice()) != 0) {
	    FlashMessages.getWarnings().add("Errore in cancellazione di un parametro: dati passati non coerenti");
	    status = "03";
	} else {
	    try {
		foArjStepsParamsService.delete(entity);
	    } catch (Exception e) {
		FlashMessages.getWarnings().add("Errore in cancellazione di un parametro: " + e.getMessage());
		status = "03";
	    }
	}
	return "redirect:view.htm?codice=" + codiceStep + "&status_msg=" + status;
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("foArjSteps") FoArjSteps foArjSteps, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(foArjSteps);
	try {
	    foArjStepsService.update(foArjSteps);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foArjSteps, e);
	    fixRenderEntityProperty(foArjSteps);
	    prepareView(model, foArjSteps.getId().getCodice());
	    setPageAttributes(model);
	    return "foarjsteps/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foArjSteps.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	FoArjSteps foArjSteps = foArjStepsService.findById(id);
	model.addAttribute("foArjSteps", foArjSteps);
	setPageAttributes(model);
	prepareView(model, codice);
	return "foarjsteps/form";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(FoArjSteps entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoArjSteps entity) {

	if (entity != null) {
	    if (entity.getFoArjStepsBase() == null) {
		entity.setFoArjStepsBase(new FoArjStepsBase());
	    }
	}
    }
}
