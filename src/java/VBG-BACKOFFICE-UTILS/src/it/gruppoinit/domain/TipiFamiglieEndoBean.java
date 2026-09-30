package it.gruppoinit.domain;

import java.io.Serializable;

public class TipiFamiglieEndoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1395719389527834654L;
    private Integer codice;
    private String tipo;
    private Integer ordine;
    private String software;
    private String note;
    private Integer flagPubblica;

    public TipiFamiglieEndoBean() {

	super();
    }

    public TipiFamiglieEndoBean(Integer codice, String tipo, Integer ordine, String software, String note, Integer flagPubblica) {

	this();
	this.codice = codice;
	this.tipo = tipo;
	this.ordine = ordine;
	this.software = software;
	this.note = note;
	this.flagPubblica = flagPubblica;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Integer getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Integer flagPubblica) {

	this.flagPubblica = flagPubblica;
    }
}
