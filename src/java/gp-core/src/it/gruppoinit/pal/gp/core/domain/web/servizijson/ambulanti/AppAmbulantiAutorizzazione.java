package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AreaPubblica;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppAmbulantiAutorizzazione {

    @XmlElement
    private Integer id;
    @XmlElement
    private String cfRiferimento;
    @XmlElement
    private String comuneRilascio;
    @XmlElement
    private Date dataRilascio;
    @XmlElement
    private Date dataCessazione;
    @XmlElement
    private String numero;
    @XmlElement
    private String titolare;
    @XmlElement
    private String occupante;
    @XmlElement
    private String posteggio;
    @XmlElement
    private RuoloAutorizzazioneEnum ruolo;
    @XmlElement
    private boolean avviso;
    @XmlElement
    private String statoAutorizzazione;
    @XmlTransient
    private AreaPubblica transientAreaPubblica;
    @XmlTransient
    private boolean areaPubblica;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCfRiferimento() {

	return cfRiferimento;
    }

    public void setCfRiferimento(String cfRiferimento) {

	this.cfRiferimento = cfRiferimento;
    }

    public String getComuneRilascio() {

	return comuneRilascio;
    }

    public void setComuneRilascio(String comuneRilascio) {

	this.comuneRilascio = comuneRilascio;
    }

    public Date getDataRilascio() {

	return dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio) {

	this.dataRilascio = dataRilascio;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getTitolare() {

	return titolare;
    }

    public void setTitolare(String titolare) {

	this.titolare = titolare;
    }

    public String getOccupante() {

	return occupante;
    }

    public void setOccupante(String occupante) {

	this.occupante = occupante;
    }

    public String getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(String posteggio) {

	this.posteggio = posteggio;
    }

    public RuoloAutorizzazioneEnum getRuolo() {

	return ruolo;
    }

    public void setRuolo(RuoloAutorizzazioneEnum ruolo) {

	this.ruolo = ruolo;
    }

    public boolean isAvviso() {

	return avviso;
    }

    public void setAvviso(boolean avviso) {

	this.avviso = avviso;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    @XmlTransient
    public AreaPubblica getTransientAreaPubblica() {

	return transientAreaPubblica;
    }

    public void setTransientAreaPubblica(AreaPubblica transientAreaPubblica) {

	this.transientAreaPubblica = transientAreaPubblica;
    }

    @XmlTransient
    public boolean isAreaPubblica() {

	return this.transientAreaPubblica != null && StringUtils.isNotBlank(this.transientAreaPubblica.getPosteggio());
    }
}
