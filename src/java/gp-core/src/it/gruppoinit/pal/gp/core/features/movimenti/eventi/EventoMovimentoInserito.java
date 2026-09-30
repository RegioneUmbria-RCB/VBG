package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoMovimentoInserito implements IEvent {

    private Integer codiceMovimento;
    private boolean scadenza;

    public EventoMovimentoInserito(Integer codiceMovimento, boolean scadenza) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("codiceMovimento non può essere null");
	}
	this.codiceMovimento = codiceMovimento;
	this.scadenza = scadenza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public boolean isScadenza() {

	return scadenza;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
