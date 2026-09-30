package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoPresenzaRevocata implements IEvent {

    private Integer idMercatiPresenzeD;
    private Autorizzazioni autorizzazione;
    private boolean revocaASpuntista;

    public EventoPresenzaRevocata(Integer idMercatiPresenzeD, Autorizzazioni autorizzazione, boolean revocaASpuntista) {

	super();
	this.idMercatiPresenzeD = idMercatiPresenzeD;
	this.autorizzazione = autorizzazione;
	this.revocaASpuntista = revocaASpuntista;
    }

    public Integer getIdMercatiPresenzeD() {

	return idMercatiPresenzeD;
    }

    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public boolean isRevocaASpuntista() {

	return revocaASpuntista;
    }
}
