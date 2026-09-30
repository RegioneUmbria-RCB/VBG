package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloAnnuale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloBimestrale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloFactory;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloMensile;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloQuadrimestrale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloSemestrale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloTrimestrale;

import org.junit.Assert;
import org.junit.Test;

public class CalcoloIntervalloFactoryTests {

    @Test
    public void get_conTipologiaMensile_restituisceCalcoloMensile() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.MENSILE);
	Assert.assertSame(CalcoloIntervalloMensile.class, strategy.getClass());
    }

    @Test
    public void get_conTipologiaBimestrale_restituisceCalcoloBimestrale() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.BIMESTRALE);
	Assert.assertSame(CalcoloIntervalloBimestrale.class, strategy.getClass());
    }

    @Test
    public void get_conTipologiaTrimestrale_restituisceCalcoloTrimestrale() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.TRIMESTRALE);
	Assert.assertSame(CalcoloIntervalloTrimestrale.class, strategy.getClass());
    }

    @Test
    public void get_conTipologiaQuadrimestrale_restituisceCalcoloQuadrimestrale() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.QUADRIMESTRALE);
	Assert.assertSame(CalcoloIntervalloQuadrimestrale.class, strategy.getClass());
    }

    @Test
    public void get_conTipologiaSemestrale_restituisceCalcoloSemestrale() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.SEMESTRALE);
	Assert.assertSame(CalcoloIntervalloSemestrale.class, strategy.getClass());
    }

    @Test
    public void get_conTipologiaAnnuale_restituisceCalcoloAnnuale() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.ANNUALE);
	Assert.assertSame(CalcoloIntervalloAnnuale.class, strategy.getClass());
    }

    @Test(expected = NotImplementedException.class)
    public void get_conTipologiaBiennale_sollevaNotImplementedException() {

	CalcoloIntervalloFactory factory = new CalcoloIntervalloFactory();
	CalcoloIntervalloStrategy strategy = factory.get(PeriodiEnum.BIENNALE);
    }
}
