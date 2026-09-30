package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class EtichettaApp {

    @XmlElement
    private String chiave;
    @XmlElement
    private String valore;

    public EtichettaApp() {

	super();
    }

    public EtichettaApp(String chiave, String valore) {

	this();
	this.chiave = chiave;
	this.valore = valore;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @XmlTransient
    public EtichettaApp rimuoviPrefisso(String prefissoEtichette) {

	if (this.chiave == null) {
	    return this;
	}
	this.setChiave(this.chiave.replaceFirst(prefissoEtichette, ""));
	return this;
    }
}
