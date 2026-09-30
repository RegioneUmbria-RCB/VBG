package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
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

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.backoffice.web.helper.RestJsonListHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.EsportazioniPentahoCommand;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoPosteggiCommand;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.BorselliniListModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.BorsellinoListModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RicercaBorselliniRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.StatoBorsellinoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.RicaricaBackofficeLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.RimborsoBackofficeLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AbbonamentoConfigModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppAutorizzazioni;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.BorsellinoAppMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoAggiornamentoBorsellino;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Controller
@SessionAttributes(value = { "abbonamentoPosteggiCommand", "borsellino", "esportazioniPentahoCommand" })
public class AbbonamentoPosteggiController extends BaseController<AbbonamentoPosteggiCommand> {

    private static final Logger log = LoggerFactory.getLogger(AbbonamentoPosteggiController.class);
    private static final String ABBONAMENTOPOSTEGGI_FORM = "abbonamentoposteggi/form";
    private IAbbonamentoService abbonamentoService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private TmpEsportazioniService tmpEsportazioniService;

    @Autowired
    public void setAbbonamentoService(IAbbonamentoService abbonamentoService) {

	this.abbonamentoService = abbonamentoService;
    }

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_ABBONAMENTO.name());
	List<String> sbe = this.abbonamentoService.selectStatoBorsellino();
	ModelMap model = new ModelMap();
	model.addAttribute("statiborsellino", sbe);
	return model;
    }

    private BorselliniListModel popolaBorsellinoListModel(List<AbbonamentoTabellaModel> abbonamenti) {

	List<BorsellinoListModel> l = new ArrayList<BorsellinoListModel>();
	for (AbbonamentoTabellaModel bors : abbonamenti) {
	    BorsellinoListModel b = new BorsellinoListModel();
	    b.setId(bors.getId());
	    b.setIdAnagrafica(bors.getIdAnagrafe());
	    b.setNominativo(bors.getNominativo());
	    b.setDataCreazione(bors.getDataCreazione());
	    b.setStato(bors.getStato());
	    b.setCreditoResiduo(bors.getCreditoResiduo());
	    l.add(b);
	}
	BorselliniListModel borsellino = new BorselliniListModel();
	borsellino.setBorsellini(l);
	return borsellino;
    }

    @RequestMapping
    public String create(Model model) {

	AbbonamentoTabellaModel borsellino = new AbbonamentoTabellaModel();
	setPageAttributes(model);
	if (model.containsAttribute("abbonamentoTabellaModel")) {
	    Map<String, Object> m = model.asMap();
	    AbbonamentoTabellaModel abb = (AbbonamentoTabellaModel) m.get("abbonamentoTabellaModel");
	    if (abb.getIdAnagrafe() != null) {
		borsellino.setIdAnagrafe(abb.getIdAnagrafe());
		borsellino.setNominativo(abb.getNominativo());
	    }
	}
	model.addAttribute("borsellino", borsellino);
	return ABBONAMENTOPOSTEGGI_FORM;
    }

    @RequestMapping
    public String view(@RequestParam("codiceanagrafe") Integer codiceanagrafe, Model model, HttpServletRequest request) {

	if (codiceanagrafe == null) {
	    throw new InvalidConfigurationException("Non è possibile visualizzare il borsellino senza passare l'anagrafica");
	}
	try {
	    AbbonamentoTabellaModel borsellino = this.abbonamentoService.findAbbonamentoByAnagrafica(codiceanagrafe);
	    if (borsellino.getId() == null) {
		model.addAttribute(borsellino);
		return this.create(model);
	    } else {
		model.addAttribute("borsellino", borsellino);
		
		BorsellinoAppModel borsellinoAppModel = abbonamentoService.getBorsellinoFromId(borsellino.getId());
		Map<Integer,String> autorizzazioniMap = new HashMap<Integer,String>();
		
		if(borsellinoAppModel.getAutorizzazioniCollegabili() != null){
		    for(BorsellinoAppAutorizzazioni borsAppAut : borsellinoAppModel.getAutorizzazioniCollegabili()){
			autorizzazioniMap.put(borsAppAut.getId(), "Numero: " + borsAppAut.getNumero() + " Data: " + borsAppAut.getData().substring(0, 10).replace("-", "/") + " Comune: " + borsAppAut.getEnte());
		    }
		}
		model.addAttribute("autorizzazioniMap", autorizzazioniMap);
	    }
	} catch (BorsellinoException e) {
	    e.printStackTrace();
	}
	setPageAttributes(model);
	return ABBONAMENTOPOSTEGGI_FORM;
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("borsellino") AbbonamentoTabellaModel borsellino, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    this.abbonamentoService.inserisci(borsellino);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, borsellino, true, e);
	    setPageAttributes(model);
	    return ABBONAMENTOPOSTEGGI_FORM;
	}
	status.setComplete();
	return "redirect:view.htm?codiceanagrafe=" + borsellino.getIdAnagrafe();
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<String> sbe = this.abbonamentoService.selectStatoBorsellino();
	model.addAttribute("statiborsellino", sbe);
	AbbonamentoConfigModel cfg = abbonamentoService.findAbbonamentoConfig();
	model.addAttribute("IS_OPERATORE", (cfg != null && cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name())));
    }

    @Override
    protected void fixMergeEntityProperty(AbbonamentoPosteggiCommand entity) {

	//  this method is empty
    }

    @Override
    protected void fixRenderEntityProperty(AbbonamentoPosteggiCommand entity) {

	//  this method is empty
    }

    @RequestMapping
    public void ajaxAggiornaStato(Model model, @RequestParam("id") Integer id, @RequestParam(value = "stato", required = false) Integer stato,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String result = "OK";
	if (id == null) {
	    throw new RuntimeException("Impossibile aggiornare lo stato senza passare l'anagrafica");
	}
	StatoBorsellinoEnum sb = stato == 1 ? StatoBorsellinoEnum.ATTIVO : StatoBorsellinoEnum.NONATTIVO;
	try {
	    this.abbonamentoService.aggiornaStato(id, sb);
	} catch (Exception e) {
	    throw new RuntimeException("Non è stato possibile aggiornare lo stato " + e.getMessage());
	}
	response.getOutputStream().write(result.getBytes());
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxViewDettMovimenti(Model model, @RequestParam("idBorsellino") Integer idBorsellino,
	    @RequestParam("idMovimento") Integer idMovimento, HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    BorsellinoAppMovimenti borsMov = abbonamentoService.getMovimentoComune(idBorsellino, idMovimento);
	    String str = Utilities.marshalJsonObject(borsMov, borsMov.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    response.setContentType("application/json");
	    response.getOutputStream().write(str.getBytes("utf-8"));
	} catch (JAXBException e) {
	    response.setContentType("text/plain");
	    response.getOutputStream().write("nessun risultato".getBytes("utf-8"));
	}
    }

    @RequestMapping
    public void ajaxInsertRicarica(Model model, @RequestParam("idBorsellino") Integer idBorsellino, @RequestParam("codiceComune") String codiceComune,
	    @RequestParam("importo") BigDecimal importo, @RequestParam("codiceSoftware") String codiceSoftware, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	String esitoString = null;
	AbbonamentoTabellaModel borsellino = abbonamentoService.findAbbonamentoById(idBorsellino);
	RicaricaBackofficeLogger auditLogger = RicaricaBackofficeLogger.fromModel(borsellino,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString(), codiceComune, importo);
	String softwareOrig = ORMHelper.getSoftware();
	try {
	    
	    if(importo.compareTo(new BigDecimal(0)) <= 0){
		throw new Exception("Il valore di importo non deve essere negativo");
	    }
	    
	    AbbonamentoConfigModel cfg = abbonamentoService.findAbbonamentoConfig(false);
	    if(cfg != null && cfg.getImportomassimo() != null && borsellino.getCreditoResiduo().add(importo).compareTo(cfg.getImportomassimo()) > 0){
		throw new Exception("La soglia non può essere superata, importo massimo: " + cfg.getImportomassimoStr());
	    }
	    
	    ORMHelper.setSoftware(codiceSoftware);
	    abbonamentoService.ricaricaBorsellino(idBorsellino, importo, codiceComune, false);
	    EsitoAggiornamentoBorsellino esito = new EsitoAggiornamentoBorsellino(true, null);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    auditLogger.log();
	} catch (Exception e) {
	    String errore = "Si è verificato un errore durante la ricarica:" + e.getMessage();
	    log.error(errore, e);
	    auditLogger.logError(errore);
	    EsitoAggiornamentoBorsellino esito = new EsitoAggiornamentoBorsellino(false, errore);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	} finally {
	    ORMHelper.setSoftware(softwareOrig);
	}
	auditLogger.logFineMetodo();
	response.getOutputStream().write(esitoString.getBytes("utf-8"));
    }
    
    @RequestMapping
    public void ajaxInsertRimborso(Model model, @RequestParam("idBorsellino") Integer idBorsellino, @RequestParam("codiceComune") String codiceComune,
	    @RequestParam("importo") BigDecimal importo, @RequestParam("codiceSoftware") String codiceSoftware, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	String esitoString = null;
	AbbonamentoTabellaModel borsellino = abbonamentoService.findAbbonamentoById(idBorsellino);
	RimborsoBackofficeLogger auditLogger = RimborsoBackofficeLogger.fromModel(borsellino,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString(), codiceComune, importo);
	String softwareOrig = ORMHelper.getSoftware();
	try {
	    
	    if(importo.compareTo(new BigDecimal(0)) <= 0){
		throw new Exception("Il valore di importo non deve essere negativo");
	    }
	    
	    if(importo.compareTo(borsellino.getCreditoResiduo()) > 0){
		throw new Exception("Il valore di importo non deve superare il saldo del borsellino");
	    }
	    
	    ORMHelper.setSoftware(codiceSoftware);
	    abbonamentoService.rimborsoBorsellino(idBorsellino, importo, codiceComune);
	    EsitoAggiornamentoBorsellino esito = new EsitoAggiornamentoBorsellino(true, null);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    auditLogger.log();	    
	} catch (Exception e) {
	    String errore = "Si è verificato un errore durante il rimborso:" + e.getMessage();
	    log.error(errore, e);
	    auditLogger.logError(errore);
	    EsitoAggiornamentoBorsellino esito = new EsitoAggiornamentoBorsellino(false, errore);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	} finally {
	    ORMHelper.setSoftware(softwareOrig);
	}
	auditLogger.logFineMetodo();
	response.getOutputStream().write(esitoString.getBytes("utf-8"));
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetBorsellino(Model model, @RequestParam(value = "stato", required = false) String stato,
	    @RequestParam(value = "anagrafe", required = false) String anagrafe,
	    @RequestParam(value = "id_autorizzazione", required = false) Integer idAutorizzazione, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String result = null;
	List<AbbonamentoTabellaModel> abbonamenti = this.abbonamentoService
		.findAbbonamenti(RicercaBorselliniRequest.fromParametri(anagrafe, stato, idAutorizzazione));
	BorselliniListModel borsellino = popolaBorsellinoListModel(abbonamenti);
	result = Utilities.marshalJsonObject(borsellino, borsellino.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(result.getBytes("utf-8"));
    }

    @RequestMapping
    public void ajaxBorselliniPerAutorizzazioni(Model model, @RequestParam(value = "autorizzazione", required = false) String autorizzazione,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = null;
	RestJsonListHelper<Integer> ret = new RestJsonListHelper<Integer>();
	List<Integer> abbonamenti = this.abbonamentoService.findBorselliniPerAutorizzazione(autorizzazione);
	ret.setDati(abbonamenti);
	result = Utilities.marshalJsonObject(ret, ret.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(result.getBytes("utf-8"));
    }

    @RequestMapping
    public void ajaxFindAutorizzazioniDaAssociare(Model model, @RequestParam("idBorsellino") Integer idBorsellino,
	    @RequestParam("codiceSoftware") String codiceSoftware, @RequestParam("textToSearch") String textToSearch, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled()) {
	    log.debug("call findAutorizzazioniDaEstremi with textToSearch: {}", textToSearch);
	}
	List<Autorizzazioni> autorizzazioniList = autorizzazioniService.findByEstremi(textToSearch, 50);
	StringBuilder buffer = new StringBuilder("<ul>");
	for (Autorizzazioni autorizzazione : autorizzazioniList) {
	    if (abbonamentoService.findBorselliniPerIdAutorizzazione(autorizzazione.getId().getCodice()).isEmpty()) {
		buffer.append("<li id='").append(autorizzazione.getId().getCodice());
		Anagrafe richiedente = autorizzazione.getAnagrafe();
		String descrizioneRichiedente = "";
		if (richiedente != null && richiedente.getId().getCodice() != null) {
		    Integer richiedenteId = richiedente.getId().getCodice();
		    buffer.append("' name='").append(richiedenteId).append("'>");
		    descrizioneRichiedente = richiedente.getDescrizioneRichiedente();
		} else {
		    buffer.append("'>");
		}
		buffer.append(autorizzazione.getTransientEstremiAut()).append(",").append(descrizioneRichiedente).append("</li>");
	    }
	}
	buffer.append("</ul>");
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
	if (log.isDebugEnabled()) {
	    log.debug("call findAutorizzazioniDaEstremi with textToSearch: '{}' return: '{}'", textToSearch, buffer);
	}
    }

    @RequestMapping
    public void ajaxCollegaAutorizzazione(Model model, @RequestParam("idBorsellino") Integer idBorsellino,
	    @RequestParam("idAutorizzazione") Integer idAutorizzazione, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	String esitoString = "{}";
	AbbonamentoTabellaModel borsellino = abbonamentoService.findAbbonamentoById(idBorsellino);
	String softwareOrig = ORMHelper.getSoftware();
	try {
	    List<Integer> bsPerAuts = abbonamentoService.findBorselliniPerIdAutorizzazione(idAutorizzazione);
	    Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	    if (!bsPerAuts.isEmpty()) {
		StringBuilder errore = new StringBuilder("L'autorizzazione " + aut.getAutoriznumero() + " è già ustata nei seguenti abbonamenti: ");
		for (Integer id : bsPerAuts) {
		    errore.append("<br />\n").append(abbonamentoService.findAbbonamentoById(id).getDescrizione());
		}
		throw new BorsellinoException(errore.toString());
	    }
	    ORMHelper.setSoftware(aut.getTipologiaregistro().getSoftware().getCodice());
	    EsitoAggiornamentoBorsellino esito = abbonamentoService.collegaAutorizzazione(borsellino.getUuid(), idAutorizzazione);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	} catch (Exception e) {
	    String errore = "Si è verificato un errore durante il collegamento dell'autorizzazione: " + e.getMessage();
	    log.error(errore, e);
	    EsitoAggiornamentoBorsellino esito = new EsitoAggiornamentoBorsellino(false, errore);
	    esitoString = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	} finally {
	    ORMHelper.setSoftware(softwareOrig);
	}
	response.setContentType("application/json");
	response.getOutputStream().write(esitoString.getBytes("utf-8"));
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export dei posteggi tramite il componente esterno Pentaho
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam("codice") Integer codice,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String comune, HttpServletRequest request, HttpServletResponse response) {

	EsportazioniPentahoCommand esportazioniPentahoCommand = new EsportazioniPentahoCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	esportazioniPentahoCommand.setResponsabili(responsabile);
	List<Esportazioni> listaEsportazioni = null;
	Esportazioni esportazioni = null;
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.ABBONAMENTO);
	    if (!listaEsportazioni.isEmpty()) {
		esportazioni = listaEsportazioni.get(0);
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    String[] codici = codiceEsportazione.split("---");
	    String idComuneEsportazione = codici[1];
	    Integer codiceEsportazioneInt = Integer.parseInt(codici[0]);
	    PkId id = new PkId(idComuneEsportazione, codiceEsportazioneInt);
	    ids.add(id);
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.ABBONAMENTO, ids);
	    esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	}
	esportazioniPentahoCommand.setEsportazioni(esportazioni);
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("esportazioniPentahoCommand", esportazioniPentahoCommand);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), comune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	return "abbonamentoposteggi/exportPentaho";
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam("codice") Integer codice,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("esportazioniPentahoCommand") EsportazioniPentahoCommand esportazioniPentahoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException, BorsellinoException {

	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	String sessionId = tmpEsportazioniService.exportModalitaPentaho(codice, StringUtils.defaultIfEmpty(emailResponsabile, ""),
		BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di pentaho");
	try {
	    Set<Parametriesportazione> parametriesportazione = esportazioniPentahoCommand.getEsportazioni().getParametriesportaziones();
	    Esportazioni e = esportazioniService.findById(new PkId(esportazioniPentahoCommand.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(), parametriesportazione, sessionId, response);
	    if (BooleanUtils.isTrue(isInviaMail) && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    // Non è previsto codice comune per i posteggi
		    String codiceComune = null;
		    Integer idAccountInt = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			idAccountInt = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), idAccountInt, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho# Mail/Tipo  non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + esportazioniPentahoCommand.getEsportazioni().getDescrizione() + ". Err: ",
		    e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }
}
