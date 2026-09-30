package it.gruppoinit.pal.gp.backoffice.aop;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.User;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.backoffice.web.rest.AnagrafeRestService;
import it.gruppoinit.pal.gp.backoffice.web.rest.DecodificheRestService;
import it.gruppoinit.pal.gp.backoffice.web.rest.LayoutRestService;
import it.gruppoinit.pal.gp.backoffice.web.rest.StatusRestService;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.LockTableService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.upgr.web.UpgradeController;

@Aspect
public class AuditingSuFile {

    private final Logger webActivityLog = LoggerFactory.getLogger("it.gruppoinit.auditing.web_activity");
    private final Logger serviceExceptionLog = LoggerFactory.getLogger("it.gruppoinit.auditing.service_errors");
    private static String USERINFO_USERNAME = "USER_NAME";
    private static String USERINFO_REMOTEADDRESS = "REMOTE_ADDRESS";
    @Autowired
    private LockTableService lockTableService;
    private static Set<String> controllerEsclusi = new HashSet<String>();
    @Autowired
    private IVerticalizzazioneParametriSistemaService parametriSistemaService;
    static {
	controllerEsclusi.add("AjaxController");
	controllerEsclusi.add("JSONController");
	controllerEsclusi.add("MenuController");
	controllerEsclusi.add("WelcomeController");
	controllerEsclusi.add("BatchScadenzarioController");
	controllerEsclusi.add("HistoryController");
	controllerEsclusi.add("JSONRicercheController");
	controllerEsclusi.add(StatusRestService.class.getSimpleName());
    }

    //    private static String MESSAGE_URL_VISITED = "URL_VISITED";
    //    private static String MESSAGE_EXCEPTION_THROWN = "EXCEPTION_THROWN";
    private static enum LAYERS {
	DAO,
	SERVICE,
	WEB
    };

