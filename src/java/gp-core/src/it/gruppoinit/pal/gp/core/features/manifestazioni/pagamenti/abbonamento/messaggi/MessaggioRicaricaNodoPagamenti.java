package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi;

import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioRicaricaNodoPagamenti extends MessaggioDiSistema {

    private Borsellino borsellino;

    public MessaggioRicaricaNodoPagamenti(Borsellino borsellino) {

	this.borsellino = borsellino;
    }

    @Override
    public String getTestoMessaggio() {

	return "Ricarica " + borsellino.getDescrizione();
    }
}
