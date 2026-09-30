package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "iuv", "codiceFiscaleEnte", "codiceFiscale", "identificativoPagamento", "formatoRT" })
public class GetRTRequest {

    @XmlElement(name = "iuv", required = true)
    private String iuv;
    @XmlElement(name = "codiceFiscale", required = true)
    private String codiceFiscale;
    @XmlElement(name = "identificativoPagamento", required = true)
    private String identificativoPagamento;
    @XmlElement(name = "formatoRT", required = true)
    private String formatoRT;
    @XmlElement(name = "codiceFiscaleEnte", required = true)
    private String codiceFiscaleEnte;

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

    public String getFormatoRT() {

	return formatoRT;
    }

    public void setFormatoRT(String formatoRT) {

	this.formatoRT = formatoRT;
    }

    public String getCodiceFiscaleEnte() {

	return codiceFiscaleEnte;
    }

    public void setCodiceFiscaleEnte(String codiceFiscaleEnte) {

	this.codiceFiscaleEnte = codiceFiscaleEnte;
    }
}
