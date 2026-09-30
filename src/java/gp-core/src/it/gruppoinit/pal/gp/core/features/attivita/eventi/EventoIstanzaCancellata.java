package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaCancellata implements IEvent {

    private Integer codiceIstanza;
    private String uuidIstanza;

    public EventoIstanzaCancellata(Integer codiceIstanza, String uuidIstanza) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.uuidIstanza = uuidIstanza;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public String getUuidIstanza() {

	return uuidIstanza;
    }
}