    /**
     * Traccia l'attività web degli utenti
     * 
     */
    @SuppressWarnings("unchecked")
    public void trackWebLayer(JoinPoint jp) {

	boolean eseguiAuditLog = true;
	String metodo = jp.getSignature().getName();
	if (metodo.toLowerCase().equalsIgnoreCase("defaultoptions")) {
	    eseguiAuditLog = false;
	}
	Object target = jp.getTarget();
	if (target instanceof UpgradeController || target instanceof AnagrafeRestService || target instanceof DecodificheRestService
		|| target instanceof LayoutRestService) {
	    eseguiAuditLog = false;
	}
	Object[] args = jp.getArgs();
	for (Object methodArg : args) {
	    if (methodArg instanceof HttpServletRequest) {
		HttpServletRequest request = (HttpServletRequest) methodArg;
		String url = StringUtils.defaultString(request.getRequestURI());
		if (url.indexOf("upgrade/setup.htm") >= 0) {
		    eseguiAuditLog = false;
		    break;
		}
	    }
	}
	String oggetto = target.getClass().getSimpleName();
	if (!controllerEsclusi.contains(oggetto)) {
	    if (eseguiAuditLog && isAttivaTracciatura()) {
		String sessionId = "";
		Map<String, String> userInfo = getUserInfo();
		args = jp.getArgs();
		StringBuilder parametriRequest = new StringBuilder();
		for (Object methodArg : args) {
		    if (methodArg instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) methodArg;
			if (StringUtils.isBlank(sessionId)) {
			    sessionId = request.getSession().getId();
			}
			Enumeration<String> requestParams = request.getParameterNames();
			while (requestParams.hasMoreElements()) {
			    String paramName = requestParams.nextElement();
			    parametriRequest.append(paramName).append("===").append(verificaParametro(paramName, request)).append("\t");
			}
			break;
		    } else {
			parametriRequest.append(methodArg).append(",");
		    }
		}
		webActivityLog.warn("SESSIONID=={}\tUTENTE=={}\tHOST=={}\tOGGETTO=={}\tMETODO=={}\tPARAMETRI_REQUEST=={}", new Object[] { sessionId,
			userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), oggetto, metodo, parametriRequest });
	    }
	}
    }

    private String verificaParametro(String paramName, HttpServletRequest request) {

	String valore = request.getParameter(paramName);
	if (StringUtils.defaultString(paramName).equalsIgnoreCase("password") || // 
		StringUtils.defaultString(paramName).equalsIgnoreCase("pwd")) {
	    valore = "*********";
	}
	return valore;
    }

    private boolean isAttivaTracciatura() {

	String key = getParamKey();
	if (pMap.get(key) == null) {
	    boolean attiva = true; // sempre attiva eventualmente lo disattivo
	    if (this.parametriSistemaService.isAttiva() && !this.parametriSistemaService.attivaAuditWeb()) {
		attiva = false;
	    }
	    pMap.put(key, attiva);
	}
	return pMap.get(key);
    }

    private String getParamKey() {

	return ORMHelper.getIdcomuneAlias();
    }

    private Map<String, Boolean> pMap = new HashMap<String, Boolean>();

    @DeletableCacheElements
    public void resetObjectCached() {

	pMap = new HashMap<String, Boolean>();
    }

    private Map<String, String> getUserInfo() {

	Map<String, String> userInfo = new HashMap<String, String>();
	String remoteAddress = "";
	User user = null;
	SecurityContext sc = SecurityContextHolder.getContext();
	WebAuthenticationDetails wad;
	Authentication auth;
	if (sc != null) {
	    auth = sc.getAuthentication();
	    if (auth != null) {
		wad = (WebAuthenticationDetails) auth.getDetails();
		if (wad != null) {
		    remoteAddress = wad.getRemoteAddress();
		}
		user = (User) sc.getAuthentication().getPrincipal();
	    }
	}
	if (user != null) {
	    if (user instanceof LoggedUser) {
		userInfo.put(USERINFO_USERNAME, ((LoggedUser) user).getResponsabile() + " - [" + ORMHelper.getIdcomune() + "-" +
						((LoggedUser) user).getCodiceResponsabile() + "]");
	    } else {
		userInfo.put(USERINFO_USERNAME, user.getUsername() + "-" + ORMHelper.getIdcomune());
	    }
	}
	userInfo.put(USERINFO_REMOTEADDRESS, remoteAddress);
	return userInfo;
    }

    public void trackServiceException(JoinPoint jp, Exception exception) {

	if (serviceExceptionLog.isTraceEnabled()) {
	    internalTrackException(jp, exception, LAYERS.SERVICE, serviceExceptionLog);
	}
    }

    @SuppressWarnings("unchecked")
    private void internalTrackException(String oggetto, String metodo, Object[] args, Exception exception, LAYERS layer, Object bean, Logger log) {

	if (log.isTraceEnabled()) {
	    Map<String, String> userInfo = getUserInfo();
	    StringBuilder parametri = new StringBuilder("");
	    if (!(exception instanceof BaseValidationException)) {
		for (Object methodArg : args) {
		    if (methodArg instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) methodArg;
			Enumeration<String> requestParams = request.getParameterNames();
			while (requestParams.hasMoreElements()) {
			    String paramName = requestParams.nextElement();
			    parametri.append(paramName).append("===").append(request.getParameter(paramName)).append("\n");
			}
			break;
		    } else {
			parametri.append(methodArg).append("\n");
		    }
		}
		parametri.append("\n\tEccezione lanciata:").append(exception.getMessage()).append("\n\tStackTrace:");
		StackTraceElement[] els = exception.getStackTrace();
		int i = 0;
		for (StackTraceElement stackTraceElement : els) {
		    if (i < 15) {
			parametri.append("\n\t\t").append(stackTraceElement.toString());
		    } else {
			break;
		    }
		    i++;
		}
		String messaggio = parametri.toString();
		log.trace("\n\tUTENTE :\t{}\n\tHOST   :\t{}\n\tOGGETTO:\t{}\n\tMETODO :\t{}\n\tPARAMETRI:{}",
			new Object[] { userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), oggetto, metodo, messaggio });
	    }
	}
    }

    private void internalTrackException(JoinPoint jp, Exception exception, LAYERS layer, Logger log) {

	String oggetto = jp.getTarget().getClass().getSimpleName();
	String metodo = jp.getSignature().getName();
	Object[] args = jp.getArgs();
	Object bean = jp.getTarget();
	internalTrackException(oggetto, metodo, args, exception, layer, bean, log);
    }

    @Before("publicMethodInsideAClassMarkedWithAtMonitor()")
    public void checkSecurityPermissions(JoinPoint jp) {

	// Recupero le informazioni sull'utente loggato
	// Recupero le informazioni sui metadati e annotazioni del metodo e della classe invocati
	Class<?> clazz = jp.getTarget().getClass();
	AnnotatedElement classAnnotations = clazz;
	String metodo = jp.getSignature().getName();
	Object[] args = jp.getArgs();
	try {
	    StringBuilder annotationDump = new StringBuilder("Loggable({})[");
	    annotationDump.append("\n\tUtente        :\t{}");
	    annotationDump.append("\n\tfeature        :\t{}");
	    annotationDump.append("\n\tmetodo invocato        :\t{}");
	    annotationDump.append("\n\tparametri passati        :\t{}");
	    String metodoParametri = "";
	    if (args != null && args.length > 0) {
		for (Object arg : args) {
		    if (arg != null) {
			metodoParametri += arg.toString() + ",";
		    }
		}
	    }
	    annotationDump.append("\n\t}\n]");
	    Loggable loggable = classAnnotations.getAnnotation(Loggable.class);
	    Object[] parametri = new Object[] { clazz.toString(), getUserInfo().get(USERINFO_USERNAME), loggable.featureName(), metodo,
		    metodoParametri };
	    LoggerFactory.getLogger(loggable.featureName()).error(annotationDump.toString(), parametri);
	} catch (SecurityException e) {
	    e.printStackTrace();
	}
    }

    @Pointcut("within(@it.gruppoinit.annotations.audit.Loggable *)")
    public void onSecuredComponent() {

    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {

    }

    @Pointcut("publicMethod() && onSecuredComponent()")
    public void publicMethodInsideAClassMarkedWithAtMonitor() {

    }

    @Pointcut("@annotation(it.gruppoinit.annotations.lock.Lockable)")
    public void onLockMetod() {

    }

    @Before("onLockMetod()")
    public void onLockMetod(JoinPoint jp) {

	Method method = null;
	try {
	    method = ((MethodSignature) jp.getSignature()).getMethod();
	    String lockName = jp.getTarget().getClass().getSimpleName() + "." + method.getName();
	    Object[] args = jp.getArgs();
	    if (args != null && args.length >= 1) {
		lockName += "_" + args[0];
	    }
	    lockTableService.getLock(lockName);
	} catch (SecurityException e) {
	    e.printStackTrace();
	}
    }
}