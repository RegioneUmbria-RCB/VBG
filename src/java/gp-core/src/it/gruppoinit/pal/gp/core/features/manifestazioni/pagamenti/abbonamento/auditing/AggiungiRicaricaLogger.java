package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiRicaricaModel;

public class AggiungiRicaricaLogger extends AbstractAbbonamentoLogger {

    public static AggiungiRicaricaLogger fromModel(AggiungiRicaricaModel model, String autore) {

	AggiungiRicaricaLogger logger = new AggiungiRicaricaLogger(model, autore);
	return logger;
    }

    private AggiungiRicaricaLogger(AggiungiRicaricaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(AggiungiRicaricaModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("AGGIUNTA NUOVA RICARICA");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}