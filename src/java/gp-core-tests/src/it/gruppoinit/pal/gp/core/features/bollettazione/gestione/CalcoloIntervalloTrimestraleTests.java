package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloTrimestrale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy.TIPO_PERIODO;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class CalcoloIntervalloTrimestraleTests {

    @Test
    public void getIntervallo_inizioIntervallo_restituiscePrimoGiornoDelTrimestre() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloTrimestrale();
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
	Assert.assertEquals("Deve restituire il primo giorno del trimestre", 1, giorno);
	Assert.assertEquals("Deve restituire il primo mese del trimestre", 6, mese);
	Assert.assertEquals("Deve restituire lo stesso anno della data inizio", 2020, anno);
    }

    @Test
    public void getIntervallo_fineIntervallo_restituisceUltimoGiornoDelTrimestre() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloTrimestrale();
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
	Assert.assertEquals("Deve restituire l'ultimo giorno del trimestre", 30, giorno);
	Assert.assertEquals("Deve restituire l'ultimo mese del trimestre", 8, mese);
	Assert.assertEquals("Deve restituire lo stesso anno della data inizio", 2020, anno);
    }

    @Test
    public void calcolaDataInizioOperazioneDaDataRiferimento() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloTrimestrale();
	Calendar c = Calendar.getInstance();
	c.set(Calendar.DAY_OF_MONTH, 3);
	c.set(Calendar.MONTH, 0);
	c.set(Calendar.YEAR, 2020);
	Date ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_ATTUALE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	int anno = c.get(Calendar.YEAR);
	int mese = c.get(Calendar.MONTH);
	Assert.assertEquals("Deve restituire l'ultimo 2020", 2020, anno);
	Assert.assertEquals("Deve restituire mese 0", 0, mese);
	ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_PRECEDENTE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	anno = c.get(Calendar.YEAR);
	mese = c.get(Calendar.MONTH);
	Assert.assertEquals("Deve restituire l'ultimo 2019", 2019, anno);
	Assert.assertEquals("Deve restituire mese 9", 9, mese);
	// 
	c.set(Calendar.DAY_OF_MONTH, 20);
	c.set(Calendar.MONTH, 3);
	c.set(Calendar.YEAR, 2020);
	ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_PRECEDENTE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	anno = c.get(Calendar.YEAR);
	mese = c.get(Calendar.MONTH);
	Assert.assertEquals("Deve restituire l'ultimo 2020", 2020, anno);
	Assert.assertEquals("Deve restituire mese 0", 0, mese);
	c.set(Calendar.DAY_OF_MONTH, 20);
	c.set(Calendar.MONTH, 6);
	c.set(Calendar.YEAR, 2020);
	ret = calcolo.calcolaDataInizioOperazioneDaDataRiferimento(TIPO_PERIODO.PERIODO_PRECEDENTE, c.getTime());
	c = Calendar.getInstance();
	c.setTime(ret);
	anno = c.get(Calendar.YEAR);
	mese = c.get(Calendar.MONTH);
	Assert.assertEquals("Deve restituire l'ultimo 2020", 2020, anno);
	Assert.assertEquals("Deve restituire mese 3", 3, mese);
    }
}
