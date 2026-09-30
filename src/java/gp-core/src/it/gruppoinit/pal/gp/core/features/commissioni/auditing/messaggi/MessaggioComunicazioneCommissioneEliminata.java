package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioComunicazioneCommissioneEliminata extends MessaggioCommissioni {

    private String numeroCommissione;
    private String autore;
    private int idTestata;

    public MessaggioComunicazioneCommissioneEliminata(String numeroCommissione, String autore, int idTestata) {

	super(CommissioniCategorieEnum.COMUNICAZIONE_COMMISSIONE_ELIMINATA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
	this.idTestata = idTestata;
    }

    @Override
    public String getTestoMessaggio() {

	return String.format("Eliminazione della comunicazione massiva (id: %d) per la commissione numero \"%s\" effettuata da %s", this.idTestata,
		this.numeroCommissione, this.autore);
    }
}
