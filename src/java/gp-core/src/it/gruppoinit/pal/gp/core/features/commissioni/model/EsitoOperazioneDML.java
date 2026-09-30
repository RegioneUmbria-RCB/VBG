package it.gruppoinit.pal.gp.core.features.commissioni.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoOperazioneDML {

    public EsitoOperazioneDML() {

	super();
	this.esito = true;
    }

    public EsitoOperazioneDML(boolean esito, String errore) {

	this();
	this.esito = esito;
	this.errore = errore;
    }

    @XmlElement
    private boolean esito;
    @XmlElement
    private String errore;

    public boolean isEsito() {

	return esito;
    }

    public void setEsito(boolean esito) {

	this.esito = esito;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }
}
