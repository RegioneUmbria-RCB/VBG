package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.AbbonamentoTabellaModel;

public class CreazioneBorsellinoLogger extends AbstractAbbonamentoLogger {

    public static CreazioneBorsellinoLogger fromModel(AbbonamentoTabellaModel model, String autore) {

	return new CreazioneBorsellinoLogger(model, autore);
    }

    private CreazioneBorsellinoLogger(AbbonamentoTabellaModel model, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model);
    }

    private String generaMessaggio(AbbonamentoTabellaModel model) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("CREAZIONE BORSELLINO");
	sb.append("\n").append(model.toString());
	sb.append("\n").append(DELIMITATORE_LOG);
	return sb.toString();
    }
}
