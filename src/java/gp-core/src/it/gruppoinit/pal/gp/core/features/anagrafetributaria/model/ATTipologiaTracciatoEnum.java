package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model;

import org.apache.commons.lang.StringUtils;

public enum ATTipologiaTracciatoEnum {

    COMMERCIO("Atti di concessione, autorizzazione e licenza (ad esclusione dei codici R1, R2 e R3)"),
    EDILIZIA("Comunicazione dei dati in materia edilizia (DIA, Permessi e atti di assenso) da parte degli uffici comunali - secondo le specifiche tecniche previste nel Provvedimento del Direttore dell'Agenzia delle Entrate del 2 ottobre 2006, e modificate dal Comunicato del 2 gennaio 2007");

    private String descrizione;

    ATTipologiaTracciatoEnum(String descrizione) {

	this.descrizione = descrizione;
    }

    public static ATTipologiaTracciatoEnum fromName(String name) {

	if (StringUtils.defaultString(name).equals(COMMERCIO.name())) {
	    return COMMERCIO;
	} else if (StringUtils.defaultString(name).equals(EDILIZIA.name())) {
	    return EDILIZIA;
	}
	throw new IllegalArgumentException("Valore " + name + " non ammesso");
    }

    public String value() {

	return this.descrizione;
    }
}
