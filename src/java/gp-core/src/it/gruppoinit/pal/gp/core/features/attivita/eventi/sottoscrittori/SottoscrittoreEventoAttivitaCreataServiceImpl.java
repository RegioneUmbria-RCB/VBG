package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoAttivitaCreata;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeDAO;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.verticalizzazione.IVerticalizzazioneIAttivitaService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoAttivitaCreataServiceImpl implements IEventSubscriber<EventoAttivitaCreata> {

    @Autowired
    IVerticalizzazioneIAttivitaService verticalizzazioneService;
    @Autowired
    IAttivitaIstanzeDAO attivitaIstanzeDAO;
    @Autowired
    IAttivitaIstanzeService attivitaIstanzeService;

    @Override
    public void onEvent(EventoAttivitaCreata e) {

	this.attivitaIstanzeService.collegaIstanze(e.getAttivitaCreata());
    }
}
