package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

public class MessaggioRigaAggiuntaManualmente extends MessaggioBollettazione {

    String autore;

    public MessaggioRigaAggiuntaManualmente(String autore) {

	super();
	this.autore = autore;
    }

    @Override
    public String getTestoMessaggio() {

	return "Riga inserita da " + autore + " in data " + getStringaData();
    }
}
