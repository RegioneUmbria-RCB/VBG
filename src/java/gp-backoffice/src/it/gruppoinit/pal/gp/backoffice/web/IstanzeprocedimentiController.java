package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeAllegatiControlloHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocedimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeprocedimentiCommand;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "istanzeprocedimentiCommand", "istanzeprocedimenti" })
public class IstanzeprocedimentiController extends BaseController<Istanzeprocedimenti> {

    @Autowired
    private IstanzeprocedimentiService istanzeprocedimentiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private NaturaendoService naturaendoService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeprocedimentiController.class);
    private static final String AUTORIZZAZIONE = "autorizzazione";
    private static final String ACQUISTO = "acquisto";
    private static final String AMMISSIONE = "ammissione";

    /**
     * Il metodo va ad aggiornare la data di ogni instanzaprocedimento configurata attraverso un achiamata ajax
     * 
     * @param codice
     * @param software
     * @param abilita
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codice,
	    @RequestParam(value = "codiciNaturaendoAggiornati", required = false) String codiciNaturacodiciendoAggiornati,
	    HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codice));
	/////////////////////////////////////////////   GESTIONE DELLE DIPENDENZE IN BASE ALLA  /////////////////////////////////////////
	///////////////////////////////////////////    	       NATURA ENDO                      ///////////////////////////////////////// 
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero le nature endo configurate per il comune in esame
	List<Naturaendo> naturaendos = naturaendoService.findAll(null, null);
	// Recupero la natura endo associata all'istanza tramite la procedura, al primo acesso le dipendenze le
	// calcoleremo a partire dalla natura endo collegata all'istanza.
	//(questo è il caso quando entriamo nella funzionalità a partire dal form dell'istanza)
	Naturaendo naturaendoDellaTipoprocedura = istanze.getProcedura().getNaturaendo();
	//Se codiciNaturacodiciendoAggiornati =null (acesso alla funzionalità dalla pagina delle istanze)
	if (StringUtils.isBlank(codiciNaturacodiciendoAggiornati)) {
	    // calcolo le dipendenze a partire da quella della procedura dell'istanza
	    // naturaendos = setDipendenzenaturaEndo(naturaendos, naturaendoDellaTipoprocedura, request);
	    naturaendos = naturaendoService.getNatureendoByDipendenze(naturaendos, naturaendoDellaTipoprocedura, false);
	}
	//codiciNaturacodiciendoAggiornati !=null 
	//(la funzionalità è stata richiamata cliccando sui check box che indicano le nature endo per cui filtrare gli endo procedimenti)
	else {
	    // Il calcolo delle dipendenze binarie verrà fatto a partire dall'ultima natura endo selezionata (partendo da sinistra)
	    Naturaendo naturaendoTemp = naturaendoService.findById(new NaturaendoId(Integer.parseInt(codiciNaturacodiciendoAggiornati)));
	    // naturaendos = setDipendenzenaturaEndo(naturaendos, naturaendoTemp, request);
	    naturaendos = naturaendoService.getNatureendoByDipendenze(naturaendos, naturaendoTemp, false);
	}
	/////////////////////////////////////////////   DATI DELLA TABELLA ELENCO ENDO ATTIVATI /////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la lista di tutti gli endo procedimenti che sono stati collegati all'istanza (da mettere sulla prima lista).
	List<Istanzeprocedimenti> istanzeprocedimentiList = istanzeprocedimentiService.findByIstanze(istanze);
	// Valori utilizzati per aggiungere il filtro sulla ricerca degli endo procedimenti sulla maschera di ricerca
	// degli endo da attivare. In particolare non verranno presetati dalla ricerca quelli che già sono stati attivati.
	// 
	String codiciInvetarioprocedimenti = "";
	codiciInvetarioprocedimenti = createStringCodiciEndoAttivati(istanzeprocedimentiList);
	ModelMap model = new ModelMap(istanzeprocedimentiList);
	boolean export = createJMesaExport(request, response, istanzeprocedimentiList);
	if (export) {
	    return null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// DATI PER LA TABELLA "INVENTARIO PROCEDIMENTI ATTIVABILI"/////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la lista dei codici delle nature endo selezionate sul form per usaltra come filtro nella ricerca di
	// endo procedimenti senza CATEGORIA ENDO.
	List<String> listaCodiciNaturaAmmissibili = new ArrayList<String>();
	//Delle nature endo prendo solo quelle che hanno  flag "transietFlagBinariodipendenze" uguale true (significa che tra tutte le nature
	//sono quelle compatibili per questa istanza.)
	listaCodiciNaturaAmmissibili = setCodiciNaturaEndo(naturaendos);
	// creao una lista di tipi famiglie endo che sarà utilizzata per mostrare tutte le famiglie endo
	// che hanno un procedimento atttivabile per l'istanza.
	Set<Tipifamiglieendo> listTipifamigliendo = new LinkedHashSet<Tipifamiglieendo>();
	// Ritorna la lista delle CATEGORIE ENDO che non hanno una TIPO FAMIGLIA ENDO configurata e che contengono endoprocedimento
	// attivabili per l'istanza
	List<Tipiendo> listTipiEndoWithoutFamigliaendo = tipiendoService.findTipiEndoWithoutFamigliaendoAndEndoAttivabili(
		listaCodiciNaturaAmmissibili, istanze);
	// Gestione delle CATEGORIE ENDO senza FAMIGLIA ENDO.
	// Creo un un oggetto fittizzio (non esiste nel db) per raggrupparle. 
	Tipifamiglieendo tipifamiglieendoGenerale = new Tipifamiglieendo();
	// Setto il nome della FAMIGLIA ENDO fittizia
	tipifamiglieendoGenerale.setTipo(getMessageFromBundle("label.tipo_famiglia_endo_generale", null));
	// Gestione degli ENDO PROCEDIMENTI senza CATEGORIA ENDO.
	// Controllo se esistono endo procedimenti senza una una CATEGORIA ENDO, se esistono creo una CATEGORIA ENDO 
	// fittizia. 
	// Ritorna true se esiste almeno un endo procediemento attivabile per quell' istanza e compatibile con la lista di nature endo 
	//passata
	boolean isExsist = inventarioprocedimentiService.isExsistWithoutTipiEndo(istanze, listaCodiciNaturaAmmissibili);
	if (isExsist) {
	    // Crea l'oggetto fittizio, metto codice 0 (non esiste nel DB)
	    Tipiendo tipiendoGenerale = new Tipiendo();
	    tipiendoGenerale.setId(new PkId(0));
	    // setto un nome fisso
	    tipiendoGenerale.setTipo(getMessageFromBundle("label.tipo_categoria_endo_generale", null));
	    // lo aggiungo alla lista dei tipi endo senza famiglia
	    listTipiEndoWithoutFamigliaendo.add(0, tipiendoGenerale);
	}
	// Trasformo la lista di categorie endo senza famiglia endo in un set e la associo alla famiglia endo 
	//generale (in questa lista è presente anche la categoria endo fittizia che contiene gli endo procedimenti senza categoria 
	//endo)
	Set<Tipiendo> setTipiEndoWithoutFamigliaendo = new LinkedHashSet<Tipiendo>(listTipiEndoWithoutFamigliaendo);
	tipifamiglieendoGenerale.setTipiendos(setTipiEndoWithoutFamigliaendo);
	// Alla lista delle della famiglia endo, recuperate dal DB, aggiungo in prima posizione quella fittizia
	// la tipo famiglia generale verrà aggiunta solo se contiene delle catgorie endo contenente almeno 
	// un endo procedimento attivabile.
	if (!tipifamiglieendoGenerale.getTipiendos().isEmpty()) {
	    listTipifamigliendo.add(tipifamiglieendoGenerale);
	}
	// Ritorna la lista di tutte le famiglie endo con inventario procedimenti attivabili
	// La lista sarà filtrata per software corrente e TT e per le famiglie che hanno almeno un endo procedimento collegato.
	listTipifamigliendo.addAll(tipifamiglieendoService.findAllBySoftwareAndTTAndEndoAttivabili(istanze, listaCodiciNaturaAmmissibili));
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// SETTO LE VARIE VARIABILI NEL MODEL //////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	IstanzeprocedimentiCommand command = new IstanzeprocedimentiCommand();
	model.addAttribute("istanze", istanze);
	model.addAttribute("istanzeprocedimentiList", istanzeprocedimentiList);
	model.addAttribute("listTipifamigliendo", listTipifamigliendo);
	model.addAttribute("listNatureendo", naturaendos);
	model.addAttribute("istanzeprocedimentiCommand", command);
	model.addAttribute("codiciInvetarioprocedimenti", codiciInvetarioprocedimenti);
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// SETTO LE VARIE VARIABILI IN REQUEST////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Metto in requet la dimenzione della lista delle nature (mi servirà per gestire la chimata ajax sulla jsp, in
	// particolare saprò a priori
	// quanti filtri aggiuntivi dovrò mettere nella ricerca di degli inventario procedimenti attivabili)
	request.setAttribute("sizeListNatureendo", naturaendos.size());
	// Ci dice che il metodo è stato richiamato aggiornando i checkbox dei tipi natura.(Utilizzato per mostrare
	// espanso l'albero degli invent. proc )
	Boolean isAggiornamento = (StringUtils.isNotBlank(codiciNaturacodiciendoAggiornati) ? true : false);
	request.setAttribute("aggiornamento", isAggiornamento);
	// controlla se per il software il esame sono attivate le tipo famiglie endo (controlla se ci sono record nella
	// tabella tipi famiglie endo)
	Boolean isFamiglieendoAttive = tipifamiglieendoService.existsRecords();
	model.addAttribute("isFamiglieendoAttive", isFamiglieendoAttive);
	boolean isModificaIstanza = istanzeService.checkModificaIstanza(istanze);
	model.addAttribute("isModificaIstanza", isModificaIstanza);
	return model;
    }

    private String createStringCodiciEndoAttivati(List<Istanzeprocedimenti> istanzeprocedimentis) {

	StringBuffer risultatoTemp = new StringBuffer("");
	for (Istanzeprocedimenti istanzeprocedimenti : istanzeprocedimentis) {
	    risultatoTemp = risultatoTemp.append(istanzeprocedimenti.getInventarioprocedimenti().getId().getCodice().toString()).append(",");
	}
	String risultato = "";
	if (StringUtils.isNotBlank(risultatoTemp.toString())) {
	    risultato = risultatoTemp.substring(0, risultatoTemp.length() - 1);
	}
	return risultato;
    }

    private List<String> setCodiciNaturaEndo(List<Naturaendo> naturaendos) {

	List<String> list = new ArrayList<String>();
	for (Naturaendo naturaendo : naturaendos) {
	    if (naturaendo.getTransietFlagBinariodipendenze() == true) {
		list.add(naturaendo.getId().getCodice().toString());
	    }
	}
	return list;
    }

    /**
     * IL METODO AGGIORNA LA DATA DEL INVENTARIO PROCEDIMENTO GIà INSERITO
     * 
     * @param codiceinventario
     * @param codiceistanza
     * @param data
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void ajaxChangeData(@RequestParam("codiceinvetario") String codiceinventario, @RequestParam("codiceistanza") String codiceistanza,
	    @RequestParam("data") String data, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Fa una chiamata ajax che fa a modificare dinamicamente la data dell'istanza procedimento già configurata
	IstanzeprocedimentiId id = new IstanzeprocedimentiId(Integer.parseInt(codiceistanza), Integer.parseInt(codiceinventario));
	Istanzeprocedimenti istanzeprocedimenti = istanzeprocedimentiService.findById(id);
	// fixMergeVerticalizzazioneProperty(verticalizzazioni);
	try {
	    Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	    checkAccessoInformazioni(istanza, true);
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    Date date = null;
	    try {
		date = dateFormat.parse(data);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    istanzeprocedimenti.setDataattivazione(date);
	    istanzeprocedimentiService.update(istanzeprocedimenti);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    /**
     * IL METODO AGGIORNA TRAMITE UNA CHIMATA AJAX IL TIPO DI INVENTARIO PROCEDIMENTO
     * 
     * @param codiceinventario
     * @param codiceistanza
     * @param autorizzazione
     * @param acquisizione
     * @param comm_conf
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void ajaxChangetipo(@RequestParam("codiceinvetario") String codiceinventario, @RequestParam("codiceistanza") String codiceistanza,
	    @RequestParam(value = "autorizzazione", required = false) String autorizzazione,
	    @RequestParam(value = "acquisizione", required = false) String acquisizione,
	    @RequestParam(value = "commissioni", required = false) String comm_conf, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// Fa una chiamata ajax che fa a modificare dinamicamente la data dell'istanza procedimento già configurata
	IstanzeprocedimentiId id = new IstanzeprocedimentiId(Integer.parseInt(codiceistanza), Integer.parseInt(codiceinventario));
	Istanzeprocedimenti istanzeprocedimenti = istanzeprocedimentiService.findById(id);
	try {
	    Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	    checkAccessoInformazioni(istanza, true);
	    if (autorizzazione != null) {
		boolean aut = (autorizzazione.equals("1") ? true : false);
		istanzeprocedimenti.setPerprovvedimento(BooleanUtils.toBooleanObject(aut));
		istanzeprocedimenti.setFlagCommissione(false);
	    }
	    if (acquisizione != null) {
		boolean acq = (acquisizione.equals("1") ? true : false);
		istanzeprocedimenti.setAcquisito(BooleanUtils.toBooleanObject(acq));
		istanzeprocedimenti.setFlagCommissione(false);
	    }
	    if (comm_conf != null) {
		boolean conf = (comm_conf.equals("1") ? true : false);
		istanzeprocedimenti.setFlagCommissione(BooleanUtils.toBooleanObject(conf));
		istanzeprocedimenti.setAcquisito(false);
		istanzeprocedimenti.setPerprovvedimento(false);
	    }
	    istanzeprocedimentiService.update(istanzeprocedimenti);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	    boolean isVisibleColumnDettaglio = false;
	    if ((istanzeprocedimenti.getAcquisito() != null && istanzeprocedimenti.getAcquisito().equals(true))
		    || (StringUtils.isNotBlank(istanzeprocedimenti.getNote()) || istanzeprocedimenti.getProtDel() != null || StringUtils
			    .isNotBlank(istanzeprocedimenti.getProtNum()))) {
		isVisibleColumnDettaglio = true;
	    }
	    response.getWriter().append("#" + BooleanUtils.toStringTrueFalse(isVisibleColumnDettaglio));
	    response.getWriter().append("#" + istanzeprocedimenti.getId().getCodiceinventario().toString());
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    /**
     * CARICA A SEGUITOP DI UNA CHIAMATA AJXA I POSSIBILI INVENTARIO PROCEDIMENTO ATTIVABILI
     * 
     * @param codicetipiendo
     * @param codiceistanza
     * @param codiciNature
     * @param model
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String ajaxLoadInventarioprocedimenti(@RequestParam("codicetipiedo") String codicetipiendo,
	    @RequestParam("codiceistanza") String codiceistanza, @RequestParam("codiciNature") String codiciNature,
	    @RequestParam(required = false, value = "desc") String descrizione, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Tipiendo tipiendo = tipiendoService.findById(new PkId(Integer.parseInt(codicetipiendo)));
	Istanze istanza = istanzeService.findById(new PkId(Integer.parseInt(codiceistanza)));
	List<String> listaCodiciNaturaAmmissibili = new ArrayList<String>();
	if (codiciNature != null) {
	    listaCodiciNaturaAmmissibili = Arrays.asList(codiciNature.split(","));
	}
	List<Inventarioprocedimenti> listInventarioprocedimento = null;
	//boolean isListLimitata = false;
	//String alertMessage = "";
	if (!listaCodiciNaturaAmmissibili.isEmpty()) {
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //TODO da deceidere ed implementare una logica che mostri un numero limitato di record quando sono un numero considerevole////
	    // Il problema si è presentato in un installazione dove è presente il cart e gli inventarioprocedimenti sono scaricati dal////
	    // dizionario.													      ////
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    //    Integer numeroRecord = inventarioprocedimentiService.countByTipiendoAndNonAttivatiPerIstanza(tipiendo, istanza,
	    //	    listaCodiciNaturaAmmissibili);
	    //    if (numeroRecord > WebConstants.NUMERO_RECORD_ENDOPROCEDIMENTI_ATTIVABILI_VISIBILI) {
	    //	listInventarioprocedimento = inventarioprocedimentiService.findByTipiendoAndNonAttivatiPerIstanza(tipiendo, istanza,
	    //		listaCodiciNaturaAmmissibili, WebConstants.NUMERO_RECORD_ENDOPROCEDIMENTI_ATTIVABILI_VISIBILI);
	    //	isListLimitata = true;
	    //	alertMessage = getMessageFromBundle("istanzeprocedimenti.label.messaggio_alert_visualizzazioni_endo_da_attivare", new Object[] {
	    //		WebConstants.NUMERO_RECORD_ENDOPROCEDIMENTI_ATTIVABILI_VISIBILI, numeroRecord });
	    //    } else {
	    listInventarioprocedimento = inventarioprocedimentiService.findByTipiendoAndNonAttivatiPerIstanza(tipiendo, null, istanza,
		    listaCodiciNaturaAmmissibili, null, true, null);
	    //    }
	} else {
	    listInventarioprocedimento = new ArrayList<Inventarioprocedimenti>();
	}
	if (log.isDebugEnabled())
	    log.debug("call inventario procedimento  with codicetipiedo: " + codicetipiendo);
	model.addAttribute("listInventarioprocedimento", listInventarioprocedimento);
	//model.addAttribute("islistLimitata", isListLimitata);
	//model.addAttribute("alertMessage", alertMessage);
	return "ajax/listInventarioprocedimenti";
    }

    @RequestMapping
    public String deleteIstanzeprocedimenti(@RequestParam("codiceinvetario") Integer codiceinventario,
	    @RequestParam("codiceistanza") Integer codiceistanza, HttpServletRequest request) {

	Istanzeprocedimenti objToDelete = istanzeprocedimentiService.findById(new IstanzeprocedimentiId(codiceistanza, codiceinventario));
	String descrizioneIstanzeprocedimenti = objToDelete.toString();
	String descrizioneIstanza = objToDelete.getIstanza().toString();
	try {
	    checkAccessoInformazioni(objToDelete.getIstanza(), true);
	    istanzeprocedimentiService.delete(objToDelete);
	} catch (Exception e) {
	    // copyErrorsToBindingResult(istanzeprocedimentiService.getValidationMessages(), result, objToDelete,
	    // e.getMessage());
	    //	    fixRenderEntityProperty(objToDelete);
	    //	    return "istanzeprocedimenti/list";
	    List<String> msgs = new ArrayList<String>();
	    msgs.add("Operazione non avvenuta correttamente: " + e.getMessage());
	    FlashMessages.setWarnings(msgs);
	    return "redirect:list.htm?codiceIstanza=" + codiceistanza + "&_ts=" + System.currentTimeMillis();
	}
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	LoggerCancellazioni.logCancellazioneIstanzeprocedimenti(userlogged.toString(), descrizioneIstanzeprocedimenti, descrizioneIstanza);
	return "redirect:list.htm?codiceIstanza=" + codiceistanza;
    }

    @RequestMapping
    public String updateDettaglio(Model model, @RequestParam(required = false, value = "isCallFromMovimenti") boolean isCallFromMovimenti,
	    @RequestParam(required = false, value = "codiceMovimento") Integer codiceMovimento,
	    @ModelAttribute("istanzeprocedimentiCommand") IstanzeprocedimentiCommand istanzeprocedimenti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	try {
	    checkAccessoInformazioni(istanzeprocedimenti.getEntity().getIstanza(), true);
	    istanzeprocedimentiService.update(istanzeprocedimenti.getEntity());
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, istanzeprocedimenti, e);
	    //	    fixRenderEntityProperty(istanzeprocedimenti.getEntity());
	    //	    model.addAttribute("istanzeprocedimentiCommand", istanzeprocedimenti);
	    List<String> msgs = new ArrayList<String>();
	    msgs.add(e.getMessage());
	    FlashMessages.setWarnings(msgs);
	    if (isCallFromMovimenti) {
		return "redirect:../movimenti/view.htm?codice=" + codiceMovimento + "&status_msg=03";
	    } else {
		return "redirect:../istanzeprocedimenti/list.htm?codiceIstanza=" + istanzeprocedimenti.getEntity().getId().getCodiceistanza()
			+ "&status_msg=03";
	    }
	}
	status.setComplete();
	if (isCallFromMovimenti) {
	    return "redirect:../movimenti/view.htm?codice=" + codiceMovimento + "&status_msg=02";
	} else {
	    return "redirect:../istanzeprocedimenti/list.htm?codiceIstanza=" + istanzeprocedimenti.getEntity().getId().getCodiceistanza()
		    + "&status_msg=02";
	}
    }

    @RequestMapping
    public String insertEndo(@RequestParam("codiceIstanza") Integer codiceistanza, Model model,
	    @ModelAttribute("istanzeprocedimentiCommand") IstanzeprocedimentiCommand istanzeprocedimenti, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	List<Istanzeprocedimenti> listIstanzeprocedimentiTot = new ArrayList<Istanzeprocedimenti>();
	if (StringUtils.isNotBlank(istanzeprocedimenti.getInsertAcquisto())) {
	    List<Istanzeprocedimenti> listIstanzeprocedimentiAcquisto = getIstanzeprocedimenti(istanzeprocedimenti.getInsertAcquisto(),
		    codiceistanza, request, ACQUISTO);
	    listIstanzeprocedimentiTot.addAll(listIstanzeprocedimentiAcquisto);
	}
	if (StringUtils.isNotBlank(istanzeprocedimenti.getInsertAutorizzazione())) {
	    List<Istanzeprocedimenti> listIstanzeprocedimentiAutorizzazioni = getIstanzeprocedimenti(istanzeprocedimenti.getInsertAutorizzazione(),
		    codiceistanza, request, AUTORIZZAZIONE);
	    listIstanzeprocedimentiTot.addAll(listIstanzeprocedimentiAutorizzazioni);
	}
	if (StringUtils.isNotBlank(istanzeprocedimenti.getInsertAmmissione())) {
	    List<Istanzeprocedimenti> listIstanzeprocedimentiAmmissione = getIstanzeprocedimenti(istanzeprocedimenti.getInsertAmmissione(),
		    codiceistanza, request, AMMISSIONE);
	    listIstanzeprocedimentiTot.addAll(listIstanzeprocedimentiAmmissione);
	}
	try {
	    Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	    checkAccessoInformazioni(istanza, true);
	    istanzeprocedimentiService.insertListIstanzeprocedimenti(listIstanzeprocedimentiTot);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, istanzeprocedimenti, e);
	    //	    fixRenderEntityProperty(istanzeprocedimenti.getEntity());
	    //	    model.addAttribute("istanzeprocedimenti", istanzeprocedimenti);
	    List<String> msgs = new ArrayList<String>();
	    msgs.add("Operazione non avvenuta correttamente: " + e.getMessage());
	    FlashMessages.setWarnings(msgs);
	    return "redirect:list.htm?codiceIstanza=" + codiceistanza + "&_ts=" + System.currentTimeMillis();
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + codiceistanza + "&status_msg=02";
    }

    private List<Istanzeprocedimenti> getIstanzeprocedimenti(String stringaDiCoidici, Integer codiceistanza, HttpServletRequest request,
	    String tipoIstanzaprocedimento) {

	Istanze istanza = istanzeService.findById(new PkId(codiceistanza));
	String[] listacodici = stringaDiCoidici.split(",");
	List<Istanzeprocedimenti> risultato = new ArrayList<Istanzeprocedimenti>();
	for (int i = 0; i < listacodici.length; i++) {
	    String codice = listacodici[i];
	    Istanzeprocedimenti istanzeprocedimenti = new Istanzeprocedimenti();
	    IstanzeprocedimentiId id = new IstanzeprocedimentiId(codiceistanza, Integer.parseInt(codice));
	    istanzeprocedimenti.setId(id);
	    istanzeprocedimenti.setIstanza(istanza);
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(Integer.parseInt(codice)));
	    istanzeprocedimenti.setPerprovvedimento(true);
	    istanzeprocedimenti.setInventarioprocedimenti(inventarioprocedimenti);
	    String dataString = request.getParameter("dataattivazioneCommnad" + codice);
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    Date date = null;
	    try {
		date = dateFormat.parse(dataString);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    istanzeprocedimenti.setDataattivazione(date);
	    Boolean flagAut = (tipoIstanzaprocedimento.equals(AUTORIZZAZIONE) ? true : false);
	    Boolean flagAmmi = (tipoIstanzaprocedimento.equals(AMMISSIONE) ? true : false);
	    Boolean flagAcqu = (tipoIstanzaprocedimento.equals(ACQUISTO) ? true : false);
	    istanzeprocedimenti.setAcquisito(flagAcqu);
	    istanzeprocedimenti.setPerprovvedimento(flagAut);
	    istanzeprocedimenti.setFlagCommissione(flagAmmi);
	    risultato.add(istanzeprocedimenti);
	}
	return risultato;
    }

    /**
     * Il metodo setta il flag compatibili uguale a true alle nature contenute nella lista in base alla natura endo
     * passata
     * 
     * @param list
     *            : lista di tutte le nature configurate
     * @param naturaendo
     *            : natura endo a cui devono essere compatibili le altre.
     * @return
     */
    private List<Naturaendo> setDipendenzenaturaEndo(List<Naturaendo> list, Naturaendo naturaendo, HttpServletRequest request) {

	// lista temporanea passata al metodo per calcolare le dipendenze
	List<Naturaendo> listTemp = new ArrayList<Naturaendo>(list);
	// Il metodo ritorna la lista delle nature endo compatibili a quella passata (secondo la regola "binario dipendenze").
	List<Naturaendo> listaNaturecompatibili = naturaendoService.getNatureendoByDipendenze(listTemp, naturaendo, false);
	//	//	// Scorro la lista di tutte le nature endo presenti  e verifico quali tra queste sono 
	//	//	// sono compatibili tra loro (confronto con la lista trovatata precedentemente "listaNaturecompatibili")
	//	//	// Metto il campo transiet "transietFlagBinariodipendenze" uguale a true per quelle che risultano compatibili 
	//	//	// dal confronto.
	//	//	// Quelle con il flag uguale a true , sul form compariranno con il checkbox selezionato e saranno utilizzate 
	//	//	// come filtri sulla query che ritorna gli endo attivabili.
	//	for (Naturaendo naturaendoTotale : list) {
	//	    // Controllo se è la natura che ho passato (è sicuramnete compatibile con se stessa)
	//	    if (naturaendoTotale.getId().getCodice() == naturaendo.getId().getCodice()) {
	//		naturaendoTotale.setTransietFlagBinariodipendenze(true);
	//	    } else {
	//		for (Naturaendo compatibili : listaNaturecompatibili) {
	//		    if (naturaendoTotale.getId().getCodice().equals(compatibili.getId().getCodice())) {
	//			naturaendoTotale.setTransietFlagBinariodipendenze(true);
	//			break;
	//		    }
	//		}
	//	    }
	//	}
	//	return list;
	return listaNaturecompatibili;
    }

    /**
     */
    @RequestMapping
    public ModelMap riepilogo(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
	List<IstanzeprocedimentiHelper> istanzeprocedimentiList = istanzeprocedimentiService.findRiepilogoEndo(istanze);
	boolean export = createJMesaExport(request, response, istanzeprocedimentiList);
	if (export) {
	    return null;
	}
	ModelMap model = new ModelMap(istanzeprocedimentiList);
	model.addAttribute("istanze", istanze);
	model.addAttribute("istanzeprocedimentiList", istanzeprocedimentiList);
	documentazioneAllegatiView(istanze, model);
	return model;
    }

    private void documentazioneAllegatiView(Istanze istanza, ModelMap model) {

	IstanzeAllegatiControlloHelper helper = istanzeallegatiService.controlloDocumentazione(istanza);
	model.addAttribute("allegatiRichiesti", helper.getAllegatiRichiesti());
	model.addAttribute("allegatiPresentati", helper.getAllegatiPresentati());
	model.addAttribute("allegatiNonValidi", helper.getAllegatiNonValidi());
	model.addAttribute("allegatiValidi", helper.getAllegatiValidi());
    }

    @Override
    protected void fixMergeEntityProperty(Istanzeprocedimenti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Istanzeprocedimenti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
