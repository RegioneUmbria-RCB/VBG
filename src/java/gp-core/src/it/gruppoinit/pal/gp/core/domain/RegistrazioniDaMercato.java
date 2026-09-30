/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

/**
 * Bean non appartenente al dominio. Utilizzato per determinare le registrazioni di un mercato reggruppate per
 * Registrazioni Causali
 * 
 * @author francescop
 * 
 */
public class RegistrazioniDaMercato {

    private RegistrazioniCausali registrazioniCausali;
    private BigDecimal incassato;
    private BigDecimal rimanenza;
    private BigDecimal importo;

    public RegistrazioniCausali getRegistrazioniCausali() {

	return registrazioniCausali;
    }

    public void setRegistrazioniCausali(RegistrazioniCausali registrazioniCausali) {

	this.registrazioniCausali = registrazioniCausali;
    }

    public BigDecimal getIncassato() {

	return incassato;
    }

    public void setIncassato(BigDecimal incassato) {

	this.incassato = incassato;
    }

    public BigDecimal getRimanenza() {

	return rimanenza;
    }

    public void setRimanenza(BigDecimal rimanenza) {

	this.rimanenza = rimanenza;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }
}
