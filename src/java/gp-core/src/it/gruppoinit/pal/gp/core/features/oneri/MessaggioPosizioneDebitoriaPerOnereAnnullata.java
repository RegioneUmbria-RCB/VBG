package it.gruppoinit.pal.gp.core.features.oneri;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioPosizioneDebitoriaPerOnereAnnullata extends MessaggioDiSistema {

    private String messaggio;

    public MessaggioPosizioneDebitoriaPerOnereAnnullata(String messaggio) {

	this.messaggio = "Posizione debitoria annullata: " + messaggio;
    }

    @Override
    public String getTestoMessaggio() {

	return messaggio;
    }
}
