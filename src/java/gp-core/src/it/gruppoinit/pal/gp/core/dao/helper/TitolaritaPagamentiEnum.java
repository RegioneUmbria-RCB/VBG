package it.gruppoinit.pal.gp.core.dao.helper;

public enum TitolaritaPagamentiEnum {
    PRIMO_CONCESSIONARIO("Al primo concessionario del periodo"),
    CONCESSIONARIO_ATTUALE("All'attuale concessionario"),
    PRESENZE_EFFETTIVE("In base alle presenze fatte");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private TitolaritaPagamentiEnum(String valore) {

	this.valore = valore;
    }

    public static TitolaritaPagamentiEnum fromValore(String valore) {

	for (TitolaritaPagamentiEnum c : TitolaritaPagamentiEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(valore);
    }
}
