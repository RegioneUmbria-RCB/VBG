package it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalvataggioTestoRequest {

    @XmlElement(name = "codiceTesto")
    private String codiceTesto;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "testo")
    private String testo;

    public String getCodiceTesto() {

	return codiceTesto;
    }

    public void setCodiceTesto(String codiceTesto) {

	this.codiceTesto = codiceTesto;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }
}
