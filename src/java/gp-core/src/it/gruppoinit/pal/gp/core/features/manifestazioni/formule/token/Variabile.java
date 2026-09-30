package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class Variabile extends TokenBase {

    String valore;
    String valoreBreve;

    public Variabile(String valore) throws TokenSintatticoNonValidoException {

	if (valore.length() <= 2) {
	    throw new TokenSintatticoNonValidoException("Il valore " + valore + " non è un token di tipo variabile valido");
	}
	this.valore = valore.substring(1, valore.length() - 1);
	if (!this.valore.matches("^[A-Z\\d_]+$")) {
	    throw new TokenSintatticoNonValidoException("Il nome di variabile " + this.valore + " contiene caratteri non validi");
	}
    }

    @Override
    public String getValore() {

	return this.valore;
    }

    @Override
    public TipoToken getTipo() {

	return TipoToken.VARIABILE;
    }
}
