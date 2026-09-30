package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.Date;

public class CdsMovDaSostituireBean {

    private Integer codicemovimento;
    private Date data;
    private String tipo;

    public Integer getCodicemovimento() {

	return codicemovimento;
    }

    public void setCodicemovimento(Integer codicemovimento) {

	this.codicemovimento = codicemovimento;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }
}
