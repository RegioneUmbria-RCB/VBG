package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;

public class PagamentoIUVResponse {

    @XmlElement(name = "identificativoPagamento")
    private String identificativoPagamento;
    @XmlElement(name = "codiceEsito")
    private String codiceEsito;
    @XmlElement(name = "descrizioneEsito")
    private String descrizioneEsito;
    @XmlElement(name = "urlWisp")
    private String urlWisp;

    public String getIdentificativoPagamento() {

	return identificativoPagamento;
    }

    public void setIdentificativoPagamento(String identificativoPagamento) {

	this.identificativoPagamento = identificativoPagamento;
    }

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

    public String getUrlWisp() {

	return urlWisp;
    }

    public void setUrlWisp(String urlWisp) {

	this.urlWisp = urlWisp;
    }
}
