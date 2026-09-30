package it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AutorizzazioniMercatoSrv {

    public static AutorizzazioniMercatoSrv fromTemplate(AutorizzazioniMercatoSrv template) {

	AutorizzazioniMercatoSrv ret = new AutorizzazioniMercatoSrv();
	ret.setAreaPubblica(template.getAreaPubblica());
	ret.setCodiceFiscale(template.getCodiceFiscale());
	ret.setComuneRilascio(template.getComuneRilascio());
	ret.setDataRilascio(template.getDataRilascio());
	ret.setDataCessazione(template.getDataCessazione());
	ret.setId(template.getId());
	ret.setNominativo(template.getNominativo());
	ret.setNumero(template.getNumero());
	ret.setRuolo(template.getRuolo());
	ret.setStato(template.getStato());
	ret.setTipologiaTitolo(template.getTipologiaTitolo());
	ret.setRuoloEnum(template.getRuoloEnum());
	return ret;
    }

    public AutorizzazioniMercatoSrv() {

	super();
    }

    @XmlElement
    private Integer id;
    @XmlElement
    private String codiceFiscale;
    @XmlElement
    private String nominativo;
    @XmlElement
    private String numero;
    @XmlElement
    private Date dataRilascio;
    @XmlElement
    private Date dataCessazione;
    @XmlElement
    private String stato;
    @XmlElement
    private String ruolo;
    @XmlElement
    private String tipologiaTitolo;
    @XmlElement
    private String comuneRilascio;
    @XmlElement
    private AreaPubblica areaPubblica;
    private RuoloAutorizzazioneEnum ruoloEnum;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public Date getDataRilascio() {

	return dataRilascio;
    }

    public void setDataRilascio(Date dataRilascio) {

	this.dataRilascio = dataRilascio;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public String getTipologiaTitolo() {

	return tipologiaTitolo;
    }

    public void setTipologiaTitolo(String tipologiaTitolo) {

	this.tipologiaTitolo = tipologiaTitolo;
    }

    public String getComuneRilascio() {

	return comuneRilascio;
    }

    public void setComuneRilascio(String comuneRilascio) {

	this.comuneRilascio = comuneRilascio;
    }

    public AreaPubblica getAreaPubblica() {

	return areaPubblica;
    }

    public void setAreaPubblica(AreaPubblica areaPubblica) {

	this.areaPubblica = areaPubblica;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    @XmlTransient
    public RuoloAutorizzazioneEnum getRuoloEnum() {

	return ruoloEnum;
    }

    public void setRuoloEnum(RuoloAutorizzazioneEnum ruoloEnum) {

	this.ruoloEnum = ruoloEnum;
    }
}
