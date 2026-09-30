package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;

public class TransazioniHelper {

    private Integer[] registrazioniChkList;
    private Integer[] importiChkList;
    private Registrazioni datiTransazione;

    public void setImportiChkList(Integer[] importiChkList) {

	this.importiChkList = importiChkList;
    }

    public Integer[] getImportiChkList() {

	return importiChkList;
    }

    public void setRegistrazioniChkList(Integer[] registrazioniChkList) {

	this.registrazioniChkList = registrazioniChkList;
    }

    public Integer[] getRegistrazioniChkList() {

	return registrazioniChkList;
    }

    public Registrazioni getDatiTransazione() {

	return datiTransazione;
    }

    public void setDatiTransazione(Registrazioni datiTransazione) {

	this.datiTransazione = datiTransazione;
    }
}
