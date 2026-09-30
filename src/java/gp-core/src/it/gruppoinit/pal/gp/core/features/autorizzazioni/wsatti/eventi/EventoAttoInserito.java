package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.AttoInseritoType;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.InserisciDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoAttoInserito implements IEvent {

    private AttoInseritoType atto;

    public EventoAttoInserito(Integer idAutorizzazione, String classifica, InserisciDeterminaResponse response) {

	if (response == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare EventoAttoInserito senza passare una response valida");
	}
	AttoInseritoType atto = new AttoInseritoType();
	atto.setIdAutorizzazione(idAutorizzazione);
	atto.setAnnoProposta(response.getAnno());
	atto.setClassifica(classifica);
	atto.setNumeroProposta(response.getNumero().toString());
	this.atto = atto;
    }

    public AttoInseritoType getAtto() {

	return atto;
    }
}
