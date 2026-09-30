package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StampaAvvisaturaRequest {

    @XmlElement
    private StampaAvvisaturaChiaviDebito chiaviDebito;
    @XmlElement
    private String idInstallazione;
    @XmlElement
    private String numeroAvviso;
    @XmlElement
    private String locale;
    @XmlElement
    private String base64FileLogoEnte;

    public StampaAvvisaturaChiaviDebito getChiaviDebito() {

	return chiaviDebito;
    }

    public void setChiaviDebito(StampaAvvisaturaChiaviDebito chiaviDebito) {

	this.chiaviDebito = chiaviDebito;
    }

    public String getIdInstallazione() {

	return idInstallazione;
    }

    public void setIdInstallazione(String idInstallazione) {

	this.idInstallazione = idInstallazione;
    }

    public String getNumeroAvviso() {

	return numeroAvviso;
    }

    public void setNumeroAvviso(String numeroAvviso) {

	this.numeroAvviso = numeroAvviso;
    }

    public String getLocale() {

	return locale;
    }

    public void setLocale(String locale) {

	this.locale = locale;
    }

    public String getBase64FileLogoEnte() {

	return base64FileLogoEnte;
    }

    public void setBase64FileLogoEnte(String base64FileLogoEnte) {

	this.base64FileLogoEnte = base64FileLogoEnte;
    }
}
