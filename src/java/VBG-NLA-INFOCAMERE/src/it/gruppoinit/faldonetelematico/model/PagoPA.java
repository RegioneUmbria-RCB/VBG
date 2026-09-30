package it.gruppoinit.faldonetelematico.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pagoPA", propOrder = { "codicePagamento", "dataPagamento", "importo", "labelPagamento" })
public class PagoPA {

    @XmlElement(name = "codice_pagamento")
    String codicePagamento;
    @XmlElement(name = "data_pagamento")
    Date dataPagamento;
    @XmlElement(name = "importo")
    Integer importo;
    @XmlElement(name = "label_pagamento")
    String labelPagamento;

    public String getCodicePagamento() {

	return codicePagamento;
    }

    public void setCodicePagamento(String codicePagamento) {

	this.codicePagamento = codicePagamento;
    }

    public Date getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    public String getLabelPagamento() {

	return labelPagamento;
    }

    public void setLabelPagamento(String labelPagamento) {

	this.labelPagamento = labelPagamento;
    }
}