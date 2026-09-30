package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class IstanzeSecurityAspect {

    private UserSecurityService userSecurityService;
    private IstanzeService istanzeService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeSecurityAspect.class);

    @SuppressWarnings("unchecked")
    public void doAccessCheck(Object retVal) {

	if (retVal == null) {
	    return;
	}
	Responsabili responsabile = (Responsabili)userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean checkIstanza = true;
	if (retVal instanceof List<?>) {
	    List<Istanze> lista = (List<Istanze>) retVal;
	    List<Istanze> daRimuovere = new ArrayList<Istanze>();
	    for (Istanze istanza : lista) {
		checkIstanza = permessiIstanza(istanza, responsabile);
		if (log.isDebugEnabled()) {
		    log.debug("permessiIstanza: istanza {}, responsabile: {}==> {}",
			    new Object[] { istanza.getId(), responsabile.getId(), Boolean.valueOf(checkIstanza) });
		}
		if (!checkIstanza) {
		    daRimuovere.add(istanza);
		}
	    }
	    if (daRimuovere.size() > 0) {
		lista.removeAll(daRimuovere);
	    }
	}
    }

    public void doViewCheck(JoinPoint jp) {

	Responsabili responsabile = (Responsabili)userSecurityService.getCurrentlyAuthenticatedUserDetails();
	Object[] args = jp.getArgs();
	Integer codiceIstanza = null;
	for (Object arg : args) {
	    if (arg instanceof Integer) {
		codiceIstanza = (Integer) arg;
		break;
	    }
	}
	boolean checkIstanza = true;
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkIstanza = permessiIstanza(istanza, responsabile);
	if (!checkIstanza) {
	    log.error("{}: L'operatore ({}) non ha accesso all'istanza [{}]", new String[] { ORMHelper.getSoftware(), responsabile.getResponsabile(),
		    istanza.getNumeroistanza() });
	    throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile()
		    + ") non ha accesso all'istanza [" + istanza.getNumeroistanza() + "]");
	}
    }

    /**
     * FIXME logica dei permessi dell'stanza
     * 
     * @param istanza
     * @param responsabile
     * @return
     */
    private boolean permessiIstanza(Istanze istanza, Responsabili responsabile) {

	TipoAccessoEnum accesso = istanzeService.checkAccessoIstanza(istanza, responsabile);
	switch (accesso) {
	case NON_CONSENTITO:
	    return false;
	default:
	    // nel caso SOLA_LETTURA, CONSENTITO
	    return true;
	}
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }
}
