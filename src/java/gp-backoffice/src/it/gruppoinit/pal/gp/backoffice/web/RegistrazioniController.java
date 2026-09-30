/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.ContoInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PagamentoUtenze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Posteggio;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.domain.RateNonpagateFilter;
import it.gruppoinit.pal.gp.core.domain.Registrazioni;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;
import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.VwRegistrazionidebiti;
import it.gruppoinit.pal.gp.core.domain.helper.RataHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RegistrazioniDataComparator;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniCommand;
import it.gruppoinit.pal.gp.core.domain.web.RegistrazioniInOutCommand;
import it.gruppoinit.pal.gp.core.domain.web.TransazioniHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.jmesa.RegistrazioniTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.RangeRateizzazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniCausaliService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniImportiService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniInOutService;
import it.gruppoinit.pal.gp.core.service.RegistrazioniService;
import it.gruppoinit.pal.gp.core.service.RegistrazionimercatoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiScadenzaService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VwConcessioniattiveService;
import it.gruppoinit.pal.gp.core.service.VwConcessionilistaService;
import it.gruppoinit.pal.gp.core.service.VwRegistrazionidebitiService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

/**
 * @author francescop
 * 
 */
@Controller
@SessionAttributes(value = { "registrazioni", "registrazioniImporti", "registrazioniFilter", "pagamentoUtenze", "registrazioniInOutCommand",
	"vwregistrazionidebiti", "ratenonpagatefilter" })
public class RegistrazioniController extends BaseController<Registrazioni> {

