package it.gruppoinit.pal.gp.core.features.protocollazione.eventi;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoMovimentoProtocollato implements IEvent {

    private Integer codiceMovimento;
    private Set<Integer> documenti = new HashSet<Integer>();

    public EventoMovimentoProtocollato(Integer codiceMovimento, Set<Integer> documenti) {

	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("CodiceMovimento non può essere null");
	}
	if (documenti == null) {
	    throw new IllegalArgumentException("documenti non può essere null passare un set vuoto");
	}
	this.codiceMovimento = codiceMovimento;
	if (!documenti.isEmpty()) {
	    this.documenti.addAll(documenti);
	}
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public Set<Integer> getDocumenti() {

	return documenti;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
