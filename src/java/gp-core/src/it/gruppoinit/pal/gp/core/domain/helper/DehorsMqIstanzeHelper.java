package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;

public class DehorsMqIstanzeHelper {

    private BigDecimal mqdisponibili;
    private BigDecimal assegnati;
    private BigDecimal disponibili;

    public BigDecimal getMqdisponibili() {

	return mqdisponibili;
    }

    public void setMqdisponibili(BigDecimal mqdisponibili) {

	this.mqdisponibili = mqdisponibili;
    }

    public BigDecimal getAssegnati() {

	return assegnati;
    }

    public void setAssegnati(BigDecimal assegnati) {

	this.assegnati = assegnati;
    }

    public BigDecimal getDisponibili() {

	return disponibili;
    }

    public void setDisponibili(BigDecimal disponibili) {

	this.disponibili = disponibili;
    }
}
