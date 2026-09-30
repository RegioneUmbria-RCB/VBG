package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PrinterLoginResponse {

    @XmlElement
    private String token;

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }
}
