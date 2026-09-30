package it.gruppoinit.pal.gp.backoffice.web;

/**
 * @author gianpaolot
 */
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.MailtipoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

@Controller
@SessionAttributes(value = { "tipologiaregistri", "protocolloregistri" })
public class TipologiaregistriController extends BaseController<Tipologiaregistri> {

    private static final Logger log = LoggerFactory.getLogger(TipologiaregistriController.class);
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ProtocolloFlussoService protocolloFlussoService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ProtocolloRegistriService protocolloRegistriService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipologiaregistri> tipologiaregistriList = tipologiaregistriService.findAll(null, null);
	ModelMap model = new ModelMap(tipologiaregistriList);
	boolean export = createJMesaExport(request, response, tipologiaregistriList);
	if (export)
	    return null;
	model.addAttribute("tipologiaregistriList", tipologiaregistriList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("tipologiaregistri") Tipologiaregistri tipologiaregistri, BindingResult result,
	    SessionStatus status) {

	Tipologiaregistri objToDelete = tipologiaregistriService.findById(tipologiaregistri.getId());
	try {
	    tipologiaregistriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "tipologiaregistri/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(@ModelAttribute("tipologiaregistri") Tipologiaregistri tipologiaregistri, BindingResult result, SessionStatus status) {

	// recupero il software corrente
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipologiaregistri.setSoftware(software);
	fixMergeEntityProperty(tipologiaregistri);
	try {
	    tipologiaregistriService.insert(tipologiaregistri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaregistri, e);
	    fixRenderEntityProperty(tipologiaregistri);
	    return "tipologiaregistri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologiaregistri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("tipologiaregistri") Tipologiaregistri tipologiaregistri, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(tipologiaregistri);
	try {
	    tipologiaregistriService.update(tipologiaregistri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaregistri, e);
	    fixRenderEntityProperty(tipologiaregistri);
	    return "tipologiaregistri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologiaregistri.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	Tipologiaregistri tipologiaregistri = new Tipologiaregistri();
	fixRenderEntityProperty(tipologiaregistri);
	model.addAttribute("tipologiaregistri", tipologiaregistri);
	setPageAttributes(model);
	return "tipologiaregistri/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(id);
	fixRenderEntityProperty(tipologiaregistri);
	model.addAttribute("tipologiaregistri", tipologiaregistri);
	setPageAttributes(model);
	return "tipologiaregistri/form";
    }

    @RequestMapping
    public String listProtocolloRegistri(@RequestParam("codiceRegistro") Integer codiceRegistro, Model model, HttpServletRequest request) {

	Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(codiceRegistro));
	List<ProtocolloRegistri> configs = protocolloRegistriService.findByRegistro(codiceRegistro);
	model.addAttribute("registro", registro);
	model.addAttribute("configs", configs);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	Boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	model.addAttribute("isComuniAssociati", isComuniAssociati);
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	return "tipologiaregistri/listProtocolloRegistri";
    }

    @RequestMapping
    public String viewProtocolloRegistri(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// Protocollo registri ha lo stesso codice di tipologia registri, quindi controllo se già esiste un oggetto nel
	// DB
	// se si allora lo passo al model e vado in update,se no ,passo l'oggetto vuoto e vado in create.
	ProtocolloRegistri protocolloRegistri = protocolloRegistriService.findById(new PkId(codice));
	Integer codiceRegistro = protocolloRegistri.getTipologiaregistri().getId().getCodice();
	setPageAttributes(model); // PRIMA DELLA FIXRENDER
	fixRenderProtocolloregistriProperty(protocolloRegistri);
	model.addAttribute("protocolloregistri", protocolloRegistri);
	model.addAttribute("codiceRegistro", codiceRegistro);
	return "tipologiaregistri/formProtocolloregistri";
    }

    @RequestMapping
    public String createProtocolloRegistri(@RequestParam("codiceRegistro") Integer codiceRegistro, @RequestParam("codicecomune") String codiceComune,
	    Model model, HttpServletRequest request) {

	ProtocolloRegistri protocolloRegistri = new ProtocolloRegistri();
	Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(codiceRegistro));
	protocolloRegistri.setTipologiaregistri(registro);
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni c = comuniService.findById(codiceComune);
	    protocolloRegistri.setComune(c);
	}
	// Responsabile inserito come il responsabile loggato
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	// Per questa funzionalità non è previsto il flusso di tipo A nche se l'operatoro ce l'ha attivo.
	List<String> escludiFlussi = new ArrayList<String>();
	escludiFlussi.add("A");
	List<ProtocolloFlusso> listaflussi = protocolloFlussoService.findByResponsabile(responsabili, escludiFlussi);
	// Richiamo dei ws per recuperare tipidocumento e classifiche
	setPageAttributes(model);
	fixRenderProtocolloregistriProperty(protocolloRegistri);
	model.addAttribute("protocolloregistri", protocolloRegistri);
	model.addAttribute("listaflussi", listaflussi);
	model.addAttribute("codiceRegistro", codiceRegistro);
	return "tipologiaregistri/formProtocolloregistri";
    }

