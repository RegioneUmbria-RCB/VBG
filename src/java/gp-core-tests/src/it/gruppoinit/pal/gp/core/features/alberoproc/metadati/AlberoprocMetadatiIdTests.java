package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

import org.junit.Assert;
import org.junit.Test;

public class AlberoprocMetadatiIdTests {

    @Test
    public void setChiaveMetadatoMaiuscolaQuandoValorizzata() {

	AlberoprocMetadatiId id = new AlberoprocMetadatiId("E256", 1, "TiPoLoGiA");
	Assert.assertEquals("TiPoLoGiA deve essere maiuscola", "TIPOLOGIA", id.getChiave());
    }

    @Test
    public void nonSchioppareQuandoChiaveNulla() {

	AlberoprocMetadatiId id = new AlberoprocMetadatiId("E256", 1, "");
	Assert.assertNull("La chiave deve essere null", id.getChiave());
    }
}
