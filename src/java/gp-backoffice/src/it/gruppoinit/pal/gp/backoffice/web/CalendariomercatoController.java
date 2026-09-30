package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

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
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.CalendariomercatoParametriService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes(value = { "mercatipresenzeT", "calendariomercatoParametri" })
public class CalendariomercatoController extends BaseController<CalendariomercatoParametri> {

    private static final Logger log = LoggerFactory.getLogger(CalendariomercatoController.class);
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private CalendariomercatoParametriService calendariomercatoParametriService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private GiornisectimanaService giornisectimanaService;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private MercatiResponsabiliService mercatiResponsabiliService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @RequestMapping
    public String delete(@RequestParam("mercati.id.codice") Integer codicemercato, @RequestParam("mercatiuso.id.codice") Integer codicemercatouso,
	    @RequestParam("anno") Integer anno, Model model,
	    @ModelAttribute("calendariomercatoParametri") CalendariomercatoParametri calendariomercatoParametri, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// §§§BEGIN§§§
	Mercati mercato = mercatiService.findById(new PkId(codicemercato));
	MercatiUso mercatoUso = mercatiUsoService.findById(new PkId(codicemercatouso));
	try {
	    mercatipresenzeTService.deleteCalendario(mercato, mercatoUso, anno);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, calendariomercatoParametri, e);
	    log.error(e.getMessage());
	    // result.reject("", "Il calendario non può essere eliminato.");
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codicemercato.toString());
	    map.put("mercatouso.id.codice", codicemercatouso.toString());
	    map.put("anno", anno.toString());
	    model.addAttribute("commandName", "calendariomercatoParametri");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	status.setComplete();
	return "redirect:listusianni.htm?mercati.id.codice=" + codicemercato;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insert(@RequestParam("mercati.id.codice") Integer codiceMercato,
	    @ModelAttribute("calendariomercatoParametri") CalendariomercatoParametri calendariomercatoParametri, BindingResult result,
	    SessionStatus status, HttpServletRequest request, Model model) {

	// §§§BEGIN§§§
	// recupero il mercato
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	// VALIDAZIONE (INIZIO)
	// esegue una doppia validazione :
	// 1- Controlla che siano presenti tutti i campi del form
	// 2- Evita di inserire le presenze per uno stesso mercato,mercatouso e
	// anno
	// In caso di mancata validazione ristabilisce lo stato iniziale
	if (!validate(calendariomercatoParametri, result, mercato)) {
	    fixRenderEntityProperty(calendariomercatoParametri);
	    // indica che sono al passo 0 creo il form per l'inserimento
	    // (sono nella fase create)
	    calendariomercatoParametri.setStep(0);
	    calendariomercatoParametri.setAnno(Calendar.getInstance().get(Calendar.YEAR));
	    // serve per capire il tipo di mercato
	    Set<MercatiUso> mercatiusoList = mercato.getMercatiUsos();
	    model.addAttribute("mercati", mercato);
	    model.addAttribute("calendariomercatoParametri", calendariomercatoParametri);
	    model.addAttribute("mercatiusoList", mercatiusoList);
	    return "calendariomercato/form";
	}
	boolean verificaConti = mercatiService.verificaConfigurazioneContiMercato(mercato, calendariomercatoParametri.getAnno());
	if (!verificaConti) {
	    // .. prima di effettuare la creazione delle giornate
	    // .. vengono verificate le impostazioni contabili
	    // .. se il controllo è false allora l'utente viene redirezionato alla pagina di adeguamento dei conti.
	    // .. l'utente potrebbe comunque farlo in maniera manuale dalla funzionalità conti all'interno del mercato
	    String goTo = "../mercaticonti/configuraContiView.htm?mercati.id.codice=" +
		    codiceMercato +
		    "&anno=" +
		    calendariomercatoParametri.getAnno();
	    List<String> listGiorniSelezionati = new ArrayList<String>();
	    for (Giornisettimana giorno : calendariomercatoParametri.getGiorniSettimana()) {
		if (giorno.isTransientSelected()) {
		    listGiorniSelezionati.add(String.valueOf(giorno.getId().intValue()));
		}
	    }
	    StringBuffer listaGiornateBuffer = new StringBuffer("");
	    for (String codiceGiorno : listGiorniSelezionati) {
		listaGiornateBuffer.append(",").append(codiceGiorno);
	    }
	    String listaGiornate = listaGiornateBuffer.toString();
	    if (listaGiornate.indexOf(",") > -1) {
		listaGiornate = listaGiornate.substring(1);
	    }
	    String returnTo = "../calendariomercato/create.htm?codice=" +
		    codiceMercato +
		    "&anno=" +
		    calendariomercatoParametri.getAnno().intValue() +
		    "&giorno=" +
		    calendariomercatoParametri.getMercatiUso().getId().getCodice().intValue() +
		    "&giorniSettimanaSelected=" +
		    listaGiornate;
	    try {
		goTo = URLEncoder.encode(goTo, "UTF-8");
		returnTo = URLEncoder.encode(returnTo, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    return "redirect:../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + returnTo;
	}
	// VALIDAZIONE (FINE)
	// recupero il tipo di uso per quel mercato
	PkId muId = new PkId();
	muId.setCodice(calendariomercatoParametri.getMercatiUso().getId().getCodice());
	MercatiUso mercatiUso = mercatiUsoService.findById(muId);
	calendariomercatoParametri.setMercatiUso(mercatiUso);
	// Calcolo tutti i giorni possibili di quel mercato in un anno
	calendariomercatoParametri
		.setGiorniMercato(calendariomercatoParametriService.findGiorniMercatoInUnAnno(calendariomercatoParametri.getAnno(),
			calendariomercatoParametri.getMercatiUso(), calendariomercatoParametri.getGiorniSettimana()).getGiorniMercato());
	// Creo la lista di tutti i giorni festivi ( fissi e non )
	calendariomercatoParametri.setGiorniFestivi(calendariomercatoParametriService.findGiornifestivi(calendariomercatoParametri.getAnno()));
	try {
	    mercatipresenzeTService.inserisciCalendario(calendariomercatoParametri, mercato, (Responsabili) getCurrentlyAuthenticatedUserDetails(), false);
	} catch (Exception e) {
	    Mercati mercati = mercatiService.findById(mercato.getId());
	    copyErrorsToBindingResult(result, calendariomercatoParametri, e);
	    // log.error(e.getMessage());
	    // result.reject(e.getMessage());
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", mercati.getId().getCodice().toString());
	    model.addAttribute("commandName", "calendariomercatoParametri");
	    model.addAttribute("method", "create.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	// setto finito il passo 0 e vado al passo 1
	calendariomercatoParametri.setStep(1);
	model.addAttribute("calendariomercatoParametri", calendariomercatoParametri);
	status.setComplete();
	return "redirect:view.htm?codice=" +
		codiceMercato +
		"&mercatouso.id.codice=" +
		calendariomercatoParametri.getMercatiUso().getId().getCodice() +
		"&anno=" +
		calendariomercatoParametri.getAnno();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String update(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatiUso") Integer codiceuso,
	    @RequestParam("day") Integer day, @RequestParam("month") Integer month, @RequestParam("year") Integer year,
	    @ModelAttribute("calendariomercatoParametri") CalendariomercatoParametri calendariomercatoParametri, BindingResult result,
	    SessionStatus status, HttpServletRequest request, Model model) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	// creo l'oggetto data che devo aggiornare
	Calendar date = new GregorianCalendar(year, month, day, 0, 0, 0);
	Integer annoInteger = date.get(Calendar.YEAR);
	// Verifico se la data passata è nella lista:
	// Si-->la elimino
	// No-->l'aggiungo
	MercatipresenzeT mercatipresenzeT = null;
	List<MercatipresenzeT> calendarioMercato = mercatipresenzeTService.findMercatipresenzeTByMercatiAndMercatiUso(mercati, mercatiUso,
		annoInteger);
	if (!mercatipresenzeTService.findDay(calendarioMercato, date)) {
	    mercatipresenzeT = new MercatipresenzeT();
	    mercatipresenzeT.setAnno(date.get(Calendar.YEAR));
	    mercatipresenzeT.setMercatoUso(mercatiUso);
	    mercatipresenzeT.setMercato(mercati);
	    mercatipresenzeT.setDescrizione(String.valueOf(annoInteger) + " " + mercati.getDescrizione() + " " + mercatiUso.getDescrizione());
	    mercatipresenzeT.setSoftware(mercati.getSoftware());
	    mercatipresenzeT.setDataRegistrazione(date.getTime());
	    Responsabili utenteLoggato = new Responsabili();
	    LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	    utenteLoggato.getId().setCodice(user.getCodiceResponsabile());
	    utenteLoggato.setResponsabile(user.getResponsabile());
	    mercatipresenzeT.setResponsabile(utenteLoggato);
	    mercatipresenzeTService.insert(mercatipresenzeT);
	} else {
	    mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
	    mercatipresenzeTService.delete(mercatipresenzeT);
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + codiceMercato + "&mercatouso.id.codice=" + mercatiUso.getId().getCodice() + "&anno=" + annoInteger;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String create(@RequestParam("codice") Integer codiceMercati, @RequestParam(value = "anno", required = false) Integer anno,
	    @RequestParam(value = "giorno", required = false) Integer giorno,
	    @RequestParam(value = "giorniSettimanaSelected", required = false) String giorniSettimanaSelected, Model model) {

	// §§§BEGIN§§§
	CalendariomercatoParametri calendariomercatoParametri = new CalendariomercatoParametri();
	// recupero la lista dei giorni della settimana e li setto nel model
	List<Giornisettimana> giorniSettimana = giornisectimanaService.findAll(null, null);
	calendariomercatoParametri.setGiorniSettimana(giorniSettimana);
	// indica che sono al passo 0 creo il form per l'inserimento
	// delle informazioni di periodicità, tipiscadenza, registarzionicausali
	// (sono nella fase create)
	if (giorno != null) {
	    MercatiUso uso = mercatiUsoService.findById(new PkId(giorno));
	    calendariomercatoParametri.setMercatiUso(uso);
	}
	if (anno != null) {
	    calendariomercatoParametri.setAnno(anno);
	}
	if (StringUtils.isNotBlank(giorniSettimanaSelected)) {
	    String[] giorniSettimanaSelectedArray = null;
	    if (giorniSettimanaSelected.indexOf(",") > -1) {
		giorniSettimanaSelectedArray = giorniSettimanaSelected.split(",");
	    } else {
		giorniSettimanaSelectedArray = new String[] { giorniSettimanaSelected };
	    }
	    for (int i = 0; i < giorniSettimanaSelectedArray.length; i++) {
		for (Giornisettimana giornosettimana : giorniSettimana) {
		    if (String.valueOf(giornosettimana.getId()).equals(giorniSettimanaSelectedArray[i])) {
			giornosettimana.setTransientSelected(true);
		    }
		}
	    }
	}
	calendariomercatoParametri.setStep(0);
	// serve per capire il tipo di mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercati));
	Set<MercatiUso> mercatiusoList = mercati.getMercatiUsos();
	model.addAttribute("mercati", mercati);
	model.addAttribute("calendariomercatoParametri", calendariomercatoParametri);
	model.addAttribute("mercatiusoList", mercatiusoList);
	setPageAttributes(model);
	return "calendariomercato/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxCheckboxMercatoUso(@RequestParam("codiceUso") Integer codiceUso, Model model) {

	// §§§BEGIN§§§
	List<Giornisettimana> giorniSettimana = giornisectimanaService.findAll(null, null);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	if (uso != null) {
	    Giornisettimana giorno = uso.getGiornisettimana();
	    // se il giorno non è "NESSUNO"
	    if (giorno.getGsValore() != null) {
		// itero per verificare quale giorno selezionare tra i checkbox
		for (Giornisettimana giornisettimana2 : giorniSettimana) {
		    if ((giornisettimana2.getGsValore() != null) && (giorno.getGsValore().intValue() == giornisettimana2.getGsValore().intValue())) {
			giornisettimana2.setTransientSelected(true);
		    }
		}
	    }
	}
	model.addAttribute("giorniSettimana", giorniSettimana);
	return "ajax/dettaglioGiorniCalendario";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam("mercatouso.id.codice") Integer codiceMercatoUso,
	    @RequestParam("anno") Integer anno, Model model, HttpServletRequest request) {

	// §§§BEGIN§§§
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceMercatoUso));
	// estrae tutte le tuple di presenzemercato_t filtrate per
	// mercati,mercatiuso,anno
	List<MercatipresenzeT> mercatipresenzeTList = mercatipresenzeTService.findMercatipresenzeTByMercatiAndMercatiUso(mercati, mercatiUso, anno);
	CalendariomercatoParametri calendariomercatoParametri = new CalendariomercatoParametri();
	calendariomercatoParametri.setAnno(anno.intValue());
	// Creo la lista di tutti i giorni festivi ( fissi e non )
	calendariomercatoParametri.setGiorniFestivi(calendariomercatoParametriService.findGiornifestivi(calendariomercatoParametri.getAnno()));
	calendariomercatoParametri.setMercatiUso(mercatiUso);
	// setto lo step a 1 per capire che sono ancora al passo 1 (usato per
	// vari controlli sulla jsp)
	if (request.getParameter("step") != null) {
	    calendariomercatoParametri.setStep(2);
	} else {
	    calendariomercatoParametri.setStep(1);
	}
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiUso", mercatiUso);
	model.addAttribute("mercatipresenzeTList", mercatipresenzeTList);
	model.addAttribute("calendariomercatoParametri", calendariomercatoParametri);
	model.addAttribute("anno", anno);
	// flagMercatoContabilita=true : gestisco le registrazioni contabili
	// flagMercatoContabilita=false : non gestisco le registrazioni contabili
	model.addAttribute("flagMercatoContabilita", mercati.getFlagContabilita().booleanValue());
	// controllo se sono stati aggiunti dei giorni di mercato
	if (!mercatipresenzeTList.isEmpty()) {
	    // controllo se il mercato è stato storicizzato
	    boolean mercatoStoricizzato = mercatipresenzeTService.verificaMercatoStoricizzato(mercati, mercatiUso, anno);
	    model.addAttribute("flagMercatoStoricizzato", mercatoStoricizzato);
	} else {
	    model.addAttribute("flagMercatoStoricizzato", false);
	}
	String blocco_accesso_futuro = this.comportamentiMercatiService.bloccaAccessoMercNelFuturo() ? "S" : "N";
	model.addAttribute("blocco_accesso_futuro", blocco_accesso_futuro);
	setPageAttributes(model);
	return "calendariomercato/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String listusianni(@RequestParam("mercati.id.codice") Integer codice, HttpServletRequest request, HttpServletResponse response,
	    Model model) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(new PkId(codice));
	// Recupera un solo oggetto mercatiprezenze_t per ogni
	// mercato,usomercato,anno
	List<MercatipresenzeT> mercatipresenzeTList = mercatipresenzeTService.findByMercatoAndGroupByAnnoAndMercatoUso(mercati);
	boolean export = createJMesaExport(request, response, mercatipresenzeTList);
	if (export)
	    return null;
	model.addAttribute("mercati", mercati);
	model.addAttribute("codice", codice);
	model.addAttribute("mercatipresenzeTList", mercatipresenzeTList);
	return "calendariomercato/listusianni";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String createSearch(Model model) {

	// §§§BEGIN§§§
	MercatipresenzeT mercatipresenzeT = new MercatipresenzeT();
	Calendar anno = Calendar.getInstance();
	mercatipresenzeT.setAnno(anno.get(Calendar.YEAR));
	model.addAttribute("mercatipresenzeT", mercatipresenzeT);
	Boolean b = userHasRole(false, RuoliUtentiEnum.GESTIONE_POSIZIONI_DEBITORIE.name());
	model.addAttribute("GESTIONE_POSIZIONI_DEBITORIE", b);
	setPageAttributes(model);
	return "calendariomercato/search";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String search(@ModelAttribute("mercatipresenzeT") MercatipresenzeT mercatipresenzeT, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(mercatipresenzeT.getMercato().getId());
	List<MercatipresenzeT> mercatipresenzeTList = null;
	// Recupera un solo oggetto mercatiprezenze_t per ogni
	// mercato,anno
	try {
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    int c = mercatiResponsabiliService.countByResponsabile(r.getId().getCodice());
	    if (c > 0) {
		mercatipresenzeTList = mercatipresenzeTService.findByMercatoAndAnnoAndResponsabileGroupByMercatoUso(mercati,
			mercatipresenzeT.getAnno(), r.getId().getCodice());
	    } else {
		mercatipresenzeTList = mercatipresenzeTService.findByMercatoAndAnnoGroupByMercatoUso(mercati, mercatipresenzeT.getAnno());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatipresenzeT, e);
	    fixRenderEntityMercatipresenzeT(mercatipresenzeT);
	    model.addAttribute("mercatipresenzeT", mercatipresenzeT);
	    return "calendariomercato/search";
	}
	boolean export = createJMesaExport(request, response, mercatipresenzeTList);
	if (export)
	    return null;
	model.addAttribute("mercatipresenzeT", mercatipresenzeT);
	model.addAttribute("mercatipresenzeTList", mercatipresenzeTList);
	setPageAttributes(model);
	return "calendariomercato/listResult";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String situazionecontabile(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	// §§§BEGIN§§§
	Mercati mercati = mercatiService.findById(new PkId(codice));
	String codiceUso = (String) request.getParameter("uso");
	MercatiUso uso = null;
	if (!(null == codiceUso || codiceUso.equals(""))) {
	    uso = mercatiUsoService.findById(new PkId(new Integer(codiceUso)));
	}
	Vector<List<Posteggio>> vector = registrazioniService.findSituazioneContabileByMercatoAndPosteggioAndMercatoUso(mercati, uso);
	model.addAttribute("mercato", mercati);
	model.addAttribute("posteggiolistmercatouso", vector);
	return "calendariomercato/situazionecontabile";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxUpdateConsolidaPresenzeAnno(Model model, @RequestParam("codiceMercato") Integer codiceMercato, @RequestParam("anno") Integer anno,
	    HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {

	// §§§BEGIN§§§
	String result = "KO";
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	try {
	    boolean esito = mercatipresenzeDService.updateConsolidaPresenzePerAnno(codiceMercato, anno);
	    String messaggio = "MERCATI_CONSOLIDA_PRESENZE: Il responsabile " +
		    r.toString() +
		    " ha effettuato l'operazione di consolidamento delle presenze per il mercato con codice: " +
		    codiceMercato +
		    ", anno: " +
		    anno;
	    LoggerCancellazioni.log(messaggio);
	    result = esito == true ? "OK" : "KO";
	} catch (Exception e) {
	}
	response.getOutputStream().write(result.getBytes("UTF-8"));
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void fixRenderEntityMercatipresenzeT(MercatipresenzeT mercatipresenzeT) {

	// §§§BEGIN§§§
	if (mercatipresenzeT.getMercato() == null) {
	    mercatipresenzeT.setMercato(new Mercati());
	}
	// §§§END§§§
    }

    // validazione:
    // 1-controlla presenza dei parametri
    // 2-evita inserimenti doppi
    private boolean validate(CalendariomercatoParametri entity, BindingResult result, Mercati mercati) {

	boolean success = true;
	// §§§BEGIN§§§
	if (entity.getAnno() == null) {
	    result.rejectValue("anno", "validator.nonvuoto");
	    success = false;
	}
	if (entity.getMercatiUso().getId().getCodice() == null || entity.getMercatiUso().getId().getCodice().intValue() == -1) {
	    result.rejectValue("mercatiUso", "validator.nonvuoto");
	    success = false;
	}
	if ((entity.getMercatiUso().getId().getCodice() != null && entity.getMercatiUso().getId().getCodice().intValue() != -1)
		&& entity.getAnno() != null
		&& mercatipresenzeTService.findMercatipresenzeTByMercatiAndMercatiUso(mercati, entity.getMercatiUso(), entity.getAnno()).size() > 0) {
	    result.rejectValue("mercatiUso", "validator.mercatiUsoPresente");
	    success = false;
	}
	// §§§END§§§
	return success;
    }

    @RequestMapping
    public String ajaxViewConfGestionePresenza(@RequestParam("idGiornata") Integer idGiornata, @RequestParam("url_back") String url_back, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	model.addAttribute("giorno", giorno);
	// controllo se il mercato è stato storicizzato
	boolean mercatoStoricizzato = mercatipresenzeTService.verificaMercatoStoricizzato(giorno.getMercato(), giorno.getMercatoUso(),
		giorno.getAnno());
	model.addAttribute("flagMercatoStoricizzato", Boolean.valueOf(mercatoStoricizzato));
	Boolean blocco_accesso_futuro = this.comportamentiMercatiService.bloccaAccessoMercNelFuturo();
	GregorianCalendar today = new GregorianCalendar();
	Boolean blocca_accesso = Boolean.FALSE;
	if (blocco_accesso_futuro && Utilities.compareDates(giorno.getDataRegistrazione(), today.getTime()) == 1) {
	    // data giornaliera superiore alla data della giornata di mercato, non devo dare la possibilità 
	    // di accedere alla giornata
	    blocca_accesso = Boolean.TRUE;
	}
	model.addAttribute("codicemercato", giorno.getMercato().getId().getCodice());
	model.addAttribute("codiceuso", giorno.getMercatoUso().getId().getCodice());
	String dataMercato = Utilities.formatDate(giorno.getDataRegistrazione(), false);
	String meseMercato = Utilities.formatDate(giorno.getDataRegistrazione(), "MM");
	model.addAttribute("dataMercato", dataMercato + "#" + meseMercato);
	Boolean gestioneAbilitata = userHasRole(false, RuoliUtentiEnum.GESTIONE_CALENDARIO_MERCATI.name());
	model.addAttribute("GESTIONE_ABILITATA", gestioneAbilitata);
	model.addAttribute("blocco_accesso_futuro", blocca_accesso);
	List<Concessioniuso> concessioniUsos = concessioniusoService.findAll(null, null);
	model.addAttribute("concessioniUsos", concessioniUsos);
	Integer codiceConcessioniUso = null;
	if (giorno != null && giorno.getConcessioniuso() != null && giorno.getConcessioniuso().getId() != null
		&& giorno.getConcessioniuso().getId().getCodice() != null) {
	    codiceConcessioniUso = giorno.getConcessioniuso().getId().getCodice();
	} else {
	    if (giorno != null && giorno.getMercatoUso() != null && giorno.getMercatoUso().getConcessioniuso() != null
		    && giorno.getMercatoUso().getConcessioniuso().getId() != null
		    && giorno.getMercatoUso().getConcessioniuso().getId().getCodice() != null) {
		codiceConcessioniUso = giorno.getMercatoUso().getConcessioniuso().getId().getCodice();
	    }
	}
	model.addAttribute("concessioniUsoSelezionato", codiceConcessioniUso);
	return "calendariomercato/ajaxViewConfGestionePresenza";
    }

    @Override
    protected void fixMergeEntityProperty(CalendariomercatoParametri entity) {

    }

    @Override
    protected void fixRenderEntityProperty(CalendariomercatoParametri entity) {

	// §§§BEGIN§§§
	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
	// §§§END§§§
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Concessioniuso> concessioniUsos = concessioniusoService.findAll(null, null);
	model.addAttribute("concessioniUsos", concessioniUsos);
    }

    @RequestMapping
    public String eliminaGiornataMercato(@RequestParam("id_giornata") Integer idGiornata, Model model, HttpServletRequest request) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_CALENDARIO_MERCATI.name());
	MercatipresenzeT entity = mercatipresenzeTService.findById(new PkId(idGiornata));
	Integer codiceMercato = entity.getMercato().getId().getCodice();
	Integer codiceUso = entity.getMercatoUso().getId().getCodice();
	Integer anno = entity.getAnno();
	mercatipresenzeTService.delete(entity);
	return "redirect:view.htm?codice=" + codiceMercato + "&mercatouso.id.codice=" + codiceUso + "&anno=" + anno;
    }

    @RequestMapping
    public String aggiornaGiornataMercato(@RequestParam("id_giornata") Integer idGiornata, @RequestParam("conc_uso") Integer concUsoId,
	    @RequestParam("flagPopolaConcessionari") Boolean flagPopolaConcessionari,
	    @RequestParam("flagConteggiaPresAss") Boolean flagConteggiaPresAss, HttpServletRequest request) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_CALENDARIO_MERCATI.name());
	mercatipresenzeTService.aggiornaGiornataMercato(idGiornata, concUsoId, flagConteggiaPresAss, flagPopolaConcessionari);
	return "redirect:tornaAView.htm?id_giornata=" + idGiornata;
    }

    @RequestMapping
    public String tornaAView(@RequestParam("id_giornata") Integer idGiornata, HttpServletRequest request) {

	MercatipresenzeT entity = mercatipresenzeTService.findById(new PkId(idGiornata));
	Integer codiceMercato = entity.getMercato().getId().getCodice();
	Integer codiceUso = entity.getMercatoUso().getId().getCodice();
	Integer anno = entity.getAnno();
	return "redirect:view.htm?codice=" + codiceMercato + "&mercatouso.id.codice=" + codiceUso + "&anno=" + anno;
    }

    @RequestMapping
    public String inserisciGiornataMercato(@RequestParam("anno") Integer anno, @RequestParam("mese") Integer mese,
	    @RequestParam("giorno") Integer giorno, @RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam("codiceuso") Integer codiceuso, @RequestParam("conc_uso") Integer concUsoId,
	    @RequestParam("flagPopolaConcessionari") Boolean flagPopolaConcessionari,
	    @RequestParam("flagConteggiaPresAss") Boolean flagConteggiaPresAss, HttpServletRequest request) {

	userHasRole(true, RuoliUtentiEnum.GESTIONE_CALENDARIO_MERCATI.name());
	mercatipresenzeTService.inserisceNuovaGiornataMercato(codicemercato, codiceuso, anno, mese, giorno, concUsoId, flagConteggiaPresAss,
		flagPopolaConcessionari, getCurrentlyAuthenticatedUserDetails());
	return "redirect:view.htm?codice=" + codicemercato + "&mercatouso.id.codice=" + codiceuso + "&anno=" + anno;
    }
}
