package it.gruppoinit.pal.gp.core.features.contabilita;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AliquotaIVA {

    private Integer iva;
    private final BigDecimal cento = new BigDecimal(100);

    public AliquotaIVA(Integer iva) {

	if (iva == null) {
	    throw new RuntimeException("Impossibile utilizzare la classe AliquotaIVA senza passare nel costruttore l'aliquota di riferimento");
	}
	this.iva = iva;
    }

    public ImportoIvato applica(BigDecimal importo) {

	if (importo == null) {
	    throw new RuntimeException("Impossibile utilizzare il metodo AliquotaIVA.applica senza passare l'importo al quale applicare l'IVA");
	}
	BigDecimal importoConIVA = importo.add(importo.multiply(new BigDecimal(iva)).divide(cento)).setScale(2, RoundingMode.HALF_EVEN);
	return new ImportoIvato(importo, this.iva, importoConIVA);
    }

    public ImportoIvato scorpora(BigDecimal importo) {

	if (importo == null) {
	    throw new RuntimeException("Impossibile utilizzare il metodo AliquotaIVA.scorpora senza passare l'importo dal quale scorporare l'IVA");
	}
	BigDecimal importoSenzaIVA = importo.subtract(importo.multiply(new BigDecimal(iva)).divide(cento)).setScale(2, RoundingMode.HALF_EVEN);
	return new ImportoIvato(importoSenzaIVA, this.iva, importo);
    }
}
