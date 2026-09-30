package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.AggiornaConfigurazioneBaseModel;

public class SalvataggioConfigurazioneLogger extends AbstractAbbonamentoLogger {

    public static SalvataggioConfigurazioneLogger fromModel(AggiornaConfigurazioneBaseModel model, String autore) {

	SalvataggioConfigurazioneLogger logger = new SalvataggioConfigurazioneLogger(model, autore);
	return logger;
    }

    private SalvataggioConfigurazioneLogger(AggiornaConfigurazioneBaseModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(AggiornaConfigurazioneBaseModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("SALVATAGGIO CONFIGURAZIONE");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
