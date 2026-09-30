package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori;

public enum StatoElaborazioneEnum {

    DA_ESEGUIRE("DA ESEGUIRE");

    private final String value;

    StatoElaborazioneEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }
}
