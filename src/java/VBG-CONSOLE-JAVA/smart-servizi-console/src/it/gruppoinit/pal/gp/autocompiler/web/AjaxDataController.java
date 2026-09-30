package it.gruppoinit.pal.gp.autocompiler.web;

import it.gruppoinit.pal.gp.areariservata.service.AnagrafeARJService;
import it.gruppoinit.pal.gp.areariservata.ws.client.WsAnagrafe2Helper;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.DomainObjectsUtils;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.ModuloEndoprocedimentoWrapper;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.DomandeFrontOfficeService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.web.util.BindingInitializer;
import it.gruppoinit.pal.gp.core.ws.client.ParixGateWsClient;
import it.gruppoinit.parixgate.ricercaimprese.RISPOSTA;
import it.gruppoinit.parixgate.ricercaimprese.RISPOSTA.DATI.LISTAIMPRESE.ESTREMIIMPRESA;
import it.gruppoinit.parixgate.ricercaimprese.RISPOSTA.DATI.LISTAIMPRESE.ESTREMIIMPRESA.DATIISCRIZIONEREA;
import it.gruppoinit.sde.SdeWSClient;
import it.gruppoinit.sde.anagrafici.CercaStradarioRequest;
import it.gruppoinit.sde.anagrafici.GetPersonaFisicaRequest;
import it.gruppoinit.sde.anagrafici.GetPersonaFisicaResponse;
import it.gruppoinit.sde.anagrafici.ListaStradarioType;
import it.gruppoinit.sde.anagrafici.PersonaFisicaType;
import it.gruppoinit.sde.anagrafici.StradarioType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.AxisFault;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AjaxDataController {

    private static final Logger log = LoggerFactory.getLogger(AjaxDataController.class);
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private WsAnagrafe2Helper wsAnagrafe2Helper;
    @Autowired
    private AnagrafeARJService anagrafeARJService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private EndoRegioneToscanaService endoRegioneToscanaService;
    @Autowired
    private FoDomandeService foDomandeService;
    @Autowired
    private DomandeFrontOfficeService domandeFoService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private SdeproxyService sdeproxyService;
    @Autowired
    private ParixGateWsClient parixGateWsClient;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;

    /*
    @InitBinder
    public void initBinder(WebDataBinder wdb, WebRequest request){
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    CustomDateEditor cde = new CustomDateEditor(sdf, true);
    wdb.registerCustomEditor(Date.class, cde);
    }
    */
    /**
     * Metodo per la ricerca delle persone fisiche utilizzato dai controlli per la compilazione automatica dei campi.
     * L'implementazione deve essere modificata invocando il WS che consente di filtrare la tabella anagrafe escludendo
     * le persone giuridiche, per ora il WS Java non espone questo metodo perciò possono essere restituiti dei risultati
     * che riguardano delle persone giuridiche che hanno il campo codice fiscale valorizzato. Il secondo argomento di
     * tipo {@link Anagrafe} non è necessario alla logica del metodo ma serve solo per forzare Spring ad attivare
     * l'inizializzazione custom del WebDataBinder che viene effettuata dal {@link BindingInitializer} di init
     * (necessaria per restituire le date formattate propriamente).
     */
    @RequestMapping
    public ModelAndView findAnagraficaPF(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Anagrafe> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	Sdeproxy proxy = sdeproxyService.findById(ORMHelper.getIdente());
	List<Anagrafe> results = param;
	PersonaFisicaType a = null;
	if (proxy != null) {
	    if (StringUtils.isNotBlank(proxy.getWsAnagrafeResidenti())) {
		SdeWSClient client = new SdeWSClient(proxy.getWsAnagrafeResidenti(), proxy.getUsernameWs(), proxy.getPasswordWs());
		GetPersonaFisicaRequest parameters = new GetPersonaFisicaRequest();
		parameters.setCodiceCatastaleComune(proxy.getCodicecatastalecomune());
		parameters.setCodiceFiscale(filter);
		// WsAnagrafe2Soap port = wsAnagrafe2Helper.getPersonaFisicaPortWS();
		// it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafe = null;
		GetPersonaFisicaResponse s = null;
		try {
		    s = client.getPersonaFisica(parameters);
		    if (s != null) {
			if (s.getDatiAnagrafici() != null) {
			    a = s.getDatiAnagrafici();
			    results.add(DomainObjectsUtils.anagrafeSdeAsDomainObject(a, comuniService));
			}
		    }
		} catch (Exception e) {
		    log.error("errore nella ricerca della persona fisica {}", e);
		    model.put("error", "Nessun record trovato");
		}
		//List<Anagrafe> results = new ArrayList<Anagrafe>();
	    }
	}
	if (a == null) {
	    model.put("error", "Nessun record trovato");
	}
	//model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findAnagraficaPG(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Anagrafe> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	List<Anagrafe> results = new ArrayList<Anagrafe>();
	try {
	    // Recupero i dati dell'impresa tramite il servizio esposto da parix gate per codice fiscale impresa
	    log.debug("findAnagraficaPG# Recupero i dati dell'impresa per codice fiscale impresa: {}", filter);
	    String resultImpresaNonCessata = parixGateWsClient.getRicercaImpreseNoncessateByCodiceFiscale(filter);
	    RISPOSTA rispostaImpresaNonCessata = (RISPOSTA) Utilities.unMarshallString(resultImpresaNonCessata, RISPOSTA.class);
	    // valuto l'esito, se la risposta è "KO" rilancia l'errore
	    log.debug("findAnagraficaPG# valuto l'esito della chiamata a getRicercaImpreseNoncessateByCodiceFiscale");
	    validaRispostaWS(rispostaImpresaNonCessata);
	    String provinciaRea = "";
	    String numeroRea = "";
	    if (!rispostaImpresaNonCessata.getDATI().getLISTAIMPRESE().getESTREMIIMPRESA().isEmpty()) {
		ESTREMIIMPRESA estremiimpresa = rispostaImpresaNonCessata.getDATI().getLISTAIMPRESE().getESTREMIIMPRESA().get(0);
		List<DATIISCRIZIONEREA> listadatiiscrizioneRea = estremiimpresa.getDATIISCRIZIONEREA();
		for (DATIISCRIZIONEREA datiiscrizionerea : listadatiiscrizioneRea) {
		    if (StringUtils.isNotBlank(datiiscrizionerea.getFLAGSEDE()) && datiiscrizionerea.getFLAGSEDE().equals("SI")) {
			numeroRea = datiiscrizionerea.getNREA();
			provinciaRea = datiiscrizionerea.getCCIAA();
		    }
		}
	    }
	    String resultDettaglioImpresaRidotto = parixGateWsClient.getDettaglioImpresaRidotto(provinciaRea, numeroRea);
	    it.gruppoinit.parixgate.dettaglioridottoimpresa.RISPOSTA rispostaDettaglioImpresaRidotto = (it.gruppoinit.parixgate.dettaglioridottoimpresa.RISPOSTA) Utilities
		    .unMarshallString(resultDettaglioImpresaRidotto, it.gruppoinit.parixgate.dettaglioridottoimpresa.RISPOSTA.class);
	    if (StringUtils.isNotBlank(resultDettaglioImpresaRidotto)) {
		Anagrafe anagrafeOut = DomainObjectsUtils.anagrafeAsDomainObject(rispostaDettaglioImpresaRidotto);
		results.add(anagrafeOut);
	    } else {
		model.put("error", "Nessun record trovato");
	    }
	} catch (AxisFault af) {
	    String errmsg = af.getCause() != null ? af.getCause().getMessage() : af.getMessage();
	    log.error("findAnagraficaPF() - ", af);
	    model.put("error", errmsg);
	} catch (Exception e) {
	    log.error("findAnagraficaPF() - ", e);
	    model.put("error", e.getMessage());
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    /**
     * Verifica se la chiamata è andata a buon fine, in caso contrario rilancia l'errore
     * 
     * @param xml
     * @return
     */
    private boolean validaRispostaWS(RISPOSTA risposta) {

	if ("KO".equalsIgnoreCase(risposta.getHEADER().getESITO())) {
	    String errore = risposta.getDATI().getERRORE().getMSGERR();
	    log.error("validaRispostaWS# esito della chiamta KO: {}", errore);
	    throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa. Dettaglio errore: " + errore);
	}
	//	String esito = "";
	//	try {
	//	    esito = Utilities.getValueFromXml(xml, "/RISPOSTA/HEADER/ESITO");
	//	    if ("KO".equalsIgnoreCase(esito)) {
	//		String errore = Utilities.getValueFromXml(xml, "/RISPOSTA/DATI/ERRORE/MSG_ERR");
	//		log.error("validaRispostaWS# esito della chiamta KO: {}", errore);
	//		throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa. Dettaglio errore: " + errore);
	//	    }
	//	} catch (XPathExpressionException e1) {
	//	    log.error("validaRispostaWS# {}", e1);
	//	    throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa: XML non corretto" + e1.getMessage(), e1);
	//	} catch (ParserConfigurationException e1) {
	//	    log.error("validaRispostaWS# {}", e1);
	//	    throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa: XML non corretto" + e1.getMessage(), e1);
	//	} catch (SAXException e1) {
	//	    log.error("validaRispostaWS# {}", e1);
	//	    throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa: XML non corretto" + e1.getMessage(), e1);
	//	} catch (IOException e1) {
	//	    log.error("validaRispostaWS# {}", e1);
	//	    throw new RuntimeException("Si è verificato un errore nel recupero dei dati dell'impresa: XML non corretto" + e1.getMessage(), e1);
	//	}
	return true;
    }

    @RequestMapping
    public ModelAndView findComuni(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Comuni> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	List<Comuni> results = new ArrayList<Comuni>();
	try {
	    results = comuniService.findByDescrizione(filter, WebConstants.NUM_MAX_RESULTS);
	} catch (Exception e) {
	    log.error("findComuni() - ", e);
	    model.put("error", e.getMessage());
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findComuniAssociati(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Comuniassociati> param) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<Comuniassociati> results = new ArrayList<Comuniassociati>();
	try {
	    results = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	} catch (Exception e) {
	    log.error("findComuniAssociati() - ", e);
	    model.put("error", e.getMessage());
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    /**
     * Metodo che recupera il numero degli allegati della domanda CART attualmente in corso nella sessione utente.
     * 
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView findNumeroAllegati(HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	Integer attachmentsCount = 0;
	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	if (datiDomanda != null && datiDomanda.getDatiContestoDomanda() != null && datiDomanda.getDatiContestoDomanda().getIdDomandaFo() != null) {
	    List<FileInfo> attachments = datiDomanda.getAllegati();
	    for (FileInfo attachment : attachments) {
		// i FileInfo che hanno id semantico non settato sono stati uploadati
		// ma il quadro in cui sono stati caricati non è stato confermato
		// dall'utente e perciò non vengono presi in considerazione
		if (StringUtils.isNotEmpty(attachment.getIdSemantico())) {
		    attachmentsCount++;
		}
	    }
	} else {
	    throw new RuntimeException(
		    "Impossibile identificare la domanda in corso. La sessione utente potrebbe essere scaduta, effettuare un nuovo accesso al sistema.");
	}
	model.put("data", attachmentsCount);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    /**
     * Metodo che recupera i dati dell'utente di sessione e li restituisce in un'oggetto di tipo {@link Anagrafe}
     * 
     * @param request
     * @return
     */
    @RequestMapping
    public ModelAndView findAnagraficaUtente(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Anagrafe> param) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<Anagrafe> results = new ArrayList<Anagrafe>();
	String token = ORMHelper.getToken();
	if (StringUtils.isNotEmpty(token)) {
	    Anagrafe userInfo = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    userInfo = (Anagrafe) DomainObjectsUtils.getDomainObjectAsPojo(userInfo, Anagrafe.class, true);
	    if (userInfo != null) {
		results.add(userInfo);
	    }
	} else {
	    throw new RuntimeException("token di autenticazione mancante.");
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findStradario(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Stradario> param) {

	Map<String, Object> model = new HashMap<String, Object>();
	List<Stradario> results = new ArrayList<Stradario>();
	String filter = request.getParameter("filter_0");
	Sdeproxy proxy = sdeproxyService.findById(ORMHelper.getIdente());
	String codiceComune = proxy.getCodicecatastalecomune(); // lo recupero dalla domanda in sessione
	if (proxy != null) {
	    if (StringUtils.isNotBlank(proxy.getWsStradario())) {
		DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
		if (datiDomanda != null) {
		    if (datiDomanda.getDatiContestoDomanda() != null) {
			codiceComune = StringUtils.defaultIfEmpty(datiDomanda.getDatiContestoDomanda().getCodicecomune(), codiceComune);
		    }
		}
		SdeWSClient client = new SdeWSClient(proxy.getWsStradario(), proxy.getUsernameWs(), proxy.getPasswordWs());
		CercaStradarioRequest parameters = new CercaStradarioRequest();
		parameters.setCodiceCatastaleComune(codiceComune);
		parameters.setTestoDaCercare(filter);
		ListaStradarioType s = null;
		try {
		    s = client.cercaStradario(parameters);
		    if (s != null) {
			List<StradarioType> l = s.getLista();
			for (StradarioType strad : l) {
			    Stradario e = new Stradario();
			    e.setPrefisso(strad.getDug());
			    e.setDescrizione(strad.getToponimo());
			    if (StringUtils.isNotBlank(strad.getCodiceCatastaleComune())) {
				Comuni c = comuniService.findById(strad.getCodiceCatastaleComune());
				e.setComune(c);
			    }
			    e.setCodviario(strad.getCodviario());
			    e.setLocfraz(strad.getLocalita());
			    results.add(e);
			}
		    }
		} catch (Exception e) {
		    log.error("errore nella ricerca dello stradario {}", e);
		    model.put("error", "Nessun record trovato");
		}
	    }
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findCittadinanza(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Stradario> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	List<Cittadinanza> results = new ArrayList<Cittadinanza>();
	try {
	    results = cittadinanzaService.findByDescrizione(filter, WebConstants.NUM_MAX_RESULTS);
	} catch (Exception e) {
	    log.error("findCittadinanza() - ", e);
	    model.put("error", e.getMessage());
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findAllegatiEndo(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<ModuloEndoprocedimentoWrapper> param) {

	String filter_0 = request.getParameter("filter_0");
	String exactMatchFlag = request.getParameter("exact_match");
	String codEndo = request.getParameter("endo");
	String codiceComune = request.getParameter("idComune");
	Boolean exactMatch = new Boolean(exactMatchFlag);
	String allegatoFilter = filter_0;
	FieldOperationsEnum filerType = exactMatch ? FieldOperationsEnum.EQ : FieldOperationsEnum.CONTAINS;
	Pattern allegatoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_DESCALLEGATO);
	Matcher m = allegatoFilterPattern.matcher(filter_0);
	if (m.find()) {
	    allegatoFilter = m.group(1);
	}
	Map<String, Object> model = new HashMap<String, Object>();
	List<ModuloEndoprocedimentoWrapper> results = new ArrayList<ModuloEndoprocedimentoWrapper>();
	Set<String> codEndos = new HashSet<String>();
	codEndos.add(codEndo);
	List<Allegati> allegati = endoRegioneToscanaService.getAllegatiEndoAttivi(codEndos, "", allegatoFilter, filerType, codiceComune);
	ModuloEndoprocedimentoWrapper wrapper = null;
	for (Allegati allegato : allegati) {
	    wrapper = new ModuloEndoprocedimentoWrapper();
	    /*
	     * il download dell'allegato nei vari formati previsti dal BO 
	     * è consentito solo se il formato di partenza dell'allegato è HTML, RTF, DOC o DOCX
	     */
	    Oggetti fileObj = allegato.getOggetti();
	    if (fileObj != null) {
		Integer pkObj = fileObj.getId().getCodice();
		fileObj = oggettiService.findById(new PkId(allegato.getId().getIdcomune(), pkObj));
		if (fileObj != null && fileObj.getOggetto() != null && fileObj.getOggetto().length > 0) {
		    String nomeFile = fileObj.getNomefile() != null ? fileObj.getNomefile() : "";
		    String extension = FilenameUtils.getExtension(nomeFile);
		    if (extension == null) {
			extension = "";
		    }
		    if (!FACCTConstants.CONVERTIBLE_EXTENSIONS.contains(extension.toUpperCase())) {
			allegato.setFoTipodownload("");
		    }
		}
	    }
	    allegato = (Allegati) DomainObjectsUtils.getDomainObjectAsPojo(allegato, Allegati.class, true);
	    wrapper.setAllegato(allegato);
	    results.add(wrapper);
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    //    @RequestMapping
    //    public ModelAndView findAllegatiEndoNoCart(HttpServletRequest request,
    //	    @ModelAttribute(value = "data") ArrayList<ModuloEndoprocedimentoWrapper> param) {
    //
    //	String filter_0 = request.getParameter("filter_0");
    //	String filter_1 = request.getParameter("filter_1");
    //	String exactMatchFlag = request.getParameter("exact_match");
    //	String codEndo = request.getParameter("endo");
    //	Boolean exactMatch = new Boolean(exactMatchFlag);
    //	String idProcFilter = null;
    //	String allegatoFilter = "";
    //	FieldOperationsEnum filerType = exactMatch ? FieldOperationsEnum.EQ : FieldOperationsEnum.CONTAINS;
    //	//String idProcFilterRegex = "^E\\[(\\d+)\\]";
    //	//String allegatoFilterRegex = "-(.+)";
    //	Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
    //	Pattern docAlberoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDALBEROPROCDOC);
    //	Pattern allegatoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_DESCALLEGATO);
    //	String idProcQuery = filter_1 != null ? filter_1 : filter_0;
    //	Matcher m = idProcFilterPattern.matcher(idProcQuery);
    //	if (m.find()) {
    //	    idProcFilter = m.group(1);
    //	} else {
    //	    m = docAlberoFilterPattern.matcher(idProcQuery);
    //	    if (m.find()) {
    //		idProcFilter = m.group(1);
    //	    }
    //	}
    //	if (idProcFilter == null) {
    //	    allegatoFilter = filter_0;
    //	}
    //	if (filter_1 == null) {
    //	    m = allegatoFilterPattern.matcher(filter_0);
    //	    if (m.find()) {
    //		allegatoFilter = m.group(1);
    //	    }
    //	} else {
    //	    allegatoFilter = filter_0;
    //	}
    //	Map<String, Object> model = new HashMap<String, Object>();
    //	List<ModuloEndoprocedimentoWrapper> results = new ArrayList<ModuloEndoprocedimentoWrapper>();
    //	//recupero l'elenco degli endo non CART selezionati nella domanda corrente
    //	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
    //	if (datiDomanda != null) {
    //	    String codiceComune = datiDomanda.getDatiContestoDomanda().getCodicecomune();
    //	    Set<String> endoNoCart = datiDomanda.getDatiContestoDomanda().getEndoNoCartAttivi();
    //	    Set<String> endoNoCart2 = new HashSet<String>();
    //	    for (String endo : endoNoCart) {
    //		PkId id = inventarioprocedimentiService.getIdFromEndoprocedimentoKey(endo);
    //		if (id != null) {
    //		    Inventarioprocedimenti ip = inventarioprocedimentiService.findById(id);
    //		    if (ip != null) { // RICERCO GLI ALLEGATI DELL'ENDO DI TIPO 2 O DEGLI ENDO LOCALI 
    //			// MA NON DI QUELLI DI TIPO 1 - DOVREBBE ESSERCI IL MODULO ENDO 0
    //			if (ip.getStpEndoTipo1s().isEmpty() && ip.getStpEndoTipo2s().isEmpty()) {
    //			    endoNoCart2.add(endo);
    //			}
    //		    }
    //		}
    //	    }
    //	    if (endoNoCart != null) {
    //		Integer idAlberoproc = datiDomanda.getDatiContestoDomanda().getIdAlberoProc();
    //		FoDomande dom = foDomandeService.findById(new PkId(datiDomanda.getDatiContestoDomanda().getIdDomandaFo()));
    //		//		results = endoRegioneToscanaService.getAllegatiDocumentiSchedeEndoAttivi(endoNoCart2, idAlberoproc, idProcFilter, allegatoFilter,
    //		//			filerType, dom.getSdeproxy().getFlagDomandaDinamica(), codiceComune);
    //	    }
    //	} else {
    //	    log.error("findAllegatiEndoNoCart() - impossibile recuperare l'elenco degli endo non CART selezionati perchè non c'è nessuna domanda in sessione");
    //	}
    //	model.put("data", results);
    //	ModelAndView mv = new ModelAndView("jsonView", model);
    //	return mv;
    //    }
    @RequestMapping
    public ModelAndView findEndoNoCart(HttpServletRequest request, @ModelAttribute(value = "data") HashSet<EndoFACCT> param) {

	String filter = request.getParameter("filter_0");
	String exactMatchFlag = request.getParameter("exact_match");
	Boolean exactMatch = new Boolean(exactMatchFlag);
	FieldOperationsEnum filerType = exactMatch ? FieldOperationsEnum.EQ : FieldOperationsEnum.CONTAINS;
	Map<String, Object> model = new HashMap<String, Object>();
	Set<EndoFACCT> results = new HashSet<EndoFACCT>();
	//estrapolo il codice endo da E[]
	Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
	Matcher m = idProcFilterPattern.matcher(filter);
	if (m.find()) {
	    filter = m.group(1);
	}
	//recupero l'elenco degli endo non CART selezionati nella domanda corrente
	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	if (datiDomanda != null) {
	    String codiceComune = datiDomanda.getDatiContestoDomanda().getCodicecomune();
	    Set<String> endosNoCart = datiDomanda.getDatiContestoDomanda().getEndoNoCartAttivi();
	    if (endosNoCart != null && !endosNoCart.isEmpty()) {
		//Integer idAlberoProc = datiDomanda.getDatiContestoDomanda().getIdAlberoProc();
		List<Allegati> allegati = this.endoRegioneToscanaService.getAllegatiEndoAttivi(endosNoCart, filter, "", filerType, codiceComune);
		for (Iterator iterator = allegati.iterator(); iterator.hasNext();) {
		    Allegati allegato = (Allegati) iterator.next();
		    Inventarioprocedimenti invProc = allegato.getInventarioprocedimento();
		    if (invProc != null) {
			EndoFACCT endo = new EndoFACCT();
			endo.setCodiceInventario(invProc.getId().getCodice());
			endo.setIdcomune(invProc.getId().getIdcomune());
			endo.setDescrizione(invProc.getProcedimento());
			endo.setObbligatorio(allegato.getRichiesto());
			results.add(endo);
			//if(invProc.)
		    }
		}
		List<Inventarioprocdyn2modellit> schede = this.endoRegioneToscanaService.getSchedeDinamicheEndoAttivi(endosNoCart, filter, "",
			filerType);
		for (Iterator iterator = schede.iterator(); iterator.hasNext();) {
		    Inventarioprocdyn2modellit scheda = (Inventarioprocdyn2modellit) iterator.next();
		    Inventarioprocedimenti invProc = scheda.getInventarioprocedimenti();
		    if (invProc != null) {
			EndoFACCT endo = new EndoFACCT();
			endo.setCodiceInventario(invProc.getId().getCodice());
			endo.setIdcomune(invProc.getId().getIdcomune());
			endo.setDescrizione(invProc.getProcedimento());
			endo.setObbligatorio(!scheda.getFlagFacoltativa());
			results.add(endo);
		    }
		}
		//results = endoRegioneToscanaService.getEndoLocaliSelezionati(idAlberoProc, endosNoCart, filter);
	    }
	} else {
	    log.error("findAllegatiEndoNoCart() - impossibile recuperare l'elenco degli endo non CART selezionati perchè non c'è nessuna domanda in sessione");
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findComuneSuap(HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	Comuni com = new Comuni();
	if (datiDomanda != null) {
	    Integer idDom = datiDomanda.getDatiContestoDomanda().getIdDomandaFo();
	    if (idDom != null) {
		FoDomande domande = foDomandeService.findById(new PkId(idDom));
		if (domande != null) {
		    // com = domande.getComuni();
		}
	    }
	}
	model.put("comune", com);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findEnte(HttpServletRequest request) {

	Map<String, Object> model = new HashMap<String, Object>();
	Sdeproxy ente = this.sdeproxyService.findByIdEnte(ORMHelper.getIdente());
	model.put("sdeproxy", ente);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }
}
