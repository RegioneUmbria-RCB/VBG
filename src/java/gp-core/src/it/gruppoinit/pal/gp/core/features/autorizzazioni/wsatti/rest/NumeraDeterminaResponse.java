package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class NumeraDeterminaResponse {

    @XmlElement(name = "iddocumento")
    private Integer idDocumento;
    @XmlElement(name = "numero")
    private Integer numero;
    @XmlElement(name = "anno")
    private Integer anno;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "esito")
    private Esito esito;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public Integer getNumero() {

	return numero;
    }

    public void setNumero(Integer numero) {

	this.numero = numero;
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public Esito getEsito() {

	return esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    @Override
    public String toString() {

	return "idDocumento: " + idDocumento + ", numero: " + numero + ", anno: " + anno + ", data: " + data + ", esito: [" + esito + "]";
    }
}
