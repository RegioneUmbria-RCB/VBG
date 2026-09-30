package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;

@Service
public class SottoscrittoreEventoStatoIstanzaModificatoServiceImpl implements IEventSubscriber<EventoStatoIstanzaModificato> {

    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoStatoIstanzaModificato e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoStatoIstanzaModificato non riporta nessuna informazione");
	}
	if (e.getCodiceIstanza() == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoStatoIstanzaModificato non riporta il codice istanza");
	}
	codaMessaggiRabbitService.insertCambioStato(e.getCodiceIstanza());
    }
}
