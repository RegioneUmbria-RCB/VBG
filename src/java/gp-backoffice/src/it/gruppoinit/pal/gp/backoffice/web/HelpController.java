package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Help;
import it.gruppoinit.pal.gp.core.domain.HelpId;
import it.gruppoinit.pal.gp.core.domain.Helpbase;
import it.gruppoinit.pal.gp.core.domain.HelpbaseId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.web.HelpCommand;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.HelpService;
import it.gruppoinit.pal.gp.core.service.HelpbaseService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class HelpController extends BaseController<Help> {

    @Autowired
    private HelpService helpService;
    @Autowired
    private HelpbaseService helpbaseService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private SoftwareService softwareService;

    /**
     * funzionalità per la visualizzazione dell'help per la funzionalità corrente
     * 
     * @param contentType
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public String ajaxshowHelp(@RequestParam("contenttype") String contentType, @RequestParam(value = "modulo", required = false) String modulo,
	    @RequestParam(value = "isBase", required = false) String isBase, Model model, HttpServletResponse response) throws IOException {

	HelpCommand helpCommand = new HelpCommand();
	Help help = null;
	Helpbase helpbase = null;
	// se passo un software specifico e sto configurando l'help base
	if (StringUtils.isNotBlank(modulo) && isBase.equals("true")) {
	    helpbase = helpbaseService.findByContentAndSoftwares(contentType, modulo);
	} else {// se  non passo un software specifico o non sto configurando l'help base
	    helpbase = helpbaseService.findByContentAndSoftwares(contentType, null);
	}
	// se passo un software specifico e sto configurando l'help specifico
	if (StringUtils.isNotBlank(modulo) && isBase.equals("false")) {
	    help = helpService.findByContentAndSoftwares(contentType, modulo);
	} else {// se  non passo un software specifico o non sto configurando l'help specifico
	    help = helpService.findByContentAndSoftwares(contentType, null);
	}
	// setto l'help e l'help base sul command
	helpCommand.setHelp(help);
	helpCommand.setHelpbase(helpbase);
	// inizializzo gli oggetti se sono null (non trovati sul db, fase di inserimento)
	fixRenderEntityCommandProperty(helpCommand, contentType, modulo);
	// recupero l'utente loggato
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	// verifico se l'utente può cambiare l'Help di base
	boolean isChangeHelpBase = helpbaseService.isAllowedChangeHelpBase(responsabile.getId().getCodice(), contentType);
	// verifico se l'utente può cambiare l'Help specifico
	boolean isChangeHelp = helpService.isAllowedChangeHelp(responsabile.getId().getCodice());
	List<Software> listSoftware = new ArrayList<Software>();
	List<Software> listSoftwareAbilitati = new ArrayList<Software>();
	// recupero i software solo se isChangeHelpBase==true
	if (isChangeHelpBase) {
	    listSoftware = softwareService.findAll(null, null);
	}
	// recupero i software abilitati per l'utente solo se isChangeHelp==true
	if (isChangeHelp) {
	    listSoftwareAbilitati = softwareService.findSoftwareAbilitati(responsabile);
	}
	model.addAttribute("listSoftware", listSoftware);
	model.addAttribute("listSoftwareAbilitati", listSoftwareAbilitati);
	model.addAttribute("isChangeHelpBase", isChangeHelpBase);
	model.addAttribute("isChangeHelp", isChangeHelp);
	model.addAttribute("helpCommand", helpCommand);
	return "ajax/dettaglioHelp";
    }

    @RequestMapping
    public String ajaxGetHelp(@RequestParam("contenttype") String contentType, @RequestParam(value = "modulo", required = false) String modulo,
	    @RequestParam(value = "isBase", required = false) String isBase, Model model, HttpServletResponse response) throws IOException {

	HelpCommand helpCommand = new HelpCommand();
	Help help = null;
	Helpbase helpbase = null;
	// se passo un software specifico e sto configurando l'help base
	if (StringUtils.isNotBlank(modulo) && isBase.equals("true")) {
	    helpbase = helpbaseService.findByContentAndSoftwares(contentType, modulo);
	} else {// se  non passo un software specifico o non sto configurando l'help base
	    helpbase = helpbaseService.findByContentAndSoftwares(contentType, null);
	}
	// se passo un software specifico e sto configurando l'help specifico
	if (StringUtils.isNotBlank(modulo) && isBase.equals("false")) {
	    help = helpService.findByContentAndSoftwares(contentType, modulo);
	} else {// se  non passo un software specifico o non sto configurando l'help specifico
	    help = helpService.findByContentAndSoftwares(contentType, null);
	}
	// setto l'help e l'help base sul command
	helpCommand.setHelp(help);
	helpCommand.setHelpbase(helpbase);
	// inizializzo gli oggetti se sono null (non trovati sul db, fase di inserimento)
	fixRenderEntityCommandProperty(helpCommand, contentType, modulo);
	// recupero l'utente loggato
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	// verifico se l'utente può cambiare l'Help di base
	boolean isChangeHelpBase = helpbaseService.isAllowedChangeHelpBase(responsabile.getId().getCodice(), contentType);
	// verifico se l'utente può cambiare l'Help specifico
	boolean isChangeHelp = helpService.isAllowedChangeHelp(responsabile.getId().getCodice());
	List<Software> listSoftware = new ArrayList<Software>();
	List<Software> listSoftwareAbilitati = new ArrayList<Software>();
	// recupero i software solo se isChangeHelpBase==true
	if (isChangeHelpBase) {
	    listSoftware = softwareService.findAll(null, null);
	}
	// recupero i software abilitati per l'utente solo se isChangeHelp==true
	if (isChangeHelp) {
	    listSoftwareAbilitati = softwareService.findSoftwareAbilitati(responsabile);
	}
	model.addAttribute("listSoftware", listSoftware);
	model.addAttribute("listSoftwareAbilitati", listSoftwareAbilitati);
	model.addAttribute("isChangeHelpBase", isChangeHelpBase);
	model.addAttribute("isChangeHelp", isChangeHelp);
	model.addAttribute("helpCommand", helpCommand);
	return "ajax/dettaglioHelp";
    }

    @RequestMapping
    public void ajaxSaveHelpbase(Model model, @ModelAttribute("helpCommand") HelpCommand helpCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	HelpbaseId id = new HelpbaseId(helpCommand.getHelpbase().getId().getContenttype(), 0, helpCommand.getHelpbase().getId().getSoftware());
	Helpbase helpbase = helpbaseService.findById(id);
	try {
	    // se esiste modifico solo il campo di testo
	    if (helpbase != null) {
		helpbase.setHelptext(helpCommand.getHelpbase().getHelptext());
		helpbaseService.update(helpbase);
	    } else {// creo un nuovo oggetto e lo inserisco nel DB
		HelpbaseId idTemp = helpCommand.getHelpbase().getId();
		idTemp.setTab(0);
		idTemp.setContenttype(helpCommand.getHelpbase().getId().getContenttype());
		id.setSoftware(helpCommand.getHelpbase().getId().getSoftware());
		Helpbase helpbaseTemp = new Helpbase();
		helpbaseTemp.setId(idTemp);
		helpbaseTemp.setHelptext(helpCommand.getHelpbase().getHelptext());
		helpbaseService.insert(helpCommand.getHelpbase());
	    }
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.errore: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public void ajaxSaveHelp(Model model, @ModelAttribute("helpCommand") HelpCommand helpCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	HelpId id = new HelpId(helpCommand.getHelp().getId().getSoftware(), helpCommand.getHelp().getId().getContenttype(), 0);
	Help help = helpService.findById(id);
	try {
	    // se esiste modifico solo il campo di testo
	    if (help != null) {
		help.setHelptext(helpCommand.getHelp().getHelptext());
		helpService.update(help);
	    } else {// creo un nuovo oggetto e lo inserisco nel DB
		HelpId idTemp = helpCommand.getHelp().getId();
		idTemp.setTab(0);
		idTemp.setContenttype(helpCommand.getHelp().getId().getContenttype());
		id.setSoftware(helpCommand.getHelp().getId().getSoftware());
		Help helpTemp = new Help();
		helpTemp.setId(idTemp);
		helpTemp.setHelptext(helpCommand.getHelp().getHelptext());
		helpService.insert(helpCommand.getHelp());
	    }
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.errore: " + e.getMessage(), null));
	}
    }

    /**
     * funzionalità per la gestione della visualizzazione del link per l'help. <br>
     * (utilizzabile solo con l'integrazione con sigeproMS dato che senza integrazione l'oggetto UserDetails non
     * contiene il codiceresponsabile ma lo userid)
     * 
     * @param contentType
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String showHelpBox(@RequestParam("contenttype") String contentType, HttpServletResponse response) throws IOException {

	Responsabili details = getCurrentlyAuthenticatedUserDetails();
	Integer codiceResponsabile = details.getId().getCodice();
	if (helpService.existHelp(codiceResponsabile, contentType)) {
	    return "includes/help";
	}
	return "includes/blank";
    }

    public void renderHTMLException(String message, HttpServletResponse response) throws IOException {

	StringBuilder sb = new StringBuilder("");
	sb.append(message);
	sb.append("");
	response.getWriter().write(sb.toString());
    }

    private void fixRenderEntityCommandProperty(HelpCommand entity, String contentType, String modulo) {

	if (entity.getHelp() == null) {
	    HelpId id = new HelpId(contentType, 0);
	    if (StringUtils.isNotBlank(modulo)) {
		id.setSoftware(modulo);
	    }
	    Help help = new Help();
	    help.setId(id);
	    entity.setHelp(help);
	}
	if (entity.getHelpbase() == null) {
	    HelpbaseId id = new HelpbaseId(contentType, 0);
	    if (StringUtils.isNotBlank(modulo)) {
		id.setSoftware(modulo);
	    }
	    Helpbase helpbase = new Helpbase();
	    helpbase.setId(id);
	    entity.setHelpbase(helpbase);
	}
    }

    @Override
    protected void fixMergeEntityProperty(Help entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Help entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
