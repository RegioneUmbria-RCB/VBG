/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.FileUpload;
import it.gruppoinit.pal.gp.core.domain.LettureContatoriCommand;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatidLetture;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.TipiContatore;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatidLettureService;
import it.gruppoinit.pal.gp.core.service.OneritipirateizzazioneService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiContatoreService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
//DAELIMINARE @Controller
@SessionAttributes(value = { "command", "mercatidLetture", "registrazioniFilter" })
public class MercatidLettureController extends BaseController<MercatidLetture> {

    @Autowired
    private TipiContatoreService tipiContatoreService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private MercatidLettureService mercatidLettureService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private OneritipirateizzazioneService oneritipirateizzazioneService;

    @RequestMapping
    public String createLettureContatore(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	LettureContatoriCommand command = new LettureContatoriCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	MercatidLetture mercatidLetture = new MercatidLetture();
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	if (configurazioneutente == null) {
	    // LISTA DELLE LETTURE//
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findAll(null, null);
	    command.setMercatidLettureList(mercatidLettureList);
	} else {
	    mercatidLetture = getMercatidLettureFilter(configurazioneutente);
	    uso.setMercati(mercati);
	    mercatidLetture.setMercatiUso(uso);
	    // LISTA DELLE LETTURE//
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findByFilter(mercatidLetture);
	    command.setMercatidLettureList(mercatidLettureList);
	}
	// FILTRO//
	command.setMercatidLetture(mercatidLetture);
	fixRenderEntityProperty(command.getMercatidLetture());
	model.addAttribute("command", command);
	List<Date> dataLetturaList = mercatidLettureService.findDataLettura(uso);
	model.addAttribute("dataLetturaList", dataLetturaList);
	List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	model.addAttribute("tipicontatoreList", list);
	model.addAttribute("posteggi", mercatiDService.findPosteggioByMercatiMercatoUso(mercati));
	// /COMMAND RIGA///
	model.addAttribute("mercatidLetture", new MercatidLetture());
	return "mercatidletture/list";
    }

    private MercatidLetture getMercatidLettureFilter(Configurazioneutente configurazioneutente) {

	MercatidLetture mercatidLetture = new MercatidLetture();
	String[] valoriFiltro = getParametriLettureContatori(configurazioneutente.getValore());
	// setto sul filtro i valori di configurazione salvati nella configurazione utente
	mercatidLetture.setAnno(valoriFiltro[4].equals("%") ? null : Short.parseShort(valoriFiltro[4]));
	mercatidLetture.setTipiContatore((valoriFiltro[1].equals("%") ? new TipiContatore() : tipiContatoreService.findById(Integer
		.parseInt(valoriFiltro[1]))));
	Date datalettura = null;
	if (valoriFiltro[0].equals("%")) {
	    datalettura = null;
	    mercatidLetture.setDataLettura(datalettura);
	} else {
	    DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    try {
		datalettura = myDateFormat.parse(valoriFiltro[0]);
	    } catch (ParseException e) {
		e.printStackTrace();
		throw new RuntimeException("ERRORE getMercatidLettureFilter: non è possibile trasformare la data lettura");
	    }
	    mercatidLetture.setDataLettura(datalettura);
	}
	Date datainizio = null;
	if (valoriFiltro[2].equals("%")) {
	    datainizio = null;
	    mercatidLetture.setDataInizio(datainizio);
	} else {
	    DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    try {
		datainizio = myDateFormat.parse(valoriFiltro[2]);
	    } catch (ParseException e) {
		e.printStackTrace();
		throw new RuntimeException("ERRORE getMercatidLettureFilter: non è possibile trasformare la data inizio");
	    }
	    mercatidLetture.setDataInizio(datainizio);
	}
	Date datafine = null;
	if (valoriFiltro[3].equals("%")) {
	    datafine = null;
	    mercatidLetture.setDataFine(datafine);
	} else {
	    DateFormat myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    try {
		datafine = myDateFormat.parse(valoriFiltro[3]);
	    } catch (ParseException e) {
		e.printStackTrace();
		throw new RuntimeException("ERRORE getMercatidLettureFilter: non è possibile trasformare la data fine");
	    }
	    mercatidLetture.setDataFine(datafine);
	}
	return mercatidLetture;
    }

