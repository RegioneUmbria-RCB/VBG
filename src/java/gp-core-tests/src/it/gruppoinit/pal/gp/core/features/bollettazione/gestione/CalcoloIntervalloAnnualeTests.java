package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloAnnuale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy.TIPO_PERIODO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class CalcoloIntervalloAnnualeTests {

    @Test
    public void getIntervallo_inizioIntervallo_restituiscePrimoGiornoDellAnno() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloAnnuale();
	Calendar c = Calendar.getInstance();
	c.set(Calendar.DAY_OF_MONTH, 3);
	c.set(Calendar.MONTH, 6);
	c.set(Calendar.YEAR, 2020);
	IntervalloDate result = calcolo.getIntervallo(c.getTime());
	Date dataInizio = result.getDataInizio();
	c = Calendar.getInstance();
	c.setTime(dataInizio);
	int giorno = c.get(Calendar.DAY_OF_MONTH);
	int mese = c.get(Calendar.MONTH);
	int anno = c.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire il primo gennaio dell'anno passato", 1, giorno);
	Assert.assertEquals("Deve restituire il mese di gennaio", 0, mese);
	Assert.assertEquals("Deve restituire lo stesso anno della data inizio", 2020, anno);
    }

    @Test
    public void getIntervallo_fineIntervallo_restituisceUltimoGiornoDellAnno() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloAnnuale();
	Calendar c = Calendar.getInstance();
	c.set(Calendar.DAY_OF_MONTH, 3);
	c.set(Calendar.MONTH, 6);
	c.set(Calendar.YEAR, 2020);
	IntervalloDate result = calcolo.getIntervallo(c.getTime());
	Date data = result.getDataFine();
	c = Calendar.getInstance();
	c.setTime(data);
	int giorno = c.get(Calendar.DAY_OF_MONTH);
	int mese = c.get(Calendar.MONTH);
	int anno = c.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire l'ultimo giorno di dicembre dell'anno passato", 31, giorno);
	Assert.assertEquals("Deve restituire il mese di dicembre", 11, mese);
	Assert.assertEquals("Deve restituire lo stesso anno della data inizio", 2020, anno);
    }

    @Test
    public void calcolaDataInizioOperazioneDaDataRiferimento() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloAnnuale();
	Calendar c = Calendar.getInstance();
	c.set(Calendar.DAY_OF_MONTH, 3);
	c.set(Calendar.MONTH, 6);
	c.set(Calendar.YEAR, 2020);
	Date ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_ATTUALE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	int anno = c.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire l'ultimo 2020", 2020, anno);
	ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_PRECEDENTE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	anno = c.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire l'ultimo 2019", 2019, anno);
    }
}
