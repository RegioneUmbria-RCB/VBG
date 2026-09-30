package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Istanze;

import java.util.Date;

public class AutorizzazioniAccessiOperazioniHelper {

    private Istanze istanza;
    private String stato;
    private String tipo;
    private Date dataChiusuraIstanza;

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public Date getDataChiusuraIstanza() {

	return dataChiusuraIstanza;
    }

    public void setDataChiusuraIstanza(Date dataChiusuraIstanza) {

	this.dataChiusuraIstanza = dataChiusuraIstanza;
    }
}
