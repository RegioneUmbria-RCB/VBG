package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniconfigurazioniHelper;
import it.gruppoinit.pal.gp.core.domain.web.AzioniProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo.AmministrazioneProtocolloModel;
import it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo.AmministrazioneProtocolloResponse;
import it.gruppoinit.pal.gp.core.features.amministrazioni.configurazione.protocollo.AmministrazioniProtocolloRequest;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.ErroreType;

@Controller
@SessionAttributes(value = { "azioniProtocollazioneCommand" })
public class AzioniprotocolloController extends BaseJsonController<AzioniProtocollazioneCommand> {

    private static final Logger log = LoggerFactory.getLogger(AzioniprotocolloController.class);
    private static final String SOFTWARE_ATTIVO_IN_SESSION = "_AZIONI_PROTOCOLLO_SOFTWARE_ATTIVO";
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private ProtocollazioneService protocollazioneService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ProtocolloFlussoService protocolloFlussoService;
    @Autowired
    private ResponsabilicomuniService responsabilicomuniService;
    private AmministrProtocolloService amministrProtocolloService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;

    @Autowired
    public void setAmministrProtocolloService(AmministrProtocolloService amministrProtocolloService) {

	this.amministrProtocolloService = amministrProtocolloService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @RequestMapping
    public String search(Model model, HttpServletRequest request) throws Exception {

	// §§§BEGIN§§§
	// il software lo comanda il link che lancia la funzionalità
	// è possibile che da TT passo a SS e torni in questo caso devo settare TT
	if (request.getParameter(WebConstants.SOFTWARE) != null) {
	    request.getSession().setAttribute(SOFTWARE_ATTIVO_IN_SESSION, request.getParameter(WebConstants.SOFTWARE));
	} else {
	    // riporto il software settato nella session
	    // ricarica impostando il software a TT
	    String qs = populateQsForSoftwareTT(request);
	    return "redirect:search.htm?" + qs;
	}
	model.addAttribute("azioniProtocollazioneCommand", new AzioniProtocollazioneCommand());
	setPageAttributes(model);
	// §§§END§§§
	return "azioniprotocollo/search";
    }

    @RequestMapping
    public ModelAndView ajaxCalcolaCodicePraticaTel(@ModelAttribute(value = "azioniProtocollazioneCommand") AzioniProtocollazioneCommand cmd,
	    HttpServletRequest request) {

	String error = "";
	StringBuilder sb = new StringBuilder();
	Istanze i = cmd.getEntity();
	if (null != i) {
	    String data = cmd.getDatiProtocolloLetto().getDataProtocollo();
	    if (null == data) {
		data = Utilities.getToday("ddMMyyyy-HHmm");
	    } else {
		GregorianCalendar d = Utilities.getDate(data, WebConstants.DATE_FORMAT_PATTERN);
		d.set(Calendar.HOUR, 8);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		data = Utilities.formatDate(d.getTime(), "ddMMyyyy-HHmm");
	    }
	    Anagrafe azienda = i.getTitolareLegaleORichiedente();
	    if (StringUtils.isEmpty(error)) {
		if (null != azienda && null != azienda.getId() && null != azienda.getId().getCodice()) {
		    azienda = anagrafeService.findById(new PkId(azienda.getId().getCodice()));
		}
		if (null == azienda || null == azienda.getId() || null == azienda.getId().getCodice()) {
		    error = getMessageFromBundle("pecinbox.message.errorecodicepraticatel.nocf", new Object[0]);
		}
	    }
	    if (StringUtils.isEmpty(error)) {
		if (StringUtils.isNotBlank(azienda.getCodicefiscale())) {
		    sb.append(azienda.getCodicefiscale());
		} else {
		    sb.append(azienda.getPartitaiva());
		}
		sb.append("-");
		sb.append(data);
	    }
	} else {
	    error = getMessageFromBundle("pecinbox.message.errorecodicepraticatel.nocf", new Object[0]);
	}
	Map<String, String> model = new HashMap<String, String>();
	model.put("codicePraticaTel", sb.toString());
	model.put("error", error);
	return new ModelAndView("jsonView", model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String view(Model model, @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand command,
	    HttpServletRequest request) {

	prepareViewLeggiProtocollo(model, command, request, command.getProtSoftware().getCodice(), command.getComune().getCodicecomune());
	setPageAttributes(model);
	return "azioniprotocollo/leggiProtocollo";
    }

    @RequestMapping
    public String pannelloIstanza(@RequestParam(WebConstants.SOFTWARE) String software, @RequestParam("codiceComune") String codiceComune,
	    Model model, @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand command, HttpServletRequest request) {

	Istanze i = new Istanze();
	DatiProtocolloLettoResponseType datiProtocollo = prepareViewLeggiProtocollo(model, command, request, software, codiceComune);
	log.debug("pannelloIstanza# Verifico se è attiva la verticalizzazione {}", WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE);
	boolean isAttivaVerticalizzazioneFVG_SUAP_IN_RETE = false;
	boolean isAttivaVerticalizzazionePraticaSistEsterno = false;
	//	public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE = "FVG_SUAP_IN_RETE";
	//	    public static final String VERTICALIZZAZIONE_FVG_SUAP_IN_RETE_NLA_NODO_SUAP_INRETE = "NLA_NODO_SUAP_INRETE";
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE)) {
	    log.debug("pannelloIstanza# Verticalizzazione {}: ATTIVA", WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE);
	    isAttivaVerticalizzazioneFVG_SUAP_IN_RETE = true;
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO)) {
	    log.debug("pannelloIstanza# Verticalizzazione {}: ATTIVA", WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO);
	    isAttivaVerticalizzazionePraticaSistEsterno = true;
	}
	if (datiProtocollo != null) {
	    if (StringUtils.isNotBlank(datiProtocollo.getOggetto())) {
		i.setLavori(datiProtocollo.getOggetto());
	    }
	    String annotazioni = "";
	    if (StringUtils.isNotBlank(datiProtocollo.getNumeroPratica())) {
		// annotazioni = "Numeropratica: " + datiProtocollo.getNumeroPratica();
	    }
	    if (StringUtils.isNotBlank(datiProtocollo.getAnnoNumeroPratica())) {
		if (StringUtils.isNotBlank(annotazioni)) {
		    //annotazioni += ", ";
		}
		annotazioni = datiProtocollo.getAnnoNumeroPratica();
	    }
	    if (StringUtils.isNotBlank(annotazioni)) {
		i.setLavoriestesa(annotazioni);
	    }
	}
	command.setEntity(i);
	command.getEntity().setTipisoggetto(new Tipisoggetto());
	model.addAttribute("FUNZIONALITA_DA_MOSTRARE", "ISTANZA");
	model.addAttribute("CREA_DA_ESTERNO", isAttivaVerticalizzazionePraticaSistEsterno);
	model.addAttribute("FVG_SUAP_IN_RETE", isAttivaVerticalizzazioneFVG_SUAP_IN_RETE);
	return "azioniprotocollo/leggiProtocollo";
    }

