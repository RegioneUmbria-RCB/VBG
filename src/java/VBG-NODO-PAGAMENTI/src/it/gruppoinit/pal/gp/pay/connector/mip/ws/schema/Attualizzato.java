/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

/**
 * Gets or Sets Attualizzato
 */
public enum Attualizzato {

    NONRICHIESTO("NONRICHIESTO"),
    CAMBIATO("CAMBIATO"),
    NONCAMBIATO("NONCAMBIATO"),
    NONDISPONIBILE("NONDISPONIBILE"),
    NONAGGIORNABILE("NONAGGIORNABILE");

    private String value;

    Attualizzato(String value) {

	this.value = value;
    }

    public String getValue() {

	return value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static Attualizzato fromValue(String input) {

	for (Attualizzato b : Attualizzato.values()) {
	    if (b.value.equals(input)) {
		return b;
	    }
	}
	return null;
    }
}
