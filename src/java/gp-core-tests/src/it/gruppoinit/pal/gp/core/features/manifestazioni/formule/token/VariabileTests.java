package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import org.junit.Assert;
import org.junit.Test;

public class VariabileTests {

    @Test
    public void costruttore_rimuove_le_parentesi_da_valore() throws TokenSintatticoNonValidoException {

	String valore = "[PIPPO]";
	String expected = "PIPPO";
	Variabile variabile = new Variabile(valore);
	Assert.assertEquals("Le parentesi vengono rimosse", expected, variabile.getValore());
    }

    @Test(expected = TokenSintatticoNonValidoException.class)
    public void costruttore_verifica_che_il_nome_variabile_non_contenga_caratteri_non_validi() throws TokenSintatticoNonValidoException {

	String valore = "[PIPPO$]";
	Variabile variabile = new Variabile(valore);
    }
}
