package it.gruppoinit.pal.gp.backoffice.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafe;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiAnagrafeId;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiD;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAccessoAttiTHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiTCommand;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiAnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiDService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiLogService;
import it.gruppoinit.pal.gp.core.service.IstanzeAccessoAttiTService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VwIstanzecollegateService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("istanzeaccessoattit")
public class IstanzeAccessoAttiTController extends BaseController<IstanzeAccessoAttiTCommand> {

    private static final Logger log = LoggerFactory.getLogger(IstanzeAccessoAttiTController.class);
    @Autowired
    private IstanzeAccessoAttiTService istanzeaccessoattitService;
    @Autowired
    private IstanzeAccessoAttiDService istanzeaccessoattidService;
    @Autowired
    private IstanzeAccessoAttiAnagrafeService istanzeacessoattianagrafeService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzecollegateService istanzeCollegateService;
    @Autowired
    private VwIstanzecollegateService vwistanzecollegateService;
    @Autowired
    private IstanzeAccessoAttiLogService istanzeAccessoAttiLogService;

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	List<IstanzeAccessoAttiT> istanzeaccessoattitList = istanzeaccessoattitService.findByIstanza(codiceIstanza);
	ModelMap model = new ModelMap(istanzeaccessoattitList);
	boolean export = createJMesaExport(request, response, istanzeaccessoattitList);
	if (export) {
	    return null;
	}
	model.addAttribute("istanza", istanza);
	model.addAttribute("istanzeaccessoattitList", istanzeaccessoattitList);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model) {

	IstanzeAccessoAttiTCommand command = new IstanzeAccessoAttiTCommand();
	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.findById(idIstanza);
	command.setDisplayMode(IstanzeAccessoAttiTCommand.NEW);
	command.getEntity().setIstanze(istanza);
	Responsabili operatore = getCurrentlyAuthenticatedUserDetails();
	Responsabili resp = new Responsabili();
	resp.getId().setCodice(operatore.getId().getCodice());
	resp.setResponsabile(operatore.getResponsabile());
	command.getEntity().setResponsabili(resp);
	model.addAttribute("istanzeaccessoattit", command);
	setPageAttributes(model);
	return "istanzeaccessoattit/form";
    }

    /**
     * <pre>
     * Il metodo esegue il collegamento di una o più istanze al fascicolo (istanzeaccessoattit), un'istanza da allegare ad un fascicolo può avere 1..N
     * istanze collegate.:
     * 1. Un istanza tra quelle scelte contiene almeno un istanza collegata	:
     * 		1.1  checkIstanzeCollegate == true : la funzionalità riporta ad un apagina intermedia che permette all'operatore di selezionare come istanze da
     *                                                da allegare al fascicolo anche le eventuali istanze collegate
     *          1.2  checkIstanzeCollegate == false  : la funzionalità collega al fascicolo tutte le istanze selezionate
     * NB: se checkIstanzeCollegate == true significa che siamo passati già dalla pagina intermendia del punto 1.1                                       
     * 2. Un istanza tra quelle scelte non contiene almeno un istanza collegata	: tutte le istanze scelte saranno allegate al fascicolo (istanzeaccessoattit)
     * &#64;param codiceIstanzaAccessoAtti
     * &#64;param checkIstanzeCollegate
     * &#64;param lista_istanze_da_collegare_accesso_atti
     * &#64;param lista_istanze_con_doc_validi
     * &#64;param model
     * &#64;param istanzeaccessoattitcommand
     * &#64;param result
     * &#64;param status
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public String addCollegamento(@RequestParam("codiceIstanzaAccessoAtti") Integer codiceIstanzaAccessoAtti,
	    @RequestParam("checkIstanzeCollegate") Integer checkIstanzeCollegate,
	    @RequestParam("lista_istanze_da_collegare_accesso_atti") String lista_istanze_da_collegare_accesso_atti,
	    @RequestParam(required = false, value = "lista_istanze_con_doc_validi") String lista_istanze_con_doc_validi, Model model,
	    @ModelAttribute("istanzeaccessoattit") IstanzeAccessoAttiTCommand istanzeaccessoattitcommand, BindingResult result,
	    SessionStatus status) {

	List<IstanzeAccessoAttiTHelper> accessoAttiTHelpers = null;
	String[] arrayCodiceIstanza = StringUtils.split(lista_istanze_da_collegare_accesso_atti, ",");
	String[] arrayCodiceIstanzaMostraDocValidi = StringUtils.split(lista_istanze_con_doc_validi, ",");
	//1. Verifico se almeno un'istanza scelta ha delle istanze collegate (tabella istanze collegate).
	try {
	    log.debug("addCollegamento# checkIstanzeCollegate = {}", checkIstanzeCollegate);
	    if (checkIstanzeCollegate.equals(1)) {
		//2. Verifico se almeno una delle istanze selezionate abbia delle istanze collegate.
		log.debug("addCollegamento# Controllo esistenza istanze collegate.....");
		if (istanzeCollegateService.isExistIstanzeCollegateByIstanza(arrayCodiceIstanza)) {
		    log.debug("addCollegamento# Istanze collegate = {}, ritorno alla pagina delle istanze collegate....", true);
		    //3.Recupero la lista degli helper (le istanze collegate a ciascuna istanza selezionata dalla ricerca effettuata)
		    accessoAttiTHelpers = istanzeaccessoattitService.findIstanzeCollegate(codiceIstanzaAccessoAtti, arrayCodiceIstanza,
			    arrayCodiceIstanzaMostraDocValidi);
		    model.addAttribute("accessoAttiTHelpers", accessoAttiTHelpers);
		    return "istanzeaccessoattit/addcollegamento";
		} else {
		    // inserimento diretto in ISTANZE_ACCESSO_ATTI_D
		    log.debug("addCollegamento# Istanze collegate = {}, aggiorno fascicolo....", false);
		    istanzeaccessoattidService.insert(arrayCodiceIstanza, arrayCodiceIstanzaMostraDocValidi, codiceIstanzaAccessoAtti);
		    return "redirect:view.htm?codice=" + codiceIstanzaAccessoAtti + "&status_msg=01"; // REDIRECT
		}
	    } else {
		log.debug("addCollegamento# Controllo esistenza istanze collegate già eseguito, aggiorno fascicolo....");
		istanzeaccessoattidService.insert(arrayCodiceIstanza, arrayCodiceIstanzaMostraDocValidi, codiceIstanzaAccessoAtti);
		return "redirect:view.htm?codice=" + codiceIstanzaAccessoAtti + "&status_msg=01"; // REDIRECT
	    }
	} catch (Exception e) {
	    log.error("addCollegamento# e={}", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codice=" + codiceIstanzaAccessoAtti + "&status_msg=05"; // REDIRECT
	}
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("istanzeaccessoattit") IstanzeAccessoAttiTCommand istanzeaccessoattitCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	IstanzeAccessoAttiT entity = istanzeaccessoattitCommand.getEntity();
	fixMergeEntityProperty(istanzeaccessoattitCommand);
	try {
	    istanzeaccessoattitService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattitCommand.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeaccessoattitCommand);
	    istanzeaccessoattitCommand.setDisplayMode(istanzeaccessoattitCommand.NEW);
	    setPageAttribute(model);
	    return "istanzeaccessoattit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IstanzeAccessoAttiTCommand command = new IstanzeAccessoAttiTCommand();
	IstanzeAccessoAttiT istanzeaccessoattit = istanzeaccessoattitService.findById(id);
	command.setDisplayMode(IstanzeAccessoAttiTCommand.VIEW);
	command.setEntity(istanzeaccessoattit);
	setPageAttributes(model);
	fixRenderEntityProperty(command);
	model.addAttribute("istanzeaccessoattit", command);
	prepareViewModel(model, request, command, istanzeaccessoattit);
	return "istanzeaccessoattit/form";
    }

    @RequestMapping
    public String update(@ModelAttribute("istanzeaccessoattit") IstanzeAccessoAttiTCommand istanzeaccessoattitcommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(istanzeaccessoattitcommand);
	try {
	    istanzeaccessoattitService.update(istanzeaccessoattitcommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, istanzeaccessoattitcommand.getEntity(), true, e);
	    fixRenderEntityProperty(istanzeaccessoattitcommand);
	    return "istanzeaccessoattit/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + istanzeaccessoattitcommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("istanzeaccessoattit") IstanzeAccessoAttiTCommand istanzeaccessoattitcommand, BindingResult result,
	    SessionStatus status) {

	IstanzeAccessoAttiT objToDelete = istanzeaccessoattitService.findById(istanzeaccessoattitcommand.getEntity().getId());
	try {
	    istanzeaccessoattitService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(istanzeaccessoattitcommand);
	    return "istanzeaccessoattit/form";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + objToDelete.getIstanze().getId().getCodice();
    }

    @RequestMapping
    public String deleteAccessoAttiD(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	IstanzeAccessoAttiD objToDelete = istanzeaccessoattidService.findById(new PkId(codice));
	try {
	    istanzeaccessoattidService.delete(objToDelete);
	} catch (Exception e) {
	    log.error("deleteAccessoAttiD# e={}", e);
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:view.htm?codice=" + objToDelete.getIstanzeAccessoAttiT().getId().getCodice() + "&status_msg=05"; // REDIRECT
	}
	return "redirect:view.htm?codice=" + objToDelete.getIstanzeAccessoAttiT().getId().getCodice();
    }

    @RequestMapping
    public void ajaxAssegnaIstanzaAccessoAttiAnagrafe(@ModelAttribute("istanzeaccessoattit") IstanzeAccessoAttiTCommand command,
	    @RequestParam("codiceIstanzaAccessoAttiT") Integer codiceIstanzaAccessoAttiT, @RequestParam("codiceAnagrafe") Integer codiceAnagrafe,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "OK";
	try {
	    Anagrafe a = anagrafeService.findById(new PkId(codiceAnagrafe));
	    IstanzeAccessoAttiT t = istanzeaccessoattitService.findById(new PkId(codiceIstanzaAccessoAttiT));
	    List<IstanzeAccessoAttiAnagrafe> attiAnagrefes = istanzeacessoattianagrafeService.findByAnagrafeAndAccessoAttiT(codiceAnagrafe,
		    codiceIstanzaAccessoAttiT);
	    boolean trovato = false;
	    for (IstanzeAccessoAttiAnagrafe attianagrafe : attiAnagrefes) {
		if (attianagrafe.getAnagrafe() != null && attianagrafe.getAnagrafe().getId() != null
			&& attianagrafe.getAnagrafe().getId().getCodice() != null) {
		    if (codiceAnagrafe.equals(attianagrafe.getAnagrafe().getId().getCodice())) {
			trovato = true;
			break;
		    }
		}
	    }
	    if (trovato) {
		result = "Attenzione il soggetto " + a.getDescrizioneRichiedente() + " è già autorizzato a visualizzare questi atti.";
	    } else {
		IstanzeAccessoAttiAnagrafe entity = new IstanzeAccessoAttiAnagrafe();
		IstanzeAccessoAttiAnagrafeId id = new IstanzeAccessoAttiAnagrafeId();
		id.setCodiceanagrafe(a.getId().getCodice());
		id.setIstanzeAccessoAttiT(t.getId().getCodice());
		entity.setId(id);
		entity.setIstanzeAccessoAttiT(t);
		entity.setAnagrafe(a);
		istanzeacessoattianagrafeService.insert(entity);
	    }
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaIstanzaAccessoAttiAnagrafe(@RequestParam("codiceistanzaaccessoattit") Integer codiceistanzaaccessoattit,
	    @RequestParam("codiceanagrafe") Integer codiceanagrafe, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    IstanzeAccessoAttiAnagrafeId id = new IstanzeAccessoAttiAnagrafeId();
	    id.setCodiceanagrafe(codiceanagrafe);
	    id.setIstanzeAccessoAttiT(codiceistanzaaccessoattit);
	    IstanzeAccessoAttiAnagrafe entity = istanzeacessoattianagrafeService.findById(id);
	    istanzeacessoattianagrafeService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioIstanzaAccessoAttiAnagrafe(@RequestParam("codiceistanzaaccessoattit") Integer codiceistanzaaccessoattit,
	    @RequestParam(required = false, value = "codiceanagrafeInserito") Integer codiceanagrafe, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<IstanzeAccessoAttiAnagrafe> afs = istanzeacessoattianagrafeService.findByIstanzeAccessoAttiT(codiceistanzaaccessoattit, null, null);
	model.addAttribute("afs", afs);
	model.addAttribute("codiceanagrafeinserito", codiceanagrafe);
	return "istanzeaccessoattit/ajaxdettaglioistanzeaccessoattianagrafe";
    }

    @RequestMapping
    public void ajaxChangeFlagVisualizzaDoc(@RequestParam("codice") Integer codice, @RequestParam("flgVisualizzaDoc") Integer flgVisualizzaDoc,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	IstanzeAccessoAttiD istanzeAccessoAttiD = istanzeaccessoattidService.findById(new PkId(codice));
	istanzeAccessoAttiD.setFlgVisualizzaDoc(flgVisualizzaDoc);
	istanzeaccessoattidService.update(istanzeAccessoAttiD);
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    @RequestMapping
    public ModelMap listLogs(@RequestParam("codice") Integer codiceIstanzaAccessoAttiT, HttpServletRequest request, HttpServletResponse response) {

	IstanzeAccessoAttiT accessoAttiT = istanzeaccessoattitService.findById(new PkId(codiceIstanzaAccessoAttiT));
	List<IstanzeAccessoAttiLog> accessoAttiLogs = istanzeAccessoAttiLogService.findByIstanzeAccessoAttiT(codiceIstanzaAccessoAttiT, null, null);
	ModelMap model = new ModelMap(accessoAttiLogs);
	boolean export = createJMesaExport(request, response, accessoAttiLogs);
	if (export) {
	    return null;
	}
	model.addAttribute("istanzeaccessoattit", accessoAttiT);
	model.addAttribute("accessoAttiLogs", accessoAttiLogs);
	return model;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(IstanzeAccessoAttiTCommand command) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(IstanzeAccessoAttiTCommand command) {

	// TODO Auto-generated method stub
	IstanzeAccessoAttiT entity = command.getEntity();
	if (EntityUtils.getNestedProperty(entity, "responsabili") == null) {
	    entity.setResponsabili(new Responsabili());
	}
	if (EntityUtils.getNestedProperty(entity, "istanze") == null) {
	    entity.setIstanze(new Istanze());
	}
    }

    /**
     * Tutto ciò che serve a visualizzare la pagina di create nuova istanza accesso atti t. Viene usato anche dopo il
     * verificarsi degli errori in update/delete.
     * 
     * @param model
     * @param request
     * @param command
     * @param istanzeaccessoattit
     */
    private void prepareViewModel(Model model, HttpServletRequest request, IstanzeAccessoAttiTCommand command,
	    IstanzeAccessoAttiT istanzeaccessoattit) {

	//recupero la lista delle istanze accesso atti d
	List<IstanzeAccessoAttiD> istanzeaccessoattids = istanzeaccessoattidService
		.findByIstanzeAccessoAttiT(command.getEntity().getId().getCodice());
	/*Set<IstanzeAccessoAttiD> istanzeaccessoattidset = new HashSet<IstanzeAccessoAttiD>(istanzeaccessoattids);
	if (!istanzeaccessoattidset.isEmpty()) {
	    model.addAttribute("istanzeaccessoattids", istanzeaccessoattidset);
	}*/
	if (!istanzeaccessoattids.isEmpty()) {
	    model.addAttribute("istanzeaccessoattids", istanzeaccessoattids);
	}
    }

    private void setPageAttribute(Model model) {

    }
}
