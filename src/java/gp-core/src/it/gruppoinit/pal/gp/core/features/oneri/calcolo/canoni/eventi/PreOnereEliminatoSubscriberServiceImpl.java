package it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.calcolo.canoni.IstanzecalcolocanoniOService;
import it.gruppoinit.pal.gp.core.features.oneri.eventi.EventoPreOnereIstanzaEliminato;

@Service("CalcoloCanoniPreOnereEliminatoSubscriberServiceImpl")
public class PreOnereEliminatoSubscriberServiceImpl implements IEventSubscriber<EventoPreOnereIstanzaEliminato> {

    private IstanzecalcolocanoniOService istanzecalcolocanoniOService;

    @Autowired
    public PreOnereEliminatoSubscriberServiceImpl(IstanzecalcolocanoniOService istanzecalcolocanoniOService) {

	this.istanzecalcolocanoniOService = istanzecalcolocanoniOService;
    }

    @Override
    public void onEvent(EventoPreOnereIstanzaEliminato e) {

	this.istanzecalcolocanoniOService.deleteByIdOnere(e.getIstanzeOneriId());
    }
}
