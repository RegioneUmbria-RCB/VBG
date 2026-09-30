package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

public enum StatoDebito {

    PAGABILE(0),
    PAGATO(1),
    ANNULLATO(2),
    SCADUTO(3);

    private StatoDebito(int value) {

	this.value = value;
    }

    private int value;

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static StatoDebito fromValue(int value) {

	for (StatoDebito v : StatoDebito.values()) {
	    if (v.value == value) {
		return v;
	    }
	}
	return null;
    }
}
