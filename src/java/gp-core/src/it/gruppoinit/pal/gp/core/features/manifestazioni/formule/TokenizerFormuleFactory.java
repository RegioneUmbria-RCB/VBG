package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneBlocchiConsecutivi;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneBloccoSeguitoDaOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneDestraOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneNumeroSeguitoDaOperatore;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneOperatoreFineFormula;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneOperatoreInizioFormula;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneVariabile;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione.RegolaValidazioneVariabileSeguitaDaOperatore;

public class TokenizerFormuleFactory {

    private List<IRegolaValidazione> regoleValidazione = new ArrayList<IRegolaValidazione>(0);

    public TokenizerFormuleFactory(List<String> variabiliAmmesse) {

	regoleValidazione.add(new RegolaValidazioneBlocchiConsecutivi());
	regoleValidazione.add(new RegolaValidazioneBloccoSeguitoDaOperatore());
	regoleValidazione.add(new RegolaValidazioneDestraOperatore());
	regoleValidazione.add(new RegolaValidazioneOperatoreFineFormula());
	regoleValidazione.add(new RegolaValidazioneOperatoreInizioFormula());
	regoleValidazione.add(new RegolaValidazioneVariabile(variabiliAmmesse));
	regoleValidazione.add(new RegolaValidazioneVariabileSeguitaDaOperatore());
	regoleValidazione.add(new RegolaValidazioneNumeroSeguitoDaOperatore());
    }

    public TokenizerFormule getTokenizerFormule() {

	return new TokenizerFormule(regoleValidazione);
    }
}
