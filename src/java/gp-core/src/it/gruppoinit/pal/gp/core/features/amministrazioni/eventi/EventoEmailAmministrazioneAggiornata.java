package it.gruppoinit.pal.gp.core.features.amministrazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoEmailAmministrazioneAggiornata implements IEvent {

    private Integer codiceAmministrazioni;
    private String mail;
    private String pec;

    public EventoEmailAmministrazioneAggiornata(Integer codiceAmministrazioni, String mail, String pec) {

	this.codiceAmministrazioni = codiceAmministrazioni;
	this.mail = mail;
	this.pec = pec;
    }

    public Integer getCodiceAmministrazioni() {

	return codiceAmministrazioni;
    }

    public String getMail() {

	return mail;
    }

    public String getPec() {

	return pec;
    }
}
