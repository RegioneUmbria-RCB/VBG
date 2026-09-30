package it.gruppoinit.pal.gp.backoffice.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCfgAttivita;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Periodicita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniDaMercato;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniStatisticheMercati;
import it.gruppoinit.pal.gp.core.domain.Registrazionimercato;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calcolo.ICalcoloCostoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.MercatiCfgAttivitaService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.PeriodicitaService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazionimercatoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiScadenzaService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;

@Controller
@SessionAttributes(value = { "mercatipresenzeT", "registrazioniFilter" })
public class RegistrazionimercatoController extends BaseController<Registrazionimercato> {

    // private static final Logger log = LoggerFactory.getLogger(RegistrazionimercatoController.class);
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private ICalcoloCostoPosteggiService calcoloCostoPosteggiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private TipiScadenzaService tipiScadenzaService;
    @Autowired
    private PeriodicitaService periodicitaService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private RegistrazionimercatoService registrazionimercatoService;
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MercatiCfgAttivitaService mercatiCfgAttivitaService;
    @Autowired
    private VwConcessioniattiveService vwConcessioniattiveService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;

    @RequestMapping
    public String create(Model model, @RequestParam("mercati.id.codice") Integer codiceMercati,
	    @RequestParam("mercatouso.id.codice") Integer mercatoUsoId, @RequestParam("anno") Short anno) {

	// §§§BEGIN§§§
	Registrazionimercato registrazionimercato = new Registrazionimercato();
	registrazionimercato.setTipoCalcolo(1);
	// indica che sono al passo 0 creo il form per l'inserimento
	// delle informazioni di periodicità, tipiscadenza, registarzionicausali
	// (sono nella fase create)
	// registrazionimercato.setStep(0);
	registrazionimercato.setDataRegistrazione(new Date());
	// serve per capire il tipo di mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercati));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(mercatoUsoId));
	// estraggo le periodicità e tipi scadenze da poi presentare sul
	// form come combobox
	registrazionimercato.setAnno(anno.intValue());
	registrazionimercato.setMercatiUso(mercatiUso);
	List<Periodicita> periodicitaList = periodicitaService.findAll(null, null);
	List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findByDescrizioneEscluseRiduzioni("");
	List<RangeRateizzazioni> rangeRateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	RegistrazioniCausali causaleDefault = mercatiConfigurazione.getCausaleCanone();
	registrazionimercato.setRegistrazioniCausali(causaleDefault);
	model.addAttribute("mercati", mercati);
	model.addAttribute("registrazionimercato", registrazionimercato);
	model.addAttribute("periodicitaList", periodicitaList);
	model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	model.addAttribute("rangeRateizzazioniList", rangeRateizzazioniList);
	setPageAttributes(model);
	return "registrazionimercato/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String insertRegistrazioni(@RequestParam("mercati.id.codice") Integer codiceMercato,
	    @ModelAttribute("registrazionimercato") Registrazionimercato registrazionimercato, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	throw new NotImplementedException("Funzinoalità dismessa");
	// §§§BEGIN§§§
	//	PkId idMercato = new PkId(codiceMercato);
	//	Mercati mercati = mercatiService.findById(idMercato);
	//	if (!validate(registrazionimercato, result, mercati)) {
	//	    fixRenderEntityProperty(registrazionimercato);
	//	    MercatiUso mercatiUso = mercatiUsoService.findById(registrazionimercato.getMercatiUso().getId());
	//	    registrazionimercato.setMercatiUso(mercatiUso);
	//	    // recupero le periodicità, tipi scadenze e causali da presentare sul form come combobox
	//	    List<Periodicita> periodicitaList = periodicitaService.findAll(null, null);
	//	    List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	//	    List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findAll(null, null);
	//	    List<RangeRateizzazioni> rangeRateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	//	    // setto l'anno corrente per evitare il nullpointer exception
	//	    if (registrazionimercato.getAnno() == null) {
	//		registrazionimercato.setAnno(Calendar.getInstance().get(Calendar.YEAR));
	//	    }
	//	    if (registrazionimercato.getDataRegistrazione() == null) {
	//		registrazionimercato.setDataRegistrazione(new Date());
	//	    }
	//	    model.addAttribute("mercati", mercati);
	//	    model.addAttribute("registrazionimercato", registrazionimercato);
	//	    model.addAttribute("periodicitaList", periodicitaList);
	//	    model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	//	    model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	//	    model.addAttribute("rangeRateizzazioniList", rangeRateizzazioniList);
	//	    return "registrazionimercato/form";
	//	}
	//	RegistrazioniCausali causali = registrazioniCausaliService.findById(registrazionimercato.getRegistrazioniCausali().getId());
	//	registrazionimercato.setRegistrazioniCausali(causali);
	//	MercatiUso mercatiUso = mercatiUsoService.findById(registrazionimercato.getMercatiUso().getId());
	//	registrazionimercato.setMercatiUso(mercatiUso);
	//	if (registrazionimercato.getTipoCalcolo().intValue() == 0) { // calcolo classico
	//	    List<MercatipresenzeT> mercatipresenzeTList = mercatipresenzeTService.findMercatipresenzeTByMercatiAndMercatiUso(mercati,
	//		    registrazionimercato.getMercatiUso(), registrazionimercato.getAnno());
	//	    registrazionimercato.setMercatipresenzeTList(mercatipresenzeTList);
	//	    // Recupero dal mercato tutti i mercati in uso associati e di
	//	    // ognuno carico il giorno della settima assogiato
	//	    Responsabili utenteLoggato = new Responsabili();
	//	    LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	//	    utenteLoggato.getId().setCodice(user.getCodiceResponsabile());
	//	    utenteLoggato.setResponsabile(user.getResponsabile());
	//	    registrazionimercato.setUtenteLoggato(utenteLoggato);
	//	    if (registrazionimercato.getPeriodicita() != null) {
	//		if (registrazionimercato.getPeriodicita().getId() != null) {
	//		    Periodicita periodicita = periodicitaService.findById(registrazionimercato.getPeriodicita().getId());
	//		    registrazionimercato.setPeriodicita(periodicita);
	//		}
	//	    }
	//	    try {
	//		registrazionimercatoService.createRegistrazioni(mercati, registrazionimercato);
	//	    } catch (Exception e) {
	//		copyErrorsToBindingResult(result, registrazionimercato, e);
	//		fixRenderEntityProperty(registrazionimercato);
	//		MercatiUso mercatiUso2 = mercatiUsoService.findById(registrazionimercato.getMercatiUso().getId());
	//		registrazionimercato.setMercatiUso(mercatiUso2);
	//		// recupero le periodicità, tipi scadenze e causali da presentare sul form come combobox
	//		List<Periodicita> periodicitaList = periodicitaService.findAll(null, null);
	//		List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	//		List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findAll(null, null);
	//		List<RangeRateizzazioni> rangeRateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	//		// setto l'anno corrente per evitare il nullpointer exception
	//		if (registrazionimercato.getAnno() == null) {
	//		    registrazionimercato.setAnno(Calendar.getInstance().get(Calendar.YEAR));
	//		}
	//		if (registrazionimercato.getDataRegistrazione() == null) {
	//		    registrazionimercato.setDataRegistrazione(new Date());
	//		}
	//		model.addAttribute("mercati", mercati);
	//		model.addAttribute("registrazionimercato", registrazionimercato);
	//		model.addAttribute("periodicitaList", periodicitaList);
	//		model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	//		model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	//		model.addAttribute("rangeRateizzazioniList", rangeRateizzazioniList);
	//		// return "redirect:create.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatouso.id.codice="
	//		// + mercatiUso.getId().getCodice() + "&anno=" + registrazionimercato.getAnno();
	//		return "registrazionimercato/form";
	//	    }
	//	} else {
	//	    try {
	//		registrazionimercatoService.createRegistrazioniAnnuali(mercati, registrazionimercato);
	//	    } catch (Exception e) {
	//		copyErrorsToBindingResult(result, registrazionimercato, e);
	//		fixRenderEntityProperty(registrazionimercato);
	//		MercatiUso mercatiUso2 = mercatiUsoService.findById(registrazionimercato.getMercatiUso().getId());
	//		registrazionimercato.setMercatiUso(mercatiUso2);
	//		// recupero le periodicità, tipi scadenze e causali da presentare sul form come combobox
	//		List<Periodicita> periodicitaList = periodicitaService.findAll(null, null);
	//		List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	//		List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findAll(null, null);
	//		List<RangeRateizzazioni> rangeRateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	//		// setto l'anno corrente per evitare il nullpointer exception
	//		if (registrazionimercato.getAnno() == null) {
	//		    registrazionimercato.setAnno(Calendar.getInstance().get(Calendar.YEAR));
	//		}
	//		if (registrazionimercato.getDataRegistrazione() == null) {
	//		    registrazionimercato.setDataRegistrazione(new Date());
	//		}
	//		model.addAttribute("mercati", mercati);
	//		model.addAttribute("registrazionimercato", registrazionimercato);
	//		model.addAttribute("periodicitaList", periodicitaList);
	//		model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	//		model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	//		model.addAttribute("rangeRateizzazioniList", rangeRateizzazioniList);
	//		// return "redirect:create.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatouso.id.codice="
	//		// + mercatiUso.getId().getCodice() + "&anno=" + registrazionimercato.getAnno();
	//		return "registrazionimercato/form";
	//	    }
	//	}
	//	// setto come filtri i parametri scelti per l'inserimento
	//	// mi permette di creare una ricerca delle registrazioni appena inserite
	//	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	//	registrazioniFilter.setDataInizio(registrazionimercato.getDataRegistrazione());
	//	registrazioniFilter.setDataFine(registrazionimercato.getDataRegistrazione());
	//	registrazioniFilter.setRegistrazioniCausali(causali);
	//	registrazioniFilter.setMercati(mercati);
	//	registrazioniFilter.setMercatiUso(mercatiUso);
	//	// lo mette sulla request
	//	model.addAttribute("registrazioniFilter", registrazioniFilter);
	//	// passo i vari parametri in get come il percorso in cui si trovano
	//	return "redirect:../registrazioni/search.htm?" + "registrazioniFilter.mercati.id.codice="
	//		+ registrazioniFilter.getMercati().getId().getCodice() + "registrazioniFilter.registrazioniCausali.id.codice"
	//		+ registrazioniFilter.getRegistrazioniCausali().getId().getCodice() + "registrazioniFilter.mercatiUso.id.codice"
	//		+ registrazioniFilter.getMercatiUso().getId().getCodice();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String result() {

	return "registrazionimercato/result";
    }

