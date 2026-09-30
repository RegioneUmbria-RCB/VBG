package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAutorizzazioneSbloccata implements IEvent {

    private Integer idAutorizzazione;

    public EventoAutorizzazioneSbloccata(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException("L'id dell'autorizzazione sbloccata non può essere nullo");
	}
	this.idAutorizzazione = idAutorizzazione;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }
}
