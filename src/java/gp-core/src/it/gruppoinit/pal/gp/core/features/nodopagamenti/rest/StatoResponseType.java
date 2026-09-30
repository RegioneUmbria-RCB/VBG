package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatoResponseType {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "stato_pagamento_nativo")
    private String statoPagamentoNativo;

    public StatoResponseType() {

    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getStatoPagamentoNativo() {

	return statoPagamentoNativo;
    }

    public void setStatoPagamentoNativo(String statoPagamentoNativo) {

	this.statoPagamentoNativo = statoPagamentoNativo;
    }
}