package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
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
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.domain.helper.ConfigurazioneUtenteHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DomandeSTCScadenzarioDTO;
import it.gruppoinit.pal.gp.core.domain.helper.EventiSistemaHelperTable;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ResponsabiliCommand;
import it.gruppoinit.pal.gp.core.domain.web.ScadenzarioOperatoreChiaveValore;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.features.scadenzario.ScadenzarioOneri;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.BatchScadenzarioService;
import it.gruppoinit.pal.gp.core.features.scadenzario.service.IResponsabiliScadenzarioService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.jmesa.BatchScadenzarioHelperTable;
import it.gruppoinit.pal.gp.core.jmesa.DocumentiDaFirmareTable;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeScadenzarioStcTable;
import it.gruppoinit.pal.gp.core.jmesa.IstanzeeventiHelperTable;
import it.gruppoinit.pal.gp.core.jmesa.MovimentiDaVisionareTable;
import it.gruppoinit.pal.gp.core.jmesa.MovimentiSTCNonNotificatiTable;
import it.gruppoinit.pal.gp.core.jmesa.RichiesteFoNonLetteTable;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmAvvService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmScaService;
import it.gruppoinit.pal.gp.core.service.RicercheService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Controller
@SessionAttributes(value = { "batchScadenzarioFilter", "responsabile" })
public class BatchScadenzarioController extends BaseController<BatchScadenzario> {

    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    private ComuniassociatiService comuniassociatiService;
    private BatchScadenzarioService batchScadenzarioService;
    private FoRichiesteService foRichiesteService;
    private MovimentiService movimentiService;
    private StatiistanzaService statiistanzaService;
    private SoftwareService softwareService;
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeeventiService istanzeeventiService;
    private ResponsabiliService responsabiliService;
    private ResponsabiliTmAvvService responsabiliTmAvvService;
    private ResponsabiliTmScaService responsabiliTmScaService;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private OggettiMetadatiService oggettiMetadatiService;
    private RicercheService ricercheService;
    private AlberoprocService alberoprocService;
    private TipiMovimentoService tipiMovimentoService;
    private IstanzeoneriService istanzeoneriService;
    private IResponsabiliScadenzarioService responsabiliScadenzarioService;

    @Autowired
    public void setResponsabiliScadenzarioService(IResponsabiliScadenzarioService responsabiliScadenzarioService) {

	this.responsabiliScadenzarioService = responsabiliScadenzarioService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setRicercheService(RicercheService ricercheService) {

	this.ricercheService = ricercheService;
    }

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
    }

