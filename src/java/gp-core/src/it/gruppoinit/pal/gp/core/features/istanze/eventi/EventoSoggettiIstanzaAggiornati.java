package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoSoggettiIstanzaAggiornati implements IEvent {

    private Integer codiceIstanza;

    public EventoSoggettiIstanzaAggiornati(Integer codiceIstanza) {

	super();
	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Il codiceIstanza non può essere nullo");
	}
	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }
}
