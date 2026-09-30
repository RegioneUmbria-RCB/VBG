package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
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
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniTMercato;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PosteggiSettori;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeConcessioniMercatoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioMercatiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.esportazioni.model.EsportazioniPentahoEsportazioneModel;
import it.gruppoinit.pal.gp.core.features.esportazioni.model.EsportazioniPentahoParametri;
import it.gruppoinit.pal.gp.core.features.esportazioni.model.EsportazioniPentahoResponsabili;
import it.gruppoinit.pal.gp.core.features.esportazioni.model.manifestazioni.EsportazioniPentahoMercatiDCommand;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniTMercatoService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MercatiDLivelloServizioService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.PosteggiSettoriService;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "esportazioniPentahoMercatiDCommand", "mercatid", "mercatidattivitaistat", "mercatiDCommand" })
public class MercatiDController extends BaseController<MercatiD> {

    @Autowired
    private MercatiDService mercatidService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private PosteggitipospazioService posteggitipospazioService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    @Autowired
    private PosteggiSettoriService posteggiSettoriService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private ComunicazioniTMercatoService comunicazioniTMercatoService;
    @Autowired
    private MercatiDLivelloServizioService mercatiDLivelloServizioService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeprocedimentiController.class);

    @RequestMapping
    public ModelMap list(@RequestParam("codicemercato") Integer codicemercato, @RequestParam(value = "codiceuso", required = false) Integer codiceuso,
	    @ModelAttribute("filtro") MercatiD mercatiD, HttpServletRequest request, HttpServletResponse response) {

	ModelMap model = new ModelMap();
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	mercatiD.setMercati(mercati);
	// gestisce la configurazione utente, permette di memorizzare su DB e riprensetare all'accesso successivo le
	// scelte per quanto riguarda le informazioni da visualizzare sui posteggi
	// -----INIZIO GESTIONE CONFIGURAZIONE UTENTE----------------------
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI);
	Configurazioneutente showHideInfomegeologieConf = configurazioneutenteService.findById(confUteId);
	String showHideMercelogieinfoDefault = "1";
	if (showHideInfomegeologieConf == null) {
	    // .. inserisco i valori di default
	    showHideInfomegeologieConf = new Configurazioneutente();
	    showHideInfomegeologieConf.setId(confUteId);
	    showHideInfomegeologieConf.setResponsabile(responsabile);
	    showHideInfomegeologieConf.setValore(showHideMercelogieinfoDefault);
	    configurazioneutenteService.insert(showHideInfomegeologieConf);
	} else {
	    showHideMercelogieinfoDefault = showHideInfomegeologieConf.getValore();
	}
	ConfigurazioneutenteId confAltreinfoUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI);
	Configurazioneutente showHideAltreInfoConf = configurazioneutenteService.findById(confAltreinfoUteId);
	String showAltreInfoHideValoreDefault = "1";
	if (showHideAltreInfoConf == null) {
	    // .. inserisco i valori di default
	    showHideAltreInfoConf = new Configurazioneutente();
	    showHideAltreInfoConf.setId(confUteId);
	    showHideAltreInfoConf.setResponsabile(responsabile);
	    showHideAltreInfoConf.setValore(showAltreInfoHideValoreDefault);
	    configurazioneutenteService.insert(showHideAltreInfoConf);
	} else {
	    showAltreInfoHideValoreDefault = showHideAltreInfoConf.getValore();
	}
	ConfigurazioneutenteId confFiltroPosteggiUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.VISUALIZZA_FILTRO_POSTEGGI);
	Configurazioneutente showHideFiltroPosteggiConf = configurazioneutenteService.findById(confFiltroPosteggiUteId);
	String showFiltroPosteggiHideValoreDefault = "1";
	if (showHideFiltroPosteggiConf == null) {
	    // .. inserisco i valori di default
	    showHideFiltroPosteggiConf = new Configurazioneutente();
	    showHideFiltroPosteggiConf.setId(confUteId);
	    showHideFiltroPosteggiConf.setResponsabile(responsabile);
	    showHideFiltroPosteggiConf.setValore(showFiltroPosteggiHideValoreDefault);
	    configurazioneutenteService.insert(showHideFiltroPosteggiConf);
	} else {
	    showFiltroPosteggiHideValoreDefault = showHideFiltroPosteggiConf.getValore();
	}
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MERCATI_VIS_POSTEGGILISTA, "0", request);
	// -----  FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	// mette sulla request le informazioni recuperate dalla configurazione e le usa per gestire le regole di
	// visualizzazione sulla jsp
	request.setAttribute(WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI, showHideMercelogieinfoDefault);
	request.setAttribute(WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI, showAltreInfoHideValoreDefault);
	request.setAttribute(WebConstants.VISUALIZZA_FILTRO_POSTEGGI, showFiltroPosteggiHideValoreDefault);
	// ------------------- FINE CONFIGURAZIONE UTENTE --------------------------------------------------
	// verifico se per il mercato sono state eseguite comunicazioni
	boolean isExistComunicazione = comunicazioniTMercatoService.isExistByCodiceMercato(codicemercato);
	model.addAttribute("isExistComunicazione", isExistComunicazione);
	//List<PosteggioMercatiHelper> _mercatidList = mercatidService.findMercatiDWithConcessioniSQL(codicemercato, mercatiD, false);
	List<PosteggioMercatiHelper> _mercatidList = new ArrayList<PosteggioMercatiHelper>();
	if (EntityUtils.getNestedProperty(mercatiD.getMercatiUsoTransient(), "id.codice") == null) {
	    _mercatidList = mercatidService.findMercatiDWithConcessioniAndAvvisi(codicemercato, mercatiD, false);
	} else {
	    _mercatidList = mercatidService.findMercatiDWithConcessioniAndAvvisi(codicemercato, mercatiD, false,
		    mercatiD.getMercatiUsoTransient().getId().getCodice());
	}
	Set<AutorizzazioniConcessioni> autConcessioni = new HashSet<AutorizzazioniConcessioni>();
	for (PosteggioMercatiHelper posteggioMercatiHelper : _mercatidList) {
	    for (IstanzeConcessioniMercatoHelper istanzeConcessioniMercatoHelper : posteggioMercatiHelper.getIstanzeConcessioniMercatoHelpers()) {
		List<AutorizzazioniConcessioni> autConc = autorizzazioniConcessioniService
			.findConcessioniByIstanza(istanzeConcessioniMercatoHelper.getCodiceistanza());
		for (AutorizzazioniConcessioni autorizzazioniConcessioni : autConc) {
		    if (autorizzazioniConcessioni != null && autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll() != null
			    && autorizzazioniConcessioni.getId() != null) {
			autConcessioni.add(autorizzazioniConcessioni);
		    }
		}
	    }
	}
		
	
	Map<Integer, List<MercatiDLivelloServizioDTO>> livelliServizioMap = new HashMap<Integer, List<MercatiDLivelloServizioDTO>>();
	if(_mercatidList != null){
	    for (PosteggioMercatiHelper posteggioMercatiHelper : _mercatidList) {
		if(  posteggioMercatiHelper.getPosteggioInfoHelper() != null && posteggioMercatiHelper.getPosteggioInfoHelper().getId() != null && 
			posteggioMercatiHelper.getPosteggioInfoHelper().getId().getCodice() != null ){
		    livelliServizioMap.put(posteggioMercatiHelper.getPosteggioInfoHelper().getId().getCodice(), mercatiDLivelloServizioService.findByPosteggio(posteggioMercatiHelper.getPosteggioInfoHelper().getId().getCodice(), true, false));
		}
	    }
	}
	
	model.addAttribute("_mercatidList", _mercatidList);
	model.addAttribute("mercati", mercati);
	mercatiD.setMercati(mercati);
	model.addAttribute("mercatid", mercatiD);
	model.addAttribute("autorizzazioniConcessioni", autConcessioni);
	model.addAttribute("livelliServizioMap", livelliServizioMap);
	
	model.addAttribute("CONF_LISTPOSTEGGI_VISDETTAGLI", leggiParametroConfigurazioneUtente("LISTPOSTEGGI_VISDETTAGLI", "0", request));
	model.addAttribute("CONF_LISTPOSTEGGI_VISCONCESSIONARI", leggiParametroConfigurazioneUtente("LISTPOSTEGGI_VISCONCESSIONARI", "0", request));
	model.addAttribute("CONF_LISTPOSTEGGI_VISMERCEOLOGIE", leggiParametroConfigurazioneUtente("LISTPOSTEGGI_VISMERCEOLOGIE", "0", request));
	model.addAttribute("CONF_LISTPOSTEGGI_VISLVS", leggiParametroConfigurazioneUtente("LISTPOSTEGGI_VISLVS", "0", request));
	
	
	return model;
    }

    @RequestMapping
    public ModelMap listconfigurazione(@RequestParam("codicemercato") Integer codicemercato, HttpServletRequest request,
	    HttpServletResponse response) {

	ModelMap model = new ModelMap();
	List<Integer> listMercatiD = mercatidService.findByCodiceByMercato(codicemercato, PosteggiEnum.ALL);
	model.addAttribute("listMercatiD", listMercatiD);
	model.addAttribute("mercatid", new MercatiDDTO());
	model.addAttribute("sizeListaPosteggi", listMercatiD.size());
	model.addAttribute("codicemercato", codicemercato);
	model.addAttribute("mercatid", new MercatiD());
	return model;
    }

    @RequestMapping
    public String ajaxDettaglioPosteggio(@RequestParam("codiceposteggio") Integer codiceposteggio,
	    @RequestParam("codicemercato") Integer codicemercato, @RequestParam("indice") Integer indice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	MercatiDDTO posteggio = mercatidService.findById(codiceposteggio);
	List<MercatiDLivelloServizioDTO> listServizi = mercatiDLivelloServizioService.findByPosteggio(codiceposteggio, true, false);
	//model.addAttribute("posteggio", posteggio);
	//Togliere
	int sizeListaPosteggi = mercatidService.countByMercato(codicemercato, PosteggiEnum.ALL);
	model.addAttribute("sizeListaPosteggi", sizeListaPosteggi);
	model.addAttribute("codicemercato", codicemercato);
	model.addAttribute("mercatid", posteggio);
	model.addAttribute("indice", indice);
	model.addAttribute("listServizi", listServizi);
	return "mercatid/ajaxDettaglioPosteggio";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping
    public ModelAndView ajaxChangeValue(@RequestParam("codiceposteggio") Integer codiceposteggio, @RequestParam("value") String value,
	    @RequestParam("nomecampo") String nomecampo, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	Map map = new HashMap();
	try {
	    MercatiD mercatiD = mercatidService.findById(new PkId(codiceposteggio));
	    if (nomecampo.equals("lunghezza")) {
		mercatiD.setLunghezza(new BigDecimal(value));
		if (mercatiD.getLarghezza() != null) {
		    mercatiD.setSuperficie(mercatiD.getLarghezza().multiply(mercatiD.getLunghezza()));
		}
	    } else if (nomecampo.equals("larghezza")) {
		mercatiD.setLarghezza(new BigDecimal(value));
		if (mercatiD.getLunghezza() != null) {
		    mercatiD.setSuperficie(mercatiD.getLarghezza().multiply(mercatiD.getLunghezza()));
		}
	    } else if (nomecampo.equals("superficie")) {
		mercatiD.setSuperficie(new BigDecimal(value));
		mercatiD.setLarghezza(null);
		mercatiD.setLunghezza(null);
	    } else if (nomecampo.equals("indirizzo")) {
		Stradario s = stradarioService.findById(new PkId(Integer.parseInt(value)));
		mercatiD.setStradario(s);
	    }
	    mercatidService.update(mercatiD);
	    map.put("lunghezza", mercatiD.getLunghezza());
	    map.put("larghezza", mercatiD.getLarghezza());
	    map.put("superficie", mercatiD.getSuperficie());
	    map.put("risultato", getMessageFromBundle("label.datoaggiornato", null));
	    map.put("status", "ok");
	    //	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    map.put("risultato", getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	    map.put("status", "ko");
	    // response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
	return new ModelAndView("jsonView", map);
    }

    @RequestMapping
    public String create(@RequestParam("codicemercato") Integer codicemercato, Model model) {

	// Reupera il mercato corrente
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	MercatiD mercatid = new MercatiD();
	// Setto il mercato nel posteggio che sto creando
	mercatid.setMercati(mercati);
	fixRenderEntityProperty(mercatid);
	model.addAttribute("mercatid", mercatid);
	model.addAttribute("mercati", mercati);
	setPageAttributes(model);
	return "mercatid/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercatid") MercatiD mercatid, BindingResult result, SessionStatus status) {

	// Recupero se inserto il Tipo Spazio
	if (mercatid.getTipoSpazio() != null && mercatid.getTipoSpazio().getId().getCodice() != null) {
	    Posteggitipospazio posteggitipospazio = posteggitipospazioService.findById(new PkId(mercatid.getTipoSpazio().getId().getCodice()));
	    mercatid.setTipoSpazio(posteggitipospazio);
	}
	if (mercatid.getPosteggiSettori() != null && mercatid.getPosteggiSettori().getId().getCodice() != null) {
	    PosteggiSettori posteggiS = posteggiSettoriService.findById(new PkId(mercatid.getPosteggiSettori().getId().getCodice()));
	    mercatid.setPosteggiSettori(posteggiS);
	}
	// Recupero se inserito Lo stradario
	if (mercatid.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatid.getStradario().getId());
	    mercatid.setStradario(stradario);
	}
	fixMergeEntityProperty(mercatid);
	try {
	    mercatidService.insert(mercatid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid, e);
	    model.addAttribute("mercati", mercatid.getMercati());
	    setPageAttributes(model);
	    fixRenderEntityProperty(mercatid);
	    return "mercatid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatid.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	MercatiD mercatid = mercatidService.findById(id);
	fixRenderEntityProperty(mercatid);
	model.addAttribute("mercatid", mercatid);
	model.addAttribute("mercati", mercatid.getMercati());
	setPageAttributes(model);
	return "mercatid/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercatid") MercatiD mercatid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero se inserto il Tipo Spazio
	if (mercatid.getTipoSpazio() != null && mercatid.getTipoSpazio().getId().getCodice() != null) {
	    Posteggitipospazio posteggitipospazio = posteggitipospazioService.findById(new PkId(mercatid.getTipoSpazio().getId().getCodice()));
	    mercatid.setTipoSpazio(posteggitipospazio);
	}
	if (mercatid.getPosteggiSettori() != null && mercatid.getPosteggiSettori().getId().getCodice() != null) {
	    PosteggiSettori posteggiS = posteggiSettoriService.findById(new PkId(mercatid.getPosteggiSettori().getId().getCodice()));
	    mercatid.setPosteggiSettori(posteggiS);
	}
	// Recupero se inserito Lo stradario
	if (mercatid.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatid.getStradario().getId());
	    mercatid.setStradario(stradario);
	}
	fixMergeEntityProperty(mercatid);
	try {
	    mercatidService.update(mercatid);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid, e);
	    model.addAttribute("mercati", mercatid.getMercati());
	    setPageAttributes(model);
	    fixRenderEntityProperty(mercatid);
	    return "mercatid/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercatid.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(@ModelAttribute("mercatid") MercatiD mercatid, BindingResult result, SessionStatus status) {

	MercatiD objToDelete = mercatidService.findById(mercatid.getId());
	try {
	    mercatidService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(mercatid);
	    return "mercatid/form";
	}
	status.setComplete();
	return "redirect:list.htm?codicemercato=" + objToDelete.getMercati().getId().getCodice();
    }

    @RequestMapping
    public String createComunicazione(@ModelAttribute("mercatid") MercatiD mercatid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	ComunicazioniTMercato comunicazioniTMercato = new ComunicazioniTMercato();
	try {
	    List<Integer> listaCodiceUso = null;
	    if (EntityUtils.getNestedProperty(mercatid.getMercatiUsoTransient(), "id.codice") != null) {
		listaCodiceUso = new ArrayList<Integer>();
		listaCodiceUso.add(mercatid.getMercatiUsoTransient().getId().getCodice());
		log.debug("createComunicazione# Impostato singolo giorno per comunicazione. MercatoUso = {}[{}]",
			mercatid.getMercatiUsoTransient().getDescrizione(), mercatid.getMercatiUsoTransient().getId().getCodice());
	    }
	    String[] listacodiciposteggio = request.getParameterValues("codiceposteggi");
	    log.debug("createComunicazione# Creo la struttura della comunicazione in base ai posteggi passati ..... ");
	    comunicazioniTMercato = comunicazioniTMercatoService.insertCreaComunicazione(mercatid.getMercati().getId().getCodice(),
		    listacodiciposteggio, listaCodiceUso);
	} catch (Exception e) {
	    log.debug("createComunicazione# {}", e);
	    FlashMessages.getWarnings().add("Errore:");
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:list.htm?codicemercato=" + mercatid.getMercati().getId().getCodice();
	}
	String uriBackEncoded = "../mercatid/list.htm?codicemercato=" + mercatid.getMercati().getId().getCodice();
	String uriToEncoded = "../comunicazionitmercato/view.htm?codice=" + comunicazioniTMercato.getId().getCodice();
	String historySetUrl = "../history/set.htm?ReturnTo=" + uriBackEncoded + "&" + WebConstants.GOTO + "=" + uriToEncoded;
	//return "redirect:../movimentiallegati/view.htm?codice=" + codiceOggettoMovimento;
	return "redirect:" + historySetUrl;
    }

    /**
     * Il metodo è utilizzato quando vogliamo andare a inserire le informazioni generali a più posteggi
     * contemporaneamente
     * 
     * @param mercatiDattivitaistat
     * @param codicemercato
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String dettaglioPosteggi(@RequestParam("codicemercato") Integer codicemercato, Model model, HttpServletRequest request) {

	// recupera dalla request tutti i codici dei posteggi che sono stati selezioni
	String[] listacodiciposteggio = request.getParameterValues("codiceposteggi");
	// Recupero il mercato corrente
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	// Se Creo un oggetto Posteggio che utilizzo per acquisire le informazioni tramite il form
	// popolo il campo tranziet ListaCodici con tutti i codici posteggio che abbiamo selezionato
	MercatiD mercatiD = null;
	mercatiD = new MercatiD();
	mercatiD.setMercati(mercati);
	mercatiD.setListacodici(listacodiciposteggio);
	fixRenderEntityProperty(mercatiD);
	model.addAttribute("mercatid", mercatiD);
	model.addAttribute("mercati", mercati);
	setPageAttributes(model);
	return "mercatid/formDettaglioPosteggi";
    }

    @RequestMapping
    public String insertDettaglioPosteggi(Model model, @ModelAttribute("mercatid") MercatiD mercatid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// Recupero il tipo spazio se inserito
	if (mercatid.getTipoSpazio() != null && mercatid.getTipoSpazio().getId().getCodice() != null) {
	    Posteggitipospazio posteggitipospazio = posteggitipospazioService.findById(new PkId(mercatid.getTipoSpazio().getId().getCodice()));
	    mercatid.setTipoSpazio(posteggitipospazio);
	}
	// Recupero lo stradario se inserito
	if (mercatid.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatid.getStradario().getId());
	    mercatid.setStradario(stradario);
	}
	fixMergeEntityProperty(mercatid);
	try {
	    // Controllo che ci siano posteggi selezioniati
	    if (mercatid.getListacodici().length > 0) {
		String[] arraycodici = mercatid.getListacodici();
		mercatidService.updateMultiPosteggi(mercatid, arraycodici);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatid, e);
	    model.addAttribute("mercati", mercatid.getMercati());
	    fixRenderEntityProperty(mercatid);
	    return "mercatid/formDettaglioPosteggi";
	}
	status.setComplete();
	//return "redirect:list.htm?codicemercato=" + mercatid.getMercati().getId().getCodice();
	return "redirect:../history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    /**
     * Metodo utilizzato per andare a cancellare più posteggi contemporaneamente
     * 
     * @param model
     * @param mercatid
     * @param result
     * @param status
     * @param request
     * @return
     */
    @RequestMapping
    public String deletePosteggi(Model model, @ModelAttribute("filtro") MercatiD mercatid, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupera dalla request tutti i codici dei posteggi che sono stati selezioni
	String[] listacodiciposteggio = request.getParameterValues("codiceposteggi");
	List<MercatiD> listDeleteObject = null;
	try {
	    listDeleteObject = mercatidService.deleteListaPosteggi(listacodiciposteggio);
	} catch (Exception e) {
	    Map<String, Integer> map = new HashMap<String, Integer>();
	    // recupero un posteggio per estrarre il codice del mercato per ritornare alla pagina della lista dei
	    // posteggi
	    MercatiD posteggi = mercatidService.findById(new PkId(Integer.valueOf(listacodiciposteggio[0])));
	    copyErrorsToBindingResult(result, mercatid, e);
	    map.put("codicemercato", posteggi.getMercati().getId().getCodice());
	    model.addAttribute("commandName", "filtro");
	    model.addAttribute("method", "list.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	// recupero un posteggio qualisia (prendo il primo,sono sicuro che c'è) eda cui estraggo il codice mercato per
	// tornare alla pagina della lista dei posteggi
	MercatiD posteggi = listDeleteObject.get(0);
	status.setComplete();
	return "redirect:list.htm?codicemercato=" + posteggi.getMercati().getId().getCodice();
    }

    @RequestMapping
    public ModelMap listmerceologie(@RequestParam("codiceposteggio") Integer codiceposteggio, HttpServletRequest request,
	    HttpServletResponse response) {

	// Recupere tutte le merceologie presenti in un posteggio
	MercatiD mercatid = mercatidService.findById(new PkId(codiceposteggio));
	Set<MercatiDattivitaistat> merceologieList = mercatid.getMercatiDattivitaistats();
	ModelMap model = new ModelMap(merceologieList);
	boolean export = createJMesaExport(request, response, merceologieList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatidattivitaistatList", merceologieList);
	model.addAttribute("mercatid", mercatid);
	return model;
    }

    @RequestMapping
    public String createMerceologia(@ModelAttribute("mercatid") MercatiD mercatiD, @RequestParam("codiceposteggio") Integer codiceposteggio,
	    Model model, HttpServletRequest request) {

	// Recupero i codici posteggi selezionati nel caso di aggiunt amerceologie a più posteggi
	String codici = (String) request.getParameter("codiceposteggi");
	String[] listacodiciposteggio = null;
	// la stringa codici esiste solo se stiamo inserendo la merceologie per più posteggi contempoarneamente
	if (codici != null && !codici.equals("")) {
	    listacodiciposteggio = codici.substring(0, codici.length() - 1).split(",");
	}
	MercatiDattivitaistat mercatiDattivitaistat = new MercatiDattivitaistat();
	// Se il codice posteggio è diverso null allora sto nella modalità di inserimento della merceologia per un
	// posteggio specifico
	if (codiceposteggio != null) {
	    // Recupero il posteggio
	    MercatiD mercatid = mercatidService.findById(new PkId(codiceposteggio));
	    // Setto alla merceologia il posteggio
	    mercatiDattivitaistat.setPosteggio(mercatid);
	    model.addAttribute("mercatid", mercatid);
	    // setto alla merceologia il mercato
	    mercatiDattivitaistat.setMercato(mercatid.getMercati());
	    // Significa che sto inserendo una merceologia per più posteggi contemporaneamente
	} else {
	    // Setto al posteggio la proprieta transien che contiene tutta la lista dei codici dei posteggi che vogliamo
	    // modificare
	    mercatiD.setListacodici(listacodiciposteggio);
	    // Setto alla merceologia il posteggio
	    mercatiDattivitaistat.setPosteggio(mercatiD);
	    // setto alla merceologia il mercato
	    mercatiDattivitaistat.setMercato(mercatiD.getMercati());
	    model.addAttribute("posteggi", mercatiD);
	    model.addAttribute("codiceposteggio", listacodiciposteggio[0]);
	}
	fixRenderMercatiDattivitaistatProperty(mercatiDattivitaistat);
	model.addAttribute("mercatidattivitaistat", mercatiDattivitaistat);
	setPageAttributes(model);
	return "mercatid/formMerceologia";
    }

    @RequestMapping
    public String insertMerceologia(Model model, @ModelAttribute("mercatidattivitaistat") MercatiDattivitaistat mercatiDattivitaistat,
	    BindingResult result, SessionStatus status) {

	// Recupero la merceologia se inserita
	Attivita attivita = null;
	if (mercatiDattivitaistat.getAttivita().getId() != null) {
	    attivita = attivitaService.findById(mercatiDattivitaistat.getAttivita().getId());
	    mercatiDattivitaistat.setAttivita(attivita);
	}
	try {
	    MercatiDattivitaistatId id = new MercatiDattivitaistatId();
	    id.setIdcomune(ORMHelper.getIdcomune());
	    // Significa che sto inserendo una merceologia a più posteggi
	    if (attivita != null && attivita.getId().getCodiceistat() != null && !attivita.getId().getCodiceistat().equals(""))
		id.setFkcodiceattivitaistat(attivita.getId().getCodiceistat());
	    mercatiDattivitaistat.setId(id);
	    // Significa che sto inserendo una merceologia a più posteggi
	    if (mercatiDattivitaistat.getPosteggio() != null && mercatiDattivitaistat.getPosteggio().getListacodici() != null
		    && mercatiDattivitaistat.getPosteggio().getListacodici().length > 0) {
		id.setFkcodicemercato(mercatiDattivitaistat.getPosteggio().getMercati().getId().getCodice());
		mercatiDattivitaistatService.insertMerceologieAPosteggi(mercatiDattivitaistat);
		// Significa che sto inserendo una merceologia a un posteggio
	    } else {
		id.setFkcodicemercato(mercatiDattivitaistat.getPosteggio().getMercati().getId().getCodice());
		id.setFkidposteggio(mercatiDattivitaistat.getPosteggio().getId().getCodice());
		mercatiDattivitaistatService.insert(mercatiDattivitaistat);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiDattivitaistat, e);
	    model.addAttribute("mercatid", mercatiDattivitaistat.getPosteggio());
	    fixRenderMercatiDattivitaistatProperty(mercatiDattivitaistat);
	    return "mercatid/formMerceologia";
	}
	status.setComplete();
	if (mercatiDattivitaistat.getPosteggio().getId().getCodice() != null) {
	    return "redirect:listmerceologie.htm?codiceposteggio=" + mercatiDattivitaistat.getPosteggio().getId().getCodice() + "&status_msg=01";
	} else {
	    return "redirect:../history/back.htm?" + WebConstants.GOTO + "=/";
	}
    }

    @RequestMapping
    public String deleteMerceologia(@RequestParam("codicemerceologia") String codicemerceologia,
	    @RequestParam("codiceposteggio") Integer codiceposteggio, @RequestParam("codicemercato") Integer codicemercato) {

	MercatiDattivitaistatId id = new MercatiDattivitaistatId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setFkcodiceattivitaistat(codicemerceologia);
	id.setFkcodicemercato(codicemercato);
	id.setFkidposteggio(codiceposteggio);
	MercatiDattivitaistat objToDelete = mercatiDattivitaistatService.findById(id);
	mercatiDattivitaistatService.delete(objToDelete);
	return "redirect:listmerceologie.htm?codiceposteggio=" + objToDelete.getPosteggio().getId().getCodice();
    }

    @RequestMapping
    public String salvaPreferenzaInConfigurazione(@RequestParam("nomeparametro") String nomeparametro,
	    @RequestParam("codicemercato") Integer codicemercato, @RequestParam("valore") String valore, HttpServletRequest request,
	    HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId showConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(), nomeparametro);
	Configurazioneutente parametroConfigurazioneUtente = configurazioneutenteService.findById(showConfId);
	if (parametroConfigurazioneUtente == null) {
	    // .. inserisco i valori di default
	    parametroConfigurazioneUtente = new Configurazioneutente();
	    parametroConfigurazioneUtente.setId(showConfId);
	    parametroConfigurazioneUtente.setResponsabile(responsabile);
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.insert(parametroConfigurazioneUtente);
	} else {
	    parametroConfigurazioneUtente.setValore(valore);
	    configurazioneutenteService.update(parametroConfigurazioneUtente);
	}
	return "redirect:list.htm?codicemercato=" + codicemercato;
    }

    @RequestMapping
    public void ajaxUpdateProprieta(Model model, @ModelAttribute("merdatid") MercatiD mercatid, BindingResult result, SessionStatus status,
	    @RequestParam("valore") String valore, @RequestParam("idPosteggio") Integer idPosteggio, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	try {
	    mercatid = mercatidService.findById(new PkId(idPosteggio));
	    mercatidService.updateCodicePosteggio(mercatid, valore);
	} catch (Exception e) {
	    response.getWriter().write((this.getMessageFromBundle(((BusinessValidationException) e).getInvalidValues().get(0).getMessage(), null)));
	}
	fixRenderEntityProperty(mercatid);
    }

    @RequestMapping
    public String ajaxStoricoSubentri(@RequestParam("codiceconcessione") Integer codiceconcessione, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	if (log.isDebugEnabled())
	    log.debug("call ajaxStoricoSubentri  with codiceconcessione: {}", codiceconcessione);
	List<AutorizzazioniSubentri> listSubentri = autorizzazioniSubentriService.findSubentriByConcessione(codiceconcessione, null, null,
		OrderTypeEnum.ASC);
	model.addAttribute("listSubentri", listSubentri);
	return "mercatid/ajaxListsubentri";
    }

    @RequestMapping
    public String ajaxDettaglioMerceologiePosteggio(@RequestParam("idposteggio") Integer idposteggio, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	List<MercatiDattivitaistat> attivitas = mercatiDattivitaistatService.findAttivitaPosteggio(idposteggio);
	model.addAttribute("attivitas", attivitas);
	return "mercatid/ajaxDettaglioMerceologiePosteggio";
    }

    /**
     * <pre>
     * 	Recupera tutti i posteggi che hanno valorizzato posizione e riodina i valori in modo sequenziale.
     * 	Es. 1,3,5,7 --> 1,2,3,4
     * &#64;param codicemercato
     * &#64;param model
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public String compattaPosizioni(@RequestParam("codicemercato") Integer codicemercato, Model model) {

	// Reupera il mercato corrente
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	mercatidService.updateCompattaPosizioni(codicemercato);
	//	MercatiD mercatid = new MercatiD();
	//	// Setto il mercato nel posteggio che sto creando
	//	mercatid.setMercati(mercati);
	//	fixRenderEntityProperty(mercatid);
	//	model.addAttribute("mercatid", mercatid);
	//	model.addAttribute("mercati", mercati);
	//	setPageAttributes(model);
	String optUrl = "";
	if (!mercati.getMercatiUsos().isEmpty() && mercati.getMercatiUsos().size() > 1) {
	    optUrl = "&codiceuso=" + mercati.getMercatiUsos();
	}
	return "redirect:list.htm?codicemercato=" + mercati.getId().getCodice() + optUrl;
    }

    @RequestMapping
    public String inizializzaExportModalitaPentaho(Model model, @ModelAttribute("filtro") MercatiD mercatiD, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	EsportazioniPentahoMercatiDCommand ePC = new EsportazioniPentahoMercatiDCommand();
	ePC.setAttivitaAmmissibile(mercatiD.getAttivataAmmessaNonAmmessaTransient());
	if (mercatiD.getMercatiUsoTransient() != null && mercatiD.getMercatiUsoTransient().getId() != null) {
	    ePC.setCodiceUso(mercatiD.getMercatiUsoTransient().getId().getCodice());
	}
	ePC.setCodiceMercato(mercatiD.getMercati().getId().getCodice());
	if (mercatiD.getAttivitaTransient() != null && mercatiD.getAttivitaTransient().getId() != null) {
	    ePC.setIdAttivitaIstat(mercatiD.getAttivitaTransient().getId().getCodiceistat());
	}
	if (mercatiD.getStradario() != null && mercatiD.getStradario().getId() != null) {
	    ePC.setCodiceStradario(mercatiD.getStradario().getId().getCodice());
	}
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ePC.setResponsabili(EsportazioniPentahoResponsabili.fromResponsabili(responsabile));
	ePC.setEmailResponsabile(responsabile.getEmail());
	model.addAttribute("esportazioniPentahoMercatiDCommand", ePC);
	return "redirect:../mercatid/createExportModalitaPentaho.htm";
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
    public String createExportModalitaPentaho(Model model,
	    @ModelAttribute("esportazioniPentahoMercatiDCommand") EsportazioniPentahoMercatiDCommand esportazioniPentahoMercatiDCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<Esportazioni> listaEsportazioni = new ArrayList<Esportazioni>();
	if (StringUtils.isBlank(esportazioniPentahoMercatiDCommand.getParametroCodiceEsportazione())) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.POSTEGGI_MERCATO);
	    if (!listaEsportazioni.isEmpty()) {
		esportazioniPentahoMercatiDCommand.setEsportazioni(EsportazioniPentahoEsportazioneModel.fromEsportazione(listaEsportazioni.get(0)));
	    }
	} else {
	    String[] codice = esportazioniPentahoMercatiDCommand.getParametroCodiceEsportazione().split("\\|");
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(codice[1], Integer.parseInt(codice[0]));
	    ids.add(id);
	    boolean sovrascrivi = true;
	    if (esportazioniPentahoMercatiDCommand.getEsportazioni() != null
		    && esportazioniPentahoMercatiDCommand.getEsportazioni().getId() != null) {
		sovrascrivi = !(esportazioniPentahoMercatiDCommand.getEsportazioni().getId().equals(id.getCodice())
			&& esportazioniPentahoMercatiDCommand.getEsportazioni().getIdComune().equals(id.getIdcomune()));
	    }
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.POSTEGGI_MERCATO, ids);
	    Esportazioni esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	    if (sovrascrivi) {
		esportazioniPentahoMercatiDCommand.setEsportazioni(EsportazioniPentahoEsportazioneModel.fromEsportazione(listaEsportazioni.get(0)));
	    }
	}
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	String codiceComune = null;
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiceComune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	return "mercatid/exportMercatidPentaho";
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(Model model,
	    @ModelAttribute("esportazioniPentahoMercatiDCommand") EsportazioniPentahoMercatiDCommand esportazioniPentahoMercatiDCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca i posteggi e li salva sulla tabella TMP_ESPORTAZIONI ");
	MercatiD mercatiD = populateMercatiDFromCommand(esportazioniPentahoMercatiDCommand);
	String sessionId = mercatidService.exportModalitaPentaho(mercatiD, mercatiD.getTransientEsportazioni(),
		StringUtils.defaultIfEmpty(esportazioniPentahoMercatiDCommand.getEmailResponsabile(), ""),
		BooleanUtils.toBoolean(esportazioniPentahoMercatiDCommand.getInvioMail()));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di Pentaho");
	try {
	    String pathFile = pentahoService.callTrasformazione(mercatiD.getTransientEsportazioni().getTrasformazione(),
		    mercatiD.getTransientEsportazioni().getParametriesportaziones(), sessionId, response);
	    if (BooleanUtils.toBoolean(esportazioniPentahoMercatiDCommand.getInvioMail()) == true
		    && StringUtils.isNotBlank(esportazioniPentahoMercatiDCommand.getEmailResponsabile())) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService
			    .populateMailMessageForExport(esportazioniPentahoMercatiDCommand.getEmailResponsabile(), pathFile, mailtipo);
		    // Non è previsto codice comune per i posteggi
		    String codiceComune = null;
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), esportazioniPentahoMercatiDCommand.getIdAccount(), codiceComune,
			    ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho# Mail/Tipo  non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + mercatiD.getTransientEsportazioni().getDescrizione() + ". Err:", e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    private MercatiD populateMercatiDFromCommand(EsportazioniPentahoMercatiDCommand esportazioniPentahoMercatiDCommand) {

	MercatiD ret = new MercatiD();
	//
	Esportazioni e = new Esportazioni();
	e.setId(new PkId(esportazioniPentahoMercatiDCommand.getEsportazioni().getIdComune(),
		esportazioniPentahoMercatiDCommand.getEsportazioni().getId()));
	e.setDescrizione(esportazioniPentahoMercatiDCommand.getEsportazioni().getDescrizione());
	Software s = new Software();
	e.setSoftware(s);
	e.setTrasformazione(esportazioniPentahoMercatiDCommand.getEsportazioni().getTrasformazione());
	List<EsportazioniPentahoParametri> parametri = esportazioniPentahoMercatiDCommand.getEsportazioni().getParametri();
	for (EsportazioniPentahoParametri ep : parametri) {
	    Parametriesportazione p = new Parametriesportazione();
	    p.setDescrizione(ep.getDescrizione());
	    p.setParametro(ep.getParametro());
	    p.setValue(ep.getValore());
	    e.getParametriesportaziones().add(p);
	}
	ret.setTransientEsportazioni(e);
	//
	if (esportazioniPentahoMercatiDCommand.getCodiceUso() != null) {
	    MercatiUso mu = new MercatiUso();
	    mu.setId(new PkId(esportazioniPentahoMercatiDCommand.getCodiceUso()));
	    ret.setMercatiUsoTransient(mu);
	}
	//
	//
	if (esportazioniPentahoMercatiDCommand.getCodiceMercato() != null) {
	    Mercati mu = new Mercati();
	    mu.setId(new PkId(esportazioniPentahoMercatiDCommand.getCodiceMercato()));
	    ret.setMercati(mu);
	}
	//
	if (StringUtils.isNotBlank(esportazioniPentahoMercatiDCommand.getIdAttivitaIstat())) {
	    Attivita mu = new Attivita();
	    mu.setId(new AttivitaId(esportazioniPentahoMercatiDCommand.getIdAttivitaIstat()));
	    ret.setAttivitaTransient(mu);
	    ret.setAttivataAmmessaNonAmmessaTransient(esportazioniPentahoMercatiDCommand.getAttivitaAmmissibile());
	}
	//
	//
	if (esportazioniPentahoMercatiDCommand.getCodiceStradario() != null) {
	    Stradario mu = new Stradario();
	    mu.setId(new PkId(esportazioniPentahoMercatiDCommand.getCodiceStradario()));
	    ret.setStradario(mu);
	}
	//
	return ret;
    }

    @Override
    protected void fixMergeEntityProperty(MercatiD entity) {

	if (entity.getMercati() != null && entity.getMercati().getId() != null && entity.getMercati().getId().getCodice() == null) {
	    entity.setMercati(null);
	}
	if (entity.getTipoSpazio() != null && entity.getTipoSpazio().getId() != null && entity.getTipoSpazio().getId().getCodice() == null) {
	    entity.setTipoSpazio(null);
	}
	if (entity.getStradario() != null && entity.getStradario().getId() != null && entity.getStradario().getId().getCodice() == null) {
	    entity.setStradario(null);
	}
	if (entity.getPosteggiSettori() != null && entity.getPosteggiSettori().getId() != null
		&& entity.getPosteggiSettori().getId().getCodice() == null) {
	    entity.setPosteggiSettori(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatiD entity) {

	if (entity.getTipoSpazio() == null) {
	    entity.setTipoSpazio(new Posteggitipospazio());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
	if (entity.getStradario() == null) {
	    entity.setStradario(new Stradario());
	}
	if (entity.getPosteggiSettori() == null) {
	    entity.setPosteggiSettori(new PosteggiSettori());
	}
	if (entity.getMercatiUsoTransient() == null) {
	    entity.setMercatiUsoTransient(new MercatiUso());
	}
	if (entity.getAttivitaTransient() == null) {
	    entity.setAttivitaTransient(new Attivita());
	}
    }

    protected void fixMergeMercatiDattivitaistatProperty(MercatiDattivitaistat entity) {

	if (entity.getAttivita() != null && entity.getAttivita().getId() != null
		&& (entity.getAttivita().getId().getCodiceistat() == null || entity.getAttivita().getId().getCodiceistat().equals(""))) {
	    entity.setAttivita(null);
	}
	if (entity.getMercato() != null && entity.getMercato().getId() != null && entity.getMercato().getId().getCodice() == null) {
	    entity.setMercato(null);
	}
	if (entity.getPosteggio() != null && entity.getPosteggio().getId() != null && entity.getPosteggio().getId().getCodice() == null) {
	    entity.setPosteggio(null);
	}
    }

    protected void fixRenderMercatiDattivitaistatProperty(MercatiDattivitaistat entity) {

	if (entity.getAttivita() == null) {
	    entity.setAttivita(new Attivita());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
	if (entity.getPosteggio() == null) {
	    entity.setPosteggio(new MercatiD());
	}
    }

    //    protected void fixRenderMercatiComTProperty(MercatiComT entity) {
    //
    //	if (entity.getAmministrazioni() == null) {
    //	    entity.setAmministrazioni(new Amministrazioni());
    //	}
    //	if (entity.getLetteretipo() == null) {
    //	    entity.setLetteretipo(new Letteretipo());
    //	}
    //	if (entity.getMailtipo() == null) {
    //	    entity.setMailtipo(new Mailtipo());
    //	}
    //	if (entity.getMercati() == null) {
    //	    entity.setMercati(new Mercati());
    //	}
    //	if (entity.getTipimovimento() == null) {
    //	    entity.setTipimovimento(new Tipimovimento());
    //	}
    //    }
    @Override
    protected void setPageAttributes(Model model) {

    }
}
