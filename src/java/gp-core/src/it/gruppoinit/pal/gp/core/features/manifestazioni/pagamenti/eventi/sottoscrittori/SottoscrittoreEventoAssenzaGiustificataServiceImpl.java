package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing.AssenzaGiustificataLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreEventoAssenzaGiustificataServiceImpl implements IEventSubscriber<EventoAssenzaGiustificata> {

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
    public void onEvent(EventoAssenzaGiustificata e) {

	//1. Verifica dei parametri passati
	if (e.getIdMercatiPresenzeD() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti senza passare il riferimento alla giornata dell'assenza giustificata");
	}
	//2. Recupero la giornata
	MercatipresenzeD giornata = mercatipresenzeDService.findById(new PkId(e.getIdMercatiPresenzeD()));
	JsonPresenzaModel model = JsonPresenzaModel.fromMercatipresenzeD(giornata);
	AssenzaGiustificataLogger logger = AssenzaGiustificataLogger.fromModel(model,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	logger.log();
	//3. Verifico la presenza di uno spuntista, perchè in quel caso non va stornato nulla in quanto
	//   eventuali pagamenti sono riferiti allo spuntista e non al concessionario
	if (giornata.isSpuntista()) {
	    logger.logFineMetodo();
	    return;
	}
	try {
	    //4. Richiamo il service per lo storno/annullamento
	    this.pagamentiService.stornaPresenza(giornata, giornata.getAutorizzazioneConcessionarioAssente());
	} catch (Exception ex) {
	    logger.logError(ex.getMessage());
	    throw new RuntimeException(ex);
	}
	logger.logFineMetodo();
    }
}
