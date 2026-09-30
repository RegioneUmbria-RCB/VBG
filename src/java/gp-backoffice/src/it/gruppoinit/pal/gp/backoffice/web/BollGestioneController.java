package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.EsportazioniPentahoCommand;
import it.gruppoinit.pal.gp.core.features.bollettazione.IVerticalizzazioneBollettazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.AnagraficaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneLettereService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneIstanzeService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneMercatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloBollettazioneService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollCfgTipo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioPeriodicitaHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioRateizzazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioRigaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ElementoListaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaBollettazione;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiManager;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.BollCfgTipoService;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

@Controller
@SessionAttributes(value = { "elementoListaBollettazione", "bollGestTestata", "esportazioniPentahoCommand" })
public class BollGestioneController extends BaseJsonController<BollCfgTipo> {

    @Autowired
    private CalcoloBollettazioneIstanzeService calcoloBollettazioneIstanzeService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private BollCfgTipoService bollCfgTipoService;
    @Autowired
    private CalcoloBollettazioneMercatiService calcoloBollettazioneMercatiService;
    @Autowired
    private CalcoloBollettazioneService calcoloBollettazioneService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private ResponsabilicomuniService responsabiliComuniService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private BollGestDettaglioService bollGestDettaglioService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private BollettazioneLettereService bollettazioneLettereService;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    @Autowired
    private IVerticalizzazioneBollettazioneService verticalizzazioneBollettazioneService;
    private static final Logger log = LoggerFactory.getLogger(BollGestioneController.class);

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	Integer codiceResponsabile = getCurrentlyAuthenticatedUserDetails().getId().getCodice();
	List<ElementoListaBollettazione> elementoListaBollettazioniList = calcoloBollettazioneService.findByCodiceResponsabile(codiceResponsabile,
		null, null);
	ModelMap model = new ModelMap(elementoListaBollettazioniList);
	boolean export = createJMesaExport(request, response, elementoListaBollettazioniList);
	if (export) {
	    return null;
	}
	model.addAttribute("mostraFunzioneComunicazioniMassive", verticalizzazioneBollettazioneService.mostraFunzioneComunicazioniMassive());
	model.addAttribute("elementoListaBollettazioniList", elementoListaBollettazioniList);
	return model;
    }

    @RequestMapping
    public String create(Model model, HttpServletRequest request, HttpServletResponse response) {

	setPageAttributes(model);
	Integer codiceResponsabile = getCurrentlyAuthenticatedUserDetails().getId().getCodice();
	CreazioneBollTestata creazioneBollTestata = calcoloBollettazioneService.inizializzaCreazioneBollTestata(codiceResponsabile);
	model.addAttribute("bollGestTestata", creazioneBollTestata);
	return "bollgestione/formCreazione";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String insert(Model model, @ModelAttribute("bollGestTestata") CreazioneBollTestata creazioneBollTestata, BindingResult bindingResult,
	    HttpServletRequest request, SessionStatus status) {

	if (bindingResult.hasErrors()) {
	    return "bollgestione/formCreazione";
	}
	try {
	    Integer codiceResponsabile = getCurrentlyAuthenticatedUserDetails().getId().getCodice();
	    ImplementazioniEnum tipo = bollCfgTipoService.findImplementazioneByTipo(creazioneBollTestata.getBollCfgTipoId());
	    if (tipo.equals(ImplementazioniEnum.ISTANZE)) {
		creazioneBollTestata.setInterventi(getScCodiciIntervento(request));
	    }
	    Integer idBollettazione = new CalcoloBollettazioneFactory(bollCfgTipoService, calcoloBollettazioneIstanzeService,
		    calcoloBollettazioneMercatiService).creaBollettazione(creazioneBollTestata, codiceResponsabile);
	    if (idBollettazione == null) {
		throw new BusinessValidationException("La ricerca non ha prodotto alcun risultato");
	    }
	    status.setComplete();
	    return "redirect:view.htm?codice=" + idBollettazione + "&status_msg=01";
	} catch (Exception e) {
	    log.error("Errore nella creazione della bollettazione", e);
	    model.addAttribute("IS_ERRORE", Boolean.TRUE);
	    copyErrorsToBindingResult(bindingResult, creazioneBollTestata, e);
	    setPageAttributes(model);
	    return "bollgestione/formCreazione";
	}
    }

    @SuppressWarnings("unchecked")
    private Set<String> getScCodiciIntervento(HttpServletRequest request) {

	Set<String> scCodiciIntervento = new HashSet<String>();
	Enumeration<String> en = request.getParameterNames();
	while (en.hasMoreElements()) {
	    String paramName = en.nextElement();
	    if (paramName.startsWith("scCodice_")) {
		try {
		    if (StringUtils.isNotBlank(request.getParameter(paramName))) {
			scCodiciIntervento.add(request.getParameter(paramName));
		    }
		} catch (Exception e) {
		    // non fa niente
		}
	    }
	}
	return scCodiciIntervento;
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codiceBollettazione, Model model, HttpServletRequest request) {

	log.debug("Inizio la creazione del model");
	DettaglioBollettazione dettaglioBollettazione = this.calcoloBollettazioneService.findById(codiceBollettazione);
	log.debug("Creazione del model terminata");
	List<Conti> contiList = contiService.findAllActive();
	if (dettaglioBollettazione == null) {
	    return "bollgestione/list";
	}
	model.addAttribute("visualizzaBottoneDettaglio", dettaglioBollettazione.isVisualizzaDettaglioBollettazione());
	model.addAttribute("dettaglioBollettazione", dettaglioBollettazione);
	model.addAttribute("contiList", contiList);
	setPageAttributes(model);
	return "bollgestione/form";
    }

    @RequestMapping
    public String ajaxRicaricaBlocco(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("idAnagrafica") Integer idAnagrafica) {

	List<RigaBollettazione> righeBollettazioneList = this.calcoloBollettazioneService.findDettaglioByBollettazioneAndAnagrafe(idBollettazione,
		idAnagrafica);
	model.addAttribute("righeBollettazioneList", righeBollettazioneList);
	return "bollgestione/dettaglioTabellaAnagrafe";
    }

    @RequestMapping
    public void ajaxVerificaTipoBollettazione(Model model, @ModelAttribute("bollGestTestata") CreazioneBollTestata creazioneBollTestata,
	    @RequestParam("id") Integer id, HttpServletRequest erquest, HttpServletResponse response) throws IOException {

	BollCfgTipo b = this.bollCfgTipoService.findById(new PkId(id));
	String res = "non presente";
	if (b != null) {
	    res = b.getImplementazione();
	}
	IntervalloDate intervalloDate = calcoloBollettazioneService.getIntervalloDateDaBollCfgTipo(id, new Date());
	String dataInizio = Utilities.formatDate(intervalloDate.getDataInizio(), false);
	String dataFine = Utilities.formatDate(intervalloDate.getDataFine(), false);
	String json = "{\"implementazione\": \"" + res + "\",\"data_inizio\": \"" + dataInizio + "\",\"data_fine\": \"" + dataFine + "\"}";
	creazioneBollTestata.setIntervalloDate(intervalloDate);
	response.setContentType("application/json");
	response.getOutputStream().write(json.getBytes());
    }

    @RequestMapping
    public String ajaxDettaglioRiga(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("idAnagrafica") Integer idAnagrafica, @RequestParam("idRiga") Integer idRiga) {

	DettaglioRigaBollettazione rigaDettaglioBollettazione = this.calcoloBollettazioneService
		.findDettaglioRigaByBollettazioneAndAnagrafe(idBollettazione, idAnagrafica, idRiga);
	model.addAttribute("rigaDettaglioBollettazione", rigaDettaglioBollettazione);
	return "bollgestione/ajaxDettaglioRiga";
    }

    @RequestMapping
    public String ajaxDettaglioRateizzazione(Model model, @RequestParam("idRiga") Integer idRiga) {

	List<DettaglioRateizzazione> rate = this.calcoloBollettazioneService.findDettaglioRateizzazione(idRiga);
	model.addAttribute("rate", rate);
	return "bollgestione/ajaxDettaglioRateizzazione";
    }

    @RequestMapping
    public String ajaxDettaglioRateizzazioneAnagrafica(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("idAnagrafica") Integer idAnagrafica) {

	List<DettaglioRateizzazione> rate = this.calcoloBollettazioneService.findDettaglioRateizzazionePerAnagrafica(idBollettazione, idAnagrafica);
	model.addAttribute("rate", rate);
	return "bollgestione/ajaxDettaglioRateizzazione";
    }

    @RequestMapping
    public void ajaxAggiornaStatoPagamenti(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("idAnagrafica") Integer idAnagrafica, HttpServletRequest request, HttpServletResponse response) throws IOException {

	this.calcoloBollettazioneService.aggiornaStatoPagamento(idBollettazione, idAnagrafica);
	response.getOutputStream().write("OK".getBytes());
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxAggiornaScadenza(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("dataScadenza") Date dataScadenza, HttpServletRequest request, HttpServletResponse response) throws IOException {

	this.calcoloBollettazioneService.aggiornaDataScadenza(idBollettazione, dataScadenza);
	response.getOutputStream().write("OK".getBytes());
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxCancellaRiga(@RequestParam("idBollettazione") Integer idBollettazione, @RequestParam("idAnagrafica") Integer idAnagrafica,
	    @RequestParam("idRiga") Integer idRiga, HttpServletRequest request, HttpServletResponse response) throws IOException {

	String userName = getCurrentlyAuthenticatedUserDetails().getResponsabile();
	this.calcoloBollettazioneService.deleteDettaglio(idRiga, userName);
	response.setContentType("text/plain");
	response.getOutputStream().write("OK".getBytes());
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxRettificaRiga(@RequestParam("idBollettazione") Integer idBollettazione, @RequestParam("idAnagrafica") Integer idAnagrafica,
	    @RequestParam("idRiga") Integer idRiga, @RequestParam("importoSenzaIVA") BigDecimal nuovoImportoSenzaIVA,
	    @RequestParam("iva") Integer nuovaIVA, @RequestParam("importo") BigDecimal nuovoImporto, HttpServletRequest request,
	    HttpServletResponse response) {

	String userName = getCurrentlyAuthenticatedUserDetails().getResponsabile();
	this.calcoloBollettazioneService.rettificaRiga(idBollettazione, idAnagrafica, nuovoImportoSenzaIVA, nuovaIVA, nuovoImporto, idRiga, userName);
    }

    @RequestMapping
    public void ajaxAggiungiRiga(@RequestParam("idBollettazione") Integer idBollettazione, @RequestParam("idAnagrafica") Integer idAnagrafica,
	    @RequestParam("idConto") Integer idConto, @RequestParam("descrizione") String descrizione,
	    @RequestParam("importoSenzaIVA") BigDecimal importoSenzaIVA, @RequestParam("iva") Integer iva,
	    @RequestParam("importo") BigDecimal importo, @RequestParam("noteUtente") String noteUtente, HttpServletRequest request,
	    HttpServletResponse response) {

	String userName = getCurrentlyAuthenticatedUserDetails().getResponsabile();
	this.calcoloBollettazioneService.aggiungiRiga(idBollettazione, idAnagrafica, idConto, userName, descrizione, importoSenzaIVA, iva, importo,
		noteUtente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String delete(Model model, @ModelAttribute("dettaglioBollettazione") DettaglioBollettazione dettaglioBollettazione,
	    HttpServletRequest request) {

	try {
	    this.calcoloBollettazioneService.delete(dettaglioBollettazione.getId());
	} catch (Exception e) {
	    log.error("errore in cancellazione della bollettazione", e);
	    setPageAttributes(model);
	    return "bollgestione/form";
	}
	return "redirect:list.htm";
    }

    @RequestMapping
    public String ajaxValidaRighe(@RequestParam("idBollettazione") Integer idBollettazione, @RequestParam("validato") Boolean validato,
	    HttpServletRequest request, HttpServletResponse response) {

	String user = getCurrentlyAuthenticatedUserDetails().getResponsabile();
	this.calcoloBollettazioneService.validaInteraBollettazione(idBollettazione, validato, user);
	return null;
    }

    @RequestMapping
    public String ajaxValida(@RequestParam("idRiga") Integer idRiga, @RequestParam("validato") Boolean validato, HttpServletRequest request,
	    HttpServletResponse response) {

	String user = getCurrentlyAuthenticatedUserDetails().getResponsabile();
	this.calcoloBollettazioneService.validaDettaglioBollettazione(idRiga, validato, user);
	return null;
    }

    private Set<String> elaborazioniNodoAttive = new HashSet<String>();

    private String getKeyElaborazioniAttive(Integer idTestataBollettazione) {

	return ORMHelper.getIdcomuneAlias() + "-" + idTestataBollettazione;
    }

    @RequestMapping
    public void ajaxBollettazioneElaborazioneAttiva(@RequestParam("idBollettazioneTestata") Integer idBollettazioneTestata, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	String keyElaborazioneAttiva = getKeyElaborazioniAttive(idBollettazioneTestata);
	boolean elaborazioneAttiva = elaborazioniNodoAttive.contains(keyElaborazioneAttiva);
	response.setContentType("application/json");
	response.getOutputStream().write(("{\"in_elaborazione\":" + elaborazioneAttiva + "}").getBytes());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bollgestione/inviaPosizioneDebitoria.htm")
    public void inviaPosizioneDebitoria(Model model, @ModelAttribute("dettaglioBollettazione") DettaglioBollettazione dettaglioBollettazione,
	    HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException, JAXBException {

	Date dataScadenzaUi = dettaglioBollettazione.getDataScadenza();
	Date dataScadenzaDB = this.calcoloBollettazioneService.getDataScadenzaBollettazione(dettaglioBollettazione.getId());
	if (!Utilities.formatDate(dataScadenzaUi, false).equals(Utilities.formatDate(dataScadenzaDB, false))) {
	    response.setContentType("application/json");
	    String etichettaDataScadenza = getMessageFromBundle("dettaglioBollettazione.label.dataScadenza", null);
	    String etichettaDataScadenzaAggiorna = getMessageFromBundle("label.aggiornaDataScadenza", null);
	    response.getOutputStream().write(toJsonBytes(new CodiceDescrizioneBean("KO", "La " + etichettaDataScadenza + //
											 " inviata [" + Utilities.formatDate(dataScadenzaUi, false) + //
											 "] non corrisponde al valore salvato su Database [" +
											 Utilities.formatDate(dataScadenzaDB, false) +
											 "]. Ricordarsi di salvare il valore di " +
											 etichettaDataScadenza + " mediante il pulsante " +
											 etichettaDataScadenzaAggiorna),
		    false));
	    return;
	}
	String keyElaborazioneAttiva = getKeyElaborazioniAttive(dettaglioBollettazione.getId());
	boolean elaborazioneAttiva = elaborazioniNodoAttive.contains(keyElaborazioneAttiva);
	CodiceDescrizioneBean cdb = new CodiceDescrizioneBean("OK", "");
	if (!elaborazioneAttiva) {
	    elaborazioniNodoAttive.add(keyElaborazioneAttiva);
	    try {
		String error = this.calcoloBollettazioneService.inviaNodoPagamenti(dettaglioBollettazione.getId());
		if (StringUtils.isNotBlank(error)) {
		    cdb.setCodice("KO");
		    cdb.setDescrizione(error);
		}
	    } catch (Exception e) {
		log.error("errore nell'invio al nodo dei pagamenti", e);
		cdb.setCodice("KO");
		cdb.setDescrizione(e.getMessage());
	    } finally {
		elaborazioniNodoAttive.remove(keyElaborazioneAttiva);
	    }
	} else {
	    cdb.setCodice("KO");
	    cdb.setDescrizione("Attenzione! È in corso un'altra elaborazione di invio al nodo pagamenti. Attendere la conclusione dell'attività.");
	}
	response.setContentType("application/json");
	response.getOutputStream().write(toJsonBytes(cdb, false));
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	List<CreazioneBollCfgTipo> bollCfgTipo = this.calcoloBollettazioneService
		.findBollCfgTipoByCodiceResponsabile(responsabili.getId().getCodice());
	model.addAttribute("bollCfgTipo", bollCfgTipo);
	model.addAttribute("mostraFunzioneRettifica", verticalizzazioneBollettazioneService.mostraFunzioneRettifica());
	model.addAttribute("mostraFunzioneAggiungiRiga", verticalizzazioneBollettazioneService.mostraFunzioneAggiungiRiga());
	model.addAttribute("mostraFunzioneComunicazioniMassive", verticalizzazioneBollettazioneService.mostraFunzioneComunicazioniMassive());
    }

    @Override
    protected void fixMergeEntityProperty(BollCfgTipo entity) {

    }

    @Override
    protected void fixRenderEntityProperty(BollCfgTipo entity) {

    }

    @Autowired
    private NodoPagamentiManager nodoPagamentiManager;

    @RequestMapping
    public void ajaxTest(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	String[] defaultStatiNonConclusiviString = c.getDefaultStatiNonConclusiviString();
	nodoPagamentiManager.aggiornaStatoPosizioniDebitorieInStati(defaultStatiNonConclusiviString);
	response.getOutputStream().write("OK".getBytes());
	response.getOutputStream().flush();
    }

    @RequestMapping
    public String ajaxCalcolaPeriodo(Model model, @ModelAttribute("bollGestTestata") CreazioneBollTestata creazioneBollTestata,
	    @RequestParam("bollCfgTipoId") Integer bollCfgTipoId, @RequestParam(value = "anno", required = false) Integer anno,
	    HttpServletRequest request, HttpServletResponse response) {

	PeriodiEnum periodicita = this.bollCfgTipoService.findPeriodiByTipo(bollCfgTipoId);
	GregorianCalendar calendar = new GregorianCalendar();
	if (anno == null) {
	    anno = calendar.get(Calendar.YEAR);
	}
	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(anno, periodicita);
	List<CodiceDescrizioneBean> anniCalcolaPeriodi = new ArrayList<CodiceDescrizioneBean>();
	int annoCorrente = calendar.get(Calendar.YEAR);
	int annoPrecedente = annoCorrente - 9;
	for (int i = annoPrecedente; i <= annoCorrente; i++) {
	    CodiceDescrizioneBean codiceDescrizioneBean = new CodiceDescrizioneBean();
	    codiceDescrizioneBean.setCodice(String.valueOf(i));
	    codiceDescrizioneBean.setDescrizione(String.valueOf(i));
	    anniCalcolaPeriodi.add(codiceDescrizioneBean);
	}
	model.addAttribute("dettaglioPeriodicitaHelper", dettaglioPeriodicitaHelper);
	model.addAttribute("anniCalcolaPeriodi", anniCalcolaPeriodi);
	return "bollgestione/ajaxcalcolaperiodo";
    }

    @RequestMapping
    public void ajaxAggiornaPeriodo(Model model, @RequestParam("anno") Integer anno, @RequestParam("chiave") String chiave,
	    HttpServletRequest request, HttpServletResponse response) {

	String[] date = chiave.split("-");
	SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
	Date dataInizio = null;
	Date dataFine = null;
	try {
	    dataInizio = sdf.parse(date[0] + anno);
	    dataFine = sdf.parse(date[1] + anno);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	String dataI = Utilities.formatDate(dataInizio, false);
	String dataF = Utilities.formatDate(dataFine, false);
	String json = "{\"data_inizio\": \"" + dataI + "\",\"data_fine\": \"" + dataF + "\"}";
	response.setContentType("application/json");
	try {
	    response.getOutputStream().write(json.getBytes());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    //// metodi per export pentaho
    /**
     * Crea il pannello per la scelta delle opzioni per l'export della bollettazione tramite il componente esterno
     * Pentaho
     * 
     * @param model
     * @param idBollettazione
     * @param codiceEsportazione
     * @param comune
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String comune, HttpServletRequest request, HttpServletResponse response) {

	EsportazioniPentahoCommand esportazioniPentahoCommand = new EsportazioniPentahoCommand();
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	esportazioniPentahoCommand.setResponsabili(responsabile);
	List<Esportazioni> listaEsportazioni = null;
	if (StringUtils.isBlank(codiceEsportazione)) {
	    listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.BOLLETTAZIONE);
	    if (!listaEsportazioni.isEmpty()) {
		esportazioniPentahoCommand.setEsportazioni(listaEsportazioni.get(0));
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(comune, Integer.parseInt(codiceEsportazione));
	    ids.add(id);
	    listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.BOLLETTAZIONE, ids);
	    Esportazioni esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	    esportazioniPentahoCommand.setEsportazioni(esportazioni);
	}
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("esportazioniPentahoCommand", esportazioniPentahoCommand);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	model.addAttribute("idBollettazione", idBollettazione);
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	List<Responsabilicomuni> responsabilicomuni = responsabiliComuniService.findByOperatore(responsabili);
	String[] codiceComuni = new String[responsabilicomuni.size()];
	int i = 0;
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	return "bollgestione/exportBollettazionePentaho";
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("esportazioniPentahoCommand") EsportazioniPentahoCommand esportazioniPentahoCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca i dettagli bollettazione e li salva sulla tabella TMP_ESPORTAZIONI ");
	String sessionId = bollGestDettaglioService.exportModalitaPentaho(idBollettazione, esportazioniPentahoCommand.getEsportazioni(),
		StringUtils.defaultIfEmpty(emailResponsabile, ""), BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di pentaho");
	try {
	    Esportazioni e = esportazioniService.findById(new PkId(esportazioniPentahoCommand.getEsportazioni().getId().getCodice()));
	    String pathFile = pentahoService.callTrasformazione(e.getTrasformazione(),
		    esportazioniPentahoCommand.getEsportazioni().getParametriesportaziones(), sessionId, response);
	    if (BooleanUtils.isTrue(isInviaMail) && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), "id.codice") != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    Integer idAccountInt = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			idAccountInt = Integer.parseInt(idAccount);
		    }
		    String codiceComune = null;
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), idAccountInt, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho# Mail/Tipo  non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + esportazioniPentahoCommand.getEsportazioni().getDescrizione() + ". Err: ",
		    e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    @RequestMapping
    public void ajaxPreviewLettera(Model model, @RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("dettIdPosizioneDebitoria") Integer dettIdPosizioneDebitoria, HttpServletResponse response) throws IOException {

	DettPosizioneDebitoria dp = dettPosizioneDebitoriaService.findById(new PkId(dettIdPosizioneDebitoria));
	InputStream lettera = bollettazioneLettereService.generaLetteraAccompagnamento(dp.getCfEnteCreditore(), dp.getIdPosizioneDebitoria(), true);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-Disposition", "attachment; filename=\"avvisatura_" + dettIdPosizioneDebitoria + ".pdf\"");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType("application/pdf");
	ServletOutputStream out = response.getOutputStream();
	IOUtils.copy(lettera, out);
	out.flush();
    }

    @RequestMapping
    public void ajaxGetDettaglioBollettazione(@RequestParam("idBollettazione") Integer idBollettazione, HttpServletResponse response)
	    throws IOException, JAXBException {

	DettaglioBollettazione dettaglioBollettazione = this.calcoloBollettazioneService.findById(idBollettazione);
	// il ciclo serve per visualizzare nel front le proprietà altrimenti il marshaller non funziona
	for (AnagraficaBollettazione anagBoll : dettaglioBollettazione.getAnagraficaBollettazioneList()) {
	    anagBoll.setDettaglioPosizioneDebitoriaPresente(anagBoll.isDettaglioPosizioneDebitoriaPresente());
	    anagBoll.setPosizioniDebitorie(new ArrayList<Integer>(anagBoll.getPosizioniDebitorie()));
	    anagBoll.setDaRateizzare(anagBoll.isDaRateizzare());
	    for (RigaBollettazione rigaBoll : anagBoll.getRigheBollettazioneList()) {
		if (anagBoll.isDettaglioPosizioneDebitoriaPresente()) {
		    rigaBoll.setSupportaRettifica(false);
		    rigaBoll.setSupportaValidazione(false);
		    rigaBoll.setSupportaCancellazione(false);
		    rigaBoll.setDaRateizzare(false);
		} else {
		    rigaBoll.setSupportaRettifica(rigaBoll.getSupportaRettifica());
		    rigaBoll.setSupportaValidazione(rigaBoll.getSupportaValidazione());
		    rigaBoll.setSupportaCancellazione(rigaBoll.getSupportaCancellazione());
		    rigaBoll.setDaRateizzare(rigaBoll.isDaRateizzare());
		}
	    }
	}
	response.setContentType("application/json");
	response.getOutputStream().write(this.toJsonBytes(dettaglioBollettazione));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxGetSummaryBollettazione(@RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, HttpServletResponse response) throws IOException, JAXBException {

	Map<String, String> jsonMap = new HashMap<String, String>();
	ObjectMapper mapper = new ObjectMapper();
	try {
	    boolean soloValidate = !calcoloBollettazioneService.findPosizioniDebitorieByBollettazioneEAnagrafe(idBollettazione, codiceAnagrafe)
		    .isEmpty();
	    String html = calcoloBollettazioneService.getHtmlForBollettazione(idBollettazione, codiceAnagrafe, false, ORMHelper.getIdcomuneAlias(),
		    ORMHelper.getSoftware(), soloValidate);
	    jsonMap.put("html", html);
	} catch (Exception e) {
	    log.error("Errore nella produzione dell'html dettaglio ", e);
	    jsonMap.put("errore", e.getMessage());
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	String json = mapper.writeValueAsString(jsonMap);
	response.setContentType("application/json");
	response.getOutputStream().write(json.getBytes("UTF-8"));
	response.getOutputStream().flush();
    }

    @RequestMapping
    public void ajaxGetSummaryBollettazionePdf(@RequestParam("idBollettazione") Integer idBollettazione,
	    @RequestParam("codiceAnagrafe") Integer codiceAnagrafe, HttpServletResponse response) throws IOException, JAXBException {

	try {
	    boolean soloValidate = !calcoloBollettazioneService.findPosizioniDebitorieByBollettazioneEAnagrafe(idBollettazione, codiceAnagrafe)
		    .isEmpty();
	    byte[] outpdf = calcoloBollettazioneService.getPdfReportForBollettazione(idBollettazione, codiceAnagrafe, ORMHelper.getIdcomuneAlias(),
		    ORMHelper.getSoftware(), soloValidate);
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=report-" + idBollettazione + "-" + codiceAnagrafe + ".pdf");
	    response.setContentLength(outpdf.length);
	    // Scrivi i byte nel corpo della risposta
	    response.getOutputStream().write(outpdf);
	    response.flushBuffer();
	} catch (Exception e) {
	    log.error("Errore durante il recupero del pdf", e);
	    throw new RuntimeException("Errore durante il recupero del pdf: " + e.getMessage(), e);
	}
    }
}
