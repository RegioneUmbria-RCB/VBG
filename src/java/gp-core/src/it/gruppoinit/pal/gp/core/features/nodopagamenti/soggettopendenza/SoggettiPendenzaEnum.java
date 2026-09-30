package it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza;

public enum SoggettiPendenzaEnum {

    AZIENDA("Azienda"),
    RICHIEDENTE("Richiedente");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private SoggettiPendenzaEnum(String valore) {

	this.valore = valore;
    }

    public static SoggettiPendenzaEnum fromValue(String v) {

	for (SoggettiPendenzaEnum c : SoggettiPendenzaEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
