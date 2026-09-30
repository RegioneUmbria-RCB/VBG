package it.gruppoinit.pal.gp.pay.ws.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "")
public class PosizioneDebitoriaInfoRestResponse {

    @XmlElement
    private String alias;
    @XmlElement
    private String idcomune;
    @XmlElement
    private String cfEnteCreditore;
    @XmlElement
    private String uuid;
    @XmlElement
    private String riferimentoClient;
    @XmlElement
    private String cfPiva;
    @XmlElement
    private String nominativo;
    @XmlElement
    private String stato;
    @XmlTransient
    private Date dataEvInternal;

    @XmlElement
    public String getDataEvento() {

	// calcolato da dataEvInternal
	if (dataEvInternal != null) {
	    return Utilities.formatDateWithJsonInterchange(dataEvInternal);
	}
	return null;
    }

    public String getAlias() {

	return alias;
    }

    public void setAlias(String alias) {

	this.alias = alias;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public void setCfEnteCreditore(String cfEnteCreditore) {

	this.cfEnteCreditore = cfEnteCreditore;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getRiferimentoClient() {

	return riferimentoClient;
    }

    public void setRiferimentoClient(String riferimentoClient) {

	this.riferimentoClient = riferimentoClient;
    }

    public String getCfPiva() {

	return cfPiva;
    }

    public void setCfPiva(String cfPiva) {

	this.cfPiva = cfPiva;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }
}
