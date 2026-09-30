package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioDocumentiCommissioniAggiunti extends MessaggioCommissioni {

    private String autore;
    private String nomeFile;

    public MessaggioDocumentiCommissioniAggiunti(String autore, String nomeFile) {

	super(CommissioniCategorieEnum.DOCUMENTI_INSERITI);
	this.autore = autore;
	this.nomeFile = nomeFile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha aggiunto il file " + nomeFile + " alla commissione.";
    }
}
