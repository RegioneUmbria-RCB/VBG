package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioDocumentiCommissioniEliminati extends MessaggioCommissioni {

    private String autore;
    private String nomeFile;

    public MessaggioDocumentiCommissioniEliminati(String autore, String nomeFile) {

	super(CommissioniCategorieEnum.DOCUMENTI_ELIMINATI);
	this.autore = autore;
	this.nomeFile = nomeFile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha eliminato il file " + nomeFile + " dalla commissione.";
    }
}
