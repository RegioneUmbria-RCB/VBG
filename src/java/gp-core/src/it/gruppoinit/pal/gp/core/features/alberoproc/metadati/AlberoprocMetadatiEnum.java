package it.gruppoinit.pal.gp.core.features.alberoproc.metadati;

public enum AlberoprocMetadatiEnum {

    DOWNLOAD_PRATICA_ZIP_NOMEFILE("DOWNLOAD_PRATICA_ZIP_NOMEFILE"),
    FRONTEND_TITOLO_SERVIZIO("FRONTEND_TITOLO_SERVIZIO"),
    FRONTEND_SOTTOTITOLO_SERVIZIO("FRONTEND_SOTTOTITOLO_SERVIZIO"),
    WS_ATTI_CODICE_TRATTAMENTO("WS_ATTI.CODICE_TRATTAMENTO");

    private final String value;

    AlberoprocMetadatiEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }

    public static AlberoprocMetadatiEnum fromValue(String v) {

	for (AlberoprocMetadatiEnum c : AlberoprocMetadatiEnum.values()) {
	    if (c.value.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
