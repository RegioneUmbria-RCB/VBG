package it.gruppoinit.pal.gp.core.features.anagrafica.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoEmailAnagrafeAggiornata implements IEvent {

    private Integer codiceAnagrafe;
    private String mail;
    private String pec;

    public EventoEmailAnagrafeAggiornata(Integer codiceAnagrafe, String mail, String pec) {

	this.codiceAnagrafe = codiceAnagrafe;
	this.mail = mail;
	this.pec = pec;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public String getMail() {

	return mail;
    }

    public String getPec() {

	return pec;
    }
}
