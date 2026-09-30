package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

public class RimuoviRicaricaLogger extends AbstractAbbonamentoLogger {

    public static RimuoviRicaricaLogger fromId(int id, String autore) {

	RimuoviRicaricaLogger logger = new RimuoviRicaricaLogger(id, autore);
	return logger;
    }

    private RimuoviRicaricaLogger(int id, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(id, autore);
    }

    private String generaMessaggio(int id, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("CANCELLAZIONE METODOLOGIA DI RICARICA");
	sb.append("\n").append(id);
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}
