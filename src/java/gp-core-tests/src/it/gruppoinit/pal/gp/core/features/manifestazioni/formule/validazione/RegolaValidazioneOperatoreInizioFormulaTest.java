package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneOperatoreInizioFormulaTest {

    @Test
    public void analizza_token_con_operatore_inizio_formula_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = null;
	IToken currToken = new Operatore("*");
	RegolaValidazioneOperatoreInizioFormula regolaValidazioneOperatoreInizioFormula = new RegolaValidazioneOperatoreInizioFormula();
	Assert.assertEquals(false, regolaValidazioneOperatoreInizioFormula.valida(lastToken, currToken));
	Assert.assertEquals("La formula non può iniziare con un operatore", regolaValidazioneOperatoreInizioFormula.getErroriValidazione());
    }

    @Test
    public void analizza_token_con_operatore_non_inizio_formula_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = null;
	IToken currToken = new Variabile("[PIPPO]");
	RegolaValidazioneOperatoreInizioFormula regolaValidazioneOperatoreInizioFormula = new RegolaValidazioneOperatoreInizioFormula();
	Assert.assertEquals(true, regolaValidazioneOperatoreInizioFormula.valida(lastToken, currToken));
    }
}
