package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAttivitaCreata implements IEvent {

    private IAttivita attivitaCreata;

    public EventoAttivitaCreata(IAttivita attivitaCreata) {

	this.attivitaCreata = attivitaCreata;
    }

    public IAttivita getAttivitaCreata() {

	return attivitaCreata;
    }
}
