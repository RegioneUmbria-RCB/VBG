package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAssenzaGiustificataRevocata implements IEvent {

    private Integer idMercatiPresenzeD;

    public EventoAssenzaGiustificataRevocata(Integer idMercatiPresenzeD) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }
}