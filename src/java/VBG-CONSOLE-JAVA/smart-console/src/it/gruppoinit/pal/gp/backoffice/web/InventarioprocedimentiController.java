package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Documenti;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.InventarioprocLeggi;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Naturaendobase;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.Testiestesi;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.TipoDownload;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.InventarioprocedimentiCommand;
import it.gruppoinit.pal.gp.core.domain.web.InventarioprocedimentisoftwareTable;
import it.gruppoinit.pal.gp.core.jmesa.InventarioprocedimentiTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.DocumentiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocLeggiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocTipititoloService;
import it.gruppoinit.pal.gp.core.service.Inventarioprocdyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentioneriService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.NaturaendobaseService;
import it.gruppoinit.pal.gp.core.service.NormativeService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TestiestesiService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.init.sigepro.rte.types.SportelloType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "inventarioprocedimenti", "allegati" })
public class InventarioprocedimentiController extends BaseController<Inventarioprocedimenti> {

    private static final Logger log = LoggerFactory.getLogger(InventarioprocedimentiController.class);
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private TempificazioniService tempificazioniService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AmministrazionireferentiService amministrazionireferentiService;
    @Autowired
    private NaturaendobaseService naturaendoService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private InventarioprocedimentioneriService inventarioprocedimentioneriService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private AllegatiService allegatiService;
    @Autowired
    private TestiestesiService testiestesiService;
    @Autowired
    private NormativeService normativeService;
    @Autowired
    private DocumentiService documentiService;
    @Autowired
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Inventarioprocdyn2modellitService inventarioprocdyn2modellitService;
    @Autowired
    private InventarioprocedimentiincompService inventarioprocedimentiincompService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private LeggiService leggiService;
    @Autowired
    private InventarioprocLeggiService inventarioprocLeggiService;
    @Autowired
    private InventarioprocTipititoloService inventarioprocTipititoloService;
    @Autowired
    private InventarioprocedimentipeopleService inventarioprocedimentipeopleService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    @Autowired
    private InventarioprocEndoService inventarioprocEndoService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    @RequestMapping
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {

	String tipo = "";
	// if (ORMHelper.isConsoleRegionale()) {
	tipo = (String) request.getParameter("tipo");
	//}
	GenerateTable<Inventarioprocedimenti> inventarioprocedimentiTable = new InventarioprocedimentiTable(tipo);
	String htmlTable = inventarioprocedimentiTable.createJMesaList(request, response,
		"inventarioprocedimenti.label.lista_inventarioprocedimenti.title", "inventarioprocedimenti_id", true);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	return "inventarioprocedimenti/list";
    }

