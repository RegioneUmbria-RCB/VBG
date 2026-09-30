package it.gruppoinit.pal.gp.core.service.helper;

public enum MetadatiFunzioneEnum {
    CREA_MD_ISTANZA("#CREA_MD_ISTANZA#"), CREA_MD_MOVIMENTO("#CREA_MD_MOVIMENTO#"), CREA_MD_ENDO("#CREA_MD_ENDO#"), CREA_MD_ARCHIVI(
	    "#REA_MD_ARCHIVI#");

    private String value;

    private MetadatiFunzioneEnum(String s) {

	value = s;
    }

    public String getValue() {

	return value;
    }

    public static MetadatiFunzioneEnum fromValue(String value) {

	if (value == null) {
	    return null;
	}
	if (value.equals("")) {
	    return null;
	}
	if (value.equals(CREA_MD_ISTANZA.getValue())) {
	    return CREA_MD_ISTANZA;
	} else if (value.equals(CREA_MD_MOVIMENTO.getValue())) {
	    return CREA_MD_MOVIMENTO;
	} else if (value.equals(CREA_MD_ARCHIVI.getValue())) {
	    return CREA_MD_ARCHIVI;
	} else if (value.equals(CREA_MD_ENDO.getValue())) {
	    return CREA_MD_ENDO;
	}
	return null;
    }
}