    @Autowired
    public void setBatchScadenzarioService(BatchScadenzarioService batchScadenzarioService) {

	this.batchScadenzarioService = batchScadenzarioService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setFoRichiesteService(FoRichiesteService foRichiesteService) {

	this.foRichiesteService = foRichiesteService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setResponsabiliTmScaService(ResponsabiliTmScaService responsabiliTmScaService) {

	this.responsabiliTmScaService = responsabiliTmScaService;
    }

    @Autowired
    public void setResponsabiliTmAvvService(ResponsabiliTmAvvService responsabiliTmAvvService) {

	this.responsabiliTmAvvService = responsabiliTmAvvService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setDocumentiDaFirmareService(DocumentiDaFirmareService documentiDaFirmareService) {

	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @RequestMapping
    public String createSearch(Model model, @RequestParam(value = "id_ricerca", required = false) String id_ricerca,
	    @RequestParam(value = "indice_combo", required = false) String indice_combo, HttpServletRequest request) {

	BatchScadenzarioFilter batchScadenzarioFilter = new BatchScadenzarioFilter();
	Responsabili loggedUser = getCurrentlyAuthenticatedUserDetails();
	gestOperatoreReadonly(model, loggedUser);
	// imposto il filtro per responsabile solo se l'utente non è amministratore o amministratore software
	batchScadenzarioFilter.setUtenteLoggato(loggedUser);
	if (isAmmOrAmmSoftware(loggedUser)) {
	    // sulla jsp visualizzo la possibilità di scelta del responsabile come filtro
	    model.addAttribute("isAmministratore", true);
	}
	if (StringUtils.isNotBlank(id_ricerca)) {
	    Ricerche ricerche = ricercheService.findById(new PkId(Integer.parseInt(id_ricerca)));
	    setFilter(batchScadenzarioFilter, ricerche.getFiltro());
	    //		IstanzeFilter istanzeFilter = istanzeFilterUtils.createIstanzeFilter(ricerche.getFiltro());
	    //		command.setIstanzeFilter(istanzeFilter);
	    //		command.setCodiceRicerca(Integer.parseInt(indice_combo));
	} else {
	    setDates(batchScadenzarioFilter, loggedUser);
	}
	List<Statiistanza> stati = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	model.addAttribute("stati", stati);
	model.addAttribute("batchScadenzarioFilter", batchScadenzarioFilter);
	//Setta in request il paramtro della configurazione utente
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "1", request);
	return "batchscadenzario/search";
    }

    private void setFilter(BatchScadenzarioFilter istanzeFilter, byte[] filtro) {

	String filtroString = new String(filtro);
	String[] filtri = filtroString.split("&");
	Map<String, String[]> map = new HashMap<String, String[]>();
	Map<String, String> mapSchedeFilter = new HashMap<String, String>();
	for (String string : filtri) {
	    String[] campi = string.split("=");
	    if (!campi[0].contains("schedaDinamicaFilter")) {
		if (campi.length > 1) {
		    if (map.get(campi[0]) != null) {
			String[] vals = map.get(campi[0]);
			ArrayList<String> v = new ArrayList<String>();
			for (String string2 : vals) {
			    v.add(string2);
			}
			v.add(campi[1]);
			String[] v2 = new String[v.size()];
			map.put(campi[0], v.toArray(v2));
		    } else {
			map.put(campi[0], new String[] { campi[1] });
		    }
		} else {
		    map.put(campi[0], new String[] { "" });
		}
	    } else {
		String key = StringUtils.remove(campi[0], "schedaDinamicaFilter.");
		if (campi.length > 1) {
		    mapSchedeFilter.put(key, campi[1]);
		} else {
		    mapSchedeFilter.put(key, "");
		}
	    }
	}
	// 
	// istanzeFilter.dallaData
	String[] dallaData = safeGet(map.get("dallaData"));
	if (dallaData.length > 0) {
	    if (StringUtils.isNotBlank(dallaData[0])) {
		Date _dallaData = Utilities.parseDateString(dallaData[0], false);
		istanzeFilter.setDallaData(_dallaData);
	    }
	}
	// istanzeFilter.allaData
	String[] allaData = safeGet(map.get("allaData"));
	if (allaData.length > 0) {
	    if (StringUtils.isNotBlank(allaData[0])) {
		Date _allaData = Utilities.parseDateString(allaData[0], false);
		istanzeFilter.setAllaData(_allaData);
	    }
	}
	String[] numeroIstanza = safeGet(map.get("numeroIstanza"));
	if (numeroIstanza.length > 0) {
	    if (StringUtils.isNotBlank(numeroIstanza[0])) {
		istanzeFilter.setNumeroIstanza(numeroIstanza[0]);
	    }
	}
	String[] codiceREsponsabile = safeGet(map.get("responsabile.id.codice"));
	if (codiceREsponsabile.length > 0) {
	    if (Utilities.isInteger(codiceREsponsabile[0])) {
		Responsabili responsabile = responsabiliService.findById(new PkId(Integer.parseInt(codiceREsponsabile[0])));
		istanzeFilter.setResponsabile(responsabile);
	    }
	}
	String[] codiceIntervento = safeGet(map.get("intervento.id.codice"));
	if (codiceIntervento.length > 0) {
	    if (Utilities.isInteger(codiceIntervento[0])) {
		Alberoproc ap = alberoprocService.findById(new PkId(Integer.parseInt(codiceIntervento[0])));
		istanzeFilter.setIntervento(ap);
	    }
	}
	String[] tipoMovimento = safeGet(map.get("tipoMovimentoFatto.id.tipomovimento"));
	if (tipoMovimento.length > 0) {
	    if (StringUtils.isNotBlank(tipoMovimento[0])) {
		Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipoMovimento[0]));
		istanzeFilter.setTipoMovimentoFatto(tm);
	    }
	}
	String[] tipoMovimentoDF = safeGet(map.get("tipoMovimentoDaFare.id.tipomovimento"));
	if (tipoMovimentoDF.length > 0) {
	    if (StringUtils.isNotBlank(tipoMovimentoDF[0])) {
		Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipoMovimentoDF[0]));
		istanzeFilter.setTipoMovimentoDaFare(tm);
	    }
	}
	String[] codiceStatoV = safeGet(map.get("statiIstanza"));
	if (codiceStatoV.length > 0) {
	    List<Statiistanza> statiIstanzas = new ArrayList<Statiistanza>();
	    for (String codiceStato : codiceStatoV) {
		if (StringUtils.isNotBlank(codiceStato)) {
		    Statiistanza chiusura = new Statiistanza();
		    chiusura = statiistanzaService.findById(new StatiistanzaId(codiceStato));
		    statiIstanzas.add(chiusura);
		}
	    }
	    istanzeFilter.setStatiIstanza(statiIstanzas);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////// GESTIONE DELLA SEZIONE DI RICERCA DELLE SCHEDE ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la scheda associata alla ricera sui campi dinamici
	//	String codice_scheda_dyn = mapSchedeFilter.get("scheda.id.codice");
	//	if (StringUtils.isNotBlank(codice_scheda_dyn)) {
	//	    // recupero il modello e lo setto al filtro
	//	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(Integer.parseInt(codice_scheda_dyn)));
	//	    SchedaDinamicaFilter schedaDinamicaFilter = new SchedaDinamicaFilter();
	//	    schedaDinamicaFilter.setScheda(dyn2Modellit);
	//	    // Conto il numero delle righe
	//	    Set<Integer> numerorighe = getNumeroRighe(mapSchedeFilter);
	//	    List<SchedaDinamicaRigheFilter> righe = new ArrayList<SchedaDinamicaRigheFilter>();
	//	    SchedaDinamicaRigheFilter dinamicaRigheFilter = null;
	//	    for (Integer riga_num : numerorighe) {
	//		dinamicaRigheFilter = populateRiga(mapSchedeFilter, riga_num);
	//		righe.add(dinamicaRigheFilter);
	//	    }
	//	    schedaDinamicaFilter.setRighe(righe);
	//	    istanzeFilter.setSchedaDinamicaFilter(schedaDinamicaFilter);
	//	}
	//	return istanzeFilter;
    }

    private String[] safeGet(String[] strings) {

	if (strings == null) {
	    strings = new String[0];
	}
	return strings;
    }

    private boolean gestOperatoreReadonly(Model model, Responsabili loggedUser) {

	boolean showScadenzario = true;
	if (BooleanUtils.isTrue(loggedUser.getReadonly())) {
	    showScadenzario = false;
	}
	if (showScadenzario == false) {
	    model.addAttribute("msgUtente",
		    "Attenzione! L'operatore e' configurato in sola lettura e non e' abilitato alla visualizzazione dello scadenzario");
	}
	return showScadenzario;
    }

