package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.validazione;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRegolaValidazione;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;

public abstract class AbstractRegolaValidazione implements IRegolaValidazione {

    private String messaggio;

    @Override
    public String getErroriValidazione() {

	return messaggio;
    }

    @Override
    public abstract boolean valida(IToken lastToken, IToken currToken);

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
