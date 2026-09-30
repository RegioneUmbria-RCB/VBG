package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.JdkVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AppIoServizi;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiCategorie;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiAssenzeTipoCalcoloEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.AppIoServiziConfigRestResponse;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoServiziConfigService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ComunicazioniMassUtils;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ComunicazioniUtilsGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.ConfigurazioniComunicazioneGen;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniToGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.QueriesConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model.ComunicazioneMassivaGenModel;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.jobs.ComunicazioniMassiveJob;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniCommissioniCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniGenCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioniMassiveModel;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.GiornisectimanaService;
import it.gruppoinit.pal.gp.core.service.JobRepositoryService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.ManifestazioniService;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatistradarioService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;

/**
 * 
 * @author gianpaolot
 */
@Controller
@SessionAttributes(value = { "mercati", "mercatiuso", "mercatistradario", "mercatid", "mercaticonsorzi", "mercatispunte",
	"comunicazioniCommissioniCommand" })
public class MercatiController extends BaseController<Mercati> {

    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ManifestazioniService manifestazioniService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private GiornisectimanaService giornisectimanaService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private MercatistradarioService mercatistradarioService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private MercatiConsorziService mercatiConsorziService;
    @Autowired
    private MercatiResponsabiliService mercatiResponsabiliService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MercatiSpunteService mercatiSpunteService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private ConcessionicausaliService concessionicausaliService;
    private IComunicazioniManifestazioniService comunicazioniMassiveService;
    private IComunicazioniGenService comunicazioniMassiveMService;
    private IComunicazioniToGenService comunicazioniToGenService;
    private JobRepositoryService jobRepositoryService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private MailtipoService mailTipoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    protected AnagrafeService anagrafeService;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;

    @Autowired
    public void setComunicazioniMassiveService(IComunicazioniManifestazioniService comunicazioniMassiveService) {

	this.comunicazioniMassiveService = comunicazioniMassiveService;
    }

    @Autowired
    public void setComunicazioniMassiveMService(@Qualifier("comunicazioniMercServiceImpl") IComunicazioniGenService comunicazioniMassiveMService) {

	this.comunicazioniMassiveMService = comunicazioniMassiveMService;
    }

    @Autowired
    public void setComunicazioniToGenService(IComunicazioniToGenService comunicazioniToGenService) {

	this.comunicazioniToGenService = comunicazioniToGenService;
    }

    @Autowired
    public void setJobRepositoryService(JobRepositoryService jobRepositoryService) {

	this.jobRepositoryService = jobRepositoryService;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Autowired
    ComunicazioniUtilsGenService comunicazioniUtilsGenService;
    @Autowired
    private IAppIoServiziConfigService appIoServiziConfigService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	int c = mercatiResponsabiliService.countByResponsabile(r.getId().getCodice());
	List<Mercati> mercatiList = null;
	if (c > 0) {
	    mercatiList = mercatiService.findByDescrizioneAndResponsabile("", r.getId().getCodice(), MercatiEnum.ALL, null, null);
	} else {
	    mercatiList = mercatiService.findAll(null, null);
	}
	ModelMap model = new ModelMap(mercatiList);
	boolean export = createJMesaExport(request, response, mercatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiList", mercatiList);
	return model;
    }

    @RequestMapping
    public String appbootstrap(@RequestParam(value = "idGiornata", required = false) Integer idGiornata, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String urlAppSpuntaDigitale = this.comportamentiMercatiService.urlAppSpuntaDigitale();
	if (StringUtils.isBlank(urlAppSpuntaDigitale)) {
	    throw new InvalidConfigurationException("Non sono state attivate le configurazioni per l'app SPUNTA DIGITALE");
	}
	String suffisso = "";
	if (idGiornata != null) {
	    suffisso = "?goto=%2Fmercato%2F" + idGiornata.intValue();
	} else {
	    Cookie cookie = new Cookie(WebConstants.COOKIEURL_SESSIONE_SCADUTA, urlAppSpuntaDigitale);
	    if (JdkVersion.getMajorJavaVersion() == JdkVersion.JAVA_16) {
		cookie.setPath(";Path=" + request.getContextPath() + ";HttpOnly;");
	    } else {
		cookie.setPath(request.getContextPath());
	    }
	    cookie.setMaxAge(-1);
	    response.addCookie(cookie);
	}
	String redirect = urlAppSpuntaDigitale + ORMHelper.getToken() + suffisso;
	if (!externalDBResolver.checkTokenValidity(ORMHelper.getToken())) {
	    String externalAuthUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URL);
	    String urlFirstRequest = URLEncoder.encode(request.getContextPath() + "/mercati/appbootstrap.htm?" + WebConstants.IDCOMUNE_ALIAS + "=" +
						       ORMHelper.getIdcomuneAlias() + "&" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware(),
		    "UTF-8");
	    externalAuthUrl += "?return_to=" + urlFirstRequest + "&" + WebConstants.IDCOMUNE_ALIAS + "=" + ORMHelper.getIdcomuneAlias() +
			       "&contesto=OPE";
	    redirect = externalAuthUrl;
	}
	return "redirect:" + redirect;
    }