    @RequestMapping
    public String list(@RequestParam(value = "tab", required = false) String tab, Model model,
	    @ModelAttribute("batchScadenzarioFilter") BatchScadenzarioFilter batchScadenzarioFilter, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	List<Statiistanza> stati = new ArrayList<Statiistanza>();
	List<Statiistanza> _stati = batchScadenzarioFilter.getStatiIstanza();
	if (_stati != null) {
	    for (Statiistanza _statiistanza : _stati) {
		Statiistanza statiistanza = statiistanzaService
			.findById(new StatiistanzaId(ORMHelper.getIdcomune(), ORMHelper.getSoftware(), _statiistanza.getId().getCodicestato()));
		stati.add(statiistanza);
	    }
	}
	batchScadenzarioFilter.setStatiIstanza(stati);
	model.addAttribute("formAction", "list.htm");
	return scadenzarioPagesAttributes(tab, model, batchScadenzarioFilter, request, response, batchScadenzarioFilter.getUtenteLoggato());
    }

    @RequestMapping
    public String listPerOperatore(@RequestParam(value = "tab", required = false) String tab, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	BatchScadenzarioFilter batchScadenzarioFilter = new BatchScadenzarioFilter();
	// imposto sempre il filtro per responsabile con l'utente loggato
	Responsabili loggedUser = getCurrentlyAuthenticatedUserDetails();
	batchScadenzarioFilter.setUtenteLoggato(loggedUser);
	populateFilterPerOPeratore(loggedUser, batchScadenzarioFilter);
	model.addAttribute("batchScadenzarioFilter", batchScadenzarioFilter);
	model.addAttribute("listPerOperatore", "listPerOperatore");
	model.addAttribute("formAction", "listPerOperatore.htm");
	return scadenzarioPagesAttributes(tab, model, batchScadenzarioFilter, request, response, loggedUser);
    }

    private void populateFilterPerOPeratore(Responsabili loggedUser, BatchScadenzarioFilter batchScadenzarioFilter) {

	batchScadenzarioFilter.setScadComportamento(loggedUser.getScadComportamento());
	batchScadenzarioFilter.setScadSoftware(loggedUser.getScadSoftware());
	if (loggedUser.getScadOperatoreId() != null) {
	    Responsabili scadOperatore = responsabiliService.findById(new PkId(loggedUser.getScadOperatoreId()));
	    batchScadenzarioFilter.setResponsabile(scadOperatore);
	}
	setDates(batchScadenzarioFilter, loggedUser);
    }

    @RequestMapping
    public void ajaxAggiungiVisualizzazioneColonnaScadenzario(@RequestParam("parametro") String parametro, @RequestParam("valore") String valore,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	valore = valore.equals("0") ? "1" : "0";
    }

