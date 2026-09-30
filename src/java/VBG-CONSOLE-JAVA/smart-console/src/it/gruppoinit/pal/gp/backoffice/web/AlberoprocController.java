/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.opensaml.artifact.InvalidArgumentException;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.FoArjStepsTestata;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Prodotto;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocDocumentiComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocEndoComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocLeggiComparator;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocProtAndFascHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TipoDownload;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocCommand;
import it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni.ComuniEsclusi;
import it.gruppoinit.pal.gp.core.service.AlberoprocArendoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocAtecoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocComuniEsclusiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocD2modtattService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDyn2modellitService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoLocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocLeggiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocOneriService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiincompService;
import it.gruppoinit.pal.gp.core.service.LdpDecodificheService;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ProdottoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo2Service;
import it.gruppoinit.pal.gp.core.service.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipisoggettoService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * @author francescop
 * @author gianpaolot
 * @author lucap
 * 
 */
@Controller
@SessionAttributes(value = { "alberoproc", "alberoprocLeggi", "alberoprocDocumenti", "alberoprocOneri", "alberoprocEndo", "alberoprocDyn2modellit",
	"alberoprocCommand", "alberoprocArendo", "alberoprocD2modtatt", "alberoprocprotocollo", "stpEndoTipo2", "alberoprocEndoLoc" })
public class AlberoprocController extends BaseController<Alberoproc> {

    private static final Logger log = LoggerFactory.getLogger(AlberoprocController.class);
    private static final String FLAG_PUBBLICA = "flag_pubblica";
    private static final String FLAG_PRINCIPALE = "flag_principale";
    private static final String FLAG_PROPOSTO = "flag_proposto";
    private static final String FLAG_RICHESTO_BACK = "flag_richiesto_bo";
    private static final String FLAG_INTERVENTO = "flag_intervento";
    private static final String FLAG_NECESSARIO = "flag_necessario";
    private static final String FLAG_RICHIEDEENDO = "flag_richiede_endo";
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AlberoprocComuniEsclusiService alberoprocComuniEsclusiService;
    @Autowired
    private AlberoprocD2modtattService alberoprocD2modtattService;
    @Autowired
    private AlberoprocTipisoggettoService alberoprocTipisoggettoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private StpTipologieEndo2Service stpTipologieEndo2Service;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private AlberoprocLeggiService alberoprocLeggiService;
    @Autowired
    private AlberoprocDocumentiService alberoprocDocumentiService;
    @Autowired
    private AlberoprocOneriService alberoprocOneriService;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private AlberoprocDocumenticatService alberoprocDocumenticatService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private LeggiService leggiService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private AlberoprocDyn2modellitService alberoprocDyn2modellitService;
    @Autowired
    private VwAlberoprocService vwAlberoprocService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocedimentiincompService inventarioprocedimentiincompService;
    @Autowired
    private AlberoprocAtecoService alberoprocAtecoService;
    @Autowired
    private AlberoprocArendoService alberoprocArendoService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private TipisoggettoService tipisoggettoService;
    @Autowired
    private AlberoprocProtocolloService alberoprocProtocolloService;
    @Autowired
    private AlberoprocEndoLocService alberoprocEndoLocService;
    @Autowired
    private ProdottoService prodottoService;
    @Autowired
    private LdpDecodificheService ldpDecodificheService;

    /**
     * FUNZIONALITA' ALBEROPROC_RUOLI
     */
    @RequestMapping
    public String createRuoli(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	 */
	ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
	Set<AlberoprocRuoli> alberoprocRuolis = alberoproc.getAlberoprocRuolis();
	List<Ruoli> ruolis = ruoliService.findAll(null, null);
	String scCodice = alberoproc.getScCodice();
	int lengthPadre = scCodice.length();
	int lengthTree = lengthPadre / 2;
	Set<Ruoli> ruolisPadre = new HashSet<Ruoli>();
	for (int i = 0; i < lengthTree - 1; i++) {
	    lengthPadre = lengthPadre - 2;
	    String sccodicePadre = scCodice.substring(0, lengthPadre);
	    Alberoproc alberoprocTemp = alberoprocService.findByScCodice(ORMHelper.getIdcomunebase(), sccodicePadre);
	    Set<AlberoprocRuoli> alberoprocRuoliTempSet = alberoprocTemp.getAlberoprocRuolis();
	    for (AlberoprocRuoli alberoprocRuoli : alberoprocRuoliTempSet) {
		ruolisPadre.add(alberoprocRuoli.getRuoli());
	    }
	}
	List<Ruoli> ruoliFiglio = new ArrayList<Ruoli>();
	ruoliFiglio.addAll(ruolis);
	for (Ruoli ruoli : ruolis) {
	    for (Ruoli ruoliPadre : ruolisPadre) {
		if (ruoliPadre.getId().getCodice().compareTo(ruoli.getId().getCodice()) == 0) {
		    ruoliFiglio.remove(ruoli);
		}
	    }
	}
	for (Ruoli ruolo : ruoliFiglio) {
	    for (AlberoprocRuoli alberoprocRuoli : alberoprocRuolis) {
		Ruoli ruoloTemp = alberoprocRuoli.getRuoli();
		if (ruoloTemp.getId().getCodice().compareTo(ruolo.getId().getCodice()) == 0) {
		    ruolo.setRuoloAlberoprocTransient(true);
		    break;
		}
	    }
	}
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("ruolisPadre", ruolisPadre);
	model.addAttribute("ruolisFiglio", ruoliFiglio);
	setPageAttributes(model);
	return "alberoproc/ruoli";
    }

    @RequestMapping
    public String saveRuoli(@ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	fixMergeEntityProperty(alberoproc);
	String[] ruolisPadreId = request.getParameterValues("ruoliPadre");
	String[] ruolisFiglioId = request.getParameterValues("ruoliFiglio");
	Set<AlberoprocRuoli> alberoprocRuolis = new HashSet<AlberoprocRuoli>();
	if (ruolisPadreId != null) {
	    for (int i = 0; i < ruolisPadreId.length; i++) {
		Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(ruolisPadreId[i])));
		AlberoprocRuoliId id = new AlberoprocRuoliId();
		id.setFkScId(alberoproc.getId().getCodice());
		id.setFkIdruolo(ruoli.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		AlberoprocRuoli alberoprocRuoliTemp = new AlberoprocRuoli();
		alberoprocRuoliTemp.setAlberoproc(alberoproc);
		alberoprocRuoliTemp.setRuoli(ruoli);
		alberoprocRuoliTemp.setId(id);
		alberoprocRuolis.add(alberoprocRuoliTemp);
	    }
	}
	if (ruolisFiglioId != null) {
	    for (int i = 0; i < ruolisFiglioId.length; i++) {
		Ruoli ruoli = ruoliService.findById(new PkId(Integer.parseInt(ruolisFiglioId[i])));
		AlberoprocRuoliId id = new AlberoprocRuoliId();
		id.setFkScId(alberoproc.getId().getCodice());
		id.setFkIdruolo(ruoli.getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		AlberoprocRuoli alberoprocRuoliTemp = new AlberoprocRuoli();
		alberoprocRuoliTemp.setAlberoproc(alberoproc);
		alberoprocRuoliTemp.setRuoli(ruoli);
		alberoprocRuoliTemp.setId(id);
		alberoprocRuolis.add(alberoprocRuoliTemp);
	    }
	}
	try {
	    alberoprocService.saveRuoli(alberoproc, alberoprocRuolis);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproc, e);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/ruoli";
	}
	status.setComplete();
	return "redirect:createRuoli.htm?alberoproc.id.codice=" + alberoproc.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String list(Model model) {

	Alberoproc alberoproc = new Alberoproc();
	model.addAttribute("alberoproc", alberoproc);
	return "alberoproc/list";
    }

    /**
     * FUNZIONALITA' ALBEROPROC
     */
    @RequestMapping
    public String create(@RequestParam("codicepadre") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoprocPadre = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	model.addAttribute("alberoprocPadre", alberoprocPadre);
	Alberoproc alberoproc = new Alberoproc();
	if (alberoprocPadre == null) {
	    alberoproc.setAreaPrimaria(true);
	}
	alberoproc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(alberoproc);
	model.addAttribute("alberoproc", alberoproc);
	StpEndoTipo2 s2 = new StpEndoTipo2();
	s2.setTipo("ENDO");
	model.addAttribute("stpEndoTipo2_loc", s2);
	model.addAttribute("entiEsclusiPresenti", false);
	this.setPageAttributes(model, null, request);
	return "alberoproc/form";
    }

