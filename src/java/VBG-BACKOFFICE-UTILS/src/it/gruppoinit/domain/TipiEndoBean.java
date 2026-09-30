package it.gruppoinit.domain;

import java.io.Serializable;

public class TipiEndoBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2803188176566679294L;
    private String tipo;
    private Integer ordine;
    private String software;
    private String note;
    private Integer flagPubblica;
    private Integer codiceFamiglia;

    public TipiEndoBean() {

	super();
    }

    public TipiEndoBean(String tipo, Integer ordine, String software, String note, Integer flagPubblica, Integer codiceFamiglia, Integer codice) {

	this();
	this.tipo = tipo;
	this.ordine = ordine;
	this.software = software;
	this.note = note;
	this.flagPubblica = flagPubblica;
	this.codiceFamiglia = codiceFamiglia;
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

    public Integer getCodiceFamiglia() {

	return codiceFamiglia;
    }

    public void setCodiceFamiglia(Integer codiceFamiglia) {

	this.codiceFamiglia = codiceFamiglia;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    private Integer codice;
}
