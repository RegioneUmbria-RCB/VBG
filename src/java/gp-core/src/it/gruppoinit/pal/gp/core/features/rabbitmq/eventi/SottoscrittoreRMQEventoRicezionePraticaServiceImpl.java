package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.rabbitmq.CodaMessaggiRabbitService;

@Service
public class SottoscrittoreRMQEventoRicezionePraticaServiceImpl implements IEventSubscriber<EventoInserimentoIstanza> {

    @Autowired
    private CodaMessaggiRabbitService codaMessaggiRabbitService;

    @Override
    public void onEvent(EventoInserimentoIstanza e) throws EventAbortedException {

	codaMessaggiRabbitService.insertNotificaPraticaNuova(e.getCodiceIstanza());
    }
}
