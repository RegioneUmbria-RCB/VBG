package it.gruppoinit.pal.gp.backoffice.aop;

import org.apache.commons.lang.BooleanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

/**
 * L'aspetto viene applicato su tutti i metodi insert/update/delete dei service, ad eccezione dei metodi del service
 * ConfigurazioneUtenteService. Se l'utente correntemente loggato é configurato come utente readonly allora quando viene
 * intercettato il pointcut viene rilanciata una eccezione di sicurezza.
 * 
 * @author riccardob
 * 
 */
@Aspect
public class OperatoreReadOnlySecurityAspect {

    private UserSecurityService userSecurityService;
    private static final Logger log = LoggerFactory.getLogger(OperatoreReadOnlySecurityAspect.class);

    /**
     * Se esiste un utente correntemente loggato allora se è readOnly=true viene rilanciata un'eccezione di sicurezza.
     * 
     * @param jp
     */
    public void checkUserReadOnly(JoinPoint jp) {

	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (responsabile == null) {
	    log.error("L'Operazione ({}) viene eseguita senza utente", getMessage(jp));
	    return;
	}
	if (BooleanUtils.isTrue(responsabile.getReadonly())) {
	    log.error("L'operatore ({}) è un utente di sola lettura  e non può effettuare modifiche sui dati", responsabile.getResponsabile());
	    throw new SecurityException(
		    "L'operatore (" + responsabile.getResponsabile() + ") è di sola lettura e non può effettuare modifiche sui dati");
	}
    }

    private String getMessage(JoinPoint jp) {

	StringBuilder annotationDump = new StringBuilder("classe(");
	if (jp != null) {
	    Class<?> clazz = jp.getTarget().getClass();
	    String metodo = jp.getSignature().getName();
	    Object[] args = jp.getArgs();
	    annotationDump.append(clazz.toString()).append(")");
	    annotationDump.append("\n\tmetodo invocato        :\t");
	    annotationDump.append(metodo);
	    annotationDump.append("\n\tparametri passati        :\t");
	    if (args != null && args.length > 0) {
		for (Object arg : args) {
		    if (arg != null) {
			annotationDump.append(arg.toString()).append(",");
		    }
		}
	    }
	    annotationDump.append("\n\t}\n]");
	}
	return annotationDump.toString();
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }
}
