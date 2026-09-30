package it.gruppoinit.pal.gp.pay.connector.payer.schema;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Paginazione", propOrder = { "numPagina", "limite", "totPagine", "successiva", "precedente" })
public class Paginazione {

    @XmlElement(name = "num_pagina")
    private Integer numPagina;
    @XmlElement(name = "limite")
    private Integer limite;
    @XmlElement(name = "tot_pagine") //Attenzione perche su master si chiama tot_pagina
    private Integer totPagine;
    @XmlElement(name = "successiva")
    private String successiva;
    @XmlElement(name = "precedente")
    private String precedente;

    public Integer getNumPagina() {

	return numPagina;
    }

    public void setNumPagina(Integer numPagina) {

	this.numPagina = numPagina;
    }

    public Integer getLimite() {

	return limite;
    }

    public void setLimite(Integer limite) {

	this.limite = limite;
    }

    public Integer getTotPagine() {

	return totPagine;
    }

    public void setTotPagine(Integer totPagine) {

	this.totPagine = totPagine;
    }

    public String getSuccessiva() {

	return successiva;
    }

    public void setSuccessiva(String successiva) {

	this.successiva = successiva;
    }

    public String getPrecedente() {

	return precedente;
    }

    public void setPrecedente(String precedente) {

	this.precedente = precedente;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
