package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import java.util.List;

public class MessaggioIstanzeAggiunteInCommissione extends MessaggioCommissioni {

    private String autore;
    private String numeriIstanza;

    public MessaggioIstanzeAggiunteInCommissione(String autore, List<String> numeriIstanza) {

	super(CommissioniCategorieEnum.ISTANZE_AGGIUNTE_COMMISSIONE);
	this.autore = autore;
	this.numeriIstanza = numeriIstanza.toString();
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha aggiunto le istanze numero " + numeriIstanza;
    }
}
