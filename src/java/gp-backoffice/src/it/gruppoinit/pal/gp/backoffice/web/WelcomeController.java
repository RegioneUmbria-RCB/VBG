package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Versione;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneutenteService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.LinkPreferitiUtenteService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.VersioneService;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;

@Controller
public class WelcomeController extends BaseController<Clmenu> {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private ConfigurazioneutenteService configurazioneutenteService;
    @Autowired
    private LinkPreferitiUtenteService linkPreferitiUtenteService;
    @Autowired
    private VersioneService versioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    IVerticalizzazioneParametriSistemaService parametriSistemaService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;

    @RequestMapping
    public String start(HttpServletRequest request, HttpServletResponse response) {

	HttpSession session = request.getSession();
	session.removeAttribute("linkPreferitis");
	Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
	// Recupero i preferiti per l'utente loggato
	List<LinkPreferitiUtente> linkPreferitis = linkPreferitiUtenteService.findLinkPreferitiUtente(responsabile.getId().getCodice(), null, null);
	// Recupero il link preferiti se ci sono e li mentto in sessione
	session.setAttribute("linkPreferitis", linkPreferitis);
	setComuneEVersione(request);
	// controllo se l'operatore ha il flag scadenzario all'avvio settato
	boolean showScadenzario = false;
	showScadenzario = BooleanUtils.isTrue(responsabile.getScadenzario());
	if (showScadenzario) {
	    return "redirect:../batchscadenzario/listPerOperatore.htm?" + WebConstants.SOFTWARE + "=" + WebConstants.SOFTWARE_TT;
	} else {
	    // FIXME se non ho il menù della vert abilitato forse devo andare alla home page
	    // recupero i software abilitati, se ha un software abilitato oltre a TT allora recupero dalla verticalizzazione CENTER_PAGE
	    // il valore del parametro CENTERPAGE per il software abilitato
	    String confUtentePaginaCentrale = getCenterPageForOperatore(responsabile);
	    if (StringUtils.isNotBlank(confUtentePaginaCentrale)) {
		return "redirect:../" + confUtentePaginaCentrale;
	    } else {
		List<Software> softwareAbilitatiList = softwareService.findSoftwareAbilitati(responsabile);
		if (softwareAbilitatiList.size() == 2) {
		    String software = softwareAbilitatiList.get(0).getCodice().equals(WebConstants.SOFTWARE_TT)
			    ? softwareAbilitatiList.get(1).getCodice()
			    : softwareAbilitatiList.get(0).getCodice();
		    Verticalizzazioniparametri verticalizzazioneParametroSoftware = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAZIONE_CENTER_PAGE, WebConstants.VERTICALIZZAZIONE_CENTER_PAGE_CENTERPAGE, software);
		    String vertValSoftware = null;
		    if (verticalizzazioneParametroSoftware != null) {
			vertValSoftware = verticalizzazioneParametroSoftware.getValore();
		    }
		    if (StringUtils.isNotBlank(vertValSoftware) && vertValSoftware.indexOf("welcome/start") == -1) {
			return "redirect:../" + vertValSoftware;
		    }
		} else {
		    // se ha più di un software abilitato oltre a TT allora recupero dalla verticalizzazione CENTER_PAGE
		    // il valore del parametro CENTERPAGE per il software TT
		    Verticalizzazioniparametri verticalizzazioneParametro = verticalizzazioniService.getVerticalizzazioniparametri(
			    WebConstants.VERTICALIZZAZIONE_CENTER_PAGE, WebConstants.VERTICALIZZAZIONE_CENTER_PAGE_CENTERPAGE,
			    WebConstants.SOFTWARE_TT);
		    if (verticalizzazioneParametro != null) {
			String vertVal = verticalizzazioneParametro.getValore();
			if (StringUtils.isNotBlank(vertVal) && vertVal.indexOf("welcome/start") == -1) {
			    return "redirect:../" + vertVal;
			}
		    }
		}
	    }
	}
	boolean nascondiScriptLocation = this.parametriSistemaService.isAttiva() && this.parametriSistemaService.nascondiScriptLocation();
	request.setAttribute("req_param_visualizzaScriptLocation", !nascondiScriptLocation);
	// se nessuna condizione è soddisfatta vado alla home page.
	return "welcome/start";
    }

    private String getCenterPageForOperatore(Responsabili utente) {

	String result = null;
	ConfigurazioneutenteId id = new ConfigurazioneutenteId(utente.getId().getCodice(), WebConstants.CONF_UTENTE_PAGINA_CENTRALE);
	Configurazioneutente c = configurazioneutenteService.findById(id);
	if (c != null) {
	    String valore = StringUtils.defaultString(c.getValore()).trim();
	    if (StringUtils.isNotBlank(valore)) {
		result = valore;
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("getCenterPageForOperatore# center page: " + result);
	}
	return result;
    }

    @RequestMapping
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

	String externalAuthUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URL);
	if (StringUtils.isNotBlank(externalAuthUrl)) {
	    try {
		ConfigurazioneId id = new ConfigurazioneId(ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT);
		Configurazione conf = configurazioneService.findById(id);
		if (EntityUtils.isNestedPropertyBlank(conf, "urllogout")) {
		    String urlFirstRequest = (String) request.getSession().getAttribute(WebConstants.URL_FIRST_REQUEST);
		    externalAuthUrl += "?return_to=" + urlFirstRequest + "&" + WebConstants.IDCOMUNE_ALIAS + "=" +
				       request.getSession().getAttribute(WebConstants.IDCOMUNE_ALIAS) + "&contesto=OPE";
		} else {
		    externalAuthUrl = conf.getUrllogout();
		}
		response.sendRedirect(externalAuthUrl);
	    } catch (IOException e) {
		log.error("login: {}", e.getMessage());
		throw new RuntimeException("Invalid redirect to AuthenticationGateway: " + externalAuthUrl);
	    }
	    return null;
	} else {
	    List<Software> softwareAttiviList = softwareService.findSoftwareAttivi(false);
	    model.addAttribute("softwareAttiviList", softwareAttiviList);
	}
	return "welcome/login";
    }

    @RequestMapping
    public String logoutBoxImage(Model model, HttpServletRequest request, HttpServletResponse response) {

	return "redirect:../j_spring_security_logout?logoutSuccessUrl=/images/logout_box.gif";
    }

    @RequestMapping
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {

	String urlLogout = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URL);
	String overrideLogoutUrl = request.getParameter("overrideLogoutUrl");
	Responsabili r = getCurrentlyAuthenticatedUserDetails();
	LoggerUpdaterecord
		.log("#LOGOUT_OPERATORE# LOGOUT UTENTE " + r.getResponsabile() + "[" + r.getId() + "] - Data:" + Calendar.getInstance().getTime());
	if (StringUtils.isNotBlank(urlLogout)) {
	    try {
		ConfigurazioneId id = new ConfigurazioneId(ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT);
		Configurazione conf = configurazioneService.findById(id);
		urlLogout = urlLogout.substring(0, urlLogout.lastIndexOf("/"));
		String urlBack = "";
		if (EntityUtils.isNestedPropertyBlank(conf, "urllogout")) {
		    urlBack = fixUrlBack((String) request.getSession().getAttribute(WebConstants.URL_FIRST_REQUEST), request);
		} else {
		    if (StringUtils.isNotBlank(overrideLogoutUrl)) {
			response.sendRedirect(conf.getUrllogout());
			return null;
		    }
		    urlBack = URLEncoder.encode(conf.getUrllogout(), "UTF-8");
		}
		urlLogout += "/logout?" + WebConstants.TOKEN + "=" + request.getSession().getAttribute(WebConstants.TOKEN) + "&return_to=" + urlBack;
		response.sendRedirect(urlLogout);
	    } catch (IOException e) {
		log.error("logout: {}", e.getMessage(), e);
		throw new RuntimeException("Invalid redirect to AuthenticationGateway: " + urlLogout);
	    }
	    return null;
	}
	return "redirect:../j_spring_security_logout";
    }

    /**
     * rimuove dall'url per accedere all'applicativo la query string e ne compone una nuova formata da idcomunealias e
     * _timestamp
     * 
     * @param urlBack
     * @param request
     * @return
     */
    private String fixUrlBack(String urlBack, HttpServletRequest request) {

	try {
	    urlBack = URLDecoder.decode(urlBack, "UTF-8");
	    urlBack = urlBack.substring(0, urlBack.indexOf("?") + 1);
	    urlBack += WebConstants.IDCOMUNE_ALIAS + "=" + request.getSession().getAttribute(WebConstants.IDCOMUNE_ALIAS) + "&_timestamp=" +
		       System.currentTimeMillis();
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	} catch (Exception e) {
	    log.error("fixUrlBack: {}", e.getMessage(), e);
	}
	return urlBack;
    }

    private void setComuneEVersione(HttpServletRequest request) {

	ConfigurazioneId id = new ConfigurazioneId(ORMHelper.getIdcomune(), WebConstants.SOFTWARE_TT);
	Configurazione conf = configurazioneService.findById(id);
	if (conf == null) {
	    log.error("setComuneEVersione(): configurazione nulla per il software TT");
	    throw new RuntimeException("Attenzione! Nessun record in CONFIGURAZIONE per il modulo " + WebConstants.SOFTWARE_TT);
	}
	List<Versione> versionis = versioneService.findAll(null, null);
	Versione versione = null;
	if (versionis.size() > 0) {
	    versione = versionis.get(0);
	}
	request.getSession().setAttribute(WebConstants.PRODUCT_BUILD, versione.getVersione());
	//request.getSession().setAttribute("app_version", request.getSession().getServletContext().getServletContextName());
	//se versione.versioneProdotto == null allora carico l'informazione alla vecchia maniera per mantenere la compatiblità
	// FIXME String appVersion = StringUtils.defaul(versione.getVersioneProdotto()) ? versione.getVersioneProdotto() : request.getSession().getServletContext().getServletContextName();
	request.getSession().setAttribute(WebConstants.PRODUCT_VERSION, request.getSession().getServletContext().getServletContextName());
	request.getSession().setAttribute(WebConstants.COMUNE_DESC, conf.getDenominazione());
    }

    @RequestMapping
    public String info(HttpServletRequest request, HttpServletResponse response) {

	List<Software> softwares = softwareService.findAll(null, null);
	request.setAttribute("softwareList", softwares);
	return "welcome/info";
    }

    @RequestMapping
    public String deleteDyn2Campi(HttpServletRequest request, HttpServletResponse response) {

	dyn2CampiService.deleteDyn2CampiNonUsati();
	return "redirect:../welcome/start.htm";
    }

    @Override
    protected void fixMergeEntityProperty(Clmenu entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Clmenu entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
