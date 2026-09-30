package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.List;

import it.gruppoinit.pal.gp.core.features.bollettazione.BollettazioneNonConsentitaException;
import it.gruppoinit.pal.gp.core.features.bollettazione.IRegolaAmmissione;

public class RegoleAmmissioneMercati {

    List<IRegolaAmmissione> regole;

    public RegoleAmmissioneMercati(List<IRegolaAmmissione> regole) {

	this.regole = regole;
    }

    public void valida() throws BollettazioneNonConsentitaException {

    }
}
