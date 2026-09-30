package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;

public class RegolaValidazioneBlocchiConsecutivi extends AbstractRegolaValidazione {

    @Override
    public boolean valida(IToken lastToken, IToken currToken) {

	if (lastToken == null || currToken == null) {
	    return true;
	}
	if (lastToken.getTipo() != currToken.getTipo()) {
	    return true;
	}
	super.setMessaggio("Non possono esistere due segnaposto consecutivi di tipo: " + lastToken.getTipo().name());
	return false;
    }
}
