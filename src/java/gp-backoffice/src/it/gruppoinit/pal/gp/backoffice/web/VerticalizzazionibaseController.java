package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneRegoleParametroHelper;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriHelper;
import it.gruppoinit.pal.gp.core.domain.web.VerticalizzazionibaseCommand;
import it.gruppoinit.pal.gp.core.features.rest.RestPrivateAuth;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazionibaseService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;

/**
 * @author gianpaolot
 */
@Controller
@SessionAttributes("verticalizzazionibase")
public class VerticalizzazionibaseController extends BaseController<Verticalizzazionibase> {

    @Autowired
    private VerticalizzazionibaseService verticalizzazionibaseService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    @Autowired
    private VerticalizzazioniparametribaseService verticalizzazioniparametribaseService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private SoftwareattiviService softwareattiviService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Verticalizzazionibase> verticalizzazionibaseList = verticalizzazionibaseService.findAllAndCheckVerticalizzazionibaseconfigurate();
	ModelMap model = new ModelMap(verticalizzazionibaseList);
	model.addAttribute("verticalizzazionibaseList", verticalizzazionibaseList);
	return model;
    }

    @RequestMapping
    public ModelMap listregoleconfigurate(@RequestParam("codice") String id, HttpServletRequest request, HttpServletResponse response) {

	/////////////////////////////////////////////////// NUOVE INFO //////////////////////////////////////////
	Responsabili responsabile = responsabiliService.findById(getCurrentlyAuthenticatedUserDetails().getId());
	Set<Responsabilicomuni> responsabilicomunis = getListResponsabilicomuni(responsabile);
	Set<Responsabilisoftware> responsabilisoftwares = getListResponsabilisoftware(responsabile);
	Verticalizzazionibase entity = verticalizzazionibaseService.findById(id);
	// recupero la lista delle sue verticalizzazioni configurate,controllando quali di queste sono configurabili
	// dall'operatore
	// (un operatore può configurare una verticalizzazione se il software per cui è configurata è attivo)
	Set<Verticalizzazioni> listVerticalizzazioni = verticalizzazioniService.findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(entity);
	entity.setVerticalizzazionis(listVerticalizzazioni);
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	ModelMap model = new ModelMap(verticalizzazionibase);
	verticalizzazionibase.setEntity(entity);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.LIST);
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	List<List<Verticalizzazioni>> list = new ArrayList<List<Verticalizzazioni>>();
	List<Verticalizzazioni> verticalizzazionis = new ArrayList<Verticalizzazioni>();
	for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
	    Software software = responsabilisoftware.getSoftware();
	    verticalizzazionis = new ArrayList<Verticalizzazioni>();
	    if (BooleanUtils.isTrue(entity.getFlagGestcomune())) {
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    Comuni comuni = responsabilicomuni.getComune();
		    Verticalizzazioni verticalizzazioni = verticalizzazioniService.findByComuneAndModulo(entity, comuni.getCodicecomune(),
			    software.getCodice());
		    if (verticalizzazioni != null) {
			verticalizzazionis.add(verticalizzazioni);
		    } else {
			Verticalizzazioni verticalizzazioniNew = new Verticalizzazioni();
			verticalizzazioniNew.setSoftware(software);
			verticalizzazioniNew.setComune(comuni);
			verticalizzazionis.add(verticalizzazioniNew);
		    }
		}
	    }
	    Verticalizzazioni verticalizzazioniWithOutComune = verticalizzazioniService.findByComuneAndModulo(entity, null, software.getCodice());
	    if (verticalizzazioniWithOutComune != null) {
		verticalizzazionis.add(verticalizzazioniWithOutComune);
	    } else {
		Verticalizzazioni verticalizzazioniNew = new Verticalizzazioni();
		verticalizzazioniNew.setSoftware(software);
		verticalizzazioniNew.setComune(null);
		verticalizzazionis.add(verticalizzazioniNew);
	    }
	    list.add(verticalizzazionis);
	    model.addAttribute("list", list);
	}
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	boolean isGestisceComuni = entity.getFlagGestcomune() == null ? Boolean.FALSE : entity.getFlagGestcomune();
	boolean hideComuni = true;
	if (isComuniAssociati) {
	    hideComuni = false;
	    if (!isGestisceComuni) {
		hideComuni = true;
	    }
	}
	model.addAttribute("hideComuni", hideComuni);
	return model;
    }

    @RequestMapping
    public ModelMap riepilogo(@RequestParam("codice") String id, HttpServletRequest request, HttpServletResponse response) {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazionibase entity = verticalizzazionibaseService.findById(id);
	// recupero la lista delle sue verticalizzazioni configurate,controllando quali di queste sono configurabili
	// dall'operatore
	// (un operatore può configurare una verticalizzazione se il software per cui è configurata è attivo)
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	ModelMap model = new ModelMap(verticalizzazionibase);
	verticalizzazionibase.setEntity(entity);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.LIST);
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	List<Softwareattivi> softs = softwareattiviService.findAllAndExcludeTT(false);
	model.addAttribute("softwares", softs);
	return model;
    }

    @RequestMapping
    public String ajaxCaricaConfigurazioni(@RequestParam("codice") String id, @RequestParam("codiceComune") String codiceComune, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	Verticalizzazionibase entity = verticalizzazionibaseService.findById(id);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	boolean isGestisceComuni = entity.getFlagGestcomune() == null ? Boolean.FALSE : entity.getFlagGestcomune();
	boolean hideComuni = true;
	if (isComuniAssociati) {
	    hideComuni = false;
	    if (!isGestisceComuni) {
		hideComuni = true;
	    }
	}
	model.addAttribute("hideComuni", hideComuni);
	// recupero la lista delle sue verticalizzazioni configurate,controllando quali di queste sono configurabili
	// dall'operatore
	// (un operatore può configurare una verticalizzazione se il software per cui è configurata è attivo)
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	if (hideComuni) {
	    codiceComune = null;
	} else {
	    List<Comuniassociati> cs = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    model.addAttribute("listaComunis", cs);
	    if (StringUtils.isBlank(codiceComune)) {
		codiceComune = cs.get(0).getId().getCodicecomune();
	    }
	}
	verticalizzazionibase.setEntity(entity);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.LIST);
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	List<ConfigurazioneRegoleParametroHelper> list = verticalizzazioniService.findConfigurazioneComune(id, codiceComune);
	model.addAttribute("datis", list);
	model.addAttribute("codiceComune", codiceComune);
	return "verticalizzazionibase/riepilogoconfigurazioni";
    }

    /**
     * Restituisce la lista di tutti i comuni che possono essere associati in una lista di Responsabilicomuni
     * 
     * @param responsabile
     * 
     */
    private Set<Responsabilicomuni> getListResponsabilicomuni(Responsabili responsabile) {

	Set<Responsabilicomuni> responsabilicomuniList = new LinkedHashSet<Responsabilicomuni>();
	List<Comuniassociati> comuniassociatiList = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati comuniassociati : comuniassociatiList) {
	    Responsabilicomuni responsabilicomuni = new Responsabilicomuni();
	    ResponsabilicomuniId idRc = new ResponsabilicomuniId();
	    idRc.setCodicecomune(comuniassociati.getId().getCodicecomune());
	    idRc.setCodiceresponsabile(responsabile.getId().getCodice());
	    responsabilicomuni.setId(idRc);
	    responsabilicomuni.setResponsabile(responsabile);
	    responsabilicomuni.setComune(comuniassociati.getComune());
	    responsabilicomuniList.add(responsabilicomuni);
	}
	return responsabilicomuniList;
    }

    /**
     * Restituisce la lista di tutti i software che possono essere attivati in una lista di Responsabilisoftware
     * 
     * @param responsabile
     * 
     */
    private Set<Responsabilisoftware> getListResponsabilisoftware(Responsabili responsabile) {

	Set<Responsabilisoftware> responsabilisoftwareList = new LinkedHashSet<Responsabilisoftware>();
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : softwares) {
	    Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
	    ResponsabilisoftwareId idSw = new ResponsabilisoftwareId(software.getCodice(), responsabile.getId().getCodice());
	    Software sw = softwareService.findById(software.getCodice());
	    responsabilisoftware.setSoftware(sw);
	    responsabilisoftware.setResponsabili(responsabile);
	    responsabilisoftware.setId(idSw);
	    responsabilisoftwareList.add(responsabilisoftware);
	}
	return responsabilisoftwareList;
    }

    @RequestMapping
    public void ajaxAbilitaDisabilita(@RequestParam(value = "codice") Integer codice, @RequestParam("abilita") Integer abilita, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	// Fa una chiamata ajax che fa a modificare dinamicamente il flag_disabilita
	Verticalizzazioni verticalizzazioni = null;
	// Verticalizzazione che vale per tuttii comuni, codicecomune==null
	PkId id = new PkId(codice);
	verticalizzazioni = verticalizzazioniService.findById(id);
	if (abilita == 1) {
	    verticalizzazioni.setAttivo(0);
	} else {
	    verticalizzazioni.setAttivo(1);
	}
	fixMergeVerticalizzazioneProperty(verticalizzazioni);
	try {
	    verticalizzazioniService.update(verticalizzazioni);
	    response.getWriter().write(getMessageFromBundle("attivita.label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("attivita.label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public String createVerticalizzazione(@RequestParam("modulo") String modulo, Model model) {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	Verticalizzazionibase entity = verticalizzazionibaseService.findById(modulo);
	// recupero la lista delle sue verticalizzazioni configurate,controllando quali di queste sono configurabili
	// dall'operatore
	// (un operatore può configurare una verticalizzazione se il software per cui è configurata è attivo)
	Set<Verticalizzazioni> listVerticalizzazioni = verticalizzazioniService.findByVerticalizzazionibaseAndCheckConfigurabilePerOperatore(entity);
	entity.setVerticalizzazionis(listVerticalizzazioni);
	verticalizzazionibase.setEntity(entity);
	Verticalizzazioni verticalizzazioni = new Verticalizzazioni();
	verticalizzazioni.setVerticalizzazionibase(entity);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.NEW);
	fixRenderVerticalizzazioniProperty(verticalizzazioni);
	verticalizzazionibase.setVerticalizzazioni(verticalizzazioni);
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	// recupera la lista di tutti i software configurati per il cimune in esame e abilitati per l'opeartore
	// loggato
	List<Software> softwareAbilitatiList = softwareService.findSoftwareAbilitati(responsabile);
	Set<Responsabilicomuni> responsabilicomunis = getListResponsabilicomuni(responsabile);
	model.addAttribute("comuniList", responsabilicomunis);
	model.addAttribute("softwareList", softwareAbilitatiList);
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	setPageAttributes(model);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	boolean isGestisceComuni = entity.getFlagGestcomune() == null ? Boolean.FALSE : entity.getFlagGestcomune();
	boolean hideComuni = true;
	if (isComuniAssociati) {
	    hideComuni = false;
	    if (!isGestisceComuni) {
		hideComuni = true;
	    }
	}
	model.addAttribute("hideComuni", hideComuni);
	return "verticalizzazionibase/listregoleconfigurate";
    }

    @RequestMapping
    public String insertVerticalizzazione(Model model, @ModelAttribute("verticalizzazionibase") VerticalizzazionibaseCommand verticalizzazionibase,
	    BindingResult result, SessionStatus status) {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni verticalizzazioni = verticalizzazionibase.getVerticalizzazioni();
	if (verticalizzazionibase.getVerticalizzazioni().getSoftware() != null
		&& StringUtils.isNotBlank(verticalizzazionibase.getVerticalizzazioni().getSoftware().getCodice())) {
	    Software software = softwareService.findById(verticalizzazionibase.getVerticalizzazioni().getSoftware().getCodice());
	    verticalizzazioni.setSoftware(software);
	}
	fixMergeVerticalizzazioneProperty(verticalizzazioni);
	String statusMsg = "01";
	try {
	    verticalizzazioniService.insert(verticalizzazioni);
	} catch (Exception e) {
	    statusMsg = "03";
	    copyErrorsToFlashMessages(verticalizzazionibase, true, "entity", e);
	}
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.LIST);
	return "redirect:listregoleconfigurate.htm?codice=" + verticalizzazionibase.getEntity().getModulo() + "&status_msg=" + statusMsg;
    }

    @RequestMapping
    public String aggiungiVerticalizzazioneComuneAndSoftware(@RequestParam("modulo") String modulo, @RequestParam("p_software") String software,
	    @RequestParam(required = false, value = "comune") String comune, Model model, HttpServletRequest request, HttpServletResponse response) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	try {
	    verticalizzazioniService.insert(modulo, comune, software);
	} catch (Exception e) {	    
	    return "verticalizzazionibase/listregoleconfigurate";
	}
	return "redirect:listregoleconfigurate.htm?codice=" + modulo + "&status_msg=01";
    }

    @RequestMapping
    public String eliminaVerticalizzazioneComuneAndSoftware(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni objToDelete = verticalizzazioniService.findById(new PkId(codice));
	String modulo = objToDelete.getVerticalizzazionibase().getModulo();
	try {
	    verticalizzazioniService.delete(objToDelete);
	} catch (Exception e) {
	    return "verticalizzazionibase/listregoleconfigurate";
	}
	return "redirect:listregoleconfigurate.htm?codice=" + modulo + "&status_msg=01";
    }

    @RequestMapping
    public String deleteVerticalizzazione(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("verticalizzazionibase") VerticalizzazionibaseCommand verticalizzazionibase, BindingResult result, SessionStatus status) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	PkId id = new PkId(codice);
	Verticalizzazioni objToDelete = verticalizzazioniService.findById(id);
	String modulo = objToDelete.getVerticalizzazionibase().getModulo();
	verticalizzazioniService.delete(objToDelete);
	status.setComplete();
	return "redirect:listregoleconfigurate.htm?codice=" + modulo;
    }

    @RequestMapping
    public ModelMap listparametribase(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni v = verticalizzazioniService.findById(new PkId(codice));
	Verticalizzazionibase entity = v.getVerticalizzazionibase();
	String codiceComune = null;
	if (v.getComune() != null) {
	    if (StringUtils.isNotBlank(v.getComune().getCodicecomune())) {
		codiceComune = v.getComune().getCodicecomune();
	    }
	}
	List<VerticalizzazioniparametriHelper> listVerticalizzazioniparametriHelpers = verticalizzazioniparametriService
		.findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatore(entity.getModulo(), v.getSoftware().getCodice(), codiceComune);
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	ModelMap model = new ModelMap(verticalizzazionibase);
	verticalizzazionibase.setEntity(entity);
	verticalizzazionibase.setVerticalizzazioniparametriHelpers(listVerticalizzazioniparametriHelpers);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.LIST);
	model.addAttribute("isSoftwareTT", v.getSoftware().getCodice().equals(WebConstants.SOFTWARE_TT));
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	model.addAttribute("verticalizzazioni", v);
	model.addAttribute("auth", RestPrivateAuth.current().toBase64());
	return model;
    }

    @RequestMapping
    public String createParametriverticalizzazione(@RequestParam("codice") Integer codiceVerticalizzazione, Model model) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni v = verticalizzazioniService.findById(new PkId(codiceVerticalizzazione));
	Verticalizzazionibase entity = v.getVerticalizzazionibase();
	VerticalizzazionibaseCommand verticalizzazionibase = new VerticalizzazionibaseCommand();
	String codiceComune = null;
	if (v.getComune() != null) {
	    if (StringUtils.isNotBlank(v.getComune().getCodicecomune())) {
		codiceComune = v.getComune().getCodicecomune();
	    }
	}
	List<VerticalizzazioniparametriHelper> listVerticalizzazioniparametriHelpers = verticalizzazioniparametriService
		.findVerticalizzazioniparametriHelperAndCheckConfigurabilePerOperatore(entity.getModulo(), v.getSoftware().getCodice(), codiceComune);
	Verticalizzazioniparametri verticalizzazioniparametri = new Verticalizzazioniparametri();
	verticalizzazioniparametri.setSoftware(v.getSoftware());
	verticalizzazioniparametri.setComune(v.getComune());
	verticalizzazionibase.setEntity(entity);
	verticalizzazionibase.setVerticalizzazioniparametri(verticalizzazioniparametri);
	verticalizzazionibase.setVerticalizzazioniparametriHelpers(listVerticalizzazioniparametriHelpers);
	verticalizzazionibase.setDisplayMode(VerticalizzazionibaseCommand.NEW);
	fixRenderVerticalizzazioniparametriProperty(verticalizzazioniparametri);
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	Responsabili responsabile = responsabiliService.findById(new PkId(userDetail.getCodiceResponsabile()));
	// recupera la lista di tutti i software configurati per il cimune in esame e abilitati per l'opeartore loggato
	List<Software> softwareAbilitatiList = softwareService.findSoftwareAbilitati(responsabile);
	model.addAttribute("verticalizzazioni", v);
	model.addAttribute("isSoftwareTT", v.getSoftware().getCodice().equalsIgnoreCase(WebConstants.SOFTWARE_TT));
	model.addAttribute("verticalizzazionibase", verticalizzazionibase);
	model.addAttribute("softwareList", softwareAbilitatiList);
	setPageAttributes(model);
	return "verticalizzazionibase/listparametribase";
    }

    @RequestMapping
    public String insertParametriverticalizzazione(@RequestParam("codice") Integer codiceVerticalizzazione, Model model,
	    @ModelAttribute("verticalizzazionibase") VerticalizzazionibaseCommand verticalizzazionibase, BindingResult result, SessionStatus status) {
	
	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni verticalizzazioni = verticalizzazioniService.findById(new PkId(codiceVerticalizzazione));
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazionibase.getVerticalizzazioniparametri();
	if (verticalizzazioniparametri.getSoftware() != null && StringUtils.isNotBlank(verticalizzazioniparametri.getSoftware().getCodice())) {
	    Software software = softwareService.findById(verticalizzazioniparametri.getSoftware().getCodice());
	    verticalizzazioniparametri.setSoftware(software);
	}
	if (verticalizzazioniparametri.getVerticalizzazioniparametribase() != null
		&& StringUtils.isNotBlank(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro())) {
	    VerticalizzazioniparametribaseId verticalizzazioniparametribaseId = new VerticalizzazioniparametribaseId();
	    verticalizzazioniparametribaseId.setModulo(verticalizzazionibase.getEntity().getModulo());
	    verticalizzazioniparametribaseId.setParametro(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro());
	    Verticalizzazioniparametribase verticalizzazioniparametribase = verticalizzazioniparametribaseService
		    .findById(verticalizzazioniparametribaseId);
	    verticalizzazioniparametri.setVerticalizzazioniparametribase(verticalizzazioniparametribase);
	}
	String statusMsg = "01";
	try {
	    verticalizzazioniparametriService.insert(verticalizzazioniparametri);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(verticalizzazionibase.getVerticalizzazioniparametri(), true, "entity", e);
	    statusMsg = "03";
	}
	status.setComplete();
	return "redirect:listparametribase.htm?codice=" + verticalizzazioni.getId().getCodice() + "&status_msg=" + statusMsg;
    }

    @RequestMapping
    public String updateParametriverticalizzazione(@RequestParam("codice") Integer codiceVerticalizzazione,
	    @ModelAttribute("verticalizzazionibase") VerticalizzazionibaseCommand verticalizzazionibase, BindingResult result, SessionStatus status) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	Verticalizzazioni verticalizzazioni = verticalizzazioniService.findById(new PkId(codiceVerticalizzazione));
	List<VerticalizzazioniparametriHelper> verticalizzazioniparametriHelpers = verticalizzazionibase.getVerticalizzazioniparametriHelpers();
	String statusMsg = "01";
	try {
	    for (VerticalizzazioniparametriHelper verticalizzazioniparametriHelper : verticalizzazioniparametriHelpers) {
		List<Verticalizzazioniparametri> verticalizzazioniparametribases = verticalizzazioniparametriHelper.getVerticalizzazioniparametris();
		for (Verticalizzazioniparametri verticalizzazioniparametri : verticalizzazioniparametribases) {
		    verticalizzazioniparametriService.update(verticalizzazioniparametri);
		}
	    }
	} catch (Exception e) {
	    copyErrorsToFlashMessages(verticalizzazionibase.getVerticalizzazioniparametri(), true, "entity", e);
	    statusMsg = "03";
	}
	status.setComplete();
	return "redirect:listparametribase.htm?codice=" + verticalizzazioni.getId().getCodice() + "&status_msg=" + statusMsg;
    }

    @RequestMapping
    public String deleteParametriverticalizzazione(@RequestParam("codice") Integer codice,
	    @RequestParam("codiceVerticalizzazione") Integer codiceVerticalizzazione, Model model,
	    @ModelAttribute("verticalizzazionibase") VerticalizzazionibaseCommand verticalizzazionibase, BindingResult result, SessionStatus status) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_REGOLE.name());
	PkId id = new PkId(codice);
	Verticalizzazioniparametri objToDelete = verticalizzazioniparametriService.findById(id);
	verticalizzazioniparametriService.delete(objToDelete);
	status.setComplete();
	return "redirect:listparametribase.htm?codice=" + codiceVerticalizzazione;
    }

    @RequestMapping
    public void ajaxDescrizione(@RequestParam("modulo") String modulo, @RequestParam("parametro") String parametro, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Fa una chiamata ajax che fa a modificare dinamicamente il flag_disabilita
	VerticalizzazioniparametribaseId id = new VerticalizzazioniparametribaseId();
	id.setModulo(modulo);
	id.setParametro(parametro);
	Verticalizzazioniparametribase verticalizzazioniparametribase = verticalizzazioniparametribaseService.findById(id);
	if (verticalizzazioniparametribase != null)
	    response.getWriter().write(verticalizzazioniparametribase.getDescrizione());
    }

    private void fixRenderVerticalizzazioniProperty(Verticalizzazioni entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getVerticalizzazionibase() == null) {
	    entity.setVerticalizzazionibase(new Verticalizzazionibase());
	}
    }

    private void fixMergeVerticalizzazioneProperty(Verticalizzazioni entity) {

	if (entity.getVerticalizzazionibase() != null && StringUtils.isBlank(entity.getVerticalizzazionibase().getModulo())) {
	    entity.setVerticalizzazionibase(null);
	}
	if (entity.getSoftware() != null && StringUtils.isBlank(entity.getSoftware().getCodice())) {
	    entity.setSoftware(null);
	}
    }

    private void fixRenderVerticalizzazioniparametriProperty(Verticalizzazioniparametri entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getVerticalizzazioniparametribase() == null) {
	    entity.setVerticalizzazioniparametribase(new Verticalizzazioniparametribase());
	}
    }

    @Override
    protected void fixMergeEntityProperty(Verticalizzazionibase entity) {
	// do nothing
    }

    @Override
    protected void fixRenderEntityProperty(Verticalizzazionibase entity) {
	// do nothing
    }

    @Override
    protected void setPageAttributes(Model model) {
	// do nothing
    }
}
