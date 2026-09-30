package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioCommissioneOrdinata extends MessaggioCommissioni {

    private String numeroCommissione;
    private String autore;

    public MessaggioCommissioneOrdinata(int idCommissione, String numeroCommissione, String autore) {

	super(CommissioniCategorieEnum.COMMISSIONE_AGGIORNATA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha riordinato la lista delle istanze in discussione per la commissione numero " + numeroCommissione;
    }
}
