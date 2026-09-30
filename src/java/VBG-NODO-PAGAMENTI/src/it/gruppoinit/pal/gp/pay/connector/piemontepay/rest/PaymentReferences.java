package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentReferences {

    @XmlElement
    private String codiceEsito;
    @XmlElement
    private String descrizioneEsito;
    @XmlElement
    private String iuv;
    @XmlElement
    private String paymentUrl;
    @XmlElement
    private String identificativoPagamento;

    public String getCodiceEsito() {

	return codiceEsito;
    }

    public void setCodiceEsito(String codiceEsito) {

	this.codiceEsito = codiceEsito;
    }

    public String getDescrizioneEsito() {

	return descrizioneEsito;
    }

    public void setDescrizioneEsito(String descrizioneEsito) {

	this.descrizioneEsito = descrizioneEsito;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getPaymentUrl() {

	return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {

	this.paymentUrl = paymentUrl;
    }

    public String getIdentificativoPagamento() {

	return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {

	this.identificativoPagamento = identificativoPagamento;
    }
}
