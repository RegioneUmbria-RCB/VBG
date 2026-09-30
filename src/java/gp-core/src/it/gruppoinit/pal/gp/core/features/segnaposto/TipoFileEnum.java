package it.gruppoinit.pal.gp.core.features.segnaposto;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.OdtConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;

public enum TipoFileEnum {

    ODT("odt"),
    RTF("rtf");

    private final String value;

    TipoFileEnum(String v) {

	value = v;
    }

    public static String getRitornoACapo(TipoFileEnum tipoFile) {

	if (tipoFile.equals(TipoFileEnum.RTF)) {
	    return RtfConstants.RTF_CRLF;
	}
	return OdtConstants.ODT_CRLF;
    }

    public static TipoFileEnum fromValue(String estensione) {

	if (StringUtils.isBlank(estensione)) {
	    throw new IllegalArgumentException("Impossibile risalire al tipo di file senza passare l'esensione");
	}
	for (TipoFileEnum c : TipoFileEnum.values()) {
	    if (c.value.equals(estensione.toLowerCase())) {
		return c;
	    }
	}
	throw new IllegalArgumentException(estensione);
    }

    public static TipoFileEnum fromFileName(String fileName) {

	if (StringUtils.isBlank(fileName) || fileName.indexOf(".") < 1) {
	    throw new IllegalArgumentException("Impossibile risalire al tipo di file senza passare il nome nel formato <nomefile>.<estensione>");
	}
	String estensione = fileName.substring(fileName.lastIndexOf("."));
	return TipoFileEnum.fromValue(estensione);
    }
}
