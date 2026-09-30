package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;

public class IstanzeOneriImporto {

    private BigDecimal importoCausale;
    private BigDecimal importoIstruttoria;

    public IstanzeOneriImporto(BigDecimal importoCausale, BigDecimal importoIstruttoria) {

	super();
	this.importoCausale = importoCausale != null ? importoCausale : BigDecimal.ZERO;
	this.importoIstruttoria = importoIstruttoria != null ? importoIstruttoria : BigDecimal.ZERO;
    }

    public BigDecimal getImportoCausale() {

	return importoCausale;
    }

    public void aggiungiImportoCausale(BigDecimal importo) {

	if (importo == null) {
	    return;
	}
	this.importoCausale = this.importoCausale.add(importo);
    }

    public BigDecimal getImportoIstruttoria() {

	return importoIstruttoria;
    }

    public void aggiungiImportoIstruttoria(BigDecimal importo) {

	if (importo == null) {
	    return;
	}
	this.importoIstruttoria = this.importoIstruttoria.add(importo);
    }

    public BigDecimal getImportoComplessivo() {

	return this.importoCausale.add(this.importoIstruttoria);
    }
}
