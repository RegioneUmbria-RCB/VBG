package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class TipoScadenzaPeriodicaTests {

    @Test(expected = IllegalArgumentException.class)
    public void impostoPeriodoGGNonPresentiInFebbraioTornaEccezione() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(1983, 6, 26);
	String periodo = "30/02";
	ITipoScadenzaResolver resolver = new TipoScadenzaPeriodica(dataPartenza.getTime(), periodo, 0);
	System.out.println(resolver.getScadenza());
    }

    @Test
    public void impostoPeriodoGiaPassatoTornaAnnoSuccessivo() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(1983, 6, 26);
	String periodo = "25/07";
	ITipoScadenzaResolver resolver = new TipoScadenzaPeriodica(dataPartenza.getTime(), periodo, 0);
	Calendar dataAspettata = GregorianCalendar.getInstance();
	dataAspettata.set(1984, 6, 25);
	Assert.assertTrue(dataAspettata.getTime().equals(resolver.getScadenza()));
    }

    @Test
    public void impostoPeriodoNONPassatoTornaStessoAnno() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(1983, 6, 26);
	String periodo = "27/07";
	ITipoScadenzaResolver resolver = new TipoScadenzaPeriodica(dataPartenza.getTime(), periodo, 0);
	Calendar dataAspettata = GregorianCalendar.getInstance();
	dataAspettata.set(1983, 6, 27);
	Assert.assertTrue(dataAspettata.getTime().equals(resolver.getScadenza()));
    }

    @Test
    public void impostoPeriodoStessoMesePrimaRataPassatoTornaStessoAnno() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(2023, 2, 11);
	String periodo = "31/03";
	ITipoScadenzaResolver resolver = new TipoScadenzaPeriodica(dataPartenza.getTime(), periodo, 0);
	Calendar dataAspettata = GregorianCalendar.getInstance();
	dataAspettata.set(2023, 2, 31);
	Assert.assertTrue(dataAspettata.getTime().equals(resolver.getScadenza()));
    }
}
