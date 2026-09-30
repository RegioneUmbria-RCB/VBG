package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiungiInformativaModel;

public class AggiungiInformativaLogger extends AbstractAbbonamentoLogger {

    public static AggiungiInformativaLogger fromModel(AggiungiInformativaModel model, String autore) {

	AggiungiInformativaLogger logger = new AggiungiInformativaLogger(model, autore);
	return logger;
    }

    private AggiungiInformativaLogger(AggiungiInformativaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(AggiungiInformativaModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("AGGIUNTA INFORMATIVA");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
