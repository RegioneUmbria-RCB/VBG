package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;

public class GetIUVChiamanteEsternoResponse {

    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codiceAvviso")
    private String codiceAvviso;
    @XmlElement(name = "codiceEsito")
    private String codiceEsito;
    @XmlElement(name = "descrizioneEsito")
    private String descrizioneEsito;

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
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

    public ElencoPosizioniDebitorieEsitoType ToElencoPosizioniDebitorieEsitoType() {

	ElencoPosizioniDebitorieEsitoType result = new ElencoPosizioniDebitorieEsitoType();
	return result;
    }
}
