package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.User;

@Aspect
public class AuditingSuFile {

    private final Logger webActivityLog = LoggerFactory.getLogger("it.gruppoinit.auditing.web_activity");
    private final Logger serviceExceptionLog = LoggerFactory.getLogger("it.gruppoinit.auditing.service_errors");
    private static String USERINFO_USERNAME = "USER_NAME";
    private static String USERINFO_REMOTEADDRESS = "REMOTE_ADDRESS";

    //    private static String MESSAGE_URL_VISITED = "URL_VISITED";
    //    private static String MESSAGE_EXCEPTION_THROWN = "EXCEPTION_THROWN";
    private static enum LAYERS {
	DAO, SERVICE, WEB
    };

    /**
     * Traccia l'attività web degli utenti
     * 
     */
    @SuppressWarnings("unchecked")
    public void trackWebLayer(JoinPoint jp) {

	if (webActivityLog.isTraceEnabled()) {
	    boolean eseguiAuditLog = false;
	    String metodo = jp.getSignature().getName();
	    if (metodo.toLowerCase().indexOf("view") > -1) {
		eseguiAuditLog = true;
	    }
	    if (metodo.toLowerCase().indexOf("update") > -1) {
		eseguiAuditLog = true;
	    }
	    if (metodo.toLowerCase().indexOf("insert") > -1) {
		eseguiAuditLog = true;
	    }
	    if (metodo.toLowerCase().indexOf("delete") > -1) {
		eseguiAuditLog = true;
	    }
	    if (eseguiAuditLog) {
		String sessionId = "";
		Map<String, String> userInfo = getUserInfo();
		String oggetto = jp.getTarget().getClass().getSimpleName().toString();
		Object[] args = jp.getArgs();
		StringBuffer parametriRequest = new StringBuffer("\n");
		for (Object methodArg : args) {
		    if (methodArg instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) methodArg;
			if (StringUtils.isBlank(sessionId)) {
			    sessionId = request.getSession().getId();
			}
			Enumeration<String> requestParams = (Enumeration<String>) request.getParameterNames();
			while (requestParams.hasMoreElements()) {
			    String paramName = (String) requestParams.nextElement();
			    parametriRequest.append("\t\t").append(paramName).append(":").append(request.getParameter(paramName)).append("\n");
			}
			break;
		    }
		}
		webActivityLog.trace("\n\tSessionId:\t {}\n\tUTENTE :\t{}\n\tHOST   :\t{}\n\tOGGETTO:\t{}\n\tMETODO :\t{}\n\tPARAMETRI REQUEST:{}",
			new Object[] { sessionId, userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), oggetto, metodo,
				parametriRequest });
	    }
	}
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
		userInfo.put(USERINFO_USERNAME,
			((LoggedUser) user).getResponsabile() + " - [" + ORMHelper.getIdcomune() + "-" + ((LoggedUser) user).getCodiceResponsabile()
				+ "]");
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
	    StringBuffer parametri = new StringBuffer("");
	    if (!(exception instanceof BaseValidationException)) {
		for (Object methodArg : args) {
		    if (methodArg instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) methodArg;
			Enumeration<String> requestParams = (Enumeration<String>) request.getParameterNames();
			while (requestParams.hasMoreElements()) {
			    String paramName = (String) requestParams.nextElement();
			    parametri.append(paramName).append(":").append(request.getParameter(paramName)).append("\n");
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

	String oggetto = jp.getTarget().getClass().getSimpleName().toString();
	String metodo = jp.getSignature().getName();
	Object[] args = jp.getArgs();
	Object bean = jp.getTarget();
	internalTrackException(oggetto, metodo, args, exception, layer, bean, log);
    }
}
