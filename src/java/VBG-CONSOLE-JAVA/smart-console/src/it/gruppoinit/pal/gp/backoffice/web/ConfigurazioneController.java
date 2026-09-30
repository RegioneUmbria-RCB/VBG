package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazione;
import it.gruppoinit.pal.gp.core.domain.ProtocolloConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ConfigurazioneCommand;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author gianpaolot
 */
@Controller
@SessionAttributes("configurazione")
public class ConfigurazioneController extends BaseController<Configurazione> {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioneController.class);
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ComuniassociatisoftwareService comuniassociatisoftwareService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private ProtocolloConfigurazioneService protocolloConfigurazioneService;

    @RequestMapping
    public String create(@RequestParam("software") String software, Model model, HttpServletRequest request) {

	ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
	// Recupero la configurazione, se esiste carico quella presente su db, altrimenti ne creo una nuova
	if (log.isDebugEnabled()) {
	    log.debug("create# Controllo se esiste un record nella tabella configurazione per idcomune {} e software {}",
		    new Object[] { ORMHelper.getIdcomune(), software });
	}
	Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId(software));
	configurazione.setDisplayMode(ConfigurazioneCommand.VIEW);
	if (configurazioneEsistente == null) {
	    if (log.isDebugEnabled()) {
		log.debug("create# Configurazione per idcomune {} e software {} non esistente, creo un nuovo record",
			new Object[] { ORMHelper.getIdcomune(), software });
	    }
	    configurazioneEsistente = new Configurazione();
	    configurazione.setDisplayMode(ConfigurazioneCommand.NEW);
	}
	fixRenderEntityProperty(configurazioneEsistente);
	configurazione.setConfigurazione(configurazioneEsistente);
	// Vado a recuperare il codice comune (prendo il primo che recupero), siamoin visulizzazione
	// mi serve solo uno codice comune, epr mostrare i dati.	
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Set<Responsabilicomuni> responsabilicomunis = responsabile.getResponsabilicomunis();
	Comuni comuni = null;
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    comuni = responsabilicomuni.getComune();
	    if (log.isDebugEnabled()) {
		log.debug("create# Codice comune recuperato {}", new Object[] { comuni.getCodicecomune() });
	    }
	    break;
	}
	// Controllo se è un installazione multicomune
	if (log.isDebugEnabled()) {
	    log.debug("create# Controllo se l'installazione è multi comune per l'idcomune {}", new Object[] { ORMHelper.getIdcomune() });
	}
	boolean isMultiComune = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (!isMultiComune) {
	    if (log.isDebugEnabled()) {
		log.debug("create# Installazione singolo comune}");
	    }
	    //Recupero l'oggetto comuniassociati
	    if (log.isDebugEnabled()) {
		log.debug("create# Recupero se estiste il record nella tabella comuniassociati per codice comune: {}",
			new Object[] { comuni.getCodicecomune() });
	    }
	    Comuniassociati comuniassociati = comuniassociatiService.findById(new ComuniassociatiId(comuni.getCodicecomune()));
	    if (EntityUtils.getNestedProperty(comuniassociati, "id") == null) {
		if (log.isDebugEnabled()) {
		    log.debug("create# Non eiste il record nella tabella Comuniassociati, ne creo uno nuovo ");
		}
		comuniassociati = new Comuniassociati();
	    }
	    fixRenderComuniassociatiProperty(comuniassociati);
	    configurazione.setComuniassociati(comuniassociati);
	    //Recupero l'oggetto comuniassociatisoftware
	    if (log.isDebugEnabled()) {
		log.debug("create# Recupero se estiste, il record nella tabella comuniassociatisoftware per codice comune: {}",
			new Object[] { comuni.getCodicecomune() });
	    }
	    Comuniassociatisoftware comuniassociatisoftware = comuniassociatisoftwareService.findByComune(comuni);
	    if (EntityUtils.getNestedProperty(comuniassociatisoftware, "id") == null) {
		if (log.isDebugEnabled()) {
		    log.debug("create# Non eiste il record nella tabella comuniassociatisoftware, ne creo uno nuovo ");
		}
		comuniassociatisoftware = new Comuniassociatisoftware();
	    }
	    configurazione.setComuniassociatisoftware(comuniassociatisoftware);
	    fixRenderComuniassociatisoftwareProperty(comuniassociatisoftware);
	} else {
	    if (log.isDebugEnabled()) {
		log.debug("create# Installazione multi comune}");
	    }
	    // Devo solo recuperare solo "comuniassociatisoftware" per vedere se ci sono record che riguardano le intestazioni
	    // questo record dovrà avere codice comune null 
	    Comuniassociatisoftware comuniassociatisoftware = comuniassociatisoftwareService.findByComune(null);
	    if (comuniassociatisoftware == null) {
		if (log.isDebugEnabled()) {
		    log.debug("create# Non è stato inserito ancora record in \"Comuniassociatisoftware\" con codicecomune : null e idcomune}",
			    ORMHelper.getIdcomune());
		}
		// Non è stata ancora inserito un record, ne creo uno nuovo per la visulaizzazione
		comuniassociatisoftware = new Comuniassociatisoftware();
	    }
	    fixRenderComuniassociatisoftwareProperty(comuniassociatisoftware);
	    configurazione.setComuniassociatisoftware(comuniassociatisoftware);
	}
	model.addAttribute("configurazione", configurazione);
	Software softwareObject = softwareService.findById(software);
	model.addAttribute("software", softwareObject);
	setPageAttributes(model, request);
	return "configurazione/form";
    }

    @RequestMapping
    public String insertOrUpdate(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand configurazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// setto il documento tipo dell'oggetto configurazione
	if (configurazione.getConfigurazione() != null && configurazione.getConfigurazione().getOggettomoddoctipo() != null
		&& configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice() != null) {
	    Integer codice = configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice();
	    Oggetti oggetti = oggettiService.findById(new PkId(codice));
	    configurazione.getConfigurazione().setOggettomoddoctipo(oggetti);
	}
	// setto l'oggetto stemma comuniassociati software in configurazione
	if (configurazione.getComuniassociatisoftware() != null && configurazione.getComuniassociatisoftware().getOggetti() != null
		&& configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice() != null) {
	    Integer codice = configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice();
	    Oggetti oggetti = oggettiService.findById(new PkId(codice));
	    configurazione.getComuniassociatisoftware().setOggetti(oggetti);
	}
	fixMergeEntityProperty(configurazione.getConfigurazione());
	fixMergeComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
	try {
	    configurazioneService.insertConfigurazionedatigeneraliAndComuniassociatesoftware(configurazione.getConfigurazione(),
		    configurazione.getComuniassociatisoftware(), configurazione.getComuniassociati());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, configurazione.getConfigurazione(), true, "configurazione", e);
	    fixRenderEntityProperty(configurazione.getConfigurazione());
	    fixRenderComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
	    Software softwareObject = softwareService.findById(ORMHelper.getSoftware());
	    model.addAttribute("software", softwareObject);
	    setPageAttributes(model, request);
	    return "configurazione/form";
	}
	//aggiorno l'intestazione se sto configurando i dati generali di tutti i backoffice
	if (WebConstants.SOFTWARE_TT.equals(configurazione.getConfigurazione().getId().getSoftware())) {
	    request.getSession().setAttribute("comune", configurazione.getConfigurazione().getDenominazione());
	}
	status.setComplete();
	return "redirect:create.htm?software=" + configurazione.getConfigurazione().getId().getSoftware() + "&status_msg=02";
    }

    @RequestMapping
    public String createComuniassociatisoftware(@RequestParam("software") String software,
	    @RequestParam(value = "idcomuneassociato", required = false) String codicecomune, Model model, HttpServletRequest request) {

	ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
	Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId(software));
	if (configurazioneEsistente != null) {
	    configurazione.setConfigurazione(configurazioneEsistente);
	}
	// Vado a recuperare il codice comune.
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Set<Responsabilicomuni> responsabilicomunis = responsabile.getResponsabilicomunis();
	Comuni comuni = null;
	for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
	    comuni = responsabilicomuni.getComune();
	    break;
	}
	// recupero tutti i comuni per cui il responsabile è configurato
	try {
	    List<Responsabilicomuni> listResponsabiliComuni = responsabilicomuniService.findByOperatore(responsabile);
	    configurazione.setResponsabilicomunis(listResponsabiliComuni);
	    if (StringUtils.isBlank(codicecomune)) {
		// Il codice comune non è passato in query strin, sono al primo accesso in visualizzazione,
		// prendo il primo trovato
		codicecomune = comuni.getCodicecomune();
	    }
	    Comuni comune = comuniService.findById(codicecomune);
	    // Recupero l'oggetto "Comuniassociatisoftware"
	    Comuniassociatisoftware comuniassociatisoftware = comuniassociatisoftwareService.findByComune(comune);
	    if (comuniassociatisoftware == null) {
		// Non trovato creo un oggetto vuoto per la visualizzazione e l'eventuale inserimento
		comuniassociatisoftware = new Comuniassociatisoftware();
		comuniassociatisoftware.setComuni(comune);
	    }
	    //Recupero l'oggetto comuniassociati
	    Comuniassociati comuniassociati = null;
	    if (EntityUtils.getNestedProperty(comuniassociatisoftware, "comuniassociati") != null) {
		comuniassociati = comuniassociatiService.findById(new ComuniassociatiId(codicecomune));
	    } else {
		// Non trovato creo un oggetto vuoto per la visualizzazione e l'eventuale inserimento
		comuniassociati = new Comuniassociati();
	    }
	    fixRenderComuniassociatisoftwareProperty(comuniassociatisoftware);
	    fixRenderEntityProperty(configurazioneEsistente);
	    fixRenderComuniassociatiProperty(comuniassociati);
	    configurazione.setComuniassociati(comuniassociati);
	    configurazione.setConfigurazione(configurazioneEsistente);
	    configurazione.setComuniassociatisoftware(comuniassociatisoftware);
	    fixRenderEntityProperty(configurazione.getConfigurazione());
	    model.addAttribute("configurazione", configurazione);
	    setPageAttributes(model, request);
	    return "configurazione/formComuniassociatisoftware";
	} catch (Exception e) {
	    // Il resposabile non ha nessuno comune associato (TAB responsabili comuni)
	    log.error("Errore di configurazione: Non è associato un comune al responsabile:  {} [{}]", responsabile.getResponsabile(), responsabile
		    .getId().getCodice());
	    throw new RuntimeException("Errore di configurazione: Non è associato un comune al responsabile:" + responsabile.getResponsabile() + "["
		    + responsabile.getId().getCodice() + "]");
	}
    }

    /**
     * Il metodo restituisce una lista ordinata secondo questa logica: Al primo posta viene messo l'oggetto responsabile
     * comune che è collegato al comune per cui abbiamo inserito/modificato l'oggetto comuni associati software.
     * 
     * @param responsabilicomunis
     * @param idcomune
     * @return
     */
    @RequestMapping
    public String insertOrUpdateComuniassociatisoftware(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand configurazione,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// mi serve per sapere se esiste o no già il record
	Comuniassociatisoftware comuniassociatisoftware = comuniassociatisoftwareService
		.findById(configurazione.getComuniassociatisoftware().getId());
	// verifico se è stato inserito un oggetto ed eventualmento lo setto
	// setto l'oggetto stemma comuniassociati software in configurazione
	if (configurazione.getComuniassociatisoftware() != null && configurazione.getComuniassociatisoftware().getOggetti() != null
		&& configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice() != null) {
	    Integer codice = configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice();
	    Oggetti oggetti = oggettiService.findById(new PkId(codice));
	    configurazione.getComuniassociatisoftware().setOggetti(oggetti);
	}
	fixMergeComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
	try {
	    // non esiste il record
	    if (comuniassociatisoftware == null) {
		configurazione.getComuniassociatisoftware().setSoftware(ORMHelper.getSoftware());
		// se siamo in inserimento di un nuovo record il codice comune passato è null se ci troviamo in un
		// istallazione
		// singolo comune, quindi settatiamo come comune quello in esame
		if (StringUtils.isBlank(configurazione.getComuniassociatisoftware().getComuni().getCodicecomune())) {
		    Comuni comuni = comuniService.findById(ORMHelper.getIdcomune());
		    configurazione.getComuniassociatisoftware().setComuni(comuni);
		}
		insertOrUpdateComuniAssociati(configurazione);
		comuniassociatisoftwareService.insert(configurazione.getComuniassociatisoftware());
	    } else {
		insertOrUpdateComuniAssociati(configurazione);
		comuniassociatisoftwareService.update(configurazione.getComuniassociatisoftware());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, configurazione.getComuniassociatisoftware(), true, "comuniassociatisoftware", e);
	    fixRenderComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
	    setPageAttributes(model, request);
	    return "configurazione/formComuniassociatisoftware";
	}
	status.setComplete();
	String comune = (String) EntityUtils.getNestedProperty(configurazione, "comuniassociatisoftware.comuni.codicecomune");
	if (StringUtils.isBlank(comune)) {
	    throw new RuntimeException("Il record di configurazione COMUNIASSOCIATI SOFTWARE non ha settato il valore di CodiceComune(Rif:["
		    + ORMHelper.getIdcomune() + ", " + ORMHelper.getSoftware() + "]). Contattare l'assistenza");
	}
	return "redirect:createComuniassociatisoftware.htm?software=" + configurazione.getConfigurazione().getId().getSoftware()
		+ "&idcomuneassociato=" + comune + "&status_msg=02";
    }

    /**
     * Inserisce i valori in comuni associati (CodiceamministrazioneIpa e Codicefiscale), se il record già esiste lo
     * aggiorna, se non esiste lo crea con i valori passati
     * 
     * @param configurazione
     */
    private void insertOrUpdateComuniAssociati(ConfigurazioneCommand configurazione) {

	// Il codice del comune lo recupero da quello passato dal form merorizzato dentro
	//configurazione.getComuniassociatisoftware().getComuni().getCodicecomune())
	String codicecomune = configurazione.getComuniassociatisoftware().getComuni().getCodicecomune();
	Comuniassociati comuniassociati = comuniassociatiService.findById(new ComuniassociatiId(codicecomune));
	comuniassociati.setCodiceamministrazioneIpa(configurazione.getComuniassociati().getCodiceamministrazioneIpa());
	comuniassociati.setCodicefiscale(configurazione.getComuniassociati().getCodicefiscale());
	comuniassociati.setCodiceEntePayer(configurazione.getComuniassociati().getCodiceEntePayer());
	if (comuniassociati != null) {
	    comuniassociatiService.update(comuniassociati);
	    configurazione.getComuniassociatisoftware().setComuniassociati(comuniassociati);
	} else {
	    comuniassociatiService.insert(comuniassociati);
	    configurazione.getComuniassociatisoftware().setComuniassociati(comuniassociati);
	}
    }

    /**
     * Il metodo carica una maschera contentente a parametri di configurazione presenti sulla tabella CONFIGURAZIONI.
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String createAndViewDatitecnici(Model model, HttpServletRequest request) {

	ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
	// Alla creazione dell'id automaticamente si creerà la chiave per la ricerca dato che i campi sono
	// idcomune e software che verranno recuperati dall 'OMRHelper
	// se la ricerca ritrova un oggetto configurazione allora andiamoa modificare quello esistente
	// altrimenti ne vreiamo uno nuovo
	configurazione.setDisplayMode(ConfigurazioneCommand.VIEW);
	Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId());
	if (configurazioneEsistente == null) {
	    configurazioneEsistente = new Configurazione();
	    configurazione.setDisplayMode(ConfigurazioneCommand.NEW);
	}
	fixRenderEntityProperty(configurazioneEsistente);
	configurazione.setConfigurazione(configurazioneEsistente);
	fixRenderEntityProperty(configurazione.getConfigurazione());
	setPageAttributes(model, request);
	setConfigurazioneutenteDatitecnici(request);
	model.addAttribute("configurazione", configurazione);
	return "configurazione/formDatitecnici";
    }

    @RequestMapping
    public String insertOrUpdateComuniassociatidatitecnici(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand configurazione,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(configurazione.getConfigurazione());
	try {
	    if (configurazione.getDisplayMode() == 0) {
		configurazioneService.insertNoValidate(configurazione.getConfigurazione());
	    } else {
		configurazioneService.update(configurazione.getConfigurazione());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, configurazione.getConfigurazione(), true, "configurazione", e);
	    fixRenderEntityProperty(configurazione.getConfigurazione());
	    // Configurazione dei parametri per la gestione della visulazzazione compatta o estesa di ogni singola
	    // sezione
	    // della pagina
	    setConfigurazioneutenteDatitecnici(request);
	    return "configurazione/formDatitecnici";
	}
	// Configurazione dei parametri per la gestione della visulazzazione compatta o estesa di ogni singola sezione
	// della pagina
	setConfigurazioneutenteDatitecnici(request);
	status.setComplete();
	return "redirect:createAndViewDatitecnici.htm?status_msg=02";
    }

    //-------------------------------------------------------------------------------------------------------------------------////
    /////////////--------------------------------GESTIONE LOGHI FRONTOFFICE-----------------------------------------------------///
    ////////////--------------------------------------------------------------------------------------------------------------////
    ////////////--------------------------------------------------------------------------------------------------------------////
    ///-----------------------------------------------------------------------------------------------------------------------////
    /**
     * Recupera i valori che vogliamo modificare,dall'oggetto configurazione
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String createAndViewLoghi(Model model, HttpServletRequest request) {

	ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
	// Alla creazione dell'id automaticamente si creerà la chiave per la ricerca dato che i campi sono
	// idcomune e software che verranno recuperati dall 'OMRHelper
	// se la ricerca ritrova un oggetto configurazione allora andiamoa modificare quello esistente
	// altrimenti ne vreiamo uno nuovo
	configurazione.setDisplayMode(ConfigurazioneCommand.VIEW);
	Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId());
	if (configurazioneEsistente == null) {
	    configurazioneEsistente = new Configurazione();
	    configurazione.setDisplayMode(ConfigurazioneCommand.NEW);
	}
	fixRenderEntityProperty(configurazioneEsistente);
	configurazione.setConfigurazione(configurazioneEsistente);
	fixRenderEntityProperty(configurazione.getConfigurazione());
	model.addAttribute("configurazione", configurazione);
	return "configurazione/formLoghi";
    }

    /**
     * Permette di modificare o inserire nell'oggetto configurazione i campi
     * scrittaregione,oggettoLogoregione,oggettoLogocomune
     * 
     * @param model
     * @param configurazione
     * @param result
     * @param status
     * @param request
     * @return
     */
    @RequestMapping
    public String insertOrUpdateLoghi(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand configurazione, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Configurazione entity = configurazione.getConfigurazione();
	if (EntityUtils.getNestedProperty(entity.getOggettoLogoregione(), "id.codice") != null) {
	    Oggetti oggettoLogoregione = oggettiService.findById(new PkId(entity.getOggettoLogoregione().getId().getCodice()));
	    entity.setOggettoLogoregione(oggettoLogoregione);
	}
	if (EntityUtils.getNestedProperty(entity.getOggettoLogocomune(), "id.codice") != null) {
	    Oggetti oggettoLogocomune = oggettiService.findById(new PkId(entity.getOggettoLogocomune().getId().getCodice()));
	    entity.setOggettoLogocomune(oggettoLogocomune);
	}
	fixMergeEntityProperty(configurazione.getConfigurazione());
	try {
	    if (configurazione.getDisplayMode() == 0) {
		configurazioneService.insertLoghi(entity);
	    } else {
		configurazioneService.updateLoghi(entity);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, configurazione.getConfigurazione(), true, "configurazione", e);
	    fixRenderEntityProperty(configurazione.getConfigurazione());
	    // Configurazione dei parametri per la gestione della visulazzazione compatta o estesa di ogni singola
	    // sezione
	    // della pagina
	    model.addAttribute("configurazione", configurazione);
	    return "configurazione/formLoghi";
	}
	// Configurazione dei parametri per la gestione della visulazzazione compatta o estesa di ogni singola sezione
	// della pagina
	status.setComplete();
	return "redirect:createAndViewLoghi.htm?status_msg=02";
    }

    /**
     * Gestione della configurazione delle mail e testi tipo
     */
    @RequestMapping
    public String createConfigurazioneMailAndTestiTpo(Model model, HttpServletRequest request) {

	ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
	configurazione.setDisplayMode(ConfigurazioneCommand.VIEW);
	Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId());
	ProtocolloConfigurazione protocolloConfigurazione = protocolloConfigurazioneService.findById(new ProtocolloConfigurazioneId());
	if (configurazioneEsistente == null) {
	    configurazioneEsistente = new Configurazione();
	    configurazione.setDisplayMode(ConfigurazioneCommand.NEW);
	}
	if (protocolloConfigurazione == null) {
	    protocolloConfigurazione = new ProtocolloConfigurazione();
	    configurazione.setDisplayMode(ConfigurazioneCommand.NEW);
	}
	fixRenderEntityProperty(configurazioneEsistente);
	fixRenderProtocolloConfigurazioneProperty(protocolloConfigurazione);
	configurazione.setConfigurazione(configurazioneEsistente);
	configurazione.setProtocolloConfigurazione(protocolloConfigurazione);
	fixRenderEntityProperty(configurazione.getConfigurazione());
	model.addAttribute("configurazione", configurazione);
	return "configurazione/formMailAndTestiTipo";
    }

    @RequestMapping
    public String insertOrUpdateConfigurazioneMailAntTestiTpo(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand configurazione,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	//	Configurazione entity = configurazione.getConfigurazione();
	configurazione = setCampiAjax(configurazione, request);
	fixMergeEntityProperty(configurazione.getConfigurazione());
	fixMergeProtocolloConfigurazioneProperty(configurazione.getProtocolloConfigurazione());
	try {
	    configurazioneService.insertOrUpdateConfigurazioneMailAntTestiTipo(configurazione.getConfigurazione(),
		    configurazione.getProtocolloConfigurazione());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, configurazione.getConfigurazione(), true, "configurazione", e);
	    fixRenderEntityProperty(configurazione.getConfigurazione());
	    model.addAttribute("configurazione", configurazione);
	    return "configurazione/formMailAndTestiTipo";
	}
	status.setComplete();
	return "redirect:createConfigurazioneMailAndTestiTpo.htm?status_msg=02";
    }

    private ConfigurazioneCommand setCampiAjax(ConfigurazioneCommand configurazione, HttpServletRequest request) {

	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (StringUtils.isNotBlank(request.getParameter("configurazione.mailtipoAmministrazioneEndo.id.codice"))) {
	    String codicemailtipo = request.getParameter("configurazione.mailtipoAmministrazioneEndo.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getConfigurazione().setMailtipoAmministrazioneEndo(mailtipo);
	} else {
	    configurazione.getConfigurazione().setMailtipoAmministrazioneEndo(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("configurazione.mailtipoMovimentoNegativo.id.codice"))) {
	    String codicemailtipo = request.getParameter("configurazione.mailtipoMovimentoNegativo.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getConfigurazione().setMailtipoMovimentoNegativo(mailtipo);
	} else {
	    configurazione.getConfigurazione().setMailtipoMovimentoNegativo(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("configurazione.mailtipoMovimentoRichiedente.id.codice"))) {
	    String codicemailtipo = request.getParameter("configurazione.mailtipoMovimentoRichiedente.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getConfigurazione().setMailtipoMovimentoRichiedente(mailtipo);
	} else {
	    configurazione.getConfigurazione().setMailtipoMovimentoRichiedente(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("configurazione.mailtipoMovimentoAmministrazione.id.codice"))) {
	    String codicemailtipo = request.getParameter("configurazione.mailtipoMovimentoAmministrazione.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getConfigurazione().setMailtipoMovimentoAmministrazione(mailtipo);
	} else {
	    configurazione.getConfigurazione().setMailtipoMovimentoAmministrazione(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("protocolloConfigurazione.mailtipoByFkIstanza.id.codice"))) {
	    String codicemailtipo = request.getParameter("protocolloConfigurazione.mailtipoByFkIstanza.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getProtocolloConfigurazione().setMailtipoByFkIstanza(mailtipo);
	} else {
	    configurazione.getProtocolloConfigurazione().setMailtipoByFkIstanza(null);
	}
	if (StringUtils.isNotBlank(request.getParameter("protocolloConfigurazione.mailtipoByFkMovimento.id.codice"))) {
	    String codicemailtipo = request.getParameter("protocolloConfigurazione.mailtipoByFkMovimento.id.codice");
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(Integer.parseInt(codicemailtipo)));
	    configurazione.getProtocolloConfigurazione().setMailtipoByFkMovimento(mailtipo);
	} else {
	    configurazione.getProtocolloConfigurazione().setMailtipoByFkMovimento(null);
	}
	//	if (EntityUtils.getNestedProperty(configurazione.getConfigurazione().getMailtipoAmministrazioneEndo(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazione().getMailtipoAmministrazioneEndo().getId()
	//		    .getCodice()));
	//	    configurazione.getConfigurazione().setMailtipoAmministrazioneEndo(mailtipo);
	//	}
	//	if (EntityUtils.getNestedProperty(configurazione.getConfigurazione().getMailtipoMovimentoAmministrazione(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazione().getMailtipoMovimentoAmministrazione().getId()
	//		    .getCodice()));
	//	    configurazione.getConfigurazione().setMailtipoMovimentoAmministrazione(mailtipo);
	//	}
	//	if (EntityUtils.getNestedProperty(configurazione.getConfigurazione().getMailtipoMovimentoNegativo(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazione().getMailtipoMovimentoNegativo().getId()
	//		    .getCodice()));
	//	    configurazione.getConfigurazione().setMailtipoMovimentoNegativo(mailtipo);
	//	}
	//	if (EntityUtils.getNestedProperty(configurazione.getConfigurazione().getMailtipoMovimentoRichiedente(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getConfigurazione().getMailtipoMovimentoRichiedente().getId()
	//		    .getCodice()));
	//	    configurazione.getConfigurazione().setMailtipoMovimentoRichiedente(mailtipo);
	//	}
	//	if (EntityUtils.getNestedProperty(configurazione.getProtocolloConfigurazione().getMailtipoByFkIstanza(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getProtocolloConfigurazione().getMailtipoByFkIstanza().getId()
	//		    .getCodice()));
	//	    configurazione.getProtocolloConfigurazione().setMailtipoByFkIstanza(mailtipo);
	//	}
	//	if (EntityUtils.getNestedProperty(configurazione.getProtocolloConfigurazione().getMailtipoByFkMovimento(), "id.codice") != null) {
	//	    Mailtipo mailtipo = mailtipoService.findById(new PkId(configurazione.getProtocolloConfigurazione().getMailtipoByFkMovimento().getId()
	//		    .getCodice()));
	//	    configurazione.getProtocolloConfigurazione().setMailtipoByFkMovimento(mailtipo);
	//	}
	return configurazione;
    }

    //------------------------------------------------END-------------------------------------------------------------------////
    /////////////--------------------------------GESTIONE LOGHI FRONTOFFICE-----------------------------------------------------///
    ///-----------------------------------------------------------------------------------------------------------------------////
    ///
    ///
    // DA VALUTARE SE FARLO
    // @RequestMapping
    // public String createAndViewParametri(@RequestParam("software") String software, Model model, HttpServletRequest
    // request) {
    //
    // ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
    // Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId(software));
    // // se non esiste ne creaiamo nuova
    // if (configurazioneEsistente == null) {
    // configurazioneEsistente = new Configurazione();
    // }
    // fixRenderEntityProperty(configurazioneEsistente);
    // configurazione.setConfigurazione(configurazioneEsistente);
    // fixRenderEntityProperty(configurazione.getConfigurazione());
    // model.addAttribute("configurazione", configurazione);
    // setPageAttributes(model, request);
    // return "configurazione/form";
    // }
    //
    // @RequestMapping
    // public String insertOrUpdateParametri(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand
    // configurazione, BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // // setto il documento tipo dell'oggetto configurazione
    // if (configurazione.getConfigurazione() != null && configurazione.getConfigurazione().getOggettomoddoctipo() !=
    // null
    // && configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice() != null) {
    // Integer codice = configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice();
    // Oggetti oggetti = oggettiService.findById(new PkId(codice));
    // configurazione.getConfigurazione().setOggettomoddoctipo(oggetti);
    // }
    // // setto l'oggetto stemma comuniassociati software in configurazione
    // if (configurazione.getComuniassociatisoftware() != null &&
    // configurazione.getComuniassociatisoftware().getOggetti() != null
    // && configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice() != null) {
    // Integer codice = configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice();
    // Oggetti oggetti = oggettiService.findById(new PkId(codice));
    // configurazione.getComuniassociatisoftware().setOggetti(oggetti);
    // }
    // fixMergeEntityProperty(configurazione.getConfigurazione());
    // fixMergeComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
    // try {
    // configurazioneService.insertConfigurazioneAndComuniassociatesoftware(configurazione.getConfigurazione(),
    // configurazione.getComuniassociatisoftware());
    // } catch (Exception e) {
    // copyErrorsToBindingResult(configurazioneService.getValidationMessages(), result,
    // configurazione.getConfigurazione(), true,
    // e.getMessage(), "configurazione");
    // fixRenderEntityProperty(configurazione.getConfigurazione());
    // fixRenderComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
    // setPageAttributes(model, request);
    // return "configurazione/form";
    // }
    // status.setComplete();
    // return "redirect:create.htm?software=" + configurazione.getConfigurazione().getId().getSoftware() +
    // "&status_msg=02";
    // }
    // @RequestMapping
    // public String createAndViewParametri(@RequestParam("software") String software, Model model, HttpServletRequest
    // request) {
    //
    // ConfigurazioneCommand configurazione = new ConfigurazioneCommand();
    // Configurazione configurazioneEsistente = configurazioneService.findById(new ConfigurazioneId(software));
    // // se non esiste ne creaiamo nuova
    // if (configurazioneEsistente == null) {
    // configurazioneEsistente = new Configurazione();
    // }
    // fixRenderEntityProperty(configurazioneEsistente);
    // configurazione.setConfigurazione(configurazioneEsistente);
    // fixRenderEntityProperty(configurazione.getConfigurazione());
    // model.addAttribute("configurazione", configurazione);
    // setPageAttributes(model, request);
    // return "configurazione/form";
    // }
    //
    // @RequestMapping
    // public String insertOrUpdateParametri(Model model, @ModelAttribute("configurazione") ConfigurazioneCommand
    // configurazione, BindingResult result,
    // SessionStatus status, HttpServletRequest request) {
    //
    // // setto il documento tipo dell'oggetto configurazione
    // if (configurazione.getConfigurazione() != null && configurazione.getConfigurazione().getOggettomoddoctipo() !=
    // null
    // && configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice() != null) {
    // Integer codice = configurazione.getConfigurazione().getOggettomoddoctipo().getId().getCodice();
    // Oggetti oggetti = oggettiService.findById(new PkId(codice));
    // configurazione.getConfigurazione().setOggettomoddoctipo(oggetti);
    // }
    // // setto l'oggetto stemma comuniassociati software in configurazione
    // if (configurazione.getComuniassociatisoftware() != null &&
    // configurazione.getComuniassociatisoftware().getOggetti() != null
    // && configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice() != null) {
    // Integer codice = configurazione.getComuniassociatisoftware().getOggetti().getId().getCodice();
    // Oggetti oggetti = oggettiService.findById(new PkId(codice));
    // configurazione.getComuniassociatisoftware().setOggetti(oggetti);
    // }
    // fixMergeEntityProperty(configurazione.getConfigurazione());
    // fixMergeComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
    // try {
    // configurazioneService.insertConfigurazionedatigeneraliAndComuniassociatesoftware(configurazione.getConfigurazione(),
    // configurazione.getComuniassociatisoftware());
    // } catch (Exception e) {
    // copyErrorsToBindingResult(configurazioneService.getValidationMessages(), result,
    // configurazione.getConfigurazione(), true,
    // e.getMessage(), "configurazione");
    // fixRenderEntityProperty(configurazione.getConfigurazione());
    // fixRenderComuniassociatisoftwareProperty(configurazione.getComuniassociatisoftware());
    // setPageAttributes(model, request);
    // return "configurazione/form";
    // }
    // status.setComplete();
    // return "redirect:create.htm?software=" + configurazione.getConfigurazione().getId().getSoftware() +
    // "&status_msg=02";
    // }
    @Override
    protected void fixMergeEntityProperty(Configurazione entity) {

	if (entity.getOggettomoddoctipo() != null && entity.getOggettomoddoctipo().getId().getCodice() == null) {
	    entity.setOggettomoddoctipo(null);
	}
	if (entity.getResponsabili() != null && entity.getResponsabili().getId().getCodice() == null) {
	    entity.setResponsabili(null);
	}
	if (entity.getOggettoLogocomune() != null && entity.getOggettoLogocomune().getId().getCodice() == null) {
	    entity.setOggettoLogocomune(null);
	}
	if (entity.getOggettoLogoregione() != null && entity.getOggettoLogoregione().getId().getCodice() == null) {
	    entity.setOggettoLogoregione(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Configurazione entity) {

	if (EntityUtils.getNestedProperty(entity, "oggettomoddoctipo") == null) {
	    entity.setOggettomoddoctipo(new Oggetti());
	}
	if (EntityUtils.getNestedProperty(entity, "oggettoLogoregione") == null) {
	    entity.setOggettoLogoregione(new Oggetti());
	}
	if (EntityUtils.getNestedProperty(entity, "oggettoLogocomune") == null) {
	    entity.setOggettoLogocomune(new Oggetti());
	}
	if (EntityUtils.getNestedProperty(entity, "responsabili") == null) {
	    entity.setResponsabili(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipoAmministrazioneEndo") == null) {
	    entity.setMailtipoAmministrazioneEndo(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipoMovimentoNegativo") == null) {
	    entity.setMailtipoMovimentoNegativo(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipoMovimentoRichiedente") == null) {
	    entity.setMailtipoMovimentoRichiedente(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipoMovimentoAmministrazione") == null) {
	    entity.setMailtipoMovimentoAmministrazione(new Mailtipo());
	}
    }

    protected void fixMergeComuniassociatisoftwareProperty(Comuniassociatisoftware entity) {

	if (entity.getOggetti() != null && entity.getOggetti().getId().getCodice() == null) {
	    entity.setOggetti(null);
	}
	if (entity.getComuniassociati() != null && StringUtils.isBlank(entity.getComuniassociati().getId().getIdcomune())) {
	    entity.setComuniassociati(null);
	}
	if (entity.getConfigurazione() != null && StringUtils.isBlank(entity.getConfigurazione().getId().getIdcomune())) {
	    entity.setConfigurazione(null);
	}
    }

    protected void fixRenderComuniassociatisoftwareProperty(Comuniassociatisoftware entity) {

	if (EntityUtils.getNestedProperty(entity, "oggetti") == null) {
	    entity.setOggetti(new Oggetti());
	}
	if (EntityUtils.getNestedProperty(entity, "comuniassociati") == null) {
	    entity.setComuniassociati(new Comuniassociati());
	}
	if (EntityUtils.getNestedProperty(entity, "configurazione") == null) {
	    entity.setConfigurazione(new Configurazione());
	}
    }

    protected void fixMergeProtocolloConfigurazioneProperty(ProtocolloConfigurazione entity) {

	//	if (entity.getMailtipoByFkIstanza() != null && entity.getMailtipoByFkIstanza().getId().getCodice() == null) {
	//	    entity.setMailtipoByFkIstanza(null);
	//	}
	//	if (entity.getMailtipoByFkMovimento() != null && StringUtils.isBlank(entity.getMailtipoByFkMovimento().getId().getIdcomune())) {
	//	    entity.setMailtipoByFkMovimento(null);
	//	}
    }

    private void fixRenderProtocolloConfigurazioneProperty(ProtocolloConfigurazione entity) {

	if (EntityUtils.getNestedProperty(entity, "mailtipoByFkIstanza") == null) {
	    entity.setMailtipoByFkIstanza(new Mailtipo());
	}
	if (EntityUtils.getNestedProperty(entity, "mailtipoByFkMovimento") == null) {
	    entity.setMailtipoByFkMovimento(new Mailtipo());
	}
    }

    private void fixRenderComuniassociatiProperty(Comuniassociati entity) {

	if (EntityUtils.getNestedProperty(entity, "comune") == null) {
	    entity.setComune(new Comuni());
	}
	if (EntityUtils.getNestedProperty(entity, "oggetti") == null) {
	    entity.setOggetti(new Oggetti());
	}
    }

    protected void setPageAttributes(Model model, HttpServletRequest request) {

	// ---------------------------------INIZIO GESTIONE CONFIGURAZIONE
	// UTENTE------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_INTESTAZIONI_COMUNIASSOCIATI, "1", request);// ------------------
	// ----------------------------------------------------------------------------------------------------------------------------
	// ---------------------------------FINE GESTIONE CONFIGURAZIONE
	// UTENTE---------------------------------------------------------
	// ---------------------------------INIZIO GESTIONE COMUNI
	// ASSOCIATI------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------------------------
	// Serve per sapere se si tratta di un comune associato, in tal caso sul form di configurazione deve comparire
	// una sezione dedicata
	// all'iserimento dei dati nella tabella comuni associati software che rappresentano i dati generali configurati
	// per tutti i comuni
	// associati
	Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
	boolean isVerticalizzazionePayerAdapter = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PAYER_ADAPTER, request);
	model.addAttribute("isPayerAdapter", isVerticalizzazionePayerAdapter);
	// ----------------------------------------------------------------------------------------------------------------------------
	// ----------------------------------FINE GESTIONE CONFIGURAZIONE
	// UTENTE---------------------------------------------------------
    }

    /**
     * Metodo privato che setta tutti i parametri per la configurazione della visualizzazione compatta ed estesa delle
     * sezioni del form a seconda dell'utente loggta
     * 
     * @param request
     */
    private void setConfigurazioneutenteDatitecnici(HttpServletRequest request) {

	// Configurazione dei parametri per la gestione della visulazzazione compatta o estesa di ogni singola sezione
	// della pagina
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_NUMERAZIONE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_GENERALE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_ISTANZE_PROTOCOLLO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VARIE, "1", request);
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
