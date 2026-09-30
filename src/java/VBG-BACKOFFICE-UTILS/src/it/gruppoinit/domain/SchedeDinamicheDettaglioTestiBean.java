package it.gruppoinit.domain;

import java.io.Serializable;

public class SchedeDinamicheDettaglioTestiBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5145533064112530037L;
    private Integer codice;
    private String d2btt;
    private String testo;

    public SchedeDinamicheDettaglioTestiBean() {

	super();
    }

    public SchedeDinamicheDettaglioTestiBean(Integer codice, String d2btt, String testo) {

	this();
	this.codice = codice;
	this.d2btt = d2btt;
	this.testo = testo;
    }

    public Integer getCodice() {

	return codice;
    }

    public void setCodice(Integer codice) {

	this.codice = codice;
    }

    public String getD2btt() {

	return d2btt;
    }

    public void setD2btt(String d2btt) {

	this.d2btt = d2btt;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }
}
