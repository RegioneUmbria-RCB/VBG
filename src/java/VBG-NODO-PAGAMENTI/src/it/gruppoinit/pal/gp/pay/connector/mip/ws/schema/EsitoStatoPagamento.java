package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

public enum EsitoStatoPagamento {

    PAGAMENTO_ESEGUITO(0),
    PAGAMENTO_NON_ESEGUITO(1),
    PAGAMENTO_PARZIALMENTE_ESEGUITO(2),
    DECORRENZA_TERMINI(3),
    DECORRENZA_TERMINI_PARZIALE(4);

    private int value;

    EsitoStatoPagamento(int value) {

	this.value = value;
    }

    public int getValue() {

	return value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    public static EsitoStatoPagamento fromValue(int input) {

	for (EsitoStatoPagamento b : EsitoStatoPagamento.values()) {
	    if (b.value == input) {
		return b;
	    }
	}
	return null;
    }
}
