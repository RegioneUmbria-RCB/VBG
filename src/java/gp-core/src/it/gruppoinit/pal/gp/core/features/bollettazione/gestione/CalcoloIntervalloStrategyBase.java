package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

public abstract class CalcoloIntervalloStrategyBase implements CalcoloIntervalloStrategy {

    @Override
    public IntervalloDate getIntervallo(Date dataInizioOperazione) {

	Date dataInizio = calcolaDataInizio(dataInizioOperazione);
	Date dataFine = calcolaDataFine(dataInizioOperazione);
	return new IntervalloDate(dataInizio, dataFine);
    }

    protected abstract Date calcolaDataInizio(Date dataInizioOperazione);

    protected abstract Date calcolaDataFine(Date dataInizioOperazione);
}
