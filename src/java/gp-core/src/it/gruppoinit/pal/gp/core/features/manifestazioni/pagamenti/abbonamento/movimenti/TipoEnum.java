package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti;

public enum TipoEnum {

    RICARICA("RICARICA"),
    USCITA("USCITA"),
    STORNO("STORNO"),
    RIMBORSO("RIMBORSO");

    private final String valore;

    TipoEnum(String valore) {

	this.valore = valore;
    }

    public static TipoEnum TipoPerPagamenti() {

	return TipoEnum.USCITA;
    }

    public static TipoEnum fromValue(String valore) {

	for (TipoEnum c : TipoEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }
}