    // restituisci un array di string.Ogni campo conterrà un parametro necessario
    // per la ricerca delle letture contatori che sono settati sulla configuarazione utente
    private String[] getParametriLettureContatori(String valore) {

	return valore.split(",");
    }

    @RequestMapping
    public String searchLettureContatori(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("command") LettureContatoriCommand command, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	// RECUPERO MANIFESTAZIONE E USO
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// recupero la configurazione per l'utente loggato
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	MercatidLetture letture = command.getMercatidLetture();
	uso.setMercati(mercati);
	letture.setMercatiUso(uso);
	if (configurazioneutente == null) {
	    String valore = getConfigurazioneLettureContatori(letture);
	    // .. inserisco i valori di default
	    Configurazioneutente configutente = new Configurazioneutente();
	    configutente.setId(confUteId);
	    configutente.setResponsabile(responsabile);
	    configutente.setValore(valore);
	    configurazioneutenteService.insert(configutente);
	} else {
	    // creo la stringa con i valori di configurazione
	    String valore = getConfigurazioneLettureContatori(letture);
	    // aggiorno la configurazione dell'utente con i nuovi filtri di ricerca
	    configurazioneutente.setValore(valore);
	    configurazioneutenteService.update(configurazioneutente);
	}
	List<MercatidLetture> mercatidLettureList = mercatidLettureService.findByFilter(command.getMercatidLetture());
	command.setMercatidLettureList(mercatidLettureList);
	List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	model.addAttribute("tipicontatoreList", list);
	command.setMercatidLetture(command.getMercatidLetture());
	fixRenderEntityProperty(command.getMercatidLetture());
	model.addAttribute("command", command);
	// SELECT PER DATA LETTURA SUL FILTRO
	List<Date> dataLetturaList = mercatidLettureService.findDataLettura(uso);
	model.addAttribute("dataLetturaList", dataLetturaList);
	model.addAttribute("posteggi", mercatiDService.findPosteggioByMercatiMercatoUso(mercati));
	// /COMMAND RIGA///
	model.addAttribute("mercatidLetture", new MercatidLetture());
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatoUso=" + uso.getId().getCodice();
    }

    private String getConfigurazioneLettureContatori(MercatidLetture mercatidLettureFilter) {

	String valore = "";
	String tipoContatore = (mercatidLettureFilter.getTipiContatore() == null ? "%" : String.valueOf((Integer) mercatidLettureFilter
		.getTipiContatore().getId()));
	String anno = (mercatidLettureFilter.getAnno() != null ? String.valueOf((Short) mercatidLettureFilter.getAnno()) : "%");
	String datalettura = "%";
	if (mercatidLettureFilter.getDataLettura() != null) {
	    DateFormat myDateFormatOutDataLettura = new SimpleDateFormat("dd/MM/yyyy");
	    myDateFormatOutDataLettura.format(mercatidLettureFilter.getDataLettura());
	    datalettura = myDateFormatOutDataLettura.format(mercatidLettureFilter.getDataLettura());
	}
	String datainizio = "%";
	if (mercatidLettureFilter.getDataInizio() != null) {
	    DateFormat myDateFormatOutDataInizio = new SimpleDateFormat("dd/MM/yyyy");
	    myDateFormatOutDataInizio.format(mercatidLettureFilter.getDataInizio());
	    datainizio = myDateFormatOutDataInizio.format(mercatidLettureFilter.getDataInizio());
	}
	String datafine = "%";
	if (mercatidLettureFilter.getDataFine() != null) {
	    DateFormat myDateFormatOutDataFine = new SimpleDateFormat("dd/MM/yyyy");
	    myDateFormatOutDataFine.format(mercatidLettureFilter.getDataFine());
	    datafine = myDateFormatOutDataFine.format(mercatidLettureFilter.getDataFine());
	}
	valore = datalettura + "," + tipoContatore + "," + datainizio + "," + datafine + "," + anno;
	return valore;
    }

