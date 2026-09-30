package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import java.util.Date;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MessaggioConvocazioneCancellata extends MessaggioCommissioni {

    private String autore;
    private String dataConvocazione;
    private String oraConvocazione;

    public MessaggioConvocazioneCancellata(String autore, Date dataConvocazione, String oraConvocazione) {

	super(CommissioniCategorieEnum.CONVOCAZIONE_COMMISSIONE_ELIMINATA);
	this.autore = autore;
	this.dataConvocazione = Utilities.formatDate(dataConvocazione, WebConstants.DATE_FORMAT_PATTERN);
	this.oraConvocazione = oraConvocazione;
    }

    @Override
    public String getTestoMessaggio() {

	return "L'utente " + autore + " ha eliminato la convocazione del giorno " + dataConvocazione + " alle ore " + oraConvocazione;
    }
}
