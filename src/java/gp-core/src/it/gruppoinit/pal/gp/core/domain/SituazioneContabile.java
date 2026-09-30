package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

/**
 * 
 * @author gianpaolot
 * 
 *         Bean utilizzato per la visulaizzazione della situazione contabile per Mercato e Posteggio
 */
public class SituazioneContabile {

    private Short anno;
    private Conti conti;
    private BigDecimal importo;
    private BigDecimal incassato;
    private BigDecimal saldo;

    public Short getAnno() {

	return anno;
    }

    public void setAnno(Short anno) {

	this.anno = anno;
    }

    public Conti getConti() {

	return conti;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public BigDecimal getIncassato() {

	return incassato;
    }

    public void setIncassato(BigDecimal incassato) {

	this.incassato = incassato;
    }

    public BigDecimal getSaldo() {

	if (incassato == null) {
	    this.incassato = new BigDecimal(0);
	}
	if (this.importo == null) {
	    this.importo = new BigDecimal(0);
	}
	this.saldo = importo.subtract(incassato);
	return this.saldo;
    }
}
