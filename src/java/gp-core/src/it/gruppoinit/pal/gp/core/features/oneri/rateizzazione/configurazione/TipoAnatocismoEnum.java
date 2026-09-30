package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.Map;
import java.util.TreeMap;

public enum TipoAnatocismoEnum {

    SENZA_ANATOCISMO(0, "Senza anatocismo");

    private final int valore;
    private final String descrizione;

    TipoAnatocismoEnum(int valore, String descrizione) {

	this.valore = valore;
	this.descrizione = descrizione;
    }

    public int getValore() {

	return this.valore;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public static Map<Integer, String> toMap() {

	TreeMap<Integer, String> elenco = new TreeMap<Integer, String>();
	for (TipoAnatocismoEnum c : TipoAnatocismoEnum.values()) {
	    elenco.put(c.valore, c.descrizione);
	}
	return elenco;
    }

    public static TipoAnatocismoEnum fromValue(Integer valore) {

	for (TipoAnatocismoEnum c : TipoAnatocismoEnum.values()) {
	    if (c.valore == valore) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Valore non ammesso: " + valore);
    }
}
