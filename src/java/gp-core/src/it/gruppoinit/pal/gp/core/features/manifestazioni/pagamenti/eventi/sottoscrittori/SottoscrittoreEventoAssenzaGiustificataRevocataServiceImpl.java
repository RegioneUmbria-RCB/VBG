package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificataRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.IPagamentiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.CreditoBorsellinoInsufficienteException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing.AssenzaGiustificataRevocataLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreEventoAssenzaGiustificataRevocataServiceImpl implements IEventSubscriber<EventoAssenzaGiustificataRevocata> {

    private IPagamentiService pagamentiService;
    private MercatipresenzeDService mercatipresenzeDService;
    private UserSecurityService userSecurityService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setMercatipresenzeDService(MercatipresenzeDService mercatipresenzeDService) {

	this.mercatipresenzeDService = mercatipresenzeDService;
    }

    @Autowired
    public void setPagamentiService(IPagamentiService pagamentiService) {

	this.pagamentiService = pagamentiService;
    }

    @Override
    public void onEvent(EventoAssenzaGiustificataRevocata e) {

	//1. Verifica dei parametri passati
	if (e.getIdMercatiPresenzeD() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare la gestione dei pagamenti in merito alla revoca di una assenza senza passare il riferimento alla giornata");
	}
	//2. Recupero la giornata
	MercatipresenzeD giornata = mercatipresenzeDService.findById(new PkId(e.getIdMercatiPresenzeD()));
	JsonPresenzaModel model = JsonPresenzaModel.fromMercatipresenzeD(giornata);
	AssenzaGiustificataRevocataLogger logger = AssenzaGiustificataRevocataLogger.fromModel(model,
		userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	logger.log();
	//3. Verifico la presenza di uno spuntista, perchè in quel caso non va fatto nulla sui pagamenti
	if (giornata.isSpuntista()) {
	    logger.logFineMetodo();
	    return;
	}
	// Sto revocando l'assenza giustificata non devo generare il pagamento se non è stata riagganciata l'autorizzazione del concessionario
	if (giornata.getAutorizzazioni() == null || giornata.getAutorizzazioni().getId() == null
		|| giornata.getAutorizzazioni().getId().getCodice() == null) {
	    logger.logFineMetodo();
	    return;
	}
	//4. Invoco il service per la generazione del pagamento
	try {
	    this.pagamentiService.generaPagamentoPresenza(giornata);
	} catch (CreditoBorsellinoInsufficienteException cbie) {
	    logger.logError(cbie.getMessage());
	    throw new RuntimeException(cbie);
	}
	logger.logFineMetodo();
    }
}
