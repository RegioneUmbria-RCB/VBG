package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaScollegata implements IEvent {

    private Integer codiceIstanzaOrigine;
    private Integer codiceIstanzaDestinazione;

    public EventoIstanzaScollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione) {

	super();
	this.codiceIstanzaOrigine = codiceIstanzaOrigine;
	this.codiceIstanzaDestinazione = codiceIstanzaDestinazione;
	if (codiceIstanzaOrigine == null || codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("Parametri non corretti CodiceIstanzaDestinazione=" + codiceIstanzaDestinazione
		    + ", codiceIstanzaOrigine=" + codiceIstanzaOrigine);
	}
    }

    public Integer getCodiceIstanzaOrigine() {

	return codiceIstanzaOrigine;
    }

    public Integer getCodiceIstanzaDestinazione() {

	return codiceIstanzaDestinazione;
    }
}
