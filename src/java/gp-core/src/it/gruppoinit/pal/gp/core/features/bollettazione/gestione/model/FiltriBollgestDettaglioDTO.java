package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class FiltriBollgestDettaglioDTO {

    private String idcomune;
    private Boolean flagEliminata;
    private Integer idTestata;
    private Integer codiceAnagrafe;

    private FiltriBollgestDettaglioDTO() {

	this.idcomune = ORMHelper.getIdcomune();
    }

    public FiltriBollgestDettaglioDTO(String idcomune, Integer idTestata, Boolean flagEliminata) {

	this();
	this.idcomune = idcomune;
	this.flagEliminata = flagEliminata;
	this.idTestata = idTestata;
    }

    public FiltriBollgestDettaglioDTO(String idcomune, Integer idBollettazione, Boolean flagEliminata, Integer idAnagrafica) {

	this(idcomune, idAnagrafica, flagEliminata);
	this.codiceAnagrafe = idAnagrafica;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Boolean getFlagEliminata() {

	return flagEliminata;
    }

    public void setFlagEliminata(Boolean flagEliminata) {

	this.flagEliminata = flagEliminata;
    }

    public Integer getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(Integer codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public Integer getIdTestata() {

	return idTestata;
    }

    public void setIdTestata(Integer idTestata) {

	this.idTestata = idTestata;
    }
}
