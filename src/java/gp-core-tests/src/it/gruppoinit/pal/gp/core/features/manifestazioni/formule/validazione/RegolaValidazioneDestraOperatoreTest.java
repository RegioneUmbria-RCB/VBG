package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.AlberoSintattico;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Blocco;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Operatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Spaziatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;

public class RegolaValidazioneDestraOperatoreTest {

    @Test
    public void analizza_token_con_operatore_seguito_da_blocconumerovariabile_ritorna_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Operatore("*");
	IToken currToken = new Blocco(new AlberoSintattico(null, "([PIPPO] +1.22)"));
	RegolaValidazioneDestraOperatore regolaValidazioneDestraOperatore = new RegolaValidazioneDestraOperatore();
	Assert.assertEquals(true, regolaValidazioneDestraOperatore.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_con_operatore_seguito_da_non_blocconumerovariabile_ritorna_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = new Operatore("*");
	IToken currToken = new Spaziatore();
	RegolaValidazioneDestraOperatore regolaValidazioneDestraOperatore = new RegolaValidazioneDestraOperatore();
	Assert.assertEquals(false, regolaValidazioneDestraOperatore.valida(lastToken, currToken));
	Assert.assertEquals("Un operatore deve seguire un blocco, un numero o una variabile: Errore(OPERATORE -> SPAZIATORE)",
		regolaValidazioneDestraOperatore.getErroriValidazione());
    }
}
