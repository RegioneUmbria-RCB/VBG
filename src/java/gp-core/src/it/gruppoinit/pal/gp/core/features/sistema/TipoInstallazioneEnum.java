package it.gruppoinit.pal.gp.core.features.sistema;

public enum TipoInstallazioneEnum {

    STANDARD("STANDARD"),
    ENTERPRISE("ENTERPRISE");

    private String value;

    TipoInstallazioneEnum(String value) {

	this.value = value;
    }

    @Override
    public String toString() {

	return String.valueOf(this.value);
    }

    public static TipoInstallazioneEnum fromValue(String text) {

	for (TipoInstallazioneEnum b : TipoInstallazioneEnum.values()) {
	    if (b.value.equalsIgnoreCase(text)) {
		return b;
	    }
	}
	return null;
    }
}
