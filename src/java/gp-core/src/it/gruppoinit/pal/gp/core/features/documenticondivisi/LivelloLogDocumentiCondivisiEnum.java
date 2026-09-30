package it.gruppoinit.pal.gp.core.features.documenticondivisi;

public enum LivelloLogDocumentiCondivisiEnum {
    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private LivelloLogDocumentiCondivisiEnum(String valore) {

	this.valore = valore;
    }

    public static LivelloLogDocumentiCondivisiEnum fromValue(String v) {

	for (LivelloLogDocumentiCondivisiEnum c : LivelloLogDocumentiCondivisiEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
