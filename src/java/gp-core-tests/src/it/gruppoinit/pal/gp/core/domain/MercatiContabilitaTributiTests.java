package it.gruppoinit.pal.gp.core.domain;

import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class MercatiContabilitaTributiTests {

    @Test(expected = IllegalArgumentException.class)
    public void isValidoAllaData_conDataNonImpostata_ritornaIllegalArgument() {

	MercatiContabilitaTributi mercatiContabilitaTributi = new MercatiContabilitaTributi();
	mercatiContabilitaTributi.isValidoAllaData(null);
    }

    @Test
    public void isValidoAllaData_conDataAntecedenteInizioAttivita_ritornaFalso() {

	MercatiContabilitaTributi mercatiContabilitaTributi = new MercatiContabilitaTributi();
	GregorianCalendar grToday = new GregorianCalendar();
	grToday.set(2020, 8, 26);
	GregorianCalendar grImpostata = new GregorianCalendar();
	grImpostata.set(2020, 9, 1);
	mercatiContabilitaTributi.setDataInizioValidita(grImpostata.getTime());
	Assert.assertFalse(mercatiContabilitaTributi.isValidoAllaData(grToday.getTime()));
    }

    @Test
    public void isValidoAllaData_conDataInizioAttivitaEDataFineNull_ritornaVero() {

	MercatiContabilitaTributi mercatiContabilitaTributi = new MercatiContabilitaTributi();
	GregorianCalendar grToday = new GregorianCalendar();
	grToday.set(2020, 8, 26);
	GregorianCalendar grImpostata = new GregorianCalendar();
	grImpostata.set(2020, 9, 1);
	mercatiContabilitaTributi.setDataInizioValidita(grToday.getTime());
	mercatiContabilitaTributi.setDataFineValidita(null);
	Assert.assertTrue(mercatiContabilitaTributi.isValidoAllaData(grImpostata.getTime()));
    }

    @Test
    public void isValidoAllaData_conDataInizioEDataFineAttivitaImpostate_ritornaVero() {

	MercatiContabilitaTributi mercatiContabilitaTributi = new MercatiContabilitaTributi();
	GregorianCalendar grToday = new GregorianCalendar();
	grToday.set(2020, 8, 26);
	GregorianCalendar grImpostata = new GregorianCalendar();
	grImpostata.set(2020, 9, 1);
	GregorianCalendar grFine = new GregorianCalendar();
	grFine.set(2020, 11, 10);
	mercatiContabilitaTributi.setDataInizioValidita(grToday.getTime());
	mercatiContabilitaTributi.setDataFineValidita(grFine.getTime());
	Assert.assertTrue(mercatiContabilitaTributi.isValidoAllaData(grImpostata.getTime()));
    }
}
