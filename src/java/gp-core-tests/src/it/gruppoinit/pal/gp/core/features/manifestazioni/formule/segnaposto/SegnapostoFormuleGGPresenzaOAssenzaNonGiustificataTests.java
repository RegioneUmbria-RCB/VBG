package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.junit.Assert;
import org.junit.Test;

public class SegnapostoFormuleGGPresenzaOAssenzaNonGiustificataTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("", false);
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("", false);
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_null_e_false_torna_0() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata(null, false);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_null_e_true_torna_0() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata(null, true);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_PRESENZE_e_false_torna_1() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("PrEsEnZe", false);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_PRESENZE_e_true_torna_1() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("PrEsEnZe", true);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_ASSENZE_e_false_torna_1() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("aSsEnZe", false);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 1";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_ASSENZE_e_true_torna_0() {

	SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata segnaposto = new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata("aSsEnZe", true);
	String formula = "GG = " + SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO;
	String expected = "GG = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
