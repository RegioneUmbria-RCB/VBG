package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "iuv", "codiceFiscale", "identificativoPagamento" })
public class PagamentoIUVRequest {

    @XmlElement(name = "iuv", required = true)
    private String iuv;
    @XmlElement(name = "codiceFiscale", required = true)
    private String codiceFiscale;
    @XmlElement(name = "identificativoPagamento", required = true)
    private String identificativoPagamento;

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getIdentificativoPagamento() {

	return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {

	this.identificativoPagamento = identificativoPagamento;
    }
}
