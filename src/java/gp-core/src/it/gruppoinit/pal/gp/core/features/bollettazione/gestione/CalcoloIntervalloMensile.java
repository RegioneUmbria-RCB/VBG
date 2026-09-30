package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy.TIPO_PERIODO;

public class CalcoloIntervalloMensile extends CalcoloIntervalloStrategyBase {

    protected Date calcolaDataFine(Date dataInizioOperazione) {

	Calendar c = GregorianCalendar.getInstance();
	c.setTime(dataInizioOperazione);
	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	c.set(Calendar.MILLISECOND, 999);
	return c.getTime();
    }

    protected Date calcolaDataInizio(Date dataInizioOperazione) {

	Calendar c = GregorianCalendar.getInstance();
	c.setTime(dataInizioOperazione);
	c.set(Calendar.DAY_OF_MONTH, 1);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	return c.getTime();
    }

    @Override
    public Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento) {

	Calendar c = Calendar.getInstance();
	c.setTime(dataRiferimento);
	if (periodo.equals(TIPO_PERIODO.PERIODO_PRECEDENTE)) {
	    int mese = c.get(Calendar.MONTH);
	    int nuovoMese = mese - 1;
	    c.set(Calendar.MONTH, nuovoMese);
	}
	return c.getTime();
    }
}
