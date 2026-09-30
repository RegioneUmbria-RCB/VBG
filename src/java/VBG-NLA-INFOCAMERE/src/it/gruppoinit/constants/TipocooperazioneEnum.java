package it.gruppoinit.constants;

public enum TipocooperazioneEnum {
    CONDIVISIONE_SINOLTRO("SINOLTRO"), CONDIVISIONE_ASINOLTRO("ASINOLTRO"), CONDIVISIONE_INOLTRENT("INOLTRENT"), CONDIVISIONE_AOINOLTRO("AOINOLTRO"), CONFERENZA_SERVIZI(
	    "OCONFSER"), RICHIESTA_INTEGRAZIONE("OCONFORM"), SENDENT("SENDENT"), SENDENT2("SENDENT2"), INTEGRAZIONE_DOCUMENTALE_IASINOLTRO(
	    "IASINOLTRO"), INTEGRAZIONE_DOCUMENTALE_OINOLTRO("OINOLTRO"), RILASCIO_PROVVEDIMENTO("SCHIPOSC"), ALTRO("altro"), NON_COFIFICATO(
	    "non coddificato"), RICHIESTA_VALIDAZIONE_ZONA_PRATICA("RICHIESTA VALIDAZIONE ZONA PRATICA"), MOVIMENTO_SISTEMA_ESTERNO("MOVIMENTO SISTEMA ESTERNO");

    private String value;

    private TipocooperazioneEnum(String s) {

	value = s;
    }

    public String getValue() {

	return value;
    }

    public static TipocooperazioneEnum fromValue(String value) {

	if (value == null) {
	    return null;
	}
	if (value.equals("")) {
	    return null;
	}
	if (value.equals(CONDIVISIONE_SINOLTRO.getValue())) {
	    return CONDIVISIONE_SINOLTRO;
	} else if (value.equals(CONDIVISIONE_ASINOLTRO.getValue())) {
	    return CONDIVISIONE_ASINOLTRO;
	} else if (value.equals(CONDIVISIONE_INOLTRENT.getValue())) {
	    return CONDIVISIONE_INOLTRENT;
	} else if (value.equals(CONDIVISIONE_AOINOLTRO.getValue())) {
	    return CONDIVISIONE_AOINOLTRO;
	} else if (value.equals(CONFERENZA_SERVIZI.getValue())) {
	    return CONFERENZA_SERVIZI;
	} else if (value.equals(RICHIESTA_INTEGRAZIONE.getValue())) {
	    return RICHIESTA_INTEGRAZIONE;
	} else if (value.equals(INTEGRAZIONE_DOCUMENTALE_IASINOLTRO.getValue())) {
	    return INTEGRAZIONE_DOCUMENTALE_IASINOLTRO;
	} else if (value.equals(INTEGRAZIONE_DOCUMENTALE_OINOLTRO.getValue())) {
	    return INTEGRAZIONE_DOCUMENTALE_OINOLTRO;
	} else if (value.equals(RILASCIO_PROVVEDIMENTO.getValue())) {
	    return RILASCIO_PROVVEDIMENTO;
	} else if (value.equals(SENDENT.getValue())) {
	    return SENDENT;
	} else if (value.equals(SENDENT2.getValue())) {
	    return SENDENT2;
	} else if (value.equals(ALTRO.getValue())) {
	    return ALTRO;
	} else if (value.equals(RICHIESTA_VALIDAZIONE_ZONA_PRATICA.getValue())) {
	    return RICHIESTA_VALIDAZIONE_ZONA_PRATICA;
	} else if (value.equals(MOVIMENTO_SISTEMA_ESTERNO.getValue())) {
		return MOVIMENTO_SISTEMA_ESTERNO;
	}else {
	    return NON_COFIFICATO;
	}
    }
}
