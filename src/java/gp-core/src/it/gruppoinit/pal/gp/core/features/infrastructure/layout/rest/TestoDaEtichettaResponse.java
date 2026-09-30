package it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestoDaEtichettaResponse {

    @XmlElement(name = "etichetta")
    private String etichetta;
    @XmlElement(name = "testo")
    private String testo;

    public TestoDaEtichettaResponse() {

	super();
    }

    public TestoDaEtichettaResponse(String etichetta, String testo) {

	this.etichetta = etichetta;
	this.testo = testo;
    }

    public String getEtichetta() {

	return etichetta;
    }

    public void setEtichetta(String etichetta) {

	this.etichetta = etichetta;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