    @RequestMapping
    public ModelMap statisticheMercato(@RequestParam("mercati.id.codice") Integer codiceMercati, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId id = new PkId(codiceMercati);
	Mercati mercati = mercatiService.findById(id);
	List<RegistrazioniStatisticheMercati> registrazioniStatisticheMercatiList = registrazioniService.findRegByMercato(codiceMercati);
	ModelMap model = new ModelMap(registrazioniStatisticheMercatiList);
	model.addAttribute("registrazioniStatisticheMercatiList", registrazioniStatisticheMercatiList);
	model.addAttribute("mercati", mercati);
	boolean export = createJMesaExport(request, response, registrazioniStatisticheMercatiList);
	if (export)
	    return null;
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String registrazioniByMercato(Model model, @RequestParam("mercati.id.codice") Integer codiceMercati,
	    @RequestParam("mercatoUso") Integer mercatoUsoId, @RequestParam("anno") Short anno, HttpServletRequest request,
	    HttpServletResponse response) {

	// §§§BEGIN§§§
	PkId idMercati = new PkId(codiceMercati);
	Mercati mercati = mercatiService.findById(idMercati);
	PkId id = new PkId(mercatoUsoId);
	MercatiUso mercatiUso = mercatiUsoService.findById(id);
	List<RegistrazioniDaMercato> registrazioniDaMercatoList = registrazioniService.findRegByMercatoForCausale(anno, codiceMercati, mercatiUso);
	if (registrazioniDaMercatoList.isEmpty()) {
	    return "redirect:create.htm?mercati.id.codice=" + codiceMercati + "&mercatouso.id.codice=" + mercatoUsoId + "&anno=" + anno;
	}
	// ModelMap model = new ModelMap(registrazioniDaMercatoList);
	model.addAttribute("registrazioniDaMercatoList", registrazioniDaMercatoList);
	model.addAttribute("anno", anno);
	model.addAttribute("mercatoUso", mercatiUso);
	model.addAttribute("mercati", mercati);
	boolean export = createJMesaExport(request, response, registrazioniDaMercatoList);
	if (export)
	    return null;
	return "registrazionimercato/registrazioniByMercato";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String registrazionipresenze(@RequestParam("mercati.id.codice") Integer codiceMercato,
	    @RequestParam("mercatouso.id.codice") Integer mercatoUsoId, @RequestParam("giornoMercato") Date giornoMercato, Model model) {

	// §§§BEGIN§§§
	// model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	PkId idMercato = new PkId(codiceMercato);
	Mercati mercato = mercatiService.findById(idMercato);
	PkId idUso = new PkId(mercatoUsoId);
	MercatiUso mercatiUso = mercatiUsoService.findById(idUso);
	Calendar giorno = Calendar.getInstance();
	giorno.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(giorno, mercato, mercatiUso);
	MercatipresenzeT mercatiPresenzeTModelAttribute = new MercatipresenzeT();
	mercatiPresenzeTModelAttribute.setAnno(mercatipresenzeT.getAnno());
	mercatiPresenzeTModelAttribute.setDataRegistrazione(mercatipresenzeT.getDataRegistrazione());
	mercatiPresenzeTModelAttribute.setDescrizione(mercatipresenzeT.getDescrizione());
	mercatiPresenzeTModelAttribute.setMercato(mercatipresenzeT.getMercato());
	mercatiPresenzeTModelAttribute.setMercatoUso(mercatipresenzeT.getMercatoUso());
	mercatiPresenzeTModelAttribute.setSoftware(mercatipresenzeT.getSoftware());
	mercatiPresenzeTModelAttribute.setResponsabile(mercatipresenzeT.getResponsabile());
	mercatiPresenzeTModelAttribute.setFlagRegfatte(mercatipresenzeT.getFlagRegfatte());
	Set<MercatipresenzeD> listaPresenze = mercatipresenzeT.getListaPresenze();
	Set<MercatipresenzeD> presenze = new HashSet<MercatipresenzeD>();
	List<MercatiCfgAttivita> listMcfgAttivita = mercatiCfgAttivitaService.findAll(null, null);
	List<MercatipresenzeD> assenzeConcessionariList = new ArrayList<MercatipresenzeD>();
	Boolean registrazioniFatte = mercatipresenzeT.getFlagRegfatte();
	if (null == registrazioniFatte) {
	    registrazioniFatte = false;
	}
	if (registrazioniFatte.booleanValue() == false) {
	    for (MercatipresenzeD mercatipresenzeD : listaPresenze) {
		if (mercatipresenzeD.getPosteggio() != null) {
		    MercatiD posteggio = mercatipresenzeD.getPosteggio();
		    VwConcessioniattive concessioneAttiva = vwConcessioniattiveService.findByMercatoUsoPosteggio(codiceMercato, mercatoUsoId,
			    posteggio.getId().getCodice());
		    // devo tirare fuori gli aumenti di accertamento - SPUNTISTI (dove isSpuntista = true)
		    // le diminuzioni di accertamento CONCESSIONARI se Mercati.flag_regcontassenza = true
		    // e se isSpuntista=true o Posteggio con concessione è Vuoto
		    if (mercatipresenzeD.isSpuntista()) {
			if (mercatipresenzeD.getRegistrazioneOccupante() == null) { // se la registrazioni è già stata
			    // effettuata
			    // aumenti di accertamento X spuntisti
			    PosteggioImportoHelper costoPosteggioSpuntista = calcoloCostoPosteggiService.calcolaCostoPosteggio(mercatipresenzeD,
				    posteggio, listMcfgAttivita, mercatipresenzeT.getAnno().intValue(), 1, concessioneAttiva,
				    WebConstants.MERCATO_CONTESTO_SPUNTISTI, null, mercatipresenzeT.getMercatoUso().getId().getCodice(),
				    mercatipresenzeT.getDataRegistrazione(), MercatiFormuleCalcoloContestoEnum.PRESENZA);
			    mercatipresenzeD.setCostoPosteggio(costoPosteggioSpuntista);
			    presenze.add(mercatipresenzeD);
			}
		    }
		    // .. BEGIN LE ASSENZE DEI CONCESSIONARI NON VENGONO PIU' CONTEGGIATE
		    // if (mercato.getFlagRegContAssenza() != null && mercato.getFlagRegContAssenza().booleanValue()) {
		    // // solo se il mercato prevede la registrazioni delle assenze dei concessionari
		    // // allora registro anche le diminuzioni di accertamento
		    //
		    // if (mercatipresenzeD.isSpuntista() || mercatipresenzeD.getOccupante() == null) {
		    // if (mercatipresenzeD.getConcessionario() != null) {
		    // if (mercatipresenzeD.getRegistrazioneConcessionario() == null) {
		    // PosteggioImportoHelper costoPosteggioConcessionario = mercatiDService
		    // .calcolaCostoPosteggio(posteggio, listMcfgAttivita, mercatipresenzeT
		    // .getAnno().intValue(), 1, concessioneAttiva,
		    // WebConstants.MERCATO_CONTESTO_CONCESSIONARI);
		    // MercatipresenzeD assenzaConcessionario = new MercatipresenzeD();
		    // assenzaConcessionario.setConcessionario(mercatipresenzeD.getConcessionario());
		    // assenzaConcessionario.setMercatiPresenzeT(mercatiPresenzeTModelAttribute);
		    // assenzaConcessionario.setPosteggio(posteggio);
		    // assenzaConcessionario.setCostoPosteggio(costoPosteggioConcessionario);
		    // assenzeConcessionariList.add(assenzaConcessionario);
		    // }
		    // }
		    // }
		    // }
		    // .. END LE ASSENZE DEI CONCESSIONARI NON VENGONO PIU' CONTEGGIATE
		}
	    }
	}
	mercatiPresenzeTModelAttribute.setListaPresenze(presenze);
	List<Tipimodalitapagamento> tipipagamentolist = tipimodalitapagamentoService.findAll(null, null, false);
	List<Registrazioni> registrazioniSpuntistiList = new ArrayList<Registrazioni>();
	List<Registrazioni> registrazioniConcessionariList = new ArrayList<Registrazioni>();
	List<MercatipresenzeDDTO> listaPresenze2 = mercatipresenzeDService.findListaPosteggi(mercatipresenzeT);
	for (MercatipresenzeDDTO mercatipresenzeDDTO : listaPresenze2) {
	    MercatipresenzeD mercatipresenzeD2 = mercatipresenzeDService.findById(mercatipresenzeDDTO.getId());
	    if (mercatipresenzeD2.getRegistrazioneConcessionario() != null) {
		registrazioniConcessionariList.add(mercatipresenzeD2.getRegistrazioneConcessionario());
	    }
	    if (mercatipresenzeD2.getRegistrazioneOccupante() != null) {
		registrazioniSpuntistiList.add(mercatipresenzeD2.getRegistrazioneOccupante());
	    }
	}
	MercatiConfigurazioneId idCfg = new MercatiConfigurazioneId();
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(idCfg);
	RegistrazioniCausali causaleAumento = mercatiConfigurazione.getCausaleAumento();
	RegistrazioniCausali causaleDiminuzione = mercatiConfigurazione.getCausaleDiminuzione();
	model.addAttribute("causaleDiminuzione", causaleDiminuzione);
	model.addAttribute("causaleAumento", causaleAumento);
	model.addAttribute("mercato", mercato);
	model.addAttribute("mercatoUso", mercatiUso);
	model.addAttribute("giornoMercato", giornoMercato);
	model.addAttribute("tipipagamentolist", tipipagamentolist);
	model.addAttribute("mercatipresenzeT", mercatiPresenzeTModelAttribute);
	model.addAttribute("registrazioniSpuntistiList", registrazioniSpuntistiList);
	model.addAttribute("assenzeConcessionariList", assenzeConcessionariList);
	model.addAttribute("registrazioniConcessionariList", registrazioniConcessionariList);
	setPageAttributes(model);
	return "registrazionimercato/registrazionipresenze";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    //
    @RequestMapping
    public String sistemaContabilitaGiornoMercato(@RequestParam("mercati.id.codice") Integer codiceMercato,
	    @RequestParam("mercatouso.id.codice") Integer mercatoUsoId, @RequestParam("giornoMercato") Date giornoMercato,
	    @ModelAttribute("mercatipresenzeT") MercatipresenzeT mercatipresenzeT, BindingResult result, HttpServletRequest request) {

	// §§§BEGIN§§§
	// aggiorno con l'operatore segnato
	PkId idMercato = new PkId(codiceMercato);
	Mercati mercato = mercatiService.findById(idMercato);
	PkId idUso = new PkId(mercatoUsoId);
	MercatiUso mercatiUso = mercatiUsoService.findById(idUso);
	Calendar giorno = Calendar.getInstance();
	giorno.setTime(giornoMercato);
	MercatipresenzeT mercatipresenzeTFromDataBase = mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(giorno, mercato,
		mercatiUso);
	List<MercatipresenzeDDTO> spuntistiConPosteggio = mercatipresenzeDService.findListaPosteggi(mercatipresenzeT);
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	Responsabili responsabile = new Responsabili();
	responsabile.getId().setCodice(user.getCodiceResponsabile());
	responsabile.setResponsabile(user.getResponsabile());
	try {
	    registrazioniService.sistemaContabilitaGiornoMercato(mercatipresenzeTFromDataBase, spuntistiConPosteggio, responsabile);
	} catch (Exception e) {
	    // FIXME modificare con try - catch sul service
	    copyErrorsToBindingResult(result, mercatipresenzeT, e);
	}
	PkId idResp = new PkId();
	idResp.setCodice(mercatipresenzeT.getResponsabile().getId().getCodice());
	Responsabili operatore = responsabiliService.findById(idResp);
	mercatipresenzeTFromDataBase.setResponsabile(operatore);
	mercatipresenzeTFromDataBase.setFlagRegfatte(true);
	mercatipresenzeTService.update(mercatipresenzeTFromDataBase);
	SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String giornoMercatoStr = sdf.format(giornoMercato);
	return "redirect:registrazionipresenze.htm?mercati.id.codice=" +
		codiceMercato +
		"&mercatouso.id.codice=" +
		mercatoUsoId +
		"&giornoMercato=" +
		giornoMercatoStr;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void fixMergeEntityProperty(Registrazionimercato entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Registrazionimercato entity) {

	if (entity.getPeriodicita() == null) {
	    entity.setPeriodicita(new Periodicita());
	}
	if (entity.getRegistrazioniCausali() == null) {
	    entity.setRegistrazioniCausali(new RegistrazioniCausali());
	}
	if (entity.getTipiScadenza() == null) {
	    entity.setTipiScadenza(new TipiScadenza());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    private boolean validate(Registrazionimercato entity, BindingResult result, Mercati mercati) {

	boolean success = true;
	if (entity.getAnno() == null) {
	    result.rejectValue("anno", "validator.nonvuoto");
	    success = false;
	}
	if (entity.getDataRegistrazione() == null) {
	    result.rejectValue("dataRegistrazione", "validator.nonvuoto");
	    success = false;
	} else {
	    Calendar cal = GregorianCalendar.getInstance();
	    cal.setTime(entity.getDataRegistrazione());
	    int anno = cal.get(Calendar.YEAR);
	    if (entity.getAnno() != null) {
		if (anno != entity.getAnno()) {
		    result.rejectValue("dataRegistrazione", "errors.date.years.must.be.equal");
		    success = false;
		}
	    }
	}
	if (entity.getRegistrazioniCausali().getId().getCodice() == null) {
	    result.rejectValue("registrazioniCausali", "validator.nonvuoto");
	    success = false;
	}
	if (entity.getMercatiUso().getId().getCodice() == null) {
	    result.rejectValue("mercatiUso", "validator.nonvuoto");
	    success = false;
	}
	return success;
    }
}
