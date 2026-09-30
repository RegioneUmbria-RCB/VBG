package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioCommissioneCreata extends MessaggioCommissioni {

    private String numeroCommissione;
    private String autore;

    public MessaggioCommissioneCreata(String numeroCommissione, String autore) {

	super(CommissioniCategorieEnum.COMMISSIONE_CREATA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
    }

    @Override
    public String getTestoMessaggio() {

	return "Creazione della commissione numero \"" + numeroCommissione + "\" effettuata da " + autore;
    }
}
