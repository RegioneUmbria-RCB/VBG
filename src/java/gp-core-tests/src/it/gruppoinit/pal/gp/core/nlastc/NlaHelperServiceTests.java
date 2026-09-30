package it.gruppoinit.pal.gp.core.nlastc;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.service.impl.NlaHelperServiceImpl;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaType;

public class NlaHelperServiceTests {

    @Test(expected = InvalidConfigurationException.class)
    public void getComuniThrowsInvalidConfigurationExceptionIfComunePraticaIsNUll() {

	NlaHelperServiceImpl s = new NlaHelperFakeAdapter();
	DettaglioPraticaType dettaglioPratica = populateDettaglioPraticaForComuneNullo();
	s.getComune(dettaglioPratica);
    }

    @Test(expected = InvalidConfigurationException.class)
    public void getComuniThrowsInvalidConfigurationExceptionIfComunePraticaNotInComuniAssociati() {

	NlaHelperServiceImpl s = new NlaHelperFakeAdapter();
	DettaglioPraticaType dettaglioPratica = populateDettaglioPraticaForComuneE625();
	s.getComune(dettaglioPratica);
    }

    @Test()
    public void getComuniRitornaComuneDiGubbio() {

	NlaHelperServiceImpl s = new NlaHelperFakeAdapter();
	DettaglioPraticaType dettaglioPratica = populateDettaglioPraticaForComuneE256();
	Comuni comune = s.getComune(dettaglioPratica);
	Assert.assertTrue("Torna il comune di Gubbio", comune.getComune().equalsIgnoreCase("gubbio"));
    }

    private DettaglioPraticaType populateDettaglioPraticaForComuneE256() {

	DettaglioPraticaType d = new DettaglioPraticaType();
	ComuneType c = new ComuneType();
	c.setCodiceCatastale("E256");
	d.setCodiceComune(c);
	return d;
    }

    private DettaglioPraticaType populateDettaglioPraticaForComuneE625() {

	DettaglioPraticaType d = new DettaglioPraticaType();
	ComuneType c = new ComuneType();
	c.setCodiceCatastale("E625");
	d.setCodiceComune(c);
	return d;
    }

    private DettaglioPraticaType populateDettaglioPraticaForComuneNullo() {

	return new DettaglioPraticaType();
    }
}
