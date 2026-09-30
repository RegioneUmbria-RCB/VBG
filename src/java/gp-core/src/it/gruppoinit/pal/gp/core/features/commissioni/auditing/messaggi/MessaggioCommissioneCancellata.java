package it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MessaggioCommissioneCancellata {

    private CommissioniedilizieT entity;
    private Responsabili autore;

    public MessaggioCommissioneCancellata(CommissioniedilizieT entity, Responsabili autore) {

	this.entity = entity;
	this.autore = autore;
    }

    public String getTestoMessaggio() {

	String operatore = "";
	if (this.autore != null) {
	    operatore = this.autore.toString();
	}
	return "La commissione con numero: \"" + entity.getDescrizione() + "\", id: \"" + entity.getId() + "\", descrizione: \"" +
	       entity.getDescrizione() + "\" è stata cancellata da " + operatore + " in data " + Utilities.getToday(true);
    }
}
