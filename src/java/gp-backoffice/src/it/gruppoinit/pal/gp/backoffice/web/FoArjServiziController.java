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

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoArjServiziService;
import it.gruppoinit.pal.gp.core.service.NlaServiziService;

/**
 * 
 * @author fabrizioc
 */
@Controller
@SessionAttributes("foarjservizi")
public class FoArjServiziController extends BaseController<FoArjServizi> {

    @Autowired
    private FoArjServiziService foarjserviziService;
    @Autowired
    private NlaServiziService nlaServiziService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private IVerticalizzazioneAreaRiservataService verticalizzazioneAreaRiservataService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, @RequestParam("codiceprocedimento") Integer codice, HttpServletResponse response) {

	verificaAccessoFunzionalita();
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<FoArjServizi> foarjserviziList = foarjserviziService.findByAlberoproc(codice);
	ModelMap model = new ModelMap(foarjserviziList);
	boolean export = createJMesaExport(request, response, foarjserviziList);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("foarjserviziList", foarjserviziList);
	setListPageAttributes(request);
	return model;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codiceprocedimento") Integer codice) {

	verificaAccessoFunzionalita();
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	FoArjServizi foarjservizi = new FoArjServizi();
	foarjservizi.setAlberoproc(alberoproc);
	fixRenderEntityProperty(foarjservizi);
	model.addAttribute("foarjservizi", foarjservizi);
	setPageAttributes(model);
	return "foarjservizi/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("foarjservizi") FoArjServizi foarjservizi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(foarjservizi);
	try {
	    foarjserviziService.insert(foarjservizi);
	} catch (Exception e) {
	    model.addAttribute("errore_inserimento", Boolean.TRUE);
	    copyErrorsToBindingResult(result, foarjservizi, false, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(foarjservizi);
	    return "foarjservizi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foarjservizi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	verificaAccessoFunzionalita();
	PkId id = new PkId(codice);
	FoArjServizi foarjservizi = foarjserviziService.findById(id);
	fixRenderEntityProperty(foarjservizi);
	model.addAttribute("foarjservizi", foarjservizi);
	setPageAttributes(model);
	return "foarjservizi/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("foarjservizi") FoArjServizi foarjservizi, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(foarjservizi);
	try {
	    NlaServizi nlaServizi = nlaServiziService.findById(foarjservizi.getNlaServizi().getId());
	    foarjservizi.setNlaServizi(nlaServizi);
	    foarjserviziService.update(foarjservizi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foarjservizi, false, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(foarjservizi);
	    return "foarjservizi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + foarjservizi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("foarjservizi") FoArjServizi foarjservizi, BindingResult result, SessionStatus status) {

	FoArjServizi objToDelete = foarjserviziService.findById(foarjservizi.getId());
	Integer codInt = foarjservizi.getAlberoproc().getId().getCodice();
	try {
	    foarjserviziService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, foarjservizi, false, e);
	    setPageAttributes(model);
	    fixRenderEntityProperty(foarjservizi);
	    return "foarjservizi/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceprocedimento=" + String.valueOf(codInt.intValue());
    }

    @Override
    protected void fixMergeEntityProperty(FoArjServizi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(FoArjServizi entity) {

    }

    private void verificaAccessoFunzionalita() {

	boolean centroServizi = this.verticalizzazioneAreaRiservataService.isAttiva() && this.verticalizzazioneAreaRiservataService.isCentroServizi();
	if (!centroServizi) {
	    throw new SecurityException("La funzionalità non è stata configurata correttamente. Non è attiva.");
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean centroServiziUrlBrevi = this.verticalizzazioneAreaRiservataService.isAttiva() && this.verticalizzazioneAreaRiservataService.isCentroServiziSoloUrlBrevi();
	model.addAttribute("centroServiziUrlBrevi", centroServiziUrlBrevi);
	model.addAttribute("nlaserviziList", nlaServiziService.findAll(null, null));
    }

    private void setListPageAttributes(HttpServletRequest request) {

	boolean centroServiziUrlBrevi = this.verticalizzazioneAreaRiservataService.isAttiva() && this.verticalizzazioneAreaRiservataService.isCentroServiziSoloUrlBrevi();
	request.setAttribute("centroServiziUrlBrevi", centroServiziUrlBrevi);
    }
}
