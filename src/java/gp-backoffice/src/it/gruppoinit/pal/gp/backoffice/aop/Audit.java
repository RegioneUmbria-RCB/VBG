package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.jms.AuditMessage;
import it.gruppoinit.jms.AuditMessageImpl;
import it.gruppoinit.jms.JmsMessageProducer;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.security.userdetails.User;

/**
 * Semplice classe per testare il funzionamento degli aspetti in spring
 * 
 * @author Riccardo Bocci
 */
@Aspect
public class Audit {

    private static final Logger log = LoggerFactory.getLogger(Audit.class);
    private static String USERINFO_USERNAME = "USER_NAME";
    private static String USERINFO_REMOTEADDRESS = "REMOTE_ADDRESS";
    private static String MESSAGE_URL_VISITED = "URL_VISITED";
    private static String MESSAGE_EXCEPTION_THROWN = "EXCEPTION_THROWN";

    private static enum LAYERS {
	DAO, SERVICE, WEB
    };

    // invio messaggi tramite jms
    private JmsMessageProducer jmsMessageProducer;

    @Autowired
    public void setJmsMessageProducer(JmsMessageProducer jmsMessageProducer) {

	this.jmsMessageProducer = jmsMessageProducer;
    }

    // @Before("execution(void it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl+.insert(..))")
    public void trackInsert(JoinPoint jp) {

	if (log.isDebugEnabled()) {
	    Map<String, String> userInfo = getUserInfo();
	    log.debug("\n\tUTENTE :\t{}\n\tHOST   :\t{}\n\tOGGETTO:\t{}\n\tMETODO :\t{}\n",
		    new Object[] { userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), jp.getTarget().getClass().toString(),
			    jp.getSignature().getName() });
	}
    }

    // @Before("execution(void it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl+.update(..))")
    public void trackUpdate(JoinPoint jp) {

	if (log.isDebugEnabled()) {
	    Map<String, String> userInfo = getUserInfo();
	    log.debug("\n\tUTENTE :\t{}\n\tHOST   :\t{}\n\tOGGETTO:\t{}\n\tMETODO :\t{}\n",
		    new Object[] { userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), jp.getTarget().getClass().toString(),
			    jp.getSignature().getName() });
	}
    }

    /**
     * 
     *
     */
    @SuppressWarnings("unchecked")
    public void trackWebLayer(JoinPoint jp) {

	if (jmsMessageProducer.isInitialized()) {
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
		Map<String, String> userInfo = getUserInfo();
		String oggetto = jp.getTarget().getClass().getSimpleName().toString();
		Object[] args = jp.getArgs();
		StringBuffer parametriRequest = new StringBuffer("");
		for (Object methodArg : args) {
		    if (methodArg instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) methodArg;
			Enumeration<String> requestParams = (Enumeration<String>) request.getParameterNames();
			while (requestParams.hasMoreElements()) {
			    String paramName = (String) requestParams.nextElement();
			    parametriRequest.append(paramName).append(":").append(request.getParameter(paramName)).append("\n");
			}
			break;
		    }
		}
		// invio messaggi tramite jms
		String azione = oggetto + "/" + metodo;
		String messaggio = parametriRequest.toString();
		byte[] messageBytes = null;
		try {
		    messageBytes = messaggio.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		    messageBytes = messaggio.getBytes();
		}
		AuditMessage message = new AuditMessageImpl(userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS),
			MESSAGE_URL_VISITED, azione, messageBytes);
		try {
		    jmsMessageProducer.sendMessage(message);
		} catch (Exception e) {
		    log.error("non ho potuto inviare il messaggio{} a causa di {}", message.toString(), e.getMessage());
		}
		if (log.isDebugEnabled()) {
		    log.debug("\n\tUTENTE :\t{}\n\tHOST   :\t{}\n\tOGGETTO:\t{}\n\tMETODO :\t{}\n\tPARAMETRI REQUEST:{}",
			    new Object[] { userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), oggetto, metodo, parametriRequest });
		}
	    }
	} else {
	    jmsMessageProducer.initialize();
	}
    }

    public void trackWebException(JoinPoint jp, Exception exception) {

	internalTrackException(jp, exception, LAYERS.WEB);
    }

    public void trackServiceException(JoinPoint jp, Exception exception) {

	internalTrackException(jp, exception, LAYERS.SERVICE);
    }

    @SuppressWarnings("unchecked")
    private void internalTrackException(String oggetto, String metodo, Object[] args, Exception exception, LAYERS layer, Object bean) {

	Map<String, String> userInfo = getUserInfo();
	StringBuffer parametri = new StringBuffer("");
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	switch (layer) {
	case SERVICE:
	    if (exception instanceof BaseValidationException) {
		ivs = ((BaseValidationException) exception).getInvalidValues();
	    }
	    break;
	default:
	    break;
	}
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
	if (null != ivs) {
	    for (InvalidValue invalidValue : ivs) {
		parametri.append(invalidValue.toString()).append("\n");
	    }
	}
	// invio messaggi tramite jms
	String azione = oggetto + "/" + metodo;
	parametri.append("\nEccezione lanciata:").append(exception.getMessage()).append("\nStackTrace:");
	StackTraceElement[] els = exception.getStackTrace();
	for (StackTraceElement stackTraceElement : els) {
	    parametri.append("\n").append(stackTraceElement.toString());
	}
	String messaggio = parametri.toString();
	byte[] messageBytes = null;
	try {
	    messageBytes = messaggio.getBytes("UTF-8");
	} catch (UnsupportedEncodingException e) {
	    messageBytes = messaggio.getBytes();
	}
	AuditMessage message = new AuditMessageImpl(userInfo.get(USERINFO_USERNAME), userInfo.get(USERINFO_REMOTEADDRESS), MESSAGE_EXCEPTION_THROWN,
		azione, messageBytes);
	try {
	    jmsMessageProducer.sendMessage(message);
	} catch (JMSException e) {
	    log.error("non ho potuto inviare il messaggio{} a causa di {}", message.toString(), e.getMessage());
	}
    }

    private void internalTrackException(JoinPoint jp, Exception exception, LAYERS layer) {

	String oggetto = jp.getTarget().getClass().getSimpleName().toString();
	String metodo = jp.getSignature().getName();
	Object[] args = jp.getArgs();
	Object bean = jp.getTarget();
	internalTrackException(oggetto, metodo, args, exception, layer, bean);
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
	    userInfo.put(USERINFO_USERNAME, user.getUsername() + "-" + ORMHelper.getIdcomune());
	}
	userInfo.put(USERINFO_REMOTEADDRESS, remoteAddress);
	return userInfo;
    }
}
