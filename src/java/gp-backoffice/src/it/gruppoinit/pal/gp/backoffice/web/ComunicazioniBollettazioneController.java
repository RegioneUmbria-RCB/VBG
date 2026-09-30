package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.LetteraGenerataPerComunicazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.ConfigurazioneComunicazioniBollettazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniToBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ListaParametriprotocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioneBollettazioneDetail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniBollettazioneCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IConversioneComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ProtocollaParametriCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.BollGestTestataService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes("comunicazioniBollettazioneCommand")
public class ComunicazioniBollettazioneController extends ComunicazioniBaseController<ComunicazioniBollettazioneCommand> {

    @Autowired
    private IComunicazioniBollettazioneService comunicazioniBollettazioneService;
    @Autowired
    private IConversioneComunicazioniBollettazioneService conversioneComunicazioniBollettazioneService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private BollGestTestataService bollGestTestataService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private LetteretipoService letteretipoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    IComunicazioniToBollettazioneService comunicazioniToBollettazioneService;
    @Autowired
    private ContenttypesService contenttypesService;
    private BollCfgTipoMetadatiService bollCfgTipoMetadatiService;

    @Autowired
    public void setBollCfgTipoMetadatiService(BollCfgTipoMetadatiService bollCfgTipoMetadatiService) {

	this.bollCfgTipoMetadatiService = bollCfgTipoMetadatiService;
    }

    @RequestMapping
    public ModelMap list(@RequestParam("idBollettazione") Integer idBollettazione, HttpServletRequest request, HttpServletResponse response) {

	List<ListaComunicazioniResoconti> comunicazioniResoconti = comunicazioniBollettazioneService.creaListaTestata(idBollettazione);
	BollGestTestata testata = bollGestTestataService.findById(new PkId(idBollettazione));
	ModelMap model = new ModelMap(comunicazioniResoconti);
	model.addAttribute("testata", testata);
	model.addAttribute("comunicazioniResoconti", comunicazioniResoconti);
	return model;
    }

    @RequestMapping
    public String create(@RequestParam("idBollettazione") Integer idBollettazione, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	ComunicazioniBollettazioneCommand cmd = new ComunicazioniBollettazioneCommand();
	BollGestTestata testata = bollGestTestataService.findById(new PkId(idBollettazione));
	cmd.setGestTestata(testata);
	cmd.getProtocollaParametriCommand()
		.setParametriPerEnte(conversioneComunicazioniBollettazioneService.popolaParametriPerProtocolloCommand(cmd));
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
	model.addAttribute("comunicazioniBollettazioneCommand", cmd);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	setParametriInModel(model, request, null, null);
	return "comunicazionibollettazione/create";
    }

    private void popolaModel(Model model, ComunicazioniBollettazioneCommand cmd) {

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

	ComunicazioneBollettazioneDetail command = this.conversioneComunicazioniBollettazioneService.popolaCommandDettaglioByIdTestata(codiceTestata);
	model.addAttribute("comunicazioneBollettazioneDetail", command);
	return "comunicazionibollettazione/form";
    }

