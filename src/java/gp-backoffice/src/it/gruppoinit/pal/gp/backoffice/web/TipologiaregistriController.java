package it.gruppoinit.pal.gp.backoffice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom.ElencoImplementazioniCustom;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;

@Controller
@SessionAttributes(value = { "tipologiaregistri", "protocolloregistri" })
public class TipologiaregistriController extends BaseController<Tipologiaregistri> {

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
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ProtocolloRegistriService protocolloRegistriService;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Tipologiaregistri> tipologiaregistriList = tipologiaregistriService.findAll(null, null);
	ModelMap model = new ModelMap(tipologiaregistriList);
	//	boolean export = createJMesaExport(request, response, tipologiaregistriList);
	//	if (export)
	//	    return null;
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
    public String insert(Model model, @ModelAttribute("tipologiaregistri") Tipologiaregistri tipologiaregistri, BindingResult result,
	    SessionStatus status) {

	// recupero il software corrente
	Software software = softwareService.findById(ORMHelper.getSoftware());
	tipologiaregistri.setSoftware(software);
	fixMergeEntityProperty(tipologiaregistri);
	try {
	    tipologiaregistriService.insert(tipologiaregistri);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaregistri, e);
	    fixRenderEntityProperty(tipologiaregistri);
	    setPageAttributes(model);
	    return "tipologiaregistri/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + tipologiaregistri.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("tipologiaregistri") Tipologiaregistri tipologiaregistri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	String codicecomune = (String) request.getParameter("comune.codicecomune");
	Comuni comune = null;
	if (StringUtils.isNotBlank(codicecomune)) {
	    comune = comuniService.findById(codicecomune);
	}
	tipologiaregistri.setComune(comune);
	fixMergeEntityProperty(tipologiaregistri);
	try {
	    tipologiaregistriService.update(tipologiaregistri);
	    tipologiaregistri = tipologiaregistriService.findById(tipologiaregistri.getId());
	    String message = "#MOD_TIPOLOGIA_REGISTRI#" +
		    tipologiaregistri.getId() +
		    ", Descrizione: " +
		    tipologiaregistri.getTrDescrizione() +
		    ", Progressivo: " +
		    tipologiaregistri.getTrProgressivo() +
		    ", numeroProroghe: " +
		    tipologiaregistri.getNumeroProroghe() +
		    ", numeroRinnovi: " +
		    tipologiaregistri.getNumeroRinnovi() +
		    ", numeroPreavvisi: " +
		    tipologiaregistri.getNumeroPreavvisi() +
		    ", durataAutorizzazione: " +
		    tipologiaregistri.getDurataAutorizzazione() +
		    ", trFlagprotocollo: " +
		    tipologiaregistri.getTrFlagprotocollo() +
		    ", trFlagdataauto: " +
		    tipologiaregistri.getTrFlagdataauto() +
		    ", flagUsaProgrConf: " +
		    tipologiaregistri.getFlagUsaProgrConf();
	    LoggerUpdaterecord.log(message, (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, tipologiaregistri, e);
	    fixRenderEntityProperty(tipologiaregistri);
	    setPageAttributes(model);
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
	ElencoImplementazioniCustom elencoImplementazioni = new ElencoImplementazioniCustom();
	model.addAttribute("elencoImplementazioniCustom", elencoImplementazioni.get());
	setPageAttributes(model);
	return "tipologiaregistri/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(id);
	ElencoImplementazioniCustom elencoImplementazioni = new ElencoImplementazioniCustom();
	model.addAttribute("elencoImplementazioniCustom", elencoImplementazioni.get());
	fixRenderEntityProperty(tipologiaregistri);
	model.addAttribute("tipologiaregistri", tipologiaregistri);
	setPageAttributes(model, tipologiaregistri);
	fixRenderEntityProperty(tipologiaregistri);
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
	// Per questa funzionalità non è previsto il flusso di tipo anche se l'operatore ce l'ha attivo.
	List<String> escludiFlussi = new ArrayList<String>();
	escludiFlussi.add("A");
	List<ProtocolloFlusso> listaflussi = protocolloFlussoService.findByResponsabile(responsabili, escludiFlussi);
	// Richiamo dei ws per recuperare tipidocumento e classifiche
	CodiceDescrizioneBean[] listaClassifiche = protocollazioneService.getListaClassifiche(ORMHelper.getSoftware(), codiceComune);
	CodiceDescrizioneBean[] listaDocumento = protocollazioneService.getListaTipiDocumento(ORMHelper.getSoftware(), codiceComune);
	setPageAttributes(model);
	fixRenderProtocolloregistriProperty(protocolloRegistri);
	model.addAttribute("protocolloregistri", protocolloRegistri);
	model.addAttribute("listaflussi", listaflussi);
	model.addAttribute("listaClassifiche", listaClassifiche);
	model.addAttribute("listaDocumento", listaDocumento);
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
	if (protocolloRegistri.getTipimovimento() != null && protocolloRegistri.getTipimovimento().getId() != null
		&& StringUtils.isNotBlank(protocolloRegistri.getTipimovimento().getId().getTipomovimento())) {
	    TipimovimentoId idMovimento = new TipimovimentoId();
	    idMovimento.setIdcomune(ORMHelper.getIdcomune());
	    idMovimento.setTipomovimento(protocolloRegistri.getTipimovimento().getId().getTipomovimento());
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(idMovimento);
	    protocolloRegistri.setTipimovimento(tipimovimento);
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
	    CodiceDescrizioneBean[] listaClassifiche = protocollazioneService.getListaClassifiche(ORMHelper.getSoftware(), codiceComune);
	    CodiceDescrizioneBean[] listaDocumento = protocollazioneService.getListaTipiDocumento(ORMHelper.getSoftware(), codiceComune);
	    setPageAttributes(model);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("protocolloregistri", protocolloRegistri);
	    model.addAttribute("listaflussi", listaflussi);
	    model.addAttribute("listaClassifiche", listaClassifiche);
	    model.addAttribute("listaDocumento", listaDocumento);
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
	if (protocolloRegistri.getTipimovimento() != null && protocolloRegistri.getTipimovimento().getId() != null
		&& StringUtils.isNotBlank(protocolloRegistri.getTipimovimento().getId().getTipomovimento())) {
	    TipimovimentoId id = new TipimovimentoId();
	    id.setIdcomune(ORMHelper.getIdcomune());
	    id.setTipomovimento(protocolloRegistri.getTipimovimento().getId().getTipomovimento());
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(id);
	    protocolloRegistri.setTipimovimento(tipimovimento);
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
	    CodiceDescrizioneBean[] listaClassifiche = protocollazioneService.getListaClassifiche(ORMHelper.getSoftware(), codiceComune);
	    CodiceDescrizioneBean[] listaDocumento = protocollazioneService.getListaTipiDocumento(ORMHelper.getSoftware(), codiceComune);
	    setPageAttributes(model);
	    fixRenderProtocolloregistriProperty(protocolloRegistri);
	    model.addAttribute("protocolloregistri", protocolloRegistri);
	    model.addAttribute("listaflussi", listaflussi);
	    model.addAttribute("listaClassifiche", listaClassifiche);
	    model.addAttribute("listaDocumento", listaDocumento);
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
	if (entity.getSoftware() != null && (entity.getSoftware().getCodice() == null || entity.getSoftware().getCodice().equals(" "))) {
	    entity.setSoftware(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Tipologiaregistri entity) {

	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
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
	if (entity.getTipimovimento() == null) {
	    entity.setTipimovimento(new Tipimovimento());
	}
	if (entity.getProtocolloFlusso() == null) {
	    entity.setProtocolloFlusso(new ProtocolloFlusso());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    private void setPageAttributes(Model model, Tipologiaregistri entity) {

	if (entity.getComune() != null
		&& (entity.getComune().getCodicecomune() == null || StringUtils.isBlank(entity.getComune().getCodicecomune()))) {
	    entity.setComune(null);
	}
	setPageAttributes(model);
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
	boolean isVerticalizzazioneAUTORIZACCESSIAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_AUTORIZ_ACCESSI);
	model.addAttribute("isVerticalizzazioneAUTORIZACCESSIAttiva", isVerticalizzazioneAUTORIZACCESSIAttiva);
    }
}
