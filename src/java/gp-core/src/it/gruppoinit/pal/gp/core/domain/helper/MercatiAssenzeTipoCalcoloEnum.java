package it.gruppoinit.pal.gp.core.domain.helper;

public enum MercatiAssenzeTipoCalcoloEnum {

    TOTALITA_POSTEGGI("Sulla totalità dei posteggi"),
    SOLO_POSTEGGI_IN_CONCESSIONE("Solo sui posteggi in concessione");

    private String valore;

    MercatiAssenzeTipoCalcoloEnum(String valore) {

	this.valore = valore;
    }

    public static MercatiAssenzeTipoCalcoloEnum fromValue(String v) {

	for (MercatiAssenzeTipoCalcoloEnum c : MercatiAssenzeTipoCalcoloEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }

    public String getValore() {

	return valore;
    }
}
