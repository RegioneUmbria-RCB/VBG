package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.NlaServizi;
import it.gruppoinit.pal.gp.core.domain.NlaServiziAltriDati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.NlaServiziAltriDatiService;
import it.gruppoinit.pal.gp.core.service.NlaServiziService;

import java.io.IOException;
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

/**
 * 
 * @author fabrizioc
 */
@Controller
@SessionAttributes("nlaservizi")
public class NlaServiziController extends BaseController<NlaServizi> {

    @Autowired
    private NlaServiziService nlaserviziService;
    @Autowired
    private NlaServiziAltriDatiService nlaServiziAltriDatiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<NlaServizi> nlaserviziList = nlaserviziService.findAll(null, null);
	ModelMap model = new ModelMap(nlaserviziList);
	boolean export = createJMesaExport(request, response, nlaserviziList);
	if (export) {
	    return null;
	}
	model.addAttribute("nlaserviziList", nlaserviziList);
	return model;
    }

    @RequestMapping
    public String create(Model model) {

	NlaServizi nlaservizi = new NlaServizi();
	fixRenderEntityProperty(nlaservizi);
	model.addAttribute("nlaservizi", nlaservizi);
	setPageAttributes(model);
	return "nlaservizi/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("nlaservizi") NlaServizi nlaservizi, BindingResult result, SessionStatus status) {

	fixMergeEntityProperty(nlaservizi);
	try {
	    nlaserviziService.insert(nlaservizi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, nlaservizi, false, e);
	    fixRenderEntityProperty(nlaservizi);
	    return "nlaservizi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + nlaservizi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	NlaServizi nlaservizi = nlaserviziService.findById(id);
	fixRenderEntityProperty(nlaservizi);
	model.addAttribute("nlaservizi", nlaservizi);
	setPageAttributes(model);
	return "nlaservizi/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("nlaservizi") NlaServizi nlaservizi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(nlaservizi);
	try {
	    nlaserviziService.update(nlaservizi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, nlaservizi, false, e);
	    fixRenderEntityProperty(nlaservizi);
	    return "nlaservizi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + nlaservizi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("nlaservizi") NlaServizi nlaservizi, BindingResult result, SessionStatus status) {

	NlaServizi objToDelete = nlaserviziService.findById(nlaservizi.getId());
	try {
	    nlaserviziService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, false, e);
	    fixRenderEntityProperty(nlaservizi);
	    return "nlaservizi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxViewAltriDati(@RequestParam("codiceservizio") Integer codiceservizio, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	NlaServizi servizio = nlaserviziService.findById(new PkId(codiceservizio));
	List<NlaServiziAltriDati> ads = nlaServiziAltriDatiService.findByNlaServizi(codiceservizio);
	model.addAttribute("servizio", servizio);
	model.addAttribute("altriDatis", ads);
	return "nlaservizi/ajaxViewAltridati";
    }

    @RequestMapping
    public void ajaxDeleteAltriDati(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	NlaServiziAltriDati ad = nlaServiziAltriDatiService.findById(new PkId(codice));
	nlaServiziAltriDatiService.delete(ad);
    }

    @RequestMapping
    public void ajaxUpdateAltriDati(@RequestParam("codice") Integer codice, @RequestParam("nomeparametro") String nomeparametro,
	    @RequestParam("valore") String valore, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	NlaServiziAltriDati ad = nlaServiziAltriDatiService.findById(new PkId(codice));
	ad.setNomeparametro(nomeparametro);
	ad.setValore(valore);
	nlaServiziAltriDatiService.update(ad);
    }

    @RequestMapping
    public String ajaxCreateAltroDato(@RequestParam("codiceservizio") Integer codiceservizio, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	NlaServizi servizio = nlaserviziService.findById(new PkId(codiceservizio));
	List<NlaServiziAltriDati> ads = nlaServiziAltriDatiService.findByNlaServizi(codiceservizio);
	model.addAttribute("servizio", servizio);
	model.addAttribute("altriDatis", ads);
	return "nlaservizi/ajaxCreateAltrodato";
    }
    
    @RequestMapping
    public void ajaxInsertAltriDati(@RequestParam("codiceservizio") Integer codiceservizio, @RequestParam("nomeparametro") String nomeparametro,
	    @RequestParam("valore") String valore, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	NlaServizi servizio = nlaserviziService.findById(new PkId(codiceservizio));
	NlaServiziAltriDati ad = new NlaServiziAltriDati();
	ad.setNlaServizi(servizio);
	ad.setNomeparametro(nomeparametro);
	ad.setValore(valore);
	nlaServiziAltriDatiService.insert(ad);
    }

    @Override
    protected void fixMergeEntityProperty(NlaServizi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(NlaServizi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
