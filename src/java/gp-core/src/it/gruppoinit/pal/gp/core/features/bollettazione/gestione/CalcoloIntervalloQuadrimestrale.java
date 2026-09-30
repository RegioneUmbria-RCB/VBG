package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Calendar;
import java.util.Date;

public class CalcoloIntervalloQuadrimestrale extends CalcoloIntervalloAPeriodo {

    public CalcoloIntervalloQuadrimestrale() {

	super(4);
    }

    @Override
    public Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento) {

	Calendar c = Calendar.getInstance();
	c.setTime(dataRiferimento);
	if (periodo.equals(TIPO_PERIODO.PERIODO_PRECEDENTE)) {
	    int mese = c.get(Calendar.MONTH);
	    int nuovoMese = mese - 4;
	    c.set(Calendar.MONTH, nuovoMese);
	}
	return c.getTime();
    }
}
