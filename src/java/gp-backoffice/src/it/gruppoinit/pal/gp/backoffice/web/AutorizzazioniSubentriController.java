package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.exceptions.OperazioniSubentriException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.NumerazioneEnum;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.DehorsMqIstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;

@SessionAttributes(value = { "autorizzazioniSubentriCommand", "autorizzazioniSubentriFilter", "listAutDaSubentrare" })
@Controller
public class AutorizzazioniSubentriController extends BaseController<AutorizzazioniSubentri> {

    private static final String PAGE_AUTORIZZAZIONISUBENTRI_LIST_SUBENTRI = "autorizzazionisubentri/listSubentri";
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private DehorsMqIstanzeService dehorsMqIstanzeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private TipologiaregistriService tipologiaregistriService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    private static final Logger log = LoggerFactory.getLogger(AutorizzazioniSubentriController.class);

    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "resetAttrs", required = false) String resetAttrs,
	    @RequestParam("codiceIstanza") Integer codIstanzaSubentro, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanzaDiSubentro = istanzeService.findById(new PkId(codIstanzaSubentro));
	if (istanzaDiSubentro != null) {
	    checkAccessoInformazioni(istanzaDiSubentro, true);
	}
	AutorizzazioniSubentriCommand autorizzazioniSubentriCommand = new AutorizzazioniSubentriCommand();
	// autorizzazioniSubentriCommand.setDataCessazione(Calendar.getInstance().getTime());
	autorizzazioniSubentriCommand.setIstanzaDiSubentro(istanzaDiSubentro);
	AutorizzazioniFilter autorizzazioniSubentriFilter = new AutorizzazioniFilter();
	if (StringUtils.isBlank(resetAttrs)) {
	    AutorizzazioniFilter _autorizzazioniSubentriFilter = (AutorizzazioniFilter) request.getSession(false)
		    .getAttribute("autorizzazioniSubentriFilter");
	    if (_autorizzazioniSubentriFilter != null) {
		autorizzazioniSubentriFilter = _autorizzazioniSubentriFilter;
	    }
	} else {
	    request.getSession(false).removeAttribute("listAutDaSubentrare");
	}
	autorizzazioniSubentriFilter.setCodiceIstanzaDaEscludere(codIstanzaSubentro);
	// controllo se per il software e idcomune settati è abilitata la gestione delle manifestazioni
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId(ORMHelper.getSoftware()));
	boolean isGestioneMercatiAttiva = false;
	if (mercatiConfigurazione != null) {
	    isGestioneMercatiAttiva = true;
	}
	model.addAttribute("isGestioneMercatiAttiva", isGestioneMercatiAttiva);
	autorizzazioniSubentriCommand.setFilter(autorizzazioniSubentriFilter);
	model.addAttribute("autorizzazioniSubentriFilter", autorizzazioniSubentriCommand.getFilter());
	model.addAttribute("autorizzazioniSubentriCommand", autorizzazioniSubentriCommand);
	setPageAttributesAutorizzazioniSubentri(model);
	return "autorizzazionisubentri/search";
    }

    @RequestMapping
    public String list(Model model, @ModelAttribute("autorizzazioniSubentriCommand") AutorizzazioniSubentriCommand autorizzazioniSubentriCommand,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	fixFilter(autorizzazioniSubentriCommand.getFilter());
	List<Autorizzazioni> list = autorizzazioniService.findByFilter(autorizzazioniSubentriCommand.getFilter(), 0,
		autorizzazioniSubentriCommand.getFilter().getMaxRows());
	Set<AutorizzazioniHelper> listH = new LinkedHashSet<AutorizzazioniHelper>();
	for (Autorizzazioni aut : list) {
	    AutorizzazioniHelper autH = new AutorizzazioniHelper();
	    if (aut.getAutorizzazioniConcessionisForFkAutconcAutcoll() != null && !aut.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
		AutorizzazioniConcessioni concessione = autorizzazioniService.findConcessione(aut);
		autH.setConcessione(concessione);
		AutorizzazioniHelper autCollegataH = new AutorizzazioniHelper();
		autCollegataH.setAutorizzazione(concessione.getAutorizzazioniByFkAutconcAutcoll());
		autH.setAutorizzazioneCollegataHelper(autCollegataH);
	    }
	    autH.setAutorizzazione(aut);
	    autH.setDaSubentrare(false);
	    listH.add(autH);
	}
	if (StringUtils.isNotBlank(request.getParameter("return_to"))) {
	    model.addAttribute("return_to", request.getParameter("return_to"));
	}
	autorizzazioniSubentriCommand.setListAutDaRicerca(listH);
	return "autorizzazionisubentri/list";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String addToList(Model model, @ModelAttribute("autorizzazioniSubentriCommand") AutorizzazioniSubentriCommand autorizzazioniSubentriCommand,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	fixFilter(autorizzazioniSubentriCommand.getFilter());
	Set<AutorizzazioniHelper> listToAdd = autorizzazioniSubentriCommand.getListAutDaRicerca();
	Set<AutorizzazioniHelper> listAutDaSubentrare = (Set<AutorizzazioniHelper>) request.getSession(false).getAttribute("listAutDaSubentrare");
	if (listAutDaSubentrare == null) {
	    listAutDaSubentrare = new LinkedHashSet<AutorizzazioniHelper>();
	}
	for (AutorizzazioniHelper autH : listToAdd) {
	    if (autH.isDaSubentrare()) {
		boolean presente = false;
		for (AutorizzazioniHelper autS : listAutDaSubentrare) {
		    if (autH.getAutorizzazione().getId().getCodice().intValue() == autS.getAutorizzazione().getId().getCodice().intValue()) {
			presente = true;
			break;
		    }
		}
		if (!presente) {
		    listAutDaSubentrare.add(autH);
		}
	    }
	}
	for (AutorizzazioniHelper _autH : listAutDaSubentrare) {
	    checkRegistro(_autH);
	}
	autorizzazioniSubentriCommand.setListAutDaSubentrare(listAutDaSubentrare);
	model.addAttribute("listAutDaSubentrare", listAutDaSubentrare);
	return PAGE_AUTORIZZAZIONISUBENTRI_LIST_SUBENTRI;
    }

    /**
     * metodo che verifica se il registro impone la numerazione. in caso affermativo valorizza a true la variabile
     * registroAutomatico
     * 
     * @param autH
     */
    private void checkRegistro(AutorizzazioniHelper autH) {

	// verifico se il registro impone il progressivo per l'aut/conc
	autH.setRegistroAutomatico(false);
	Integer codiceRegistro = autH.getAutorizzazione().getTipologiaregistro().getId().getCodice();
	Tipologiaregistri registro = this.tipologiaregistriService.findById(new PkId(codiceRegistro));
	NumerazioneEnum tipologiaregistriConfigurazioneEnum = NumerazioneEnum.daRegistro(registro);
	if (!NumerazioneEnum.VUOTO.equals(tipologiaregistriConfigurazioneEnum)) {
	    autH.setRegistroAutomatico(true);
	}
	// verifico se il registro impone il progressivo per l'aut collegata
	if (autH.getAutorizzazioneCollegataHelper() != null && autH.getAutorizzazioneCollegataHelper().getAutorizzazione() != null) {
	    autH.getAutorizzazioneCollegataHelper().setRegistroAutomatico(false);
	    Integer codiceRegistroAutColl = autH.getAutorizzazioneCollegataHelper().getAutorizzazione().getTipologiaregistro().getId().getCodice();
	    registro = this.tipologiaregistriService.findById(new PkId(codiceRegistroAutColl));
	    NumerazioneEnum tipologiaregistriConfigurazioneEnumAutColl = NumerazioneEnum.daRegistro(registro);
	    if (!NumerazioneEnum.VUOTO.equals(tipologiaregistriConfigurazioneEnumAutColl)) {
		autH.getAutorizzazioneCollegataHelper().setRegistroAutomatico(true);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String removeFromList(Model model,
	    @ModelAttribute("autorizzazioniSubentriCommand") AutorizzazioniSubentriCommand autorizzazioniSubentriCommand, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	fixFilter(autorizzazioniSubentriCommand.getFilter());
	Set<AutorizzazioniHelper> listToRemove = new HashSet<AutorizzazioniHelper>();
	Set<AutorizzazioniHelper> listFromJsp = autorizzazioniSubentriCommand.getListAutDaSubentrare();
	Set<AutorizzazioniHelper> listAutDaSubentrare = (Set<AutorizzazioniHelper>) request.getSession(false).getAttribute("listAutDaSubentrare");
	if (listAutDaSubentrare == null) {
	    listAutDaSubentrare = new LinkedHashSet<AutorizzazioniHelper>();
	}
	for (AutorizzazioniHelper autH : listFromJsp) {
	    if (!autH.isDaSubentrare()) {
		for (AutorizzazioniHelper autS : listAutDaSubentrare) {
		    if (autH.getAutorizzazione().getId().getCodice().intValue() == autS.getAutorizzazione().getId().getCodice().intValue()) {
			listToRemove.add(autS);
			break;
		    }
		}
	    }
	}
	listAutDaSubentrare.removeAll(listToRemove);
	autorizzazioniSubentriCommand.setListAutDaSubentrare(listAutDaSubentrare);
	model.addAttribute("listAutDaSubentrare", listAutDaSubentrare);
	return PAGE_AUTORIZZAZIONISUBENTRI_LIST_SUBENTRI;
    }

    @RequestMapping
    public String validateInsertSubentri(Model model,
	    @ModelAttribute("autorizzazioniSubentriCommand") AutorizzazioniSubentriCommand autorizzazioniSubentriCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	try {
	    EsitoElaborazioneSubentri esito = autorizzazioniService.validaInserimentoSubentri(autorizzazioniSubentriCommand);
	    if (!esito.isErroreOWarning()) {
		return insertSubentri(false, model, autorizzazioniSubentriCommand, result, status, request, response);
	    }
	    autorizzazioniSubentriCommand.setEsito(esito);
	    setPageAttributes(model);
	    return "autorizzazionisubentri/subentriValidati";
	} catch (OperazioniSubentriException ope) {
	    // LEGGE GLI ERRORI E LI MOSTRA A VIDEO
	    result.reject("04", new Object[] { ope.getMessage() }, "");
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, autorizzazioniSubentriCommand, true, e);
	    log.error(e.getMessage());
	}
	setPageAttributes(model);
	return PAGE_AUTORIZZAZIONISUBENTRI_LIST_SUBENTRI;
    }

    @RequestMapping
    public String insertSubentri(@RequestParam(value = "forzaInserimento", required = false) Boolean forzaInserimento, Model model,
	    @ModelAttribute("autorizzazioniSubentriCommand") AutorizzazioniSubentriCommand autorizzazioniSubentriCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	try {
	    // Il metodo controlla se prima dell'inserimento di un subentro sono necessarie altre operazioni 
	    // o se si deve essere rendirizzati su un altra pagina.
	    String page = preViewInsertSubentri(model, autorizzazioniSubentriCommand);
	    if (StringUtils.isNotBlank(page) && Boolean.FALSE.equals(forzaInserimento)) {
		return page;
	    }
	    Set<Integer> codiciCreati = autorizzazioniService.insertSubentri(autorizzazioniSubentriCommand);
	    String codiciAutSub = StringUtils.join(codiciCreati.iterator(), ",");
	    status.setComplete();
	    return "redirect:resultInsertSubentri.htm?codiceIstanza=" + autorizzazioniSubentriCommand.getIstanzaDiSubentro().getId().getCodice() +
		   "&codiciAutSub=" + codiciAutSub;
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, autorizzazioniSubentriCommand, true, e);
	    setPageAttributes(model);
	    log.error(e.getMessage());
	    return PAGE_AUTORIZZAZIONISUBENTRI_LIST_SUBENTRI;
	}
    }

    private String preViewInsertSubentri(Model model, AutorizzazioniSubentriCommand autorizzazioniSubentriCommand) {

	String page = "";
	log.debug("preViewInsertSupentri# Verifico il tipo di registro impostato");
	//controllo se  il registro potrebbe essere quello dell'autorizzazione precedente o un nuovo imposta dall'utente
	Integer codiceRegistro = null;
	if (!autorizzazioniSubentriCommand.getListAutDaSubentrare().isEmpty() && EntityUtils.getNestedProperty(
		autorizzazioniSubentriCommand.getListAutDaSubentrare().iterator().next().getAutorizregistroSubentro(), "id.codice") != null) {
	    codiceRegistro = autorizzazioniSubentriCommand.getListAutDaSubentrare().iterator().next().getAutorizregistroSubentro().getId()
		    .getCodice();
	} else {
	    if (!autorizzazioniSubentriCommand.getListAutDaSubentrare().isEmpty()
		    && EntityUtils.getNestedProperty(autorizzazioniSubentriCommand.getListAutDaSubentrare().iterator().next().getAutorizzazione(),
			    "id.codice") != null) {
		codiceRegistro = autorizzazioniSubentriCommand.getListAutDaSubentrare().iterator().next().getAutorizzazione().getTipologiaregistro()
			.getId().getCodice();
	    }
	}
	log.debug("preViewInsertSupentri# Recupero la tipologia di registro con codice {}", codiceRegistro);
	String tipoAut = autorizzazioniService.tipoAutorizzazione(codiceRegistro);
	// AUTORIZZAZIONE TIPO DEHORS
	if (tipoAut.equals(WebConstants.AUTORIZZAZIONE_DEHORS)) {
	    Map<String, Object> map = dehorsMqIstanzeService.preViewInsertSubentriDehors(autorizzazioniSubentriCommand);
	    model.addAttribute("codiceAut", map.get("codiceAut"));
	    model.addAttribute("autorizzazioniSubentriCommand", map.get("autorizzazioniSubentriCommand"));
	    page = (String) map.get("page");
	}
	return page;
    }

    @RequestMapping
    public String resultInsertSubentri(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiciAutSub") String codiciAutSub) throws Exception {

	String[] _codiciAutSub = StringUtils.split(codiciAutSub, ",");
	LinkedHashSet<AutorizzazioniHelper> listAutSub = new LinkedHashSet<AutorizzazioniHelper>();
	for (String codAut : _codiciAutSub) {
	    Autorizzazioni aut = autorizzazioniService.findById(new PkId(new Integer(codAut)));
	    AutorizzazioniHelper autH = new AutorizzazioniHelper();
	    if (aut.getAutorizzazioniConcessionisForFkAutconcAutcoll() != null && !aut.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()) {
		AutorizzazioniConcessioni concessione = autorizzazioniService.findConcessione(aut);
		autH.setConcessione(concessione);
		AutorizzazioniHelper autCollegataH = new AutorizzazioniHelper();
		autCollegataH.setAutorizzazione(concessione.getAutorizzazioniByFkAutconcAutcoll());
		autH.setAutorizzazioneCollegataHelper(autCollegataH);
	    }
	    autH.setAutorizzazione(aut);
	    autH.setDaSubentrare(false);
	    listAutSub.add(autH);
	}
	model.addAttribute("codiceIstanza", codiceIstanza);
	model.addAttribute("listAutSub", listAutSub);
	return "autorizzazionisubentri/result";
    }

    protected void fixFilter(AutorizzazioniFilter autorizzazioniSubentriFilter) {

	if (autorizzazioniSubentriFilter.getAnagrafe() != null && autorizzazioniSubentriFilter.getAnagrafe().getId() != null
		&& autorizzazioniSubentriFilter.getAnagrafe().getId().getCodice() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(autorizzazioniSubentriFilter.getAnagrafe().getId());
	    if (anagrafe != null) {
		autorizzazioniSubentriFilter.setAnagrafe(anagrafe);
	    }
	} else {
	    autorizzazioniSubentriFilter.setAnagrafe(new Anagrafe());
	}
	if (EntityUtils.getNestedProperty(autorizzazioniSubentriFilter.getMercati(), "id.codice") != null) {
	    Mercati mercati = mercatiService.findById(autorizzazioniSubentriFilter.getMercati().getId());
	    autorizzazioniSubentriFilter.setMercati(mercati);
	} else {
	    autorizzazioniSubentriFilter.setMercati(new Mercati());
	}
	if (EntityUtils.getNestedProperty(autorizzazioniSubentriFilter.getMercatiUso(), "id.codice") != null) {
	    MercatiUso mercatiUso = mercatiUsoService.findById(autorizzazioniSubentriFilter.getMercatiUso().getId());
	    autorizzazioniSubentriFilter.setMercatiUso(mercatiUso);
	} else {
	    autorizzazioniSubentriFilter.setMercatiUso(new MercatiUso());
	}
	if (EntityUtils.getNestedProperty(autorizzazioniSubentriFilter.getMercatiD(), "id.codice") != null) {
	    MercatiD mercatiD = mercatiDService.findById(autorizzazioniSubentriFilter.getMercatiD().getId());
	    autorizzazioniSubentriFilter.setMercatiD(mercatiD);
	} else {
	    autorizzazioniSubentriFilter.setMercatiD(new MercatiD());
	}
    }

    private void setPageAttributesAutorizzazioniSubentri(Model model) {

	log.debug(
		"setPageAttributesAutorizzazioniSubentri# Controllo data scadenza autorizzazione [chiudo quelle con data scadenza minore della data odierna]");
	autorizzazioniService.updateCessaAutorizzazioniDehorsScadute();
    }

    @Override
    protected void fixMergeEntityProperty(AutorizzazioniSubentri entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(AutorizzazioniSubentri entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public void isDataFineAffitto(@RequestParam("codiceConcCausale") Integer codiceConcCausale, HttpServletResponse response) throws IOException {

	boolean isAffitto = concessionicausaliService.isAffitto(codiceConcCausale);
	response.setContentType("text/plain");
	//response.getWriter().write(String.valueOf(isAffitto));
	response.getOutputStream().write(String.valueOf(isAffitto).getBytes());
    }
}
