package it.gruppoinit.pal.gp.core.segnaposto.v2;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.segnaposto.v2.StrutturaSegnaposto;

public class StrutturaSegnapostoTests {

    @Test
    public void ricostruisceIlFormatoDelTagSenzaArgomenti() {

	StrutturaSegnaposto s = new StrutturaSegnaposto("TEST");
	Assert.assertEquals("TEST", s.toStringaSegnaposto());
    }

    @Test
    public void ricostruisceIlFormatoDelTagConArgomentiMantenendoGliSpaziOriginali() {

	StrutturaSegnaposto s = new StrutturaSegnaposto("TEST", "1, 2, 3");
	Assert.assertEquals("TEST(1, 2, 3)", s.toStringaSegnaposto());
    }
}
