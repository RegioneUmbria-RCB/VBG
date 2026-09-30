package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import org.junit.Test;

public class NumeroTests {

    @Test(expected = TokenSintatticoNonValidoException.class)
    public void costruttore_solleva_eccezione_se_numero_non_valido() throws TokenSintatticoNonValidoException {

	String valore = "1.234.56";
	Numero numero = new Numero(valore);
    }
}
