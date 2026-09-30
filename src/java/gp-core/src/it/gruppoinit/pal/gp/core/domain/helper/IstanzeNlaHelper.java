package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;

public class IstanzeNlaHelper {

    private Istanze istanze;
    private Integer numeroInterventi;
    private String numeroistanzaprenotato;

    public Istanze getIstanze() {

	return istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    public Integer getNumeroInterventi() {

	return numeroInterventi;
    }

    public void setNumeroInterventi(Integer numeroInterventi) {

	this.numeroInterventi = numeroInterventi;
    }

    public String getNumeroistanzaprenotato() {

	return numeroistanzaprenotato;
    }

    public void setNumeroistanzaprenotato(String numeroistanzaprenotato) {

	this.numeroistanzaprenotato = numeroistanzaprenotato;
    }
}
