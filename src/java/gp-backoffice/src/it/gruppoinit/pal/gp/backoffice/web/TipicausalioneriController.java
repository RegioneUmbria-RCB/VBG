package it.gruppoinit.pal.gp.backoffice.web;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneridettaglio;
import it.gruppoinit.pal.gp.core.domain.TipicausalioneridettaglioId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioninteressi;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.RaggruppamentocausalioneriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneridettaglioService;
import it.gruppoinit.pal.gp.core.service.TipicausalioninteressiService;

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
    @Autowired
    private ContiService contiService;
    @Autowired
    private TipicausalioneridettaglioService tipicausalioneridettaglioService;

    @RequestMapping
    public ModelMap list(@RequestParam("flgTipicausaliinteressi") Boolean flgTipicausaliinteressi, HttpServletRequest request,
	    HttpServletResponse response) {

	List<Tipicausalioneri> tipicausalioneriList = tipicausalioneriService.findAll(null, null, flgTipicausaliinteressi);
	ModelMap model = new ModelMap(tipicausalioneriList);
	//	boolean export = createJMesaExport(request, response, tipicausalioneriList);
	//	if (export) {
	//	    return null;
	//	}
	model.addAttribute("isMora", flgTipicausaliinteressi);
	model.addAttribute("tipicausalioneriList", tipicausalioneriList);
	return model;
    }

    @RequestMapping
    public String listinteressimora(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<Tipicausalioneri> tipicausalioneriList = tipicausalioneriService.findAll(null, null, true);
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
    public String create(Model model, @RequestParam("isMora") boolean isMora) {

	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(null);
	model.addAttribute("causalibolloList", causalibolloList);
	Tipicausalioneri tipicausalioneri = new Tipicausalioneri();
	tipicausalioneri.setFlgTipicausaliinteressi(isMora);
	tipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(tipicausalioneri);
	model.addAttribute("tipicausalioneri", tipicausalioneri);
	setPageAttributes(model);
	return "tipicausalioneri/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status) {

	if (tipicausalioneri.getRaggruppamentocausalioneri().getId().getCodice() == null
		|| tipicausalioneri.getRaggruppamentocausalioneri().getId().getCodice() == 0) {
	    tipicausalioneri.setRaggruppamentocausalioneri(null);
	}
	if (tipicausalioneri.getCausalebollo().getId().getCodice() == null || tipicausalioneri.getCausalebollo().getId().getCodice() == 0) {
	    tipicausalioneri.setCausalebollo(null);
	}
	if (EntityUtils.getNestedProperty(tipicausalioneri.getTipicausalioneriMora(), "id.codice") != null) {
	    Tipicausalioneri tipicausalioneriMora = tipicausalioneriService
		    .findById(new PkId(tipicausalioneri.getTipicausalioneriMora().getId().getCodice()));
	    tipicausalioneri.setTipicausalioneriMora(tipicausalioneriMora);
	}
	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	fixMergeEntityProperty(tipicausalioneri);
	tipicausalioneri.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	if (!tipicausalioneri.getPagamentiregulus()) {
	    tipicausalioneri.setCausalebollo(null);
	}
	try {
	    tipicausalioneriService.insert(tipicausalioneri);
	    if (tipicausalioneri.getContoAttivo() != null) {
		Tipicausalioneridettaglio tcod = new Tipicausalioneridettaglio();
		TipicausalioneridettaglioId id = new TipicausalioneridettaglioId(tipicausalioneri.getContoAttivo(),
			tipicausalioneri.getId().getCodice());
		tcod.setId(id);
		tcod.setFlagAttivo(Boolean.TRUE);
		tipicausalioneridettaglioService.insert(tcod);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipicausalioneri, e);
	    fixRenderEntityProperty(tipicausalioneri);
	    prepareView(model, tipicausalioneri, false, true);
	    List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(null);
	    setPageAttributes(model);
	    model.addAttribute("causalibolloList", causalibolloList);
	    return "tipicausalioneri/form";
	}
	List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
	model.addAttribute("causalibolloList", causalibolloList);
	status.setComplete();
	return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	PkId id = new PkId(codice);
	Tipicausalioneri tipicausalioneri = tipicausalioneriService.findById(id);
	boolean isBollo = tipicausalioneriService.isCausaleBollo(tipicausalioneri, null);
	if (!isBollo) {
	    List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
	    model.addAttribute("causalibolloList", causalibolloList);
	}
	//metodo per recuperare i conti attivi
	Integer idContoAttivo = tipicausalioneridettaglioService.findIdContoAttivoByCausaleOneri(codice);
	tipicausalioneri.setContoAttivo(idContoAttivo);
	fixRenderEntityProperty(tipicausalioneri);
	prepareView(model, tipicausalioneri, false, false);
	setPageAttributes(model);
	return "tipicausalioneri/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// Uso la lista solo in caso di eccezione nel salvataggio, per settarla nuovamente all'oggetto Tipicausalioneri
	// In quanto nel service nella fase di update prima di salvare l'oggetto Tipicausalioneri si annulla la lista 
	// delle Tipicausalioninteressi e si inseriscono uno alla volta
	Set<Tipicausalioninteressi> listTemp = tipicausalioneri.getTipicausalioninteressis();
	if (tipicausalioneri.getRaggruppamentocausalioneri().getId().getCodice() == null
		|| tipicausalioneri.getRaggruppamentocausalioneri().getId().getCodice() == 0) {
	    tipicausalioneri.setRaggruppamentocausalioneri(null);
	}
	if (tipicausalioneri.getCausalebollo().getId().getCodice() == null || tipicausalioneri.getCausalebollo().getId().getCodice() == 0) {
	    tipicausalioneri.setCausalebollo(null);
	}
	if (EntityUtils.getNestedProperty(tipicausalioneri.getTipicausalioneriMora(), "id.codice") != null) {
	    Tipicausalioneri tipicausalioneriMora = tipicausalioneriService
		    .findById(new PkId(tipicausalioneri.getTipicausalioneriMora().getId().getCodice()));
	    tipicausalioneri.setTipicausalioneriMora(tipicausalioneriMora);
	}
	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
	model.addAttribute("causalibolloList", causalibolloList);
	if (!tipicausalioneri.getPagamentiregulus()) {
	    tipicausalioneri.setCausalebollo(null);
	}
	fixMergeEntityProperty(tipicausalioneri);
	try {
	    tipicausalioneriService.update(tipicausalioneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipicausalioneri, e);
	    tipicausalioneri.setTipicausalioninteressis(listTemp);
	    model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	    model.addAttribute("causalibolloList", causalibolloList);
	    prepareView(model, tipicausalioneri, true, true);
	    fixRenderEntityProperty(tipicausalioneri);
	    setPageAttributes(model);
	    model.addAttribute("causalibolloList", causalibolloList);
	    return "tipicausalioneri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipicausalioneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipicausalioneri") Tipicausalioneri tipicausalioneri, BindingResult result,
	    SessionStatus status) {

	List<Raggruppamentocausalioneri> raggruppamentocausalioneris = raggruppamentocausalioneriService.findAll(null, null);
	model.addAttribute("raggruppamentocausalioneriList", raggruppamentocausalioneris);
	List<Tipicausalioneri> causalibolloList = tipicausalioneriService.findCausaliBollo(tipicausalioneri);
	model.addAttribute("causalibolloList", causalibolloList);
	Tipicausalioneri objToDelete = tipicausalioneriService.findById(tipicausalioneri.getId());
	try {
	    //Elimino i riferimenti a TipiCausaliOneriDettaglio
	    tipicausalioneridettaglioService.deleteByIdOnere(objToDelete.getId().getCodice());
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
		prepareView(model, tipicausalioneri, false, true);
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
	    prepareView(model, tipicausalioneri, true, false);
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
	prepareView(model, tipicausalioneri, true, true);
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
     * Il metodo popola l'oggetto tipicausalioneri per la visualizzazione su jsp
     * Se: isAddTipicausaliinteressi==false allora i dati tipi causali oneri interessi vengono recuperati da db (richiamato dal metodo view(..) )
     * Se: isAddTipicausaliinteressi==true allora i dati tipi causali oneri interessi vengono recuperati dal model in quanto dovremo riportare anche quelli 
     * 					aggiunti da interfaccia e non acora salvati. (richiamato dal metodo addTipicausaliInteressi(..) )
     * &#64;param model
     * &#64;param tipicausalioneri
     * &#64;param isAddTipicausaliinteressi
     * </pre>
     */
    private void prepareView(Model model, Tipicausalioneri tipicausalioneri, boolean isAddTipicausaliinteressi, boolean isAddNewRecord) {

	Set<Tipicausalioninteressi> tipicausalioninteressis = new LinkedHashSet<Tipicausalioninteressi>();
	// Caso del metodo richiamato da addTipicausaliInteressi(..)
	if (isAddTipicausaliinteressi) {
	    List<Tipicausalioninteressi> listTemp = tipicausalioninteressiService.findByTipicausalioneri(tipicausalioneri);
	    for (Tipicausalioninteressi tipicausalioninteressi : listTemp) {
		tipicausalioninteressis.add(tipicausalioninteressi);
	    }
	    // tipicausalioninteressis = tipicausalioneri.getTipicausalioninteressis();
	    int i = 0;
	    // variabile temporane che tiene in memoria l'oggetto Tipicausalioninteressi
	    Tipicausalioninteressi tipicausalioninteressiPrecedente = null;
	    for (Tipicausalioninteressi tipicausalioninteressi : tipicausalioninteressis) {
		// Al primo passaggio il valore dei giorni di scadenza sarà zero
		//(GgritardopagamentoPrecedente campo transiet aggiunto  riportare sulla lista il dato dei giorni di scadenza 
		//(del Tipicausalioninteressi precedente. Es. se  Tipicausalioninteressi[0].ggritardopagamentoPrecedente=0 Tipicausalioninteressi[0].ggritardopagamento=10 al passo due
		// Tipicausalioninteressi[1].ggritardopagamentoPrecedente=Tipicausalioninteressi[0].ggritardopagamento......
		//Tipicausalioninteressi[n].ggritardopagamentoPrecedente=valore=Tipicausalioninteressi[n-1].ggritardopagamento
		if (i == 0) {
		    tipicausalioninteressi.setGgritardopagamentoPrecedente(new Integer(0));
		} else {// Al secondo passo segue la logica descritta prima
		    if (tipicausalioninteressiPrecedente.getGgritardopagamento() != null) {
			tipicausalioninteressi.setGgritardopagamentoPrecedente(tipicausalioninteressiPrecedente.getGgritardopagamento());
		    }
		    // Annullo l'ogetto in modo che porò inserire il nuovo precedente
		    tipicausalioninteressiPrecedente = null;
		}
		// Inserisco il nuovo precedente
		tipicausalioninteressiPrecedente = tipicausalioninteressi;
		// Contatore che mi dici che non sono più al primo passo
		i++;
	    }
	    // Ho premuto aggiungi , quindi inserisco un nuovo record di tipi Tipicausalioninteressi solo se esplicitamente richiesto
	    if (isAddNewRecord) {
		Tipicausalioninteressi tipicausalioninteressi = new Tipicausalioninteressi();
		tipicausalioninteressis.add(tipicausalioninteressi);
		tipicausalioneri.setTipicausalioninteressis(tipicausalioninteressis);
	    }
	} else {
	    // Caso del metodo richiamato da view(..), recupero la lista dal db, applico la stessa logica per popolare il campo transient
	    // ggritardopagamentoPrecedente
	    List<Tipicausalioninteressi> tipicausalioninteressisDB = tipicausalioninteressiService.findByTipicausalioneri(tipicausalioneri);
	    int i = 0;
	    for (Tipicausalioninteressi tipicausalioninteressi : tipicausalioninteressisDB) {
		if (i == 0) {
		    tipicausalioninteressi.setGgritardopagamentoPrecedente(new Integer(0));
		} else {
		    tipicausalioninteressi.setGgritardopagamentoPrecedente(tipicausalioninteressisDB.get(i - 1).getGgritardopagamento());
		}
		i++;
	    }
	    tipicausalioninteressis = new LinkedHashSet(tipicausalioninteressisDB);
	}
	tipicausalioneri.setTipicausalioninteressis(tipicausalioninteressis);
	model.addAttribute("tipicausalioneri", tipicausalioneri);
    }

    @Override
    protected void fixMergeEntityProperty(Tipicausalioneri entity) {

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
    }

    @Override
    protected void fixRenderEntityProperty(Tipicausalioneri entity) {

	if (entity.getRaggruppamentocausalioneri() == null) {
	    entity.setRaggruppamentocausalioneri(new Raggruppamentocausalioneri());
	}
	if (entity.getCausalebollo() == null) {
	    entity.setCausalebollo(new Tipicausalioneri());
	}
	if (entity.getTipicausalioneriMora() == null) {
	    entity.setTipicausalioneriMora(new Tipicausalioneri());
	}
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
	 * Controllo se è attiva la VERTICALIZZAZIONE SIEDER
	 */
	Verticalizzazioni verticalizzazioni_sieder = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_SIEDER);
	if (verticalizzazioni_sieder != null) {
	    if (verticalizzazioni_sieder.getAttivo() == 1) {
		model.addAttribute("vert_sieder_attivo", true);
	    } else {
		model.addAttribute("vert_sieder_attivo", false);
	    }
	} else {
	    model.addAttribute("vert_sieder_attivo", false);
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
	/*
	 * Controllo se è attiva la VERTICALIZZAZIONE NODO_PAGAMENTI 
	 */
	Verticalizzazioni verticalizzazioni_nodoPagamenti_attiva = verticalizzazioniService
		.findByModulo(VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE);
	if (verticalizzazioni_nodoPagamenti_attiva != null) {
	    if (verticalizzazioni_nodoPagamenti_attiva.getAttivo() == 1) {
		model.addAttribute("verticalizzazioni_nodoPagamenti_attiva", true);
	    } else {
		model.addAttribute("verticalizzazioni_nodoPagamenti_attiva", false);
	    }
	} else {
	    model.addAttribute("verticalizzazioni_nodoPagamenti_attiva", false);
	}
	/*
	 * Carico la lista dei conti
	 */
	List<Conti> contiList = this.contiService.findAll(null, null);
	model.addAttribute("contiList", contiList);
    }
}
