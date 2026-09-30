package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.jmesa.VwConcessionilistaTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Controller
@SessionAttributes("vwConcessionilista")
public class VwConcessionilistaController extends BaseController<VwConcessionilista> {

    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private VwConcessionilistaService vwConcessionilistaService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private MailConfigService mailConfigService;
    private static final Logger log = LoggerFactory.getLogger(VwConcessionilistaController.class);

    @RequestMapping
    public String create(Model model, @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza, HttpServletRequest request) {

	VwConcessionilista vwConcessionilista = new VwConcessionilista();
	//Gestione dei parametri in configurazione utente per l'ordinamento
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI, "concNumero,concDatarilascio", request);
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI);
	vwConcessionilista.setOrderBy(orderFiled);
	List<Concessionicausali> list = concessionicausaliService.findAllbyCausaleStorico(false);
	fixRenderEntityProperty(vwConcessionilista);
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	model.addAttribute("concessioniCausaliList", list);
	setPageAttributes(model);
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("TIPO_SEARCH_CONC", WebConstants.SEARCH_CONC_DEFAULT);
	if (modalita_ricerca != null) {
	    model.addAttribute("TIPO_SEARCH_CONC", modalita_ricerca);
	}
	return "vwconcessionilista/concessionisearch";
    }

    @RequestMapping
    public String search(@ModelAttribute("vwConcessionilista") VwConcessionilista vwConcessionilista, BindingResult result, SessionStatus status,
	    Model model, @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca, HttpServletRequest request,
	    HttpServletResponse response) {

	if (vwConcessionilista.getConcIdmercato() != null && vwConcessionilista.getConcIdmercato() != 0) {
	    Mercati mercato = mercatiService.findById(new PkId(vwConcessionilista.getConcIdmercato()));
	    vwConcessionilista.setConcMercato(mercato.getDescrizione());
	    if (vwConcessionilista.getConcIdmercatiuso() != null && vwConcessionilista.getConcIdmercatiuso() != 0) {
		MercatiUso uso = mercatiUsoService.findById(new PkId(vwConcessionilista.getConcIdmercatiuso()));
		vwConcessionilista.setConcDescrizioneuso(uso.getDescrizione());
	    }
	    if (vwConcessionilista.getConcIdposteggio() != null && vwConcessionilista.getConcIdposteggio() != 0) {
		MercatiD posteggio = mercatiDService.findById(new PkId(vwConcessionilista.getConcIdposteggio()));
		vwConcessionilista.setConcPosteggio(posteggio.getCodiceposteggio());
	    }
	}
	if (vwConcessionilista.getIconcCodicecausale() != null && vwConcessionilista.getIconcCodicecausale() != 0) {
	    Concessionicausali concessionicausali = concessionicausaliService
		    .findById(new PkId(vwConcessionilista.getIconcCodicecausale().intValue()));
	    vwConcessionilista.setIconcCausale(concessionicausali.getDescrizione());
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getTitolareConcessione(), "id.codice") != null) {
	    vwConcessionilista.setTitolareConcessione(anagrafeService.findById(vwConcessionilista.getTitolareConcessione().getId()));
	}
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    vwConcessionilista.getIstanza().setRichiedente(anagrafeService.findById(vwConcessionilista.getIstanza().getRichiedente().getId()));
	}
	GenerateTable<VwConcessionilista> vwConcessionilistaTable = new VwConcessionilistaTable(vwConcessionilista, modalita_ricerca, codiceIstanza);
	String htmlTable = vwConcessionilistaTable.createJMesaList(request, response, "form.vwconcessionilista.title.list", "vwconcessionilista_id",
		true);
	if (htmlTable == null) {
	    return null;
	}
	// Numero di concessioni trovate 
	int numConcessioni = vwConcessionilistaTable.getTotalRows(); // vwConcessionilistaService.countByFilter(vwConcessionilista);
	model.addAttribute("htmltable", htmlTable);
	fixRenderEntityProperty(vwConcessionilista);
	// Nel caso sia passato come filtro tutti i comuni allora verranno presi tutti i comuni attivi per l'operatore
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	model.addAttribute("numConcessioni", numConcessioni);
	// GESTIONE DEL TIPO ESPORTAZIONE : si verifica la verticalizzazione OBS_EXPORT è attiva:
	// Attiva 	: espoertazione con WS ASP
	// Non attiva	: espoertazione PENTAHO
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAIONE_OBS_EXPORT, request);
	return "vwconcessionilista/list";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String popupstampa(Model model, @ModelAttribute("vwConcessionilista") VwConcessionilista conVwConcessionilista, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	if (EntityUtils.getNestedProperty(conVwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    Anagrafe richiedente = anagrafeService.findById(new PkId(conVwConcessionilista.getIstanza().getRichiedente().getId().getCodice()));
	    conVwConcessionilista.getIstanza().setRichiedente(richiedente);
	}
	String url_stampa = BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI() + "?SoloDocTipo=1&windowed=S";
	//StampaAutorizzazioniHelper stampaAutorizzazioniHelper = configStampaAutorizzazioniHelper(autorizzazioniFilter, request);
	String proprietaOrdimanetoMS = mappingProprietaOrdinamento(conVwConcessionilista.getOrderBy());
	String tipoOrdimanetoMS = mappingTipoOrdinamento(conVwConcessionilista.getOrderAscDesc());
	conVwConcessionilista.setOrdinamento(proprietaOrdimanetoMS);
	conVwConcessionilista.setOrdinamentoASCDESC(tipoOrdimanetoMS);
	List<Comuni> comuniassociatiAttiviPerOperatore = (ArrayList<Comuni>) request.getAttribute("comuniassociatiListInRequest");
	String codiceComuni = "";
	for (Comuni comuni : comuniassociatiAttiviPerOperatore) {
	    codiceComuni += comuni.getCodicecomune() + ",";
	}
	codiceComuni = codiceComuni.substring(0, codiceComuni.length() - 1);
	String codiceComune = ((conVwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(conVwConcessionilista.getIstanza().getComune().getCodicecomune())
			? conVwConcessionilista.getIstanza().getComune().getCodicecomune()
			: codiceComuni));
	conVwConcessionilista.setCodiceComuni(codiceComune);
	model.addAttribute("urlStampe", url_stampa);
	model.addAttribute("VwConcessionilista", conVwConcessionilista);
	return "vwconcessionilista/popupstampa";
    }

    @RequestMapping
    public String cessazioniUltimoMese(Model model, HttpServletRequest request, HttpServletResponse response) {

	VwConcessionilista vwConcessionilista = vwConcessionilistaService.populateFilterCessateUltimoMese();
	//Gestione dei parametri in configurazione utente per l'ordinamento
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI, "concNumero,concDatarilascio", request);
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI);
	vwConcessionilista.setOrderBy(orderFiled);
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	return "redirect:../vwconcessionilista/search.htm";//search(vwConcessionilista, result, status, model, request, response);
    }

    @RequestMapping
    public String subentriUltimoMese(Model model, HttpServletRequest request, HttpServletResponse response) {

	VwConcessionilista vwConcessionilista = vwConcessionilistaService.populateFilterSubentriUltimoMese();
	//Gestione dei parametri in configurazione utente per l'ordinamento
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI, "concNumero,concDatarilascio", request);
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI);
	vwConcessionilista.setOrderBy(orderFiled);
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	return "redirect:../vwconcessionilista/search.htm";//search(vwConcessionilista, result, status, model, request, response);
    }

    @RequestMapping
    public String rilasciUltimoMese(Model model, HttpServletRequest request, HttpServletResponse response) {

	VwConcessionilista vwConcessionilista = vwConcessionilistaService.populateFilterRilasciUltimoMese();
	//Gestione dei parametri in configurazione utente per l'ordinamento
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_CONCESSIONI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI, "concNumero,concDatarilascio", request);
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_CONCESSIONI);
	vwConcessionilista.setOrderBy(orderFiled);
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	return "redirect:../vwconcessionilista/search.htm";//search(vwConcessionilista, result, status, model, request, response);
    }

    @RequestMapping
    public void ajaxExport(@RequestParam("codice") String codice, @RequestParam("descrizione") String descrizione, Model model,
	    @RequestParam("email") String emailResponsabile, @RequestParam("isInviaMail") boolean isInviaMail,
	    @ModelAttribute("vwConcessionilista") VwConcessionilista vwConcessionilista, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	//Recupero il  responsabile loggato
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	try {
	    String[] codiceEsportazione = codice.split("\\|");
	    String idComuneEsportazione = codiceEsportazione[1];
	    Integer codiceEsp = Integer.parseInt(codiceEsportazione[0]);
	    byte[] responseByte = vwConcessionilistaService.exportConcessioni(vwConcessionilista, codiceEsp, idComuneEsportazione,
		    responsabile.getEmail(), isInviaMail);
	    String filename = descrizione + "_" + ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + System.currentTimeMillis() + ".zip";
	    if (filename != null && !filename.equals("")) {
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setHeader("Content-transfer-encoding", "binary");
		String cType = contenttypesService.findMimeTypeByFileName(filename);
		response.setContentType(cType);
		response.setContentLength(responseByte.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(responseByte);
		out.flush();
	    } else {
		throw new RuntimeException("File senza nome.");
	    }
	} catch (Exception e) {
	    log.error("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	}
	// §§§END§§§
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export per l'esportazine delle attività tramite il componente
     * esterno Pentaho
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String _comune,
	    @ModelAttribute("vwConcessionilista") VwConcessionilista vwConcessionilista, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	vwConcessionilista.setTransientResponsabili(responsabile);
	if (StringUtils.isBlank(contestoExport)) {
	    contestoExport = "CON";
	}
	List<Esportazioni> listaEsportazioni = new ArrayList<Esportazioni>();
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.CONCESSIONI);
	    if (!listaEsportazioni.isEmpty()) {
		vwConcessionilista.setTransientEsportazioni(listaEsportazioni.get(0));
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(_comune, Integer.parseInt(codiceEsportazione));
	    ids.add(id);
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.CONCESSIONI, ids);
	    Esportazioni esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	    vwConcessionilista.setTransientEsportazioni(esportazioni);
	}
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("vwConcessionilista", vwConcessionilista);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	String codiceComune = null;
	if (vwConcessionilista.getIstanza() != null && vwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
	    codiceComune = vwConcessionilista.getIstanza().getComune().getCodicecomune();
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiceComune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	// Se il contesto è presente si verifica quale contesto è stato scelto
	//	if (iscontestoExportPresente) {
	//	    // FIX potrebbe non servire (ad oggi non ci entra mai)
	//	    //	    if (contestoExport.equals("ATS")) {
	//	    //		model.addAttribute("contestoExport", "ATS");
	//	    //		return "iattivita/exportIAttivitaInDataPentaho";
	//	    //	    } else {
	//	    //		log.error("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
	//	    //		throw new RuntimeException("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
	//	    //	    }
	//	} else// Se non è passoto di default si va alla jsp che gestisce il contesto di tipo "ATT"
	//	{
	return "vwconcessionilista/exportConcessioniPentaho";
	//	}
    }

    /**
     * 
     * @param contestoExport
     * @param emailResponsabile
     * @param isInviaMail
     * @param codiceEsportazione
     * @param data
     * @param model
     * @param vwConcessionilista
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    //FIX ME data è rimasto, ma potrebbe non servire, ad oggi non viene usata
    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "data") String data, @RequestParam(required = false, value = "idAccount") String idAccount,
	    Model model, @ModelAttribute("vwConcessionilista") VwConcessionilista vwConcessionilista, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca delle concessioni le salva sulla tabella TMP_ESPORTAZIONI ");
	// Ad oggi sarà sempre null, non viene utilizzata (è previsto l'export solo in data attuale)
	Date _data = null;
	if (StringUtils.isNotBlank(data)) {
	    _data = Utilities.parseDateString(data, false);
	}
	String sessionId = vwConcessionilistaService.exportModalitaPentaho(vwConcessionilista, vwConcessionilista.getTransientEsportazioni(), _data,
		StringUtils.defaultIfEmpty(emailResponsabile, ""), contestoExport, BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di Pentaho");
	try {
	    String pathFile = pentahoService.callTrasformazione(vwConcessionilista.getTransientEsportazioni().getTrasformazione(),
		    vwConcessionilista.getTransientEsportazioni().getParametriesportaziones(), sessionId, response);
	    //String pathFile = "C:\\Temp\\src.zip";
	    if (isInviaMail == true && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    //mailServiceWSClient.sendMail(null, ORMHelper.getSoftware(), ORMHelper.getToken(), mailMessage);
		    String codiceComune = null;
		    if (vwConcessionilista.getIstanza() != null && vwConcessionilista.getIstanza().getComune() != null
			    && StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
			codiceComune = vwConcessionilista.getIstanza().getComune().getCodicecomune();
		    }
		    Integer _idAccount = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			_idAccount = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), _idAccount, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho#Tipo email non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + vwConcessionilista.getTransientEsportazioni().getDescrizione() + ". Err: ",
		    e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    /**
     * Controlla se la verticalizzazione è attiva o meno e mette in request un attributo con nome pari al nome della
     * verticalizzazione e valore true o false a seconda che sia attivo o meno
     * 
     * @param vertName
     *            nome della verticalizzazione
     * @param request
     */
    protected boolean isVerticalizzazioneAttiva(String vertName, HttpServletRequest request) {

	boolean isAttiva = verticalizzazioniService.isAttiva(vertName);
	request.setAttribute(vertName, isAttiva);
	return isAttiva;
    }

    // Effettua il mapping tra il tipo ordimentosu VBG e il parametro che rappresenta lo
    // stesso tipo di ordinamento sulla funzionalità ASP di stampa autorizzazioni (Es. ASC---> Crescente)
    private String mappingTipoOrdinamento(OrderTypeEnum orderAscDesc) {

	if (orderAscDesc.equals(OrderTypeEnum.ASC)) {
	    return "Crescente";
	}
	if (orderAscDesc.equals(OrderTypeEnum.DESC)) {
	    return "Decrescente";
	}
	return "";
    }

    // Effettua il mapping tra la proprietà per cui facciamo l'ordimento su VBG e il parametro che rappresenta lo
    // stesso tipo di ordinamento sulla funzionalità ASP di stampa autorizzazioni (Es. istanza.numeroistanza---> Codice Istanza)
    private String mappingProprietaOrdinamento(String orderBy) {

	if (orderBy.equals("concNumero,concDatarilascio")) {
	    return "Numero Autorizzazione";
	}
	if (orderBy.equals("concDatarilascio,concNumero")) {
	    return "Data Rilascio";
	}
	if (orderBy.equals("istanza.data")) {
	    return "Data Presentazione";
	}
	if (orderBy.equals("anagrafeconcessione")) {
	    return "Richiedente";
	}
	if (orderBy.equals("istanza.numeroistanza")) {
	    return "Codice Istanza";
	}
	if (orderBy.equals("istanza.istanzestradario.stradario.descrizione")) {
	    return "Localizzazione";
	}
	return null;
    }

    @Override
    protected void fixMergeEntityProperty(VwConcessionilista entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(VwConcessionilista entity) {

	if (EntityUtils.getNestedProperty(entity.getTitolareConcessione(), "id.codice") == null) {
	    entity.setTitolareConcessione(new Anagrafe());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
