package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;

public class ImportoIstanzeOneri {

    private BigDecimal importoCausale;
    private BigDecimal importoIstruttoria;

    public static ImportoIstanzeOneri fromImporti(BigDecimal importoCausale, BigDecimal importoIstruttoria) {

	ImportoIstanzeOneri importo = new ImportoIstanzeOneri();
	importo.importoCausale = (importoCausale == null ? BigDecimal.ZERO : importoCausale);
	importo.importoIstruttoria = (importoIstruttoria == null ? BigDecimal.ZERO : importoIstruttoria);
	return importo;
    }

    public BigDecimal getImportoCausale() {

	return importoCausale;
    }

    public BigDecimal getImportoIstruttoria() {

	return importoIstruttoria;
    }

    public BigDecimal getImportoTotale() {

	return this.importoCausale.add(this.importoIstruttoria);
    }
}
