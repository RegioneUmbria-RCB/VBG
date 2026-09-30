/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;
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
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.GraduatoriedCom;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.GiorniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.TipibandoinputComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ConcessioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorieHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.BandiService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietPianorotazioneService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipibandoService;
import it.gruppoinit.pal.gp.core.service.TipibandoinputService;
import it.gruppoinit.pal.gp.core.service.TipigraduatorietService;
import it.gruppoinit.pal.gp.core.service.helper.GraduatoriedComHelper;

/**
 * @author fabrizioc
 * 
 */
@Controller
@SessionAttributes(value = { "bandi", "graduatoriet", "graduatoried", "concessioni" })
public class BandiController extends BaseController<Bandi> {

    private static final Logger log = LoggerFactory.getLogger(BandiController.class);
    @Autowired
    private BandiService bandiService;
    @Autowired
    private TipibandoService tipibandoService;
    @Autowired
    private TipibandoinputService tipibandoinputService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TipigraduatorietService tipigraduatorietService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private GraduatorietService graduatorietService;
    /*@Autowired
    private GraduatoriedService graduatoriedService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private TipibandooutputService tipibandooutputService;
    @Autowired
    private CampigraduatoriaService campigraduatoriaService;
    */
    @Autowired
    private GraduatorietPianorotazioneService graduatorietPianorotazioneService;
    //@Autowired
    //private IstanzeService istanzeService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private GraduatoriedComService graduatoriedComService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private DocumentMergeService documentMergeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	List<Bandi> bandiList = bandiService.findAll(null, null);
	ModelMap model = new ModelMap(bandiList);
	boolean export = createJMesaExport(request, response, bandiList);
	if (export)
	    return null;
	model.addAttribute("bandiList", bandiList);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("bandi") Bandi bandi, BindingResult result, SessionStatus status) {

	// §§§BEGIN§§§
	Bandi objectToDelete = bandiService.findById(bandi.getId());
	try {
	    bandiService.delete(objectToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objectToDelete, e);
	    fixRenderEntityProperty(bandi);
	    setPageAttributes(model);
	    return "bandi/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("bandi") Bandi bandi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Set<Bandiinput> bandiinputSet = bandi.getBandiinputs();
	/**
	 * Itero il set di bandiinput e setto in ogni oggetto bandiinput il bando che mi arriva dal form. Il motivo di
	 * questa soluzione è dovuta dal fatto che inserendo un bando non vengono inseriti i bandiinput. L'errore è
	 * dovuto dal fatto che Hibernate non riesce a recuperare il codice del bando appena inserito.
	 */
	for (Iterator<Bandiinput> iterator = bandiinputSet.iterator(); iterator.hasNext();) {
	    Bandiinput bandiinput = iterator.next();
	    bandiinput.setBandi(bandi);
	}
	try {
	    bandiService.insert(bandi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bandi, e);
	    fixRenderEntityProperty(bandi);
	    setPageAttributes(model);
	    return "bandi/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + bandi.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("bandi") Bandi bandi, BindingResult result, SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	fixMergeEntityProperty(bandi);
	try {
	    bandiService.update(bandi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, bandi, e);
	    fixRenderEntityProperty(bandi);
	    setPageAttributes(model);
	    return "bandi/form";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:view.htm?codice=" + bandi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Bandi bandi = bandiService.findById(id);
	fixRenderEntityProperty(bandi);
	model.addAttribute("bandi", bandi);
	setPageAttributes(model);
	// §§§END§§§
	return "bandi/form";
    }

    @RequestMapping
    public String create(Model model) {

	// §§§BEGIN§§§
	// Ricerco tutti i tipibando che sono attivi
	List<Tipibando> tipibandoList = tipibandoService.findActiveTipibando();
	Bandi bandi = new Bandi();
	bandi.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(bandi);
	model.addAttribute("bandi", bandi);
	/**
	 * aggiungo nel model la lista dei tipibando che sono necessari per la select del form.
	 */
	model.addAttribute("tipibandoList", tipibandoList);
	setPageAttributes(model);
	// §§§END§§§
	return "bandi/form";
    }

    /**
     * Metodo per la seconda fase dell'inserimento del form di Bandi Fase: inserimento Tipi Bandi input
     * 
     * @param model
     * @return
     */
    @RequestMapping
    public String nextPhase(Model model, @ModelAttribute("bandi") Bandi bandi, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	Tipibando tipibando = tipibandoService.findById(bandi.getTipibando().getId());
	bandi.setTipibando(tipibando);
	if (bandi.getAlberoproc().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(bandi.getAlberoproc().getId().getCodice()));
	    bandi.setAlberoproc(alberoproc);
	} else {
	    bandi.setAlberoproc(null);
	}
	/**
	 * validazione dei campi inseriti
	 */
	if (!validate(bandi, result)) {
	    List<Tipibando> tipibandoList = tipibandoService.findAll(null, null);
	    model.addAttribute("tipibandoList", tipibandoList);
	} else {
	    /**
	     * Determino il tipobandoinput che ha tipoinput="VALORE" e ha il tipo bando selezionato nel form
	     */
	    Tipibandoinput tipibandoinput = new Tipibandoinput();
	    tipibandoinput.setTipoinput(WebConstants.BANDI_TIPOINPUT_VALORE);
	    tipibandoinput.setTipibando(tipibando);
	    Set<Bandiinput> bandiinputs = new LinkedHashSet<Bandiinput>();
	    List<Tipibandoinput> tipBanInpList = tipibandoinputService.findTipiBandiInput(tipibandoinput);
	    Collections.sort(tipBanInpList, new TipibandoinputComparator());
	    model.addAttribute("tipibandiinp", tipBanInpList);
	    Bandiinput bandiinput = null;
	    /**
	     * Setto tipibandoinput in bandiinput
	     */
	    for (Tipibandoinput tipibandoinput2 : tipBanInpList) {
		bandiinput = new Bandiinput();
		bandiinput.setTipibandoinput(tipibandoinput2);
		bandiinputs.add(bandiinput);
	    }
	    if (bandiinputs.size() <= 0) {
		return insert(model, bandi, result, status, request);
	    }
	    bandi.setBandiinputs(bandiinputs);
	}
	fixRenderEntityProperty(bandi);
	model.addAttribute("bandi", bandi);
	// §§§END§§§
	return "bandi/form";
    }

    @RequestMapping
    public String createGraduatoria(Model model, @RequestParam("bandoid") Integer bandoId) {

	// §§§BEGIN§§§
	PkId id = new PkId(bandoId);
	Bandi bando = bandiService.findById(id);
	Graduatoriet graduatoriet = new Graduatoriet();
	graduatoriet.setBandi(bando);
	model.addAttribute("graduatoriet", graduatoriet);
	model.addAttribute("tipigraduatorietList", bando.getTipibando().getTipigraduatoriets());
	// §§§END§§§
	return "bandi/formGraduatoria";
    }

    @RequestMapping
    public String insertGraduatoria(Model model, @ModelAttribute("graduatoriet") Graduatoriet graduatoriet, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	Bandi bandi = bandiService.findById(graduatoriet.getBandi().getId());
	Set<Tipigraduatoriet> tipiGraduatorie = bandi.getTipibando().getTipigraduatoriets();
	tipiGraduatorie.size();
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(graduatoriet.getTipigraduatoriet().getId().getCodice()));
	graduatoriet.setBandi(bandi);
	if (StringUtils.isBlank(graduatoriet.getDescrizione()) && tipigraduatoriet != null) {
	    graduatoriet.setDescrizione(tipigraduatoriet.getDescrizione());
	}
	graduatoriet.setTipigraduatoriet(tipigraduatoriet);
	fixMergeGraduatorieTProperty(graduatoriet);
	try {
	    graduatorietService.compilaGraduatoria(graduatoriet);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatoriet, e);
	    // String messaggioErrore = getMessageFromBundle("errors.operazione.fallita.messaggio");
	    // result.reject("", messaggioErrore + e.getMessage());
	    graduatoriet.getId().setCodice(null);
	    fixRenderGraduatorieTProperty(graduatoriet);
	    model.addAttribute("tipigraduatorietList", tipiGraduatorie);
	    return "bandi/formGraduatoria";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewGraduatoria(@RequestParam("codice") Integer graduatoriaid, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	log.debug("viewGraduatoria(codice={})", graduatoriaid);
	GraduatorieHelper helper = bandiService.findHelperGraduatoria(graduatoriaid);
	// model.addAttribute("helperGraduatoria", helper);
	model.addAttribute("mercatoIsPresente", helper.getMercatoIsPresente());
	model.addAttribute("isRilasciaConcessionePerIstanza", helper.getMostraRilasciaEdAssegnaConcessione());
	model.addAttribute("isMostraBottoneRilasciaConc", helper.getMostraButtonRilasciaConcessione());
	model.addAttribute("isMostraBottoneRilasciaPos", helper.getMostraButtonRilasciaPosizione());
	model.addAttribute("graduatoriet", helper.getGraduatoriet());
	model.addAttribute("tipibandooutputList", helper.getTipibandooutputList());
	model.addAttribute("graduatoriedCampi", helper.getGraduatoriedDTO2s());
	model.addAttribute("sizeCriteri", helper.getSizeCriteri());
	model.addAttribute("graduatoriaid", graduatoriaid);
	model.addAttribute("concessioni", new ConcessioniCommand());
	List<GraduatorietPianorotazione> graduatorietPianorotaziones = graduatorietPianorotazioneService.findByGraduatorit(graduatoriaid);
	boolean b = !graduatorietPianorotaziones.isEmpty();
	model.addAttribute("isPianorotazioneCreato", !graduatorietPianorotaziones.isEmpty());
	model.addAttribute("isPresenticomunicazioni", Boolean.valueOf(helper.isPresentiComunicazioni()));
	fixRenderGraduatorieTProperty(helper.getGraduatoriet());
	setPageAttributes(model);
	// §§§END§§§
	log.debug("viewGraduatoria(codice={}) return", graduatoriaid);
	return "bandi/formGraduatoria";
    }

    @RequestMapping
    public String updateGraduatoria(Model model, @ModelAttribute("graduatoriet") Graduatoriet graduatoriet, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Bandi bandi = bandiService.findById(graduatoriet.getBandi().getId());
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(graduatoriet.getTipigraduatoriet().getId().getCodice()));
	graduatoriet.setBandi(bandi);
	graduatoriet.setTipigraduatoriet(tipigraduatoriet);
	fixMergeGraduatorieTProperty(graduatoriet);
	try {
	    if (graduatoriet.getDescrizione().equals("") && tipigraduatoriet != null) {
		graduatoriet.setDescrizione(tipigraduatoriet.getDescrizione());
	    }
	    graduatorietService.update(graduatoriet);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatoriet, e);
	    Set<Graduatoried> set = graduatoriet.getGraduatorieds();
	    model.addAttribute("graduatoriedCampi", set);
	    model.addAttribute("sizeCriteri", set.size());
	    fixRenderGraduatorieTProperty(graduatoriet);
	    setPageAttributes(model);
	    return "bandi/formGraduatoria";
	}
	status.setComplete();
	// §§§END§§§
	return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String deleteGraduatoria(Model model, @ModelAttribute("graduatoriet") Graduatoriet graduatoriet, BindingResult result,
	    SessionStatus status) {

	// §§§BEGIN§§§
	try {
	    graduatorietService.delete(graduatoriet);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, graduatoriet, e);
	    Map map = new HashMap();
	    map.put("codice", graduatoriet.getBandi().getId().getCodice());
	    model.addAttribute("commandName", "graduatoriet");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	// §§§END§§§
	return "redirect:view.htm?codice=" + graduatoriet.getBandi().getId().getCodice();
    }

    @RequestMapping
    public String assegnaConcessioniAlleIstanzeInGratuatoria(@RequestParam("codice") Integer graduatoriaid, Model model,
	    @ModelAttribute("graduatoriet") Graduatoriet graduatoriet, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Ricerco graduatoria per il codice {}", graduatoriaid);
	}
	PkId id = new PkId(graduatoriaid);
	Graduatoriet graduatoriet1 = graduatorietService.findById(id);
	try {
	    if (log.isDebugEnabled()) {
		log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Inizio assegnazione concessioni alle istanze presenti per la graduatoria {}",
			graduatoriaid);
	    }
	    bandiService.insertConcessioniAlleIstanzeInGratuatoria(graduatoriet1);
	    if (log.isDebugEnabled()) {
		log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Fine assegnazione concessioni alle istanze presenti per la graduatoria {}",
			graduatoriaid);
	    }
	} catch (Exception e) {
	    copyErrorsToFlashMessages(graduatoriet1, false, "", e);
	    return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice();
	}
	return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String assegnaConcessioniTemporaneeAlleIstanzeInGratuatoria(@RequestParam("codice") Integer graduatoriaid, Model model,
	    @ModelAttribute("concessioni") ConcessioniCommand concessioni, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Ricerco graduatoria per il codice {}", graduatoriaid);
	}
	PkId id = new PkId(graduatoriaid);
	Graduatoriet graduatoriet = graduatorietService.findById(id);
	if (EntityUtils.getNestedProperty(concessioni.getTipologiaregistri(), "id.codice") != null) {
	    Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(new PkId(concessioni.getTipologiaregistri().getId().getCodice()));
	    graduatoriet.setTipologiaregistri(tipologiaregistri);
	} else {
	    graduatoriet.setTipologiaregistri(new Tipologiaregistri());
	}
	try {
	    if (log.isDebugEnabled()) {
		log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Inizio assegnazione concessioni alle istanze presenti per la graduatoria {}",
			graduatoriaid);
	    }
	    bandiService.insertConcessioniAlleIstanzeInGratuatoria(graduatoriet);
	    if (log.isDebugEnabled()) {
		log.debug("assegnaConcessioniAlleIstanzeInGratuatoria# Fine assegnazione concessioni alle istanze presenti per la graduatoria {}",
			graduatoriaid);
	    }
	} catch (Exception e) {
	    copyErrorsToFlashMessages(graduatoriet, false, "", e);
	    return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice();
	}
	return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String pianorotazione(@RequestParam("codiceGraduatoria") Integer graduatoriat, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(graduatoriat));
	Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(graduatoriet.getTipigraduatoriet().getId().getCodice()));
	boolean flagPianoRotazione = tipigraduatoriet.getFlagPianorotazione();
	List<GraduatorietPianorotazione> graduatorietPianorotaziones = graduatorietPianorotazioneService.findByGraduatorit(graduatoriat);
	try {
	    if (flagPianoRotazione && graduatorietPianorotaziones.isEmpty()) {
		graduatorietPianorotazioneService.creaPianoRotazione(graduatoriat);
	    }
	} catch (Exception e) {
	    copyErrorsToFlashMessages(graduatoriet, false, "", e);
	    return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice();
	}
	return "redirect:viewPianorotazione.htm?codiceGraduatoria=" + graduatoriet.getId().getCodice();
    }

    @RequestMapping
    public String viewPianorotazione(@RequestParam("codiceGraduatoria") Integer graduatoriat, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(graduatoriat));
	//Tipigraduatoriet tipigraduatoriet = tipigraduatorietService.findById(new PkId(graduatoriet.getTipigraduatoriet().getId().getCodice()));
	//boolean flagPianoRotazione = tipigraduatoriet.getFlagPianorotazione();
	List<PosteggioHelper> graduatorietPianorotaziones = graduatorietPianorotazioneService.createMapPosteggiPianorotazione(graduatoriet);
	// Nel caso non esiste un piano di rotazione, verrà creato
	//		if (flagPianoRotazione && graduatorietPianorotaziones.isEmpty()) {
	//		    graduatorietPianorotazioneService.creaPianoRotazione(graduatoriat);
	//		} else { // esiste già il piano di rotazione, viene mostrato
	List<String> giorni = new ArrayList<String>();
	for (PosteggioHelper posteggioHelper : graduatorietPianorotaziones) {
	    if (!posteggioHelper.getGiornosHelper().isEmpty()) {
		for (GiorniHelper giorniHelper : posteggioHelper.getGiornosHelper()) {
		    giorni.add(giorniHelper.getDescrizioneGiorno());
		}
		break;
	    }
	}
	//	List<GiorniHelper> listGiorniHelper = graduatorietPianorotaziones.get(0).getGiornosHelper();
	// Devo valutare se già sono state rilasciate le concessioni, non dovrà essere più presente il bottone 
	// rilascia concessione.
	boolean isRilasciate = checkAutAndConcessioniRilasciate(graduatoriet);
	model.addAttribute("graduatorietPianorotaziones", graduatorietPianorotaziones);
	model.addAttribute("giorni", giorni);
	model.addAttribute("isRilasciateConcessioni", isRilasciate);
	return "bandi/formPianoRotazione";
	//}
	//return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice();
    }

    @RequestMapping
    public String createConcessioniTemporanee(@RequestParam("codiceGraduatoria") Integer codiceGraduatoria, Model model,
	    @ModelAttribute("concessioni") ConcessioniCommand concessioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(codiceGraduatoria));
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	if (mercatiConfigurazione != null && EntityUtils.getNestedProperty(mercatiConfigurazione.getRegistroConcessioni(), "id.codice") != null) {
	    concessioniCommand.setTipologiaregistri(mercatiConfigurazione.getRegistroConcessioni());
	}
	model.addAttribute("concessioni", concessioniCommand);
	model.addAttribute("graduatoriet", graduatoriet);
	return "bandi/createConcessioniTemporanee";
    }

    @RequestMapping
    public String deletePianorotazione(@RequestParam("codice") Integer graduatoriat, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(graduatoriat));
	List<GraduatorietPianorotazione> graduatorietPianorotaziones = graduatorietPianorotazioneService.findByGraduatorit(graduatoriat);
	try {
	    graduatorietPianorotazioneService.deletePianoRotazione(graduatorietPianorotaziones);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(graduatoriet, false, "", e);
	    List<PosteggioHelper> graduatorietPianorotazionesHelper = graduatorietPianorotazioneService.createMapPosteggiPianorotazione(graduatoriet);
	    List<String> giorni = new ArrayList<String>();
	    List<GiorniHelper> listGiorniHelper = graduatorietPianorotazionesHelper.get(0).getGiornosHelper();
	    for (GiorniHelper giorniHelper : listGiorniHelper) {
		giorni.add(giorniHelper.getDescrizioneGiorno());
	    }
	    // Devo valutare se già sono state rilasciate le concessioni, non dovrà essere più presente il bottone 
	    // rilascia concessione.
	    boolean isRilasciate = checkAutAndConcessioniRilasciate(graduatoriet);
	    model.addAttribute("graduatorietPianorotaziones", graduatorietPianorotaziones);
	    model.addAttribute("giorni", giorni);
	    model.addAttribute("isRilasciateConcessioni", isRilasciate);
	    return "bandi/formPianoRotazione";
	}
	return "redirect:viewGraduatoria.htm?codice=" + graduatoriet.getId().getCodice();
    }

    /**
     * Controllo se esistono autorizzazione e concessioni per la prima istanza della graduatoria, nel caso presuppongo
     * che le concessioni sono state rilasciate dal piano di rotazione
     * 
     * @param graduatoriet
     */
    private boolean checkAutAndConcessioniRilasciate(Graduatoriet graduatoriet) {

	boolean risultato = false;
	Set<Graduatoried> graduatorieds = graduatoriet.getGraduatorieds();
	for (Graduatoried graduatoried : graduatorieds) {
	    if (graduatoried.getIstanza().getAutorizzazionis().size() > 0) {
		risultato = true;
		break;
	    }
	}
	return risultato;
    }

    @RequestMapping
    public String createConcessioniPianorotazione(Model model, @RequestParam("codice") Integer graduatoriaid, HttpServletRequest request,
	    HttpServletResponse response) {

	PkId id = new PkId(graduatoriaid);
	Graduatoriet graduatoriet = graduatorietService.findById(id);
	model.addAttribute("graduatoriet", graduatoriet);
	model.addAttribute("concessioni", new ConcessioniCommand());
	return "bandi/createConcessioniPianorotazione";
    }

    @RequestMapping
    public String inserisciConcessioniPerPianoRotazione(@RequestParam("codiceGraduatotiat") Integer graduatoriatid, Model model,
	    @ModelAttribute("concessioni") ConcessioniCommand concessioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId id = new PkId(graduatoriatid);
	Graduatoriet graduatoriet = graduatorietService.findById(id);
	try {
	    bandiService.insertConcessioniPianoRotazione(graduatoriet, concessioniCommand.getDateRilascio(),
		    concessioniCommand.getTipologiaregistri());
	} catch (Exception e) {
	    copyErrorsToFlashMessages(concessioniCommand, false, "", e);
	    model.addAttribute("graduatoriet", graduatoriet);
	    model.addAttribute("concessioni", new ConcessioniCommand());
	    return "bandi/createConcessioniPianorotazione";
	}
	return "redirect:viewPianorotazione.htm?codiceGraduatoria=" + graduatoriet.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String assegnaPosizioniPosteggiMassivamente(@RequestParam("codiceGraduatoria") Integer codice, Model model, HttpServletRequest request) {

	GraduatorieHelper graduatorieHelper = bandiService.findHelperGraduatoria(codice);
	Set<GraduatoriedDTO> graduatoriedDTOs = graduatorieHelper.getGraduatoriedDTO2s();
	for (GraduatoriedDTO graduatoriedDTO : graduatoriedDTOs) {
	    if (!graduatoriedDTO.getConcESub().isEmpty()) {
		MercatiD mercatiD = mercatiDService.findById(new PkId(graduatoriedDTO.getConcESub().get(0).getIdposteggio()));
		mercatiD.setPosizione(graduatoriedDTO.getPosizione());
		mercatiDService.update(mercatiD);
	    }
	}
	setPageAttributes(model);
	return "redirect:viewGraduatoria.htm?codice=" + codice;
    }

    private void fixMergeGraduatorieTProperty(Graduatoriet graduatoriet) {

    }

    @Override
    protected void fixMergeEntityProperty(Bandi entity) {

	if (entity.getTipibando() != null && entity.getTipibando().getId() != null && entity.getTipibando().getId().getCodice() == null) {
	    entity.setTipibando(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Bandi entity) {

	if (entity.getTipibando() == null) {
	    entity.setTipibando(new Tipibando());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getAlberoproc() == null) {
	    entity.setAlberoproc(new Alberoproc());
	}
    }

    protected void fixRenderGraduatorieTProperty(Graduatoriet entity) {

	if (entity.getTipigraduatoriet() == null) {
	    entity.setTipigraduatoriet(new Tipigraduatoriet());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    /**
     * Metodo privato per la validazione dei campi del form di Bandi utilizzato durante la insert di Bandi perchè
     * avviene in due fasi e durante la prima fase non c'è un vero inserimento nel db e quindi non c'è la chiamata al
     * metodo validate
     * 
     * @param entity
     * @param result
     * @return boolean
     */
    private boolean validate(Bandi entity, BindingResult result) {

	ClassValidator<Bandi> validator = new ClassValidator<Bandi>(Bandi.class);
	InvalidValue[] validationMessages = validator.getInvalidValues(entity);
	if (validationMessages != null && validationMessages.length > 0) {
	    for (InvalidValue invalidValue : validationMessages) {
		result.rejectValue(invalidValue.getPropertyPath(), "", invalidValue.getMessage());
	    }
	    return false;
	}
	return true;
    }

    @RequestMapping
    public String ajaxmostracomunicazioni(@RequestParam("codicegraduatoriad") Integer codicegraduatoriad, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	List<GraduatoriedComHelper> gds = graduatoriedComService.findByGraduatoried(codicegraduatoriad);
	boolean existAllegati = false;
	for (GraduatoriedComHelper gh : gds) {
	    existAllegati = (gh.getMovimentiAllegatis().size() > 0 || gh.getTipiMovdoctipos().size() > 0);
	    if (existAllegati) {
		break;
	    }
	}
	model.addAttribute("existAllegati", Boolean.valueOf(existAllegati));
	model.addAttribute("gds", gds);
	return "bandi/ajaxShowcomunicazioni";
    }

    @RequestMapping
    public String ajaxcreaallegatocomunicazioni(@RequestParam("codicegraduatoriadcom") Integer codicegraduatoriadcom,
	    @RequestParam("codicelettera") Integer codicelettera, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	GraduatoriedCom s = graduatoriedComService.findById(new PkId(codicegraduatoriadcom));
	Graduatoried gd = s.getGraduatoried();
	model.addAttribute("graduatoried", gd);
	Movimenti movimento = s.getMovimenti();
	Integer codiceMovimento = movimento.getId().getCodice();
	Integer codiceIstanza = movimento.getIstanza().getId().getCodice();
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codicelettera));
	String tipoMovimento = movimento.getTipomovimento().getId().getTipomovimento();
	// Serve la IF perchè creiamo il documento con due tecnologie differenti, se anche quello rtf sarà creato in java non servirà più il 
	// discriminare,verrà fatto già dentro "createAllegatoDaDocumentoTipo"
	if (StringUtils.contains(letteretipo.getFile().getNomefile(), ".odt")) {
	    try {
		Oggetti oggetto = documentMergeService.createAllegatoDaDocumentoTipo(codicelettera, codiceIstanza, codiceMovimento,
			new DocumentMergeHelper());
		Movimentiallegati movimentiallegati = new Movimentiallegati();
		// Movimenti movimento = movimentiNoSecurityService.findById(new PkId(codiceMovimento));
		movimentiallegati.setMovimento(movimento);
		movimentiallegati.setOggetto(oggetto);
		movimentiallegati.setDescrizione(letteretipo.getDescrizione());
		movimentiallegatiService.insert(movimentiallegati);
		// Chiamo la applet per la gestionedei file
		request.setAttribute("codiceAllegato", oggetto.getId().getCodice());
		if (isSalvaFileInFileSystem()) {
		    return "redirect:../file/ajaxDownload.htm?fileId=" + oggetto.getId().getCodice();
		}
	    } catch (Exception e) {
		response.setStatus(500);
		response.getOutputStream()
			.print("<b>Si e' verificato un errore nella stampa del documento contattare l'assistenza</b>.<p /> " +
				"<i style=\"color: red\">[Funzionalita': BANDI.ajaxcreaallegatocomunicazioni, Dettaglio errore: " +
				e +
				"]</i> ");
		return null;
	    }
	} else {
	    // Creo l'URL della chiamata alla pagina ASP
	    String urlcreaallegato = documentMergeService.getUrlGeneraAllegato() +
		    "?codiceDocumento=" +
		    codicelettera +
		    "&codiceIstanza=" +
		    codiceIstanza +
		    "&codiceMovimento=" +
		    codiceMovimento +
		    "&TipoMovimento=" +
		    tipoMovimento +
		    "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    ORMHelper.getSoftware() +
		    "&" +
		    WebConstants.TOKEN +
		    "=" +
		    ORMHelper.getToken();
	    // Recupero tramite HTTP cliet il contenuto della pagina e lo metto su uno stream	
	    HttpClient cli = new HttpClient();
	    HttpMethod method = null;
	    int status = 0;
	    try {
		method = new GetMethod(urlcreaallegato);
		status = cli.executeMethod(method);
		//	    Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceDocumento));
		request.setAttribute("nomeFile", letteretipo.getNomefile());
	    } catch (Exception e) {
		response.setStatus(500);
		response.getOutputStream()
			.print("<b>Si e' verificato un errore nella stampa del documento contattare l'assistenza</b>.<p /> " +
				"<i style=\"color: red\">[Funzionalita': BANDI.ajaxcreaallegatocomunicazioni,Dettaglio errore: " +
				e +
				"]</i> ");
		return null;
	    }
	    InputStream inputStream = method.getResponseBodyAsStream();
	    // Trasformo lo stream in una stringa (conterrà solo il codice)
	    //InputStream inputStream = method.getResponseBodyAsStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
		sb.append(line);
	    }
	    inputStream.close();
	    String co = sb.toString();
	    if (status == 200) {
		// Chiamo la applet per la gestionedei file
		Integer codice = Integer.parseInt(co);
		request.setAttribute("codiceAllegato", codice);
		if (isSalvaFileInFileSystem()) {
		    return "redirect:../file/ajaxDownload.htm?fileId=" + codice;
		}
		//return "redirect:../file/editDocApplet.htm?fileId=" + codice + "&func=closeEditDocs";
	    }
	    if (status == 500) {
		response.setStatus(500);
		response.getOutputStream().print(sb.toString());
		return null;
	    }
	}
	return "bandi/ajaxAllegatoCreato";
    }

    /**
     * Controlla se sono attive tutte le condizione per cui i file si trovano su file system possono essere salvati
     * direttamente
     * 
     * @return
     */
    private boolean isSalvaFileInFileSystem() {

	boolean _isSalvaFileInFileSystem = false;
	boolean isFileSystemAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, ORMHelper.getSoftware());
	// Se è attiva allo controllo i parametri se sono attivi
	if (isFileSystemAttiva) {
	    Verticalizzazioniparametri verticalizzazioniparametri_READONLY = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_READONLY, ORMHelper.getSoftware());
	    Verticalizzazioniparametri verticalizzazioniparametri_SHAREDPAT = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH, ORMHelper.getSoftware());
	    // Controllo se non configurati entrambi i parametri
	    if (verticalizzazioniparametri_READONLY != null && verticalizzazioniparametri_SHAREDPAT != null) {
		// Devo verificare che verticalizzazioniparametri_READONLY sia null o 0 e che verticalizzazioniparametri_SHAREDPAT sia diverso da null
		if ((StringUtils.isBlank(verticalizzazioniparametri_READONLY.getValore())
			|| verticalizzazioniparametri_READONLY.getValore().equals("0"))
			&& (StringUtils.isNotBlank(verticalizzazioniparametri_SHAREDPAT.getValore()))) {
		    _isSalvaFileInFileSystem = true;
		}
	    }
	}
	return _isSalvaFileInFileSystem;
    }
}
