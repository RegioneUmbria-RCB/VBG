package it.gruppoinit.stc.service.helper;

import it.gruppoinit.stc.domain.Pratiche;
import it.init.sigepro.rte.types.ErroreType;

public class ErroriSTCHelper {

    public static final String ERRORE_PRATICA_MITTENTE_ESISTENTE = "ERR_PRAT_MITT_ESISTENTE";

    public static ErroreType praticaMittenteEsistente(Pratiche praticaMit) {

	ErroreType e = new ErroreType();
	e.setNumeroErrore(ERRORE_PRATICA_MITTENTE_ESISTENTE);
	e.setDescrizione("Pratica mittente esistente su db STC! (idpratica=" + praticaMit.getIdpratica() + ", numpratica="
		+ praticaMit.getNumpratica() + ")");
	return e;
    }
}
