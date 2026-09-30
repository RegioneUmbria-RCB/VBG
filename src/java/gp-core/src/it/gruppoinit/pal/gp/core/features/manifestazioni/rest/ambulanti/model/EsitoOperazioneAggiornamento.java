package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EsitoOperazioneAggiornamento {

    @XmlElement
    private boolean esito;
    @XmlElement(name = "messaggio")
    private String messaggio;

    protected EsitoOperazioneAggiornamento() {

	super();
    }

    public EsitoOperazioneAggiornamento(boolean esito, String messaggio) {

	this.esito = esito;
	this.messaggio = messaggio;
    }

    public boolean isEsito() {

	return esito;
    }

    public void setEsito(boolean esito) {

	this.esito = esito;
    }

    public String getMessaggio() {

	return messaggio;
    }
}
