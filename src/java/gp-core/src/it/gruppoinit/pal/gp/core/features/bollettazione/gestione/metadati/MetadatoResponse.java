package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class MetadatoResponse {

    @XmlElement(name = "ok")
    private boolean ok;
    @XmlElement(name = "messaggio")
    private String messaggio;

    public MetadatoResponse() {

    }

    public MetadatoResponse(boolean ok) {

	this.ok = ok;
    }

    public MetadatoResponse(boolean ok, String messaggio) {

	this.ok = ok;
	this.messaggio = messaggio;
    }

    public static MetadatoResponse OK() {

	return new MetadatoResponse(true);
    }

    public static MetadatoResponse KO(String messaggio) {

	return new MetadatoResponse(false, messaggio);
    }
}