    @RequestMapping
    public String updateLettureContatori(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("mercatidLetture") MercatidLetture mercatidLetture, @ModelAttribute("command") LettureContatoriCommand command,
	    BindingResult result, SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// /UPDATE LA LISTA DI MERCATIDLETTURE///
	try {
	    mercatidLettureService.updateNuoveLetture(command.getMercatidLettureList());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    // recupero la configurazione per l'utente loggato
	    LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	    PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	    Responsabili responsabile = responsabiliService.findById(idResponsabile);
	    ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	    Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	    MercatidLetture letture = getMercatidLettureFilter(configurazioneutente);
	    uso.setMercati(mercati);
	    letture.setMercatiUso(uso);
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findByFilter(letture);
	    command.setMercatidLettureList(mercatidLettureList);
	    List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	    model.addAttribute("tipicontatoreList", list);
	    command.setMercatidLetture(command.getMercatidLetture());
	    fixRenderEntityProperty(command.getMercatidLetture());
	    model.addAttribute("command", command);
	    // SELECT PER DATA LETTURA SUL FILTRO
	    List<Date> dataLetturaList = mercatidLettureService.findDataLettura(uso);
	    model.addAttribute("dataLetturaList", dataLetturaList);
	    return "mercatidletture/list";
	}
	// /FINE UPDATE///
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatoUso=" + uso.getId().getCodice()
		+ "&status_msg=02";
    }

    @RequestMapping
    public String deleteLettureContatori(@RequestParam("mercatidLetture.id.codice") Integer codiceMercatidLetture,
	    @RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("command") LettureContatoriCommand command, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	MercatidLetture entity = mercatidLettureService.findById(new PkId(codiceMercatidLetture));
	mercatidLettureService.delete(entity);
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + codiceMercato + "&mercatoUso=" + codiceUso;
    }

    @RequestMapping
    public String createRigaLettureContatori(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("command") LettureContatoriCommand command, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	MercatidLetture mercatidLetture = new MercatidLetture();
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	if (configurazioneutente == null) {
	    // LISTA DELLE LETTURE//
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findAll(null, null);
	    command.setMercatidLettureList(mercatidLettureList);
	} else {
	    mercatidLetture = getMercatidLettureFilter(configurazioneutente);
	    uso.setMercati(mercati);
	    mercatidLetture.setMercatiUso(uso);
	    // LISTA DELLE LETTURE//
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findByFilter(mercatidLetture);
	    command.setMercatidLettureList(mercatidLettureList);
	}
	// FILTRO//
	command.setMercatidLetture(mercatidLetture);
	fixRenderEntityProperty(command.getMercatidLetture());
	model.addAttribute("command", command);
	List<Date> dataLetturaList = mercatidLettureService.findDataLettura(uso);
	model.addAttribute("dataLetturaList", dataLetturaList);
	List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	model.addAttribute("tipicontatoreList", list);
	model.addAttribute("posteggi", mercatiDService.findPosteggioByMercatiMercatoUso(mercati));
	// /COMMAND RIGA///
	MercatidLetture entity = new MercatidLetture();
	fixRenderEntityProperty(entity);
	model.addAttribute("mercatidLetture", entity);
	model.addAttribute("newLettura", true);
	return "mercatidletture/list";
    }

