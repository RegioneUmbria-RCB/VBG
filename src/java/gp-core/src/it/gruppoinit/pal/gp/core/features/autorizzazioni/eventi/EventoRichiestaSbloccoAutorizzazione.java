package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoRichiestaSbloccoAutorizzazione implements IEvent {

    private Integer idAutorizzazione;

    public EventoRichiestaSbloccoAutorizzazione(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException("L'id dell'autorizzazione da sbloccare non può essere nullo");
	}
	this.idAutorizzazione = idAutorizzazione;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }
}
