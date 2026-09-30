package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

public class MessaggioVotazioneCommissioneAggiornata extends MessaggioCommissioni {

    private String autore;
    private String numeroCommissione;
    private String votazione;
    private String soggetto;

    public MessaggioVotazioneCommissioneAggiornata(String autore, String numeroCommissione, String votazione, String soggetto) {

	super(CommissioniCategorieEnum.VOTAZIONE_COMMISSIONE_MODIFICATA);
	this.autore = autore;
	this.numeroCommissione = numeroCommissione;
	this.votazione = votazione;
	this.soggetto = soggetto;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " +
		autore +
		" ha aggiornato le votazioni per la commissione numero \"" +
		numeroCommissione +
		"\" con votazione \"" +
		votazione +
		"\" per il soggetto " +
		soggetto;
    }
}
