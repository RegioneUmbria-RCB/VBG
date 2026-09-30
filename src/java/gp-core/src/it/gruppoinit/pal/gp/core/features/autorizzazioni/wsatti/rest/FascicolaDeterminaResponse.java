package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class FascicolaDeterminaResponse {

    @XmlElement(name = "numero")
    private String numero;
    @XmlElement(name = "datafascicolo")
    private String dataFascicolo;

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getDataFascicolo() {

	return dataFascicolo;
    }

    public void setDataFascicolo(String dataFascicolo) {

	this.dataFascicolo = dataFascicolo;
    }
}
