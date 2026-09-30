package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioEsitoVotazioneInserito extends MessaggioCommissioni {

    private String autore;
    private String numeroIstanza;

    public MessaggioEsitoVotazioneInserito(String autore, String numeroIstanza) {

	super(CommissioniCategorieEnum.ESITO_VOTAZIONE_INSERITO);
	this.autore = autore;
	this.numeroIstanza = numeroIstanza;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha inserito l'esito della votazione per l'istanza numero " + numeroIstanza;
    }
}
