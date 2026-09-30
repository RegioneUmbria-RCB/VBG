package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import javax.xml.bind.annotation.XmlElement;

public class DatiMarcaBolloDigitale {

    @XmlElement(name = "hashDocumento")
    private String hashDocumento;
    @XmlElement(name = "provinciaResidenzaPagatore")
    private String provinciaResidenzaPagatore;

    public String getHashDocumento() {

	return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {

	this.hashDocumento = hashDocumento;
    }

    public String getProvinciaResidenzaPagatore() {

	return provinciaResidenzaPagatore;
    }

    public void setProvinciaResidenzaPagatore(String provinciaResidenzaPagatore) {

	this.provinciaResidenzaPagatore = provinciaResidenzaPagatore;
    }
}
