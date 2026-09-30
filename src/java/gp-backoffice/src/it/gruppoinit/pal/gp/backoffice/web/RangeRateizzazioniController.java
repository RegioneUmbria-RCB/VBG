package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.Rateizzazioni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

@Controller
@SessionAttributes(value = { "rateizzazioni", "listtipirateizzazioni", "rangeRateizzazioni" })
public class RangeRateizzazioniController extends BaseController<Rateizzazioni> {

    private static final String RATEIZZAZIONI_STRING = "rateizzazioni";
    private static final String QUERY_STRING_PARAMS = "queryStringParams";
    private static final String METHOD = "method";
    private static final String SOFTWARE_STRING = "software";
    private static final String VIEW_HTM = "view.htm";
    private static final String LISTTIPIRATEIZZAZIONI2 = "listtipirateizzazioni";
    private static final String ERROR = "error";
    private static final String COMMAND_NAME = "commandName";
    private static final String RANGE_RATEIZZAZIONI_FORM = "rangerateizzazioni/form";
    private static final Logger log = LoggerFactory.getLogger(RangeRateizzazioniController.class);
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private SoftwareService softwareService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<RangeRateizzazioni> rangeRateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	ModelMap model = new ModelMap(rangeRateizzazioniList);
	Software software = softwareService.findById(ORMHelper.getSoftware());
	model.addAttribute("rangeRateizzazioniList", rangeRateizzazioniList);
	model.addAttribute(SOFTWARE_STRING, software);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute(RATEIZZAZIONI_STRING) Rateizzazioni rateizzazioni, BindingResult result, SessionStatus status) {

	List<RangeRateizzazioni> listrangerateizzazioni = rateizzazioni.getRangerateizzazioniList();
	for (RangeRateizzazioni rangeRateizzazioni : listrangerateizzazioni) {
	    try {
		rangeRateizzazioniService.delete(rangeRateizzazioni);
	    } catch (Exception e) {
		copyErrorsToBindingResult(result, rangeRateizzazioni, e);
		log.error(e.getMessage());
		Map<String, String> map = new HashMap<String, String>();
		map.put(SOFTWARE_STRING, ORMHelper.getSoftware());
		model.addAttribute(COMMAND_NAME, RATEIZZAZIONI_STRING);
		model.addAttribute(METHOD, VIEW_HTM);
		model.addAttribute(QUERY_STRING_PARAMS, map);
		return ERROR;
	    }
	}
	if (result.hasErrors()) {
	    fixRenderEntityProperty(rateizzazioni);
	    return RANGE_RATEIZZAZIONI_FORM;
	}
	status.setComplete();
	return "redirect:../mercaticonfigurazione/view.htm?software=" + ORMHelper.getSoftware();
    }

    @RequestMapping
    public String insert(@ModelAttribute(RATEIZZAZIONI_STRING) Rateizzazioni rateizzazioni, BindingResult result, SessionStatus status, Model model) {

	List<RangeRateizzazioni> listrangerateizzazioni = rateizzazioni.getRangerateizzazioniList();
	rateizzazioni.setRangerateizzazioniList(listrangerateizzazioni);
	fixMergeEntityProperty(rateizzazioni);
	if (!validate(listrangerateizzazioni, result) || result.hasErrors()) {
	    fixRenderEntityProperty(rateizzazioni);
	    List<Oneritipirateizzazione> listtipirateizzazioni = oneritipirateizzazioneService.findAll(null, null);
	    fixRenderEntityProperty(rateizzazioni);
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    model.addAttribute(SOFTWARE_STRING, software);
	    model.addAttribute(RATEIZZAZIONI_STRING, rateizzazioni);
	    model.addAttribute(LISTTIPIRATEIZZAZIONI2, listtipirateizzazioni);
	    return RANGE_RATEIZZAZIONI_FORM;
	}
	// inserisce nel data base una riga alla volta contrlollando che non
	// abbia tipo registrazioni null
	for (RangeRateizzazioni rangeRateizzazioni : listrangerateizzazioni) {
	    rangeRateizzazioni.setTiporateizzazione(
		    oneritipirateizzazioneService.findById(new PkId(rangeRateizzazioni.getTiporateizzazione().getId().getCodice())));
	    rangeRateizzazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	    if (rangeRateizzazioni.getTiporateizzazione() != null) {
		try {
		    rangeRateizzazioniService.insert(rangeRateizzazioni);
		} catch (Exception e) {
		    copyErrorsToBindingResult(result, rangeRateizzazioni, e);
		    log.error(e.getMessage());
		    Map<String, String> map = new HashMap<String, String>();
		    map.put(SOFTWARE_STRING, ORMHelper.getSoftware());
		    model.addAttribute(COMMAND_NAME, RATEIZZAZIONI_STRING);
		    model.addAttribute(METHOD, VIEW_HTM);
		    model.addAttribute(QUERY_STRING_PARAMS, map);
		    return ERROR;
		}
	    }
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute(RATEIZZAZIONI_STRING) Rateizzazioni rateizzazioni, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	List<RangeRateizzazioni> listrangerateizzazioni = rateizzazioni.getRangerateizzazioniList();
	fixMergeEntityProperty(rateizzazioni);
	if (!validate(listrangerateizzazioni, result) || result.hasErrors()) {
	    // estraggo la lista dei tipi di rateizzazione da mostrare sulla
	    // maschera di inserimento
	    List<Oneritipirateizzazione> listtipirateizzazioni = oneritipirateizzazioneService.findAllSenzaInteressiLegali();
	    // inserisco tanti campi fino ad arrivare al numero richiesto 4
	    while (rateizzazioni.getRangerateizzazioniList().size() < 4) {
		RangeRateizzazioni rangeRateizzazioni = new RangeRateizzazioni();
		rateizzazioni.getRangerateizzazioniList().add(rangeRateizzazioni);
	    }
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    model.addAttribute(SOFTWARE_STRING, software);
	    model.addAttribute(RATEIZZAZIONI_STRING, rateizzazioni);
	    model.addAttribute(LISTTIPIRATEIZZAZIONI2, listtipirateizzazioni);
	    return RANGE_RATEIZZAZIONI_FORM;
	}
	// pulisco il database in quanto c'è la possibilità che che nella
	// modifica vengano tolte delle
	// rateizzazioni
	List<RangeRateizzazioni> oldRangerateizzazioni = rangeRateizzazioniService.findAll(null, null);
	for (RangeRateizzazioni rangeRateizzazioni : oldRangerateizzazioni) {
	    try {
		rangeRateizzazioniService.delete(rangeRateizzazioni);
	    } catch (Exception e) {
		copyErrorsToBindingResult(result, rangeRateizzazioni, e);
		log.error(e.getMessage());
		Map<String, String> map = new HashMap<String, String>();
		map.put(SOFTWARE_STRING, ORMHelper.getSoftware());
		model.addAttribute(COMMAND_NAME, RATEIZZAZIONI_STRING);
		model.addAttribute(METHOD, VIEW_HTM);
		model.addAttribute(QUERY_STRING_PARAMS, map);
		return ERROR;
	    }
	}
	// inserisce nel data base una riga alla volta contrlollando che non
	// abbia tipo registrazioni null
	for (RangeRateizzazioni rangeRateizzazioni : listrangerateizzazioni) {
	    rangeRateizzazioni.setTiporateizzazione(
		    oneritipirateizzazioneService.findById(new PkId(rangeRateizzazioni.getTiporateizzazione().getId().getCodice())));
	    rangeRateizzazioni.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	    if (rangeRateizzazioni.getTiporateizzazione() != null) {
		try {
		    rangeRateizzazioniService.update(rangeRateizzazioni);
		} catch (Exception e) {
		    copyErrorsToBindingResult(result, rangeRateizzazioni, e);
		    log.error(e.getMessage());
		    Map<String, String> map = new HashMap<String, String>();
		    map.put(SOFTWARE_STRING, ORMHelper.getSoftware());
		    model.addAttribute(COMMAND_NAME, RATEIZZAZIONI_STRING);
		    model.addAttribute(METHOD, VIEW_HTM);
		    model.addAttribute(QUERY_STRING_PARAMS, map);
		    return ERROR;
		}
	    }
	}
	status.setComplete();
	return "redirect:view.htm?status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	// creo L'oggetto rateizzazioni e setto 4 campi di range_rateizzaioni
	Rateizzazioni rateizzazioni = new Rateizzazioni();
	RangeRateizzazioni rangeRateizzazioni1 = new RangeRateizzazioni();
	RangeRateizzazioni rangeRateizzazioni2 = new RangeRateizzazioni();
	RangeRateizzazioni rangeRateizzazioni3 = new RangeRateizzazioni();
	RangeRateizzazioni rangeRateizzazioni4 = new RangeRateizzazioni();
	// setto il range basso del primo campo di rateizzazione per default = 0
	rangeRateizzazioni1.setRangeBasso(0);
	List<RangeRateizzazioni> list = new ArrayList<RangeRateizzazioni>();
	list.add(rangeRateizzazioni1);
	list.add(rangeRateizzazioni2);
	list.add(rangeRateizzazioni3);
	list.add(rangeRateizzazioni4);
	rateizzazioni.setRangerateizzazioniList(list);
	// estraggo la lista dei tipi di rateizzazione da mostrare sulla
	// maschera di inserimento
	List<Oneritipirateizzazione> listtipirateizzazioni = oneritipirateizzazioneService.findAllSenzaInteressiLegali();
	fixRenderEntityProperty(rateizzazioni);
	Software software = softwareService.findById(ORMHelper.getSoftware());
	model.addAttribute(SOFTWARE_STRING, software);
	model.addAttribute(RATEIZZAZIONI_STRING, rateizzazioni);
	model.addAttribute(LISTTIPIRATEIZZAZIONI2, listtipirateizzazioni);
	setPageAttributes(model);
	return RANGE_RATEIZZAZIONI_FORM;
    }

    @RequestMapping
    public String view(Model model, HttpServletRequest request) {

	List<Oneritipirateizzazione> listtipirateizzazioni = oneritipirateizzazioneService.findAllSenzaInteressiLegali();
	List<RangeRateizzazioni> list = rangeRateizzazioniService.findAll(null, null);
	Rateizzazioni rateizzazioni = new Rateizzazioni();
	while (list.size() < 4) {
	    RangeRateizzazioni rangeRateizzazioni = new RangeRateizzazioni();
	    list.add(rangeRateizzazioni);
	}
	rateizzazioni.setRangerateizzazioniList(list);
	fixRenderEntityProperty(rateizzazioni);
	Software software = softwareService.findById(ORMHelper.getSoftware());
	model.addAttribute(SOFTWARE_STRING, software);
	model.addAttribute(RATEIZZAZIONI_STRING, rateizzazioni);
	model.addAttribute(LISTTIPIRATEIZZAZIONI2, listtipirateizzazioni);
	setPageAttributes(model);
	return RANGE_RATEIZZAZIONI_FORM;
    }

    @Override
    protected void fixMergeEntityProperty(Rateizzazioni entity) {

	// Metodo non implementato
    }

    @Override
    protected void fixRenderEntityProperty(Rateizzazioni entity) {

	// Metodo non implementato
    }

    @Override
    protected void setPageAttributes(Model model) {

	// Metodo non implementato
    }

    // -------------------------VALIDAZIONE----------------------------------
    // ----------------------------START------------------------------------
    protected boolean validate(List<RangeRateizzazioni> listrangerateizzazioni, BindingResult result) {

	boolean success = true;
	String rangeAlto = "].rangeAlto";
	String rangeRateizzazioniList = "rangerateizzazioniList[";
	String validatorMessage = "validator.parametrinoncorretti";
	for (int i = 0; i < listrangerateizzazioni.size(); i++) {
	    RangeRateizzazioni rangeRateizzazioniAttuale = listrangerateizzazioni.get(i);
	    // Controllo per non inserire numero negativo
	    if ((rangeRateizzazioniAttuale.getRangeBasso() != null && rangeRateizzazioniAttuale.getRangeBasso() < 0)
		    || (rangeRateizzazioniAttuale.getRangeAlto() != null && rangeRateizzazioniAttuale.getRangeAlto() < 0)) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		success = false;
	    }
	    // Controllo sul mancato inserimento di un tipo di rateizzazione o
	    // sul mancato inserimento dei range con tipo di rateizzazione
	    // selezionata
	    if (((rangeRateizzazioniAttuale.getRangeBasso() != null || rangeRateizzazioniAttuale.getRangeAlto() != null)
		    && rangeRateizzazioniAttuale.getTiporateizzazione().getId().getCodice() == null)
		    || ((rangeRateizzazioniAttuale.getRangeBasso() == null && rangeRateizzazioniAttuale.getRangeAlto() == null)
			    && rangeRateizzazioniAttuale.getTiporateizzazione().getId().getCodice() != null)) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		success = false;
	    }
	    // controllo che il range attuale basso deve essere maggiore del
	    // range alto attuale
	    if ((rangeRateizzazioniAttuale.getRangeAlto() != null && rangeRateizzazioniAttuale.getRangeBasso() != null)
		    && rangeRateizzazioniAttuale.getRangeBasso() >= rangeRateizzazioniAttuale.getRangeAlto()) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		success = false;
	    }
	    // controlla che il range dell'ultima rateizzazione deve essere vuoto
	    if ((rangeRateizzazioniAttuale.getRangeAlto() != null && rangeRateizzazioniAttuale.getRangeBasso() == null)) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		success = false;
	    }
	    if (i > 0) {
		RangeRateizzazioni rangeRateizzazioniPrecendente = listrangerateizzazioni.get(i - 1);
		if (rangeRateizzazioniPrecendente.getRangeAlto() == null
			&& (rangeRateizzazioniAttuale.getRangeBasso() != null || rangeRateizzazioniAttuale.getRangeAlto() != null)) {
		    verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		    success = false;
		}
		// controllo affinche il range alto del precedente sia uguale al
		// range basso del successivo
		if ((rangeRateizzazioniAttuale.getRangeBasso() != null && rangeRateizzazioniPrecendente.getRangeAlto() != null)
			&& rangeRateizzazioniAttuale.getRangeBasso().compareTo(rangeRateizzazioniPrecendente.getRangeAlto()) != 0) {
		    verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		    success = false;
		}
	    }
	    if (i == listrangerateizzazioni.size() - 1 && rangeRateizzazioniAttuale.getRangeAlto() != null) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i);
		success = false;
	    }
	}
	// controllo affinche il range alto dell'ultimo campo sia vuoto
	for (int i = 1; i < listrangerateizzazioni.size(); i++) {
	    RangeRateizzazioni rangeRateizzazioniAttuale = listrangerateizzazioni.get(i);
	    RangeRateizzazioni rangeRateizzazioniPrecedente = listrangerateizzazioni.get(i - 1);
	    if (listrangerateizzazioni.size() == 1 && rangeRateizzazioniPrecedente.getRangeAlto() != null) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i - 1);
		success = false;
		break;
	    }
	    if ((rangeRateizzazioniAttuale.getRangeBasso() == null && rangeRateizzazioniAttuale.getRangeAlto() == null)
		    && (rangeRateizzazioniPrecedente.getRangeBasso() != null && rangeRateizzazioniPrecedente.getRangeAlto() != null)) {
		verificaSeErrorePresente(result, rangeAlto, rangeRateizzazioniList, validatorMessage, i - 1);
		success = false;
		break;
	    }
	}
	return success;
    }
    // ----------------------------------------END--------------------------------
    // ----------

    private void verificaSeErrorePresente(BindingResult result, String rangeAlto, String rangeRateizzazioniList, String validatorMessage, int i) {

	if (result.getFieldErrors(rangeRateizzazioniList + (i) + rangeAlto).isEmpty()) {
	    result.rejectValue(rangeRateizzazioniList + i + rangeAlto, validatorMessage);
	}
    }
}
