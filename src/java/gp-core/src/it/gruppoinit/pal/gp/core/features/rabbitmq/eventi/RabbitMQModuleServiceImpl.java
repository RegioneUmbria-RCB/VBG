package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaCancellata;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.EventoSoggettiIstanzaAggiornati;
import it.gruppoinit.pal.gp.core.features.protocollazione.eventi.EventoIstanzaProtocollata;

@Service
public class RabbitMQModuleServiceImpl extends EventBusModule {

    @Autowired
    public RabbitMQModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoIstanzaProtocollata.class, SottoscrittoreRMQEventoIstanzaProtocollataServiceImpl.class);
	eventPublisher.add(EventoInserimentoIstanza.class, SottoscrittoreRMQEventoRicezionePraticaServiceImpl.class);
	eventPublisher.add(EventoSoggettiIstanzaAggiornati.class, SottoscrittoreRMQEventoSoggettiIstanzaAggiornatiServiceImpl.class);
	eventPublisher.add(EventoIstanzaCancellata.class, SottoscrittoreRMQEventoIstanzaCancellataServiceImpl.class);
    }
}
