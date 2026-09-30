package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class BollettazioneBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private BigDecimal importo;
    @XmlElement(name = "data_registrazione")
    private Date dataRegistrazione;
    @XmlElement
    private String idBollettino;
    @XmlElement(name = "id_pagamento")
    private Integer idPagamento;
    @XmlElement
    private boolean effettuato;
    @XmlElement(name= "stato_pagamento")
    private String statoPagamento;
    @XmlElement(name = "codice_iuv")
    private String codiceIuv;
    @XmlElement
    private String codiceAvviso;
    @XmlElement
    private String urlDettaglio;
    @XmlElement(name = "descrizione")
    private String descrizioneCausale;
    @XmlElement
    private Integer idAutorizzazione;
    @XmlElement
    private Integer idBollettazione;
    @XmlElement
    private Integer codiceAnagrafe;
    @XmlElement
    private String idRicevuta;

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    // Getters e setters
    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public Date getDataRegistrazione() {

	return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {

	this.dataRegistrazione = dataRegistrazione;
    }

    public String getIdBollettino() {

	return idBollettino;
    }

    public void setIdBollettino(String idBollettino) {

	this.idBollettino = idBollettino;
    }

    public Integer getIdPagamento() {

	return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {

	this.idPagamento = idPagamento;
    }

    public boolean getEffettuato() {

	return effettuato;
    }

    public void setEffettuato(boolean effettuato) {

	this.effettuato = effettuato;
    }

    public String getStatoPagamento() {

	return statoPagamento;
    }

    public void setStatoPagamento(String statoPagamento) {

	this.statoPagamento = statoPagamento;
    }

    public String getCodiceIuv() {

	return codiceIuv;
    }

    public void setCodiceIuv(String codiceIuv) {

	this.codiceIuv = codiceIuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getUrlDettaglio() {

	return urlDettaglio;
    }

    public void setUrlDettaglio(String urlDettaglio) {

	this.urlDettaglio = urlDettaglio;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public Integer getIdBollettazione() {

	return idBollettazione;
    }

    public void setIdBollettazione(Integer idBollettazione) {

	this.idBollettazione = idBollettazione;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public String getIdRicevuta() {

	return idRicevuta;
    }

    public void setIdRicevuta(String idRicevuta) {

	this.idRicevuta = idRicevuta;
    }

    @Override
    public String toString() {

	StringBuilder builder = new StringBuilder();
	builder.append("BollettazioneBean [importo=");
	builder.append(importo);
	builder.append(", dataRegistrazione=");
	builder.append(dataRegistrazione);
	builder.append(", idBollettino=");
	builder.append(idBollettino);
	builder.append(", idPagamento=");
	builder.append(idPagamento);
	builder.append(", effettuato=");
	builder.append(effettuato);
	builder.append(", statoPagamento=");
	builder.append(statoPagamento);
	builder.append(", codiceIuv=");
	builder.append(codiceIuv);
	builder.append(", codiceAvviso=");
	builder.append(codiceAvviso);
	builder.append(", urlDettaglio=");
	builder.append(urlDettaglio);
	builder.append(", descrizioneCausale=");
	builder.append(descrizioneCausale);
	builder.append(", idAutorizzazione=");
	builder.append(idAutorizzazione);
	builder.append(", idBollettazione=");
	builder.append(idBollettazione);
	builder.append(", codiceAnagrafe=");
	builder.append(codiceAnagrafe);
	builder.append(", idRicevuta=");
	builder.append(idRicevuta);
	builder.append("]");
	return builder.toString();
    }
}
