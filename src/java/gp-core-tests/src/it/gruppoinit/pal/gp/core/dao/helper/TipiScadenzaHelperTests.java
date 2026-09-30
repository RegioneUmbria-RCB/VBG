package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;

public class TipiScadenzaHelperTests {

    @Test
    public void verifica_scadenza_mese_successivo() {

	Calendar dataRegistrazione = GregorianCalendar.getInstance();
	dataRegistrazione.set(Calendar.DAY_OF_MONTH, 3);
	dataRegistrazione.set(Calendar.MONTH, 6);
	dataRegistrazione.set(Calendar.YEAR, 2020);
	Date dt = new DataScadenzaResolver(TipoScadenzaEnum.FINE_MESE_SUCCESSIVO, dataRegistrazione.getTime(), 1, null).calcolaScadenza();
	dataRegistrazione = GregorianCalendar.getInstance();
	dataRegistrazione.setTime(dt);
	int giorno = dataRegistrazione.get(Calendar.DAY_OF_MONTH);
	int mese = dataRegistrazione.get(Calendar.MONTH);
	int anno = dataRegistrazione.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire il giorno trentuno", 31, giorno);
	Assert.assertEquals("Deve restituire il mese agosto", 7, mese);
	Assert.assertEquals("Deve restituire il 2020", 2020, anno);
    }

    @Test
    public void verifica_scadenza_mese_successivo_e_cambio_anno() {

	Calendar dataRegistrazione = GregorianCalendar.getInstance();
	dataRegistrazione.set(Calendar.DAY_OF_MONTH, 3);
	dataRegistrazione.set(Calendar.MONTH, 11);
	dataRegistrazione.set(Calendar.YEAR, 2020);
	Date dt = new DataScadenzaResolver(TipoScadenzaEnum.FINE_MESE_SUCCESSIVO, dataRegistrazione.getTime(), 1, null).calcolaScadenza();
	dataRegistrazione = GregorianCalendar.getInstance();
	dataRegistrazione.setTime(dt);
	int giorno = dataRegistrazione.get(Calendar.DAY_OF_MONTH);
	int mese = dataRegistrazione.get(Calendar.MONTH);
	int anno = dataRegistrazione.get(Calendar.YEAR);
	Assert.assertEquals("Deve restituire il giorno trentuno", 31, giorno);
	Assert.assertEquals("Deve restituire il mese gennaio", 0, mese);
	Assert.assertEquals("Deve restituire il 2021", 2021, anno);
    }
}
