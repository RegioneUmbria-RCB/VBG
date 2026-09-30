package it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;
import it.gruppoinit.pal.gp.core.features.oneri.posizionidebitorie.batch.IstanzeoneriPosdebBatchService;

@Service("PosDebBatchPreOnereEliminatoSubscriberServiceImpl")
public class PreOnereEliminatoSubscriberServiceImpl implements IEventSubscriber<EventoPreOnereIstanzaEliminato> {

    private IstanzeoneriPosdebBatchService service;

    @Autowired
    public PreOnereEliminatoSubscriberServiceImpl(IstanzeoneriPosdebBatchService service) {

	this.service = service;
    }

    @Override
    public void onEvent(EventoPreOnereIstanzaEliminato e) {

	this.service.eliminaDaIdOnere(e.getIstanzeOneriId());
    }
}
