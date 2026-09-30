package it.gruppoinit.pal.gp.core.oggetti;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.oggetti.OggettiServiceImpl;

public class OggettiServiceImplTest {

    private static final String DEVE_RESTITUIRE_FALSE = "Deve restituire false";
    private static final String DEVE_RESTITUIRE_TRUE = "Deve restituire true";

    @Test
    public void scriviSUAlfrescoTornaFalseSeCMISEAlfrescoFalse() {

	OggettiServiceImpl omd = new OggettiServiceImpl();
	Assert.assertFalse(DEVE_RESTITUIRE_FALSE, omd.scriviSuAlfrescoOCMIS(false, false, false));
    }

    @Test
    public void scriviSUAlfrescoTornaTrueSeCMIS() {

	OggettiServiceImpl omd = new OggettiServiceImpl();
	Assert.assertTrue(DEVE_RESTITUIRE_TRUE, omd.scriviSuAlfrescoOCMIS(true, false, false));
    }

    @Test
    public void scriviSUAlfrescoTornaTrueSeCMISeECMISEAlfrescoTrueESolaLetturaTrue() {

	// la verticalizzazione CMIS sovrascrive quella eAPIALFRESCO e quindi anche se impostato
	// eApiAlfrescoSolaLettura true vince CMIS
	OggettiServiceImpl omd = new OggettiServiceImpl();
	Assert.assertTrue(DEVE_RESTITUIRE_TRUE, omd.scriviSuAlfrescoOCMIS(true, true, true));
    }

    @Test
    public void scriviSUAlfrescoTornaTrueSeECMISEAlfrescoTrueESolaLetturaFalse() {

	OggettiServiceImpl omd = new OggettiServiceImpl();
	Assert.assertTrue(DEVE_RESTITUIRE_TRUE, omd.scriviSuAlfrescoOCMIS(false, true, false));
    }

    @Test
    public void scriviSUAlfrescoTornaFalseSeECMISEAlfrescoTrueESolaLetturaTrue() {

	OggettiServiceImpl omd = new OggettiServiceImpl();
	Assert.assertFalse(DEVE_RESTITUIRE_FALSE, omd.scriviSuAlfrescoOCMIS(false, true, true));
    }
}
