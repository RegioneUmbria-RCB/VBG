package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StampaAvvisaturaChiaviDebito {

    @XmlElement
    private String codiceIpaEnte;
    @XmlElement
    private String codiceTipoDebito;
    @XmlElement
    private String idPosizione;
    @XmlElement
    private String idDebito;
    @XmlElement
    private String codiceServizio;

    public String getCodiceIpaEnte() {

	return codiceIpaEnte;
    }

    public void setCodiceIpaEnte(String codiceIpaEnte) {

	this.codiceIpaEnte = codiceIpaEnte;
    }

    public String getCodiceTipoDebito() {

	return codiceTipoDebito;
    }

    public void setCodiceTipoDebito(String codiceTipoDebito) {

	this.codiceTipoDebito = codiceTipoDebito;
    }

    public String getIdPosizione() {

	return idPosizione;
    }

    public void setIdPosizione(String idPosizione) {

	this.idPosizione = idPosizione;
    }

    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public String getCodiceServizio() {

	return codiceServizio;
    }

    public void setCodiceServizio(String codiceServizio) {

	this.codiceServizio = codiceServizio;
    }
}
