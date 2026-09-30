package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

public class EliminazioneOnereFallita extends EliminaOnereResponse {

    public EliminazioneOnereFallita(Integer codiceOnere, String messaggioErrore) {

	String errMsg = String.format("Eliminazione dell'onere %s fallita: %s", codiceOnere.toString(), messaggioErrore);
	this.esitoOperazione = new EsitoOperazioneType(0, errMsg);
    }
}