    private static final String CONTABILITA_VISUALIZZA_SCHEDE = "CONTABILITA_VISUALIZZA_SCHEDE";
    private static final String REGISTRAZIONI_FILTER_IN_SESSION = "_REGISTRAZIONI_FILTER_IN_SESSION_";
    private static final String REGISTRAZIONI_CODICI_IN_SESSION = "_REGISTRAZIONI_CODICI_IN_SESSION_";
    private static final Logger log = LoggerFactory.getLogger(RegistrazioniController.class);
    @Autowired
    private RegistrazioniService registrazioniService;
    @Autowired
    private RegistrazionimercatoService registrazionimercatoService;
    @Autowired
    private RegistrazioniCausaliService registrazioniCausaliService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private RegistrazioniImportiService registrazioniImportiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private RangeRateizzazioniService rangeRateizzazioniService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private VwConcessioniattiveService vwConcessioniattiveService;
    @Autowired
    private VwConcessionilistaService vwConcessionilistaService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private VwRegistrazionidebitiService vwRegistrazionidebitiService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;
    @Autowired
    private TipiScadenzaService tipiScadenzaService;
    @Autowired
    private TipimodalitapagamentoService tipimodalitapagamentoService;
    @Autowired
    private RegistrazioniInOutService registrazioniInOutService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<Registrazioni> registrazioniList = registrazioniService.findAll(null, null);
	ModelMap model = new ModelMap(registrazioniList);
	boolean export = createJMesaExport(request, response, registrazioniList);
	if (export) {
	    return null;
	}
	model.addAttribute("list", true);
	model.addAttribute("registrazioniList", registrazioniList);
	return model;
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result,
	    SessionStatus status) {

	Registrazioni entity = registrazioni.getEntity();
	Integer codice = entity.getId().getCodice();
	entity = registrazioniService.findById(entity.getId());
	try {
	    registrazioniService.delete(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioni.getEntity(), true, e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", codice.toString());
	    model.addAttribute("commandName", "registrazioni");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	return "redirect:/history/back.htm?" + WebConstants.GOTO + "=%2F";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result,
	    SessionStatus status) {

	Registrazioni entity = registrazioni.getEntity();
	Software software = softwareService.findById(ORMHelper.getSoftware());
	entity.setSoftware(software);
	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (registrazioni.getEntity().getResponsabili().getId() != null && registrazioni.getEntity().getResponsabili().getId().getCodice() != null) {
	    Integer codice = registrazioni.getEntity().getResponsabili().getId().getCodice();
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    registrazioni.getEntity().setResponsabili(responsabili);
	}
	fixMergeEntityProperty(registrazioni.getEntity());
	try {
	    registrazioniService.insert(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioni.getEntity(), true, e);
	    fixRenderEntityProperty(registrazioni.getEntity());
	    setPageAttributes(model);
	    return "registrazioni/formView";
	}
	// status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String update(@ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (!request.getParameter("entity.responsabili.id.codice").equals("")) {
	    Integer codice = Integer.parseInt(request.getParameter("entity.responsabili.id.codice"));
	    PkId codiceId = new PkId(codice);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    registrazioni.getEntity().setResponsabili(responsabili);
	}
	Registrazioni entity = registrazioni.getEntity();
	try {
	    fixMergeEntityProperty(registrazioni.getEntity());
	    registrazioniService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioni.getEntity(), e);
	    fixRenderEntityProperty(registrazioni.getEntity());
	    setPageAttributes(model);
	    return "registrazioni/formView";
	}
	// status.setComplete();
	return "redirect:view.htm?codice=" + entity.getId().getCodice() + "&status_msg=02";
    }

    /*
     * Metodo per l'inserimento di una RegistrazioniImporti nel form di Registrazioni
     */
    @RequestMapping
    public String insertImporto(Model model, @ModelAttribute("registrazioniImporti") RegistrazioniImporti registrazioniImporti, BindingResult result,
	    SessionStatus status) {

	try {
	    fixMergeRegistrazioniImporti(registrazioniImporti);
	    registrazioniImportiService.insert(registrazioniImporti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, new Registrazioni(), e);
	    fixRenderRegistrazioniImporti(registrazioniImporti);
	    return "registrazioni/formRegistrazioniImporti";
	}
	return "redirect:view.htm?codice=" + registrazioniImporti.getRegistrazioni().getId().getCodice();
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Registrazioni registrazione = registrazioniService.findById(id);
	if (registrazione.getMercatiD() != null) {
	    if (registrazione.getMercatiD().getId() != null) {
		MercatiD mercatid = mercatiDService.findById(new PkId(registrazione.getMercatiD().getId().getCodice()));
		MercatiUso uso = mercatiUsoService.findById(new PkId(registrazione.getMercatiUso().getId().getCodice()));
		// Datermino le concessioni legate alla registrazione
		VwConcessionilista vwConcessionilista = new VwConcessionilista();
		vwConcessionilista.setConcIdmercatiuso(uso.getId().getCodice());
		vwConcessionilista.setConcIdmercato(mercatid.getMercati().getId().getCodice());
		vwConcessionilista.setConcIdposteggio(mercatid.getId().getCodice());
		if (registrazione.getAnagrafe() != null) {
		    if (registrazione.getAnagrafe().getId() != null) {
			vwConcessionilista.setIstCodicerichiedente(registrazione.getAnagrafe().getId().getCodice());
		    }
		}
		// filtro per mercato, uso e posteggio
		List<VwConcessionilista> concessioniList = vwConcessionilistaService.findConcessioniCriteria(vwConcessionilista);
		model.addAttribute("concessioniList", concessioniList);
	    }
	}
	RegistrazioniCommand registrazioni = new RegistrazioniCommand();
	if ((null == request.getParameter("displayMode") || request.getParameter("displayMode").equals(""))) {
	    registrazioni.setDisplayMode(RegistrazioniCommand.VIEW);
	} else {
	    registrazioni.setDisplayMode((new Integer(request.getParameter("displayMode"))).intValue());
	}
	registrazioni.setEntity(registrazione);
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	fixRenderEntityProperty(registrazioni.getEntity());
	model.addAttribute("registrazioni", registrazioni);
	request.setAttribute("codice", codice);
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REGISTRAZIONI_SHOW_INFO);
	Configurazioneutente showHideConcessioniConf = configurazioneutenteService.findById(confUteId);
	String showHideValoreDefault = "1";
	if (showHideConcessioniConf == null) {
	    // .. inserisco i valori di default
	    showHideConcessioniConf = new Configurazioneutente();
	    showHideConcessioniConf.setId(confUteId);
	    showHideConcessioniConf.setResponsabile(responsabile);
	    showHideConcessioniConf.setValore(showHideValoreDefault);
	    configurazioneutenteService.insert(showHideConcessioniConf);
	} else {
	    showHideValoreDefault = showHideConcessioniConf.getValore();
	}
	request.setAttribute(WebConstants.CONF_UTENTE_REGISTRAZIONI_SHOW_INFO, showHideValoreDefault);
	// gestione della visualizzazione raggruppata
	// recupero la configurazione per l'utente loggato
	ConfigurazioneutenteId confUteRateId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REGISTRAZIONI_RATE_GROUPED);
	Configurazioneutente showRateRaggruppate = configurazioneutenteService.findById(confUteRateId);
	// il valore di default deve essere false (visualizzazione non raggruppata)
	String showRateValoreDefault = "0";
	if (showRateRaggruppate == null) {
	    // .. inserisco i valori di default
	    showRateRaggruppate = new Configurazioneutente();
	    showRateRaggruppate.setId(confUteId);
	    showRateRaggruppate.setResponsabile(responsabile);
	    showRateRaggruppate.setValore(showRateValoreDefault);
	    configurazioneutenteService.insert(showRateRaggruppate);
	} else {
	    showRateValoreDefault = showRateRaggruppate.getValore();
	}
	// metto in request il valore boolean ( utilizzato per recuperare il valore da inserire nella ceckbox)
	request.setAttribute(WebConstants.CONF_UTENTE_REGISTRAZIONI_RATE_GROUPED, showRateValoreDefault);
	model.addAttribute("configurazionerate", showRateValoreDefault);
	return "registrazioni/formView";
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request) {

	Registrazioni entity = new Registrazioni();
	String codiceanagrafe = (String) request.getParameter("entity.anagrafe.id.codice");
	if (!(null == codiceanagrafe || codiceanagrafe.equals("0"))) {
	    PkId richiedenteID = new PkId(new Integer(codiceanagrafe));
	    Anagrafe richiedente = anagrafeService.findById(richiedenteID);
	    entity.setAnagrafe(richiedente);
	}
	Responsabili responsabili = new Responsabili();
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	entity.setResponsabili(responsabili);
	entity.setResponsabiliSistema(responsabili);
	RegistrazioniCommand registrazioni = new RegistrazioniCommand();
	registrazioni.setEntity(entity);
	registrazioni.setDisplayMode(RegistrazioniCommand.NEW);
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("registrazioni", registrazioni);
	return "registrazioni/formView";
    }

    @RequestMapping
    public String createStep1(Model model, HttpServletRequest request) {

	Registrazioni entity = new Registrazioni();
	RegistrazioniCommand registrazioni = new RegistrazioniCommand();
	registrazioni.setEntity(entity);
	registrazioni.setDisplayMode(RegistrazioniCommand.NEW);
	model.addAttribute("registrazioni", registrazioni);
	return "registrazioni/createStep1";
    }

    @RequestMapping
    public String createStepPosteggi(Model model, HttpServletRequest request) {

	Registrazioni entity = new Registrazioni();
	Responsabili responsabili = new Responsabili();
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	entity.setResponsabili(responsabili);
	entity.setResponsabiliSistema(responsabili);
	RegistrazioniCommand registrazioni = new RegistrazioniCommand();
	registrazioni.setEntity(entity);
	registrazioni.setDisplayMode(RegistrazioniCommand.NEW);
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	Calendar c = Calendar.getInstance();
	registrazioniFilter.setAnno((short) (c.get(Calendar.YEAR)));
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("registrazioni", registrazioni);
	List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	return "registrazioni/createStepPosteggi";
    }

    @RequestMapping
    public String insertStepPosteggi(@ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	String gennaio = request.getParameter("gennaio");
	String febbraio = request.getParameter("febbraio");
	String marzo = request.getParameter("marzo");
	String aprile = request.getParameter("aprile");
	String maggio = request.getParameter("maggio");
	String giugno = request.getParameter("giugno");
	String luglio = request.getParameter("luglio");
	String agosto = request.getParameter("agosto");
	String settembre = request.getParameter("settembre");
	String ottobre = request.getParameter("ottobre");
	String novembre = request.getParameter("novembre");
	String dicembre = request.getParameter("dicembre");
	String scadenzarate = request.getParameter("scadenzarate");
	List<ChiaveValoreBean<Integer, BigDecimal>> meseImportis = new ArrayList<ChiaveValoreBean<Integer, BigDecimal>>();
	meseImportis.add(getCvb(1, gennaio));
	meseImportis.add(getCvb(2, febbraio));
	meseImportis.add(getCvb(3, marzo));
	meseImportis.add(getCvb(4, aprile));
	meseImportis.add(getCvb(5, maggio));
	meseImportis.add(getCvb(6, giugno));
	meseImportis.add(getCvb(7, luglio));
	meseImportis.add(getCvb(8, agosto));
	meseImportis.add(getCvb(9, settembre));
	meseImportis.add(getCvb(10, ottobre));
	meseImportis.add(getCvb(11, novembre));
	meseImportis.add(getCvb(12, dicembre));
	TipiScadenza ts = tipiScadenzaService.findById(Integer.parseInt(scadenzarate));
	Mercati mercato = registrazioniFilter.getMercati();
	MercatiD posteggio = registrazioniFilter.getPosteggio();
	MercatiUso uso = registrazioniFilter.getMercatiUso();
	Anagrafe a = registrazioniFilter.getAnagrafe();
	RegistrazioniCausali rc = registrazioniFilter.getRegistrazioniCausali();
	Integer anno = (int) registrazioniFilter.getAnno();
	String codConto = request.getParameter("codiceConto");
	Integer codiceConto = Integer.parseInt(codConto);
	Conti conto = contiService.findById(new PkId(codiceConto));
	try {
	    Registrazioni r = registrazionimercatoService.registraCostoPosteggioDaWizard(mercato, uso, posteggio, a, rc, meseImportis, anno, conto,
		    ts);
	    return "redirect:view.htm?codice=" + r.getId().getCodice();
	} catch (Exception e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	}
	return "redirect:createStepPosteggi.htm?status_msg=03";
    }

    private ChiaveValoreBean<Integer, BigDecimal> getCvb(Integer chiave, String valoreDecimalString) {

	BigDecimal valore = BigDecimal.ZERO;
	ChiaveValoreBean<Integer, BigDecimal> cvb = new ChiaveValoreBean<Integer, BigDecimal>();
	cvb.setChiave(chiave);
	try {
	    valore = new BigDecimal(valoreDecimalString).setScale(2, BigDecimal.ROUND_HALF_UP);
	} catch (Exception e) {
	}
	cvb.setValore(valore);
	return cvb;
    }

    @RequestMapping
    public String createImporto(@RequestParam("registrazionecodice") Integer registrazionecodice, Model model) {

	RegistrazioniImporti registrazioniImporti = new RegistrazioniImporti();
	PkId id = new PkId(registrazionecodice);
	Registrazioni registrazioni = registrazioniService.findById(id);
	registrazioniImporti.setRegistrazioni(registrazioni);
	Set<RegistrazioniImporti> importis = registrazioni.getRegistrazioniImportis();
	Date scadenza = new Date();
	int paragone = 0;
	int numeroRataRegistrazione = 1;
	for (RegistrazioniImporti registrazioniImporti2 : importis) {
	    if (registrazioniImporti2.getNrRata() == null) {
		numeroRataRegistrazione = 1;
	    } else {
		numeroRataRegistrazione = registrazioniImporti2.getNrRata().intValue();
	    }
	    if (paragone < numeroRataRegistrazione) {
		paragone = numeroRataRegistrazione;
		scadenza = registrazioniImporti2.getScadenza();
	    }
	}
	paragone += 1;
	// registrazioniImporti.setIva(WebConstants.CONST_IVA);
	registrazioniImporti.setNrRata(paragone);
	registrazioniImporti.setScadenza(scadenza);
	model.addAttribute("registrazioniImporti", registrazioniImporti);
	return "registrazioni/formRegistrazioniImporti";
    }

    /*
     * View dell'oggetto Registrazioneimporti
     */
    @RequestMapping
    public String viewRegistrazioneimporti(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(id);
	model.addAttribute("registrazioniImporti", registrazioniImporti);
	return "registrazioni/formRegistrazioniImporti";
    }

    /*
     * Delete dell'oggetto Registrazioneimporti
     */
    @RequestMapping
    public String deleteRegistrazioniImporti(@RequestParam("codiceImporto") Integer codiceImporto, Model model,
	    @ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result, SessionStatus status) {

	// elimino Registrazioni Importi e aggiorno i campi importo in
	// Registrazioni e Conti Progressivi Anno
	PkId codiceRigaImporto = new PkId(codiceImporto);
	RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(codiceRigaImporto);
	try {
	    registrazioniImportiService.delete(registrazioniImporti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioni.getEntity(), e);
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("codice", registrazioni.getEntity().getId().getCodice().toString());
	    model.addAttribute("commandName", "registrazioni");
	    model.addAttribute("method", "view.htm");
	    model.addAttribute("queryStringParams", map);
	    return "error";
	}
	return "redirect:view.htm?codice=" + registrazioni.getEntity().getId().getCodice();
    }

    /*
     * Update dell'oggetto Registrazioneimporti
     */
    @RequestMapping
    public String updateRegistrazioniImporti(@ModelAttribute("registrazioniImporti") RegistrazioniImporti registrazioniImporti, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeRegistrazioniImporti(registrazioniImporti);
	try {
	    registrazioniImportiService.update(registrazioniImporti);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniImporti, e);
	    fixRenderRegistrazioniImporti(registrazioniImporti);
	    return "registrazioni/formRegistrazioniImporti";
	}
	// status.setComplete();
	return "redirect:viewRegistrazioneimporti.htm?codice=" + registrazioniImporti.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String createSearch(Model model, HttpServletRequest request) {

	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	request.getSession().removeAttribute(REGISTRAZIONI_FILTER_IN_SESSION);
	request.getSession().removeAttribute(REGISTRAZIONI_CODICI_IN_SESSION);
	//
	VwConcessionilista filter = vwConcessionilistaService.populateFilterCessateUltimoMese();
	int concessioniCessate = vwConcessionilistaService.countByFilter(filter);
	request.setAttribute("concessioni_cessate", Integer.valueOf(concessioniCessate));
	VwConcessionilista filter2 = vwConcessionilistaService.populateFilterSubentriUltimoMese();
	int concessioniSubentrate = vwConcessionilistaService.countByFilter(filter2);
	request.setAttribute("concessioni_subentrate", Integer.valueOf(concessioniSubentrate));
	VwConcessionilista filter3 = vwConcessionilistaService.populateFilterRilasciUltimoMese();
	int concessioniRilasciate = vwConcessionilistaService.countByFilter(filter3);
	request.setAttribute("concessioni_rilasciate", Integer.valueOf(concessioniRilasciate));
	// String valore = leggiParametroConfigurazioneUtente(CONTABILITA_VISUALIZZA_SCHEDE, "1", request);
	gestisciParametroConfigurazioneUtente(CONTABILITA_VISUALIZZA_SCHEDE, "1", request);
	// boolean isVisualizzaScheda = StringUtils.defaultString(valore).equals("1") ? true : false;
	// registrazioniFilter.setFlgVisualizzaScheda(Boolean.valueOf(isVisualizzaScheda));
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	return "registrazioni/registrazioneSearch";
    }

    @RequestMapping
    public String search(@ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result, SessionStatus status,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (request.getSession().getAttribute(REGISTRAZIONI_FILTER_IN_SESSION) != null) {
	    registrazioniFilter = (RegistrazioniFilter) request.getSession().getAttribute(REGISTRAZIONI_FILTER_IN_SESSION);
	} else {
	    request.getSession().setAttribute(REGISTRAZIONI_FILTER_IN_SESSION, registrazioniFilter);
	}
	String valore = leggiParametroConfigurazioneUtente(CONTABILITA_VISUALIZZA_SCHEDE, "1", request);
	boolean isVisualizzaScheda = StringUtils.defaultString(valore).equals("1") ? true : false;
	if (isVisualizzaScheda) {
	    request.getSession().removeAttribute(REGISTRAZIONI_CODICI_IN_SESSION);
	    return searchAndView(null, registrazioniFilter, model, request, response);
	}
	GenerateTable<Registrazioni> istanzeTable = new RegistrazioniTable(registrazioniFilter, registrazioniService, responsabiliService,
		configurazioneutenteService, userSecurityService);
	String htmlTable = istanzeTable.createJMesaList(request, response, "label.lista_istanze", "istanze_id", true);
	//String htmlTable = createJMesaListIstanze(request, response, istanzeList, false);
	if (htmlTable == null) {
	    return null;
	}
	// setListPageAttributes(model, request);
	model.addAttribute("htmltable", htmlTable);
	if (registrazioniFilter.getDescrizione() != null && registrazioniFilter.getDescrizione().equals("%")) {
	    registrazioniFilter.setDescrizione("");
	}
	if (registrazioniFilter.getProgressivo() != null && registrazioniFilter.getProgressivo().equals("%")) {
	    registrazioniFilter.setProgressivo("");
	}
	//model.addAttribute("registrazioniList", registrazioniList);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniInOutCommand", new RegistrazioniInOutCommand());
	// status.setComplete();
	return "registrazioni/list";
    }

    @RequestMapping
    public String searchAndView(@RequestParam(required = false, value = "codice") Integer codiceRegistrazione,
	    @ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	model.addAttribute("registrazioniInOutCommand", new RegistrazioniInOutCommand());
	if (codiceRegistrazione != null) {
	    return view(codiceRegistrazione, model, request);
	}
	List<Integer> codicis = new ArrayList<Integer>();
	if (request.getSession().getAttribute(REGISTRAZIONI_CODICI_IN_SESSION) == null) {
	    codicis = registrazioniService.findCodiciByRegistrazioniFilter(registrazioniFilter);
	    request.getSession().setAttribute(REGISTRAZIONI_CODICI_IN_SESSION, codicis);
	} else {
	    codicis = (List<Integer>) request.getSession().getAttribute(REGISTRAZIONI_CODICI_IN_SESSION);
	}
	if (codicis.size() > 0) {
	    response.sendRedirect("../registrazioni/view.htm?codice=" + codicis.get(0));
	    return null;// view(codicis.get(0), model, request);
	} else {
	    throw new RuntimeException("Nessun risultato soddisfa i criteri di ricerca");
	}
    }

    @RequestMapping
    public String ajaxPannelloRisultati(@RequestParam(required = true, value = "codice") Integer codiceRegistrazione, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<Integer> codicis = new ArrayList<Integer>();
	codicis = (List<Integer>) request.getSession().getAttribute(REGISTRAZIONI_CODICI_IN_SESSION);
	if (codicis != null && codicis.size() > 0) {
	    model.addAttribute("numRisultati", codicis.size());
	    int i = 0;
	    int prev = 0;
	    int next = 0;
	    for (Integer cod : codicis) {
		if (codiceRegistrazione.equals(cod)) {
		    next = i + 1;
		    model.addAttribute("current", next);
		    prev = i - 1;
		    if (next <= (codicis.size() - 1)) {
			model.addAttribute("next", codicis.get(next));
		    } else {
			model.addAttribute("next", -1);
		    }
		    if (prev >= 0) {
			model.addAttribute("prev", codicis.get(prev));
		    } else {
			model.addAttribute("prev", -1);
		    }
		    break;
		}
		i++;
	    }
	    return "registrazioni/ajaxpannello";
	} else {
	    return "includes/blank";
	}
    }

    @RequestMapping
    public void ajaxRecuperaAnagrafePosteggio(@RequestParam(required = true, value = "mercato") Integer mercato,
	    @RequestParam(required = true, value = "uso") Integer uso, @RequestParam(required = true, value = "posteggio") Integer posteggio,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	List<VwConcessioniattive> concs = vwConcessioniattiveService.findByMercatoMercatoUsoAndPosteggio(mercato, uso, posteggio);
	if (concs.size() == 0) {
	    //response.setStatus(500);
	    response.getWriter().write("Per il posteggio non esistono concessioni attive");
	    return;
	}
	if (concs.size() > 1) {
	    //response.setStatus(500);
	    response.getWriter().write("Per il posteggio esistono più concessioni attive");
	    return;
	}
	VwConcessioniattive ca = concs.get(0);
	Anagrafe result = ca.getOccupante();
	if (result == null) {
	    result = ca.getTitolare();
	}
	if (result == null) {
	    //response.setStatus(500);
	    String message = "La concessione numero " + ca.getAutorizzazione().getAutoriznumero() + " non ha definito un titolare/occupante";
	    response.getWriter().write(message);
	    return;
	}
	response.setContentType("text/plain");
	String resultS = result.getId().getCodice().intValue() + "|" + result.getDescrizioneRichiedente();
	response.getWriter().write(resultS);
    }

    @RequestMapping
    public void ajaxRecuperaCostoPosteggio(@RequestParam(required = true, value = "anno") Integer anno,
	    @RequestParam(required = true, value = "mercato") Integer mercato, @RequestParam(required = true, value = "uso") Integer uso,
	    @RequestParam(required = true, value = "posteggio") Integer posteggio, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	throw new NotImplementedException("Funzionalità dismessa");
	//	MercatiD posteggioO = mercatiDService.findById(new PkId(posteggio));
	//	PosteggioImportoHelper posteggioImportoHelper = mercatiDService.calcolaCostoPosteggioAnnuale(posteggioO, anno,
	//		WebConstants.MERCATO_CONTESTO_CONCESSIONARI);
	//	// BigDecimal importo = posteggioImportoHelper.getImporto();
	//	List<RigaImporto> ri = posteggioImportoHelper.getListaImporti();
	//	String result = "";
	//	for (RigaImporto rigaImporto : ri) {
	//	    result += rigaImporto.getConto().getDescrizioneConto() + "|" + rigaImporto.getImporto().doubleValue() + "|" + rigaImporto.getIva() + "|"
	//		    + rigaImporto.getConto().getId().getCodice().intValue() + "#";
	//	}
	//	response.setContentType("text/plain");
	//	// String resultS = "1|" + importo.doubleValue(); //result.getId().getCodice().intValue() + "|" + result.getDescrizioneRichiedente();
	//	response.getWriter().write(result);
    }

    @RequestMapping
    public String ajaxPannelloPagamento(@RequestParam(required = true, value = "codice") Integer codiceRegistrazione,
	    @RequestParam(required = true, value = "nrRata") Integer nrRata, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	Registrazioni reg = registrazioniService.findById(new PkId(codiceRegistrazione));
	List<RataHelper> rate = reg.getListaRate();
	BigDecimal importo = BigDecimal.ZERO;
	for (RataHelper rh : rate) {
	    if (rh.getNumeroRata().equals(nrRata)) {
		importo = rh.getRimanenza();
		break;
	    }
	}
	model.addAttribute("rimanenza", importo);
	model.addAttribute("tipimodalitapagamentoList", tipimodalitapagamentoService.findAll(null, null, false));
	Date datadistinta = getDataDistinta();
	model.addAttribute("datadistinta", datadistinta);
	model.addAttribute("modPag", getTipimodalitapagamento());
	Date dataIncassoDate = getDataIncasso();
	model.addAttribute("dataincasso", dataIncassoDate);
	return "registrazioni/ajaxPannelloPagamento";
    }

    @RequestMapping
    public String ajaxPannelloAdeguaIVA(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	return "registrazioni/ajaxPannelloAdeguaIVA";
    }

    @RequestMapping
    public String updateAggiornaIVA(@RequestParam(required = true, value = "valoreIva") BigDecimal valoreIva,
	    @RequestParam(required = false, value = "data") Date data, HttpServletRequest request, Model model) {

	// §§§BEGIN§§§
	String exitCode = "02";
	try {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    if (valoreIva.compareTo(BigDecimal.ZERO) > 0) {
		registrazioniService.updateAggiornaIVA(valoreIva, null, software);
		LoggerCancellazioni.logRegistrazioniAdeguamentoIva(getCurrentlyAuthenticatedUserDetails().toString(), String.valueOf(valoreIva),
			ORMHelper.getSoftware());
	    }
	} catch (Exception e) {
	    log.error("Errore nella procedura di aggiornamento IVA . Dettaglio errore:{}", e);
	    FlashMessages.getWarnings().add("Errore nella procedura di aggiornamento IVA . Dettaglio errore: " + e.getMessage());
	    exitCode = "03";
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	return "redirect:createSearch.htm?software=" + ORMHelper.getSoftware() + "&status_msg=" + exitCode;
    }

    @RequestMapping
    public String insertEffettuaPagamento(@RequestParam(required = true, value = "codice") Integer codiceRegistrazione,
	    @RequestParam(required = true, value = "nrRata") Integer nrRata,
	    @RequestParam(required = true, value = "tipimodalitapagamento") Integer tipimodalitapagamentoId,
	    @RequestParam(required = true, value = "importo") BigDecimal importo, HttpServletRequest request, Model model) {

	// §§§BEGIN§§§
	// aggiorna o inserisce la data distinta nella tabella configurazione utente
	// quando creo un nuovo incasso
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	Configurazioneutente tabConf = configurazioneutenteService.findById(dataDistintaConfId);
	String dataDistintaString = request.getParameter("dataDistinta");
	if (tabConf == null) {
	    // .. inserisco i valori di default
	    tabConf = new Configurazioneutente();
	    tabConf.setId(dataDistintaConfId);
	    tabConf.setResponsabile(responsabile);
	    tabConf.setValore(dataDistintaString);
	    configurazioneutenteService.insert(tabConf);
	} else {
	    tabConf.setValore(dataDistintaString);
	    configurazioneutenteService.update(tabConf);
	}
	// /INSERIMENTO Modalità Pagamento nella configurazione utente
	ConfigurazioneutenteId modalitaPagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	Configurazioneutente modalitaPagamentoTabConf = configurazioneutenteService.findById(modalitaPagamentoConfId);
	if (modalitaPagamentoTabConf == null) {
	    // .. inserisco i valori di default
	    modalitaPagamentoTabConf = new Configurazioneutente();
	    modalitaPagamentoTabConf.setId(modalitaPagamentoConfId);
	    modalitaPagamentoTabConf.setResponsabile(responsabile);
	    modalitaPagamentoTabConf.setValore(String.valueOf(tipimodalitapagamentoId));
	    configurazioneutenteService.insert(modalitaPagamentoTabConf);
	} else {
	    modalitaPagamentoTabConf.setValore(String.valueOf(tipimodalitapagamentoId));
	    configurazioneutenteService.update(modalitaPagamentoTabConf);
	}
	// /INSERIMENTO Data Incasso nella configurazione utente
	ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	Configurazioneutente dataIncassoTabConf = configurazioneutenteService.findById(dataIncassoConfId);
	String dataIncassoString = request.getParameter("dataIncasso");
	if (dataIncassoTabConf == null) {
	    // .. inserisco i valori di default
	    dataIncassoTabConf = new Configurazioneutente();
	    dataIncassoTabConf.setId(dataIncassoConfId);
	    dataIncassoTabConf.setResponsabile(responsabile);
	    dataIncassoTabConf.setValore(dataIncassoString);
	    configurazioneutenteService.insert(dataIncassoTabConf);
	} else {
	    dataIncassoTabConf.setValore(dataIncassoString);
	    configurazioneutenteService.update(dataIncassoTabConf);
	}
	Software software = softwareService.findById(ORMHelper.getSoftware());
	String note = request.getParameter("note");
	String riferimentiPagamento = request.getParameter("riferimentiPagamento");
	// fixMergeEntityProperty(registrazioniInOut);
	String exitCode = "02";
	try {
	    Date dataDistinta = null;
	    Date dataIncasso = null;
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    if (StringUtils.isNotBlank(dataDistintaString)) {
		dataDistinta = sdf.parse(dataDistintaString);
	    }
	    if (StringUtils.isNotBlank(dataIncassoString)) {
		dataIncasso = sdf.parse(dataIncassoString);
	    }
	    registrazioniInOutService.insertPagamentoRata(software, importo, responsabile, codiceRegistrazione, nrRata, dataDistinta, dataIncasso,
		    tipimodalitapagamentoId, riferimentiPagamento, note);
	} catch (Exception e) {
	    log.error("Errore nella registrazione del pagamento della rata #{}. Dettaglio:{}", nrRata, e);
	    FlashMessages.getWarnings().add("Errore nella registrazione del pagamento della rata #" + nrRata + ". Dettaglio:" + e.getMessage());
	    exitCode = "03";
	}
	// VISUALIZZAZIONE DETTAGLI : INSERIMENTO IN CONFIGURAZIONE UTENTE
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
	return "redirect:view.htm?codice=" + codiceRegistrazione + "&status_msg=" + exitCode;
    }

    private Date getDataDistinta() {

	// §§§BEGIN§§§	
	Date dataDistintaDate = GregorianCalendar.getInstance().getTime();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataDistintaConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATADISTINTA);
	Configurazioneutente dataDistintaConf = configurazioneutenteService.findById(dataDistintaConfId);
	if (dataDistintaConf != null) {
	    String dataDistinta = dataDistintaConf.getValore();
	    if (StringUtils.isNotBlank(dataDistinta)) {
		SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		try {
		    dataDistintaDate = sdf.parse(dataDistinta);
		} catch (ParseException e) {
		}
	    }
	}
	return dataDistintaDate;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Date getDataIncasso() {

	// §§§BEGIN§§§
	Date dataIncassoDate = GregorianCalendar.getInstance().getTime();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId dataIncassoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_DATA_INCASSO);
	Configurazioneutente dataIncassoConf = configurazioneutenteService.findById(dataIncassoConfId);
	if (dataIncassoConf != null) {
	    String dataIncasso = dataIncassoConf.getValore();
	    if (StringUtils.isNotBlank(dataIncasso)) {
		SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
		try {
		    dataIncassoDate = sdf.parse(dataIncasso);
		} catch (ParseException e) {
		}
	    }
	}
	return dataIncassoDate;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private Tipimodalitapagamento getTipimodalitapagamento() {

	// §§§BEGIN§§§
	Tipimodalitapagamento tipimodalitapagamento = new Tipimodalitapagamento();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId modalitapagamentoConfId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_REG_IO_MODALITA_PAGAMENTO);
	Configurazioneutente modalitapagamentoConf = configurazioneutenteService.findById(modalitapagamentoConfId);
	if (modalitapagamentoConf != null) {
	    tipimodalitapagamento = tipimodalitapagamentoService.findById(new PkId(Integer.valueOf(modalitapagamentoConf.getValore())));
	}
	return tipimodalitapagamento;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String adeguaImporti(@RequestParam("adeguamentoPercentuale") BigDecimal adeguamentoPercentuale, Model model,
	    @ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    registrazioniService.adeguaImportiRegistrazioni(registrazioniFilter, adeguamentoPercentuale);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniFilter, e);
	    setPageAttributes(model);
	    model.addAttribute("registrazioniFilter", registrazioniFilter);
	    return "registrazioni/registrazioneSearch";
	}
	status.setComplete();
	return "redirect:createSearch.htm?status_msg=02";
    }

    @RequestMapping
    public String pagamentoUtenzeCreate(@RequestParam("mercati.id.codice") Integer codicemercato,
	    @RequestParam("mercatoUso") Integer codicemercatouso, Model model) {

	// crea l'oggetto che sarà il command ,sarà utilizzato anche per fare il reset voluto della pagina in caso di
	// errore
	PagamentoUtenze pagamentoUtenze = new PagamentoUtenze();
	// estraggo tutti i dati che mi servono da presentare sulla maschera
	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatouso));
	List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findByDescrizioneEscluseRiduzioni("");
	List<RegistrazioniImporti> registrazioniImportiList = new ArrayList<RegistrazioniImporti>();
	List<Posteggio> posteggioList = new ArrayList<Posteggio>();
	List<Conti> contiList = null;
	// setto al mio command i parametri che rimarranno fissi
	pagamentoUtenze.setMercati(mercati);
	pagamentoUtenze.setMercatiUso(mercatiUso);
	pagamentoUtenze.setDataregistrazione(GregorianCalendar.getInstance().getTime());
	pagamentoUtenze.setRegistrazioneImportiList(registrazioniImportiList);
	pagamentoUtenze.setPosteggiList(posteggioList);
	model.addAttribute("pagamentoUtenze", pagamentoUtenze);
	model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	model.addAttribute("contiList", contiList);
	return "registrazioni/pagamentoutenzeposteggi";
    }

    @RequestMapping
    public String pagamentoUtenze(@ModelAttribute("pagamentoUtenze") PagamentoUtenze pagamentoUtenze, Model model) {

	// ricavo l'oggetto registrazini causale dal codice passato con la select e ala setto sul command
	RegistrazioniCausali registrazioniCausali = registrazioniCausaliService
		.findById(new PkId(pagamentoUtenze.getRegistrazioniCausali().getId().getCodice()));
	pagamentoUtenze.setRegistrazioniCausali(registrazioniCausali);
	// tolgo la registrazione causale e al rimetto al primo posto per ripresentarla della pagina come prima nella
	// select
	List<RegistrazioniCausali> registrazionicausaliList = registrazioniCausaliService.findByDescrizioneEscluseRiduzioni("");
	registrazionicausaliList.remove(registrazioniCausali);
	registrazionicausaliList.add(0, registrazioniCausali);
	List<Conti> contiList = contiService.findAll(null, null);
	// Codice che viene eseguito:
	// 1-dalla seconda volta in poi che entriamo in questo controller
	// 2-se veniamo dal controller che cancella un pagamento utenza settato nel command
	if (pagamentoUtenze.getConti().getId().getCodice() != null && pagamentoUtenze.getFlagCancellato() != 1) {
	    RegistrazioniImporti registrazioniImporti = new RegistrazioniImporti();
	    registrazioniImporti.setConti(contiService.findById(new PkId(pagamentoUtenze.getConti().getId().getCodice())));
	    registrazioniImporti.setScadenza(pagamentoUtenze.getDatascadenza());
	    registrazioniImporti.setImporto(pagamentoUtenze.getImporti());
	    pagamentoUtenze.getRegistrazioneImportiList().add(registrazioniImporti);
	} else {
	    pagamentoUtenze.setFlagCancellato(0);
	}
	// calcola la data scadenza come la data di registrazionio passata più un mese e la setto sul command
	Date date = pagamentoUtenze.getDataregistrazione();
	Calendar theDate = GregorianCalendar.getInstance();
	theDate.setTime(date);
	int mese = theDate.get(Calendar.MONTH) + 1;
	int anno = theDate.get(Calendar.YEAR);
	int giorno = theDate.get(Calendar.DATE);
	Calendar scadenza = new GregorianCalendar();
	scadenza.set(anno, mese, giorno);
	pagamentoUtenze.setDatascadenza(scadenza.getTime());
	pagamentoUtenze.setImporti(new BigDecimal(0.0));
	model.addAttribute("pagamentoUtenze", pagamentoUtenze);
	model.addAttribute("registrazionicausaliList", registrazionicausaliList);
	model.addAttribute("contiList", contiList);
	return "registrazioni/pagamentoutenzeposteggi";
    }

    @RequestMapping
    public String deleteImporto(@RequestParam("codice") Integer codice, @ModelAttribute("pagamentoUtenze") PagamentoUtenze pagamentoUtenze,
	    Model model) {

	// cancella dal command l'elemento della lista registrazioniImportiList con il codice passato come parametro
	// estraggo dal command la lista delle registrazioniImporti configurati
	List<RegistrazioniImporti> risultato = pagamentoUtenze.getRegistrazioneImportiList();
	// scelgo quello nella posizione pari al codice passato come parametro nella request
	RegistrazioniImporti registrazioniImporti = risultato.get(codice);
	// lo rimuovo dalla lista
	risultato.remove(registrazioniImporti);
	// setto la lista aggiornata al command
	pagamentoUtenze.setRegistrazioneImportiList(risultato);
	// pone il flag = 1 in modo che rientrando sul controller "pagamentoUtenze" non inserisca sul commnad una nuova
	// registrazione
	// non voluta
	pagamentoUtenze.setFlagCancellato(1);
	List<RangeRateizzazioni> rangerateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	model.addAttribute("rangerateizzazioniList", rangerateizzazioniList);
	model.addAttribute("pagamentoUtenze", pagamentoUtenze);
	return "redirect:pagamentoUtenze.htm";
    }

    @RequestMapping
    public String assegnaPagamentiAposteggi(@ModelAttribute("pagamentoUtenze") PagamentoUtenze pagamentoUtenze, Model model) {

	Mercati mercati = mercatiService.findById(new PkId(pagamentoUtenze.getMercati().getId().getCodice()));
	// ricava tutti i posteggi attivi del mercato che stiamo trattando
	List<MercatiD> posteggiList = mercatiDService.findByMercato(mercati, PosteggiEnum.ACTIVE);
	// ricava i tipi di rateizzazione di questo mercato
	List<RangeRateizzazioni> rangerateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	// associa ad ogni posteggio la lista di registrazioni importi configurati sopra
	Posteggio posteggio = null;
	List<RegistrazioniImporti> list = pagamentoUtenze.getRegistrazioneImportiList();
	List<RegistrazioniImporti> importiPosteggioList = null;
	pagamentoUtenze.setPosteggiList(new ArrayList<Posteggio>());
	for (MercatiD mercatiD : posteggiList) {
	    // setto l'anagrafe dalla concessione attiva per ricavare l'anagrafe del posteggio in esame sbagliato
	    VwConcessioniattive concessioneAttiva = vwConcessioniattiveService.findByMercatoUsoPosteggio(mercati.getId().getCodice(),
		    pagamentoUtenze.getMercatiUso().getId().getCodice(), mercatiD.getId().getCodice());
	    // per ogni posteggio con concessione attiva setto le registrazioniImporti configurati
	    if (null != concessioneAttiva) {
		posteggio = new Posteggio();
		posteggio.setPosteggio(mercatiD);
		posteggio.setAnagrafe(concessioneAttiva.getOccupante());
		posteggio.setPosteggio(mercatiD);
		RegistrazioniImporti registrazioniImportiPosteggio = null;
		importiPosteggioList = new ArrayList<RegistrazioniImporti>();
		for (RegistrazioniImporti registrazioniImporti : list) {
		    registrazioniImportiPosteggio = new RegistrazioniImporti();
		    registrazioniImportiPosteggio.setConti(registrazioniImporti.getConti());
		    registrazioniImportiPosteggio.setImporto(registrazioniImporti.getImporto());
		    registrazioniImportiPosteggio.setScadenza(registrazioniImporti.getScadenza());
		    importiPosteggioList.add(registrazioniImportiPosteggio);
		}
		posteggio.setRegistrazioniimportiposteggioList(importiPosteggioList);
		// aggiorno il command pagamentoUtenze con i posteggi aggiornati con le registrazioniImporti
		pagamentoUtenze.getPosteggiList().add(posteggio);
	    }
	}
	List<Conti> contiList = contiService.findAll(null, null);
	model.addAttribute("contiList", contiList);
	model.addAttribute("pagamentoUtenze", pagamentoUtenze);
	model.addAttribute("rangerateizzazioniList", rangerateizzazioniList);
	return "registrazioni/pagamentoutenzeposteggi";
    }

    @RequestMapping
    public String insertPagamentiUtenze(@ModelAttribute("pagamentoUtenze") PagamentoUtenze pagamentoUtenze, BindingResult result, Model model) {

	List<Posteggio> listaposteggi = pagamentoUtenze.getPosteggiList();
	Date dataRegistrazione = pagamentoUtenze.getDataregistrazione();
	MercatiUso mercatiuso = pagamentoUtenze.getMercatiUso();
	Boolean rate = pagamentoUtenze.getRate();
	RegistrazioniCausali registrazioniCausali = pagamentoUtenze.getRegistrazioniCausali();
	try {
	    registrazioniService.insertPagamentiUtente(listaposteggi, dataRegistrazione, mercatiuso, rate, registrazioniCausali);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, pagamentoUtenze, e);
	    List<Conti> contiList = contiService.findAll(null, null);
	    model.addAttribute("contiList", contiList);
	    model.addAttribute("pagamentoUtenze", pagamentoUtenze);
	    // ricava i tipi di rateizzazione di questo mercato
	    List<RangeRateizzazioni> rangerateizzazioniList = rangeRateizzazioniService.findAll(null, null);
	    model.addAttribute("rangerateizzazioniList", rangerateizzazioniList);
	    return "registrazioni/pagamentoutenzeposteggi";
	}
	// Creo il model Registrazione filter per poter usare il controller "search.htm"
	RegistrazioniFilter filter = new RegistrazioniFilter();
	filter.setMercati(mercatiService.findById(new PkId(pagamentoUtenze.getMercati().getId().getCodice())));
	filter.setMercatiUso(mercatiUsoService.findById(new PkId(pagamentoUtenze.getMercatiUso().getId().getCodice())));
	filter.setDataInizio(pagamentoUtenze.getDataregistrazione());
	filter.setRegistrazioniCausali(registrazioniCausali);
	filter.setDataFine(pagamentoUtenze.getDataregistrazione());
	model.addAttribute("registrazioniFilter", filter);
	return "redirect:search.htm";
    }

    @RequestMapping
    public String createRiduzioniAccertamento(@ModelAttribute("registrazioni") RegistrazioniCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request, Model model) {

	Registrazioni regRiduzioneAccertamento = new Registrazioni();
	Responsabili responsabili = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	regRiduzioneAccertamento.setResponsabili(responsabili);
	regRiduzioneAccertamento.setResponsabiliSistema(responsabili);
	regRiduzioneAccertamento.setAnagrafe(command.getEntity().getAnagrafe());
	regRiduzioneAccertamento.setMercatiD(command.getEntity().getMercatiD());
	regRiduzioneAccertamento.setMercatiUso(command.getEntity().getMercatiUso());
	regRiduzioneAccertamento.setSoftware(command.getEntity().getSoftware());
	regRiduzioneAccertamento.setAnno(command.getEntity().getAnno());
	command.setRegRiduzioneAccertamento(regRiduzioneAccertamento);
	command.setDisplayMode(RegistrazioniCommand.NEW);
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByDescrizioneSoloRiduzioni("");
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("command", command);
	return "registrazioni/formRiduzioni";
    }

    @RequestMapping
    public String insertRiduzioni(@ModelAttribute("registrazioni") RegistrazioniCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	Responsabili respSistema = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	respSistema.getId().setCodice(user.getCodiceResponsabile());
	Registrazioni regRiduzioneAccertamento = command.getRegRiduzioneAccertamento();
	Integer codice;
	try {
	    codice = registrazioniService.insertRiduzioniAccertamento(regRiduzioneAccertamento, respSistema, command.getRegImportiSelezionati());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command.getEntity(), true, e);
	    setPageAttributes(model);
	    model.addAttribute("command", command);
	    return "registrazioni/formRiduzioni";
	}
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("command", command);
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
    }

    @RequestMapping
    public String createSearchRateNonPagate(Model model, HttpServletRequest request) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_RATE_NON_PAGATE);
	Configurazioneutente showHideRateConf = configurazioneutenteService.findById(confUteId);
	String currentYear = Integer.toString(GregorianCalendar.getInstance().get(Calendar.YEAR));
	String valoreNonPrensente = "%";
	String rateNonpagateFilterValoreDefault = currentYear +
		"," +
		valoreNonPrensente +
		"," +
		valoreNonPrensente +
		"," +
		valoreNonPrensente +
		"," +
		WebConstants.NUMERO_MINIMO_RATE_NON_PAGATE +
		"," +
		valoreNonPrensente +
		"," +
		valoreNonPrensente;
	RateNonpagateFilter rateNonpagateFilter = null;
	if (showHideRateConf == null) {
	    // .. inserisco i valori di default
	    showHideRateConf = new Configurazioneutente();
	    showHideRateConf.setId(confUteId);
	    showHideRateConf.setResponsabile(responsabile);
	    showHideRateConf.setValore(rateNonpagateFilterValoreDefault);
	    configurazioneutenteService.insert(showHideRateConf);
	    // setto i valori di default se l'utente loggato non ha alcuna configurazione salvata
	    rateNonpagateFilter = new RateNonpagateFilter();
	    rateNonpagateFilter.setAnno(Short.parseShort(currentYear));
	    rateNonpagateFilter.setRateNonPagate(Integer.parseInt(WebConstants.NUMERO_MINIMO_RATE_NON_PAGATE));
	} else {
	    rateNonpagateFilter = getRateNonPagateFilter(showHideRateConf);
	}
	List<VwRegistrazionidebiti> listaRegistrazioniNonPagate = vwRegistrazionidebitiService.findByFilter(rateNonpagateFilter);
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	// serve per mostrare nella select box della ricerca come primo valore quello della configuarazione utente se
	// c'è
	if ((rateNonpagateFilter.getRegistrazioniCausali() != null && rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice() != null)) {
	    for (RegistrazioniCausali registrazioniCausali : registrazioniCausaliList) {
		if (registrazioniCausali.getId().getCodice().intValue() == rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice()
			.intValue()) {
		    RegistrazioniCausali registrazioniCausaliTemp = registrazioniCausaliList.remove(0);
		    registrazioniCausaliList.set(0, registrazioniCausaliTemp);
		    break;
		}
	    }
	}
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("listaRegistrazioniNonPagate", listaRegistrazioniNonPagate);
	model.addAttribute("ratenonpagatefilter", rateNonpagateFilter);
	model.addAttribute("registrazioniInOutCommand", new RegistrazioniInOutCommand());
	//
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "1", request);
	return "registrazioni/searchRateNonPagate";
    }

    @RequestMapping
    public String searchRateNonPagate(@ModelAttribute("ratenonpagatefilter") RateNonpagateFilter rateNonpagateFilter, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	// recupero la configurazione per l'utente loggato
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_RATE_NON_PAGATE);
	Configurazioneutente showHideRateConf = configurazioneutenteService.findById(confUteId);
	// creo la stringa con i valori di configurazione
	String valore = getConfigurazione(rateNonpagateFilter);
	// aggiorno la configurazione dell'utente con i nuovi filtri di ricerca
	showHideRateConf.setValore(valore);
	configurazioneutenteService.update(showHideRateConf);
	List<VwRegistrazionidebiti> listaRegistrazioniNonPagate = vwRegistrazionidebitiService.findByFilter(rateNonpagateFilter);
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	// serve per mostrare nella select box della ricerca come primo valore quello della configuarazione utente se
	// c'è
	if ((rateNonpagateFilter.getRegistrazioniCausali() != null && rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice() != null)) {
	    for (RegistrazioniCausali registrazioniCausali : registrazioniCausaliList) {
		if (registrazioniCausali.getId().getCodice().intValue() == rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice()
			.intValue()) {
		    RegistrazioniCausali registrazioniCausaliTemp = registrazioniCausaliList.remove(0);
		    registrazioniCausaliList.set(0, registrazioniCausaliTemp);
		    break;
		}
	    }
	}
	boolean export = createJMesaExport(request, response, listaRegistrazioniNonPagate);
	if (export)
	    return null;
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("listaRegistrazioniNonPagate", listaRegistrazioniNonPagate);
	model.addAttribute("ratenonpagatefilter", rateNonpagateFilter);
	return "registrazioni/searchRateNonPagate";
    }

    private String getConfigurazione(RateNonpagateFilter rateNonpagateFilter) {

	String valore = "";
	String anno = (rateNonpagateFilter.getAnno() == null ? "%" : String.valueOf((Short) rateNonpagateFilter.getAnno()));
	String idcausale = (rateNonpagateFilter.getRegistrazioniCausali() != null
		&& rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice() != null
			? rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice().toString()
			: "%");
	String idmercato = (rateNonpagateFilter.getMercati() != null && rateNonpagateFilter.getMercati().getId().getCodice() != null
		? rateNonpagateFilter.getMercati().getId().getCodice().toString()
		: "%");
	String idmercatouso = (rateNonpagateFilter.getMercatiUso() != null && rateNonpagateFilter.getMercatiUso().getId().getCodice() != null
		? rateNonpagateFilter.getMercatiUso().getId().getCodice().toString()
		: "%");
	String ratenonpagate = (rateNonpagateFilter.getRateNonPagate() == null ? WebConstants.NUMERO_MINIMO_RATE_NON_PAGATE
		: String.valueOf((Integer) rateNonpagateFilter.getRateNonPagate()));
	String daincassareinf = (rateNonpagateFilter.getImportoDaIncassareInf() == null ? "0"
		: rateNonpagateFilter.getImportoDaIncassareInf().toString());
	String daincassaresup = (rateNonpagateFilter.getImportoDaIncassareSup() == null ? "%"
		: rateNonpagateFilter.getImportoDaIncassareSup().toString());
	valore = anno + "," + idcausale + "," + idmercato + "," + idmercatouso + "," + ratenonpagate + "," + daincassareinf + "," + daincassaresup;
	return valore;
    }

    private RateNonpagateFilter getRateNonPagateFilter(Configurazioneutente configurazioneutente) {

	RateNonpagateFilter rateNonpagateFilter = new RateNonpagateFilter();
	String[] valoriFiltro = getParametriRateNonPagate(configurazioneutente.getValore());
	// setto sul filtro i valori di configurazione salvati nella configurazione utente
	rateNonpagateFilter.setAnno(valoriFiltro[0].equals("%") ? null : Short.parseShort(valoriFiltro[0]));
	rateNonpagateFilter.setRegistrazioniCausali((valoriFiltro[1].equals("%") ? new RegistrazioniCausali()
		: registrazioniCausaliService.findById(new PkId(Integer.parseInt(valoriFiltro[1])))));
	rateNonpagateFilter
		.setMercati((valoriFiltro[2].equals("%") ? new Mercati() : mercatiService.findById(new PkId(Integer.parseInt(valoriFiltro[2])))));
	rateNonpagateFilter.setMercatiUso(
		(valoriFiltro[3].equals("%") ? new MercatiUso() : mercatiUsoService.findById(new PkId(Integer.parseInt(valoriFiltro[3])))));
	rateNonpagateFilter.setRateNonPagate((valoriFiltro[4].equals("%") ? null : Integer.parseInt(valoriFiltro[4])));
	rateNonpagateFilter.setImportoDaIncassareInf((valoriFiltro[5].equals("%") ? null : new BigDecimal(valoriFiltro[5])));
	rateNonpagateFilter.setImportoDaIncassareSup((valoriFiltro[6].equals("%") ? null : new BigDecimal(valoriFiltro[6])));
	return rateNonpagateFilter;
    }

    @RequestMapping
    public String searchTransazioni(@ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	if (registrazioniFilter.getAnagrafe().getId().getCodice() == null) {
	    result.rejectValue("anagrafe", "errors.validator.transazioni.anagrafe.null", "nokey");
	}
	if (null == registrazioniFilter.getSaldo()) {
	    // di default imposto il filtro a > 0,01 centesimi
	    // così escludo dalla lista le registrazioni completamente incassate
	    // e le riduzioni di accertamento
	    registrazioniFilter.setSaldo(new BigDecimal(0.01));
	}
	// TODO PAGINARE
	List<Registrazioni> registrazioniList = registrazioniService.findByRegistrazioniFilter(registrazioniFilter, null, null);
	// if (result.hasErrors()) {
	// return "registrazioni/registrazioneSearch";
	// }
	// ordino le registrazioni per data
	Collections.sort(registrazioniList, new RegistrazioniDataComparator());
	if (registrazioniFilter.getDescrizione() != null && registrazioniFilter.getDescrizione().equals("%")) {
	    registrazioniFilter.setDescrizione("");
	}
	if (registrazioniFilter.getProgressivo() != null && registrazioniFilter.getProgressivo().equals("%")) {
	    registrazioniFilter.setProgressivo("");
	}
	RegistrazioniCommand command = new RegistrazioniCommand();
	Registrazioni entity = command.getEntity();
	Anagrafe richiedente = anagrafeService.findById(new PkId(registrazioniFilter.getAnagrafe().getId().getCodice()));
	entity.setAnagrafe(richiedente);
	if (registrazioniFilter.getPosteggio().getId().getCodice() != null) {
	    MercatiD posteggio = mercatiDService.findById(new PkId(registrazioniFilter.getPosteggio().getId().getCodice()));
	    entity.setMercatiD(posteggio);
	}
	if (registrazioniFilter.getMercatiUso().getId().getCodice() != null) {
	    MercatiUso uso = mercatiUsoService.findById(new PkId(registrazioniFilter.getMercatiUso().getId().getCodice()));
	    entity.setMercatiUso(uso);
	}
	command.setEntity(entity);
	model.addAttribute("registrazioniList", registrazioniList);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("registrazioniInOutCommand", new RegistrazioniInOutCommand());
	model.addAttribute("registrazioni", command);
	return "registrazioni/listTransazioni";
    }

    @RequestMapping
    public String listTransazioniImporti(Model model, @ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result,
	    SessionStatus status) {

	List<Registrazioni> registrazioniList = new ArrayList<Registrazioni>();
	Integer[] registrazioniDaTransare = registrazioni.getTransazioniHelper().getRegistrazioniChkList();
	if (!(null == registrazioniDaTransare)) {
	    for (int i = 0; i < registrazioniDaTransare.length; i++) {
		Registrazioni registrazione = registrazioniService.findById(new PkId(registrazioniDaTransare[i]));
		registrazioniList.add(registrazione);
	    }
	    Collections.sort(registrazioniList, new RegistrazioniDataComparator());
	}
	// ordino le registrazioni per data
	registrazioni.setDisplayMode(BaseCommand.NEW);
	model.addAttribute("registrazioniList", registrazioniList);
	return "registrazioni/listTransazioniImporti";
    }

    @RequestMapping
    public String preparaTransazione(Model model, @ModelAttribute("registrazioni") RegistrazioniCommand registrazioni, BindingResult result,
	    SessionStatus status) {

	Registrazioni entity = new Registrazioni();
	Responsabili responsabili = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	responsabili.getId().setCodice(user.getCodiceResponsabile());
	responsabili.setResponsabile(user.getResponsabile());
	entity.setResponsabili(responsabili);
	entity.setResponsabiliSistema(responsabili);
	TransazioniHelper transazioniHelper = registrazioni.getTransazioniHelper();
	// cerco i dati comuni della transazione da proporre all'utente
	MercatiUso mercatiUso = registrazioni.getEntity().getMercatiUso();
	MercatiD posteggio = registrazioni.getEntity().getMercatiD();
	Software software = softwareService.findById(ORMHelper.getSoftware());
	Integer[] codiciRegistrazioni = transazioniHelper.getRegistrazioniChkList();
	List<Registrazioni> registrazioniDaRidurre = new ArrayList<Registrazioni>();
	if (!(null == codiciRegistrazioni)) {
	    for (int i = 0; i < codiciRegistrazioni.length; i++) {
		Integer codice = codiciRegistrazioni[i];
		Registrazioni registrazione = registrazioniService.findById(new PkId(codice));
		mercatiUso = registrazione.getMercatiUso();
		posteggio = registrazione.getMercatiD();
		registrazioniDaRidurre.add(registrazione);
	    }
	}
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(new MercatiConfigurazioneId());
	RegistrazioniCausali causaleTransazioneDefault = mercatiConfigurazione.getCausaleTransazione();
	if (causaleTransazioneDefault != null) {
	    entity.setRegistrazioniCausali(causaleTransazioneDefault);
	}
	entity.setAnagrafe(registrazioni.getEntity().getAnagrafe());
	entity.setMercatiD(posteggio);
	entity.setMercatiUso(mercatiUso);
	entity.setDataRegistrazione(Calendar.getInstance().getTime());
	entity.setSoftware(software);
	Map<Integer, RigaImporto> importoPerContoMap = new HashMap<Integer, RigaImporto>();
	Integer[] codiciRigheImporti = transazioniHelper.getImportiChkList();
	Set<RegistrazioniImporti> registrazioniImportiList = new HashSet<RegistrazioniImporti>();
	if (null != codiciRigheImporti) {
	    for (int i = 0; i < codiciRigheImporti.length; i++) {
		Integer codice = codiciRigheImporti[i];
		RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(new PkId(codice));
		Integer contoRiga = registrazioniImporti.getConti().getId().getCodice();
		if (importoPerContoMap.get(contoRiga) != null) {
		    RigaImporto rigaImporto = importoPerContoMap.get(contoRiga);
		    BigDecimal importoConto = rigaImporto.getImporto();
		    importoConto = importoConto.add(registrazioniImporti.getRimanenza());
		    rigaImporto.setImporto(importoConto);
		    rigaImporto.setIva(registrazioniImporti.getIva());
		    importoPerContoMap.put(contoRiga, rigaImporto);
		} else {
		    RigaImporto rigaImporto = new RigaImporto();
		    BigDecimal importoConto = registrazioniImporti.getRimanenza();
		    rigaImporto.setImporto(importoConto);
		    rigaImporto.setIva(registrazioniImporti.getIva());
		    importoPerContoMap.put(contoRiga, rigaImporto);
		}
	    }
	    for (Map.Entry<Integer, RigaImporto> entry : importoPerContoMap.entrySet()) {
		Integer codiceConto = entry.getKey();
		RigaImporto rigaImportoEntry = entry.getValue();
		BigDecimal importoTotale = rigaImportoEntry.getImporto();
		Conti conto = contiService.findById(new PkId(codiceConto));
		RegistrazioniImporti registrazioniImporto = new RegistrazioniImporti();
		registrazioniImporto.setConti(conto);
		registrazioniImporto.setRegistrazioni(entity);
		registrazioniImporto.setScadenza(Calendar.getInstance().getTime());
		registrazioniImporto.setNrRata(1);
		registrazioniImporto.setImporto(importoTotale);
		registrazioniImporto.setIva(rigaImportoEntry.getIva());
		registrazioniImporto.setInteressi(new BigDecimal(0));
		registrazioniImportiList.add(registrazioniImporto);
	    }
	}
	entity.setRegistrazioniImportis(registrazioniImportiList);
	registrazioni.setEntity(entity);
	registrazioni.setDisplayMode(BaseCommand.NEW);
	fixRenderEntityProperty(registrazioni.getEntity());
	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
	model.addAttribute("registrazioni", registrazioni);
	// metto nel model la lista delle registrazioni che saranno ridotte
	model.addAttribute("registrazioniDaRidurreList", registrazioniDaRidurre);
	return "registrazioni/preparaTransazione";
    }

    @RequestMapping
    public String insertTransazione(@ModelAttribute("registrazioni") RegistrazioniCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request, Model model) {

	Responsabili respSistema = new Responsabili();
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	respSistema.getId().setCodice(user.getCodiceResponsabile());
	TransazioniHelper transazioniHelper = command.getTransazioniHelper();
	Integer[] codiciRegistrazioni = transazioniHelper.getRegistrazioniChkList();
	List<Registrazioni> registrazioniDaRidurreList = new ArrayList<Registrazioni>();
	for (int i = 0; i < codiciRegistrazioni.length; i++) {
	    Registrazioni registrazioni = registrazioniService.findById(new PkId(codiciRegistrazioni[i]));
	    registrazioniDaRidurreList.add(registrazioni);
	}
	Integer[] codiciImporti = transazioniHelper.getImportiChkList();
	List<RegistrazioniImporti> registrazioniImportiList = new ArrayList<RegistrazioniImporti>();
	if (codiciImporti != null) {
	    for (int i = 0; i < codiciImporti.length; i++) {
		RegistrazioniImporti registrazioniImporti = registrazioniImportiService.findById(new PkId(codiciImporti[i]));
		registrazioniImportiList.add(registrazioniImporti);
	    }
	}
	Integer codice = null;
	// FIXME bug di hibernate quando ho più chiavi esterne verso la stessa tabella
	if (!request.getParameter("entity.responsabili.id.codice").equals("")) {
	    Integer codiceResp = Integer.parseInt(request.getParameter("entity.responsabili.id.codice"));
	    PkId codiceId = new PkId(codiceResp);
	    Responsabili responsabili = responsabiliService.findById(codiceId);
	    Registrazioni registrazioni = command.getEntity();
	    registrazioni.setResponsabili(responsabili);
	    command.setEntity(registrazioni);
	}
	try {
	    fixMergeEntityProperty(command.getEntity());
	    codice = registrazioniService.insertTransazione(command.getEntity(), registrazioniDaRidurreList, registrazioniImportiList);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command.getEntity(), true, e);
	    fixRenderEntityProperty(command.getEntity());
	    model.addAttribute("registrazioniDaRidurreList", registrazioniDaRidurreList);
	    setPageAttributes(model);
	    model.addAttribute("command", command);
	    return "registrazioni/preparaTransazione";
	}
	String goTo = "../registrazioni/view.htm?codice=" + codice;
	String returnTo = "../registrazioni/createSearch.htm?software=" + ORMHelper.getSoftware();
	try {
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	    returnTo = URLEncoder.encode(returnTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}
	status.setComplete();
	return "redirect:../history/set.htm?" + WebConstants.GOTO + "=" + goTo + "&" + WebConstants.RETURNTO + "=" + returnTo;
    }

    @RequestMapping
    public String createRateizzazioni(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	Registrazioni registrazioni = registrazioniService.findById(new PkId(codice));
	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	registrazioniFilter.setProgressivo(registrazioni.getProgressivo());
	registrazioniFilter.setDataInizio(registrazioni.getDataRegistrazione());
	registrazioniFilter.setImporto(registrazioni.getImporto());
	List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
	List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
	Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
	registrazioniFilter.setOneritipirateizzazione(oneritipirateizzazione);
	model.addAttribute("codiceRegistrazione", registrazioni.getId().getCodice());
	List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(registrazioni);
	model.addAttribute("regImportiList", regImportiList);
	MercatiConfigurazione mc = mercatiConfigurazioneService.findConfigurazione();
	Conti contoInteressi = mc.getContoInteressi();
	registrazioniFilter.setContoInteressiRat(contoInteressi);
	List<ChiaveValoreBean<Conti, Integer>> ocr = new ArrayList<ChiaveValoreBean<Conti, Integer>>();
	int i = 1;
	for (RegistrazioniImporti registrazioniImporti : regImportiList) {
	    ChiaveValoreBean<Conti, Integer> cvb = new ChiaveValoreBean<Conti, Integer>();
	    cvb.setChiave(registrazioniImporti.getConti());
	    cvb.setValore(i);
	    ocr.add(cvb);
	    i++;
	}
	ChiaveValoreBean<Conti, Integer> interessi = new ChiaveValoreBean<Conti, Integer>();
	Conti inte = new Conti();
	inte.getId().setCodice(RegistrazioniService.CODICE_CONTO_INTERESSI_TEMPORANEO);
	inte.setDescrizione(RegistrazioniService.DESCRIZIONE_CONTO_INTERESSI_TEMPORANEO);
	interessi.setChiave(inte);
	interessi.setValore(i);
	ocr.add(interessi);
	registrazioniFilter.setOrdinamentoContiRat(ocr);
	// 
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE, "1", request);
	String tipoRipartizione = (String) request.getAttribute(WebConstants.CONF_UTENTE_CONTAB_RAT_RIPARTIZIONE);
	Integer tipoRipint = null;
	try {
	    tipoRipint = Integer.valueOf(tipoRipartizione);
	    registrazioniFilter.setTipologiaRipartizioneRat(tipoRipint);
	} catch (NumberFormatException e) {
	    registrazioniFilter.setTipologiaRipartizioneRat(1);
	}
	// Setto i conti per gli interessi legali
	List<ContoInteressiLegali> contoList = new ArrayList<ContoInteressiLegali>();
	for (RegistrazioniImporti registrazioniImporti : regImportiList) {
	    ContoInteressiLegali contoInteressiLegali = new ContoInteressiLegali();
	    contoInteressiLegali.setConti(registrazioniImporti.getConti());
	    contoList.add(contoInteressiLegali);
	}
	registrazioniFilter.setContoInteressiLegaliList(contoList);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	return "registrazioni/formRateizzazioni";
    }

    /**
     * metodo per creare una simulazione di rateizzazione
     * 
     * @param model
     * @param registrazioniFilter
     * @param result
     * @param status
     * @param request
     * @param codice
     * @return
     */
    @RequestMapping
    public String simulaRateizzazioni(@RequestParam("flagInteressi") String flagInteressi, Model model,
	    @ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, @RequestParam("codice") Integer codice) {

	// Devo inviare il parametro flagInteressiLegali perchè spring in caso di errore non mi aggiorna il valore
	Oneritipirateizzazione oneriRate = registrazioniFilter.getOneritipirateizzazione();
	boolean flagInteressiLegali = Boolean.valueOf(flagInteressi);
	oneriRate.setFlagInteressiLegali(flagInteressiLegali);
	registrazioniFilter.setOneritipirateizzazione(oneriRate);
	// Controllo se sto in modalità edit dei valori di Oneritipirateizzazione
	if (!registrazioniFilter.getOneritipirateizzazione().isEditOneriTransient()) {
	    Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService
		    .findById(new PkId(registrazioniFilter.getOneritipirateizzazione().getId().getCodice()));
	    oneritipirateizzazione.setDataInizioTransient(oneriRate.getDataInizioTransient());
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	    registrazioniFilter.setOneritipirateizzazione(oneritipirateizzazione);
	}
	try {
	    registrazioniService.validateRateizzazione(registrazioniFilter);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniFilter, e);
	    Registrazioni registrazioni = registrazioniService.findById(new PkId(codice));
	    List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
	    List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	    model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	    model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
	    model.addAttribute("registrazioniFilter", registrazioniFilter);
	    model.addAttribute("codiceRegistrazione", registrazioni.getId().getCodice());
	    List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(registrazioni);
	    model.addAttribute("regImportiList", regImportiList);
	    return "registrazioni/formRateizzazioni";
	}
	Registrazioni registrazioni = registrazioniService.findById(new PkId(codice));
	// Lista di oneri tipi rateizzazione
	List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
	model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
	model.addAttribute("registrazioniFilter", registrazioniFilter);
	model.addAttribute("codiceRegistrazione", registrazioni.getId().getCodice());
	// Lista di registrazioni importi raggruppate per conto
	List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(registrazioni);
	model.addAttribute("regImportiList", regImportiList);
	Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
	// CONTROLLO SE HO INSERITO MANUALMENTE I VALORI DI ONERITIPIRATEIZZAZIONI
	if (registrazioniFilter.getOneritipirateizzazione().isEditOneriTransient()) {
	    Oneritipirateizzazione oneri = registrazioniFilter.getOneritipirateizzazione();
	    oneritipirateizzazione.setNrorate(oneri.getNrorate());
	    oneritipirateizzazione.setInteressirate(oneri.getInteressirate());
	    oneritipirateizzazione.setFrequenzarate(oneri.getFrequenzarate());
	    oneritipirateizzazione.setRipartizionerate(oneri.getRipartizionerate());
	    oneritipirateizzazione.setScadenzarate(oneri.getScadenzarate());
	    oneritipirateizzazione.setDataInizioTransient(oneri.getDataInizioTransient());
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	} else {
	    oneritipirateizzazione = oneritipirateizzazioneService
		    .findById(new PkId(registrazioniFilter.getOneritipirateizzazione().getId().getCodice()));
	    oneritipirateizzazione.setDataInizioTransient(registrazioniFilter.getOneritipirateizzazione().getDataInizioTransient());
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	}
	// Set di registrazioni importi per generare la simulazione
	Set<RegistrazioniImporti> simulaRegImportiList = registrazioniService.rateizzaRegistrazioni(registrazioniFilter.getDataInizio(),
		regImportiList, oneritipirateizzazione, registrazioniFilter.getContoInteressiLegaliList(),
		registrazioniFilter.getTipologiaRipartizioneRat(), registrazioniFilter.getContoInteressiRat(),
		registrazioniFilter.getOrdinamentoContiRat());
	Registrazioni reg = new Registrazioni();
	reg.setRegistrazioniImportis(simulaRegImportiList);
	List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	model.addAttribute("simulaReg", reg);
	model.addAttribute("simulazione", true);
	return "registrazioni/formRateizzazioni";
    }

    /**
     * Metodo per aggiornare le registrazioni importi di una registrazione con il nuovo piano di rateizzazione
     * 
     * @param model
     * @param registrazioniFilter
     * @param result
     * @param status
     * @param request
     * @param codice
     * @return
     */
    @RequestMapping
    public String updateRateizzazioni(@RequestParam("flagInteressi") String flagInteressi, Model model,
	    @ModelAttribute("registrazioniFilter") RegistrazioniFilter registrazioniFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, @RequestParam("codice") Integer codice) {

	// Devo inviare il parametro flagInteressiLegali perchè spring in caso di errore non mi aggiorna il valore
	Oneritipirateizzazione oneriRate = registrazioniFilter.getOneritipirateizzazione();
	boolean flagInteressiLegali = Boolean.valueOf(flagInteressi);
	oneriRate.setFlagInteressiLegali(flagInteressiLegali);
	registrazioniFilter.setOneritipirateizzazione(oneriRate);
	// /////////////////////////////////////////////////////////////////
	registrazioniService.validateRateizzazione(registrazioniFilter);
	// Controllo se sto in modalità edit dei valori di Oneritipirateizzazione
	if (!registrazioniFilter.getOneritipirateizzazione().isEditOneriTransient()) {
	    Oneritipirateizzazione oneritipirateizzazione = oneritipirateizzazioneService
		    .findById(new PkId(registrazioniFilter.getOneritipirateizzazione().getId().getCodice()));
	    oneritipirateizzazione.setDataInizioTransient(oneriRate.getDataInizioTransient());
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	    registrazioniFilter.setOneritipirateizzazione(oneritipirateizzazione);
	    // Valido il form
	    registrazioniService.validateRateizzazione(registrazioniFilter);
	}
	// Valido il form
	try {
	    registrazioniService.validateRateizzazione(registrazioniFilter);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, registrazioniFilter, e);
	    Registrazioni registrazioni = registrazioniService.findById(new PkId(codice));
	    List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
	    model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
	    model.addAttribute("registrazioniFilter", registrazioniFilter);
	    model.addAttribute("codiceRegistrazione", registrazioni.getId().getCodice());
	    List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	    model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	    List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(registrazioni);
	    model.addAttribute("regImportiList", regImportiList);
	    return "registrazioni/formRateizzazioni";
	}
	Registrazioni registrazioni = registrazioniService.findById(new PkId(codice));
	Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
	// CONTROLLO SE HO INSERITO MANUALMENTE I VALORI DI ONERITIPIRATEIZZAZIONI
	if (registrazioniFilter.getOneritipirateizzazione().isEditOneriTransient()) {
	    Oneritipirateizzazione oneri = registrazioniFilter.getOneritipirateizzazione();
	    oneritipirateizzazione.setNrorate(oneri.getNrorate());
	    oneritipirateizzazione.setInteressirate(oneri.getInteressirate());
	    oneritipirateizzazione.setFrequenzarate(oneri.getFrequenzarate());
	    oneritipirateizzazione.setRipartizionerate(oneri.getRipartizionerate());
	    oneritipirateizzazione.setScadenzarate(oneri.getScadenzarate());
	    oneritipirateizzazione.setEditOneriTransient(true);
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	    oneritipirateizzazione.setDataInizioTransient(oneri.getDataInizioTransient());
	} else {
	    oneritipirateizzazione = oneritipirateizzazioneService
		    .findById(new PkId(registrazioniFilter.getOneritipirateizzazione().getId().getCodice()));
	    oneritipirateizzazione.setDataInizioTransient(registrazioniFilter.getOneritipirateizzazione().getDataInizioTransient());
	    oneritipirateizzazione.setFlagInteressiLegali(flagInteressiLegali);
	}
	try {
	    // Metodo che aggiorna le registrazioni importi
	    registrazioniService.updateRegistrazioniImportiRateizzati(oneritipirateizzazione, registrazioniFilter.getDataInizio(), registrazioni,
		    registrazioniFilter.getContoInteressiLegaliList(), registrazioniFilter.getTipologiaRipartizioneRat(),
		    registrazioniFilter.getContoInteressiRat(), registrazioniFilter.getOrdinamentoContiRat());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, new Registrazioni(), e);
	    Registrazioni reg = registrazioniService.findById(new PkId(codice));
	    List<Oneritipirateizzazione> oneritipirateizzazioneList = oneritipirateizzazioneService.findAll(null, null);
	    model.addAttribute("oneritipirateizzazioneList", oneritipirateizzazioneList);
	    model.addAttribute("registrazioniFilter", registrazioniFilter);
	    model.addAttribute("codiceRegistrazione", reg.getId().getCodice());
	    List<TipiScadenza> tipiscadenzaList = tipiScadenzaService.findAll(null, null);
	    model.addAttribute("tipiscadenzaList", tipiscadenzaList);
	    List<RegistrazioniImporti> regImportiList = registrazioniImportiService.findByRegistrazioneGroupByConto(reg);
	    model.addAttribute("regImportiList", regImportiList);
	    // Set di registrazioni importi per generare la simulazione
	    Set<RegistrazioniImporti> simulaRegImportiList = registrazioniService.rateizzaRegistrazioni(registrazioniFilter.getDataInizio(),
		    regImportiList, oneritipirateizzazione, registrazioniFilter.getContoInteressiLegaliList(),
		    registrazioniFilter.getTipologiaRipartizioneRat(), registrazioniFilter.getContoInteressiRat(),
		    registrazioniFilter.getOrdinamentoContiRat());
	    reg.setRegistrazioniImportis(simulaRegImportiList);
	    model.addAttribute("simulaReg", reg);
	    model.addAttribute("simulazione", true);
	    return "registrazioni/formRateizzazioni";
	}
	return "redirect:view.htm?codice=" + codice + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Registrazioni entity) {

	// Registrazioni entity = command.getEntity();
	if (entity.getIstanze() != null && entity.getIstanze().getId() != null && entity.getIstanze().getId().getCodice() == null) {
	    entity.setIstanze(null);
	}
	if (entity.getInventarioprocedimenti() != null && entity.getInventarioprocedimenti().getId() != null
		&& entity.getInventarioprocedimenti().getId().getCodice() == null) {
	    entity.setInventarioprocedimenti(null);
	}
	if (entity.getMercatiD() != null && entity.getMercatiD().getId() != null && entity.getMercatiD().getId().getCodice() == null) {
	    entity.setMercatiD(null);
	}
	if (entity.getAnagrafe() != null && entity.getAnagrafe().getId() != null && entity.getAnagrafe().getId().getCodice() == null) {
	    entity.setAnagrafe(null);
	}
	if (entity.getRegistrazioniCausali() != null && entity.getRegistrazioniCausali().getId() != null
		&& entity.getRegistrazioniCausali().getId().getCodice() == null) {
	    entity.setRegistrazioniCausali(null);
	}
	if (entity.getMercatiUso() != null && entity.getMercatiUso().getId() != null && entity.getMercatiUso().getId().getCodice() == null) {
	    entity.setMercatiUso(null);
	}
	if (entity.getResponsabili() != null && entity.getResponsabili().getId() != null && entity.getResponsabili().getId().getCodice() == null) {
	    entity.setResponsabili(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Registrazioni entity) {

	if (entity.getMercatiD() != null) {
	    MercatiD mercatiD = mercatiDService.findById(entity.getMercatiD().getId());
	    entity.setMercatiD(mercatiD);
	}
	if (entity.getInventarioprocedimenti() != null) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(entity.getInventarioprocedimenti().getId());
	    entity.setInventarioprocedimenti(inventarioprocedimenti);
	}
	if (entity.getAnagrafe() != null) {
	    Anagrafe anagrafe = anagrafeService.findById(entity.getAnagrafe().getId());
	    entity.setAnagrafe(anagrafe);
	}
	if (entity.getIstanze() == null) {
	    entity.setIstanze(new Istanze());
	}
	if (entity.getInventarioprocedimenti() == null) {
	    entity.setInventarioprocedimenti(new Inventarioprocedimenti());
	}
	if (entity.getMercatiD() == null) {
	    entity.setMercatiD(new MercatiD());
	}
	if (entity.getAnagrafe() == null) {
	    entity.setAnagrafe(new Anagrafe());
	}
	if (entity.getRegistrazioniCausali() == null) {
	    entity.setRegistrazioniCausali(new RegistrazioniCausali());
	}
	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
	if (entity.getResponsabili() == null) {
	    entity.setResponsabili(new Responsabili());
	}
    }

    // restituisci un array di string.Ogni campo conterrà un parametro necessario
    // per la ricerca delle rate non pagate che sono settati sulla configuarazione utente
    private String[] getParametriRateNonPagate(String valore) {

	return valore.split(",");
    }

    protected void fixMergeRegistrazioniImporti(RegistrazioniImporti entity) {

	if (entity.getConti() != null && entity.getConti().getId() != null && entity.getConti().getId().getCodice() == null) {
	    entity.setConti(null);
	}
    }

    protected void fixRenderRegistrazioniImporti(RegistrazioniImporti entity) {

	if (entity.getConti() == null) {
	    entity.setConti(new Conti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<RegistrazioniCausali> registrazioniCausaliList = registrazioniCausaliService.findByAbilitato();
	model.addAttribute("registrazioniCausaliList", registrazioniCausaliList);
    }

    @RequestMapping
    public void ajaxEsportaTracciato(HttpServletRequest request, HttpServletResponse response) throws IOException {

	throw new NotImplementedException("Funzionalità non più implementata");
	//	RegistrazioniFilter registrazioniFilter = new RegistrazioniFilter();
	//	if (request.getSession().getAttribute(REGISTRAZIONI_FILTER_IN_SESSION) != null) {
	//	    registrazioniFilter = (RegistrazioniFilter) request.getSession().getAttribute(REGISTRAZIONI_FILTER_IN_SESSION);
	//	} else {
	//	    request.getSession().setAttribute(REGISTRAZIONI_FILTER_IN_SESSION, registrazioniFilter);
	//	}
	//	byte[] file = registrazioniService.createTracciatoByRegistrazioniFilter(registrazioniFilter);
	//	response.setHeader("Pragma", "public");
	//	response.setHeader("Cache-Control", "max-age=0");
	//	if (request.getParameter("no_dialog") == null) {
	//	    // BOCCI 2012-08-13
	//	    // Nel mostrare gli allegati togliere gli spazi (https://support.mozilla.org/it/questions/724438) altrimenti firefox non va.
	//	    // When a user clicks on an attachment with spaces, the filename is truncated to the first whitespace. 
	//	    // While IE, Chrome & Safari handle this, Firefox refuses to accept mime headers with unquoted filename parameters. 
	//	    // According to Firefox's bugzilla/knowledgebase, Firefox's behavior is the correct behavior and it's a problem with 
	//	    // most webservers or web applications. This problem can be easily corrected by surrounding the filename parameter with double quotes.
	//	    // Eg	Response.AddHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	//	    response.setHeader("Content-Disposition", "attachment; filename=\"tracciato.zip");
	//	}
	//	response.setHeader("Content-transfer-encoding", "binary");
	//	String cType = "application/zip";
	//	response.setContentType(cType);
	//	response.setContentLength(file.length);
	//	ServletOutputStream out = response.getOutputStream();
	//	out.write(file);
	//	out.flush();
    }
}
