package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipicausalioninteressiService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.LinkedHashSet;
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

/**
 * 
 * @author francescop
 */
@Controller
@SessionAttributes("tipicausalioneri")
public class TipicausalioneriController extends BaseController<Tipicausalioneri> {

    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private TipicausalioninteressiService tipicausalioninteressiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private RaggruppamentocausalioneriService raggruppamentocausalioneriService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(@RequestParam(value = "flgTipicausaliinteressi", required = false) Boolean flgTipicausaliinteressi,
	    HttpServletRequest request, HttpServletResponse response) {

	boolean flgTipiCausaliInt = false;
	if (flgTipicausaliinteressi == null) {
	    flgTipicausaliinteressi = false;
	}
	//le causali degli oneri sono censite solo a liovello regionale
	List<Tipicausalioneri> tipicausalioneriList = tipicausalioneriService.findAll(ORMHelper.getIdcomunebase(), null, null,
		flgTipicausaliinteressi);
	ModelMap model = new ModelMap(tipicausalioneriList);
	boolean export = createJMesaExport(request, response, tipicausalioneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("isMora", flgTipicausaliinteressi);
	model.addAttribute("tipicausalioneriList", tipicausalioneriList);
	return model;
    }

    @RequestMapping
    public String listinteressimora(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<Tipicausalioneri> tipicausalioneriList = tipicausalioneriService.findAll(ORMHelper.getIdente(), null, null, true);
	//	model = new ModelMap(tipicausalioneriList);
	boolean export = createJMesaExport(request, response, tipicausalioneriList);
	if (export) {
	    return null;
	}
	model.addAttribute("isMora", true);
	model.addAttribute("tipicausalioneriList", tipicausalioneriList);
	return "tipicausalioneri/list";
    }

    @RequestMapping
    public String create(Model model) {

	Tipicausalioneri tipicausalioneri = new Tipicausalioneri();
	tipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipicausalioneri);
	model.addAttribute("tipicausalioneri", tipicausalioneri);
	setPageAttributes(model);
	return "tipicausalioneri/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status) {

	tipicausalioneri.setRaggruppamentocausalioneri(null);
	tipicausalioneri.setCausalebollo(null);
	fixMergeEntityProperty(tipicausalioneri);
	tipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    tipicausalioneriService.insert(tipicausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipicausalioneri, e);
	    fixRenderEntityProperty(tipicausalioneri);
	    prepareView(model, tipicausalioneri);
	    setPageAttributes(model);
	    return "tipicausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&codicecomune=" + tipicausalioneri.getId().getCodice()
		+ "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	/*
	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	*/
	PkId id = new PkId(ORMHelper.getIdcomunebase(), codice);
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(id);
	fixRenderEntityProperty(tipicausalioneri);
	prepareView(model, tipicausalioneri);
	setPageAttributes(model);
	return "tipicausalioneri/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	tipicausalioneri.setRaggruppamentocausalioneri(null);
	tipicausalioneri.setCausalebollo(null);
	tipicausalioneri.setTipicausalioneriMora(null);
	fixMergeEntityProperty(tipicausalioneri);
	try {
	    tipicausalioneriService.update(tipicausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipicausalioneri, e);
	    prepareView(model, tipicausalioneri);
	    fixRenderEntityProperty(tipicausalioneri);
	    setPageAttributes(model);
	    return "tipicausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status) {

	Tipicausalioneri objToDelete = tipicausalioneriService.findById(tipicausalioneri.getId());
	try {
	    tipicausalioneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(tipicausalioneri);
	    return "tipicausalioneri/form";
	}
	status.setComplete();
	return "redirect:list.htm?flgTipicausaliinteressi=" + tipicausalioneri.getFlgTipicausaliinteressi();
    }

    @RequestMapping
    public String deleteTipicausaliInteressi(@RequestParam("codice") Integer codiceTipicausaliOneriInteressi,
	    @RequestParam("indiceLista") Integer indiceLista, Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri,
	    BindingResult result, SessionStatus status) {

	PkId id = new PkId(codiceTipicausaliOneriInteressi);
	Tipicausalioninteressi objToDelete = tipicausalioninteressiService.findById(id);
	// Rimuove fisicamnete il record da BD
	if (objToDelete != null) {
	    tipicausalioneri = tipicausalioneriService.findById(objToDelete.getTipicausalioneri().getId());
	    try {
		tipicausalioninteressiService.delete(objToDelete);
	    } catch (Exception e) {
		copyErrorsToBindingResult(result, objToDelete, e);
		List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
		model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
		boolean isBollo = tipicausalioneriService.isCausaleBollo(tipicausalioneri, null);
		if (!isBollo) {
		    List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
		    model.addAttribute("causalibolloList", causalibolloList);
		}
		fixRenderEntityProperty(tipicausalioneri);
		prepareView(model, tipicausalioneri);
		setPageAttributes(model);
		return "tipicausalioneri/form";
	    }
	} else {// Rimuove l'oggetto sola dalla lista, l'oggetto ancora non è stato salvato su DB
	    Set<Tipicausalioninteressi> tipicausalioninteressiSet = removeTipicausaleonereinteressiNonSalvato(tipicausalioneri, indiceLista);
	    tipicausalioneri.setTipicausalioninteressis(tipicausalioninteressiSet);
	    List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	    model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	    boolean isBollo = tipicausalioneriService.isCausaleBollo(tipicausalioneri, null);
	    if (!isBollo) {
		List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
		model.addAttribute("causalibolloList", causalibolloList);
	    }
	    fixRenderEntityProperty(tipicausalioneri);
	    prepareView(model, tipicausalioneri);
	    setPageAttributes(model);
	    return "tipicausalioneri/form";
	    //	    return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&status_msg=02";
	}
	return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String addTipicausaliInteressi(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	PkId id = new PkId(codice);
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(id);
	boolean isBollo = tipicausalioneriService.isCausaleBollo(tipicausalioneri, null);
	if (!isBollo) {
	    List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
	    model.addAttribute("causalibolloList", causalibolloList);
	}
	fixRenderEntityProperty(tipicausalioneri);
	prepareView(model, tipicausalioneri);
	setPageAttributes(model);
	return "tipicausalioneri/form";
    }

    /**
     * Rimuvove dalla lista l'oggetto nella posizione indicata da "indiceLista"
     * 
     * @param tipicausalioneri
     * @param indiceLista
     * @return
     */
    private Set<Tipicausalioninteressi> removeTipicausaleonereinteressiNonSalvato(Tipicausalioneri tipicausalioneri, Integer indiceLista) {

	Set<Tipicausalioninteressi> list = tipicausalioneri.getTipicausalioninteressis();
	Set<Tipicausalioninteressi> risultato = new LinkedHashSet<Tipicausalioninteressi>();
	int i = 0;
	for (Tipicausalioninteressi tipicausalioninteressi : list) {
	    if (i != indiceLista) {
		risultato.add(tipicausalioninteressi);
	    }
	    i++;
	}
	return risultato;
    }

    /**
     * <pre>
     * </pre>
     */
    private void prepareView(Model model, Tipicausalioneri tipicausalioneri) {

	model.addAttribute("tipicausalioneri", tipicausalioneri);
    }

    @Override
    protected void fixMergeEntityProperty(Tipicausalioneri entity) {

	/*
	if (entity.getRaggruppamentocausalioneri() != null && entity.getRaggruppamentocausalioneri().getId() != null
		&& entity.getRaggruppamentocausalioneri().getId().getCodice() == null) {
	    entity.setRaggruppamentocausalioneri(null);
	}
	if (entity.getCausalebollo() != null && entity.getCausalebollo().getId() != null && entity.getCausalebollo().getId().getCodice() == null) {
	    entity.setCausalebollo(null);
	    if (EntityUtils.getNestedProperty(entity.getTipicausalioneriMora(), "id.codice") == null) {
		entity.setTipicausalioneriMora(null);
	    }
	}
	*/
    }

    @Override
    protected void fixRenderEntityProperty(Tipicausalioneri entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

	/*
	 * Controllo se è attiva la VERTICALIZZAZIONE PEOPLE
	 */
	Verticalizzazioni verticalizzazioni_PEOPLE = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	if (verticalizzazioni_PEOPLE != null) {
	    if (verticalizzazioni_PEOPLE.getAttivo() == 1) {
		model.addAttribute("vert_people_attivo", true);
	    } else {
		model.addAttribute("vert_people_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_people_attivo", false);
	}
	/*
	 * Controllo se è attiva la VERTICALIZZAZIONE PEOPLE
	 */
	Verticalizzazioni verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO = verticalizzazioniService
		.findByModulo(WebConstants.VERTICALIZZAZIONE_SISTEMAPAGAMENTI_ATTIVO);
	if (verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO != null) {
	    if (verticalizzazioni_SISTEMAPAGAMENTI_ATTIVO.getAttivo() == 1) {
		model.addAttribute("vert_pagamenti_attivo", true);
	    } else {
		model.addAttribute("vert_pagamenti_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_pagamenti_attivo", false);
	}
    }
}
