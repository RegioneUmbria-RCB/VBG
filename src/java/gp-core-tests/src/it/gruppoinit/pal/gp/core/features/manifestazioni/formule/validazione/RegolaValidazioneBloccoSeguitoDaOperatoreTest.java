package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.AlberoSintattico;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Blocco;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Numero;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;

public class RegolaValidazioneBloccoSeguitoDaOperatoreTest {

    @Test
    public void analizza_token_consecutivi_se_blocco_seguito_da_operatore_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Blocco(new AlberoSintattico(null, "([PIPPO] * 1.22)"));
	IToken currToken = new Operatore("*");
	RegolaValidazioneBloccoSeguitoDaOperatore regolaValidazioneBloccoSeguitoDaOperatore = new RegolaValidazioneBloccoSeguitoDaOperatore();
	Assert.assertEquals(true, regolaValidazioneBloccoSeguitoDaOperatore.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_consecutivi_se_blocco_seguito_da_non_operatore_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Blocco(new AlberoSintattico(null, "([PIPPO] * 1.22)"));
	IToken currToken = new Numero("2.42");
	RegolaValidazioneBloccoSeguitoDaOperatore regolaValidazioneBloccoSeguitoDaOperatore = new RegolaValidazioneBloccoSeguitoDaOperatore();
	Assert.assertEquals(false, regolaValidazioneBloccoSeguitoDaOperatore.valida(lastToken, currToken));
	Assert.assertEquals("Un blocco deve seguire un operatore: Errore(BLOCCO -> NUMERO)",
		regolaValidazioneBloccoSeguitoDaOperatore.getErroriValidazione());
    }
}
