package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

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

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.commissioni.ICommissioniService;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ListaParametriprotocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioneCommissioneDetail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniCommissioniCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IConversioneComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ProtocollaParametriCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("comunicazioniCommissioniCommand")
public class ComunicazioniCommissioniController extends ComunicazioniBaseController<ComunicazioniCommissioniCommand> {

    @Autowired
    private IComunicazioniCommissioniService comunicazioniCommissioniService;
    @Autowired
    private IConversioneComunicazioniCommissioniService conversioneComunicazioniCommissioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private ICommissioniService commissioniService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private LetteretipoService letteretipoService;

    @RequestMapping
    public ModelMap list(@RequestParam("idCommissione") Integer idCommissione, HttpServletRequest request, HttpServletResponse response) {

	List<ListaComunicazioniResoconti> comunicazioniResoconti = comunicazioniCommissioniService.creaListaTestata(idCommissione);
	CommissioneModel testata = commissioniService.getCommissione(idCommissione);
	ModelMap model = new ModelMap(comunicazioniResoconti);
	model.addAttribute("testata", testata);
	model.addAttribute("comunicazioniResoconti", comunicazioniResoconti);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("idCommissione") Integer idCommissione, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	ComunicazioniCommissioniCommand cmd = new ComunicazioniCommissioniCommand();
	CommissioneModel testata = commissioniService.getCommissione(idCommissione);
	cmd.setCommissione(testata);
	cmd.getProtocollaParametriCommand().setParametriPerEnte(conversioneComunicazioniCommissioniService.popolaParametriPerProtocolloCommand(cmd));
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("comunicazioniCommissioniCommand", cmd);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	setParametriInModel(model, request, null, null);
	return "comunicazionicommissioni/create";
    }

    private void popolaModel(Model model, ComunicazioniCommissioniCommand cmd) {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
	for (IParametriProtocolloPerEnteHelper param : cmd.getProtocollaParametriCommand().getParametriPerEnte()) {
	    if (param.getAmmMittente() != null && param.getAmmMittente().getId() != null) {
		Amministrazioni ammMitente = amministrazioniService.findById(new PkId(param.getAmmMittente().getId()));
		param.setAmmMittente(new IdentificativoDescrizioneBean(ammMitente.getId().getCodice(), ammMitente.getAmministrazione()));
	    }
	}
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codiceTestata, Model model) {

	ComunicazioneCommissioneDetail command = this.conversioneComunicazioniCommissioniService.popolaCommandDettaglioByIdTestata(codiceTestata);
	model.addAttribute("comunicazioneCommissioneDetail", command);
	return "comunicazionicommissioni/form";
    }