    @RequestMapping
    public String insert(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    ConfigurazioneComunicazioniBollettazione c = conversioneComunicazioniBollettazioneService
		    .popolaConfigurazioneComunicazioniBollettazione(comunicazioniBollettazioneCommand);
	    int idTestata = comunicazioniBollettazioneService.creaNuovaComunicazione(c);
	    // di modalità inserimento multiplo)
	    return "redirect:../comunicazionibollettazione/view.htm?codice=" + idTestata;
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, comunicazioniBollettazioneCommand, e);
	    popolaModel(model, comunicazioniBollettazioneCommand);
	    return "comunicazionibollettazione/create";
	}
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetParametriProtocollazione(
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	List<IParametriProtocolloPerEnteHelper> parametriprotocolloPerEnteHelpers = conversioneComunicazioniBollettazioneService
		.popolaParametriPerProtocolloCommand(comunicazioniBollettazioneCommand);
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
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	ListaParametriprotocolloPerEnteHelper l = this.jsonToParametriProtocolloPerEnteHelper(request);
	ProtocollaParametriCommand prot = new ProtocollaParametriCommand();
	prot.setParametriPerEnte(new ArrayList<IParametriProtocolloPerEnteHelper>(l.getListParamprotoPerEnte()));
	comunicazioniBollettazioneCommand.setProtocollaParametriCommand(prot);
	response.setContentType("text/plain");
	response.getOutputStream().write("OK".getBytes());
    }

    @RequestMapping
    public void ajaxAggiungiFirmatario(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (verificaFirmatarioGiaAggiunto(codiceFirmatario, comunicazioniBollettazioneCommand)) {
	    return;
	}
	Responsabili responsabili = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniBollettazioneCommand.getFirmatari().add(responsabili);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    @RequestMapping
    public void ajaxAggiungiAllegatiCompilabili(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (verificaAllegatoCompilabileGiaAggiunto(codiceLetteretipo, comunicazioniBollettazioneCommand)) {
	    return;
	}
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniBollettazioneCommand.getAllegaticompilabili().add(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
	return;
    }

    @RequestMapping
    public void popolaPopupLettere(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	if (codiceLetteretipo == null) {
	    throw new RuntimeException("Non è stato impostato correttamente il codice lettera!");
	}
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	Integer codiceOggetto = letteretipo.getFile().getId().getCodice();
	boolean oggettoBloccato = false;
	String downloadFileLink = "";
	if (codiceOggetto != null) {
	    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(codiceOggetto));
	    downloadFileLink = getDownloadLink(oggetto);
	    oggettoBloccato = addToModelMetadatiFile(codiceOggetto, model, userlogged, oggetto);
	}
	String nomefile = letteretipo.getFile().getNomefile();
	StringBuffer buffer = new StringBuffer();
	buffer.append("<div class=\"form-group\">").append("<label>Descrizione</label>");
	buffer.append("<input id=\"descrizione_lettera\" type=\"text\" size=\"50\" value=\"").append(letteretipo.getDescrizione()).append("\">");
	buffer.append("</div>");
	buffer.append("<div class=\"form-group\">").append("<label>File</label>");
	buffer.append("<input id=\"nome_file\" type=\"text\"  size=\"80\" value=\"").append(nomefile).append("\">");
	if (!oggettoBloccato) {
	    buffer.append("<a id=\"fileIdCodice_btn_modFile\" href=\"javascript:void 0\" onclick=\"editDocsfileIdCodice('" +
		    codiceOggetto +
		    "');\"><i class=\"fa fa-edit\" title=\"Modifica\"></i><fmt:message key=\"label.rettifica\" /></a>");
	    buffer.append(
		    "<div id=\"fileIdCodice_tooltip\" dojoType=\"dijit.Tooltip\" connectId=\"fileIdCodice_btn_modFile\" position=\"below\" style=\"display: none;\"><fmt:message key=\"label.applet_modifica_doc.help\" /></div>");
	}
	buffer.append("<a href=\"" +
		downloadFileLink +
		"\" class=\"azione cmd-visualizza\"><i class=\"fa fa-file-alt\" title=\"Visualizza\"></i><fmt:message key=\"label.visualizza\" /></a>");
	buffer.append("</div>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    private String getDownloadLink(Oggetti oggettiLazy) throws UnsupportedEncodingException, IOException {

	String filename = oggettiLazy.getNomefile();
	if (StringUtils.isNotBlank(filename)) {
	    try {
		String filePath = oggettiService.getSharedFileLink(oggettiLazy.getId().getCodice());
		if (StringUtils.isNotBlank(filePath)) {
		    String redirect = "file:///" + filePath;
		    return redirect;
		}
	    } catch (Exception e) {
		//log.error("Errore nel recupero del file: {}", e);
	    }
	}
	return "../file/ajaxDownload.htm?fileId=" + oggettiLazy.getId().getCodice();
    }

    private boolean addToModelMetadatiFile(Integer codiceOggetto, Model model, Responsabili userLogged, Oggetti oggettoLazy) {

	OggettiMetadatiId idom = new OggettiMetadatiId(codiceOggetto, WebConstants.OGGETTI_FILE_LOCKED_BY);
	OggettiMetadati omdt = oggettiMetadatiService.findById(idom);
	boolean fileBloccato = false;
	if (omdt != null) {
	    fileBloccato = true;
	}
	return fileBloccato;
    }

    public boolean verificaFirmatarioGiaAggiunto(Integer codiceFirmatario, ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand) {

	if (comunicazioniBollettazioneCommand != null) {
	    for (Responsabili firmatario : comunicazioniBollettazioneCommand.getFirmatari()) {
		if (firmatario.getId().getCodice().equals(codiceFirmatario)) {
		    return true;
		}
	    }
	}
	return false;
    }

    public boolean verificaAllegatoCompilabileGiaAggiunto(Integer codiceLetteretipo,
	    ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand) {

	if (comunicazioniBollettazioneCommand != null) {
	    for (Letteretipo letteretipo : comunicazioniBollettazioneCommand.getAllegaticompilabili()) {
		if (letteretipo.getId().getCodice().equals(codiceLetteretipo)) {
		    return true;
		}
	    }
	}
	return false;
    }

    @RequestMapping
    public void ajaxRimuoviFirmatario(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Responsabili firmatario = responsabiliService.findById(new PkId(codiceFirmatario));
	comunicazioniBollettazioneCommand.getFirmatari().remove(firmatario);
	model.addAttribute("comunicazioniBollettazioneCommand", comunicazioniBollettazioneCommand);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @RequestMapping
    public void ajaxRimuoviAllegatoCompilabile(Model model,
	    @ModelAttribute("comunicazioniBollettazioneCommand") ComunicazioniBollettazioneCommand comunicazioniBollettazioneCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceLetteretipo));
	comunicazioniBollettazioneCommand.getAllegaticompilabili().remove(letteretipo);
	response.getOutputStream().write("OK".getBytes());
	response.flushBuffer();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxElabora(@RequestParam("idMassiveTestata") Integer codiceTestata, HttpServletResponse response) throws IOException {

	comunicazioniBollettazioneService.elabora(codiceTestata);
	String retVal = "OK";
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxElaboraRiga(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	try {
	    this.comunicazioniBollettazioneService.elaboraRiga(idRiga);
	} catch (Exception e) {
	    //
	}
	RigaComunicazioneDettagliata riga = this.comunicazioniBollettazioneService.getRigaDettagliata(idRiga);
	String richiesta = Utilities.marshalJsonObject(riga, RigaComunicazioneDettagliata.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(richiesta.getBytes("utf-8"));
    }

    @RequestMapping
    public void ajaxAnteprimaDocRiga(@RequestParam("idRiga") Integer idRiga, @RequestParam("codiceLettera") Integer codiceLettera,
	    @RequestParam("convertiInPdf") Boolean convertiInPdf, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	LetteraGenerataPerComunicazione docAnteprima = this.comunicazioniToBollettazioneService
		.generaLetteraAccompagnamentoBollettazioneDettaglio(codiceLettera, idRiga, convertiInPdf.booleanValue());
	if (docAnteprima.getContenutoFile() != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if (request.getParameter("no_dialog") == null) {
		// BOCCI 2012-08-13
		// Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
		// When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
		// While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
		// According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
		// most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
		// Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + docAnteprima.getNomeFile() + "\"");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    String cType = contenttypesService.findMimeTypeByFileName(docAnteprima.getNomeFile());
	    response.setContentType(cType);
	    response.setContentLength(docAnteprima.getContenutoFile().length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(docAnteprima.getContenutoFile());
	    out.flush();
	} else {
	    throw new RuntimeException("Errore nella generazione del file");
	}
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	RigaComunicazioneDettagliata riga = this.comunicazioniBollettazioneService.getRigaDettagliata(idRiga);
	String richiesta = Utilities.marshalJsonObject(riga, RigaComunicazioneDettagliata.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(richiesta.getBytes("utf-8"));
    }

    @RequestMapping
    public String eliminaMassiva(@RequestParam("idTestata") Integer idTestata, @RequestParam("idBollettazione") Integer idBollettazione,
	    Model model) {

	String msg = "05";
	try {
	    this.comunicazioniBollettazioneService.eliminaMassiva(idTestata, getCurrentlyAuthenticatedUserDetails());
	} catch (Exception e) {
	    msg = "03";
	    FlashMessages.getWarnings().add("Errore nella cancellazione della comunicazione massiva: " + e.getMessage());
	    e.printStackTrace();
	}
	return "redirect:list.htm?idBollettazione=" + idBollettazione + "&status_msg=" + msg;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(ComunicazioniBollettazioneCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(ComunicazioniBollettazioneCommand entity) {

    }
}
