package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;

public class AnagrafeFiere {

    private Anagrafe anagrafe;
    private String catMerc;
    private Integer istanzaId;

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public void setCatMerc(String catMerc) {

	this.catMerc = catMerc;
    }

    public String getCatMerc() {

	return catMerc;
    }

    public void setIstanzaId(Integer istanzaId) {

	this.istanzaId = istanzaId;
    }

    public Integer getIstanzaId() {

	return istanzaId;
    }
}
