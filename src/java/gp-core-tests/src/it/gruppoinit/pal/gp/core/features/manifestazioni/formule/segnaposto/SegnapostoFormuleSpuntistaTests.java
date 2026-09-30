package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleSpuntistaTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleSpuntista segnaposto = new SegnapostoFormuleSpuntista(false);
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleSpuntista segnaposto = new SegnapostoFormuleSpuntista(false);
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_spuntista_assente_torna0() {

	SegnapostoFormuleSpuntista segnaposto = new SegnapostoFormuleSpuntista(false);
	String formula = "1 * " + SegnapostoFormuleSpuntista.SEGNAPOSTO;
	String expected = "1 * 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_spuntista_presente_torna1() {

	SegnapostoFormuleSpuntista segnaposto = new SegnapostoFormuleSpuntista(true);
	String formula = "1 * " + SegnapostoFormuleSpuntista.SEGNAPOSTO;
	String expected = "1 * 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
