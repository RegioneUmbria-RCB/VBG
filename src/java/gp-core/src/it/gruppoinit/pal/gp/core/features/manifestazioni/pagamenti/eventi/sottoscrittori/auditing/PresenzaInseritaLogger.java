package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;

public class PresenzaInseritaLogger extends AbstractAbbonamentoLogger {

    public static PresenzaInseritaLogger fromModel(JsonPresenzaModel model, String autore) {

	PresenzaInseritaLogger logger = new PresenzaInseritaLogger(model, autore);
	return logger;
    }

    private PresenzaInseritaLogger(JsonPresenzaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(JsonPresenzaModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("REGISTRAZIONE NUOVA PRESENZA");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
