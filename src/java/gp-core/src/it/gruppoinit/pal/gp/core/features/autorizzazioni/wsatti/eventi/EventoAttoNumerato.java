package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.AttoNumeratoType;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAttoNumerato implements IEvent {

    private AttoNumeratoType atto;

    public static EventoAttoNumerato fromNumeraDeterminaResponse(NumeraDeterminaResponse response) {

	if (response == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare EventoAttoNumerato.fromNumeraDeterminaResponse senza passare una response valida");
	}
	AttoNumeratoType atto = new AttoNumeratoType();
	atto.setNumeroAtto(response.getNumero().toString());
	atto.setDataAtto(atto.getDataAtto());
	return new EventoAttoNumerato(atto);
    }

    private EventoAttoNumerato(AttoNumeratoType atto) {

	this.atto = atto;
    }

    public AttoNumeratoType getAtto() {

	return atto;
    }
}
