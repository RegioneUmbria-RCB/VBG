package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.validator.InvalidValue;
import org.jmesa.custom.DoubleNavigatorHtmlView;
import org.jmesa.custom.PECInboxSelectedRowRenderer;
import org.jmesa.customColumn.PECComputedCellRenderer;
import org.jmesa.customColumn.PECHilightedCellRenderer;
import org.jmesa.customColumn.PECLinkCellRenderer;
import org.jmesa.customColumn.PECReadUnreadCellRenderer;
import org.jmesa.customColumn.PECStatusCellEditor;
import org.jmesa.customColumn.PECStatusCellRenderer;
import org.jmesa.customColumn.SplittedStringCellRenderer;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Order;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.view.View;
import org.jmesa.view.component.Table;
import org.jmesa.view.editor.DateWithTimeCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.HtmlCellEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMail;
import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMailWSClient;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.AllegatoMailType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.DettaglioReportType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.FiltroType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ListaMessaggiRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ListaMessaggiResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.MessaggioType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ProcessaMessaggiRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ProcessaMessaggiResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaAllegatiMessaggioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaAllegatiMessaggioResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioBinarioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioBinarioResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.SetFlagLetturaMessaggioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.SetFlagLetturaMessaggioResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.TipoFiltroEnum;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxAllegati;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.TipiLocalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.MailConfigComparator;
import it.gruppoinit.pal.gp.core.domain.helper.PECAttachmentHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PecStatusHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ReportProcessamentoPecDTO;
import it.gruppoinit.pal.gp.core.domain.helper.SessioneElaborazionePEC;
import it.gruppoinit.pal.gp.core.domain.helper.SessioneElaborazionePEC.TipoSessioneElaborazionePEC;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzestradarioCommand;
import it.gruppoinit.pal.gp.core.domain.web.PECCommand;
import it.gruppoinit.pal.gp.core.domain.web.PECInboxFilter;
import it.gruppoinit.pal.gp.core.domain.web.PECInboxFilter.FiltroPECSiNoTuttiEnum;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterOrder;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.CatastoService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.TipiLocalizzazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoType;
import it.gruppoinit.pal.gp.core.service.impl.NlaHelperServiceImpl;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.CircoscrizioneType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.CoordinateType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.FrazioneType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.QuartiereType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.RiferimentoCatastaleType;
import it.init.sigepro.rte.types.RuoloType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;
import it.init.sigepro.rte.types.TipoLocalizzazioneType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Controller
public class PECInboxController extends BaseController<PecInbox> {

    private static final String SOFTWARE_ATTIVO_IN_SESSION = "_PECINBOX_software_attivo_in_session";
    private static final String PEC_ERROR_MESSAGE_SESSION_ATTR = "pecErrorMessage";
    private static final String CATASTO_EDILIZIO_URBANO = "Edilizio Urbano";
    private static final String CATASTO_TERRENI = "Terreni";
    private static final Logger log = LoggerFactory.getLogger(PECInboxController.class);
    //PORZIONE DI QUERYSTRING UTILIZZATA PER SEPARARE I PARAMETRI DI JMESA DAGLI ALTRI.
    public static final String JMESA_QUERYSTRING_SPLIT = "&F=F";
    public static final String ESITO_PROCESSAMENTO_PEC = "ESITO_PROCESSAMENTO_PEC";
    private static final Map<String, String> jMesaToHibernateMappings = new HashMap<String, String>();
    static {
	jMesaToHibernateMappings.put("mittentiString", "pecFrom");
	jMesaToHibernateMappings.put("destinatariString", "pecTo");
	jMesaToHibernateMappings.put("destinatariCCString", "pecToCC");
	jMesaToHibernateMappings.put("oggetto", "pecSubject");
	jMesaToHibernateMappings.put("dataRicezione", "pecDate");
	jMesaToHibernateMappings.put("letto", "flagLetta");
	jMesaToHibernateMappings.put("processato", "flagProcessata");
	jMesaToHibernateMappings.put("numeroProtocollo", "numeroprotocollo");
	jMesaToHibernateMappings.put("fkRespInEvidenza", "responsabileEvidenza.id.codice");
    }
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private CatastoService catastoService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private PecInboxService pecInboxService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private StcWsClient stcWsClient;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private NlaHelperService nlaHelperService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private StradariocoloreService stradariocoloreService;
    @Autowired
    private ProtocolloFlussoService protocolloFlussoService;
    @Autowired
    private TipiLocalizzazioniService tipiLocalizzazioniService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    private String SET_ENDO_IN_SESSION = "PEC_INBOX_ENDOSINSESSION";

