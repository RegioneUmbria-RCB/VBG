package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioCommissioneRiaperta extends MessaggioCommissioni {

    private int idCommissione;
    private String numeroCommissione;
    private String autore;

    public MessaggioCommissioneRiaperta(int idCommissione, String numeroCommissione, String autore) {

	super(CommissioniCategorieEnum.COMMISSIONE_RIAPERTA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
	this.idCommissione = idCommissione;
    }

    @Override
    public String getTestoMessaggio() {

	return "La commissione con id " + this.idCommissione + " numero \"" + numeroCommissione + "\" è stata riaperta da " + autore;
    }
}
