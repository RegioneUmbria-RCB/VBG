package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAssenzaGiustificata implements IEvent {

    private Integer idMercatiPresenzeD;

    public EventoAssenzaGiustificata(Integer idMercatiPresenzeD) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }
}
