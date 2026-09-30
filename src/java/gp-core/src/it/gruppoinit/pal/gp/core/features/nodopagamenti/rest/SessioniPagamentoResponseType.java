package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "dataOraInizio", "esito" })
@XmlRootElement
public class SessioniPagamentoResponseType {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "dataOraInizio", type = Date.class)
    private Date dataOraInizio;
    @XmlElement(name = "esito")
    private Integer esito;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public Date getDataOraInizio() {

	return dataOraInizio;
    }

    public void setDataOraInizio(Date dataOraInizio) {

	this.dataOraInizio = dataOraInizio;
    }

    public Integer getEsito() {

	return esito;
    }

    public void setEsito(Integer esito) {

	this.esito = esito;
    }
}
