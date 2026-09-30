package it.gruppoinit.pal.gp.core.features.movimenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoErroreInCancellazioneMovimentodaIstanzaCollegata implements IEvent {

    private Integer codiceIstanzaOrigine;
    private Integer codiceIstanzaDestinazione;
    private Integer codiceMovimento;
    private String messaggio;

    public EventoErroreInCancellazioneMovimentodaIstanzaCollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione, Integer codiceMovimento,
	    String messaggio) {

	if (codiceIstanzaOrigine == null) {
	    throw new IllegalArgumentException("codiceIstanzaOrigine non può essere null");
	}
	if (codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("codiceIstanzaDestinazione non può essere null");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("CodiceMovimento non può essere null");
	}
	this.codiceIstanzaOrigine = codiceIstanzaOrigine;
	this.codiceIstanzaDestinazione = codiceIstanzaDestinazione;
	this.codiceMovimento = codiceMovimento;
	this.messaggio = messaggio;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public Integer getCodiceIstanzaDestinazione() {

	return codiceIstanzaDestinazione;
    }

    public Integer getCodiceIstanzaOrigine() {

	return codiceIstanzaOrigine;
    }
}
