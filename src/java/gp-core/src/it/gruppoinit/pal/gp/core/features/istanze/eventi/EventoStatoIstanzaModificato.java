package it.gruppoinit.pal.gp.core.features.istanze.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoStatoIstanzaModificato implements IEvent {

    private Integer codiceIstanza;

    public EventoStatoIstanzaModificato(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("Il codiceIstanza non può essere nullo");
	}
	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }
}
