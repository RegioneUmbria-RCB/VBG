package it.gruppoinit.pal.gp.core.dao.helper;

public enum PeriodiEnum {
    MENSILE("Mensile"), BIMESTRALE("Bimestrale"), TRIMESTRALE("Trimestrale"), QUADRIMESTRALE("Quadrimestrale"), SEMESTRALE("Semestrale"), ANNUALE(
	    "Annuale"), BIENNALE("Biennale"), TRIENNALE("Triennale");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private PeriodiEnum(String valore) {

	this.valore = valore;
    }

    public static PeriodiEnum fromValue(String v) {

	for (PeriodiEnum c : PeriodiEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