    @RequestMapping
    public String insertProtocolloRegistri(@RequestParam("codiceRegistro") Integer codiceRegistro, Model model,
	    @ModelAttribute("protocolloregistri") ProtocolloRegistri protocolloRegistri, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Le tabelle protocolloregistri e tipologiaregistri sono in relazione uno a uno quindi l'id dell'oggetto
	// protocolloregistri sede essere
	// settato uguale a quello di tipologiaregistri.
	if (!StringUtils.defaultIfEmpty(request.getParameter("mittente.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("mittente.id.codice"));
	    Amministrazioni mittente = amministrazioniService.findById(new PkId(codice));
	    protocolloRegistri.setMittente(mittente);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("destinatario.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("destinatario.id.codice"));
	    Amministrazioni destinatario = amministrazioniService.findById(new PkId(codice));
	    protocolloRegistri.setDestinatario(destinatario);
	}
	if (protocolloRegistri.getMailtipo() != null && protocolloRegistri.getMailtipo().getId().getCodice() != null) {
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(protocolloRegistri.getMailtipo().getId().getCodice()));
	    protocolloRegistri.setMailtipo(mailtipo);
	}
	try {
	    protocolloRegistriService.insert(protocolloRegistri);
	} catch (Exception e) {
	    setPageAttributes(model);
	    // Richiamo dei ws per recuperare tipidocumento e classifiche
	    copyErrorsToBindingResult(result, protocolloRegistri, e);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("codiceRegistro", codiceRegistro);
	    //////////////////////////////// 
	    Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	    List<String> escludiFlussi = new ArrayList<String>();
	    escludiFlussi.add("A");
	    List<ProtocolloFlusso> listaflussi = protocolloFlussoService.findByResponsabile(responsabili, escludiFlussi);
	    String codiceComune = null;
	    if (protocolloRegistri.getComune() != null) {
		if (StringUtils.isNotBlank(protocolloRegistri.getComune().getCodicecomune())) {
		    codiceComune = protocolloRegistri.getComune().getCodicecomune();
		}
	    }
	    setPageAttributes(model);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("protocolloregistri", protocolloRegistri);
	    model.addAttribute("listaflussi", listaflussi);
	    /////
	    return "tipologiaregistri/formProtocolloregistri";
	}
	status.setComplete();
	return "redirect:viewProtocolloRegistri.htm?codice=" + protocolloRegistri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String updateProtocolloRegistri(Model model, @ModelAttribute("protocolloregistri") ProtocolloRegistri protocolloRegistri,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	// deve controllare se il protocollo di flusso è di tipo partenza allora controlla e se è stato inserito
	// per evitare il null point
	// se non è di partenza allora non deve esiste il mittente,quini in fase di modifica può essere necessario
	// metetre null
	// un il mittente
	if (!StringUtils.defaultIfEmpty(request.getParameter("mittente.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("mittente.id.codice"));
	    Amministrazioni mittente = amministrazioniService.findById(new PkId(codice));
	    protocolloRegistri.setMittente(mittente);
	}
	// deve controllare se il protocollo di flusso è di tipo partenza allora controlla e se è stato inserito
	// per evitare il null point
	// se non è di partenza allora non deve esiste il destinatario,quini in fase di modifica può essere necessario
	// metetre null
	// un il destinatario
	if (protocolloRegistri.getProtocolloFlusso().getCodice().equals("I")) {
	    if (!StringUtils.defaultIfEmpty(request.getParameter("destinatario.id.codice"), "").equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("destinatario.id.codice"));
		Amministrazioni destinatario = amministrazioniService.findById(new PkId(codice));
		protocolloRegistri.setDestinatario(destinatario);
	    }
	} else {
	    protocolloRegistri.setDestinatario(null);
	}
	if (protocolloRegistri.getMailtipo() != null && protocolloRegistri.getMailtipo().getId().getCodice() != null) {
	    Mailtipo mailtipo = mailtipoService.findById(new PkId(protocolloRegistri.getMailtipo().getId().getCodice()));
	    protocolloRegistri.setMailtipo(mailtipo);
	}
	try {
	    protocolloRegistriService.update(protocolloRegistri);
	} catch (Exception e) {
	    setPageAttributes(model);
	    copyErrorsToBindingResult(result, protocolloRegistri, e);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("codiceregistro", protocolloRegistri.getId().getCodice());
	    ////////////////////////////////
	    Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	    List<String> escludiFlussi = new ArrayList<String>();
	    escludiFlussi.add("A");
	    List<ProtocolloFlusso> listaflussi = protocolloFlussoService.findByResponsabile(responsabili, escludiFlussi);
	    String codiceComune = null;
	    if (protocolloRegistri.getComune() != null) {
		if (StringUtils.isNotBlank(protocolloRegistri.getComune().getCodicecomune())) {
		    codiceComune = protocolloRegistri.getComune().getCodicecomune();
		}
	    }
	    setPageAttributes(model);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("protocolloregistri", protocolloRegistri);
	    model.addAttribute("listaflussi", listaflussi);
	    /////
	    return "tipologiaregistri/formProtocolloregistri";
	}
	status.setComplete();
	return "redirect:viewProtocolloRegistri.htm?codice=" + protocolloRegistri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String deleteProtocolloRegistri(Model model, @ModelAttribute("protocolloregistri") ProtocolloRegistri protocolloRegistri,
	    BindingResult result, SessionStatus status) {

	ProtocolloRegistri objToDelete = protocolloRegistriService.findById(protocolloRegistri.getId());
	try {
	    protocolloRegistriService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    return "tipologiaregistri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + protocolloRegistri.getId().getCodice();
    }

    @Override
    protected void fixMergeEntityProperty(Tipologiaregistri entity) {

	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipologiaregistri entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
    }

    protected void fixRenderProtocolloregistriProperty(ProtocolloRegistri entity) {

	if (entity.getMailtipo() == null) {
	    entity.setMailtipo(new Mailtipo());
	}
	if (entity.getMittente() == null) {
	    entity.setMittente(new Amministrazioni());
	}
	if (entity.getDestinatario() == null) {
	    entity.setDestinatario(new Amministrazioni());
	}
	if (entity.getProtocolloFlusso() == null) {
	    entity.setProtocolloFlusso(new ProtocolloFlusso());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabili);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	List<String> escludiFlussi = new ArrayList<String>();
	escludiFlussi.add("A");
	List<ProtocolloFlusso> listaflussi = protocolloFlussoService.findByResponsabile(responsabili, escludiFlussi);
	model.addAttribute("listaflussi", listaflussi);
	boolean verticalizzazioni_DOCER = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER);
	model.addAttribute("isDocEr", verticalizzazioni_DOCER);
    }
}
