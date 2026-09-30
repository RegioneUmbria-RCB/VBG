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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologie;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedett;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipologiedettId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologieService;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipologiedettService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes({ "commedilizietipologie", "commedilizietipologiedett" })
public class CommedilizieTipologieController extends BaseController<CommedilizieTipologie> {

    @Autowired
    private CommedilizieTipologieService commedilizietipologieService;
    @Autowired
    private CommedilizieTipologiedettService commedilizietipologiedettService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<CommedilizieTipologie> commedilizietipologieList = commedilizietipologieService.findAll(null, null);
	ModelMap model = new ModelMap(commedilizietipologieList);
	boolean export = createJMesaExport(request, response, commedilizietipologieList);
	if (export) {
	    return null;
	}
	model.addAttribute("commedilizietipologieList", commedilizietipologieList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	CommedilizieTipologie commedilizietipologie = new CommedilizieTipologie();
	fixRenderEntityProperty(commedilizietipologie);
	model.addAttribute("commedilizietipologie", commedilizietipologie);
	setPageAttributes(model);
	return "commedilizietipologie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("commedilizietipologie") CommedilizieTipologie commedilizietipologie, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(commedilizietipologie);
	try {
	    commedilizietipologieService.insert(commedilizietipologie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizietipologie, e);
	    fixRenderEntityProperty(commedilizietipologie);
	    return "commedilizietipologie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizietipologie.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieTipologie commedilizietipologie = commedilizietipologieService.findById(id);
	fixRenderEntityProperty(commedilizietipologie);
	//	List<Amministrazioni> amministrazioniList = amministrazioniService.findAll(null, null);
	model.addAttribute("commedilizietipologie", commedilizietipologie);
	//	model.addAttribute("amministrazioniList", amministrazioniList);
	setPageAttributes(model);
	return "commedilizietipologie/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("commedilizietipologie") CommedilizieTipologie commedilizietipologie, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(commedilizietipologie, "amministrazione") != null) {
	    Amministrazioni amministrazione = amministrazioniService.findById(commedilizietipologie.getAmministrazione().getId());
	    commedilizietipologie.setAmministrazione(amministrazione);
	}
	try {
	    commedilizietipologieService.update(commedilizietipologie);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizietipologie, e);
	    fixRenderEntityProperty(commedilizietipologie);
	    return "commedilizietipologie/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizietipologie.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("commedilizietipologie") CommedilizieTipologie commedilizietipologie, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieTipologie objToDelete = commedilizietipologieService.findById(commedilizietipologie.getId());
	try {
	    commedilizietipologieService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commedilizietipologie);
	    return "commedilizietipologie/form";
	}
	status.setComplete();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String createDettaglio(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieTipologie commedilizietipologie = commedilizietipologieService.findById(id);
	CommedilizieTipologiedett dettaglio = new CommedilizieTipologiedett();
	dettaglio.setCommedilizieTipologie(commedilizietipologie);
	dettaglio.getId().setCodcommtipologia(commedilizietipologie.getId().getCodice());
	dettaglio.setTipimovimento(new Tipimovimento());
	model.addAttribute("commedilizietipologiedett", dettaglio);
	List<Software> softwareattivi = softwareService.findAttiviAndExcludeTT(false);
	model.addAttribute("softareAttiviDaConfiguare", softwareattivi);
	setPageAttributes(model);
	return "commedilizietipologie/formDettaglio";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insertDettaglio(Model model, @ModelAttribute("commedilizietipologiedett") CommedilizieTipologiedett commedilizietipologiedett,
	    BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	try {
	    CommedilizieTipologiedettId id = commedilizietipologiedett.getId();
	    id.setTipomovimento(commedilizietipologiedett.getTipimovimento().getId().getTipomovimento());
	    commedilizietipologiedettService.insert(commedilizietipologiedett);
	} catch (Exception e) {
	    List<Software> softwareattivi = softwareService.findAttiviAndExcludeTT(false);
	    model.addAttribute("softareAttiviDaConfiguare", softwareattivi);
	    copyErrorsToBindingResult(result, commedilizietipologiedett, e);
	    return "commedilizietipologie/formDettaglio";
	}
	return "redirect:view.htm?codice=" + commedilizietipologiedett.getCommedilizieTipologie().getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String deleteDettaglio(Model model, @RequestParam("codice") Integer codice, @RequestParam("tipomovimento") String tipomovimento,
	    @ModelAttribute("commedilizietipologie") CommedilizieTipologie commedilizietipologie, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieTipologiedettId id = new CommedilizieTipologiedettId();
	id.setCodcommtipologia(codice);
	id.setTipomovimento(tipomovimento);
	CommedilizieTipologiedett objToDelete = commedilizietipologiedettService.findById(id);
	try {
	    commedilizietipologiedettService.delete(objToDelete);
	} catch (Exception e) {
	    List<Software> softwareattivi = softwareService.findAttiviAndExcludeTT(false);
	    model.addAttribute("softareAttiviDaConfiguare", softwareattivi);
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "commedilizietipologie/formDettaglio";
	}
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxAssegnaRuoli(@RequestParam("idtipologia") Integer idtipologia, @RequestParam("codiceRuolo") Integer codiceRuolo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	if (!commedilizietipologieService.aggiungiRuolo(idtipologia, codiceRuolo)) {
	    result = "Attenzione! Il ruolo  è già stato assegnato.";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaRuoli(@RequestParam("idtipologia") Integer idtipologia, @RequestParam("codiceRuolo") Integer codiceRuolo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "Ok";
	if (!commedilizietipologieService.eliminaRuolo(idtipologia, codiceRuolo)) {
	    result = "Attenzione! Il ruolo non è presente.";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieTipologie entity) {

	if (entity.getAmministrazione() != null && entity.getAmministrazione().getId() != null
		&& entity.getAmministrazione().getId().getCodice() == null) {
	    entity.setAmministrazione(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieTipologie entity) {

	if (entity.getAmministrazione() == null) {
	    entity.setAmministrazione(new Amministrazioni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Amministrazioni> amministrazioniList = amministrazioniService.findAll(null, null);
	model.addAttribute("amministrazioniList", amministrazioniList);
    }
}
