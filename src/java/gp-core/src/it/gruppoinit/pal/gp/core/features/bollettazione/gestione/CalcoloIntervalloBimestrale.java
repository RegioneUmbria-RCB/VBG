package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Calendar;
import java.util.Date;

public class CalcoloIntervalloBimestrale extends CalcoloIntervalloAPeriodo {

    public CalcoloIntervalloBimestrale() {

	super(2);
    }

    @Override
    public Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento) {

	Calendar c = Calendar.getInstance();
	c.setTime(dataRiferimento);
	if (periodo.equals(TIPO_PERIODO.PERIODO_PRECEDENTE)) {
	    int mese = c.get(Calendar.MONTH);
	    int nuovoMese = mese - 2;
	    c.set(Calendar.MONTH, nuovoMese);
	}
	return c.getTime();
    }
}
