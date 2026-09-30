package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.JsonBorsellinoAutorizzazione;

public class CollegamentoAutorizzazioneLogger extends AbstractAbbonamentoLogger {

    public static CollegamentoAutorizzazioneLogger fromModel(JsonBorsellinoAutorizzazione model, String autore) {

	CollegamentoAutorizzazioneLogger logger = new CollegamentoAutorizzazioneLogger(model, autore);
	return logger;
    }

    private CollegamentoAutorizzazioneLogger(JsonBorsellinoAutorizzazione model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(JsonBorsellinoAutorizzazione model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("COLLEGAMENTO AUTORIZZAZIONE");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
