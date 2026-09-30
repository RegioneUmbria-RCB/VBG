package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.TokenSintatticoNonValidoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token.Variabile;

public class RegolaValidazioneVariabileTest {

    @Test
    public void analizza_token_con_variabile_valido_ritorna_true() throws TokenSintatticoNonValidoException {

	IToken lastToken = null;
	IToken currToken = new Variabile("[PIPPO]");
	List<String> livelliServiziEVarGlobali = new ArrayList<String>();
	livelliServiziEVarGlobali.add("GG");
	livelliServiziEVarGlobali.add("PIPPO");
	RegolaValidazioneVariabile regolaValidazioneVariabile = new RegolaValidazioneVariabile(livelliServiziEVarGlobali);
	Assert.assertEquals(true, regolaValidazioneVariabile.valida(lastToken, currToken));
    }

    @Test
    public void analizza_token_con_variabile_non_valido_ritorna_false() throws TokenSintatticoNonValidoException {

	IToken lastToken = null;
	IToken currToken = new Variabile("[BOBBO]");
	List<String> livelliServiziEVarGlobali = new ArrayList<String>();
	livelliServiziEVarGlobali.add("GG");
	livelliServiziEVarGlobali.add("PIPPO");
	RegolaValidazioneVariabile regolaValidazioneVariabile = new RegolaValidazioneVariabile(livelliServiziEVarGlobali);
	Assert.assertEquals(false, regolaValidazioneVariabile.valida(lastToken, currToken));
	Assert.assertEquals("Il segnaposto BOBBO non è valido", regolaValidazioneVariabile.getErroriValidazione());
    }
}
