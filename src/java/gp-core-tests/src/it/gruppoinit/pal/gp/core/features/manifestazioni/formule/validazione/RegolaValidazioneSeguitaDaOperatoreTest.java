package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Numero;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneSeguitaDaOperatoreTest {

    @Test
    public void analizza_token_consecutivi_se_variabile_seguito_da_operatore_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Variabile("[PIPPO]");
	IToken currToken = new Operatore("*");
	RegolaValidazioneVariabileSeguitaDaOperatore regolaValidazioneVariabileSeguitaDaOperatore = new RegolaValidazioneVariabileSeguitaDaOperatore();
	Assert.assertEquals(true, regolaValidazioneVariabileSeguitaDaOperatore.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_consecutivi_se_variabile_seguito_da_non_operatore_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Variabile("[PIPPO]");
	IToken currToken = new Numero("1.22");
	RegolaValidazioneVariabileSeguitaDaOperatore regolaValidazioneVariabileSeguitaDaOperatore = new RegolaValidazioneVariabileSeguitaDaOperatore();
	Assert.assertEquals(false, regolaValidazioneVariabileSeguitaDaOperatore.valida(lastToken, currToken));
	Assert.assertEquals("Un segnaposto deve seguire un operatore: errore -> VARIABILE -> NUMERO",
		regolaValidazioneVariabileSeguitaDaOperatore.getErroriValidazione());
    }
}
