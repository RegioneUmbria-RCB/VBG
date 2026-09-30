package it.gruppoinit.pal.gp.backoffice.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniAllegatiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;

@Controller
@SessionAttributes(value = { "alboPubblicazioniAllegati" })
public class AlboPubblicazioniAllegatiController extends BaseController<AlboPubblicazioniAllegati> {

    @Autowired
    private AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private AlboPubblicazioniService alboPubblicazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<AlboPubblicazioniAllegati> alboPubblicazioniAllegatiList = alboPubblicazioniAllegatiService.findAll(null, null);
	ModelMap model = new ModelMap(alboPubblicazioniAllegatiList);
	boolean export = createJMesaExport(request, response, alboPubblicazioniAllegatiList);
	if (export)
	    return null;
	model.addAttribute("alboPubblicazioniList", alboPubblicazioniAllegatiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("alboPubblicazioniAllegati") AlboPubblicazioniAllegati alboPubblicazioniAllegati,
	    BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	AlboPubblicazioniAllegati objToDelete = alboPubblicazioniAllegatiService.findById(alboPubblicazioniAllegati.getId());
	Integer codice = objToDelete.getId().getCodice();
	try {
	    alboPubblicazioniAllegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "alboPubblicazioniAllegati");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@ModelAttribute("alboPubblicazioniAllegati") AlboPubblicazioniAllegati alboPubblicazioniAllegati, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	// recupero l'oggetto corrente e lo inserisco in albopoubblicazioniallegati
	Oggetti oggetto = oggettiService.findById(alboPubblicazioniAllegati.getOggetti().getId());
	alboPubblicazioniAllegati.setOggetti(oggetto);
	// recupero il bando corrente e lo inserisco in albopoubblicazioniallegati
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(alboPubblicazioniAllegati.getAlboPubblicazioni().getId());
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	fixMergeEntityProperty(alboPubblicazioniAllegati);
	// data creazione inserita da sistema
	try {
	    alboPubblicazioniAllegatiService.insert(alboPubblicazioniAllegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alboPubblicazioniAllegati, e);
	    fixRenderEntityProperty(alboPubblicazioniAllegati);
	    return "albopubblicazioniallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alboPubblicazioniAllegati.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@ModelAttribute("alboPubblicazioniAllegati") AlboPubblicazioniAllegati alboPubblicazioniAllegati, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	// recupero l'oggetto corrente e lo inserisco in albopoubblicazioniallegati
	Oggetti oggetto = oggettiService.findById(alboPubblicazioniAllegati.getOggetti().getId());
	alboPubblicazioniAllegati.setOggetti(oggetto);
	// recupero il bando corrente e lo inserisco in albopoubblicazioniallegati
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(alboPubblicazioniAllegati.getAlboPubblicazioni().getId());
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	fixMergeEntityProperty(alboPubblicazioniAllegati);
	try {
	    alboPubblicazioniAllegatiService.update(alboPubblicazioniAllegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alboPubblicazioniAllegati, e);
	    fixRenderEntityProperty(alboPubblicazioniAllegati);
	    return "albopubblicazioniallegati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alboPubblicazioniAllegati.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(@RequestParam("albopubblicazioni.codice.id") Integer codice, Model model) {

	// §§§BEGIN§§§
	AlboPubblicazioniAllegati alboPubblicazioniAllegati = new AlboPubblicazioniAllegati();
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(new PkId(codice));
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	fixRenderEntityProperty(alboPubblicazioniAllegati);
	model.addAttribute("alboPubblicazioniAllegati", alboPubblicazioniAllegati);
	setPageAttributes(model);
	return "albopubblicazioniallegati/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	AlboPubblicazioniAllegati alboPubblicazioniAllegati = alboPubblicazioniAllegatiService.findById(id);
	fixRenderEntityProperty(alboPubblicazioniAllegati);
	model.addAttribute("alboPubblicazioniAllegati", alboPubblicazioniAllegati);
	setPageAttributes(model);
	return "albopubblicazioniallegati/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(AlboPubblicazioniAllegati entity) {

	if (entity.getAlboPubblicazioni() != null && entity.getAlboPubblicazioni().getId() != null
		&& entity.getAlboPubblicazioni().getId().getCodice() == null) {
	    entity.setAlboPubblicazioni(null);
	}
	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlboPubblicazioniAllegati entity) {

	if (entity.getAlboPubblicazioni() == null) {
	    entity.setAlboPubblicazioni(new AlboPubblicazioni());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
