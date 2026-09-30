package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.JSonBorsellinoAppMovimenti;

public class RimuoviRicaricaAbbonamentoAppLogger extends AbstractAbbonamentoLogger {

    public RimuoviRicaricaAbbonamentoAppLogger(JSonBorsellinoAppMovimenti movimento, String autore) {

	super(autore);
	this.messaggio = generaMessaggio(movimento);
    }

    private String generaMessaggio(JSonBorsellinoAppMovimenti movimento) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("ELIMINAZIONE RICARICA BORSELLINO");
	sb.append("\n").append("MOVIMENTO ELIMINATO: ").append(movimento);
	sb.append("\n").append(DELIMITATORE_LOG);
	return sb.toString();
    }
}
