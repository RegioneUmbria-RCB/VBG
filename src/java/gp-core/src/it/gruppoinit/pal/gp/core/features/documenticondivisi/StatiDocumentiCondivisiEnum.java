package it.gruppoinit.pal.gp.core.features.documenticondivisi;

public enum StatiDocumentiCondivisiEnum {
    DA_CONDIVIDERE("DA CONDIVIDERE"),
    CONDIVISO("CONDIVISO");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private StatiDocumentiCondivisiEnum(String valore) {

	this.valore = valore;
    }

    public static StatiDocumentiCondivisiEnum fromValue(String v) {

	for (StatiDocumentiCondivisiEnum c : StatiDocumentiCondivisiEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
