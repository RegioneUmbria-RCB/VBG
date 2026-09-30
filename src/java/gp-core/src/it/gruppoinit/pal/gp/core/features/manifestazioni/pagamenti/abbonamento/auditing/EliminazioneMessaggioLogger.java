package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.MessaggioNodoPagNonDispModel;

public class EliminazioneMessaggioLogger extends AbstractAbbonamentoLogger {

    public static EliminazioneMessaggioLogger fromModel(MessaggioNodoPagNonDispModel model, String autore) {

	EliminazioneMessaggioLogger logger = new EliminazioneMessaggioLogger(model, autore);
	return logger;
    }

    private EliminazioneMessaggioLogger(MessaggioNodoPagNonDispModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(MessaggioNodoPagNonDispModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("CANCELLAZIONE MESSAGGIO NODO PAGAMENTI NON DISPONIBILE");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
