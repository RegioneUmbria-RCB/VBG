package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.auditing;

public class RimuoviInformativaLogger extends AbstractAbbonamentoLogger {

    public static RimuoviInformativaLogger fromId(int id, String autore) {

	RimuoviInformativaLogger logger = new RimuoviInformativaLogger(id, autore);
	return logger;
    }

    private RimuoviInformativaLogger(int id, String autore) {

	super(autore);
	this.messaggio = this.generaMessaggio(id, autore);
    }

    private String generaMessaggio(int id, String autore) {

	StringBuilder sb = new StringBuilder();
	sb.append("\n").append("CANCELLAZIONE INFORMATIVA");
	sb.append("\n").append(id);
	sb.append("\n").append("=========================================================");
	return sb.toString();
    }
}