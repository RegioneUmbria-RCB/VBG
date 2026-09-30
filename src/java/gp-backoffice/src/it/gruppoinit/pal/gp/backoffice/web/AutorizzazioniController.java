package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

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

import com.fasterxml.jackson.core.JsonProcessingException;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.domain.DehorsCfg;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniConcessioniDatiGenerali;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniExportHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzaAutConcHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.domain.helper.StampaAutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.ConcessioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.domain.web.SpostaPresenzeCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidaEliminazioneAutConcCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneDataCessazioneSubentroCommand;
import it.gruppoinit.pal.gp.core.domain.web.ValidazioneOccupanteCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoCancellazioneAutOConc;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaDataCessazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EsitoModificaOccupante;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneEnum;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.AutorizzazioniComposteSpostaPresenzeDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.ConfigurazioneWSAtti;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.IConfigurazioneResolverService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.WSAttiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.ElencoFirmatariResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.IstanzecollegateService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.jmesa.AutorizzazioniSubentriTable;
import it.gruppoinit.pal.gp.core.jmesa.AutorizzazioniTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;
import it.gruppoinit.pal.gp.core.service.DehorsCfgService;
import it.gruppoinit.pal.gp.core.service.DehorsMqIstanzeService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MercatiAudPresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniAccessiHelper;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoGiornoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@SessionAttributes(value = { "autorizzazioni", "qString", "autorizzazioniFilter", "autorizzazioniCommand", "concessioniCommand",
    "autorizzazioniExportHelper", "validazioneOccupanteCommand", "validazioneDataCessazioneSubentroCommand", "validaEliminazioneAutConcCommand" })
