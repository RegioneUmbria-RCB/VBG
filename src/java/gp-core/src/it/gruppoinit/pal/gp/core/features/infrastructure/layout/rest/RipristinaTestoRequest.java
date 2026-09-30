package it.gruppoinit.pal.gp.core.features.infrastructure.layout.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RipristinaTestoRequest {

    @XmlElement(name = "codiceTesto")
    private String codiceTesto;
    @XmlElement(name = "software")
    private String software;

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
}
