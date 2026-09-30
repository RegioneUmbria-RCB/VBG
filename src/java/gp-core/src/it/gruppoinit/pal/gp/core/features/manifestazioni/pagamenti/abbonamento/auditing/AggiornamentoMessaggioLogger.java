package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.MessaggioNodoPagNonDispModel;

public class AggiornamentoMessaggioLogger extends AbstractAbbonamentoLogger {

    public static AggiornamentoMessaggioLogger fromModel(MessaggioNodoPagNonDispModel model, String autore) {

	AggiornamentoMessaggioLogger logger = new AggiornamentoMessaggioLogger(model, autore);
	return logger;
    }

    private AggiornamentoMessaggioLogger(MessaggioNodoPagNonDispModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(MessaggioNodoPagNonDispModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("SALVATAGGIO MESSAGGIO NODO PAGAMENTI NON DISPONIBILE");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
