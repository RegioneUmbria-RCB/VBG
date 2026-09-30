/*
 * MIP API - WS Conversion Libreria API del Modulo Incassi e Pagamenti del Comune di Genova per operazioni su Avvisi di
 * Pagamento
 *
 * OpenAPI spec version: 0.0.94 Contact: helpservizionline@comune.genova.it
 *
 */
package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

/**
 * Gets or Sets EsitoPagamento
 */
public enum EsitoPagamento {

    OK("OK"),
    CODE9("CODE9"),
    KO("KO");

    private String value;

    EsitoPagamento(String value) {

	this.value = value;
    }

    public String getValue() {

	return value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static EsitoPagamento fromValue(String input) {

	for (EsitoPagamento b : EsitoPagamento.values()) {
	    if (b.value.equals(input)) {
		return b;
	    }
	}
	return null;
    }
}
