package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaModificaCampoDinamico implements IEvent {

    private Integer codiceIstanza;
    private Integer codiceScheda;

    public EventoIstanzaModificaCampoDinamico(Integer codiceIstanza, Integer codiceScheda) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.codiceScheda = codiceScheda;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Integer getCodiceScheda() {

	return codiceScheda;
    }
}
