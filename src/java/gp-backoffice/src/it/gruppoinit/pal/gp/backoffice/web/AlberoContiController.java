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

import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.AlberoConti;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.service.AlberoCausaliService;
import it.gruppoinit.pal.gp.core.service.AlberoContiService;

@Controller
@SessionAttributes("alberoConti")
public class AlberoContiController extends BaseController<AlberoConti> {

    @Autowired
    private AlberoContiService alberoContiService;
    @Autowired
    private AlberoCausaliService alberoCausaliService;
    @Autowired
    private ContiService contiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<AlberoConti> alberoContiList = alberoContiService.findAll(null, null);
	ModelMap model = new ModelMap(alberoContiList);
	boolean export = createJMesaExport(request, response, alberoContiList);
	if (export)
	    return null;
	model.addAttribute("alberoContiList", alberoContiList);
	return model;
    }

    @RequestMapping
    public String delete(@RequestParam("codice") Integer codiceAlberocausali, @ModelAttribute("alberoConti") AlberoConti alberoConti,
	    BindingResult result, SessionStatus status) {

	AlberoConti objToDelete = alberoContiService.findById(alberoConti.getId());
	try {
	    alberoContiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(alberoConti);
	    return "alberoconti/form";
	}
	status.setComplete();
	return "redirect:../alberocausali/view.htm?codice=" + codiceAlberocausali;
    }

    @RequestMapping
    public String insert(@ModelAttribute("alberoConti") AlberoConti alberoConti, BindingResult result, SessionStatus status) {

	// estraggo l'oggetto conti usando l'id passatop tramire la ricerca ajax
	// nel form
	Conti conti = contiService.findById(alberoConti.getConti().getId());
	// inserisco l'oggeto conti scelto nell'oggetto alberoConti da
	// salvare
	alberoConti.setConti(conti);
	fixMergeEntityProperty(alberoConti);
	try {
	    alberoContiService.insert(alberoConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoConti, e);
	    fixRenderEntityProperty(alberoConti);
	    return "alberoconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoConti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("alberoConti") AlberoConti alberoConti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// se necessario inserire parte di codice che deve ricercare altri
	// attribbuti da settare
	// all'oggetto del dominio
	fixMergeEntityProperty(alberoConti);
	try {
	    alberoContiService.update(alberoConti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoConti, e);
	    fixRenderEntityProperty(alberoConti);
	    return "alberoconti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoConti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(@RequestParam("alberoCausali.id.codice") Integer codiceAlberoCausali, Model model) {

	AlberoConti alberoConti = new AlberoConti();
	AlberoCausali alberoCausali = alberoCausaliService.findById(new PkId(codiceAlberoCausali));
	alberoConti.setAlberoCausali(alberoCausali);
	fixRenderEntityProperty(alberoConti);
	model.addAttribute("alberoConti", alberoConti);
	setPageAttributes(model);
	return "alberoconti/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoConti alberoConti = alberoContiService.findById(id);
	fixRenderEntityProperty(alberoConti);
	model.addAttribute("alberoConti", alberoConti);
	setPageAttributes(model);
	return "alberoconti/form";
    }

    @Override
    protected void fixMergeEntityProperty(AlberoConti entity) {

	if (entity.getConti() != null && entity.getConti().getId() != null && entity.getConti().getId().getCodice() == null) {
	    entity.setConti(null);
	}
	if (entity.getAlberoCausali() != null && entity.getAlberoCausali().getId() != null && entity.getAlberoCausali().getId().getCodice() == null) {
	    entity.setAlberoCausali(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlberoConti entity) {

	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
	if (entity.getAlberoCausali() == null) {
	    entity.setAlberoCausali(new AlberoCausali());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
