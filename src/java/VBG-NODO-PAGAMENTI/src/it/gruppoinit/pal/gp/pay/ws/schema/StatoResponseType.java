package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;

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

    public StatoResponseType(PayStatoPagamenti payStato) {

	if (payStato != null) {
	    this.stato = payStato.getStato();
	    this.descrizione = payStato.getDescStato();
	    this.data = payStato.getDataEvento();
	    this.statoPagamentoNativo = payStato.getStatoPagamentoNativo();
	}
    }

    public StatoResponseType(PosizioneDebitoriaFiltrata posizione) {

	if (posizione == null) {
	    return;
	}
	this.id = posizione.getIdStatoPagamenti();
	this.stato = posizione.getStato();
	this.descrizione = posizione.getDescrizioneStato();
	this.data = posizione.getDataEvento();
    }

    public Integer getId() {

	return id;
    }
}
