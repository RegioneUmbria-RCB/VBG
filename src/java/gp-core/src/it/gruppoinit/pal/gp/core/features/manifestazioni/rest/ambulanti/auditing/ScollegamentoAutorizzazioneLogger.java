package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.JsonBorsellinoAutorizzazione;

public class ScollegamentoAutorizzazioneLogger extends AbstractAbbonamentoLogger {

    public static ScollegamentoAutorizzazioneLogger fromModel(JsonBorsellinoAutorizzazione model, String autore) {

	ScollegamentoAutorizzazioneLogger logger = new ScollegamentoAutorizzazioneLogger(model, autore);
	return logger;
    }

    private ScollegamentoAutorizzazioneLogger(JsonBorsellinoAutorizzazione model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(JsonBorsellinoAutorizzazione model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("SCOLLEGAMENTO AUTORIZZAZIONE");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