    @RequestMapping
    public String create(Model model) {

	// recupero le manifestazioni
	List<Manifestazioni> listmanifestazioni = manifestazioniService.findAll(null, null);
	Mercati mercati = new Mercati();
	mercati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("listmanifestazioni", listmanifestazioni);
	setPageAttributes(model);
	return "mercati/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status) {

	// recupero la manifestazione passata
	Manifestazioni manifestazione = manifestazioniService.findById(mercati.getManifestazione().getCodice());
	mercati.setManifestazione(manifestazione);
	fixMergeEntityProperty(mercati);
	mercati.setSoftware(softwareService.findById(ORMHelper.getSoftware()));
	try {
	    mercatiService.insert(mercati);
	} catch (Exception e) {
	    // recupero le manifestazioni
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    setPageAttributes(model, mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercati.getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String insertComunicazioneMa(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand, BindingResult result,
	    HttpServletRequest request, HttpServletResponse response) {

	try {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(false);
	    Date dallaDataM = null;
	    if (!StringUtils.isBlank(comunicazioniCommissioniCommand.getDallaDataM())) {
		try {
		    dallaDataM = sdf.parse(comunicazioniCommissioniCommand.getDallaDataM());
		} catch (ParseException e) {
		    // TODO Auto-generated catch block
		    throw new RuntimeException(e);
		}
	    }
	    Date allaDataM = null;
	    if (!StringUtils.isBlank(comunicazioniCommissioniCommand.getAllaDataM())) {
		try {
		    allaDataM = sdf.parse(comunicazioniCommissioniCommand.getAllaDataM());
		} catch (ParseException e) {
		    throw new RuntimeException(e);
		}
	    }
	    boolean isConcessionari = false;
	    boolean isSpuntisti = false;
	    if ("tutti".equals(comunicazioniCommissioniCommand.getTipoDestinatario())) {
		isConcessionari = true;
		isSpuntisti = true;
	    } else if ("concessionari".equals(comunicazioniCommissioniCommand.getTipoDestinatario())) {
		isConcessionari = true;
	    } else {
		isSpuntisti = true;
	    }
	    int[] mercatiIds;
	    if (comunicazioniCommissioniCommand.getIdsmercato() == null || comunicazioniCommissioniCommand.getIdsmercato().length == 0) {
		mercatiIds = null;
		if ("scegli".equals(comunicazioniCommissioniCommand.getManifestazioneRadio())) {
		    throw new RuntimeException("Scelta manifestazioni manuale, ma nessun mercato selezionato");
		}
	    } else {
		mercatiIds = comunicazioniCommissioniCommand.getIdsmercato();
		if ("tutte".equals(comunicazioniCommissioniCommand.getManifestazioneRadio())) {
		    throw new RuntimeException("Scelta manifestazioni non manuale, ma risultano mercati selezionati");
		}
	    }
	    boolean isAutorizzazione = false;
	    if ("autorizzazione".equals(comunicazioniCommissioniCommand.getTipoInvioMercato())) {
		isAutorizzazione = true;
	    }
	    boolean isOccupante = false;
	    if ("occupante".equals(comunicazioniCommissioniCommand.getTipoAnagrafe())) {
		isOccupante = true;
	    }
	    if (!comunicazioniCommissioniCommand.isScegliMailChckN() && !comunicazioniCommissioniCommand.isScegliAppioChckN()
		    && !comunicazioniCommissioniCommand.getProtocollaParametriCommand().isProtocolla()) {
		throw new Exception("Tipo comunicazione non valorizzato");
	    }
	    //	    if(mercatiIds == null || mercatiIds.length == 0) {
	    //		throw new Exception("ids mercati non valorizzati");
	    //	    }
	    Map<String, String> altriparametri = new HashMap<String, String>();
	    if (comunicazioniCommissioniCommand.isScegliMailChckN() || comunicazioniCommissioniCommand.isScegliAppioChckN()) {
		if (StringUtils.isBlank(comunicazioniCommissioniCommand.getOggettoEmail())) {
		    throw new RuntimeException("oggetto non valorizzato");
		}
		if (StringUtils.isBlank(comunicazioniCommissioniCommand.getBodyEmail())) {
		    throw new RuntimeException("body non valorizzato");
		}
		if (comunicazioniCommissioniCommand.isScegliAppioChckN()) {
		    try {
			if (comunicazioniCommissioniCommand.getOggettoEmail().getBytes("UTF-8").length < 10) {
			    throw new RuntimeException("L'oggetto non può avere lunghezza inferiore a 10");
			}
			if (comunicazioniCommissioniCommand.getOggettoEmail().getBytes("UTF-8").length > 120) {
			    throw new RuntimeException("L'oggetto non può avere lunghezza superiore a 120");
			}
			if (comunicazioniCommissioniCommand.getBodyEmail().getBytes("UTF-8").length < 80) {
			    throw new RuntimeException("Il corpo non può avere lunghezza inferiore a 80");
			}
		    } catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		    }
		}
		altriparametri.put("OGGETTOMAIL_NAME", comunicazioniCommissioniCommand.getOggettoEmail());
		altriparametri.put("BODYMAIL_NAME", comunicazioniCommissioniCommand.getBodyEmail());
	    }
	    if (comunicazioniCommissioniCommand.isScegliMailChckN()) {
		altriparametri.put(ConfigurazioneComunicazioniCommissioni.GESTIONE_SCELTA_MAIL_ANAGRAFE,
			comunicazioniCommissioniCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice());
	    }
	    if (comunicazioniCommissioniCommand.isScegliAppioChckN()) {
		altriparametri.put("APPIO_SERVIZIO", request.getParameter("servizioappio"));
	    }
	    if (!isAutorizzazione) {
		comunicazioniCommissioniCommand.getAllegaticompilabili().clear();
		comunicazioniCommissioniCommand.setConvertiPDF(false);
	    }
	    if (comunicazioniCommissioniCommand.isScegliAppioChckN() && !comunicazioniCommissioniCommand.isScegliMailChckN()
		    && !comunicazioniCommissioniCommand.getProtocollaParametriCommand().isProtocolla()) {
		comunicazioniCommissioniCommand.getAllegatiFissi().clear();
		comunicazioniCommissioniCommand.getAllegaticompilabili().clear();
		comunicazioniCommissioniCommand.getFirmatari().clear();
		comunicazioniCommissioniCommand.setConvertiPDF(false);
	    }
	    ConfigurazioniComunicazioneGen c = ComunicazioniMassUtils.popolaConfigurazioneComunicazioniCommissioni(comunicazioniCommissioniCommand,
		    ContestoComunicazioneEnum.MERCATI, 0, altriparametri);
	    c.setSpuntisti(isSpuntisti);
	    c.setConcessionario(isConcessionari);
	    c.setAutorizzazioniGroup(isAutorizzazione);
	    c.setDataInizio(dallaDataM);
	    c.setDataFine(allaDataM);
	    c.setIdsmercati(mercatiIds);
	    c.setIstitolare(!isOccupante);
	    int idTestata = comunicazioniMassiveMService.creaNuovaComunicazione(c);
	    // di modalità inserimento multiplo)
	    return "redirect:../mercati/viewComunicazioneM.htm?idcomunicazione=" + idTestata;
	} catch (Exception e) {
	    e.printStackTrace();
	    copyErrorsToBindingResult(result, comunicazioniCommissioniCommand, e);
	    popolaModelMa(model, comunicazioniCommissioniCommand);
	    return "mercati/createM";
	}
    }

    private void popolaModelMa(Model model, ComunicazioniGenCommand cmd) {

	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
	for (IParametriProtocolloPerEnteHelper param : cmd.getProtocollaParametriCommand().getParametriPerEnte()) {
	    if (param.getAmmMittente() != null && param.getAmmMittente().getId() != null) {
		Amministrazioni ammMitente = amministrazioniService.findById(new PkId(param.getAmmMittente().getId()));
		if (ammMitente == null || ammMitente.getId() == null || ammMitente.getId().getCodice() == null
			|| StringUtils.isBlank(ammMitente.getAmministrazione())) {
		    continue;
		}
		param.setAmmMittente(new IdentificativoDescrizioneBean(ammMitente.getId().getCodice(), ammMitente.getAmministrazione()));
	    }
	}
	List<Map<String, String>> appioservizilist;
	if(cmd.isCodiciComuneUguale()){
	    appioservizilist = findAppioServiziList(cmd.getCodicecomuneComunicazione());
	}else{
	    appioservizilist = findAppioServiziList(null);
	}
	model.addAttribute("appioservizilist", appioservizilist);
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	// recupero le manifestazioni
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	boolean isAttivaGiornateNulle = this.comportamentiMercatiService.isAttivaGiornateNulle();
	if (isAttivaGiornateNulle) {
	    model.addAttribute("assenzeTipoCalcoloList", MercatiAssenzeTipoCalcoloEnum.values());
	}
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatid", new MercatiD());
	model.addAttribute("isAttivaGiornateNulle", isAttivaGiornateNulle);
	setPageAttributes(model, mercati);
	setPageAttributes(model);
	return "mercati/form";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public String listComunicazioni(@RequestParam("codicemercato") Integer codiceMercato, Model model, HttpServletRequest request) {

	if (codiceMercato == null) {
	    throw new IllegalArgumentException(
		    "Impossibile trovare le comunicazioni massive senza passare il codice della manifestazione di riferimento");
	}
	Mercati manifestazione = this.mercatiService.findById(new PkId(codiceMercato));
	if (manifestazione == null) {
	    throw new IllegalArgumentException("La manifestazione con codice " + codiceMercato + " non esiste");
	}
	ComunicazioniMassiveModel dettaglioComunicazioni = new ComunicazioniMassiveModel();
	dettaglioComunicazioni.setAlias(ORMHelper.getIdcomuneAlias());
	dettaglioComunicazioni.setSoftware(ORMHelper.getSoftware());
	dettaglioComunicazioni.setCodiceManifestazione(manifestazione.getId().getCodice());
	dettaglioComunicazioni.setManifestazione(manifestazione.getDescrizione());
	List<ListaComunicazioniResoconti> elenco = this.comunicazioniMassiveService.creaListaTestata(codiceMercato);
	dettaglioComunicazioni.setComunicazioni(elenco);
	model.addAttribute("schedulerAttivo", this.jobRepositoryService.isJobAttivo(ComunicazioniMassiveJob.class.getName()));
	model.addAttribute("dettaglio", dettaglioComunicazioni);
	return "mercati/listComunicazioni";
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping
    public String listComunicazioniM(Model model, HttpServletRequest request) {

	ComunicazioniMassiveModel dettaglioComunicazioni = new ComunicazioniMassiveModel();
	dettaglioComunicazioni.setAlias(ORMHelper.getIdcomuneAlias());
	dettaglioComunicazioni.setSoftware(ORMHelper.getSoftware());
	dettaglioComunicazioni.setManifestazione("Comunicazioni mercati");
	List<ListaComunicazioniResoconti> elenco = this.comunicazioniMassiveMService.creaListaTestataGen(QueriesConstants.MERCATISELECT,
		QueriesConstants.toArray(ORMHelper.getIdcomune(), ORMHelper.getSoftware()), "fkid_testata");
	dettaglioComunicazioni.setComunicazioni(elenco);
	model.addAttribute("schedulerAttivo", true);
	model.addAttribute("dettaglio", dettaglioComunicazioni);
	model.addAttribute("urlnuovacomunicazione", "'preselezionaMercatiMa.htm'");
	return "mercati/listComunicazioniM";
    }

    @RequestMapping
    public String viewComunicazione(@RequestParam("codicemercato") Integer codiceMercato, @RequestParam("idcomunicazione") Integer idComunicazione,
	    Model model, HttpServletRequest request) {

	if (codiceMercato == null) {
	    throw new IllegalArgumentException(
		    "Impossibile trovare le comunicazioni massive senza passare il codice della manifestazione di riferimento");
	}
	Mercati manifestazione = this.mercatiService.findById(new PkId(codiceMercato));
	if (manifestazione == null) {
	    throw new IllegalArgumentException("La manifestazione con codice " + codiceMercato + " non esiste");
	}
	if (idComunicazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile trovare la comunicazione massiva senza passare il codice della comunicazione da visualizzare");
	}
	ComunicazioneMassivaModel comunicazione = this.comunicazioniMassiveService.getComunicazioneByIdTestata(idComunicazione);
	model.addAttribute("codiceManifestazione", codiceMercato);
	model.addAttribute("manifestazione", manifestazione.getDescrizione());
	model.addAttribute("dettaglio", comunicazione);
	model.addAttribute("schedulerAttivo", this.jobRepositoryService.isJobAttivo(ComunicazioniMassiveJob.class.getName()));
	return "mercati/viewComunicazione";
    }

    @RequestMapping
    public String viewComunicazioneM(@RequestParam("idcomunicazione") Integer idComunicazione, Model model, HttpServletRequest request) {

	//Si può utilizzare questa
	ComunicazioneMassivaGenModel comunicazione = this.comunicazioniMassiveService.getComunicazioneByIdGenTestata(idComunicazione);
	if (!StringUtils.isBlank(comunicazione.getServizioAppio())) {
	    AppIoServizi servizio = comunicazioniUtilsGenService.findAppIoServizioById(comunicazione.getServizioAppio());
	    if (servizio != null) {
		comunicazione.setServizioAppio(servizio.getDescrizione());
	    }
	}
	//Così manteniamo l'ordine
	if (comunicazione.getDestinatari() != null && !comunicazione.getDestinatari().isEmpty()) {
	    Collections.sort(comunicazione.getDestinatari(), new Comparator<ComunicazioneMassivaRigaModel>() {

		@Override
		public int compare(ComunicazioneMassivaRigaModel o1, ComunicazioneMassivaRigaModel o2) {

		    String n1 = o1.getNominativo() != null ? o1.getNominativo() : "";
		    String n2 = o2.getNominativo() != null ? o2.getNominativo() : "";
		    return n1.compareTo(n2);
		}
	    });
	}
	comunicazione.setContesto("mercati");
	model.addAttribute("manifestazione", comunicazione.getDescrizione());
	model.addAttribute("dettaglio", comunicazione);
	model.addAttribute("schedulerAttivo", true);
	return "mercati/viewComunicazioneM";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero l'oggetto corrente
	Oggetti oggetto = oggettiService.findById(mercati.getOggetto().getId());
	mercati.setOggetto(oggetto);
	// recupero la manifestazione passata
	Manifestazioni manifestazione = manifestazioniService.findById(mercati.getManifestazione().getCodice());
	mercati.setManifestazione(manifestazione);
	fixMergeEntityProperty(mercati);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    // recupero le manifestazioni
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    setPageAttributes(model, mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status) {

	Mercati objToDelete = mercatiService.findById(mercati.getId());
	try {
	    mercatiService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    setPageAttributes(model, mercati);
	    fixRenderEntityProperty(mercati);
	    return "mercati/form";
	}
	status.setComplete();
	return "redirect:list.htm";
    }

    @RequestMapping
    public String createAltridati(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	setPageAttributes(model);
	return "mercati/formAltridati";
    }

    @RequestMapping
    public String insertAltridati(Model model, @ModelAttribute("mercati") Mercati mercati, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	fixMergeEntityProperty(mercati);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercati, e);
	    fixRenderEntityProperty(mercati);
	    return "mercati/formAltridati";
	}
	status.setComplete();
	return "redirect:createAltridati.htm?codice=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public ModelMap listgiorni(@RequestParam("codicemercato") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codice));
	List<MercatiUso> mercatiusoList = mercatiUsoService.findByMercato(mercato);
	ModelMap model = new ModelMap(mercatiusoList);
	boolean export = createJMesaExport(request, response, mercatiusoList);
	if (export)
	    return null;
	model.addAttribute("mercatiusoList", mercatiusoList);
	model.addAttribute("mercati", mercato);
	return model;
    }

    @RequestMapping
    public String createGiorni(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	MercatiUso mercatiUso = new MercatiUso();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	// recupero la lista dei giorni della settimana
	List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	mercatiUso.setMercati(mercati);
	fixRenderEntityProperty(mercati);
	fixRenderMercatiusoProperty(mercatiUso);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiuso", mercatiUso);
	model.addAttribute("listagiorni", listagiorni);
	setPageAttributes(model);
	return "mercati/formGiorni";
    }

    @RequestMapping
    public String insertGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiUso, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il giorno della settimana
	if (mercatiUso.getGiornisettimana().getId() != null) {
	    Giornisettimana giornisettimana = giornisectimanaService.findById(mercatiUso.getGiornisettimana().getId());
	    mercatiUso.setGiornisettimana(giornisettimana);
	}
	// recupero la conecssione uso
	if (mercatiUso.getConcessioniuso().getId() != null && mercatiUso.getConcessioniuso().getId().getCodice() != null) {
	    Concessioniuso concessioniuso = concessioniusoService.findById(new PkId(mercatiUso.getConcessioniuso().getId().getCodice()));
	    mercatiUso.setConcessioniuso(concessioniuso);
	}
	fixMergeMercatiusoProperty(mercatiUso);
	try {
	    mercatiUsoService.insert(mercatiUso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiUso, e);
	    fixRenderMercatiusoProperty(mercatiUso);
	    // recupero la lista dei giorni della settimana
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiUso.getMercati());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewGiorni.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codicemercatouso=" +
	       mercatiUso.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewGiorni(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codicemercatouso") Integer codicemercatouso,
	    Model model) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codicemercatouso));
	// recupero la lista dei giorni della settimana
	List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	fixRenderMercatiusoProperty(mercatiUso);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatiuso", mercatiUso);
	model.addAttribute("listagiorni", listagiorni);
	setPageAttributes(model);
	return "mercati/formGiorni";
    }

