package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MessaggioNuovaConvocazione extends MessaggioCommissioni {

    private String autore;
    private String numeroCommissione;
    private String dataConvocazione;
    private String oraConvocazione;

    public MessaggioNuovaConvocazione(String autore, String numeroCommissione, Date dataConvocazione, String oraConvocazione) {

	super(CommissioniCategorieEnum.CONVOCAZIONE_COMMISSIONE_CREATA);
	this.autore = autore;
	this.numeroCommissione = numeroCommissione;
	this.dataConvocazione = Utilities.formatDate(dataConvocazione, WebConstants.DATE_FORMAT_PATTERN);
	this.oraConvocazione = oraConvocazione;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'operatore " + autore + " ha creato una nuova convocazione per la commissione numero " + numeroCommissione + " per il giorno " +
	       dataConvocazione + " alle ore " + oraConvocazione;
    }
}