    @RequestMapping
    public String insert(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    ConfigurazioneComunicazioniCommissioni c = conversioneComunicazioniCommissioniService
		    .popolaConfigurazioneComunicazioniCommissioni(comunicazioniCommissioniCommand);
	    int idTestata = comunicazioniCommissioniService.creaNuovaComunicazione(c);
	    // di modalità inserimento multiplo)
	    return "redirect:../comunicazionicommissioni/view.htm?codice=" + idTestata;
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazioniCommissioniCommand, e);
	    popolaModel(model, comunicazioniCommissioniCommand);
	    return "comunicazionicommissioni/create";
	}
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetParametriProtocollazione(
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	List<IParametriProtocolloPerEnteHelper> parametriprotocolloPerEnteHelpers = conversioneComunicazioniCommissioniService
		.popolaParametriPerProtocolloCommand(comunicazioniCommissioniCommand);
	String result = parametriProtocolloPerEnteHelperToJson(parametriprotocolloPerEnteHelpers);
	if (StringUtils.isNotEmpty(result)) {
	    response.setContentType("application/json");
	    response.getOutputStream().write(result.getBytes("utf-8"));
	} else {
	    response.setContentType("text/plain");
	    response.getOutputStream().write("nessun risultato".getBytes("utf-8"));
	}
    }

    @RequestMapping
    public void ajaxSetParametriProtocollazione(
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	ListaParametriprotocolloPerEnteHelper l = this.jsonToParametriProtocolloPerEnteHelper(request);
	ProtocollaParametriCommand prot = new ProtocollaParametriCommand();
	prot.setParametriPerEnte(new ArrayList<IParametriProtocolloPerEnteHelper>(l.getListParamprotoPerEnte()));
	comunicazioniCommissioniCommand.setProtocollaParametriCommand(prot);
	response.setContentType("text/plain");
	response.getOutputStream().write("OK".getBytes());
    }

    @RequestMapping
    public void ajaxAggiungiFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (verificaFirmatarioGiaAggiunto(codiceFirmatario, comunicazioniCommissioniCommand)) {
	    return;
	}
	Responsabili responsabili = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniCommissioniCommand.getFirmatari().add(responsabili);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    @RequestMapping
    public void ajaxAggiungiAllegatiCompilabili(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (verificaAllegatoCompilabileGiaAggiunto(codiceLetteretipo, comunicazioniCommissioniCommand)) {
	    return;
	}
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniCommissioniCommand.getAllegaticompilabili().add(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    public boolean verificaFirmatarioGiaAggiunto(Integer codiceFirmatario, ComunicazioniCommissioniCommand comunicazioniCommissioniCommand) {

	if (comunicazioniCommissioniCommand != null) {
	    for (Responsabili firmatario : comunicazioniCommissioniCommand.getFirmatari()) {
		if (firmatario.getId().getCodice().equals(codiceFirmatario)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean verificaAllegatoCompilabileGiaAggiunto(Integer codiceLetteretipo,
	    ComunicazioniCommissioniCommand comunicazioniCommissioniCommand) {

	if (comunicazioniCommissioniCommand != null) {
	    for (Letteretipo letteretipo : comunicazioniCommissioniCommand.getAllegaticompilabili()) {
		if (letteretipo.getId().getCodice().equals(codiceLetteretipo)) {
		    return true;
		}
	    }
	}
	return false;
    }

    @RequestMapping
    public void ajaxRimuoviFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili firmatario = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniCommissioniCommand.getFirmatari().remove(firmatario);
	model.addAttribute("comunicazioniCommissioniCommand", comunicazioniCommissioniCommand);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @RequestMapping
    public void ajaxRimuoviAllegatoCompilabile(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniCommissioniCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniCommissioniCommand.getAllegaticompilabili().remove(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxElabora(@RequestParam("idMassiveTestata") Integer codiceTestata, HttpServletResponse response) throws IOException {

	comunicazioniCommissioniService.elabora(codiceTestata);
	String retVal = "OK";
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxElaboraRiga(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	try {
	    this.comunicazioniCommissioniService.elaboraRiga(idRiga);
	} catch (Exception e) {
	    //
	}
	RigaComunicazioneDettagliata riga = this.comunicazioniCommissioniService.getRigaDettagliata(idRiga);
	String richiesta = Utilities.marshalJsonObject(riga, RigaComunicazioneDettagliata.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(richiesta.getBytes("utf-8"));
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	RigaComunicazioneDettagliata riga = this.comunicazioniCommissioniService.getRigaDettagliata(idRiga);
	String richiesta = Utilities.marshalJsonObject(riga, RigaComunicazioneDettagliata.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(richiesta.getBytes("utf-8"));
    }

    @RequestMapping
    public String eliminaMassiva(@RequestParam("idTestata") Integer idTestata, @RequestParam("idCommissione") Integer idCommissione, Model model) {

	String msg = "05";
	try {
	    comunicazioniCommissioniService.eliminaMassiva(idTestata, getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    msg = "03";
	    FlashMessages.getWarnings().add("Errore nella cancellazione della comunicazione massiva: " + e.getMessage());
	    e.printStackTrace();
	}
	return "redirect:list.htm?idCommissione=" + idCommissione + "&status_msg=" + msg;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(ComunicazioniCommissioniCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunicazioniCommissioniCommand entity) {

    }
}
