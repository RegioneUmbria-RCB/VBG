package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;

public enum NumerazioneEnum {

    VUOTO,
    DA_CONFIGURAZIONE,
    DA_PROTOCOLLO,
    CUSTOM;

    public static NumerazioneEnum daRegistro(Tipologiaregistri registro) {

	if (registro == null) {
	    return VUOTO;
	}
	if (Boolean.TRUE.equals(registro.getTrFlagprotocollo())) {
	    return DA_PROTOCOLLO;
	}
	if (StringUtils.isNotBlank(registro.getNumerazioneCustom())) {
	    return CUSTOM;
	}
	if (Boolean.TRUE.equals(registro.getFlagUsaProgrConf()) || StringUtils.isNotBlank(registro.getTrProgressivo())) {
	    return DA_CONFIGURAZIONE;
	}
	return VUOTO;
    }
}
