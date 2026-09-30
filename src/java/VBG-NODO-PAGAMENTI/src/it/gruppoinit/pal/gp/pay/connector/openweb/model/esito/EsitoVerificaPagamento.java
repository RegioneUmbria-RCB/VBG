package it.gruppoinit.pal.gp.pay.connector.openweb.model.esito;

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
	"iuv", //
	"pagato", //
	"dataPagamento", //
	"stato", //
	"ricevuta", //
	"rt", //
	"marcaDaBollo" //
})
public class EsitoVerificaPagamento {

    @XmlElement(name = "esito")
    private String esito;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "pagato")
    private Integer pagato;
    @XmlElement(name = "data_pagamento")
    private String dataPagamento;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "ricevuta")
    private String ricevuta;
    @XmlElement(name = "rt")
    private String rt;
    @XmlElement(name = "mdb")
    private String marcaDaBollo;

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public Integer getPagato() {

	return pagato;
    }

    public void setPagato(Integer pagato) {

	this.pagato = pagato;
    }

    public String getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getRicevuta() {

	return ricevuta;
    }

    public void setRicevuta(String ricevuta) {

	this.ricevuta = ricevuta;
    }

    public String getRt() {

	return rt;
    }

    public void setRt(String rt) {

	this.rt = rt;
    }

    public String getMarcaDaBollo() {

	return marcaDaBollo;
    }

    public void setMarcaDaBollo(String marcaDaBollo) {

	this.marcaDaBollo = marcaDaBollo;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
