package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

public enum ResponsabileIstanzaEnum {

    RESPONSABILE_ISTRUTTORIA("RESPONSABILE_ISTRUTTORIA"),
    RESPONSABILE_PROCEDIMENTO("RESPONSABILE_PROCEDIMENTO");

    private final String value;

    ResponsabileIstanzaEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }

    public static ResponsabileIstanzaEnum fromName(String v) {

	for (ResponsabileIstanzaEnum b : ResponsabileIstanzaEnum.values()) {
	    if (b.value.equalsIgnoreCase(v)) {
		return b;
	    }
	}
	return null;
    }
}
