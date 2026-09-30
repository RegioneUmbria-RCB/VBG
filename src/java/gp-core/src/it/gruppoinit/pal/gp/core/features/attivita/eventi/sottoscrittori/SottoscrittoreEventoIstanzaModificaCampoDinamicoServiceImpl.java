package it.gruppoinit.pal.gp.core.features.attivita.eventi.sottoscrittori;

import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.attivita.eventi.EventoIstanzaModificaCampoDinamico;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoIstanzaModificaCampoDinamicoServiceImpl implements IEventSubscriber<EventoIstanzaModificaCampoDinamico> {

    @Override
    public void onEvent(EventoIstanzaModificaCampoDinamico e) {

	// predisposta classe ma allo stato attuale non è implementata la modifica di un singolo campo ma utilizza il metodo di modifica di una scheda 
	// dinamica
    }
}
