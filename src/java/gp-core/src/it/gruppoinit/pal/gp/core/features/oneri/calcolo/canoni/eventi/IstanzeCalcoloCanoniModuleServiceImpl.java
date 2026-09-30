package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;

@Service
public class IstanzeCalcoloCanoniModuleServiceImpl extends EventBusModule {

    @Autowired
    public IstanzeCalcoloCanoniModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoPreOnereIstanzaEliminato.class, PreOnereEliminatoSubscriberServiceImpl.class);
    }
}
