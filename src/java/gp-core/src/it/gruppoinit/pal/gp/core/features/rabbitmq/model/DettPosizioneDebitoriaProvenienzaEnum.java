package it.gruppoinit.pal.gp.core.features.rabbitmq.model;

public enum DettPosizioneDebitoriaProvenienzaEnum {

    BOLLETTAZIONE,
    MERCATIPRESENZE_D,
    ISTANZEONERI,
    ABBONAMENTO;

    private String value;

    public String getValue() {

	return value;
    }

    public void setValue(String value) {

	this.value = value;
    }

    public static DettPosizioneDebitoriaProvenienzaEnum fromValue(String v) {

	for (DettPosizioneDebitoriaProvenienzaEnum c : DettPosizioneDebitoriaProvenienzaEnum.values()) {
	    if (c.toString().equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
