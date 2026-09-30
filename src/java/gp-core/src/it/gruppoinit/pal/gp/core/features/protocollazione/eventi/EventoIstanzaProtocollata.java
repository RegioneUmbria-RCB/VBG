package it.gruppoinit.pal.gp.core.features.protocollazione.eventi;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaProtocollata implements IEvent {

    private Integer codiceIstanza;
    private Set<Integer> documenti = new HashSet<Integer>();

    public EventoIstanzaProtocollata(Integer codiceIstanza, Set<Integer> documenti) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("CodiceIstanza non può essere null");
	}
	if (documenti == null) {
	    throw new IllegalArgumentException("documenti non può essere null passare un set vuoto");
	}
	this.codiceIstanza = codiceIstanza;
	this.documenti.addAll(documenti);
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Set<Integer> getDocumenti() {

	return documenti;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
