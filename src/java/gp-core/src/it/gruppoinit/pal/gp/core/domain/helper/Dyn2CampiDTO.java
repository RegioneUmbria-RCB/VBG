package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class Dyn2CampiDTO {

    private PkId id;
    private String software;
    private String nomecampo;
    private String etichetta;
    private String descrizione;
    private String tipodato;

    public Dyn2CampiDTO() {

	id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNomecampo() {

	return nomecampo;
    }

    public void setNomecampo(String nomecampo) {

	this.nomecampo = nomecampo;
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

    public String getTipodato() {

	return tipodato;
    }

    public void setTipodato(String tipodato) {

	this.tipodato = tipodato;
    }
}
