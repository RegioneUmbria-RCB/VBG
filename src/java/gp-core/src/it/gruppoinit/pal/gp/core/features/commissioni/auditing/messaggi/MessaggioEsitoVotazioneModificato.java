package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioEsitoVotazioneModificato extends MessaggioCommissioni {

    private String autore;
    private String numeroIstanza;

    public MessaggioEsitoVotazioneModificato(String autore, String numeroIstanza) {

	super(CommissioniCategorieEnum.ESITO_VOTAZIONE_MODIFICATO);
	this.autore = autore;
	this.numeroIstanza = numeroIstanza;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha modificato l'esito della votazione per l'istanza numero " + numeroIstanza;
    }
}
