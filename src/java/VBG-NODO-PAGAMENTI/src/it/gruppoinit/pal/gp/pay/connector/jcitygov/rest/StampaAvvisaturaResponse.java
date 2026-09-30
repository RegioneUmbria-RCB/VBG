package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StampaAvvisaturaResponse {

    @XmlElement
    private String esito;
    @XmlElement
    private String message;
    @XmlElement
    private String fileBase64Encoded;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getMessage() {

	return message;
    }

    public void setMessage(String message) {

	this.message = message;
    }

    public String getFileBase64Encoded() {

	return fileBase64Encoded;
    }

    public void setFileBase64Encoded(String fileBase64Encoded) {

	this.fileBase64Encoded = fileBase64Encoded;
    }
}