    @RequestMapping
    public String updateGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiUso, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	// recupero il giorno della settimana
	if (mercatiUso.getGiornisettimana().getId() != null) {
	    Giornisettimana giornisettimana = giornisectimanaService.findById(mercatiUso.getGiornisettimana().getId());
	    mercatiUso.setGiornisettimana(giornisettimana);
	}
	// recupero la conecssione uso
	if (mercatiUso.getConcessioniuso().getId() != null && mercatiUso.getConcessioniuso().getId().getCodice() != null) {
	    Concessioniuso concessioniuso = concessioniusoService.findById(new PkId(mercatiUso.getConcessioniuso().getId().getCodice()));
	    mercatiUso.setConcessioniuso(concessioniuso);
	}
	fixMergeMercatiusoProperty(mercatiUso);
	try {
	    mercatiUsoService.update(mercatiUso);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiUso, e);
	    fixRenderMercatiusoProperty(mercatiUso);
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiUso.getMercati());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewGiorni.htm?codicemercato=" + mercatiUso.getMercati().getId().getCodice() + "&codicemercatouso=" +
	       mercatiUso.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteGiorni(Model model, @ModelAttribute("mercatiuso") MercatiUso mercatiuso, BindingResult result, SessionStatus status) {

	MercatiUso objToDelete = mercatiUsoService.findById(mercatiuso.getId());
	try {
	    mercatiUsoService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderMercatiusoProperty(mercatiuso);
	    List<Giornisettimana> listagiorni = giornisectimanaService.findAll(null, null);
	    MercatiUso mercatiusoTemp = mercatiUsoService.findById(mercatiuso.getId());
	    model.addAttribute("listagiorni", listagiorni);
	    model.addAttribute("mercati", mercatiusoTemp.getMercati());
	    return "mercati/formGiorni";
	}
	return "redirect:listgiorni.htm?codicemercato=" + mercatiuso.getMercati().getId().getCodice();
    }

    @RequestMapping
    public String createConsorzio(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	MercatiConsorzi mercatic = new MercatiConsorzi();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	// recupero la lista dei giorni della settimana
	mercatic.setMercato(mercati);
	fixRenderEntityProperty(mercati);
	fixRenderMercatiConsorzi(mercatic);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercaticonsorzi", mercatic);
	setPageAttributes(model);
	return "mercati/formConsorzi";
    }

    @RequestMapping
    public String insertConsorzio(Model model, @ModelAttribute("mercaticonsorzi") MercatiConsorzi mercatiConsorzi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il giorno della settimana
	try {
	    mercatiConsorziService.insert(mercatiConsorzi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConsorzi, e);
	    fixRenderMercatiConsorzi(mercatiConsorzi);
	    model.addAttribute("mercati", mercatiConsorzi.getMercato());
	    return "mercati/formConsorzi";
	}
	status.setComplete();
	return "redirect:viewConsorzio.htm?codicemercato=" + mercatiConsorzi.getMercato().getId().getCodice() + "&codice=" +
	       mercatiConsorzi.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewConsorzio(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codice") Integer codice, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	MercatiConsorzi mercatic = mercatiConsorziService.findById(new PkId(codice));
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercaticonsorzi", mercatic);
	setPageAttributes(model);
	fixRenderMercatiConsorzi(mercatic);
	return "mercati/formConsorzi";
    }

    private void fixRenderMercatiConsorzi(MercatiConsorzi mercatic) {

	if (mercatic.getConsorzio() == null) {
	    mercatic.setConsorzio(new Anagrafe());
	}
    }

    @RequestMapping
    public String updateConsorzio(Model model, @ModelAttribute("mercaticonsorzi") MercatiConsorzi mercatiConsorzi, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il giorno della settimana
	try {
	    mercatiConsorziService.update(mercatiConsorzi);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiConsorzi, e);
	    fixRenderMercatiConsorzi(mercatiConsorzi);
	    model.addAttribute("mercati", mercatiConsorzi.getMercato());
	    return "mercati/formGiorni";
	}
	status.setComplete();
	return "redirect:viewConsorzio.htm?codicemercato=" + mercatiConsorzi.getMercato().getId().getCodice() + "&codice=" +
	       mercatiConsorzi.getId().getCodice() + "&status_msg=02";
    }

    // 
    @RequestMapping
    public String deleteMercatiConsorzioList(@RequestParam("codicemercato") Integer codicemercato, @RequestParam("codice") Integer codice,
	    Model model, HttpServletRequest request) {

	MercatiConsorzi objToDelete = mercatiConsorziService.findById(new PkId(codice));
	try {
	    mercatiConsorziService.delete(objToDelete);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Non è stato possibile cancellare il consorzio a causa di: " + e.getMessage());
	    return "redirect:view.htm?codice=" + codicemercato + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codicemercato + "&status_msg=05";
    }

    @RequestMapping
    public ModelMap listmercatistradario(@RequestParam("codicemercato") Integer codice, HttpServletRequest request, HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codice));
	List<Mercatistradario> mercatistradarioList = mercatistradarioService.findByMercato(mercato);
	ModelMap model = new ModelMap(mercatistradarioList);
	boolean export = createJMesaExport(request, response, mercatistradarioList);
	if (export)
	    return null;
	model.addAttribute("mercatistradarioList", mercatistradarioList);
	model.addAttribute("mercati", mercato);
	return model;
    }

    @RequestMapping
    public String listresponsabili(@RequestParam("codicemercato") Integer codicemercato, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codicemercato));
	model.addAttribute("mercato", mercato);
	return "mercati/listresponsabili";
    }

    @RequestMapping
    public String createMercatistradario(@RequestParam("codicemercato") Integer codice, Model model, HttpServletRequest request) {

	Mercatistradario mercatistradario = new Mercatistradario();
	// setto il mercato corrente
	PkId id = new PkId(codice);
	Mercati mercati = mercatiService.findById(id);
	fixRenderEntityProperty(mercati);
	fixRenderMercatistradarioProperty(mercatistradario);
	mercatistradario.setMercato(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatistradario", mercatistradario);
	setPageAttributes(model);
	return "mercati/formMercatistradario";
    }

    @RequestMapping
    public String insertMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero lo stradario
	if (mercatistradario.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatistradario.getStradario().getId());
	    mercatistradario.setStradario(stradario);
	}
	fixMergeMercatistradarioProperty(mercatistradario);
	try {
	    mercatistradarioService.insert(mercatistradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatistradario, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", mercatistradario.getMercato());
	    return "mercati/formMercatistradario";
	}
	status.setComplete();
	return "redirect:viewMercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice() + "&codicemercatostradario=" +
	       mercatistradario.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewMercatistradario(@RequestParam("codicemercato") Integer codicemercato,
	    @RequestParam("codicemercatostradario") Integer codicemercatostradario, Model model) {

	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	Mercatistradario mercatistradario = mercatistradarioService.findById(new PkId(codicemercatostradario));
	fixRenderMercatistradarioProperty(mercatistradario);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatistradario", mercatistradario);
	setPageAttributes(model);
	return "mercati/formMercatistradario";
    }

    @RequestMapping
    public String updateMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero lo stradario
	if (mercatistradario.getStradario().getId() != null) {
	    Stradario stradario = stradarioService.findById(mercatistradario.getStradario().getId());
	    mercatistradario.setStradario(stradario);
	}
	fixMergeMercatistradarioProperty(mercatistradario);
	try {
	    mercatistradarioService.update(mercatistradario);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatistradario, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", mercatistradario.getMercato());
	    return "mercati/formMercatistradario";
	}
	status.setComplete();
	return "redirect:viewMercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice() + "&codicemercatostradario=" +
	       mercatistradario.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteMercatistradario(Model model, @ModelAttribute("mercatistradario") Mercatistradario mercatistradario, BindingResult result,
	    SessionStatus status) {

	Mercatistradario objToDelete = mercatistradarioService.findById(mercatistradario.getId());
	try {
	    mercatistradarioService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderMercatistradarioProperty(mercatistradario);
	    model.addAttribute("mercati", objToDelete.getMercato());
	    return "mercati/formMercatistradario";
	}
	return "redirect:listmercatistradario.htm?codicemercato=" + mercatistradario.getMercato().getId().getCodice();
    }

    // Quando faccio la cancellazione direttamente dalla lista.Sulla cancellazione di uno stradario non ci sono
    // controlli
    @RequestMapping
    public String deleteMercatistradariofromList(@RequestParam("codice") Integer codice) {

	Mercatistradario objToDelete = mercatistradarioService.findById(new PkId(codice));
	mercatistradarioService.delete(objToDelete);
	return "redirect:listmercatistradario.htm?codicemercato=" + objToDelete.getMercato().getId().getCodice();
    }

    @RequestMapping
    public String updateCampoDinamicoMercato(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("codiceCampoDinamico") Integer codiceCampoDinamico, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampoDinamico));
	mercati.setDyn2Campi(dyn2Campi);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String updateCampoDinamicoPreferenzaPosteggioMercato(@RequestParam("codiceMercato") Integer codicemercato,
	    @RequestParam("codiceCampoDinamico") Integer codiceCampoDinamico, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	Dyn2Campi dyn2Campi = dyn2CampiService.findById(new PkId(codiceCampoDinamico));
	mercati.setDyn2CampiPrefPosteggio(dyn2Campi);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deletePreferenzaUsoDyn2Dati(@RequestParam("codice") Integer codicemercato, HttpServletRequest request) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	mercati.setDyn2Campi(null);
	try {
	    mercatiService.update(mercati);
	} catch (Exception e) {
	    //	    copyErrorsToBindingResult(result, mercati, e);
	    //	    fixRenderEntityProperty(mercati);
	    //	    setPageAttributes(model, mercati);
	    //	    return "mercati/form";
	}
	return "redirect:../mercatid2cassegnaz/list.htm?codiceMercato=" + mercati.getId().getCodice() + "&status_msg=02";
    }

    @Override
    protected void fixMergeEntityProperty(Mercati entity) {

	if (entity.getOggetto() != null && entity.getOggetto().getId() != null && entity.getOggetto().getId().getCodice() == null) {
	    entity.setOggetto(null);
	}
	if (entity.getManifestazione() != null && entity.getManifestazione().getCodice() == null) {
	    entity.setManifestazione(null);
	}
	if (entity.getDyn2Campi() != null && entity.getDyn2Campi().getId().getCodice() == null) {
	    entity.setDyn2Campi(null);
	}
	if (entity.getComune() != null && entity.getComune().getCodicecomune() == null) {
	    entity.setComune(null);
	}
    }

    @Override
    protected void fixRenderEntityProperty(Mercati entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getSoftware() == null) {
	    entity.setSoftware(new Software());
	}
	if (entity.getManifestazione() == null) {
	    entity.setManifestazione(new Manifestazioni());
	}
	if (entity.getDyn2Campi() == null) {
	    entity.setDyn2Campi(new Dyn2Campi());
	}
	if (entity.getDyn2Modellit() == null) {
	    entity.setDyn2Modellit(new Dyn2Modellit());
	}
	if (entity.getMercatiCategorie() == null) {
	    entity.setMercatiCategorie(new MercatiCategorie());
	}
	if (entity.getComune() == null) {
	    entity.setComune(new Comuni());
	}
    }

    protected void fixRenderMercatiusoProperty(MercatiUso entity) {

	if (entity.getConcessioniuso() == null) {
	    entity.setConcessioniuso(new Concessioniuso());
	}
	if (entity.getGiornisettimana() == null) {
	    entity.setGiornisettimana(new Giornisettimana());
	}
	if (entity.getMercati() == null) {
	    entity.setMercati(new Mercati());
	}
    }

    protected void fixMergeMercatiusoProperty(MercatiUso entity) {

	if (entity.getConcessioniuso() != null && entity.getConcessioniuso().getId() != null
		&& entity.getConcessioniuso().getId().getCodice() == null) {
	    entity.setConcessioniuso(null);
	}
	if (entity.getGiornisettimana() != null && entity.getGiornisettimana().getId() == null) {
	    entity.setGiornisettimana(null);
	}
	if (entity.getMercati() != null && entity.getMercati().getId() != null && entity.getMercati().getId().getCodice() == null) {
	    entity.setMercati(null);
	}
    }

    protected void fixRenderMercatistradarioProperty(Mercatistradario entity) {

	if (entity.getStradario() == null) {
	    entity.setStradario(new Stradario());
	}
	if (entity.getMercato() == null) {
	    entity.setMercato(new Mercati());
	}
    }

    protected void fixMergeMercatistradarioProperty(Mercatistradario entity) {

	if (entity.getStradario() != null && entity.getStradario().getId() != null && entity.getStradario().getId().getCodice() == null) {
	    entity.setStradario(null);
	}
	if (entity.getMercato() != null && entity.getMercato().getId() != null && entity.getMercato().getId().getCodice() == null) {
	    entity.setMercato(null);
	}
    }

    protected void setPageAttributes(Model model, Mercati entity) {

	List<Manifestazioni> listmanifestazioni = manifestazioniService.findAll(null, null);
	model.addAttribute("listmanifestazioni", listmanifestazioni);
	List<MercatiConsorzi> mercatiConsorzis = mercatiConsorziService.findByCodiceMercato(entity.getId().getCodice());
	model.addAttribute("consorzis", mercatiConsorzis);
	Boolean isMercatoMoreDay = !entity.getMercatiUsos().isEmpty() && entity.getMercatiUsos().size() > 1 ? true : false;
	model.addAttribute("isMercatoMoreDay", isMercatoMoreDay);
	boolean nodoPagamenti = false;
	try {
	    nodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, entity.getComune().getCodicecomune()).isAttiva();
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Risulta attivato il nodo dei pagamenti ma non configurato correttamente: " + e.getMessage());
	}
	model.addAttribute("NODO_PAGAMENTI", Boolean.valueOf(nodoPagamenti));
	IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, entity.getComune().getCodicecomune());
	boolean isRegolaBorsellinoAttivo = vert.isAttiva();
	model.addAttribute("BORSELLINO_ATTIVO", isRegolaBorsellinoAttivo);
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @RequestMapping
    public String ajaxDettaglioresponsabili(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam(required = false, value = "codiceRespInserito") Integer codiceRespInserito, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	List<MercatiResponsabili> gds = mercatiResponsabiliService.findByMercato(codiceMercato, null, null);
	model.addAttribute("gds", gds);
	model.addAttribute("codiceRespInserito", codiceRespInserito);
	return "mercati/ajaxDettaglioResponsabili";
    }

    @RequestMapping
    public void ajaxEliminaResponsabile(@RequestParam("idRiga") Integer idRiga, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    MercatiResponsabili entity = mercatiResponsabiliService.findById(new PkId(idRiga));
	    mercatiResponsabiliService.delete(entity);
	} catch (Exception e) {
	    result = "Si e' verificato un errore nella cancellazione del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public void ajaxAssegnaResponsabile(@RequestParam("codiceMercato") Integer codiceMercato,
	    @RequestParam("codiceResponsabile") Integer codiceResponsabile, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	String result = "OK";
	try {
	    Responsabili r = responsabiliService.findById(new PkId(codiceResponsabile));
	    Mercati m = mercatiService.findById(new PkId(codiceMercato));
	    List<MercatiResponsabili> list = mercatiResponsabiliService.findByMercato(codiceMercato, null, null);
	    boolean trovato = false;
	    for (MercatiResponsabili mr : list) {
		if (mr.getResponsabili() != null && mr.getResponsabili().getId() != null && mr.getResponsabili().getId().getCodice() != null) {
		    if (codiceResponsabile.equals(mr.getResponsabili().getId().getCodice())) {
			trovato = true;
			break;
		    }
		}
	    }
	    if (trovato) {
		result = "Attenzione!! il responsabile " + r.getResponsabile() + " e' gia' assegnato al mercato";
	    } else {
		MercatiResponsabili entity = new MercatiResponsabili();
		entity.setMercato(m);
		entity.setResponsabili(r);
		mercatiResponsabiliService.insert(entity);
	    }
	} catch (Exception e) {
	    result = "Si e' verificato un errore durante l'inserimento del dato. (dettaglio:" + e.getMessage() + ")";
	}
	response.getOutputStream().write(result.getBytes());
    }

    @RequestMapping
    public ModelMap listmercatispunte(@RequestParam("codicemercato") Integer codicemercato, HttpServletRequest request,
	    HttpServletResponse response) {

	Mercati mercato = mercatiService.findById(new PkId(codicemercato));
	List<MercatiSpunte> mercatispunteList = mercatiSpunteService.findByMercato(codicemercato);
	ModelMap model = new ModelMap(mercatispunteList);
	boolean export = createJMesaExport(request, response, mercatispunteList);
	if (export)
	    return null;
	model.addAttribute("mercatispunteList", mercatispunteList);
	model.addAttribute("mercati", mercato);
	return model;
    }

    @RequestMapping
    public String createMercatiSpunte(@RequestParam("codicemercato") Integer codicemercato, Model model, HttpServletRequest request) {

	MercatiSpunte mercatiSpunte = new MercatiSpunte();
	// setto il mercato corrente
	PkId id = new PkId(codicemercato);
	Mercati mercati = mercatiService.findById(id);
	mercatiSpunte.setMercato(mercati);
	fixRenderEntityProperty(mercati);
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatispunte", mercatiSpunte);
	setPageAttributes(model);
	return "mercati/formMercatiSpunte";
    }

    @RequestMapping
    public String insertMercatiSpunte(Model model, @ModelAttribute("mercatispunte") MercatiSpunte mercatiSpunte, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// recupero il giorno della settimana
	try {
	    mercatiSpunteService.insert(mercatiSpunte);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiSpunte, e);
	    // recupero la lista dei mercatispunte della settimana
	    model.addAttribute("mercati", mercatiSpunte.getMercato());
	    return "mercati/formMercatiSpunte";
	}
	status.setComplete();
	return "redirect:viewMercatiSpunte.htm?codice=" + mercatiSpunte.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String viewMercatiSpunte(@RequestParam("codice") Integer codice, Model model) {

	MercatiSpunte mercatiSpunte = mercatiSpunteService.findById(new PkId(codice));
	Mercati mercati = mercatiSpunte.getMercato();
	// recupero la lista dei mercatispunte della settimana	
	model.addAttribute("mercati", mercati);
	model.addAttribute("mercatispunte", mercatiSpunte);
	setPageAttributes(model);
	return "mercati/formMercatiSpunte";
    }

    @RequestMapping
    public String updateMercatiSpunte(Model model, @ModelAttribute("mercatispunte") MercatiSpunte mercatiSpunte, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	try {
	    mercatiSpunteService.update(mercatiSpunte);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, mercatiSpunte, e);
	    return "mercati/formMercatiSpunte";
	}
	status.setComplete();
	return "redirect:viewMercatiSpunte.htm?codice=" + mercatiSpunte.getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String deleteMercatiSpunte(Model model, @ModelAttribute("mercatispunte") MercatiSpunte mercatispunte, BindingResult result,
	    SessionStatus status) {

	MercatiSpunte objToDelete = mercatiSpunteService.findById(mercatispunte.getId());
	Mercati m = objToDelete.getMercato();
	try {
	    mercatiSpunteService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    model.addAttribute("mercati", m);
	    return "mercati/formMercatiSpunte";
	}
	return "redirect:listmercatispunte.htm?codicemercato=" + m.getId().getCodice();
    }

    @RequestMapping
    public String updateCopiaMercato(@RequestParam("idMercato") Integer codiceMercato,
	    @RequestParam("idCausaleCessazione") Integer idCausaleCessazione, @RequestParam("idCausaleAcquisizione") Integer idCausaleAcquisizione,
	    HttpServletRequest request, HttpServletResponse response) {

	boolean spostaConcessioni = true;
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.defaultIfEmpty(responsabili.getAmministratore(), "0").equals("0")) {
	    throw new SecurityException("Utente non abilitato alla funzionalità");
	}
	LoggerUpdaterecord.log("#COPIA_MERCATI# IDMERCARTO: " + codiceMercato, responsabili);
	mercatiService.updateCopiaInfoMercato(codiceMercato, spostaConcessioni, idCausaleCessazione, idCausaleAcquisizione);
	FlashMessages.getInfos().add("Copia avvenuta correttamente");
	return "redirect:listCopia.htm";
    }

    @RequestMapping
    public ModelMap listCopia(HttpServletRequest request, HttpServletResponse response) {

	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	int c = mercatiResponsabiliService.countByResponsabile(r.getId().getCodice());
	List<Mercati> mercatiList = null;
	if (c > 0) {
	    mercatiList = mercatiService.findByDescrizioneAndResponsabile("", r.getId().getCodice(), MercatiEnum.ALL, null, null);
	} else {
	    mercatiList = mercatiService.findAll(null, null);
	}
	ModelMap model = new ModelMap(mercatiList);
	boolean export = createJMesaExport(request, response, mercatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiList", mercatiList);
	List<Concessionicausali> conc = concessionicausaliService.findAll(null, null);
	List<Concessionicausali> cessazione = new ArrayList<Concessionicausali>();
	List<Concessionicausali> acquisizione = new ArrayList<Concessionicausali>();
	for (Concessionicausali concessionicausali : conc) {
	    if (concessionicausali.isCausalestorico()) {
		cessazione.add(concessionicausali);
	    } else {
		acquisizione.add(concessionicausali);
	    }
	}
	model.addAttribute("acquisizionis", acquisizione);
	model.addAttribute("cessazionis", cessazione);
	return model;
    }

    @RequestMapping
    public String createComunicazioneMa(Model model, @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand cmd,
	    HttpServletRequest request, HttpServletResponse response) {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	sdf.setLenient(false);
	Date dallaDataM = null;
	if (!StringUtils.isBlank(cmd.getDallaDataM())) {
	    try {
		dallaDataM = sdf.parse(cmd.getDallaDataM());
	    } catch (ParseException e) {
		// TODO Auto-generated catch block
		throw new RuntimeException(e);
	    }
	}
	Date allaDataM = null;
	if (!StringUtils.isBlank(cmd.getAllaDataM())) {
	    try {
		allaDataM = sdf.parse(cmd.getAllaDataM());
	    } catch (ParseException e) {
		throw new RuntimeException(e);
	    }
	}
	boolean isConcessionari = false;
	boolean isSpuntisti = false;
	if ("tutti".equals(cmd.getTipoDestinatario())) {
	    isConcessionari = true;
	    isSpuntisti = true;
	} else if ("concessionari".equals(cmd.getTipoDestinatario())) {
	    isConcessionari = true;
	} else {
	    isSpuntisti = true;
	}
	int[] mercatiIds;
	if ("tutte".equals(cmd.getManifestazioneRadio())) {
	    mercatiIds = null;
	} else {
	    String[] idsmercatostv = request.getParameterValues("mercatiscelti");
	    if (idsmercatostv == null || idsmercatostv.length < 1) {
		if (cmd.getIdsmercato() != null && cmd.getIdsmercato().length > 0) {
		    mercatiIds = cmd.getIdsmercato();
		} else {
		    throw new RuntimeException("Non sono stati selezionati mercati");
		}
	    } else {
		mercatiIds = new int[idsmercatostv.length];
		for (int i = 0; i < idsmercatostv.length; i++) {
		    mercatiIds[i] = Integer.parseInt(idsmercatostv[i].trim());
		}
	    }
	}
	cmd.setIdsmercato(mercatiIds);
	ConfigurazioniComunicazioneGen configurazioniComunicazioneGen = new ConfigurazioniComunicazioneGen(ContestoComunicazioneEnum.MERCATI);
	configurazioniComunicazioneGen.setDataInizio(dallaDataM);
	configurazioniComunicazioneGen.setDataFine(allaDataM);
	configurazioniComunicazioneGen.setIdsmercati(mercatiIds);
	configurazioniComunicazioneGen.setConcessionario(isConcessionari);
	configurazioniComunicazioneGen.setSpuntisti(isSpuntisti);
	Set<ISoftwareComuneData> softwareAndComune = new HashSet<ISoftwareComuneData>();
	cmd.setCodiciComuneUguale(false);
	List<IParametriProtocolloPerEnteHelper> helps = cmd.getProtocollaParametriCommand().getParametriPerEnte();
	if (helps.isEmpty()) {
	    List<ISoftwareComuneData> softCom = comunicazioniToGenService.getSoftwareAndComune(configurazioniComunicazioneGen);
	    helps = comunicazioniToGenService.popolaParametriProtocollazione(configurazioniComunicazioneGen, softCom);
	    softwareAndComune = new HashSet<ISoftwareComuneData>(softCom);
	    if (softwareAndComune != null && !softwareAndComune.isEmpty() && softwareAndComune.size() == 1) {
		ISoftwareComuneData next = softwareAndComune.iterator().next();
		cmd.setCodicecomuneComunicazione(next.getCodiceComune());
		cmd.setCodiciComuneUguale(true);
	    }
	}
	cmd.getProtocollaParametriCommand().setParametriPerEnte(helps);
	Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
	Set<Responsabilicomuni> responsabilicomuni = responsabiliService.findListResponsabilicomuni(responsabili);
	int i = 0;
	String[] codiceComuni = new String[responsabilicomuni.size()];
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiceComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		codiceComuni);

	List<Map<String, String>> appioservizilist;
	if(cmd.isCodiciComuneUguale()){
	    appioservizilist = findAppioServiziList(cmd.getCodicecomuneComunicazione());
	}else{
	    appioservizilist = findAppioServiziList(null);
	}
	model.addAttribute("listMailConfig", listMailConfig);
	model.addAttribute("sceltaTipoMailAnagrafeList", SceltaTipoMailAnagrafeEnum.asList());
	model.addAttribute("appioservizilist", appioservizilist);
	setParametriInModel(model, request, null, null);
	cmd.setTipoAnagrafe("titolare");
	cmd.setTipoInvioMercato("anagrafe");
	cmd.setScegliMailChckN(true);
	return "mercati/createM";
    }
    
    private List<Map<String, String>> findAppioServiziList(String codicecomunecomunicazione){
	List<AppIoServizi> appioservizi = comunicazioniUtilsGenService.findAllAppIoServizi();
	List<Map<String, String>> appioservizilist = new ArrayList<Map<String, String>>();
	if (appioservizi != null) {
	    
	    if(codicecomunecomunicazione == null){
		Map<String, String> m = new HashMap<String, String>();
		m.put("id", "");
		m.put("descrizione", "");
		m.put("noservizidesc", "Non ci sono servizi disponibili, il codicecomune risulta null");
		appioservizilist.add(m);
		return appioservizilist;
	    }
	    
	    Set<String> codiciComune = new HashSet<String>();
	    codiciComune.add(codicecomunecomunicazione);
	    String currentSoftware = ORMHelper.getSoftware();
	    for (AppIoServizi servizi : appioservizi) {
		String idServizio = servizi.getId().getIdentificativoServizio();
		Map<String, AppIoServiziConfigRestResponse> configs = appIoServiziConfigService.findByIdServizioAndComuni(idServizio, codiciComune);
		for (Map.Entry<String, AppIoServiziConfigRestResponse> entry : configs.entrySet()) {
		    AppIoServiziConfigRestResponse config = entry.getValue();
		    if (config != null && StringUtils.equals(currentSoftware, config.getSoftware())) {
			Map<String, String> m = new HashMap<String, String>();
			m.put("id", idServizio);
			m.put("descrizione", servizi.getDescrizione());
			appioservizilist.add(m);
		    }
		}
	    }
	    if(appioservizilist.isEmpty()){
		Map<String, String> m = new HashMap<String, String>();
		m.put("id", "");
		m.put("descrizione", "");
		m.put("noservizidesc", "Non ci sono servizi disponibili, per il comune " + codicecomunecomunicazione + " e software " + currentSoftware);
		appioservizilist.add(m);
		return appioservizilist;
	    }
	}
	return appioservizilist;
    }

    @RequestMapping
    public String preselezionaMercatiMa(Model model, HttpServletRequest request, HttpServletResponse response) {

	ComunicazioniGenCommand cmd = new ComunicazioniGenCommand();
	cmd.setManifestazioneRadio("tutte");
	model.addAttribute("comunicazioniCommissioniCommand", cmd);
	return "mercati/listFormComMerc";
    }

    protected void setParametriInModel(Model model, HttpServletRequest request, String codiceComune, String software) {

	/*
	 * Controllo se è attiva la verticalizzazione con il modulo PROTOCOLLO_ATTIVO
	 */
	if (verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    model.addAttribute("vert_prot_attivo", true);
	} else {
	    model.addAttribute("vert_prot_attivo", false);
	}
    }

    @RequestMapping
    public String ajaxElaboraRigaM(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	try {
	    System.out.println("Sto elaborando riga");
	    Integer idRiga = Integer.parseInt(request.getParameter("idRiga"));
	    comunicazioniMassiveMService.elaboraRiga(idRiga);
	    System.out.println("Ho elaborato riga");
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "redirect:../comunicazioniiicommissioni/view.htm?codice=" + 0;
    }

    @RequestMapping
    public ModelMap listFormComMerc(HttpServletRequest request, HttpServletResponse response) {

	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	int c = mercatiResponsabiliService.countByResponsabile(r.getId().getCodice());
	List<Mercati> mercatiList = null;
	if (c > 0) {
	    mercatiList = mercatiService.findByDescrizioneAndResponsabile("", r.getId().getCodice(), MercatiEnum.ALL, null, null);
	} else {
	    mercatiList = mercatiService.findAll(null, null);
	}
	ModelMap model = new ModelMap(mercatiList);
	boolean export = createJMesaExport(request, response, mercatiList);
	if (export) {
	    return null;
	}
	model.addAttribute("mercatiList", mercatiList);
	model.addAttribute("indietrolink", "listComunicazioniM.htm");
	return model;
    }

    @RequestMapping
    public void ajaxAggiungiAllegatiCompilabili(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	comunicazioniUtilsGenService.ajaxAggiungiAllegatiCompilabili(model, comunicazioniCommissioniCommand, codiceLetteretipo, request, response);
	return;
    }

    @RequestMapping
    public void ajaxRimuoviAllegatoCompilabile(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceLetteretipo") Integer codiceLetteretipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	comunicazioniUtilsGenService.ajaxRimuoviAllegatoCompilabile(model, comunicazioniCommissioniCommand, codiceLetteretipo, request, response);
    }

    @RequestMapping
    public void ajaxAggiungiFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	comunicazioniUtilsGenService.ajaxAggiungiFirmatario(model, comunicazioniCommissioniCommand, codiceFirmatario, request, response);
	return;
    }

    @RequestMapping
    public void ajaxRimuoviFirmatario(Model model,
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand,
	    @RequestParam("codiceFirmatario") Integer codiceFirmatario, HttpServletRequest request, HttpServletResponse response) throws IOException {

	comunicazioniUtilsGenService.ajaxRimuoviFirmatario(model, comunicazioniCommissioniCommand, codiceFirmatario, request, response);
    }

    @RequestMapping
    public void ajaxSetParametriProtocollazione(
	    @ModelAttribute("comunicazioniCommissioniCommand") ComunicazioniGenCommand comunicazioniCommissioniCommand, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	comunicazioniUtilsGenService.ajaxSetParametriProtocollazione(comunicazioniCommissioniCommand, request, response);
    }

    @RequestMapping
    public void getDataMailFromMailTipoById(@RequestParam("mailTipoId") String mailTipoId, HttpServletRequest request, HttpServletResponse response) {

	Mailtipo mailTipo = mailTipoService.findById(new PkId(Integer.parseInt(mailTipoId)));
	Map<String, String> jsonMap = new HashMap<String, String>();
	jsonMap.put("oggetto", mailTipo.getOggetto());
	jsonMap.put("corpo", mailTipo.getCorpo());
	jsonMap.put("descrizione", mailTipo.getDescrizione());
	ObjectMapper objectMapper = new ObjectMapper();
	String json;
	try {
	    json = objectMapper.writeValueAsString(jsonMap);
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	    throw new RuntimeException("Error eseguendo la get sul parsing");
	}
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	try {
	    response.getWriter().write(json);
	} catch (IOException e) {
	    throw new RuntimeException("Error in response");
	}
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxGetRigaDettagliata(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException {

	comunicazioniUtilsGenService.ajaxGetRigaDettagliata(model, request, response, ContestoComunicazioneEnum.MERCATI);
    }

    @RequestMapping
    public void ajaxAggiornaMailOPec(@RequestParam(value = "codiceAnagrafe", required = false) Integer codiceAnagrafe,
	    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "pec", required = false) String pec,
	    @RequestParam(value = "sceltaMailAnagrafe", required = false) String sceltaMailAnagrafe,
	    @RequestParam(value = "idMassiveDettaglio", required = true) Integer idMassiveDett, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String risultato = "{\"aggiorna_mail\":{\"codice\":\"CODE_MAIL\",\"descrizione\":\"DESC_MAIL\"}}";
	String codeMail = "OK";
	String descMail = "";
	if (codiceAnagrafe != null) {
	    try {
		anagrafeService.aggiornaMailEPec(codiceAnagrafe, email, pec);
		//comunicazioniMassiveDettaglioDAO.aggiornaMailAnagrafe(codiceAnagrafe);
		SceltaTipoMailAnagrafeEnum tipoMail = SceltaTipoMailAnagrafeEnum.valueOf(sceltaMailAnagrafe);
		String mailDaAggiornare = "";
		switch (tipoMail) {
		case PEC_O_MAIL:
		    mailDaAggiornare = StringUtils.defaultIfEmpty(pec, email);
		    break;
		case SOLO_MAIL:
		    mailDaAggiornare = email;
		    break;
		case SOLO_PEC:
		    mailDaAggiornare = pec;
		    break;
		default:
		    break;
		}
		if (StringUtils.isNotBlank(mailDaAggiornare)) {
		    MassiveDettaglio m = comunicazioniMassiveDettaglioDAO.getById(idMassiveDett);
		    MassiveDettDestinatari destinatari = m.getDestinatari();
		    destinatari.setMailDestinatario(mailDaAggiornare);
		    comunicazioniUtilsGenService.insertMassiveDettDestinatari(destinatari);
		}
	    } catch (Exception e) {
		codeMail = "KO";
		descMail = "Si sono verificati degli errori nell'aggiornamento della mail";
	    }
	}
	risultato = risultato //
		.replace("CODE_MAIL", codeMail) //
		.replace("DESC_MAIL", descMail);
	response.setContentType("application/json");
	response.getOutputStream().write(risultato.getBytes());
    }
}