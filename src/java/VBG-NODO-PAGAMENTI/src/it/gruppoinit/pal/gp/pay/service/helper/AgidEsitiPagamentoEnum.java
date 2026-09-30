package it.gruppoinit.pal.gp.pay.service.helper;

import org.apache.commons.lang.StringUtils;

public enum AgidEsitiPagamentoEnum {

    //	    0 Pagamento eseguito
    //	    1 Pagamento non eseguito
    //	    2 Pagamento parzialmente eseguito
    //	    3 Decorrenza termini
    //	    4 Decorrenza termini parziale
    PAGAMENTO_ESEGUITO("0"), // 
    PAGAMENTO_NON_ESEGUITO("1"), //
    PAGAMENTO_PARZIALMENTE_ESEGUITO("2"), // 
    DECORRENZA_TERMINI("3"), //
    DECORRENZA_TERMINI_PARZIALE("4");

    private String valore;

    private AgidEsitiPagamentoEnum(String valore) {

	this.valore = valore;
    }

    public String getValore() {

	return valore;
    }

    public static AgidEsitiPagamentoEnum fromValore(String valore) {

	valore = StringUtils.defaultIfBlank(valore, "-1");
	if (valore.equals("0")) {
	    return PAGAMENTO_ESEGUITO;
	} else if (valore.equals("1")) {
	    return PAGAMENTO_NON_ESEGUITO;
	}
	if (valore.equals("2")) {
	    return PAGAMENTO_PARZIALMENTE_ESEGUITO;
	}
	if (valore.equals("3")) {
	    return DECORRENZA_TERMINI;
	}
	if (valore.equals("4")) {
	    return DECORRENZA_TERMINI_PARZIALE;
	}
	throw new IllegalArgumentException("Valore non valido " + valore);
    }
}
