package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoRchiestaAnnullamentoPagamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing.RichiestaAnnullamentoPagamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

public class SottoscrittoreEventoRchiestaAnnullamentoPagamentoServiceImpl implements IEventSubscriber<EventoRchiestaAnnullamentoPagamento> {

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
    public void onEvent(EventoRchiestaAnnullamentoPagamento e) {

	//1. Verifica dei parametri passati
	if (e.getPresenza() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti in merito all'annullamento senza passare il riferimento alla giornata");
	}
	//2. Richiamo il service per lo storno o annullamento
	JsonPresenzaModel model = JsonPresenzaModel.fromMercatipresenzeD(e.getPresenza());
	RichiestaAnnullamentoPagamentoLogger logger = RichiestaAnnullamentoPagamentoLogger.fromModel(model,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	try {
	    logger.log();
	    this.pagamentiService.stornaPresenza(e.getPresenza());
	} catch (Exception ex) {
	    logger.logError(ex.getMessage());
	    throw new RuntimeException(ex);
	}
	logger.logFineMetodo();
    }
}
