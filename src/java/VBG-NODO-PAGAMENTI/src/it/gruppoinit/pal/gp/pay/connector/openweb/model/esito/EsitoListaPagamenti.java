package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@XmlType(name = "", propOrder = { "esito", //
	"errore", //
	"listaPagamenti" //
})
public class EsitoListaPagamenti {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "descrizione")
    private String errore;
    @XmlElement(name = "lista_pagamenti")
    private List<DatiPagamento> listaPagamenti;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public List<DatiPagamento> getListaPagamenti() {

	return listaPagamenti;
    }

    public void setListaPagamenti(List<DatiPagamento> listaPagamenti) {

	this.listaPagamenti = listaPagamenti;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
