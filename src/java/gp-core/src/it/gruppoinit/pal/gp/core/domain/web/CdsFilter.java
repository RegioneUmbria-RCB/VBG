package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.Date;

public class CdsFilter {

    private Comuni comune;
    private String numeroistanza;
    private Date dallaData;
    private Date allaData;
    private String nominativo;

    public CdsFilter() {

	this.comune = new Comuni();
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	this.numeroistanza = numeroistanza;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }
}
