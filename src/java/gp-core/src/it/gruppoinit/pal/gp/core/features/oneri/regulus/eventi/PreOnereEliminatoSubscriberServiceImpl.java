package it.gruppoinit.pal.gp.core.features.oneri.regulus.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;
import it.gruppoinit.pal.gp.core.features.oneri.regulus.IstanzeOneriRegulusService;

@Service("OneriRegulusPreOnereEliminatoSubscriberServiceImpl")
public class PreOnereEliminatoSubscriberServiceImpl implements IEventSubscriber<EventoPreOnereIstanzaEliminato> {

    private IstanzeOneriRegulusService istanzeOneriRegulusService;

    @Autowired
    public PreOnereEliminatoSubscriberServiceImpl(IstanzeOneriRegulusService istanzeOneriRegulusService) {

	this.istanzeOneriRegulusService = istanzeOneriRegulusService;
    }

    @Override
    public void onEvent(EventoPreOnereIstanzaEliminato e) {

	this.istanzeOneriRegulusService.deleteByIdOnere(e.getIstanzeOneriId());
    }
}
