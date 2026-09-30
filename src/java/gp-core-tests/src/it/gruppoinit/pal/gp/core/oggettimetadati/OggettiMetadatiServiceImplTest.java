package it.gruppoinit.pal.gp.core.oggettimetadati;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.oggetti.metadati.OggettiMetadatiServiceImpl;

public class OggettiMetadatiServiceImplTest {

    @Test(expected = RuntimeException.class)
    public void findByChiaveEValore_RilanciaEccezionePerParamChiaveVuoto() {

	OggettiMetadatiServiceImpl omd = new OggettiMetadatiServiceImpl();
	omd.setOggettiMetadatiDAO(new OggettiMetadatiDAOFake());
	omd.findByChiaveEValore(null, null);
    }

    @Test
    public void findByChiaveEValore_RilanciaEccezionePerParamValoreVuoto() {

	OggettiMetadatiServiceImpl omd = new OggettiMetadatiServiceImpl();
	omd.setOggettiMetadatiDAO(new OggettiMetadatiDAOFake());
	String chiave = "Chiave";
	try {
	    omd.findByChiaveEValore(chiave, null);
	} catch (Exception e) {
	    Assert.assertEquals(e.getMessage(), chiave + " nullo");
	}
    }

    @Test
    public void findByChiaveEValore_RilanciaEccezioneSeListaOMDSMaggiorediUno() {

	OggettiMetadatiServiceImpl omd = new OggettiMetadatiServiceImpl();
	omd.setOggettiMetadatiDAO(new OggettiMetadatiDAOFakeFilterTableSize3());
	String chiave = "Chiave";
	String valore = "Valore";
	try {
	    omd.findByChiaveEValore(chiave, valore);
	} catch (Exception e) {
	    Assert.assertEquals(e.getMessage(), "Sono stati trovati più oggetti per la chiave " + chiave + " e valore " + valore);
	}
    }

    @Test
    public void findByChiaveEValore_RitornaIlcodiceOggettoSeListaOMDSUgualeAUno() {

	OggettiMetadatiServiceImpl omd = new OggettiMetadatiServiceImpl();
	omd.setOggettiMetadatiDAO(new OggettiMetadatiDAOFakeFilterTableSize1());
	String chiave = "Chiave";
	String valore = "Valore";
	Integer findByChiaveEValore = omd.findByChiaveEValore(chiave, valore);
	Assert.assertEquals(findByChiaveEValore, Integer.valueOf(1000));
    }
}
