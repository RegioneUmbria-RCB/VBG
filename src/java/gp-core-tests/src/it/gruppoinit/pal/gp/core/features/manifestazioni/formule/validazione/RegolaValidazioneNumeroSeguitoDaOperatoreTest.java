package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Numero;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneNumeroSeguitoDaOperatoreTest {

    @Test
    public void analizza_token_con_numero_seguito_da_operatore_ritorna_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Numero("1.22");
	IToken currToken = new Operatore("*");
	RegolaValidazioneNumeroSeguitoDaOperatore regolaValidazioneNumeroSeguitoDaOperatore = new RegolaValidazioneNumeroSeguitoDaOperatore();
	Assert.assertEquals(true, regolaValidazioneNumeroSeguitoDaOperatore.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_con_numero_seguito_da_non_operatore_ritorna_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Numero("1.22");
	IToken currToken = new Variabile("[PIPPO]");
	RegolaValidazioneNumeroSeguitoDaOperatore regolaValidazioneNumeroSeguitoDaOperatore = new RegolaValidazioneNumeroSeguitoDaOperatore();
	Assert.assertEquals(false, regolaValidazioneNumeroSeguitoDaOperatore.valida(lastToken, currToken));
	Assert.assertEquals("Un numero deve seguire un opertore: Errore(NUMERO -> VARIABILE)",
		regolaValidazioneNumeroSeguitoDaOperatore.getErroriValidazione());
    }
}
