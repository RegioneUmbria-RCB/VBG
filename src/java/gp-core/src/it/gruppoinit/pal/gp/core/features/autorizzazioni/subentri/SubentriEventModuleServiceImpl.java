package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckEliminazioneSubentro;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoCheckSubentro;
import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;

@Service
public class SubentriEventModuleServiceImpl extends EventBusModule {

    @Autowired
    protected SubentriEventModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoCheckSubentro.class, SottoscrittoreSubentriEventoCheckSubentroServiceImpl.class);
	eventPublisher.add(EventoCheckEliminazioneSubentro.class, SottoscrittoreEventoCheckEliminazioneSubentroServiceImpl.class);
    }
}
