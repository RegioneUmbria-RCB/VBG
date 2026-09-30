package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

public class MessaggioValidazioneRiga extends MessaggioBollettazione {

    String autore;
    Boolean valido;

    public MessaggioValidazioneRiga(String autore, Boolean valido) {

	super();
	this.autore = autore;
	this.valido = valido;
    }

    @Override
    public String getTestoMessaggio() {

	String messaggioFormat = null;
	if (valido) {
	    messaggioFormat = "Contrassegnata come valida da %s il %s";
	} else {
	    messaggioFormat = "Contrassegnata non valida da %s il %s";
	}
	return String.format(messaggioFormat, autore, getStringaData());
    }
}
