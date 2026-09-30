package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

public enum StatiCodaEnum {

    DA_PROCESSARE,
    INVIATA_A_GATEWAY,
    NOTIFICATA_APPIO("ACCEPTED"),
    IN_LAVORAZIONE("THROTTOLED"),
    NOTIFICATA_UTENTE("PROCESSED"),
    ERRORE("FAILED/REJECTED");

    private String value;

    private StatiCodaEnum() {

    }

    private StatiCodaEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }

    public static StatiCodaEnum fromName(String v) {

	for (StatiCodaEnum b : StatiCodaEnum.values()) {
	    if (b.value.equalsIgnoreCase(v)) {
		return b;
	    }
	}
	return null;
    }
}