    /**
     * @param tab
     * @param model
     * @param batchScadenzarioFilter
     * @param request
     * @param response
     * @param stati
     * @return
     */
    private String scadenzarioPagesAttributes(String tab, Model model, BatchScadenzarioFilter batchScadenzarioFilter, HttpServletRequest request,
	    HttpServletResponse response, Responsabili utenteLoggato) {

	boolean showScadenzario = gestOperatoreReadonly(model, utenteLoggato);
	int numeroMovimentiDaEffettuare = 0;
	int numeroMovimentiDaLeggere = 0;
	int numeroMovimentiSTCNonNotificati = 0;
	int numeroRichiesteNonLette = 0;
	int numeroEventiNonLetti = 0;
	int numeroIstanzeStc = 0;
	int numeroIstanzeNonImportateStc = 0;
	int numeroEventiSistema = 0;
	int numeroDocumentiDafirmare = 0;
	int numeroOneri = 0;
	String movimentiDaEffettuare = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE, "1", request);
	String movimentiDaVisionare = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE, "1", request);
	String movimentiNonNotificati = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI, "1", request);
	String istanzeStc = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC, "1", request);
	String istanzeNonImporateStc = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE, "1",
		request);
	String richiesteFo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO, "1", request);
	String tipoOrdinamentoScadenze = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA, "DESC", request);
	String eventiSistema = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA, "1", request);
	String visualizzaDocumentiDaFirmare = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE, "1",
		request);
	//Scadenzario oneri
	String visualizzaScadenzarioOneri = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_SCADENZARIO_ONERI, "1",
		request);
	String ngiorniScOneri = responsabiliScadenzarioService.leggiParametriConfigurazioneScadenzario(ScadenzarioOneri.SCADENZARIO_ONERI,
		ScadenzarioOneri.GIORNI_DA_DATA_ATTUALE, request);
	batchScadenzarioFilter.setOrdinamentoScadenze(OrderTypeEnum.valueOf(tipoOrdinamentoScadenze));
	if (showScadenzario) {
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI, "1", request);
	    String ssi = (String) request.getAttribute(WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI);
	    if (StringUtils.defaultIfEmpty(ssi, "0").equalsIgnoreCase("1")) {
		batchScadenzarioFilter.setSoloScadenzeImportanti(Boolean.TRUE);
		List<ResponsabiliTmAvv> ravv = responsabiliTmAvvService.findByResponsabile(utenteLoggato.getId().getCodice());
		batchScadenzarioFilter.setTipimovimentoAvv(ravv);
		List<ResponsabiliTmSca> rsca = responsabiliTmScaService.findByResponsabile(utenteLoggato.getId().getCodice());
		batchScadenzarioFilter.setTipimovimentoSca(rsca);
	    } else {
		batchScadenzarioFilter.setSoloScadenzeImportanti(Boolean.FALSE);
		batchScadenzarioFilter.setTipimovimentoAvv(new ArrayList<ResponsabiliTmAvv>());
		batchScadenzarioFilter.setTipimovimentoSca(new ArrayList<ResponsabiliTmSca>());
	    }
	    ///////////////////////////////// TABELLA LISTA MOVIMENTI DA EFFETTUARE ///////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(movimentiDaEffettuare, "0").equalsIgnoreCase("1")) {
		GenerateTable<ScadenzarioListHelper> batchScadenzarioTable = new BatchScadenzarioHelperTable(batchScadenzarioFilter,
			batchScadenzarioService, configurazioneutenteService, false);
		String batchScadenzarioHtmlTable = batchScadenzarioTable.createJMesaList(request, response, "label.movimenti_da_effettuare",
			"batchscadenzario_id", true);
		if (batchScadenzarioHtmlTable == null) {
		    return null;
		}
		numeroMovimentiDaEffettuare = ((BatchScadenzarioHelperTable) batchScadenzarioTable).getCountedRecords();
		model.addAttribute("batchScadenzarioHtmlTable", batchScadenzarioHtmlTable);
	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA MOVIMENTI DA VISIONARE //////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(movimentiDaVisionare, "0").equalsIgnoreCase("1")) {
		GenerateTable<MovimentiDTO> movimentidaVisionareTable = new MovimentiDaVisionareTable(batchScadenzarioFilter, movimentiService,
			configurazioneutenteService);
		String movimentidaVisionareHtmlTable = movimentidaVisionareTable.createJMesaList(request, response, "label.movimenti_da_visionare",
			"movimenti_da_visionare_id", true);
		if (movimentidaVisionareHtmlTable == null) {
		    return null;
		}
		model.addAttribute("movimentidaVisionareHtmlTable", movimentidaVisionareHtmlTable);
		numeroMovimentiDaLeggere = ((MovimentiDaVisionareTable) movimentidaVisionareTable).getCountedRecords();
	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA MOVIMENTI STC NON NOTIFICATI //////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(movimentiNonNotificati, "0").equalsIgnoreCase("1")) {
		GenerateTable<MovimentiDTO> movimentiSTCnonNotificatiTable = new MovimentiSTCNonNotificatiTable(batchScadenzarioFilter,
			movimentiService, configurazioneutenteService);
		String movimentiSTCnonNotificatiHtmlTable = movimentiSTCnonNotificatiTable.createJMesaList(request, response,
			"label.movimenti_non_notificati", "movimenti_STC_non_notificati_id", true);
		if (movimentiSTCnonNotificatiHtmlTable == null) {
		    return null;
		}
		model.addAttribute("movimentiSTCnonNotificatiHtmlTable", movimentiSTCnonNotificatiHtmlTable);
		numeroMovimentiSTCNonNotificati = ((MovimentiSTCNonNotificatiTable) movimentiSTCnonNotificatiTable).getCountedRecords();
	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA ISTANZE STC //////////////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(istanzeStc, "0").equalsIgnoreCase("1")) {
		GenerateTable<DomandeSTCScadenzarioDTO> istanzeStcTable = new IstanzeScadenzarioStcTable(utenteLoggato, userSecurityService,
			verticalizzazioniparametriService, configurazioneutenteService, false, true);
		String istanzeStcHtmlTable = istanzeStcTable.createJMesaList(request, response, "label.nuove_istanze_stc", "istanze_stc_id", false);
		if (istanzeStcHtmlTable == null) {
		    return null;
		}
		model.addAttribute("istanzeStcHtmlTable", istanzeStcHtmlTable);
		numeroIstanzeStc = ((IstanzeScadenzarioStcTable) istanzeStcTable).getCountedRecords();
	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA ISTANZE STC NON IMPORTATE //////////////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(istanzeNonImporateStc, "0").equalsIgnoreCase("1")) {
		GenerateTable<DomandeSTCScadenzarioDTO> istanzeStcNonImportateTable = new IstanzeScadenzarioStcTable(utenteLoggato,
			userSecurityService, verticalizzazioniparametriService, configurazioneutenteService, false, false);
		String istanzeNonImportateStcHtmlTable = istanzeStcNonImportateTable.createJMesaList(request, response,
			"label.istanze_non_importate_stc", "label.istanze_non_importate_stc_id", false);
		if (istanzeNonImportateStcHtmlTable == null) {
		    return null;
		}
		model.addAttribute("istanzeNonImportateStcHtmlTable", istanzeNonImportateStcHtmlTable);
		numeroIstanzeNonImportateStc = ((IstanzeScadenzarioStcTable) istanzeStcNonImportateTable).getCountedRecords();
	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA RICHIESTE FRONT-END /////////////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(richiesteFo, "0").equalsIgnoreCase("1")) {
		GenerateTable<FoRichieste> foRichiesteTable = new RichiesteFoNonLetteTable(foRichiesteService);
		String foRichiesteHtmlTable = foRichiesteTable.createJMesaList(request, response, "label.richieste_frontoffice",
			"richieste_frontoffice_id", true);
		if (foRichiesteHtmlTable == null) {
		    return null;
		}
		model.addAttribute("foRichiesteHtmlTable", foRichiesteHtmlTable);
		// Recupero il numero dei record presenti nella lista tramite una count
		numeroRichiesteNonLette = foRichiesteService.countRichiesteNonLette();
	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA DOCUMENTI DA FIRMARE /////////////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(visualizzaDocumentiDaFirmare, "0").equalsIgnoreCase("1")) {
		GenerateTable<DocumentiDaFirmare> foDocumentiTable = new DocumentiDaFirmareTable(utenteLoggato, documentiDaFirmareService,
			oggettiMetadatiService, responsabiliService);
		String foDocumentiDaFirmareHtmlTable = foDocumentiTable.createJMesaList(request, response, "label.documenti_da_firmare",
			"documenti_da_firmare_id", true);
		if (foDocumentiDaFirmareHtmlTable == null) {
		    return null;
		}
		model.addAttribute("documentiDaFirmareHtmlTable", foDocumentiDaFirmareHtmlTable);
		// Recupero il numero dei documenti presenti nella lista tramite una count
		numeroDocumentiDafirmare = ((DocumentiDaFirmareTable) foDocumentiTable).getCountedRecords();
	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA SCADENZARIO ONERI /////////////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(visualizzaScadenzarioOneri, "0").equalsIgnoreCase("1")) {
		if (ngiorniScOneri == "" || ngiorniScOneri == null) {
		    ngiorniScOneri = "0";
		}
		Calendar dataodierna = new GregorianCalendar();
		dataodierna.add(Calendar.DATE, Integer.parseInt(ngiorniScOneri));
		Set<Responsabilicomuni> listResponsabilicomuni = getListResponsabilicomuni(utenteLoggato);
		String[] codiciComune = new String[listResponsabilicomuni.size()];
		int p = 0;
		for (Responsabilicomuni responsabilicomuni : listResponsabilicomuni) {
		    codiciComune[p++] = responsabilicomuni.getId().getCodicecomune();
		}
		List<ScadenzarioOneri> tabScadenzarioOneri = istanzeoneriService.findTabellaScadenzario(dataodierna.getTime(), codiciComune);
		model.addAttribute("stampaOneri", true);
		model.addAttribute("tabScadenzarioOneri", tabScadenzarioOneri);
		numeroOneri = tabScadenzarioOneri.size();
	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA EVENTI NON LETTI /////////////////////////////////////////////////////////////////////
	    //////////////////////////
	} else {
	    // setto parametri che rendono false la query sugli eventi
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, -10);
	    c.set(Calendar.MONTH, 1);
	    c.set(Calendar.DATE, 1);
	    batchScadenzarioFilter.setAllaData(c.getTime());
	    batchScadenzarioFilter.setDallaData(c.getTime());
	    batchScadenzarioFilter.setNumeroIstanza("-10000");
	}
	String eventiNonLettiConf = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI, "1", request);
	if (StringUtils.defaultIfEmpty(eventiNonLettiConf, "0").equalsIgnoreCase("1")) {
	    batchScadenzarioFilter.setFlagLetto(Boolean.FALSE);
	    GenerateTable<IstanzeeventiListHelper> eventiNonLettiTable = new IstanzeeventiHelperTable(batchScadenzarioFilter, istanzeeventiService);
	    String eventiNonLettiTableHtmlTable = eventiNonLettiTable.createJMesaList(request, response, "label.lista_eventi_non_letti",
		    "eventi_non_letti_id", true);
	    if (eventiNonLettiTableHtmlTable == null) {
		return null;
	    }
	    model.addAttribute("eventiNonLettiTableHtmlTable", eventiNonLettiTableHtmlTable);
	    numeroEventiNonLetti = ((IstanzeeventiHelperTable) eventiNonLettiTable).getCountedRecords();
	    //	    List<Istanzeeventi> eventiNonLetti = istanzeeventiService.findAllByScadenzarioFilter(batchScadenzarioFilter, 0, 100);
	    //	    boolean export = createJMesaExport(request, response, eventiNonLetti);
	    //	    if (export) {
	    //		return null;
	    //	    }
	    //	    model.addAttribute("eventiNonLetti", eventiNonLetti);
	    //	    numeroEventiNonLetti = eventiNonLetti.size();
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////  TABELLA LISTA EVENTI SISTEMA //////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	if (StringUtils.defaultIfEmpty(eventiSistema, "0").equalsIgnoreCase("1")) {
	    // ci permette di discriminare gli eventi di sistema, in modo da poter pplicare alla query in questo caso i filtri
	    //CODICEISTANZA, CODICEMOVIMENTO uguali a NULL	    
	    GenerateTable<Istanzeeventi> eventiSistemaTable = new EventiSistemaHelperTable(batchScadenzarioFilter, istanzeeventiService);
	    String eventiSistemaHtmlTable = eventiSistemaTable.createJMesaList(request, response, "label.eventi_sistema", "eventi_sistema_id", true);
	    if (eventiSistemaHtmlTable == null) {
		return null;
	    }
	    model.addAttribute("eventiSistemaHtmlTable", eventiSistemaHtmlTable);
	    // Recupero il numero dei record presenti nella lista tramite una count
	    numeroEventiSistema = ((EventiSistemaHelperTable) eventiSistemaTable).getCountedRecords();
	}
	model.addAttribute("numeroMovimentiDaEffettuare", numeroMovimentiDaEffettuare);
	model.addAttribute("numeroMovimentiDaLeggere", numeroMovimentiDaLeggere);
	model.addAttribute("numeroMovimentiSTCNonNotificati", numeroMovimentiSTCNonNotificati);
	model.addAttribute("numeroRichiesteNonLette", numeroRichiesteNonLette);
	model.addAttribute("numeroEventiNonLetti", numeroEventiNonLetti);
	model.addAttribute("numeroIstanzeStc", numeroIstanzeStc);
	model.addAttribute("numeroIstanzeNonImportateStc", numeroIstanzeNonImportateStc);
	model.addAttribute("numeroEventiSistema", numeroEventiSistema);
	model.addAttribute("numeroDocumentiDafirmare", numeroDocumentiDafirmare);
	model.addAttribute("numeroOneri", numeroOneri);
	boolean isVertSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	model.addAttribute("isVertSTCAttiva", isVertSTCAttiva);
	if (isVertSTCAttiva) {
	    // recupero il conteggio tramite la count
	    model.addAttribute("showStc", true);
	} else {
	    model.addAttribute("showStc", false);
	}
	String defTab = "tabScadenzario";
	// "Scadenzario","MovNonLetti","MovSTCNonNotificati","IstanzeStc","IstanzeStcNonImportate""RichiesteFoNonLette","EventiNonLetti","DocumentiDaFirmare",ScadenzarioOneri
	if (StringUtils.defaultIfEmpty(movimentiDaEffettuare, "0").equalsIgnoreCase("0")) {
	    if (StringUtils.defaultIfEmpty(movimentiDaVisionare, "0").equalsIgnoreCase("0")) {
		if (StringUtils.defaultIfEmpty(movimentiNonNotificati, "0").equalsIgnoreCase("0")) {
		    if (StringUtils.defaultIfEmpty(istanzeStc, "0").equalsIgnoreCase("0")) {
			if (StringUtils.defaultIfEmpty(istanzeNonImporateStc, "0").equalsIgnoreCase("0")) {
			    if (StringUtils.defaultIfEmpty(richiesteFo, "0").equalsIgnoreCase("0")) {
				if (StringUtils.defaultIfEmpty(eventiNonLettiConf, "0").equalsIgnoreCase("0")) {
				    if (StringUtils.defaultIfEmpty(visualizzaScadenzarioOneri, "0").equalsIgnoreCase("0")) {
					defTab = "tabScadenzarioOneri";
				    } else {
					defTab = "tabEventiNonLetti";
				    }
				} else {
				    defTab = "tabEventiSistema";
				}
			    } else {
				defTab = "tabRichiesteFoNonLette";
			    }
			} else {
			    defTab = "tabIstanzeStcNonImportate";
			}
		    } else {
			defTab = "tabIstanzeStc";
		    }
		} else {
		    defTab = "tabMovSTCNonNotificati";
		}
	    } else {
		defTab = "tabMovNonLetti";
	    }
	}
	if (numeroDocumentiDafirmare > 0) {
	    defTab = WebConstants.TAB_DOC_DA_FIRMARE;
	}
	model.addAttribute("tab", defTab);
	//////////////////////////
	if (StringUtils.isNotBlank(tab)) {
	    model.addAttribute("tab", tab);
	}
	return "batchscadenzario/list";
    }

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
    @RequestMapping
    public String viewParametriscadenzario(Model model, HttpServletRequest request) {

	Responsabili entity = getCurrentlyAuthenticatedUserDetails();
	entity = responsabiliService.findById(new PkId(entity.getId().getCodice()));
	ResponsabiliCommand responsabile = new ResponsabiliCommand();
	responsabile.setEntity(entity);
	responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(entity));
	responsabile.setResponsabilicomuniList(getListResponsabilicomuni(entity));
	if (entity.getScadOperatoreId() != null) {
	    ScadenzarioOperatoreChiaveValore scadOperatoreCvb = getScadOperatore(entity);
	    responsabile.setScadOperatore(scadOperatoreCvb);
	}
	model.addAttribute("responsabile", responsabile);
	setPageAttributes(model);
	setScadenzarioAttribute(responsabile, model, request);
	fixRenderResponsabiliEntityProperty(entity);
	return "responsabili/formScadenzario";
    }

    private ScadenzarioOperatoreChiaveValore getScadOperatore(Responsabili entity) {

	Responsabili scadOperatore = responsabiliService.findById(new PkId(entity.getScadOperatoreId()));
	ScadenzarioOperatoreChiaveValore scadOperatoreCvb = new ScadenzarioOperatoreChiaveValore();
	scadOperatoreCvb.setChiave(entity.getScadOperatoreId());
	scadOperatoreCvb.setValore(scadOperatore.getResponsabile());
	return scadOperatoreCvb;
    }

    private void setScadenzarioAttribute(ResponsabiliCommand responsabili, Model model, HttpServletRequest request) {

	List<Software> softwares = softwareService.findSoftwareAbilitati(responsabili.getEntity(), true);
	model.addAttribute("softwares", softwares);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_SCADENZARIO_ONERI, "1", request);
	String valore = responsabiliScadenzarioService.gestisciParametriConfigurazioneScadenzario(ScadenzarioOneri.SCADENZARIO_ONERI,
		ScadenzarioOneri.GIORNI_DA_DATA_ATTUALE, "0");
	if (valore != "") {
	    responsabili.setNgiorniDaOggi(Integer.parseInt(valore));
	}
	ConfigurazioneutenteId id = new ConfigurazioneutenteId(responsabili.getEntity().getId().getCodice(),
		WebConstants.CONF_UTENTE_PAGINA_CENTRALE);
	Configurazioneutente c = configurazioneutenteService.findById(id);
	if (c != null) {
	    request.setAttribute(WebConstants.CONF_UTENTE_PAGINA_CENTRALE, StringUtils.defaultString(c.getValore()).trim());
	}
	ConfigurazioneutenteId idConfigurazioneOrdinamentoDate = new ConfigurazioneutenteId(responsabili.getEntity().getId().getCodice(),
		WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
	Configurazioneutente configurazioneutenteOrdinamentoDate = configurazioneutenteService.findById(idConfigurazioneOrdinamentoDate);
	if (configurazioneutenteOrdinamentoDate != null) {
	    responsabili.setValoreOrdinamentoData(configurazioneutenteOrdinamentoDate.getValore());
	} else {
	    responsabili.setValoreOrdinamentoData("ASC");
	}
	model.addAttribute("isBatchScadenzarioPage", Boolean.TRUE);
	/**
	 * Parametri per la gestione della visualizzazione delle colonne nelle tabelle "Movimenti effettuati","Movimenti
	 * da visionare" "Movimenti visionati","Nuove domanda da STC"
	 *
	 */
	boolean isVisualizzaAlmenoUnaColonna = false;
	ConfigurazioneUtenteHelper configurazioneUtenteStato = gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_COLONNA_STATO, "0",
		false);
	isVisualizzaAlmenoUnaColonna = configurazioneUtenteStato.getValore().equalsIgnoreCase("1") ? true : false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteSoftware = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_SOFTWARE, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteSoftware.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteIntervento = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_INTERVENTO, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteIntervento.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteProcedimenti = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_PROCEDURA, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteProcedimenti.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtentePosArchivio = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_POS_ARCHIVIO, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtentePosArchivio.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	ConfigurazioneUtenteHelper configurazioneUtenteEndo = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_ENDOPROCEDIMENTO, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteEndo.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true : false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteDataPresentazioneIstanza = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_DATA_ISTANZA, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteDataPresentazioneIstanza.getValore().equalsIgnoreCase("1")
		|| isVisualizzaAlmenoUnaColonna) ? true : false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteTermineProcedimento = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_TERMINE_PROCEDIMENTO, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteTermineProcedimento.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna)
		? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteOperatore = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_OPERATORE, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteOperatore.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	ConfigurazioneUtenteHelper configurazioneUtenteIstruttore = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_ISTRUTTORE, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteIstruttore.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	ConfigurazioneUtenteHelper configurazioneUtenteAmministrazione = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_AMMINISTRAZIONE, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteAmministrazione.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	// NON HA SENSO NASCONDERE UNA COLONNA PER CUI è STATO CRETO LO SCADENZARIO
	//	ConfigurazioneUtenteHelper configurazioneUtenteMovimentoFatto = gestisciParametroConfigurazioneUtente(
	//		WebConstants.CONF_UTENTE_SCAD_COLONNA_MOVIMENTO_FATTO, "0", false);
	//	isVisualizzaAlemnoUnaColonna = (configurazioneUtenteMovimentoFatto.getValore().equalsIgnoreCase("1") || isVisualizzaAlemnoUnaColonna) ? true
	//		: false;
	//	
	ConfigurazioneUtenteHelper configurazioneUtenteRichiedente = gestisciParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_SCAD_COLONNA_RICHIEDENTE, "0", false);
	isVisualizzaAlmenoUnaColonna = (configurazioneUtenteRichiedente.getValore().equalsIgnoreCase("1") || isVisualizzaAlmenoUnaColonna) ? true
		: false;
	//
	List<ConfigurazioneUtenteHelper> configurazioneUtenteHelpers = new ArrayList<ConfigurazioneUtenteHelper>(0);
	configurazioneUtenteHelpers.add(configurazioneUtenteStato);
	configurazioneUtenteHelpers.add(configurazioneUtenteSoftware);
	configurazioneUtenteHelpers.add(configurazioneUtenteIntervento);
	configurazioneUtenteHelpers.add(configurazioneUtenteProcedimenti);
	configurazioneUtenteHelpers.add(configurazioneUtentePosArchivio);
	configurazioneUtenteHelpers.add(configurazioneUtenteEndo);
	configurazioneUtenteHelpers.add(configurazioneUtenteDataPresentazioneIstanza);
	configurazioneUtenteHelpers.add(configurazioneUtenteTermineProcedimento);
	configurazioneUtenteHelpers.add(configurazioneUtenteOperatore);
	configurazioneUtenteHelpers.add(configurazioneUtenteIstruttore);
	configurazioneUtenteHelpers.add(configurazioneUtenteAmministrazione);
	//	configurazioneUtenteHelpers.add(configurazioneUtenteMovimentoFatto);
	configurazioneUtenteHelpers.add(configurazioneUtenteRichiedente);
	model.addAttribute("configurazioneUtenteHelpers", configurazioneUtenteHelpers);
	model.addAttribute("isVisualizzaAlemnoUnaColonna", isVisualizzaAlmenoUnaColonna);
    }

    @RequestMapping
    public String saveParametriscadenzario(@ModelAttribute("responsabile") ResponsabiliCommand responsabile, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request) {

	Responsabili resp = responsabile.getEntity();
	Integer codiceResponsabile = resp.getId().getCodice();
	//  FIXME trick per far funzionare il salvataggio del campo sca_operatore.
	if (EntityUtils.getNestedProperty(responsabile.getScadOperatore(), "chiave") != null) {
	    resp.setScadOperatoreId(responsabile.getScadOperatore().getChiave());
	} else {
	    resp.setScadOperatoreId(null);
	}
	try {
	    responsabiliService.update(resp);
	    responsabiliScadenzarioService.aggiornaParametriConfigurazioneScadenzario(ScadenzarioOneri.SCADENZARIO_ONERI,
		    ScadenzarioOneri.GIORNI_DA_DATA_ATTUALE, String.valueOf(responsabile.getNgiorniDaOggi()));
	    if (StringUtils.isNotBlank(responsabile.getValoreOrdinamentoData())) {
		ConfigurazioneutenteId idOrdinamentoDataScadenzario = new ConfigurazioneutenteId(codiceResponsabile,
			WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
		Configurazioneutente configurazioneUtenteOrdinamnetoDataScadezario = configurazioneutenteService
			.findById(idOrdinamentoDataScadenzario);
		if (configurazioneUtenteOrdinamnetoDataScadezario == null) {
		    configurazioneUtenteOrdinamnetoDataScadezario = new Configurazioneutente();
		    configurazioneUtenteOrdinamnetoDataScadezario.setId(idOrdinamentoDataScadenzario);
		    configurazioneUtenteOrdinamnetoDataScadezario.setResponsabile(resp);
		    configurazioneUtenteOrdinamnetoDataScadezario
			    .setValore(StringUtils.defaultString(responsabile.getValoreOrdinamentoData().trim()));
		    configurazioneutenteService.insert(configurazioneUtenteOrdinamnetoDataScadezario);
		} else {
		    configurazioneUtenteOrdinamnetoDataScadezario
			    .setValore(StringUtils.defaultString(responsabile.getValoreOrdinamentoData()).trim());
		    configurazioneutenteService.update(configurazioneUtenteOrdinamnetoDataScadezario);
		}
	    }
	} catch (Exception e) {
	    Responsabili entity = responsabiliService.findById(new PkId(codiceResponsabile));
	    if (entity.getScadOperatoreId() != null) {
		ScadenzarioOperatoreChiaveValore scadOperatoreCvb = getScadOperatore(entity);
		responsabile.setScadOperatore(scadOperatoreCvb);
	    }
	    copyErrorsToBindingResult(result, responsabile, e);
	    responsabile.setEntity(entity);
	    responsabile.setResponsabilisoftwareList(getListResponsabilisoftware(entity));
	    responsabile.setResponsabilicomuniList(getListResponsabilicomuni(entity));
	    model.addAttribute("responsabile", responsabile);
	    setPageAttributes(model);
	    setScadenzarioAttribute(responsabile, model, request);
	    fixRenderResponsabiliEntityProperty(entity);
	    return "responsabili/formScadenzario";
	}
	// status.setComplete();
	return "redirect:viewParametriscadenzario.htm?status_msg=02";
    }

    @RequestMapping
    public String deleteFoRichiesta(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	FoRichieste fori = foRichiesteService.findById(new PkId(codice));
	foRichiesteService.delete(fori);
	return "redirect:list.htm?tab=" + WebConstants.TAB_RICHIESTE_FO_NON_NON_LETTE + "&status_msg=02";
    }

    private void fixRenderResponsabiliEntityProperty(Responsabili entity) {

	if (entity.getTiporesponsabile() == null) {
	    entity.setTiporesponsabile(new Tipiresponsabili());
	}
	if (entity.getProtTprofilassegnazione() == null) {
	    entity.setProtTprofilassegnazione(new ProtTprofilassegnazione());
	}
	if (entity.getScadSoftware() == null) {
	    entity.setScadSoftware(new Software());
	    entity.getScadSoftware().setCodice(null);
	}
    }

    /**
     * Restituisce la lista di tutti i comuni che possono essere associati in una lista di Responsabilicomuni
     * 
     * @param responsabile
     * 
     */
    private Set<Responsabilicomuni> getListResponsabilicomuni(Responsabili responsabile) {

	Set<Responsabilicomuni> responsabilicomuniList = new LinkedHashSet<Responsabilicomuni>();
	List<Comuniassociati> comuniassociatiList = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	for (Comuniassociati comuniassociati : comuniassociatiList) {
	    Responsabilicomuni responsabilicomuni = new Responsabilicomuni();
	    ResponsabilicomuniId idRc = new ResponsabilicomuniId();
	    idRc.setCodicecomune(comuniassociati.getId().getCodicecomune());
	    idRc.setCodiceresponsabile(responsabile.getId().getCodice());
	    responsabilicomuni.setId(idRc);
	    responsabilicomuni.setResponsabile(responsabile);
	    responsabilicomuni.setComune(comuniassociati.getComune());
	    responsabilicomuniList.add(responsabilicomuni);
	}
	return responsabilicomuniList;
    }

    @RequestMapping
    public void ajaxSegnaLettoRichiestaFo(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	FoRichieste fo = foRichiesteService.findById(new PkId(codice));
	Boolean value = fo.getFlagLetto();
	if (value == null) {
	    value = Boolean.FALSE;
	}
	if (BooleanUtils.isFalse(value)) {
	    value = Boolean.TRUE;
	} else {
	    value = Boolean.FALSE;
	}
	fo.setFlagLetto(value);
	foRichiesteService.update(fo);
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    /**
     * Restituisce la lista di tutti i software che possono essere attivati in una lista di Responsabilisoftware
     * 
     * @param responsabile
     * 
     */
    private Set<Responsabilisoftware> getListResponsabilisoftware(Responsabili responsabile) {

	Set<Responsabilisoftware> responsabilisoftwareList = new LinkedHashSet<Responsabilisoftware>();
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	for (Software software : softwares) {
	    Responsabilisoftware responsabilisoftware = new Responsabilisoftware();
	    ResponsabilisoftwareId idSw = new ResponsabilisoftwareId(software.getCodice(), responsabile.getId().getCodice());
	    Software sw = softwareService.findById(software.getCodice());
	    responsabilisoftware.setSoftware(sw);
	    responsabilisoftware.setResponsabili(responsabile);
	    responsabilisoftware.setId(idSw);
	    responsabilisoftwareList.add(responsabilisoftware);
	}
	return responsabilisoftwareList;
    }

    @Override
    protected void fixMergeEntityProperty(BatchScadenzario entity) {

	//.. non usato
    }

    @Override
    protected void fixRenderEntityProperty(BatchScadenzario entity) {

	//.. non usato
    }

    @Override
    protected void setPageAttributes(Model model) {

	//.. non usato
    }

    /**
     * Imposta l'intervallo di date all'interno del quale ricercare le scadenze.<br />
     * Le date sono calcolate a partire dai valori(giorni) dei campi 'scadDataInizio' o 'scadenzarioprec' e
     * 'numggscadenz' della tabella responsabili.<br />
     * Se non sono impostate allora rimane inalterato il valore di default dell'oggetto filter che corrisponde alla data
     * odierna
     * 
     * @param batchScadenzarioFilter
     * @param loggedUser
     */
    private void setDates(BatchScadenzarioFilter batchScadenzarioFilter, Responsabili loggedUser) {

	// Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	Calendar dallaData = new GregorianCalendar();
	Calendar allaData = new GregorianCalendar();
	batchScadenzarioFilter.setDallaData(null);
	if (loggedUser.getScadDatainizio() != null) {
	    batchScadenzarioFilter.setDallaData(loggedUser.getScadDatainizio());
	} else {
	    if (loggedUser.getScadenzarioprec() != null && loggedUser.getScadenzarioprec() > 0) {
		dallaData.add(Calendar.DATE, -loggedUser.getScadenzarioprec());
		batchScadenzarioFilter.setDallaData(dallaData.getTime());
	    }
	}
	if (loggedUser.getNumggscadenz() != null && loggedUser.getNumggscadenz() > 0) {
	    allaData.add(Calendar.DATE, loggedUser.getNumggscadenz());
	    batchScadenzarioFilter.setAllaData(allaData.getTime());
	}
    }

    /**
     * verifica se l'operatore è amministratore o amministratore software
     * 
     * @param loggedUser
     * @return
     */
    private boolean isAmmOrAmmSoftware(Responsabili loggedUser) {

	boolean success = false;
	if (StringUtils.isNotBlank(loggedUser.getAmministratore()) && loggedUser.getAmministratore().equals("1")) {
	    success = true;
	} else {
	    if (StringUtils.isNotBlank(loggedUser.getAmministratoresoftware()) && loggedUser.getAmministratoresoftware().equals("1")) {
		success = true;
	    }
	}
	return success;
    }
}
