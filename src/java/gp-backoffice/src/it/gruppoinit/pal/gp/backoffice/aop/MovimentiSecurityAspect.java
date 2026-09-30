package it.gruppoinit.pal.gp.backoffice.aop;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.BatchScadenzario;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.ScadenzarioListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.rules.OperazioniAutomaticheBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class MovimentiSecurityAspect {

    private UserSecurityService userSecurityService;
    private MovimentiService movimentiService;
    private static final Logger log = LoggerFactory.getLogger(MovimentiSecurityAspect.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void doAccessCheck(Object retVal) {

	if (retVal == null) {
	    log.debug("doAccessCheck(): movimento nullo, non eseguo il controllo dei permessi");
	    return;
	}
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	boolean isNotificaStcAutomatica = ((OperazioniAutomaticheBusinessRules) SigeproBusinessRules
		.getClassRules(OperazioniAutomaticheBusinessRules.class)).isNotificaStcAutomatica();
	if (retVal instanceof List<?>) {
	    List<?> lista = (List) retVal;
	    List daRimuovere = new ArrayList();
	    for (Object scadenza : lista) {
		if (scadenza instanceof BatchScadenzario) {
		    // (checkPermessiMovimento) (torna TRUE per inserire/modificare il movimento, false per visualizzare il movimento. Per lo scadenzario false significa non mostrare la scadenza)
		    Movimenti movimento = ((BatchScadenzario) scadenza).getMovimentoDaFare();
		    boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, false);
		    log.debug("doAccessCheck(): movimento {}, responsabile {} ==> {}", new Object[] { movimento.getId(), responsabile.getId(),
			    accesso });
		    if (!accesso) {
			daRimuovere.add(scadenza);
		    }
		}
		if (scadenza instanceof Movimenti) {
		    Movimenti movimento = (Movimenti) scadenza;
		    boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, false);
		    // log.debug("doAccessCheck(): movimento {}, responsabile {} ==> {}", new Object[] { movimento.getId(), responsabile.getId(),
		    //	accesso });
		    ((Movimenti) scadenza).setTransientAccessoNegato(!accesso);
		}
		if (scadenza instanceof ScadenzarioListHelper) {
		    // (checkPermessiMovimento) (torna TRUE per inserire/modificare il movimento, false per visualizzare il movimento. Per lo scadenzario false significa non mostrare la scadenza)
		    Movimenti movimento = new Movimenti();
		    movimento.getId().setCodice(((ScadenzarioListHelper) scadenza).getId().intValue());
		    if (((ScadenzarioListHelper) scadenza).getCodiceamministrazione() != null) {
			Amministrazioni amm = new Amministrazioni();
			amm.getId().setCodice(((ScadenzarioListHelper) scadenza).getCodiceamministrazione());
			movimento.setAmministrazioni(amm);
		    }
		    if (((ScadenzarioListHelper) scadenza).getCodiceistanza() != null) {
			Istanze istanza = new Istanze();
			istanza.getId().setCodice(((ScadenzarioListHelper) scadenza).getCodiceistanza());
			movimento.setIstanza(istanza);
		    }
		    if (StringUtils.isNotBlank(((ScadenzarioListHelper) scadenza).getTipomovimentodafare())) {
			Tipimovimento tm = new Tipimovimento();
			tm.setId(new TipimovimentoId(((ScadenzarioListHelper) scadenza).getTipomovimentodafare()));
			movimento.setTipomovimento(tm);
		    }
		    boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, false);
		    log.debug("doAccessCheck(): movimento {}, responsabile {} ==> {}", new Object[] { movimento.getId(), responsabile.getId(),
			    accesso });
		    if (!accesso) {
			daRimuovere.add(scadenza);
		    }
		}
		if (scadenza instanceof MovimentiDTO) {
		    Movimenti movimento = new Movimenti();
		    movimento.getId().setCodice(((MovimentiDTO) scadenza).getCodicemovimento().intValue());
		    if (((MovimentiDTO) scadenza).getAmministrazionicodice() != null) {
			Amministrazioni amm = new Amministrazioni();
			amm.getId().setCodice(((MovimentiDTO) scadenza).getAmministrazionicodice());
			movimento.setAmministrazioni(amm);
		    }
		    if (((MovimentiDTO) scadenza).getCodiceistanza() != null) {
			Istanze istanza = new Istanze();
			istanza.getId().setCodice(((MovimentiDTO) scadenza).getCodiceistanza());
			movimento.setIstanza(istanza);
		    }
		    if (StringUtils.isNotBlank(((MovimentiDTO) scadenza).getTipomovimento())) {
			Tipimovimento tm = new Tipimovimento();
			tm.setId(new TipimovimentoId(((MovimentiDTO) scadenza).getTipomovimento()));
			movimento.setTipomovimento(tm);
		    }
		    boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, false);
		    log.debug("doAccessCheck(): movimento {}, responsabile {} ==> {}", new Object[] { movimento.getId(), responsabile.getId(),
			    accesso });
		    if (!accesso) {
			daRimuovere.add(scadenza);
		    }
		}
	    }
	    if (!daRimuovere.isEmpty()) {
		lista.removeAll(daRimuovere);
	    }
	}
	if (retVal instanceof Movimenti && !isNotificaStcAutomatica) {
	    Movimenti movimento = (Movimenti) retVal;
	    boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, false);
	    if (!accesso) {
		log.error("L'operatore [{}] non ha accesso al movimento [{}] del modulo [{}]",
			new Object[] { responsabile.getResponsabile(), movimento.getId(), ORMHelper.getSoftware() });	
		throw new SecurityException("L'operatore [" + responsabile.getResponsabile() + "] non ha accesso al movimento [" + movimento.getId() +
					    "] del modulo [" + ORMHelper.getSoftware() + "]");
	    }
	}
    }

    public void doUpdateDataCheck(JoinPoint jp) {

	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	Object[] args = jp.getArgs();
	for (Object arg : args) {
	    // Siamo nel caso di insert/delete/update 
	    if (arg instanceof Movimenti) {
		if (EntityUtils.getNestedProperty(arg, "id.codice") != null) {
		    //.. nella insert posso in realtà eseguire un'update o una vera insert. 
		    // Posso controllare solamente quelli che costituiscono un update 
		    Movimenti copy = movimentiService.findById(((Movimenti) arg).getId());
		    boolean accesso = movimentiService.checkPermessiMovimento(copy, responsabile, true);
		    if (!accesso) {
			log.error("L'operatore [{}] non può eseguire il movimento [{}] con tipo accesso [{}]",
				new Object[] { responsabile.getResponsabile(), copy.getTipomovimento().getId().getTipomovimento(), accesso });
			throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile()
				+ ") non può eseguire il movimento [" + copy.getTipomovimento().getId().getTipomovimento()
				+ "] con il tipo di accesso [" + accesso + "]");
		    }
		}
		break; // valuto solamente il parametro che rappresenta il movimento da inserire, cancellare, modificare
	    }
	    //siamo nel caso di StcService.notificaAttivita(Integer codiceMovimento,....)
	    if (arg instanceof Integer) {
		PkId id = new PkId((Integer) arg);
		Movimenti movimento = movimentiService.findById(id);
		boolean accesso = movimentiService.checkPermessiMovimento(movimento, responsabile, true);
		if (!accesso) {
		    log.error("L'operatore [{}] non può eseguire il movimento [{}] con tipo accesso [{}]",
			    new Object[] { responsabile.getResponsabile(), movimento.getTipomovimento().getId().getTipomovimento(), accesso });
		    throw new SecurityException(ORMHelper.getSoftware() + ": L'operatore (" + responsabile.getResponsabile() +
						") non può eseguire il movimento [" + movimento.getTipomovimento().getId().getTipomovimento() +
						"] con il tipo di accesso [" + accesso + "]");
		}
		break; // valuto solamente il primo parametro
	    }
	}
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }
}
