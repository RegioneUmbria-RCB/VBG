package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class AnagrafeFiere {

    private Anagrafe anagrafe;
    private Istanze istanze;
    private Autorizzazioni autorizzazioni;
    private String catMerc;
    private Integer istanzaId;

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    public Istanze getIstanze() {

	return istanze;
    }

    public void setAutorizzazioni(Autorizzazioni autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public Autorizzazioni getAutorizzazioni() {

	return autorizzazioni;
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
