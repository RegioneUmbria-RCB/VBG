package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioIstanzaCancellataDaCommissione extends MessaggioCommissioni {

    private String autore;
    private Integer codiceCommissione;
    private String numeroIstanza;

    public MessaggioIstanzaCancellataDaCommissione(String autore, Integer codiceCommissione, String numeroIstanza) {

	super(CommissioniCategorieEnum.ISTANZA_ELIMINATA_COMMISSIONE);
	this.autore = autore;
	this.codiceCommissione = codiceCommissione;
	this.numeroIstanza = numeroIstanza;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha eliminato il collegamento con l'stanza numero " + numeroIstanza + " per la commissione con id " +
	       codiceCommissione;
    }
}
