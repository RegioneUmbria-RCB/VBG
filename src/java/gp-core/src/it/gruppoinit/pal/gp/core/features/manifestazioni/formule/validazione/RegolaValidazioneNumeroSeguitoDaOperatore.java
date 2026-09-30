package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class RegolaValidazioneNumeroSeguitoDaOperatore extends AbstractRegolaValidazione {

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (lastToken == null || currToken == null) {
	    return true;
	}
	if (lastToken.getTipo() != TipoToken.NUMERO) {
	    return true;
	}
	if (currToken.getTipo() == TipoToken.OPERATORE) {
	    return true;
	}
	super.setMessaggio("Un numero deve seguire un opertore: Errore(" + lastToken.getTipo().name() + " -> " + currToken.getTipo().name() + ")");
	return false;
    }
}
