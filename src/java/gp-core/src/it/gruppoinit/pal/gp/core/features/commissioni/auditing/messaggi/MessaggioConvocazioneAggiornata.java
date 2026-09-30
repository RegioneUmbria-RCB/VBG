package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioConvocazioneAggiornata extends MessaggioCommissioni {

    private String autore;
    private int idConvocazione;

    public MessaggioConvocazioneAggiornata(String autore, int idConvocazione) {

	super(CommissioniCategorieEnum.CONVOCAZIONE_COMMISSIONE_AGGIORNATA);
	this.autore = autore;
	this.idConvocazione = idConvocazione;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha aggiornato la convocazione con id " + idConvocazione;
    }
}
