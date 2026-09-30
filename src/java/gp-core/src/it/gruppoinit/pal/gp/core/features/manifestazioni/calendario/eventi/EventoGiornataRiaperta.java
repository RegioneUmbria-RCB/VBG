package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoGiornataRiaperta implements IEvent {

    private Integer idGiornata;

    public EventoGiornataRiaperta(Integer idGiornata) {

	super();
	this.idGiornata = idGiornata;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }
}
