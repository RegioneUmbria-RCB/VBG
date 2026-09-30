package it.gruppoinit.pal.gp.backoffice.web;

import it.cassaedileweb.serviziodurc.insert.in.DURCInsertRequest;
import it.cassaedileweb.serviziodurc.insert.in.Institute;
import it.cassaedileweb.serviziodurc.insert.in.Office;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Elenchiprofessionalibase;
import it.gruppoinit.pal.gp.core.domain.Elencocassaedilebase;
import it.gruppoinit.pal.gp.core.domain.Elencoinailbase;
import it.gruppoinit.pal.gp.core.domain.Elencoinpsbase;
import it.gruppoinit.pal.gp.core.domain.Emailanagr;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.Formegiuridiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Scadenze;
import it.gruppoinit.pal.gp.core.domain.Scadenzecategoriebase;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.Titoli;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeCommand;
import it.gruppoinit.pal.gp.core.jmesa.AnagrafeTable;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ElenchiprofessionalibaseService;
import it.gruppoinit.pal.gp.core.service.EmailanagrService;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.service.FormegiuridicheService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ScadenzeService;
import it.gruppoinit.pal.gp.core.service.ScadenzecategoriebaseService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.service.TitoliService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "anagrafe", "nuovoDurcHelper" })
public class AnagrafeController extends BaseController<Anagrafe> {

    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private FoRichiesteService foRichiesteService;
    @Autowired
    private FormegiuridicheService formegiuridicheService;
    @Autowired
    private TitoliService titoliService;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private ScadenzeService scadenzeService;
    @Autowired
    private EmailanagrService emailanagrService;
    //    @Autowired
    //    private AnagrafedocumentiService anagrafedocumentiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private TipidocumentoService tipidocumentoService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private ElenchiprofessionalibaseService elenchiprofessionalibaseService;
    @Autowired
    private ScadenzecategoriebaseService scadenzecategoriebaseService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    private static String PASSWORD_SETTATA = "1";
    private static String PASSWORD_NON_SETTATA = "0";

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	GenerateTable<Anagrafe> anagrafeTable = new AnagrafeTable();
	String htmlTable = anagrafeTable.createJMesaList(request, response, "anagrafe.label.lista_anagrafe.title", "anagrafe_id", true);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	return "anagrafe/list";
    }

    //    @RequestMapping
    //    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {
    //
    //	List<Anagrafe> anagrafeList = anagrafeService.findAll(null, null);
    //	ModelMap model = new ModelMap(anagrafeList);
    //	boolean export = createJMesaExport(request, response, anagrafeList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("anagrafeList", anagrafeList);
    //	return model;
    //    }
    @RequestMapping
    public String popupcreate(Model model, @RequestParam(value = "tiposoggetto", required = false) String tiposoggetto,
	    @RequestParam(value = "popupCaller") String popupCaller,
	    @RequestParam(value = "visualizzaTipoAnagrafe", required = false) String visualizzaTipoAnagrafe, HttpServletRequest request) {

	return create(model, Boolean.TRUE, popupCaller, tiposoggetto, visualizzaTipoAnagrafe, request);
    }

    @RequestMapping
    public String create(Model model, @RequestParam(value = "popup", required = false) Boolean popup,
	    @RequestParam(value = "popupCaller", required = false) String popupCaller,
	    @RequestParam(value = "tiposoggetto", required = false) String tiposoggetto,
	    @RequestParam(value = "visualizzaTipoAnagrafe", required = false) String visualizzaTipoAnagrafe, HttpServletRequest request) {

	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setPopup(BooleanUtils.toBoolean(popup));
	anagrafe.setPopupCaller(popupCaller);
	anagrafe.setVisualizzaTipoAnagrafe(visualizzaTipoAnagrafe);
	Anagrafe entity = new Anagrafe();
	anagrafe = gestioneVisualizzazioneSezioneTipoAnagrafe(popupCaller, visualizzaTipoAnagrafe, tiposoggetto, anagrafe, entity);
	Anagrafe oldAnagrafe = new Anagrafe();
	anagrafe.setEntity(entity);
	anagrafe.setOldAnagrafe(oldAnagrafe);
	setPageAttributes(model, anagrafe, request);
	fixRenderEntityProperty(anagrafe.getEntity());
	model.addAttribute("anagrafe", anagrafe);
	// model.addAttribute("configurazione", configurazione);
	return "anagrafe/form";
    }

    private AnagrafeCommand gestioneVisualizzazioneSezioneTipoAnagrafe(String popupCaller, String visualizzaTipoAnagrafe, String tiposoggetto,
	    AnagrafeCommand anagrafe, Anagrafe entity) {

	//Funzionalità richiamata da un popup (Inserimento anagrafica da funzionalità esterna ES. creazione istanza)
	if (StringUtils.isNotBlank(popupCaller)) {
	    // visualizzaTipoAnagrafe!=null allora vogliamo obbligare la scelta a PERSONA GIURIDICA o FISICA
	    if (StringUtils.isNotBlank(visualizzaTipoAnagrafe)) {
		// visualizzaTipoAnagrafe=G allora diamo come scelta obligata la PERSONA_GIURIDICA
		if (visualizzaTipoAnagrafe.equals(WebConstants.PERSONA_GIURIDICA)) {
		    entity.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		} else { //// visualizzaTipoAnagrafe=F allora diamo come scelta obligata la PERSONA_FISICA
		    entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		}
	    } else {
		// se visualizzaTipoAnagrafe==null visualizziamo entrambe e la scelta di quella da
		// proporre è data dal valore di tiposoggetto
		if (visualizzaTipoAnagrafe == null) {
		    anagrafe.setVisualizzaTipoAnagrafe("");
		    if (tiposoggetto.equals(WebConstants.PERSONA_GIURIDICA)) {
			entity.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		    } else {
			entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		    }
		    //Se visualizzaTipoAnagrafe=='' allora siamo nella situazione di default visualizziamo entrambe
		    // con default PERSONA_FISICA
		} else {
		    anagrafe.setVisualizzaTipoAnagrafe("");
		    entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
		}
	    }
	} else {
	    // La richiesta nn è stata fatta da una funzione esterna, ma dalla gestione delle anagrafiche
	    anagrafe.setPopupCaller("");
	    if (StringUtils.isNotBlank(tiposoggetto)) {
		entity.setTipoanagrafe(tiposoggetto);
	    } else {
		entity.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	    }
	}
	anagrafe.setEntity(entity);
	return anagrafe;
    }

    @RequestMapping
    public String popupinsert(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return insert(model, anagrafe, result, status, request);
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupera tutti i campa ajax se passati
	Anagrafe entity = anagrafe.getEntity();
	entity = getAjaxField(entity, request);
	anagrafe.setEntity(entity);
	anagrafe.setOldAnagrafe(entity);
	fixMergeEntityProperty(anagrafe.getEntity());
	try {
	    anagrafeService.insert(anagrafe.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getEntity(), true, e);
	    setPageAttributes(model, anagrafe, request);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    return "anagrafe/form";
	}
	if (BooleanUtils.isTrue(anagrafe.getPopup())) {
	    return "redirect:" + popupcloseRedirect(anagrafe.getEntity().getId().getCodice(), "01", anagrafe.getPopupCaller());
	} else {
	    status.setComplete();
	    return "redirect:view.htm?codice=" + anagrafe.getEntity().getId().getCodice() + "&status_msg=01";
	}
    }

    private String popupcloseRedirect(Integer codiceAnagrafe, String status_msg, String popupCaller) {

	// return "popupclose.htm?codice=" + codiceAnagrafe + "&popup=true&status_msg=" + status_msg + "&popupCaller=" + popupCaller;
	return "popupview.htm?codice=" + codiceAnagrafe + "&popup=true&status_msg=" + status_msg + "&popupCaller=" + popupCaller + "&done=true";
    }

    @RequestMapping
    public String popupview(@RequestParam("codice") Integer codice, @RequestParam(value = "popupCaller") String popupCaller, Model model,
	    HttpServletRequest request) {

	return view(codice, true, popupCaller, model, request);
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam(value = "popup", required = false) Boolean popup,
	    @RequestParam(value = "popupCaller", required = false) String popupCaller, Model model, HttpServletRequest request) {

	// verifico se è attiva la verticalizzazione ANGRAFE con il parametro "ANAGRAFE_PG_TO_PF"==S
	boolean isConversioneAnagrafica = false;
	boolean isVerticalizzazioneAnagrafeAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ANAGRAFE);
	if (isVerticalizzazioneAnagrafeAttiva) {
	    Verticalizzazioniparametri verticalizzazioniparametroANAGRAFE_PG_TO_PF = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_ANAGRAFE, WebConstants.ANAGRAFE_PG_TO_PF);
	    if (verticalizzazioniparametroANAGRAFE_PG_TO_PF != null && verticalizzazioniparametroANAGRAFE_PG_TO_PF.getValore().equals(WebConstants.S)) {
		isConversioneAnagrafica = true;
	    }
	}
	PkId id = new PkId(codice);
	Anagrafe entity = anagrafeService.findById(id);
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setPopup(BooleanUtils.toBoolean(popup));
	anagrafe.setPopupCaller(popupCaller);
	anagrafe.setEntity(entity);
	anagrafe.setOldAnagrafe(entity);
	setPageAttributes(model, anagrafe, request);
	fixRenderEntityProperty(anagrafe.getEntity());
	model.addAttribute("anagrafe", anagrafe);
	model.addAttribute("isConversioneAnagrafica", isConversioneAnagrafica);
	request.setAttribute("codiceAnagrafe", anagrafe.getEntity().getId().getCodice());
	// Nel caso di WS DURC attivato, in caso di chiamata da popup viene passato anche il 
	// codice istanza che servira per fare le chiamate verificaDurc e RichiediDurc
	if ((Integer) request.getAttribute("codiceIstanza") != null) {
	    model.addAttribute("codiceIstanza", (Integer) request.getAttribute("codiceIstanza"));
	}
	request.setAttribute("codiceAnagrafe", anagrafe.getEntity().getId().getCodice());
	return "anagrafe/form";
    }

    @RequestMapping
    public String popupupdate(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return update(model, anagrafe, result, status, request);
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupera tutti i campa ajax se passati
	Anagrafe entity = anagrafe.getEntity();
	entity = getAjaxField(entity, request);
	anagrafe.setEntity(entity);
	fixMergeEntityProperty(anagrafe.getEntity());
	try {
	    anagrafeService.update(anagrafe.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getEntity(), true, e);
	    setPageAttributes(model, anagrafe, request);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    return "anagrafe/form";
	}
	if (BooleanUtils.isTrue(anagrafe.getPopup())) {
	    return "redirect:" + popupcloseRedirect(anagrafe.getEntity().getId().getCodice(), "02", anagrafe.getPopupCaller());
	} else {
	    status.setComplete();
	    return "redirect:view.htm?codice=" + anagrafe.getEntity().getId().getCodice() + "&status_msg=02";
	}
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Anagrafe objToDelete = anagrafeService.findById(anagrafe.getEntity().getId());
	try {
	    anagrafeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    setPageAttributes(model, anagrafe, request);
	    return "anagrafe/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    // GESTIONE DELLE SCADENZE
    @RequestMapping
    public ModelMap listscadenze(@RequestParam("codiceanagrafe") Integer codiceanagrafe, HttpServletRequest request, HttpServletResponse response) {

	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceanagrafe));
	Set<Scadenze> scadenzeList = anagrafe.getScadenzes();
	ModelMap model = new ModelMap(scadenzeList);
	boolean export = createJMesaExport(request, response, scadenzeList);
	if (export) {
	    return null;
	}
	model.addAttribute("anagrafe", anagrafe);
	model.addAttribute("scadenzeList", scadenzeList);
	return model;
    }

    @RequestMapping
    public String createScadenze(Model model, @RequestParam("codiceanagrafe") Integer codiceanagrafe) {

	Anagrafe entity = anagrafeService.findById(new PkId(codiceanagrafe));
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setEntity(entity);
	Scadenze scadenze = new Scadenze();
	scadenze.setAnagrafe(entity);
	anagrafe.setScadenze(scadenze);
	fixRenderScadenzeProperty(anagrafe.getScadenze());
	List<Scadenzecategoriebase> listScadenzabase = scadenzecategoriebaseService.findAll(null, null);
	model.addAttribute("anagrafe", anagrafe);
	model.addAttribute("listScadenzabase", listScadenzabase);
	setPageAttributes(model);
	return "anagrafe/formScadenze";
    }

    @RequestMapping
    public String insertScadenze(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeScadenzeProperty(anagrafe.getScadenze());
	try {
	    scadenzeService.insert(anagrafe.getScadenze());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getScadenze(), true, "scadenze", e);
	    List<Scadenzecategoriebase> listScadenzabase = scadenzecategoriebaseService.findAll(null, null);
	    model.addAttribute("listScadenzabase", listScadenzabase);
	    fixRenderScadenzeProperty(anagrafe.getScadenze());
	    return "anagrafe/formScadenze";
	}
	status.setComplete();
	return "redirect:viewScadenze.htm?codice=" + anagrafe.getScadenze().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewScadenze(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Scadenze scadenze = scadenzeService.findById(id);
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setEntity(scadenze.getAnagrafe());
	anagrafe.setScadenze(scadenze);
	fixRenderScadenzeProperty(anagrafe.getScadenze());
	List<Scadenzecategoriebase> listScadenzabase = scadenzecategoriebaseService.findAll(null, null);
	model.addAttribute("listScadenzabase", listScadenzabase);
	model.addAttribute("anagrafe", anagrafe);
	setPageAttributes(model);
	return "anagrafe/formScadenze";
    }

    @RequestMapping
    public String updateScadenze(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeScadenzeProperty(anagrafe.getScadenze());
	try {
	    scadenzeService.update(anagrafe.getScadenze());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getScadenze(), true, "scadenze", e);
	    List<Scadenzecategoriebase> listScadenzabase = scadenzecategoriebaseService.findAll(null, null);
	    model.addAttribute("listScadenzabase", listScadenzabase);
	    fixRenderScadenzeProperty(anagrafe.getScadenze());
	    return "anagrafe/formScadenze";
	}
	status.setComplete();
	return "redirect:viewScadenze.htm?codice=" + anagrafe.getScadenze().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteScadenze(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status) {

	Scadenze objToDelete = scadenzeService.findById(anagrafe.getScadenze().getId());
	try {
	    scadenzeService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getScadenze(), true, "scadenze", e);
	    List<Scadenzecategoriebase> listScadenzabase = scadenzecategoriebaseService.findAll(null, null);
	    model.addAttribute("listScadenzabase", listScadenzabase);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    return "anagrafe/formScadenze";
	}
	status.setComplete();
	return "redirect:listscadenze.htm?codiceanagrafe=" + objToDelete.getAnagrafe().getId().getCodice();
    }

    // GESTIONE DELLE EMAIL
    @RequestMapping
    public ModelMap listemail(@RequestParam("codiceanagrafe") Integer codiceanagrafe, HttpServletRequest request, HttpServletResponse response) {

	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceanagrafe));
	Set<Emailanagr> emailanagrList = anagrafe.getEmailanagrs();
	ModelMap model = new ModelMap(emailanagrList);
	boolean export = createJMesaExport(request, response, emailanagrList);
	if (export) {
	    return null;
	}
	model.addAttribute("anagrafe", anagrafe);
	model.addAttribute("emailanagrList", emailanagrList);
	return model;
    }

    @RequestMapping
    public String viewEmail(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Emailanagr emailanagr = emailanagrService.findById(id);
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setEntity(emailanagr.getAnagrafe());
	anagrafe.setEmailanagr(emailanagr);
	fixRenderEmailanagrProperty(anagrafe.getEmailanagr());
	model.addAttribute("anagrafe", anagrafe);
	setPageAttributes(model);
	return "anagrafe/formEmail";
    }

    // GESTIONE DEI DOCUMENTI
    @RequestMapping
    public ModelMap listdocumenti(@RequestParam("codiceanagrafe") Integer codiceanagrafe, HttpServletRequest request, HttpServletResponse response) {

	//	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceanagrafe));
	//	Set<Anagrafedocumenti> anagrafedocumentiList = anagrafe.getAnagrafedocumentis();
	//	ModelMap model = new ModelMap(anagrafedocumentiList);
	//	boolean export = createJMesaExport(request, response, anagrafedocumentiList);
	//	if (export) {
	//	    return null;
	//	}
	//	boolean isWSDURC = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSDURC);
	//	model.addAttribute("isWSDURC", isWSDURC);
	//	model.addAttribute("anagrafe", anagrafe);
	//	model.addAttribute("anagrafedocumentiList", anagrafedocumentiList);
	//	Boolean isParixGate = verticalizzazioniService.isAttiva("WSANAGRAFE_PARIX");
	//	model.addAttribute("isParixGate", isParixGate);
	return null;
    }

    @RequestMapping
    public String insertDocumenti(@ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	//	// recupero campi ajax e l'oggetto passato
	//	Anagrafedocumenti anagrafedocumenti = anagrafe.getAnagrafedocumenti();
	//	if (anagrafedocumenti.getTipidocumento() != null && anagrafedocumenti.getTipidocumento().getId().getCodice() != null) {
	//	    Tipidocumento tipidocumento = tipidocumentoService.findById(anagrafe.getAnagrafedocumenti().getTipidocumento().getId());
	//	    anagrafedocumenti.setTipidocumento(tipidocumento);
	//	}
	//	if (anagrafedocumenti.getOggetto() != null && anagrafedocumenti.getOggetto().getId().getCodice() != null) {
	//	    Oggetti oggetto = oggettiService.findById(anagrafe.getAnagrafedocumenti().getOggetto().getId());
	//	    anagrafedocumenti.setOggetto(oggetto);
	//	}
	//	fixMergeAnagrafedocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	anagrafe.setAnagrafedocumenti(anagrafedocumenti);
	//	try {
	//	    anagrafedocumentiService.insert(anagrafe.getAnagrafedocumenti());
	//	} catch (Exception e) {
	//	    copyErrorsToBindingResult(result, anagrafe.getAnagrafedocumenti(), true, "anagrafedocumenti", e);
	//	    fixRenderAnagrafeDocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	    return "anagrafe/formDocumenti";
	//	}
	//	status.setComplete();
	return "redirect:viewDocumenti.htm?codice=";// + anagrafe.getAnagrafedocumenti().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewDocumenti(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	//
	//	PkId id = new PkId(codice);
	//	Anagrafedocumenti anagrafedocumenti = anagrafedocumentiService.findById(id);
	//	AnagrafeCommand anagrafe = new AnagrafeCommand();
	//	anagrafe.setEntity(anagrafedocumenti.getAnagrafe());
	//	anagrafe.setAnagrafedocumenti(anagrafedocumenti);
	//	fixRenderAnagrafeDocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	model.addAttribute("anagrafe", anagrafe);
	//	setPageAttributes(model);
	return "anagrafe/formDocumenti";
    }

    @RequestMapping
    public String updateDocumenti(@ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	//	// recupero campi ajax e l'oggetto passato
	//	Anagrafedocumenti anagrafedocumenti = anagrafe.getAnagrafedocumenti();
	//	if (anagrafedocumenti.getTipidocumento() != null && anagrafedocumenti.getTipidocumento().getId().getCodice() != null) {
	//	    Tipidocumento tipidocumento = tipidocumentoService.findById(anagrafe.getAnagrafedocumenti().getTipidocumento().getId());
	//	    anagrafedocumenti.setTipidocumento(tipidocumento);
	//	}
	//	if (anagrafedocumenti.getOggetto() != null && anagrafedocumenti.getOggetto().getId().getCodice() != null) {
	//	    Oggetti oggetto = oggettiService.findById(anagrafe.getAnagrafedocumenti().getOggetto().getId());
	//	    anagrafedocumenti.setOggetto(oggetto);
	//	}
	//	fixMergeAnagrafedocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	try {
	//	    anagrafedocumentiService.update(anagrafe.getAnagrafedocumenti());
	//	} catch (Exception e) {
	//	    copyErrorsToBindingResult(result, anagrafe.getAnagrafedocumenti(), true, "anagrafedocumenti", e);
	//	    fixRenderAnagrafeDocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	    return "anagrafe/formDocumenti";
	//	}
	//	status.setComplete();
	//	return "redirect:viewDocumenti.htm?codice=" + anagrafe.getAnagrafedocumenti().getId().getCodice() + "&status_msg=02";
	return "";
    }

    @RequestMapping
    public String deleteDocumenti(@ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status) {

	//	Anagrafedocumenti objToDelete = anagrafedocumentiService.findById(anagrafe.getAnagrafedocumenti().getId());
	//	try {
	//	    anagrafedocumentiService.delete(objToDelete);
	//	} catch (Exception e) {
	//	    copyErrorsToBindingResult(result, anagrafe.getAnagrafedocumenti(), true, "anagrafedocumenti", e);
	//	    fixRenderAnagrafeDocumentiProperty(anagrafe.getAnagrafedocumenti());
	//	    return "anagrafe/formDocumenti";
	//	}
	//	status.setComplete();
	//	return "redirect:listdocumenti.htm?codiceanagrafe=" + objToDelete.getAnagrafe().getId().getCodice();
	return "";
    }

    // GESTIONE DEI DOCUMENTI
    @RequestMapping
    public ModelMap listanagrafestorico(@RequestParam("codiceanagrafe") Integer codiceanagrafe, HttpServletRequest request,
	    HttpServletResponse response) {

	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceanagrafe));
	//Set<Anagrafestorico> anagrafestoricoList = anagrafe.getAnagrafestoricos();
	List<Anagrafestorico> anagrafestoricoList = anagrafeService.findStorico(anagrafe);
	ModelMap model = new ModelMap(anagrafestoricoList);
	boolean export = createJMesaExport(request, response, anagrafestoricoList);
	if (export) {
	    return null;
	}
	AnagrafeCommand anagrafeCommand = new AnagrafeCommand();
	anagrafeCommand.setEntity(anagrafe);
	model.addAttribute("anagrafe", anagrafeCommand);
	//model.addAttribute("codiceanagrafe", anagrafe.getId().getCodice());
	model.addAttribute("anagrafestoricoList", anagrafestoricoList);
	return model;
    }

    // GESTIONE FUNZIONALITà SUPPLEMENTARI DELLA GESTIONE ANAGRAFE:
    // 1- GENERAZIONE AUTOMATICA PASSWORD
    // 2- RESET DELLA PASSWORD SALVATA
    // 3- GENRAZIONE AUTOMATICA DEL CODICE FISCALE
    // 4- CONTROLLO AGGIORNAMENTI INFORMAZIONI ANAGRAFE
    @RequestMapping
    public void ajaxGeneraPassword(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	String password = Utilities.generaPassword(WebConstants.LUNGHEZZA_PASSWORD);
	Anagrafe entity = anagrafe.getEntity();
	entity.setPasswordClear(password);
	anagrafe.setEntity(entity);
	anagrafe.setOldAnagrafe(entity);
	model.addAttribute("anagrafe", anagrafe);
	setPageAttributes(model, anagrafe, request);
	try {
	    response.getWriter().write(password);
	} catch (IOException e) {
	    renderErrors(e, true);
	}
    }

    @RequestMapping
    public String resetPassword(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	Anagrafe entity = anagrafeService.findById(new PkId(codice));
	entity.setPassword(null);
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setEntity(entity);
	anagrafe.setOldAnagrafe(entity);
	fixMergeEntityProperty(entity);
	anagrafeService.update(entity);
	model.addAttribute("anagrafe", anagrafe);
	setPageAttributes(model, anagrafe, request);
	return "redirect:view.htm?codice=" + anagrafe.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxGeneraCodicefiscale(@RequestParam("nominativo") String nominativo, @RequestParam("nome") String nome,
	    @RequestParam("datanascita") Date datanascita, @RequestParam("sesso") String sesso, @RequestParam("codicecomune") String codicecomune,
	    Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	Anagrafe entity = anagrafe.getEntity();
	try {
	    String codiceFiscale = anagrafeService.calcolaCodicefiscale(nominativo, nome, datanascita, sesso, codicecomune);
	    entity.setCodicefiscale(codiceFiscale);
	    anagrafe.setEntity(entity);
	    anagrafe.setOldAnagrafe(entity);
	    response.getWriter().write(codiceFiscale);
	} catch (Exception e) {
	    renderErrors(e, true);
	}
    }

    @RequestMapping
    public String popuppopolaDatiDaWs(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return popolaDatiDaWs(model, anagrafe, result, status, request);
    }

    @RequestMapping
    public String popolaDatiDaWs(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    Anagrafe anagrafeAggiornata = anagrafeService.findDatiAnagrafeDaWs(anagrafe.getCfPivaRicercaWs(), anagrafe.getEntity());
	    setPageAttributes(model, anagrafe, request);
	    anagrafe.setEntity(anagrafeAggiornata);
	    anagrafe.setOldAnagrafe(anagrafeAggiornata);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    model.addAttribute("anagrafe", anagrafe);
	    return "anagrafe/form";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe.getEntity(), true, e);
	    setPageAttributes(model, anagrafe, request);
	    fixRenderEntityProperty(anagrafe.getEntity());
	    return "anagrafe/form";
	}
    }

    @RequestMapping
    public String popupcontrolloAggiornamentiAnagrafeByCF(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	return controlloAggiornamentiAnagrafeByCF(model, anagrafe, result, status, request);
    }

    @RequestMapping
    public String controlloAggiornamentiAnagrafeByCF(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Anagrafe anagrafeSigepro = anagrafe.getEntity();
	Anagrafe anagrafeConModifiche = null;
	try {
	    anagrafeConModifiche = anagrafeService.findAnagrafeAggiornataByCF(anagrafeSigepro);
	    fixRenderEntityProperty(anagrafeConModifiche);
	    // sul form viene mostrato entity che ha subito modifiche
	    anagrafe.setEntity(anagrafeConModifiche);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe, true, e);
	}
	// l'entity originale viene passata in modo che è possibile ripristinare lo stato precedente
	anagrafe.setOldAnagrafe(anagrafeSigepro);
	fixRenderEntityProperty(anagrafeSigepro);
	model.addAttribute("anagrafe", anagrafe);
	// parametri messi in request (utilizzati per decidere se un check bx a cui sono riferiti è selezionato o no)
	request.setAttribute("tipologia", anagrafe.getEntity().getTipologia());
	request.setAttribute("inviomail", anagrafe.getEntity().getInvioemail());
	request.setAttribute("inviomailtec", anagrafe.getEntity().getInvioemailtec());
	setPageAttributes(model, anagrafe, request);
	return "anagrafe/form";
    }

    @RequestMapping
    public String popupcontrolloAggiornamentiAnagrafeByPI(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	return controlloAggiornamentiAnagrafeByPI(model, anagrafe, result, status, request);
    }

    @RequestMapping
    public String controlloAggiornamentiAnagrafeByPI(Model model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Anagrafe anagrafeSigepro = anagrafe.getEntity();
	Anagrafe anagrafeConModifiche = null;
	try {
	    anagrafeConModifiche = anagrafeService.findAnagrafeAggiornataByPI(anagrafeSigepro);
	    fixRenderEntityProperty(anagrafeConModifiche);
	    // sul form viene mostrato entity che ha subito modifiche
	    anagrafe.setEntity(anagrafeConModifiche);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe, true, e);
	}
	// l'entity originale viene passata in modo che è possibile ripristinare lo stato precedente
	anagrafe.setOldAnagrafe(anagrafeSigepro);
	fixRenderEntityProperty(anagrafeSigepro);
	// parametri messi in request (utilizzati per decidere se un check bx a cui sono riferiti è selezionato o no)
	request.setAttribute("tipologia", anagrafe.getEntity().getTipologia());
	request.setAttribute("inviomail", anagrafe.getEntity().getInvioemail());
	request.setAttribute("inviomailtec", anagrafe.getEntity().getInvioemailtec());
	request.setAttribute("flagNoProfit", anagrafe.getEntity().getFlagNoprofit());
	model.addAttribute("anagrafe", anagrafe);
	setPageAttributes(model, anagrafe, request);
	return "anagrafe/form";
    }

    @RequestMapping
    public String controlloAggiornamentiAnagrafeByFE(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	Assert.notNull(codice, "Il codice richiesta front end non può essere vuoto");
	FoRichieste foRichieste = foRichiesteService.findById(new PkId(codice));
	Assert.notNull(foRichieste, "Non è stata trovata nessuna richiesta frontend con codice " + String.valueOf(codice));
	if (EntityUtils.getNestedProperty(foRichieste.getAnagrafe(), "id.codice") == null) {
	    return "redirect:inserisciAnagrafeByFE.htm?codice=" + codice;
	}
	Anagrafe oldAnagrafe = anagrafeService.findById(new PkId(foRichieste.getAnagrafe().getId().getCodice()));
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	Anagrafe anagrafeConModifiche = anagrafeService.findAnagrafeAggiornataByFE(codice);
	fixRenderEntityProperty(anagrafeConModifiche);
	// sul form viene mostrato entity che ha subito modifiche
	anagrafe.setEntity(anagrafeConModifiche);
	// l'entity originale viene passata in modo che è possibile ripristinare lo stato precedente
	anagrafe.setOldAnagrafe(oldAnagrafe);
	fixRenderEntityProperty(oldAnagrafe);
	model.addAttribute("anagrafe", anagrafe);
	// parametri messi in request (utilizzati per decidere se un check bx a cui sono riferiti è selezionato o no)
	request.setAttribute("tipologia", anagrafe.getEntity().getTipologia());
	request.setAttribute("inviomail", anagrafe.getEntity().getInvioemail());
	request.setAttribute("inviomailtec", anagrafe.getEntity().getInvioemailtec());
	setPageAttributes(model, anagrafe, request);
	return "anagrafe/form";
    }

    @RequestMapping
    public String inserisciAnagrafeByFE(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	Assert.notNull(codice, "Il codice richiesta front end non può essere vuoto");
	FoRichieste foRichieste = foRichiesteService.findById(new PkId(codice));
	Assert.notNull(foRichieste, "Non è stata trovata nessuna richiesta frontend con codice " + String.valueOf(codice));
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	Anagrafe anagrafeConModifiche = anagrafeService.popolateAnagrafeByFoRichiesta(codice);
	// fixRenderEntityProperty(anagrafeConModifiche);
	anagrafe.setPopup(Boolean.FALSE);
	anagrafe.setPopupCaller("");
	anagrafe.setVisualizzaTipoAnagrafe(anagrafeConModifiche.getTipoanagrafe());
	anagrafe = gestioneVisualizzazioneSezioneTipoAnagrafe("", anagrafeConModifiche.getTipoanagrafe(), anagrafeConModifiche.getTipoanagrafe(),
		anagrafe, anagrafeConModifiche);
	anagrafe.setEntity(anagrafeConModifiche);
	anagrafe.setOldAnagrafe(anagrafeConModifiche);
	setPageAttributes(model, anagrafe, request);
	fixRenderEntityProperty(anagrafe.getEntity());
	model.addAttribute("anagrafe", anagrafe);
	return "anagrafe/form";
    }

    @RequestMapping
    public String popupclose(@RequestParam("codice") Integer codice, @RequestParam("popup") boolean popup,
	    @RequestParam("popupCaller") String popupCaller, Model model, HttpServletRequest request) {

	Anagrafe entity = anagrafeService.findById(new PkId(codice));
	AnagrafeCommand anagrafe = new AnagrafeCommand();
	anagrafe.setEntity(entity);
	anagrafe.setPopup(true);
	anagrafe.setPopupCaller(popupCaller);
	model.addAttribute("anagrafe", anagrafe);
	return "anagrafe/popupclose";
    }

    /**
     * Il metodo converte una persona fisica in giuridica e viceversa.
     * 
     * 1- Se l'angrafe passata è giuridica automaticamente il metodo la converte in persona fisica 2- Se l'angrafe
     * passata è fisica automaticamente il metodo la converte in persona giuridica
     * 
     * @param codice
     * @param popupCaller
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String covertTipoAnagrafe(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// Recupero l'anagrafica
	Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	// Converto da fisica in giuridica
	if (anagrafe.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)) {
	    anagrafeService.convertPersonaFisicaToGiuridica(anagrafe);
	} else// converto da giuridica a fisica
	{
	    anagrafeService.convertPersonaGiuridicaToFisica(anagrafe);
	}
	return "redirect:view.htm?codice=" + anagrafe.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxCalcolaFineValidita(Model model, @RequestParam("dataInizio") Date dataInizio,
	    @RequestParam("tipoDocumento") Integer tipoDocumento, HttpServletRequest request, HttpServletResponse response) {

	Tipidocumento tipidocumento = tipidocumentoService.findById(new PkId(tipoDocumento));
	if (tipidocumento == null) {
	    return;
	}
	try {
	    Integer validita = tipidocumento.getNumggvalidita();
	    if (validita != null) {
		Date dataFine = Utilities.addDays(dataInizio, validita);
		String dataFineStr = Utilities.formatDate(dataFine, false);
		response.getWriter().write(dataFineStr);
	    }
	} catch (IOException e) {
	    response.setStatus(500);
	    renderErrors(e, true);
	}
    }

    @RequestMapping
    public String deleteAnagrafeStorico(@RequestParam("codice") Integer codice, ModelMap model, @ModelAttribute("anagrafe") AnagrafeCommand anagrafe,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	Anagrafestorico anagrafestorico = anagrafeService.findAnagrafeStoricoById(new PkId(codice));
	anagrafe.setEntity(anagrafestorico.getAnagrafe());
	try {
	    anagrafeService.deleteAnagrafeStorico(codice);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, anagrafe, true, e);
	    List<Anagrafestorico> anagrafestoricoList = anagrafeService.findStorico(anagrafe.getEntity());
	    model.addAttribute("anagrafe", anagrafe);
	    model.addAttribute("anagrafestoricoList", anagrafestoricoList);
	    return "anagrafe/listanagrafestorico";
	}
	status.setComplete();
	return "redirect:listanagrafestorico.htm?codiceanagrafe=" + anagrafe.getEntity().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Anagrafe entity) {

	if (entity.getComunecomregditte() != null && StringUtils.isBlank(entity.getComunecomregditte().getCodicecomune())) {
	    entity.setComunecomregditte(null);
	}
	if (entity.getComunecorrispondenza() != null && StringUtils.isBlank(entity.getComunecorrispondenza().getCodicecomune())) {
	    entity.setComunecorrispondenza(null);
	}
	if (entity.getComuneNascita() != null && StringUtils.isBlank(entity.getComuneNascita().getCodicecomune())) {
	    entity.setComuneNascita(null);
	}
	if (entity.getComuneregtrib() != null && StringUtils.isBlank(entity.getComuneregtrib().getCodicecomune())) {
	    entity.setComuneregtrib(null);
	}
	if (entity.getComuneResidenza() != null && StringUtils.isBlank(entity.getComuneResidenza().getCodicecomune())) {
	    entity.setComuneResidenza(null);
	}
	if (entity.getTitolo() != null && entity.getTitolo() != null && entity.getTitolo().getId().getCodice() == null) {
	    entity.setTitolo(null);
	}
	if (entity.getCittadinanza() != null && entity.getCittadinanza().getCodice() == null) {
	    entity.setCittadinanza(null);
	}
	if (entity.getFormagiuridica() != null && entity.getFormagiuridica().getId().getCodice() == null) {
	    entity.setFormagiuridica(null);
	}
	if (entity.getElenchiprofessionalibase() != null && entity.getElenchiprofessionalibase().getId() == null) {
	    entity.setElenchiprofessionalibase(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Anagrafe entity) {

	if (entity.getComunecomregditte() == null) {
	    entity.setComunecomregditte(new Comuni());
	}
	if (entity.getComunecorrispondenza() == null) {
	    entity.setComunecorrispondenza(new Comuni());
	}
	if (entity.getComuneNascita() == null) {
	    entity.setComuneNascita(new Comuni());
	}
	if (entity.getComuneregtrib() == null) {
	    entity.setComuneregtrib(new Comuni());
	}
	if (entity.getComuneResidenza() == null) {
	    entity.setComuneResidenza(new Comuni());
	}
	if (entity.getTitolo() == null) {
	    entity.setTitolo(new Titoli());
	}
	if (entity.getCittadinanza() == null) {
	    entity.setCittadinanza(new Cittadinanza());
	}
	if (entity.getFormagiuridica() == null) {
	    entity.setFormagiuridica(new Formegiuridiche());
	}
	if (entity.getComuneNascita() == null) {
	    entity.setComuneNascita(new Comuni());
	}
	if (entity.getElenchiprofessionalibase() == null) {
	    entity.setElenchiprofessionalibase(new Elenchiprofessionalibase());
	}
	if (entity.getSedeInail() == null) {
	    entity.setSedeInail(new Elencoinailbase());
	}
	if (entity.getSedeInps() == null) {
	    entity.setSedeInps(new Elencoinpsbase());
	}
	if (entity.getSedeCassaedile() == null) {
	    entity.setSedeCassaedile(new Elencocassaedilebase());
	}
    }

    protected void fixMergeScadenzeProperty(Scadenze entity) {

    }

    protected void fixRenderScadenzeProperty(Scadenze entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
    }

    private void fixRenderEmailanagrProperty(Emailanagr entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
    }

    //    private void fixMergeAnagrafedocumentiProperty(Anagrafedocumenti entity) {
    //
    //	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId().getCodice() == null) {
    //	    entity.setAnagrafe(null);
    //	}
    //	if (entity.getTipidocumento() != null && entity.getTipidocumento().getId().getCodice() == null) {
    //	    entity.setTipidocumento(null);
    //	}
    //	if (entity.getOggetto() != null && entity.getOggetto().getId().getCodice() == null) {
    //	    entity.setOggetto(null);
    //	}
    //    }
    //    private void fixRenderAnagrafeDocumentiProperty(Anagrafedocumenti entity) {
    //
    //	if (entity.getAnagrafe() == null) {
    //	    entity.setAnagrafe(new Anagrafe());
    //	}
    //	if (entity.getOggetto() == null) {
    //	    entity.setOggetto(new Oggetti());
    //	}
    //	if (entity.getTipidocumento() == null) {
    //	    entity.setTipidocumento(new Tipidocumento());
    //	}
    //    }
    private void fixRenderOfficeProperty(DURCInsertRequest durc) {

	if (durc.getInstitute() != null && !durc.getInstitute().isEmpty()) {
	    List<Institute> institutes = durc.getInstitute();
	    for (Institute institute : institutes) {
		if (institute.getInstituteOffice() == null) {
		    institute.setInstituteOffice(new Office());
		}
	    }
	}
    }

    protected void setPageAttributes(Model model, AnagrafeCommand anagrafe, HttpServletRequest request) {

	// -----------------INIZIO GESTIONE PASSWORD AUTOMATICA-----------------
	ConfigurazioneId id = new ConfigurazioneId();
	Configurazione configurazione = configurazioneService.findById(id);
	// -----------------FINE GESTIONE PASSWORD ANAGRAFICA
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ANAGRAFE_ALTRI_DATI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ANAGRAFE_DATI_NASCITA_O_DATI_AZIENDA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ANAGRAFE_INDIRIZZO_CORRISPONDENZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ANAGRAFE_RESIDENZA_SEDE_LEGALE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ANAGRAFE_PARAMETRI_FRONTOFFICE, "1", request);
	model.addAttribute("configurazione", configurazione);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------- -----------
	// GESTICE LA VISUALIZZAZIONE O NO DELLE FUNZIONALITà DI GENERAZIONE E RESET PASSWORD
	if (anagrafe.getEntity() != null) {
	    if (StringUtils.isNotBlank(anagrafe.getEntity().getPassword())) {
		model.addAttribute("isPasswordSet", PASSWORD_SETTATA);
	    }
	    if (!StringUtils.isNotBlank(anagrafe.getEntity().getPassword())) {
		model.addAttribute("isPasswordSet", PASSWORD_NON_SETTATA);
	    }
	}
	if (anagrafe.getEntity() != null && anagrafe.getEntity().getTipologia() == null) {
	    anagrafe.getEntity().setTipologia(new Integer("0"));
	}
	if (anagrafe.getOldAnagrafe() != null && anagrafe.getOldAnagrafe().getTipologia() == null) {
	    anagrafe.getOldAnagrafe().setTipologia(new Integer("0"));
	}
	model.addAttribute("isTecnico", anagrafe.getEntity().getTipologia());
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, request);
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE)) {
	    //recupero dalla vert STC tutti i parametri
	    Verticalizzazioniparametri escludiRicercaPF = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_WSANAGRAFE, WebConstants.ESCLUDI_RICERCA_PER_PF);
	    if (escludiRicercaPF != null && StringUtils.isNotBlank(escludiRicercaPF.getValore())) {
		request.setAttribute(WebConstants.ESCLUDI_RICERCA_PER_PF, escludiRicercaPF.getValore());
	    } else {
		request.setAttribute(WebConstants.ESCLUDI_RICERCA_PER_PF, 0);
	    }
	}
    }

    private Anagrafe getAjaxField(Anagrafe anagrafe, HttpServletRequest request) {

	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (StringUtils.isNotBlank(request.getParameter("entity.comuneNascita.codicecomune"))) {
	    String codicecomunenascita = request.getParameter("entity.comuneNascita.codicecomune");
	    Comuni comunenascita = comuniService.findById(codicecomunenascita);
	    anagrafe.setComuneNascita(comunenascita);
	} else {
	    anagrafe.setComuneNascita(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("entity.comuneResidenza.codicecomune"))) {
	    String codicecomuneresidenza = request.getParameter("entity.comuneResidenza.codicecomune");
	    Comuni comuneresidenza = comuniService.findById(codicecomuneresidenza);
	    anagrafe.setComuneResidenza(comuneresidenza);
	} else {
	    anagrafe.setComuneResidenza(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("entity.comunecorrispondenza.codicecomune"))) {
	    String codicecomunecorrispondenza = request.getParameter("entity.comunecorrispondenza.codicecomune");
	    Comuni comunecorrispondenza = comuniService.findById(codicecomunecorrispondenza);
	    anagrafe.setComunecorrispondenza(comunecorrispondenza);
	} else {
	    anagrafe.setComunecorrispondenza(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("entity.comunecomregditte.codicecomune"))) {
	    String codicecomuneregditte = request.getParameter("entity.comunecomregditte.codicecomune");
	    Comuni comuneregditte = comuniService.findById(codicecomuneregditte);
	    anagrafe.setComunecomregditte(comuneregditte);
	} else {
	    anagrafe.setComunecomregditte(null);
	}
	// campi in più se persona giuridica
	if (StringUtils.isNotBlank(request.getParameter("entity.comuneregtrib.codicecomune"))) {
	    String codicecomuniRegistTrib = request.getParameter("entity.comuneregtrib.codicecomune");
	    Comuni comuniRegistTrib = comuniService.findById(codicecomuniRegistTrib);
	    anagrafe.setComuneregtrib(comuniRegistTrib);
	} else {
	    anagrafe.setComuneregtrib(null);
	}
	if (anagrafe.getCittadinanza() != null && anagrafe.getCittadinanza().getCodice() != null) {
	    Cittadinanza cittadinanza = cittadinanzaService.findById(anagrafe.getCittadinanza().getCodice());
	    anagrafe.setCittadinanza(cittadinanza);
	}
	if (anagrafe.getTitolo() != null && anagrafe.getTitolo().getId().getCodice() != null) {
	    Titoli titolo = titoliService.findById(new PkId(anagrafe.getTitolo().getId().getCodice()));
	    anagrafe.setTitolo(titolo);
	}
	if (anagrafe.getFormagiuridica() != null && anagrafe.getFormagiuridica().getId().getCodice() != null) {
	    Formegiuridiche formegiuridiche = formegiuridicheService.findById(new PkId(anagrafe.getFormagiuridica().getId().getCodice()));
	    anagrafe.setFormagiuridica(formegiuridiche);
	}
	// campi in più se persona giuridica
	// se è tecnico potrebbe essere inserito:
	// 1-l'albo di iscrizione
	// 2-la provincia in cui è iscritto;
	if (anagrafe.getElenchiprofessionalibase() != null && anagrafe.getElenchiprofessionalibase().getId() != null) {
	    Elenchiprofessionalibase elenchiprofessionalibase = elenchiprofessionalibaseService.findById(anagrafe.getElenchiprofessionalibase()
		    .getId());
	    anagrafe.setElenchiprofessionalibase(elenchiprofessionalibase);
	}
	return anagrafe;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
