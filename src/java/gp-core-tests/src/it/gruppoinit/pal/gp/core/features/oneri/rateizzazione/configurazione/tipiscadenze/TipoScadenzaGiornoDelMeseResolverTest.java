package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.utils.Utilities;

public class TipoScadenzaGiornoDelMeseResolverTest {

    @Test(expected = IllegalArgumentException.class)
    public void dataNullaTornaEccezione() {

	new TipoScadenzaGiornoDelMeseResolver(null, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void giorniNonNelRangeNullaTornaEccezione1() {

	impostaGiorno(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void giorniNonNelRangeNullaTornaEccezione2() {

	impostaGiorno(32);
    }

    private TipoScadenzaGiornoDelMeseResolver impostaGiorno(int giorno) {

	return new TipoScadenzaGiornoDelMeseResolver(GregorianCalendar.getInstance().getTime(), giorno);
    }

    @Test
    public void ventiDelMeseGiorniPrecedenti() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(2026, 1, 15);
	ITipoScadenzaResolver resolver = new TipoScadenzaGiornoDelMeseResolver(dataPartenza.getTime(), 20);
	Calendar dataAspettata = GregorianCalendar.getInstance();
	dataAspettata.set(2026, 1, 20);
	Assert.assertTrue(Utilities.compareDates(dataAspettata.getTime(), resolver.getScadenza()) == 0);
    }

    @Test
    public void ventiDelMeseGiorniSuccessiviTornaMeseSeguente() {

	Calendar dataPartenza = GregorianCalendar.getInstance();
	dataPartenza.set(2026, 1, 21);
	ITipoScadenzaResolver resolver = new TipoScadenzaGiornoDelMeseResolver(dataPartenza.getTime(), 20);
	Calendar dataAspettata = GregorianCalendar.getInstance();
	dataAspettata.set(2026, 2, 20);
	Assert.assertTrue(Utilities.compareDates(dataAspettata.getTime(), resolver.getScadenza()) == 0);
    }
}
