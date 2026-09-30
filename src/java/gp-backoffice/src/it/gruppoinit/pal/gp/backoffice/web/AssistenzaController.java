package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.jms.AuditMessage;
import it.gruppoinit.jms.AuditMessageImpl;
import it.gruppoinit.jms.JmsMessageProducer;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadati;
import it.gruppoinit.pal.gp.core.domain.OggettiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListDettaglio;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayoutTestiMessageSource;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListAttivaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.RuoliUtentiEnum;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Controller
@SessionAttributes("administrationCommand")
public class AssistenzaController extends BaseController<Responsabili> {

    private static final Logger log = LoggerFactory.getLogger(AssistenzaController.class);
    private static final String OPERATION_AUTHORIZE_REDIRECT = "../assistenza/authorize.htm";
    @Autowired(required = false)
    private CacheManager cacheManager;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private OggettiMetadatiService oggettiMetadatiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private BlacklistMotiviService blacklistMotiviService;
    @Autowired
    private MercatiAppService mercatiAppService;

    @RequestMapping
    public String clearCache(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	if (cacheManager != null) {
	    String[] cacheNames = cacheManager.getCacheNames();
	    if (cacheNames != null) {
		for (String nomeCache : cacheNames) {
		    Cache cache = cacheManager.getCache(nomeCache);
		    cache.removeAll();
		}
	    }
	    model.addAttribute("caches", cacheNames);
	}
	setPageAttributes(model);
	return "assistenza/form";
    }

    @RequestMapping
    public String clearLabelCache(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	ApplicationContext ctx = getContext();
	Object obj = ctx.getBean("messageSource");
	if (obj instanceof DelegatingMessageSource) {
	    DelegatingMessageSource messageSource = (DelegatingMessageSource) obj;
	    MessageSource parentMessageSource = messageSource.getParentMessageSource();
	    if (parentMessageSource != null) {
		if (parentMessageSource instanceof ReloadableResourceBundleMessageSource) {
		    ReloadableResourceBundleMessageSource reloSource = (ReloadableResourceBundleMessageSource) parentMessageSource;
		    reloSource.clearCache();
		} else if (parentMessageSource instanceof LayoutTestiMessageSource) {
		    LayoutTestiMessageSource s = ((LayoutTestiMessageSource) parentMessageSource);
		    s.refreshIfNecessary();
		}
	    }
	}
	model.addAttribute("labelCache", "true");
	setPageAttributes(model);
	return "assistenza/form";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	// Controllo se in session c'è il parametro RISULTATO
	if (request.getSession().getAttribute("RISULTATO") != null) {
	    String risultato = (String) request.getSession().getAttribute("RISULTATO");
	    model.addAttribute("RISULTATO", risultato);
	    request.getSession().removeAttribute("RISULTATO");
	}
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	if (request.getParameter("viewDSInfo") != null) {
	    Set<PooledDataSource> pooledDSList = C3P0Registry.getPooledDataSources();
	    model.addAttribute("pooledDSList", pooledDSList);
	}
	setPageAttributes(model);
	return "assistenza/form";
    }

    @RequestMapping
    public String updateSoftwareIstanzaView(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	List<Software> softwares = softwareService.findAll(null, null);
	model.addAttribute("softwares", softwares);
	setPageAttributes(model);
	return "assistenza/updateSoftwareIstanza";
    }

    @RequestMapping
    public String updateComuneIstanzaView(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	List<Software> softwares = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("softwares", softwares);
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	model.addAttribute("comuniassociatis", comuniassociatis);
	setPageAttributes(model);
	return "assistenza/updateComuneIstanza";
    }

