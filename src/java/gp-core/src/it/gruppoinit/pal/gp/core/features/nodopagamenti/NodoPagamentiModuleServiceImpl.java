package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.EventBusModule;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.sottoscrittori.SegnaPosizioneDaVerificareServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoOnereIstanzaInserito;

@Service
public class NodoPagamentiModuleServiceImpl extends EventBusModule {

    @Autowired
    public NodoPagamentiModuleServiceImpl(IEventPublisher eventPublisher) {

	super(eventPublisher);
    }

    @Override
    public void load(IEventPublisher eventPublisher) {

	eventPublisher.add(EventoOnereIstanzaInserito.class, SegnaPosizioneDaVerificareServiceImpl.class);
    }
}
