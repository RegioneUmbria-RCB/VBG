package it.gruppoinit.pal.gp.core.features.attivita.eventi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoIstanzaScollegata implements IEvent {

    private Integer idAttivita;
    private Date dataElaborazione;

    public EventoIstanzaScollegata(Integer idAttivita, Date dataElaborazione) {

	super();
	if (idAttivita == null) {
	    throw new IllegalArgumentException("Parametri non validi");
	}
	this.idAttivita = idAttivita;
	this.dataElaborazione = dataElaborazione;
    }

    public Date getDataElaborazione() {

	return dataElaborazione;
    }

    public Integer getIdAttivita() {

	return idAttivita;
    }
}
