package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class Utilizzo {

    @JsonProperty("istanze")
    private Metodi istanze;
    @JsonProperty("autorizzazioni")
    private Metodi autorizzazioni;
    @JsonProperty("attivita")
    private Metodi attivita;

    public Utilizzo() {

	this.attivita = new Metodi();
	this.autorizzazioni = new Metodi();
	this.istanze = new Metodi();
    }

    public Metodi getIstanze() {

	return istanze;
    }

    public void setIstanze(Metodi istanze) {

	this.istanze = istanze;
    }

    public Metodi getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(Metodi autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public Metodi getAttivita() {

	return attivita;
    }

    public void setAttivita(Metodi attivita) {

	this.attivita = attivita;
    }
}
