package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

public class MessaggioRettificaAnnullata extends MessaggioBollettazione {

    String autore;

    public MessaggioRettificaAnnullata(String autore) {

	this.autore = autore;
    }

    @Override
    public String getTestoMessaggio() {

	return String.format("Rettifica annullata da %s in data %s", autore, getStringaData());
    }
}
