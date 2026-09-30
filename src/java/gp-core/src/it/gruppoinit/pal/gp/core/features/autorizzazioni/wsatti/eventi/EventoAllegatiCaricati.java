package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAllegatiCaricati implements IEvent {

    private Integer idAutorizzazione;

    public EventoAllegatiCaricati(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "L'id dell'autorizzazione di cui è stato completato il caricamento degli allegati non può essere nullo");
	}
	this.idAutorizzazione = idAutorizzazione;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }
}
