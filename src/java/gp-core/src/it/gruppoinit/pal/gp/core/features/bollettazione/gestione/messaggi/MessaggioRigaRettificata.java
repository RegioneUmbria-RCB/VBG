package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi;

import java.math.BigDecimal;

public class MessaggioRigaRettificata extends MessaggioBollettazione {

    String autore;
    BigDecimal nuovoImporto;
    BigDecimal vecchioImporto;

    public MessaggioRigaRettificata(String autore, BigDecimal nuovoImporto, BigDecimal vecchioImporto) {

	super();
	this.autore = autore;
	this.nuovoImporto = nuovoImporto;
	this.vecchioImporto = vecchioImporto;
    }

    @Override
    public String getTestoMessaggio() {

	return String.format("Modificato da %s il %s da €%.2f a €%.2f", autore, getStringaData(), vecchioImporto.doubleValue(),
		nuovoImporto.doubleValue());
    }
}
