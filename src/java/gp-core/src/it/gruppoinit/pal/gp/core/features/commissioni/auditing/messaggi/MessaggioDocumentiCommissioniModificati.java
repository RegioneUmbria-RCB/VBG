package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioDocumentiCommissioniModificati extends MessaggioCommissioni {

    private String autore;
    private String nomeFile;

    public MessaggioDocumentiCommissioniModificati(String autore, String nomeFile) {

	super(CommissioniCategorieEnum.DOCUMENTI_MODIFICATI);
	this.autore = autore;
	this.nomeFile = nomeFile;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha modificato il file " + nomeFile + " della commissione.";
    }
}
