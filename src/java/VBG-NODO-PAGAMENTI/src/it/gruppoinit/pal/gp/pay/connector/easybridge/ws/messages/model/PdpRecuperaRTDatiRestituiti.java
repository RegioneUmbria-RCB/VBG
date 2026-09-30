package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class PdpRecuperaRTDatiRestituiti {

    @XmlElement(name = "xmlRT")
    private String xmlRT;
    @XmlElement(name = "parametriPSP")
    private ParametriPSP parametriPSP;
    @XmlElement(name = "datiPagamento")
    private DatiPagamento datiPagamento;

    public String getXmlRT() {

	return xmlRT;
    }

    public void setXmlRT(String xmlRT) {

	this.xmlRT = xmlRT;
    }

    public ParametriPSP getParametriPSP() {

	return parametriPSP;
    }

    public void setParametriPSP(ParametriPSP parametriPSP) {

	this.parametriPSP = parametriPSP;
    }

    public DatiPagamento getDatiPagamento() {

	return datiPagamento;
    }

    public void setDatiPagamento(DatiPagamento datiPagamento) {

	this.datiPagamento = datiPagamento;
    }
}
