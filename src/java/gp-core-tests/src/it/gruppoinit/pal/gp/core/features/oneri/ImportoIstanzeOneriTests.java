package it.gruppoinit.pal.gp.core.features.oneri;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class ImportoIstanzeOneriTests {

    @Test
    public void importoCausaleEIstruttoriaNullTorna0ComeSomma() {

	ImportoIstanzeOneri importo = ImportoIstanzeOneri.fromImporti(null, null);
	Assert.assertEquals(new BigDecimal(0), importo.getImportoTotale());
    }

    @Test
    public void oggettoImmodificato() {

	ImportoIstanzeOneri importo = ImportoIstanzeOneri.fromImporti(new BigDecimal(10), new BigDecimal(20));
	Assert.assertEquals(new BigDecimal(30), importo.getImportoTotale());
	Assert.assertEquals(new BigDecimal(10), importo.getImportoCausale());
	Assert.assertEquals(new BigDecimal(20), importo.getImportoIstruttoria());
    }
}
