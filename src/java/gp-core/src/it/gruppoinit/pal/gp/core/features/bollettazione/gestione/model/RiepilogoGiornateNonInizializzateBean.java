package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import java.util.Date;

public class RiepilogoGiornateNonInizializzateBean {

    private String mercato;
    private Date dataregistrazione;

    public String getMercato() {

	return mercato;
    }

    public void setMercato(String mercato) {

	this.mercato = mercato;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }
}
