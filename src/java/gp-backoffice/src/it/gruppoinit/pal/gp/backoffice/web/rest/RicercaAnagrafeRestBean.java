package it.gruppoinit.pal.gp.backoffice.web.rest;

public class RicercaAnagrafeRestBean {

    private String testo;
    private int numMaxRecords;
    private Integer idGiornata;

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }

    public int getNumMaxRecords() {

	return numMaxRecords;
    }

    public void setNumMaxRecords(int numMaxRecords) {

	this.numMaxRecords = numMaxRecords;
    }

    public Integer getIdGiornata() {

	return idGiornata;
    }

    public void setIdGiornata(Integer idGiornata) {

	this.idGiornata = idGiornata;
    }
}
