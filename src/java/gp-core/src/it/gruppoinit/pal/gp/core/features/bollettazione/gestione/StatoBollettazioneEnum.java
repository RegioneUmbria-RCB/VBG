package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public enum StatoBollettazioneEnum {
    APERTA("Aperta"), CHIUSA("Chiusa");

    private String valore;

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    private StatoBollettazioneEnum(String valore) {

	this.valore = valore;
    }

    public static StatoBollettazioneEnum fromValue(String v) {

	for (StatoBollettazioneEnum c : StatoBollettazioneEnum.values()) {
	    if (c.valore.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
