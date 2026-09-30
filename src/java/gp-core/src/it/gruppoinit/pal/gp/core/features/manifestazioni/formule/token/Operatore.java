package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class Operatore extends TokenBase {

    String valore;

    public Operatore(String valore) {

	this.valore = valore;
    }

    @Override
    public String getValore() {

	return this.valore;
    }

    @Override
    public TipoToken getTipo() {

	return TipoToken.OPERATORE;
    }
}
