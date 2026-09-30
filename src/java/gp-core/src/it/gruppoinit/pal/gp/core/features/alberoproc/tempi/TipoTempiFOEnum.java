package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import org.apache.commons.lang.StringUtils;

public enum TipoTempiFOEnum {

    ASSOLUTA("A"),
    RELATIVA("R");

    private String value;

    public String value() {

	return value;
    }

    TipoTempiFOEnum(String v) {

	value = v;
    }

    public static TipoTempiFOEnum fromValue(String v) {

	if (StringUtils.isBlank(v)) {
	    return null;
	}
	for (TipoTempiFOEnum c : TipoTempiFOEnum.values()) {
	    if (c.value.equals(v)) {
		return c;
	    }
	}
	throw new IllegalArgumentException(v);
    }
}
