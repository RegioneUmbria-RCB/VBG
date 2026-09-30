package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

public enum StatoBorsellinoEnum {

    ATTIVO("ATTIVO"),
    NONATTIVO("NON ATTIVO");

    private final String valore;

    public String getValore() {

	return valore;
    }

    StatoBorsellinoEnum(String valore) {

	this.valore = valore;
    }

    public static StatoBorsellinoEnum fromValue(String valore) {

	for (StatoBorsellinoEnum c : StatoBorsellinoEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }
}
