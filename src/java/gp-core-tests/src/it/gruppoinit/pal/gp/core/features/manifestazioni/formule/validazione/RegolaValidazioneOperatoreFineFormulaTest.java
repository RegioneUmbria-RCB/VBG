package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneOperatoreFineFormulaTest {

    @Test
    public void analizza_token_con_operatore_fine_formula_e_senza_currToken_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Operatore("*");
	IToken currToken = null;
	RegolaValidazioneOperatoreFineFormula regolaValidazioneOperatoreFineFormula = new RegolaValidazioneOperatoreFineFormula();
	Assert.assertEquals(false, regolaValidazioneOperatoreFineFormula.valida(lastToken, currToken));
	Assert.assertEquals("La formula non può finire con un operatore", regolaValidazioneOperatoreFineFormula.getErroriValidazione());
    }

    @Test
    public void analizza_token_con_operatore_non_fine_formula_e_senza_currToken_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Variabile("[PIPPO]");
	IToken currToken = null;
	RegolaValidazioneOperatoreFineFormula regolaValidazioneOperatoreFineFormula = new RegolaValidazioneOperatoreFineFormula();
	Assert.assertEquals(true, regolaValidazioneOperatoreFineFormula.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_con_operatore_fine_formula_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Operatore("*");
	IToken currToken = null;
	RegolaValidazioneOperatoreFineFormula regolaValidazioneOperatoreFineFormula = new RegolaValidazioneOperatoreFineFormula();
	Assert.assertEquals(false, regolaValidazioneOperatoreFineFormula.valida(lastToken, currToken));
    }
}
