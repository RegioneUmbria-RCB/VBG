package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.CreditoBorsellinoInsufficienteException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing.PresenzaInseritaLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreEventoPresenzaInseritaServiceImpl implements IEventSubscriber<EventoPresenzaInserita> {

    private IPagamentiService pagamentiService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setPagamentiService(IPagamentiService pagamentiService) {

	this.pagamentiService = pagamentiService;
    }

    @Override
    public void onEvent(EventoPresenzaInserita e) {

	JsonPresenzaModel model = JsonPresenzaModel.fromMercatipresenzeD(e.getPresenza());
	PresenzaInseritaLogger logger = PresenzaInseritaLogger.fromModel(model,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	try {
	    logger.log();
	    this.pagamentiService.generaPagamentoPresenza(e.getPresenza());
	} catch (CreditoBorsellinoInsufficienteException cbie) {
	    logger.logError(cbie.getMessage());
	    throw new RuntimeException(cbie);
	}
	logger.logFineMetodo();
    }
}
