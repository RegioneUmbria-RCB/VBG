package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

public class MessaggioRigaEliminata extends MessaggioBollettazione {

    String autore;

    public MessaggioRigaEliminata(String autore) {

	this.autore = autore;
    }

    @Override
    public String getTestoMessaggio() {

	return "Eliminata da " + autore + " in data " + getStringaData();
    }
}
