package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class StatoBorsellinoPerAnagrafe {

    protected StatoBorsellinoPerAnagrafe() {

	super();
    }

    public StatoBorsellinoPerAnagrafe(String stato) {

	this();
	this.stato = stato;
    }

    @XmlElement
    private String stato;

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }
}
