package it.gruppoinit.pal.gp.core.segnaposto.v2;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.segnaposto.v2.SegnapostoParser;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.StrutturaSegnaposto;

public class SegnapostoParserTests {

    @Test()
    public void segnapostoSenzaArgomenti() {

	String segnaposto = "SEGNAPOSTO_TEST";
	SegnapostoParser parser = new SegnapostoParser();
	StrutturaSegnaposto struttura = parser.analizza(segnaposto);
	Assert.assertEquals(struttura.getNome(), "SEGNAPOSTO_TEST");
	Assert.assertEquals(struttura.getArgomenti().length, 0);
    }

    @Test()
    public void segnapostoConArgomenti() {

	String segnaposto = "SEGNAPOSTO_TEST(1,2,3)";
	SegnapostoParser parser = new SegnapostoParser();
	StrutturaSegnaposto struttura = parser.analizza(segnaposto);
	Assert.assertEquals(struttura.getNome(), "SEGNAPOSTO_TEST");
	Assert.assertEquals(struttura.getArgomenti().length, 3);
	Assert.assertEquals(struttura.getArgomenti()[0], "1");
	Assert.assertEquals(struttura.getArgomenti()[1], "2");
	Assert.assertEquals(struttura.getArgomenti()[2], "3");
    }

    @Test
    public void analizzaSegnapostoTraParentesiQuadre() {

	String segnaposto = "[-SEGNAPOSTO_TEST-]";
	SegnapostoParser parser = new SegnapostoParser();
	StrutturaSegnaposto struttura = parser.analizza(segnaposto);
	Assert.assertEquals(struttura.getNome(), "SEGNAPOSTO_TEST");
	Assert.assertEquals(struttura.getArgomenti().length, 0);
    }

    @Test
    public void analizzaSegnapostoTraParentesiQuadreConArgomenti() {

	String segnaposto = "[-SEGNAPOSTO_TEST(1,2,3)-]";
	SegnapostoParser parser = new SegnapostoParser();
	StrutturaSegnaposto struttura = parser.analizza(segnaposto);
	Assert.assertEquals(struttura.getNome(), "SEGNAPOSTO_TEST");
	Assert.assertEquals(struttura.getArgomenti().length, 3);
	Assert.assertEquals(struttura.getArgomenti()[0], "1");
	Assert.assertEquals(struttura.getArgomenti()[1], "2");
	Assert.assertEquals(struttura.getArgomenti()[2], "3");
    }

    @Test
    public void mantieneLaFormattazioneOriginaleAncheConParentesiQuadre() {

	String segnaposto = "[-SEGNAPOSTO_TEST(1, 2, 3)-]";
	SegnapostoParser parser = new SegnapostoParser();
	StrutturaSegnaposto struttura = parser.analizza(segnaposto);
	String current = struttura.toStringaSegnaposto();
	Assert.assertEquals("SEGNAPOSTO_TEST(1, 2, 3)", current);
    }
}
