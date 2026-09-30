package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioComunicazioneCommissioneCreata extends MessaggioCommissioni {

    private String numeroCommissione;
    private String autore;
    private int idTestata;

    public MessaggioComunicazioneCommissioneCreata(String numeroCommissione, String autore, int idtestata) {

	super(CommissioniCategorieEnum.COMUNICAZIONE_COMMISSIONE_CREATA);
	this.numeroCommissione = numeroCommissione;
	this.autore = autore;
	this.idTestata = idtestata;
    }

    @Override
    public String getTestoMessaggio() {

	return String.format("Creazione della comunicazione massiva (id: %d) per la commissione numero \"%s\" effettuata da %s", idTestata,
		numeroCommissione, autore);
    }
}
