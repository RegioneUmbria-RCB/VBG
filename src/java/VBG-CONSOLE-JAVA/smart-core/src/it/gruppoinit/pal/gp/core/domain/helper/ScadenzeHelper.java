package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScadenzeHelper implements Serializable {

    private static final long serialVersionUID = -5138531239463600332L;
    private BigDecimal sommaEmesso;
    private BigDecimal sommaIncassato;
    private Date scadenza;

    public ScadenzeHelper() {

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

    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }
}
