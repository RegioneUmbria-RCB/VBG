package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.token;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.TipoToken;

public class Numero extends TokenBase {

    String valore;
    BigDecimal valoreDecimal;

    public Numero(String valore) throws TokenSintatticoNonValidoException {

	this.valore = valore;
	try {
	    this.valoreDecimal = new BigDecimal(valore);
	} catch (Exception ex) {
	    throw new TokenSintatticoNonValidoException("Il valore " + valore + " non è una rappresentazione numerica valida");
	}
    }

    @Override
    public String getValore() {

	return this.valore;
    }

    @Override
    public TipoToken getTipo() {

	return TipoToken.NUMERO;
    }
}
