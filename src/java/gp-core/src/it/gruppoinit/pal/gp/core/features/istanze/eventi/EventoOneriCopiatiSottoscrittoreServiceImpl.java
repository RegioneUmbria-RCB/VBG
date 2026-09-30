package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oneri.EventoOneriCopiati;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoOneriCopiatiSottoscrittoreServiceImpl implements IEventSubscriber<EventoOneriCopiati> {

    @Autowired
    private IstanzeeventiService istanzeeventiService;

    @Override
    public void onEvent(EventoOneriCopiati e) {

	istanzeeventiService.insertEventoOnereCopiato(e.getCodiceIstanzaOrigine(), e.getCodiceIstanzaDestinazione(), e.getIstanzeOneriCopiati());
    }
}
