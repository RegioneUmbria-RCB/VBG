package it.gruppoinit.pal.gp.core.features.datidinamici.model;

import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;

public class CampoDinamico {

    private Integer id;
    private String nomeCampo;
    private String etichetta;
    private String descrizione;
    private String tipoDato;

    public CampoDinamico() {

    }

    public CampoDinamico(Dyn2Campi campo) {

	super();
	if (campo == null || campo.getId() == null) {
	    return;
	}
	this.id = campo.getId().getCodice();
	this.nomeCampo = campo.getNomecampo();
	this.etichetta = campo.getEtichetta();
	this.descrizione = campo.getDescrizione();
	this.tipoDato = campo.getTipodato();
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNomeCampo() {

	return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {

	this.nomeCampo = nomeCampo;
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

    public String getTipoDato() {

	return tipoDato;
    }

    public void setTipoDato(String tipoDato) {

	this.tipoDato = tipoDato;
    }
}
