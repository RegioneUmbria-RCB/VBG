/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

/**
 * Bean non appartenente al dominio. Utilizzato per la generazione delle statistiche dei mercati.
 * 
 * @author francescop
 * @author gianpaolot
 */
public class RegistrazioniStatisticheMercati {

    private Integer anno;
    private BigDecimal incassatoAnno;
    private BigDecimal rimanenzaAnno;
    private BigDecimal importoAnno;

    public RegistrazioniStatisticheMercati(Integer anno, BigDecimal incassatoAnno, BigDecimal rimanenzaAnno, BigDecimal importoAnno) {

	this.importoAnno = importoAnno;
	this.incassatoAnno = incassatoAnno;
	this.rimanenzaAnno = rimanenzaAnno;
	this.anno = anno;
    }

    public RegistrazioniStatisticheMercati() {

	this.importoAnno = new BigDecimal(0);
	this.incassatoAnno = new BigDecimal(0);
	this.rimanenzaAnno = new BigDecimal(0);
    }

    public Integer getAnno() {

	return anno;
    }

    public void setAnno(Integer anno) {

	this.anno = anno;
    }

    public BigDecimal getIncassatoAnno() {

	return incassatoAnno;
    }

    public void setIncassatoAnno(BigDecimal incassatoAnno) {

	this.incassatoAnno = incassatoAnno;
    }

    public BigDecimal getRimanenzaAnno() {

	return rimanenzaAnno;
    }

    public void setRimanenzaAnno(BigDecimal rimanenzaAnno) {

	this.rimanenzaAnno = rimanenzaAnno;
    }

    public BigDecimal getImportoAnno() {

	return importoAnno;
    }

    public void setImportoAnno(BigDecimal importoAnno) {

	this.importoAnno = importoAnno;
    }
}
