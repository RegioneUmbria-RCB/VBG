package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoOrdineIstanzaModificato implements IEvent {

    private Integer idAttivita;
    private Date dataEvento;

    public EventoOrdineIstanzaModificato(Integer idAttivita, Date dataEvento) {

	super();
	this.idAttivita = idAttivita;
	this.dataEvento = dataEvento;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }

    public Date getDataEvento() {

	return dataEvento;
    }
}