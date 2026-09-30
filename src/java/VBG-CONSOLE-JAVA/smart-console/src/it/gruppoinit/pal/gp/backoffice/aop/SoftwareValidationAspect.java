package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.LayoutpagineService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class SoftwareValidationAspect {

    private static final Logger log = LoggerFactory.getLogger(SoftwareValidationAspect.class);
    private CacheManager cacheManager;
    private LayoutpagineService layoutpagineService;
    private SoftwareService softwareService;
    private UserSecurityService userSecurityService;
    private ResponsabilicomuniService responsabilicomuniService;
    private ComuniService comuniService;

    @Before("execution(* ((@org.springframework.stereotype.Controller *..*Controller) && (! *..*UpgradeController) && (! *..*ImportController) && (! *..*WelcomeController) && (! *..*AvviaController)).*(..))")
    public void validate(JoinPoint joinPoint) throws Throwable {

	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	String codiceSoftware = request.getParameter(WebConstants.SOFTWARE);
	if (StringUtils.isBlank(codiceSoftware)) {
	    codiceSoftware = (String) request.getSession().getAttribute(WebConstants.SOFTWARE);
	}
	Software software = softwareService.findById(codiceSoftware);
	if (software == null) {
	    log.error("software inesistente: '{}'", codiceSoftware);
	    throw new RuntimeException("Il modulo [" + codiceSoftware + "] non esiste.");
	}
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	request.getSession().setAttribute(WebConstants.USER_lOGGED_SESSION_VARIABLE_NAME, responsabile);
	List<Software> softwareAbilitati = softwareService.findSoftwareAbilitati(responsabile);
	boolean success = false;
	for (Software softwareAbilitato : softwareAbilitati) {
	    if (softwareAbilitato.getCodice().equals(software.getCodice())) {
		success = true;
		break;
	    }
	}
	if (!success) {
	    log.error("software non attivo: '{}'", codiceSoftware);
	    throw new RuntimeException("L'operatore [" + responsabile.getResponsabile() + "] non ha il modulo [" + software.getCodice()
		    + "] abilitato.");
	}
	putObjectsinRequest(request, responsabile);
    }

    @After("execution(* ((@org.springframework.stereotype.Controller *..*Controller) && (! *..*UpgradeController)).*(..))")
    public void dopoEsecuzione() throws Throwable {

	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	List<String> infos = FlashMessages.getInfos();
	List<String> warnings = FlashMessages.getWarnings();
	List<String> infosInSession = (List<String>) request.getSession().getAttribute("___infos");
	List<String> warningsInSession = (List<String>) request.getSession().getAttribute("___warnings");
	if (infosInSession == null || !infos.isEmpty()) {
	    request.getSession().setAttribute("___infos", infos);
	    FlashMessages.setInfos(null);
	}
	if (warningsInSession == null || !warnings.isEmpty()) {
	    request.getSession().setAttribute("___warnings", warnings);
	    FlashMessages.setWarnings(null);
	}
    }

    @SuppressWarnings("unchecked")
    private void putObjectsinRequest(HttpServletRequest request, Responsabili responsabile) {

	// OGGETTI ABILITATI
	// metto sulla request la mappa degli oggetti abilitati per la pagina in esecuzione e il software
	String nomePagina = request.getRequestURI().toString();
	String nomeContesto = request.getContextPath();
	Enumeration<String> enume = request.getParameterNames();
	SigeproBusinessRules.setCancellazioneDatiMaster(Boolean.FALSE);
	while (enume.hasMoreElements()) {
	    String paramName = (String) enume.nextElement();
	    if (StringUtils.defaultString(paramName).endsWith("_set_cancellazione_master")) {
		log.warn("putObjectsinRequest# l'utente {} ha richiesto la cancellazione di un record master {}", responsabile.getResponsabile(),
			nomePagina);
		SigeproBusinessRules.setCancellazioneDatiMaster(Boolean.TRUE);
		break;
	    }
	}
	// OGGETTI ABILITATI
	// metto sulla request la mappa degli oggetti abilitati per la pagina in esecuzione e il software
	if (StringUtils.isNotBlank(nomeContesto)) {
	    if (nomePagina.indexOf(nomeContesto) >= 0) {
		nomePagina = nomePagina.replaceFirst(nomeContesto + "/", "");
	    }
	}
	String nomeDellaPaginaInCache = ORMHelper.getSoftware() + "_" + nomePagina;
	String nomeDellaMappaInCache = ORMHelper.getIdcomuneAlias() + "_" + nomePagina;
	Set<String> oggettiDisabilitati = null;
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_OGGETTIDISABILITATI_KEY);
	    Element obj = cache.get(nomeDellaMappaInCache);
	    Map<String, Set<String>> mappaOggettiDisabilitati = null;
	    if (obj != null) {
		mappaOggettiDisabilitati = (Map<String, Set<String>>) obj.getObjectValue();
		oggettiDisabilitati = mappaOggettiDisabilitati.get(nomeDellaPaginaInCache);
		if (oggettiDisabilitati == null) {
		    // devo mettere nella mappa gli oggetti della pagina
		    oggettiDisabilitati = layoutpagineService.findOggettiDisabilitatiPerPagina(nomePagina);
		    mappaOggettiDisabilitati.put(nomeDellaPaginaInCache, oggettiDisabilitati);
		    cache.remove(nomeDellaMappaInCache);
		    Element element = new Element(nomeDellaMappaInCache, mappaOggettiDisabilitati);
		    cache.put(element);
		    cache.flush();
		}
	    } else {
		oggettiDisabilitati = layoutpagineService.findOggettiDisabilitatiPerPagina(nomePagina);
		mappaOggettiDisabilitati = new HashMap<String, Set<String>>();
		mappaOggettiDisabilitati.put(nomeDellaPaginaInCache, oggettiDisabilitati);
		Element element = new Element(nomeDellaMappaInCache, mappaOggettiDisabilitati);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    oggettiDisabilitati = layoutpagineService.findOggettiDisabilitatiPerPagina(nomePagina);
	}
	request.setAttribute("oggettiDisabilitatiSetInRequest", oggettiDisabilitati);
	// COMUNIASSOCIATI
	if (responsabile != null) {
	    List<Responsabilicomuni> listaComuni = responsabilicomuniService.findByOperatore(responsabile);
	    List<Comuni> listaComuniAssociati = new ArrayList<Comuni>();
	    for (Responsabilicomuni responsabilicomuni : listaComuni) {
		Comuni comune = comuniService.findById(responsabilicomuni.getComune().getCodicecomune());
		listaComuniAssociati.add(comune);
	    }
	    request.setAttribute("comuniassociatiListInRequest", listaComuniAssociati);
	    request.setAttribute("responsabileInRequest", responsabile);
	}
	// COMUNIASSOCIATI	
    }

    @Autowired(required = false)
    public void setCacheManager(CacheManager cacheManager) {

	this.cacheManager = cacheManager;
    }

    @Autowired
    public void setLayoutpagineService(LayoutpagineService layoutpagineService) {

	this.layoutpagineService = layoutpagineService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setResponsabilicomuniService(ResponsabilicomuniService responsabilicomuniService) {

	this.responsabilicomuniService = responsabilicomuniService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }
}
