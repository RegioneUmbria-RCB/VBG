package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.Date;

public interface CalcoloIntervalloStrategy {

    public enum TIPO_PERIODO {
	PERIODO_ATTUALE,
	PERIODO_PRECEDENTE
    }

    IntervalloDate getIntervallo(Date dataInizioOperazione);

    Date calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO periodo, Date dataRiferimento);
}
