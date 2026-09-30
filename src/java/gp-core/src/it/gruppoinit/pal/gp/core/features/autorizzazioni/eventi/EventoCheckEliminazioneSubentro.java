package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoCheckEliminazioneSubentro implements IEvent {

    private Integer idSubentroDaEliminare;

    public EventoCheckEliminazioneSubentro(Integer idSubentroDaEliminare) {

	super();
	this.idSubentroDaEliminare = idSubentroDaEliminare;
    }

    public Integer getIdSubentroDaEliminare() {

	return idSubentroDaEliminare;
    }
}
