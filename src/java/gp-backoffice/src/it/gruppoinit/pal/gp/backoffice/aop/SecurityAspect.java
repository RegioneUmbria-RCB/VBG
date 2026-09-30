package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.annotations.security.SecuredComponent;
import it.gruppoinit.annotations.security.SecuredMethod;

import java.lang.reflect.AnnotatedElement;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(SecurityAspect.class);

    @Before("onSecuredMethod()")
    public void checkSecurityPermissions(JoinPoint jp) {

	// TODO 1. CONTROLLARE CHE L'UTENTE SIA LOGGATO
	// ---------> LANCIARE ECCEZIONE UTENTE NON LOGGATO
	// TODO 2. CONTROLLARE CHE L'UTENTE ABBIA NELLA MAPPA DEI SUOI PERMESSI IL CONTROLLER ED IL METODO CONFIGURATI
	// ---------> PER CONTROLLARE I PERMESSI CERCARE NELLA MAPPA DEI PERMESSI ES.
	// ---------> (String permission = user.getPermissionMap.get("tipibando/list"))
	// ---------> LANCIARE ECCEZIONE UTENTE NON ABILITATO ALLA FUNZIONE
	// TODO 3. EFFETTUO IL LOG VIA JMS DELLA FUNZIONALITA' USATA E DELL'UTENTE CHE LA USA
	if (log.isDebugEnabled()) {
	    // Recupero le informazioni sull'utente loggato
	    // Recupero le informazioni sui metadati e annotazioni del metodo e della classe invocati
	    Class<?> clazz = jp.getTarget().getClass();
	    AnnotatedElement methodAnnotations;
	    AnnotatedElement classAnnotations;
	    try {
		classAnnotations = clazz;
		SecuredComponent securedComponent = classAnnotations.getAnnotation(SecuredComponent.class);
		methodAnnotations = ((MethodSignature) jp.getSignature()).getMethod();
		SecuredMethod securedAnnotation = methodAnnotations.getAnnotation(SecuredMethod.class);
		StringBuffer annotationDump = new StringBuffer("SecuredComponent({})[");
		annotationDump.append("\n\tParentClass:\t{}");
		annotationDump.append("\n\trootElement:\t{}");
		annotationDump.append("\n\tKey        :\t{}");
		annotationDump.append("\n\tHelp Key   :\t{}");
		annotationDump.append("\n\tSecuredMethod({}){\n\t\tKey        :\t{}");
		annotationDump.append("\n\t\tHelp Key   :\t{}");
		annotationDump.append("\n\t\tMethod Type:\t{}");
		annotationDump.append("\n\t\tOrder      :\t{}");
		annotationDump.append("\n\t}\n]");
		log.debug(annotationDump.toString(), new Object[] { clazz.toString(), securedComponent.parentClass(), securedComponent.rootElement(),
			securedComponent.key(), securedComponent.helpKey(), jp.getSignature().getName(), securedAnnotation.key(),
			securedAnnotation.helpKey(), securedAnnotation.methodType(), securedAnnotation.order() });
	    } catch (SecurityException e) {
		e.printStackTrace();
	    }
	}
    }

    @Pointcut("@annotation(it.gruppoinit.annotations.security.SecuredMethod)")
    public void onSecuredMethod() {

    }
}
