package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecuperaParametriRequest {

    @XmlElement(name = "nomeComponente")
    private String nomeComponente;
    @XmlElement(name = "endpoint")
    private String endpoint;

    public String getNomeComponente() {

	return nomeComponente;
    }

    public void setNomeComponente(String nomeComponente) {

	this.nomeComponente = nomeComponente;
    }

    public String getEndpoint() {

	return endpoint;
    }

    public void setEndpoint(String endpoint) {

	this.endpoint = endpoint;
    }
}
