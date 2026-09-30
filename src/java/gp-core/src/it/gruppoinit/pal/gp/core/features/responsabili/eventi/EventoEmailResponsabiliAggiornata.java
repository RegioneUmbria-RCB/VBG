package it.gruppoinit.pal.gp.core.features.responsabili.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoEmailResponsabiliAggiornata implements IEvent {

    private Integer codiceResponsabile;
    private String mail;

    public EventoEmailResponsabiliAggiornata(Integer codiceResponsabile, String mail) {

	this.codiceResponsabile = codiceResponsabile;
	this.mail = mail;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public String getMail() {

	return mail;
    }
}
