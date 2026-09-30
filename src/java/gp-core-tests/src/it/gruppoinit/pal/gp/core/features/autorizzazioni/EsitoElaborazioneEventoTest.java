package it.gruppoinit.pal.gp.core.features.autorizzazioni;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean;
import it.gruppoinit.pal.gp.core.features.buslightyear.model.OperazioneEventoBean.CHIAMANTE;

public class EsitoElaborazioneEventoTest {

    @Test
    public void verificaesitoElaborazioneEventi() {

	EsitoElaborazioneEvento e = new EsitoElaborazioneEvento();
	Assert.assertFalse("isErroreOwarning con esito vuoto false", e.isErroreOWarning());
	e.getErrors().add(new OperazioneEventoBean("1", "", CHIAMANTE.MERCATIPRESENZE_D));
	Assert.assertTrue("isErroreOwarning con un errore true", e.isErroreOWarning());
	e.getErrors().clear();
	e.getWarnings().add(new OperazioneEventoBean("1", "", CHIAMANTE.MERCATIPRESENZE_D));
	Assert.assertTrue("isErroreOwarning con uno warning true", e.isErroreOWarning());
	e.getWarnings().clear();
	e.getWarnings().add(new OperazioneEventoBean("1", "", CHIAMANTE.MERCATIPRESENZE_D));
	e.getErrors().add(new OperazioneEventoBean("1", "", CHIAMANTE.MERCATIPRESENZE_D));
	Assert.assertTrue("isErroreOwarning con un errore e uno warning true", e.isErroreOWarning());
    }
}