    @RequestMapping
    public String updateComuneIstanzaExec(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("comuneA") String comuneA, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    FlashMessages.getWarnings().add("Istanza con codice " + codiceIstanza + " non è stata trovata");
	    return "redirect:updateComuneIstanzaView.htm";
	}
	String comuneDA = istanza.getComune().getCodicecomune();
	if (comuneDA.equalsIgnoreCase(comuneA)) {
	    FlashMessages.getWarnings().add("I comuni passati sono uguali. Nessuna operazione eseguita");
	    return "redirect:updateComuneIstanzaView.htm";
	}
	String descrizioneIstanza = istanza.toString();
	try {
	    istanzeService.updateComuneIstanza(codiceIstanza, comuneA);
	    String responsabile = (String) EntityUtils.getNestedProperty(getCurrentlyAuthenticatedUserDetails(), "responsabile");
	    LoggerCancellazioni.logModificaComuneIstanza(responsabile, descrizioneIstanza, comuneA);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'aggiornamento del comune della pratica. " + e.getMessage());
	    return "redirect:updateComuneIstanzaView.htm";
	}
	FlashMessages.getWarnings()
		.add("L'istanza [" + istanza.getNumeroistanza() + "] del comune [" + comuneDA + "] è stata spostata nel comune " + comuneA + ".");
	List<Software> softwares = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("softwares", softwares);
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	model.addAttribute("comuniassociatis", comuniassociatis);
	setPageAttributes(model);
	return "redirect:updateComuneIstanzaView.htm";
    }

    @RequestMapping
    public String updateSbloccaDocumento(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	setPageAttributes(model);
	return "assistenza/updateSbloccaDocumento";
    }

    @RequestMapping
    public String updateSbloccaDocumentoExec(@RequestParam("oggetto.id.codice") Integer codiceOggetto, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	if (oggetto == null) {
	    FlashMessages.getWarnings().add("Oggetto con codice " + codiceOggetto + " non è stata trovato");
	    return "redirect:updateSbloccaDocumento.htm";
	}
	OggettiMetadatiId metadatiId = new OggettiMetadatiId(oggetto.getId().getCodice(), WebConstants.TAG_LOCKED_FILE);
	OggettiMetadati oggettiMetadati = oggettiMetadatiService.findById(metadatiId);
	if (oggettiMetadati == null) {
	    FlashMessages.getWarnings()
		    .add("Per l'oggetto con codice " + codiceOggetto + " non è stata trovato il metadato " + WebConstants.TAG_LOCKED_FILE);
	    return "redirect:updateSbloccaDocumento.htm";
	}
	try {
	    oggettiService.updateSbloccaOggetto(oggettiMetadati);
	    String responsabile = (String) EntityUtils.getNestedProperty(getCurrentlyAuthenticatedUserDetails(), "responsabile");
	    LoggerUpdaterecord.log(responsabile);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'aggiornamento dei metadati dell' oggetto. " + e.getMessage());
	    return "redirect:updateSbloccaDocumento.htm";
	}
	FlashMessages.getWarnings()
		.add("L'oggetto [" + oggetto.getNomefile() + "] con codice  [" + oggetto.getId().getCodice() + "] è stato sbloccato.");
	setPageAttributes(model);
	return "redirect:updateSbloccaDocumento.htm";
    }

    @RequestMapping
    public String listSoftwareAttivi(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	List<SoftwareattiviDTO> softwareattiviDTOs = softwareattiviService.findAllSoftwareattiviDTO();
	model.addAttribute("softwaresList", softwareattiviDTOs);
	setPageAttributes(model);
	return "assistenza/listSoftwareAttivi";
    }

    @RequestMapping
    public void ajaxAttivaDisattiva(@RequestParam("codice") String codice, @RequestParam("attivo") Boolean attivo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	Software software = softwareService.findById(codice);
	SoftwareattiviId softwareattiviId = new SoftwareattiviId(codice);
	try {
	    if (attivo) {
		// Se viene attivato il software si inserisce il record nella tabella Softwareattivi
		Softwareattivi softwareattivi = new Softwareattivi(softwareattiviId, software, false);
		softwareattiviService.insert(softwareattivi);
	    } else {
		// Se il software viene disattivato il record nella tabella Softwareattivi viene cancellato
		Softwareattivi softwareattivi = softwareattiviService.findById(softwareattiviId);
		softwareattiviService.delete(softwareattivi);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public void ajaxAbilitaCancellazioneMasterSuSlave(@RequestParam("abilita") Boolean abilita, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	user.setAbilitaCancellazioneMasterSuSlave(abilita.booleanValue());
	request.getSession().setAttribute("AbilitaCancellazioneMasterSuSlave", abilita);
	LoggerCancellazioni.log("l'utente [" + user.getResponsabile() + "(" + user.getCodiceResponsabile() +
				")] è stato abilitato alla cancellazione di record MASTER  (" + abilita.booleanValue() + ")");
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    @RequestMapping
    public void ajaxAttivaDisattivaFO(@RequestParam("codice") String codice, @RequestParam("attivo") Boolean attivo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	SoftwareattiviId softwareattiviId = new SoftwareattiviId(codice);
	try {
	    Softwareattivi softwareattivi = softwareattiviService.findById(softwareattiviId);
	    softwareattivi.setAttivoFo(attivo);
	    softwareattiviService.update(softwareattivi);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null));
	}
    }

    @RequestMapping
    public String updateSoftwareIstanzaExec(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("softwareA") String softwareA,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    FlashMessages.getWarnings().add("Istanza con codice " + codiceIstanza + " non è stata trovata");
	    return "redirect:updateSoftwareIstanzaView.htm";
	}
	String softwareDA = istanza.getSoftware().getCodice();
	if (softwareDA.equalsIgnoreCase(softwareA)) {
	    FlashMessages.getWarnings().add("I software passati sono uguali. Nessuna operazione eseguita");
	    return "redirect:updateSoftwareIstanzaView.htm";
	}
	String descrizioneIstanza = istanza.toString();
	Software newSoftware = softwareService.findById(softwareA);
	istanza.setSoftware(newSoftware);
	String oldNumeroIStanza = istanza.getNumeroistanza();
	String suffisso = StringUtils.defaultIfEmpty(request.getParameter("suffisso"), softwareA);
	istanza.setNumeroistanza(istanza.getNumeroistanza() + "/" + suffisso);
	try {
	    istanzeService.update(istanza);
	    String responsabile = (String) EntityUtils.getNestedProperty(getCurrentlyAuthenticatedUserDetails(), "responsabile");
	    LoggerCancellazioni.logModificaSoftwareIstanza(responsabile, descrizioneIstanza, softwareA);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'aggiornamento del software della pratica. " + e.getMessage());
	    return "redirect:updateSoftwareIstanzaView.htm";
	}
	FlashMessages.getWarnings().add("L'istanza [" + oldNumeroIStanza + "] del software [" + softwareDA + "] è stata spostata nel software " +
					softwareA + ". Il nuovo numeroistanza è " + istanza.getNumeroistanza());
	List<Software> softwares = softwareService.findAll(null, null);
	model.addAttribute("softwares", softwares);
	setPageAttributes(model);
	return "redirect:updateSoftwareIstanzaView.htm";
    }

    @RequestMapping
    public String resetApplicationParams(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Map<String, String> params = new TreeMap<String, String>();
	params.putAll(WebConstants.reloadSecurityParamsMap());
	model.addAttribute("securityparams", params);
	ApplicationContext ctx = getContext();
	try {
	    JmsMessageProducer jmsMessageProducer = (JmsMessageProducer) ctx.getBean("jmsMessageProducer", JmsMessageProducer.class);
	    jmsMessageProducer.initialize();
	    AuditMessage message = new AuditMessageImpl(getCurrentlyAuthenticatedUserDetails().getResponsabile(), request.getRemoteAddr(),
		    "check Connettività jms", "admin check", "messaggio OK".getBytes());
	    jmsMessageProducer.sendMessage(message);
	} catch (Exception e) {
	    log.error("resetApplicationParams(): {}", e.getMessage());
	}
	setPageAttributes(model);
	return "assistenza/form";
    }

    @RequestMapping
    public String authorize(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	String password = request.getParameter("password");
	if (StringUtils.isNotBlank(password)) {
	    String passwordMatch = getPasswordFunzionalitaAmminitrative();
	    if (password.equals(passwordMatch)) {
		Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
		String message = "Operatore [" + responsabili.getResponsabile() + "-" + responsabili.getId() +
				 "] è autorizzato alle funzionalità amministrative";
		log.warn(message);
		request.getSession().setAttribute(UTENTE_AUTORIZZATO_AMMINISTRAZIONE, responsabili.getResponsabile());
		FlashMessages.getInfos().add(message);
		return "redirect:view.htm";
	    } else {
		FlashMessages.getWarnings().add("Password non corretta");
	    }
	}
	return "assistenza/pwd";
    }

    private String getPasswordFunzionalitaAmminitrative() {

	Properties ps = externalDBResolver.getConnectionProperties(ORMHelper.getIdcomuneAlias());
	return ps.getProperty("hibernate.connection.password");
    }

    @Override
    protected void setPageAttributes(Model model) {

	if (cacheManager != null) {
	    model.addAttribute("isCacheManager", true);
	} else {
	    model.addAttribute("isCacheManager", false);
	}
	model.addAttribute("administrationCommand", new BaseCommand());
    }

    @Override
    protected void fixMergeEntityProperty(Responsabili entity) {

	// non devo fare niente
    }

    @Override
    protected void fixRenderEntityProperty(Responsabili entity) {

	// non devo fare niente
    }

    private void clearClassObjectCache(Model model) throws Exception {

	ApplicationContext ctx = getContext();
	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
	scanner.addIncludeFilter(new AnnotationTypeFilter(DeletableCacheElements.class));
	StringBuilder sb = new StringBuilder("Rimozione degli oggetti in cache delle seguenti classi:<br /><ul>");
	for (BeanDefinition bd : scanner.findCandidateComponents("it.gruppoinit")) {
	    Object obj = null;
	    String className = bd.getBeanClassName();
	    Class c = Class.forName(className);
	    if (className.indexOf(".") >= 0) {
		className = className.substring(className.lastIndexOf("."));
		className = className.replace(".", "");
		className = StringUtils.uncapitalize(className);
	    }
	    log.warn("clearClassObjectCache: className={}", className);
	    try {
		obj = ctx.getBean(className);
	    } catch (Exception e) {
		if (className.endsWith("Impl")) {
		    className = className.substring(0, className.lastIndexOf("Impl"));
		}
		try {
		    obj = ctx.getBean(className);
		} catch (Exception e1) {
		    log.error("clearClassObjectCache: {}", e.getMessage());
		}
	    }
	    if (obj != null) {
		Method[] methods = c.getDeclaredMethods();
		// Loop through the methods and print out their names
		for (Method method : methods) {
		    AnnotatedElement annotation = method;
		    DeletableCacheElements annMethod = annotation.getAnnotation(DeletableCacheElements.class);
		    if (annMethod != null) {
			sb.append("<li>Metodo: ").append(method.toString());
			log.warn("clearClassObjectCache: il metodo {} della classe {} implementa l''annotazione @DeletableCacheElements",
				method.toString(), c.getName());
			try {
			    InvocationHandler handler = Proxy.getInvocationHandler(obj);
			    log.warn("clearClassObjectCache: Invoco il metodo {}", method.toString());
			    handler.invoke(obj, method, null);
			    sb.append(" <b style=\"color:green\">SUCCESS</b>");
			} catch (Throwable e) {
			    try {
				// probabilmente non è un proxy   
				method.invoke(obj);
				sb.append(" <b style=\"color:green\">SUCCESS</b>");
			    } catch (Throwable ex) {
				log.error("Non è stato possibile invocare il metodo {} a causa di {}",
					new Object[] { method.toString(), ex.getMessage() });
				sb.append(" <b style=\"color:red\">FAILURE</b> ").append(ex.getMessage());
			    }
			}
			sb.append("</li>");
		    }
		}
	    }
	}
	model.addAttribute("CLASS_CACHE_REMOVED", sb.append("</ul>").toString());
    }

    @RequestMapping
    public String resetClassCache(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	clearClassObjectCache(model);
	setPageAttributes(model);
	return "assistenza/form";
    }

    @RequestMapping
    public String resetAllCache(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	// SIGEPROSECURITYPARAMS
	Map<String, String> params = WebConstants.reloadSecurityParamsMap();
	model.addAttribute("securityparams", params);
	// EHCACHE 
	if (cacheManager != null) {
	    String[] cacheNames = cacheManager.getCacheNames();
	    if (cacheNames != null) {
		for (String nomeCache : cacheNames) {
		    Cache cache = cacheManager.getCache(nomeCache);
		    cache.removeAll();
		}
	    }
	    model.addAttribute("caches", cacheNames);
	}
	// LABEL CACHE
	ApplicationContext ctx = getContext();
	Object obj = ctx.getBean("messageSource");
	if (obj instanceof DelegatingMessageSource) {
	    DelegatingMessageSource messageSource = (DelegatingMessageSource) obj;
	    MessageSource parentMessageSource = messageSource.getParentMessageSource();
	    if (parentMessageSource instanceof ReloadableResourceBundleMessageSource) {
		ReloadableResourceBundleMessageSource reloSource = (ReloadableResourceBundleMessageSource) parentMessageSource;
		reloSource.clearCache();
	    }
	}
	model.addAttribute("labelCache", "true");
	// tutte le classi che usano l'annotazione @DeletableCacheElements
	clearClassObjectCache(model);
	setPageAttributes(model);
	return "assistenza/form";
    }

    @RequestMapping
    public String ajaxViewUsers(@RequestParam(value = "mostraTutti", required = false) Boolean mostraTutti, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Map<String, String> model = new HashMap<String, String>();
	ServletContext context = request.getSession().getServletContext();
	model = (Map<String, String>) context.getAttribute(WebConstants.MAPPA_UTENTI_COLLEGATI_REQ_ATTR);
	List<String> result = new ArrayList<String>();
	if (model != null && model.size() > 0) {
	    for (Map.Entry<String, String> entry : model.entrySet()) {
		if (!BooleanUtils.toBoolean(mostraTutti)) {
		    result.add(entry.getValue());
		} else {
		    if (entry.getKey().startsWith(ORMHelper.getIdcomune())) {
			result.add(entry.getValue());
		    }
		}
	    }
	}
	request.setAttribute("listUsers", result);
	return "assistenza/listUsers";
    }

    @RequestMapping
    public String ajaxPrivateProcessaContatoriIstanze(@RequestParam(value = "processaSPGA", required = true) Boolean processaSPGA,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	istanzeService.updateContatori(processaSPGA.booleanValue());
	return "redirect:view.htm";
    }

    @RequestMapping
    public void updateBlackList(HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	StringBuilder result = blacklistMotiviService.updateBlackList();
	response.setContentType("text/plain");
	response.getOutputStream().write(result.toString().getBytes());
    }

    @RequestMapping
    public void blackList(HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	List<BlackListAttivaBean> findAutorizzazioniInBlackList = mercatiAppService.findAutorizzazioniInBlackList();
	response.setContentType("text/plain");
	StringBuilder sbuf = new StringBuilder();
	for (BlackListAttivaBean bl : findAutorizzazioniInBlackList) {
	    sbuf.append(bl.getAutorizzazioniId()).append("\n");
	}
	response.getOutputStream().write(sbuf.toString().getBytes());
    }

    @RequestMapping
    public void blackListAutorizzazione(@RequestParam(value = "idAutorizzazione", required = true) Integer idAutorizzazione,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	userHasRole(true, RuoliUtentiEnum.OPERATION.name());
	checkAccessoFunzionalitaAmministrative(request, response, OPERATION_AUTHORIZE_REDIRECT);
	Serializer serializer = new JsonSerializer();
	BlackListDettaglio result = mercatiAppService.findDettaglioBlackListAutorizzazioneEGiornata(idAutorizzazione, null, null);
	String output = (String) serializer.serialize(result);
	response.setContentType("application/json");
	response.getOutputStream().write(output.getBytes());
    }
}
