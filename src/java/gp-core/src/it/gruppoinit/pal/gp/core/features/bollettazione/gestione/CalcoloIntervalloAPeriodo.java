package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class CalcoloIntervalloAPeriodo extends CalcoloIntervalloStrategyBase {

    private int periodicita;

    protected CalcoloIntervalloAPeriodo(int periodicita) {

	this.periodicita = periodicita;
    }

    @Override
    protected Date calcolaDataInizio(Date dataInizioOperazione) {

	//Calendar c = Calendar.getInstance();
	Calendar c = GregorianCalendar.getInstance();
	c.setTime(dataInizioOperazione);
	c.set(Calendar.DAY_OF_MONTH, 1);
	c.set(Calendar.MONTH, this.getBimestreBase0(dataInizioOperazione) * this.periodicita);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	return c.getTime();
    }

    @Override
    protected Date calcolaDataFine(Date dataInizioOperazione) {

	Date dataInizio = calcolaDataInizio(dataInizioOperazione);
	//Calendar c = Calendar.getInstance();
	Calendar c = GregorianCalendar.getInstance();
	c.setTime(dataInizio);
	c.add(Calendar.MONTH, this.periodicita - 1);
	int dom = c.getActualMaximum(Calendar.DAY_OF_MONTH);
	c.set(Calendar.DAY_OF_MONTH, dom);
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	c.set(Calendar.MILLISECOND, 999);
	return c.getTime();
    }

    private int getBimestreBase0(Date dataInizio) {

	Calendar c = Calendar.getInstance();
	c.setTime(dataInizio);
	int mese = c.get(Calendar.MONTH);
	return (mese / this.periodicita);
    }

    public abstract Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento);
}
