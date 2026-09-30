package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import org.apache.commons.lang.StringUtils;

public enum ComportamentoEnum {

    AUTORIZZAZIONI("AUTORIZZAZIONI"),
    OPERATORE("OPERATORE");

    private final String valore;

    public String getValore() {

	return valore;
    }

    ComportamentoEnum(String valore) {

	this.valore = valore;
    }

    public static ComportamentoEnum fromValue(String valore) {

	if (StringUtils.isBlank(valore)) {
	    return ComportamentoEnum.fromDefault();
	}
	for (ComportamentoEnum c : ComportamentoEnum.values()) {
	    if (c.valore.equals(valore)) {
		return c;
	    }
	}
	throw new IllegalArgumentException("Nessun enumeratore trovato per il valore " + valore);
    }

    public static ComportamentoEnum fromDefault() {

	return ComportamentoEnum.AUTORIZZAZIONI;
    }
}
