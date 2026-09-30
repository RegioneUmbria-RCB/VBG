package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleConcessionarioTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleConcessionario segnaposto = new SegnapostoFormuleConcessionario(false);
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleConcessionario segnaposto = new SegnapostoFormuleConcessionario(false);
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_concessionario_assente_torna0() {

	SegnapostoFormuleConcessionario segnaposto = new SegnapostoFormuleConcessionario(false);
	String formula = "1 * " + SegnapostoFormuleConcessionario.SEGNAPOSTO;
	String expected = "1 * 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_concessionario_presente_torna1() {

	SegnapostoFormuleConcessionario segnaposto = new SegnapostoFormuleConcessionario(true);
	String formula = "1 * " + SegnapostoFormuleConcessionario.SEGNAPOSTO;
	String expected = "1 * 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
