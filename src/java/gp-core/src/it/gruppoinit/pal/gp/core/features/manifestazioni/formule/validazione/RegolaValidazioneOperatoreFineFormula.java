package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class RegolaValidazioneOperatoreFineFormula extends AbstractRegolaValidazione {

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (lastToken == null) {
	    return true;
	}
	if (lastToken.getTipo() != TipoToken.OPERATORE) {
	    return true;
	}
	if (currToken == null) {
	    super.setMessaggio("La formula non può finire con un operatore");
	    return false;
	}
	return true;
    }
}
