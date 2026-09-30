package it.gruppoinit.pal.gp.autocompiler.web;

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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.areariservata.web.BaseController;
import it.gruppoinit.pal.gp.areariservata.ws.client.WsAnagrafe2Helper;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocdyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.DomainObjectsUtils;
import it.gruppoinit.pal.gp.core.domain.autocompiler.utils.ModuloEndoprocedimentoWrapper;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.EndoFACCT;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.web.util.BindingInitializer;
import it.gruppoinit.wsanagrafe2.ws.WsAnagrafe2Soap;

@Controller
public class AjaxDataController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AjaxDataController.class);
    private static final List<String> CONVERTIBLE_EXTENSIONS = new ArrayList<String>();
    static {
	CONVERTIBLE_EXTENSIONS.add("HTML");
	CONVERTIBLE_EXTENSIONS.add("HTM");
	CONVERTIBLE_EXTENSIONS.add("RTF");
	CONVERTIBLE_EXTENSIONS.add("ODT");
	CONVERTIBLE_EXTENSIONS.add("DOC");
	CONVERTIBLE_EXTENSIONS.add("DOCX");
    }
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private WsAnagrafe2Helper wsAnagrafe2Helper;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private EndoRegioneToscanaService endoRegioneToscanaService;
    @Autowired
    private OggettiService oggettiService;

    /*
     * @InitBinder public void initBinder(WebDataBinder wdb, WebRequest
     * request){ SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
     * CustomDateEditor cde = new CustomDateEditor(sdf, true);
     * wdb.registerCustomEditor(Date.class, cde); }
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
	String token = ORMHelper.getToken();
	WsAnagrafe2Soap port = wsAnagrafe2Helper.getPersonaFisicaPortWS();
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafe = null;
	// List<Anagrafe> results = new ArrayList<Anagrafe>();
	List<Anagrafe> results = param;
	try {
	    anagrafe = port.getPersonaFisica(token, filter);
	    if (null != anagrafe) {
		results.add(DomainObjectsUtils.anagrafeAsDomainObject(anagrafe));
	    } else {
		model.put("error", "Nessun record trovato");
	    }
	} catch (Exception e) {
	    log.error("findAnagraficaPF() - ", e);
	    model.put("error", e.getMessage());
	}
	// model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findAnagraficaPG(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Anagrafe> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	String token = ORMHelper.getToken();
	WsAnagrafe2Soap port = wsAnagrafe2Helper.getPersonaGiuridicaPortWS();
	it.gruppoinit.wsanagrafe2.schema.Anagrafe anagrafe = null;
	List<Anagrafe> results = new ArrayList<Anagrafe>();
	try {
	    anagrafe = port.getPersonaGiuridica(token, filter);
	    if (null != anagrafe) {
		results.add(DomainObjectsUtils.anagrafeAsDomainObject(anagrafe));
	    } else {
		model.put("error", "Nessun record trovato");
	    }
	} catch (Exception e) {
	    log.error("findAnagraficaPF() - ", e);
	    model.put("error", e.getMessage());
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findComuni(HttpServletRequest request, @ModelAttribute(value = "data") ArrayList<Comuni> param) {

	String filter = request.getParameter("filter_0");
	Map<String, Object> model = new HashMap<String, Object>();
	List<Comuni> results = new ArrayList<Comuni>();
	try {
	    results = comuniService.findByDescrizione(filter);
	} catch (Exception e) {
	    log.error("findComuni() - ", e);
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
	    // attachmentsCount =
	    // this.domandeFoService.contaAllegatiDomanda(datiDomanda.getDatiContestoDomanda().getIdDomandaFo());
	    List<FileInfo> attachments = datiDomanda.getAllegati();
	    for (FileInfo attachment : attachments) {
		// i FileInfo che hanno id semantico non settato sono stati
		// uploadati
		// ma il quadro in cui sono stati caricati non è stato
		// confermato
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
	String idComune = request.getParameter("idComune");
	String filter = request.getParameter("filter_0");
	if (StringUtils.isNotEmpty(filter)) {
	    results = stradarioService.findByDescrizione(filter, idComune, 0, WebConstants.NUM_MAX_RESULTS, false);
	    results = (List<Stradario>) DomainObjectsUtils.getDomainObjectListAsPojoList(results, Stradario.class, 0);
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findAllegatiEndoNoCart(HttpServletRequest request,
	    @ModelAttribute(value = "data") ArrayList<ModuloEndoprocedimentoWrapper> param) {

	String filter_0 = request.getParameter("filter_0");
	String filter_1 = request.getParameter("filter_1");
	String exactMatchFlag = request.getParameter("exact_match");
	Boolean exactMatch = new Boolean(exactMatchFlag);
	String idProcFilter = null;
	String allegatoFilter = "";
	FieldOperationsEnum filerType = exactMatch ? FieldOperationsEnum.EQ : FieldOperationsEnum.CONTAINS;
	// String idProcFilterRegex = "^E\\[(\\d+)\\]";
	// String allegatoFilterRegex = "-(.+)";
	Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
	Pattern docAlberoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDALBEROPROCDOC);
	Pattern allegatoFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_DESCALLEGATO);
	String idProcQuery = filter_1 != null ? filter_1 : filter_0;
	Matcher m = idProcFilterPattern.matcher(idProcQuery);
	if (m.find()) {
	    idProcFilter = m.group(1);
	} else {
	    m = docAlberoFilterPattern.matcher(idProcQuery);
	    if (m.find()) {
		idProcFilter = m.group(1);
	    }
	}
	if (idProcFilter == null) {
	    allegatoFilter = filter_0;
	}
	if (filter_1 == null) {
	    m = allegatoFilterPattern.matcher(filter_0);
	    if (m.find()) {
		allegatoFilter = m.group(1);
	    }
	} else {
	    allegatoFilter = filter_0;
	}
	Map<String, Object> model = new HashMap<String, Object>();
	List<ModuloEndoprocedimentoWrapper> results = new ArrayList<ModuloEndoprocedimentoWrapper>();
	// recupero l'elenco degli endo non CART selezionati nella domanda
	// corrente
	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	if (datiDomanda != null) {
	    List<String> endoNoCart = datiDomanda.getDatiContestoDomanda().getEndoNoCartAttivi();
	    if (endoNoCart != null) {
		List<Allegati> allegati = new ArrayList<Allegati>();
		List<AlberoprocDocumenti> alberoDocs = new ArrayList<AlberoprocDocumenti>();
		List<Inventarioprocdyn2modellit> schede = new ArrayList<Inventarioprocdyn2modellit>();
		ModuloEndoprocedimentoWrapper wrapper = null;
		try {
		    allegati = this.endoRegioneToscanaService.getAllegatiEndoAttivi(endoNoCart, idProcFilter, allegatoFilter, filerType);
		    allegati = DomainObjectsUtils.getDomainObjectListAsPojoList(allegati, Allegati.class);
		    for (Allegati allegato : allegati) {
			wrapper = new ModuloEndoprocedimentoWrapper();
			/*
			 * il download dell'allegato nei vari formati previsti
			 * dal BO è consentito solo se il formato di partenza
			 * dell'allegato è HTML, RTF, DOC o DOCX
			 */
			Oggetti fileObj = allegato.getOggetti();
			Integer pkObj = allegato.getOggetti().getId().getCodice();
			fileObj = oggettiService.findById(new PkId(pkObj));
			if (fileObj != null && fileObj.getOggetto() != null && fileObj.getOggetto().length > 0) {
			    String nomeFile = fileObj.getNomefile() != null ? fileObj.getNomefile() : "";
			    String extension = FilenameUtils.getExtension(nomeFile);
			    if (extension == null) {
				extension = "";
			    }
			    if (!CONVERTIBLE_EXTENSIONS.contains(extension.toUpperCase())) {
				allegato.setFoTipodownload("");
			    }
			}
			allegato = (Allegati) DomainObjectsUtils.getDomainObjectAsPojo(allegato, Allegati.class, true);
			wrapper.setAllegato(allegato);
			results.add(wrapper);
		    }
		    alberoDocs = this.endoRegioneToscanaService
			    .getDocumentiEreditatiEndoAttivi(datiDomanda.getDatiContestoDomanda().getIdAlberoProc(), allegatoFilter, filerType);
		    alberoDocs = DomainObjectsUtils.getDomainObjectListAsPojoList(alberoDocs, AlberoprocDocumenti.class);
		    for (AlberoprocDocumenti alberoDoc : alberoDocs) {
			wrapper = new ModuloEndoprocedimentoWrapper();
			Oggetti fileObj = alberoDoc.getOggetto();
			if (fileObj != null && fileObj.getId() != null && fileObj.getId().getCodice() != null) {
			    Integer pkObj = fileObj.getId().getCodice();
			    fileObj = oggettiService.findById(new PkId(pkObj));
			    if (fileObj != null && fileObj.getOggetto() != null && fileObj.getOggetto().length > 0) {
				String nomeFile = fileObj.getNomefile() != null ? fileObj.getNomefile() : "";
				String extension = FilenameUtils.getExtension(nomeFile);
				if (extension == null) {
				    extension = "";
				}
				if (!CONVERTIBLE_EXTENSIONS.contains(extension.toUpperCase())) {
				    alberoDoc.setFoTipodownload("");
				}
			    }
			}
			alberoDoc = (AlberoprocDocumenti) DomainObjectsUtils.getDomainObjectAsPojo(alberoDoc, AlberoprocDocumenti.class, true);
			wrapper.setDocumentoAlbero(alberoDoc);
			results.add(wrapper);
		    }
		    schede = this.endoRegioneToscanaService.getSchedeDinamicheEndoAttivi(endoNoCart, idProcFilter, allegatoFilter, filerType);
		    schede = DomainObjectsUtils.getDomainObjectListAsPojoList(schede, Inventarioprocdyn2modellit.class);
		    for (Inventarioprocdyn2modellit scheda : schede) {
			wrapper = new ModuloEndoprocedimentoWrapper();
			wrapper.setSchedaDinamica(scheda);
			results.add(wrapper);
		    }
		} catch (Exception e) {
		    log.error("findAllegatiEndoNoCart() - ", e);
		    model.put("error", e.getMessage());
		}
	    }
	} else {
	    log.error(
		    "findAllegatiEndoNoCart() - impossibile recuperare l'elenco degli endo non CART selezionati perchè non c'è nessuna domanda in sessione");
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }

    @RequestMapping
    public ModelAndView findEndoNoCart(HttpServletRequest request, @ModelAttribute(value = "data") HashSet<EndoFACCT> param) {

	String filter = request.getParameter("filter_0");
	String exactMatchFlag = request.getParameter("exact_match");
	Boolean exactMatch = new Boolean(exactMatchFlag);
	FieldOperationsEnum filerType = exactMatch ? FieldOperationsEnum.EQ : FieldOperationsEnum.CONTAINS;
	Map<String, Object> model = new HashMap<String, Object>();
	Set<EndoFACCT> results = new HashSet<EndoFACCT>();
	// estrapolo il codice endo da E[]
	Pattern idProcFilterPattern = Pattern.compile(FACCTConstants.REGEX_ALLEGATINOCART_IDPROCEDIMENTO);
	Matcher m = idProcFilterPattern.matcher(filter);
	if (m.find()) {
	    filter = m.group(1);
	}
	// recupero l'elenco degli endo non CART selezionati nella domanda
	// corrente
	DatiDomandaCart datiDomanda = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	if (datiDomanda != null) {
	    List<String> endosNoCart = datiDomanda.getDatiContestoDomanda().getEndoNoCartAttivi();
	    if (endosNoCart != null && !endosNoCart.isEmpty()) {
		// Integer idAlberoProc =
		// datiDomanda.getDatiContestoDomanda().getIdAlberoProc();
		List<Allegati> allegati = this.endoRegioneToscanaService.getAllegatiEndoAttivi(endosNoCart, filter, "", filerType);
		for (Iterator iterator = allegati.iterator(); iterator.hasNext();) {
		    Allegati allegato = (Allegati) iterator.next();
		    Inventarioprocedimenti invProc = allegato.getInventarioprocedimento();
		    if (invProc != null) {
			EndoFACCT endo = new EndoFACCT();
			endo.setCodiceInventario(invProc.getId().getCodice());
			endo.setDescrizione(invProc.getProcedimento());
			endo.setObbligatorio(allegato.getRichiesto());
			results.add(endo);
			// if(invProc.)
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
			endo.setDescrizione(invProc.getProcedimento());
			endo.setObbligatorio(!scheda.getFlagFacoltativa());
			results.add(endo);
		    }
		}
		// results =
		// endoRegioneToscanaService.getEndoLocaliSelezionati(idAlberoProc,
		// endosNoCart, filter);
	    }
	} else {
	    log.error(
		    "findAllegatiEndoNoCart() - impossibile recuperare l'elenco degli endo non CART selezionati perchè non c'è nessuna domanda in sessione");
	}
	model.put("data", results);
	ModelAndView mv = new ModelAndView("jsonView", model);
	return mv;
    }
}
