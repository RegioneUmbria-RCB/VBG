package it.gruppoinit.pal.gp.backoffice.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlboCategorie;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlboCategorieService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniAllegatiService;
import it.gruppoinit.pal.gp.core.service.AlboPubblicazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;

@Controller
@SessionAttributes(value = { "alboPubblicazioni", "alboPubblicazioniAllegati" })
public class AlboPubblicazioniController extends BaseController<AlboPubblicazioni> {

    @Autowired
    private AlboPubblicazioniService alboPubblicazioniService;
    @Autowired
    private AlboCategorieService alboCategorieService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AmministrazionireferentiService amministrazionireferentiService;
    @Autowired
    private AlboPubblicazioniAllegatiService alboPubblicazioniAllegatiService;
    @Autowired
    private OggettiService oggettiService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<AlboPubblicazioni> alboPubblicazioniList = alboPubblicazioniService.findAll(null, null);
	ModelMap model = new ModelMap(alboPubblicazioniList);
	boolean export = createJMesaExport(request, response, alboPubblicazioniList);
	if (export)
	    return null;
	model.addAttribute("alboPubblicazioniList", alboPubblicazioniList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String listFromCategoria(@RequestParam("codicecategoria") Integer codicecategoria, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId id = new PkId(codicecategoria);
	AlboCategorie alboCategorie = alboCategorieService.findById(id);
	Set<AlboPubblicazioni> alboPubblicazioniList = alboCategorie.getAlboPubblicazionis();
	ModelMap model = new ModelMap(alboPubblicazioniList);
	boolean export = createJMesaExport(request, response, alboPubblicazioniList);
	if (export)
	    return null;
	model.addAttribute("alboPubblicazioniList", alboPubblicazioniList);
	return "redirect:albocategorie/view.htm?codice=" + codicecategoria;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("alboPubblicazioni") AlboPubblicazioni alboPubblicazioni, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	AlboPubblicazioni objToDelete = alboPubblicazioniService.findById(alboPubblicazioni.getId());
	try {
	    alboPubblicazioniService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "albopubblicazioni/form";
	}
	status.setComplete();
	return "redirect:list.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("alboPubblicazioni") AlboPubblicazioni alboPubblicazioni, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(alboPubblicazioni);
	// data creazione inserita da sistema
	Calendar calendar = Calendar.getInstance();
	Date dataCreazione = calendar.getTime();
	alboPubblicazioni.setDataCreazione(dataCreazione);
	// Responsabile inserito come il responsabile loggato
	Responsabili responsabili = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	alboPubblicazioni.setResponsabili(responsabili);
	// Recupere dall'id l'oggetto albo categorie
	if (alboPubblicazioni.getAlboCategorie() != null && alboPubblicazioni.getAlboCategorie().getId().getCodice() != null) {
	    AlboCategorie alboCategorie = alboCategorieService.findById(new PkId(alboPubblicazioni.getAlboCategorie().getId().getCodice()));
	    alboPubblicazioni.setAlboCategorie(alboCategorie);
	}
	// Recupero dall'id l'oggetto Amministrazioni
	if (alboPubblicazioni.getAmministrazioni() != null && alboPubblicazioni.getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(alboPubblicazioni.getAmministrazioni().getId().getCodice()));
	    alboPubblicazioni.setAmministrazioni(amministrazioni);
	}
	// Recupero dall'id oggetto amministrazioni referenti,se esiste o se è stato scelto
	if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
	    Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService
		    .findById(new PkId(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice()));
	    alboPubblicazioni.setAmministrazionireferenti(amministrazionireferenti);
	}
	// setto il software
	Software software = new Software();
	software.setCodice(ORMHelper.getSoftware());
	alboPubblicazioni.setSoftware(software);
	try {
	    fixMergeEntityProperty(alboPubblicazioni);
	    alboPubblicazioniService.insert(alboPubblicazioni);
	} catch (Exception e) {
	    List<AlboCategorie> listaCategorie = alboCategorieService.findAll(null, null);
	    model.addAttribute("listacategorie", listaCategorie);
	    copyErrorsToBindingResult(result, alboPubblicazioni, e);
	    fixRenderEntityProperty(alboPubblicazioni);
	    return "albopubblicazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alboPubblicazioni.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("alboPubblicazioni") AlboPubblicazioni alboPubblicazioni, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(alboPubblicazioni);
	// Responsabile inserito come il responsabile loggato
	Responsabili responsabili = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	alboPubblicazioni.setResponsabili(responsabili);
	AlboCategorie alboCategorie = alboCategorieService.findById(new PkId(alboPubblicazioni.getAlboCategorie().getId().getCodice()));
	alboPubblicazioni.setAlboCategorie(alboCategorie);
	// Recupero dall'id l'oggetto Amministrazioni
	if (alboPubblicazioni.getAmministrazioni() != null && alboPubblicazioni.getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(alboPubblicazioni.getAmministrazioni().getId().getCodice()));
	    alboPubblicazioni.setAmministrazioni(amministrazioni);
	}
	// Recupero dall'id oggetto amministrazioni referenti,se esiste o se è stato scelto
	if (alboPubblicazioni.getAmministrazionireferenti() != null && alboPubblicazioni.getAmministrazionireferenti().getId().getCodice() != null) {
	    Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService
		    .findById(new PkId(alboPubblicazioni.getAmministrazionireferenti().getId().getCodice()));
	    alboPubblicazioni.setAmministrazionireferenti(amministrazionireferenti);
	}
	try {
	    alboPubblicazioniService.update(alboPubblicazioni);
	} catch (Exception e) {
	    List<AlboCategorie> listaCategorie = alboCategorieService.findAll(null, null);
	    model.addAttribute("listacategorie", listaCategorie);
	    copyErrorsToBindingResult(result, alboPubblicazioni, e);
	    fixRenderEntityProperty(alboPubblicazioni);
	    return "albopubblicazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alboPubblicazioni.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	AlboPubblicazioni alboPubblicazioni = new AlboPubblicazioni();
	fixRenderEntityProperty(alboPubblicazioni);
	// carico la lista delle categorie,amministrazione,ufficio
	List<AlboCategorie> listaCategorie = alboCategorieService.findAll(null, null);
	List<Amministrazioni> listAmministrazioni = amministrazioniService.findAll(null, null);
	Set<AlboPubblicazioniAllegati> listPubblicazioniallegate = alboPubblicazioni.getAlboPubblicazioniAllegatis();
	// model di liste successive
	model.addAttribute("listacategorie", listaCategorie);
	model.addAttribute("listpubblicazioniallegate", listPubblicazioniallegate);
	model.addAttribute("listaamministrazioni", listAmministrazioni);
	model.addAttribute("alboPubblicazioni", alboPubblicazioni);
	setPageAttributes(model);
	return "albopubblicazioni/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String createFromCategoria(@RequestParam("codicecategoria") Integer codicecategoria, Model model) {

	// §§§BEGIN§§§
	AlboPubblicazioni alboPubblicazioni = new AlboPubblicazioni();
	fixRenderEntityProperty(alboPubblicazioni);
	// Set AlboCategoria
	PkId id = new PkId(codicecategoria);
	AlboCategorie albocategoria = alboCategorieService.findById(id);
	alboPubblicazioni.setAlboCategorie(albocategoria);
	// carico la lista delle amministrazioni,uffici
	List<Amministrazioni> listAmministrazioni = amministrazioniService.findAll(null, null);
	Set<AlboPubblicazioniAllegati> listPubblicazioniallegate = alboPubblicazioni.getAlboPubblicazioniAllegatis();
	// model di liste successive
	model.addAttribute("listpubblicazioniallegate", listPubblicazioniallegate);
	model.addAttribute("listaamministrazioni", listAmministrazioni);
	model.addAttribute("alboPubblicazioni", alboPubblicazioni);
	setPageAttributes(model);
	return "albopubblicazioni/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(id);
	fixRenderEntityProperty(alboPubblicazioni);
	// liste da ripresentare
	List<AlboCategorie> listaCategorie = alboCategorieService.findAll(null, null);
	List<AlboPubblicazioniAllegati> listPubblicazioniallegate = alboPubblicazioniAllegatiService.findOrderByOrdine(alboPubblicazioni);
	model.addAttribute("alboPubblicazioni", alboPubblicazioni);
	// model delle liste da visulaizzare
	model.addAttribute("listacategorie", listaCategorie);
	model.addAttribute("listpubblicazioniallegate", listPubblicazioniallegate);
	setPageAttributes(model);
	return "albopubblicazioni/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // cancella un allegato collegato ad un apubblicazione a partire dal suo codice
    @RequestMapping
    public String deleteAlbopubblicazioneallegatoFromCodice(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("alboPubblicazioni") AlboPubblicazioni alboPubblicazioni, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	AlboPubblicazioniAllegati objToDelete = alboPubblicazioniAllegatiService.findById(new PkId(codice));
	try {
	    alboPubblicazioniAllegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", alboPubblicazioni.getId().getCodice().toString());
	    model.addAttribute("commandName", "alboPubblicazioni");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alboPubblicazioni.getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // rimanda la form per modificare un allegato già collegato ad una pubblicazione
    @RequestMapping
    public String viewAllegato(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	AlboPubblicazioniAllegati alboPubblicazioniAllegati = alboPubblicazioniAllegatiService.findById(id);
	fixRenderEntityPropertyAllegati(alboPubblicazioniAllegati);
	model.addAttribute("alboPubblicazioniAllegati", alboPubblicazioniAllegati);
	setPageAttributes(model);
	return "albopubblicazioni/formAllegati";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // rimanda al forma per aggiungere un allegato ad un apubblicazione
    @RequestMapping
    public String createAllegato(@RequestParam("albopubblicazioni.codice.id") Integer codice, Model model) {

	// §§§BEGIN§§§
	AlboPubblicazioniAllegati alboPubblicazioniAllegati = new AlboPubblicazioniAllegati();
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(new PkId(codice));
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	model.addAttribute("alboPubblicazioniAllegati", alboPubblicazioniAllegati);
	setPageAttributes(model);
	return "albopubblicazioni/formAllegati";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // inserisce un allegato alla pubblicazione
    @RequestMapping
    public String insertAllegato(@ModelAttribute("alboPubblicazioniAllegati") AlboPubblicazioniAllegati alboPubblicazioniAllegati,
	    BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	// recupero l'oggetto corrente e lo inserisco in albopoubblicazioniallegati
	Oggetti oggetto = oggettiService.findById(alboPubblicazioniAllegati.getOggetti().getId());
	alboPubblicazioniAllegati.setOggetti(oggetto);
	// recupero il bando corrente e lo inserisco in albopoubblicazioniallegati
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(alboPubblicazioniAllegati.getAlboPubblicazioni().getId());
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	try {
	    fixMergeEntityPropertyAllegati(alboPubblicazioniAllegati);
	    alboPubblicazioniAllegatiService.insert(alboPubblicazioniAllegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alboPubblicazioniAllegati, e);
	    fixRenderEntityPropertyAllegati(alboPubblicazioniAllegati);
	    return "albopubblicazioni/formAllegati";
	}
	status.setComplete();
	return "redirect:viewAllegato.htm?codice=" + alboPubblicazioniAllegati.getId().getCodice() + "&status_msg=01";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    // modifica un allegato già associato alla pubblicazione
    @RequestMapping
    public String updateAllegato(@ModelAttribute("alboPubblicazioniAllegati") AlboPubblicazioniAllegati alboPubblicazioniAllegati,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	// recupero l'oggetto corrente e lo inserisco in albopoubblicazioniallegati
	Oggetti oggetto = oggettiService.findById(alboPubblicazioniAllegati.getOggetti().getId());
	alboPubblicazioniAllegati.setOggetti(oggetto);
	// recupero il bando corrente e lo inserisco in albopoubblicazioniallegati
	AlboPubblicazioni alboPubblicazioni = alboPubblicazioniService.findById(alboPubblicazioniAllegati.getAlboPubblicazioni().getId());
	alboPubblicazioniAllegati.setAlboPubblicazioni(alboPubblicazioni);
	try {
	    fixMergeEntityPropertyAllegati(alboPubblicazioniAllegati);
	    alboPubblicazioniAllegatiService.update(alboPubblicazioniAllegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alboPubblicazioniAllegati, e);
	    fixRenderEntityPropertyAllegati(alboPubblicazioniAllegati);
	    return "albopubblicazioni/formAllegati";
	}
	status.setComplete();
	return "redirect:viewAllegato.htm?codice=" + alboPubblicazioniAllegati.getId().getCodice() + "&status_msg=02";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(AlboPubblicazioni entity) {

	// §§§BEGIN§§§
	if (entity.getResponsabili() != null && entity.getResponsabili().getId() != null && entity.getResponsabili().getId().getCodice() == null) {
	    entity.setResponsabili(null);
	}
	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& entity.getAmministrazioni().getId().getCodice() == null) {
	    entity.setAmministrazioni(null);
	}
	if (entity.getAmministrazionireferenti() != null && entity.getAmministrazionireferenti().getId() != null
		&& entity.getAmministrazionireferenti().getId().getCodice() == null) {
	    entity.setAmministrazionireferenti(null);
	}
	if (entity.getAlboCategorie() != null && entity.getAlboCategorie().getId() != null && entity.getAlboCategorie().getId().getCodice() == null) {
	    entity.setAlboCategorie(null);
	}
	if (entity.getSoftware() != null && entity.getSoftware().getCodice() != null && entity.getSoftware().getCodice() == null) {
	    entity.setSoftware(null);
	}
	// §§§END§§§
    }

    protected void fixMergeEntityPropertyAllegati(AlboPubblicazioniAllegati entity) {

	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(AlboPubblicazioni entity) {

	if (entity.getResponsabili() == null) {
	    entity.setResponsabili(new Responsabili());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getAmministrazionireferenti() == null) {
	    entity.setAmministrazionireferenti(new Amministrazionireferenti());
	}
	if (entity.getAlboCategorie() == null) {
	    entity.setAlboCategorie(new AlboCategorie());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    protected void fixRenderEntityPropertyAllegati(AlboPubblicazioniAllegati entity) {

	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
