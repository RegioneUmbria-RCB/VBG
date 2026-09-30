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
import org.springframework.web.servlet.ModelAndView;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;
import it.gruppoinit.annotations.security.SecuredMethod.ReadWriteMethod;
import it.gruppoinit.jms.AuditMessage;
import it.gruppoinit.jms.AuditMessageImpl;
import it.gruppoinit.jms.JmsMessageProducer;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
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
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListDettaglio;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayoutTestiMessageSource;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListAttivaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AnagrafeManager;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareattiviService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

@Controller
@SessionAttributes("administrationCommand")
@SecuredComponent(key = "annotations.tipibando.component", parentMenuId = 1)
public class AdminController extends BaseController<Responsabili> {

    private static final String ADMIN_AUTHORIZE_REDIRECT = "../admin/authorize.htm";
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AnagrafeManager anagrafeManager;
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
    private InventarioprocedimentiService inventarioprocedimentiService;
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

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String clearCache(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
    public String updateSoftwareIstanzaView(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	List<Software> softwares = softwareService.findAll(null, null);
	model.addAttribute("softwares", softwares);
	setPageAttributes(model);
	return "admin/updateSoftwareIstanza";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.view")
    public String updateComuneIstanzaView(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	List<Software> softwares = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails());
	model.addAttribute("softwares", softwares);
	List<Comuniassociati> comuniassociatis = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	model.addAttribute("comuniassociatis", comuniassociatis);
	setPageAttributes(model);
	return "admin/updateComuneIstanza";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String updateComuneIstanzaExec(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("comuneA") String comuneA, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.view")
    public String updateSbloccaDocumento(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	setPageAttributes(model);
	return "admin/updateSbloccaDocumento";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String updateSbloccaDocumentoExec(@RequestParam("oggetto.id.codice") Integer codiceOggetto, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	    //	    String responsabile = (String) EntityUtils.getNestedProperty(getCurrentlyAuthenticatedUserDetails(), "responsabile");
	    //	    LoggerCancellazioni.logModificaComuneIstanza(responsabile, descrizioneIstanza, comuneA);
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore nell'aggiornamento dei metadati dell' oggetto. " + e.getMessage());
	    return "redirect:updateSbloccaDocumento.htm";
	}
	FlashMessages.getWarnings()
		.add("L'oggetto [" + oggetto.getNomefile() + "] con codice  [" + oggetto.getId().getCodice() + "] è stato sbloccato.");
	setPageAttributes(model);
	return "redirect:updateSbloccaDocumento.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    @SecuredMethod(methodType = ReadWriteMethod.READ, order = 1, key = "annotations.security.method.view")
    public String listSoftwareAttivi(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
    public void ajaxAbilitaCancellazioneMasterSuSlave(@RequestParam("abilita") Boolean abilita, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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

    @SecuredMethod(methodType = ReadWriteMethod.WRITE, order = 2, key = "annotations.security.method.insert")
    @RequestMapping
    public String updateSoftwareIstanzaExec(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("softwareA") String softwareA,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String cancellazioneAnagrafiche(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	//int count = anagrafeService.countRecord(filterTable);
	List<String> warnings = new ArrayList<String>();
	String intestazioneErroriCancellazione = "Non è stato possibile cancellare le seguenti anagrafiche: <ul>";
	List<Integer> anagrafes = anagrafeService.findCodiciAnagrafe();
	for (Integer codiceAnagrafe : anagrafes) {
	    Anagrafe entity = anagrafeService.findById(new PkId(codiceAnagrafe));
	    try {
		anagrafeService.delete(entity);
	    } catch (Exception e) {
		Anagrafe anagrafe = anagrafeService.findById(new PkId(codiceAnagrafe));
		String errorMessage = "<li><a href=\"../anagrafe/view.htm?codice=" + anagrafe.getId().getCodice() + ">" +
				      entity.getDescrizioneRichiedente() + "[" + entity.getId() + "]" + "</a> a causa di:<ul>";
		if (e instanceof BusinessValidationException) {
		    List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		    for (InvalidValue invalidValue : ivs) {
			String descrizioneMessaggio = Utilities.getMessageFromBundle(getContext(),
				StringUtils.defaultIfEmpty(invalidValue.getMessage(), ""), new Object[] { invalidValue.getValue() });
			if (StringUtils.defaultIfEmpty(descrizioneMessaggio, "").indexOf("??") > 0) {
			    descrizioneMessaggio = invalidValue.getMessage();
			}
			errorMessage = errorMessage.concat("<li>");
			if (!StringUtils.defaultIfEmpty((String) EntityUtils.getNestedProperty(invalidValue, "beanClass"), "").equals("")) {
			    errorMessage = errorMessage.concat("[").concat((String) EntityUtils.getNestedProperty(invalidValue, "beanClass"))
				    .concat("]");
			}
			if (!StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), "").equalsIgnoreCase("")) {
			    errorMessage = errorMessage.concat(",[").concat(StringUtils.defaultIfEmpty(invalidValue.getPropertyPath(), ""))
				    .concat("]");
			}
			errorMessage = errorMessage.concat(descrizioneMessaggio).concat("</li>");
		    }
		    errorMessage = errorMessage.concat("</ul></li>");
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
		    errorMessage = errorMessage.concat("<li>").concat(descrizioneMessaggio).concat("</li></ul></li>");
		} else {
		    errorMessage = errorMessage.concat("<li>").concat(StringUtils.defaultIfEmpty(e.getMessage(), "Errore non definito"))
			    .concat("</li></ul></li>");
		}
		warnings.add(errorMessage);
	    }
	}
	if (warnings.size() > 0) {
	    warnings.add(0, intestazioneErroriCancellazione);
	    warnings.add("</ul>");
	    FlashMessages.setWarnings(warnings);
	}
	setPageAttributes(model);
	return "admin/cancellazioneAnagrafiche";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String cancellazioneEndo(Model model, @RequestParam(required = false) String codiceSoftware, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    Map<Inventarioprocedimenti, String> endoNonCancellati = new HashMap<Inventarioprocedimenti, String>();
	    List<Integer> list = inventarioprocedimentiService.findCodiciEndoPerSoftware(codiceSoftware);
	    for (Integer endo : list) {
		Inventarioprocedimenti _endo = inventarioprocedimentiService.findById(new PkId(endo));
		try {
		    inventarioprocedimentiService.delete(_endo);
		} catch (Exception e) {
		    endoNonCancellati.put(_endo, buildErrorString(e));
		}
	    }
	    model.addAttribute("mapEndoNonCancellati", endoNonCancellati);
	}
	List<Software> softwares = softwareService.findSoftwareAttivi(false);
	Map<Software, Integer> mapEndoCount = new HashMap<Software, Integer>();
	for (Software software : softwares) {
	    FilterTable filter = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction restriction = new FilterRestriction();
	    restriction.addFilterField(FilterUtils.equals("codice", software.getCodice(), "software", String.class));
	    filter.addRestriction(restriction);
	    int count = inventarioprocedimentiService.countRecord(filter);
	    mapEndoCount.put(software, count);
	}
	model.addAttribute("mapEndoCount", mapEndoCount);
	return "admin/cancellazioneEndo";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String resetApplicationParams(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	clearClassObjectCache(model);
	setPageAttributes(model);
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String resetAllCache(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
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
    public void ajaxUpdateAllineaAnagrafichePG(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	anagrafeManager.updateAllineaPersoneGiuridiche();
	FlashMessages.getInfos().add("Operazione completata con successo");
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxUpdateAllineaAnagrafichePF(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	anagrafeManager.updateAllineaPersoneFisiche();
	FlashMessages.getInfos().add("Operazione completata con successo");
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxUpdateAllineaAnagrafiche(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	anagrafeManager.updateAllineaTuttaAnagrafe();
	FlashMessages.getInfos().add("Operazione completata con successo");
	// §§§END§§§
    }

    @RequestMapping
    public String stopAllineamentoAnagrafe(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	anagrafeManager.fermaAllineamentoAnagrafiche();
	FlashMessages.getInfos().add("Operazione completata con successo");
	return "admin/form";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public ModelAndView ajaxReportAllineaAnagrafiche(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	ChiaveValoreBean<String, String> result = anagrafeManager.reportAllineamento();
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("report", result);
	return new ModelAndView("jsonView", model);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public ModelAndView ajaxAllineaAnagraficheInElaborazione(HttpServletRequest request, HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	boolean result = anagrafeManager.elaborazioneInCorso();
	Map<String, Object> model = new HashMap<String, Object>();
	model.put("allineaAnagraficheInCorso", String.valueOf(result));
	return new ModelAndView("jsonView", model);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public String ajaxViewUsers(@RequestParam(value = "mostraTutti", required = false) Boolean mostraTutti, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	// §§§BEGIN§§§
	// checkAccessoFunzionalitaAmministrative(request, response, "../admin/authorize.htm");
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
	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	istanzeService.updateContatori(processaSPGA.booleanValue());
	return "redirect:view.htm";
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @RequestMapping
    public void updateBlackList(HttpServletRequest request, HttpServletResponse response) throws Exception {

	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	StringBuilder result = blacklistMotiviService.updateBlackList();
	response.setContentType("text/plain");
	response.getOutputStream().write(result.toString().getBytes());
    }

    @RequestMapping
    public void blackList(HttpServletRequest request, HttpServletResponse response) throws Exception {

	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	List<BlackListAttivaBean> findAutorizzazioniInBlackList = mercatiAppService.findAutorizzazioniInBlackList();
	response.setContentType("text/plain");
	StringBuffer sbuf = new StringBuffer();
	for (BlackListAttivaBean bl : findAutorizzazioniInBlackList) {
	    sbuf.append(bl.getAutorizzazioniId()).append("\n");
	}
	response.getOutputStream().write(sbuf.toString().getBytes());
    }

    @RequestMapping
    public void blackListAutorizzazione(@RequestParam(value = "idAutorizzazione", required = true) Integer idAutorizzazione,
	    HttpServletRequest request, HttpServletResponse response) throws Exception {

	checkAccessoFunzionalitaAmministrative(request, response, ADMIN_AUTHORIZE_REDIRECT);
	Serializer serializer = new JsonSerializer();
	BlackListDettaglio result = mercatiAppService.findDettaglioBlackListAutorizzazioneEGiornata(idAutorizzazione, null, null);
	String output = (String) serializer.serialize(result);
	response.setContentType("application/json");
	response.getOutputStream().write(output.getBytes());
    }
}
