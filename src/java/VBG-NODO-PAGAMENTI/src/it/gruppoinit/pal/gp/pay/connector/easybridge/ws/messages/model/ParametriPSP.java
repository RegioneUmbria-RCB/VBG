package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class ParametriPSP {

    @XmlElement(name = "identificativoPSP")
    private String identificativoPSP;
    @XmlElement(name = "identificativoIntermediarioPSP")
    private String identificativoIntermediarioPSP;
    @XmlElement(name = "identificativoCanale")
    private String identificativoCanale;

    public String getIdentificativoPSP() {

	return identificativoPSP;
    }

    public void setIdentificativoPSP(String identificativoPSP) {

	this.identificativoPSP = identificativoPSP;
    }

    public String getIdentificativoIntermediarioPSP() {

	return identificativoIntermediarioPSP;
    }

    public void setIdentificativoIntermediarioPSP(String identificativoIntermediarioPSP) {

	this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
    }

    public String getIdentificativoCanale() {

	return identificativoCanale;
    }

    public void setIdentificativoCanale(String identificativoCanale) {

	this.identificativoCanale = identificativoCanale;
    }
}