@Controller
public class AutorizzazioniController extends BaseController<Autorizzazioni> {

    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniController.class);
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    @Autowired
    private ConcessionitipiService concessionitipiService;
    @Autowired
    private DehorsCfgService dehorsCfgService;
    @Autowired
    private DehorsMqIstanzeService dehorsMqIstanzeService;
    @Autowired
    private IAttivitaService iAttivitaService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
    private final static String AUTORIZZAZIONI_FILTER_IN_SESSION = "AUTORIZZAZIONI_FILTER_IN_SESSION";
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private IstanzecollegateService istanzecollegateService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private ResponsabilicomuniService responsabiliComuniService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private MercatiAudPresenzeService mercatiAudPresenzeService;
    private WSAttiService wsAttiService;
    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private IConfigurazioneResolverService configurazioneService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    private ICartograficoService cartograficoService;

    @Autowired
    public void setConfigurazioneService(IConfigurazioneResolverService configurazioneService) {

	this.configurazioneService = configurazioneService;
    }

    @Autowired
    public void setWsAttiService(WSAttiService wsAttiService) {

	this.wsAttiService = wsAttiService;
    }

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Autowired
    public void setCartograficoService(ICartograficoService cartograficoService) {

	this.cartograficoService = cartograficoService;
    }

    @RequestMapping
    public String create(Model model, @RequestParam("codiceIstanza") Integer codIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codIstanza));
	if (istanza != null) {
	    checkAccessoInformazioni(istanza, false);
	}
	IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
	istanzaAutConcHelper.setIstanza(istanza);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	boolean mercatiConfigurazione = mercatiService.existsRecords(ft);
	boolean isAttivaConfigurazioneSpuntista = mercatiConfigurazioneService.isAttivaConfigurazioneSpuntista();
	model.addAttribute("isAttivaConfigurazioneSpuntista", isAttivaConfigurazioneSpuntista);
	model.addAttribute("mercatiConfigurazione", mercatiConfigurazione);
	model.addAttribute("istanzaAutConcHelper", istanzaAutConcHelper);
	return "autorizzazioni/listDaIstanza";
    }

    /**
     * 
     * @param model
     * @param resetAttrs
     * @param codiceIstanza
     *            : Utilizzato per la ricerca delle autorizzazioni nella funzionalità per gestione degli spuntisti
     * @param modalita_ricerca
     *            : Definisce la funzionalità per cui stiamo utilizzando a ricerca
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "resetAttrs", required = false) String resetAttrs,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca, HttpServletRequest request,
	    HttpServletResponse response) {

	AutorizzazioniFilter autorizzazioniFilter = new AutorizzazioniFilter();
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ORDINAMENTO_AUTORIZZAZIONI, DAOOrderTypeEnum.ASC.toString(), request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_AUTORIZZAZIONI,
		"autoriznumero,autorizdata,tipologiaregistro.trDescrizione", request);
	String orderFiled = (String) request.getAttribute(WebConstants.CONF_UTENTE_CAMPI_ORDINAMENTO_AUTORIZZAZIONI);
	autorizzazioniFilter.setOrderBy(orderFiled);
	if (StringUtils.isBlank(resetAttrs)) {
	    AutorizzazioniFilter _autorizzazioniFilter = (AutorizzazioniFilter) request.getSession(false).getAttribute("autorizzazioniFilter");
	    if (_autorizzazioniFilter != null) {
		autorizzazioniFilter = _autorizzazioniFilter;
	    }
	}
	// VERIFICO CHE IN SESSION NON SIA IMPOSTATO IL FILTRO NEL CASO LO RIMUOVO
	if (request.getSession().getAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION) != null) {
	    request.getSession().removeAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION);
	}
	model.addAttribute("autorizzazioniFilter", autorizzazioniFilter);
	// Codice istanza che genera la richiesta di partecipare ad una spunta con un'autorizzazione
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("TIPO_SEARCH_AUT", WebConstants.SEARCH_AUT_DEFAULT);
	if (modalita_ricerca != null) {
	    model.addAttribute("TIPO_SEARCH_AUT", modalita_ricerca);
	}
	return "autorizzazioni/search";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("autorizzazioniFilter") AutorizzazioniFilter autorizzazioniFilter,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "modalita_ricerca", required = false) Integer modalita_ricerca, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	// FILTRO VIENE PRESO DALLA SESSIONE PER RICOSTRUIRE LA RICERCA NEI VARI HISTORY BACK
	if (request.getSession().getAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION) == null) {
	    request.getSession().setAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION, autorizzazioniFilter);
	} else {
	    autorizzazioniFilter = (AutorizzazioniFilter) request.getSession().getAttribute(AUTORIZZAZIONI_FILTER_IN_SESSION);
	}
	if (request.getParameter("autorizzazioniFilter.cercaTraSubentrate") != null) {
	    autorizzazioniFilter
		    .setCercaTraSubentrate(BooleanUtils.toBoolean((String) request.getParameter("autorizzazioniFilter.cercaTraSubentrate")));
	}
	autorizzazioniFilter.setEscludiConcessioni(true);
	fixFilter(autorizzazioniFilter);
	String htmlTable = "";
	boolean richiediRicercaSuSubentrate = false;
	if (!autorizzazioniFilter.getCercaTraSubentrate()) {
	    GenerateTable<Autorizzazioni> autorizzazioniTable = new AutorizzazioniTable(autorizzazioniFilter, codiceIstanza, modalita_ricerca);
	    htmlTable = autorizzazioniTable.createJMesaList(request, response, "label.autorizzazioni", "autorizzazioni_id", true);
	    richiediRicercaSuSubentrate = !autorizzazioniFilter.getCercaTraSubentrate()
		    && (WebConstants.SEARCH_AUT_DEFAULT.equals(modalita_ricerca) || modalita_ricerca == null);
	} else {
	    GenerateTable<AutorizzazioniSubentri> autorizzazioniTable = new AutorizzazioniSubentriTable(autorizzazioniFilter, codiceIstanza);
	    htmlTable = autorizzazioniTable.createJMesaList(request, response, "label.autorizzazioni", "autorizzazioni_id", false);
	}
	if (htmlTable == null) {
	    return null;
	}
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getAutorizzazioni().getElenco();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	model.addAttribute("richiediRicercaSuSubentrate", richiediRicercaSuSubentrate);
	model.addAttribute("htmltable", htmlTable);
	model.addAttribute("autorizzazioniFilter", autorizzazioniFilter);
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("TIPO_SEARCH_AUT", WebConstants.SEARCH_AUT_DEFAULT);
	if (modalita_ricerca != null) {
	    model.addAttribute("TIPO_SEARCH_AUT", modalita_ricerca);
	}
	return "autorizzazioni/list";
    }

    @RequestMapping
    public String listDaGestionePresenze(Model model, @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Autorizzazioni autorizzazioni = new Autorizzazioni();
	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	autorizzazioni.setAnagrafe(anagrafe);
	AutorizzazioniFilter filter = new AutorizzazioniFilter();
	filter.setSoloFlagManifestazioni(true);
	filter.setIncludiCessate(true);
	filter.setEscludiLeAutorizzazioniCollegate(true);
	filter.setAnagrafe(anagrafe);
	List<Autorizzazioni> list = autorizzazioniService.findByFilter(filter, null, null);
	boolean export = createJMesaExport(request, response, list);
	if (export) {
	    return null;
	}
	Mercati mercati = mercatiService.findById(new PkId(new Integer(request.getParameter("codiceMercato"))));
	// FIXME prepopolare i campi dell'aut e della catMerc da i dyn2dati
	// VECCHIA LOGICA
	boolean useCatMerc = mercatipresenzeTService.checkSeUsareCatMerc(mercati.getManifestazione());
	if (useCatMerc) {
	    String[] catMercList = mercatipresenzeTService.findComboCategorieMerceologiche();
	    model.addAttribute("catMercList", catMercList);
	    model.addAttribute("useCatMerc", "useCatMerc");
	}
	//NUOVA LOGICA
	List<CodiceDescrizioneBean> listaCategorieMerceologicheGiornataMercatoAmmesse = new ArrayList<CodiceDescrizioneBean>();
	List<CodiceDescrizioneBean> tutte = autorizzazioniService.findAttivitaInAutorizzazioni();
	String attivitaEscluse = "";
	String codiceSc = this.comportamentiMercatiService.codiceIstatBattitori();
	if (StringUtils.isNotBlank(StringUtils.defaultString(codiceSc).trim())) {
	    attivitaEscluse = codiceSc.trim();
	}
	boolean add = true;
	for (CodiceDescrizioneBean codiceDescrizioneBean : tutte) {
	    add = true;
	    if (StringUtils.isNotBlank(attivitaEscluse) && attivitaEscluse.indexOf(codiceDescrizioneBean.getCodice()) >= 0) {
		add = false;
	    }
	    if (add) {
		listaCategorieMerceologicheGiornataMercatoAmmesse.add(codiceDescrizioneBean);
	    }
	}
	model.addAttribute("listaCategorieMerceologicheGiornataMercatoAmmesse", listaCategorieMerceologicheGiornataMercatoAmmesse);
	// fine logica
	// creo la query string da salvare in sessione per non perdere i valori dopo la insert
	String d = request.getParameter("dataFine");
	String dataFineqString = "";
	if (StringUtils.isNotBlank(d)) {
	    dataFineqString = "&dataFine=" + d;
	}
	String qString = "codiceMercato=" +
		request.getParameter("codiceMercato") +
		"&usoMercato=" +
		request.getParameter("usoMercato") +
		"&giornoMercato=" +
		request.getParameter("giornoMercato") +
		"&idPosteggio=" +
		request.getParameter("idPosteggio") +
		"&codice=" +
		request.getParameter("codice") +
		"&codiceAnagrafe=" +
		codiceAnagrafe +
		"&occupante=" +
		request.getParameter("occupante") +
		"&flag=" +
		request.getParameter("flag") +
		StringUtils.defaultIfEmpty(dataFineqString, "");
	model.addAttribute("qString", qString);
	model.addAttribute("autorizzazioni", autorizzazioni);
	model.addAttribute("listAutorizzazioni", list);
	return "autorizzazioni/listDaGestionePresenze";
    }

    @RequestMapping
    public String listDaAnagrafe(Model model, @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	Autorizzazioni autorizzazioni = new Autorizzazioni();
	Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	autorizzazioni.setAnagrafe(anagrafe);
	String softwareCorrente = ORMHelper.getSoftware();
	List<Software> sas = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails(), true);
	Map<String, List<Autorizzazioni>> mList = new HashMap<String, List<Autorizzazioni>>();
	AutorizzazioniFilter filter = new AutorizzazioniFilter();
	filter.setIncludiCessate(true);
	filter.setAnagrafe(anagrafe);
	for (Software sa : sas) {
	    ORMHelper.setSoftware(sa.getCodice());
	    List<Autorizzazioni> list = autorizzazioniService.findByFilter(filter, null, null);
	    if (!list.isEmpty()) {
		mList.put(sa.getCodice() + "|" + sa.getDescrizione(), list);
	    }
	}
	ORMHelper.setSoftware(softwareCorrente);
	model.addAttribute("autorizzazioni", autorizzazioni);
	model.addAttribute("mList", mList);
	return "autorizzazioni/listDaAnagrafe";
    }

    @RequestMapping
    public String createConcessione(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	ConcessioniCommand concessioniCommand = new ConcessioniCommand();
	concessioniCommand.setViewMode(ConcessioniCommand.CONCESSIONE_VIEW);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza != null) {
	    checkAccessoInformazioni(istanza, true);
	}
	AutorizzazioniConcessioni concessione = autorizzazioniService.precompilaConcessione(codiceIstanza);
	if (concessioniCommand.getAutorizzazione() == null) {
	    concessioniCommand.setAutorizzazione(new Autorizzazioni());
	}
	concessioniCommand.setIstanza(istanza);
	concessioniCommand.setEntity(new AutorizzazioniConcessioniDatiGenerali());
	fixRenderConcessioniEntityProperty(concessione);
	fixRenderConcessioniCommandProperty(concessioniCommand.getEntity());
	concessioniCommand.setConcessioneInsert(concessione);
	concessioniCommand.setDisplayMode(BaseCommand.NEW);
	concessioniCommand.setInserisciAutorizzazione(false);
	// Qui viene fatto il "model.addAttribute("concessioniCommand", concessioniCommand);"
	setPageAttributesConcessioni(model, concessioniCommand, null);
	if (request.getParameter("decorator") != null) {
	    return "autorizzazioni/formConcessionePopup";
	}
	return "autorizzazioni/formConcessione";
    }

    @RequestMapping
    public String spostaPresenze(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request)
	    throws JsonProcessingException {

	SpostaPresenzeCommand spostaPresenzeCommand = new SpostaPresenzeCommand();
	List<AutorizzazioniComposteSpostaPresenzeDTO> autorizzazioniSorgenteList = autorizzazioniService
		.findAutorizzazioniComposteSpostaPresenze(codiceIstanza, ORMHelper.getIdcomune());
	// Verifica se l'operatore ha l'autorizzazione a interagire con questa istanza
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza != null) {
	    checkAccessoInformazioni(istanza, true);
	}
	spostaPresenzeCommand.setCodiceIstanza(codiceIstanza);
	model.addAttribute("spostaPresenzeCommand", spostaPresenzeCommand);
	model.addAttribute("autorizzazioniSorgenteList", autorizzazioniSorgenteList);
	model.addAttribute("codiceIstanza", codiceIstanza);
	return "autorizzazioni/formSpostaPresenze";
    }

    @RequestMapping
    public String confermaSpostaPresenze(Model model, @ModelAttribute("spostaPresenzeCommand") SpostaPresenzeCommand spostaPresenzeCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	Integer codiceSorgente = spostaPresenzeCommand.getAutorizzazioneSorgente().getId();
	Integer codiceDestinataria = spostaPresenzeCommand.getCodiceAutorizzazioneDestinataria();
	Integer codiceIstanza = spostaPresenzeCommand.getCodiceIstanza();
	System.out.println("spostamento presenze in corso");
	log.info("spostamento presenze in corso");
	mercatiAudPresenzeService.spostaPresenze(codiceSorgente, codiceDestinataria);
	log.info("spostamento confermato");
	System.out.println("spostamento confermato");
	status.setComplete();
	// Per tornare alla pagina precedente della listDaIstanza
	return "redirect:create.htm?status_msg=02&codiceIstanza=" + codiceIstanza;
    }

    /**
     * Il metodo è utilizzato per tornare alla pagina di creazione di un nuova concessione quando si va ad inserire un
     * nuovo posteggio, nel momento della creazione della concessione stessa. Questo permette di tornare alla pagina di
     * inserimento mantenendo i campi già popolati.
     * 
     * @param codiceIstanza
     * @param model
     * @param concessioniCommand
     * @param request
     * @return
     */
    @RequestMapping
    public String createCommandPopolato(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, HttpServletRequest request) {

	concessioniCommand.setViewMode(ConcessioniCommand.CONCESSIONE_VIEW);
	concessioniCommand.setDisplayMode(BaseCommand.NEW);
	concessioniCommand.setInserisciAutorizzazione(false);
	setPageAttributesConcessioni(model, concessioniCommand, null);
	return "autorizzazioni/formConcessione";
    }

    /**
     * Va alla funzionalità di inserimento di un nuovo posteggio per il mercato passato
     * 
     * @param model
     * @param concessioniCommand
     * @param request
     * @return
     */
    @RequestMapping
    public String addPosteggio(Model model, @RequestParam("codiceMercato") Integer codiceMercato,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, HttpServletRequest request) {

	String goTo = "../mercatid/create.htm?codicemercato=" + codiceMercato;
	String returnTo = "../autorizzazioni/createCommandPopolato.htm?codiceIstanza=" +
		concessioniCommand.getIstanza().getId().getCodice() +
		"&software=" +
		ORMHelper.getSoftware();
	try {
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	    returnTo = URLEncoder.encode(returnTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}
	model.addAttribute("concessioniCommand", concessioniCommand);
	return "redirect:../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + returnTo;
    }

    /**
     * Va alla funzionalità di modifica di un nuovo posteggio per il mercato passato
     * 
     * @param model
     * @param idPosteggio
     * @param idAutorizzazione
     * @param codiceIstanza
     * @param request
     * @return
     */
    @RequestMapping
    public String updatePosteggio(Model model, @RequestParam("idPosteggio") Integer idPosteggio,
	    @RequestParam("idAutorizzazione") Integer idAutorizzazione, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    HttpServletRequest request) {

	String goTo = "../mercatid/view.htm?codice=" + idPosteggio;
	String returnTo = "../autorizzazioni/viewConcessione.htm?codiceIstanza=" + codiceIstanza + "&codiceAutorizzazione=" + idAutorizzazione;
	try {
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	    returnTo = URLEncoder.encode(returnTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}
	//model.addAttribute("concessioniCommand", concessioniCommand);
	return "redirect:../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + returnTo;
    }

    @RequestMapping
    public String updateAssegnaPosteggioLibero(Model model, @RequestParam("codiceConcessione") Integer codiceConcessione,
	    @RequestParam("idPosteggio") Integer idPosteggio, @RequestParam("idAutorizzazione") Integer idAutorizzazione,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request) {

	String returnTo = "redirect:../autorizzazioni/viewConcessione.htm?codiceIstanza=" +
		codiceIstanza +
		"&codiceAutorizzazione=" +
		idAutorizzazione;
	LoggerUpdaterecord.log("#ASSEGNAZIONE_POSTEGGIO_LIBERO#", getCurrentlyAuthenticatedUserDetails());
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	AutorizzazioniConcessioni conc = autorizzazioniConcessioniService.findById(new PkId(codiceConcessione));
	String messaggio = "#ASSEGNAZIONE_POSTEGGIO_LIBERO#Conc [" +
		conc.getAutorizzazioniByFkAutconcAutatt().getTransientEstremiAut() +
		"], dal posteggio [" +
		conc.getMercatiD().getCodiceposteggio() +
		"] al posteggio ]" +
		posteggio.getCodiceposteggio() +
		"]";
	LoggerUpdaterecord.log(messaggio, getCurrentlyAuthenticatedUserDetails());
	conc.setMercatiD(posteggio);
	autorizzazioniConcessioniService.update(conc);
	return returnTo;
    }

    /**
     * Il metodo è utilizzato per tornare alla pagina di modifica di una concessione quando si va a mofificare un
     * posteggio, nel momento della creazione della concessione stessa. Questo permette di tornare alla pagina di
     * inserimento mantenendo i campi già popolati.
     * 
     * @param codiceIstanza
     * @param model
     * @param concessioniCommand
     * @param request
     * @return
     */
    @RequestMapping
    public String viewCommandPopolato(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, HttpServletRequest request) {

	concessioniCommand.setViewMode(ConcessioniCommand.CONCESSIONE_VIEW);
	//concessioniCommand.setDisplayMode(BaseCommand.NEW);
	setPageAttributesConcessioni(model, concessioniCommand, null);
	return "autorizzazioni/formConcessione";
    }

    /**
     * Viene richiamato quando in inserimento di una concessione si sceglie un registro.
     * 
     * @param codiceIstanza
     * @param codiceRegistro
     * @param model
     * @param concessioniCommand
     * @param request
     * @return
     */
    @RequestMapping
    public String updateRegistroConcessioneAutColl(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceRegistro") Integer codiceRegistro, Model model,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, HttpServletRequest request) {

	//	Autorizzazioni autCollegata = autorizzazioniService.findById(new PkId(codiceAutorizzazioneColl));
	//	if (null != autCollegata) {
	//	    autorizzazioniService.updateRegistro(autCollegata, codiceRegistro);
	//	}
	//	return "redirect:viewConcessione.htm?codiceAutorizzazione=" + codiceAutorizzazione + "&codiceIStanza=" + codiceIstanza;
	AutorizzazioniConcessioni concessioni = concessioniCommand.getConcessioneInsert();
	Autorizzazioni aut = concessioni.getAutorizzazioniByFkAutconcAutcoll();
	autorizzazioniService.updateRegistroConcessione(aut, codiceRegistro);
	concessioniCommand.getConcessioneInsert().setAutorizzazioniByFkAutconcAutcoll(aut);
	// fixRenderConcessioniEntityProperty(concessioni);
	// concessioniCommand.setEntity(concessioni);
	concessioniCommand.setDisplayMode(BaseCommand.NEW);
	setPageAttributesConcessioni(model, concessioniCommand, concessioni.getAutorizzazioniByFkAutconcAutatt());
	setPageAttributes(model);
	if (request.getParameter("decorator") != null) {
	    return "autorizzazioni/formConcessionePopup";
	}
	return "autorizzazioni/formConcessione";
    }

    @RequestMapping
    public String insertConcessione(Model model, @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Autorizzazioni autCollegata = null;
	AutorizzazioniConcessioni concessione = concessioniCommand.getConcessioneInsert();
	fixMergeConcessioniEntityProperty(concessione);
	Autorizzazioni autorizzazione = concessioniCommand.getAutorizzazione();
	if (autorizzazione != null && autorizzazione.getIstanza() != null && autorizzazione.getIstanza().getId() != null
		&& autorizzazione.getIstanza().getId().getCodice() != null) {
	    Istanze istanza = istanzeService.findById(new PkId(autorizzazione.getIstanza().getId().getCodice()));
	    if (istanza != null) {
		checkAccessoInformazioni(istanza, true);
	    }
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice"), "")
		.equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    concessione.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(anagrafe);
	} else {
	    concessione.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice"), "")
		.equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    concessione.getAutorizzazioniByFkAutconcAutatt().setOccupante(anagrafe);
	} else {
	    concessione.getAutorizzazioniByFkAutconcAutatt().setOccupante(null);
	}
	if (EntityUtils.getNestedProperty(concessione.getMercati(), "id.codice") != null) {
	    Mercati mercati = mercatiService.findById(new PkId(concessione.getMercati().getId().getCodice()));
	    concessione.setMercati(mercati);
	}
	if (EntityUtils.getNestedProperty(concessione.getMercatiUso(), "id.codice") != null) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(concessione.getMercatiUso().getId().getCodice()));
	    concessione.setMercatiUso(mercatiUso);
	}
	if (EntityUtils.getNestedProperty(concessione.getMercatiD(), "id.codice") != null) {
	    MercatiD mercatiD = mercatiDService.findById(new PkId(concessione.getMercatiD().getId().getCodice()));
	    concessione.setMercatiD(mercatiD);
	}
	try {
	    if (!concessioniCommand.isInserisciAutorizzazione()) {
		// se non si vuole inserire l'autorizzazione collegata alla concessione
		concessione.setAutorizzazioniByFkAutconcAutcoll(null);
	    } else {
		if (concessioniCommand.getAutorizzazioneAssociata().getId().getCodice() != null) {
		    autCollegata = autorizzazioniService.findById(new PkId(concessioniCommand.getAutorizzazioneAssociata().getId().getCodice()));
		    concessione.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
		}
	    }
	    SessionDetails sessionDetails = getSessionDetails(request);
	    String token = sessionDetails.getToken();
	    if (token == null) {
		throw new RuntimeException("Nessun Token trovato in sessione!!! Contattare l'assistenza");
	    }
	    autorizzazioniService.insertConcessione(concessione);
	} catch (Exception e) {
	    if (autCollegata != null) {
		autCollegata = autorizzazioniService.findById(new PkId(concessioniCommand.getAutorizzazioneAssociata().getId().getCodice()));
		concessione.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
	    }
	    fixRenderConcessioniEntityProperty(concessione);
	    copyErrorsToBindingResult(result, concessione, true, e);
	    setPageAttributesConcessioni(model, concessioniCommand, autorizzazione);
	    if (request.getParameter("decorator") != null) {
		return "autorizzazioni/formConcessionePopup";
	    }
	    return "autorizzazioni/formConcessione";
	}
	status.setComplete();
	if (request.getParameter("decorator") != null) {
	    return "redirect:viewConcessione.htm?codiceAutorizzazione=" +
		    concessione.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() +
		    "&codiceIstanza=" +
		    concessione.getAutorizzazioniByFkAutconcAutatt().getIstanza().getId().getCodice() +
		    "&status_msg=01&decorator=popup";
	}
	return "redirect:viewConcessione.htm?codiceAutorizzazione=" +
		concessione.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() +
		"&codiceIstanza=" +
		concessione.getAutorizzazioniByFkAutconcAutatt().getIstanza().getId().getCodice() +
		"&status_msg=01";
    }

    @RequestMapping
    public String createScambioPosteggio(Model model, @RequestParam("codiceConcessione") Integer codiceConcessione,
	    @RequestParam("codiceAutorizzazione") Integer codiceAutorizzazione, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    HttpServletRequest request) {

	List<Concessionicausali> concessionicausalisCess = concessionicausaliService.findAllbyCausaleStorico(true);
	List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	ConcessioniCommand concessioniCommand = new ConcessioniCommand();
	Autorizzazioni autorizzazione = autorizzazioniService.findById(new PkId(codiceAutorizzazione));
	AutorizzazioniConcessioni entity = autorizzazioniConcessioniService.findById(new PkId(codiceConcessione));
	concessioniCommand.setAutorizzazione(autorizzazione);
	concessioniCommand.setConcessioneInsert(entity);
	fixRenderConcessioniEntityProperty(entity);
	model.addAttribute("concessioniCommand", concessioniCommand);
	model.addAttribute("concessionicausalisCess", concessionicausalisCess);
	model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	return "autorizzazioni/formCreateScambioPosteggio";
    }

    @RequestMapping
    public String scambioPosteggio(Model model, @RequestParam("codiceConcPartenza") Integer codiceConcPartenza,
	    @RequestParam(required = false, value = "codiceConcDestinazione") Integer codiceConcDestinazione,
	    @RequestParam("codicePosteggioDestinazione") Integer codicePosteggioDestinazione,
	    @RequestParam(required = false, value = "codiceCausaleCessazione") Integer codiceCausaleCessazione,
	    @RequestParam(required = false, value = "codiceCausaleAcquisizione") Integer codiceCausaleAcquisizione, HttpServletRequest request) {

	AutorizzazioniConcessioni autconc = autorizzazioniConcessioniService.findById(new PkId(codiceConcPartenza));
	try {
	    if (codiceConcDestinazione != null && codicePosteggioDestinazione.equals(autconc.getMercatiD().getId().getCodice())) {
		String errPosteggiUguali = getMessageFromBundle("service_error.posteggio_part_e_dest_uguali", null);
		throw new BusinessValidationException(errPosteggiUguali);
	    }
	    autorizzazioniService.updateScambiaPosteggio(codiceConcPartenza, codiceConcDestinazione, codiceCausaleCessazione,
		    codiceCausaleAcquisizione, codicePosteggioDestinazione);
	} catch (Exception e) {
	    fixRenderConcessioniEntityProperty(autconc);
	    FlashMessages.getWarnings().add("si è verificato un errore imprevisto nello scambio posteggio: " + e.toString());
	    log.error("scambioPosteggio - errore nello scambio posteggio: {}", e.getMessage(), e);
	    return "redirect:createScambioPosteggio.htm?codiceAutorizzazione=" +
		    autconc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() +
		    "&codiceIstanza=" +
		    autconc.getAutorizzazioniByFkAutconcAutatt().getIstanza().getId().getCodice() +
		    "&codiceConcessione=" +
		    codiceConcPartenza;
	}
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F" + "&status_msg=02";
    }

    @RequestMapping
    public String viewConcessione(Model model, @RequestParam("codiceAutorizzazione") Integer codiceAutorizzazione,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request) {

	ConcessioniCommand concessioniCommand = new ConcessioniCommand();
	concessioniCommand.setViewMode(ConcessioniCommand.CONCESSIONE_VIEW);
	Istanze i = istanzeService.findById(new PkId(codiceIstanza));
	concessioniCommand.setIstanza(i);
	Autorizzazioni autorizzazione = autorizzazioniService.findById(new PkId(codiceAutorizzazione));
	AutorizzazioniConcessioniDatiGenerali datiGeneraliConcessione = autorizzazioniConcessioniService
		.findDatiGeneraliConcessione(codiceAutorizzazione);
	concessioniCommand.setEntity(datiGeneraliConcessione);
	List<AutorizzazioniConcessioni> concessionis = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(codiceAutorizzazione);
	for (AutorizzazioniConcessioni autorizzazioniConcessioni : concessionis) {
	    if (autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll() != null
		    && autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getId().getCodice() != null) {
		concessioniCommand.setInserisciAutorizzazione(true);
	    }
	    fixRenderConcessioniEntityProperty(autorizzazioniConcessioni);
	    concessioniCommand.setConcessioneInsert(autorizzazioniConcessioni);
	}
	fixRenderConcessioniCommandProperty(datiGeneraliConcessione);
	concessioniCommand.setConcessionis(concessionis);
	List<AutorizzazioniSubentri> subentris = autorizzazioniSubentriService.findByAutorizzazione(codiceAutorizzazione, null, null);
	if (autorizzazione.getIstanza() != null) {
	    Integer codIstanza = (Integer) EntityUtils.getNestedProperty(autorizzazione, "istanza.id.codice");
	    // se il codiceIstanza per la quale si richiede la visualizzazione non corrisponde a quello presente in
	    // autorizzazioni (quella del codice passato) allora significa che voglio visualizzare una concessione
	    // subentrata e devo trovare nella lista dei subentri il riferimento alla autorizzazione del codice
	    // istanza passato
	    if (!codIstanza.equals(codiceIstanza)) {
		concessioniCommand.setSubentroPresente(true);
		boolean trovatoSubentro = false;
		for (AutorizzazioniSubentri subentro : subentris) {
		    Integer codiceIstanzaSubentro = subentro.getIstanze().getId().getCodice();
		    // se il codiceIstanza del subentro è lo stesso di quello passato allora ho trovato il subentro
		    // che cercavo
		    if (codiceIstanzaSubentro.equals(codiceIstanza)) {
			concessioniCommand.setSubentro(subentro);
			trovatoSubentro = true;
			AutorizzazioniConcessioni concessione = subentro.getConcessione();
			if (concessione != null) {
			    Autorizzazioni autCollegata = concessione.getAutorizzazioniByFkAutconcAutcoll();
			    if (autCollegata != null) {
				Integer codIstanzaAutCollegata = autCollegata.getIstanza().getId().getCodice();
				if (!codiceIstanza.equals(codIstanzaAutCollegata)) {
				    Set<AutorizzazioniSubentri> subentriAutColl = autCollegata.getAutorizzazioniSubentris();
				    for (AutorizzazioniSubentri subentroAutColl : subentriAutColl) {
					Integer codiceIstanzaSubentroAutCollegata = subentroAutColl.getIstanze().getId().getCodice();
					if (codiceIstanzaSubentroAutCollegata.equals(codiceIstanza)) {
					    concessioniCommand.setInserisciAutorizzazione(true);
					    concessioniCommand.setSubentroAutCollegata(subentroAutColl);
					    break;
					}
				    }
				}
			    }
			}
			break;
		    }
		}
		if (!trovatoSubentro) {
		    log.error("Attenzione non è stato trovato un subentro per l'istanza codice: {}, autorizzazione id: {}", codiceIstanza,
			    codiceAutorizzazione);
		    throw new RuntimeException("Attenzione non è stato trovato un subentro per l'istanza codice: " +
			    codiceIstanza +
			    ", autorizzazione id: " +
			    codiceAutorizzazione);
		}
	    }
	}
	//concessioniCommand.setConcessioneInsert(entity);
	concessioniCommand.setDisplayMode(BaseCommand.EDIT);
	setPageAttributesConcessioni(model, concessioniCommand, autorizzazione);
	if (request.getParameter("decorator") != null) {
	    return "autorizzazioni/formConcessionePopup";
	}
	return "autorizzazioni/formConcessione";
    }

    private void fixRenderConcessioniCommandProperty(AutorizzazioniConcessioniDatiGenerali entity) {

	if (entity.getAutorizzazioniByFkAutconcAutatt() == null) {
	    entity.setAutorizzazioniByFkAutconcAutatt(new Autorizzazioni());
	}
	if (entity.getAutorizzazioniByFkAutconcAutcoll() == null) {
	    entity.setAutorizzazioniByFkAutconcAutcoll(new Autorizzazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.anagrafe") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.occupante") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setOccupante(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.autorizcomune") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setAutorizcomune(new VwEntilocali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.istanza") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.movimenti") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setMovimenti(new Movimenti());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.tipologiaregistro") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setTipologiaregistro(new Tipologiaregistri());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt") == null) {
	    entity.setAutorizzazioniByFkAutconcAutatt(new Autorizzazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll") == null) {
	    entity.setAutorizzazioniByFkAutconcAutcoll(new Autorizzazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.anagrafe") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.autorizcomune") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setAutorizcomune(new VwEntilocali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.istanza") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.movimenti") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setMovimenti(new Movimenti());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.tipologiaregistro") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setTipologiaregistro(new Tipologiaregistri());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.concessionicausaliByFkAutConccausAcq") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.concessionicausaliByFkAutConccausCess") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "concessionitipi") == null) {
	    entity.setConcessionitipi(new Concessionitipi());
	}
    }

    @RequestMapping
    public String updateConcessione(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Autorizzazioni autCollegata = null;
	// fixMergeConcessioniEntityProperty(concessioniCommand.getDatiGeneraliConcessione());
	// AutorizzazioniConcessioniDatiGenerali entity = concessioniCommand.getEntity();
	AutorizzazioniConcessioni entity = concessioniCommand.getConcessioneInsert();
	Autorizzazioni autorizzazione = entity.getAutorizzazioniByFkAutconcAutatt();
	try {
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
	    }
	    checkAutorizzazioneisReadonly(codiceIstanza, entity.getAutorizzazioniByFkAutconcAutatt());
	    if (!concessioniCommand.isInserisciAutorizzazione()) {
		// se non si vuole inserire l'autorizzazione collegata alla concessione
		autCollegata = entity.getAutorizzazioniByFkAutconcAutcoll();
		entity.setAutorizzazioniByFkAutconcAutcoll(null);
	    } else {
		if (concessioniCommand.getAutorizzazioneAssociata().getId().getCodice() != null) {
		    autCollegata = autorizzazioniService.findById(new PkId(concessioniCommand.getAutorizzazioneAssociata().getId().getCodice()));
		    entity.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
		}
	    }
	    if (!StringUtils.defaultIfEmpty(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice"), "")
		    .equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.anagrafe.id.codice"));
		Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
		autorizzazione.setAnagrafe(anagrafe);
	    } else {
		autorizzazione.setAnagrafe(null);
	    }
	    if (!StringUtils.defaultIfEmpty(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice"), "")
		    .equals("")) {
		Integer codice = Integer.parseInt(request.getParameter("concessioneInsert.autorizzazioniByFkAutconcAutatt.occupante.id.codice"));
		Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
		autorizzazione.setOccupante(anagrafe);
	    } else {
		autorizzazione.setOccupante(null);
	    }
	    SessionDetails sessionDetails = getSessionDetails(request);
	    String token = sessionDetails.getToken();
	    if (token == null) {
		throw new RuntimeException("Nessun Token trovato in sessione!!! Contattare l'assistenza");
	    }
	    autorizzazioniService.updateConcessione(entity, codiceIstanza);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(concessioniCommand, true, "entity", e);
	    return "redirect:viewConcessione.htm?codiceAutorizzazione=" +
		    entity.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() +
		    "&codiceIstanza=" +
		    entity.getAutorizzazioniByFkAutconcAutatt().getIstanza().getId().getCodice() +
		    "&status_msg=03";
	    //	    if (autCollegata != null) {
	    //		entity.setAutorizzazioniByFkAutconcAutcoll(autCollegata);
	    //	    }
	    //	    entity = autorizzazioniService.findConcessione(autorizzazione);
	    //	    concessioniCommand.setDatiGeneraliConcessione(entity);
	    //	    copyErrorsToBindingResult(result, concessioniCommand.getDatiGeneraliConcessione(), true, e);
	    //	    fixRenderConcessioniEntityProperty(entity);
	    //	    setPageAttributesConcessioni(model, concessioniCommand, autorizzazione);
	    //	    return "autorizzazioni/formConcessione";
	}
	status.setComplete();
	return "redirect:viewConcessione.htm?codiceAutorizzazione=" +
		entity.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() +
		"&codiceIstanza=" +
		entity.getAutorizzazioniByFkAutconcAutatt().getIstanza().getId().getCodice() +
		"&status_msg=02";
    }

    @RequestMapping
    public String updateRegistroConcessione(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe, @RequestParam("codiceRegistro") Integer codiceRegistro,
	    Model model, @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, HttpServletRequest request) {

	AutorizzazioniConcessioni concessioni = concessioniCommand.getConcessioneInsert();
	Autorizzazioni aut = concessioni.getAutorizzazioniByFkAutconcAutatt();
	autorizzazioniService.updateRegistroConcessione(aut, codiceRegistro);
	if (codiceAnagrafe != null) {
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
	    concessioni.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(anagrafe);
	}
	concessioniCommand.getConcessioneInsert().setAutorizzazioniByFkAutconcAutatt(aut);
	// concessioniCommand.setAutorizzazione(aut);
	// fixRenderConcessioniEntityProperty(concessioni);
	// concessioniCommand.setEntity(concessioni);
	concessioniCommand.setDisplayMode(BaseCommand.NEW);
	setPageAttributesConcessioni(model, concessioniCommand, aut);
	setPageAttributes(model);
	if (request.getParameter("decorator") != null) {
	    return "autorizzazioni/formConcessionePopup";
	}
	return "autorizzazioni/formConcessione";
    }

    /**
     * Il metodo controlla se la concessione è dell'istanza indicata come parametro. se non è la stessa è probabile che
     * sto trattando i dati di un subentro e devo lanciare un' eccezione. Il metodo serve più che altro come controllo
     * prima di cancellare o modificare i dati
     * 
     * @param codiceIstanza
     *            l'istanza che serve per il confronto
     * @param entity
     *            l'entity che devo aggiornare
     */
    private void checkAutorizzazioneisReadonly(Integer codiceIstanza, Autorizzazioni entity) {

	Autorizzazioni checkReadonly = autorizzazioniService.findById(entity.getId());
	if (!checkReadonly.getIstanza().getId().getCodice().equals(codiceIstanza)) {
	    throw new RuntimeException("Non è possibile aggiornare il dato.");
	}
    }

    @RequestMapping
    public String validaDeleteConcessione(@RequestParam("codiceAutorizzazione") Integer codiceAutorizzazione,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("return_to_page") String returnToPage, Model model,
	    @ModelAttribute("concessioniCommand") ConcessioniCommand concessioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    Autorizzazioni aut = autorizzazioniService.findById(new PkId(codiceAutorizzazione));
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
	    }
	    checkAutorizzazioneisReadonly(codiceIstanza, aut);
	    ValidaEliminazioneAutConcCommand cmd = new ValidaEliminazioneAutConcCommand(returnToPage, aut);
	    model.addAttribute("validaEliminazioneAutConcCommand", cmd);
	    EsitoCancellazioneAutOConc esito = autorizzazioniService.validaCancellazioneAutConc(cmd);
	    if (!esito.isErroreOWarning()) {
		return deleteConcessione(model, cmd, result, status, request);
	    }
	    cmd.setEsito(esito);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(concessioniCommand, true, "entity", e);
	    return "redirect:viewConcessione.htm?codiceAutorizzazione=" + codiceAutorizzazione + "&codiceIstanza=" + codiceIstanza + "&status_msg=03";
	}
	return "autorizzazioni/verificaCancellazioneAutConc";
    }

    @RequestMapping
    public String deleteConcessione(Model model,
	    @ModelAttribute("validaEliminazioneAutConcCommand") ValidaEliminazioneAutConcCommand validaEliminazioneAutConcCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    autorizzazioniService.deleteConcessione(validaEliminazioneAutConcCommand);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(validaEliminazioneAutConcCommand, true, "entity", e);
	    return "redirect:viewConcessione.htm?codiceAutorizzazione=" +
		    validaEliminazioneAutConcCommand.getIdAutorizzazioni() +
		    "&codiceIstanza=" +
		    validaEliminazioneAutConcCommand.getCodiceIstanza() +
		    "&status_msg=03";
	}
	status.setComplete();
	return "redirect:concessioneDeleted.htm?status_msg=05&codiceIstanza=" + validaEliminazioneAutConcCommand.getCodiceIstanza();
    }

    @RequestMapping
    public String concessioneDeleted(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request) {

	ConcessioniCommand command = new ConcessioniCommand();
	command.setDisplayMode(ConcessioniCommand.DELETED);
	model.addAttribute("concessioniCommand", command);
	return "autorizzazioni/formConcessione";
    }

    private void setPageAttributesConcessioni(Model model, ConcessioniCommand concessioniCommand, Autorizzazioni autorizzazione) {

	boolean isAttivita = attivitaService.existsRecords();
	model.addAttribute("isTipoInformazioni", Boolean.valueOf(isAttivita));
	List<Concessionitipi> concessionitipis = concessionitipiService.findAll(null, null);
	model.addAttribute("concessionitipis", concessionitipis);
	List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	List<Concessionicausali> concessionicausalisCess = concessionicausaliService.findAllbyCausaleStorico(true);
	model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	model.addAttribute("concessionicausalisCess", concessionicausalisCess);
	Istanze istanza = concessioniCommand.getIstanza();
	//	Istanze istanza = istanzeService
	//		.findById(concessioniCommand.getConcessioneInsert().getAutorizzazioniByFkAutconcAutatt().getIstanza().getId());
	model.addAttribute("istanza", istanza);
	model.addAttribute("autorizzazioneAttuale", autorizzazione);
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	if (EntityUtils.getNestedProperty(mercatiConfigurazione, "registroConcessioni.id.codice") != null) {
	    Tipologiaregistri registroConcessione = tipologiaregistriService.findById(mercatiConfigurazione.getRegistroConcessioni().getId());
	    model.addAttribute("registroConcessioniConfigurazione", registroConcessione);
	}
	List<AutorizzazioniConcessioni> concs = concessioniCommand.getConcessionis();
	Map<Integer, List<CodiceDescrizioneBean>> mappaConcPosteggiLiberi = new HashMap<Integer, List<CodiceDescrizioneBean>>();
	for (AutorizzazioniConcessioni conc : concs) {
	    List<CodiceDescrizioneBean> mercatiDList = mercatiDService.findPosteggiNonAssegnatiByMercato(conc.getMercati().getId().getCodice(),
		    conc.getMercatiUso().getId().getCodice(), null, PosteggiEnum.ACTIVE);
	    mappaConcPosteggiLiberi.put(conc.getId().getCodice(), mercatiDList);
	}
	//FIX errore rendering pagina JSP per proprietà nulla
	if (concessioniCommand.getSubentro() == null) {
	    concessioniCommand.setSubentro(new AutorizzazioniSubentri());
	}
	if (concessioniCommand.getSubentro().getConcessionicausaliByFkAutsubConccausAcq() == null) {
	    concessioniCommand.getSubentro().setConcessionicausaliByFkAutsubConccausAcq(new Concessionicausali());
	}
	if (concessioniCommand.getSubentro().getConcessionicausaliByFkAutsubConccausCess() == null) {
	    concessioniCommand.getSubentro().setConcessionicausaliByFkAutsubConccausCess(new Concessionicausali());
	}
	if (concessioniCommand.getSubentro().getOccupante() == null) {
	    concessioniCommand.getSubentro().setOccupante(new Anagrafe());
	}
	// Controlla se è stato precompilato le info su mercato,mercatoUso, le informazioni saranno recuperato solo se
	// nella voce dell'albero associata all'istanza è stato configurato una manifestazione
	// TODO mercatiDList
	//	if (concessioniCommand.getIsManifestazioneDaProcedimentoPresente() == null) {
	//	    if (concessioniCommand.getDatiGeneraliConcessione() != null
	//		    && EntityUtils.getNestedProperty(concessioniCommand.getDatiGeneraliConcessione().getMercati(), "id.codice") != null
	//		    && EntityUtils.getNestedProperty(concessioniCommand.getDatiGeneraliConcessione().getMercatiUso(), "id.codice") != null) {
	//		concessioniCommand.setIsManifestazioneDaProcedimentoPresente(true);
	//		mercatiDList = mercatiDService.findPosteggiNonAssegnatiByMercato(concessioniCommand.getDatiGeneraliConcessione().getMercati().getId()
	//			.getCodice(), concessioniCommand.getDatiGeneraliConcessione().getMercatiUso().getId().getCodice(), concessioniCommand
	//			.getDatiGeneraliConcessione().getMercatiD().getId().getCodice(), PosteggiEnum.ACTIVE);
	//	    } else {
	//		concessioniCommand.setIsManifestazioneDaProcedimentoPresente(false);
	//	    }
	//	    //	    if (EntityUtils.getNestedProperty(concessioniCommand.getConcessioneInsert().getMercati(), "id.codice") != null) {
	//	    //		Set<MercatiUso> listUsos = concessioniCommand.getConcessioneInsert().getMercati().getMercatiUsos();
	//	    //		model.addAttribute("listUsos", listUsos);
	//	    //	    }
	//	}
	/**
	 * GESTIONE DELLA FUNZIONALITÀ GESTIONE SPUNTISTA (RICHIESTA DI SPUNTA GESTITA TRAMITE ISTANZA)
	 */
	boolean isGestioneSpuntistiAttiva = false;
	MercatiConfigurazione mercaticonfig = mercatiConfigurazioneService.findConfigurazione();
	isGestioneSpuntistiAttiva = MercatiConfigurazione.checkConfigurazioneGradSpuntistiPerManifestazioni(mercaticonfig);
	model.addAttribute("isGestioneSpuntistiAttiva", isGestioneSpuntistiAttiva);
	model.addAttribute("mappaConcPosteggiLiberi", mappaConcPosteggiLiberi);
	model.addAttribute("concessioniCommand", concessioniCommand);
    }

    @RequestMapping
    public String createAutorizzazione(Model model, @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) {

	Istanze istanza = null;
	if (codiceIstanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (istanza != null) {
		model.addAttribute("codicecomune", istanza.getComune().getCodicecomune());
		checkAccessoInformazioni(istanza, true);
	    }
	}
	AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	Autorizzazioni entity = autorizzazioniCommand.getEntity();
	precompilaAutorizzazioneCommand(codiceIstanza, codiceAnagrafe, codiceMovimento, autorizzazioniCommand);
	autorizzazioniCommand.setDisplayMode(AutorizzazioniCommand.NEW);
	model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	if (codiceMovimento != null) {
	    Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	    if (movimenti == null) {
		log.error("createAutorizzazione(): Nessun movimento trovato con codice: {}", codiceMovimento);
		throw new RuntimeException("Nessun movimento trovato con codice: " + codiceMovimento);
	    }
	    istanza = movimenti.getIstanza();
	    //1.Verifico se numerazione da WS Atti e se devo mostrare i firmatari
	    this.impostaVariabiliFirmatari(model, entity, istanza);
	    //
	    assegnaNumEDataProtocollo(entity, movimenti);
	    IstanzaAutConcHelper autConcHelper = autorizzazioniService.findByIstanza(istanza);
	    model.addAttribute("autConcHelper", autConcHelper);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_NUOVA_AUT_VIS_AUTORIZZAZIONI, "1", request);
	    if (!movimenti.getAutorizzazioni().isEmpty()) {
		// se il movimento ha già un'autorizzazione allora eseguo la redirect in view
		Autorizzazioni aut = null;
		Set<Autorizzazioni> autorizzazioniSet = movimenti.getAutorizzazioni();
		for (Autorizzazioni autorizzazioni : autorizzazioniSet) {
		    aut = autorizzazioni;
		}
		return "redirect:viewAutorizzazione.htm?codice=" + aut.getId().getCodice() + "&codiceIstanza=" + aut.getIstanza().getId().getCodice();
	    } else if (!movimenti.getAutorizzazioniSubentri().isEmpty()) {
		Set<AutorizzazioniSubentri> subentri = movimenti.getAutorizzazioniSubentri();
		for (AutorizzazioniSubentri autorizzazioniSubentri : subentri) {
		    autorizzazioniCommand.setSubentro(autorizzazioniSubentri);
		}
		autorizzazioniCommand.setDisplayMode(AutorizzazioniCommand.READONLY);
		setPageAttributes(model);
		return "autorizzazioni/formAutorizzazione";
	    }
	    Tipiprocedure procedura = movimenti.getIstanza().getProcedura();
	    Tipimovimento tipimov = movimenti.getTipomovimento();
	    String idcchiusura = (String) EntityUtils.getNestedProperty(procedura, "tipimovimentoChiusura.id.tipomovimento");
	    if (StringUtils.isNotBlank(idcchiusura)) {
		if (idcchiusura.equalsIgnoreCase(tipimov.getId().getTipomovimento())) {
		    String codiceStato = StringUtils.defaultString(request.getParameter("codiceStato"));
		    if (StringUtils.isNotBlank(codiceStato)) {
			autorizzazioniCommand.getStatoistanza().getId().setCodicestato(codiceStato);
		    }
		    List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
		    model.addAttribute("statiIstanzaList", statiistanzas);
		}
	    }
	}
	setPageAttributesAutorizzazioni(model, autorizzazioniCommand, false);
	setPageAttributes(model);
	List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	return "autorizzazioni/formAutorizzazione";
    }

    @SuppressWarnings("unused")
    @RequestMapping
    public String viewOperazioni(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, false);
	List<Autorizzazioni> auts = autorizzazioniService.findByIstanza(codiceIstanza);
	int size = auts.size();
	if (size == 0) {
	    // 1. risalgo alle istanze collegate padre
	    IstanzecollegateHelper istanzeCollegateHelperPrecedenti = istanzecollegateService.getSchemaPrecedentiAndSuccessive(istanze);
	    List<Istanze> istanzeCollegatePrecedenti = istanzeCollegateHelperPrecedenti.getListaIstanzePrecedenti();
	    if (!istanzeCollegatePrecedenti.isEmpty()) {
		String redirect = "";
		for (Istanze istanza : istanzeCollegatePrecedenti) {
		    // 2. verifico chi di queste ha una autorizzazione e se ce ne è qualcuna esco su
		    List<Autorizzazioni> autsOfIstanza = autorizzazioniService.findByIstanza(istanza.getId().getCodice());
		    int size2 = 0;
		    if (!autsOfIstanza.isEmpty()) {
			size2 = autsOfIstanza.size();
		    }
		    if (size2 > 1) {
			IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
			istanzaAutConcHelper.setIstanza(istanza);
			model.addAttribute("istanzaAutConcHelper", istanzaAutConcHelper);
			redirect = "autorizzazioni/scegliAutorizzazione";
			break;
		    } else if (size2 == 1) {
			redirect = "redirect:../autorizzazioni/viewOperazione.htm?idAutorizzazione=" + autsOfIstanza.get(0).getId().getCodice();
			break;
		    }
		}
		// 3. nel caso in cui nessuna delle istanze collegate ha un'autorizzazione, mostro un messaggio di errore
		if (StringUtils.isBlank(redirect)) {
		    FlashMessages.getWarnings().add("Non è stata trovata nessuna autorizzazione nelle pratiche collegate");
		    redirect = "redirect:../istanze/view.htm?codice=" + codiceIstanza + "&status_msg=03";
		}
		return redirect;
	    }
	    // manda messaggio di errore
	    FlashMessages.getWarnings().add("Nessuna autorizzazione per l'istanza");
	    return "redirect:../istanze/view.htm?codice=" + codiceIstanza + "&status_msg=03";
	} else if (size == 1) {
	    return "redirect:../autorizzazioni/viewOperazione.htm?idAutorizzazione=" + auts.get(0).getId().getCodice();
	}
	IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanze);
	istanzaAutConcHelper.setIstanza(istanze);
	model.addAttribute("istanzaAutConcHelper", istanzaAutConcHelper);
	return "autorizzazioni/scegliAutorizzazione";
    }

    @RequestMapping
    public String viewOperazione(@RequestParam(value = "idAutorizzazione") Integer idAutorizzazione, Model model, HttpServletRequest request) {

	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAutorizzazione));
	AutorizzazioniAccessiHelper helper = istanzeService.findAutorizzazioniAccessiHelper(autorizzazioni.getId().getCodice());
	Tipologiaregistri tr = autorizzazioni.getTipologiaregistro();
	boolean isProrogata = false;
	if (tr.getNumeroProroghe() != null && tr.getNumeroProroghe().intValue() > 0) {
	    int numpr = autorizzazioni.getNumProrogheRimaste() == null ? 0 : autorizzazioni.getNumProrogheRimaste().intValue();
	    if (numpr < tr.getNumeroProroghe().intValue()) {
		isProrogata = true;
	    }
	}
	model.addAttribute("isProrogata", Boolean.valueOf(isProrogata));
	model.addAttribute("helper", helper);
	return "autorizzazioni/formOperazioni";
    }

    @RequestMapping
    public void ajaxCompletaAutorizzazione(@RequestParam("codiceautorizzazione") Integer codiceAutorizzazione,
	    @RequestParam("completa") Boolean completa, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	String result = "OK";
	try {
	    this.autorizzazioniService.completaAutorizzazione(codiceAutorizzazione, completa);
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante il tentativo di completamento dell'autorizzazione. (" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxViewOperazione(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	Integer idAutorizzazione = null;
	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanze, false);
	List<Autorizzazioni> auts = autorizzazioniService.findByIstanza(codiceIstanza);
	int size = auts.size();
	if (size == 0) {
	    // 1. risalgo alle istanze collegate padre
	    IstanzecollegateHelper istanzeCollegateHelperPrecedenti = istanzecollegateService.getSchemaPrecedentiAndSuccessive(istanze);
	    List<Istanze> istanzeCollegatePrecedenti = istanzeCollegateHelperPrecedenti.getListaIstanzePrecedenti();
	    if (!istanzeCollegatePrecedenti.isEmpty()) {
		for (Istanze istanza : istanzeCollegatePrecedenti) {
		    // 2. verifico chi di queste ha una autorizzazione e se ce ne è qualcuna esco su
		    List<Autorizzazioni> autsOfIstanza = autorizzazioniService.findByIstanza(istanza.getId().getCodice());
		    if (autsOfIstanza.size() == 1) {
			idAutorizzazione = autsOfIstanza.get(0).getId().getCodice();
			break;
		    }
		}
	    }
	} else if (size == 1) {
	    idAutorizzazione = auts.get(0).getId().getCodice();
	}
	if (idAutorizzazione == null) {
	    return "autorizzazioni/blank";
	}
	IstanzaAutConcHelper istanzaAutConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanze);
	istanzaAutConcHelper.setIstanza(istanze);
	model.addAttribute("istanzaAutConcHelper", istanzaAutConcHelper);
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAutorizzazione));
	AutorizzazioniAccessiHelper helper = istanzeService.findAutorizzazioniAccessiHelper(autorizzazioni.getId().getCodice());
	Tipologiaregistri tr = autorizzazioni.getTipologiaregistro();
	boolean isProrogata = false;
	if (tr.getNumeroProroghe() != null && tr.getNumeroProroghe().intValue() > 0) {
	    int numpr = autorizzazioni.getNumProrogheRimaste() == null ? 0 : autorizzazioni.getNumProrogheRimaste().intValue();
	    if (numpr < tr.getNumeroProroghe().intValue()) {
		isProrogata = true;
	    }
	}
	model.addAttribute("isProrogata", Boolean.valueOf(isProrogata));
	model.addAttribute("helper", helper);
	return "autorizzazioni/ajaxFormOperazioni";
    }

    private void setPageAttributesAutorizzazioni(Model model, AutorizzazioniCommand autorizzazioniCommand, boolean afterInsert) {

	//Controlli per prepopolare in base al registro scelto
	boolean isViewPresenze = true;
	boolean isAttivita = attivitaService.existsRecords();
	model.addAttribute("isTipoInformazioni", Boolean.valueOf(isAttivita));
	Boolean isDoppioAtto = Boolean.FALSE;
	if (autorizzazioniCommand.getEntity() != null && autorizzazioniCommand.getEntity().getId() != null
		&& autorizzazioniCommand.getEntity().getId().getCodice() != null) {
	    isDoppioAtto = autorizzazioniConcessioniService
		    .getFkIdautAttualePerAutCollegata(autorizzazioniCommand.getEntity().getId().getCodice()) != null;
	}
	model.addAttribute("isAutorizzazioneAttoCollegatoAConcessione", isDoppioAtto);
	/**
	 * GESTIONE DELLA FUNZIONALITÀ GESTIONE SPUNTISTA (RICHIESTA DI SPUNTA GESTITA TRAMITE ISTANZA)
	 */
	boolean isGestioneSpuntistiAttiva = false;
	MercatiConfigurazione mercaticonfig = mercatiConfigurazioneService.findConfigurazione();
	if (mercaticonfig != null) {
	    autorizzazioniCommand.setManifestazioniConfigurate(true);
	    isGestioneSpuntistiAttiva = MercatiConfigurazione.checkConfigurazioneGradSpuntistiPerManifestazioni(mercaticonfig);
	    model.addAttribute("isGestioneSpuntistiAttiva", isGestioneSpuntistiAttiva);
	}
	if (EntityUtils.getNestedProperty(autorizzazioniCommand.getEntity().getTipologiaregistro(), "id.codice") != null) {
	    // Verifico se è un autorizzazione dehor
	    log.debug("setPageAttributesAutorizzazioni# Controllo se è un autorizzazione di tipo dehors....");
	    DehorsCfg dehorsCfg = dehorsCfgService
		    .findByTipologiaregistro(autorizzazioniCommand.getEntity().getTipologiaregistro().getId().getCodice());
	    if (EntityUtils.getNestedProperty(dehorsCfg, "id.codice") != null) {
		// per i dehors non si deve vedere il bottone presenze
		isViewPresenze = false;
		log.debug(
			"setPageAttributesAutorizzazioni# Controllo data scadenza autorizzazione [chiudo quelle con data scadenza minore della data odierna]");
		autorizzazioniService.updateCessaAutorizzazioniDehorsScadute();
		// provo a prepolare i campi, controllo se dalle schede dinamiche sono stati inseriti mq richiesti e area
		DehorsMqIstanze dehorsMqIstanze = null;
		if (!afterInsert) {
		    // va popolare l'oggetto DehorsMqIstanze con le informazioni legate all'istanza nella tabella DehorsMqIstanza (alcui campi
		    //potrebbero essere già presenti per l'istanza passata, campi popolati tramite le formule delle schede dinamiche)
		    dehorsMqIstanzeService.prepopulateDehorsMqIstanze(autorizzazioniCommand.getDehorsMqIstanze(),
			    autorizzazioniCommand.getEntity().getIstanza().getId().getCodice());
		    dehorsMqIstanze = autorizzazioniCommand.getDehorsMqIstanze();
		} else {
		    dehorsMqIstanze = dehorsMqIstanzeService.findAttiveByAutorizzazione(autorizzazioniCommand.getEntity().getId().getCodice());
		    if (EntityUtils.getNestedProperty(dehorsMqIstanze, "id.codice") == null) {
			dehorsMqIstanze = new DehorsMqIstanze();
		    }
		    autorizzazioniCommand.setDehorsMqIstanze(dehorsMqIstanze);
		}
		// setto nuovamente il record al command
		//autorizzazioniCommand.setDehorsMqIstanze(dehorsMqIstanze);
		log.debug("setPageAttributesAutorizzazioni# Registro configurato per dehors,mostro pagina di inclusione {}",
			"../autorizzazioni/datiDehors.jsp");
		String value0or1 = "0";
		if (afterInsert) {
		    value0or1 = "1";
		}
		model.addAttribute("value0or1", value0or1);
		model.addAttribute("paginaInclude", "../autorizzazioni/datiDehors.jsp");
	    }
	    model.addAttribute("isViewPresenze", isViewPresenze);
	}
    }

    /**
     * @param codiceIstanza
     * @param codiceAnagrafe
     * @param codiceMovimento
     * @param command
     */
    private void precompilaAutorizzazioneCommand(Integer codiceIstanza, Integer codiceAnagrafe, Integer codiceMovimento,
	    AutorizzazioniCommand command) {

	Autorizzazioni entity = command.getEntity();
	entity.setFlagAttiva(true);
	if (null != codiceIstanza) {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (null == istanza) {
		log.error("precompilaAutorizzazioneCommand(): Nessuna istanza trovata con codice: {}", codiceIstanza);
		throw new RuntimeException("Nessuna istanza trovata con codice: " + codiceIstanza);
	    }
	    entity.setIstanza(istanza);
	    VwEntilocali comune = vwEntilocaliService.findById(istanza.getComune().getCodicecomune());
	    entity.setAutorizcomune(comune);
	    // POPOLA IL TIPO REGISTRO
	    // Nel caso non sia presente sulla voce dell'albero, prova a recuperare,se presente, quello 
	    // delle voci gerarchicamente più alte.
	    Tipologiaregistri registro = istanza.getAlberoproc().getTipologiaregistro();
	    if (EntityUtils.getNestedProperty(registro, "id.codice") != null) {
		entity.setTipologiaregistro(registro);
		autorizzazioniService.updateRegistro(entity, registro.getId().getCodice());
	    } else {
		AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(istanza.getAlberoproc());
		entity.setTipologiaregistro(alberoprocHelper.getTipologiaregistro());
		autorizzazioniService.updateRegistro(entity, alberoprocHelper.getTipologiaregistro().getId().getCodice());
	    }
	    Anagrafe titolare = istanza.getRichiedente();
	    if (istanza.getTitolarelegale() != null && istanza.getTitolarelegale().getId() != null
		    && istanza.getTitolarelegale().getId().getCodice() != null) {
		titolare = istanza.getTitolarelegale();
	    }
	    entity.setAnagrafe(titolare);
	    entity.setOccupante(titolare);
	}
	if (null != codiceMovimento) {
	    Movimenti movimenti = movimentiService.findById(new PkId(codiceMovimento));
	    if (null == movimenti) {
		log.error("precompilaAutorizzazioneCommand(): Nessun movimento trovato con codice: {}", codiceMovimento);
		throw new RuntimeException("Nessun movimento trovato con codice: " + codiceMovimento);
	    }
	    entity.setMovimenti(movimenti);
	    VwEntilocali comune = vwEntilocaliService.findById(movimenti.getIstanza().getComune().getCodicecomune());
	    entity.setAutorizcomune(comune);
	    Istanze istanza = movimenti.getIstanza();
	    Anagrafe titolare = istanza.getRichiedente();
	    if (istanza.getTitolarelegale() != null && istanza.getTitolarelegale().getId() != null
		    && istanza.getTitolarelegale().getId().getCodice() != null) {
		titolare = istanza.getTitolarelegale();
	    }
	    entity.setAnagrafe(titolare);
	    Tipologiaregistri registro = movimenti.getTipomovimento().getTipologiaregistri();
	    if (registro != null && registro.getId() != null && registro.getId().getCodice() != null) {
		entity.setTipologiaregistro(registro);
		autorizzazioniService.updateRegistro(entity, registro.getId().getCodice());
		if (BooleanUtils.isFalse(registro.getTrFlagdataauto())) {
		    entity.setAutorizdata(movimenti.getData());
		}
	    }
	}
	if (null != codiceAnagrafe) {
	    Anagrafe titolare = anagrafeService.findById(new PkId(codiceAnagrafe));
	    if (null == titolare) {
		log.error("precompilaAutorizzazioneCommand(): Nessuna anagrafe trovata con codice: {}", codiceAnagrafe);
		throw new RuntimeException("Nessuna anagrafe trovata con codice:  " + codiceAnagrafe);
	    }
	    entity.setAnagrafe(titolare);
	    entity.setOccupante(titolare);
	}
	if (entity.getDatascadenza() == null) {
	    if (entity.getTipologiaregistro() != null && entity.getTipologiaregistro().getId() != null
		    && entity.getTipologiaregistro().getId().getCodice() != null) {
		Tipologiaregistri registro = tipologiaregistriService.findById(new PkId(entity.getTipologiaregistro().getId().getCodice()));
		String aggiornaDuration = registro.getDurataAutorizzazione();
		if (StringUtils.isNotBlank(aggiornaDuration) && entity.getAutorizdata() != null) {
		    Date scadenza = Utilities.addDuration(entity.getAutorizdata(), registro.getDurataAutorizzazione());
		    entity.setDatascadenza(scadenza);
		}
	    }
	}
    }

    @RequestMapping
    public String updateRegistroAutorizzazione(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam("codiceRegistro") Integer codiceRegistro, Model model,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, HttpServletRequest request) {

	Autorizzazioni autorizzazione = autorizzazioniCommand.getEntity();
	autorizzazioniService.updateRegistro(autorizzazione, codiceRegistro);
	fixRenderEntityProperty(autorizzazione);
	autorizzazioniCommand.setEntity(autorizzazione);
	if (autorizzazione.getMovimenti() != null && autorizzazione.getMovimenti().getId() != null
		&& autorizzazione.getMovimenti().getId().getCodice() != null) {
	    Movimenti movimenti = movimentiService.findById(new PkId(autorizzazione.getMovimenti().getId().getCodice()));
	    assegnaNumEDataProtocollo(autorizzazione, movimenti);
	    Tipiprocedure procedura = movimenti.getIstanza().getProcedura();
	    Tipimovimento tipimov = movimenti.getTipomovimento();
	    String idcchiusura = (String) EntityUtils.getNestedProperty(procedura, "tipimovimentoChiusura.id.tipomovimento");
	    if (StringUtils.isNotBlank(idcchiusura)) {
		if (idcchiusura.equalsIgnoreCase(tipimov.getId().getTipomovimento())) {
		    List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
		    model.addAttribute("statiIstanzaList", statiistanzas);
		}
	    }
	    //1.Verifico se numerazione da WS Atti e se devo mostrare i firmatari
	    this.impostaVariabiliFirmatari(model, autorizzazione, autorizzazione.getMovimenti().getIstanza());
	}
	setPageAttributes(model);
	setPageAttributesAutorizzazioni(model, autorizzazioniCommand, false);
	List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	return "autorizzazioni/formAutorizzazione";
    }

    private void impostaVariabiliFirmatari(Model model, Autorizzazioni autorizzazione, Istanze istanza) {

	if (autorizzazione == null) {
	    return;
	}
	//1.Verifico se numerazione da WS Atti e se devo mostrare i firmatari
	Integer codiceRegistro = (Integer) EntityUtils.getNestedProperty(autorizzazione.getTipologiaregistro(), "id.codice");
	if (codiceRegistro != null) {
	    Tipologiaregistri registro = this.tipologiaregistriService.findById(new PkId(codiceRegistro));
	    NumerazioneEnum numerazione = NumerazioneEnum.daRegistro(registro);
	    if (NumerazioneEnum.CUSTOM.equals(numerazione)) {
		if (!StringUtils.isBlank(this.verticalizzazioneWSAttiService.getUrlFirmatari())) {
		    model.addAttribute("mostraFirmatari", 1);
		    ElencoFirmatariResponse firmatari = this.wsAttiService.elencoFirmatari(istanza.getComune().getCodicecomune());
		    model.addAttribute("elencoFirmatari", firmatari.getFirmatari());
		    //imposto di default il soggetto in base alla configurazione
		    ConfigurazioneWSAtti config = this.configurazioneService.getConfigurazione(istanza.getAlberoproc().getId().getCodice());
		    model.addAttribute("codiceFirmatario", config.getCodiceDirigente());
		}
	    }
	}
    }

    private void assegnaNumEDataProtocollo(Autorizzazioni entity, Movimenti movimento) {

	if (entity != null) {
	    Integer codiceRegistro = (Integer) EntityUtils.getNestedProperty(entity.getTipologiaregistro(), "id.codice");
	    if (codiceRegistro != null) {
		Tipologiaregistri registro = this.tipologiaregistriService.findById(new PkId(codiceRegistro));
		NumerazioneEnum numerazione = NumerazioneEnum.daRegistro(registro);
		if (NumerazioneEnum.DA_PROTOCOLLO.equals(numerazione)) {
		    if (StringUtils.isNotBlank(movimento.getNumeroprotocollo())) {
			if (StringUtils.isBlank(entity.getAutoriznumero())) {
			    entity.setFkidprotocollo(movimento.getFkidprotocollo());
			    entity.setAutoriznumero(movimento.getNumeroprotocollo());
			    if (movimento.getDataprotocollo() != null) {
				entity.setAutorizdata(movimento.getDataprotocollo());
			    }
			}
		    }
		}
	    }
	}
    }

    @RequestMapping
    public String merceologie(@RequestParam("idautorizzazione") Integer idautorizzazione, Model model, HttpServletRequest request) {

	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idautorizzazione));
	AutorizzazioniConcessioni conc = autorizzazioniService.findConcessione(aut);
	model.addAttribute("aut", aut);
	model.addAttribute("conc", conc);
	return "autorizzazioni/formMerceologie";
    }

    @RequestMapping
    public void ajaxAssegnaAttivita(@RequestParam("idautorizzazione") Integer idautorizzazione, @RequestParam("codiceAttivita") String codiceAttivita,
	    HttpServletResponse response) throws IOException {

	String result = "OK";
	try {
	    List<AutorizzazioniAttivita> autatts = autorizzazioniAttivitaService.findByAutorizzazione(idautorizzazione, null, null);
	    boolean trovata = false;
	    for (AutorizzazioniAttivita aa : autatts) {
		if (aa.getAttivita() != null && aa.getAttivita().getId() != null
			&& aa.getAttivita().getId().getCodiceistat().equalsIgnoreCase(codiceAttivita)) {
		    trovata = true;
		    break;
		}
	    }
	    if (!trovata) {
		Autorizzazioni aut = autorizzazioniService.findById(new PkId(idautorizzazione));
		AttivitaId atttt = new AttivitaId(codiceAttivita);
		Attivita att = attivitaService.findById(atttt);
		AutorizzazioniAttivita a = new AutorizzazioniAttivita();
		a.setAttivita(att);
		a.setAutorizzazioni(aut);
		autorizzazioniAttivitaService.insert(a);
	    } else {
		result = "Informazione già inserita.";
	    }
	} catch (Exception e) {
	    if (e instanceof BusinessValidationException) {
		result = e.getMessage();
	    } else {
		result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio:" + e.getMessage() + ")";
	    }
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxEliminaAttivita(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    AutorizzazioniAttivita entity = autorizzazioniAttivitaService.findById(new PkId(idRiga));
	    autorizzazioniAttivitaService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioMerceologia(@RequestParam("idautorizzazione") Integer idautorizzazione,
	    @RequestParam(required = false, value = "codiceAttivitaInserito") String codiceAttivitaInserito, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<AutorizzazioniAttivita> gds = autorizzazioniAttivitaService.findByAutorizzazione(idautorizzazione, null, null);
	model.addAttribute("gds", gds);
	model.addAttribute("codiceAttivitaInserito", codiceAttivitaInserito);
	return "autorizzazioni/ajaxDettaglioMerceologia";
    }

    @RequestMapping
    public String insertAutorizzazione(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.anagrafe.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.anagrafe.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    autorizzazioniCommand.getEntity().setAnagrafe(anagrafe);
	} else {
	    autorizzazioniCommand.getEntity().setAnagrafe(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.occupante.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.occupante.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    autorizzazioniCommand.getEntity().setOccupante(anagrafe);
	} else {
	    autorizzazioniCommand.getEntity().setOccupante(null);
	}
	fixMergeEntityProperty(autorizzazioniCommand.getEntity());
	Autorizzazioni entity = autorizzazioniCommand.getEntity();
	try {
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
	    }
	    SessionDetails sessionDetails = getSessionDetails(request);
	    String token = sessionDetails.getToken();
	    if (token == null) {
		throw new RuntimeException("Nessun Token trovato in sessione!!! Contattare l'assistenza");
	    }
	    updateStatoIstanza(autorizzazioniCommand, entity);
	    if (codiceMovimento != null) {
		//	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
		//	if (movimento != null) {
		//	assegnaNumEDataProtocollo(entity, movimento);
		//	}
	    }
	    // Effettua una pre-autorizzazione all'operazione, nel caso non venga passata, viene rilanciato l'errore di 
	    //tipo "InvalidValue"
	    autorizzazioniService.validateInsertAutorizzazione(entity);
	    autorizzazioniService.insertAutorizzazione(autorizzazioniCommand, false);
	} catch (Exception e) {
	    fixRenderEntityProperty(autorizzazioniCommand.getEntity());
	    copyErrorsToBindingResult(result, autorizzazioniCommand.getEntity(), true, e);
	    if (entity.getMovimenti() != null && entity.getMovimenti().getId() != null && entity.getMovimenti().getId().getCodice() != null) {
		Movimenti movimenti = movimentiService.findById(new PkId(entity.getMovimenti().getId().getCodice()));
		Tipiprocedure procedura = movimenti.getIstanza().getProcedura();
		Tipimovimento tipimov = movimenti.getTipomovimento();
		String idcchiusura = (String) EntityUtils.getNestedProperty(procedura, "tipimovimentoChiusura.id.tipomovimento");
		if (StringUtils.isNotBlank(idcchiusura)) {
		    if (idcchiusura.equalsIgnoreCase(tipimov.getId().getTipomovimento())) {
			List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
			model.addAttribute("statiIstanzaList", statiistanzas);
		    }
		}
	    }
	    List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	    model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	    setPageAttributesAutorizzazioni(model, autorizzazioniCommand, false);
	    setPageAttributes(model);
	    // GIANPAOLO FIXME - capire quando è errore di duplicazione
	    model.addAttribute("isDuplicaAutorizzazione", true);
	    Autorizzazioni autorizzazioneEsistente = autorizzazioniService.findAutOConcByEstremi(entity.getAutoriznumero(), entity.getAutorizdata(),
		    entity.getAutorizcomune().getCodicecomune(), entity.getTipologiaregistro().getId().getCodice());
	    if (EntityUtils.getNestedProperty(autorizzazioneEsistente, "id.codice") != null) {
		model.addAttribute("codiceAut", autorizzazioneEsistente.getId().getCodice());
		boolean isStessaAnagrafeSuAutorizzazione = true;
		if (!autorizzazioneEsistente.getAnagrafe().getId().getCodice()
			.equals(autorizzazioniCommand.getEntity().getAnagrafe().getId().getCodice())) {
		    isStessaAnagrafeSuAutorizzazione = false;
		}
		model.addAttribute("isStessaAnagrafeSuAutorizzazione", isStessaAnagrafeSuAutorizzazione);
	    } else {
		model.addAttribute("codiceAut", autorizzazioniCommand.getEntity().getId().getCodice());
	    }
	    model.addAttribute("codiceIstanza", codiceIstanza);
	    return "autorizzazioni/formAutorizzazione";
	}
	status.setComplete();
	String redirect = "redirect:viewAutorizzazione.htm?codice=" + autorizzazioniCommand.getEntity().getId().getCodice() + "&status_msg=01";
	if (codiceIstanza != null) {
	    redirect += "&codiceIstanza=" + String.valueOf(codiceIstanza);
	}
	if (codiceMovimento != null) {
	    redirect += "&codiceMovimento=" + String.valueOf(codiceMovimento);
	}
	if (codiceAnagrafe != null) {
	    redirect += "&codiceAnagrafe=" + String.valueOf(codiceAnagrafe);
	}
	return redirect;
    }

    @RequestMapping
    public String updateAutorizzazione(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.anagrafe.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.anagrafe.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    autorizzazioniCommand.getEntity().setAnagrafe(anagrafe);
	} else {
	    autorizzazioniCommand.getEntity().setAnagrafe(null);
	}
	if (!StringUtils.defaultIfEmpty(request.getParameter("entity.occupante.id.codice"), "").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.occupante.id.codice"));
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(codice));
	    autorizzazioniCommand.getEntity().setOccupante(anagrafe);
	} else {
	    autorizzazioniCommand.getEntity().setOccupante(null);
	}
	fixMergeEntityProperty(autorizzazioniCommand.getEntity());
	Autorizzazioni entity = autorizzazioniCommand.getEntity();
	try {
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
	    }
	    updateStatoIstanza(autorizzazioniCommand, entity);
	    if (EntityUtils.getNestedProperty(autorizzazioniCommand.getEntity().getConcessionicausaliByFkAutConccausAcq(), "id.codice") != null) {
		Concessionicausali concessionicausaliAcq = concessionicausaliService
			.findById(new PkId(autorizzazioniCommand.getEntity().getConcessionicausaliByFkAutConccausAcq().getId().getCodice()));
		entity.setConcessionicausaliByFkAutConccausAcq(concessionicausaliAcq);
	    }
	    if (EntityUtils.getNestedProperty(autorizzazioniCommand.getEntity().getConcessionicausaliByFkAutConccausCess(), "id.codice") != null) {
		Concessionicausali concessionicausaliCess = concessionicausaliService
			.findById(new PkId(autorizzazioniCommand.getEntity().getConcessionicausaliByFkAutConccausCess().getId().getCodice()));
		entity.setConcessionicausaliByFkAutConccausCess(concessionicausaliCess);
	    }
	    autorizzazioniService.update(entity);
	    //autorizzazioniService.updateAutorizzazione(autorizzazioniCommand, false);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, autorizzazioniCommand.getEntity(), true, e);
	    fixRenderEntityProperty(autorizzazioniCommand.getEntity());
	    if (entity.getMovimenti() != null && entity.getMovimenti().getId() != null && entity.getMovimenti().getId().getCodice() != null) {
		Movimenti movimenti = movimentiService.findById(new PkId(entity.getMovimenti().getId().getCodice()));
		Tipiprocedure procedura = movimenti.getIstanza().getProcedura();
		Tipimovimento tipimov = movimenti.getTipomovimento();
		String idcchiusura = (String) EntityUtils.getNestedProperty(procedura, "tipimovimentoChiusura.id.tipomovimento");
		if (StringUtils.isNotBlank(idcchiusura)) {
		    if (idcchiusura.equalsIgnoreCase(tipimov.getId().getTipomovimento())) {
			List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
			model.addAttribute("statiIstanzaList", statiistanzas);
		    }
		}
	    }
	    List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	    model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	    setPageAttributesAutorizzazioni(model, autorizzazioniCommand, false);
	    setPageAttributes(model);
	    return "autorizzazioni/formAutorizzazione";
	}
	//status.setComplete();
	String redirect = "redirect:viewAutorizzazione.htm?codice=" + autorizzazioniCommand.getEntity().getId().getCodice() + "&status_msg=02";
	if (codiceIstanza != null) {
	    redirect += "&codiceIstanza=" + String.valueOf(codiceIstanza);
	}
	if (codiceMovimento != null) {
	    redirect += "&codiceMovimento=" + String.valueOf(codiceMovimento);
	}
	if (codiceAnagrafe != null) {
	    redirect += "&codiceAnagrafe=" + String.valueOf(codiceAnagrafe);
	}
	return redirect;
    }

    /**
     * @param autorizzazioniCommand
     * @param entity
     */
    private void updateStatoIstanza(AutorizzazioniCommand autorizzazioniCommand, Autorizzazioni entity) {

	if (entity.getIstanza() != null && entity.getIstanza().getId() != null && entity.getIstanza().getId().getCodice() != null) {
	    if (autorizzazioniCommand.getStatoistanza() != null && autorizzazioniCommand.getStatoistanza().getId() != null
		    && autorizzazioniCommand.getStatoistanza().getId().getCodicestato() != null) {
		Statiistanza chiusura = statiistanzaService
			.findById(new StatiistanzaId(autorizzazioniCommand.getStatoistanza().getId().getCodicestato()));
		Istanze istanza = istanzeService.findById(new PkId(entity.getIstanza().getId().getCodice()));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
		istanza.setChiusura(chiusura);
		istanzeService.update(istanza);
	    }
	}
    }

    @RequestMapping
    public String validaDeleteAutorizzazione(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe, @RequestParam("return_to_page") String returnToPage,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Autorizzazioni aut = autorizzazioniCommand.getEntity();
	try {
	    if (codiceIstanza != null) {
		Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
		if (istanza != null) {
		    checkAccessoInformazioni(istanza, true);
		}
	    }
	    checkAutorizzazioneisReadonly(codiceIstanza, aut);
	    ValidaEliminazioneAutConcCommand cmd = new ValidaEliminazioneAutConcCommand(returnToPage, aut);
	    model.addAttribute("validaEliminazioneAutConcCommand", cmd);
	    EsitoCancellazioneAutOConc esito = autorizzazioniService.validaCancellazioneAutConc(cmd);
	    if (!esito.isErroreOWarning()) {
		return deleteAutorizzazione(model, cmd, autorizzazioniCommand, result, status, request);
	    }
	    cmd.setEsito(esito);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(autorizzazioniCommand, true, "entity", e);
	    return "redirect:viewAutorizzazione.htm?codice=" + aut.getId().getCodice() + "&codiceIstanza=" + codiceIstanza + "&status_msg=03";
	}
	return "autorizzazioni/verificaCancellazioneAutConc";
    }

    @RequestMapping
    public String deleteAutorizzazione(Model model,
	    @ModelAttribute("validaEliminazioneAutConcCommand") ValidaEliminazioneAutConcCommand validaEliminazioneAutConcCommand,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Autorizzazioni entity = autorizzazioniCommand.getEntity();
	entity = autorizzazioniService.findById(entity.getId());
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	try {
	    autorizzazioniService.deleteAutorizzazione(validaEliminazioneAutConcCommand);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(autorizzazioniCommand, false, "entity", e);
	    return "redirect:viewAutorizzazione.htm?codice=" + entity.getId().getCodice() + "&codiceIstanza=" + codiceIstanza + "&status_msg=03";
	}
	status.setComplete();
	if (codiceIstanza != null) {
	    return "redirect:autorizzazioneDeleted.htm?status_msg=05&codiceIstanza=" + codiceIstanza;
	}
	return "redirect:autorizzazioneDeleted.htm?status_msg=05";
    }

    @RequestMapping
    public String autorizzazioneDeleted(Model model, HttpServletRequest request) {

	AutorizzazioniCommand command = new AutorizzazioniCommand();
	command.setDisplayMode(AutorizzazioniCommand.DELETED);
	model.addAttribute("autorizzazioniCommand", command);
	model.addAttribute("autorizzazioniFilter", new AutorizzazioniFilter());
	return "autorizzazioni/formAutorizzazione";
    }

    @RequestMapping
    public String viewAutorizzazione(@RequestParam("codice") Integer codice,
	    @RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe, Model model, HttpServletRequest request) {

	AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findByAutorizzazioneAttuale(codice);
	Autorizzazioni entity = autorizzazioniService.findById(new PkId(codice));
	Integer codiceIstanza = null;
	if (EntityUtils.getNestedProperty(entity.getIstanza(), "id.codice") != null) {
	    codiceIstanza = entity.getIstanza().getId().getCodice();
	}
	if (!concs.isEmpty()) {
	    return "redirect:viewConcessione.htm?codiceAutorizzazione=" + codice + "&codiceIstanza=" + codiceIstanza;
	}
	autorizzazioniCommand.setEntity(entity);
	autorizzazioniCommand.setDisplayMode(BaseCommand.EDIT);
	fixRenderEntityProperty(entity);
	model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	if (entity.getMovimenti() != null && entity.getMovimenti().getId() != null && entity.getMovimenti().getId().getCodice() != null) {
	    Movimenti movimenti = movimentiService.findById(new PkId(entity.getMovimenti().getId().getCodice()));
	    Tipiprocedure procedura = movimenti.getIstanza().getProcedura();
	    Tipimovimento tipimov = movimenti.getTipomovimento();
	    String idcchiusura = (String) EntityUtils.getNestedProperty(procedura, "tipimovimentoChiusura.id.tipomovimento");
	    if (StringUtils.isNotBlank(idcchiusura) && idcchiusura.equalsIgnoreCase(tipimov.getId().getTipomovimento())) {
		List<Statiistanza> statiistanzas = statiistanzaService.findByStatocomportamentoChiuse();
		model.addAttribute("statiIstanzaList", statiistanzas);
		Istanze istanza = movimenti.getIstanza();
		Statiistanza statiistanza = statiistanzaService.findById(istanza.getChiusura().getId());
		autorizzazioniCommand.setStatoistanza(statiistanza);
	    }
	}
	setPageAttributes(model);
	setPageAttributesAutorizzazioni(model, autorizzazioniCommand, true);
	List<Concessionicausali> concessionicausalisAcq = concessionicausaliService.findAllbyCausaleStorico(false);
	model.addAttribute("concessionicausalisAcq", concessionicausalisAcq);
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("codiceAut", entity.getId().getCodice());
	model.addAttribute("codiceAnagrafe", codiceAnagrafe);
	return "autorizzazioni/formAutorizzazione";
    }

    @RequestMapping
    public String createCessaConcessioniManifestazione(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam(required = false, value = "codiceUso") Integer codiceUso, Model model, HttpServletRequest request) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercato);
	List<Concessionicausali> concessionicausalis = concessionicausaliService.findAllbyCausaleStorico(true);
	if (EntityUtils.getNestedProperty(uso, "id.codice") != null) {
	    if (log.isDebugEnabled()) {
		log.debug("createCessaConcessioniManifestazione# Uso predefinito dall'albero dei procedimenti presente : {}[{}]",
			new Object[] { uso.getDescrizione(), uso.getId().getCodice() });
	    }
	}
	// Se il mercato uso è null allora la ricerca verrà fatta senza considerare l'uso e ritornano tutte le concessioni attive per il mercato
	List<AutorizzazioniConcessioni> concessionis = autorizzazioniConcessioniService.findConcessioniAttive(mercato, uso);
	AutorizzazioniCommand autorizzazioniCommand = new AutorizzazioniCommand();
	autorizzazioniCommand.setDataCessazioneMassiva(new Date());
	model.addAttribute("mercato", mercato);
	model.addAttribute("uso", uso);
	model.addAttribute("mercatiUsos", mercatiUsos);
	model.addAttribute("concessionis", concessionis);
	model.addAttribute("concessionicausalis", concessionicausalis);
	model.addAttribute("autorizzazioniCommand", autorizzazioniCommand);
	return "autorizzazioni/formCessaConcessioni";
    }

    @RequestMapping
    public String cessaConcessioniManifestazione(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("escludiNuoveDaSubentro") boolean escludiNuoveDaSubentro, Model model,
	    @ModelAttribute("autorizzazioniCommand") AutorizzazioniCommand autorizzazioniCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = null;
	boolean isUsoPresente = false;
	if (EntityUtils.getNestedProperty(autorizzazioniCommand.getMercatiUso(), "id.codice") != null) {
	    uso = mercatiUsoService.findById(new PkId(autorizzazioniCommand.getMercatiUso().getId().getCodice()));
	    isUsoPresente = true;
	    if (log.isDebugEnabled()) {
		log.debug("createCessaConcessioniManifestazione# Uso predefinito dall'albero dei procedimenti presente : {}[{}]",
			new Object[] { uso.getDescrizione(), uso.getId().getCodice() });
	    }
	}
	List<Concessionicausali> concessionicausalis = concessionicausaliService.findAllbyCausaleStorico(true);
	Concessionicausali causale = concessionicausaliService
		.findById(new PkId(autorizzazioniCommand.getCausaleCessazioneMassiva().getId().getCodice()));
	try {
	    autorizzazioniService.cessaConcessioniDellaManifestazione(mercato, uso, causale, autorizzazioniCommand.getDataCessazioneMassiva(),
		    escludiNuoveDaSubentro);
	} catch (Exception e) {
	    if (EntityUtils.getNestedProperty(uso, "id.codice") != null) {
		if (log.isDebugEnabled()) {
		    log.debug("createCessaConcessioniManifestazione# Uso predefinito dall'albero dei procedimenti presente : {}[{}]",
			    new Object[] { uso.getDescrizione(), uso.getId().getCodice() });
		}
	    }
	    // Se il mercato uso è null allora la ricerca verrà fatta senza considerare l'uso e ritornano tutte le concessioni attive per il mercato
	    List<AutorizzazioniConcessioni> concessionis = autorizzazioniConcessioniService.findConcessioniAttive(mercato, uso);
	    List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercato);
	    copyErrorsToBindingResult(result, autorizzazioniCommand.getEntity(), true, e);
	    log.error("errore durante la cessazione delle concessioni:{}", e.getMessage());
	    // setPageAttributes(model);
	    model.addAttribute("mercato", mercato);
	    model.addAttribute("uso", uso);
	    model.addAttribute("mercatiUsos", mercatiUsos);
	    model.addAttribute("concessionis", concessionis);
	    model.addAttribute("concessionicausalis", concessionicausalis);
	    return "autorizzazioni/formCessaConcessioni";
	}
	status.setComplete();
	if (isUsoPresente) {
	    return "redirect:createCessaConcessioniManifestazione.htm?codiceMercato=" +
		    codiceMercato +
		    "&codiceUso=" +
		    uso.getId().getCodice() +
		    "&status_msg=02";
	} else {
	    return "redirect:createCessaConcessioniManifestazione.htm?codiceMercato=" + codiceMercato + "&status_msg=02";
	}
    }

    @RequestMapping
    public void ajaxFindAutorizzazioniAndConcessioniByIstanza(@RequestParam("codiceIstanza") Integer codiceistanza, HttpServletResponse response)
	    throws IOException {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	IstanzaAutConcHelper autConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
	StringBuffer buffer = new StringBuffer();
	List<Autorizzazioni> autorizzazionis = autConcHelper.getAutorizzazioni();
	if (autorizzazionis.size() > 0) {
	    buffer = buffer.append("<b>Autorizzazioni</b>:<br />");
	}
	for (Autorizzazioni autorizzazioni : autorizzazionis) {
	    buffer = buffer.append("<b>-</b> ").append(autorizzazioni.getTransientEstremiAut());
	    if (!BooleanUtils.toBoolean(autorizzazioni.getFlagAttiva())) {
		buffer = buffer.append(" <b>[NON ATTIVA]</b>");
	    }
	    buffer = buffer.append("<br />");
	}
	List<AutorizzazioniConcessioni> concessionis = autConcHelper.getConcessioni();
	if (concessionis.size() > 0) {
	    buffer = buffer.append("<b>Concessioni</b>:<br />");
	}
	for (AutorizzazioniConcessioni autorizzazioniConcessioni : concessionis) {
	    buffer = buffer.append("<b>-</b> ").append(autorizzazioniConcessioni.getTransientEstremiConcessione());
	    if (EntityUtils.getNestedProperty(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt(), "id.codice") != null
		    && !BooleanUtils.toBoolean(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutatt().getFlagAttiva())) {
		buffer = buffer.append(" <b>[NON ATTIVA]</b>");
	    }
	    buffer = buffer.append("<br />");
	}
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public void ajaxFindAutorizzazioniAndConcessioniByAttivita(@RequestParam("codiceAttivita") Integer codiceAttivita, HttpServletResponse response)
	    throws IOException {

	StringBuffer buffer = new StringBuffer();
	buffer = createTextAutorizzazioniConcessioniAttivita(codiceAttivita);
	response.setContentType("text/plain");
	response.getWriter().write(buffer.toString());
    }

    @RequestMapping
    public String popupstampa(Model model, @ModelAttribute("autorizzazioniFilter") AutorizzazioniFilter autorizzazioniFilter,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    Anagrafe richiedente = anagrafeService.findById(new PkId(autorizzazioniFilter.getIstanzeFilter().getRichiedente().getId().getCodice()));
	    autorizzazioniFilter.getIstanzeFilter().setRichiedente(richiedente);
	}
	String urlStampa = BackofficeNETConstants.getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI() + "?SoloDocTipo=1&windowed=S";
	StampaAutorizzazioniHelper stampaAutorizzazioniHelper = configStampaAutorizzazioniHelper(autorizzazioniFilter, request);
	model.addAttribute("urlStampe", urlStampa);
	model.addAttribute("stampaAutorizzazioniHelper", stampaAutorizzazioniHelper);
	return "autorizzazioni/popupstampa";
    }

    @SuppressWarnings("unchecked")
    private StampaAutorizzazioniHelper configStampaAutorizzazioniHelper(AutorizzazioniFilter autorizzazioniFilter, HttpServletRequest request) {

	StampaAutorizzazioniHelper stampaAutorizzazioniHelper = new StampaAutorizzazioniHelper();
	String numeroAut = (StringUtils.isNotBlank(autorizzazioniFilter.getAutoriznumero()) ? autorizzazioniFilter.getAutoriznumero() : "");
	stampaAutorizzazioniHelper.setnAutorizzazione(numeroAut);
	String dataAutorizzazione = (autorizzazioniFilter.getDallaData() != null ? (Utilities.formatDate(autorizzazioniFilter.getDallaData(), false))
		: "");
	stampaAutorizzazioniHelper.setDataAutorizzazione(dataAutorizzazione);
	String dataAutorizzazioneFine = (autorizzazioniFilter.getAllaData() != null
		? (Utilities.formatDate(autorizzazioniFilter.getAllaData(), false))
		: "");
	stampaAutorizzazioniHelper.setDataAutorizzazioneFine(dataAutorizzazioneFine);
	String codiceReg = (EntityUtils.getNestedProperty(autorizzazioniFilter.getTipologiaregistro(), "id.codice") != null
		? String.valueOf(autorizzazioniFilter.getTipologiaregistro().getId().getCodice())
		: "");
	stampaAutorizzazioniHelper.setCodiceRegistro(codiceReg);
	String codiceIst = (StringUtils.isNotBlank(autorizzazioniFilter.getIstanzeFilter().getNumeroistanza())
		? autorizzazioniFilter.getIstanzeFilter().getNumeroistanza()
		: "");
	stampaAutorizzazioniHelper.setCodiceIstanza(codiceIst);
	String nominativo = (EntityUtils.getNestedProperty(autorizzazioniFilter.getAnagrafe(), "id.codice") != null
		? autorizzazioniFilter.getAnagrafe().getDescrizioneRichiedente()
		: "");
	stampaAutorizzazioniHelper.setNominativo(nominativo);
	String codiceTitolare = (EntityUtils.getNestedProperty(autorizzazioniFilter.getAnagrafe(), "id.codice") != null
		? String.valueOf(autorizzazioniFilter.getAnagrafe().getId().getCodice())
		: "");
	stampaAutorizzazioniHelper.setCodiceTitolare(codiceTitolare);		
	String codiceRichiedente = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getRichiedente(), "id.codice") != null
		? String.valueOf(autorizzazioniFilter.getIstanzeFilter().getRichiedente().getId().getCodice())
		: "");
	stampaAutorizzazioniHelper.setCodiceRichiedente(codiceRichiedente);
	String dataPresentazione = (autorizzazioniFilter.getIstanzeFilter().getDallaData() != null
		? (Utilities.formatDate(autorizzazioniFilter.getIstanzeFilter().getDallaData(), false))
		: "");
	stampaAutorizzazioniHelper.setDataPresentazione(dataPresentazione);
	String dataPresentazioneFine = (autorizzazioniFilter.getIstanzeFilter().getAllaData() != null
		? (Utilities.formatDate(autorizzazioniFilter.getIstanzeFilter().getAllaData(), false))
		: "");
	stampaAutorizzazioniHelper.setDataPresentazioneFine(dataPresentazioneFine);
	String codiceStradario = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario(),
		"id.codice") != null
			? String.valueOf(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario().getId().getCodice())
			: "");
	stampaAutorizzazioniHelper.setCodiceStradario(codiceStradario);
	String stradario = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario(),
		"id.codice") != null ? autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario().getPrefissoAndDescrizione() : "");
	stampaAutorizzazioniHelper.setStradario(stradario);
	String cap = (StringUtils.isNotBlank(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getCap())
		? autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getCap()
		: "");
	stampaAutorizzazioniHelper.setCap(cap);
	String codiceInterventoProc = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getAlberoproc(), "id.codice") != null
		? String.valueOf(autorizzazioniFilter.getIstanzeFilter().getAlberoproc().getId().getCodice())
		: "");
	stampaAutorizzazioniHelper.setCodiceInterventoProc(codiceInterventoProc);
	String interventoProc = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getAlberoproc(), "id.codice") != null
		? autorizzazioniFilter.getIstanzeFilter().getAlberoproc().getVwAlberoproc().getScDescrizione()
		: "");
	stampaAutorizzazioniHelper.setInterventoProc(interventoProc);
	String codiceProcedura = (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getProcedura(), "id.codice") != null
		? String.valueOf(autorizzazioniFilter.getIstanzeFilter().getProcedura().getId().getCodice())
		: "");
	stampaAutorizzazioniHelper.setCodiceProcedura(codiceProcedura);
	// Nel caso sia passato come filtro tutti i comuni allora verranno presi tutti i comuni attivi per l'operatore
	List<Comuni> comuniassociatiAttiviPerOperatore = (ArrayList<Comuni>) request.getAttribute("comuniassociatiListInRequest");
	String codiceComuni = "";
	for (Comuni comuni : comuniassociatiAttiviPerOperatore) {
	    codiceComuni += comuni.getCodicecomune() + ",";
	}
	codiceComuni = codiceComuni.substring(0, codiceComuni.length() - 1);
	String codiceComune = ((autorizzazioniFilter.getIstanzeFilter().getComune() != null
		&& StringUtils.isNotBlank(autorizzazioniFilter.getIstanzeFilter().getComune().getCodicecomune())
			? autorizzazioniFilter.getIstanzeFilter().getComune().getCodicecomune()
			: codiceComuni));
	stampaAutorizzazioniHelper.setCodiceComune(codiceComune);
	String proprietaOrdimanetoMS = mappingProprietaOrdinamento(autorizzazioniFilter.getOrderBy());
	String tipoOrdimanetoMS = mappingTipoOrdinamento(autorizzazioniFilter.getOrderAscDesc());
	stampaAutorizzazioniHelper.setOrdinamento(proprietaOrdimanetoMS);
	stampaAutorizzazioniHelper.setOrdinamentoASCDESC(tipoOrdimanetoMS);
	return stampaAutorizzazioniHelper;
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

	if (orderBy.equals("autoriznumero,autorizdata,tipologiaregistro.trDescrizione")) {
	    return "Numero Autorizzazione";
	}
	if (orderBy.equals("autorizdata,autoriznumero,tipologiaregistro.trDescrizione")) {
	    return "Data Rilascio";
	}
	if (orderBy.equals("istanza.data")) {
	    return "Data Presentazione";
	}
	if (orderBy.equals("anagrafeautotizzazione")) {
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

    private StringBuffer createTextAutorizzazioniConcessioniAttivita(Integer codiceAttivita) {

	IAttivita iattivita = iAttivitaService.findById(new PkId(codiceAttivita));
	List<Istanze> istanzes = iAttivitaService.findIstanzeOrdinate(iattivita, true);
	StringBuffer buffer = new StringBuffer();
	StringBuffer intestazioneBufferAutt = new StringBuffer("<b>Autorizzazioni</b>:<br />");
	StringBuffer intestazioneBufferCon = new StringBuffer("<b>Concessioni</b>:<br />");
	StringBuffer bufferAutt = new StringBuffer();
	StringBuffer bufferCon = new StringBuffer();
	for (Istanze istanza : istanzes) {
	    IstanzaAutConcHelper autConcHelper = autorizzazioniService.findAutEConcESubByIstanza(istanza);
	    List<Autorizzazioni> autorizzazionis = autConcHelper.getAutorizzazioni();
	    if (!autorizzazionis.isEmpty()) {
		bufferAutt.append("<b>" + istanza.getNumeroistanza() + "</b><br />");
		bufferAutt.append("<ul>");
		for (Autorizzazioni autorizzazioni : autorizzazionis) {
		    bufferAutt.append("<li>");
		    bufferAutt.append(autorizzazioni.getTransientEstremiAut()).append("<br />").append("</li>");
		}
		bufferAutt.append("</ul>");
	    }
	    List<AutorizzazioniConcessioni> concessionis = autConcHelper.getConcessioni();
	    if (!concessionis.isEmpty()) {
		bufferCon.append("<b>" + istanza.getNumeroistanza() + "</b><br />");
		bufferCon.append("<ul>");
		for (AutorizzazioniConcessioni autorizzazioniConcessioni : concessionis) {
		    bufferCon.append("<li> ").append(autorizzazioniConcessioni.getTransientEstremiConcessione()).append("<br />").append("</li>");
		}
		bufferCon.append("</ul>");
	    }
	}
	if (StringUtils.isNotBlank(bufferAutt.toString())) {
	    intestazioneBufferAutt.append(bufferAutt);
	    buffer.append(intestazioneBufferAutt);
	}
	if (StringUtils.isNotBlank(bufferCon.toString())) {
	    intestazioneBufferCon.append(bufferCon);
	    buffer.append("<br />").append(intestazioneBufferCon);
	} else {
	    buffer.append("<br />");
	}
	return buffer;
    }

    @RequestMapping
    public String ajaxCalcolaAreaDehorsDisonibile(@RequestParam("codicearea") Integer codicearea, @RequestParam("mqRichiesti") BigDecimal mqRichiesti,
	    @RequestParam(required = false, value = "codAutPrecNonCessata") Integer codAutPrecNonCessata, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// Nel caso sia presente l'area devo fare il calcolo dello spazio disponibile
	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = dehorsMqIstanzeService.findDehorsMqIstanzeHelper(codicearea);
	// Nel caso sia diverso da null, significa che stiamo subentando ad una autorizzazione dehors non cessata, quindi
	// il record su dehorsMqIstanze per questa autorizzazione avrà flsg cessata =0 ed e è stato considerato nel calcolo dei disponibili.
	// Dovremmo aggiungere il valore di mq assegnati a quello disponibili e sottrarlo a quelli assegnati dell'oggetto 
	// dehorsMqIstanzeHelper per avere la situazion reale
	BigDecimal numeroMqDisponibiliConNuovaRichiesta = new BigDecimal(0);
	if (codAutPrecNonCessata != null && dehorsMqIstanzeHelper != null) {
	    DehorsMqIstanze dehorsMqIstanzeBD = dehorsMqIstanzeService.findAttiveByAutorizzazione(codAutPrecNonCessata);
	    if (EntityUtils.getNestedProperty(dehorsMqIstanzeBD, "id.codice") != null && dehorsMqIstanzeBD.getMqassegnati() != null) {
		if (codicearea.equals(dehorsMqIstanzeBD.getAree().getId().getCodice())) {
		    dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili().add(dehorsMqIstanzeBD.getMqassegnati()));
		    if (mqRichiesti != null) {
			numeroMqDisponibiliConNuovaRichiesta = dehorsMqIstanzeHelper.getDisponibili().subtract(mqRichiesti);
			//dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getMqdisponibili().subtract(subt)));
		    }
		    dehorsMqIstanzeHelper.setAssegnati(dehorsMqIstanzeHelper.getAssegnati().subtract(dehorsMqIstanzeBD.getMqassegnati()));
		} else {
		    numeroMqDisponibiliConNuovaRichiesta = dehorsMqIstanzeHelper.getDisponibili().subtract(mqRichiesti);
		    //dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili().subtract(mqRichiesti));
		}
	    }
	} else {
	    numeroMqDisponibiliConNuovaRichiesta = dehorsMqIstanzeHelper.getDisponibili().subtract(mqRichiesti);
	    // dehorsMqIstanzeHelper.setDisponibili(dehorsMqIstanzeHelper.getDisponibili().subtract(mqRichiesti));
	}
	String jsp_string = "";
	if (numeroMqDisponibiliConNuovaRichiesta.doubleValue() >= 0) {
	    jsp_string = "E’ possibile assegnare i metri quadri";
	} else {
	    jsp_string = "E' possibile assegnare fino a " + dehorsMqIstanzeHelper.getDisponibili() + " mq";
	}
	model.addAttribute("jsp_string", jsp_string);
	model.addAttribute("disponibili", numeroMqDisponibiliConNuovaRichiesta);
	model.addAttribute("dehorsMqIstanzeHelper", dehorsMqIstanzeHelper);
	model.addAttribute("mqRichiesti", mqRichiesti);
	return "autorizzazioni/pannelloMq";
    }

    @Override
    protected void fixMergeEntityProperty(Autorizzazioni entity) {

	Object codiceIstanza = EntityUtils.getNestedProperty(entity, "istanza.id.codice");
	if (codiceIstanza == null) {
	    entity.setIstanza(null);
	}
	Object codiceAnagrafe = EntityUtils.getNestedProperty(entity, "anagrafe.id.codice");
	if (codiceAnagrafe == null) {
	    entity.setAnagrafe(null);
	}
	Object codiceMovimento = EntityUtils.getNestedProperty(entity, "movimenti.id.codice");
	if (codiceMovimento == null) {
	    entity.setMovimenti(null);
	}
	Object codiceRegistro = EntityUtils.getNestedProperty(entity, "tipologiaregistro.id.codice");
	if (codiceRegistro == null) {
	    entity.setTipologiaregistro(null);
	}
	Object codiceCausaleAcq = EntityUtils.getNestedProperty(entity, "concessionicausaliByFkAutConccausAcq.id.codice");
	if (codiceCausaleAcq == null) {
	    entity.setConcessionicausaliByFkAutConccausAcq(null);
	}
	Object codiceCausaleCess = EntityUtils.getNestedProperty(entity, "concessionicausaliByFkAutConccausCess.id.codice");
	if (codiceCausaleCess == null) {
	    entity.setConcessionicausaliByFkAutConccausCess(null);
	}
	Object codiceComune = EntityUtils.getNestedProperty(entity, "autorizcomune.codicecomune");
	if (codiceComune == null) {
	    entity.setAutorizcomune(null);
	}
    }

    protected void fixMergeConcessioniEntityProperty(AutorizzazioniConcessioni entity) {

	if (null != entity) {
	    Object codiceIstanza = EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.istanza.id.codice");
	    if (codiceIstanza == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setIstanza(null);
	    }
	    Object codiceAnagrafe = EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.anagrafe.id.codice");
	    if (codiceAnagrafe == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(null);
	    }
	    Object codiceMovimento = EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.movimenti.id.codice");
	    if (codiceMovimento == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setMovimenti(null);
	    }
	    Object codiceRegistro = EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.tipologiaregistro.id.codice");
	    if (codiceRegistro == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setTipologiaregistro(null);
	    }
	    Object codiceCausaleAcq = EntityUtils.getNestedProperty(entity,
		    "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq.id.codice");
	    if (codiceCausaleAcq == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(null);
	    }
	    Object codiceCausaleCess = EntityUtils.getNestedProperty(entity,
		    "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess.id.codice");
	    if (codiceCausaleCess == null) {
		entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausCess(null);
	    }
	    Object codiceTipoconcessione = EntityUtils.getNestedProperty(entity, "concessionitipi.tipoconcessione");
	    if (codiceTipoconcessione == null || ((String) codiceTipoconcessione).equals("")) {
		entity.setConcessionitipi(null);
	    }
	    Object codiceMercato = EntityUtils.getNestedProperty(entity, "mercati.id.codice");
	    if (codiceMercato == null) {
		entity.setMercati(null);
	    }
	    Object codiceUso = EntityUtils.getNestedProperty(entity, "mercatiUso.id.codice");
	    if (codiceUso == null) {
		entity.setMercatiUso(null);
	    }
	    Object codicePosteggio = EntityUtils.getNestedProperty(entity, "mercatiD.id.codice");
	    if (codicePosteggio == null) {
		entity.setMercatiD(null);
	    }
	    Autorizzazioni autCollegata = entity.getAutorizzazioniByFkAutconcAutcoll();
	    fixMergeEntityProperty(autCollegata);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Autorizzazioni entity) {

	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getOccupante() == null) {
	    entity.setOccupante(new Anagrafe());
	}
	if (entity.getAutorizcomune() == null) {
	    entity.setAutorizcomune(new VwEntilocali());
	}
	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getMovimenti() == null) {
	    entity.setMovimenti(new Movimenti());
	}
	if (entity.getTipologiaregistro() == null) {
	    entity.setTipologiaregistro(new Tipologiaregistri());
	}
	if (entity.getConcessionicausaliByFkAutConccausAcq() == null) {
	    entity.setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	}
	if (entity.getConcessionicausaliByFkAutConccausCess() == null) {
	    entity.setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	}
    }

    protected void fixRenderConcessioniEntityProperty(AutorizzazioniConcessioni entity) {

	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt") == null) {
	    entity.setAutorizzazioniByFkAutconcAutatt(new Autorizzazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.anagrafe") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.occupante") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setOccupante(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.autorizcomune") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setAutorizcomune(new VwEntilocali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.istanza") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.movimenti") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setMovimenti(new Movimenti());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.tipologiaregistro") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setTipologiaregistro(new Tipologiaregistri());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausAcq") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutatt.concessionicausaliByFkAutConccausCess") == null) {
	    entity.getAutorizzazioniByFkAutconcAutatt().setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll") == null) {
	    entity.setAutorizzazioniByFkAutconcAutcoll(new Autorizzazioni());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.anagrafe") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.autorizcomune") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setAutorizcomune(new VwEntilocali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.istanza") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.movimenti") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setMovimenti(new Movimenti());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.tipologiaregistro") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setTipologiaregistro(new Tipologiaregistri());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.concessionicausaliByFkAutConccausAcq") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "autorizzazioniByFkAutconcAutcoll.concessionicausaliByFkAutConccausCess") == null) {
	    entity.getAutorizzazioniByFkAutconcAutcoll().setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	}
	if (EntityUtils.getNestedProperty(entity, "concessionitipi") == null) {
	    entity.setConcessionitipi(new Concessionitipi());
	}
	if (EntityUtils.getNestedProperty(entity.getMercatiUso(), "id.codice") == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
	if (EntityUtils.getNestedProperty(entity.getMercati(), "id.codice") == null) {
	    entity.setMercati(new Mercati());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Concessionicausali> concessionicausalis = concessionicausaliService.findAllbyCausaleStorico(true);
	model.addAttribute("concessionicausalis", concessionicausalis);
    }

    protected void fixFilter(AutorizzazioniFilter autorizzazioniFilter) {

	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getAnagrafe(), "id.codice") != null) {
	    Anagrafe anagrafe = anagrafeService.findById(autorizzazioniFilter.getAnagrafe().getId());
	    if (anagrafe != null) {
		autorizzazioniFilter.setAnagrafe(anagrafe);
	    }
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getTipologiaregistro(), "id.codice") != null) {
	    Tipologiaregistri tipologiaregistri = tipologiaregistriService.findById(autorizzazioniFilter.getTipologiaregistro().getId());
	    autorizzazioniFilter.setTipologiaregistro(tipologiaregistri);
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getAutorizcomune(), "codicecomune") != null) {
	    VwEntilocali comuni = vwEntilocaliService.findById(autorizzazioniFilter.getAutorizcomune().getCodicecomune());
	    autorizzazioniFilter.setAutorizcomune(comuni);
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getComune(), "codicecomune") != null) {
	    Comuni comuni = comuniService.findById(autorizzazioniFilter.getIstanzeFilter().getComune().getCodicecomune());
	    autorizzazioniFilter.getIstanzeFilter().setComune(comuni);
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    Anagrafe anagrafe = anagrafeService.findById(autorizzazioniFilter.getIstanzeFilter().getRichiedente().getId());
	    if (anagrafe != null) {
		autorizzazioniFilter.getIstanzeFilter().setRichiedente(anagrafe);
	    }
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getAlberoproc(), "id.codice") != null) {
	    Alberoproc alberoproc = alberoprocService.findById(autorizzazioniFilter.getIstanzeFilter().getAlberoproc().getId());
	    if (alberoproc != null) {
		autorizzazioniFilter.getIstanzeFilter().setAlberoproc(alberoproc);
	    }
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getProcedura(), "id.codice") != null) {
	    Tipiprocedure procedura = tipiprocedureService.findById(autorizzazioniFilter.getIstanzeFilter().getProcedura().getId());
	    if (procedura != null) {
		autorizzazioniFilter.getIstanzeFilter().setProcedura(procedura);
	    }
	}
	if (EntityUtils.getNestedProperty(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario(), "id.codice") != null) {
	    Stradario stradario = stradarioService.findById(autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().getStradario().getId());
	    if (stradario != null) {
		autorizzazioniFilter.getIstanzeFilter().getIstanzestradario().setStradario(stradario);
	    }
	}
    }

    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String _comune,
	    @ModelAttribute("autorizzazioniFilter") AutorizzazioniFilter autorizzazioniFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	AutorizzazioniExportHelper autorizzazioniExportHelper = new AutorizzazioniExportHelper();
	autorizzazioniExportHelper.setAutorizzazioniFilter(autorizzazioniFilter);
	autorizzazioniExportHelper.setResponsabili(responsabile);
	List<Esportazioni> listaEsportazioni = new ArrayList<Esportazioni>();
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.AUTORIZZAZIONI);
	    if (!listaEsportazioni.isEmpty()) {
		autorizzazioniExportHelper.setEsportazioni(listaEsportazioni.get(0));
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(_comune, Integer.parseInt(codiceEsportazione));
	    ids.add(id);
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.AUTORIZZAZIONI, ids);
	    Esportazioni esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	    autorizzazioniExportHelper.setEsportazioni(esportazioni);
	}
	model.addAttribute("autorizzazioniExportHelper", autorizzazioniExportHelper);
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomuni = responsabiliComuniService.findByOperatore(responsabili);
	String[] codiceComuni = new String[responsabilicomuni.size()];
	int i = 0;
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	return "autorizzazioni/exportAutorizzazioniPentaho";
    }

    @RequestMapping
    public void ajaxEportModalitaPentaho(@RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "data") String data, @RequestParam(required = false, value = "idAccount") String idAccount,
	    Model model, @ModelAttribute("autorizzazioniExportHelper") AutorizzazioniExportHelper autorizzazioniExportHelper, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Pentaho....");
	log.debug("exportModalitaPentaho# Ricerca delle autorizzazioni le salva sulla tabella TMP_ESPORTAZIONI ");
	// Ad oggi sarà sempre null, non viene utilizzata (è previsto l'export solo in data attuale)
	Date _data = null;
	if (StringUtils.isNotBlank(data)) {
	    _data = Utilities.parseDateString(data, false);
	}
	String sessionId = autorizzazioniService.exportModalitaPentaho(autorizzazioniExportHelper, autorizzazioniExportHelper.getEsportazioni(),
		_data, StringUtils.defaultIfEmpty(emailResponsabile, ""), contestoExport, BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di pentaho");
	try {
	    String pathFile = pentahoService.callTrasformazione(autorizzazioniExportHelper.getEsportazioni().getTrasformazione(),
		    autorizzazioniExportHelper.getEsportazioni().getParametriesportaziones(), sessionId, response);
	    //String pathFile = "C:\\Temp\\src.zip";
	    if (isInviaMail == true && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    String codiceComune = null;
		    // TODO settare codice comune			
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
	    log.error("Errore durante la chiamata alla trasformazione {}. Err: {}",
		    new Object[] { autorizzazioniExportHelper.getEsportazioni().getDescrizione(), e.getMessage() });
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    @RequestMapping
    public String modificaOccupante(@RequestParam("idAutConc") Integer idAutConc, @RequestParam("return_to_page") String returnToPage, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	ValidazioneOccupanteCommand validazioneOccupanteCommand = new ValidazioneOccupanteCommand(autorizzazioniService.findById(new PkId(idAutConc)),
		returnToPage);
	model.addAttribute("validazioneOccupanteCommand", validazioneOccupanteCommand);
	return "autorizzazioni/verificaModificaOccupante";
    }

    @RequestMapping
    public String verificaModificaOccupante(Model model, @ModelAttribute("validazioneOccupanteCommand") ValidazioneOccupanteCommand command,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, EventAbortedException, FunzioneBusinessRemotaException {

	command.setEsito(null);
	if (command.isAttoCollegato()) {
	    throw new FunzioneBusinessRemotaException(getMessageFromBundle("label.autorizzazioni_modifica_occupante.errore_atto_collegato", null));
	}
	EsitoModificaOccupante esito = autorizzazioniService.validaModificaOccupante(command.getIdAuOConc(),
		command.getCodiceAnagrafeNuovoOccupante());
	if (!esito.isErroreOWarning()) {
	    return updateModificaOccupante(model, command, result, status, request, response);
	}
	command.setEsito(esito);
	return "autorizzazioni/verificaModificaOccupante";
    }

    @RequestMapping
    public String updateModificaOccupante(Model model, @ModelAttribute("validazioneOccupanteCommand") ValidazioneOccupanteCommand command,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, EventAbortedException, FunzioneBusinessRemotaException {

	if (command.isAttoCollegato()) {
	    throw new FunzioneBusinessRemotaException(getMessageFromBundle("label.autorizzazioni_modifica_occupante.errore_atto_collegato", null));
	}
	autorizzazioniService.updateModificaOccupante(command);
	status.setComplete();
	return "redirect:" + URLDecoder.decode(command.getReturnTo(), "UTF-8") + "&status_msg=02";
    }

    @RequestMapping
    public String modificaDataCessazioneSubentro(@RequestParam("idSubentro") Integer idSubentro, @RequestParam("return_to_page") String returnToPage,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	ValidazioneDataCessazioneSubentroCommand validazioneDataCessazioneSubentroCommand = new ValidazioneDataCessazioneSubentroCommand(
		autorizzazioniSubentriService.findById(new PkId(idSubentro)), returnToPage);
	model.addAttribute("validazioneDataCessazioneSubentroCommand", validazioneDataCessazioneSubentroCommand);
	return "autorizzazioni/verificaModificaDataCessazione";
    }

    @RequestMapping
    public String verificaDataCessazioneSubentro(Model model,
	    @ModelAttribute("validazioneDataCessazioneSubentroCommand") ValidazioneDataCessazioneSubentroCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response)
	    throws JAXBException, IOException, EventAbortedException, OperazioniSubentriException {

	command.setEsito(null);
	EsitoModificaDataCessazione esito = autorizzazioniService.validaModificaDataCessazioneSubentro(command.getIdAutorizzazioniSubentri(),
		command.getNuovaDataCessazione());
	if (!esito.isErroreOWarning()) {
	    return updateModificaDataCessazioneSubentro(model, command, result, status, request, response);
	}
	command.setEsito(esito);
	return "autorizzazioni/verificaModificaDataCessazione";
    }

    @RequestMapping
    public String updateModificaDataCessazioneSubentro(Model model,
	    @ModelAttribute("validazioneDataCessazioneSubentroCommand") ValidazioneDataCessazioneSubentroCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException, EventAbortedException {

	autorizzazioniService.updateModificaDataCessazioneSubentro(command);
	status.setComplete();
	return "redirect:" + URLDecoder.decode(command.getReturnTo(), "UTF-8") + "&status_msg=02";
    }

    @RequestMapping
    public String ricercaPagamentiAutorizzazioniConcessione(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, EventAbortedException {

	Comuni comuni = new Comuni();
	model.addAttribute("comune", comuni);
	return "autorizzazioni/ricercaPagamentiAutorizzazioniConcessione";
    }

    @RequestMapping
    public void findAutorizzazioniConcessione(@RequestParam("numeroAutorizzazione") String numeroAutorizzazione,
	    @RequestParam("codiceComune") String codiceComune, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, EventAbortedException {

	Autorizzazioni aut = autorizzazioniService.findByNumeroAndComune(numeroAutorizzazione, codiceComune);
	response.setContentType("application/json");
	if (aut != null) {
	    String msg = "{\"response\":\"ok\"}";
	    response.getOutputStream().write(msg.getBytes());
	} else {
	    String msg = "{\"response\":\"Numero autorizzazione non valido o comune errato\"}";
	    response.getOutputStream().write(msg.getBytes());
	}
    }

    @RequestMapping
    public String listPagamentiAutorizzazioniConcessione(@RequestParam("numeroAutorizzazione") String numeroAutorizzazione,
	    @RequestParam("codiceComune") String codiceComune, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, EventAbortedException {

	List<PagamentiMercatoRestHelper> result = nodoPagamentiService.getPagamentoByAutorizzazioneComune(mercatiService, numeroAutorizzazione,
		codiceComune);
	for (PagamentiMercatoRestHelper pagamentiMercatoRestHelper : result) {
	    for (PagamentiMercatoGiornoRestHelper pagamentiMercatoGiornoRestHelper : pagamentiMercatoRestHelper.getGiorno()) {
		Collections.sort(pagamentiMercatoGiornoRestHelper.getPagamenti(), new Comparator<PagamentiMercatoPosizDebRestHelper>() {

		    @Override
		    public int compare(PagamentiMercatoPosizDebRestHelper o1, PagamentiMercatoPosizDebRestHelper o2) {

			if (o1.getData_presenza().compareTo(o2.getData_presenza()) > 0) {
			    return -1;
			} else if (o1.getData_presenza().compareTo(o2.getData_presenza()) < 0) {
			    return 1;
			} else {
			    return 0;
			}
		    }
		});
	    }
	}
	model.addAttribute("pagamenti", result);
	return "autorizzazioni/listPagamentiAutorizzazioniConcessione";
    }
}
