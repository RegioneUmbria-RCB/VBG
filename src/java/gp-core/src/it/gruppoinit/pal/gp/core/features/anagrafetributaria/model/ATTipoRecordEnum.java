package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model;

import org.apache.commons.lang.StringUtils;

public enum ATTipoRecordEnum {

    RECORD_1("1"),
    RECORD_2("2"),
    RECORD_3("3"),
    RECORD_4("4"),
    RECORD_5("5");

    private String valore;

    ATTipoRecordEnum(String valore) {

	this.valore = valore;
    }

    public static ATTipoRecordEnum fromValore(String valore) {

	if (StringUtils.defaultString(valore).equals("1")) {
	    return RECORD_1;
	} else if (StringUtils.defaultString(valore).equals("2")) {
	    return RECORD_2;
	} else if (StringUtils.defaultString(valore).equals("3")) {
	    return RECORD_3;
	} else if (StringUtils.defaultString(valore).equals("4")) {
	    return RECORD_4;
	} else if (StringUtils.defaultString(valore).equals("5")) {
	    return RECORD_5;
	}
	throw new IllegalArgumentException("Valore " + valore + " non ammesso");
    }

    public String value() {

	return this.valore;
    }
}
