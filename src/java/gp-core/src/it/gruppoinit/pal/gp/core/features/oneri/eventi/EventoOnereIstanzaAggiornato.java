package it.gruppoinit.pal.gp.core.features.oneri.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoOnereIstanzaAggiornato implements IEvent {

    private Integer idOnere;

    public EventoOnereIstanzaAggiornato(Integer idOnere) {

	if (idOnere == null) {
	    throw new IllegalArgumentException("L'id dell'onere non può essere nullo");
	}
	if (idOnere < 0) {
	    throw new IllegalArgumentException("L'id dell'onere non può essere minore di 0");
	}
	this.idOnere = idOnere;
    }

    public Integer getIdOnere() {

	return idOnere;
    }
}