    @RequestMapping
    public String createEndoPrincipale(@RequestParam(required = true, value = "alberoproc.id.codice") Integer codicealberoproc, Model model,
	    HttpServletRequest request) {

	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setCodiceAlberoproc(codicealberoproc);
	inventarioprocedimenti.setDisplayMode(InventarioprocedimentiCommand.NEW);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	boolean isVerticalizzazioneCARTttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("isVerticalizzazioneCARTttiva", isVerticalizzazioneCARTttiva);
	model.addAttribute("isIstanzecollegatePresenti", false);
	model.addAttribute("alberoproc.id.codice", codicealberoproc);
	Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codicealberoproc));
	if (ap == null) {
	    throw new RuntimeException("Attenzione attività con codice " + codicealberoproc + " non trovata");
	}
	StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), codicealberoproc);
	if (stp2 == null) {
	    throw new RuntimeException(
		    "Attenzione non sono stati ancora configurati i parametri regionali per l'attività " + ap.getVwAlberoproc().getScDescrizione());
	}
	if (stp2.getStpTipologieEndo2() == null) {
	    throw new RuntimeException("Attenzione non è configurata la tipologia endo nei parametri regionali per l'attività "
		    + ap.getVwAlberoproc().getScDescrizione());
	}
	if (StringUtils.isBlank(stp2.getCodiceEndoRegionale())) {
	    throw new RuntimeException("Attenzione non è configurato il codice endo regionale nei parametri regionali per l'attività "
		    + ap.getVwAlberoproc().getScDescrizione());
	}
	Alberoproc apPadre = alberoprocService.findByScCodice(ORMHelper.getIdcomunebase(),
		ap.getScCodice().substring(0, ap.getScCodice().length() - 2));
	String descrizioneEndo = ap.getScDescrizione() + " - " + apPadre.getScDescrizione();
	inventarioprocedimenti.getEntity().setProcedimento(descrizioneEndo);
	model.addAttribute("alberoproc", ap);
	setPageAttributes(model);
	return "inventarioprocedimenti/form";
    }

    @RequestMapping
    public String create(@RequestParam(required = false, value = "codiceTipiendo") Integer codiceTipiendo, Model model, HttpServletRequest request) {

	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	// Se "codiceTipiendo" è diveso da null allora iposta la categoria e la presento già popolata sulla maschera di inserimento
	if (codiceTipiendo != null) {
	    Tipiendo tipiendo = tipiendoService.findById(new PkId(codiceTipiendo));
	    inventarioprocedimenti.getEntity().setTipoendo(tipiendo);
	}
	// controllo verticalizzazioni attive
	//boolean isVerticalizzazionePEOPLEAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE);
	boolean isVerticalizzazioneCARTttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	//model.addAttribute("isVerticalizzazionePEOPLEAttiva", isVerticalizzazionePEOPLEAttiva);
	//boolean isVerticalizzazioneAIDAAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AIDA);
	//model.addAttribute("isVerticalizzazioneAIDAAttiva", isVerticalizzazioneAIDAAttiva);
	model.addAttribute("isVerticalizzazioneCARTttiva", isVerticalizzazioneCARTttiva);
	model.addAttribute("isIstanzecollegatePresenti", false);
	model.addAttribute("codiceTipiendo", codiceTipiendo);
	if (ORMHelper.isConsoleRegionale()) {
	    if (inventarioprocedimenti.getCodiceAlberoproc() == null) {
		inventarioprocedimenti.setStpEndoTipo1(new StpEndoTipo1());
		inventarioprocedimenti.getStpEndoTipo1().setFlagRegionale(Boolean.TRUE);
		inventarioprocedimenti.getStpEndoTipo1().setInventarioprocedimenti(inventarioprocedimenti.getEntity());
	    }
	}
	setPageAttributes(model);
	return "inventarioprocedimenti/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/form";
	}
	// recupero i campi ajax
	Inventarioprocedimenti entity = getCampiAjax(inventarioprocedimenti.getEntity());
	inventarioprocedimenti.setEntity(entity);
	fixMergeEntityProperty(inventarioprocedimenti.getEntity());
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    modificaAbilitata(entity.getId(), inventarioprocedimenti.getClass().toString());
	    if (inventarioprocedimenti.getCodiceAlberoproc() != null) {
		inventarioprocedimentiService.insertEndo2(inventarioprocedimenti.getEntity(), inventarioprocedimenti.getCodiceAlberoproc());
	    } else if (inventarioprocedimenti.getStpEndoTipo1() != null) {
		inventarioprocedimentiService.insertEndo1(entity, inventarioprocedimenti.getStpEndoTipo1());
	    } else {
		modificaAbilitataPerComune(entity.getComune(), false);
		inventarioprocedimentiService.insert(inventarioprocedimenti.getEntity());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getEntity(), true, e);
	    // -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	    // -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	    // controllo verticalizzazioni attive
	    boolean isVerticalizzazioneCARTttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    model.addAttribute("isVerticalizzazioneCARTttiva", isVerticalizzazioneCARTttiva);
	    model.addAttribute("isIstanzecollegatePresenti", false);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam(required = true, value = "codicecomune") String codicecomune,
	    Model model, HttpServletRequest request) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	boolean isResponsabileSistemaOrSoftware = false;
	if (responsabile.getAmministratore().equals("1") || responsabile.getAmministratoresoftware().equals("1")) {
	    isResponsabileSistemaOrSoftware = true;
	}
	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codice));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	// controllo verticalizzazioni attive
	boolean isVerticalizzazioneCARTattiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	boolean isIstanzecollegatePresenti = false;
	// controllo se l'operatore loggato è responsabile di sistema o repsonsabile software (bottone ,scheda
	// speigazione e messaggi di cart)
	// controllo se oltre ad esistere un tipo_endo_1 collegato, uno di questi ha anche un oggetto (bottene schede
	// spiegazione)
	StpEndoTipo1 endoTipo1 = null;
	List<StpEndoTipo2> endoTipo2s = null;
	StpEndoTipo2 endoTipo2 = null;
	boolean isEndoTipo1 = false;
	boolean isEndoTipo2 = false;
	if (codicecomune.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
	    endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(ORMHelper.getIdcomunebase(), entity.getId().getCodice());
	    if (endoTipo1 != null) {
		isEndoTipo1 = true;
		inventarioprocedimenti.setStpEndoTipo1(endoTipo1);
	    }
	    endoTipo2s = stpEndoTipo2Service.findByInventarioproc(ORMHelper.getIdcomunebase(), codice);
	    if (endoTipo2s != null && endoTipo2s.size() == 1) {
		isEndoTipo2 = true;
		endoTipo2 = endoTipo2s.get(0);
	    }
	}
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	model.addAttribute("endoTipo1", endoTipo1);
	model.addAttribute("endoTipo2", endoTipo2);
	model.addAttribute("isEndoTipo2", isEndoTipo2);
	model.addAttribute("isEndoTipo1", isEndoTipo1);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("isVerticalizzazioneCARTattiva", isVerticalizzazioneCARTattiva);
	model.addAttribute("isIstanzecollegatePresenti", isIstanzecollegatePresenti);
	model.addAttribute("isResponsabileSistemaOrSoftware", isResponsabileSistemaOrSoftware);
	model.addAttribute("endoprocedimentoTipo2TTR", Boolean.FALSE);
	model.addAttribute("endoprocedimentoTipo1TTR", Boolean.FALSE);
	String idcomuneEntity = entity.getId().getIdcomune();
	if (idcomuneEntity.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
	    if (isEndoTipo2) {
		model.addAttribute("endoprocedimentoTipo2TTR", Boolean.TRUE);
	    } else if (isEndoTipo1) {
		model.addAttribute("endoprocedimentoTipo1TTR", Boolean.TRUE);
	    }
	}
	model.addAttribute(WebConstants.VIEW_REQUEST_VARIABLE, Boolean.TRUE);
	setPageAttributes(model);
	fixRenderEntityProperty(entity);
	return "inventarioprocedimenti/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/form";
	}
	Inventarioprocedimenti entity = getCampiAjax(inventarioprocedimenti.getEntity());
	inventarioprocedimenti.setEntity(entity);
	fixMergeEntityProperty(inventarioprocedimenti.getEntity());
	modificaAbilitata(entity.getId(), inventarioprocedimenti.getClass().toString());
	if (!isBDR(entity)) {
	    modificaAbilitataPerComune(entity.getComune(), false);
	}
	try {
	    if (inventarioprocedimenti.getStpEndoTipo1() != null) {
		inventarioprocedimentiService.updateEndo1(entity, inventarioprocedimenti.getStpEndoTipo1());
	    } else {
		inventarioprocedimentiService.update(inventarioprocedimenti.getEntity());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getEntity(), true, e);
	    // -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	    // -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	    // controllo se l'operatore loggato è responsabile di sistema o repsonsabile software (bottene schede
	    // spiegazione emessaggi cart)
	    LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	    PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	    Responsabili responsabile = responsabiliService.findById(idResponsabile);
	    boolean isResponsabileSistemaOrSoftware = false;
	    if (responsabile.getAmministratore().equals("1") || responsabile.getAmministratoresoftware().equals("1")) {
		isResponsabileSistemaOrSoftware = true;
	    }
	    boolean isIstanzecollegatePresenti = false;
	    // controllo se oltre ad esistere un tipo_endo_1 collegato, uno di questi ha anche un oggetto (bottene
	    // schede
	    // spiegazione)
	    boolean isEndoTipo1ConOggetto = false;
	    //	    if (!inventarioprocedimenti.getEntity().getStpEndoTipo1s().isEmpty()) {
	    //		Set<StpEndoTipo1> list = inventarioprocedimenti.getEntity().getStpEndoTipo1s();
	    //		for (StpEndoTipo1 stpEndoTipo1 : list) {
	    //		    if (stpEndoTipo1.getOggetti() != null) {
	    //			isEndoTipo1ConOggetto = true;
	    //		    }
	    //		}
	    //	    }
	    model.addAttribute("isEndoTipo1ConOggetto", isEndoTipo1ConOggetto);
	    boolean isVerticalizzazioneCARTttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    model.addAttribute("isIstanzecollegatePresenti", isIstanzecollegatePresenti);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    model.addAttribute("isVerticalizzazioneCARTttiva", isVerticalizzazioneCARTttiva);
	    model.addAttribute("isResponsabileSistemaOrSoftware", isResponsabileSistemaOrSoftware);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune() + "&status_msg=02";
    }

    private boolean isBDR(Inventarioprocedimenti ip) {

	StpEndoTipo1 s = stpEndoTipo1Service.findByInventarioProcedimenti(ip.getId().getIdcomune(), ip.getId().getCodice());
	boolean isBDR = (s == null) ? false : true;
	if (!isBDR) {
	    List<StpEndoTipo2> s2 = stpEndoTipo2Service.findByInventarioproc(ip.getId().getIdcomune(), ip.getId().getCodice());
	    isBDR = s2.size() > 0;
	}
	return isBDR;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Inventarioprocedimenti objToDelete = inventarioprocedimentiService.findById(inventarioprocedimenti.getEntity().getId());
	try {
	    modificaAbilitata(inventarioprocedimenti.getEntity().getId(), inventarioprocedimenti.getClass().toString());
	    modificaAbilitataPerComune(objToDelete.getComune(), isBDR(objToDelete));
	    inventarioprocedimentiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    // -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE, "1", request);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI, "1", request);
	    // -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	    // controllo verticalizzazioni attive
	    boolean isVerticalizzazioneCARTttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	    if (EntityUtils.getNestedProperty(objToDelete.getAmministrazionireferente(), "id.codice") != null) {
		Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService
			.findById(new PkId(objToDelete.getAmministrazionireferente().getId().getCodice()));
		inventarioprocedimenti.getEntity().setAmministrazionireferente(amministrazionireferenti);
	    }
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    model.addAttribute("isVerticalizzazioneCARTttiva", isVerticalizzazioneCARTttiva);
	    // controlla se è collegato a di un istanza, l'amministrazione non può essere modificata
	    boolean isIstanzecollegatePresenti = false;
	    model.addAttribute("isIstanzecollegatePresenti", isIstanzecollegatePresenti);
	    return "inventarioprocedimenti/form";
	}
	status.setComplete();
	//return "redirect:list.htm";
	return getHistoryBack();
    }

    @RequestMapping
    public ModelMap listallegati(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	List<Allegati> allegatiList = null;//inventarioprocedimenti.getAllegatis();
	allegatiList = allegatiService.findByInventarioprocedimentoAndComune(codiceendo, codicecomune, ORMHelper.getIdcomune());
	if (!ORMHelper.getIdcomune().equalsIgnoreCase(codicecomune)) {
	    List<Allegati> allegatiList2 = allegatiService.findByInventarioprocedimentoAndComune(codiceendo, codicecomune, codicecomune);
	    if (!allegatiList2.isEmpty()) {
		allegatiList.addAll(allegatiList2);
	    }
	}
	ModelMap model = new ModelMap(allegatiList);
	boolean export = createJMesaExport(request, response, allegatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("allegatiList", allegatiList);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setTipoEndo(inventarioprocedimenti, model);
	return model;
    }

    @RequestMapping
    public String createAllegati(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Allegati allegati = new Allegati();
	allegati.setInventarioprocedimento(entity);
	inventarioprocedimenti.setAllegati(allegati);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("allegati", allegati);
	setPageAttributes(model);
	setTipoEndo(entity, model);
	return "inventarioprocedimenti/formAllegati";
    }

    @RequestMapping
    public String insertAllegati(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    @ModelAttribute("allegati") Allegati allegati, BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formAllegati";
	}
	// recupero l'oggetto passato
	if (allegati.getOggetti() != null && allegati.getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(allegati.getId().getIdcomune(), allegati.getOggetti().getId().getCodice()));
	    allegati.setOggetti(oggetti);
	}
	fixMergeAllegatiProperty(allegati);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    String[] valueType = request.getParameterValues("tipoDownloads");
	    String foTipiDownload = "";
	    if (valueType != null) {
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		allegati.setFoTipodownload(foTipiDownload);
	    } else {
		allegati.setFoTipodownload(null);
	    }
	    allegatiService.insert(allegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, allegati, false, "", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderAllegatiProperty(allegati);
	    model.addAttribute("allegati", allegati);
	    setPageAttributes(model);
	    setTipoEndo(inventarioprocedimenti.getEntity(), model);
	    return "inventarioprocedimenti/formAllegati";
	}
	status.setComplete();
	return "redirect:viewAllegati.htm?codiceallegato=" + allegati.getId().getCodice() + "&codicecomune=" + allegati.getId().getIdcomune()
		+ "&status_msg=01";
    }

    @RequestMapping
    public String viewAllegati(@RequestParam("codiceallegato") Integer codiceallegato, @RequestParam("codicecomune") String codicecomune, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codicecomune, codiceallegato);
	Allegati allegati = allegatiService.findById(id);
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	if (allegati.getFoTipodownload() != null) {
	    String[] tipodown = allegati.getFoTipodownload().split(",");
	    Set<TipoDownload> tipoDownloadList = TipoDownload.fromString(tipodown, tipoDownloads);
	    allegati.setTipoDownloads(tipoDownloadList);
	}
	fixRenderAllegatiProperty(allegati);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(allegati.getInventarioprocedimento());
	inventarioprocedimenti.setAllegati(allegati);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("allegati", allegati);
	setPageAttributes(model);
	setTipoEndo(allegati.getInventarioprocedimento(), model);
	model.addAttribute(WebConstants.VIEW_REQUEST_VARIABLE, Boolean.TRUE);
	return "inventarioprocedimenti/formAllegati";
    }

    private void setTipoEndo(Inventarioprocedimenti ip, ModelMap model) {

	StpEndoTipo1 endoTipo1 = null;
	List<StpEndoTipo2> endoTipo2 = null;
	boolean isEndoTipo1 = false;
	boolean isEndoTipo2 = false;
	String codicecomune = ip.getId().getIdcomune();
	if (codicecomune.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
	    endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(ip.getId().getIdcomune(), ip.getId().getCodice());
	    if (endoTipo1 != null) {
		isEndoTipo1 = true;
	    }
	    endoTipo2 = stpEndoTipo2Service.findByInventarioproc(ip.getId().getIdcomune(), ip.getId().getCodice());
	    if (endoTipo2.size() > 0) {
		isEndoTipo2 = true;
	    }
	}
	model.addAttribute("isEndoTipo2", isEndoTipo2);
	model.addAttribute("isEndoTipo1", isEndoTipo1);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
    }

    private void setTipoEndo(Inventarioprocedimenti ip, Model model) {

	StpEndoTipo1 endoTipo1 = null;
	List<StpEndoTipo2> endoTipo2 = null;
	boolean isEndoTipo1 = false;
	boolean isEndoTipo2 = false;
	String codicecomune = ip.getId().getIdcomune();
	if (codicecomune.equalsIgnoreCase(ORMHelper.getIdcomunebase())) {
	    endoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(ip.getId().getIdcomune(), ip.getId().getCodice());
	    if (endoTipo1 != null) {
		isEndoTipo1 = true;
	    }
	    endoTipo2 = stpEndoTipo2Service.findByInventarioproc(ip.getId().getIdcomune(), ip.getId().getCodice());
	    if (endoTipo2.size() > 0) {
		isEndoTipo2 = true;
	    }
	}
	model.addAttribute("isEndoTipo2", isEndoTipo2);
	model.addAttribute("isEndoTipo1", isEndoTipo1);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
    }

    @RequestMapping
    public String updateAllegati(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    @ModelAttribute("allegati") Allegati allegati, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// recupero l'oggetto passato
	if (allegati.getOggetti() != null && allegati.getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(allegati.getId().getIdcomune(), allegati.getOggetti().getId().getCodice()));
	    allegati.setOggetti(oggetti);
	}
	if (allegati.getOggetti() != null && allegati.getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(allegati.getId().getIdcomune(), allegati.getOggetti().getId().getCodice()));
	    allegati.setOggetti(oggetti);
	}
	fixMergeAllegatiProperty(allegati);
	modificaAbilitata(allegati.getId(), allegati.getClass().toString());
	modificaAbilitataPerComune(allegati.getComune(), isBDR(allegati.getInventarioprocedimento()));
	try {
	    String[] valueType = request.getParameterValues("tipoDownloads");
	    if (valueType != null) {
		String foTipiDownload = "";
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		allegati.setFoTipodownload(foTipiDownload);
	    } else {
		allegati.setFoTipodownload(null);
	    }
	    allegatiService.update(allegati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, allegati, true, "allegati", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderAllegatiProperty(allegati);
	    model.addAttribute("allegati", allegati);
	    setPageAttributes(model);
	    return "inventarioprocedimenti/formAllegati";
	}
	status.setComplete();
	return "redirect:viewAllegati.htm?codiceallegato=" + inventarioprocedimenti.getAllegati().getId().getCodice() + "&codicecomune="
		+ allegati.getId().getIdcomune() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteAllegati(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    @ModelAttribute("allegati") Allegati allegati, BindingResult result, SessionStatus status) {

	PkId id = new PkId(allegati.getId().getCodice());
	Allegati objToDelete = allegatiService.findById(id);
	try {
	    modificaAbilitata(objToDelete.getId(), allegati.getClass().toString());
	    modificaAbilitataPerComune(objToDelete.getComune(), isBDR(objToDelete.getInventarioprocedimento()));
	    allegatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "allegati", e);
	    fixRenderAllegatiProperty(allegati);
	    setPageAttributes(model);
	    model.addAttribute("allegati", allegati);
	    return "inventarioprocedimenti/formAllegati";
	}
	status.setComplete();
	return "redirect:listallegati.htm?codiceendo=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    //    @RequestMapping
    //    public ModelMap listtestiestesi(@RequestParam("codiceendo") Integer codiceendo,
    //	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {
    //
    //	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
    //	Set<Testiestesi> testiestesiList = inventarioprocedimenti.getTestiestesis();
    //	ModelMap model = new ModelMap(testiestesiList);
    //	boolean export = createJMesaExport(request, response, testiestesiList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("testiestesiList", testiestesiList);
    //	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
    //	return model;
    //    }
    @RequestMapping
    public String createTestiestesi(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Testiestesi testiestesi = new Testiestesi();
	testiestesi.setInventarioprocedimento(entity);
	inventarioprocedimenti.setTestiestesi(testiestesi);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formTestiestesi";
    }

    @RequestMapping
    public String insertTestiestesi(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formTestiestesi";
	}
	// recupero l'oggetto passato
	if (inventarioprocedimenti.getTestiestesi().getOggetti() != null
		&& inventarioprocedimenti.getTestiestesi().getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(inventarioprocedimenti.getTestiestesi().getOggetti().getId().getCodice()));
	    inventarioprocedimenti.getTestiestesi().setOggetti(oggetti);
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getTestiestesi().getNormative() != null
		&& inventarioprocedimenti.getTestiestesi().getNormative().getId().getCodice() != null) {
	    Normative normative = normativeService.findById(inventarioprocedimenti.getTestiestesi().getNormative().getId());
	    inventarioprocedimenti.getTestiestesi().setNormative(normative);
	}
	fixMergeTestiestesiProperty(inventarioprocedimenti.getTestiestesi());
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    testiestesiService.insert(inventarioprocedimenti.getTestiestesi());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getTestiestesi(), true, "testiestesi", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderTestiestesiProperty(inventarioprocedimenti.getTestiestesi());
	    return "inventarioprocedimenti/formTestiestesi";
	}
	status.setComplete();
	return "redirect:viewTestiestesi.htm?codicetesto=" + inventarioprocedimenti.getTestiestesi().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewTestiestesi(@RequestParam("codicetesto") Integer codicetesto, Model model, HttpServletRequest request) {

	PkId id = new PkId(codicetesto);
	Testiestesi testiestesi = testiestesiService.findById(id);
	fixRenderTestiestesiProperty(testiestesi);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(testiestesi.getInventarioprocedimento());
	inventarioprocedimenti.setTestiestesi(testiestesi);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formTestiestesi";
    }

    @RequestMapping
    public String updateTestiestesi(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formTestiestesi";
	}
	// recupero l'oggetto passato
	if (inventarioprocedimenti.getTestiestesi().getOggetti() != null
		&& inventarioprocedimenti.getTestiestesi().getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(inventarioprocedimenti.getTestiestesi().getOggetti().getId().getCodice()));
	    inventarioprocedimenti.getTestiestesi().setOggetti(oggetti);
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getTestiestesi().getNormative() != null
		&& inventarioprocedimenti.getTestiestesi().getNormative().getId().getCodice() != null) {
	    Normative normative = normativeService.findById(inventarioprocedimenti.getTestiestesi().getNormative().getId());
	    inventarioprocedimenti.getTestiestesi().setNormative(normative);
	}
	fixMergeTestiestesiProperty(inventarioprocedimenti.getTestiestesi());
	try {
	    modificaAbilitata(inventarioprocedimenti.getTestiestesi().getId(), inventarioprocedimenti.getTestiestesi().getClass().toString());
	    testiestesiService.update(inventarioprocedimenti.getTestiestesi());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getTestiestesi(), true, "testiestesi", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderTestiestesiProperty(inventarioprocedimenti.getTestiestesi());
	    return "inventarioprocedimenti/formTestiestesi";
	}
	status.setComplete();
	return "redirect:viewTestiestesi.htm?codicetesto=" + inventarioprocedimenti.getTestiestesi().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteTestiestesi(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	PkId id = new PkId(inventarioprocedimenti.getTestiestesi().getId().getCodice());
	Testiestesi objToDelete = testiestesiService.findById(id);
	try {
	    modificaAbilitata(inventarioprocedimenti.getTestiestesi().getId(), inventarioprocedimenti.getTestiestesi().getClass().toString());
	    testiestesiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "testiestesi", e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/formTestiestesi";
	}
	status.setComplete();
	return "redirect:listtestiestesi.htm?codiceendo=" + inventarioprocedimenti.getTestiestesi().getInventarioprocedimento().getId().getCodice()
		+ "&codicecomune=" + inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    //    @RequestMapping
    //    public ModelMap listdocumenti(@RequestParam("codiceendo") Integer codiceendo, HttpServletRequest request, HttpServletResponse response) {
    //
    //	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceendo));
    //	Set<Documenti> documentiList = inventarioprocedimenti.getDocumentis();
    //	ModelMap model = new ModelMap(documentiList);
    //	boolean export = createJMesaExport(request, response, documentiList);
    //	if (export) {
    //	    return null;
    //	}
    //	model.addAttribute("documentiList", documentiList);
    //	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
    //	return model;
    //    }
    @RequestMapping
    public String createDocumento(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Documenti documenti = new Documenti();
	documenti.setInventarioprocedimento(entity);
	inventarioprocedimenti.setDocumenti(documenti);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formDocumenti";
    }

    @RequestMapping
    public String insertDocumento(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formDocumenti";
	}
	// recupero l'oggetto passato
	if (inventarioprocedimenti.getDocumenti().getOggetti() != null
		&& inventarioprocedimenti.getDocumenti().getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(inventarioprocedimenti.getDocumenti().getOggetti().getId().getCodice()));
	    inventarioprocedimenti.getDocumenti().setOggetti(oggetti);
	}
	fixMergeDocumentiProperty(inventarioprocedimenti.getDocumenti());
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    documentiService.insert(inventarioprocedimenti.getDocumenti());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getDocumenti(), true, "documenti", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderDocumentiProperty(inventarioprocedimenti.getDocumenti());
	    return "inventarioprocedimenti/formDocumenti";
	}
	status.setComplete();
	return "redirect:viewDocumento.htm?codicedocumento=" + inventarioprocedimenti.getDocumenti().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewDocumento(@RequestParam("codicedocumento") Integer codicedocumento, Model model, HttpServletRequest request) {

	PkId id = new PkId(codicedocumento);
	Documenti documenti = documentiService.findById(id);
	fixRenderDocumentiProperty(documenti);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(documenti.getInventarioprocedimento());
	inventarioprocedimenti.setDocumenti(documenti);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formDocumenti";
    }

    @RequestMapping
    public String updateDocumento(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formDocumenti";
	}
	// recupero l'oggetto passato
	if (inventarioprocedimenti.getDocumenti().getOggetti() != null
		&& inventarioprocedimenti.getDocumenti().getOggetti().getId().getCodice() != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(inventarioprocedimenti.getDocumenti().getOggetti().getId().getCodice()));
	    inventarioprocedimenti.getDocumenti().setOggetti(oggetti);
	}
	fixMergeDocumentiProperty(inventarioprocedimenti.getDocumenti());
	try {
	    modificaAbilitata(inventarioprocedimenti.getDocumenti().getId(), inventarioprocedimenti.getDocumenti().getClass().toString());
	    documentiService.update(inventarioprocedimenti.getDocumenti());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getDocumenti(), true, "documenti", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderDocumentiProperty(inventarioprocedimenti.getDocumenti());
	    return "inventarioprocedimenti/formDocumenti";
	}
	status.setComplete();
	return "redirect:viewDocumento.htm?codicedocumento=" + inventarioprocedimenti.getDocumenti().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteDocumento(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	PkId id = new PkId(inventarioprocedimenti.getDocumenti().getId().getCodice());
	Documenti objToDelete = documentiService.findById(id);
	try {
	    modificaAbilitata(objToDelete.getId(), inventarioprocedimenti.getDocumenti().getClass().toString());
	    documentiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "documenti", e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/formDocumenti";
	}
	status.setComplete();
	return "redirect:listdocumenti.htm?codiceendo=" + inventarioprocedimenti.getDocumenti().getInventarioprocedimento().getId().getCodice()
		+ "&codicecomune=" + inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public ModelMap lististanzecollegate(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	return null;
    }

    /*
    @RequestMapping
    public ModelMap listoneri(@RequestParam("codiceendo") Integer codiceendo,
        @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {
    
    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
    List<Inventarioprocedimentioneri> inventarioprocedimentioneriList = inventarioprocedimentioneriService.findByCodiceInventario(codiceendo, false, codicecomune);
    ModelMap model = new ModelMap(inventarioprocedimentioneriList);
    boolean export = createJMesaExport(request, response, inventarioprocedimentioneriList);
    if (export) {
        return null;
    }
    model.addAttribute("inventarioprocedimentioneriList", inventarioprocedimentioneriList);
    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
    return model;
    }
    */
    @RequestMapping
    public String createOneri(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Inventarioprocedimentioneri inventarioprocedimentioneri = new Inventarioprocedimentioneri();
	inventarioprocedimentioneri.setInventarioprocedimenti(entity);
	inventarioprocedimenti.setInventarioprocedimentioneri(inventarioprocedimentioneri);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	Boolean isImportoIstruttoriaImpostabile = Boolean.FALSE;
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	setPageAttributes(model);
	return "inventarioprocedimenti/formOneri";
    }

    @RequestMapping
    public String insertOneri(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formOneri";
	}
	fixMergeInventarioprocedimentioneriProperty(inventarioprocedimenti.getInventarioprocedimentioneri());
	inventarioprocedimenti.getInventarioprocedimentioneri().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    inventarioprocedimentioneriService.insert(inventarioprocedimenti.getInventarioprocedimentioneri());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentioneri(), true, "inventarioprocedimentioneri", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocedimentioneriProperty(inventarioprocedimenti.getInventarioprocedimentioneri());
	    return "inventarioprocedimenti/formOneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?codiceoneri=" + inventarioprocedimenti.getInventarioprocedimentioneri().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewOneri(@RequestParam("codiceoneri") Integer codiceoneri, Model model, HttpServletRequest request) {

	PkId id = new PkId(codiceoneri);
	Inventarioprocedimentioneri inventarioprocedimentioneri = inventarioprocedimentioneriService.findById(id);
	fixRenderInventarioprocedimentioneriProperty(inventarioprocedimentioneri);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(inventarioprocedimentioneri.getInventarioprocedimenti());
	inventarioprocedimenti.setInventarioprocedimentioneri(inventarioprocedimentioneri);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	Boolean isImportoIstruttoriaImpostabile = tipicausalioneriService
		.isImportoIstruttoriaImpostabile(inventarioprocedimentioneri.getTipicausalioneri());
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	setPageAttributes(model);
	return "inventarioprocedimenti/formOneri";
    }

    @RequestMapping
    public String updateOneri(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formOneri";
	}
	fixMergeInventarioprocedimentioneriProperty(inventarioprocedimenti.getInventarioprocedimentioneri());
	try {
	    modificaAbilitata(inventarioprocedimenti.getInventarioprocedimentioneri().getId(),
		    inventarioprocedimenti.getInventarioprocedimentioneri().getClass().toString());
	    inventarioprocedimentioneriService.update(inventarioprocedimenti.getInventarioprocedimentioneri());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentioneri(), true, "inventarioprocedimentioneri", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocedimentioneriProperty(inventarioprocedimenti.getInventarioprocedimentioneri());
	    return "inventarioprocedimenti/formOneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?codiceoneri=" + inventarioprocedimenti.getInventarioprocedimentioneri().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String deleteOneri(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti, BindingResult result,
	    SessionStatus status) {

	PkId id = new PkId(inventarioprocedimenti.getInventarioprocedimentioneri().getId().getCodice());
	Inventarioprocedimentioneri objToDelete = inventarioprocedimentioneriService.findById(id);
	try {
	    modificaAbilitata(inventarioprocedimenti.getInventarioprocedimentioneri().getId(),
		    inventarioprocedimenti.getInventarioprocedimentioneri().getClass().toString());
	    inventarioprocedimentioneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "inventarioprocedimentioneri", e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/formOneri";
	}
	status.setComplete();
	return "redirect:listoneri.htm?codiceendo="
		+ inventarioprocedimenti.getInventarioprocedimentioneri().getInventarioprocedimenti().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public ModelMap listmodalita(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	List<Inventarioprocedimentisoftware> softwareList = inventarioprocedimentisoftwareService.findByCodiceInventarioprocedimenti(codicecomune,
		codiceendo);
	ModelMap model = new ModelMap(softwareList);
	boolean export = createJMesaExport(request, response, softwareList);
	if (export) {
	    return null;
	}
	model.addAttribute("inventariosoftwareList", softwareList);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String createInvetariosoftware(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(ip);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Inventarioprocedimentisoftware inventarioprocedimentisoftware = new Inventarioprocedimentisoftware();
	inventarioprocedimentisoftware.setInventarioprocedimento(ip);
	inventarioprocedimentisoftware.setSoftware(ip.getSoftware());
	inventarioprocedimenti.setInventarioprocedimentisoftware(inventarioprocedimentisoftware);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	List<Software> softwareattivi = softwareService.findAttiviAndExcludeTT(false);
	// Set<Software> softareAttiviDaConfiguare = getSoftwareattiviDaConfigurare(softwareattivi, ip);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("softareAttiviDaConfiguare", softwareattivi);
	setPageAttributes(model);
	return "inventarioprocedimenti/formInvetariosoftware";
    }

    @RequestMapping
    public String listEndobase(Model model, HttpServletRequest request, HttpServletResponse response) {

	GenerateTable<Inventarioprocedimentisoftware> inventarioprocedimentisoftwareTable = new InventarioprocedimentisoftwareTable();
	String htmlTable = inventarioprocedimentisoftwareTable.createJMesaList(request, response,
		"inventarioprocedimenti.label.lista_endo_base.title", "inventarioprocedimentisoftware_id", false);
	if (htmlTable == null) {
	    return null;
	}
	model.addAttribute("htmltable", htmlTable);
	return "inventarioprocedimenti/listEndobase";
    }

    @RequestMapping
    public void ajaxInsertInventarioprocedimentisoftware(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Inventarioprocedimenti inventarioprocedimento = inventarioprocedimentiService.findById(new PkId(codiceInventario));
	//	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	//	try {
	//	    if (inventarioprocedimentisoftware != null) {
	//		// Eliminare inventarioprocedimentisoftware
	//		inventarioprocedimentisoftwareService.delete(inventarioprocedimentisoftware);
	//	    } else {
	//		// Inserire inventarioprocedimentisoftware
	//		Inventarioprocedimentisoftware entity = new Inventarioprocedimentisoftware();
	//		entity.setInventarioprocedimento(inventarioprocedimento);
	//		inventarioprocedimentisoftwareService.insert(entity);
	//	    }
	//	} catch (Exception e) {
	//	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	//	}
	//	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    @RequestMapping
    public void ajaxDeleteInventarioprocedimentisoftwareMov(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	//	try {
	//	    inventarioprocedimentisoftware.setTipimovimento(null);
	//	    inventarioprocedimentisoftwareService.update(inventarioprocedimentisoftware);
	//	} catch (Exception e) {
	//	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	//	}
    }

    @RequestMapping
    public void ajaxUpdateInventarioprocedimentisoftwareMov(@RequestParam("codice") Integer codice,
	    @RequestParam("codiceTipomovimento") String codiceTipomovimento, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Tipimovimento tipimovimento = new Tipimovimento();
	//	if (codiceTipomovimento != null) {
	//	    TipimovimentoId id = new TipimovimentoId(ORMHelper.getIdcomune(), codiceTipomovimento);
	//	    tipimovimento = tipiMovimentoService.findById(id);
	//	}
	//	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	//	inventarioprocedimentisoftware.setTipimovimento(tipimovimento);
	//	try {
	//	    inventarioprocedimentisoftwareService.update(inventarioprocedimentisoftware);
	//	} catch (Exception e) {
	//	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	//	}
    }

    @RequestMapping
    public String ajaxShowContent(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	//	model.addAttribute("ips", inventarioprocedimentisoftware);
	//	return "inventarioprocedimenti/ajaxContentIps";
    }

    @RequestMapping
    public String ajaxCreateRicercaMovimenti(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	//	if (inventarioprocedimentisoftware.getTipimovimento() == null) {
	//	    inventarioprocedimentisoftware.setTipimovimento(new Tipimovimento());
	//	}
	//	model.addAttribute("ips", inventarioprocedimentisoftware);
	//	return "inventarioprocedimenti/createSearchMovimenti";
    }

    @RequestMapping
    public String insertInvetariosoftware(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formInvetariosoftware";
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni() != null
		&& inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId() != null
		&& inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amm = amministrazioniService
		    .findById(new PkId(inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId().getCodice()));
	    inventarioprocedimenti.getInventarioprocedimentisoftware().setAmministrazioni(amm);
	}
	fixMergeInventarioprocedimentisoftwareProperty(inventarioprocedimenti.getInventarioprocedimentisoftware());
	try {
	    inventarioprocedimentisoftwareService.insert(inventarioprocedimenti.getInventarioprocedimentisoftware());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentisoftware(), true, "inventarioprocedimentisoftware", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    //inventarioprocedimenti.getInventarioprocedimentisoftware().getId().setModulosoftware(null);
	    List<Software> softwareattivi = softwareService.findSoftwareAttivi(false);
	    //model.addAttribute("softwareattivi", softwareattivi);
	    // Set<Software> softareAttiviDaConfiguare = getSoftwareattiviDaConfigurare(softwareattivi, inventarioprocedimenti.getEntity());
	    model.addAttribute("softareAttiviDaConfiguare", softwareattivi);
	    fixRenderInventarioprocedimentisoftwareProperty(inventarioprocedimenti.getInventarioprocedimentisoftware());
	    return "inventarioprocedimenti/formInvetariosoftware";
	}
	status.setComplete();
	return "redirect:viewInvetariosoftware.htm?codice=" + inventarioprocedimenti.getInventarioprocedimentisoftware().getId().getCodice()
		+ "&status_msg=01";
    }

    @RequestMapping
    public String viewInvetariosoftware(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Inventarioprocedimentisoftware inventarioprocedimentisoftware = inventarioprocedimentisoftwareService.findById(new PkId(codice));
	fixRenderInventarioprocedimentisoftwareProperty(inventarioprocedimentisoftware);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.getEntity().getId().setCodice(inventarioprocedimentisoftware.getInventarioprocedimento().getId().getCodice());
	inventarioprocedimenti.setEntity(inventarioprocedimentisoftware.getInventarioprocedimento());
	inventarioprocedimenti.setInventarioprocedimentisoftware(inventarioprocedimentisoftware);
	Software softwareconfigurato = softwareService.findById(inventarioprocedimentisoftware.getSoftware().getCodice());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("softwareconfiguarato", softwareconfigurato.getDescrizione());
	setPageAttributes(model);
	return "inventarioprocedimenti/formInvetariosoftware";
    }

    @RequestMapping
    public String updateInvetariosoftware(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formInvetariosoftware";
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni() != null
		&& inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId() != null
		&& inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amm = amministrazioniService
		    .findById(new PkId(inventarioprocedimenti.getInventarioprocedimentisoftware().getAmministrazioni().getId().getCodice()));
	    inventarioprocedimenti.getInventarioprocedimentisoftware().setAmministrazioni(amm);
	}
	fixMergeInventarioprocedimentisoftwareProperty(inventarioprocedimenti.getInventarioprocedimentisoftware());
	try {
	    inventarioprocedimentisoftwareService.update(inventarioprocedimenti.getInventarioprocedimentisoftware());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentisoftware(), true, "inventarioprocedimentisoftware", e);
	    List<Software> softwareattivi = softwareService.findSoftwareAttivi(false);
	    model.addAttribute("softwareattivi", softwareattivi);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocedimentisoftwareProperty(inventarioprocedimenti.getInventarioprocedimentisoftware());
	    return "inventarioprocedimenti/formInvetariosoftware";
	}
	status.setComplete();
	return "redirect:viewInvetariosoftware.htm?codice=" + inventarioprocedimenti.getInventarioprocedimentisoftware().getId().getCodice()
		+ "&status_msg=02";
    }

    @RequestMapping
    public String deleteInvetariosoftware(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	Inventarioprocedimentisoftware objToDelete = inventarioprocedimentisoftwareService
		.findById(inventarioprocedimenti.getInventarioprocedimentisoftware().getId());
	try {
	    inventarioprocedimentisoftwareService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "inventarioprocedimentisoftware", e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/formInvetariosoftware";
	}
	status.setComplete();
	return "redirect:listmodalita.htm?codiceendo="
		+ inventarioprocedimenti.getInventarioprocedimentisoftware().getInventarioprocedimento().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getInventarioprocedimentisoftware().getInventarioprocedimento().getId().getIdcomune();
    }

    @RequestMapping
    public ModelMap listmodelli(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	//	Set<Inventarioprocdyn2modellit> inventarioprocdyn2modellits = inventarioprocedimenti.getInventarioprocdyn2modellits();
	List<Inventarioprocdyn2modellit> inventarioprocdyn2modellits = inventarioprocdyn2modellitService.findByInventarioprocedimento(codicecomune,
		codiceendo);
	ModelMap model = new ModelMap(inventarioprocdyn2modellits);
	boolean export = createJMesaExport(request, response, inventarioprocdyn2modellits);
	if (export) {
	    return null;
	}
	setTipoEndo(inventarioprocedimenti, model);
	model.addAttribute("inventarioprocdyn2modellits", inventarioprocdyn2modellits);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String createmodelli(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Inventarioprocdyn2modellit inventarioprocdyn2modellit = new Inventarioprocdyn2modellit();
	Inventarioprocdyn2modellitId id = new Inventarioprocdyn2modellitId();
	id.setCodiceinventario(codiceendo);
	id.setIdcomune(ORMHelper.getIdcomune());
	inventarioprocdyn2modellit.setId(id);
	inventarioprocdyn2modellit.setInventarioprocedimenti(entity);
	inventarioprocedimenti.setInventarioprocdyn2modellit(inventarioprocdyn2modellit);
	// Sezione per popolare la lista delle tipo localizzazioni/////////////////
	List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	////////////////////////////////////////////////////////////////////////////////////////////
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	fixRenderInventarioprocdyn2modellitProperty(inventarioprocedimenti.getInventarioprocdyn2modellit());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("view", Boolean.FALSE);
	setPageAttributes(model);
	setTipoEndo(entity, model);
	return "inventarioprocedimenti/formModelli";
    }

    @RequestMapping
    public String insertModelli(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formModelli";
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getInventarioprocdyn2modellit().getDyn2Modellit() != null
		&& inventarioprocedimenti.getInventarioprocdyn2modellit().getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService
		    .findById(inventarioprocedimenti.getInventarioprocdyn2modellit().getDyn2Modellit().getId());
	    inventarioprocedimenti.getInventarioprocdyn2modellit().setDyn2Modellit(dyn2Modellit);
	    inventarioprocedimenti.getInventarioprocdyn2modellit().getId()
		    .setFkD2mtId(inventarioprocedimenti.getInventarioprocdyn2modellit().getDyn2Modellit().getId().getCodice());
	}
	fixMergeInventarioprocdyn2modellitProperty(inventarioprocedimenti.getInventarioprocdyn2modellit());
	try {
	    inventarioprocdyn2modellitService.insert(inventarioprocedimenti.getInventarioprocdyn2modellit());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocdyn2modellit(), true, "inventarioprocdyn2modellit", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    model.addAttribute("view", Boolean.FALSE);
	    // Sezione per popolare la lista delle tipo localizzazioni/////////////////
	    List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	    model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	    ////////////////////////////////////////////////////////////////////////////////////////////
	    fixRenderInventarioprocdyn2modellitProperty(inventarioprocedimenti.getInventarioprocdyn2modellit());
	    return "inventarioprocedimenti/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceendo="
		+ inventarioprocedimenti.getInventarioprocdyn2modellit().getInventarioprocedimenti().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune() + "&codicemodello="
		+ inventarioprocedimenti.getInventarioprocdyn2modellit().getId().getFkD2mtId() + "&status_msg=01";
    }

    @RequestMapping
    public String viewModelli(@RequestParam("codiceendo") Integer codice, @RequestParam(required = true, value = "codicecomune") String codicecomune,
	    @RequestParam("codicemodello") Integer codicemodello, Model model) {

	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codice));
	inventarioprocedimenti.setEntity(entity);
	Inventarioprocdyn2modellitId id = new Inventarioprocdyn2modellitId(codice, codicemodello);
	id.setIdcomune(codicecomune);
	Inventarioprocdyn2modellit inventarioprocdyn2modellit = inventarioprocdyn2modellitService.findById(id);
	fixRenderInventarioprocdyn2modellitProperty(inventarioprocdyn2modellit);
	// Controlla che per il modello scelto i campi dinamici collegati abbiano tutti il flag getFlgMultiplo==true
	// Passa al model un flag= true se sono tutti posti a true 
	// Passa al model un flag=false se almeno uno dei campi è posto a false
	Boolean flagmultiplo = Boolean.TRUE;
	if (inventarioprocdyn2modellit.getFlagTipofirma() != null && inventarioprocdyn2modellit.getFlagTipofirma().equals(2)) {
	    if (EntityUtils.getNestedProperty(inventarioprocdyn2modellit.getDyn2Modellit(), "id.codice") != null) {
		if (inventarioprocdyn2modellit.getDyn2Modellit().getDyn2Modellids() != null
			&& !inventarioprocdyn2modellit.getDyn2Modellit().getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = inventarioprocdyn2modellit.getDyn2Modellit().getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || !dyn2Modellid.getFlgMultiplo()) {
			    flagmultiplo = Boolean.FALSE;
			}
		    }
		}
	    }
	}
	// Sezione per popolare la lista delle tipo localizzazioni/////////////////
	List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	////////////////////////////////////////////////////////////////////////////////////////////
	if (!flagmultiplo) {
	    model.addAttribute("flagmultiplo", flagmultiplo);
	}
	inventarioprocedimenti.setInventarioprocdyn2modellit(inventarioprocdyn2modellit);
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	setTipoEndo(entity, model);
	return "inventarioprocedimenti/formModelli";
    }

    @RequestMapping
    public String updateModelli(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	Inventarioprocdyn2modellit inventarioprocdyn2modellit = inventarioprocedimenti.getInventarioprocdyn2modellit();
	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formModelli";
	}
	fixRenderInventarioprocdyn2modellitProperty(inventarioprocedimenti.getInventarioprocdyn2modellit());
	try {
	    modificaAbilitata(inventarioprocedimenti.getInventarioprocdyn2modellit().getId().getIdcomune(),
		    inventarioprocedimenti.getInventarioprocdyn2modellit().getClass().toString());
	    inventarioprocdyn2modellitService.update(inventarioprocedimenti.getInventarioprocdyn2modellit());
	} catch (Exception e) {
	    model.addAttribute("view", Boolean.TRUE);
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocdyn2modellit(), true, "inventarioprocdyn2modellit", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    // Sezione per popolare la lista delle tipo localizzazioni/////////////////
	    List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	    model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	    ////////////////////////////////////////////////////////////////////////////////////////////
	    fixRenderInventarioprocdyn2modellitProperty(inventarioprocedimenti.getInventarioprocdyn2modellit());
	    return "inventarioprocedimenti/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceendo=" + inventarioprocdyn2modellit.getInventarioprocedimenti().getId().getCodice() + "&codicecomune="
		+ inventarioprocdyn2modellit.getId().getIdcomune() + "&codicemodello=" + inventarioprocdyn2modellit.getId().getFkD2mtId()
		+ "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelli(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti, BindingResult result,
	    SessionStatus status) {

	Inventarioprocdyn2modellit objToDelete = inventarioprocdyn2modellitService
		.findById(inventarioprocedimenti.getInventarioprocdyn2modellit().getId());
	try {
	    modificaAbilitata(objToDelete.getId().getIdcomune(), objToDelete.getClass().toString());
	    inventarioprocdyn2modellitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "inventarioprocdyn2modellit", e);
	    fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	    return "inventarioprocedimenti/formModelli";
	}
	status.setComplete();
	return "redirect:listmodelli.htm?codiceendo="
		+ inventarioprocedimenti.getInventarioprocdyn2modellit().getInventarioprocedimenti().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public ModelMap listendoincompatibili(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	List<Inventarioprocedimentiincomp> inventarioprocedimentiincomps = inventarioprocedimentiincompService
		.findByEndoprocedimento(inventarioprocedimenti);
	ModelMap model = new ModelMap(inventarioprocedimentiincomps);
	boolean export = createJMesaExport(request, response, inventarioprocedimentiincomps);
	if (export) {
	    return null;
	}
	model.addAttribute("inventarioprocedimentiincomps", inventarioprocedimentiincomps);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String createEndoincompatibili(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	Inventarioprocedimentiincomp inventarioprocedimentiincomp = new Inventarioprocedimentiincomp();
	inventarioprocedimentiincomp.setInventarioprocedimento(entity);
	inventarioprocedimenti.setInventarioprocedimentiincomp(inventarioprocedimentiincomp);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formEndoincompatibili";
    }

    @RequestMapping
    public String insertEndoincompatibili(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formEndoincompatibili";
	}
	// recupero i campi ajax
	if (inventarioprocedimenti.getInventarioprocedimentiincomp().getInventarioprocedimentoincompatibile().getId() != null
		&& inventarioprocedimenti.getInventarioprocedimentiincomp().getInventarioprocedimentoincompatibile().getId().getCodice() != null) {
	    Inventarioprocedimenti inventarioprocedimentiincomp = inventarioprocedimentiService
		    .findById(inventarioprocedimenti.getInventarioprocedimentiincomp().getInventarioprocedimentoincompatibile().getId());
	    inventarioprocedimenti.getInventarioprocedimentiincomp().setInventarioprocedimentoincompatibile(inventarioprocedimentiincomp);
	}
	fixMergeInventarioprocedimentoincompProperty(inventarioprocedimenti.getInventarioprocedimentiincomp());
	try {
	    inventarioprocedimentiincompService.insert(inventarioprocedimenti.getInventarioprocedimentiincomp());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentiincomp(), true, "inventarioprocedimentiincomp", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocedimentoincompProperty(inventarioprocedimenti.getInventarioprocedimentiincomp());
	    return "inventarioprocedimenti/formEndoincompatibili";
	}
	status.setComplete();
	return "redirect:listendoincompatibili.htm?codiceendo=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public String deleteEndoincompatibili(@RequestParam("codiceendoinc") Integer codiceendoinc) {

	Inventarioprocedimentiincomp objToDelete = inventarioprocedimentiincompService.findById(new PkId(codiceendoinc));
	inventarioprocedimentiincompService.delete(objToDelete);
	return "redirect:listendoincompatibili.htm?codiceendo=" + objToDelete.getInventarioprocedimento().getId().getCodice() + "&codicecomune="
		+ objToDelete.getId().getIdcomune();
    }

    @RequestMapping
    public String createCopia(Model model) {

	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.getEntity().setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return "inventarioprocedimenti/formCopia";
    }

    @RequestMapping
    public String insertCopia(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti entity = new Inventarioprocedimenti();
	Inventarioprocedimenti copy = null;
	if (inventarioprocedimenti.getEntity().getId() != null && inventarioprocedimenti.getEntity().getId().getCodice() != null) {
	    entity = inventarioprocedimentiService.findById(new PkId(inventarioprocedimenti.getEntity().getId().getCodice()));
	}
	fixMergeEntityProperty(entity);
	try {
	    copy = inventarioprocedimentiService.insertCopia(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti, true, e);
	    return "inventarioprocedimenti/formCopia";
	}
	return "redirect:view.htm?codice=" + copy.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String salvaPreferenzaInConfigurazione(@RequestParam("nomeparametro") String nomeparametro,
	    @RequestParam("codiceprocedura") Integer codiceprocedura, @RequestParam("valore") String valore, HttpServletRequest request,
	    HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId showConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeparametro);
	Configurazioneutente parametroConfigurazioneUtente = configurazioneutenteService.findById(showConfId);
	if (parametroConfigurazioneUtente == null) {
	    // .. inserisco i valori di default
	    parametroConfigurazioneUtente = new Configurazioneutente();
	    parametroConfigurazioneUtente.setId(showConfId);
	    parametroConfigurazioneUtente.setResponsabile(responsabile);
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.insert(parametroConfigurazioneUtente);
	} else {
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.update(parametroConfigurazioneUtente);
	}
	return "redirect:view.htm?codiceprocedura=" + codiceprocedura;
    }

    @RequestMapping
    public ModelMap listnormative(@RequestParam("codiceendo") Integer codiceendo, HttpServletRequest request, HttpServletResponse response) {

	// Lista delle normative configurate (leggi inserite nella tabella inventarioproc_leggi)
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codiceendo));
	Set<InventarioprocLeggi> inventarioprocLeggis = inventarioprocedimenti.getInventarioprocLeggis();
	// Lista delle leggi che posso essere configurate
	List<Leggi> leggiList = leggiService.findAll(null, null);
	ModelMap model = new ModelMap(leggiList);
	boolean export = createJMesaExport(request, response, leggiList);
	if (export) {
	    return null;
	}
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	model.addAttribute("inventarioprocLeggis", inventarioprocLeggis);
	model.addAttribute("leggiList", leggiList);
	return model;
    }

    @RequestMapping
    public ModelMap listtipititolo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	Set<InventarioprocTipititolo> tipititoloList = inventarioprocedimenti.getInventarioprocTipititolos();
	ModelMap model = new ModelMap(tipititoloList);
	boolean export = createJMesaExport(request, response, tipititoloList);
	if (export) {
	    return null;
	}
	model.addAttribute("tipititoloList", tipititoloList);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	return model;
    }

    @RequestMapping
    public String createTipititolo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, Model model) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	InventarioprocTipititolo inventarioprocTipititolo = new InventarioprocTipititolo();
	inventarioprocTipititolo.setInventarioprocedimenti(entity);
	inventarioprocedimenti.setInventarioprocTipititolo(inventarioprocTipititolo);
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formTipititolo";
    }

    @RequestMapping
    public String insertTipititolo(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formTipititolo";
	}
	try {
	    inventarioprocTipititoloService.insert(inventarioprocedimenti.getInventarioprocTipititolo());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocTipititolo(), true, "inventarioprocTipititolo", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocTipititoloProperty(inventarioprocedimenti.getInventarioprocTipititolo());
	    return "inventarioprocedimenti/formTipititolo";
	}
	status.setComplete();
	return "redirect:listtipititolo.htm?codiceendo=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public String viewTipititolo(@RequestParam("codicetipititolo") Integer codicetipititolo, Model model, HttpServletRequest request) {

	PkId id = new PkId(codicetipititolo);
	InventarioprocTipititolo inventarioprocTipititolo = inventarioprocTipititoloService.findById(id);
	fixRenderInventarioprocTipititoloProperty(inventarioprocTipititolo);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(inventarioprocTipititolo.getInventarioprocedimenti());
	inventarioprocedimenti.setInventarioprocTipititolo(inventarioprocTipititolo);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/formTipititolo";
    }

    @RequestMapping
    public String updateTipititolo(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	if (result.hasErrors()) {
	    return "inventarioprocedimenti/formTipititolo";
	}
	try {
	    modificaAbilitata(inventarioprocedimenti.getInventarioprocTipititolo().getId().getIdcomune(),
		    inventarioprocedimenti.getInventarioprocTipititolo().getClass().toString());
	    inventarioprocTipititoloService.update(inventarioprocedimenti.getInventarioprocTipititolo());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocTipititolo(), true, "inventarioprocTipititolo", e);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    fixRenderInventarioprocTipititoloProperty(inventarioprocedimenti.getInventarioprocTipititolo());
	    return "inventarioprocedimenti/formTipititolo";
	}
	status.setComplete();
	return "redirect:viewTipititolo.htm?codicetipititolo=" + inventarioprocedimenti.getInventarioprocTipititolo().getId().getCodice()
		+ "&status_msg=02";
    }

    @RequestMapping
    public String deleteTipititolo(@ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status) {

	PkId id = new PkId(inventarioprocedimenti.getInventarioprocTipititolo().getId().getIdcomune(),
		inventarioprocedimenti.getInventarioprocTipititolo().getId().getCodice());
	InventarioprocTipititolo objToDelete = inventarioprocTipititoloService.findById(id);
	try {
	    modificaAbilitata(id, inventarioprocedimenti.getInventarioprocTipititolo().getClass().toString());
	    inventarioprocTipititoloService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, true, "inventarioprocTipititolo", e);
	    fixRenderInventarioprocTipititoloProperty(inventarioprocedimenti.getInventarioprocTipititolo());
	    return "inventarioprocedimenti/formTipititolo";
	}
	status.setComplete();
	return "redirect:listtipititolo.htm?codiceendo=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		+ inventarioprocedimenti.getEntity().getId().getIdcomune();
    }

    @RequestMapping
    public void ajaxAddNormativa(@RequestParam("codicelegge") Integer codicelegge, @RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	// creo l'oggetto che andrò ad inserire
	InventarioprocLeggi inventarioprocLeggi = new InventarioprocLeggi();
	// recupero la legge scelta e l'endo procedimento che sto configurando
	Leggi legge = leggiService.findById(new PkId(codicelegge));
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceendo));
	// setto legge ed endo all'oggetto che andrò ad inserire
	inventarioprocLeggi.setLeggi(legge);
	inventarioprocLeggi.setInventarioprocedimenti(inventarioprocedimenti);
	try {
	    inventarioprocLeggiService.insert(inventarioprocLeggi);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public void ajaxDeleteNormativa(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	// creo l'oggetto che andrò ad inserire
	InventarioprocLeggi inventarioprocLeggi = inventarioprocLeggiService.findById(new PkId(codice));
	//
	try {
	    inventarioprocLeggiService.delete(inventarioprocLeggi);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public String updateInventarioprocLeggi(@RequestParam("codice") Integer codice, @RequestParam("riferimenti") String riferimenti) {

	InventarioprocLeggi inventarioprocLeggi = inventarioprocLeggiService.findById(new PkId(codice));
	inventarioprocLeggi.setRiferimenti(riferimenti);
	inventarioprocLeggiService.update(inventarioprocLeggi);
	return "redirect:listnormative.htm?codiceendo=" + inventarioprocLeggi.getInventarioprocedimenti().getId().getCodice() + "&codicecomune="
		+ inventarioprocLeggi.getInventarioprocedimenti().getId().getIdcomune() + "&status_msg=02";
    }

    @RequestMapping
    public String listMappingSTP(Model model, @RequestParam("codiceInventario") Integer codiceInventario,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceInventario));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	return "inventarioprocedimenti/listMappingSTP";
    }

    @RequestMapping
    public String createMappingSTP(Model model, @RequestParam("codiceInventario") Integer codiceInventario,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceInventario));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	List<Amministrazioni> amministrazioni = amministrazioniService.findAmministrazioniSTC();
	List<SportelloType> listNodi = this.getNodiPerMappingDaVertSTC();
	model.addAttribute("amministrazioni", amministrazioni);
	model.addAttribute("nodi", listNodi);
	setPageAttributes(model);
	return "inventarioprocedimenti/formMappingSTP";
    }

    @RequestMapping
    public String insertMappingSTP(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimenti.getInventarioprocedimentipeople();
	    inventarioprocedimentipeople.setInventarioprocedimenti(inventarioprocedimenti.getEntity());
	    inventarioprocedimentipeopleService.insert(inventarioprocedimentipeople);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    setPageAttributes(model);
	    status.setComplete();
	    return "redirect:viewMappingSTP.htm?codice=" + inventarioprocedimentipeople.getId().getCodice() + "&status_msg=01";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentipeople(), true, "inventarioprocedimentipeople", e);
	    inventarioprocedimenti.setDisplayMode(BaseCommand.NEW);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    List<Amministrazioni> amministrazioni = amministrazioniService.findAmministrazioniSTC();
	    List<SportelloType> listNodi = this.getNodiPerMappingDaVertSTC();
	    model.addAttribute("amministrazioni", amministrazioni);
	    model.addAttribute("nodi", listNodi);
	    return "inventarioprocedimenti/formMappingSTP";
	}
    }

    @RequestMapping
    public String updateMappingSTP(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimenti.getInventarioprocedimentipeople();
	    inventarioprocedimentipeople.setInventarioprocedimenti(inventarioprocedimenti.getEntity());
	    inventarioprocedimentipeopleService.update(inventarioprocedimentipeople);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    setPageAttributes(model);
	    status.setComplete();
	    return "redirect:viewMappingSTP.htm?codice=" + inventarioprocedimentipeople.getId().getCodice() + "&status_msg=02";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentipeople(), true, "inventarioprocedimentipeople", e);
	    inventarioprocedimenti.setDisplayMode(BaseCommand.EDIT);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    List<Amministrazioni> amministrazioni = amministrazioniService.findAmministrazioniSTC();
	    List<SportelloType> listNodi = this.getNodiPerMappingDaVertSTC();
	    model.addAttribute("amministrazioni", amministrazioni);
	    model.addAttribute("nodi", listNodi);
	    return "inventarioprocedimenti/formMappingSTP";
	}
    }

    @RequestMapping
    public String deleteMappingSTP(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimenti.getInventarioprocedimentipeople();
	    inventarioprocedimentipeopleService.delete(inventarioprocedimentipeople);
	    status.setComplete();
	    return "redirect:listMappingSTP.htm?codiceInventario=" + inventarioprocedimenti.getEntity().getId().getCodice() + "&codicecomune="
		    + inventarioprocedimenti.getEntity().getId().getIdcomune() + "&status_msg=03";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, inventarioprocedimenti.getInventarioprocedimentipeople(), true, "inventarioprocedimentipeople", e);
	    inventarioprocedimenti.setDisplayMode(BaseCommand.EDIT);
	    model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	    List<Amministrazioni> amministrazioni = amministrazioniService.findAmministrazioniSTC();
	    List<SportelloType> listNodi = this.getNodiPerMappingDaVertSTC();
	    model.addAttribute("amministrazioni", amministrazioni);
	    model.addAttribute("nodi", listNodi);
	    return "inventarioprocedimenti/formMappingSTP";
	}
    }

    @RequestMapping
    public String viewMappingSTP(Model model, @RequestParam("codice") Integer codice) {

	Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimentipeopleService.findById(new PkId(codice));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(inventarioprocedimentipeople.getInventarioprocedimenti());
	inventarioprocedimenti.setInventarioprocedimentipeople(inventarioprocedimentipeople);
	inventarioprocedimenti.setDisplayMode(BaseCommand.VIEW);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	List<Amministrazioni> amministrazioni = amministrazioniService.findAmministrazioniSTC();
	List<SportelloType> listNodi = this.getNodiPerMappingDaVertSTC();
	model.addAttribute("amministrazioni", amministrazioni);
	model.addAttribute("nodi", listNodi);
	setPageAttributes(model);
	return "inventarioprocedimenti/formMappingSTP";
    }

    @RequestMapping
    public void ajaxUpdateProprieta(Model model, @ModelAttribute("inventarioprocedimenti") InventarioprocedimentiCommand inventarioprocedimenti,
	    @RequestParam("campoDaModificare") String campoDaModificare, @RequestParam("valore") String valore, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Inventarioprocedimenti entity = inventarioprocedimenti.getEntity();
	if (campoDaModificare.equalsIgnoreCase("campoapplicazione")) {
	    entity.setCampoapplicazione(valore);
	} else if (campoDaModificare.equalsIgnoreCase("datigenerali")) {
	    entity.setDatigenerali(valore);
	}
	if (campoDaModificare.equalsIgnoreCase("normativaue")) {
	    entity.setNormativaue(valore);
	} else if (campoDaModificare.equalsIgnoreCase("normativana")) {
	    entity.setNormativana(valore);
	}
	if (campoDaModificare.equalsIgnoreCase("normativare")) {
	    entity.setNormativare(valore);
	} else if (campoDaModificare.equalsIgnoreCase("regolamenti")) {
	    entity.setRegolamenti(valore);
	} else if (campoDaModificare.equalsIgnoreCase("adempimenti")) {
	    entity.setAdempimenti(valore);
	}
	// SessionDetails sd = getSessionDetails(request);
	// String token = sd.getToken();
	try {
	    modificaAbilitata(entity.getId(), entity.getClass().toString());
	    inventarioprocedimentiService.update(entity);
	    inventarioprocedimenti.setEntity(entity);
	} catch (Exception e) {
	    String err = this.renderErrors(e, false);
	    response.getWriter().write(err);
	}
	fixRenderEntityProperty(inventarioprocedimenti.getEntity());
    }

    @RequestMapping
    public String ajaxVisualizzasubEndo(Model model, @RequestParam("codiceinventario") Integer codiceinventario,
	    @RequestParam("idcomuneinventario") String idcomuneinventario, HttpServletResponse response) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(idcomuneinventario, codiceinventario));
	List<InventarioprocEndo> invprocEndos = inventarioprocEndoService.findByInventarioprocT(idcomuneinventario, codiceinventario, null, null,
		null, null, false);
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	model.addAttribute("inventarioprocedimenti", entity);
	model.addAttribute("invprocEndos", invprocEndos);
	response.setContentType("text/html");
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
	return "inventarioprocedimenti/ajaxVisualizzasubEndo";
    }

    @RequestMapping
    public String ajaxListaMappingCodiceSTC(Model model, @RequestParam("codiceinventario") Integer codiceinventario,
	    @RequestParam(required = true, value = "codicecomune") String codicecomune, HttpServletResponse response) {

	Inventarioprocedimenti entity = inventarioprocedimentiService.findById(new PkId(codicecomune, codiceinventario));
	InventarioprocedimentiCommand inventarioprocedimenti = new InventarioprocedimentiCommand();
	inventarioprocedimenti.setEntity(entity);
	model.addAttribute("inventarioprocedimenti", inventarioprocedimenti);
	setPageAttributes(model);
	if (log.isDebugEnabled())
	    log.debug("call dettaglio istanze procedimenti with codiceIstanzaprocedimento: " + codiceinventario);
	response.setContentType("text/plain");
	return "inventarioprocedimenti/listAjaxMappingSTP";
    }

    private List<SportelloType> getNodiPerMappingDaVertSTC() {

	int base = NodoNLAEnum.NLA_IDNODO.name().length() + 1;
	List<SportelloType> listNodi = new ArrayList<SportelloType>();
	List<Verticalizzazioniparametri> params = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC);
	for (Verticalizzazioniparametri param : params) {
	    String vertParamValue = param.getVerticalizzazioniparametribase().getId().getParametro();
	    if (vertParamValue.equals(NodoNLAEnum.NLA_IDNODO_AIDA.name()) || vertParamValue.equals(NodoNLAEnum.NLA_IDNODO_PEOPLE.name())) {
		String nomeNodo = param.getVerticalizzazioniparametribase().getId().getParametro().substring(base);
		SportelloType nodo = new SportelloType();
		nodo.setIdEnte(nomeNodo);
		nodo.setIdSportello(nomeNodo);
		listNodi.add(nodo);
	    }
	}
	return listNodi;
    }

    private Inventarioprocedimenti getCampiAjax(Inventarioprocedimenti inventarioprocedimenti) {

	//	if (inventarioprocedimenti.getTempificazione() != null && inventarioprocedimenti.getTempificazione().getId().getCodice() != null) {
	//	    Tempificazioni field = tempificazioniService.findById(inventarioprocedimenti.getTempificazione().getId());
	//	    inventarioprocedimenti.setTempificazione(field);
	//	}
	if (inventarioprocedimenti.getTipoendo() != null && inventarioprocedimenti.getTipoendo().getId().getCodice() != null) {
	    Tipiendo field = tipiendoService.findById(inventarioprocedimenti.getTipoendo().getId());
	    inventarioprocedimenti.setTipoendo(field);
	}
	if (inventarioprocedimenti.getAmministrazioni() != null && inventarioprocedimenti.getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni field = amministrazioniService.findById(inventarioprocedimenti.getAmministrazioni().getId());
	    inventarioprocedimenti.setAmministrazioni(field);
	}
	if (inventarioprocedimenti.getAmministrazionireferente() != null
		&& inventarioprocedimenti.getAmministrazionireferente().getId().getCodice() != null) {
	    Amministrazionireferenti field = amministrazionireferentiService.findById(inventarioprocedimenti.getAmministrazionireferente().getId());
	    inventarioprocedimenti.setAmministrazionireferente(field);
	}
	if (inventarioprocedimenti.getNaturaendo() != null && inventarioprocedimenti.getNaturaendo().getId() != null) {
	    Naturaendobase field = naturaendoService.findById(inventarioprocedimenti.getNaturaendo().getId());
	    inventarioprocedimenti.setNaturaendo(field);
	}
	return inventarioprocedimenti;
    }

    @Override
    protected void fixMergeEntityProperty(Inventarioprocedimenti entity) {

	//	if (entity.getTempificazione() != null && entity.getTempificazione().getId() != null
	//		&& (entity.getTempificazione().getId().getCodice() == null)) {
	//	    entity.setTempificazione(null);
	//	}
	if (entity.getTipoendo() != null && entity.getTipoendo().getId() != null && entity.getTipoendo().getId().getCodice() == null) {
	    entity.setTipoendo(null);
	}
	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& entity.getAmministrazioni().getId().getCodice() == null) {
	    entity.setAmministrazioni(null);
	}
	if (entity.getAmministrazionireferente() != null && entity.getAmministrazionireferente().getId() != null
		&& entity.getAmministrazionireferente().getId().getCodice() == null) {
	    entity.setAmministrazionireferente(null);
	}
	if (entity.getNaturaendo() != null && entity.getNaturaendo().getId() != null && entity.getNaturaendo().getId() == null) {
	    entity.setNaturaendo(null);
	}
	if (entity.getComune() == null || (StringUtils.isBlank(entity.getComune().getCodicecomune()))) {
	    entity.setComune(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Inventarioprocedimenti entity) {

	//	if (entity.getTempificazione() == null) {
	//	    entity.setTempificazione(new Tempificazioni());
	//	}
	if (entity.getTipoendo() == null) {
	    entity.setTipoendo(new Tipiendo());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getAmministrazionireferente() == null) {
	    entity.setAmministrazionireferente(new Amministrazionireferenti());
	}
	if (entity.getNaturaendo() == null) {
	    entity.setNaturaendo(new Naturaendobase());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    protected void fixMergeAllegatiProperty(Allegati entity) {

	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& (entity.getAmministrazioni().getId().getCodice() == null)) {
	    entity.setAmministrazioni(null);
	}
	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& (entity.getInventarioprocedimento().getId().getCodice() == null)) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && (entity.getOggetti().getId().getCodice() == null)) {
	    entity.setOggetti(null);
	}
	if (entity.getComune() == null || (StringUtils.isBlank(entity.getComune().getCodicecomune()))) {
	    entity.setComune(null);
	}
    }

    protected void fixRenderAllegatiProperty(Allegati entity) {

	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    protected void fixMergeTestiestesiProperty(Testiestesi entity) {

	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& (entity.getInventarioprocedimento().getId().getCodice() == null)) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && (entity.getOggetti().getId().getCodice() == null)) {
	    entity.setOggetti(null);
	}
	if (entity.getNormative() != null && entity.getNormative().getId() != null && (entity.getNormative().getId().getCodice() == null)) {
	    entity.setNormative(null);
	}
    }

    protected void fixRenderTestiestesiProperty(Testiestesi entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
	if (entity.getNormative() == null) {
	    entity.setNormative(new Normative());
	}
    }

    protected void fixMergeInventarioprocedimentioneriProperty(Inventarioprocedimentioneri entity) {

	if (entity.getTipicausalioneri() != null && entity.getTipicausalioneri().getId() != null
		&& (entity.getTipicausalioneri().getId().getCodice() == null)) {
	    entity.setTipicausalioneri(null);
	}
	if (entity.getInventarioprocedimenti() != null && entity.getInventarioprocedimenti().getId() != null
		&& (entity.getInventarioprocedimenti().getId().getCodice() == null)) {
	    entity.setInventarioprocedimenti(null);
	}
	if (entity.getTipimodalitapagamento() != null && entity.getTipimodalitapagamento().getId() != null
		&& (entity.getTipimodalitapagamento().getId().getCodice() == null)) {
	    entity.setTipimodalitapagamento(null);
	}
    }

    protected void fixRenderInventarioprocedimentioneriProperty(Inventarioprocedimentioneri entity) {

	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getTipimodalitapagamento() == null) {
	    entity.setTipimodalitapagamento(new Tipimodalitapagamento());
	}
    }

    protected void fixMergeInventarioprocedimentisoftwareProperty(Inventarioprocedimentisoftware entity) {

	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& (entity.getInventarioprocedimento().getId().getCodice() == null)) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& entity.getAmministrazioni().getId().getCodice() == null) {
	    entity.setAmministrazioni(null);
	}
    }

    protected void fixRenderInventarioprocedimentisoftwareProperty(Inventarioprocedimentisoftware entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
    }

    protected void fixMergeDocumentiProperty(Documenti entity) {

	if (entity.getAmministrazioni() != null && entity.getAmministrazioni().getId() != null
		&& (entity.getAmministrazioni().getId().getCodice() == null)) {
	    entity.setAmministrazioni(null);
	}
	if (entity.getOggetti() != null && entity.getOggetti().getId() != null && (entity.getOggetti().getId().getCodice() == null)) {
	    entity.setOggetti(null);
	}
	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& (entity.getInventarioprocedimento().getId().getCodice() == null)) {
	    entity.setInventarioprocedimento(null);
	}
    }

    protected void fixRenderDocumentiProperty(Documenti entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getOggetti() == null) {
	    entity.setOggetti(new Oggetti());
	}
	if (entity.getAmministrazioni() == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
    }

    protected void fixMergeInventarioprocdyn2modellitProperty(Inventarioprocdyn2modellit entity) {

	//	if (entity.getInventarioprocedimenti() != null && entity.getInventarioprocedimenti().getId() != null
	//		&& (entity.getInventarioprocedimenti().getId().getCodice() == null)) {
	//	    entity.setInventarioprocedimenti(null);
	//	}
	//	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && (entity.getDyn2Modellit().getId().getCodice() == null)) {
	//	    entity.setDyn2Modellit(null);
	//	}
	//	if (entity.getTipiLocalizzazioni() != null && entity.getTipiLocalizzazioni().getId() != null
	//		&& (entity.getTipiLocalizzazioni().getId().getCodice() == null)) {
	//	    entity.setTipiLocalizzazioni(null);
	//	}
    }

    protected void fixRenderInventarioprocdyn2modellitProperty(Inventarioprocdyn2modellit entity) {

	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getTipiLocalizzazioni() == null) {
	    entity.setTipiLocalizzazioni(new TipiLocalizzazioni());
	}
    }

    protected void fixMergeInventarioprocedimentoincompProperty(Inventarioprocedimentiincomp entity) {

	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& (entity.getInventarioprocedimento().getId().getCodice() == null)) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getInventarioprocedimentoincompatibile() != null && entity.getInventarioprocedimentoincompatibile().getId() != null
		&& (entity.getInventarioprocedimentoincompatibile().getId().getCodice() == null)) {
	    entity.setInventarioprocedimentoincompatibile(null);
	}
    }

    protected void fixRenderInventarioprocedimentoincompProperty(Inventarioprocedimentiincomp entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getInventarioprocedimentoincompatibile() == null) {
	    entity.setInventarioprocedimentoincompatibile(new Inventarioprocedimenti());
	}
    }

    protected void fixRenderInventarioprocTipititoloProperty(InventarioprocTipititolo entity) {

	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
	List<Software> softwareattivi = softwareService.findSoftwareAttivi(false);
	model.addAttribute("listaSoftwareAttivi", softwareattivi);
    }

    private Set<Software> getSoftwareattiviDaConfigurare(List<Software> softwareattivi, Inventarioprocedimenti entity) {

	throw new RuntimeException("NON IMPLEMENTATO DOPO MODIFICA PK");
	//	Set<Inventarioprocedimentisoftware> inventarioprocedimentisoftwares = entity.getInventarioprocedimentisoftwares();
	//	SortedSet<Software> softwateDaConfigurare = new TreeSet<Software>(new SoftwareComparator());
	//	for (Software software : softwareattivi) {
	//	    boolean toAdd = true;
	//	    for (Inventarioprocedimentisoftware inventarioprocedimentisoftware : inventarioprocedimentisoftwares) {
	//		if (software.getCodice().equals(inventarioprocedimentisoftware.getId().getModulosoftware())) {
	//		    toAdd = false;
	//		    break;
	//		}
	//	    }
	//	    if (toAdd) {
	//		softwateDaConfigurare.add(software);
	//	    }
	//	}
	//	return softwateDaConfigurare;
    }
}
