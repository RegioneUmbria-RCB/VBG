package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.sottoscrittori.model.JsonPresenzaModel;

public class RichiestaAnnullamentoPagamentoLogger extends AbstractAbbonamentoLogger {

    public static RichiestaAnnullamentoPagamentoLogger fromModel(JsonPresenzaModel model, String autore) {

	RichiestaAnnullamentoPagamentoLogger logger = new RichiestaAnnullamentoPagamentoLogger(model, autore);
	return logger;
    }

    private RichiestaAnnullamentoPagamentoLogger(JsonPresenzaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, autore);
    }

    private String generaMessaggio(JsonPresenzaModel model, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("ANNULLAMENTO DI UN PAGAMENTO");
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
