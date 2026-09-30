package it.gruppoinit.pal.gp.areariservata.aop;

import it.gruppoinit.pal.gp.core.service.MessagesService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class DomandeSecurityAspect {

    private static final Logger log = LoggerFactory.getLogger(DomandeSecurityAspect.class);
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private MessagesService messagesService;

    public void doCheck(JoinPoint jp) {

	//
	//	log.debug("doCheck");
	//	UserDetails loggedUser = userSecurityService.getCurrentlyAuthenticatedUser();
	//	String codiceAnagrafe = loggedUser.getUsername();
	//	Object[] args = jp.getArgs();
	//	Integer codiceDomanda = (Integer) args[args.length - 1];
	//	FoArjDomande domanda = foArjDomandeService.findById(new PkId(codiceDomanda));
	//	if (domanda != null) {
	//	    if (!domanda.getAnagrafe().getId().getCodice().toString().equals(codiceAnagrafe)) {
	//		log.error("doCkeck: KO l'utente con codiceAnagrafe:{} non ha accesso alla domanda con id:{}", codiceAnagrafe, codiceDomanda);
	//		throw new AuthorizationServiceException(messagesService.getMessage("error.accesso-negato", null));
	//	    }
	//	} else {
	//	    log.error("doCkeck: KO l'utente con codiceAnagrafe:{} ha provato ad accedere alla domanda con id:{} ma la domanda non esiste.",
	//		    codiceAnagrafe, codiceDomanda);
	//	    throw new AuthorizationServiceException(messagesService.getMessage("error.accesso-negato", null));
	//	}
	//	log.info("doCheck: OK, utente loggato codiceAnagrafe={}, domanda id={}", codiceAnagrafe, codiceDomanda);
    }
}
