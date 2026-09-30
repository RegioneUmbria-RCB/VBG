package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;

public class Esito {

    @XmlElement(name = "ok")
    private boolean ok;
    @XmlElement(name = "messaggio")
    private String messaggio;

    public boolean isOk() {

	return ok;
    }

    public void setOk(boolean ok) {

	this.ok = ok;
    }

    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    @Override
    public String toString() {

	return "Esito: " + (this.isOk() ? "OK" : "KO") + ", Messaggio: " + this.getMessaggio();
    }
}
