package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.service.FoDomRichiesteService;

import org.apache.commons.lang.StringUtils;

public class FoDomRichiesteHelper {

    public static String decodeTipoRichiesta(String codiceTipo) {

	String result = StringUtils.defaultString(codiceTipo);
	if (FoDomRichiesteService.RICHIESTA_INTEGRAZIONE.equals(result)) {
	    return "richiesta integrazione";
	} else if (FoDomRichiesteService.INOLTRO_INTEGRAZIONE.equals(result)) {
	    return "inoltro integrazione";
	} else if (FoDomRichiesteService.RICHIESTA_CONFORMAZIONE.equals(result)) {
	    return "richiesta conformazione";
	} else if (FoDomRichiesteService.INOLTRO_CONFORMAZIONE.equals(result)) {
	    return "inoltro conformazione";
	} else if (FoDomRichiesteService.RICHIESTA_COMUNICAZIONE.equals(result)) {
	    return "richiesta comunicazione";
	} else if (FoDomRichiesteService.INOLTRO_COMUNICAZIONE.equals(result)) {
	    return "inoltro comunicazione";
	} else if (FoDomRichiesteService.DINIEGO.equals(result)) {
	    return "diniego";
	} else if (FoDomRichiesteService.INVIO_PROVVEDIMENTO.equals(result)) {
	    return "invio provvedimento";
	}
	return result;
    }
}
