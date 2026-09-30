package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class AggiungiAllegatoRequest {

    @XmlElement(name = "anno")
    private Integer anno;
    @XmlElement(name = "image")
    private String allegatoBase64;
    @XmlElement(name = "tipofile")
    private String estensioneFile;
    @XmlElement(name = "iddocumento")
    private Integer idDocumento;
    @XmlElement(name = "nomeallegato")
    private String nomeFile;
    @XmlElement(name = "numero")
    private Integer numero;
    @XmlElement(name = "principale")
    private boolean principale;
    @XmlElement(name = "serial")
    private String serial;

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

    public Integer getNumero() {

	return numero;
    }

    public void setNumero(Integer numero) {

	this.numero = numero;
    }

    public boolean isPrincipale() {

	return principale;
    }

    public void setPrincipale(boolean principale) {

	this.principale = principale;
    }

    public String getEstensioneFile() {

	return estensioneFile;
    }

    public void setEstensioneFile(String estensioneFile) {

	this.estensioneFile = estensioneFile;
    }

    public String getAllegatoBase64() {

	return allegatoBase64;
    }

    public void setAllegatoBase64(String allegatoBase64) {

	this.allegatoBase64 = allegatoBase64;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getSerial() {

	return serial;
    }

    public void setSerial(String serial) {

	this.serial = serial;
    }
}
