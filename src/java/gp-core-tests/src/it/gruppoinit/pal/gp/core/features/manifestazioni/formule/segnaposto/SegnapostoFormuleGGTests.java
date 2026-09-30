package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleGGTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleGG segnaposto = new SegnapostoFormuleGG();
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleGG segnaposto = new SegnapostoFormuleGG();
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_valida_torna_1() {

	SegnapostoFormuleGG segnaposto = new SegnapostoFormuleGG();
	String formula = "GG = " + SegnapostoFormuleGG.SEGNAPOSTO;
	String expected = "GG = 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
