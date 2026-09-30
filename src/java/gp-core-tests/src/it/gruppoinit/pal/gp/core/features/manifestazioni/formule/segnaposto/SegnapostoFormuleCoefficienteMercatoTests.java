package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleCoefficienteMercatoTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleCoefficienteMercato segnaposto = new SegnapostoFormuleCoefficienteMercato(null);
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleCoefficienteMercato segnaposto = new SegnapostoFormuleCoefficienteMercato(null);
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_coefficiente0_torna0() {

	SegnapostoFormuleCoefficienteMercato segnaposto = new SegnapostoFormuleCoefficienteMercato(BigDecimal.ZERO);
	String formula = "1 * " + SegnapostoFormuleCoefficienteMercato.SEGNAPOSTO;
	String expected = "1 * 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
