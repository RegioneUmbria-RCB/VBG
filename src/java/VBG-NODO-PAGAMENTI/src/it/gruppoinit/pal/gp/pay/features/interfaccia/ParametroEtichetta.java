package it.gruppoinit.pal.gp.pay.features.interfaccia;

import javax.xml.bind.annotation.XmlElement;

public class ParametroEtichetta {

    @XmlElement(name = "etichetta")
    private String etichetta;
    @XmlElement(name = "descrizione")
    private String descrizione;

    public ParametroEtichetta() {

	super();
    }

    public ParametroEtichetta(String etichetta, String descrizione) {

	this.etichetta = etichetta;
	this.descrizione = descrizione == null ? "" : descrizione;
    }

    public String getEtichetta() {

	return etichetta;
    }

    public void setEtichetta(String etichetta) {

	this.etichetta = etichetta;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
