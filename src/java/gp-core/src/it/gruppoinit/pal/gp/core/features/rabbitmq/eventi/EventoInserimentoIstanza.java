package it.gruppoinit.pal.gp.core.features.rabbitmq.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoInserimentoIstanza implements IEvent {

    private Integer codiceIstanza;

    public EventoInserimentoIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("CodiceIstanza non può essere null");
	}
	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }
}
