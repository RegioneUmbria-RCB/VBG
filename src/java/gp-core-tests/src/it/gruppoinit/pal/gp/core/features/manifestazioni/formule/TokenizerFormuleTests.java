package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.NullRegolaValidazione;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneBlocchiConsecutivi;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneBloccoSeguitoDaOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneDestraOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneNumeroSeguitoDaOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneOperatoreFineFormula;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneOperatoreInizioFormula;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneVariabile;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneVariabileSeguitaDaOperatore;

public class TokenizerFormuleTests {

    TokenizerFormule tokenizer;

    @Before
    public void initialize() {

	List<String> variabiliAmmesse = new ArrayList<String>(0);
	variabiliAmmesse.add("CARRELLI");
	variabiliAmmesse.add("MAGAZZINO");
	variabiliAmmesse.add("ALLACCIO_IDRICO");
	List<IRegolaValidazione> regoleValidazione = new ArrayList<IRegolaValidazione>();
	regoleValidazione.add(new NullRegolaValidazione());
	regoleValidazione.add(new RegolaValidazioneBlocchiConsecutivi());
	regoleValidazione.add(new RegolaValidazioneBloccoSeguitoDaOperatore());
	regoleValidazione.add(new RegolaValidazioneDestraOperatore());
	regoleValidazione.add(new RegolaValidazioneOperatoreFineFormula());
	regoleValidazione.add(new RegolaValidazioneOperatoreInizioFormula());
	regoleValidazione.add(new RegolaValidazioneVariabile(variabiliAmmesse));
	regoleValidazione.add(new RegolaValidazioneVariabileSeguitaDaOperatore());
	regoleValidazione.add(new RegolaValidazioneNumeroSeguitoDaOperatore());
	this.tokenizer = new TokenizerFormule(regoleValidazione);
    }

    @Test
    public void analizza_restituisce_struttura_sintattica() throws ValidazioneFormulaFallitaException {

	String formula = "[CARRELLI] + 1";
	//TokenizerFormule tokenizer = new TokenizerFormule();
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 3 token", 3, albero.contaToken());
	Assert.assertEquals("Token 1 è variabile", TipoToken.VARIABILE, albero.tokenAt(0).getTipo());
	Assert.assertEquals("Token 2 è operatore", TipoToken.OPERATORE, albero.tokenAt(1).getTipo());
	Assert.assertEquals("Token 3 è numero", TipoToken.NUMERO, albero.tokenAt(2).getTipo());
    }

    @Test
    public void analizza_restituisce_struttura_sintattica2() throws ValidazioneFormulaFallitaException {

	String formula = "[CARRELLI] * 1.234 + [MAGAZZINO]";
	//TokenizerFormule tokenizer = new TokenizerFormule();
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 5 token", 5, albero.contaToken());
	Assert.assertEquals("Token 1 è variabile", TipoToken.VARIABILE, albero.tokenAt(0).getTipo());
	Assert.assertEquals("Token 2 è operatore", TipoToken.OPERATORE, albero.tokenAt(1).getTipo());
	Assert.assertEquals("Token 3 è numero", TipoToken.NUMERO, albero.tokenAt(2).getTipo());
	Assert.assertEquals("Token 4 è operatore", TipoToken.OPERATORE, albero.tokenAt(3).getTipo());
	Assert.assertEquals("Token 5 è numero", TipoToken.VARIABILE, albero.tokenAt(4).getTipo());
    }

    @Test
    public void analizza_con_stringa_vuota_restituisce_albero_vuoto() throws ValidazioneFormulaFallitaException {

	String formula = "";
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 0 token", 0, albero.contaToken());
    }

    @Test
    public void analizza_con_stringa_solo_spazi_restituisce_albero_vuoto() throws ValidazioneFormulaFallitaException {

	String formula = "   ";
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 0 token", 0, albero.contaToken());
    }

    @Test(expected = RuntimeException.class)
    public void analizza_con_stringa_con_parentesi_sollevaeccezione_se_non_bilanciate() throws ValidazioneFormulaFallitaException {

	String formula = "1 * ([MAGAZZINO] + ( 3.45 * ([CARRELLI] + 9.45))";
	AlberoSintattico albero = tokenizer.analizza(formula);
    }

    @Test()
    public void analizza_con_stringa_con_parentesi_bilanciate_non_sollevaeccezione() throws ValidazioneFormulaFallitaException {

	String formula = "1 * ([ALLACCIO_IDRICO] + ( 3.45 * ([CARRELLI] + 9.45)))";
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 9 token", 9, albero.totaleTokenInclusiAnnidati());
    }

    @Test(expected = ValidazioneFormulaFallitaException.class)
    public void analizza_con_stringa_con_segnaposti_non_validi() throws ValidazioneFormulaFallitaException {

	String formula = "1 * ([CARRELLI] + ( 3.45 * ([PIPPO] + 9.45)))";
	AlberoSintattico albero = tokenizer.analizza(formula);
    }

    @Test(expected = RuntimeException.class)
    public void analizza_con_stringa_con_numeri_non_validi() throws ValidazioneFormulaFallitaException {

	String formula = "3.4$5 * 1";
	AlberoSintattico albero = tokenizer.analizza(formula);
    }

    @Test(expected = ValidazioneFormulaFallitaException.class)
    public void analizza_formula_con_blocchi_consecutivi() throws ValidazioneFormulaFallitaException {

	String formula = "(1*2)(2+2)";
	try {
	    AlberoSintattico albero = tokenizer.analizza(formula);
	} catch (ValidazioneFormulaFallitaException e) {
	    Assert.assertEquals("Non possono esistere due segnaposto consecutivi di tipo: BLOCCO", e.getMessage());
	    throw e;
	}
    }

    @Test(expected = RuntimeException.class)
    public void analizza_formula_con_errore_parentesi_non_bilanciate() throws ValidazioneFormulaFallitaException {

	String formula = "[ALLACCIO_IDRICO])";
	tokenizer.analizza(formula);
    }

    @Test()
    public void analizza_formula_con_parentesi_blocco_in_fondo() throws ValidazioneFormulaFallitaException {

	String formula = "([ALLACCIO_IDRICO])";
	AlberoSintattico albero = tokenizer.analizza(formula);
	Assert.assertEquals("Contiene 1 token", 1, albero.totaleTokenInclusiAnnidati());
    }
}