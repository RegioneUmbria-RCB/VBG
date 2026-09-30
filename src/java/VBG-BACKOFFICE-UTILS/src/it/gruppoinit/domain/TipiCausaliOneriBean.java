package it.gruppoinit.domain;

import java.io.Serializable;

public class TipiCausaliOneriBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2332506744082886465L;
    // CO_ID,CO_DESCRIZIONE,CO_SERICHIEDEENDO,CO_DISABILITATO
    private Integer coId;
    private String coDescrizione;
    private String coSerichiedeendo;
    private Integer coDisabilitato;

    public TipiCausaliOneriBean() {

    }

    public TipiCausaliOneriBean(Integer coId, String coDescrizione, String coSerichiedeendo, Integer coDisabilitato) {

	this();
	this.coId = coId;
	this.coDescrizione = coDescrizione;
	this.coSerichiedeendo = coSerichiedeendo;
	this.coDisabilitato = coDisabilitato;
    }

    public Integer getCoId() {

	return coId;
    }

    public void setCoId(Integer coId) {

	this.coId = coId;
    }

    public String getCoDescrizione() {

	return coDescrizione;
    }

    public void setCoDescrizione(String coDescrizione) {

	this.coDescrizione = coDescrizione;
    }

    public String getCoSerichiedeendo() {

	return coSerichiedeendo;
    }

    public void setCoSerichiedeendo(String coSerichiedeendo) {

	this.coSerichiedeendo = coSerichiedeendo;
    }

    public Integer getCoDisabilitato() {

	return coDisabilitato;
    }

    public void setCoDisabilitato(Integer coDisabilitato) {

	this.coDisabilitato = coDisabilitato;
    }
}
