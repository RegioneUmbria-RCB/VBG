package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaAggiornato;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaInserito;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;

@Service
public class PosizioniDebitorieBatchModuleServiceImpl extends EventBusModule {

    @Autowired
    public PosizioniDebitorieBatchModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoOnereIstanzaInserito.class, OnereInseritoSubscriberServiceImpl.class);
	eventPublisher.add(EventoOnereIstanzaAggiornato.class, OnereAggiornatoSubscriberServiceImpl.class);
	eventPublisher.add(EventoPreOnereIstanzaEliminato.class, PreOnereEliminatoSubscriberServiceImpl.class);
    }
}
