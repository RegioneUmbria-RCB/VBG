package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class AggiungiAllegatoResponse {

    @XmlElement(name = "iddocumento")
    private Integer idDocumento;
    @XmlElement(name = "idallegato")
    private String idAllegato;
    @XmlElement(name = "esito")
    private Esito esito;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public String getIdAllegato() {

	return idAllegato;
    }

    public void setIdAllegato(String idAllegato) {

	this.idAllegato = idAllegato;
    }

    public Esito getEsito() {

	return esito;
    }

    public void setEsito(Esito esito) {

	this.esito = esito;
    }
}
