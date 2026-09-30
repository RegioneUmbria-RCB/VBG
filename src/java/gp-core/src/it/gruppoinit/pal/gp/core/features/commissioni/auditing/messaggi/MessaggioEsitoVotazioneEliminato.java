package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioEsitoVotazioneEliminato extends MessaggioCommissioni {

    private String autore;
    private String numeroIstanza;

    public MessaggioEsitoVotazioneEliminato(String autore, String numeroIstanza) {

	super(CommissioniCategorieEnum.ESITO_VOTAZIONE_ELIMINATO);
	this.autore = autore;
	this.numeroIstanza = numeroIstanza;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha eliminato l'esito della votazione per l'istanza numero " + numeroIstanza;
    }
}
