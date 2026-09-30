package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

public enum TipoScadenzaEnum {

    FINE_MESE(0),
    QUINDICI_DEL_MESE(1),
    FINE_MESE_ESCLUSA_PRIMA_RATA(2),
    QUINDICI_DEL_MESE_ESCLUSA_PRIMA_RATA(3),
    LASCIA_INALTERATO(4),
    MESE_SUCCESSIVO_IL_GIORNO_15(5),
    MESE_SUCCESSIVO_IL_GIORNO_15_ESCLUSA_PRIMA_RATA(6),
    INIZIO_MESE(7),
    FINE_MESE_SUCCESSIVO(8),
    SCADENZA_PERIODICA_FISSA(9),
    VENTI_DEL_MESE(10);

    private final Integer valore;

    TipoScadenzaEnum(Integer valore) {

	this.valore = valore;
    }

    public Integer getValore() {

	return this.valore;
    }

    public static TipoScadenzaEnum fromValue(Integer v) {

	for (TipoScadenzaEnum c : TipoScadenzaEnum.values()) {
	    if (c.getValore().equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v.toString());
    }
}
