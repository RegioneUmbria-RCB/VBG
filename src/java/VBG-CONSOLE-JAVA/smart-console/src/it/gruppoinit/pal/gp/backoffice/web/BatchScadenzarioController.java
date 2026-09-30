package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.FoRichieste;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtTprofilassegnazione;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.ResponsabilicomuniId;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipiresponsabili;
import it.gruppoinit.pal.gp.core.domain.helper.EventiSistemaHelperTable;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.domain.web.ResponsabiliCommand;
import it.gruppoinit.pal.gp.core.domain.web.ScadenzarioOperatoreChiaveValore;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.FoRichiesteService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

// DAELIMINARE @Controller
@SessionAttributes(value = { "batchScadenzarioFilter", "responsabile" })
public class BatchScadenzarioController extends BaseController<BatchScadenzarioFilter> {

    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    private ComuniassociatiService comuniassociatiService;
    private FoRichiesteService foRichiesteService;
    private SoftwareService softwareService;
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeeventiService istanzeeventiService;
    private ResponsabiliService responsabiliService;
    private UserSecurityService userSecurityService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private OggettiMetadatiService oggettiMetadatiService;

    @Autowired
    public void setOggettiMetadatiService(OggettiMetadatiService oggettiMetadatiService) {

	this.oggettiMetadatiService = oggettiMetadatiService;
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
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @RequestMapping
    public String createSearch(Model model, HttpServletRequest request) {

	// BatchScadenzarioFilter batchScadenzarioFilter = new BatchScadenzarioFilter();
	Responsabili loggedUser = getCurrentlyAuthenticatedUserDetails();
	gestOperatoreReadonly(model, loggedUser);
	// imposto il filtro per responsabile solo se l'utente non è amministratore o amministratore software
	//	batchScadenzarioFilter.setUtenteLoggato(loggedUser);
	//	if (isAmmOrAmmSoftware(loggedUser)) {
	//	    // sulla jsp visualizzo la possibilità di scelta del responsabile come filtro
	//	    model.addAttribute("isAmministratore", true);
	//	}
	//	setDates(batchScadenzarioFilter, loggedUser);
	Software software = softwareService.findById(ORMHelper.getSoftware());
	// 	model.addAttribute("batchScadenzarioFilter", batchScadenzarioFilter);
	//Setta in request il paramtro della configurazione utente
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "1", request);
	return "batchscadenzario/search";
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
	batchScadenzarioFilter.setOrdinamentoScadenze(OrderTypeEnum.valueOf(tipoOrdinamentoScadenze));
	if (showScadenzario) {
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI, "1", request);
	    String ssi = (String) request.getAttribute(WebConstants.CONF_UTENTE_SCADENZARIO_SOLO_SCADENZE_IMPORTANTI);
	    if (StringUtils.defaultIfEmpty(ssi, "0").equalsIgnoreCase("1")) {
		//		batchScadenzarioFilter.setSoloScadenzeImportanti(Boolean.TRUE);
		//		List<ResponsabiliTmAvv> ravv = responsabiliTmAvvService.findByResponsabile(utenteLoggato.getId().getCodice());
		//		List<Tipimovimento> tmavvs = new ArrayList<Tipimovimento>();
		//		for (ResponsabiliTmAvv rTmAvv : ravv) {
		//		    Tipimovimento tm = rTmAvv.getTipimovimento();
		//		    tmavvs.add(tm);
		//		}
		//		batchScadenzarioFilter.setTipimovimentoAvv(tmavvs);
		//		///// 
		//		List<ResponsabiliTmSca> rsca = responsabiliTmScaService.findByResponsabile(utenteLoggato.getId().getCodice());
		//		List<Tipimovimento> tmscas = new ArrayList<Tipimovimento>();
		//		for (ResponsabiliTmSca rTmSca : rsca) {
		//		    Tipimovimento tm = rTmSca.getTipimovimento();
		//		    tmscas.add(tm);
		//		}
		//		batchScadenzarioFilter.setTipimovimentoSca(tmscas);
	    } else {
		//		batchScadenzarioFilter.setSoloScadenzeImportanti(Boolean.FALSE);
		//		batchScadenzarioFilter.setTipimovimentoAvv(new ArrayList<Tipimovimento>());
		//		batchScadenzarioFilter.setTipimovimentoSca(new ArrayList<Tipimovimento>());
	    }
	    ///////////////////////////////// TABELLA LISTA MOVIMENTI DA EFFETTUARE ///////////////////////////////////////////////////////////////
	    if (StringUtils.defaultIfEmpty(movimentiDaEffettuare, "0").equalsIgnoreCase("1")) {
		//		GenerateTable<ScadenzarioListHelper> batchScadenzarioTable = new BatchScadenzarioHelperTable(batchScadenzarioFilter,
		//			batchScadenzarioService, false);
		//		String batchScadenzarioHtmlTable = batchScadenzarioTable.createJMesaList(request, response, "label.movimenti_da_effettuare",
		//			"batchscadenzario_id", true);
		//		if (batchScadenzarioHtmlTable == null) {
		//		    return null;
		//		}
		//		numeroMovimentiDaEffettuare = ((BatchScadenzarioHelperTable) batchScadenzarioTable).getCountedRecords();
		//		model.addAttribute("batchScadenzarioHtmlTable", batchScadenzarioHtmlTable);
	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA MOVIMENTI DA VISIONARE //////////////////////////////////////////////////////////////
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA MOVIMENTI STC NON NOTIFICATI //////////////////////////////////////////////////////////////
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA ISTANZE STC //////////////////////////////////////////////////////////////////////
	    //	    if (StringUtils.defaultIfEmpty(istanzeStc, "0").equalsIgnoreCase("1")) {
	    //		GenerateTable<DomandeSTCScadenzarioDTO> istanzeStcTable = new IstanzeScadenzarioStcTable(utenteLoggato, userSecurityService,
	    //			verticalizzazioniparametriService, false, true);
	    //		String istanzeStcHtmlTable = istanzeStcTable.createJMesaList(request, response, "label.nuove_istanze_stc", "istanze_stc_id", false);
	    //		if (istanzeStcHtmlTable == null) {
	    //		    return null;
	    //		}
	    //		model.addAttribute("istanzeStcHtmlTable", istanzeStcHtmlTable);
	    //		numeroIstanzeStc = ((IstanzeScadenzarioStcTable) istanzeStcTable).getCountedRecords();
	    //	    }
	    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA ISTANZE STC NON IMPORTATE //////////////////////////////////////////////////////////////////////
	    //	    if (StringUtils.defaultIfEmpty(istanzeNonImporateStc, "0").equalsIgnoreCase("1")) {
	    //		GenerateTable<DomandeSTCScadenzarioDTO> istanzeStcNonImportateTable = new IstanzeScadenzarioStcTable(utenteLoggato,
	    //			userSecurityService, verticalizzazioniparametriService, false, false);
	    //		String istanzeNonImportateStcHtmlTable = istanzeStcNonImportateTable.createJMesaList(request, response,
	    //			"label.istanze_non_importate_stc", "label.istanze_non_importate_stc_id", false);
	    //		if (istanzeNonImportateStcHtmlTable == null) {
	    //		    return null;
	    //		}
	    //		model.addAttribute("istanzeNonImportateStcHtmlTable", istanzeNonImportateStcHtmlTable);
	    //		numeroIstanzeNonImportateStc = ((IstanzeScadenzarioStcTable) istanzeStcNonImportateTable).getCountedRecords();
	    //	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA RICHIESTE FRONT-END /////////////////////////////////////////////////////////////////////
	    //	    if (StringUtils.defaultIfEmpty(richiesteFo, "0").equalsIgnoreCase("1")) {
	    //		GenerateTable<FoRichieste> foRichiesteTable = new RichiesteFoNonLetteTable(foRichiesteService);
	    //		String foRichiesteHtmlTable = foRichiesteTable.createJMesaList(request, response, "label.richieste_frontoffice",
	    //			"richieste_frontoffice_id", true);
	    //		if (foRichiesteHtmlTable == null) {
	    //		    return null;
	    //		}
	    //		model.addAttribute("foRichiesteHtmlTable", foRichiesteHtmlTable);
	    //		// Recupero il numero dei record presenti nella lista tramite una count
	    //		numeroRichiesteNonLette = foRichiesteService.countRichiesteNonLette();
	    //	    }
	    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ///////////////////////////////////  TABELLA LISTA DOCUMENTI DA FIRMARE /////////////////////////////////////////////////////////////////////
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
	boolean isVertSTCAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	model.addAttribute("isVertSTCAttiva", isVertSTCAttiva);
	if (isVertSTCAttiva) {
	    // recupero il conteggio tramite la count
	    model.addAttribute("showStc", true);
	} else {
	    model.addAttribute("showStc", false);
	}
	String defTab = "tabScadenzario";
	String eventiNonLettiConf = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI, "1", request);
	// "Scadenzario","MovNonLetti","MovSTCNonNotificati","IstanzeStc","IstanzeStcNonImportate""RichiesteFoNonLette","EventiNonLetti","DocumentiDaFirmare"
	if (StringUtils.defaultIfEmpty(movimentiDaEffettuare, "0").equalsIgnoreCase("0")) {
	    if (StringUtils.defaultIfEmpty(movimentiDaVisionare, "0").equalsIgnoreCase("0")) {
		if (StringUtils.defaultIfEmpty(movimentiNonNotificati, "0").equalsIgnoreCase("0")) {
		    if (StringUtils.defaultIfEmpty(istanzeStc, "0").equalsIgnoreCase("0")) {
			if (StringUtils.defaultIfEmpty(istanzeNonImporateStc, "0").equalsIgnoreCase("0")) {
			    if (StringUtils.defaultIfEmpty(richiesteFo, "0").equalsIgnoreCase("0")) {
				if (StringUtils.defaultIfEmpty(eventiNonLettiConf, "0").equalsIgnoreCase("0")) {
				    defTab = "tabEventiNonLetti";
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
	//
	ConfigurazioneutenteId id = new ConfigurazioneutenteId(responsabili.getEntity().getId().getCodice(), WebConstants.CONF_UTENTE_PAGINA_CENTRALE);
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
    }

    //    private void setScadenzarioAttribute(Responsabili entity, Model model, HttpServletRequest request) {
    //
    //	List<Software> softwares = softwareService.findSoftwareAbilitati(entity, true);
    //	model.addAttribute("softwares", softwares);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_EFFETTUARE, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_DA_VISIONARE, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_MOV_NON_NOTIFICATI, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_RICHIESTE_FO, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_NON_LETTI, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_ISTANZE_STC_NON_IMPORTATE, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_EVENTI_SISTEMA, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCAD_VISUALIZZA_DOCUMENTI_DA_FIRMARE, "1", request);
    //	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA, "DESC", request);
    //	model.addAttribute("isBatchScadenzarioPage", Boolean.TRUE);
    //    }
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
	    if (StringUtils.isNotBlank(responsabile.getValoreOrdinamentoData())) {
		ConfigurazioneutenteId idOrdinamnetoDataScadezario = new ConfigurazioneutenteId(codiceResponsabile,
			WebConstants.CONF_UTENTE_SCADENZARIO_ORDINAMENTO_DATA);
		Configurazioneutente configurazioneUtenteOrdinamnetoDataScadezario = configurazioneutenteService
			.findById(idOrdinamnetoDataScadezario);
		if (configurazioneUtenteOrdinamnetoDataScadezario == null) {
		    configurazioneUtenteOrdinamnetoDataScadezario = new Configurazioneutente();
		    configurazioneUtenteOrdinamnetoDataScadezario.setId(idOrdinamnetoDataScadezario);
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
	//	if (entity.getScadOperatore() == null) {
	//	    entity.setScadOperatore(new Responsabili());
	//	}
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
    protected void fixMergeEntityProperty(BatchScadenzarioFilter entity) {

	//.. non usato
    }

    @Override
    protected void fixRenderEntityProperty(BatchScadenzarioFilter entity) {

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
