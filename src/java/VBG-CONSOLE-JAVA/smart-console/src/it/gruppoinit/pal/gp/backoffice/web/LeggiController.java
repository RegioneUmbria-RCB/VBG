/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.NormativeService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

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
 * @author francescop
 * 
 */
@Controller
@SessionAttributes("legge")
public class LeggiController extends BaseController<Leggi> {

    @Autowired
    private LeggiService leggiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private NormativeService normativeService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Leggi> leggiList = leggiService.findAll(null, null);
	ModelMap model = new ModelMap(leggiList);
	boolean export = createJMesaExport(request, response, leggiList);
	if (export)
	    return null;
	model.addAttribute("leggi", leggiList);
	return model;
    }

    @RequestMapping
    public String delete(@ModelAttribute("legge") Leggi leggi, BindingResult result, SessionStatus status) {

	Leggi objToDelete = leggiService.findById(leggi.getId());
	try {
	    leggiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(leggi);
	    return "leggi/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("legge") Leggi leggi, BindingResult result, SessionStatus status) {

	// recupero l'oggetto corrente e lo inserisco in letteretipo
	if ((leggi.getOggetto() != null && leggi.getOggetto().getId().getCodice() != null)) {
	    Oggetti oggetto = oggettiService.findById(leggi.getOggetto().getId());
	    leggi.setOggetto(oggetto);
	}
	if ((leggi.getNormative() != null && leggi.getNormative().getId().getCodice() != null)) {
	    Normative normative = normativeService.findById(leggi.getNormative().getId());
	    leggi.setNormative(normative);
	}
	fixMergeEntityProperty(leggi);
	try {
	    leggiService.insert(leggi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, leggi, e);
	    fixRenderEntityProperty(leggi);
	    return "leggi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + leggi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("legge") Leggi leggi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// recupero l'oggetto corrente e lo inserisco in letteretipo
	if ((leggi.getOggetto() != null && leggi.getOggetto().getId().getCodice() != null)) {
	    Oggetti oggetto = oggettiService.findById(leggi.getOggetto().getId());
	    leggi.setOggetto(oggetto);
	}
	if ((leggi.getNormative() != null && leggi.getNormative().getId().getCodice() != null)) {
	    Normative normative = normativeService.findById(leggi.getNormative().getId());
	    leggi.setNormative(normative);
	}
	fixMergeEntityProperty(leggi);
	try {
	    leggiService.update(leggi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, leggi, e);
	    fixRenderEntityProperty(leggi);
	    return "leggi/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + leggi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Leggi legge = new Leggi();
	fixRenderEntityProperty(legge);
	model.addAttribute("legge", legge);
	setPageAttributes(model);
	return "leggi/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Leggi legge = leggiService.findById(id);
	fixRenderEntityProperty(legge);
	model.addAttribute("legge", legge);
	setPageAttributes(model);
	return "leggi/form";
    }

    @Override
    protected void fixMergeEntityProperty(Leggi entity) {

	if (entity.getLeggitipi() != null && entity.getLeggitipi().getId() != null && entity.getLeggitipi().getId().getCodice() == null) {
	    entity.setLeggitipi(null);
	}
	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getNormative() != null && entity.getNormative().getId() != null && entity.getNormative().getId().getCodice() == null) {
	    entity.setNormative(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Leggi entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getLeggitipi() == null) {
	    entity.setLeggitipi(new Leggitipi());
	}
	if (entity.getNormative() == null) {
	    entity.setNormative(new Normative());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
