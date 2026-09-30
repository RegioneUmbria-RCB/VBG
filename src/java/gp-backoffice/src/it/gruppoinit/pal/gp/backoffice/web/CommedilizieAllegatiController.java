package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
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

import it.gruppoinit.pal.gp.core.domain.CommedilizieAllegati;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.commissioni.allegati.ICommissioniAllegatiService;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdilizieAllegatiFirmeModel;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.CommedilizieAllegatiService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes("commedilizieallegati")
public class CommedilizieAllegatiController extends BaseController<CommedilizieAllegati> {

    @Autowired
    private CommedilizieAllegatiService commedilizieallegatiService;
    @Autowired
    private CommissioniedilizieTService commissioniedilizieTService;
    @Autowired
    private ICommissioniAllegatiService icommissioniAllegatiService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceCommissione") Integer codiceCommissione, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.findById(new PkId(codiceCommissione));
	Set<CommedilizieAllegati> commedilizieallegatiList = commissioniedilizieT.getCommedilizieAllegatis();
	ModelMap model = new ModelMap(commedilizieallegatiList);
	boolean export = createJMesaExport(request, response, commedilizieallegatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("commissioniedilizieT", commissioniedilizieT);
	model.addAttribute("commedilizieallegatiList", commedilizieallegatiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(@RequestParam("codiceCommissione") Integer codiceCommissione, Model model) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.findById(new PkId(codiceCommissione));
	CommedilizieAllegati commedilizieallegati = new CommedilizieAllegati();
	commedilizieallegati.setCommissioniedilizieT(commissioniedilizieT);
	fixRenderEntityProperty(commedilizieallegati);
	model.addAttribute("commedilizieallegati", commedilizieallegati);
	setPageAttributes(model);
	return "commedilizieallegati/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("commedilizieallegati") CommedilizieAllegati commedilizieallegati, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(commedilizieallegati.getOggetti(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(commedilizieallegati.getOggetti().getId());
	    commedilizieallegati.setOggetti(oggetti);
	}
	try {
	    commedilizieallegatiService.insert(commedilizieallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieallegati, e);
	    fixRenderEntityProperty(commedilizieallegati);
	    return "commedilizieallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizieallegati.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	CommedilizieAllegati commedilizieallegati = commedilizieallegatiService.findById(id);
	fixRenderEntityProperty(commedilizieallegati);
	model.addAttribute("commedilizieallegati", commedilizieallegati);
	setPageAttributes(model);
	return "commedilizieallegati/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("commedilizieallegati") CommedilizieAllegati commedilizieallegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (EntityUtils.getNestedProperty(commedilizieallegati.getOggetti(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(commedilizieallegati.getOggetti().getId());
	    commedilizieallegati.setOggetti(oggetti);
	}
	try {
	    commedilizieallegatiService.update(commedilizieallegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, commedilizieallegati, e);
	    fixRenderEntityProperty(commedilizieallegati);
	    return "commedilizieallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + commedilizieallegati.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(@ModelAttribute("commedilizieallegati") CommedilizieAllegati commedilizieallegati, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	CommedilizieAllegati objToDelete = commedilizieallegatiService.findById(commedilizieallegati.getId());
	try {
	    commedilizieallegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(commedilizieallegati);
	    return "commedilizieallegati/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceCommissione=" + objToDelete.getCommissioniedilizieT().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxPubblica(@RequestParam("codice") String codice, @RequestParam("abilita") Boolean abilita, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	CommedilizieAllegati commedilizieAllegati = commedilizieallegatiService.findById(new PkId(Integer.parseInt(codice)));
	response.setContentType("text/plain");
	try {
	    commedilizieAllegati.setFlagPubblica(abilita == null ? false : abilita.booleanValue());
	    commedilizieallegatiService.update(commedilizieAllegati);
	    response.getWriter().write("Dato aggiornato");
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write("Errore aggiornamento: " + e.getMessage() + "");
	}
    }

    @RequestMapping
    public void ajaxCountFirme(@RequestParam("id_allegato") Integer id_allegato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	response.setContentType("application/json");
	try {
	    long countFirmePerAllegato = icommissioniAllegatiService.countFirmePerAllegato(id_allegato);
	    response.getWriter().write("{\"count\": " + countFirmePerAllegato + "}");
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write("Errore nel recupero del conteggio degli allegati: " + e.getMessage() + "");
	}
    }

    @RequestMapping
    public String ajaxVisualizzaFirme(@RequestParam("id_allegato") Integer id_allegato, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	response.setContentType("text/html");
	try {
	    List<CommissioniEdilizieAllegatiFirmeModel> firmePerAllegato = icommissioniAllegatiService.findFirmePerAllegato(id_allegato);
	    model.addAttribute("firmePerAllegato", firmePerAllegato);
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write("Errore nel recupero del conteggio degli allegati: " + e.getMessage() + "");
	}
	return "commedilizieallegati/ajaxVisualizzaFirme";
    }

    @Override
    protected void fixMergeEntityProperty(CommedilizieAllegati entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CommedilizieAllegati entity) {

	if (entity.getCommissioniedilizieT() == null) {
	    entity.setCommissioniedilizieT(new CommissioniedilizieT());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
