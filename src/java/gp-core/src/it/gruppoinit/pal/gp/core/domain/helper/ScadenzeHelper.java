package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScadenzeHelper implements Serializable {

    private static final long serialVersionUID = -5138531239463600332L;
    private BigDecimal sommaEmesso;
    private BigDecimal sommaIncassato;
    private Registrazioni registrazioni;
    private Date scadenza;

    public ScadenzeHelper() {

	this.registrazioni = new Registrazioni();
	this.sommaEmesso = new BigDecimal(0);
	this.sommaIncassato = new BigDecimal(0);
    }

    public BigDecimal getSommaEmesso() {

	return sommaEmesso;
    }

    public void setSommaEmesso(BigDecimal sommaEmesso) {

	this.sommaEmesso = sommaEmesso;
    }

    public BigDecimal getSommaIncassato() {

	return sommaIncassato;
    }

    public void setSommaIncassato(BigDecimal sommaIncassato) {

	this.sommaIncassato = sommaIncassato;
    }

    public Registrazioni getRegistrazioni() {

	return registrazioni;
    }

    public void setRegistrazioni(Registrazioni registrazioni) {

	this.registrazioni = registrazioni;
    }

    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }
}