    /**
     * Metodo per inserire una riga di Mercatidletture.
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param mercatidLetture
     * @param result
     * @param command
     * @param status
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String insertLettureContatori(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("mercatidLetture") MercatidLetture mercatidLetture, BindingResult result,
	    @ModelAttribute("command") LettureContatoriCommand command, SessionStatus status, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	// Recupero Manifestazione e Uso
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// INSERIMENTO RIGA LETTURA//
	TipiContatore contatore = tipiContatoreService.findById(Integer.valueOf(request.getParameter("tipicontatoreRiga")));
	mercatidLetture.setTipiContatore(contatore);
	uso.setMercati(mercati);
	mercatidLetture.setMercatiUso(uso);
	// Imposto l'anno con l'anno della data lettura
	if (mercatidLetture.getDataLettura() != null) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(mercatidLetture.getDataLettura());
	    mercatidLetture.setAnno((Short) ((Integer) calendar.get(Calendar.YEAR)).shortValue());
	}
	try {
	    fixMergeEntityProperty(mercatidLetture);
	    mercatidLettureService.insert(mercatidLetture);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatidLetture, e);
	    model.addAttribute("newLettura", true);
	    // recupero la configurazione per l'utente loggato
	    LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	    PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	    Responsabili responsabile = responsabiliService.findById(idResponsabile);
	    ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		    WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	    Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	    MercatidLetture lettureContatori = getMercatidLettureFilter(configurazioneutente);
	    lettureContatori.setMercatiUso(uso);
	    List<MercatidLetture> mercatidLettureList = mercatidLettureService.findByFilter(lettureContatori);
	    command.setMercatidLettureList(mercatidLettureList);
	    List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	    model.addAttribute("tipicontatoreList", list);
	    command.setMercatidLetture(lettureContatori);
	    model.addAttribute("command", command);
	    // SELECT PER DATA LETTURA SUL FILTRO
	    List<Date> dataLetturaList = mercatidLettureService.findDataLettura(uso);
	    model.addAttribute("dataLetturaList", dataLetturaList);
	    model.addAttribute("posteggi", mercatiDService.findPosteggioByMercatiMercatoUso(mercati));
	    return "mercatidletture/list";
	}
	// FINE INSERIMENTO RIGA LETTURA//
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatoUso=" + uso.getId().getCodice();
    }

    @RequestMapping
    public String createNuovaLettura(Model model, @RequestParam("mercati.id.codice") Integer codiceMercato,
	    @RequestParam("mercatoUso") Integer codiceUso, HttpServletRequest request, HttpServletResponse response) {

	// Recupero Manifestazione e Uso
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	uso.setMercati(mercati);
	LettureContatoriCommand command = new LettureContatoriCommand();
	List<MercatidLetture> mercatidLettureList = new ArrayList<MercatidLetture>();
	Set<MercatiD> posteggiList = mercati.getMercatiDs();
	for (MercatiD mercatiD : posteggiList) {
	    MercatidLetture letture = new MercatidLetture();
	    letture.setMercatiUso(uso);
	    letture.setPosteggio(mercatiD);
	    MercatidLetture lettureTemp = mercatidLettureService.findUltimaLetturaFinaleByPosteggio(mercatiD);
	    if (lettureTemp.getLetturaFinale() != null) {
		letture.setLetturaIniziale(lettureTemp.getLetturaFinale());
	    }
	    mercatidLettureList.add(letture);
	}
	command.setMercatidLettureList(mercatidLettureList);
	model.addAttribute("command", command);
	List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	model.addAttribute("tipicontatoreList", list);
	return "mercatidletture/formNuovaLettura";
    }

    @RequestMapping
    public String insertNuovaLettura(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("command") LettureContatoriCommand command, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	// Recupero Manifestazione e Uso
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	uso.setMercati(mercati);
	List<TipiContatore> list = tipiContatoreService.findAll(null, null);
	model.addAttribute("tipicontatoreList", list);
	List<MercatidLetture> lettureList = new ArrayList<MercatidLetture>();
	MercatidLetture mercatidLetture = command.getMercatidLetture();
	for (MercatidLetture letture : command.getMercatidLettureList()) {
	    letture.setDataLettura(mercatidLetture.getDataLettura());
	    letture.setDataFine(mercatidLetture.getDataFine());
	    letture.setDataInizio(mercatidLetture.getDataInizio());
	    letture.setTipiContatore(mercatidLetture.getTipiContatore());
	    letture.setPresunta(mercatidLetture.getPresunta());
	    lettureList.add(letture);
	}
	try {
	    List<MercatidLetture> lettureNonInseriteList = mercatidLettureService.insertNuoveLetture(lettureList);
	    if (lettureNonInseriteList.size() == lettureList.size()) {
		request.setAttribute("status_msg", "03");
	    } else {
		request.setAttribute("status_msg", "01");
	    }
	    command.setMercatidLettureList(lettureNonInseriteList);
	    model.addAttribute("command", command);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    return "mercatidletture/formNuovaLettura";
	}
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatoUso=" + uso.getId().getCodice();
    }

    /**
     * Metodo per esporte un file excel.
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param command
     * @param result
     * @param status
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String exportLettureContatore(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    @ModelAttribute("command") LettureContatoriCommand command, BindingResult result, SessionStatus status, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	// RECUPERO IMPOSTAZIONE UTENTE//
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// RECUPERO LISTA DA ESPORTARE//
	List<MercatidLetture> mercatidLettureList = null;
	if (configurazioneutente == null) {
	    // LISTA DELLE LETTURE//
	    mercatidLettureList = mercatidLettureService.findAll(null, null);
	    command.setMercatidLettureList(mercatidLettureList);
	} else {
	    MercatidLetture mercatidLetture = getMercatidLettureFilter(configurazioneutente);
	    uso.setMercati(mercati);
	    mercatidLetture.setMercatiUso(uso);
	    // LISTA DELLE LETTURE//
	    mercatidLettureList = mercatidLettureService.findByFilter(mercatidLetture);
	    command.setMercatidLettureList(mercatidLettureList);
	}
	// /////FILE EXCEL/////////
	HSSFWorkbook workbook = createExportExcel(mercatidLettureList);
	// ///////////////////////
	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
	String encoding = "UTF-8";
	String fn = "";
	try {
	    fn = new String("letture_contatori.xls".getBytes(encoding), encoding);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
	response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	response.setHeader("Pragma", "public");
	response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	try {
	    OutputStream outputStream = response.getOutputStream();
	    workbook.write(outputStream);
	    outputStream.close();
	    outputStream.flush();
	    response.flushBuffer();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return null;
    }

    private HSSFWorkbook createExportExcel(List<MercatidLetture> mercatidLettureList) {

	// CREO EXPORT IN EXCEL
	HSSFWorkbook workbook = new HSSFWorkbook();
	String caption = "Letture Contatori";
	HSSFSheet sheet = workbook.createSheet(caption);
	// renderer header
	HSSFRow hssfRow = sheet.createRow(0);
	// Header della table
	HSSFCell cell1 = hssfRow.createCell(0);
	cell1.setCellValue(new HSSFRichTextString("Id"));
	HSSFCell cell2 = hssfRow.createCell(1);
	cell2.setCellValue(new HSSFRichTextString("Anno"));
	HSSFCell cell3 = hssfRow.createCell(2);
	cell3.setCellValue(new HSSFRichTextString("Mercato"));
	HSSFCell cell4 = hssfRow.createCell(3);
	cell4.setCellValue(new HSSFRichTextString("Posteggio"));
	HSSFCell cell5 = hssfRow.createCell(4);
	cell5.setCellValue(new HSSFRichTextString("Tipo Contatore"));
	HSSFCell cell6 = hssfRow.createCell(5);
	cell6.setCellValue(new HSSFRichTextString("Da data"));
	HSSFCell cell7 = hssfRow.createCell(6);
	cell7.setCellValue(new HSSFRichTextString("A data"));
	HSSFCell cell8 = hssfRow.createCell(7);
	cell8.setCellValue(new HSSFRichTextString("Lettura iniziale"));
	HSSFCell cell9 = hssfRow.createCell(8);
	cell9.setCellValue(new HSSFRichTextString("Lettura"));
	HSSFCell cell10 = hssfRow.createCell(9);
	cell10.setCellValue(new HSSFRichTextString("Consumo"));
	HSSFCell cell11 = hssfRow.createCell(10);
	cell11.setCellValue(new HSSFRichTextString("Importo"));
	// renderer body
	Collection<MercatidLetture> items = mercatidLettureList;
	// Le stile per rendere le celle readonly
	HSSFCellStyle csTrue = workbook.createCellStyle();
	csTrue.setLocked(true);
	// Le stile per rendere le celle editabili
	HSSFCellStyle csFalse = workbook.createCellStyle();
	csFalse.setLocked(false);
	int rowcount = 1;
	for (MercatidLetture item : items) {
	    HSSFRow r = sheet.createRow(rowcount++);
	    // ID
	    HSSFCell cellID = r.createCell(0);
	    cellID.setCellStyle(csTrue);
	    cellID.setCellValue(item.getId().getCodice());
	    // ANNO
	    HSSFCell cellANNO = r.createCell(1);
	    cellANNO.setCellStyle(csTrue);
	    cellANNO.setCellValue(item.getAnno());
	    // MERCATO
	    HSSFCell cellMERCATO = r.createCell(2);
	    cellMERCATO.setCellStyle(csTrue);
	    cellMERCATO.setCellValue(new HSSFRichTextString(item.getPosteggio().getMercati().getDescrizione()));
	    // POSTEGGIO
	    HSSFCell cellPOSTEGGIO = r.createCell(3);
	    cellPOSTEGGIO.setCellStyle(csTrue);
	    cellPOSTEGGIO.setCellValue(new HSSFRichTextString(item.getPosteggio().getCodiceposteggio()));
	    // TIPI CONTATORE
	    HSSFCell cellTIPICONTATORE = r.createCell(4);
	    cellTIPICONTATORE.setCellStyle(csTrue);
	    cellTIPICONTATORE.setCellValue(new HSSFRichTextString(item.getTipiContatore().getDescrizione()));
	    // DA DATA
	    HSSFCell cellDADATA = r.createCell(5);
	    cellDADATA.setCellStyle(csTrue);
	    DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
	    String outdateDallaData = myDateFormatOut.format(item.getDataInizio());
	    cellDADATA.setCellValue(new HSSFRichTextString(outdateDallaData));
	    // A DATA
	    HSSFCell cellADATA = r.createCell(6);
	    cellADATA.setCellStyle(csTrue);
	    DateFormat myDateFormatOutAllaData = new SimpleDateFormat("dd/MM/yyyy");
	    String outdateallaData = myDateFormatOutAllaData.format(item.getDataFine());
	    cellADATA.setCellValue(new HSSFRichTextString(outdateallaData));
	    // LETTURA INIZIALE
	    HSSFCell cellLETTURAINIZIALE = r.createCell(7);
	    cellLETTURAINIZIALE.setCellStyle(csFalse);
	    cellLETTURAINIZIALE.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    if (item.getLetturaIniziale() != null)
		cellLETTURAINIZIALE.setCellValue(item.getLetturaIniziale().doubleValue());
	    // LETTURA FINALE
	    HSSFCell cellLETTURAFINALE = r.createCell(8);
	    cellLETTURAFINALE.setCellStyle(csFalse);
	    cellLETTURAFINALE.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    if (item.getLetturaFinale() != null)
		cellLETTURAFINALE.setCellValue(item.getLetturaFinale().doubleValue());
	    // CONSUMO
	    HSSFCell cellCONSUMO = r.createCell(9);
	    cellCONSUMO.setCellStyle(csFalse);
	    cellCONSUMO.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    if (item.getConsumo() != null)
		cellCONSUMO.setCellValue(item.getConsumo().doubleValue());
	    // IMPORTO
	    HSSFCell cellIMPORTO = r.createCell(10);
	    cellIMPORTO.setCellStyle(csFalse);
	    cellIMPORTO.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	    if (item.getImporto() != null)
		cellIMPORTO.setCellValue(item.getImporto().doubleValue());
	}
	// Auto dimimensiona la larghezza delle colonne
	sheet.autoSizeColumn((short) 1);
	sheet.autoSizeColumn((short) 2);
	sheet.autoSizeColumn((short) 3);
	sheet.autoSizeColumn((short) 4);
	sheet.autoSizeColumn((short) 5);
	sheet.autoSizeColumn((short) 6);
	sheet.autoSizeColumn((short) 7);
	sheet.autoSizeColumn((short) 8);
	sheet.autoSizeColumn((short) 9);
	sheet.autoSizeColumn((short) 10);
	// /////////////////////////////////////////////
	// ///VALIDAZIONE DEI CAMPI DA INSERIRE//////////
	// ///Validazione per lettura iniziale,lettura finale e consumo///
	org.apache.poi.ss.util.CellRangeAddressList addressList = new org.apache.poi.ss.util.CellRangeAddressList(1, rowcount, 7, 9);
	DVConstraint dvConstraint = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.DECIMAL, DVConstraint.OperatorType.BETWEEN, "0",
		"99999.99999");
	HSSFDataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
	dataValidation.setSuppressDropDownArrow(false);
	dataValidation.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
	dataValidation
		.createErrorBox(
			"Errore Validazione",
			"Deve essere inserito un valore numerico corretto.\nIl valore deve essere compreso tra 0 e 99999.99999.\nValore numerico fuori dai limiti (atteso <5 cifre>,<5 cifre>)");
	sheet.addValidationData(dataValidation);
	// ///Validazione per importo///
	org.apache.poi.ss.util.CellRangeAddressList addressList2 = new org.apache.poi.ss.util.CellRangeAddressList(1, rowcount, 10, 10);
	DVConstraint dvConstraint2 = DVConstraint.createNumericConstraint(DVConstraint.ValidationType.DECIMAL, DVConstraint.OperatorType.BETWEEN,
		"0", "99999999.99");
	HSSFDataValidation dataValidation2 = new HSSFDataValidation(addressList2, dvConstraint2);
	dataValidation2.setSuppressDropDownArrow(false);
	dataValidation2.setErrorStyle(HSSFDataValidation.ErrorStyle.STOP);
	dataValidation2
		.createErrorBox(
			"Errore Validazione",
			"Deve essere inserito un valore numerico corretto.\nIl valore deve essere compreso tra 0 e 99999999.99.\nValore numerico fuori dai limiti (atteso <8 cifre>,<2 cifre>)");
	sheet.addValidationData(dataValidation2);
	// //////////////////////////////////////////////
	// pwd per bloccare il foglio Excel
	sheet.protectSheet(WebConstants.PWD_EXCEL_SHEET);
	return workbook;
    }

    @RequestMapping
    public String createImport(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	// RECUPERO MANIFESTAZIONE E USO//
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// ////////////////////////////////
	model.addAttribute("file", new FileUpload());
	return "mercatidletture/importExcel";
    }

    /**
     * Metodo che importa un file excel.il metodo aggiorna le righe di mercatidLetture.
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param model
     * @param file
     * @param result
     * @param status
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping
    public String uploadExcel(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso, Model model,
	    @ModelAttribute("file") FileUpload file, BindingResult result, SessionStatus status, HttpServletRequest request,
	    HttpServletResponse response) {

	// RECUPERO MANIFESTAZIONE E USO//
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// ////////////////////////////////
	List<MercatidLetture> listImport = new ArrayList<MercatidLetture>();
	try {
	    InputStream inp = file.getFile().getInputStream();
	    HSSFWorkbook wb = new HSSFWorkbook(inp);
	    HSSFSheet sheet = wb.getSheetAt(0); // first sheet
	    int numeroRighe = sheet.getLastRowNum();
	    numeroRighe++;
	    for (int i = 1; i < numeroRighe; i++) {
		HSSFRow row = sheet.getRow(i); // first row
		HSSFCell cell = row.getCell(0); // ID
		PkId id = new PkId(((Double) cell.getNumericCellValue()).intValue());
		MercatidLetture letture = mercatidLettureService.findById(id);
		HSSFCell cell7 = row.getCell(7); // Lettura iniziale
		// try-catch per controllare che i valori inseriti siano valori numerici
		try {
		    BigDecimal letturainiziale = new BigDecimal(cell7.getNumericCellValue());
		    letturainiziale = letturainiziale.setScale(2, BigDecimal.ROUND_HALF_UP);
		    letture.setLetturaIniziale(letturainiziale);
		} catch (Exception e) {
		    letture.setLetturaIniziale(null);
		}
		HSSFCell cell8 = row.getCell(8); // Lettura finale
		// try-catch per controllare che i valori inseriti siano valori numerici
		try {
		    BigDecimal letturafinale = new BigDecimal(cell8.getNumericCellValue());
		    letturafinale = letturafinale.setScale(2, BigDecimal.ROUND_HALF_UP);
		    letture.setLetturaFinale(letturafinale);
		} catch (Exception e) {
		    letture.setLetturaFinale(null);
		}
		HSSFCell cell9 = row.getCell(9); // Consumo
		// try-catch per controllare che i valori inseriti siano valori numerici
		try {
		    BigDecimal consumo = new BigDecimal(cell9.getNumericCellValue());
		    consumo = consumo.setScale(2, BigDecimal.ROUND_HALF_UP);
		    letture.setConsumo(consumo);
		} catch (Exception e) {
		    letture.setConsumo(null);
		}
		// try-catch per controllare che i valori inseriti siano valori numerici
		HSSFCell cell10 = row.getCell(10); // Importo
		try {
		    BigDecimal importo = new BigDecimal(cell10.getNumericCellValue());
		    importo = importo.setScale(2, BigDecimal.ROUND_HALF_UP);
		    letture.setImporto(importo);
		} catch (Exception e) {
		    letture.setImporto(null);
		}
		listImport.add(letture);
	    }
	    mercatidLettureService.updateImportLetture(listImport);
	} catch (Exception e) {
	    String errorString = "Errore nel upload del file excel :" + " Controllare che il file importato sia corretto.";
	    model.addAttribute("file", new FileUpload());
	    model.addAttribute("errorString", errorString);
	    return "mercatidletture/importExcel";
	}
	return "redirect:createLettureContatore.htm?mercati.id.codice=" + mercati.getId().getCodice() + "&mercatoUso=" + uso.getId().getCodice();
    }

    @RequestMapping
    public String createRegistrazioni(@RequestParam("mercati.id.codice") Integer codiceMercato, @RequestParam("mercatoUso") Integer codiceUso,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// RECUPERO MANIFESTAZIONE E USO//
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	model.addAttribute("mercato", mercati);
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	model.addAttribute("uso", uso);
	// ////////////////////////////////
	// RECUPERO IMPOSTAZIONE UTENTE//
	LettureContatoriCommand command = new LettureContatoriCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	ConfigurazioneutenteId confUteId = new ConfigurazioneutenteId(responsabile.getId().getCodice(),
		WebConstants.CONF_UTENTE_RICERCA_LETTURE_CONTATORI);
	Configurazioneutente configurazioneutente = configurazioneutenteService.findById(confUteId);
	List<MercatidLetture> mercatidLettureList = null;
	if (configurazioneutente == null) {
	    // LISTA DELLE LETTURE//
	    mercatidLettureList = mercatidLettureService.findAll(null, null);
	} else {
	    MercatidLetture mercatidLetture = getMercatidLettureFilter(configurazioneutente);
	    uso.setMercati(mercati);
	    mercatidLetture.setMercatiUso(uso);
	    // LISTA DELLE LETTURE//
	    mercatidLettureList = mercatidLettureService.findByLettureConImporto(mercatidLetture);
	}
	// ////////////////////////////////
	List<MercatidLetture> lettureList = new ArrayList<MercatidLetture>();
	for (MercatidLetture mercatidLetture2 : mercatidLettureList) {
	    mercatidLetture2.setCreaRegistrazioni(true);
	    lettureList.add(mercatidLetture2);
	}
	command.setMercatidLettureList(lettureList);
	model.addAttribute("command", command);
	List<Oneritipirateizzazione> rateList = oneritipirateizzazioneService.findAllSenzaInteressiLegali();
	model.addAttribute("rateList", rateList);
	return "mercatidletture/registrazioniLetture";
    }

    @Override
    protected void fixMergeEntityProperty(MercatidLetture entity) {

	if (entity.getMercatiUso() != null && entity.getMercatiUso().getId() != null && entity.getMercatiUso().getId().getCodice() == null) {
	    entity.setMercatiUso(null);
	}
	if (entity.getPosteggio() != null && entity.getPosteggio().getId() != null && entity.getPosteggio().getId().getCodice() == null) {
	    entity.setPosteggio(null);
	}
	if (entity.getTipiContatore() != null && entity.getTipiContatore().getId() == null) {
	    entity.setTipiContatore(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(MercatidLetture entity) {

	if (entity.getMercatiUso() == null) {
	    entity.setMercatiUso(new MercatiUso());
	}
	if (entity.getPosteggio() == null) {
	    entity.setPosteggio(new MercatiD());
	}
	if (entity.getTipiContatore() == null) {
	    entity.setTipiContatore(new TipiContatore());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
