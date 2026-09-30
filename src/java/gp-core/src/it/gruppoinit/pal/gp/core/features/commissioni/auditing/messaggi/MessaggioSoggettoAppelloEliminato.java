package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioSoggettoAppelloEliminato extends MessaggioCommissioni {

    private String autore;
    private String responsabile;

    public MessaggioSoggettoAppelloEliminato(String autore, String responsabile) {

	super(CommissioniCategorieEnum.COMMISSIONE_AGGIORNATA);
	this.autore = autore;
	this.responsabile = responsabile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + this.autore + " ha eliminato " + this.responsabile + " dall'elenco dei soggetti convocati alla commissione.";
    }
}
