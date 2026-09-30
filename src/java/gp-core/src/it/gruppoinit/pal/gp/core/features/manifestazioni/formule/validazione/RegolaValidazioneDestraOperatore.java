package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class RegolaValidazioneDestraOperatore extends AbstractRegolaValidazione {

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (lastToken == null || currToken == null) {
	    return true;
	}
	if (lastToken.getTipo() != TipoToken.OPERATORE) {
	    return true;
	}
	switch (currToken.getTipo()) {
	case BLOCCO:
	case NUMERO:
	case VARIABILE:
	    return true;
	default:
	    break;
	}
	super.setMessaggio("Un operatore deve seguire un blocco, un numero o una variabile: Errore(" +
		lastToken.getTipo().name() +
		" -> " +
		currToken.getTipo().name() +
		")");
	return false;
    }
}
