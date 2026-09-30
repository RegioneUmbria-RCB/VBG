package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.auditing;

import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing.AbstractAbbonamentoLogger;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.RicaricaBorsellinoRequest;

public class RicaricaBorsellinoLogger extends AbstractAbbonamentoLogger {

    public static RicaricaBorsellinoLogger fromModel(RicaricaBorsellinoRequest model, Integer codiceAnagrafe, String autore) {

	RicaricaBorsellinoLogger logger = new RicaricaBorsellinoLogger(model, codiceAnagrafe, autore);
	return logger;
    }

    private RicaricaBorsellinoLogger(RicaricaBorsellinoRequest model, Integer codiceAnagrafe, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(model, codiceAnagrafe, autore);
    }

    private String generaMessaggio(RicaricaBorsellinoRequest model, Integer codiceAnagrafe, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("RICARICA BORSELLINO");
	sb.append("\n").append("CodiceAnagrafe: ").append(codiceAnagrafe);
	sb.append("\n").append(model.toString());
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
