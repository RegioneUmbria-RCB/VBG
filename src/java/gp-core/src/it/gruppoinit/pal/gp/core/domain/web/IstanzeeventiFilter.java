package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.ArrayList;
import java.util.List;

public class IstanzeeventiFilter {

    private Istanze istanze;
    private Movimenti movimenti;
    private Categorieeventibase categorieeventibase;
    private Boolean flagLetto;
    private String descrizione;
    private List<Software> softwares = new ArrayList<Software>();

    public Istanze getIstanze() {

	return istanze;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    public Movimenti getMovimenti() {

	return movimenti;
    }

    public void setMovimenti(Movimenti movimenti) {

	this.movimenti = movimenti;
    }

    public Categorieeventibase getCategorieeventibase() {

	return categorieeventibase;
    }

    public void setCategorieeventibase(Categorieeventibase categorieeventibase) {

	this.categorieeventibase = categorieeventibase;
    }

    public Boolean getFlagLetto() {

	return flagLetto;
    }

    public void setFlagLetto(Boolean flagLetto) {

	this.flagLetto = flagLetto;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public List<Software> getSoftwares() {

	return softwares;
    }

    public void setSoftwares(List<Software> softwares) {

	this.softwares = softwares;
    }
}
