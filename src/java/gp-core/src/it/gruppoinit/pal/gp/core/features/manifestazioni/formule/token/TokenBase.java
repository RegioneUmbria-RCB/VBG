package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IToken;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public abstract class TokenBase implements IToken {

    @Override
    public abstract String getValore();

    @Override
    public abstract TipoToken getTipo();

    public int getValoreConteggio() {

	return 1;
    }
}
