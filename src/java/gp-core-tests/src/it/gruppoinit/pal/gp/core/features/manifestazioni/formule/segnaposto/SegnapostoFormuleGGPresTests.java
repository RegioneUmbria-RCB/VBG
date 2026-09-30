package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleGGPresTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleGGPres segnaposto = new SegnapostoFormuleGGPres("");
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleGGPres segnaposto = new SegnapostoFormuleGGPres("");
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_valore_PRESENZE_torna_1() {

	SegnapostoFormuleGGPres segnaposto = new SegnapostoFormuleGGPres("PrEsEnZe");
	String formula = "GG = " + SegnapostoFormuleGGPres.SEGNAPOSTO;
	String expected = "GG = 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_valore_ASSENZE_torna_0() {

	SegnapostoFormuleGGPres segnaposto = new SegnapostoFormuleGGPres("aSsEnZe");
	String formula = "GG = " + SegnapostoFormuleGGPres.SEGNAPOSTO;
	String expected = "GG = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
