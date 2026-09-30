package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.DocumentiistanzaCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeattivitaCommand;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes("istanzeattivita")
public class IstanzeattivitaController extends BaseController<Istanzeattivita> {

    @Autowired
    private IstanzeattivitaService istanzeattivitaService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private SettoriService settoriService;
    @Autowired
    private AttivitaService attivitaService;

    @RequestMapping
    public String list(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request, HttpServletResponse response) {

	IstanzeattivitaCommand istanzeattivita = new IstanzeattivitaCommand();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	istanzeattivita.setIstanza(istanza);
	Boolean orderByCodiceistat = Boolean.FALSE;
	if (leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT, "1", request).equals("1")) {
	    orderByCodiceistat = Boolean.TRUE;
	}
	/*
	 * Viene utilizzata la Map settoreAttivitaMap allo scopo di raggruppare le istanzeattivita per settore 
	 * Viene utilizzata la Map settoreTotMQ per associare ad ogni settore il totale dei metriq delle sue istanzeattività
	 */
	Map<Settori, List<Istanzeattivita>> settoreAttivitaMap = new LinkedHashMap<Settori, List<Istanzeattivita>>();
	Map<Settori, BigDecimal> settoreTotMQ = new LinkedHashMap<Settori, BigDecimal>();
	List<Istanzeattivita> istanzeattivitaList = istanzeattivitaService.findByIstanza(istanza, orderByCodiceistat);
	for (Istanzeattivita istanzeattivita2 : istanzeattivitaList) {
	    BigDecimal totMq = new BigDecimal(0);
	    Settori settore = istanzeattivita2.getAttivita().getSettori();
	    if (!settoreAttivitaMap.containsKey(settore)) {
		List<Istanzeattivita> istanzeattivitas = new ArrayList<Istanzeattivita>();
		for (Istanzeattivita istanzeattivitaTemp : istanzeattivitaList) {
		    if (istanzeattivitaTemp.getAttivita().getSettori().equals(settore)) {
			istanzeattivitas.add(istanzeattivitaTemp);
			if (settore.getTipiunitamisura() != null) {
			    if (istanzeattivitaTemp.getMetriq() != null) {
				totMq = totMq.add(istanzeattivitaTemp.getMetriq());
			    }
			}
		    }
		}
		settoreAttivitaMap.put(settore, istanzeattivitas);
		settoreTotMQ.put(settore, totMq);
	    }
	}
	istanzeattivita.setSettoreAttivitaMap(settoreAttivitaMap);
	istanzeattivita.setSettoreTotMQ(settoreTotMQ);
	model.addAttribute("istanzeattivita", istanzeattivita);
	setPageAttributes(model, request, response);
	return "istanzeattivita/list";
    }

    /*
     * Ritorna il form per la scelta del settore dell'attività
     */
    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model) {

	Settori settori = new Settori();
	IstanzeattivitaCommand istanzeattivita = new IstanzeattivitaCommand();
	istanzeattivita.setSettori(settori);
	istanzeattivita.setDisplayMode(IstanzeattivitaCommand.NEW);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	istanzeattivita.setIstanza(istanza);
	Istanzeattivita entity = new Istanzeattivita();
	entity.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	entity.setIstanza(istanza);
	fixRenderEntityProperty(entity);
	istanzeattivita.setEntity(entity);
	/*
	 * La variabile isStep2 memorizza il livello corrente della procedura di creazione di una nuova istanzaattivita.
	 * La variabile isInsMultiplo memorizza se il settore selezionato ha flagInsmultiplo=1.
	 * La variabile isUnitaMisura memorizza se il settore selezionato ha tipiunitamisura!=null.
	 * Tali variabili mostrano/nascondono alcuni campi nella jsp
	 */
	Boolean isStep2 = false;
	Boolean isInsMultiplo = false;
	Boolean isUnitaMisura = false;
	istanzeattivita.setIsInsMultiplo(isInsMultiplo);
	istanzeattivita.setIsStep2(isStep2);
	istanzeattivita.setIsUnitaMisura(isUnitaMisura);
	model.addAttribute("istanzeattivita", istanzeattivita);
	setPageAttributes(model);
	return "istanzeattivita/form";
    }

    /*
     * Ritorna il form per l'inserimento della o delle attività
     */
    @RequestMapping
    public String createStep2(@ModelAttribute("istanzeattivita") IstanzeattivitaCommand istanzeattivita,
	    @RequestParam("codiceSettore") String codiceSettore, Model model, HttpServletRequest request, HttpServletResponse response) {

	istanzeattivita.setDisplayMode(IstanzeattivitaCommand.NEW);
	fixRenderEntityProperty(istanzeattivita.getEntity());
	istanzeattivita.setIsStep2(true);
	Settori settori = settoriService.findById(new SettoriId(codiceSettore));
	istanzeattivita.setSettori(settori);
	Istanze istanza = istanzeattivita.getIstanza();
	Boolean isUnitaMisura = false;
	if (settori.getTipiunitamisura() != null) {
	    isUnitaMisura = true;
	}
	istanzeattivita.setIsUnitaMisura(isUnitaMisura);
	Boolean isInsMultiplo = false;
	if (BooleanUtils.isTrue(settori.getFlagInsmultiplo())) { //settori.getFlagInsmultiplo()
	    isInsMultiplo = true;
	    /*
	     * Viene utilizzata la List istAttSettoreList per memorizzare tutte le istanzeattivita del settore scelto precedentemente.
	     * Viene utilizzata la Map attivitaPresentiMap per memorizzare le attivita del settore che sono già presenti nell'istanza 
	     * (nel caso in cui il settore presenti tipiunitamisura==null).
	     * Viene utilizzata la Map attivitaPresentiUMMap per memorizzare i metriq delle attivita del settore che sono già presenti 
	     * nell'istanza (nel caso in cui il settore presenti tipiunitamisura!=null).
	     */
	    Map<String, Boolean> attivitaPresentiMap = new HashMap<String, Boolean>();
	    Map<String, BigDecimal> attivitaPresentiUMMap = new HashMap<String, BigDecimal>();
	    Boolean orderByCodiceistat = Boolean.FALSE;
	    if (leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT, "1", request).equals("1")) {
		orderByCodiceistat = Boolean.TRUE;
	    }
	    List<Istanzeattivita> istanzeattivitaPresentiList = istanzeattivitaService.findByIstanza(istanza, orderByCodiceistat);
	    List<Istanzeattivita> istAttSettoreList = new ArrayList<Istanzeattivita>();
	    String orderByProp = "istat";
	    if (orderByCodiceistat) {
		orderByProp = "id.codiceistat";
	    }
	    List<Attivita> attivitas = attivitaService.findAttivitaBySettore(codiceSettore, orderByProp);
	    for (Attivita attivitaTemp : attivitas) {
		Istanzeattivita istanzeattivitaTemp = new Istanzeattivita();
		istanzeattivitaTemp.setAttivita(attivitaTemp);
		istanzeattivitaTemp.setIstanza(istanza);
		istAttSettoreList.add(istanzeattivitaTemp);
		Boolean presente = false;
		for (Istanzeattivita istanzeattivita2 : istanzeattivitaPresentiList) {
		    if (attivitaTemp.equals(istanzeattivita2.getAttivita())) {
			presente = true;
		    }
		    attivitaPresentiMap.put(istanzeattivitaTemp.getAttivita().getId().getCodiceistat(), presente);
		}
		BigDecimal metriQ = new BigDecimal(0);
		for (Istanzeattivita istanzeattivita2 : istanzeattivitaPresentiList) {
		    if (attivitaTemp.equals(istanzeattivita2.getAttivita())) {
			metriQ = istanzeattivita2.getMetriq();
		    }
		    attivitaPresentiUMMap.put(istanzeattivitaTemp.getAttivita().getId().getCodiceistat(), metriQ);
		}
	    }
	    istanzeattivita.setAttivitaPresentiMap(attivitaPresentiMap);
	    istanzeattivita.setAttivitaPresentiUMMap(attivitaPresentiUMMap);
	    istanzeattivita.setIstAttSettoreList(istAttSettoreList);
	} else {
	    // Inserimento singolo
	    istanzeattivita.getEntity().getAttivita().setSettori(settori);
	}
	istanzeattivita.setIsInsMultiplo(isInsMultiplo);
	model.addAttribute("istanzeattivita", istanzeattivita);
	setPageAttributes(model, request, response);
	return "istanzeattivita/form";
    }

    @RequestMapping
    public String insert(@ModelAttribute("istanzeattivita") IstanzeattivitaCommand istanzeattivita, BindingResult result, SessionStatus status) {

	Istanze istanza = istanzeattivita.getIstanza();
	checkAccessoInformazioni(istanza, true);
	Set<Istanzeattivita> istanzeattivitas = new HashSet<Istanzeattivita>();
	// inserimento multiplo
	if (BooleanUtils.isTrue(istanzeattivita.getIsInsMultiplo())) {
	    if (BooleanUtils.isFalse(istanzeattivita.getIsUnitaMisura())) {
		// Checkbox
		Iterator<Entry<String, Boolean>> it = istanzeattivita.getAttivitaPresentiMap().entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Boolean> pairs = it.next();
		    // Verifico che l'attivita sia checked e che non sia già presente e la inserisco in istanzeattivita	        
		    if (pairs.getValue()) {
			List<Istanzeattivita> istanzeattivitaOld = istanzeattivitaService.findByIstanza(istanza, Boolean.FALSE);
			Boolean trovata = false;
			for (Istanzeattivita istanzeattivita2 : istanzeattivitaOld) {
			    if (istanzeattivita2.getAttivita().getId().getCodiceistat().equals(pairs.getKey())) {
				trovata = true;
			    }
			}
			if (!trovata) {
			    Istanzeattivita istanzeattivitaTemp = new Istanzeattivita();
			    AttivitaId attivitaId = new AttivitaId(pairs.getKey());
			    Attivita attivita = attivitaService.findById(attivitaId);
			    istanzeattivitaTemp.setAttivita(attivita);
			    if (StringUtils.isNotBlank(attivita.getNote())) {
				istanzeattivitaTemp.setNote(attivita.getNote());
			    }
			    istanzeattivitas.add(istanzeattivitaTemp);
			}
		    }
		}
	    } else {
		// UnitaMisura
		Iterator<Entry<String, BigDecimal>> it = istanzeattivita.getAttivitaPresentiUMMap().entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, BigDecimal> pairs = it.next();
		    // Verifico che l'attivita sia stata modificata (inserimento nel campo metriq) e che non sia già presente e la inserisco in istanzeattivita	        
		    if (pairs.getValue() != null) {
			List<Istanzeattivita> istanzeattivitaOld = istanzeattivitaService.findByIstanza(istanza, Boolean.FALSE);
			Boolean trovata = false;
			for (Istanzeattivita istanzeattivita2 : istanzeattivitaOld) {
			    if (istanzeattivita2.getAttivita().getId().getCodiceistat().equals(pairs.getKey())) {
				trovata = true;
			    }
			}
			if (!trovata) {
			    Istanzeattivita istanzeattivitaTemp = new Istanzeattivita();
			    AttivitaId attivitaId = new AttivitaId(pairs.getKey());
			    Attivita attivita = attivitaService.findById(attivitaId);
			    istanzeattivitaTemp.setAttivita(attivita);
			    if (StringUtils.isNotBlank(attivita.getNote())) {
				istanzeattivitaTemp.setNote(attivita.getNote());
			    }
			    istanzeattivitaTemp.setMetriq(pairs.getValue());
			    istanzeattivitas.add(istanzeattivitaTemp);
			}
		    }
		}
	    }
	} else {
	    // inserimento singolo
	    istanzeattivitas.add(istanzeattivita.getEntity());
	}
	try {
	    for (Istanzeattivita istanzeattivitaTemp : istanzeattivitas) {
		istanzeattivitaTemp.setIstanza(istanzeattivita.getIstanza());
	    }
	    istanzeattivitaService.insertIstanzeattivitaSet(istanzeattivitas);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeattivita.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeattivita.getEntity());
	    istanzeattivita.setDisplayMode(DocumentiistanzaCommand.NEW);
	    return "istanzeattivita/form";
	}
	status.setComplete();
	if (istanzeattivita.getIsInsMultiplo()) {
	    return "redirect:list.htm?codiceIstanza=" + istanzeattivita.getIstanza().getId().getCodice();
	} else {
	    return "redirect:view.htm?codice=" + istanzeattivita.getEntity().getId().getCodice() + "&status_msg=01";
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Istanzeattivita entity = istanzeattivitaService.findById(id);
	checkAccessoInformazioni(entity.getIstanza(), false);
	IstanzeattivitaCommand istanzeattivita = new IstanzeattivitaCommand();
	istanzeattivita.setDisplayMode(DocumentiistanzaCommand.VIEW);
	if (EntityUtils.getNestedProperty(entity.getAttivita(), "settori.tipiunitamisura.id.codice") != null) { // entity.getAttivita().getSettori().getTipiunitamisura() != null
	    istanzeattivita.setIsUnitaMisura(Boolean.TRUE);
	}
	fixRenderEntityProperty(entity);
	istanzeattivita.setEntity(entity);
	istanzeattivita.setSettori(entity.getAttivita().getSettori());
	istanzeattivita.setIstanza(entity.getIstanza());
	model.addAttribute("istanzeattivita", istanzeattivita);
	setPageAttributes(model);
	return "istanzeattivita/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeattivita") IstanzeattivitaCommand istanzeattivita, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(istanzeattivita.getEntity());
	checkAccessoInformazioni(istanzeattivita.getEntity().getIstanza(), true);
	try {
	    istanzeattivitaService.update(istanzeattivita.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeattivita.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeattivita.getEntity());
	    return "istanzeattivita/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeattivita.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeattivita") IstanzeattivitaCommand istanzeattivita, BindingResult result, SessionStatus status) {

	Istanzeattivita objToDelete = istanzeattivitaService.findById(istanzeattivita.getEntity().getId());
	checkAccessoInformazioni(objToDelete.getIstanza(), true);
	try {
	    istanzeattivitaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(istanzeattivita.getEntity());
	    return "istanzeattivita/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + istanzeattivita.getIstanza().getId().getCodice();
    }

    @RequestMapping
    public String eliminaIstanzeattivita(@RequestParam("codice") Integer codiceIstanzeattivita,
	    @ModelAttribute("istanzeattivita") IstanzeattivitaCommand istanzeattivita, BindingResult result, SessionStatus status) {

	Istanzeattivita objToDelete = istanzeattivitaService.findById(new PkId(codiceIstanzeattivita));
	Istanze istanza = istanzeattivita.getIstanza();
	checkAccessoInformazioni(istanza, true);
	try {
	    istanzeattivitaService.delete(objToDelete);
	} catch (Exception e) {
	    // FIXME
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(objToDelete);
	    return "redirect:list.htm?codiceIstanza=" + istanza.getId().getCodice() + "&status_msg=03";
	}
	return "redirect:list.htm?codiceIstanza=" + istanza.getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeattivita entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeattivita entity) {

	if (entity.getAttivita() == null) {
	    entity.setAttivita(new Attivita());
	}
	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private void setPageAttributes(Model model, HttpServletRequest request, HttpServletResponse response) {

	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA, "0", request);
	if (leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT, "1", request).equals("0")
		&& leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT, "1", request).equals("0")) {
	    salvaPreferenza(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT, "1", request, response);
	}
	model.addAttribute("CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT, "1", request)));
	model.addAttribute("CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_CODICE_ISTAT, "1", request));
	model.addAttribute("CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT, "1", request)));
	model.addAttribute("CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_VISUALIZZA_ISTAT, "1", request));
	model.addAttribute("CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT, "1", request)));
	model.addAttribute("CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_IST_ATT_ORDER_CODICEISTAT, "1", request));
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }
}
