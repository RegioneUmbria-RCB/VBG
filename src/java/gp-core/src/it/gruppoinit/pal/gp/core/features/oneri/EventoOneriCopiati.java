package it.gruppoinit.pal.gp.core.features.oneri;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

import java.util.List;

public class EventoOneriCopiati implements IEvent {

    private Integer codiceIstanzaOrigine;
    private Integer codiceIstanzaDestinazione;
    private List<Integer> istanzeOneriCopiati;

    public EventoOneriCopiati(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione, List<Integer> istanzeOneriCopiati) {

	super();
	this.codiceIstanzaOrigine = codiceIstanzaOrigine;
	this.codiceIstanzaDestinazione = codiceIstanzaDestinazione;
	this.istanzeOneriCopiati = istanzeOneriCopiati;
	if (codiceIstanzaOrigine == null || codiceIstanzaDestinazione == null) {
	    throw new RuntimeException("Parametri non corretti CodiceIstanzaDestinazione=" + codiceIstanzaDestinazione + ", codiceIstanzaOrigine="
		    + codiceIstanzaOrigine);
	}
    }

    public Integer getCodiceIstanzaOrigine() {

	return codiceIstanzaOrigine;
    }

    public Integer getCodiceIstanzaDestinazione() {

	return codiceIstanzaDestinazione;
    }

    public List<Integer> getIstanzeOneriCopiati() {

	return istanzeOneriCopiati;
    }
}
