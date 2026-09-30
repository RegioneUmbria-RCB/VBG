package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;

public class LeggiDeterminaRequest {

    @XmlElement(name = "iddocumento")
    private Integer idDocumento;
    @XmlElement(name = "anno")
    private Integer anno;
    @XmlElement(name = "numero")
    private String numero;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }
}
