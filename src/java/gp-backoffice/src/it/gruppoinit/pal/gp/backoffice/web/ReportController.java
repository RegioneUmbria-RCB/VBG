package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;
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

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.EsportazioniPentahoCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.report.helper.TypeReport;
import it.gruppoinit.pal.gp.core.report.model.ReportBase;
import it.gruppoinit.pal.gp.core.report.model.ReportIstanze;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ReportService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StaticomportamentoService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CEsportazione;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpLocator;
import it.gruppoinit.sigepro.ws.sigeproexport.stub.CWSSigeproExpSoap;

@Controller
@SessionAttributes(value = { "reportbase", "reportistanze", "esportazioniPentahoCommand" })
public class ReportController extends BaseController<Object> {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private StaticomportamentoService staticomportamentoService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private MailConfigService mailConfigService;
    private final static String STATISTICHE = "STATISTICHE";
    private final static String STAMPE = "STAMPE";

    @RequestMapping
    public String createReportBase(Model model) {

	ReportBase reportbase = new ReportBase();
	reportbase.setTypeReport(TypeReport.OPERATORI);
	model.addAttribute("reportbase", reportbase);
	return "report/reportbase";
    }

    @RequestMapping
    public void printReportBase(Model model, @ModelAttribute("reportbase") ReportBase reportBase, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	reportService.doReportDiBase(reportBase, request, response);
    }

    @RequestMapping
    public String createReportIstanze(Model model, @RequestParam(required = false, value = "codiceIstanza") Integer codiceistanza) {

	ReportIstanze reportIstanze = new ReportIstanze();
	reportIstanze.setTypeReport(TypeReport.RICEVUTA_ISTANZA);
	if (codiceistanza != null) {
	    Istanze istanze = istanzeService.findById(new PkId(codiceistanza));
	    reportIstanze.setIstanze(istanze);
	}
	model.addAttribute("reportistanze", reportIstanze);
	return "report/reportistanze";
    }

