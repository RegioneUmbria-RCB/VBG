package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;
import it.gruppoinit.pal.gp.core.domain.Dyn2MetadatiContesti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.Dyn2MetadatiContestiService;
import it.gruppoinit.pal.gp.core.features.datidinamici.metadati.Dyn2MetadatiService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 *
 */
@Controller
@SessionAttributes("dyn2metadaticontesti")
public class Dyn2MetadatiContestiController extends BaseController<Dyn2MetadatiContesti> {

    @Autowired
    private Dyn2MetadatiContestiService dyn2MetadatiContestiService;
    @Autowired
    private Dyn2MetadatiService dyn2MetadatiService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Dyn2MetadatiContesti> dyn2metadatiContestiList = dyn2MetadatiContestiService.findAll(null, null);
	ModelMap model = new ModelMap(dyn2metadatiContestiList);
	boolean export = createJMesaExport(request, response, dyn2metadatiContestiList);
	if (export) {
	    return null;
	}
	model.addAttribute("dyn2metadatiContestiList", dyn2metadatiContestiList);
	return model;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Dyn2MetadatiContesti dyn2metadaticontesti = dyn2MetadatiContestiService.findById(id);
	fixRenderEntityProperty(dyn2metadaticontesti);
	model.addAttribute("dyn2metadaticontesti", dyn2metadaticontesti);
	return "dyn2metadaticontesti/form";
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	Dyn2MetadatiContesti dyn2metadaticontesti = new Dyn2MetadatiContesti();
	fixRenderEntityProperty(dyn2metadaticontesti);
	model.addAttribute("dyn2metadaticontesti", dyn2metadaticontesti);
	return "dyn2metadaticontesti/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("dyn2metadaticontesti") Dyn2MetadatiContesti dyn2metadaticontesti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(dyn2metadaticontesti);
	try {
	    if (validateInsert(dyn2metadaticontesti)) {
		dyn2MetadatiContestiService.insert(dyn2metadaticontesti);
	    } else {
		FlashMessages.getWarnings().add("Impossibile inserire poiché è già presente un metadato con lo stesso contesto.");
		return "redirect:create.htm";
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2metadaticontesti, e);
	    fixRenderEntityProperty(dyn2metadaticontesti);
	    return "dyn2metadaticontesti/form";
	}
	return "redirect:view.htm?codice=" + dyn2metadaticontesti.getId().getCodice() + "&status_msg=01";
    }

    private boolean validateInsert(Dyn2MetadatiContesti dyn2metadaticontesti) {

	List<Dyn2MetadatiContesti> list = dyn2MetadatiContestiService.findAll(null, null);
	for (Dyn2MetadatiContesti contesti : list) {
	    if (dyn2metadaticontesti.getContesto().equals(contesti.getContesto())) {
		return false;
	    }
	}
	return true;
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("dyn2metadaticontesti") Dyn2MetadatiContesti dyn2metadaticontesti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(dyn2metadaticontesti);
	try {
	    dyn2MetadatiContestiService.update(dyn2metadaticontesti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2metadaticontesti, e);
	    fixRenderEntityProperty(dyn2metadaticontesti);
	    return "dyn2metadaticontesti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + dyn2metadaticontesti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("dyn2metadaticontesti") Dyn2MetadatiContesti dyn2metadaticontesti, BindingResult result,
	    SessionStatus status) {

	Dyn2MetadatiContesti objToDelete = dyn2MetadatiContestiService.findById(dyn2metadaticontesti.getId());
	try {
	    if (validateDelete(objToDelete)) {
		dyn2MetadatiContestiService.delete(objToDelete);
	    } else {
		FlashMessages.getWarnings().add("Impossibile eliminare in quanto sono presenti dei metadati collegati.");
		return "redirect:view.htm?codice=" + objToDelete.getId().getCodice();
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, dyn2metadaticontesti, e);
	    fixRenderEntityProperty(dyn2metadaticontesti);
	    return "dyn2metadaticontesti/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    private boolean validateDelete(Dyn2MetadatiContesti entity) {

	// controllo se sono popolate le chiavi esterne 
	List<Dyn2Metadati> list = dyn2MetadatiService.findByIdDyn2MetadatoContesto(entity.getId().getCodice());
	for (Dyn2Metadati dyn2Metadati : list) {
	    if (dyn2Metadati.getDyn2MetadatiContesti().getId().getCodice() != null) {
		return false;
	    }
	}
	return true;
    }

    @RequestMapping
    public String ajaxDettaglioMetadati(@RequestParam("codiceMetadatoContesto") Integer codiceMetadatoContesto, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	List<Dyn2Metadati> dyn2MetadatiList = dyn2MetadatiService.findByIdDyn2MetadatoContesto(codiceMetadatoContesto);
	model.addAttribute("dyn2MetadatiList", dyn2MetadatiList);
	return "dyn2metadaticontesti/ajaxDettaglioMetadati";
    }

    @RequestMapping
    public void ajaxAssegnaMetadati(@RequestParam("codiceMetadatoContesti") Integer codiceMetadatoContesti,
	    @RequestParam("codiceCampo") Integer codiceCampo, @RequestParam("contestoCampo") String contestoCampo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "ok";
	try {
	    //boolean trovato = false;
	    // controllo se è già presente un metadato con lo stesso CONTESTO_CAMPO, FK_CONTESTO_ID e ID
	    //List<Dyn2Metadati> dyn2MetadatiList = dyn2MetadatiService.findByIdDyn2MetadatoContesto(codiceMetadatoContesti);
	    //for (Dyn2Metadati dyn2Metadati : dyn2MetadatiList) {
	    //	if (!dyn2Metadati.getContestoCampo().isEmpty() && dyn2Metadati.getContestoCampo().equalsIgnoreCase(contestoCampo)) {
	    //	    trovato = true;
	    //	    break;
	    //	}
	    //}
	    if (codiceCampo == null && StringUtils.isBlank(contestoCampo)) {
		result = "I campi non possono essere vuoti.";
	    }
	    //if (trovato) {
	    //	result = "Attenzione è già stato inserito un metadato con lo stesso campo contesto.";
	    //} else {
	    Dyn2Metadati dyn2Metadati = new Dyn2Metadati();
	    Dyn2MetadatiContesti dyn2metadatoContesti = dyn2MetadatiContestiService.findById(new PkId(codiceMetadatoContesti));
	    Dyn2Campi dyn2campi = dyn2CampiService.findById(new PkId(codiceCampo));
	    dyn2Metadati.setDyn2Campi(dyn2campi);
	    dyn2Metadati.setDyn2MetadatiContesti(dyn2metadatoContesti);
	    dyn2Metadati.setContestoCampo(contestoCampo);
	    dyn2MetadatiService.insert(dyn2Metadati);
	    //}
	} catch (Exception e) {
	    result = "Si è verificato un errore nell'inserimento del metadato (dettaglio: " + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaMetadato(@RequestParam("codiceMetadato") Integer codiceMetadato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String result = "Ok";
	try {
	    Dyn2Metadati dyn2Metadati = dyn2MetadatiService.findById(new PkId(codiceMetadato));
	    if (dyn2Metadati != null) {
		dyn2MetadatiService.delete(dyn2Metadati);
	    }
	} catch (Exception e) {
	    result = "Si è verificato un errore durante la cancellazione del dato!. (" + e.getMessage() + ").";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Dyn2MetadatiContesti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Dyn2MetadatiContesti entity) {

    }
}
