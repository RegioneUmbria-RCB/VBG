package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.movimenti.FaseMovimentoEnum;

public class EventoMovimentoAggiornato implements IEvent {

    private Integer codiceMovimento;
    private FaseMovimentoEnum fase;

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public FaseMovimentoEnum getFase() {

	return fase;
    }

    public EventoMovimentoAggiornato(Integer codiceMovimento, FaseMovimentoEnum fase) {

	super();
	this.codiceMovimento = codiceMovimento;
	this.fase = fase;
    }
}
