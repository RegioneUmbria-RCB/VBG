package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;

@Service
public class SottoscrittoreRMQEventoIstanzaProtocollataServiceImpl implements IEventSubscriber<EventoIstanzaProtocollata> {

    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoIstanzaProtocollata e) throws EventAbortedException {

	codaMessaggiRabbitService.insertNuovaComunicazioneUtente(e.getCodiceIstanza());
    }
}
