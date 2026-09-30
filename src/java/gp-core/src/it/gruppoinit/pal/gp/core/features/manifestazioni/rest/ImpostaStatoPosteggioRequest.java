package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImpostaStatoPosteggioRequest {

    @XmlElement(name = "abilitato")
    private Boolean abilitato;

    public Boolean getAbilitato() {

	return abilitato;
    }

    public void setAbilitato(Boolean abilitato) {

	this.abilitato = abilitato;
    }
}
