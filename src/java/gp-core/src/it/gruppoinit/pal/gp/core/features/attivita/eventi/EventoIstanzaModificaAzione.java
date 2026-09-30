package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaModificaAzione implements IEvent {

    private Integer codiceIstanza;

    public EventoIstanzaModificaAzione(Integer codiceIstanza) {

	super();
	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }
}