    @RequestMapping
    public void printReportIstanze(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    reportService.doReportAndStatisticheIstanze(reportIstanze, request, response);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String createReportPerModulo(Model model, HttpServletRequest request) {

	ReportIstanze reportIstanze = new ReportIstanze();
	Software software = softwareService.findById(ORMHelper.getSoftware());
	reportIstanze.getIstanzeFilter().setModulo(software);
	setConfigurazioneUtente(model, request);
	// Recupero il vaore che indica il campo per cui ordiniamo che è stato associato 
	//al parametro di cinfigurazione e lo setto al filtro
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE);
	reportIstanze.getIstanzeFilter().setOrderBy(orderFiled);
	// Recupero il vaore che indica il campo di ricerca stato istanza associato alla configurazione
	// utente  e lo setto al filtro
	String statoIstanza = (String) request.getAttribute(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA);
	reportIstanze.getIstanzeFilter().getChiusura().getId().setCodicestato(statoIstanza);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE------------------------
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, request);
	//	// Recupero il tipi di installazione STANDARD o ENTERPRISE
	//	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
	//		WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE, WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO)
	// -----FINE VERTICALIZZAZIONI ATTIVE------------------------
	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ftable.addOrder(FilterUtils.orderAsc("ordine"));
	List<Statiistanza> statiistanzaList = statiistanzaService.findByFilterTable(ftable);
	model.addAttribute("statiistanzaList", statiistanzaList);
	//	IstanzeOnLineHelper helper = istanzeService.findIstanzeOnline()
	//	model.addAttribute("istanzeOnLineHelper", helper)
	setPageAttributes(model);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STAMPE);
	return "report/reportIstanzePerSoftware";
    }

    @RequestMapping
    public void printReportPerSoftware(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    reportService.doReportAndStatisticheIstanze(reportIstanze, request, response);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String createStatisticaPerModulo(Model model, HttpServletRequest request) {

	ReportIstanze reportIstanze = new ReportIstanze();
	prepareModel(model, request, reportIstanze);
	setPageAttributes(model);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STATISTICHE);
	return "report/reportIstanzePerSoftware";
    }

    @RequestMapping
    public String createStatisticaPentahoPerModulo(Model model, HttpServletRequest request) {

	ReportIstanze reportIstanze = new ReportIstanze();
	prepareModel(model, request, reportIstanze);
	setPageAttributes(model);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STATISTICHE);
	return "report/statisticheIstanzaPerSoftware";
    }

    private void prepareModel(Model model, HttpServletRequest request, ReportIstanze reportIstanze) {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	reportIstanze.getIstanzeFilter().setModulo(software);
	setConfigurazioneUtente(model, request);
	// Recupero il vaore che indica il campo per cui ordiniamo che è stato associato 
	//al parametro di cinfigurazione e lo setto al filtro
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE);
	reportIstanze.getIstanzeFilter().setOrderBy(orderFiled);
	// Recupero il valore che indica il campo di ricerca stato istanza associato alla configurazione
	// utente  e lo setto al filtro
	String statoIstanza = (String) request.getAttribute(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA);
	reportIstanze.getIstanzeFilter().getChiusura().getId().setCodicestato(statoIstanza);
	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	ftable.addOrder(FilterUtils.orderAsc("ordine"));
	List<Statiistanza> statiistanzaList = statiistanzaService.findByFilterTable(ftable);
	model.addAttribute("statiistanzaList", statiistanzaList);
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	reportIstanze.setResponsabili(responsabile);
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE------------------------
	// -----INIZIO VERTICALIZZAZIONI ATTIVE----------------------
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_PEOPLE, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_AREA_RISERVATA, request);
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE, WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO);
	if (verticalizzazioniparametri != null
		&& verticalizzazioniparametri.getValore().equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_STANDARD)) {
	    model.addAttribute(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO,
		    WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_STANDARD);
	} else {
	    model.addAttribute(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO,
		    WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_TIPO_ENTERPRISE);
	}
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAIONE_OBS_EXPORT, request);
	// -----FINE VERTICALIZZAZIONI ATTIVE------------------------
    }

    @RequestMapping
    public void printStatisticaPerSoftware(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) {

	try {
	    reportService.doReportAndStatisticheIstanze(reportIstanze, request, response);
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage());
	}
    }

    @RequestMapping
    public String printStatisticheModalitaDotNet(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Creo l'URL della chiamata alla pagina ASP
	List<String> errorivalidazione = validaCampiObbligatori(reportIstanze);
	if (errorivalidazione.size() > 2) {
	    FlashMessages.setWarnings(errorivalidazione);
	    return "report/statisticheIstanzaPerSoftware";
	}
	String urlcreaallegato = BackofficeNETConstants.getURL_GENERA_STAMPA();
	String baseUrl = getBaseUrlFromAspnetBaseURL();
	urlcreaallegato = baseUrl + urlcreaallegato;
	// I parametri IDCOMUNE,SOFTWARE,TOKEN devono essere passati in request
	urlcreaallegato += "?IDCOMUNE=" + ORMHelper.getIdcomune() + "&SOFTWARE=" + ORMHelper.getSoftware() + "&TOKEN=" + ORMHelper.getToken();
	HttpClient h = new HttpClient();
	PostMethod p = new PostMethod(urlcreaallegato);
	log.debug("printStatisticheModalitaDotNet# Creo i parametri da mandare in post");
	NameValuePair[] data = setParametriPost(reportIstanze);
	p.setRequestBody(data);
	log.debug("printStatisticheModalitaDotNet# chiamo il link {} ", new Object[] { urlcreaallegato });
	int resStatus = h.executeMethod(p);
	log.debug("printStatisticheModalitaDotNet# risposta chiamata link {} :{}", new Object[] { urlcreaallegato, resStatus });
	String s = p.getResponseBodyAsString();
	if (resStatus == 200) {
	    log.debug("printStatisticheModalitaDotNet# resposose {}", resStatus);
	    // recupero il link tornato dalla chiamata generastampa.asp
	    String fileURL = URIUtil.encodeQuery(
		    StringUtils.substringBetween(s, "#@", "#@") + "&SOFTWARE=" + ORMHelper.getSoftware() + "&TOKEN=" + ORMHelper.getToken());
	    log.debug("printStatisticheModalitaDotNet# Link per il download del file recuperato per dalla response {}", fileURL);
	    URL url = new URL(fileURL);
	    log.debug("printStatisticheModalitaDotNet# Call link download file.....");
	    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	    int responseCode = httpConn.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		log.debug("printStatisticheModalitaDotNet# response {}", responseCode);
		String fileName = "";
		String disposition = httpConn.getHeaderField("Content-Disposition");
		log.debug("printStatisticheModalitaDotNet# Recupero il nome del file");
		if (disposition != null) {
		    // extracts file name from header field
		    log.debug("printStatisticheModalitaDotNet# Recupero nome file dal campo header");
		    int index = disposition.indexOf("filename=");
		    if (index > 0) {
			fileName = disposition.substring(index + 10, disposition.length() - 1);
		    }
		} else {
		    log.debug("printStatisticheModalitaDotNet# Recupero nome file dall' URL");
		    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
		}
		// 
		log.debug("printStatisticheModalitaDotNet# Apro input stream dall HTTP connection...");
		InputStream inputStream = httpConn.getInputStream();
		log.debug("printStatisticheModalitaDotNet# Converto lo stream in byte[] e creo la response");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		if (bytes != null) {
		    response.setHeader("Pragma", "public");
		    response.setHeader("Cache-Control", "max-age=0");
		    if (request.getParameter("no_dialog") == null) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		    }
		    response.setHeader("Content-transfer-encoding", "binary");
		    String cType = contenttypesService.findMimeTypeByFileName(fileName);
		    response.setContentType(cType);
		    response.setContentLength(bytes.length);
		    log.debug("printStatisticheModalitaDotNet# flush response");
		    ServletOutputStream out = response.getOutputStream();
		    out.write(bytes);
		    out.flush();
		    inputStream.close();
		} else {
		    log.debug("printStatisticheModalitaDotNet# Contenuto file vuoto");
		    List<String> error = new ArrayList<String>();
		    error.add("Errore durante la creazione della stampa: Contenuto file vuoto");
		    FlashMessages.setWarnings(error);
		}
		httpConn.disconnect();
	    } else {
		log.error("Errore dutante la chiamata al link {}.", new Object[] { fileURL });
		log.error("Code response: {}", new Object[] { responseCode });
		List<String> error = new ArrayList<String>();
		error.add("Errore durante il download del file:");
		error.add("Url chiamato:" + fileURL);
		error.add("Status response:" + responseCode);
		error.add("Response:" + httpConn.getResponseMessage());
		FlashMessages.setWarnings(error);
		//
	    }
	}
	if (resStatus == 500) {
	    log.error("Errore dutante la chiamata al link {}.", new Object[] { urlcreaallegato });
	    log.error("Code response: {}", new Object[] { resStatus });
	    log.error("Response: {}", new Object[] { s });
	    List<String> error = new ArrayList<String>();
	    error.add("Errore durante la creazione della stampa:");
	    error.add("Url chiamato:" + urlcreaallegato);
	    error.add("Status response:" + resStatus);
	    error.add("Response: " + s);
	    FlashMessages.setWarnings(error);
	    return "report/statisticheIstanzaPerSoftware";
	}
	return "report/statisticheIstanzaPerSoftware";
    }

    private List<String> validaCampiObbligatori(ReportIstanze reportIstanze) {

	List<String> error = new ArrayList<String>();
	error.add("Attenzione errore durante la creazione della stampa:<ul>");
	if (EntityUtils.getNestedProperty(reportIstanze.getLetteretipo(), "id.codice") == null) {
	    error.add("<li>Documento tipo obbligatorio</ol>");
	}
	error.add("</ul>");
	return error;
    }

    private NameValuePair[] setParametriPost(ReportIstanze reportIstanze) {

	List<NameValuePair> listDataPOst = new ArrayList<NameValuePair>();
	// gestione filtro comune nel caso il codice comune sia null, allora significa che devo passare tutti i comuni attivi per 
	// l'operatore che ha richiesto la statistica.
	// Recupero dei parametri da elaborare
	String codcomune = recuperaCodiceComune(reportIstanze.getIstanzeFilter().getComune());
	String stato = recuperastato(reportIstanze.getIstanzeFilter().getChiusura().getStato());
	// Creazione stringa di request
	if (StringUtils.isNotBlank(reportIstanze.getIstanzeFilter().getNumeroistanza())) {
	    NameValuePair numeroIstanza = new NameValuePair("NUMEROISTANZA", reportIstanze.getIstanzeFilter().getNumeroistanza());
	    listDataPOst.add(numeroIstanza);
	}
	if (reportIstanze.getIstanzeFilter().getDallaData() != null) {
	    NameValuePair dalladata = new NameValuePair("DALLADATA", Utilities.formatDate(reportIstanze.getIstanzeFilter().getDallaData(), false));
	    listDataPOst.add(dalladata);
	}
	if (reportIstanze.getIstanzeFilter().getAllaData() != null) {
	    NameValuePair alladata = new NameValuePair("ALLADATA", Utilities.formatDate(reportIstanze.getIstanzeFilter().getAllaData(), false));
	    listDataPOst.add(alladata);
	}
	NameValuePair codicecomune = new NameValuePair("CODICECOMUNE", codcomune);
	listDataPOst.add(codicecomune);
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getAlberoproc(), "id.codice") != null) {
	    NameValuePair codiceinterventoProc = new NameValuePair("CODICEINTERVENTOPROC",
		    reportIstanze.getIstanzeFilter().getAlberoproc().getId().getCodice().toString());
	    NameValuePair interventoproc = new NameValuePair("INTERVENTOPROC", reportIstanze.getIstanzeFilter().getAlberoproc().getScDescrizione());
	    listDataPOst.add(codiceinterventoProc);
	    listDataPOst.add(interventoproc);
	}
	if (reportIstanze.getIstanzeFilter().getChkexportanagrafetrib() != null) {
	    NameValuePair checkExportAnagTrib = new NameValuePair("CHKEXPORTANAGRAFETRIB",
		    BooleanUtils.toStringTrueFalse(reportIstanze.getIstanzeFilter().getChkexportanagrafetrib()));
	    listDataPOst.add(checkExportAnagTrib);
	}
	if (StringUtils.isNotBlank(reportIstanze.getIstanzeFilter().getLavori())) {
	    NameValuePair lavori = new NameValuePair("LAVORI", reportIstanze.getIstanzeFilter().getLavori());
	    listDataPOst.add(lavori);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getProcedura(), "id.codice") != null) {
	    NameValuePair codiceprocedura = new NameValuePair("CODICEPROCEDURA",
		    reportIstanze.getIstanzeFilter().getProcedura().getId().getCodice().toString());
	    NameValuePair procedura = new NameValuePair("PROCEDURA", reportIstanze.getIstanzeFilter().getProcedura().getProcedura());
	    listDataPOst.add(codiceprocedura);
	    listDataPOst.add(procedura);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getIstanzearee().getArea(), "id.codice") != null) {
	    NameValuePair codicearea = new NameValuePair("CODICEAREA",
		    reportIstanze.getIstanzeFilter().getIstanzearee().getArea().getId().getCodice().toString());
	    NameValuePair denominazione = new NameValuePair("DENOMINAZIONE",
		    reportIstanze.getIstanzeFilter().getIstanzearee().getArea().getDenominazione());
	    listDataPOst.add(codicearea);
	    listDataPOst.add(denominazione);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getDatiAutorizzazione(), "id.codice") != null && EntityUtils
		.getNestedProperty(reportIstanze.getIstanzeFilter().getDatiAutorizzazione().getTipologiaregistro(), "id.codice") != null) {
	    NameValuePair regprovvaut = new NameValuePair("REGPROVVAUT",
		    reportIstanze.getIstanzeFilter().getDatiAutorizzazione().getTipologiaregistro().getId().getCodice().toString());
	    NameValuePair hregistro = new NameValuePair("HREGISTRO",
		    reportIstanze.getIstanzeFilter().getDatiAutorizzazione().getTipologiaregistro().getTrDescrizione());
	    listDataPOst.add(regprovvaut);
	    listDataPOst.add(hregistro);
	}
	// Gestire il caso tutto
	NameValuePair statoistanza = new NameValuePair("STATOISTANZA", stato);//0 ( Tutte -> % , Tutte le istanze non chiuse -> 0, Tutte le istanze chiuse -> 1,-1)
	String descStato = "TUTTI";
	if (!stato.equalsIgnoreCase("%")) {
	    descStato = staticomportamentoService.findById(Integer.parseInt(stato)).getComportamento();
	}
	NameValuePair hstatoistanza = new NameValuePair("HSTATOISTANZA", descStato);
	listDataPOst.add(statoistanza);
	listDataPOst.add(hstatoistanza);
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getResponsabile(), "id.codice") != null) {
	    NameValuePair codiceResponsabile = new NameValuePair("CODICERESPONSABILE",
		    reportIstanze.getIstanzeFilter().getResponsabile().getId().getCodice().toString());
	    NameValuePair responsabile = new NameValuePair("RESPONSABILE", reportIstanze.getIstanzeFilter().getResponsabile().getResponsabile());
	    listDataPOst.add(codiceResponsabile);
	    listDataPOst.add(responsabile);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getResponsabileProcedimento(), "id.codice") != null) {
	    NameValuePair codiceresponsabileproc = new NameValuePair("CODICERESPONSABILEPROC",
		    reportIstanze.getIstanzeFilter().getResponsabileProcedimento().getId().getCodice().toString());
	    NameValuePair responsabilep = new NameValuePair("RESPONSABILEP",
		    reportIstanze.getIstanzeFilter().getResponsabileProcedimento().getResponsabile());
	    listDataPOst.add(codiceresponsabileproc);
	    listDataPOst.add(responsabilep);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    NameValuePair codicerichiedente = new NameValuePair("CODICERICHIEDENTE",
		    reportIstanze.getIstanzeFilter().getRichiedente().getId().getCodice().toString());
	    NameValuePair richiedente = new NameValuePair("RICHIEDENTE",
		    reportIstanze.getIstanzeFilter().getRichiedente().getDescrizioneRichiedente());
	    listDataPOst.add(codicerichiedente);
	    listDataPOst.add(richiedente);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    NameValuePair codiceprofessionista = new NameValuePair("CODICEPROFESSIONISTA",
		    reportIstanze.getIstanzeFilter().getProfessionista().getId().getCodice().toString());
	    NameValuePair professionista = new NameValuePair("PROFESSIONISTA",
		    reportIstanze.getIstanzeFilter().getProfessionista().getDescrizioneRichiedente());
	    listDataPOst.add(codiceprofessionista);
	    listDataPOst.add(professionista);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getTipoMovimento(), "id.tipomovimento") != null) {
	    NameValuePair codicetipomovimento = new NameValuePair("CODICETIPOMOVIMENTO",
		    reportIstanze.getIstanzeFilter().getTipoMovimento().getId().getTipomovimento());
	    NameValuePair descrizioneTipoMovimento = new NameValuePair("DESCRIZIONETIPOMOVIMENTO",
		    reportIstanze.getIstanzeFilter().getTipoMovimento().getMovimento());
	    listDataPOst.add(codicetipomovimento);
	    listDataPOst.add(descrizioneTipoMovimento);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getInventarioprocedimenti(), "id.codice") != null) {
	    NameValuePair codiceEndo = new NameValuePair("CODICEENDO",
		    reportIstanze.getIstanzeFilter().getInventarioprocedimenti().getId().getCodice().toString());
	    NameValuePair descrizioneEndo = new NameValuePair("DESCRIZIONEENDO",
		    reportIstanze.getIstanzeFilter().getInventarioprocedimenti().getProcedimento());
	    listDataPOst.add(codiceEndo);
	    listDataPOst.add(descrizioneEndo);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getIstanzestradario(), "id.codice") != null) {
	    NameValuePair codiceStradario = new NameValuePair("CODICESTRADARIO",
		    reportIstanze.getIstanzeFilter().getIstanzestradario().getId().getCodice().toString());
	    NameValuePair stradario = new NameValuePair("STRADARIO",
		    reportIstanze.getIstanzeFilter().getIstanzestradario().getStradario().getDescrizione());
	    NameValuePair civico = new NameValuePair("CIVICO", reportIstanze.getIstanzeFilter().getIstanzestradario().getCivico());
	    listDataPOst.add(codiceStradario);
	    listDataPOst.add(stradario);
	    listDataPOst.add(civico);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getTipiarchivioistanza(), "id.codice") != null) {
	    NameValuePair codicetipoarchivio = new NameValuePair("CODICETIPOARCHIVIO",
		    reportIstanze.getIstanzeFilter().getTipiarchivioistanza().getId().getCodice().toString());
	    NameValuePair tipoarchivio = new NameValuePair("TIPOARCHIVIO", reportIstanze.getIstanzeFilter().getTipiarchivioistanza().getArchivio());
	    listDataPOst.add(codicetipoarchivio);
	    listDataPOst.add(tipoarchivio);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getIstanzeattivita(), "id.codice") != null
		&& EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita(), "id.codice") != null) {
	    NameValuePair codiceattivita = new NameValuePair("CODICEATTIVITA",
		    reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita().getId().getCodiceistat());
	    NameValuePair codiceattivitadesc = new NameValuePair("CODICEATTIVITADESC",
		    reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita().getDescrizioneEstesa());
	    listDataPOst.add(codiceattivita);
	    listDataPOst.add(codiceattivitadesc);
	    if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita().getSettori(),
		    "id.codice") != null) {
		NameValuePair codicesettore2 = new NameValuePair("CODICESETTORE2",
			reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore());
		NameValuePair codicesettore2Desc = new NameValuePair("CODICESETTORE2DESC",
			reportIstanze.getIstanzeFilter().getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore());
		listDataPOst.add(codicesettore2);
		listDataPOst.add(codicesettore2Desc);
	    }
	}
	if (reportIstanze.getIstanzeFilter().getDaMq() != null) {
	    NameValuePair damq = new NameValuePair("DAMQ", reportIstanze.getIstanzeFilter().getDaMq().toString());
	    listDataPOst.add(damq);
	}
	if (reportIstanze.getIstanzeFilter().getaMq() != null) {
	    NameValuePair amq = new NameValuePair("AMQ", reportIstanze.getIstanzeFilter().getaMq().toString());
	    listDataPOst.add(amq);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getBandi(), "id.codice") != null) {
	    NameValuePair idbando = new NameValuePair("IDBANDO", reportIstanze.getIstanzeFilter().getBandi().getId().getCodice().toString());
	    NameValuePair bando = new NameValuePair("BANDO", reportIstanze.getIstanzeFilter().getBandi().getDescrizione());
	    listDataPOst.add(idbando);
	    listDataPOst.add(bando);
	    if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getGraduatoriet(), "id.codice") != null) {
		NameValuePair codgrad = new NameValuePair("CODGRAD",
			reportIstanze.getIstanzeFilter().getGraduatoriet().getId().getCodice().toString());
		NameValuePair graduatoria = new NameValuePair("GRADUATORIA", reportIstanze.getIstanzeFilter().getGraduatoriet().getDescrizione());
		listDataPOst.add(codgrad);
		listDataPOst.add(graduatoria);
	    }
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getLetteretipo(), "id.codice") != null) {
	    NameValuePair codicelettera = new NameValuePair("CODICELETTERA", reportIstanze.getLetteretipo().getId().getCodice().toString());
	    NameValuePair lettera = new NameValuePair("LETTERA", reportIstanze.getLetteretipo().getDescrizione());
	    listDataPOst.add(codicelettera);
	    listDataPOst.add(lettera);
	}
	if (reportIstanze.getIstanzeFilter().getDallaDataValidita() != null) {
	    NameValuePair datavaliditada = new NameValuePair("DATAVALIDITADA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getDallaDataValidita(), false));
	    listDataPOst.add(datavaliditada);
	}
	if (reportIstanze.getIstanzeFilter().getAllaDataValidita() != null) {
	    NameValuePair datavaliditaa = new NameValuePair("DATAVALIDITAA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getAllaDataValidita(), false));
	    listDataPOst.add(datavaliditaa);
	}
	if (reportIstanze.getIstanzeFilter().getDallaDataProtocollo() != null) {
	    NameValuePair dataprotocolloda = new NameValuePair("DATAPROTOCOLLODA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getDallaDataProtocollo(), false));
	    listDataPOst.add(dataprotocolloda);
	}
	if (reportIstanze.getIstanzeFilter().getAllaDataProtocollo() != null) {
	    NameValuePair dataprotocolloa = new NameValuePair("DATAPROTOCOLLOA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getAllaDataProtocollo(), false));
	    listDataPOst.add(dataprotocolloa);
	}
	if (EntityUtils.getNestedProperty(reportIstanze.getIstanzeFilter().getResponsabileIstruttoria(), "id.codice") != null) {
	    NameValuePair codiceistruttore = new NameValuePair("CODICEISTRUTTORE",
		    reportIstanze.getIstanzeFilter().getResponsabileIstruttoria().getId().getCodice().toString());
	    NameValuePair istruttore = new NameValuePair("ISTRUTTORE",
		    reportIstanze.getIstanzeFilter().getResponsabileIstruttoria().getResponsabile());
	    listDataPOst.add(codiceistruttore);
	    listDataPOst.add(istruttore);
	}
	if (reportIstanze.getIstanzeFilter().getDallaDataSorteggio() != null) {
	    NameValuePair datasorteggioda = new NameValuePair("DATASORTEGGIODA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getDallaDataSorteggio(), false));
	    listDataPOst.add(datasorteggioda);
	}
	if (reportIstanze.getIstanzeFilter().getAllaDataSorteggio() != null) {
	    NameValuePair datasorteggioa = new NameValuePair("DATASORTEGGIOA",
		    Utilities.formatDate(reportIstanze.getIstanzeFilter().getAllaDataSorteggio(), false));
	    listDataPOst.add(datasorteggioa);
	}
	NameValuePair tipoconteggio = new NameValuePair("TIPOCONTEGGIO", BooleanUtils.toStringTrueFalse(reportIstanze.getConteggioMqOrNrIstanze()));
	listDataPOst.add(tipoconteggio);
	NameValuePair[] postData = new NameValuePair[listDataPOst.size()];
	postData = listDataPOst.toArray(postData);
	return postData;
    }

    private String recuperaCodiceComune(Comuni comuni) {

	StringBuilder codicicomune = new StringBuilder();
	if (comuni != null && StringUtils.isBlank(comuni.getComune())) {
	    List<Comuniassociati> associatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    for (Comuniassociati associati : associatis) {
		codicicomune.append(associati.getId().getCodicecomune()).append(",");
	    }
	} else {
	    return comuni.getCodicecomune();
	}
	return StringUtils.removeEnd(codicicomune.toString(), ",");
    }

    private String recuperastato(String stato) {

	// '%' Indica tutte
	String codificaStato = "%";
	// se diverso vuoto 
	if (StringUtils.isNotBlank(stato)) {
	    return codificaStato;
	}
	return codificaStato;
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String ajaxExportModalitaDotNet(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    HttpServletResponse response) throws IOException {

	if (StringUtils.isBlank(contestoExport)) {
	    contestoExport = "IST";
	}
	CWSSigeproExpLocator service = new CWSSigeproExpLocator();
	response.setContentType("text/plain");
	try {
	    // Recuperare wsdl dalla configurazione
	    CWSSigeproExpSoap port = service.getCWSSigeproExpSoap(new URL(WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_EXPORT)));
	    // CHIAMO IL METODO LISTEXPCONTEXT DEL WEBSERVICE SIGEPROEXPORT
	    CEsportazione[] lista = port.listExpContext(ORMHelper.getToken(), contestoExport);
	    model.addAttribute("listaEsportazioni", lista);
	} catch (ServiceException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	} catch (RemoteException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(remote error):" + e.getMessage());
	} catch (MalformedURLException e) {
	    log.error("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	    throw new RuntimeException("WS IATTIVITA - ERRORE WEBSERVICE SIGEPROEXPORT(url webservice error):" + e.getMessage());
	}
	return "report/export";
    }

    @RequestMapping
    public void ajaxCreateExportModalitaDotNet(@RequestParam("codice") String codice, @RequestParam("descrizione") String descrizione,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") boolean isInviaMail, Model model,
	    @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	log.debug("ajaxCreateExportModalitaDotNet# Esportazione tramite funzionalità exoport dotNet");
	IstanzeFilter filter = reportIstanze.getIstanzeFilter();
	try {
	    String[] codiceEsportazione = codice.split("\\|");
	    String idComuneEsportazione = codiceEsportazione[1];
	    Integer codiceEsp = Integer.parseInt(codiceEsportazione[0]);
	    byte[] responseByte = istanzeService.export(filter, codiceEsp, idComuneEsportazione, emailResponsabile, isInviaMail);
	    String filename = descrizione + "_" + ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + System.currentTimeMillis() + ".zip";

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
	    } catch (Exception e) {
		log.error("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
		throw new RuntimeException("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    }
	    // §§§END§§§
    }

    @RequestMapping
    public String createExportModalitaPentaho(@RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String _comune, Model model,
	    @ModelAttribute("reportistanze") ReportIstanze reportIstanze, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	log.debug("ajaxCreateExportModalitaDotNet# Creazione funzione di esportazione tramite  Penthao");
	// Query da modificare
	List<Esportazioni> esportazionis = new ArrayList<Esportazioni>();
	// Se la lista delle esportazioni è non vuota, inserisco il promo record nel command 
	// reportIstanze in modo che se sono presenti vengano popolati anche i parametri dell'esportazione
	Esportazioni esportazioni = null;
	if (codiceEsportazione != null) {
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(_comune, Integer.parseInt(codiceEsportazione));
	    ids.add(id);
	    esportazionis = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.ISTANZE, ids);
	    esportazioni = esportazioniService.findById(id);
	    esportazionis.add(0, esportazioni);
	    reportIstanze.setEsportazioni(esportazioni);
	} else {
	    esportazionis = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.ISTANZE);
	    if (!esportazionis.isEmpty() && esportazioni == null) {
		reportIstanze.setEsportazioni(esportazionis.get(0));
	    }
	}
	String codiceComune = null;
	if (reportIstanze.getIstanzeFilter() != null && reportIstanze.getIstanzeFilter().getComune() != null
		&& StringUtils.isNotBlank(reportIstanze.getIstanzeFilter().getComune().getCodicecomune())) {
	    codiceComune = reportIstanze.getIstanzeFilter().getComune().getCodicecomune();
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiceComune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("esportazionis", esportazionis);
	model.addAttribute("_codiceEsportazione", codiceEsportazione);
	return "report/createExportModalitaPentaho";
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("reportistanze") ReportIstanze reportIstanze, @RequestParam BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca le istanze e le salva sulla tabella TMP_ESPORTAZIONI ");
	String sessionId = istanzeService.exportModalitaPentaho(reportIstanze.getIstanzeFilter(), reportIstanze.getEsportazioni(),
		StringUtils.defaultIfEmpty(emailResponsabile, ""), BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di Pentaho");
	try {
	    Set<Parametriesportazione> parametriesportazione = reportIstanze.getEsportazioni().getParametriesportaziones();
	    Esportazioni e = esportazioniService
		    .findById(new PkId(reportIstanze.getEsportazioni().getId().getIdcomune(), reportIstanze.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(), parametriesportazione, sessionId, response);
	    if (isInviaMail == true && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    String codiceComune = null;
		    if (reportIstanze.getIstanzeFilter() != null && reportIstanze.getIstanzeFilter().getComune() != null
			    && StringUtils.isNotBlank(reportIstanze.getIstanzeFilter().getComune().getCodicecomune())) {
			codiceComune = reportIstanze.getIstanzeFilter().getComune().getCodicecomune();
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
	    log.error("Errore durante la chiamata alla trasformazione" + reportIstanze.getEsportazioni().getDescrizione() + " . Err: ", e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    @RequestMapping
    public String changeScheda(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = reportIstanze.getIstanzeFilter().getSchedaDinamicaFilter();
	if (sf.getScheda() != null) {
	    if (sf.getScheda().getId() != null) {
		if (sf.getScheda().getId().getCodice() != null) {
		    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(sf.getScheda().getId().getCodice()));
		    List<Dyn2Campi> d2cs = dyn2CampiService.findByDescrizioneAndSoftwareAndModello("", sf.getScheda().getId().getCodice(), null);
		    sf.getListaCampiModello().addAll(d2cs);
		    sf.setScheda(scheda);
		} else {
		    sf.setScheda(new Dyn2Modellit());
		}
	    } else {
		sf.getScheda().setId(new PkId());
	    }
	} else {
	    sf.setScheda(new Dyn2Modellit());
	}
	List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	righe.add(riga);
	sf.setRighe(righe);
	prepareModel(model, request, reportIstanze);
	setPageAttributes(model);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STATISTICHE);
	return "report/reportIstanzePerSoftware";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String addCampoAScheda(Model model, @ModelAttribute("reportistanze") ReportIstanze reportIstanze, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = reportIstanze.getIstanzeFilter().getSchedaDinamicaFilter();
	//	if (sf.getScheda() != null) {
	//	    if (sf.getScheda().getId() != null) {
	//		if (sf.getScheda().getId().getCodice() != null) {
	//		    SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	//		    sf.getRighe().add(riga);
	//		}
	//	    }
	//	}
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	sf.getRighe().add(riga);
	prepareModel(model, request, reportIstanze);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STATISTICHE);
	setPageAttributes(model);
	return "report/reportIstanzePerSoftware";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String removeCampoScheda(@RequestParam(value = "idx", required = true) Integer idx, Model model,
	    @ModelAttribute("reportistanze") ReportIstanze reportIstanze, HttpServletRequest request) {

	// §§§BEGIN§§§
	SchedaDinamicaFilter sf = reportIstanze.getIstanzeFilter().getSchedaDinamicaFilter();
	if (sf != null) {
	    List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	    if (righe != null) {
		for (int i = 0; i < righe.size(); i++) {
		    if (i == idx.intValue()) {
			righe.remove(i);
			break;
		    }
		}
	    }
	    List<SchedaDinamicaRigheFilter> ricalcoloIndici = new ArrayList<SchedaDinamicaRigheFilter>(righe.size());
	    int i = 0;
	    for (SchedaDinamicaRigheFilter schedaDinamicaRigheFilter : righe) {
		ricalcoloIndici.add(i, schedaDinamicaRigheFilter);
		i++;
	    }
	    sf.setRighe(ricalcoloIndici);
	}
	prepareModel(model, request, reportIstanze);
	model.addAttribute("reportistanze", reportIstanze);
	model.addAttribute("statisticheOrStampe", STATISTICHE);
	setPageAttributes(model);
	return "report/reportIstanzePerSoftware";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void setConfigurazioneUtente(Model model, HttpServletRequest request) {

	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_CONC_AUT, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_LOCALIZZ, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SEARCH_DATI_PROGETTO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CERCAISTANZA_ALTRI_INDIRIZZI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORIDIMANENTO_ISTANZE, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPO_ORDINAMENTO_ISTANZE, "data", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VALORE_STATO_ISTANZA, "stato_tutte", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "0", request);
	model.addAttribute("CONF_UTENTE_LISTISTANZA_VISISTANZA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTISTANZA_VISISTANZA, "1", request)));
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    private String getBaseUrlFromAspnetBaseURL() {

	String result = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET);
	String aspNetApp = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.APP_ASPNET);
	if (StringUtils.isNotBlank(result)) {
	    if (StringUtils.isNotBlank(aspNetApp)) {
		result = result.replaceAll("/" + aspNetApp, "/");
	    }
	    if (result.endsWith("/")) {
		result = result.substring(0, result.lastIndexOf("/"));
	    }
	    return result;
	}
	return "";
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

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
    public String createReportGenerici(Model model, @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "codiceComune") String codiceComune, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	EsportazioniPentahoCommand esportazioniPentahoCommand = new EsportazioniPentahoCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	esportazioniPentahoCommand.setResponsabili(responsabile);
	List<Esportazioni> listaEsportazioni = new ArrayList<Esportazioni>();
	Esportazioni esportazioni = null;
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.GENERICI);
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
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.GENERICI, ids);
	    esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	}
	esportazioniPentahoCommand.setEsportazioni(esportazioni);
	popolaParametriEsportazione(esportazioni);
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("esportazioniPentahoCommand", esportazioniPentahoCommand);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiceComune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	return "report/exportGenerici";
    }

    @RequestMapping
    public void ajaxExportReportGenerici(@RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceComune") String codiceComune,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("esportazioniPentahoCommand") EsportazioniPentahoCommand esportazioniPentahoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportReportGenerici# Ricerca i posteggi e li salva sulla tabella TMP_ESPORTAZIONI ");
	salvaParametriEsportazione(esportazioniPentahoCommand.getEsportazioni());
	String sessionId = reportService.exportReportGenerico(esportazioniPentahoCommand.getEsportazioni(),
		StringUtils.defaultIfEmpty(emailResponsabile, ""), codiceComune);
	log.debug("exportReportGenerici# Invoco il link che attiva il job di pentaho");
	try {
	    Set<Parametriesportazione> parametriesportazione = esportazioniPentahoCommand.getEsportazioni().getParametriesportaziones();
	    Esportazioni e = esportazioniService.findById(new PkId(esportazioniPentahoCommand.getEsportazioni().getId().getIdcomune(),
		    esportazioniPentahoCommand.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(), parametriesportazione, sessionId, response);
	    if (isInviaMail == true && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    // Non è previsto codice comune per i posteggi
		    Integer _idAccount = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			_idAccount = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), _idAccount, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportReportGenerici# Mail/Tipo  non configurato ");
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

    @RequestMapping
    public String exportReportGenerici(@RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceComune") String codiceComune,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("esportazioniPentahoCommand") EsportazioniPentahoCommand esportazioniPentahoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportReportGenerici# Ricerca i posteggi e li salva sulla tabella TMP_ESPORTAZIONI ");
	salvaParametriEsportazione(esportazioniPentahoCommand.getEsportazioni());
	String sessionId = reportService.exportReportGenerico(esportazioniPentahoCommand.getEsportazioni(),
		StringUtils.defaultIfEmpty(emailResponsabile, ""), codiceComune);
	log.debug("exportReportGenerici# Invoco il link che attiva il job di pentaho");
	try {
	    Set<Parametriesportazione> parametriesportazione = esportazioniPentahoCommand.getEsportazioni().getParametriesportaziones();
	    Esportazioni e = esportazioniService.findById(new PkId(esportazioniPentahoCommand.getEsportazioni().getId().getIdcomune(),
		    esportazioniPentahoCommand.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(), parametriesportazione, sessionId, response);
	    if (isInviaMail == true && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    // Non è previsto codice comune per i posteggi
		    Integer _idAccount = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			_idAccount = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), _idAccount, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportReportGenerici# Mail/Tipo  non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione {}. Err: {}",
		    new Object[] { esportazioniPentahoCommand.getEsportazioni().getDescrizione(), e.getMessage() });
	    copyErrorsToFlashMessages(esportazioniPentahoCommand, false, "esportazioniPentahoCommand", e);
	    return "redirect:../report/createReportGenerici.htm";
	}
	List<Esportazioni> esportazionis = new ArrayList<Esportazioni>();
	esportazionis = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.GENERICI);
	model.addAttribute("esportazionis", esportazionis);
	this.createReportGenerici(model, codiceEsportazione, codiceComune, request, response);
	return "redirect:../report/createReportGenerici.htm";
    }

    private void popolaParametriEsportazione(Esportazioni esportazioni) {

	if (esportazioni == null) {
	    return;
	}
	Set<Parametriesportazione> parametriesportaziones = esportazioni.getParametriesportaziones();
	if (!parametriesportaziones.isEmpty()) {
	    // 
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    Map<String, String> ps = configurazioneutenteService.mapByResponsabile(r);
	    for (Parametriesportazione pe : parametriesportaziones) {
		pe.setValue(ps.get(getChiaveParametro(esportazioni.getId().getCodice(), pe.getParametro())));
	    }
	}
    }

    private String getChiaveParametro(Integer idEsportazione, String parametro) {

	return "RPT_" + idEsportazione + "_" + parametro;
    }

    private void salvaParametriEsportazione(Esportazioni esportazioni) {

	if (esportazioni == null) {
	    return;
	}
	Set<Parametriesportazione> parametriesportaziones = esportazioni.getParametriesportaziones();
	if (!parametriesportaziones.isEmpty()) {
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    for (Parametriesportazione pe : parametriesportaziones) {
		String chiaveParametro = getChiaveParametro(esportazioni.getId().getCodice(), pe.getParametro());
		Configurazioneutente c = configurazioneutenteService.findById(new ConfigurazioneutenteId(r.getId().getCodice(), chiaveParametro));
		if (c == null) {
		    c = new Configurazioneutente();
		    c.setId(new ConfigurazioneutenteId(r.getId().getCodice(), chiaveParametro));
		    c.setResponsabile(r);
		    c.setValore(pe.getValue());
		    configurazioneutenteService.insert(c);
		} else {
		    // 
		    c.setValore(pe.getValue());
		    configurazioneutenteService.update(c);
		}
	    }
	}
    }
}
