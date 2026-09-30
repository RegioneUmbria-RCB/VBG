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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.InvalidValue;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;
import it.gruppoinit.pal.gp.core.domain.helper.SoftwareattiviDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayoutTestiMessageSource;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Controller
@SessionAttributes("administrationCommand")
@SecuredComponent(key = "annotations.tipibando.component", parentMenuId = 1)
public class AdminController extends BaseController<Responsabili> {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired(required = false)
    private CacheManager cacheManager;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private SoftwareattiviService softwareattiviService;
    @Autowired
    private ExternalDBResolver externalDBResolver;

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String clearCache(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
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
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String clearLabelCache(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
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
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.view")
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	// Controllo se in session c'è il parametro RISULTATO
	if (request.getSession().getAttribute("RISULTATO") != null) {
	    String risultato = (String) request.getSession().getAttribute("RISULTATO");
	    model.addAttribute("RISULTATO", risultato);
	    request.getSession().removeAttribute("RISULTATO");
	}
	checkAccessoFunzionalitaAmministrative(request, response);
	if (request.getParameter("viewDSInfo") != null) {
	    Set<PooledDataSource> pooledDSList = C3P0Registry.getPooledDataSources();
	    model.addAttribute("pooledDSList", pooledDSList);
	}
	setPageAttributes(model);
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.view")
    public String listSoftwareAttivi(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	List<SoftwareattiviDTO> softwareattiviDTOs = softwareattiviService.findAllSoftwareattiviDTO();
	model.addAttribute("softwaresList", softwareattiviDTOs);
	setPageAttributes(model);
	return "admin/listSoftwareAttivi";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void ajaxAttivaDisattiva(@RequestParam("codice") String codice, @RequestParam("attivo") Boolean attivo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

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
    public void ajaxAttivaDisattivaFO(@RequestParam("codice") String codice, @RequestParam("attivo") Boolean attivo, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

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
    public String resetApplicationParams(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	Map<String, String> params = WebConstants.reloadSecurityParamsMap();
	model.addAttribute("securityparams", params);
	ApplicationContext ctx = getContext();
	setPageAttributes(model);
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String authorize(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	String password = (String) request.getParameter("password");
	if (StringUtils.isNotBlank(password)) {
	    String passwordMatch = getPasswordFunzionalitaAmminitrative();
	    if (password.equals(passwordMatch)) {
		Responsabili responsabili = getCurrentlyAuthenticatedUserDetails();
		String message = "Operatore [" +
			responsabili.getResponsabile() +
			"-" +
			responsabili.getId() +
			"] è autorizzato alle funzionalità amministrative";
		log.warn(message);
		request.getSession().setAttribute(UTENTE_AUTORIZZATO_AMMINISTRAZIONE, responsabili.getResponsabile());
		FlashMessages.getInfos().add(message);
		return "redirect:view.htm";
	    } else {
		FlashMessages.getWarnings().add("Password non corretta");
	    }
	}
	return "admin/pwd";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
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

    }

    @Override
    protected void fixRenderEntityProperty(Responsabili entity) {

    }

    private void clearClassObjectCache(Model model) throws Exception {

	ApplicationContext ctx = getContext();
	ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
	scanner.addIncludeFilter(new AnnotationTypeFilter(DeletableCacheElements.class));
	StringBuffer sb = new StringBuffer("Rimozione degli oggetti in cache delle seguenti classi:<br /><ul>");
	for (BeanDefinition bd : scanner.findCandidateComponents("it.gruppoinit")) {
	    Object obj = null;
	    String className = bd.getBeanClassName();
	    Class c = Class.forName(className);
	    if (className.indexOf(".") > 0) {
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
		    AnnotatedElement annotation = (AnnotatedElement) method;
		    DeletableCacheElements annMethod = annotation.getAnnotation(DeletableCacheElements.class);
		    if (annMethod != null) {
			sb.append("<li>Metodo: ").append(method.toString());
			log.warn("clearClassObjectCache: il metodo {} implementa l''annotazione @DeletableCacheElements", method.toString(),
				c.getName());
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

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	clearClassObjectCache(model);
	setPageAttributes(model);
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String resetAllCache(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
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
	    if (parentMessageSource != null && parentMessageSource instanceof ReloadableResourceBundleMessageSource) {
		ReloadableResourceBundleMessageSource reloSource = (ReloadableResourceBundleMessageSource) parentMessageSource;
		reloSource.clearCache();
	    }
	}
	model.addAttribute("labelCache", "true");
	// tutte le classi che usano l'annotazione @DeletableCacheElements
	clearClassObjectCache(model);
	setPageAttributes(model);
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private String buildErrorString(Exception e) {

	StringBuffer errorMessage = new StringBuffer("<ul>");
	if (e instanceof BusinessValidationException) {
	    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
	    for (InvalidValue invalidValue : ivs) {
		String descrizioneMessaggio = Utilities.getMessageFromBundle(getContext(), StringUtils.defaultIfEmpty(invalidValue.getMessage(), ""),
			new Object[] { invalidValue.getValue() });
		if (StringUtils.defaultIfEmpty(descrizioneMessaggio, "").indexOf("??") > 0) {
		    descrizioneMessaggio = invalidValue.getMessage();
		}
		errorMessage = errorMessage.append("<li>");
		if (!StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(invalidValue, "beanClass"), "").equals("")) {
		    errorMessage = errorMessage.append("[").append((String) EntityUtils.getNestedProperty(invalidValue, "beanClass")).append("]");
		}
		if (!StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), "").equalsIgnoreCase("")) {
		    errorMessage = errorMessage.append(",[").append(StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), "")).append("]");
		}
		errorMessage = errorMessage.append(descrizioneMessaggio).append("</li>");
	    }
	    errorMessage = errorMessage.append("</ul>");
	} else if (e instanceof DataIntegrityViolationException) {
	    String descrizioneMessaggio = "";
	    Throwable cause = e.getCause();
	    if (cause != null) {
		if (cause instanceof ConstraintViolationException) {
		    descrizioneMessaggio = ((ConstraintViolationException) cause).getSQLException().getMessage();
		} else {
		    descrizioneMessaggio = cause.getMessage();
		}
	    } else {
		e.getMessage();
	    }
	    errorMessage = errorMessage.append("<li>").append(descrizioneMessaggio).append("</li></ul>");
	} else {
	    errorMessage = errorMessage.append("<li>").append(StringUtils.defaultIfEmpty(e.getMessage(), "Errore non definito")).append("</li></ul>");
	}
	return errorMessage.toString();
    }

    @RequestMapping
    public String ajaxViewUsers(@RequestParam(value = "mostraTutti", required = false) Boolean mostraTutti, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	// checkAccessoFunzionalitaAmministrative(request, response);
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
	return "admin/listUsers";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxPrivateProcessaContatoriIstanze(@RequestParam(value = "processaSPGA", required = true) Boolean processaSPGA,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response);
	istanzeService.updateContatori(processaSPGA.booleanValue());
	return "redirect:view.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
