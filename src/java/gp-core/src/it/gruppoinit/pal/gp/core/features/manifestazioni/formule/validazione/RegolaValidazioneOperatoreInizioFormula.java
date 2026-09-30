package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class RegolaValidazioneOperatoreInizioFormula extends AbstractRegolaValidazione {

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (lastToken != null) {
	    return true;
	}
	if (currToken.getTipo() == TipoToken.OPERATORE) {
	    super.setMessaggio("La formula non può iniziare con un operatore");
	    return false;
	}
	return true;
    }
}
