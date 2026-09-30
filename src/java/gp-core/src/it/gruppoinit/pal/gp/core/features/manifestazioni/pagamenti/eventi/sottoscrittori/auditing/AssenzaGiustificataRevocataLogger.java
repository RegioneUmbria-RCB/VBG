package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;

public class AssenzaGiustificataRevocataLogger extends AbstractAbbonamentoLogger {

    public static AssenzaGiustificataRevocataLogger fromModel(JsonPresenzaModel model, String autore) {

	AssenzaGiustificataRevocataLogger logger = new AssenzaGiustificataRevocataLogger(model, autore);
	return logger;
    }

    private AssenzaGiustificataRevocataLogger(JsonPresenzaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(JsonPresenzaModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("REVOCA DI UNA ASSENZA GIUSTIFICATA");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}