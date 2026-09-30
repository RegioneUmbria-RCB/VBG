package it.gruppoinit.pal.gp.core.dao.helper;

public enum ImplementazioniEnum {
    MERCATI("Mercati"), ISTANZE("Istanze");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private ImplementazioniEnum(String valore) {

	this.valore = valore;
    }

    public static ImplementazioniEnum fromValore(String valore) {

	for (ImplementazioniEnum c : ImplementazioniEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(valore);
    }
}
