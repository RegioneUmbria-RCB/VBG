package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggitipospazio;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.PosteggitipospazioService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 
 * @author gianpaolot
 */
//DAELIMINARE @Controller
@SessionAttributes(value = { "fitro", "mercatid", "mercatidattivitaistat" })
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
    private static final Logger log = LoggerFactory.getLogger(MercatiDController.class);

    @RequestMapping
    public ModelMap list(@RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam(value = "codiceuso", required = false) Integer codiceuso, @ModelAttribute("filtro") MercatiD mercatiD,
	    HttpServletRequest request, HttpServletResponse response) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	mercatiD.setMercati(mercati);
	// Il metodo restituisce una lista di posteggi filtrati per caratteristiche del posteggio,se si sta accendendo
	// per la prima volta alla pagina della lista dei posteggi, non verra applicato alcun filtro
	List<MercatiD> mercatidList = mercatidService.findByMercatiD(mercatiD);
	ModelMap model = new ModelMap(mercatidList);
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
	// -----FINE GESTIONE CONFIGURAZIONE UTENTE----------------------
	// mette sulla request le informazioni recuperate dalla configurazione e le usa per gestire le regole di
	// visualizzazione sulla jsp
	request.setAttribute(WebConstants.VISUALIZZA_MERCEOLOGIA_MERCATI, showHideMercelogieinfoDefault);
	request.setAttribute(WebConstants.VISUALIZZA_ALTRE_INFORMAZIONI_MERCATI, showAltreInfoHideValoreDefault);
	request.setAttribute(WebConstants.VISUALIZZA_FILTRO_POSTEGGI, showFiltroPosteggiHideValoreDefault);
	// -------------------FINE CONFIGURAZIONE UTENTE
	// Recupero una lista contenete tutte le merceologie presenti nei vari posteggi,utilizzato nella select per il
	// filtraggio dei posteggi in visualizzazione
	// (Non presenta i record doppi)
	List<MercatiDattivitaistat> listaMerceologie = mercatiDattivitaistatService.findAttivitaPosteggi(codicemercato, null);
	// restituisci la lista dei mercati passata, per ogni posteggio tale metodo se esistono popola i campi transient
	// ( Set<AutorizzazioniSubentri> transientSubentri Autorizzazioni transientautConcessioneattiva) della lista
	// vwconcessioniattive contenuta in ogni posteggio
	// FIXME LA FUNZIONALITA' SOTTO DEVE ESSERE RICONTROLLATA
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatid", mercatiD);
	model.addAttribute("mercatidattivitaistat", new MercatiDattivitaistat());
	model.addAttribute("merceologieList", listaMerceologie);
	return model;
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
	return "redirect:list.htm?codicemercato=" + mercatid.getMercati().getId().getCodice();
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
    public ModelMap listmerceologie(@RequestParam("codiceposteggio") Integer codiceposteggio, HttpServletRequest request, HttpServletResponse response) {

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
	return "mercatid/ajaxListsubentri";
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

    @Override
    protected void setPageAttributes(Model model) {

    }
}
