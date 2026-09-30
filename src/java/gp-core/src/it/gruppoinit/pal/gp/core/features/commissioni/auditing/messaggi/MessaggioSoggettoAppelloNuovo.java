package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioSoggettoAppelloNuovo extends MessaggioCommissioni {

    private String autore;
    private String responsabile;

    public MessaggioSoggettoAppelloNuovo(String autore, String responsabile) {

	super(CommissioniCategorieEnum.COMMISSIONE_AGGIORNATA);
	this.autore = autore;
	this.responsabile = responsabile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + this.autore + " ha aggiunto " + this.responsabile + " all'elenco dei soggetti convocati alla commissione.";
    }
}
