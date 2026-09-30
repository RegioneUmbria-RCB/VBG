package it.gruppoinit.pal.gp.core.domain.web;

import java.util.Date;

public class RegistrazioniInOutFilter {

    private int codiceAnagrafe;
    private Date dallaData;
    private Date allaData;
    private int codiceMercato;
    private int codiceUso;
    private int anno;
    private int codiceCausale;
    private int codiceConto;

    public int getCodiceAnagrafe() {

	return codiceAnagrafe;
    }

    public void setCodiceAnagrafe(int codiceAnagrafe) {

	this.codiceAnagrafe = codiceAnagrafe;
    }

    public int getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(int codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public int getCodiceUso() {

	return codiceUso;
    }

    public void setCodiceUso(int codiceUso) {

	this.codiceUso = codiceUso;
    }

    public int getAnno() {

	return anno;
    }

    public void setAnno(int anno) {

	this.anno = anno;
    }

    public int getCodiceCausale() {

	return codiceCausale;
    }

    public void setCodiceCausale(int codiceCausale) {

	this.codiceCausale = codiceCausale;
    }

    public int getCodiceConto() {

	return codiceConto;
    }

    public void setCodiceConto(int codiceConto) {

	this.codiceConto = codiceConto;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public Date getAllaData() {

	return allaData;
    }
}
