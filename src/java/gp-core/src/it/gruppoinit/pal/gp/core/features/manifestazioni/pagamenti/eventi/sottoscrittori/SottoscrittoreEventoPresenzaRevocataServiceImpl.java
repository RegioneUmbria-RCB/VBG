package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing.PresenzaRevocataLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreEventoPresenzaRevocataServiceImpl implements IEventSubscriber<EventoPresenzaRevocata> {

    private IPagamentiService pagamentiService;
    private MercatipresenzeDService mercatipresenzeDService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setPagamentiService(IPagamentiService pagamentiService) {

	this.pagamentiService = pagamentiService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Override
    public void onEvent(EventoPresenzaRevocata e) {

	//1. Potrebbe essere stata cancellata una riga di una giornata in cui il posteggio è libero
	if (e.getAutorizzazione() == null) {
	    return;
	}
	//2. Verifica dei parametri passati
	if (e.getIdMercatiPresenzeD() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti in merito alla revoca di una presenza senza passare il riferimento alla giornata");
	}
	//3. Recupero la giornata
	MercatipresenzeD giornata = this.mercatipresenzeDService.findById(new PkId(e.getIdMercatiPresenzeD()));
	//4. Richiamo il service per lo storno o annullamento
	JsonPresenzaModel model = JsonPresenzaModel.fromMercatipresenzeD(giornata);
	PresenzaRevocataLogger logger = PresenzaRevocataLogger.fromModel(model,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	try {
	    logger.log();
	    this.pagamentiService.stornaPresenza(giornata, e.getAutorizzazione());
	} catch (Exception ex) {
	    logger.logError(ex.getMessage());
	    throw new RuntimeException(ex);
	}
	logger.logFineMetodo();
    }
}