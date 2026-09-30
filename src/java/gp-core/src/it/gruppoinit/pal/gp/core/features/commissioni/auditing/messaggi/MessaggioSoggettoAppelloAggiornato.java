package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioSoggettoAppelloAggiornato extends MessaggioCommissioni {

    private String autore;
    private String responsabile;

    public MessaggioSoggettoAppelloAggiornato(String autore, String responsabile) {

	super(CommissioniCategorieEnum.COMMISSIONE_AGGIORNATA);
	this.autore = autore;
	this.responsabile = responsabile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + this.autore + " ha modificato " + this.responsabile + " nell'elenco dei soggetti convocati alla commissione.";
    }
}
