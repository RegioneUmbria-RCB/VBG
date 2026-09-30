package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoMovimentoDisabilitato implements IEvent {

    private Integer codiceMovimento;

    public EventoMovimentoDisabilitato(Integer codiceMovimento) {

	super();
	this.codiceMovimento = codiceMovimento;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }
}
