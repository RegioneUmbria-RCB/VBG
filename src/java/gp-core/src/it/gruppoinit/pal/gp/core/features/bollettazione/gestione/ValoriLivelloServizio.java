package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;

public class ValoriLivelloServizio {

    private String segnaposto;
    private BigDecimal tariffa;
    private BigDecimal quantita;

    public ValoriLivelloServizio() {

    }

    public ValoriLivelloServizio(BigDecimal tariffa, BigDecimal quantita, String segnaposto) {

	super();
	this.tariffa = tariffa;
	this.quantita = quantita;
	this.segnaposto = segnaposto;
    }

    public BigDecimal getTariffa() {

	return tariffa;
    }

    public String getSegnaposto() {

	return segnaposto;
    }

    public BigDecimal getQuantita() {

	return quantita;
    }

    public void setSegnaposto(String segnaposto) {

	this.segnaposto = segnaposto;
    }

    public void setTariffa(BigDecimal tariffa) {

	this.tariffa = tariffa;
    }

    public void setQuantita(BigDecimal quantita) {

	this.quantita = quantita;
    }
}
