package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoCheckModificaOccupante implements IEvent {

    private Integer idAutOConc;
    private Integer nuovoOccupante;

    public EventoCheckModificaOccupante(Integer idAutOConc, Integer nuovoOccupante) {

	super();
	this.idAutOConc = idAutOConc;
	this.nuovoOccupante = nuovoOccupante;
    }

    public Integer getIdAutOConc() {

	return idAutOConc;
    }

    public Integer getNuovoOccupante() {

	return nuovoOccupante;
    }
}
