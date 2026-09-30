package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.PeriodiEnum;

public class DettaglioPeriodicitaHelperTest {

    @Test(expected = IllegalArgumentException.class)
    public void dettaglioPeriodicitaHelper_lanciaEccezioneSeIParametriNulli() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(null, null);
    }

    @Test(expected = NotImplementedException.class)
    public void dettaglioPeriodicitaHelper_lanciaEccezioneSePeriodicitaErrataBiennale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.BIENNALE);
    }

    @Test(expected = NotImplementedException.class)
    public void dettaglioPeriodicitaHelper_lanciaEccezioneSePeriodicitaErrataTriennale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.TRIENNALE);
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaDodiciElementiCasoMensile() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.MENSILE);
	Assert.assertEquals(12, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaSeiElementiCasoBimestrale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.BIMESTRALE);
	Assert.assertEquals(6, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaQuatroElementiCasoTrimestrale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.TRIMESTRALE);
	Assert.assertEquals(4, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaDueElementiCasoSemetrale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.SEMESTRALE);
	Assert.assertEquals(2, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaTreElementiCasoQuadriMestrale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.QUADRIMESTRALE);
	Assert.assertEquals(3, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_tornaUnElementiCasoAnnuale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.ANNUALE);
	Assert.assertEquals(1, dettaglioPeriodicitaHelper.getElementi().size());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_verificaChiaviAnnoBisestile() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2020, PeriodiEnum.MENSILE);
	Assert.assertEquals("0201-0229", dettaglioPeriodicitaHelper.getElementi().get(1).getChiave());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_verificaChiaviAnnoNonBisestile() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2019, PeriodiEnum.MENSILE);
	Assert.assertEquals("0201-0228", dettaglioPeriodicitaHelper.getElementi().get(1).getChiave());
    }

    @Test()
    public void dettaglioPeriodicitaHelper_verificaChiaveAnnuale() {

	DettaglioPeriodicitaHelper dettaglioPeriodicitaHelper = new DettaglioPeriodicitaHelper(2019, PeriodiEnum.ANNUALE);
	Assert.assertEquals("0101-1231", dettaglioPeriodicitaHelper.getElementi().get(0).getChiave());
    }
}
