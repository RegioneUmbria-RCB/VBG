package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Stili;
import it.gruppoinit.pal.gp.core.domain.web.ConfigurazioniutenteCommand;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.LinkPreferitiUtenteService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.StiliService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@SessionAttributes("configurazioneutenteCommand")
public class ConfigurazioneutenteController extends BaseController<Configurazioneutente> {

    private static final Logger log = LoggerFactory.getLogger(ConfigurazioneutenteController.class);
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private StiliService stiliService;
    @Autowired
    private LinkPreferitiUtenteService linkPreferitiUtenteService;
    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) {

	ConfigurazioniutenteCommand configurazioneutenteCommand = new ConfigurazioniutenteCommand();
	// Gestisce la logica di recupero della confifigurazione utente  per mostrala sulla maschera di modifica delle impostazioni.
	// Se si vogliono aggiungere ulteriori parametri di configurazione sulla pagina, la logica deve essere aggiunta
	// all'interno del metodo
	// Gestione visulillazione tabella dei link preferiti impostati dall'utente
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	Integer codObj = responsabile.getOggettoImmagine() != null ? responsabile.getOggettoImmagine().getId().getCodice() : null;
	configurazioneutenteCommand.setUserObjectId(codObj);
	prepareView(model, configurazioneutenteCommand);
	setPageAttributes(model);
	List<LinkPreferitiUtente> linkPreferitiUtente = linkPreferitiUtenteService.findLinkPreferitiUtente(responsabile.getId().getCodice(), null,
		null);
	//ModelMap model = new ModelMap(linkPreferitiUtente);
	boolean export = createJMesaExport(request, response, linkPreferitiUtente);
	if (export) {
	    return null;
	}
	model.addAttribute("linkPreferitiUtente", linkPreferitiUtente);
	return "configurazioneutente/form";
    }

    /**
     * Gestisce la logica di visualizzazione delle configurazione utenti da visualizzare sulla pagina di modifica
     * 
     * @param model
     * @param configurazioneutenteCommand
     */
    private void prepareView(Model model, ConfigurazioniutenteCommand configurazioneutenteCommand) {

	// Recupero l'utente loggato
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	// Recupero le configurazioni utente che si vogliono visualizzare/Modificare
	// Configurazione utente: Numero massimo di record che si possono visualizzare su una tabella
	Configurazioneutente limitePaginazioneListe = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId()
		.getCodice(), WebConstants.CONF_UTENTE_NUMERO_RECORD_LISTE));
	// Configurazione utente: Comportamento da effettuare al salvataggio di un moviemento
	Configurazioneutente salvaEchiudiInMovimenti = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId()
		.getCodice(), WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI));
	// Configurazione utente: Stile impostato dall'utente
	Configurazioneutente stileBO = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_STILE_BO));
	// Controlla se esiste una configurazione per CONF_UTENTE_NUMERO_RECORD_LISTE
	if (limitePaginazioneListe != null) {
	    if (StringUtils.isNotBlank(limitePaginazioneListe.getValore())) {
		configurazioneutenteCommand.setLimitePaginazioneListe(limitePaginazioneListe.getValore());
	    }
	}
	// Controlla se esiste una configurazione per CONF_UTENTE_MOVIMENTI_OK_CHIUDI
	if (salvaEchiudiInMovimenti != null && StringUtils.isNotBlank(salvaEchiudiInMovimenti.getValore())) {
	    boolean valore = false;
	    if (salvaEchiudiInMovimenti.getValore().equals("1")) {
		valore = true;
	    }
	    configurazioneutenteCommand.setSalvaEchiudiInMovimenti(valore);
	}
	List<Stili> stilis = null;
	// Controlla se esiste una configurazione per CONF_UTENTE_STILE_BO
	if (stileBO != null) {
	    // Se esiste la recupera e la mette in prima posizione sulla lista
	    // della combo box
	    if (StringUtils.isNotBlank(stileBO.getValore())) {
		configurazioneutenteCommand.setStyleCss(stileBO.getValore());
		Stili stileConfigurato = stiliService.findByNome(stileBO.getValore());
		stilis = stiliService.findAll(null, null);
		stilis.remove(stileConfigurato);
		stilis.add(0, stileConfigurato);
	    } else {// Le mostra tutte in ordine alfabetico
		stilis = stiliService.findAll(null, null);
	    }
	} else {
	    stilis = stiliService.findAll(null, null);
	}
	// Setta tutte le info sul model
	model.addAttribute("configurazioneutenteCommand", configurazioneutenteCommand);
	model.addAttribute("stilis", stilis);
    }

    @RequestMapping
    public String insertOrUpdate(Model model, @RequestParam(value = "usrimg", required = false) MultipartFile userImage,
	    @ModelAttribute("configurazioneutenteCommand") ConfigurazioniutenteCommand configurazioneutenteCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) throws IOException {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	//gestione aggiornamenti immagine utente
	boolean updloadImage = userImage != null && !userImage.isEmpty() && userImage.getSize() > 0;
	if (updloadImage) {
	    byte[] fileByteArray = userImage.getBytes();
	    Oggetti oggetto = new Oggetti();
	    oggetto.setNomefile(userImage.getOriginalFilename());
	    oggetto.setOggetto(fileByteArray);
	    responsabiliService.updateImmagine(responsabile, oggetto);
	    configurazioneutenteCommand.setUserObjectId(oggetto.getId().getCodice());
	    userDetail.setCodiceOggettoImmagine(oggetto.getId().getCodice());
	    userSecurityService.resetObjectCached();
	} else if (configurazioneutenteCommand.getUserObjectId() == null && responsabile.getOggettoImmagine() != null) {
	    responsabiliService.updateImmagine(responsabile, null);
	    configurazioneutenteCommand.setUserObjectId(null);
	    userDetail.setCodiceOggettoImmagine(null);
	    userSecurityService.resetObjectCached();
	}
	Configurazioneutente limitePaginazioneListe = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId()
		.getCodice(), WebConstants.CONF_UTENTE_NUMERO_RECORD_LISTE));
	configurazioneutenteService.insertOrUpdate(limitePaginazioneListe, WebConstants.CONF_UTENTE_NUMERO_RECORD_LISTE,
		configurazioneutenteCommand.getLimitePaginazioneListe(), responsabile);
	Configurazioneutente salvaEchiudiInMovimenti = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId()
		.getCodice(), WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI));
	configurazioneutenteService.insertOrUpdate(salvaEchiudiInMovimenti, WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI,
		String.valueOf(BooleanUtils.toInteger(configurazioneutenteCommand.getSalvaEchiudiInMovimenti())), responsabile);
	/*
	Configurazioneutente stileBO = configurazioneutenteService.findById(new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_STILE_BO));
	configurazioneutenteService.insertOrUpdate(stileBO, WebConstants.CONF_UTENTE_STILE_BO, configurazioneutenteCommand.getStyleCss(),
		responsabile);
	*/
	return "redirect:view.htm" + "?status_msg=02";
    }

    @RequestMapping
    public String ajaxAddPreferito(Model model, HttpServletResponse response) throws IOException {

	ConfigurazioniutenteCommand configurazioneutenteCommand = new ConfigurazioniutenteCommand();
	LinkPreferitiUtente linkPreferitiUtente = new LinkPreferitiUtente();
	configurazioneutenteCommand.setLinkPreferitiUtente(linkPreferitiUtente);
	model.addAttribute("configurazioneutenteCommand", configurazioneutenteCommand);
	response.setContentType("text/plain");
	return "configurazioneutente/formAddPreferito";
    }

    @RequestMapping
    public String aggiungiLinkPreferito(Model model,
	    @ModelAttribute("configurazioneutenteCommand") ConfigurazioniutenteCommand configurazioneutenteCommand, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	LinkPreferitiUtente linkPreferitiUtente = configurazioneutenteCommand.getLinkPreferitiUtente();
	linkPreferitiUtente.setResponsabili(responsabili);
	try {
	    linkPreferitiUtenteService.insert(linkPreferitiUtente);
	    HttpSession session = request.getSession();
	    session.removeAttribute("linkPreferitis");
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    // Recupero i preferiti per l'utente loggato
	    List<LinkPreferitiUtente> linkPreferitis = linkPreferitiUtenteService.findLinkPreferitiUtente(responsabile.getId().getCodice(), null,
		    null);
	    // Recupero il link preferiti se ci sono e li mentto in sessione
	    session.setAttribute("linkPreferitis", linkPreferitis);
	} catch (RuntimeException e) {
	    log.error("Errore inserimento link nei preferiti : {} ({})", new Object[] { e.getMessage(), e });
	}
	return "redirect:view.htm" + "?status_msg=02";
    }

    @RequestMapping
    public String deleteLinkPreferiti(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	LinkPreferitiUtente linkPreferitiUtente = linkPreferitiUtenteService.findById(new PkId(codice));
	try {
	    linkPreferitiUtenteService.delete(linkPreferitiUtente);
	    // Devo aggiornare la session, togliendo il link appena eliminato
	    HttpSession session = request.getSession();
	    session.removeAttribute("linkPreferitis");
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    // Recupero i preferiti per l'utente loggato
	    List<LinkPreferitiUtente> linkPreferitis = linkPreferitiUtenteService.findLinkPreferitiUtente(responsabile.getId().getCodice(), null,
		    null);
	    // Recupero il link preferiti se ci sono e li mentto in sessione
	    session.setAttribute("linkPreferitis", linkPreferitis);
	} catch (RuntimeException e) {
	    log.error("Errore durante la cancellazione  link nei preferiti : {} ({})", new Object[] { e.getMessage(), e });
	}
	return "redirect:view.htm" + "?status_msg=02";
    }

    @RequestMapping
    public String ajaxUpdatePreferito(@RequestParam("codice") String codice, Model model, HttpServletResponse response) throws IOException {

	ConfigurazioniutenteCommand configurazioneutenteCommand = new ConfigurazioniutenteCommand();
	LinkPreferitiUtente linkPreferitiUtente = linkPreferitiUtenteService.findById(new PkId(Integer.parseInt(codice)));
	configurazioneutenteCommand.setLinkPreferitiUtente(linkPreferitiUtente);
	model.addAttribute("configurazioneutenteCommand", configurazioneutenteCommand);
	response.setContentType("text/plain");
	return "configurazioneutente/formAddPreferito";
    }

    @RequestMapping
    public String updateLinkPreferito(@ModelAttribute("configurazioneutenteCommand") ConfigurazioniutenteCommand configurazioneutenteCommand,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    linkPreferitiUtenteService.update(configurazioneutenteCommand.getLinkPreferitiUtente());
	    HttpSession session = request.getSession();
	    session.removeAttribute("linkPreferitis");
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    // Recupero i preferiti per l'utente loggato
	    List<LinkPreferitiUtente> linkPreferitis = linkPreferitiUtenteService.findLinkPreferitiUtente(responsabile.getId().getCodice(), null,
		    null);
	    // Recupero il link preferiti se ci sono e li mentto in sessione
	    session.setAttribute("linkPreferitis", linkPreferitis);
	} catch (RuntimeException e) {
	    log.error("Errore inserimento link nei preferiti : {} ({})", new Object[] { e.getMessage(), e });
	}
	return "redirect:view.htm" + "?status_msg=02";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Configurazioneutente entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Configurazioneutente entity) {

    }
}
