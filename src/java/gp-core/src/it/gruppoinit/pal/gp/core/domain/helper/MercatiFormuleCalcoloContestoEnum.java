package it.gruppoinit.pal.gp.core.domain.helper;

public enum MercatiFormuleCalcoloContestoEnum {

    PRESENZA("Presenza"),
    BOLLETTAZIONE("Bollettazione");

    private final String descrizione;
    private final String id;

    MercatiFormuleCalcoloContestoEnum(String v) {

	descrizione = v;
	id = name();
    }

    public static MercatiFormuleCalcoloContestoEnum fromValue(String v) {

	for (MercatiFormuleCalcoloContestoEnum c : MercatiFormuleCalcoloContestoEnum.values()) {
	    if (c.descrizione.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }

    public String getId() {

	return id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    @Override
    public String toString() {

	return name();
    }
}
