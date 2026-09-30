package it.gruppoinit.pal.gp.core.features.anagrafe;

public enum TipoAnagrafeEnum {

    G,
    F,
    NON_SPECIFICATO;

    public String value() {

	return name();
    }

    public static TipoAnagrafeEnum fromValue(String v) {

	return valueOf(v);
    }
}