    @RequestMapping
    public void ajaxCreaIstanzaSTC(@RequestParam(required = false, value = "tipoProtocollo") String tipoProtocollo,
	    @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand azioniProtocollazioneCommand, HttpServletRequest request,
	    HttpServletResponse res) throws IOException {

	String error = "";
	InserimentoPraticaResponse response = null;
	try {
	    tipoProtocollo = StringUtils.defaultIfEmpty(tipoProtocollo, "DEFAULT");
	    if (tipoProtocollo.equals("SUAPINRETE")) {
		log.debug("ajaxCreaIstanzaSTC# SUAP IN RETE");
		response = protocollazioneService.insertPraticaSTCDaSuapInRete(azioniProtocollazioneCommand);
	    } else if (tipoProtocollo.equals("DEFAULT")) {
		log.debug("ajaxCreaIstanzaSTC# Creazione pratica da protocollo standar");
		response = protocollazioneService.insertPraticaSTCDaAzioni(azioniProtocollazioneCommand);
	    } else if (tipoProtocollo.equals("NLAINFOCAMERE")) {
		log.debug("ajaxCreaIstanzaSTC# Creazione pratica da NLA-INFOCAMERE");
		response = protocollazioneService.insertPraticaSTCDaSistemaEsterno(azioniProtocollazioneCommand);
		res.setContentType(MediaType.APPLICATION_JSON);
		if (response.getDettaglioErrore().isEmpty()) {
		    res.getOutputStream().write(("{\"idPratica\":" + response.getDettaglioPratica().getIdPratica() + "}").getBytes());
		} else {
		    error = "Sono stati rilevati i seguenti errori in inserimento pratica: ";
		    List<ErroreType> errs = response.getDettaglioErrore();
		    for (ErroreType erroreType : errs) {
			error += "-" + erroreType.getDescrizione() + "(" + erroreType.getNumeroErrore() + ") ";
		    }
		    String jsonError = "{\"error\": \"KO " + error + "\"}";
		    res.getOutputStream().write(jsonError.getBytes());
		}
		return;
	    }
	    if (response.getDettaglioErrore().isEmpty()) {
		res.getOutputStream()
			.write(("OK L'istanza " + response.getDettaglioPratica().getNumeroPratica() + " e' stata creata correttamente").getBytes());
		return;
	    } else {
		error = "Sono stati rilevati i seguenti errori in inserimento pratica: \n";
		List<ErroreType> errs = response.getDettaglioErrore();
		for (ErroreType erroreType : errs) {
		    error += "-" + erroreType.getDescrizione() + "(" + erroreType.getNumeroErrore() + ")\n";
		}
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    error = "E' stato rilevato il seguente errore in inserimento pratica: " + e.getMessage();
	} catch (BusinessValidationException e) {
	    error = "E' stato rilevato il seguente errore in inserimento pratica: " + e.getMessage();
	}
	List<String> s = FlashMessages.getWarnings();
	if (s != null) {
	    for (String string : s) {
		error += "\n" + string;
	    }
	}
	res.getOutputStream().write(("KO " + error).getBytes());
	return;
    }

    @RequestMapping
    public void ajaxCreaMovimentoSTC(@RequestParam(required = false, value = "tipoProtocollo") String tipoProtocollo,
	    @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand azioniProtocollazioneCommand, HttpServletRequest request,
	    HttpServletResponse res) throws IOException {

	String error = "";
	tipoProtocollo = StringUtils.isBlank(tipoProtocollo) ? "DEFAULT" : tipoProtocollo;
	NotificaAttivitaResponse response = null;
	try {
	    if (tipoProtocollo.equals("SUAPINRETE")) {
		log.debug("ajaxCreaIstanzaSTC# Creazione movimento da protocollo SUAP IN RETE");
		response = protocollazioneService.insertMovimentoSTCSuapInRete(azioniProtocollazioneCommand);
	    }else if(tipoProtocollo.equals("NLAINFOCAMERE")){
		log.debug("ajaxCreaIstanzaSTC# Creazione movimento da protocollo DA SISTEMA ESTERNO");
		response = protocollazioneService.insertMovimentoSistemaExt(azioniProtocollazioneCommand);
	    }else if (tipoProtocollo.equals("DEFAULT")) {
		log.debug("ajaxCreaIstanzaSTC# Creazione movimento da protocollo standar");
		response = protocollazioneService.insertMovimentoSTCDaAzioni(azioniProtocollazioneCommand, null, null);
	    }
	    if (response.getDettaglioErrore().isEmpty()) {
		res.getOutputStream().write(
			("OK Il movimento con id " + response.getDettaglioattivita().getIdAttivita() + " e' stato creato correttamente").getBytes());
		return;
	    } else {
		error = "Sono stati rilevati i seguenti errori in inserimento attività: \n";
		List<ErroreType> errs = response.getDettaglioErrore();
		for (ErroreType erroreType : errs) {
		    error += "-" + erroreType.getDescrizione() + "(" + erroreType.getNumeroErrore() + ")\n";
		}
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    error = "E' stato rilevato il seguente errore in inserimento attività: " + e.getMessage();
	} catch (BusinessValidationException e) {
	    error = "E' stato rilevato il seguente errore in inserimento attività: " + e.getMessage();
	}
	List<String> s = FlashMessages.getWarnings();
	if (s != null) {
	    for (String string : s) {
		error += "\n" + string;
	    }
	}
	res.getOutputStream().write(("KO" + error).getBytes());
	return;
    }

    @RequestMapping
    public String ajaxVisualizzaIstanze(Model model, @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand command,
	    HttpServletRequest request) {

	List<IstanzeListHelper> istanzes = istanzeService.findIstanzeListHelperByFilter(
		prepareFilterIstanze(command.getDatiProtocolloLetto().getNumeroProtocollo(), command.getEntity().getDataprotocollo()), 0, 100);
	model.addAttribute("listaIstanze", istanzes);
	return "azioniprotocollo/ajaxVisualizzaIstanze";
    }

    @RequestMapping
    public String pannelloMovimento(@RequestParam(WebConstants.SOFTWARE) String software, @RequestParam("codiceComune") String codiceComune,
	    Model model, @ModelAttribute("azioniProtocollazioneCommand") AzioniProtocollazioneCommand command, HttpServletRequest request) {

	prepareViewLeggiProtocollo(model, command, request, software, codiceComune);
	command.setEntity(new Istanze());
	command.setMovimento(new Movimenti());
	command.getEntity().setTipisoggetto(new Tipisoggetto());
	boolean isAttivaVerticalizzazioneFVG_SUAP_IN_RETE = false;
	boolean isAttivaVerticalizzazionePraticaSistEsterno = false;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE)) {
	    log.debug("pannelloMovimento# Verticalizzazione {}: ATTIVA", WebConstants.VERTICALIZZAZIONE_FVG_SUAP_IN_RETE);
	    isAttivaVerticalizzazioneFVG_SUAP_IN_RETE = true;
	}
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO)) {
	    log.debug("pannelloIstanza# Verticalizzazione {}: ATTIVA", WebConstants.VERTICALIZZAZIONE_PRATICA_DA_SISTEMA_ESTERNO);
	    isAttivaVerticalizzazionePraticaSistEsterno = true;
	}
	model.addAttribute("FVG_SUAP_IN_RETE_MOV", isAttivaVerticalizzazioneFVG_SUAP_IN_RETE);
	model.addAttribute("FUNZIONALITA_DA_MOSTRARE", "MOVIMENTO");
	model.addAttribute("CREA_DA_ESTERNO", isAttivaVerticalizzazionePraticaSistEsterno);
	return "azioniprotocollo/leggiProtocollo";
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST)
    public void jsonFindAmministrazioniProtocollo(Model model, HttpServletRequest request, HttpServletResponse response)
	    throws JAXBException, IOException {

	try {
	    AmministrazioniProtocolloRequest jsonRequest = fromJson(request.getInputStream(), AmministrazioniProtocolloRequest.class);
	    //1. Verifico se è richiesto di indicare un'amministrazione
	    String modulo = VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE;
	    String parametro = VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_RICHIEDI_AMM_AZIONI_PROTOCOLLO;
	    String software = jsonRequest.getSoftware();
	    String codiceComune = jsonRequest.getCodiceComune();
	    Verticalizzazioniparametri par = this.verticalizzazioniparametriService.findByModuloAndParametroAndSoftwareAndComune(modulo, parametro,
		    software, codiceComune);
	    List<AmministrazioneProtocolloModel> amministrazioni = new ArrayList<AmministrazioneProtocolloModel>(0);
	    if (par != null && par.getValore() != null && par.getValore().equals("1")) {
		amministrazioni = this.amministrProtocolloService.findByComuneAndSoftware(jsonRequest);
	    }
	    AmministrazioneProtocolloResponse jsonResponse = new AmministrazioneProtocolloResponse(amministrazioni);
	    response.setContentType("application/json");
	    String res = toJson(jsonResponse, true);
	    response.getOutputStream().write(res.getBytes("utf-8"));
	} catch (Exception ex) {
	    super.writeJsonError(response, ex.getMessage());
	}
    }

    private IstanzeFilter prepareFilterIstanze(String numeroprotocollo, Date dataprotocollo) {

	IstanzeFilter filter = new IstanzeFilter();
	filter.setModulo(null);
	filter.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
	filter.setNumeroprotocollo(numeroprotocollo);
	filter.setDataprotocollo(dataprotocollo);
	filter.setCercaprotocolloinmovimenti(true);
	return filter;
    }

    private DatiProtocolloLettoResponseType prepareViewLeggiProtocollo(Model model, AzioniProtocollazioneCommand command, HttpServletRequest request,
	    String pSoftware, String codiceComune) {

	DatiProtocolloLettoResponseType datiProtocolloLetto = null;
	datiProtocolloLetto = protocollazioneService.leggiProtocolloUORuolo(ORMHelper.getToken(), command.getDatiProtocollo().getNumeroProtocollo(),
		command.getDatiProtocollo().getAnnoProtocollo(), command.getDatiProtocollo().getIdProtocollo(), command.getUo(), command.getRuolo(),
		pSoftware, codiceComune);
	command.getEntity().setNumeroprotocollo(datiProtocolloLetto.getNumeroProtocollo());
	Date dataProt = null;
	GregorianCalendar cg = Utilities.getDate(datiProtocolloLetto.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN);
	if (cg != null) {
	    dataProt = cg.getTime();
	}
	command.getEntity().setDataprotocollo(dataProt);
	command.getEntity().setFkidprotocollo(datiProtocolloLetto.getIdProtocollo());
	command.setDatiProtocolloLetto(datiProtocolloLetto);
	String inviaAllegati = StringUtils
		.defaultString(this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_AZIONI_PROTOCOLLO_INVIAALLEGATI, "1", request), "1")
		.trim();
	command.setInviaAllegati(inviaAllegati.equals("1") ? Boolean.TRUE : Boolean.FALSE);
	String scompattaAllegati = StringUtils.defaultString(
		this.leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_AZIONI_PROTOCOLLO_SCOMPATTAALLEGATICOMPRESSI, "0", request), "1")
		.trim();
	command.setScompattaAllegati(scompattaAllegati.equals("1") ? Boolean.TRUE : Boolean.FALSE);
	model.addAttribute("azioniProtocollazioneCommand", command);
	model.addAttribute("datiProtocolloLetto", datiProtocolloLetto);
	int cistanzes = istanzeService.countIstanzeListHelperByFilter(
		prepareFilterIstanze(datiProtocolloLetto.getNumeroProtocollo(), command.getEntity().getDataprotocollo()));
	// List<IstanzeListHelper> istanzes = istanzeService.findIstanzeListHelperByFilter(filter, 0, 20);
	model.addAttribute("istanzePresenti", ((cistanzes > 0) ? Boolean.TRUE : Boolean.FALSE));
	List<ProtocolloFlusso> flussiInrequest = getFlussi();
	model.addAttribute("flussiInRequest", flussiInrequest);
	boolean isVisualizzaValoriNulli = false;
	// Verifico il parametro VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_DATI_PROTOCOLLO_NULLI della verticalizzazione PROTOCOLLO_ATTIVO
	// che permette di decide se visualizzare in fase di lettura del protocollo i dati con valore nullo
	Verticalizzazioniparametri visualizzaValoriNulli = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_DATI_PROTOCOLLO_NULLI,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	if (visualizzaValoriNulli != null && visualizzaValoriNulli.getValore().equals("1")) {
	    isVisualizzaValoriNulli = true;
	}
	model.addAttribute("isVisualizzaValoriNulli", isVisualizzaValoriNulli);
	String softwareMenuLink = (String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION);
	List<Software> softwareList = new ArrayList<Software>();
	if (softwareMenuLink.equals(WebConstants.SOFTWARE_TT)) {
	    // in caso di menù da TT mostro la lista dei software abilitati
	    List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(getCurrentlyAuthenticatedUserDetails(),
		    new Software());
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		if (BooleanUtils.isTrue(responsabilisoftware.getSoftware().getModuloopzionale())) {
		    softwareList.add(responsabilisoftware.getSoftware());
		}
	    }
	} else {
	    // altrimenti solamente il software del menù
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    softwareList.add(software);
	}
	model.addAttribute("softwareList", softwareList);
	ConfigurazioneId cid = new ConfigurazioneId(pSoftware);
	Configurazione configurazione = configurazioneService.findById(cid);
	if (configurazione == null) {
	    throw new InvalidConfigurationException("Nessuna configurazione trovata per il software [" + ORMHelper.getSoftware() + "]");
	}
	model.addAttribute("configurazione", configurazione);
	return datiProtocolloLetto;
    }

    private List<ProtocolloFlusso> getFlussi() {

	return protocolloFlussoService.findAll(null, null);
    }

    @SuppressWarnings("unchecked")
    private String populateQsForSoftwareTT(HttpServletRequest request) throws UnsupportedEncodingException {

	String result = "FirstP=true";
	Map<String, String[]> parameters = request.getParameterMap();
	boolean softwarePresente = false;
	for (String parameter : parameters.keySet()) {
	    if (parameter.equals(WebConstants.SOFTWARE)) {
		softwarePresente = true;
		result += "&" +
			WebConstants.SOFTWARE +
			"=" +
			StringUtils.defaultString(((String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION)));
	    } else {
		String[] values = parameters.get(parameter);
		for (String val : values) {
		    result += "&" + parameter + "=" + URLEncoder.encode(val, "UTF-8");
		}
	    }
	}
	if (!softwarePresente) {
	    result += "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    StringUtils.defaultString(((String) request.getSession().getAttribute(SOFTWARE_ATTIVO_IN_SESSION)));
	}
	return result;
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<VerticalizzazioniconfigurazioniHelper> temp = verticalizzazioniService.findListaConfigurazioniPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO);
	Set<String> codiciComune = new HashSet<String>();
	List<Responsabilicomuni> comuni = responsabilicomuniService.findByOperatore(getCurrentlyAuthenticatedUserDetails());
	for (Responsabilicomuni r : comuni) {
	    codiciComune.add(r.getId().getCodicecomune());
	}
	List<VerticalizzazioniconfigurazioniHelper> helpers = new ArrayList<VerticalizzazioniconfigurazioniHelper>();
	for (VerticalizzazioniconfigurazioniHelper h : temp) {
	    if (h.getComune() == null) {
		helpers.add(h);
		continue;
	    }
	    String codiceComune = h.getComune().getCodicecomune();
	    if (codiciComune.contains(codiceComune)) {
		helpers.add(h);
	    }
	}
	model.addAttribute("configurazioniVerticalizzazionis", helpers);
    }

    @Override
    protected void fixMergeEntityProperty(AzioniProtocollazioneCommand entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(AzioniProtocollazioneCommand entity) {

	// TODO Auto-generated method stub
    }
}
