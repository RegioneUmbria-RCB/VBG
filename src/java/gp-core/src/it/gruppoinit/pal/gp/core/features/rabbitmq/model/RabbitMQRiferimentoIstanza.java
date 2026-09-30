package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RabbitMQRiferimentoIstanza {

    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "numeroIstanza")
    private String numeroIstanza;
    @XmlElement(name = "uuid")
    private String uuid;

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }
}
