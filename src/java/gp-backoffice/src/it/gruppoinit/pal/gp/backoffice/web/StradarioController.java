package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.domain.helper.AllineamentoStradarioHelper;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.service.AreedettagliService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author Riccardo Bocci
 */
@Controller
@SessionAttributes("stradario")
public class StradarioController extends BaseController<Stradario> {

    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private AreedettagliService areedettagliService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    private static final Logger log = LoggerFactory.getLogger(StradarioController.class);

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Stradario> stradarioList = stradarioService.findAll(null, null);
	ModelMap model = new ModelMap(stradarioList);
	boolean export = createJMesaExport(request, response, stradarioList);
	if (export) {
	    return null;
	}
	model.addAttribute("stradarioList", stradarioList);
	return model;
    }

    @RequestMapping
    public String popupcreate(Model model, @RequestParam(value = "popupCaller") String popupCaller, HttpServletRequest request) {

	return create(model, Boolean.TRUE, popupCaller, request);
    }

    @RequestMapping
    public String create(Model model, @RequestParam(value = "popup", required = false) Boolean popup,
	    @RequestParam(value = "popupCaller", required = false) String popupCaller, HttpServletRequest request) {

	Stradario stradario = new Stradario();
	stradario.setPopup(BooleanUtils.toBoolean(popup));
	stradario.setPopupCaller(popupCaller);
	fixRenderEntityProperty(stradario);
	model.addAttribute("stradario", stradario);
	setPageAttributes(model);
	return "stradario/form";
    }

    @RequestMapping
    public String popupinsert(Model model, @ModelAttribute("stradario") Stradario stradario, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	return insert(stradario, result, status, request);
    }

    @RequestMapping
    public String insert(@ModelAttribute("stradario") Stradario stradario, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(stradario);
	try {
	    stradarioService.insert(stradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradario, e);
	    fixRenderEntityProperty(stradario);
	    return "stradario/form";
	}
	if (BooleanUtils.isTrue(stradario.getPopup())) {
	    return "redirect:" + popupcloseRedirect(stradario.getId().getCodice(), "01", stradario.getPopupCaller());
	} else {
	    status.setComplete();
	    return "redirect:view.htm?codice=" + stradario.getId().getCodice() + "&status_msg=01";
	}
    }

    private String popupcloseRedirect(Integer codiceAnagrafe, String status_msg, String popupCaller) {

	return "popupclose.htm?codice=" + codiceAnagrafe + "&popup=true&status_msg=" + status_msg + "&popupCaller=" + popupCaller;
    }

    @RequestMapping
    public String popupclose(@RequestParam("codice") Integer codice, @RequestParam("popup") boolean popup,
	    @RequestParam("popupCaller") String popupCaller, Model model, HttpServletRequest request) {

	Stradario stradario = stradarioService.findById(new PkId(codice));
	stradario.setPopup(true);
	stradario.setPopupCaller(popupCaller);
	fixRenderEntityProperty(stradario);
	model.addAttribute("stradario", stradario);
	return "stradario/popupclose";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Stradario stradario = stradarioService.findById(id);
	fixRenderEntityProperty(stradario);
	model.addAttribute("stradario", stradario);
	boolean canModifyComune = true;
	int countRecordAreaDettagli = areedettagliService.countRecordByStradario(stradario);
	if (countRecordAreaDettagli > 0) {
	    canModifyComune = false;
	}
	int countRecordStradario = istanzestradarioService.countRecordByStradario(stradario);
	if (countRecordStradario > 0) {
	    canModifyComune = false;
	}
	// if (stradario.getIstanzes().size() > 0) {
	// canModifyComune = false;
	// }
	model.addAttribute("canModifyComune", canModifyComune);
	setPageAttributes(model);
	return "stradario/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("stradario") Stradario stradario, BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(stradario);
	try {
	    stradarioService.update(stradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, stradario, e);
	    fixRenderEntityProperty(stradario);
	    return "stradario/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + stradario.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("stradario") Stradario stradario, BindingResult result, SessionStatus status) {

	Stradario objToDelete = stradarioService.findById(stradario.getId());
	try {
	    stradarioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(stradario);
	    return "stradario/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public void ajaxIsAllowedInsertStardario(HttpServletResponse response, HttpServletRequest request) throws IOException {

	ConfigurazioneId id = new ConfigurazioneId(ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	Configurazione conf = configurazioneService.findById(id);
	if (conf == null) {
	    log.error("ajaxIsAllowedInsertStardario(): configurazione nulla per il software {}", ORMHelper.getSoftware());
	    throw new RuntimeException("Attenzione! Non è stata effettuata la configurazione per il Modulo " + ORMHelper.getSoftware());
	}
	if (conf.getFlagVietainsstrdaistanze() != null && conf.getFlagVietainsstrdaistanze() == true) {
	    StringBuffer buf = new StringBuffer("false");
	    response.setContentType("text/plain");
	    response.getWriter().write(buf.toString());
	} else {
	    StringBuffer buf = new StringBuffer("true");
	    response.setContentType("text/plain");
	    response.getWriter().write(buf.toString());
	}
    }

    @RequestMapping
    public String createAllineaStradario(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Recupero la lista dei comuni attivi per l'utente loggato
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomunis = responsabilicomuniService.findByOperatore(responsabile);
	List<AllineamentoStradarioHelper> allineamentoStradarioHelpers = new ArrayList<AllineamentoStradarioHelper>();
	AllineamentoStradarioHelper allineamentoStradarioHelper = null;
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    allineamentoStradarioHelper = new AllineamentoStradarioHelper();
	    allineamentoStradarioHelper.setCodicecomune(responsabilicomuni.getComune().getComune());
	    allineamentoStradarioHelper.setComune(responsabilicomuni.getComune().getComune());
	    allineamentoStradarioHelpers.add(allineamentoStradarioHelper);
	}
	model.addAttribute("allineamentoStradarioHelpers", allineamentoStradarioHelpers);
	return "stradario/formAllineamento";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String allineaStradario(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Recupero la lista dei comuni attivi per l'utente loggato
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomunis = responsabilicomuniService.findByOperatore(responsabile);
	Set<String> codiciComuni = new HashSet<String>();
	Map<String, Map<String, AllineamentoStradarioHelper>> risultato = null;
	// Dai responsabili comuni recupero il codici comuni
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    codiciComuni.add(responsabilicomuni.getComune().getCodicecomune());
	}
	try {
	    // Effettua l'aggiornamento
	    risultato = stradarioService.updateAllineaStradario(codiciComuni, responsabile);
	} catch (Exception e) {
	    log.error("allineaStradario: ", e);
	    throw new RuntimeException(e);
	}
	List<AllineamentoStradarioHelper> allineamentoStradarioHelpers = new ArrayList<AllineamentoStradarioHelper>();
	List<String> errori = new ArrayList<String>();
	AllineamentoStradarioHelper allineamentoStradarioHelper = null;
	// Estraggo il valore della mappa con la chiave risultato (è un latra mappa <key,value> <codicecomune,allineamentoStradarioHelper)
	Map<String, AllineamentoStradarioHelper> map = risultato.get("RISULTATO");
	// Per ogni comune attivo per il responsabile estraggo l'oggetto allineamentoStradarioHelper
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    allineamentoStradarioHelper = (AllineamentoStradarioHelper) map.get(responsabilicomuni.getComune().getCodicecomune());
	    // L'oggetto conterrà le info sui valori aggiornati inseriti, setto il comune e il codice comune
	    allineamentoStradarioHelper.setCodicecomune(responsabilicomuni.getComune().getComune());
	    allineamentoStradarioHelper.setComune(responsabilicomuni.getComune().getComune());
	    allineamentoStradarioHelpers.add(allineamentoStradarioHelper);
	    // Creo una lista di errori e la popolo con tutti gli errori degli oggetti allineamentoStradarioHelper
	    errori.addAll(allineamentoStradarioHelper.getErrori());
	}
	model.addAttribute("allineamentoStradarioHelpers", allineamentoStradarioHelpers);
	model.addAttribute("errori", errori);
	FlashMessages.getInfos().add("Aggiornamento terminato");
	return "stradario/formAllineamento";
	//return "redirect:createAllineaStradario.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void allineaStradarioAutomatico(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	//	// Recupero la lista dei comuni attivi per l'utente loggato
	//	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	//	List<Responsabilicomuni> responsabilicomunis = responsabilicomuniService.findByOperatore(responsabile);
	//	List<String> codiciComuni = new ArrayList<String>();
	//	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	//	    codiciComuni.add(responsabilicomuni.getComune().getCodicecomune());
	//	}
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	Set<String> codiciComuni = new HashSet<String>();
	for (Comuniassociati comuniassociati : comuniassociatis) {
	    codiciComuni.add(comuniassociati.getComune().getCodicecomune());
	}
	try {
	    stradarioService.updateAllineaStradario(codiciComuni, null);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    protected void fixMergeEntityProperty(Stradario entity) {

	if (entity.getStradariozone() != null && entity.getStradariozone().getId() != null && entity.getStradariozone().getId().getCodice() == null) {
	    entity.setStradariozone(null);
	}
	if (entity.getComune() != null && entity.getComune().getCodicecomune() == null) {
	    entity.setComune(null);
	}
	if (entity.getComuneLocalizzazione() != null && entity.getComuneLocalizzazione().getCodicecomune() == null) {
	    entity.setComuneLocalizzazione(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Stradario entity) {

	if (entity.getStradariozone() == null) {
	    entity.setStradariozone(new Stradariozone());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
	if (entity.getComuneLocalizzazione() == null) {
	    entity.setComuneLocalizzazione(new Comuni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