    @RequestMapping
    public String spostaVoceAlbero(@RequestParam("sorgente") Integer sorgente, @RequestParam("destinazione") Integer destinazione, Model model,
	    HttpServletRequest request) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(resp.getAmministratore(), "N").equalsIgnoreCase("N")) {
	    log.error("spostaVoceAlbero: L'operatore {} non può eseguire questa funzionalità amministrativa", resp.getResponsabile());
	    throw new SecurityException("L'operatore " + resp.getResponsabile() + " non può eseguire questa funzionalità amministrativa");
	}
	try {
	    alberoprocService.spostaVoceAlbero(ORMHelper.getIdcomunebase(), sorgente, destinazione);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	}
	return "redirect:list.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware() + "&_ts=" + System.currentTimeMillis();
    }

    @RequestMapping
    public String insert(@RequestParam("codicepadre") Integer codice, Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Alberoproc alberoprocPadre = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoprocPadre", alberoprocPadre);
	this.setPageAttributes(model, null, request);
	// recupero l'oggetto passato (recupero del file oggettoworkflow)
	if (EntityUtils.getNestedProperty(alberoproc.getOggettoWorkflowAreaRis(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(alberoproc.getOggettoWorkflowAreaRis().getId().getCodice()));
	    alberoproc.setOggettoWorkflowAreaRis(oggetti);
	}
	sistemaRequest(alberoproc, request);
	fixMergeEntityProperty(alberoproc);
	alberoproc.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	StpEndoTipo2 s2 = recuperaStpEndoDaRequest(request);
	try {
	    alberoprocService.insertAlberoproc(alberoproc, alberoprocPadre, s2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproc, e);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=01";
    }

    private StpEndoTipo2 recuperaStpEndoDaRequest(HttpServletRequest request) {

	StpEndoTipo2 s2 = new StpEndoTipo2();
	String codice = (String) request.getParameter("stp_endo_codice");
	codice = StringUtils.defaultString(codice);
	Integer codiceInt = null;
	if (Utilities.isInteger(codice)) {
	    codiceInt = Integer.parseInt(codice);
	    s2.getId().setCodice(codiceInt);
	}
	String tipologia2 = (String) request.getParameter("stp_endo_stpTipologieEndo2_codice");
	tipologia2 = StringUtils.defaultString(tipologia2);
	Integer codiceTipologia2 = null;
	if (Utilities.isInteger(tipologia2)) {
	    codiceTipologia2 = Integer.parseInt(tipologia2);
	    StpTipologieEndo2 stpt2 = stpTipologieEndo2Service.findById(new PkId(ORMHelper.getIdcomunebase(), codiceTipologia2));
	    s2.setStpTipologieEndo2(stpt2);
	}
	String tipo = (String) request.getParameter("stp_endo_tipo");
	String codiceEndoRegionale = (String) request.getParameter("stp_endo_codiceEndoRegionale");
	s2.setCodiceEndoRegionale(codiceEndoRegionale);
	s2.setTipo(tipo);
	return s2;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(ORMHelper.getIdcomunebase(), codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Recupero Normative e Documenti ereditate
	 */
	this.setPageAttributes(model, alberoproc, request);
	// questa find è necessaria perchè nel metodo sopra (setPageAttributes) ci sono dei service
	// che 'catchano' le eventuali eccezioni. Quando 'catchi' un'eccezione in un service la sessione di hibernate è svuotata
	// per cui è necessario ricaricarla.
	alberoproc = alberoprocService.findById(id);
	model.addAttribute("entiEsclusiPresenti", this.alberoprocComuniEsclusiService.entiEsclusiPresenti(alberoproc.getScCodice()));
	fixRenderEntityProperty(alberoproc);
	model.addAttribute("alberoproc", alberoproc);
	List<Azioni> azionis = this.azioniService.findAll(null, null);
	model.addAttribute("azionis", azionis);
	setPageAttributes(model);
	return "alberoproc/form";
    }

    private void sistemaRequest(Alberoproc alberoproc, HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("responsabile.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("responsabile.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setResponsabile(responsabili);
	} else {
	    alberoproc.setResponsabile(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("respistruttoria.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("respistruttoria.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setRespistruttoria(responsabili);
	} else {
	    alberoproc.setRespistruttoria(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("operatoreStc.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("operatoreStc.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    alberoproc.setOperatoreStc(responsabili);
	} else {
	    alberoproc.setOperatoreStc(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpOccupazionis.id.codice"))) {
	    String cod = (String) request.getParameter("ldpOccupazionis.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpOccupazionis(c);
	    }
	} else {
	    alberoproc.setLdpOccupazionis(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpPeriodis.id.codice"))) {
	    String cod = (String) request.getParameter("ldpPeriodis.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpPeriodis(c);
	    }
	} else {
	    alberoproc.setLdpPeriodis(null);
	}
	if (StringUtils.isNotBlank((String) request.getParameter("ldpGeometries.id.codice"))) {
	    String cod = (String) request.getParameter("ldpGeometries.id.codice");
	    if (Utilities.isInteger(cod)) {
		LdpDecodifiche c = ldpDecodificheService.findById(new PkId(Integer.parseInt(cod)));
		alberoproc.setLdpGeometries(c);
	    }
	} else {
	    alberoproc.setLdpGeometries(null);
	}
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	sistemaRequest(alberoproc, request);
	// recupero l'oggetto passato (recupero del file oggettoworkflow)
	if (EntityUtils.getNestedProperty(alberoproc.getOggettoWorkflowAreaRis(), "id.codice") != null) {
	    Oggetti oggetti = oggettiService.findById(new PkId(alberoproc.getOggettoWorkflowAreaRis().getId().getCodice()));
	    alberoproc.setOggettoWorkflowAreaRis(oggetti);
	}
	fixMergeEntityProperty(alberoproc);
	try {
	    StpEndoTipo2 s2 = recuperaStpEndoDaRequest(request);
	    alberoprocService.updateAlberoproc(alberoproc, s2);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoproc, e);
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("alberoproc") Alberoproc alberoproc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Alberoproc objToDelete = alberoprocService.findById(alberoproc.getId());
	try {
	    alberoprocService.delete(objToDelete);
	} catch (Exception e) {
	    this.setPageAttributes(model, objToDelete, request);
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(objToDelete);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:list.htm?status_msg=05";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_LEGGI
     */
    @RequestMapping
    public String createLeggi(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(ORMHelper.getIdcomunebase(), codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	List<Leggi> leggis = leggiService.findAllWithOrder("leggitipi.id.codice");
	List<AlberoprocLeggi> alberoprocLeggis = alberoprocLeggiService.findByAlberoProc(ORMHelper.getIdcomunebase(), codice);
	List<Leggi> leggiList = new ArrayList<Leggi>();
	leggiList.addAll(leggis);
	if (!alberoprocLeggis.isEmpty()) {
	    for (Leggi leggi : leggis) {
		for (AlberoprocLeggi alberoprocLeggi : alberoprocLeggis) {
		    if (alberoprocLeggi.getLegge().getId().getCodice().compareTo(leggi.getId().getCodice()) == 0) {
			leggiList.remove(leggi);
		    }
		}
	    }
	}
	AlberoprocLeggi alberoprocLeggi = new AlberoprocLeggi();
	alberoprocLeggi.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocLeggi", alberoprocLeggi);
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("leggiList", leggiList);
	setPageAttributes(model);
	return "alberoproc/leggi";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ARENDO
     */
    @RequestMapping
    public String createArendo(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	AlberoprocArendo alberoprocArendo = new AlberoprocArendo();
	alberoprocArendo.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocArendo", alberoprocArendo);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityPropertyAlberoprocArendo(alberoprocArendo);
	return "alberoproc/arendo";
    }

    @RequestMapping
    public String insertArendo(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocArendo") AlberoprocArendo alberoprocArendo, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	if (EntityUtils.getNestedProperty(alberoprocArendo.getTipiendo(), "id.codice") != null) {
	    Tipiendo tipiendo = tipiendoService.findById(new PkId(alberoprocArendo.getTipiendo().getId().getCodice()));
	    alberoprocArendo.setTipiendo(tipiendo);
	}
	if (EntityUtils.getNestedProperty(alberoprocArendo.getTipifamiglieendo(), "id.codice") != null) {
	    Tipifamiglieendo tipifamiglieendo = tipifamiglieendoService
		    .findById(new PkId(alberoprocArendo.getTipifamiglieendo().getId().getCodice()));
	    alberoprocArendo.setTipifamiglieendo(tipifamiglieendo);
	}
	Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), alberoproc.getId().getCodice()));
	alberoprocArendo.setAlberoproc(ap);
	try {
	    alberoprocArendoService.insert(alberoprocArendo);
	} catch (Exception e) {
	    fixRenderEntityPropertyAlberoprocArendo(alberoprocArendo);
	    copyErrorsToBindingResult(result, alberoprocArendo, e);
	    model.addAttribute("alberoprocArendo", alberoprocArendo);
	    ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), alberoproc.getId().getCodice()));
	    model.addAttribute("alberoproc", ap);
	    return "alberoproc/arendo";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#arendo_anchor";
    }

    @RequestMapping
    public String insertLegge(@ModelAttribute("alberoproc") Alberoproc alberoproc, @ModelAttribute("alberoprocLeggi") AlberoprocLeggi alberoprocLeggi,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {

	try {
	    alberoprocLeggiService.insert(alberoprocLeggi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocLeggi, e);
	    return "alberoproc/leggi";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#legge_anchor";
    }

    @RequestMapping
    public String eliminaLegge(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicelegge") Integer codice, HttpServletRequest request) {

	AlberoprocLeggi alberoprocLeggi = alberoprocLeggiService.findById(new PkId(codice));
	try {
	    alberoprocLeggiService.delete(alberoprocLeggi);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    @RequestMapping
    public String eliminaArendo(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicearendo") Integer codice, HttpServletRequest request) {

	AlberoprocArendo alberoprocArendo = alberoprocArendoService.findById(new PkId(codice));
	try {
	    alberoprocArendoService.delete(alberoprocArendo);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    copyErrorsToFlashMessages(alberoproc, true, "alberoproc", e);
	    return "redirect:view.htm?codice=" + codicealberoproc + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    //////////////////////////////////
    /**
     * FUNZIONALITA' ALBEROPROC_TIPISOGGETTO
     */
    @RequestMapping
    public String createTipisoggetto(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocTipisoggetto alberoprocTipisoggetto = new AlberoprocTipisoggetto();
	alberoprocTipisoggetto.setAlberoproc(alberoproc);
	model.addAttribute("alberoprocTipisoggetto", alberoprocTipisoggetto);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	fixRenderEntityPropertyAlberoprocTipisoggetto(alberoprocTipisoggetto);
	return "alberoproc/alberoproctipisoggetto";
    }

    @RequestMapping
    public String insertTipisoggetto(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocTipisoggetto") AlberoprocTipisoggetto alberoprocTipisoggetto, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request) {

	if (EntityUtils.getNestedProperty(alberoprocTipisoggetto.getTipisoggetto(), "id.codice") != null) {
	    Tipisoggetto tipifamiglieendo = tipisoggettoService.findById(new PkId(alberoprocTipisoggetto.getTipisoggetto().getId().getCodice()));
	    alberoprocTipisoggetto.setTipisoggetto(tipifamiglieendo);
	}
	try {
	    alberoprocTipisoggettoService.insert(alberoprocTipisoggetto);
	} catch (Exception e) {
	    fixRenderEntityPropertyAlberoprocTipisoggetto(alberoprocTipisoggetto);
	    copyErrorsToBindingResult(result, alberoprocTipisoggetto, e);
	    model.addAttribute("alberoprocTipisoggetto", alberoprocTipisoggetto);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/alberoproctipisoggetto";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "#artipisoggetto_anchor";
    }

    private void fixRenderEntityPropertyAlberoprocTipisoggetto(AlberoprocTipisoggetto alberoprocTipisoggetto) {

	if (alberoprocTipisoggetto.getTipisoggetto() == null) {
	    alberoprocTipisoggetto.setTipisoggetto(new Tipisoggetto());
	}
	if (alberoprocTipisoggetto.getAlberoproc() == null) {
	    alberoprocTipisoggetto.setAlberoproc(new Alberoproc());
	}
    }

    @RequestMapping
    public String deleteTiposoggetto(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codice") Integer codice, HttpServletRequest request) {

	AlberoprocTipisoggetto alberoprocTipisoggetto = alberoprocTipisoggettoService.findById(new PkId(codice));
	try {
	    alberoprocTipisoggettoService.delete(alberoprocTipisoggetto);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codice=" + codicealberoproc + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    //////////////////////////////////
    @RequestMapping
    public String eliminaAteco(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codiceateco") Integer codiceateco, HttpServletRequest request) {

	AlberoprocAtecoId id = new AlberoprocAtecoId(codicealberoproc, codiceateco);
	AlberoprocAteco alberoprocAteco = alberoprocAtecoService.findById(id);
	try {
	    alberoprocAtecoService.delete(alberoprocAteco);
	} catch (Exception e) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    /**
     * FUNZIONALITA' ALBEROPROC_DOCUMENTI
     */
    @RequestMapping
    public String createDocumenti(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	/*
	 * Determino l'ordine massimo e lo incremento di 10. L'incremento viene utilizzato per facilitare lo spostamento
	 * dei vari ordini.
	 */
	Integer ordine = 0;
	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocDocumenti alberoprocDocumenti = new AlberoprocDocumenti();
	alberoprocDocumenti.setAlberoproc(alberoproc);
	alberoprocDocumenti.setOrdine(ordine);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	return "alberoproc/documenti";
    }

    @RequestMapping
    public String insertDocumenti(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocDocumenti") AlberoprocDocumenti alberoprocDocumenti, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	String[] valueType = request.getParameterValues("tipoDownloads");
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	try {
	    String foTipiDownload = "";
	    if (valueType != null) {
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		alberoprocDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		alberoprocDocumenti.setFoTipodownload(null);
	    }
	    alberoprocDocumentiService.insert(alberoprocDocumenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenti, e);
	    fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	    return "alberoproc/documenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?alberoprocDocumenti.id.codice=" + alberoprocDocumenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewDocumenti(@RequestParam("alberoprocDocumenti.id.codice") Integer codice, Model model, HttpServletRequest request) {

	/*
	 * Recupero le categorie. In view viene effettuato il controllo se sono presenti categorie per il software
	 * corrente.
	 */
	List<AlberoprocDocumenticat> alberoprocDocumenticats = alberoprocDocumenticatService.findAll(null, null);
	model.addAttribute("alberoprocDocumenticats", alberoprocDocumenticats);
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codice));
	if (alberoprocDocumenti.getFoTipodownload() != null) {
	    String[] tipodown = alberoprocDocumenti.getFoTipodownload().split(",");
	    Set<TipoDownload> tipoDownloadList = TipoDownload.fromString(tipodown, tipoDownloads);
	    alberoprocDocumenti.setTipoDownloads(tipoDownloadList);
	}
	fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	model.addAttribute("alberoprocDocumenti", alberoprocDocumenti);
	Alberoproc alberoprocTemp = alberoprocService.findById(alberoprocDocumenti.getAlberoproc().getId());
	model.addAttribute("alberoproc", alberoprocTemp);
	return "alberoproc/documenti";
    }

    private void fixRenderEntityPropertyAlberoprocDocumenti(AlberoprocDocumenti entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getAlberoprocDocumenticat() == null) {
	    entity.setAlberoprocDocumenticat(new AlberoprocDocumenticat());
	}
    }

    private void fixRenderEntityPropertyAlberoprocArendo(AlberoprocArendo entity) {

	if (entity.getTipiendo() == null) {
	    entity.setTipiendo(new Tipiendo());
	}
	if (entity.getTipifamiglieendo() == null) {
	    entity.setTipifamiglieendo(new Tipifamiglieendo());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    @RequestMapping
    public String updateDocumenti(@ModelAttribute("alberoproc") Alberoproc alberoproc,
	    @ModelAttribute("alberoprocDocumenti") AlberoprocDocumenti alberoprocDocumenti, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request) {

	String[] valueType = request.getParameterValues("tipoDownloads");
	/*
	 * Creo oggetto Tipodowload e popolo la lista di AlberoprocDocumenti
	 */
	List<TipoDownload> tipoDownloads = TipoDownload.getTipoDownloads();
	model.addAttribute("tipoDownloads", tipoDownloads);
	try {
	    if (valueType != null) {
		String foTipiDownload = "";
		for (String type : valueType) {
		    foTipiDownload = foTipiDownload.concat(type) + ",";
		}
		alberoprocDocumenti.setFoTipodownload(foTipiDownload);
	    } else {
		alberoprocDocumenti.setFoTipodownload(null);
	    }
	    alberoprocDocumentiService.update(alberoprocDocumenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDocumenti, e);
	    fixRenderEntityPropertyAlberoprocDocumenti(alberoprocDocumenti);
	    return "alberoproc/documenti";
	}
	status.setComplete();
	return "redirect:viewDocumenti.htm?alberoprocDocumenti.id.codice=" + alberoprocDocumenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String eliminaDocumento(Model model, @RequestParam("alberoproc.id.codice") Integer codicealberoproc,
	    @RequestParam("codicedocumento") Integer codice, HttpServletRequest request) {

	AlberoprocDocumenti alberoprocDocumenti = alberoprocDocumentiService.findById(new PkId(codice));
	try {
	    alberoprocDocumentiService.delete(alberoprocDocumenti);
	} catch (Exception e) {
	    log.error("eliminaDocumento codicealberoproc={}, codicedocumento={}", new Object[] { codicealberoproc, codice, e });
	    FlashMessages.getWarnings().add(e.getMessage());
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(codicealberoproc));
	    this.setPageAttributes(model, alberoproc, request);
	    fixRenderEntityProperty(alberoproc);
	    model.addAttribute("alberoproc", alberoproc);
	    return "alberoproc/form";
	}
	return "redirect:view.htm?codice=" + codicealberoproc;
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ONERI
     */
    @RequestMapping
    public ModelMap listOneri(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	List<AlberoprocOneri> alberoprocOneris = alberoprocOneriService.findAllByAlberoproc(ORMHelper.getIdcomunebase(), codice);
	ModelMap model = new ModelMap(alberoprocOneris);
	model.addAttribute("alberoprocOneriList", alberoprocOneris);
	model.addAttribute("alberoproc", alberoprocTemp);
	return model;
    }

    @RequestMapping
    public String deleteOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status) {

	AlberoprocOneri objToDelete = alberoprocOneriService.findById(alberoprocOneri.getId());
	try {
	    alberoprocOneriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "alberoproc/form";
	}
	status.setComplete();
	return "redirect:listOneri.htm?alberoproc.id.codice=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    @RequestMapping
    public String insertOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status) {

	fixMergeEntityPropertyOneri(alberoprocOneri);
	try {
	    alberoprocOneriService.insert(alberoprocOneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocOneri, e);
	    fixRenderEntityPropertyOneri(alberoprocOneri);
	    return "alberoproc/oneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?alberoprocOneri.id.codice=" + alberoprocOneri.getId().getCodice() + "&status_msg=01";
    }

    private void fixRenderEntityPropertyOneri(AlberoprocOneri entity) {

	if (entity.getTipicausalioneri() == null) {
	    entity.setTipicausalioneri(new Tipicausalioneri());
	}
    }

    private void fixMergeEntityPropertyOneri(AlberoprocOneri entity) {

	if (entity.getTipicausalioneri() != null && entity.getTipicausalioneri().getId() != null
		&& entity.getTipicausalioneri().getId().getCodice() == null) {
	    entity.setTipicausalioneri(null);
	}
    }

    @RequestMapping
    public String updateOneri(Model model, @ModelAttribute("alberoprocOneri") AlberoprocOneri alberoprocOneri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityPropertyOneri(alberoprocOneri);
	try {
	    alberoprocOneriService.update(alberoprocOneri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocOneri, e);
	    fixRenderEntityPropertyOneri(alberoprocOneri);
	    return "alberoproc/oneri";
	}
	status.setComplete();
	return "redirect:viewOneri.htm?alberoprocOneri.id.codice=" + alberoprocOneri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createOneri(@RequestParam("alberoproc.id.codice") Integer codice, Model model) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(codice));
	AlberoprocOneri alberoprocOneri = new AlberoprocOneri();
	alberoprocOneri.setAlberoproc(alberoprocTemp);
	fixRenderEntityPropertyOneri(alberoprocOneri);
	Boolean isImportoIstruttoriaImpostabile = Boolean.FALSE;
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	model.addAttribute("alberoprocOneri", alberoprocOneri);
	setPageAttributes(model);
	return "alberoproc/oneri";
    }

    @RequestMapping
    public String viewOneri(@RequestParam("alberoprocOneri.id.codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocOneri alberoprocOneri = alberoprocOneriService.findById(id);
	fixRenderEntityPropertyOneri(alberoprocOneri);
	Alberoproc alberoprocTemp = alberoprocService.findById(alberoprocOneri.getAlberoproc().getId());
	model.addAttribute("alberoproc", alberoprocTemp);
	model.addAttribute("alberoprocOneri", alberoprocOneri);
	Boolean isImportoIstruttoriaImpostabile = tipicausalioneriService.isImportoIstruttoriaImpostabile(alberoprocOneri.getTipicausalioneri());
	model.addAttribute("isImportoIstruttoriaImpostabile", isImportoIstruttoriaImpostabile);
	setPageAttributes(model);
	return "alberoproc/oneri";
    }

    /**
     * FUNZIONALITA' ALBEROPROC_ENDO
     */
    @RequestMapping
    public String createEndo(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	List<Azioni> azionis = new ArrayList<Azioni>();
	azionis = azioniService.findAll(null, null);
	model.addAttribute("azionis", azionis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	AlberoprocEndo alberoprocEndo = new AlberoprocEndo();
	AlberoprocEndoId id = new AlberoprocEndoId();
	id.setFkscid(codice);
	alberoprocEndo.setId(id);
	alberoprocEndo.setAlberoproc(alberoproc);
	fixRenderEntityPropertyEndo(alberoprocEndo);
	model.addAttribute("alberoprocEndo", alberoprocEndo);
	model.addAttribute("isNew", true);
	return "alberoproc/endo";
    }

    @RequestMapping
    public String editEndo(@RequestParam("aProcId") Integer codiceAlberoproc, @RequestParam("invProcId") Integer codiceInventario,
	    @RequestParam("idComune") String idComune, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	model.addAttribute("alberoproc", alberoproc);
	AlberoprocEndoId id = new AlberoprocEndoId();
	id.setFkscid(codiceAlberoproc);
	id.setIdcomune(idComune);
	id.setCodiceinventario(codiceInventario);
	AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(id);
	if (alberoprocEndo != null) {
	    fixRenderEntityPropertyEndo(alberoprocEndo);
	    model.addAttribute("alberoprocEndo", alberoprocEndo);
	    model.addAttribute("isNew", false);
	    return "alberoproc/endo";
	} else {
	    return "redirect:view.htm?codice=" + codiceAlberoproc + "&status_msg=03";
	}
    }

    @RequestMapping
    public String insertEndo(@RequestParam("alberoproc.id.codice") Integer codice, Model model,
	    @ModelAttribute("alberoprocEndo") AlberoprocEndo alberoprocEndo, BindingResult result, SessionStatus status, HttpServletRequest request) {

	List<Azioni> azionis = new ArrayList<Azioni>();
	if (!ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_CE)) {
	    azionis = azioniService.findAll(null, null);
	}
	model.addAttribute("azionis", azionis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	fixMergeEntityPropertyEndo(alberoprocEndo);
	String msg = "";
	try {
	    if (request.getParameter("inventarioprocedimento.procedimento") != null) {
		alberoprocEndoService.insert(alberoprocEndo);
		msg = "01";
	    } else {
		alberoprocEndoService.update(alberoprocEndo);
		msg = "02";
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocEndo, e);
	    fixRenderEntityPropertyEndo(alberoprocEndo);
	    alberoprocEndo.setInventarioprocedimento(new Inventarioprocedimenti());
	    model.addAttribute("alberoprocEndo", alberoprocEndo);
	    return "alberoproc/endo";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=" + msg;
    }

    private void fixRenderEntityPropertyEndo(AlberoprocEndo entity) {

	if (entity.getInventarioprocedimento() == null) {
	    entity.setInventarioprocedimento(new Inventarioprocedimenti());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getAzione() == null) {
	    entity.setAzione(new Azioni());
	}
    }

    private void fixMergeEntityPropertyEndo(AlberoprocEndo entity) {

	if (entity.getInventarioprocedimento() != null && entity.getInventarioprocedimento().getId() != null
		&& entity.getInventarioprocedimento().getId().getCodice() == null) {
	    entity.setInventarioprocedimento(null);
	}
	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
	if (entity.getAzione() != null && entity.getAzione().getAzId() != null) {
	    if (entity.getAzione().getAzId().compareTo(0) == 0) {
		entity.setAzione(null);
	    }
	} else {
	    entity.setAzione(null);
	}
    }

    @RequestMapping
    public ModelMap listEndo(@RequestParam("alberoproc.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoprocTemp = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findAllByAlberoproc(ORMHelper.getIdcomunebase(), codice);
	ModelMap model = new ModelMap(alberoprocEndos);
	model.addAttribute("alberoprocEndosList", alberoprocEndos);
	model.addAttribute("alberoproc", alberoprocTemp);
	return model;
    }

    @RequestMapping
    public String deleteEndo(Model model, @RequestParam("alberoproc.id.codice") Integer codiceAlberoproc,
	    @RequestParam("codiceinventario") Integer codiceInventario, HttpServletRequest request) {

	AlberoprocEndoId id = new AlberoprocEndoId();
	id.setFkscid(codiceAlberoproc);
	id.setCodiceinventario(codiceInventario);
	AlberoprocEndo objToDelete = alberoprocEndoService.findById(id);
	try {
	    alberoprocEndoService.delete(objToDelete);
	} catch (Exception e) {
	    return "redirect:listEndo.htm?alberoproc.id.codice=" + objToDelete.getAlberoproc().getId().getCodice();
	}
	return "redirect:view.htm?codice=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    // gestione ALBEROPROC_ENDO_LOC (INTERVENTI)
    @RequestMapping
    public String createIntervento(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	model.addAttribute("alberoproc", alberoproc);
	AlberoprocEndoLoc alberoprocEndoLoc = new AlberoprocEndoLoc();
	alberoprocEndoLoc.setAlberoproc(alberoproc);
	alberoprocEndoLoc.setFlagIntervento(Boolean.TRUE);
	fixRenderEntityPropertyEndoLoc(alberoprocEndoLoc);
	model.addAttribute("alberoprocEndoLoc", alberoprocEndoLoc);
	return "alberoproc/endolocinterventi";
    }

    @RequestMapping
    public String createEndoLoc(@RequestParam("alberoproc.id.codice") Integer codice, Model model, HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	model.addAttribute("alberoproc", alberoproc);
	AlberoprocEndoLoc alberoprocEndoLoc = new AlberoprocEndoLoc();
	alberoprocEndoLoc.setAlberoproc(alberoproc);
	alberoprocEndoLoc.setFlagIntervento(Boolean.FALSE);
	fixRenderEntityPropertyEndoLoc(alberoprocEndoLoc);
	model.addAttribute("alberoprocEndoLoc", alberoprocEndoLoc);
	setPageAttributes(model);
	return "alberoproc/endoloc";
    }

    @RequestMapping
    public String insertEndoLoc(@RequestParam("alberoproc.id.codice") Integer codice,
	    @RequestParam(required = false, value = "isIdcomunebase") Boolean isIdcomunebase, Model model,
	    @ModelAttribute("alberoprocEndoLoc") AlberoprocEndoLoc alberoprocEndoLoc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	model.addAttribute("alberoproc", alberoproc);
	fixMergeEntityPropertyEndo(alberoprocEndoLoc);
	try {
	    alberoprocEndoLoc.setFlagIntervento(Boolean.FALSE);
	    alberoprocEndoLocService.insert(alberoprocEndoLoc, isIdcomunebase);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(alberoprocEndoLoc, false, "alberoprocEndoLoc", e);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String insertIntervento(@RequestParam("alberoproc.id.codice") Integer codice, Model model,
	    @ModelAttribute("alberoprocEndoLoc") AlberoprocEndoLoc alberoprocEndoLoc, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codice));
	model.addAttribute("alberoproc", alberoproc);
	try {
	    alberoprocEndoLoc.setFlagIntervento(Boolean.TRUE);
	    alberoprocEndoLocService.insert(alberoprocEndoLoc);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(alberoprocEndoLoc, false, "alberoprocEndoLoc", e);
	    status.setComplete();
	    return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + alberoproc.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String deleteInterventoEndo(Model model, @RequestParam("alberoproc.id.codice") Integer codiceAlberoproc,
	    @RequestParam("codice") Integer codice, HttpServletRequest request) {

	AlberoprocEndoLoc objToDelete = alberoprocEndoLocService.findById(new PkId(codice));
	try {
	    alberoprocEndoLocService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(objToDelete, false, "alberoprocEndoLoc", e);
	    return "redirect:view.htm?codice=" + codiceAlberoproc + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAlberoproc + "&status_msg=05";
    }

    @RequestMapping
    public String deleteEndoLoc(Model model, @RequestParam("alberoproc.id.codice") Integer codiceAlberoproc, @RequestParam("codice") Integer codice,
	    HttpServletRequest request) {

	return deleteInterventoEndo(model, codiceAlberoproc, codice, request);
    }

    private void fixRenderEntityPropertyEndoLoc(AlberoprocEndoLoc entity) {

	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    private void fixMergeEntityPropertyEndo(AlberoprocEndoLoc entity) {

	if (entity.getInventarioprocedimenti() != null && entity.getInventarioprocedimenti().getId() != null
		&& entity.getInventarioprocedimenti().getId().getCodice() == null) {
	    entity.setInventarioprocedimenti(null);
	}
	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
	if (entity.getComune() == null || (StringUtils.isBlank(entity.getComune().getCodicecomune()))) {
	    entity.setComune(null);
	}
    }

    // GESTIONE DEI MODELLI
    @RequestMapping
    public ModelMap listmodelli(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<AlberoprocDyn2modellit> alberoprocDyn2modellits = alberoprocDyn2modellitService.findByAlberoProc(codice);
	ModelMap model = new ModelMap(alberoprocDyn2modellits);
	boolean export = createJMesaExport(request, response, alberoprocDyn2modellits);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoprocDyn2modellits", alberoprocDyn2modellits);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public ModelMap comuniesclusi(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<ComuniEsclusi> comuniEsclusi = this.alberoprocComuniEsclusiService.findByAlberoProc(alberoproc.getScCodice());
	ModelMap model = new ModelMap(comuniEsclusi);
	model.addAttribute("comuniEsclusi", comuniEsclusi);
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("elencoComuni", this.comuniassociatiService.findAll());
	return model;
    }

    @RequestMapping
    public String createmodelli(@RequestParam("codiceprocedimento") Integer codice, Model model) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	AlberoprocDyn2modellit alberoprocDyn2modellit = new AlberoprocDyn2modellit();
	alberoprocDyn2modellit.setAlberoproc(alberoproc);
	// setto l'id
	AlberoprocDyn2modellitId id = new AlberoprocDyn2modellitId();
	id.setFkScId(codice);
	alberoprocDyn2modellit.setId(id);
	fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	setPageAttributes(model);
	return "alberoproc/formModelli";
    }

    @RequestMapping
    public String viewModelli(@RequestParam("codiceprocedimento") Integer codice, @RequestParam("codicemodello") Integer codicemodello, Model model) {

	AlberoprocDyn2modellitId id = new AlberoprocDyn2modellitId(ORMHelper.getIdcomune(), codice, codicemodello);
	AlberoprocDyn2modellit alberoprocDyn2modellit = alberoprocDyn2modellitService.findById(id);
	fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	Boolean flagmultiplo = Boolean.TRUE;
	if (alberoprocDyn2modellit.getFlagTipofirma() != null && alberoprocDyn2modellit.getFlagTipofirma().equals(2)) {
	    if (EntityUtils.getNestedProperty(alberoprocDyn2modellit.getDyn2Modellit(), "id.codice") != null) {
		if (alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids() != null
			&& !alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids().isEmpty()) {
		    Set<Dyn2Modellid> dyn2Modellids = alberoprocDyn2modellit.getDyn2Modellit().getDyn2Modellids();
		    for (Dyn2Modellid dyn2Modellid : dyn2Modellids) {
			if (dyn2Modellid.getFlgMultiplo() == null || !dyn2Modellid.getFlgMultiplo()) {
			    flagmultiplo = Boolean.FALSE;
			}
		    }
		}
	    }
	}
	if (!flagmultiplo) {
	    model.addAttribute("flagmultiplo", flagmultiplo);
	}
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	setPageAttributes(model);
	return "alberoproc/formModelli";
    }

    @RequestMapping
    public String insertModelli(Model model, @ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelli";
	}
	// recupero i campi ajax
	if (alberoprocDyn2modellit.getDyn2Modellit() != null && alberoprocDyn2modellit.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(alberoprocDyn2modellit.getDyn2Modellit().getId());
	    AlberoprocDyn2modellitId id = alberoprocDyn2modellit.getId();
	    id.setFkD2mtId(alberoprocDyn2modellit.getDyn2Modellit().getId().getCodice());
	    alberoprocDyn2modellit.setId(id);
	    alberoprocDyn2modellit.setDyn2Modellit(dyn2Modellit);
	}
	fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocDyn2modellitService.insert(alberoprocDyn2modellit);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocDyn2modellit, e);
	    fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	    model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceprocedimento=" + alberoprocDyn2modellit.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocDyn2modellit.getId().getFkD2mtId() + "&status_msg=01";
    }

    @RequestMapping
    public String updateModelli(Model model, @ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelli";
	}
	fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocDyn2modellitService.update(alberoprocDyn2modellit);
	} catch (Exception e) {
	    model.addAttribute("view", Boolean.TRUE);
	    copyErrorsToBindingResult(result, alberoprocDyn2modellit, e);
	    model.addAttribute("alberoprocDyn2modellit", alberoprocDyn2modellit);
	    fixRenderAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:viewModelli.htm?codiceprocedimento=" + alberoprocDyn2modellit.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocDyn2modellit.getId().getFkD2mtId() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelli(@ModelAttribute("alberoprocDyn2modellit") AlberoprocDyn2modellit alberoprocDyn2modellit, BindingResult result,
	    SessionStatus status) {

	AlberoprocDyn2modellit objToDelete = alberoprocDyn2modellitService.findById(alberoprocDyn2modellit.getId());
	try {
	    alberoprocDyn2modellitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocDyn2modellitProperty(objToDelete);
	    return "alberoproc/formModelli";
	}
	status.setComplete();
	return "redirect:listmodelli.htm?codiceprocedimento=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    // GESTIONE DEGLI ENDOPROCEDIMENTI DI UN PROCEDIMENTO INCOMPATIBILI TRA LORO
    @RequestMapping
    public String listincompatibili(@RequestParam("codiceendo") Integer codice, @RequestParam("codicealberoproc") Integer codiceAlberoproc,
	    Model model, HttpServletRequest request) {

	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(codice));
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoproc));
	VwAlberoproc vwAlberoproc = vwAlberoprocService.findById(new PkId(codiceAlberoproc));
	AlberoprocCommand alberoprocCommand = new AlberoprocCommand();
	Set<AlberoprocEndo> alberoprocEndos = alberoproc.getAlberoprocEndos();
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	// alla collezione degli endo procedimenti configurati per il procedimento devo sottrarre quello che sto///
	// valutando///////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	AlberoprocEndoId id = new AlberoprocEndoId(ORMHelper.getIdcomune(), codiceAlberoproc, codice);
	AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(id);
	alberoprocEndos.remove(alberoprocEndo);
	alberoproc.setAlberoprocEndos(alberoprocEndos);
	// setto tutti i valori di cui ho bisogno sul command
	alberoprocCommand.setInventarioprocedimenti(inventarioprocedimenti);
	alberoprocCommand.setVwAlberoproc(vwAlberoproc);
	alberoprocCommand.setAlberoproc(alberoproc);
	for (AlberoprocEndo alberoprocEndoTemp : alberoprocEndos) {
	    Set<Inventarioprocedimentiincomp> list = inventarioprocedimenti.getInventarioprocedimentiincomps();
	    for (Inventarioprocedimentiincomp inventarioprocedimentiincomp : list) {
		if (inventarioprocedimentiincomp.getInventarioprocedimentoincompatibile().equals(alberoprocEndoTemp.getInventarioprocedimento())) {
		    alberoprocEndoTemp.getInventarioprocedimento().setFlagIncompatibile(true);
		    break;
		}
	    }
	}
	request.setAttribute("endoList", alberoprocCommand.getAlberoproc().getAlberoprocEndos());
	model.addAttribute("alberoprocCommand", alberoprocCommand);
	return "alberoproc/listincompatibili";
    }

    /**
     * Il metodo andrà a rimuovere o aggiungere compatibilità dell'endoprocedimento scelto con gli altri
     * endoproecedimenti configurati sullo stesso procedimento
     * 
     * @param model
     * @param alberoprocDyn2modellit
     * @param result
     * @param status
     * @return
     */
    @RequestMapping
    public String addOrRemoveIncompatibilitaendo(Model model, @ModelAttribute("alberoprocCommand") AlberoprocCommand alberoprocCommand,
	    BindingResult result, SessionStatus status) {

	Inventarioprocedimenti inventarioprocedimenti = alberoprocCommand.getInventarioprocedimenti();
	String codiciDegliEndoIncompatibili = alberoprocCommand.getListaDiCodiciDegliEndoprocedimentiIncompatibili();
	String codiciTotaliDegliEndoPerUnProcedimento = alberoprocCommand.getListaDiCodiciDeiEndoPerUnProcedimento();
	inventarioprocedimentiincompService.addOrRemoveIncompatibilitaendo(inventarioprocedimenti, codiciDegliEndoIncompatibili,
		codiciTotaliDegliEndoPerUnProcedimento);
	status.setComplete();
	return "redirect:listincompatibili.htm?codiceendo=" + inventarioprocedimenti.getId().getCodice() + "&codicealberoproc=" +
	       alberoprocCommand.getAlberoproc().getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Alberoproc entity) {

	if (entity.getTipologiaregistro() != null && entity.getTipologiaregistro().getId() != null
		&& entity.getTipologiaregistro().getId().getCodice() == null) {
	    entity.setTipologiaregistro(null);
	}
	if (entity.getTipoProcedura() != null && entity.getTipoProcedura().getId() != null && entity.getTipoProcedura().getId().getCodice() == null) {
	    entity.setTipoProcedura(null);
	}
	if (entity.getAzione() != null && entity.getAzione().getAzId() == null) {
	    entity.setAzione(null);
	}
	if (entity.getResponsabile() != null && entity.getResponsabile().getId() != null && entity.getResponsabile().getId().getCodice() == null) {
	    entity.setResponsabile(null);
	}
	if (entity.getRespistruttoria() != null && entity.getRespistruttoria().getId() != null
		&& entity.getRespistruttoria().getId().getCodice() == null) {
	    entity.setRespistruttoria(null);
	}
	if (entity.getRiTipiintervento() != null && StringUtils.isBlank(entity.getRiTipiintervento().getCodice())) {
	    entity.setRiTipiintervento(null);
	}
	if (entity.getFoArjStepsTestata() != null && entity.getFoArjStepsTestata().getId() == null
		&& entity.getFoArjStepsTestata().getId().getCodice() != null) {
	    entity.setFoArjStepsTestata(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Alberoproc entity) {

	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
	if (entity.getMercatoUso() == null) {
	    entity.setMercatoUso(new MercatiUso());
	}
	if (entity.getTipologiaregistro() == null) {
	    entity.setTipologiaregistro(new Tipologiaregistri());
	}
	if (entity.getTipoProcedura() == null) {
	    entity.setTipoProcedura(new Tipiprocedure());
	}
	if (entity.getAzione() == null) {
	    entity.setAzione(new Azioni());
	}
	if (entity.getResponsabile() == null) {
	    entity.setResponsabile(new Responsabili());
	}
	if (entity.getRespistruttoria() == null) {
	    entity.setRespistruttoria(new Responsabili());
	}
	if (entity.getOperatoreStc() == null) {
	    entity.setOperatoreStc(new Responsabili());
	}
	if (entity.getOggettoWorkflowAreaRis() == null) {
	    entity.setOggettoWorkflowAreaRis(new Oggetti());
	}
	if (entity.getRiTipiintervento() == null) {
	    entity.setRiTipiintervento(new RiTipiintervento());
	}
	if (entity.getFoArjStepsTestata() == null) {
	    entity.setFoArjStepsTestata(new FoArjStepsTestata());
	}
    }

    private void fixRenderAlberoprocDyn2modellitProperty(AlberoprocDyn2modellit entity) {

	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    private void fixMergeAlberoprocDyn2modellitProperty(AlberoprocDyn2modellit entity) {

	if (entity.getAlberoproc() != null && entity.getAlberoproc().getId() != null && entity.getAlberoproc().getId().getCodice() == null) {
	    entity.setAlberoproc(null);
	}
	if (entity.getDyn2Modellit() != null && entity.getDyn2Modellit().getId() != null && entity.getDyn2Modellit().getId().getCodice() == null) {
	    entity.setDyn2Modellit(null);
	}
    }

    /**
     * Metodo per settare nel model tutti i parametri necessari per la pagina di Alberoproc.<br/>
     * Sostituisce il metodo: protected void setPageAttributes(Model model);
     * 
     * @param model
     * @param entity
     * @param request
     */
    private void setPageAttributes(Model model, Alberoproc entity, HttpServletRequest request) {

	Prodotto p = prodottoService.findProdotto();
	Boolean viewMittente = Boolean.FALSE;
	if (p != null) {
	    if (BooleanUtils.isTrue(p.getFlagModMittente())) {
		viewMittente = Boolean.TRUE;
	    }
	}
	model.addAttribute("viewCampoMittente", viewMittente);
	List<StpTipologieEndo2> l = stpTipologieEndo2Service.findAll(null, null);
	model.addAttribute("stpTipologieEndo2s", l);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO, "0", request);
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "1", request)));
	model.addAttribute("CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE",
		leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE, "0", request));
	/*
	 * Lista AZIONI
	 */
	model.addAttribute("azioniList", azioniService.findAll(null, null));
	model.addAttribute("conf_contatore", false);
	List<LdpDecodifiche> ldpOccupazionis = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.OCCUPAZIONE.name());
	List<LdpDecodifiche> ldpGeometries = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.GEOMETRIA.name());
	List<LdpDecodifiche> ldpPeriodis = ldpDecodificheService.findByContesto(WebConstants.LDP_DECODIFICHE_CONTESTI.PERIODO.name());
	model.addAttribute("ldpOccupazionis", ldpOccupazionis);
	model.addAttribute("ldpGeometries", ldpGeometries);
	model.addAttribute("ldpPeriodis", ldpPeriodis);
	boolean isLdpContesti = false;
	if (!(ldpOccupazionis.isEmpty() || ldpGeometries.isEmpty() || ldpPeriodis.isEmpty())) {
	    isLdpContesti = true;
	}
	model.addAttribute("isLdpContesti", isLdpContesti);
	model.addAttribute("vert_replicaistanze_attivo", false);
	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	boolean verticalizzazioni_PROTOCOLLO_ATTIVO = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO);
	model.addAttribute("vert_prot_attivo", verticalizzazioni_PROTOCOLLO_ATTIVO);
	model.addAttribute("mercati_attivi", false);
	StpEndoTipo2 s2 = new StpEndoTipo2();
	s2.setTipo("ENDO");
	model.addAttribute("stpEndoTipo2_loc", s2);
	if (!EntityUtils.isNestedPropertyBlank(entity, "id.codice")) {
	    /*
	     * Recupero la lista delle endoprocedimenti collegati alla voce dell'albero
	     */
	    s2 = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), entity.getId().getCodice());
	    model.addAttribute("stpEndoTipo2_loc", s2);
	    List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findAllByAlberoproc(ORMHelper.getIdcomunebase(), entity.getId().getCodice());
	    model.addAttribute("alberoprocEndosList", alberoprocEndos);
	    /*
	     * Recupero la lista delle leggi ordinate per descrizione legge
	     */
	    List<AlberoprocLeggi> alberoprocLeggis = alberoprocLeggiService.findByAlberoProc(ORMHelper.getIdcomunebase(), entity.getId().getCodice());
	    model.addAttribute("alberoprocLeggiList", alberoprocLeggis);
	    /*
	     * Recupero la lista delle documenti ordinati per ordine (asc) e descrizione (asc)
	     */
	    List<AlberoprocDocumenti> alberoprocDocumentis = alberoprocDocumentiService.findByAlberoProc(entity.getId().getCodice());
	    model.addAttribute("alberoprocDocumentiList", alberoprocDocumentis);
	    /*
	     * Recupero la lista degli endo procedimenti per l'area riservata 
	     */
	    // devo sempre recuperare gli albero proc areendo , nel caso siamo in console(BDL), allora saranno quelli della consolle,
	    // nel caso siamo entrati nella BDR allora saranno quelli regionali
	    List<AlberoprocArendo> alberoprocArendosTTR = alberoprocArendoService.findByAlberoProc(entity.getId().getCodice(),
		    ORMHelper.getIdcomunebase(), ORMHelper.getIdcomunebase());
	    if (ORMHelper.isConsoleLocale()) {
		List<AlberoprocArendo> alberoprocArendos = alberoprocArendoService.findByAlberoProc(entity.getId().getCodice(),
			ORMHelper.getIdcomunebase(), ORMHelper.getIdcomune());
		model.addAttribute("alberoprocArendosLoc", alberoprocArendos);
		// devo visualizzare anche quelli regionali della BDR, passo come filtro idcomune l'idcomunebase
	    }
	    model.addAttribute("alberoprocArendosTTR", alberoprocArendosTTR);
	    /*
	     * Recupero degli ATECO associati alla voce dell'albero passata ordinati per codice (asc)
	     */
	    List<AlberoprocAteco> alberoprocAtecos = alberoprocAtecoService.findByAlberoproc(entity);
	    model.addAttribute("alberoprocAtecos", alberoprocAtecos);
	    /*
	     * Recupero degli TIPISOGGETTO associati alla voce dell'albero passata ordinati per codice (asc)
	     */
	    List<AlberoprocTipisoggetto> alberoprocTipisoggettos = alberoprocTipisoggettoService.findByAlberoprocId(entity.getId().getIdcomune(),
		    entity.getId().getCodice(), null, null);
	    model.addAttribute("alberoprocTipisoggettos", alberoprocTipisoggettos);
	    /*
	     * Recupero il SC_CODICE
	     */
	    model.addAttribute("SC_CODICE", entity.getScCodice());
	    model.addAttribute("vert_people_attivo", false);
	    model.addAttribute("vert_cart_attivo", true);
	    String scCodice = entity.getScCodice();
	    int lengthCodice = scCodice.length();
	    int lengthTree = lengthCodice / 2;
	    Set<AlberoprocLeggi> alberoprocLeggiEreditate = new HashSet<AlberoprocLeggi>(0);
	    Set<AlberoprocDocumenti> alberoprocDocumentiEreditati = new HashSet<AlberoprocDocumenti>(0);
	    Set<AlberoprocArendo> alberoprocArendoEreditate = new HashSet<AlberoprocArendo>(0);
	    Set<AlberoprocEndo> alberoprocEndoEreditate = new HashSet<AlberoprocEndo>(0);
	    Set<AlberoprocTipisoggetto> alberoprocTipisoggettoEreditatis = new HashSet<AlberoprocTipisoggetto>(0);
	    boolean arendoSettati = false;
	    for (int i = 0; i < lengthTree - 1; i++) {
		lengthCodice = lengthCodice - 2;
		String sccodicePadre = scCodice.substring(0, lengthCodice);
		Alberoproc alberoprocTemp = alberoprocService.findByScCodice(ORMHelper.getIdcomunebase(), sccodicePadre);
		if (alberoprocTemp.getAlberoprocLeggis() != null && !alberoprocTemp.getAlberoprocLeggis().isEmpty()) {
		    for (AlberoprocLeggi arr : alberoprocTemp.getAlberoprocLeggis()) {
			if (ORMHelper.getIdcomune().equals(arr.getId().getIdcomune())
				|| ORMHelper.getIdcomunebase().equals(arr.getId().getIdcomune())) {
			    alberoprocLeggiEreditate.add(arr);
			}
		    }
		}
		if (alberoprocTemp.getAlberoprocDocumentis() != null && !alberoprocTemp.getAlberoprocDocumentis().isEmpty()) {
		    for (AlberoprocDocumenti arr : alberoprocTemp.getAlberoprocDocumentis()) {
			if (ORMHelper.getIdcomune().equals(arr.getId().getIdcomune())
				|| ORMHelper.getIdcomunebase().equals(arr.getId().getIdcomune())) {
			    alberoprocDocumentiEreditati.add(arr);
			}
		    }
		}
		if (alberoprocTemp.getAlberoprocArendos() != null && !alberoprocTemp.getAlberoprocArendos().isEmpty()) {
		    // BOCCI 2012-06-13 La sezione endo ereditati, se visibile, deve far vedere solamente gli endo ereditati dalla voce di albero immediatamente precedente. Si risale alla prima voce dell'albero che definisce alberoproc_arendo e stop.
		    if (arendoSettati == false) {
			for (AlberoprocArendo arr : alberoprocTemp.getAlberoprocArendos()) {
			    if (ORMHelper.getIdcomune().equals(arr.getId().getIdcomune())
				    || ORMHelper.getIdcomunebase().equals(arr.getId().getIdcomune())) {
				alberoprocArendoEreditate.add(arr);
			    }
			}
			arendoSettati = true;
		    }
		}
		if (alberoprocTemp.getAlberoprocEndos() != null && !alberoprocTemp.getAlberoprocEndos().isEmpty()) {
		    for (AlberoprocEndo arr : alberoprocTemp.getAlberoprocEndos()) {
			if (ORMHelper.getIdcomune().equals(arr.getId().getIdcomune())
				|| ORMHelper.getIdcomunebase().equals(arr.getId().getIdcomune())) {
			    alberoprocEndoEreditate.add(arr);
			}
		    }
		}
		if (alberoprocTemp.getAlberoprocTipisoggettos() != null && !alberoprocTemp.getAlberoprocTipisoggettos().isEmpty()) {
		    for (AlberoprocTipisoggetto arr : alberoprocTipisoggettoEreditatis) {
			if (ORMHelper.getIdcomune().equals(arr.getId().getIdcomune())
				|| ORMHelper.getIdcomunebase().equals(arr.getId().getIdcomune())) {
			    alberoprocTipisoggettoEreditatis.add(arr);
			}
		    }
		}
	    }
	    // ordino tutte le liste trovate prima di meterle sul model
	    //Oridna per il campo descrizione dell'oggetto Legge
	    List<AlberoprocLeggi> alberoprocLeggiEreditateOrdinate = new ArrayList<AlberoprocLeggi>(alberoprocLeggiEreditate);
	    Collections.sort(alberoprocLeggiEreditateOrdinate, new AlberoprocLeggiComparator());
	    // Ordina prima per campo ordine e poi per campo descrizione
	    List<AlberoprocDocumenti> alberoprocDocumentiEreditatiOrdinati = new ArrayList<AlberoprocDocumenti>(alberoprocDocumentiEreditati);
	    Collections.sort(alberoprocDocumentiEreditatiOrdinati, new AlberoprocDocumentiComparator());
	    // Ordina prima per campo ordine e poi per campo descrizione	
	    List<AlberoprocEndo> alberoprocEndoEreditatiOrdinati = new ArrayList<AlberoprocEndo>(alberoprocEndoEreditate);
	    Collections.sort(alberoprocEndoEreditatiOrdinati, new AlberoprocEndoComparator());
	    model.addAttribute("alberoprocLeggiEreditate", alberoprocLeggiEreditateOrdinate);
	    model.addAttribute("alberoprocDocumentiEreditati", alberoprocDocumentiEreditatiOrdinati);
	    model.addAttribute("alberoprocArendoEreditate", alberoprocArendoEreditate);
	    model.addAttribute("alberoprocEndoEreditate", alberoprocEndoEreditatiOrdinati);
	    model.addAttribute("alberoprocTipisoggettoEreditati", alberoprocTipisoggettoEreditatis);
	    List<AlberoprocEndoLoc> listinterventilocs = alberoprocEndoLocService.findInterventiFromAlbero(ORMHelper.getIdcomunebase(),
		    entity.getId().getCodice());
	    model.addAttribute("listinterventilocs", listinterventilocs);
	    //FIXME
	    List<AlberoprocEndoLoc> listendolocs = alberoprocEndoLocService.findEndoprocedimentiFromAlbero(ORMHelper.getIdcomunebase(),
		    entity.getId().getCodice(), null);
	    model.addAttribute("listendolocs", listendolocs);
	}
	boolean arjAttiva = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA_AREA_RISERVATA_JAVA_ATTIVA, "S");
	model.addAttribute("ARJ_ATTIVA", arjAttiva);
	boolean centroServizi = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA,
		WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA_CENTRO_SERVIZI, "1");
	model.addAttribute("CENTRO_SERVIZI", centroServizi);
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @Override
    protected void setPageAttributes(Model model) {

	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute(WebConstants.COMUNIASSOCIATI_REQUEST_VARIABLE, Boolean.valueOf(isComuniAssociati));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////GESTIONE DEI MODELLI DELL' ATTIVITA///////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping
    public ModelMap listmodelliAttivita(@RequestParam("codiceprocedimento") Integer codice, HttpServletRequest request,
	    HttpServletResponse response) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	List<AlberoprocD2modtatt> alberoprocD2modtatts = alberoprocD2modtattService.findByAlberoProc(codice);
	ModelMap model = new ModelMap(alberoprocD2modtatts);
	boolean export = createJMesaExport(request, response, alberoprocD2modtatts);
	if (export) {
	    return null;
	}
	model.addAttribute("alberoprocD2modtatts", alberoprocD2modtatts);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public String createmodelliAttivita(@RequestParam("codiceprocedimento") Integer codice, Model model) {

	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	AlberoprocD2modtatt alberoprocD2modtatt = new AlberoprocD2modtatt();
	alberoprocD2modtatt.setAlberoproc(alberoproc);
	// setto l'id
	AlberoprocD2modtattId id = new AlberoprocD2modtattId();
	id.setFkScId(codice);
	alberoprocD2modtatt.setId(id);
	fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	setPageAttributes(model);
	return "alberoproc/formModelliAttivita";
    }

    @RequestMapping
    public String viewModelliAttivita(@RequestParam("codiceprocedimento") Integer codice, @RequestParam("codicemodello") Integer codicemodello,
	    Model model) {

	AlberoprocD2modtattId id = new AlberoprocD2modtattId(codice, codicemodello);
	AlberoprocD2modtatt alberoprocD2modtatt = alberoprocD2modtattService.findById(id);
	fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	model.addAttribute("view", Boolean.TRUE);
	model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	setPageAttributes(model);
	return "alberoproc/formModelliAttivita";
    }

    @RequestMapping
    public String insertModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelliAttivita";
	}
	//	Alberoproc alberoproc = alberoprocService.findById(new PkId(alberoprocD2modtatt.getId().getFkScId()));
	//	alberoprocD2modtatt.setAlberoproc(alberoproc);
	// recupero i campi ajax
	if (alberoprocD2modtatt.getDyn2Modellit() != null && alberoprocD2modtatt.getDyn2Modellit().getId().getCodice() != null) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(alberoprocD2modtatt.getDyn2Modellit().getId());
	    AlberoprocD2modtattId id = alberoprocD2modtatt.getId();
	    id.setFkD2mtId(alberoprocD2modtatt.getDyn2Modellit().getId().getCodice());
	    alberoprocD2modtatt.setId(id);
	    alberoprocD2modtatt.setDyn2Modellit(dyn2Modellit);
	}
	//fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocD2modtattService.insert(alberoprocD2modtatt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocD2modtatt, e);
	    fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	    model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:viewModelliAttivita.htm?codiceprocedimento=" + alberoprocD2modtatt.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocD2modtatt.getId().getFkD2mtId() + "&status_msg=01";
    }

    @RequestMapping
    public String updateModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	if (result.hasErrors()) {
	    return "alberoproc/formModelliAttivita";
	}
	//fixMergeAlberoprocDyn2modellitProperty(alberoprocDyn2modellit);
	try {
	    alberoprocD2modtattService.update(alberoprocD2modtatt);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocD2modtatt, e);
	    model.addAttribute("alberoprocD2modtatt", alberoprocD2modtatt);
	    fixRenderAlberoprocD2modtattProperty(alberoprocD2modtatt);
	    model.addAttribute("view", Boolean.TRUE);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:viewModelliAttivita.htm?codiceprocedimento=" + alberoprocD2modtatt.getAlberoproc().getId().getCodice() + "&codicemodello=" +
	       alberoprocD2modtatt.getId().getFkD2mtId() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteModelliAttivita(Model model, @ModelAttribute("alberoprocD2modtatt") AlberoprocD2modtatt alberoprocD2modtatt,
	    BindingResult result, SessionStatus status) {

	AlberoprocD2modtatt objToDelete = alberoprocD2modtattService.findById(alberoprocD2modtatt.getId());
	try {
	    alberoprocD2modtattService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocD2modtattProperty(objToDelete);
	    model.addAttribute("view", Boolean.TRUE);
	    return "alberoproc/formModelliAttivita";
	}
	status.setComplete();
	return "redirect:listmodelliAttivita.htm?codiceprocedimento=" + objToDelete.getAlberoproc().getId().getCodice();
    }

    @RequestMapping
    public void ajaxmodificadescrizioneinterventoloc(@RequestParam("descrizione") String descrizione, @RequestParam("codice") Integer codice,
	    HttpServletResponse response) throws Exception {

	AlberoprocEndoLoc alberoprocEndo = alberoprocEndoLocService.findById(new PkId(codice));
	String message = "";
	try {
	    alberoprocEndo.setDescrizione(descrizione);
	    alberoprocEndoLocService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage().toString();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxmodificadescrizioneendo(@RequestParam("descrizione") String descrizione, @RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam("codicealbero") Integer codicealberoproc, HttpServletResponse response) throws Exception {

	AlberoprocEndo alberoprocEndo = alberoprocEndoService
		.findById(new AlberoprocEndoId(ORMHelper.getIdcomunebase(), codicealberoproc, codiceendo));
	String message = "";
	try {
	    alberoprocEndo.setDescrizione(descrizione);
	    alberoprocEndoService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage().toString();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeFlagAlberoprocEndoLoc(@RequestParam("codice") Integer codice, @RequestParam("tipoFlag") String tipoFlag,
	    HttpServletResponse response) throws Exception {

	AlberoprocEndoLoc alberoprocEndo = alberoprocEndoLocService.findById(new PkId(codice));
	String message = "";
	try {
	    if (tipoFlag.equalsIgnoreCase(FLAG_PUBBLICA))
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagPubblica())) {
		    alberoprocEndo.setFlagPubblica(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagPubblica(Boolean.FALSE);
		}
	    if (tipoFlag.equalsIgnoreCase(FLAG_NECESSARIO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagNecessario())) {
		    alberoprocEndo.setFlagNecessario(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagNecessario(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_INTERVENTO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagIntervento())) {
		    alberoprocEndo.setFlagIntervento(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagIntervento(Boolean.FALSE);
		}
	    }
	    alberoprocEndoLocService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage().toString();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeFlagAlberoprocEndo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam("codicealberoproc") Integer codicealberoproc, @RequestParam("tipoFlag") String tipoFlag, HttpServletResponse response)
	    throws Exception {

	AlberoprocEndo alberoprocEndo = alberoprocEndoService
		.findById(new AlberoprocEndoId(ORMHelper.getIdcomunebase(), codicealberoproc, codiceendo));
	String message = "";
	try {
	    if (tipoFlag.equalsIgnoreCase(FLAG_PUBBLICA))
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagPubblica())) {
		    alberoprocEndo.setFlagPubblica(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagPubblica(Boolean.FALSE);
		}
	    if (tipoFlag.equalsIgnoreCase(FLAG_PROPOSTO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagRichiesto())) {
		    alberoprocEndo.setFlagRichiesto(Boolean.TRUE);
		    // nel caso sia proposto deve essere anche richiesto da BO
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagRichiesto(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_PRINCIPALE)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagPrincipale())) {
		    alberoprocEndo.setFlagPrincipale(Boolean.TRUE);
		    // nel caso sia proposto deve essere anche richiesto da BO
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_RICHESTO_BACK)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagRichiestoBo())) {
		    alberoprocEndo.setFlagRichiestoBo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagRichiestoBo(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_INTERVENTO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagIntervento())) {
		    alberoprocEndo.setFlagIntervento(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagIntervento(Boolean.FALSE);
		}
	    }
	    if (tipoFlag.equalsIgnoreCase(FLAG_RICHIEDEENDO)) {
		if (BooleanUtils.isFalse(alberoprocEndo.getFlagRichiedeEndo())) {
		    alberoprocEndo.setFlagRichiedeEndo(Boolean.TRUE);
		} else {
		    alberoprocEndo.setFlagRichiedeEndo(Boolean.FALSE);
		}
	    }
	    alberoprocEndoService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	} catch (BusinessValidationException e) {
	    response.setStatus(500);
	    if (!e.getInvalidValues().isEmpty()) {
		message = getMessageFromBundle(e.getInvalidValues().get(0).getMessage(), null);
	    } else {
		message = e.getMessage().toString();
	    }
	}
	response.getWriter().write(message);
    }

    @RequestMapping
    public void ajaxChangeSelectBoxAzioneEndo(@RequestParam("codiceendo") Integer codiceendo,
	    @RequestParam("codicealberoproc") Integer codicealberoproc, @RequestParam("codiceAzione") Integer codiceAzione,
	    HttpServletResponse response) throws Exception {

	try {
	    AlberoprocEndo alberoprocEndo = alberoprocEndoService
		    .findById(new AlberoprocEndoId(ORMHelper.getIdcomune(), codicealberoproc, codiceendo));
	    String message = "";
	    Azioni azione = azioniService.findById(codiceAzione);
	    alberoprocEndo.setAzione(azione);
	    alberoprocEndoService.update(alberoprocEndo);
	    message = getMessageFromBundle("label.datoaggiornato", null);
	    response.getWriter().write(message);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @RequestMapping
    public void ajaxCancellaEsclusioneComune(@RequestParam("codiceinterventoproc") Integer codiceInterventoProc,
	    @RequestParam("codicecomune") String codiceComune, HttpServletResponse response) throws Exception {

	String message = "";
	try {
	    if (codiceInterventoProc == null) {
		throw new InvalidArgumentException("Non è stato passato l'id dell'intervento");
	    }
	    if (StringUtils.isBlank(codiceComune)) {
		throw new InvalidArgumentException("Non è stato passato il codice dell'ente");
	    }
	    this.alberoprocComuniEsclusiService.delete(codiceInterventoProc, codiceComune);
	} catch (Exception e) {
	    response.setStatus(500);
	    message = e.getMessage();
	} finally {
	    response.getWriter().write(message);
	}
    }

    @RequestMapping
    public void ajaxAggiungiComuniEsclusi(@RequestParam("codiceinterventoproc") Integer codiceInterventoProc, @RequestParam("comuni") String[] comuni,
	    HttpServletResponse response) throws Exception {

	String message = "";
	try {
	    this.alberoprocComuniEsclusiService.aggiungiEnti(codiceInterventoProc, comuni);
	} catch (Exception e) {
	    response.setStatus(500);
	    message = e.getMessage();
	} finally {
	    response.getWriter().write(message);
	}
    }

    /**
     * Metodo che gestisce la modifica dei paramtri di protocollazione presenti all'interno del tabella ALBEROPROC
     * 
     * @param codice
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String parametriprotocollazione(@RequestParam("codice") Integer codice, @RequestParam("codiceComune") String codiceComune, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione 
	 */
	setParametriInModel(model, request, codiceComune, alberoproc.getSoftware().getCodice());
	// questa find è necessaria perchè nel metodo sopra (setPageAttributes) ci sono dei service
	// che 'catchano' le eventuali eccezioni. Quando 'catchi' un'eccezione in un service la sessione di hibernate è svuotata
	// per cui è necessario ricaricarla.
	fixRenderEntityProperty(alberoproc);
	model.addAttribute("alberoproc", alberoproc);
	setPageAttributes(model);
	return "alberoproc/formParametriProtocollazione";
    }

    @RequestMapping
    public String listparametriProtAndFasc(@RequestParam("codiceAlberoproc") Integer codice, Model model, HttpServletRequest request) {

	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<AlberoprocProtAndFascHelper> alberoprocProtAndFascHelpers = alberoprocProtocolloService.findByComuniPerOperatore(codice, responsabile);
	model.addAttribute("alberoprocProtAndFascHelpers", alberoprocProtAndFascHelpers);
	Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codice));
	model.addAttribute("alberoproc", alberoproc);
	return "alberoproc/listparametriProtAndFasc";
    }

    @RequestMapping
    public String ajaxRiepilogoConfigurazioniProt(@RequestParam("codicealberoproc") Integer codicealberoproc, Model model,
	    HttpServletResponse response) throws Exception {

	Map<String, AlberoprocProtocollo> m = alberoprocProtocolloService.findConfigurazioniHelper(codicealberoproc);
	model.addAttribute("configAlberoproc", m);
	return "alberoproc/ajaxRiepilogoConfigurazioniProt";
    }

    @RequestMapping
    public String createparametriProtAndFasc(@RequestParam("codice") Integer codice, @RequestParam("codiceComune") String codiceComune, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	Alberoproc alberoproc = alberoprocService.findById(id);
	AlberoprocProtocollo alberoprocprotocollo = new AlberoprocProtocollo();
	alberoprocprotocollo.setTestoFascicolo(new Mailtipo());
	alberoprocprotocollo.setTestoProtocollo(new Mailtipo());
	alberoprocprotocollo.setAlberoproc(alberoproc);
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni c = comuniService.findById(codiceComune);
	    alberoprocprotocollo.setComuni(c);
	}
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione 
	 */
	model.addAttribute("alberoproc", alberoproc);
	model.addAttribute("alberoprocprotocollo", alberoprocprotocollo);
	setParametriInModel(model, request, codiceComune, alberoproc.getSoftware().getCodice());
	setPageAttributes(model);
	fixRenderEntityProperty(alberoproc);
	return "alberoproc/formParametriProtAndFasc";
    }

    public static String decodificaTipo(Integer val, boolean isProt) {

	if (val == null) {
	    return "";
	}
	Map<Integer, String> presenteM = new HashMap<Integer, String>();
	presenteM.put(0, "Non protocollare");
	presenteM.put(1, "Protocolla da inserimento normale");
	presenteM.put(2, "Protocolla da on line");
	presenteM.put(4, "Protocolla da inserimento rapido");
	presenteM.put(8, "Controlla l'impostazione dei rami padre");
	Map<Integer, String> presenteF = new HashMap<Integer, String>();
	presenteF.put(0, "Non fascicolare");
	presenteF.put(1, "Fascicola da inserimento normale");
	presenteF.put(2, "Fascicola da on line");
	presenteF.put(4, "Fascicola da inserimento rapido");
	presenteF.put(8, "Fascicola l'impostazione dei rami padre");
	List<Integer> numeri = new ArrayList<Integer>();
	numeri.add(0);
	numeri.add(1);
	numeri.add(2);
	numeri.add(4);
	numeri.add(8);
	String result = "<ul>";
	for (Integer n : numeri) {
	    boolean presente = false;
	    if (val == 0) {
		presente = true;
	    } else {
		presente = ((val & n.intValue()) == n.intValue() && n.intValue() != 0) ? true : false;
	    }
	    if (presente) {
		if (isProt) {
		    result += "<li>" + presenteM.get(n) + "</li>";
		} else {
		    result += "<li>" + presenteF.get(n) + "</li>";
		}
		if (val == 0) {
		    break;
		}
	    }
	}
	return result + "</ul>";
    }

    @RequestMapping
    public String insertParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	setProtAndFascAutomatica(alberoprocProtocollo, request);
	String codiceComune = null;
	manageRequestParam(alberoprocProtocollo, request);
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.insert(alberoprocProtocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocProtocollo, e);
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione 
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:viewParametriProtAndFasc.htm?codice=" + alberoprocProtocollo.getId().getCodice() + "&status_msg=01";
    }

    private void manageRequestParam(AlberoprocProtocollo alberoprocProtocollo, HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("testoProtocollo.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("testoProtocollo.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Mailtipo m = mailtipoService.findById(codiceId);
	    alberoprocProtocollo.setTestoProtocollo(m);
	} else {
	    alberoprocProtocollo.setTestoProtocollo(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("testoFascicolo.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("testoFascicolo.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Mailtipo m = mailtipoService.findById(codiceId);
	    alberoprocProtocollo.setTestoFascicolo(m);
	} else {
	    alberoprocProtocollo.setTestoFascicolo(null);
	}
    }

    @RequestMapping
    public String viewParametriProtAndFasc(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	AlberoprocProtocollo alberoprocProtocollo = alberoprocProtocolloService.findById(id);
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	/*
	 * Recupera le informazioni per popolare i parametri di protocollazione 
	 */
	setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	model.addAttribute("alberoproc", alberoprocProtocollo.getAlberoproc());
	model.addAttribute("alberoprocprotocollo", alberoprocProtocollo);
	return "alberoproc/formParametriProtAndFasc";
    }

    @RequestMapping
    public String updateParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	setProtAndFascAutomatica(alberoprocProtocollo, request);
	manageRequestParam(alberoprocProtocollo, request);
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.update(alberoprocProtocollo);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, alberoprocProtocollo, e);
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione 
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    fixRenderAlberoprocProtocolloProperty(alberoprocProtocollo);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:viewParametriProtAndFasc.htm?codice=" + alberoprocProtocollo.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteParametriProtAndFasc(Model model, @ModelAttribute("alberoprocprotocollo") AlberoprocProtocollo alberoprocProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	AlberoprocProtocollo objToDelete = alberoprocProtocolloService.findById(alberoprocProtocollo.getId());
	String codiceComune = null;
	if (alberoprocProtocollo.getComuni() != null) {
	    codiceComune = alberoprocProtocollo.getComuni().getCodicecomune();
	}
	try {
	    alberoprocProtocolloService.delete(objToDelete);
	} catch (Exception e) {
	    /*
	     * Recupera le informazioni per popolare i parametri di protocollazione 
	     */
	    setParametriInModel(model, request, codiceComune, alberoprocProtocollo.getAlberoproc().getSoftware().getCodice());
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderAlberoprocProtocolloProperty(objToDelete);
	    return "alberoproc/formParametriProtAndFasc";
	}
	status.setComplete();
	return "redirect:listparametriProtAndFasc.htm?codiceAlberoproc=" + objToDelete.getAlberoproc().getId().getCodice() + "&status_msg=03";
    }

    // Il metodo recupera dalla request i valori selezionati per i campi scFascautomatica e scProtautomatica
    // sono campi a scelt amultipla.
    private void setProtAndFascAutomatica(AlberoprocProtocollo alberoprocProtocollo, HttpServletRequest request) {

	String[] scProtautomatica = request.getParameterValues("scProtautomatica");
	Integer totProtAutomatica = null;
	if (scProtautomatica != null) {
	    totProtAutomatica = Integer.valueOf(0);
	    for (String value : scProtautomatica) {
		Integer codiceProtAutomatica = Integer.parseInt(value);
		totProtAutomatica += codiceProtAutomatica;
	    }
	    alberoprocProtocollo.setScProtautomatica(totProtAutomatica);
	}
	// Recupero i parametri per la fascicolazione del campo scProtautomatica da inserire come scelta multipla 
	String[] scFascautomatica = request.getParameterValues("scFascautomatica");
	Integer totscFascautomatica = null;
	if (scFascautomatica != null) {
	    totscFascautomatica = Integer.valueOf(0);
	    for (String value : scFascautomatica) {
		Integer codiceProtAutomatica = Integer.parseInt(value);
		totscFascautomatica += codiceProtAutomatica;
	    }
	    alberoprocProtocollo.setScFascautomatica(totscFascautomatica);
	}
    }

    @RequestMapping
    public String pubblicaSuCart(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, @RequestParam("pubblica") Boolean pubblica, Model model,
	    HttpServletRequest request) {

	Alberoproc ap = alberoprocService.findById(new PkId(codiceAlberoproc));
	try {
	    alberoprocService.updatePubblicaSuCart(ORMHelper.getIdcomune(), codiceAlberoproc, pubblica.booleanValue());
	    String result = getMessageFromBundle("02", new Object[] {});
	    FlashMessages.getInfos().add(result);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(ap, false, null, e);
	}
	return "redirect:view.htm?codice=" + codiceAlberoproc;
    }

    @RequestMapping
    public String ajaxViewParametriStp(@RequestParam("codiceAlberoproc") Integer codiceAlberoproc, Model model, HttpServletRequest request) {

	Alberoproc ap = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceAlberoproc));
	StpEndoTipo2 stp2 = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), codiceAlberoproc);
	Boolean isInsert = Boolean.FALSE;
	if (stp2 == null) {
	    String scCodice = ap.getScCodice();
	    String codiceEndoRegionale = "";
	    if (scCodice.length() > 2) {
		Alberoproc padre = alberoprocService.findByScCodice(ORMHelper.getIdcomunebase(), StringUtils.left(scCodice, scCodice.length() - 2));
		StpEndoTipo2 sppadre = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), padre.getId().getCodice());
		if (sppadre != null) {
		    codiceEndoRegionale = sppadre.getCodiceEndoRegionale();
		}
	    }
	    stp2 = new StpEndoTipo2();
	    stp2.setAlberoproc(ap);
	    stp2.setCodiceEndoRegionale(codiceEndoRegionale);
	    stp2.setTipo(StpEndoTipo2Service.TIPO_ENDO);
	    isInsert = Boolean.TRUE;
	}
	model.addAttribute("isInsert", isInsert);
	model.addAttribute("alberoproc", ap);
	model.addAttribute("stpEndoTipo2", stp2);
	if (stp2.getStpTipologieEndo2() == null) {
	    stp2.setStpTipologieEndo2(new StpTipologieEndo2());
	}
	List<StpTipologieEndo2> l = stpTipologieEndo2Service.findAll(null, null);
	model.addAttribute("stpTipologieEndo2s", l);
	return "alberoproc/ajaxparametriStp";
    }

    @RequestMapping
    public String updateStpEndo2(Model model, @ModelAttribute("stpEndoTipo2") StpEndoTipo2 stpEndoTipo2, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	Integer albprocId = stpEndoTipo2.getAlberoproc().getId().getCodice();
	String tipologia = request.getParameter("stpTipologieEndo2.id.codice");
	if (StringUtils.isNotBlank(tipologia)) {
	    if (Utilities.isInteger(tipologia)) {
		StpTipologieEndo2 stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(Integer.valueOf(tipologia)));
		stpEndoTipo2.setStpTipologieEndo2(stpTipologieEndo2);
	    }
	} else {
	    stpEndoTipo2.setStpTipologieEndo2(null);
	}
	try {
	    stpEndoTipo2Service.update(stpEndoTipo2);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(stpEndoTipo2, false, "", e);
	    return "redirect:view.htm?codice=" + albprocId + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + albprocId + "&status_msg=02";
    }

    @RequestMapping
    public String insertStpEndo2(Model model, @ModelAttribute("stpEndoTipo2") StpEndoTipo2 stpEndoTipo2, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero i parametri per il protocollo del campo scProtautomatica da inserire come scelta multipla 
	Integer albprocId = stpEndoTipo2.getAlberoproc().getId().getCodice();
	try {
	    stpEndoTipo2Service.insert(stpEndoTipo2);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(stpEndoTipo2, false, "", e);
	    return "redirect:view.htm?codice=" + albprocId + "&status_msg=03";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + albprocId + "&status_msg=02";
    }

    private void setParametriInModel(Model model, HttpServletRequest request, String codiceComune, String software) {

	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	boolean verticalizzazioni_PROTOCOLLO_ATTIVO = verticalizzazioniService
		.isAttivaPerComuneESoftware(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO, software, codiceComune);
	if (verticalizzazioni_PROTOCOLLO_ATTIVO) {
	    model.addAttribute("vert_prot_attivo", true);
	    /*
	     * Lista MAILTIPO
	     */
	    model.addAttribute("mailtipoList", mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.ALL));
	    Verticalizzazioniparametri gestisciFascicoloParam = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE,
		    codiceComune, software);
	    boolean gestiscifascicolo = false;
	    if (gestisciFascicoloParam != null) {
		if (StringUtils.defaultIfEmpty(gestisciFascicoloParam.getValore(), "0").equalsIgnoreCase("1")) {
		    gestiscifascicolo = true;
		}
	    }
	    model.addAttribute("gestisciFascicolo", Boolean.valueOf(gestiscifascicolo));
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
	// setto la lista della amministrazioni
	List<Amministrazioni> amministrazionis = amministrazioniService.findAll(null, null);
	model.addAttribute("amministrazionis", amministrazionis);
    }

    private void fixRenderAlberoprocD2modtattProperty(AlberoprocD2modtatt alberoprocD2modtatt) {

	if (alberoprocD2modtatt.getAlberoproc() == null) {
	    alberoprocD2modtatt.setAlberoproc(new Alberoproc());
	}
	if (alberoprocD2modtatt.getDyn2Modellit() == null) {
	    alberoprocD2modtatt.setDyn2Modellit(new Dyn2Modellit());
	}
    }

    private void fixRenderAlberoprocProtocolloProperty(AlberoprocProtocollo alberoprocProtocollo) {

	if (alberoprocProtocollo.getAlberoproc() == null) {
	    alberoprocProtocollo.setAlberoproc(new Alberoproc());
	}
	if (alberoprocProtocollo.getAmministrazioni() == null) {
	    alberoprocProtocollo.setAmministrazioni(new Amministrazioni());
	}
	if (alberoprocProtocollo.getComuni() == null) {
	    alberoprocProtocollo.setComuni(new Comuni());
	}
	if (alberoprocProtocollo.getTestoFascicolo() == null) {
	    alberoprocProtocollo.setTestoFascicolo(new Mailtipo());
	}
	if (alberoprocProtocollo.getTestoProtocollo() == null) {
	    alberoprocProtocollo.setTestoProtocollo(new Mailtipo());
	}
    }
}
