package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import org.apache.commons.lang.StringUtils;

public enum DestinatariEnum {
    
    TUTTI("TUTTI"),
    CONCESSIONARI("CONCESSIONARI"),
    SPUNTISTI("SPUNTISTI");
    
    private final String valore;

    public String getValore() {

	return valore;
    }

    DestinatariEnum(String valore) {

	this.valore = valore;
    }

    public static DestinatariEnum fromValue(String valore) {

	if (StringUtils.isBlank(valore)) {
	    return DestinatariEnum.fromDefault();
	}
	for (DestinatariEnum c : DestinatariEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }

    public static DestinatariEnum fromDefault() {

	return DestinatariEnum.TUTTI;
    }
}
