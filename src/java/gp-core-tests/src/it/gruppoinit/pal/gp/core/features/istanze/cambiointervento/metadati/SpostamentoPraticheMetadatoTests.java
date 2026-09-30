package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento.metadati;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.istanze.metadati.IMetadatoIstanza;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class SpostamentoPraticheMetadatoTests {

    @Test
    public void chiaveCompostaConDataDiSistema() {

	IMetadatoIstanza metadato = new SpostamentoPraticheMetadato(1, 2);
	String expected = "SPOSTAMENTO_PRATICHE" + Utilities.formatDate(Calendar.getInstance().getTime(), "yyyyMMddHHmmss");
	Assert.assertEquals("Chiave non generata correttamente", expected, metadato.getChiave());
    }

    @Test(expected = IllegalArgumentException.class)
    public void metadatoIstanziatoSenzaCodiceIstanzaTornaErrore() {

	new SpostamentoPraticheMetadato(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void metadatoIstanziatoSenzaCodiceInterventoProcTornaErrore() {

	new SpostamentoPraticheMetadato(1, null);
    }
}
