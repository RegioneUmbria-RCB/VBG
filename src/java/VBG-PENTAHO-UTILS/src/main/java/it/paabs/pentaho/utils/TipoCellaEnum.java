package it.paabs.pentaho.utils;

public enum TipoCellaEnum {

    TESTO("Testo"),
    NUMERICOINTERO("NumericoIntero"),
    NUMERICODOUBLE("NumericoDouble"),
    DATA("Data"),
    RICERCA("Ricerca");

    private String valore;

    private TipoCellaEnum(String valore) {

	this.valore = valore;
    }

    public String valore() {

	return valore;
    }

    public static TipoCellaEnum fromValore(String valore) {

	TipoCellaEnum[] values = values();
	for (TipoCellaEnum tipoCellaEnum : values) {
	    if (tipoCellaEnum.valore.equalsIgnoreCase(valore)) {
		return tipoCellaEnum;
	    }
	}
	return TipoCellaEnum.TESTO;
    }
}
