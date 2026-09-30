package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioCommissioneAggiornata extends MessaggioCommissioni {

    private int idCommissione;
    private String numeroCommissione;
    private String autore;

    public MessaggioCommissioneAggiornata(int idCommissione, String numeroCommissione, String autore) {

	super(CommissioniCategorieEnum.COMMISSIONE_AGGIORNATA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
	this.idCommissione = idCommissione;
    }

    @Override
    public String getTestoMessaggio() {

	return "Aggiornamento della commissione con id " + this.idCommissione + " numero \"" + numeroCommissione + "\" effettuata da " + autore;
    }
}