    @RequestMapping
    public String inbox(Model model, @ModelAttribute(value = "pecInboxFilter") PECInboxFilter filter, BindingResult result,
	    @RequestParam(value = "idAccount", required = false) Integer idAccount,
	    @RequestParam(value = "resetFilter", required = false) Boolean resetFilter,
	    @RequestParam(value = "errorMessage", required = false) String errorMessage, HttpServletRequest request) throws Exception {

	// §§§BEGIN§§§
	// il software lo comanda il link che lancia la funzionalità
	// è possibile che da TT passo a SS e torni in questo caso devo settare TT
	if (request.getParameter(WebConstants.SOFTWARE) != null) {
	    request.getSession().setAttribute(SOFTWARE_ATTIVO_IN_SESSION, request.getParameter(WebConstants.SOFTWARE));
	} else {
	    // riporto il software settato nella session
	    // String softwareSettato = StringUtils.defaultString(((String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION)));
	    //if (softwareSettato.equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    // ricarica impostando il software a TT
	    String qs = populateQsForSoftwareTT(request);
	    return "redirect:inbox.htm?" + qs;
	    // }
	}
	populateFilter(request, filter, idAccount);
	//model.addAttribute("pecInboxFilter", filter);
	if (null != resetFilter && resetFilter.booleanValue()) {
	    filter.setDataRicezioneDa(null);
	    filter.setDataRicezioneA(null);
	    filter.setjMesaParamString("");
	    SessioneElaborazionePEC processingSession = (SessioneElaborazionePEC) request.getSession().getAttribute(ESITO_PROCESSAMENTO_PEC);
	    if (null != processingSession) {
		if (!processingSession.isRunning()) {
		    request.getSession().removeAttribute(ESITO_PROCESSAMENTO_PEC);
		}
	    }
	}
	String errMessage = (String) request.getSession().getAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR);
	if (StringUtils.isNotBlank(errMessage)) {
	    result.reject(errMessage, errMessage);
	    request.getSession().removeAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR);
	}
	setPageAttributes(model);
	// §§§END§§§
	return "pecinbox/inbox";
    }

    private String populateQsForSoftwareTT(HttpServletRequest request) throws UnsupportedEncodingException {

	String result = "FirstP=true";
	Map<String, String[]> parameters = request.getParameterMap();
	boolean softwarePresente = false;
	for (String parameter : parameters.keySet()) {
	    if (parameter.equals(WebConstants.SOFTWARE)) {
		softwarePresente = true;
		result += "&" +
			WebConstants.SOFTWARE +
			"=" +
			StringUtils.defaultString(((String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION)));
	    } else {
		String[] values = parameters.get(parameter);
		for (String val : values) {
		    result += "&" + parameter + "=" + URLEncoder.encode(val, "UTF-8");
		}
	    }
	}
	if (!softwarePresente) {
	    result += "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    StringUtils.defaultString(((String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION)));
	}
	return result;
    }

    @RequestMapping
    public ModelAndView ajaxSincronizzaPECInbox(@RequestParam(required = true, value = "account") String account,
	    @RequestParam(required = false, value = "idAccount") String idAccount, @RequestParam(required = false, value = "syncAll") Boolean syncAll,
	    HttpServletRequest request) throws Exception {

	ModelAndView mev = new ModelAndView();
	String errorMessage = null;
	SessioneElaborazionePEC processingSession = (SessioneElaborazionePEC) request.getSession().getAttribute(ESITO_PROCESSAMENTO_PEC);
	if (processingSession == null) {
	    processingSession = new SessioneElaborazionePEC();
	    processingSession.setTipoProcessamento(TipoSessioneElaborazionePEC.SINCRONIZZAZIONE);
	} else {
	    if (processingSession.isRunning()) {
		throw new RuntimeException(getMessageFromBundle("pecinbox.message.processamentoincorso", new Object[0]));
	    } else {
		processingSession = new SessioneElaborazionePEC();
		processingSession.setTipoProcessamento(TipoSessioneElaborazionePEC.SINCRONIZZAZIONE);
	    }
	}
	request.getSession().setAttribute(ESITO_PROCESSAMENTO_PEC, processingSession);
	Date lastSyncDate = null;
	if (!BooleanUtils.toBoolean(syncAll)) {
	    //recupero la data di ultima sincronizzazione
	    lastSyncDate = this.pecInboxService.getLastSynchronizationDate(account);
	}
	if (log.isDebugEnabled()) {
	    log.debug("ajaxSincronizzaPECInbox() - richiesta sincronizzazione casella PEC INBOX per account {} a partire dalla data {}",
		    new Object[] { account, lastSyncDate });
	}
	Map<String, Object> model = new HashMap<String, Object>();
	String mailWsAddress = getNlaGestioneMailWSURL();
	NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(mailWsAddress, this.verticalizzazioneParametriSistemaService);
	NlaGestioneMail pecWs = null;
	int inserted = -1;
	try {
	    pecWs = nlaGestioneMailWSClient.getWSPort();
	} catch (Exception e) {
	    errorMessage = getMessageFromBundle("pecinbox.message.nomailservice",
		    new Object[] { mailWsAddress, StringUtils.defaultString(e.getMessage()) });
	    processingSession.setErrore(errorMessage);
	    log.error("ajaxSincronizzaPECInbox() - errore nella connessione al WS NLAGestioneMail all'URL {}. {}", new Object[] { mailWsAddress, e });
	}
	if (null == errorMessage) {
	    ListaMessaggiResponse listResp = null;
	    ListaMessaggiRequest listReq = new ListaMessaggiRequest();
	    listReq.setToken(ORMHelper.getToken());
	    listReq.setSoftware(ORMHelper.getSoftware());
	    if (idAccount != null) {
		listReq.setIdaccount(new BigInteger(idAccount.toString()));
	    }
	    //if(pecInboxFilter.isReadFromServer()){}
	    List<FiltroType> filtri = listReq.getListaFiltri();
	    filtri.addAll(buildListaFiltriForSynchronization(lastSyncDate));
	    if (log.isDebugEnabled()) {
		log.debug("ajaxSincronizzaPECInbox() - invocazione del WS listaMessaggi all'indirizzo {}",
			new String[] { getNlaGestioneMailWSURL() });
	    }
	    try {
		processingSession.setRunning(true);
		listResp = pecWs.listaMessaggi(listReq);
	    } catch (Exception e) {
		errorMessage = getMessageFromBundle("pecinbox.message.mailserviceerror",
			new Object[] { mailWsAddress, StringUtils.defaultString(e.getMessage()) });
		log.error("ajaxSincronizzaPECInbox - errore nella chiamata al WS NLAGestioneMail all'URL {}. {}", new Object[] { mailWsAddress, e });
		processingSession.setRunning(false);
		processingSession.setErrore(errorMessage);
	    }
	    if (null == errorMessage) {
		inserted = 0;
		ReportProcessamentoPecDTO pecProcessingResult = new ReportProcessamentoPecDTO();
		List<MessaggioType> wsMessages = listResp.getMessaggio();
		if (log.isDebugEnabled()) {
		    log.debug("ajaxSincronizzaPECInbox - invocazione del WS listaMessaggi: trovati {} messaggi PEC", wsMessages.size());
		}
		List<String> insertErrors = new ArrayList<String>();
		for (MessaggioType wsmsg : wsMessages) {
		    PecInbox pecRec = null;
		    String dbError = null;
		    try {
			pecRec = elaboraMessaggioPEC(wsmsg, account, new Integer(listReq.getIdaccount().intValue()));
			if (pecRec != null) {
			    inserted++;
			}
		    } catch (Exception e) {
			log.error("ajaxSincronizzaPECInbox - errore nell'elaborazione del messaggio PEC {}: {}",
				new Object[] { wsmsg.getIdentificativo(), e });
			dbError = getMessageFromBundle("pecinbox.message.pecinserterror", new Object[] { wsmsg.getIdentificativo(), e.getMessage() });
			insertErrors.add(dbError);
		    }
		}
		processingSession.setRunning(false);
		//request.getSession().removeAttribute(ESITO_PROCESSAMENTO_PEC);
		pecProcessingResult.setErroriPec(insertErrors);
		if (insertErrors.size() > 0) {
		    errorMessage = getMessageFromBundle("pecinbox.message.pecinserterrors", new Object[] { insertErrors.size() });
		    pecProcessingResult.setErrore(errorMessage);
		}
		pecProcessingResult
			.setMessaggio(getMessageFromBundle("pecinbox.message.esitosincronizzazione", new Object[] { new Integer(inserted) }));
		processingSession.setEsitoProcessamento(pecProcessingResult);
		model.put("data", pecProcessingResult);
	    } else {
		model.put("error", errorMessage);
	    }
	}
	mev.setViewName("jsonView");
	mev.addAllObjects(model);
	return mev;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public void ajaxPECList(Model model, @ModelAttribute("pecInboxFilter") PECInboxFilter pecInboxFilter,
	    @RequestParam(required = false, value = "clearSelection") Boolean clearSelection,
	    @RequestParam(required = false, value = "idAccount") Integer idAccount, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	// §§§BEGIN§§§
	String errorMessage = null;
	//List<String> warnings = new ArrayList<String>();
	List<PECMessageHelper> messages = new ArrayList<PECMessageHelper>();
	saveFilter(pecInboxFilter, request);
	// setto l'id per cui sto richiesto la lista delle pec ricevute
	pecInboxFilter.setIdMailConfig(idAccount);
	// recupero IN_LOGINNAME la ricerca sarà fatta per questo parametro 
	// questo ci permette di recuperare la lista salvata su pec_inbox indipendetemente per l'account salvato
	// Es. per TT è salvato l'account con in_loginname --> aruba --> id account 1
	//     per CO è salvato l'account con in_loginname --> aruba --> id account 2
	// Per qualsiasi valore id account [1,2] vegono scaricati poi sulla lista saranno recuperati per IN_LOGINNAME.
	// Questo comporta che se scarico le pec per id account 1 la lista di queste pec saranno viste nel pannello pec
	// sia di TT che di CO perchè condividono lo stesso account
	MailConfig mfg = mailConfigService.findById(new PkId(idAccount));
	pecInboxFilter.setMailAccount(mfg.getInLoginname());
	//ripetendo la ricerca viene resettata la riga selezionata se richiesto 
	if (BooleanUtils.isTrue(clearSelection)) {
	    request.getSession().removeAttribute(PECInboxSelectedRowRenderer.SELECTED_PEC_SESSION_ATTR);
	}
	//inizio a creare i TableFacade di jMesa perchè mi serve per parsare i criteri di filtro di jMesa
	String tableId = "inbox_list";
	TableFacade tableFacade = TableFacadeFactory.createTableFacade(tableId, request);
	//tableFacade.setEditable(true);
	int totalRows = 0;
	if (log.isDebugEnabled()) {
	    log.debug("ajaxPECList - lettura dei messaggi PEC dal DB in corso");
	}
	FilterSet jmFilters = tableFacade.getLimit().getFilterSet();
	String mittentiFilter = "";
	String oggettoFilter = "";
	String protocolloFilter = "";
	String destCCString = "";
	String destinatarioString = "";
	Filter f = jmFilters.getFilter("mittentiString");
	if (f != null) {
	    mittentiFilter = f.getValue();
	    pecInboxFilter.setMittente(mittentiFilter);
	}
	f = jmFilters.getFilter("oggetto");
	if (f != null) {
	    oggettoFilter = f.getValue();
	    pecInboxFilter.setOggetto(oggettoFilter);
	}
	f = jmFilters.getFilter("numeroProtocollo");
	if (f != null) {
	    protocolloFilter = f.getValue();
	    pecInboxFilter.setNumeroProtocollo(protocolloFilter);
	}
	f = jmFilters.getFilter("destinatariCCString");
	if (f != null) {
	    destCCString = f.getValue();
	    pecInboxFilter.setDestinatarioCC(destCCString);
	}
	f = jmFilters.getFilter("destinatariString");
	if (f != null) {
	    destinatarioString = f.getValue();
	    pecInboxFilter.setDestinatario(destinatarioString);
	}
	List<FilterOrder> sortByTemp = new ArrayList<FilterOrder>(5);
	SortSet sortSet = tableFacade.getLimit().getSortSet();
	if (sortSet != null) {
	    Iterator<String> jMesaAttrsIterator = jMesaToHibernateMappings.keySet().iterator();
	    while (jMesaAttrsIterator.hasNext()) {
		String jMesaAttr = jMesaAttrsIterator.next();
		String hibernateAttr = jMesaToHibernateMappings.get(jMesaAttr);
		if (StringUtils.isNotBlank(hibernateAttr)) {
		    Sort sort = sortSet.getSort(jMesaAttr);
		    if (sort != null && sort.getOrder() != null && !sort.getOrder().equals(Order.NONE)) {
			FilterOrder orderBy = null;
			if (sort.getOrder().equals(Order.ASC)) {
			    orderBy = FilterUtils.orderAsc(hibernateAttr);
			} else {
			    orderBy = FilterUtils.orderDesc(hibernateAttr);
			}
			int ordinal = sort.getOrder().ordinal();
			while (sortByTemp.size() < ordinal) {
			    sortByTemp.add(null);
			}
			sortByTemp.add(ordinal, orderBy);
		    }
		} else if (log.isDebugEnabled()) {
		    log.debug("ajaxPECList - imossibile individuare l'attributo di PecInbox mappato sulla colonna jMesa {}.", jMesaAttr);
		}
	    }
	}
	//elimino valori null dalla lista degli oder by
	List<FilterOrder> sortBy = new ArrayList<FilterOrder>(5);
	for (FilterOrder filterOrder : sortByTemp) {
	    if (null != filterOrder) {
		sortBy.add(filterOrder);
	    }
	}
	totalRows = this.pecInboxService.countByDataRicezioneFlagLettaMittenteOggettoProtocollo(pecInboxFilter);
	tableFacade.setTotalRows(totalRows);
	int firstRowIndex = 0;
	int rowsPerPage = 100;
	RowSelect rowSelect = tableFacade.getLimit().getRowSelect();
	if (null != rowSelect) {
	    firstRowIndex = rowSelect.getRowStart();
	    rowsPerPage = rowSelect.getMaxRows();
	}
	List<PecInbox> pecs = null;
	try {
	    pecs = this.pecInboxService.findByDataRicezioneFlagLettaMittenteOggettoProtocolloAndSort(pecInboxFilter, sortBy, firstRowIndex,
		    rowsPerPage);
	    if (log.isDebugEnabled()) {
		log.debug("ajaxPECList - letti {} messaggi PEC dal DB.", new Object[] { pecs.size() });
	    }
	    for (PecInbox pec : pecs) {
		PECMessageHelper pech = populatePECHelper(null, pec);
		messages.add(pech);
	    }
	} catch (Exception e) {
	    StringBuilder sbErr = new StringBuilder("<span class='error'>errore durante la lettura dei messaggi PEC dal DB:");
	    sbErr.append(".<br/>Dettaglio errore: ").append(e.getMessage()).append("</span>");
	    errorMessage = sbErr.toString();
	    log.error("ajaxPECList - errore nella lettura dei messaggi PEC dal DB", e);
	}
	if (errorMessage == null) {
	    try {
		Responsabili operatore = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
		//verifico se la verticalizzazione  PROTOCOLLO_ATTIVO sia attiva, metto l'informazione nella request ad uso del renderer di JMesa
		Boolean isProtocolloAttivo = this.verticalizzazioniService
			.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
		request.setAttribute(PECStatusCellRenderer.ATTIVA_PROTOCOLLO_REQUEST_ATTRIBUTE, isProtocolloAttivo);
		//imposto l'operatore corrente come attributo della request ad uso del cell renderer di JMesa.
		//inizializzo nell'istanza di Responsabili il Set di ProtocolloFlussos che serve al renderer dopo la chiusura della session di hibernate
		List<ProtocolloFlusso> flussi = this.protocolloFlussoService.findByResponsabile(operatore, new ArrayList<String>());
		operatore.setProtocolloFlussos(new HashSet<ProtocolloFlusso>(flussi));
		request.setAttribute(PECStatusCellRenderer.OPERATORE_REQUEST_ATTRIBUTE, operatore);
		//verifico se il parametro verticalizzazione GEST_CANCELLAZIONI.PASSWORD_SBLOCCO_PEC è attivo e valorizzato, metto l'informazione nella request ad uso del renderer di JMesa
		Boolean sbloccoAttivo = StringUtils.isNotEmpty(this.verticalizzazioniService.getVerticalizzazioniparametriValore(
			WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SBLOCCO_PEC));
		request.setAttribute(PECStatusCellRenderer.ATTIVA_PASSWORD_SBLOCCO_PEC, sbloccoAttivo);
		//tableFacade.getLimit().getRowSelect().
		tableFacade.setColumnProperties("mittentiString", "destinatariString", "destinatariCCString", "oggetto", "dataRicezione",
			"descrizioneAccountMailCfg", "letto", "processato", "fkRespInEvidenza", "numeroProtocollo", "status");
		tableFacade.setItems(messages);
		Table table = tableFacade.getTable();
		View view = new DoubleNavigatorHtmlView();
		tableFacade.setView(view);
		table.setCaption(Utilities.getMessageFromBundle(context, "pecinbox.list.title",
			new Object[] { pecInboxFilter.getDescMailConfigDefault(), pecInboxFilter.getMailAccount() }));
		//implementare row renderer che evidenzi l'ultima PEC lavorata dall'utente
		PECInboxSelectedRowRenderer rowRenderer = new PECInboxSelectedRowRenderer();
		rowRenderer.setSelectedRowClass("active-pec");
		table.getRow().setRowRenderer(rowRenderer);
		//table.setRow(new HtmlRowImpl());
		//colonna mittenti
		HtmlColumn col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("mittentiString");
		//recupero etichette da MessageBundle
		SplittedStringCellRenderer fromAddressCellRenderer = new SplittedStringCellRenderer();
		fromAddressCellRenderer.setSeparator(PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
		col.setTitle(Utilities.getMessageFromBundle(context, "label.da", null));
		col.setCellRenderer(fromAddressCellRenderer);
		col.getCellRenderer().setCellEditor(new HtmlCellEditor());
		col.setFilterable(true);
		col.setWidth("12%");
		//colonna destinatari
		SplittedStringCellRenderer toMittentiAddressCellRenderer = new SplittedStringCellRenderer();
		toMittentiAddressCellRenderer.setSeparator(PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("destinatariString");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.destinatari", null));
		col.setCellRenderer(toMittentiAddressCellRenderer);
		col.getCellRenderer().setCellEditor(new HtmlCellEditor());
		col.setFilterable(true);
		col.setWidth("12%");
		//colonna destinatari CC
		SplittedStringCellRenderer toccAddressCellRenderer = new SplittedStringCellRenderer();
		toccAddressCellRenderer.setSeparator(PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("destinatariCCString");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.destinataricc", null));
		col.setCellRenderer(toccAddressCellRenderer);
		col.getCellRenderer().setCellEditor(new HtmlCellEditor());
		col.setFilterable(true);
		col.setWidth("12%");
		//colonna oggetto
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("oggetto");
		col.setCellRenderer(new PECLinkCellRenderer());
		col.getCellRenderer().setCellEditor(new HtmlCellEditor());
		col.setTitle(Utilities.getMessageFromBundle(context, "label.oggetto", null));
		col.setFilterable(true);
		//col.setWidth("20%");
		//colonna data ricezione
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("dataRicezione");
		col.setTitle(Utilities.getMessageFromBundle(context, "label.data", null));
		col.setWidth("10%");
		col.setFilterable(false);
		col.getCellRenderer().setCellEditor(new DateWithTimeCellEditor(WebConstants.DATE_WITH_TIME_SEC_FORMAT_PATTERN));
		// Colonna account
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("descrizioneAccountMailCfg");
		col.setTitle(Utilities.getMessageFromBundle(context, "label.account_mail_cfg", null));
		col.setWidth("10%");
		col.setFilterable(false);
		//colonna letto/non letto
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("letto");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.letto", null));
		col.getCellRenderer().setCellEditor(new PECReadUnreadCellRenderer());
		col.setFilterable(false);
		col.setWidth("4%");
		//colonna elaborato/non elaborato
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("processato");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.processato", null));
		col.getCellRenderer().setCellEditor(new PECComputedCellRenderer());
		col.setFilterable(false);
		col.setWidth("3%");
		//colonna in evidenza
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("fkRespInEvidenza");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.inevidenza", null));
		col.getCellRenderer().setCellEditor(new PECHilightedCellRenderer());
		col.setFilterable(false);
		col.setWidth("3%");
		//colonna numero protocollo
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("numeroProtocollo");
		col.setTitle(Utilities.getMessageFromBundle(context, "pecinbox.label.numeroprotocollo", null));
		//col.getCellRenderer().setCellEditor(new PECComputedCellRenderer());
		col.setFilterable(true);
		col.setWidth("6%");
		//colonna stato
		col = (HtmlColumn) tableFacade.getTable().getRow().getColumn("status");
		col.setTitle(Utilities.getMessageFromBundle(context, "label.azioni", null));
		col.setWidth("10%");
		col.setCellRenderer(new PECStatusCellRenderer());
		col.getCellRenderer().setCellEditor(new PECStatusCellEditor());
		col.setFilterable(Boolean.FALSE);
		col.setSortable(Boolean.FALSE);
	    } catch (Exception e) {
		StringBuilder sbErr = new StringBuilder(
			"<span class='error'>errore durante l'elaborazione dei messaggi di posta elettronica certificata.");
		sbErr.append(".<br/>Dettaglio errore: ").append(e.getMessage()).append("</span>");
		errorMessage = sbErr.toString();
		log.error("ajaxPECList() - errore durante l'eleborazione dei messaggi PEC ricevuti. {}", new Object[] { e });
	    }
	}
	String htmlOut = StringUtils.isNotEmpty(errorMessage) ? errorMessage : tableFacade.render();
	byte[] bytes = htmlOut.getBytes("UTF-8");
	response.setContentLength(bytes.length);
	response.setContentType("text/html");
	ServletOutputStream os = response.getOutputStream();
	os.write(bytes);
	os.flush();
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxSegnaPecNonCancellata(@RequestParam("identificativo") String identificativo, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	PecInboxId id = new PecInboxId(identificativo);
	PecInbox pecInbox = pecInboxService.findById(id);
	if (pecInbox == null || StringUtils.isBlank(pecInbox.getId().getId())) {
	    response.setContentType("text/plain");
	    response.getWriter().write("Attenzione! Impossibile trovare la Pec per l'identificativo passato: " + identificativo);
	}
	try {
	    pecInbox.setFlagCancellata(Boolean.FALSE);
	    pecInboxService.update(pecInbox);
	} catch (Exception e) {
	    log.error(
		    "Attenzione durante l'aggiornamento del flag cancellata per la PEC codice identificativo: {} si è verificato il seguente errore : {} ",
		    new Object[] { identificativo, e.getMessage() });
	    response.getWriter()
		    .write("Attenzione durante l'aggiornamento del flag cancellata per la PEC codice identificativo: " +
			    identificativo +
			    " si è verificato il seguente errore " +
			    e.getMessage());
	}
    }

    @RequestMapping
    public ModelAndView dettaglioPEC(Model model, @ModelAttribute(value = "pecCommand") PECCommand cmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec, HttpServletRequest request) {

	ModelAndView mev = new ModelAndView();
	// §§§BEGIN§§§
	String errorMessage = null;
	if (null == cmd) {
	    cmd = new PECCommand();
	}
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = pecInboxService.findById(pecId);
	cmd.setPec(pec);
	if (pec != null) {
	    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
	    errorMessage = scaricaPec(cmd, null, result);
	    setPECSelected(pec, request);
	    String provenienza = StringUtils.defaultIfEmpty(request.getParameter("provenienza"), "");
	    if (provenienza.equalsIgnoreCase("movimenti")) {
		if (pec.getMovimenti() != null) {
		    String ret = "../movimenti/view.htm?codice=" +
			    pec.getMovimenti().getId().getCodice() +
			    "&software=" +
			    pec.getMovimenti().getIstanza().getSoftware().getCodice();
		    mev.addObject("returnToExternal", ret);
		}
	    } else if (provenienza.equalsIgnoreCase("istanze")) {
		if (pec.getIstanze() != null) {
		    String ret = "../istanze/view.htm?codice=" +
			    pec.getIstanze().getId().getCodice() +
			    "&software=" +
			    pec.getIstanze().getSoftware().getCodice();
		    mev.addObject("returnToExternal", ret);
		}
	    }
	} else {
	    errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    log.error("dettaglioPEC() - " + errorMessage);
	}
	if (StringUtils.isNotEmpty(errorMessage)) {
	    mev.addObject("error", errorMessage);
	}
	mev.setViewName("pecinbox/dettaglioPEC");
	// §§§END§§§
	return mev;
    }

    @RequestMapping
    public void ajaxLeggiCorpoPEC(@RequestParam(value = "codicePec", required = true) String codicePec, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String errorMessage = null;
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = pecInboxService.findById(pecId);
	PECCommand cmd = new PECCommand();
	cmd.setPec(pec);
	if (pec != null) {
	    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
	    errorMessage = scaricaPec(cmd, null, null);
	} else {
	    errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    log.error("ajaxLeggiCorpoPEC() - " + errorMessage);
	}
	if (StringUtils.isNotEmpty(errorMessage)) {
	    response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, errorMessage);
	} else {
	    byte[] htmlBytes = cmd.getCorpo().getBinaryContent();
	    //htmlBytes = "Corpo della PEC di testo semplice con ritorni a capo\r\nseconda riga.".getBytes();
	    response.setContentType("text/html");
	    response.setContentLength(htmlBytes.length);
	    response.getOutputStream().write(htmlBytes);
	    response.getOutputStream().flush();
	}
    }

    @RequestMapping
    public void ajaxScaricaPEC(@RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "idAccount", required = false) String idAccount, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String errorMessage = null;
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = pecInboxService.findById(pecId);
	PECCommand cmd = new PECCommand();
	cmd.setPec(pec);
	if (pec != null) {
	    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
	    errorMessage = scaricaPec(cmd, new BigInteger(idAccount), null);
	} else {
	    errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    log.error("ajaxScaricaPEC() - " + errorMessage);
	}
	byte[] htmlBytes = "OK".getBytes();
	if (StringUtils.isNotEmpty(errorMessage)) {
	    htmlBytes = errorMessage.getBytes();
	}
	response.setContentType("text/plain");
	response.setContentLength(htmlBytes.length);
	response.getOutputStream().write(htmlBytes);
	response.getOutputStream().flush();
    }

    @RequestMapping
    public String rilasciaPEC(Model model, @ModelAttribute(value = "pecCommand") PECCommand cmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec, @RequestParam(value = "pwd", required = true) String pwd,
	    HttpServletRequest request) {

	// §§§BEGIN§§§
	String errorMessage = null;
	if (null == cmd) {
	    cmd = new PECCommand();
	}
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = pecInboxService.findById(pecId);
	cmd.setPec(pec);
	if (pec != null) {
	    Responsabili utenteConnesso = getCurrentlyAuthenticatedUserDetails();
	    if (utenteConnesso != null) {
		if (pec.getResponsabili() != null) {
		    if (utenteConnesso.getId().getCodice().equals(pec.getResponsabili().getId().getCodice())) {
			pec.setResponsabili(null);
			pecInboxService.update(pec);
			FlashMessages.getInfos().add(getMessageFromBundle("02", new Object[0]));
		    } else {
			//l'utente connesso non è il responsabile che ha in carico la PEC => richiesta password come da verticalizzazione
			if (StringUtils.isNotBlank(pwd)) {
			    boolean validPwd = this.verticalizzazioniService.isAttivaAndParametroEqualsToValore(
				    WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI,
				    WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SBLOCCO_PEC, pwd);
			    if (validPwd) {
				pec.setResponsabili(null);
				pecInboxService.update(pec);
				FlashMessages.getInfos().add(getMessageFromBundle("02", new Object[0]));
			    } else {
				errorMessage = getMessageFromBundle("pecinbox.message.passwordnonvalida", new Object[0]);
				FlashMessages.getWarnings().add(errorMessage);
			    }
			} else {
			    errorMessage = getMessageFromBundle("pecinbox.message.passwordnonvalida", new Object[0]);
			    FlashMessages.getWarnings().add(errorMessage);
			}
		    }
		}
	    }
	} else {
	    errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    log.error("rilasciaPEC() - " + errorMessage);
	    //FlashMessages.getWarnings().add(errorMessage);
	    request.getSession().setAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR, errorMessage);
	}
	// §§§END§§§
	return "redirect:inbox.htm";
    }

    @RequestMapping
    public ModelAndView istanzaDaPEC(Model model, @ModelAttribute(value = "istanzaPecCommand") PECCommand cmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "idAccount", required = false) String idAccount, HttpServletRequest request) {

	ModelAndView mev = new ModelAndView();
	String viewName = "pecinbox/creaIstanza";
	// §§§BEGIN§§§
	String errorMessage = null;
	if (null == cmd) {
	    cmd = new PECCommand();
	}
	BigInteger _idAccount = null;
	if (StringUtils.isNotBlank(idAccount)) {
	    _idAccount = new BigInteger(idAccount);
	}
	PecInbox pec = null;
	try {
	    pec = pecInboxService.lockPecInboxAssegnaResponsabileIstanzaOMovimento(codicePec);
	    cmd.setNumeroProtocollo(pec.getNumeroprotocollo());
	    cmd.setDataProtocollo(pec.getDataprotocollo());
	    cmd.setPec(pec);
	} catch (Exception e) {
	    errorMessage = e.getMessage();
	}
	//predispongo nel command l'opzione utente 'scompatta allegati'
	String scompattaString = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI, "true", request);
	boolean scompattaAllegati = BooleanUtils.toBoolean(scompattaString);
	cmd.setScompattaAllegati(scompattaAllegati);
	if (pec != null) {
	    setPECSelected(pec, request);
	    //carico le impostazioni di configurazione per definire il layout della scheda dell'istanza
	    Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId());
	    if (configurazione == null) {
		throw new InvalidConfigurationException("Nessuna configurazione trovata per il software [" + ORMHelper.getSoftware() + "]");
	    }
	    model.addAttribute("configurazione", configurazione);
	    //carico l'istanza
	    Istanze inst = pec.getIstanze();
	    //se la pec non è associata a nessun'istanza
	    if (null == inst || null == inst.getId() || null == inst.getId().getCodice()) {
		//e se non è associata a nessun movimento
		Movimenti mov = pec.getMovimenti();
		if (mov == null || mov.getId() == null || mov.getId().getCodice() == null) {
		    //predispongo i dati iniziali dell'istanza in base a quelli presenti nella PEC.
		    inst = populateIstanzaFromPEC(pec);
		    cmd.setIstanza(inst);
		}
		//se è associata già ad un movimento non deve essere consentita la creazione di un'istanza
		else {
		    errorMessage = getMessageFromBundle("pecinbox.message.erroremovimentopresente", new Object[] { mov.getMovimento() });
		}
	    } else {
		//se la PEC è già associata ad un'istanza l'utente viene reindirizzato alla pagina di creazione istanza in sola lettura
		if (log.isDebugEnabled()) {
		    log.debug("istanzaDaPEC() - la pec con codice {} è associata all'istanza {}. Lettura dei dati dell'istanza dal DB in corso.",
			    new Object[] { pec.getId().getId(), inst.getId().getCodice() });
		}
		inst = istanzeService.findById(new PkId(inst.getId().getCodice()));
		//}
		cmd.setIstanza(inst);
		if (inst.getTitolarelegale() == null) {
		    inst.setTitolarelegale(new Anagrafe());
		}
		if (inst.getTipisoggetto() == null) {
		    inst.setTipisoggetto(new Tipisoggetto());
		}
		cmd.setReadOnly(true);
	    }
	    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
	    errorMessage = scaricaPec(cmd, _idAccount, result);
	    //cmd.setAllegati(allegati);
	    //mev.addObject("istanzaPecCommand", cmd);
	} else {
	    if (StringUtils.isEmpty(errorMessage)) {
		errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
		log.error("istanzaDaPEC() - " + errorMessage);
	    }
	}
	if (StringUtils.isNotEmpty(errorMessage)) {
	    request.getSession().setAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR, errorMessage);
	    viewName = "redirect:inbox.htm";
	}
	setModelLocalizzazione(model);
	mev.setViewName(viewName);
	// §§§END§§§
	return mev;
    }

    @RequestMapping
    public String leggiProtocollo(Model model, @ModelAttribute(value = "protocolloCommand") ProtocollazioneCommand protCmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune,
	    @RequestParam(value = "pSoftware", required = false) String pSoftware, HttpServletRequest request) {

	// IL SOFTWARE ED IL CODICE COMUNE LO DESUMO DAI DATI PRESENTI IN PEC_INBOX
	// SE NON PRESENTI O L'ISTANZA O IL MOVIMENTO SONO PROTOCOLLATI ED HANNO LO STESSO NUMERO/DATA DI PEC_INBOX
	// PRENDO IL CODICE COMUNE / SOFTWARE DA QUESTI
	protCmd = new ProtocollazioneCommand();
	PecInbox pec = pecInboxService.findById(new PecInboxId(codicePec));
	boolean done = false;
	if (pec.getSoftwareProt() != null) {
	    protCmd.setComune(pec.getComuniProt());
	    protCmd.setProtSoftware(pec.getSoftwareProt());
	    done = true;
	}
	if (!done) {
	    String numProt = StringUtils.defaultString(pec.getNumeroprotocollo());
	    String dataProt = pec.getDataprotocollo() == null ? "" : Utilities.formatDate(pec.getDataprotocollo(), false);
	    Istanze i = pec.getIstanze();
	    String numprotIM = "";
	    String dataprotIM = "";
	    if (i != null) {
		numprotIM = StringUtils.defaultString(i.getNumeroprotocollo());
		dataprotIM = i.getDataprotocollo() == null ? "" : Utilities.formatDate(i.getDataprotocollo(), false);
	    } else {
		if (pec.getMovimenti() != null) {
		    i = pec.getMovimenti().getIstanza();
		    numprotIM = StringUtils.defaultString(pec.getMovimenti().getNumeroprotocollo());
		    dataprotIM = pec.getMovimenti().getDataprotocollo() == null ? ""
			    : Utilities.formatDate(pec.getMovimenti().getDataprotocollo(), false);
		}
	    }
	    if (StringUtils.isNotBlank(numprotIM) && StringUtils.isNotBlank(dataprotIM)) {
		if (numProt.equalsIgnoreCase(numprotIM) && dataProt.equalsIgnoreCase(dataprotIM)) {
		    // che il protocollo del movimento o dell'istanza siano gli stessi della pec 
		    // il software e il comune lo desumo dall'istanza
		    pSoftware = i.getSoftware().getCodice();
		    codiceComune = i.getComune().getCodicecomune();
		}
	    }
	    if (StringUtils.isNotBlank(pSoftware) && StringUtils.isNotBlank(codiceComune)) {
		Software s = softwareService.findById(pSoftware);
		Comuni c = comuniService.findById(codiceComune);
		protCmd.setComune(c);
		protCmd.setProtSoftware(s);
	    } else {
		List<VerticalizzazioniconfigurazioniHelper> helpers = verticalizzazioniService.findListaConfigurazioniPerComuneESoftware(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO);
		if (helpers.size() > 1) {
		    model.addAttribute("configurazioniVerticalizzazionis", helpers);
		    model.addAttribute("isLeggiProtocollo", Boolean.TRUE);
		    // più configurazioni di protocollo indico all'operatore di scegliere quella più adatta
		    return "pecinbox/sceltaCofinfigurazioneProtocollo";
		} else {
		    Software s = softwareService.findById(ORMHelper.getSoftware());
		    List<Comuniassociati> list = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
		    Comuni c = null;
		    if (list.size() > 0) {
			c = list.get(0).getComune(); // prendo il primo dovrebbe essere indifferente se una sola configurazione
		    } else {
			// errore
			throw new InvalidConfigurationException("Nessun record nella tabella comuniassociati.");
		    }
		    protCmd.setComune(c);
		    protCmd.setProtSoftware(s);
		}
	    }
	}
	return "redirect:../protocollazione/leggiProtocollo.htm?codicePEC=" + codicePec + "#ancora_pec_id_" + codicePec;
    }

    @SuppressWarnings("unused")
    @RequestMapping
    public String protocolloDaPEC(Model model, @ModelAttribute(value = "protocolloCommand") ProtocollazioneCommand protCmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "codiceComune", required = false) String codiceComune,
	    @RequestParam(value = "pSoftware", required = false) String pSoftware,
	    @RequestParam(value = "idAccount", required = false) String idAccount, HttpServletRequest request) {

	PECCommand cmd = new PECCommand();
	// §§§BEGIN§§§
	BigInteger _idAccount = null;
	if (StringUtils.isNotBlank(idAccount)) {
	    _idAccount = new BigInteger(idAccount);
	}
	model.addAttribute("_idAccount", _idAccount);
	String errorMessage = null;
	String _codicePec = codicePec;
	try {
	    _codicePec = URLEncoder.encode(codicePec, "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    log.error("Impossibile fare l'encodig della stringa {}: {}[{}]. ", new Object[] { codicePec, e1.getMessage(), e1 });
	    log.error(
		    "Per le ricerche che utilizzano il 'codicePec' verrà utilizzazione {} potrebbe creare errori nel caso contenesse caratteri speciali",
		    codicePec);
	}
	String protocolloView = "redirect:../protocollazione/create.htm?provenienza=P&codicePEC=" + _codicePec;
	PecInbox pec = null;
	try {
	    pec = pecInboxService.lockPecInboxAssegnaResponsabileProtocollo(codicePec);
	    cmd.setPec(pec);
	} catch (RuntimeException re) {
	    errorMessage = re.getMessage();
	}
	if (pec != null) {
	    setPECSelected(pec, request);
	    String prot = pec.getNumeroprotocollo();
	    if (!StringUtils.isEmpty(prot)) {
		if (log.isDebugEnabled()) {
		    log.debug("protocolloDaPEC# pec ha già il protocollo assegnato {}", prot);
		}
		protocolloView = "redirect:../protocollazione/leggiProtocollo.htm?codicePEC=" + codicePec;
	    } else {
		/*
		 * se la pec è associata ad un'istanza o un movimento verifico se esiste il protocollo 
		 * dell'istanza o del movimento. Se esiste reindirizzo l'utente alla letture del protocollo che ho trovato.
		 * In realtà protocollando una Istanza o un Movimento collegati ad una PEC 
		 * il riferimento del protocollo dovrebbe essere già riportato anche sulla PEC da cui provengono l'istanza o il movimento
		 */
		if (log.isDebugEnabled()) {
		    log.debug("protocolloDaPEC# verifico che non sia già assegnata ad una istanza o movimento");
		}
		Istanze istanzaPec = pec.getIstanze();
		if (null != istanzaPec && null != istanzaPec.getId() && null != istanzaPec.getId().getCodice()) {
		    if (log.isDebugEnabled()) {
			log.debug("protocolloDaPEC# assegnata ad istanza {}", istanzaPec.toString());
		    }
		    prot = istanzaPec.getNumeroprotocollo();
		    if (!StringUtils.isEmpty(prot)) {
			protocolloView = "redirect:../protocollazione/leggiProtocollo.htm?codiceIstanza=" + istanzaPec.getId().getCodice();
		    }
		} else {
		    Movimenti movimentoPec = pec.getMovimenti();
		    if (null != movimentoPec && null != movimentoPec.getId() && null != movimentoPec.getId().getCodice()) {
			if (log.isDebugEnabled()) {
			    log.debug("protocolloDaPEC# assegnata a movimento {}", movimentoPec.getId());
			}
			prot = movimentoPec.getNumeroprotocollo();
			if (!StringUtils.isEmpty(prot)) {
			    protocolloView = "redirect:../protocollazione/leggiProtocollo.htm?codiceMovimento=" + movimentoPec.getId().getCodice();
			}
		    }
		}
	    }
	    if (StringUtils.isEmpty(prot)) {
		// controllare se protocollo attivo e se configurazione multipla
		if (StringUtils.isNotBlank(pSoftware) && StringUtils.isNotBlank(codiceComune)) {
		    Software s = softwareService.findById(pSoftware);
		    Comuni c = comuniService.findById(codiceComune);
		    protCmd.setComune(c);
		    protCmd.setProtSoftware(s);
		    pec.setComuniProt(c);
		    pec.setSoftwareProt(s);
		    pecInboxService.update(pec);
		} else {
		    List<VerticalizzazioniconfigurazioniHelper> helpers = verticalizzazioniService.findListaConfigurazioniPerComuneESoftware(
			    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO);
		    if (helpers.size() > 1) {
			model.addAttribute("configurazioniVerticalizzazionis", helpers);
			// più configurazioni di protocollo indico all'operatore di scegliere quella più adatta
			return "pecinbox/sceltaCofinfigurazioneProtocollo";
		    } else {
			Software s = softwareService.findById(ORMHelper.getSoftware());
			List<Comuniassociati> list = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
			Comuni c = null;
			if (list.size() > 0) {
			    c = list.get(0).getComune(); // prendo il primo dovrebbe essere indifferente se una sola configurazione
			} else {
			    // errore
			    throw new InvalidConfigurationException("Nessun record nella tabella comuniassociati.");
			}
			protCmd.setComune(c);
			protCmd.setProtSoftware(s);
			pec.setComuniProt(c);
			pec.setSoftwareProt(s);
			pecInboxService.update(pec);
		    }
		}
		if (log.isDebugEnabled()) {
		    log.debug("protocolloDaPEC# prot è nullo verifico se scaricare l'oggetto");
		}
		//se non è ancora stato fatto, salvo in OGGETTI il corpo della PEC e ne imposto il riferimento in PEC_INBOX.CODICEOGGETTOPROTOCOLLO
		if (pec.getOggettoProtocollo() == null || pec.getOggettoProtocollo().getId() == null
			|| pec.getOggettoProtocollo().getId().getCodice() == null) {
		    if (log.isDebugEnabled()) {
			log.debug("protocolloDaPEC# devo scaricare la PEC codiceoggettoProtocollo è nullo");
		    }
		    String wsUrl = this.pecInboxService.getNlaGestioneMailWSURL();
		    if (log.isDebugEnabled()) {
			log.debug("protocolloDaPEC# URL per chiamata WS {}", wsUrl);
		    }
		    NlaGestioneMail pecWs = null;
		    NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl,
			    this.verticalizzazioneParametriSistemaService);
		    try {
			pecWs = nlaGestioneMailWSClient.getWSPort();
		    } catch (Exception e) {
			errorMessage = getMessageFromBundle("pecinbox.message.nomailservice",
				new Object[] { wsUrl, StringUtils.defaultString(e.getMessage()) });
			log.error("protocolloDaPEC() - errore nella connessione al WS NLAGestioneMail all'URL {}. {}", new Object[] { wsUrl, e });
		    }
		    if (log.isDebugEnabled()) {
			log.debug("protocolloDaPEC# prima di scaricare il messaggio errorMessage: {}", errorMessage);
		    }
		    if (StringUtils.isEmpty(errorMessage)) {
			if (log.isDebugEnabled()) {
			    log.debug("protocolloDaPEC# procedo a scaricare il mesaggio PEC {}", pec.getId().getId());
			}
			ScaricaMessaggioBinarioRequest req = new ScaricaMessaggioBinarioRequest();
			req.setSoftware(ORMHelper.getSoftware());
			req.setToken(ORMHelper.getToken());
			req.setIdentificativoMessaggio(pec.getId().getId());
			req.setIdaccount(_idAccount);
			ScaricaMessaggioBinarioResponse rsp = null;
			try {
			    rsp = pecWs.scaricaMessaggioBinario(req);
			    if (log.isDebugEnabled()) {
				log.debug("protocolloDaPEC# messaggio scaricato");
			    }
			} catch (Exception e) {
			    errorMessage = getMessageFromBundle("pecinbox.message.mailserviceerror",
				    new Object[] { wsUrl, StringUtils.defaultString(e.getMessage()) });
			    log.error("protocolloDaPEC - errore nella chiamata al WS NLAGestioneMail.scaricaMessaggioBinario all'URL {}. {}",
				    new Object[] { wsUrl, e });
			}
			if (rsp == null || rsp.getContent() == null || rsp.getNomeFile() == null) {
			    if (log.isDebugEnabled()) {
				log.debug("protocolloDaPEC# la pec è stata cancellata la contrassegno come cancellata");
			    }
			    errorMessage = getMessageFromBundle("pecinbox.message.pecprotocolloerror", new Object[0]);
			    this.pecInboxService.contrassegnaPecCancellata(pec, true);
			}
			if (StringUtils.isEmpty(errorMessage)) {
			    try {
				if (log.isDebugEnabled()) {
				    log.debug("protocolloDaPEC# salvo il contenuto binario nella tabella oggetti per il file {}", rsp.getNomeFile());
				}
				DataHandler pecDh = rsp.getContent();
				byte[] binaryPEC = null;
				final InputStream in = pecDh.getInputStream();
				binaryPEC = org.apache.commons.io.IOUtils.toByteArray(in);
				in.close();
				if (binaryPEC != null) {
				    if (binaryPEC.length != 0) {
					this.pecInboxService.salvaOggettoProtocollo(pec, binaryPEC, rsp.getNomeFile());
					if (log.isDebugEnabled()) {
					    log.debug(
						    "protocolloDaPEC() - scaricato il contenuto binario della PEC {} e salvato nella tabella OGGETTI.",
						    new Object[] { pec.getId().getId() });
					}
				    } else {
					errorMessage = "Il contenuto della PEC tornato dal WS è vuoto";
					log.error("protocolloDaPEC# {}", errorMessage);
				    }
				} else {
				    errorMessage = "Il contenuto della PEC tornato dal WS è nullo";
				    log.error("protocolloDaPEC# {}", errorMessage);
				}
			    } catch (Exception e) {
				errorMessage = getMessageFromBundle("pecinbox.message.erroreallegati",
					new Object[] { StringUtils.defaultString(e.getMessage()) });
				log.error("protocolloDaPEC - errore nel salvataggio in locale del messaggio PEC in formato binario. {}",
					new Object[] { wsUrl, e });
			    }
			}
		    }
		}
		if (log.isDebugEnabled()) {
		    log.debug("protocolloDaPEC# errorMessage: {}", errorMessage);
		}
		if (StringUtils.isEmpty(errorMessage)) {
		    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
		    errorMessage = scaricaPec(cmd, _idAccount, result);
		}
	    } else {
		cmd.setReadOnly(true);
	    }
	} else {
	    //pec può essere null o perchè non esiste o perchè esiste ma è già assegnata ad altro utente oppure il record è lockato
	    //nel primo caso occorre predisporre il messaggio di errore
	    if (StringUtils.isEmpty(errorMessage)) {
		errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    }
	    log.error("protocolloDaPEC# - " + errorMessage);
	}
	if (StringUtils.isNotEmpty(errorMessage)) {
	    protocolloView = "redirect:inbox.htm";
	    request.getSession().setAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR, errorMessage);
	}
	protCmd.setPecCommand(cmd);
	model.addAttribute("protocolloCommand", protCmd);
	request.getSession().setAttribute("protocolloCommand", protCmd);
	//mev.setViewName(protocolloView);
	// §§§END§§§
	return protocolloView;
    }

    @RequestMapping
    public ModelAndView movimentoDaPEC(Model model, @ModelAttribute(value = "movimentoPecCommand") PECCommand cmd, BindingResult result,
	    @RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "idAccount", required = false) String idAccount, HttpServletRequest request) {

	ModelAndView mev = new ModelAndView();
	String viewName = "pecinbox/creaMovimento";
	// §§§BEGIN§§§
	String errorMessage = null;
	if (null == cmd) {
	    cmd = new PECCommand();
	}
	BigInteger _idAccout = null;
	if (StringUtils.isNotBlank(idAccount)) {
	    _idAccout = new BigInteger(idAccount);
	}
	PecInbox pec = null;
	try {
	    pec = pecInboxService.lockPecInboxAssegnaResponsabileIstanzaOMovimento(codicePec);
	    cmd.setNumeroProtocollo(pec.getNumeroprotocollo());
	    cmd.setDataProtocollo(pec.getDataprotocollo());
	    cmd.setPec(pec);
	} catch (Exception e) {
	    errorMessage = e.getMessage();
	}
	//predispongo nel command l'opzione utente 'scompatta allegati'
	String scompattaString = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI, "true", request);
	boolean scompattaAllegati = BooleanUtils.toBoolean(scompattaString);
	cmd.setScompattaAllegati(scompattaAllegati);
	if (pec != null) {
	    setPECSelected(pec, request);
	    Movimenti mov = pec.getMovimenti();
	    if (null == mov) {
		Istanze istanzaPec = pec.getIstanze();
		if (istanzaPec == null || istanzaPec.getId() == null || istanzaPec.getId().getCodice() == null) {
		    //predispongo i valori del movimento che possono essere recuperati dal messaggio PEC
		    cmd.getMovimento().setNote(pec.getPecSubject());
		    cmd.getMovimento().setData(pec.getPecDate());
		} else {
		    //se esiste già un'istanza associata a questa PEC impedisco la creazione del movimento
		    errorMessage = getMessageFromBundle("pecinbox.message.erroreistanzapresente", new Object[] { istanzaPec.getNumeroistanza() });
		}
	    } else {
		//se esite già un movimento associato alla PEC visualizzo la pagina di creazione del movimento ma in sola lettura.
		cmd.setMovimento(mov);
		if (StringUtils.isNotBlank(mov.getNumeroprotocollo())) {
		    cmd.setNumeroProtocollo(mov.getNumeroprotocollo());
		}
		if (mov.getDataprotocollo() != null) {
		    cmd.setDataProtocollo(mov.getDataprotocollo());
		}
		cmd.setReadOnly(true);
	    }
	    //scaricamento degli allegati della pec tramite ws o loro lettura dal DB se sono già stati scaricati in precedenza
	    errorMessage = scaricaPec(cmd, _idAccout, result);
	    //cmd.setAllegati(allegati);
	    //mev.addObject("istanzaPecCommand", cmd);
	} else {
	    if (StringUtils.isEmpty(errorMessage)) {
		errorMessage = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
		log.error("movimentoDaPEC() - " + errorMessage);
	    }
	}
	if (StringUtils.isNotEmpty(errorMessage)) {
	    request.getSession().setAttribute(PEC_ERROR_MESSAGE_SESSION_ATTR, errorMessage);
	    viewName = "redirect:inbox.htm";
	}
	mev.setViewName(viewName);
	// §§§END§§§
	return mev;
    }

    @RequestMapping
    public String indietro(@RequestParam(value = "idPec") String idPec, HttpServletRequest request) {

	// §§§BEGIN§§§
	if (StringUtils.isNotBlank(idPec)) {
	    PecInboxId pecId = new PecInboxId(idPec);
	    PecInbox pec = this.pecInboxService.findById(pecId);
	    if (null != pec) {
		Responsabili resp = pec.getResponsabili();
		if (null != resp) {
		    pec.setResponsabili(null);
		    this.pecInboxService.update(pec);
		    if (log.isDebugEnabled()) {
			log.debug("indietro - il messaggio pec {} non è più in carico al responsabile {}",
				new Object[] { pec.getId().getId(), resp.getResponsabile() });
		    }
		}
	    }
	}
	// §§§END§§§
	String retVal = "redirect:inbox.htm?resetFilter=false";
	return retVal;
    }

    @RequestMapping
    public ModelAndView creaIstanza(Model model, @ModelAttribute(value = "istanzaPecCommand") PECCommand cmd, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) {

	ModelAndView retVal = new ModelAndView();
	// §§§BEGIN§§§
	String viewName = "pecinbox/creaIstanza";
	String codicePec = cmd.getPec().getId().getId();
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = this.pecInboxService.findById(pecId);
	cmd.setPec(pec);
	String error = null;
	boolean notValid = false;
	//salvo l'impostazione utente 'scompatta allegati'
	this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI, new Boolean(cmd.isScompattaAllegati()).toString(), request,
		response);
	if (null != pec) {
	    Set<PecInboxAllegati> allegatiPec = pec.getAllegatiPec();
	    //elaboraAllegatiPecDaDb(cmd, new ArrayList<PecInboxAllegati>(allegatiPec));
	    //creazione istanza via STC
	    InserimentoPraticaRequest stcRequest = new InserimentoPraticaRequest();
	    Verticalizzazioniparametri vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	    if (null == vertParam) {
		error = "Impossibile inviare a STC la richiesta di creazione della pratica: manca la verticalizzazione STC.NLA_IDNODO";
	    } else {
		SportelloType mitt = new SportelloType();
		mitt.setIdNodo(vertParam.getValore());
		mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
		mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX);
		stcRequest.setSportelloMittente(mitt);
		SportelloType dest = new SportelloType();
		dest.setIdNodo(vertParam.getValore());
		dest.setIdEnte(ORMHelper.getIdcomuneAlias());
		dest.setIdSportello(ORMHelper.getSoftware());
		stcRequest.setSportelloDestinatario(dest);
		DettaglioPraticaType datiPratica = new DettaglioPraticaType();
		datiPratica.setIdPratica(pec.getId().getId());
		datiPratica.setNumeroPratica(pec.getId().getId());
		//validazioni
		//richiedente
		Anagrafe richiedente = findRichiedente(cmd);
		if (null == richiedente) {
		    //error = "Impossibile creare l'istanza dalla PEC selezionata: richiedente non specificato";
		    //String validationError = getMessageFromBundle("pecinbox.message.norichiedente", new Object[0]);
		    String validationError = "Richiedente non specificato.";
		    result.rejectValue("richiedente", "pecinbox.message.norichiedente", validationError);
		    notValid = true;
		}
		//intervento
		Alberoproc intervento = cmd.getIntervento();
		if (null != intervento && null != intervento.getId()) {
		    intervento = this.alberoprocService.findById(new PkId(intervento.getId().getCodice()));
		}
		if (null == intervento) {
		    String validationError = "Intervento non specificato.";
		    result.rejectValue("intervento", "pecinbox.message.nointervento", validationError);
		    notValid = true;
		}
		//indirizzo pec
		String pecAddress = cmd.getIstanza().getDomicilioElettronico();
		boolean pecValid = Utilities.validaIndirizzoMail(pecAddress);
		if (!pecValid) {
		    result.rejectValue("istanza.domicilioElettronico", "validator.email", "Indirizzo email non valido.");
		    notValid = true;
		}
		datiPratica.setDomicilioElettronico(pecAddress);
		Comuni comuneIn = cmd.getIstanza().getComune();
		if (null != comuneIn) {
		    Comuni comuneOut = this.comuniService.findByCodiceComune(comuneIn);
		    if (null != comuneOut) {
			ComuneType codComune = new ComuneType();
			codComune.setCodiceIstat(comuneOut.getCodiceistat());
			codComune.setComune(comuneOut.getComune());
			//codComune.setCodiceCatastale()
			datiPratica.setCodiceComune(codComune);
			cmd.getIstanza().setComune(comuneOut);
		    } else {
			log.warn("creaIstanza - impossibile trovare il comune avente codice {}.", new Object[] { comuneIn.getCodicecomune() });
		    }
		}
		GregorianCalendar calDataPratica = new GregorianCalendar();
		calDataPratica.setTime(cmd.getIstanza().getData());
		datiPratica.setDataPratica(Utilities.getXMLGregorianCalendar(calDataPratica));
		if (StringUtils.isNotBlank(cmd.getIstanza().getOraInserimento())) {
		    datiPratica.setOraDataPratica(cmd.getIstanza().getOraInserimento());
		}
		datiPratica.setOggetto(cmd.getIstanza().getLavori());
		datiPratica.setAnnotazioni(cmd.getIstanza().getLavoriestesa());
		datiPratica.setCodicePraticaTelematica(cmd.getIstanza().getCodicepraticatel());
		//Anagrafe richiedente = findRichiedente(cmd);
		if (!notValid) {
		    //imposto il richiedente
		    cmd.setRichiedente(richiedente);
		    RichiedenteType richiedenteStc = new RichiedenteType();
		    AnagrafeType stcAna = this.nlaHelperService.populateAnagrafeType(richiedente);
		    richiedenteStc.setAnagrafica(stcAna.getPersonaFisica());
		    //ruolo richiedente 
		    Tipisoggetto inQualitaDi = cmd.getIstanza().getTipisoggetto();
		    if (null != inQualitaDi && null != inQualitaDi.getId() && null != inQualitaDi.getId().getCodice()) {
			RuoloType ruoloStc = new RuoloType();
			ruoloStc.setRuolo(inQualitaDi.getTiposoggetto());
			ruoloStc.setIdRuolo(inQualitaDi.getId().getCodice().toString());
			richiedenteStc.setRuolo(ruoloStc);
		    }
		    datiPratica.setRichiedente(richiedenteStc);
		    //il titolare legale
		    Anagrafe azienda = cmd.getIstanza().getTitolarelegale();
		    if (azienda != null && azienda.getId() != null && azienda.getId().getCodice() != null) {
			azienda = anagrafeService.findById(new PkId(azienda.getId().getCodice()));
		    }
		    if (null != azienda && StringUtils.isNotBlank(azienda.getTipoanagrafe())) {
			stcAna = this.nlaHelperService.populateAnagrafeType(azienda);
			datiPratica.setAziendaRichiedente(stcAna.getPersonaGiuridica());
			cmd.setTitolarelegale(azienda);
		    } else {
			cmd.getIstanza().setTitolarelegale(new Anagrafe());
		    }
		    //intervento
		    InterventoType interventoStc = new InterventoType();
		    interventoStc.setCodice(intervento.getId().getCodice().toString());
		    interventoStc.setDescrizione(intervento.getVwAlberoproc().getScDescrizione());
		    datiPratica.setIntervento(interventoStc);
		    //protocollo
		    if (null != pec.getDataprotocollo()) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(pec.getDataprotocollo());
			datiPratica.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
		    } else {
			if (cmd.getDataProtocollo() != null) {
			    GregorianCalendar gc = new GregorianCalendar();
			    gc.setTime(cmd.getDataProtocollo());
			    datiPratica.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
			}
		    }
		    if (StringUtils.isNotBlank(pec.getNumeroprotocollo())) {
			datiPratica.setNumeroProtocolloGenerale(pec.getNumeroprotocollo());
		    } else {
			if (StringUtils.isNotBlank(cmd.getNumeroProtocollo())) {
			    datiPratica.setNumeroProtocolloGenerale(cmd.getNumeroProtocollo());
			}
		    }
		    if (StringUtils.isNotEmpty(pec.getIdprotocollo())) {
			List<ParametroType> altriDati = datiPratica.getAltriDati();
			ParametroType idProtParam = new ParametroType();
			idProtParam.setNome(NlaHelperServiceImpl.ALTRI_DATI_ISTANZE_MOVIMENTI_FKIDPROTOCOLLO);
			ValoreParametroType idProtParamVal = new ValoreParametroType();
			idProtParamVal.setCodice(pec.getIdprotocollo());
			idProtParamVal.setDescrizione(pec.getIdprotocollo());
			idProtParam.getValore().add(idProtParamVal);
			altriDati.add(idProtParam);
		    }
		    //allegati
		    List<DocumentiType> stcDocs = datiPratica.getDocumenti();
		    //se richiesto dall'utente scompatto gli allegati compressi
		    List<Oggetti> allegatiIstanza = this.pecInboxService.getAllegatiPECPerIstanzaoMovimento(allegatiPec, cmd.isScompattaAllegati());
		    for (Oggetti allegato : allegatiIstanza) {
			DocumentiType stcDoc = new DocumentiType();
			stcDoc.setId(allegato.getId().getCodice().toString());
			stcDoc.setDocumento(allegato.getNomefile());
			AllegatiType allType = new AllegatiType();
			allType.setId(allegato.getId().getCodice().toString());
			allType.setAllegato(allegato.getNomefile());
			stcDoc.setAllegati(allType);
			stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
			stcDocs.add(stcDoc);
		    }
		    stcRequest.setDettaglioPratica(datiPratica);
		    populateEndoForInsert(request, stcRequest);
		    // POPULATE - LOCALIZZAZIONE
		    log.debug("populate - dati localizzazione istanza");
		    populateLocalizzazioneIstanza(stcRequest, cmd.getIstanzestradario());
		    log.debug("creaIstanza - invocazione in corso del servizio stc.inserimentoPratica.");
		    try {
			InserimentoPraticaResponse stcResponse = this.stcWsClient.inserimentoPratica(stcRequest);
			//se inserimento a buon fine riporto nell'output il riferimento all'istanza creata
			List<ErroreType> stcErrors = stcResponse.getDettaglioErrore();
			if (stcErrors == null || stcErrors.isEmpty()) {
			    RiferimentiPraticaType stcIstanza = stcResponse.getDettaglioPratica();
			    if (null != stcIstanza) {
				String idPratica = stcIstanza.getIdPratica();
				String numeroPratica = stcIstanza.getNumeroPratica();
				PkId pkIstanza = new PkId(Integer.parseInt(idPratica));
				//l'istanza viene riletta dal DB per caricare anche i dati del protocollo che potrebbe essere stato creato in automatico.
				Istanze istanza = this.istanzeService.findById(pkIstanza);
				if (istanza != null) {
				    cmd.setIstanza(istanza);
				} else {
				    //l'istanza potrebbe non essere riletta perchè inserita nelle istanze con errori a causa di un problema verificatosi in STC
				    cmd.setStcError(true);
				    cmd.getIstanza().setNumeroistanza(numeroPratica);
				    cmd.getIstanza().setId(pkIstanza);
				}
				log.debug("creaIstanza - creata istanza n° {}, id={}, a partire dalla pec {}.",
					new Object[] { numeroPratica, idPratica, pec.getId().getId() });
				//imposto il riferimento all'istanza nel record di PEC_INBOX
				try {
				    this.pecInboxService.associaPecAIstanza(pec, cmd.getIstanza());
				    log.debug("creaIstanza - la pec {} è stata associata all'istanza n°{}, id={}.", new Object[] {
					    pec.getId().getId(), cmd.getIstanza().getNumeroistanza(), cmd.getIstanza().getId().getCodice() });
				    StringBuilder sbMsg = new StringBuilder(
					    "Creazione istanza da PEC completata con successo. Alla nuova istanza è stato assegnato il numero ");
				    sbMsg.append(numeroPratica);
				} catch (DataIntegrityViolationException dive) {
				    log.error(
					    "creaIstanza - la creazione dell'istanza è fallita in STC ma la domanda è stata registrata fra le domande con errori.");
				    cmd.setStcError(true);
				}
				cmd.setReadOnly(true);
			    }
			}
			//se ci sono errori li visualizzo all'utente
			else {
			    StringBuilder sbErr = new StringBuilder("Si sono verificati errori durante la creazione dell'istanza:<br/><ul>");
			    for (ErroreType stcError : stcErrors) {
				sbErr.append("<li>").append(stcError.getNumeroErrore()).append(" - ").append(stcError.getDescrizione());
			    }
			    sbErr.append("</ul>");
			    error = sbErr.toString();
			}
		    } catch (Exception e) {
			log.error("creaIstanza - errore alla chiamata di STC: ", e);
			setModelLocalizzazione(model);
			this.copyErrorsToBindingResult(result, cmd.getPec(), true, e);
		    }
		}
	    }
	} else {
	    error = new StringBuilder("Impossibile individuare la PEC selezionata avente codice ").append(codicePec)
		    .append(". L'istanza non è stata creata.").toString();
	}
	if (StringUtils.isNotEmpty(error)) {
	    setModelLocalizzazione(model);
	    model.addAttribute("error", error);
	}
	//se l'inserimento non è andato a buon fine (anche con errori STC) ricarico l'elenco degli allegati
	if (!cmd.isReadOnly()) {
	    setModelLocalizzazione(model);
	    elaboraAllegatiPecDaDb(cmd, new ArrayList<PecInboxAllegati>(pec.getAllegatiPec()));
	}
	retVal.setViewName(viewName);
	// FIX: per evitare NPL sulla jsp quando l'istanza viene creata senza titolare legale e tipo soggetto
	fixRenderIstanza(cmd.getIstanza());
	// §§§END§§§
	return retVal;
    }

    /**
     * <pre>
     * Il medodo carica le informazioni sulle localizzazione da presnetare nella maschera:
     * 1- Tipo localizzazione
     * 2- colore
     * 3- tipo catasto
     * </pre>
     */
    private void setModelLocalizzazione(Model model) {

	boolean isStradariocolore = stradariocoloreService.existsRecords();
	model.addAttribute("isStradariocoloreVisible", isStradariocolore);
	if (isStradariocolore) {
	    List<Stradariocolore> stradariocoloreList = stradariocoloreService.findAll();
	    model.addAttribute("stradariocoloreList", stradariocoloreList);
	}
	List<TipiLocalizzazioni> tipiLocalizzazionis = tipiLocalizzazioniService.findAll(null, null);
	model.addAttribute("tipiLocalizzazionis", tipiLocalizzazionis);
	List<Catasto> catastoList = catastoService.findAll();
	model.addAttribute("catastoList", catastoList);
    }

    private void populateLocalizzazioneIstanza(InserimentoPraticaRequest stcRequest, IstanzestradarioCommand istanzestradario) {

	System.out.println("");
	LocalizzazioneNelComuneType locComuneType = new LocalizzazioneNelComuneType();
	TipoLocalizzazioneType tipoLocalizzazioneType = new TipoLocalizzazioneType();
	// TIPO LOCALIZZAZIONE
	if (EntityUtils.getNestedProperty(istanzestradario.getEntity().getTipiLocalizzazioni(), "id.codice") != null) {
	    TipiLocalizzazioni localizzazioni = tipiLocalizzazioniService
		    .findById(new PkId(istanzestradario.getEntity().getTipiLocalizzazioni().getId().getCodice()));
	    tipoLocalizzazioneType.setCodice(localizzazioni.getId().getCodice().toString());
	    tipoLocalizzazioneType.setDescrizione(localizzazioni.getDescrizione());
	    locComuneType.setTipo(tipoLocalizzazioneType);
	}
	// STRADARIO
	if (EntityUtils.getNestedProperty(istanzestradario.getEntity().getStradario(), "id.codice") != null) {
	    it.gruppoinit.pal.gp.core.domain.Stradario stradario = stradarioService
		    .findById(new PkId(istanzestradario.getEntity().getStradario().getId().getCodice()));
	    locComuneType.setDenominazione(stradario.getDescrizione());
	    locComuneType.setId(stradario.getId().getCodice().toString().trim());
	    if (StringUtils.isNotBlank(stradario.getCodviario())) {
		locComuneType.setCodiceViario(stradario.getCodviario().trim());
	    }
	}
	// CIVICO
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getCivico())) {
	    locComuneType.setCivico(istanzestradario.getEntity().getCivico());
	}
	// ESPONENTE
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getEsponente())) {
	    locComuneType.setEsponente(istanzestradario.getEntity().getEsponente());
	}
	// COLORE
	if (istanzestradario.getEntity().getStradariocolore() != null
		&& StringUtils.isNotBlank(istanzestradario.getEntity().getStradariocolore().getId().getCodicecolore())) {
	    locComuneType.setColore(istanzestradario.getEntity().getStradariocolore().getId().getCodicecolore());
	}
	// SCALA
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getScala())) {
	    locComuneType.setScala(istanzestradario.getEntity().getScala());
	}
	// PIANO
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getPiano())) {
	    locComuneType.setPiano(istanzestradario.getEntity().getPiano());
	}
	// PIANO
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getInterno())) {
	    locComuneType.setInterno(istanzestradario.getEntity().getInterno());
	}
	// ESPONENTE INTERNO
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getEsponenteinterno())) {
	    locComuneType.setEsponenteInterno(istanzestradario.getEntity().getEsponenteinterno());
	}
	// FABBRICATO
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getFabbricato())) {
	    locComuneType.setFabbricato(istanzestradario.getEntity().getFabbricato());
	}
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getKm())) {
	    locComuneType.setKm(istanzestradario.getEntity().getKm());
	}
	//NOTE
	// npn sono presenti nella modellazione
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getCap())) {
	    locComuneType.setCap(istanzestradario.getEntity().getCap());
	}
	// FRAZIONE
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getFrazione())) {
	    FrazioneType frazioneType = new FrazioneType();
	    //frazioneType.setCodice(value);??????????
	    frazioneType.setDescrizione(istanzestradario.getEntity().getFrazione());
	    locComuneType.setFrazione(frazioneType);
	}
	// QUARTIERE
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getQuartiere())) {
	    QuartiereType quartiereType = new QuartiereType();
	    //	quartiereType.setCodice(value);????
	    quartiereType.setDescrizione(istanzestradario.getEntity().getQuartiere());
	    locComuneType.setQuartiere(quartiereType);
	}
	// CIRCOSCRIZIONE
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getCircoscrizione())) {
	    CircoscrizioneType circoscrizioneType = new CircoscrizioneType();
	    //circoscrizioneType.setCodice(value);?????????
	    circoscrizioneType.setDescrizione(istanzestradario.getEntity().getCircoscrizione());
	    locComuneType.setCircoscrizione(circoscrizioneType);
	}
	CoordinateType coordinateType = new CoordinateType();
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getLatitudine())) {
	    coordinateType.setLatitudine(istanzestradario.getEntity().getLatitudine());
	}
	if (StringUtils.isNotBlank(istanzestradario.getEntity().getLongitudine())) {
	    coordinateType.setLongitudine(istanzestradario.getEntity().getLongitudine());
	}
	locComuneType.setCoordinate(coordinateType);
	// Rifermenti catastali
	RiferimentoCatastaleType catastaleTypes = new RiferimentoCatastaleType();
	if (StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getFoglio())) {
	    catastaleTypes.setFoglio(istanzestradario.getIstanzemappali().getFoglio());
	}
	if (StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getParticella())) {
	    catastaleTypes.setParticella(istanzestradario.getIstanzemappali().getParticella());
	}
	if (StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getSezione())) {
	    catastaleTypes.setSezione(istanzestradario.getIstanzemappali().getSezione());
	}
	if (StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getSub())) {
	    catastaleTypes.setSub(istanzestradario.getIstanzemappali().getSub());
	}
	if (istanzestradario.getIstanzemappali().getCatasto() != null
		&& StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getCatasto().getCodice())) {
	    if (istanzestradario.getIstanzemappali().getCatasto().getCodice().equalsIgnoreCase("F")) {
		catastaleTypes.setTipoCatasto(CATASTO_EDILIZIO_URBANO);
	    } else {
		catastaleTypes.setTipoCatasto(CATASTO_TERRENI);
	    }
	}
	if (StringUtils.isNotBlank(istanzestradario.getIstanzemappali().getUnitaimmob())) {
	    catastaleTypes.setUnitaImobiliare(istanzestradario.getIstanzemappali().getUnitaimmob());
	}
	locComuneType.getRiferimentoCatastale().add(catastaleTypes);
	stcRequest.getDettaglioPratica().getLocalizzazione().add(locComuneType);
	System.out.println("");
    }

    private void fixRenderIstanza(Istanze istanze) {

	if (EntityUtils.getNestedProperty(istanze.getTitolarelegale(), "id.codice") == null) {
	    istanze.setTitolarelegale(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(istanze.getTipisoggetto(), "id.codice") == null) {
	    istanze.setTipisoggetto(new Tipisoggetto());
	}
    }

    @RequestMapping
    public ModelAndView creaMovimento(Model model, @ModelAttribute(value = "movimentoPecCommand") PECCommand cmd, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) {

	ModelAndView mev = new ModelAndView();
	// §§§BEGIN§§§
	String viewName = "pecinbox/creaMovimento";
	String codicePec = cmd.getPec().getId().getId();
	PecInboxId pecId = new PecInboxId(codicePec);
	PecInbox pec = this.pecInboxService.findById(pecId);
	cmd.setPec(pec);
	String error = null;
	boolean notValid = false;
	//salvo l'impostazione utente 'scompatta allegati'
	this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_SCOMPATTA_ALLEGATI, new Boolean(cmd.isScompattaAllegati()).toString(), request,
		response);
	if (null != pec) {
	    Set<PecInboxAllegati> allegatiPec = pec.getAllegatiPec();
	    //creazione istanza via STC
	    NotificaAttivitaRequest stcRequest = new NotificaAttivitaRequest();
	    Verticalizzazioniparametri vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_NLA_IDNODO);
	    if (null == vertParam) {
		error = "Impossibile inviare a STC la richiesta di creazione del movimento: manca la verticalizzazione STC.NLA_IDNODO";
	    } else {
		//sportelli mittente e destinatario
		SportelloType mitt = new SportelloType();
		mitt.setIdNodo(vertParam.getValore());
		mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
		mitt.setIdSportello(ORMHelper.getSoftware() + WebConstants.PEC_CLIENT_IDSPORTELLO_SUFFIX);
		stcRequest.setSportelloMittente(mitt);
		SportelloType dest = new SportelloType();
		dest.setIdNodo(vertParam.getValore());
		dest.setIdEnte(ORMHelper.getIdcomuneAlias());
		dest.setIdSportello(ORMHelper.getSoftware());
		stcRequest.setSportelloDestinatario(dest);
		//dettaglio attivita...
		DettaglioAttivitaType attivita = new DettaglioAttivitaType();
		GregorianCalendar calDataMov = new GregorianCalendar();
		calDataMov.setTime(pec.getPecDate());
		//data movimento
		attivita.setDataAttivita(Utilities.getXMLGregorianCalendar(calDataMov));
		//GIANPAOLO-ORA
		String dataPec = Utilities.getOrario(pec.getPecDate());
		attivita.setOraDataAttivita(dataPec);
		//protocollo
		if (null != pec.getDataprotocollo()) {
		    GregorianCalendar gc = new GregorianCalendar();
		    gc.setTime(pec.getDataprotocollo());
		    attivita.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
		} else {
		    if (cmd.getDataProtocollo() != null) {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(cmd.getDataProtocollo());
			attivita.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(gc));
		    }
		}
		if (StringUtils.isNotBlank(pec.getNumeroprotocollo())) {
		    attivita.setNumeroProtocolloGenerale(pec.getNumeroprotocollo());
		} else {
		    if (StringUtils.isNotBlank(cmd.getNumeroProtocollo())) {
			attivita.setNumeroProtocolloGenerale(cmd.getNumeroProtocollo());
		    }
		}
		if (StringUtils.isNotEmpty(pec.getIdprotocollo())) {
		    List<ParametroType> altriDati = attivita.getAltriDati();
		    ParametroType idProtParam = new ParametroType();
		    idProtParam.setNome("");
		    ValoreParametroType idProtParamVal = new ValoreParametroType();
		    idProtParamVal.setCodice(pec.getIdprotocollo());
		    idProtParamVal.setDescrizione(pec.getIdprotocollo());
		    idProtParam.getValore().add(idProtParamVal);
		    altriDati.add(idProtParam);
		}
		//tipo attivita
		TipoAttivitaType tipoAtt = new TipoAttivitaType();
		Movimenti mov = cmd.getMovimento();
		//istanza
		Istanze istanza = null;
		if (null != mov) {
		    istanza = mov.getIstanza();
		    if (null != istanza && null != istanza.getId() && null != istanza.getId().getCodice()) {
			attivita.setIdPratica(istanza.getId().getCodice().toString());
		    } else {
			notValid = true;
			result.rejectValue("movimento.istanza", "pecinbox.message.noistanza", "Istanza non specificata.");
		    }
		    //tipo movimento e movimento
		    Tipimovimento tipoMov = mov.getTipomovimento();
		    if (tipoMov != null && tipoMov.getId() != null && StringUtils.isNotEmpty(tipoMov.getId().getTipomovimento())) {
			tipoAtt.setCodice(tipoMov.getId().getTipomovimento());
			if (StringUtils.isBlank(mov.getMovimento())) {
			    //se il nome del movimento è lasciato vuoto viene presa la descrizione estesa del tipo movimento
			    mov.setMovimento(tipoMov.getDescrizioneEstesa());
			}
			tipoAtt.setDescrizione(mov.getMovimento());
			attivita.setTipoAttivita(tipoAtt);
		    } else {
			notValid = true;
			result.rejectValue("movimento.tipomovimento", "pecinbox.message.notipomovimento", "Tipo movimento non specificato.");
		    }
		    //Amministrazione
		    Amministrazioni amministrazione = mov.getAmministrazioni();
		    if (amministrazione != null && amministrazione.getId() != null && amministrazione.getId().getCodice() != null) {
			ParametroType altroDato = new ParametroType();
			Integer codiceAmm = amministrazione.getId().getCodice();
			altroDato.setNome(NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC);
			ValoreParametroType vpt = new ValoreParametroType();
			vpt.setCodice(String.valueOf(codiceAmm));
			vpt.setDescrizione(String.valueOf(amministrazione.getAmministrazione()));
			altroDato.getValore().add(vpt);
			attivita.getAltriDati().add(altroDato);
		    }
		} else {
		    notValid = true;
		    result.rejectValue("movimento.tipomovimento", "pecinbox.message.notipomovimento", "Tipo movimento non specificato.");
		}
		//se non ci sono errori di validazione procedo nel recupero dei dati inseriti dall'utente e alla chiamata di stc.notificaAttivita
		if (!notValid) {
		    //esito
		    boolean esito = BooleanUtils.toBooleanDefaultIfNull(mov.getEsito(), false);
		    attivita.setEsito(esito);
		    attivita.setNote(mov.getNote());
		    RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
		    //rifPratica
		    rifPratica.setIdPratica(istanza.getId().getCodice().toString());
		    stcRequest.setRifPraticaDestinatario(rifPratica);
		    attivita.setTipoAttivita(tipoAtt);
		    stcRequest.setDatiAttivita(attivita);
		    //gestione allegati
		    List<DocumentiType> stcDocs = attivita.getDocumenti();
		    List<Oggetti> allegatiMovimento = this.pecInboxService.getAllegatiPECPerIstanzaoMovimento(allegatiPec, cmd.isScompattaAllegati());
		    for (Oggetti allegato : allegatiMovimento) {
			DocumentiType stcDoc = new DocumentiType();
			stcDoc.setId(allegato.getId().getCodice().toString());
			stcDoc.setDocumento(allegato.getNomefile());
			AllegatiType allType = new AllegatiType();
			allType.setId(allegato.getId().getCodice().toString());
			allType.setAllegato(allegato.getNomefile());
			stcDoc.setAllegati(allType);
			stcDoc.setTipoDocumento(TipoDocumentoType.ALTRO.value());
			stcDocs.add(stcDoc);
		    }
		    stcRequest.setDatiAttivita(attivita);
		    if (log.isDebugEnabled()) {
			log.debug("creaMovimento - invocazione in corso del servizio stc.notificaAttivita");
		    }
		    try {
			NotificaAttivitaResponse stcResponse = this.stcWsClient.notificaAttivita(stcRequest);
			//se inserimento a buon fine riporto nell'output il riferimento all'istanza creata
			List<ErroreType> stcErrors = stcResponse.getDettaglioErrore();
			if (stcErrors == null || stcErrors.isEmpty()) {
			    RiferimentiAttivitaType stcMovimento = stcResponse.getDettaglioattivita();
			    if (null != stcMovimento) {
				String idMov = stcMovimento.getIdAttivita();
				cmd.getMovimento().getId().setCodice(Integer.parseInt(idMov));
				if (log.isDebugEnabled()) {
				    log.debug("creaMovimento - creato movimento con id={}, a partire dalla pec {}.",
					    new Object[] { idMov, pec.getId().getId() });
				}
				//imposto il riferimento al movimento nel record di PEC_INBOX
				try {
				    this.pecInboxService.associaPecAMovimento(pec, cmd.getMovimento());
				    if (log.isDebugEnabled()) {
					log.debug("creaMovimento - la pec {} è stata associata al movimento con id={}.",
						new Object[] { pec.getId().getId(), cmd.getMovimento().getId().getCodice() });
				    }
				} catch (DataIntegrityViolationException dive) {
				    log.error(
					    "creaMovimento - la creazione del movimento è fallita in STC ma la domanda è stata registrata fra le domande con errori.");
				    cmd.setStcError(true);
				}
				cmd.setReadOnly(true);
				viewName = "redirect:movimentoDaPEC.htm?codicePec=" + pec.getId().getId() + "&idAccount=" + pec.getMailConfigId();
			    }
			}
			//se ci sono errori li visualizzo all'utente
			else {
			    StringBuilder sbErr = new StringBuilder("Si sono verificati errori durante la creazione dell' istanza:<br/><ul>");
			    for (ErroreType stcError : stcErrors) {
				sbErr.append("<li>").append(stcError.getNumeroErrore()).append(" - ").append(stcError.getDescrizione());
			    }
			    sbErr.append("</ul>");
			    error = sbErr.toString();
			}
		    } catch (Exception e) {
			log.error("creaMovimento - errore alla chiamata di STC: ", e);
			this.copyErrorsToBindingResult(result, cmd.getPec(), true, e);
		    }
		}
	    }
	} else {
	    error = new StringBuilder("Impossibile individuare la PEC selezionata avente codice ").append(codicePec)
		    .append(". Il movimento non è stato creato.").toString();
	}
	if (StringUtils.isNotEmpty(error)) {
	    model.addAttribute("error", error);
	}
	//se l'inserimento non è andato a buon fine (anche con errori STC) ricarico l'elenco degli allegati
	if (!cmd.isReadOnly()) {
	    elaboraAllegatiPecDaDb(cmd, new ArrayList<PecInboxAllegati>(pec.getAllegatiPec()));
	}
	//mev.setViewName("pecinbox/creaMovimento");
	mev.setViewName(viewName);
	// §§§END§§§
	return mev;
    }

    @RequestMapping
    public ModelAndView processaMessaggiPEC(@RequestParam(required = false, value = "idAccount") String idAccount, HttpServletRequest request,
	    HttpServletResponse response) {

	ModelAndView mev = new ModelAndView();
	Map<String, Object> model = new HashMap<String, Object>();
	String retError = null;
	SessioneElaborazionePEC processingSession = (SessioneElaborazionePEC) request.getSession().getAttribute(ESITO_PROCESSAMENTO_PEC);
	if (processingSession == null) {
	    processingSession = new SessioneElaborazionePEC();
	    processingSession.setTipoProcessamento(TipoSessioneElaborazionePEC.PROCESSAMENTO);
	} else {
	    if (processingSession.isRunning()) {
		throw new RuntimeException(getMessageFromBundle("pecinbox.message.processamentoincorso", new Object[0]));
	    } else {
		processingSession = new SessioneElaborazionePEC();
		processingSession.setTipoProcessamento(TipoSessioneElaborazionePEC.PROCESSAMENTO);
	    }
	}
	request.getSession().setAttribute(ESITO_PROCESSAMENTO_PEC, processingSession);
	String wsUrl = getNlaGestioneMailWSURL();
	NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl, this.verticalizzazioneParametriSistemaService);
	//il processamento delle pec è una procedura lenta quindi imposto il timeout a 15 min.
	// nlaGestioneMailWSClient.setCallTimeout(900000); default è 1200000
	NlaGestioneMail pecWs = null;
	if (log.isDebugEnabled()) {
	    log.debug(
		    "processaMessaggiPEC - processamento massivo di tutti i messaggi PEC tramite invocazione del WS NlaGestioneMail.ProcessaMessaggi all'indirizzo {}.",
		    new Object[] { wsUrl });
	}
	try {
	    pecWs = nlaGestioneMailWSClient.getWSPort();
	    //throw new SocketTimeoutException();
	} catch (Exception e) {
	    retError = getMessageFromBundle("pecinbox.message.nomailservice", new Object[] { wsUrl });
	    log.error("processaMessaggiPEC - " + retError, e);
	    model.put("error", retError);
	    processingSession.setRunning(false);
	    processingSession.setErrore(retError);
	}
	if (StringUtils.isEmpty(retError)) {
	    ProcessaMessaggiResponse processaWsResp = null;
	    try {
		ReportProcessamentoPecDTO out = new ReportProcessamentoPecDTO();
		DettaglioReportType report = null;
		ProcessaMessaggiRequest processaWsReq = new ProcessaMessaggiRequest();
		processaWsReq.setSoftware(ORMHelper.getSoftware());
		processaWsReq.setToken(ORMHelper.getToken());
		processingSession.setStartMillis(new Date().getTime());
		processingSession.setRunning(true);
		if (StringUtils.isNotBlank(idAccount)) {
		    processaWsReq.setIdaccount(new BigInteger(idAccount));
		}
		processaWsResp = pecWs.processaMessaggi(processaWsReq);
		out.setErrore(processaWsResp.getErrore());
		out.setAvviso(processaWsResp.getAvviso());
		report = processaWsResp.getListaDettagli();
		if (null != report) {
		    out.setMessaggio(getMessageFromBundle("pecinbox.message.esitoprocessamento",
			    new Object[] { report.getNumeroMessaggiTotali(), report.getNumeroMessaggiConErrore() }));
		    out.setErroriPec(report.getListaErrori());
		    out.setNumeroPecProcessate(report.getNumeroMessaggiTotali());
		    out.setNumeroPecConErrore(report.getNumeroMessaggiConErrore());
		} else {
		    out.setMessaggio(getMessageFromBundle("pecinbox.message.processamentocompletato", new Object[0]));
		}
		//Lion - test visualizzazione messaggio di errore e/o warning
		//out.setAvviso("La tua casella PEC è piena di SPAM!!");
		//out.setErrore("Mi dispiace ma il processamento delle PEC non potrà mai funzionare, è una sola completa!!");
		//End Lion - test visualizzazione messaggio di errore e/o warning
		processingSession.setEndMillis(new Date().getTime());
		processingSession.setEsitoProcessamento(out);
		processingSession.setRunning(false);
		BigInteger ecxecutionSeconds = BigInteger.valueOf(processingSession.getEndMillis())
			.subtract(BigInteger.valueOf(processingSession.getStartMillis())).divide(BigInteger.valueOf(1000L));
		if (log.isDebugEnabled()) {
		    log.debug("processaMessaggiPEC - Processamento dei messaggi PEC completato: processati {} messaggi con {} errori in {} secondi",
			    new Object[] { report.getNumeroMessaggiTotali(), report.getNumeroMessaggiConErrore(), ecxecutionSeconds.intValue() });
		}
		model.put("data", out);
		//Lion test gestione eccezione durante il processamento
		//throw new RuntimeException("Si è verificato un'errore irreversibile del sistema.");
	    } catch (Exception e) {
		retError = getMessageFromBundle("pecinbox.message.erroreprocessamento", new Object[0]) + StringUtils.defaultString(e.getMessage());
		log.error("processaMessaggiPEC - " + retError);
		model.put("error", retError);
		processingSession.setRunning(false);
		processingSession.setErrore(retError);
	    }
	    //processaWsResp.g
	}
	//Lion test gestione errore HTTP
	//response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
	//end Lion
	mev.addAllObjects(model);
	mev.setViewName("jsonView");
	return mev;
    }

    @RequestMapping
    public ModelAndView ajaxStatoProcessamentoPEC(HttpServletRequest request, HttpServletResponse response) {

	ModelAndView mev = new ModelAndView();
	Map<String, Object> model = new HashMap<String, Object>();
	//String retError = null;
	SessioneElaborazionePEC processingSession = (SessioneElaborazionePEC) request.getSession().getAttribute(ESITO_PROCESSAMENTO_PEC);
	if (processingSession != null) {
	    model.put("running", processingSession.isRunning());
	    if (!processingSession.isRunning()) {
		if (StringUtils.isNotBlank(processingSession.getErrore())) {
		    model.put("error", processingSession.getErrore());
		} else if (null != processingSession.getEsitoProcessamento()) {
		    model.put("data", processingSession.getEsitoProcessamento());
		}
	    }
	} else {
	    model.put("running", false);
	}
	mev.addAllObjects(model);
	mev.setViewName("jsonView");
	return mev;
    }

    @RequestMapping
    public ModelAndView ajaxCalcolaCodicePraticaTel(Model model, @ModelAttribute(value = "istanzaPecCommand") PECCommand cmd,
	    HttpServletRequest request) {

	String error = "";
	StringBuilder sb = new StringBuilder();
	String codicePec = cmd.getPec().getId().getId();
	PecInbox pec = this.pecInboxService.findById(new PecInboxId(codicePec));
	Istanze i = cmd.getIstanza();
	if (null != i) {
	    Date data = pec.getPecDate();
	    if (null == data) {
		if (null != pec && null != i.getData()) {
		    data = i.getData();
		} else {
		    error = getMessageFromBundle("pecinbox.message.errorecodicepraticatel.nodata", new Object[0]);
		}
	    }
	    Anagrafe azienda = i.getTitolareLegaleORichiedente();
	    if (StringUtils.isEmpty(error)) {
		if (null != azienda && null != azienda.getId() && null != azienda.getId().getCodice()) {
		    azienda = anagrafeService.findById(new PkId(azienda.getId().getCodice()));
		}
		if (null == azienda || null == azienda.getId() || null == azienda.getId().getCodice()) {
		    error = getMessageFromBundle("pecinbox.message.errorecodicepraticatel.nocf", new Object[0]);
		}
	    }
	    if (StringUtils.isEmpty(error)) {
		if (StringUtils.isNotBlank(azienda.getCodicefiscale())) {
		    sb.append(azienda.getCodicefiscale());
		} else {
		    sb.append(azienda.getPartitaiva());
		}
		sb.append("-");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmm");
		sb.append(sdf.format(data));
	    }
	} else {
	    error = getMessageFromBundle("pecinbox.message.errorecodicepraticatel.nocf", new Object[0]);
	}
	model.addAttribute("codicePraticaTel", sb.toString());
	model.addAttribute("error", error);
	return new ModelAndView("jsonView");
    }

    @RequestMapping
    public ModelAndView ajaxSwitchFlagLetta(Model model, @RequestParam(value = "codicePec", required = true) String codicePec) {

	String result = "";
	PecInboxId id = new PecInboxId(codicePec);
	PecInbox pec = this.pecInboxService.findById(id);
	boolean executed = false;
	if (null != pec) {
	    boolean newFlagLetta = BooleanUtils.negate(BooleanUtils.toBoolean(pec.getFlagLetta()));
	    result = this.impostaPecLetta(pec, newFlagLetta);
	    if (StringUtils.isEmpty(result)) {
		result = getMessageFromBundle("pecinbox.message.esitoimpostaflagletta.ok", new Object[] { newFlagLetta ? "letta" : "non letta" });
		executed = true;
	    }
	} else {
	    StringBuilder sbMsg = new StringBuilder();
	    sbMsg.append(getMessageFromBundle("pecinbox.message.esitoimpostaflagletta.ko", new Object[0])).append(" ");
	    sbMsg.append(getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec }));
	    result = sbMsg.toString();
	}
	model.addAttribute("message", result);
	model.addAttribute("success", executed);
	model.addAttribute("codicePec", codicePec);
	return new ModelAndView("jsonView");
    }

    @RequestMapping
    public ModelAndView ajaxEvidenziaPEC(Model model, @RequestParam(value = "codicePec", required = true) String codicePec,
	    @RequestParam(value = "evidenzia", required = true) Boolean evidenzia) {

	String result = "";
	PecInboxId id = new PecInboxId(codicePec);
	PecInbox pec = this.pecInboxService.findById(id);
	boolean executed = false;
	String descEvidenza = evidenzia ? "in evidenza" : "non in evidenza";
	Integer codiceResponsabile = null;
	if (null != pec) {
	    try {
		Responsabili respEvidenza = pec.getResponsabileEvidenza();
		Responsabili currentUser = this.getCurrentlyAuthenticatedUserDetails();
		if (respEvidenza != null && respEvidenza.getId() != null && respEvidenza.getId().getCodice() != null) {
		    pec.setResponsabileEvidenza(null);
		    if (!respEvidenza.equals(currentUser)) {
			LoggerCancellazioni.logImpostaPecNonInEvidenza(currentUser.getResponsabile(), respEvidenza.getResponsabile(), codicePec);
		    }
		} else {
		    codiceResponsabile = currentUser.getId().getCodice();
		    pec.setResponsabileEvidenza(currentUser);
		}
		this.pecInboxService.update(pec);
		result = getMessageFromBundle("pecinbox.message.esitomettinevidenza.ok", new Object[] { descEvidenza });
	    } catch (Exception e) {
		log.error("ajaxEvidenziaPEC - errore nell'aggiornamento del record in PEC_INBOX");
		result = getMessageFromBundle("pecinbox.message.esitomettinevidenza.ko", new Object[] { descEvidenza, e.getMessage() });
	    }
	} else {
	    String failReason = getMessageFromBundle("pecinbox.message.pecnotfound", new Object[] { codicePec });
	    result = getMessageFromBundle("pecinbox.message.esitomettinevidenza.ko", new Object[] { descEvidenza, failReason });
	}
	model.addAttribute("message", result);
	model.addAttribute("success", executed);
	model.addAttribute("codicePec", codicePec);
	model.addAttribute("codiceResponsabile", codiceResponsabile);
	return new ModelAndView("jsonView");
    }

    private String getNlaGestioneMailWSURL() {

	return this.pecInboxService.getNlaGestioneMailWSURL();
	//return "http://10.10.45.89:8080/nla-pec/services/nlaGestioneMail.wsdl"; 
    }

    /**
     * Popola il {@link PECInboxFilter} con le impostazioni dell'utente salvate l'ultima volta. In base a software e
     * idcomune vene anche popolato il nome dell'account da cui si recuperano le PEC.
     * 
     * @param request
     * @param filter
     */
    private void populateFilter(HttpServletRequest request, PECInboxFilter filter, Integer idAccount) {

	//PECInboxFilter retVal = new PECInboxFilter();
	// §§§BEGIN§§§
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	//leggo le impostazioni del filtro della PEC dalla tabella delle impostazioni utente
	String userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_DATADA, "", request);
	if (StringUtils.isNotBlank(userConf)) {
	    try {
		Date date = sdf.parse(userConf);
		filter.setDataRicezioneDa(date);
	    } catch (ParseException e) {
		log.warn("loadFilter - il valore {} del parametro di configurazione {} non ha un formato data valido.",
			new Object[] { userConf, WebConstants.CONF_UTENTE_PECINBOX_DATADA });
	    }
	}
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_DATAA, "", request);
	if (StringUtils.isNotBlank(userConf)) {
	    try {
		Date date = sdf.parse(userConf);
		filter.setDataRicezioneA(date);
	    } catch (ParseException e) {
		log.warn("loadFilter - il valore {} del parametro di configurazione {} non ha un formato data valido.",
			new Object[] { userConf, WebConstants.CONF_UTENTE_PECINBOX_DATADA });
	    }
	}
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_JMESA, "", request);
	filter.setjMesaParamString(userConf);
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_LETTI, FiltroPECSiNoTuttiEnum.TUTTI.value(), request);
	filter.setLettiNonLettiString(userConf);
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_ELABORATI, FiltroPECSiNoTuttiEnum.TUTTI.value(),
		request);
	filter.setElaboratiString(userConf);
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_LAVORATI, FiltroPECSiNoTuttiEnum.TUTTI.value(), request);
	filter.setLavoratiString(userConf);
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_RICEVUTE, "false", request);
	filter.setFlagRicevute(BooleanUtils.toBooleanObject(userConf));
	//recupero l'account PEC per idcomune e software
	//MailConfig mcfg = this.mailConfigService.findById(new MailConfigId());
	// @gianpaolot: commentato perchè deveo trovarlo per software idcomune e principal
	MailConfig mcfg = null;
	if (idAccount == null) {
	    mcfg = mailConfigService.findBySoftwareAttiviAndPrincipali(ORMHelper.getSoftware(), true);
	    if (mcfg == null) {
		Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
		List<Responsabilicomuni> responsabilicomuni = responsabilicomuniService.findByOperatore(responsabili);
		String[] codiceComuni = new String[responsabilicomuni.size()];
		int i = 0;
		for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
		    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
		    i++;
		}
		//	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		//		istanza.getComune().getCodicecomune(), false);
		List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
			codiceComuni);
		mcfg = listMailConfig.get(0);
	    }
	} else {
	    mcfg = this.mailConfigService.findById(new PkId(idAccount));
	}
	if (mcfg != null) {
	    filter.setMailAccount(mcfg.getInLoginname());
	    //filter.setDescMailConfigDefault(mcfg.getDescrizioneLunga());
	    filter.setDescMailConfigDefault(mcfg.getDescrizione());
	    filter.setIdMailConfig(mcfg.getId().getCodice());
	}
	/*
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_NONLETTI, "true", request);
	boolVal = BooleanUtils.isTrue(BooleanUtils.toBooleanObject(userConf));
	retVal.setFlagNonLetti(boolVal);
	*/
	/*
	userConf = this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_PECINBOX_USEWS, "false", request);
	boolean boolVal = BooleanUtils.isTrue(BooleanUtils.toBooleanObject(userConf));
	filter.setReadFromServer(boolVal);
	*/
	// §§§END§§§
	//return retVal;
    }

    private void saveFilter(PECInboxFilter filter, HttpServletRequest request) {

	// §§§BEGIN§§§
	//salvo le impostazioni del filtro della PEC nella tabella delle impostazioni utente
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	if (null != filter) {
	    String value = filter.getDataRicezioneDa() == null ? "" : sdf.format(filter.getDataRicezioneDa());
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_DATADA, value, request, null);
	    value = filter.getDataRicezioneA() == null ? "" : sdf.format(filter.getDataRicezioneA());
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_DATAA, value, request, null);
	    value = request.getQueryString();
	    int splitIndex = value.indexOf(JMESA_QUERYSTRING_SPLIT);
	    if (splitIndex > -1) {
		value = value.substring(0, splitIndex);
	    } else {
		value = "";
	    }
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_JMESA, value, request, null);
	    filter.setjMesaParamString(value);
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_LETTI, filter.getLettiNonLettiString(), request, null);
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_ELABORATI, filter.getElaboratiString(), request, null);
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_LAVORATI, filter.getLavoratiString(), request, null);
	    value = filter.getFlagRicevute().toString();
	    this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_RICEVUTE, value, request, null);
	    //this.salvaPreferenza(WebConstants.CONF_UTENTE_PECINBOX_USEWS, BooleanUtils.toStringTrueFalse(filter.isReadFromServer()), request, null);
	}
	// §§§END§§§
    }

    /*
    private List<FiltroType> buildListaFiltri(PECInboxFilter filter) {
    
    	List<FiltroType> filtri = new ArrayList<FiltroType>();
    	// §§§BEGIN§§§
    	if (null != filter) {
    	    FiltroType ft = new FiltroType();
    	    ft.setTipo(TipoFiltroEnum.LETTI.value());
    	    if (filter.getFlagLetti()) {
    		ft.setValore("S");
    	    } else {
    		ft.setValore("N");
    	    }
    	    filtri.add(ft);
    	    ft = new FiltroType();
    	    ft.setTipo(TipoFiltroEnum.NON_LETTI.value());
    	    if (filter.getFlagNonLetti()) {
    		ft.setValore("S");
    	    } else {
    		ft.setValore("N");
    	    }
    	    filtri.add(ft);
    	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
    	    if (filter.getDataRicezioneDa() != null) {
    		ft = new FiltroType();
    		ft.setTipo(TipoFiltroEnum.DATA_DA.value());
    		ft.setValore(sdf.format(filter.getDataRicezioneDa()));
    		filtri.add(ft);
    	    }
    	    if (filter.getDataRicezioneA() != null) {
    		ft = new FiltroType();
    		ft.setTipo(TipoFiltroEnum.DATA_A.value());
    		ft.setValore(sdf.format(filter.getDataRicezioneA()));
    		filtri.add(ft);
    	    }
    	}
    	// §§§END§§§
    	return filtri;
    }
    */
    private List<FiltroType> buildListaFiltriForSynchronization(Date lastSyncDate) {

	List<FiltroType> filtri = new ArrayList<FiltroType>();
	// §§§BEGIN§§§
	FiltroType ft = new FiltroType();
	ft.setTipo(TipoFiltroEnum.LETTI.value());
	ft.setValore("S");
	filtri.add(ft);
	ft = new FiltroType();
	ft.setTipo(TipoFiltroEnum.NON_LETTI.value());
	ft.setValore("S");
	filtri.add(ft);
	if (lastSyncDate != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    ft = new FiltroType();
	    ft.setTipo(TipoFiltroEnum.DATA_DA.value());
	    ft.setValore(sdf.format(lastSyncDate));
	    filtri.add(ft);
	}
	// §§§END§§§
	return filtri;
    }

    private PECMessageHelper populatePECHelper(MessaggioType inMessage, PecInbox tableData) {

	PECMessageHelper outMessage = new PECMessageHelper();
	// §§§BEGIN§§§
	if (inMessage != null) {
	    outMessage.setIdentificativo(inMessage.getIdentificativo());
	    outMessage.setDataRicezione(Utilities.getDate(inMessage.getDataRicezione()));
	    outMessage.setMittenti(inMessage.getMittenti());
	    outMessage.setOggetto(inMessage.getOggetto());
	    outMessage.setLetto(inMessage.isLetto());
	}
	if (tableData != null) {
	    //se il messaggio non è stato scaricato dal WS i dati principali vengono recuperati dal DB
	    if (inMessage == null) {
		outMessage.setIdentificativo(tableData.getId().getId());
		outMessage.setDataRicezione(tableData.getPecDate());
		outMessage.setMittentiString(tableData.getPecFrom());
		outMessage.setOggetto(tableData.getPecSubject());
		outMessage.setLetto(tableData.getFlagLetta());
		outMessage.setDestinatariCCString(tableData.getPecToCC());
		outMessage.setDestinatariString(tableData.getPecTo());
	    }
	    outMessage.setProcessato(BooleanUtils.toBoolean(tableData.getFlagProcessata()));
	    PecStatusHelper status = new PecStatusHelper();
	    status.setDataProtocollo(tableData.getDataprotocollo());
	    status.setIdProtocollo(tableData.getIdprotocollo());
	    status.setNumeroProtocollo(tableData.getNumeroprotocollo());
	    outMessage.setNumeroProtocollo(tableData.getNumeroprotocollo());
	    status.setCancellata(BooleanUtils.toBoolean(tableData.getFlagCancellata()));
	    status.setAllegatoProtocolloPresente(
		    tableData.getOggettoProtocollo() != null && tableData.getOggettoProtocollo().getId().getCodice() != null);
	    status.setAllegatiPresenti(tableData.getAllegatiPec() != null && tableData.getAllegatiPec().size() > 0);
	    if (tableData.getResponsabili() != null) {
		status.setIdOperatore(tableData.getResponsabili().getId().getCodice());
		status.setOperatore(tableData.getResponsabili().getResponsabile());
	    }
	    if (tableData.getResponsabileEvidenza() != null) {
		outMessage.setFkRespInEvidenza(tableData.getResponsabileEvidenza().getId().getCodice());
		outMessage.setRespInEvidenza(tableData.getResponsabileEvidenza().getResponsabile());
	    }
	    Istanze istanza = tableData.getIstanze();
	    if (istanza != null) {
		status.setIdIstanza(istanza.getId().getCodice());
		status.setCodiceIstanza(istanza.getNumeroistanza());
		String idProt = istanza.getFkidprotocollo();
		if (!StringUtils.isEmpty(idProt)) {
		    status.setIdProtocollo(idProt);
		    status.setNumeroProtocollo(istanza.getNumeroprotocollo());
		    status.setDataProtocollo(istanza.getDataprotocollo());
		}
		status.setSoftware(istanza.getSoftware().getCodice());
	    }
	    Movimenti mov = tableData.getMovimenti();
	    if (mov != null) {
		status.setIdMovimento(mov.getId().getCodice());
		status.setCodiceMovimento(mov.getMovimento());
		String idProt = mov.getFkidprotocollo();
		if (!StringUtils.isEmpty(idProt)) {
		    status.setIdProtocollo(idProt);
		    status.setNumeroProtocollo(mov.getNumeroprotocollo());
		    status.setDataProtocollo(mov.getDataprotocollo());
		}
		if (status.getSoftware() == null) {
		    status.setSoftware(mov.getIstanza().getSoftware().getCodice());
		}
	    }
	    outMessage.setStatus(status);
	    outMessage.setDescrizioneAccountMailCfg(tableData.getMailConfig().getDescrizione());
	    outMessage.setIdAccountMailcfg(tableData.getMailConfig().getId().getCodice());
	}
	// §§§END§§§
	return outMessage;
    }

    private Istanze populateIstanzaFromPEC(PecInbox pec) {

	Istanze instance = null;
	if (null != pec) {
	    instance = new Istanze();
	    //predispongo il campo lavori dell'istanza uguale a pec.pecSubject
	    instance.setLavori(pec.getPecSubject());
	    //predispongo la data dell'istanza uguale alla data della pec
	    instance.setData(pec.getPecDate());
	    //	    String orarioData=Utilities.getOrario(pec.getPecDate());
	    //	    if(StringUtils.isNotBlank(orarioData))
	    //	    {
	    //		i
	    //	    }
	    //cerco di individuare il richiedente e/o il titolare legale (azienda richiedente) con domicilio elettronico uguale al mittente della PEC 
	    PECMessageHelper pmh = populatePECHelper(null, pec);
	    List<String> fromAddresses = pmh.getMittenti();
	    List<Anagrafe> mittenti = new ArrayList<Anagrafe>();
	    AnagrafeFilter filter = new AnagrafeFilter();
	    Anagrafe datiFiltro = new Anagrafe();
	    //cerco tutte le anagrafiche non disabilitate
	    datiFiltro.setFlagDisabilitato(0);
	    //dell'IDCOMUNE corrente
	    filter.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
	    filter.setDatiAnagrafe(datiFiltro);
	    for (int i = 0; i < fromAddresses.size(); i++) {
		if (i == 0) {
		    //predispongo il domicilo elettronico della nuova istanza uguale a pec.pecFrom ripulito da eventuali ';'
		    instance.setDomicilioElettronico(fromAddresses.get(i));
		}
		//cerco le anagrafiche con indirizzo PEC uguale a quello della PEC che stiamo elaborando
		datiFiltro.setPec(fromAddresses.get(i));
		mittenti.addAll(anagrafeService.findByFilter(filter));
	    }
	    for (Anagrafe mittente : mittenti) {
		if (mittente.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)) {
		    if (instance.getRichiedente() == null || instance.getRichiedente().getId() == null
			    || instance.getRichiedente().getId().getCodice() == null) {
			instance.setRichiedente(mittente);
		    }
		} else {
		    if (instance.getTitolarelegale() == null || instance.getTitolarelegale().getId() == null
			    || instance.getTitolarelegale().getId().getCodice() == null) {
			instance.setTitolarelegale(mittente);
		    }
		}
	    }
	}
	return instance;
    }

    /**
     * Sincronizza il messaggio PEC ricevuto dal WS passato come argomento con il DB. Se viene inserito un nuovo record
     * in PEC_INBOX viene restituito l'oggetto di dominio corrispondente, se non è stato fatto nessun iniserimento viene
     * restituito null.
     * 
     * @param inMessage
     * @return
     */
    private PecInbox elaboraMessaggioPEC(MessaggioType inMessage, String pecAccount, Integer idAccount) {

	PecInbox pecDB = null;
	PecInbox pecMailServer = populateDomainObject(inMessage, idAccount);
	pecMailServer.setInLoginname(pecAccount);
	// §§§BEGIN§§§
	if (inMessage != null && StringUtils.isNotBlank(inMessage.getIdentificativo())) {
	    PecInboxId pkPec = new PecInboxId(inMessage.getIdentificativo());
	    try {
		pecDB = pecInboxService.findById(pkPec);
	    } catch (Exception e) {
		String dbErr = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "";
		String errMsg = MessageFormat.format("Errore nella lettura dal Database dei dati relativi alla PEC {0}: {1}",
			new Object[] { inMessage.getIdentificativo(), dbErr });
		throw new RuntimeException(errMsg);
	    }
	    if (null == pecDB) {
		try {
		    pecInboxService.insert(pecMailServer);
		} catch (Exception e) {
		    String dbErr = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "";
		    String errMsg = MessageFormat.format("Errore nell'inserimento nel Database dei dati relativi alla PEC {0}: {1}",
			    new Object[] { inMessage.getIdentificativo(), dbErr });
		    throw new RuntimeException(errMsg);
		}
		if (log.isDebugEnabled()) {
		    log.debug("elaboraMessaggioPEC() - inserito nuovo record in PEC_INBOX per il messaggio PEC avente ID={}",
			    new String[] { inMessage.getIdentificativo() });
		}
		return pecMailServer;
	    } else {
		return null;
	    }
	    /* l'informazione se la PEC è stata letta o no non deve più essere sincronizzata con il mail server
	    else if (BooleanUtils.toBoolean(pecDB.getFlagLetta()) != BooleanUtils.toBoolean(pecMailServer.getFlagLetta())
	        || BooleanUtils.toBoolean(pecDB.getFlagProcessata()) != BooleanUtils.toBoolean(pecMailServer.getFlagProcessata())) {
	    pecDB.setFlagLetta(pecMailServer.getFlagLetta());
	    pecDB.setFlagProcessata(pecMailServer.getFlagProcessata());
	    try {
	        pecInboxService.update(pecDB);
	    } catch (Exception e) {
	        String dbErr = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "";
	        String errMsg = MessageFormat.format("Errore nell'aggiornamento nel Database dei dati relativi alla PEC {0}: {1}", new Object[] {
	    	    inMessage.getIdentificativo(), dbErr });
	        throw new RuntimeException(errMsg);
	    }
	    if (log.isDebugEnabled()) {
	        log.debug("elaboraMessaggioPEC() - aggiornato record in PEC_INBOX per il messaggio PEC avente ID={}",
	    	    new String[] { inMessage.getIdentificativo() });
	    }
	    }
	    */
	} else {
	    return null;
	}
	// §§§END§§§
    }

    private PecInbox populateDomainObject(MessaggioType pecMessage, Integer idAccount) {

	PecInbox pec = null;
	// §§§BEGIN§§§
	PecInboxId pkPec = new PecInboxId(pecMessage.getIdentificativo());
	pec = new PecInbox(pkPec);
	if (idAccount == null) {
	    Software sftw = new Software();
	    sftw.setCodice(ORMHelper.getSoftware());
	    pec.setSoftware(sftw);
	} else {
	    MailConfig mailConfig = mailConfigService.findById(new PkId(idAccount));
	    pec.setSoftware(mailConfig.getSoftware());
	}
	XMLGregorianCalendar cal = pecMessage.getDataRicezione();
	pec.setPecDate(Utilities.getDate(cal));
	String str = StringUtils.join(pecMessage.getMittenti().iterator(), PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
	pec.setPecFrom(str);
	str = StringUtils.join(pecMessage.getDestinatari().iterator(), PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
	pec.setPecTo(str);
	pec.setPecSubject(pecMessage.getOggetto());
	pec.setFlagLetta(pecMessage.isLetto());
	str = StringUtils.join(pecMessage.getDestinataricc().iterator(), PECMessageHelper.EMAIL_ADDRESS_SEPARATOR);
	pec.setPecToCC(str);
	MailConfig m = mailConfigService.findById(new PkId(idAccount));
	pec.setMailConfig(m);
	// §§§END§§§
	return pec;
    }

    private void setPECSelected(PecInbox selectedPec, HttpServletRequest request) {

	if (selectedPec != null && request != null) {
	    PecInboxId pecId = selectedPec.getId();
	    if (pecId != null && StringUtils.isNotBlank(pecId.getId())) {
		request.getSession().setAttribute(PECInboxSelectedRowRenderer.SELECTED_PEC_SESSION_ATTR, pecId.getId());
	    }
	}
    }

    private String scaricaPec(PECCommand cmd, BigInteger idAccount, BindingResult result) {

	String retError = null;
	// §§§BEGIN§§§
	//l'istanza di PECINBOX è già stata popolata dal DB e caricata nel command dal metodo chiamante
	PecInbox pec = cmd.getPec();
	/*
	 * questo metodo è invocato in tutti i casi in cui l'utente visualizza il dettaglio della PEC:
	 * dettaglio PEC, protocolla PEC, istanza da PEC e movimeto da PEC.
	 * In tutti questi casi occorre flaggare come già letta la PEC.
	 */
	impostaPecLetta(pec, true);
	String codicePec = pec.getId().getId();
	Set<PecInboxAllegati> allegatiPECSalvati = pec.getAllegatiPec();
	/*
	 * se gli allegati non sono ancora stati salvati nel DB invoco il WS ScaricaAllegatiMessaggio per recuperare 
	 * i dati binari di ciascun allegato PEC, ciscun allegato viene inserito in OGGETTI e collegato alla PEC tramite la tabella PEC_INBOX_ALLEGATI
	 */
	if (allegatiPECSalvati == null || allegatiPECSalvati.size() == 0) {
	    String wsUrl = getNlaGestioneMailWSURL();
	    NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl, this.verticalizzazioneParametriSistemaService);
	    //con poco più  di 20 Mb di allegati 5 min di timeout non bastavano. Portati a 10 min.	    
	    NlaGestioneMail pecWs = null;
	    if (log.isDebugEnabled()) {
		log.debug(
			"scaricaPec - lettura degli allegati del messaggio PEC {} tramite invocazione del WS ScaricaAllegatiMessaggioWS all'indirizzo {}.",
			new Object[] { codicePec, wsUrl });
	    }
	    try {
		pecWs = nlaGestioneMailWSClient.getWSPort();
		//throw new SocketTimeoutException();
	    } catch (Exception e) {
		retError = getMessageFromBundle("pecinbox.message.nomailservice", new Object[] { wsUrl });
		log.error("scaricaPec - " + retError, e);
		if (null != result) {
		    this.copyErrorsToBindingResult(result, cmd.getPec(), true, e);
		}
	    }
	    //List<AllegatoMailType> allegatiPecWs = null;
	    ScaricaAllegatiMessaggioResponse wsResp = null;
	    if (StringUtils.isEmpty(retError)) {
		try {
		    ScaricaAllegatiMessaggioRequest wsReq = new ScaricaAllegatiMessaggioRequest();
		    wsReq.setIdentificativoMessaggio(codicePec);
		    wsReq.setSoftware(ORMHelper.getSoftware());
		    wsReq.setToken(ORMHelper.getToken());
		    wsReq.setIdaccount(idAccount);
		    wsResp = pecWs.scaricaAllegatiMessaggio(wsReq);
		    //allegatiPecWs = wsResp.getAllegati();
		    if (log.isDebugEnabled()) {
			log.debug("scaricaPec - scaricati {} allegati per la PEC {} tramite invocazione del WS ScaricaAllegatiMessaggioWS.",
				new Object[] { wsResp.getAllegati().size(), codicePec });
		    }
		} catch (Exception e) {
		    retError = getMessageFromBundle("pecinbox.message.mailserviceerror", new Object[] { wsUrl, e });
		    log.error("scaricaPec - " + retError, e);
		}
	    }
	    if (null != wsResp) {
		if (null != wsResp.getMessaggio()) {
		    try {
			elaboraAllegatiPecDaWs(cmd, wsResp);
		    } catch (Exception e) {
			retError = getMessageFromBundle("pecinbox.message.salvaallegatierror", new Object[] { e.getMessage() });
			if (null == result) {
			    this.copyErrorsToBindingResult(result, cmd.getPec(), true, e);
			}
		    }
		} else {
		    /*
		     * se la PEC non esiste più sul server di posta il WS mi restituisce una risposta con il riferimento a MessaggioType == null.
		     * In questo caso il record di PEC_INBOX viene flaggato come cancellato.
		     */
		    retError = getMessageFromBundle("pecinbox.message.peccancellataerror", new Object[0]);
		    this.pecInboxService.contrassegnaPecCancellata(pec, true);
		}
	    }
	}
	//se gli allegati della PEC sono già presenti nel DB non invoco + il WS e non serve di scrivere nulla sul DB
	else {
	    elaboraAllegatiPecDaDb(cmd, new ArrayList<PecInboxAllegati>(allegatiPECSalvati));
	}
	// §§§END§§§
	return retError;
    }

    @SuppressWarnings("unused")
    private void elaboraAllegatiPecDaWs(PECCommand cmd, ScaricaAllegatiMessaggioResponse pecWsResponse) throws IOException {

	// §§§BEGIN§§§
	List<AllegatoMailType> allegatiPecWs = pecWsResponse.getAllegati();
	List<PECAttachmentHelper> allegatiPec = new ArrayList<PECAttachmentHelper>(allegatiPecWs.size());
	//il corpo della PEC viene salvato come file di testo insieme agli allegati veri e propri
	MessaggioType pecMsg = pecWsResponse.getMessaggio();
	PECAttachmentHelper allegatoPec = new PECAttachmentHelper();
	//TODO gestione character encoding?
	allegatoPec.setBinaryContent(StringUtils.defaultIfEmpty(pecMsg.getCorpo(), "Nessun contenuto").getBytes());
	StringBuilder sbNomeFile = new StringBuilder();
	sbNomeFile.append(Utilities.cleanFilename(pecMsg.getIdentificativo())).append(".htm");
	allegatoPec.setNomeFile(sbNomeFile.toString());
	//allegatiPec.add(allegatoPec);
	cmd.setCorpo(allegatoPec);
	for (AllegatoMailType allegatoPecWs : allegatiPecWs) {
	    allegatoPec = new PECAttachmentHelper();
	    allegatoPec.setNomeFile(allegatoPecWs.getNomeFile());
	    DataHandler pecDh = allegatoPecWs.getContent();
	    byte[] binaryPEC = null;
	    final InputStream in = pecDh.getInputStream();
	    binaryPEC = org.apache.commons.io.IOUtils.toByteArray(in);
	    in.close();
	    allegatoPec.setBinaryContent(binaryPEC);
	    allegatiPec.add(allegatoPec);
	}
	cmd.setAllegati(allegatiPec);
	this.pecInboxService.elaboraAllegatiPec(cmd.getPec(), cmd.getCorpo(), allegatiPec);
	// §§§END§§§
    }

    private void elaboraAllegatiPecDaDb(PECCommand cmd, List<PecInboxAllegati> allegatiPecDb) {

	// §§§BEGIN§§§
	List<PECAttachmentHelper> allegati = new ArrayList<PECAttachmentHelper>();
	if (null != allegatiPecDb) {
	    for (PecInboxAllegati allegatoDb : allegatiPecDb) {
		PECAttachmentHelper allegato = new PECAttachmentHelper();
		allegato.setCodiceOggetto(allegatoDb.getOggetto().getId().getCodice());
		allegato.setNomeFile(allegatoDb.getOggetto().getNomefile());
		if (!allegatoDb.getFlagCorpo()) {
		    allegati.add(allegato);
		} else {
		    Oggetti datiAllegato = allegatoDb.getOggetto();
		    datiAllegato = this.oggettiService.findById(new PkId(datiAllegato.getId().getCodice()));
		    allegato.setBinaryContent(datiAllegato.getOggetto());
		    cmd.setCorpo(allegato);
		}
	    }
	}
	cmd.setAllegati(allegati);
	// §§§END§§§
    }

    private Anagrafe findRichiedente(PECCommand cmd) {

	Anagrafe retAna = null;
	// §§§BEGIN§§§
	if (null != cmd) {
	    Integer codiceRichiedente = null;
	    Anagrafe tempAna = cmd.getRichiedente();
	    if (null != tempAna && tempAna.getId() != null) {
		codiceRichiedente = tempAna.getId().getCodice();
	    }
	    if (null == codiceRichiedente) {
		Verticalizzazioniparametri vertParam = this.verticalizzazioniService.getVerticalizzazioniparametri(
			WebConstants.VERTICALIZZAZIONE_PEC_CLIENT, WebConstants.VERTICALIZZAZIONE_PEC_CLIENT_RICHIEDENTE_DEFAULT);
		if (null != vertParam) {
		    String vertVal = vertParam.getValore();
		    if (NumberUtils.isNumber(vertVal)) {
			codiceRichiedente = Integer.parseInt(vertVal);
		    }
		} else {
		    log.error(
			    "findRichiedente - impossibile impostare il richiedente di default perchè la verticalizzazione {}.{} non è attiva o non è presente.",
			    new Object[] { WebConstants.VERTICALIZZAZIONE_PEC_CLIENT,
				    WebConstants.VERTICALIZZAZIONE_PEC_CLIENT_RICHIEDENTE_DEFAULT });
		}
	    }
	    if (null != codiceRichiedente) {
		retAna = this.anagrafeService.findById(new PkId(codiceRichiedente));
		cmd.setRichiedente(retAna);
	    }
	}
	// §§§END§§§
	return retAna;
    }

    private String impostaPecLetta(PecInbox pec, boolean letta) {

	String retError = null;
	if (null != pec && null != pec.getId() && StringUtils.isNotEmpty(pec.getId().getId())) {
	    String codicePec = pec.getId().getId();
	    if (BooleanUtils.toBoolean(pec.getFlagLetta()) != letta) {
		String wsUrl = getNlaGestioneMailWSURL();
		NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl, this.verticalizzazioneParametriSistemaService);
		NlaGestioneMail pecWs = null;
		String lettaStatusDesc = letta ? "letto" : "non letto";
		if (log.isDebugEnabled()) {
		    log.debug(
			    "impostaPecLetta - il messaggio PEC {} viene impostato come {} tramite invocazione del WS SetFlagLetturaMessaggio all'indirizzo {}.",
			    new Object[] { codicePec, lettaStatusDesc, wsUrl });
		}
		try {
		    pecWs = nlaGestioneMailWSClient.getWSPort();
		} catch (Exception e) {
		    retError = getMessageFromBundle("pecinbox.message.nomailservice", new Object[] { wsUrl });
		    log.error("impostaPecLetta - " + retError, e);
		}
		SetFlagLetturaMessaggioResponse flagLettaResponse = null;
		try {
		    SetFlagLetturaMessaggioRequest flagLettaRequest = new SetFlagLetturaMessaggioRequest();
		    flagLettaRequest.setFlagValue(letta);
		    flagLettaRequest.setIdentificativoMessaggio(codicePec);
		    flagLettaRequest.setSoftware(ORMHelper.getSoftware());
		    flagLettaRequest.setToken(ORMHelper.getToken());
		    flagLettaResponse = pecWs.setFlagLetturaMessaggio(flagLettaRequest);
		} catch (Exception e) {
		    retError = getMessageFromBundle("pecinbox.message.mailserviceerror", new Object[] { wsUrl });
		    log.error("scaricaPec - " + retError, e);
		}
		if (log.isDebugEnabled()) {
		    log.debug(
			    "impostaPecLetta - la PEC {} è stata impostata come {} nel server di posta tramite invocazione del WS SetFlagLetturaMessaggio. Esito: {}.",
			    new Object[] { codicePec, lettaStatusDesc, flagLettaResponse != null ? flagLettaResponse.getEsito() : "" });
		}
		//se utile analizzare l'esito per sapere se occorre aggiornare il campo anche nel DB
		boolean esitoOk = true;
		if (esitoOk) {
		    pec.setFlagLetta(letta);
		    this.pecInboxService.update(pec);
		    if (log.isDebugEnabled()) {
			log.debug("impostaPecLetta - la PEC {} è stata impostata come {} nel DB.", new Object[] { codicePec, lettaStatusDesc });
		    }
		}
	    } else {
	    }
	}
	return retError;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public void ajaxEndoInSession(@RequestParam("codiceinventario") Integer codiceinventario, @RequestParam("selezionato") Boolean selezionato,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Set<Integer> endosInSession = (Set<Integer>) request.getSession().getAttribute(SET_ENDO_IN_SESSION);
	if (endosInSession == null) {
	    endosInSession = new HashSet<Integer>();
	    request.getSession().setAttribute(SET_ENDO_IN_SESSION, endosInSession);
	}
	if (BooleanUtils.isTrue(selezionato)) {
	    endosInSession.add(codiceinventario);
	} else {
	    endosInSession.remove(codiceinventario);
	}
	try {
	    if (endosInSession.size() > 1) {
		inventarioprocedimentiService.checkEndoIncompatibili(endosInSession);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    endosInSession.remove(codiceinventario);
	    String err = "";
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    err += getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }) + "\n";
		}
	    } else {
		err = this.renderErrors(e, false);
	    }
	    // response.getWriter().write(err);
	    response.setStatus(500);
	    response.getWriter().write(err);// throw new RuntimeException(err);
	}
    }

    @RequestMapping
    public ModelMap ajaxListaEndo(@RequestParam("codiceAlberoProc") Integer codiceAlberoProc, HttpServletRequest request,
	    HttpServletResponse response) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
	Alberoproc alberoproc = alberoprocService.findById(new PkId(codiceAlberoProc));
	List<AlberoprocEndo> alberoprocEndos = alberoprocEndoService.findEndoprocedimentiHierarchy(alberoproc);
	ModelMap model = new ModelMap(alberoprocEndos);
	Set<Integer> endosInSession = new HashSet<Integer>();
	for (AlberoprocEndo alberoprocEndo : alberoprocEndos) {
	    if (BooleanUtils.isTrue(alberoprocEndo.getFlagRichiesto())) {
		endosInSession.add(alberoprocEndo.getInventarioprocedimento().getId().getCodice());
	    }
	}
	request.getSession().setAttribute(SET_ENDO_IN_SESSION, endosInSession);
	model.addAttribute("alberoprocEndosList", alberoprocEndos);
	model.addAttribute("alberoproc", alberoproc);
	return model;
    }

    @RequestMapping
    public void ajaxResetEndo(HttpServletRequest request, HttpServletResponse response) {

	request.getSession().removeAttribute(SET_ENDO_IN_SESSION);
    }

    @SuppressWarnings("unchecked")
    private void populateEndoForInsert(HttpServletRequest request, InserimentoPraticaRequest iprequest) {

	Set<Integer> codiciEndosInSession = (Set<Integer>) request.getSession().getAttribute(SET_ENDO_IN_SESSION);
	if (codiciEndosInSession != null) {
	    if (codiciEndosInSession.size() > 0) {
		List<ProcedimentoType> prs = new ArrayList<ProcedimentoType>();
		for (Integer codice : codiciEndosInSession) {
		    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(new PkId(codice));
		    ProcedimentoType p = new ProcedimentoType();
		    p.setCodice(String.valueOf(endo.getId().getCodice()));
		    p.setDescrizione(endo.getProcedimento());
		    p.setDataAttivazione(iprequest.getDettaglioPratica().getDataPratica());
		    prs.add(p);
		}
		if (prs.size() > 0) {
		    iprequest.getDettaglioPratica().getProcedimenti().clear();
		    iprequest.getDettaglioPratica().getProcedimenti().addAll(prs);
		}
	    }
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Software> softwareList = new ArrayList<Software>();
	List<Software> softwareListCreaPraticaOrMovimento = new ArrayList<Software>();
	Responsabili currentUser = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomuni = responsabilicomuniService.findByOperatore(currentUser);
	List<MailConfig> listMailConfig = new ArrayList<MailConfig>();
	String[] codiciComune = new String[responsabilicomuni.size()];
	for (int i = 0; i < responsabilicomuni.size() - 1; i++) {
	    codiciComune[i] = responsabilicomuni.get(i).getComune().getCodicecomune();
	}
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(currentUser, new Software());
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		if (BooleanUtils.isTrue(responsabilisoftware.getSoftware().getModuloopzionale())) {
		    softwareListCreaPraticaOrMovimento.add(responsabilisoftware.getSoftware());
		}
	    }
	} else {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    softwareListCreaPraticaOrMovimento.add(software);
	}
	Software software = softwareService.findById(ORMHelper.getSoftware());
	listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiciComune);
	softwareList.add(software);
	List<MailConfig> configsTemp1 = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), new String[0]);
	listMailConfig.addAll(configsTemp1);
	Collections.sort(listMailConfig, new MailConfigComparator());
	model.addAttribute("listMailConfig", listMailConfig);
	Boolean sbloccoAttivo = StringUtils.isNotEmpty(this.verticalizzazioniService.getVerticalizzazioniparametriValore(
		WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI, WebConstants.VERTICALIZZAZIONE_GEST_CANCELLAZIONI_PWD_SBLOCCO_PEC));
	model.addAttribute("operatore", currentUser);
	model.addAttribute("softwareList", softwareList);
	model.addAttribute("softwareListCreaPraticaOrMovimento", softwareListCreaPraticaOrMovimento);
	model.addAttribute("sbloccoAttivo", sbloccoAttivo);
    }

    @Override
    protected void fixMergeEntityProperty(PecInbox entity) {

	// 
    }

    @Override
    protected void fixRenderEntityProperty(PecInbox entity) {

	// 
    }
}
