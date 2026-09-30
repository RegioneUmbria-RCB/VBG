package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniruoli;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniruoliId;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Email;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloRegistri;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.AmministrProtocolloHelper;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniresponsabiliService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniruoliService;
import it.gruppoinit.pal.gp.core.service.EmailService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloRegistriService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@SessionAttributes(value = { "amministrazioni", "email", "amministrazionireferenti", "amministrazioniresposabili", "amministrProtocollo" })
public class AmministrazioniController extends BaseController<Amministrazioni> {

    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private AmministrazioniresponsabiliService amministrazioniresponsabiliService;
    @Autowired
    private RuoliService ruoliService;
    @Autowired
    private AmministrazioniruoliService amministrazioniruoliService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AmministrazionireferentiService amministrazionireferentiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private ProtocolloRegistriService protocolloRegistriService;
    @Autowired
    private AmministrProtocolloService amministrProtocolloService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Amministrazioni> amministrazioniList = amministrazioniService.findAll(null, null);
	ModelMap model = new ModelMap(amministrazioniList);
	boolean export = createJMesaExport(request, response, amministrazioniList);
	if (export)
	    return null;
	model.addAttribute("amministrazioniList", amministrazioniList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("amministrazioni") Amministrazioni amministrazioni, BindingResult result, SessionStatus status) {

	Amministrazioni objToDelete = amministrazioniService.findById(amministrazioni.getId());
	try {
	    amministrazioniService.delete(objToDelete);
	} catch (Exception e) {
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    copyErrorsToBindingResult(result, amministrazioni, e);
	    fixRenderEntityProperty(amministrazioni);
	    return "amministrazioni/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("amministrazioni") Amministrazioni amministrazioni, BindingResult result, SessionStatus status) {

	// i campi protRuolo e protUo sul database sono obligatori,se non vengono inseriti per default devono essere
	// messia stringa vuota
	//	if (amministrazioni.getProtUo() == null || amministrazioni.getProtUo().equals(""))
	//	    amministrazioni.setProtUo("");
	//	if (amministrazioni.getProtRuolo() == null || amministrazioni.getProtRuolo().equals(""))
	//	    amministrazioni.setProtRuolo("");
	fixMergeEntityProperty(amministrazioni);
	try {
	    amministrazioniService.insert(amministrazioni);
	} catch (Exception e) {
	    // controlla se è attiva la verticalizzazione stc
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    boolean isVerticalizzazioneProtocolloAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    model.addAttribute("isVerticalizzazioneProtocolloAttiva", isVerticalizzazioneProtocolloAttiva);
	    copyErrorsToBindingResult(result, amministrazioni, e);
	    fixRenderEntityProperty(amministrazioni);
	    return "amministrazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + amministrazioni.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("amministrazioni") Amministrazioni amministrazioni, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// i campi protRuolo e protUo sul database sono obligatori,se non vengono inseriti per default devono essere
	// messia stringa vuota
	//	if (amministrazioni.getProtUo() == null || amministrazioni.getProtUo().equals(""))
	//	    amministrazioni.setProtUo("");
	//	if (amministrazioni.getProtRuolo() == null || amministrazioni.getProtRuolo().equals(""))
	//	    amministrazioni.setProtRuolo("");
	fixMergeEntityProperty(amministrazioni);
	try {
	    amministrazioniService.update(amministrazioni);
	} catch (Exception e) {
	    // controlla se è attiva la verticalizzazione stc
	    boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    boolean isVerticalizzazioneProtocolloAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO);
	    model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	    model.addAttribute("isVerticalizzazioneProtocolloAttiva", isVerticalizzazioneProtocolloAttiva);
	    copyErrorsToBindingResult(result, amministrazioni, e);
	    fixRenderEntityProperty(amministrazioni);
	    return "amministrazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + amministrazioni.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String create(Model model) {

	// controlla se è attiva la verticalizzazione stc
	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	boolean isVerticalizzazioneProtocolloAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO);
	Amministrazioni amministrazioni = new Amministrazioni();
	//	if (isVerticalizzazioneSTCAttiva) {
	//	    Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
	//		    "NLA_IDNODO");
	//	    if (vparam != null) {
	//		amministrazioni.setStcIdnodo(vparam.getValore());
	//	    }
	//	}
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	fixRenderEntityProperty(amministrazioni);
	model.addAttribute("amministrazioni", amministrazioni);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	model.addAttribute("isVerticalizzazioneProtocolloAttiva", isVerticalizzazioneProtocolloAttiva);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	setPageAttributes(model);
	return "amministrazioni/form";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// controlla se è attiva la verticalizzazione stc
	PkId id = new PkId(codice);
	Amministrazioni amministrazioni = amministrazioniService.findById(id);
	fixRenderEntityProperty(amministrazioni);
	prepareViewPage(amministrazioni, codice, model, request);
	setPageAttributes(model);
	return "amministrazioni/form";
    }

    private void prepareViewPage(Amministrazioni amministrazioni, Integer codice, Model model, HttpServletRequest request) {

	boolean isVerticalizzazioneSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	boolean isVerticalizzazioneProtocolloAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO);
	boolean isVerticalizzazioneCARTAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_CART);
	model.addAttribute("amministrazioni", amministrazioni);
	model.addAttribute("isVerticalizzazioneSTCAttiva", isVerticalizzazioneSTCAttiva);
	model.addAttribute("isVerticalizzazioneProtocolloAttiva", isVerticalizzazioneProtocolloAttiva);
	model.addAttribute("isVerticalizzazioneCARTAttiva", isVerticalizzazioneCARTAttiva);
	model.addAttribute("codice", codice);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String insertResponsabile(@RequestParam("codiceResponsabile") Integer codiceresponsabile, Model model,
	    @ModelAttribute("amministrazioni") Amministrazioni amministrazioni, BindingResult result, SessionStatus status, HttpServletRequest request) {

	Amministrazioniresponsabili amministrazioniresponsabili = new Amministrazioniresponsabili();
	Responsabili responsabili = responsabiliService.findById(new PkId(codiceresponsabile));
	amministrazioniresponsabili.setAmministrazioni(amministrazioni);
	amministrazioniresponsabili.setResponsabili(responsabili);
	try {
	    amministrazioniresponsabiliService.insert(amministrazioniresponsabili);
	} catch (Exception e) {
	    Map map = new HashMap();
	    copyErrorsToBindingResult(result, amministrazioniresponsabili, false, e);
	    map.put("codice", amministrazioni.getId().getCodice());
	    model.addAttribute("commandName", "amministrazioni");
	    model.addAttribute("method", "listresponsabili.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	fixRenderEntityProperty(amministrazioni);
	setPageAttributes(model);
	return "redirect:listresponsabili.htm?codice=" + amministrazioni.getId().getCodice();
    }

    @RequestMapping
    public String deleteResponsabile(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Amministrazioniresponsabili amministrazioniresponsabili = amministrazioniresponsabiliService.findById(new PkId(codice));
	amministrazioniresponsabiliService.delete(amministrazioniresponsabili);
	setPageAttributes(model);
	return "redirect:listresponsabili.htm?codice=" + amministrazioniresponsabili.getAmministrazioni().getId().getCodice();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String insertRuolo(@RequestParam("codiceRuolo") Integer codiceruolo, @ModelAttribute("amministrazioni") Amministrazioni amministrazioni,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request) {

	Amministrazioniruoli amministrazioniruoli = new Amministrazioniruoli();
	AmministrazioniruoliId id = new AmministrazioniruoliId();
	id.setCodiceamministrazione(amministrazioni.getId().getCodice());
	id.setIdruolo(codiceruolo);
	id.setIdcomune(ORMHelper.getIdcomune());
	Ruoli ruoli = ruoliService.findById(new PkId(codiceruolo));
	amministrazioniruoli.setAmministrazioni(amministrazioni);
	amministrazioniruoli.setRuoli(ruoli);
	amministrazioniruoli.setId(id);
	try {
	    amministrazioniruoliService.insert(amministrazioniruoli);
	    fixRenderEntityProperty(amministrazioni);
	} catch (Exception e) {
	    Map map = new HashMap();
	    copyErrorsToBindingResult(result, amministrazioniruoli, false, e);
	    map.put("codice", amministrazioni.getId().getCodice());
	    model.addAttribute("commandName", "amministrazioni");
	    model.addAttribute("method", "listruoli.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	setPageAttributes(model);
	return "redirect:listruoli.htm?codice=" + amministrazioni.getId().getCodice();
    }

    @RequestMapping
    public String deleteRuolo(@RequestParam("codiceAmministrazione") Integer codiceAmministrazione, @RequestParam("codiceRuolo") Integer codiceRuolo,
	    Model model, HttpServletRequest request) {

	AmministrazioniruoliId id = new AmministrazioniruoliId();
	id.setCodiceamministrazione(codiceAmministrazione);
	id.setIdruolo(codiceRuolo);
	id.setIdcomune(ORMHelper.getIdcomune());
	Amministrazioniruoli amministrazioniruoli = amministrazioniruoliService.findById(id);
	amministrazioniruoliService.delete(amministrazioniruoli);
	setPageAttributes(model);
	return "redirect:listruoli.htm?codice=" + amministrazioniruoli.getAmministrazioni().getId().getCodice();
    }

    @RequestMapping
    public ModelMap listemail(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
	List<Email> emailList = emailService.findAllOrderByData(amministrazioni, DAOOrderTypeEnum.DESC);
	ModelMap model = new ModelMap(emailList);
	boolean export = createJMesaExport(request, response, emailList);
	if (export)
	    return null;
	model.addAttribute("emailList", emailList);
	model.addAttribute("codice", codice);
	model.addAttribute("amministrazioni", amministrazioni);
	return model;
    }

    @RequestMapping
    public String viewEmailDetail(@RequestParam("codice") int codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Email email = emailService.findById(id);
	model.addAttribute("email", email);
	setPageAttributes(model);
	return "amministrazioni/emaildetail";
    }

    @RequestMapping
    public ModelMap listamministrazionireferenti(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
	Set<Amministrazionireferenti> amministrazionireferentiList = amministrazioni.getAmministrazionireferentis();
	ModelMap model = new ModelMap(amministrazionireferentiList);
	boolean export = createJMesaExport(request, response, amministrazionireferentiList);
	if (export)
	    return null;
	model.addAttribute("amministrazionireferentiList", amministrazionireferentiList);
	model.addAttribute("codice", codice);
	model.addAttribute("amministrazioni", amministrazioni);
	return model;
    }

    @RequestMapping
    public String createAmministrazionireferenti(Model model, @RequestParam("codiceamministrazione") Integer codiceamministrazione) {

	Amministrazionireferenti amministrazionireferenti = new Amministrazionireferenti();
	PkId id = new PkId();
	id.setCodice(codiceamministrazione);
	id.setIdcomune(ORMHelper.getIdcomune());
	Amministrazioni amministrazioni = amministrazioniService.findById(id);
	amministrazionireferenti.setAmministrazioni(amministrazioni);
	model.addAttribute("amministrazionireferenti", amministrazionireferenti);
	setPageAttributes(model);
	return "amministrazioni/formAmministrazionireferenti";
    }

    @RequestMapping
    public String insertAmministrazionireferenti(Model model,
	    @ModelAttribute("amministrazionireferenti") Amministrazionireferenti amministrazionireferenti, BindingResult result, SessionStatus status) {

	fixMergeAmministrazionireferentiProperty(amministrazionireferenti);
	try {
	    amministrazionireferentiService.insert(amministrazionireferenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, amministrazionireferenti, e);
	    fixMergeAmministrazionireferentiProperty(amministrazionireferenti);
	    return "amministrazioni/formAmministrazionireferenti";
	}
	status.setComplete();
	return "redirect:viewAmministrazionireferenti.htm?codice=" + amministrazionireferenti.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewAmministrazionireferenti(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Amministrazionireferenti amministrazionireferenti = amministrazionireferentiService.findById(id);
	fixRenderAmministrazionireferentiProperty(amministrazionireferenti);
	model.addAttribute("amministrazionireferenti", amministrazionireferenti);
	setPageAttributes(model);
	return "amministrazioni/formAmministrazionireferenti";
    }

    @RequestMapping
    public String updateAmministrazionireferenti(Model model,
	    @ModelAttribute("amministrazionireferenti") Amministrazionireferenti amministrazionireferenti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeAmministrazionireferentiProperty(amministrazionireferenti);
	try {
	    amministrazionireferentiService.update(amministrazionireferenti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, amministrazionireferenti, e);
	    fixMergeAmministrazionireferentiProperty(amministrazionireferenti);
	    return "amministrazioni/formAmministrazionireferenti";
	}
	status.setComplete();
	return "redirect:viewAmministrazionireferenti.htm?codice=" + amministrazionireferenti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteAmministrazionireferenti(Model model,
	    @ModelAttribute("amministrazionireferenti") Amministrazionireferenti amministrazionireferenti, BindingResult result, SessionStatus status) {

	Amministrazionireferenti objToDelete = amministrazionireferentiService.findById(amministrazionireferenti.getId());
	try {
	    amministrazionireferentiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, amministrazionireferenti, e);
	    fixRenderAmministrazionireferentiProperty(amministrazionireferenti);
	    return "amministrazioni/formAmministrazionireferenti";
	}
	status.setComplete();
	return "redirect:listamministrazionireferenti.htm?codice=" + amministrazionireferenti.getAmministrazioni().getId().getCodice();
    }

//    @RequestMapping
//    public ModelMap listruoli(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {
//
//	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
//	Set<Amministrazioniruoli> listaruoli = amministrazioni.getAmministrazioniruolis();
//	ModelMap model = new ModelMap(listaruoli);
//	boolean export = createJMesaExport(request, response, listaruoli);
//	if (export)
//	    return null;
//	model.addAttribute("amministrazioni", amministrazioni);
//	model.addAttribute("listaruoli", listaruoli);
//	model.addAttribute("codice", codice);
//	return model;
//    }

    //    @RequestMapping
    //    public ModelMap listresponsabili(@RequestParam("codice") Integer codice, HttpServletRequest request, HttpServletResponse response) {
    //
    //	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
    //	Set<Amministrazioniresponsabili> amministrazioniresponsabiliList = amministrazioni.getAmministrazioniresponsabilis();
    //	ModelMap model = new ModelMap(amministrazioniresponsabiliList);
    //	boolean export = createJMesaExport(request, response, amministrazioniresponsabiliList);
    //	if (export)
    //	    return null;
    //	model.addAttribute("amministrazioniresponsabiliList", amministrazioniresponsabiliList);
    //	model.addAttribute("codice", codice);
    //	model.addAttribute("amministrazioni", amministrazioni);
    //	return model;
    //    }
    @Override
    protected void fixMergeEntityProperty(Amministrazioni entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Amministrazioni entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    protected void fixMergeAmministrazionireferentiProperty(Amministrazionireferenti amministrazionireferenti) {

	if (amministrazionireferenti.getAmministrazioni() != null && amministrazionireferenti.getAmministrazioni().getId() != null
		&& amministrazionireferenti.getAmministrazioni().getId().getCodice() == null) {
	    amministrazionireferenti.setAmministrazioni(null);
	}
    }

    protected void fixRenderAmministrazionireferentiProperty(Amministrazionireferenti amministrazionireferenti) {

	if (amministrazionireferenti.getAmministrazioni() == null) {
	    amministrazionireferenti.setAmministrazioni(new Amministrazioni());
	}
    }

    @RequestMapping
    public String abilitaDisabilita(Model model, @ModelAttribute("amministrazioni") Amministrazioni amministrazioni, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	amministrazioni = amministrazioniService.findById(amministrazioni.getId());
	boolean disabilita = false;
	if (amministrazioni.getFlagDisabilitato() == null || amministrazioni.getFlagDisabilitato().booleanValue() == false) {
	    disabilita = true;
	    boolean check = amministrazioniService.checkSeDisabilitare(amministrazioni);
	    if (!check) {
		return "redirect:listaDipendenze.htm?codice=" + amministrazioni.getId().getCodice();
	    }
	}
	amministrazioni.setFlagDisabilitato(disabilita);
	try {
	    amministrazioniService.update(amministrazioni);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, amministrazioni, e);
	    fixRenderEntityProperty(amministrazioni);
	    amministrazioni = amministrazioniService.findById(amministrazioni.getId());
	    prepareViewPage(amministrazioni, amministrazioni.getId().getCodice(), model, request);
	    setPageAttributes(model);
	    return "amministrazioni/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + amministrazioni.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String listaDipendenze(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Amministrazioni amministrazioni = amministrazioniService.findById(id);
	fixRenderEntityProperty(amministrazioni);
	model.addAttribute("amministrazioni", amministrazioni);
	//
	List<Inventarioprocedimenti> inventarioprocedimentis = inventarioprocedimentiService.findByAmministrazioni(amministrazioni.getId()
		.getCodice(), 0, 10);
	List<ProtocolloRegistri> protocolloregistrimitts = protocolloRegistriService.findByAmministrazioniMittente(amministrazioni.getId()
		.getCodice(), 0, 10);
	List<ProtocolloRegistri> protocolloregistridests = protocolloRegistriService.findByAmministrazioniDestinatario(amministrazioni.getId()
		.getCodice(), 0, 10);
	model.addAttribute("inventarioprocedimentis", inventarioprocedimentis);
	model.addAttribute("protocolloregistrimitts", protocolloregistrimitts);
	model.addAttribute("protocolloregistridests", protocolloregistridests);
	setPageAttributes(model);
	return "amministrazioni/listaDipendenze";
    }

    @RequestMapping
    public String listparametriprotocollo(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	List<AmministrProtocolloHelper> amministrProtocolloHelpers = amministrProtocolloService.findByComuniAndSoftwarePerOperatore(codice,
		responsabile);
	model.addAttribute("amministrProtocolloHelpers", amministrProtocolloHelpers);
	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
	model.addAttribute("amministrazioni", amministrazioni);
	return "amministrazioni/listparametriprotocolloAmministrazione";
    }

    @RequestMapping
    public String createParametriProtocollo(@RequestParam("codice") Integer codice, Model model) {

	AmministrProtocollo amministrProtocollo = new AmministrProtocollo();
	Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(codice));
	amministrProtocollo.setAmministrazioni(amministrazioni);
	fixRenderAmministrProtocolloProperty(amministrProtocollo);
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	model.addAttribute("amministrProtocollo", amministrProtocollo);
	model.addAttribute("responsabilicomunis", responsabilicomunis);
	model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	setPageAttributes(model);
	return "amministrazioni/formParametriprotocollo";
    }

    @RequestMapping
    public String insertParametriProtocollo(Model model, @ModelAttribute("amministrProtocollo") AmministrProtocollo amministrProtocollo,
	    BindingResult result, SessionStatus status) {

	fixMergeAmministrProtocolloProperty(amministrProtocollo);
	try {
	    amministrProtocolloService.insert(amministrProtocollo);
	} catch (Exception e) {
	    Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	    Set<Responsabilicomuni> responsabilicomunis = responsabiliService.findListResponsabilicomuni(responsabile);
	    Set<Responsabilisoftware> responsabilisoftwares = responsabiliService.findListResponsabilisoftware(responsabile);
	    model.addAttribute("amministrProtocollo", amministrProtocollo);
	    model.addAttribute("responsabilicomunis", responsabilicomunis);
	    model.addAttribute("responsabilisoftwares", responsabilisoftwares);
	    copyErrorsToBindingResult(result, amministrProtocollo, e);
	    fixRenderAmministrProtocolloProperty(amministrProtocollo);
	    return "amministrazioni/formParametriprotocollo";
	}
	status.setComplete();
	return "redirect:listparametriprotocollo.htm?codice=" + amministrProtocollo.getAmministrazioni().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String viewParametriProtocollo(@RequestParam("codice") Integer codice, Model model) {

	AmministrProtocollo amministrProtocollo = amministrProtocolloService.findById(new PkId(codice));
	fixRenderAmministrProtocolloProperty(amministrProtocollo);
	model.addAttribute("amministrProtocollo", amministrProtocollo);
	setPageAttributes(model);
	return "amministrazioni/formParametriprotocollo";
    }

    @RequestMapping
    public String deleteParametriProtocollo(Model model, @ModelAttribute("amministrProtocollo") AmministrProtocollo amministrProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	AmministrProtocollo objToDelete = amministrProtocolloService.findById(new PkId(amministrProtocollo.getId().getCodice()));
	Integer codiceAmministrazione = objToDelete.getAmministrazioni().getId().getCodice();
	Integer codice = objToDelete.getId().getCodice();
	try {
	    amministrProtocolloService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(objToDelete, false, "amministrProtocollo", e);
	    status.setComplete();
	    return "redirect:viewParametriProtocollo.htm?codice=" + codice;
	}
	status.setComplete();
	return "redirect:listparametriprotocollo.htm?codice=" + codiceAmministrazione + "&status_msg=01";
    }

    @RequestMapping
    public String updateParametriProtocollo(Model model, @ModelAttribute("amministrProtocollo") AmministrProtocollo amministrProtocollo,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	//fixMergeEntityProperty(amministrazioni);
	try {
	    amministrProtocolloService.update(amministrProtocollo);
	} catch (Exception e) {
	    model.addAttribute("amministrProtocollo", amministrProtocollo);
	    copyErrorsToBindingResult(result, amministrProtocollo, e);
	    fixRenderAmministrProtocolloProperty(amministrProtocollo);
	    return "amministrazioni/formParametriprotocollo";
	}
	status.setComplete();
	return "redirect:listparametriprotocollo.htm?codice=" + amministrProtocollo.getAmministrazioni().getId().getCodice() + "&status_msg=01";
    }

    protected void fixMergeAmministrProtocolloProperty(AmministrProtocollo amministrProtocollo) {

    }

    protected void fixRenderAmministrProtocolloProperty(AmministrProtocollo amministrProtocollo) {

	if (amministrProtocollo.getAmministrazioni() == null) {
	    amministrProtocollo.setAmministrazioni(new Amministrazioni());
	}
	if (amministrProtocollo.getSoftware() == null) {
	    amministrProtocollo.setSoftware(new Software());
	}
	if (amministrProtocollo.getComuni() == null) {
	    amministrProtocollo.setComuni(new Comuni());
	}
    }
}
