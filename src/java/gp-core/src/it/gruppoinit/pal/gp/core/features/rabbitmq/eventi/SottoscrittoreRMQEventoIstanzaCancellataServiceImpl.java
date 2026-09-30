package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCancellata;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;

@Service
public class SottoscrittoreRMQEventoIstanzaCancellataServiceImpl implements IEventSubscriber<EventoIstanzaCancellata> {

    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoIstanzaCancellata e) throws EventAbortedException {

	if (e == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoIstanzaCancellata non riporta nessuna informazione");
	}
	if (e.getCodiceIstanza() == null) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoIstanzaCancellata non riporta il codice istanza");
	}
	if (StringUtils.isBlank(e.getUuidIstanza())) {
	    throw new RuntimeException("Impossibile proseguire in quanto l'evento EventoIstanzaCancellata non riporta il uuidIstanza");
	}
	codaMessaggiRabbitService.insertMessaggioPraticaCancellata(e.getUuidIstanza());
    }
}
