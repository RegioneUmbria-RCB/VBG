package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

public class EliminazioneOnereRiuscita extends EliminaOnereResponse {

    public EliminazioneOnereRiuscita() {

	this.esitoOperazione = new EsitoOperazioneType(1);
    }
}
