package it.gruppoinit.pal.gp.core.features.comunicazioni.appio;

public enum AppIoServiziAmbitiEnum {

    MOVIMENTO("Movimento"),
    AUTORIZZAZIONE("Autorizzazione");

    private String value;

    private AppIoServiziAmbitiEnum(String v) {

	this.value = v;
    }

    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }

    public static AppIoServiziAmbitiEnum fromName(String v) {

	for (AppIoServiziAmbitiEnum b : AppIoServiziAmbitiEnum.values()) {
	    if (b.value.equalsIgnoreCase(v)) {
		return b;
	    }
	}
	return null;
    }
}
