package it.gruppoinit.pal.gp.core.features.datidinamici.metadati;

import it.gruppoinit.pal.gp.core.domain.Dyn2Metadati;

public class Dyn2MetadatiRestBean {

    private Integer campoDinamicoId;
    private String nomeCampo;
    private String contestoCampo;
    private String tipoDato;

    private Dyn2MetadatiRestBean() {

	super();
    }

    public Dyn2MetadatiRestBean(Dyn2Metadati d2md) {

	this();
	this.campoDinamicoId = d2md.getDyn2Campi().getId().getCodice();
	this.nomeCampo = d2md.getDyn2Campi().getNomecampo();
	this.tipoDato = d2md.getDyn2Campi().getTipodato();
	this.contestoCampo = d2md.getContestoCampo();
	//    campoDinamicoId | DYN2_CAMPI.ID
	//    - nomeCampo | DYN2_CAMPI.NOMECAMPO
	//    - contestoCampo | DYN2_METADATI.CONTESTO_CAMPO
	//    - tipoDato | DYN2_CAMPI.TIPODATO
    }

    public Integer getCampoDinamicoId() {

	return campoDinamicoId;
    }

    public void setCampoDinamicoId(Integer campoDinamicoId) {

	this.campoDinamicoId = campoDinamicoId;
    }

    public String getNomeCampo() {

	return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {

	this.nomeCampo = nomeCampo;
    }

    public String getContestoCampo() {

	return contestoCampo;
    }

    public void setContestoCampo(String contestoCampo) {

	this.contestoCampo = contestoCampo;
    }

    public String getTipoDato() {

	return tipoDato;
    }

    public void setTipoDato(String tipoDato) {

	this.tipoDato = tipoDato;
    }
}
