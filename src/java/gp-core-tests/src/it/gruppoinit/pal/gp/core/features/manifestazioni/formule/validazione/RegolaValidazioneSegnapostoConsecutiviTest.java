package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneSegnapostoConsecutiviTest {

    @Test
    public void analizza_formula_con_segnaposto_consecutivi_ritorna_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Variabile("[PIPPO]");
	IToken currToken = new Variabile("[PIPPO2]");
	RegolaValidazioneBlocchiConsecutivi regolaValidazioneBlocchiConsecutivi = new RegolaValidazioneBlocchiConsecutivi();
	boolean actual = regolaValidazioneBlocchiConsecutivi.valida(lastToken, currToken);
	Assert.assertEquals(false, actual);
	Assert.assertEquals("Non possono esistere due segnaposto consecutivi di tipo: VARIABILE",
		regolaValidazioneBlocchiConsecutivi.getErroriValidazione());
    }

    @Test
    public void analizza_formula_con_segnaposto_non_consecutivi_ritorna_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Variabile("[PIPPO]");
	IToken currToken = new Operatore("*");
	RegolaValidazioneBlocchiConsecutivi regolaValidazioneBlocchiConsecutivi = new RegolaValidazioneBlocchiConsecutivi();
	Assert.assertEquals(true, regolaValidazioneBlocchiConsecutivi.valida(lastToken, currToken));
    }
}
