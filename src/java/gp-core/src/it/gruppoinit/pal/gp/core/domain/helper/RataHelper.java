package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RataHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3981289774774099676L;
    private Integer numeroRata;
    private BigDecimal importoRata;
    private BigDecimal incassato;
    private BigDecimal rimanenza;
    private List<RegistrazioniImporti> registrazioniImportiList;

    public RataHelper() {

	this.numeroRata = 1;
	this.registrazioniImportiList = new ArrayList<RegistrazioniImporti>();
	this.importoRata = new BigDecimal(0);
    }

    public BigDecimal getImportoRata() {

	this.importoRata = new BigDecimal(0);
	for (RegistrazioniImporti importi : registrazioniImportiList) {
	    this.importoRata = this.importoRata.add(importi.getImporto());
	}
	return this.importoRata;
    }

    public BigDecimal getIncassato() {

	this.incassato = new BigDecimal(0);
	// BigDecimal inc = new BigDecimal(0);
	for (RegistrazioniImporti importi : registrazioniImportiList) {
	    // inc = new BigDecimal(0);
	    BigDecimal inc = importi.getImporto().subtract(importi.getRimanenza());
	    this.incassato = this.incassato.add(inc);
	}
	return this.incassato;
    }

    public void setIncassato(BigDecimal incassato) {

	this.incassato = incassato;
    }

    public BigDecimal getRimanenza() {

	this.rimanenza = new BigDecimal(0);
	for (RegistrazioniImporti importi : registrazioniImportiList) {
	    if (importi.isNonPrevedeIncassi() == false)
		this.rimanenza = this.rimanenza.add(importi.getRimanenza());
	}
	return this.rimanenza;
    }

    public void setRimanenza(BigDecimal rimanenza) {

	this.rimanenza = rimanenza;
    }

    public List<RegistrazioniImporti> getRegistrazioniImportiList() {

	return registrazioniImportiList;
    }

    public void setRegistrazioniImportiList(List<RegistrazioniImporti> registrazioniImportiList) {

	this.registrazioniImportiList = registrazioniImportiList;
    }

    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }
}
