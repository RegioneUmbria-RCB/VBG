package it.gruppoinit.pal.gp.core.features.contabilita;

import java.math.BigDecimal;

public class ImportoIvato {

    private BigDecimal importoSenzaIVA;
    private Integer iva;
    private BigDecimal importoConIVA;

    public ImportoIvato() {

	this.importoSenzaIVA = BigDecimal.ZERO;
	this.iva = 0;
	this.importoConIVA = BigDecimal.ZERO;
    }

    public ImportoIvato(BigDecimal importoSenzaIVA, Integer iva, BigDecimal importoConIVA) {

	this.importoSenzaIVA = importoSenzaIVA;
	this.iva = iva;
	this.importoConIVA = importoConIVA;
    }

    public BigDecimal getImportoSenzaIVA() {

	return importoSenzaIVA;
    }

    public void setImportoSenzaIVA(BigDecimal importoSenzaIVA) {

	this.importoSenzaIVA = importoSenzaIVA;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public BigDecimal getImportoConIVA() {

	return importoConIVA;
    }

    public void setImportoConIVA(BigDecimal importoConIVA) {

	this.importoConIVA = importoConIVA;
    }
}
