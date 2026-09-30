package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive.metadati;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.BollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.FakeBollCfgTipoMetadatiService;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.MetadatoBollettazione;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class BollMetadatiServiceTests {

    private String segnapostoBollettazione = "[IDBOLLETTAZIONE]";

    @Test(expected = BusinessValidationException.class)
    public void elencoMetadatiConTestataNullTornaBusinessValidationException() {

	BollMetadatiService service = new BollMetadatiService(this.getService());
	service.elencoMetadati(null);
    }

    @Test(expected = BusinessValidationException.class)
    public void elencoMetadatiConTestataVuotaTornaBusinessValidationException() {

	BollMetadatiService service = new BollMetadatiService(this.getService());
	service.elencoMetadati(new BollGestTestata());
    }

    @Test(expected = BusinessValidationException.class)
    public void elencoMetadatiConConfigurazioneNullTornaBusinessValidationException() {

	BollGestTestata testata = new BollGestTestata();
	testata.setId(new PkId(1));
	testata.setBollCfgTipo(null);
	BollMetadatiService service = new BollMetadatiService(this.getService());
	service.elencoMetadati(testata);
    }

    @Test(expected = BusinessValidationException.class)
    public void elencoMetadatiConConfigurazioneVuotaTornaBusinessValidationException() {

	BollGestTestata testata = new BollGestTestata();
	testata.setId(new PkId(1));
	testata.setBollCfgTipo(new BollCfgTipo());
	BollMetadatiService service = new BollMetadatiService(this.getService());
	service.elencoMetadati(testata);
    }

    @Test(expected = BusinessValidationException.class)
    public void elencoMetadatiConDescrizioneBollettazioneVuotaTornaBusinessValidationException() {

	BollGestTestata testata = new BollGestTestata();
	testata.setId(new PkId(1));
	BollCfgTipo cfg = new BollCfgTipo();
	cfg.setId(new PkId(1));
	testata.setBollCfgTipo(cfg);
	BollMetadatiService service = new BollMetadatiService(this.getServiceConMetadatiPerBollettazione());
	service.elencoMetadati(testata);
    }

    @Test
    public void elencoMetadatiConSostituzioneSegnapostoIdBollettazioneSostituito() {

	BollGestTestata testata = new BollGestTestata();
	testata.setId(new PkId(1));
	testata.setDescrizione("Bollettazione di prova");
	BollCfgTipo cfg = new BollCfgTipo();
	cfg.setId(new PkId(1));
	testata.setBollCfgTipo(cfg);
	BollMetadatiService service = new BollMetadatiService(this.getServiceConMetadatiPerBollettazione());
	List<MetadatoBollettazione> metadati = service.elencoMetadati(testata);
	boolean segnapostoSostituito = true;
	for (MetadatoBollettazione metadato : metadati) {
	    if (metadato.getValore().contains(segnapostoBollettazione)) {
		segnapostoSostituito = false;
		break;
	    }
	}
	Assert.assertTrue("Deve restituire true ma ha resitituito false", segnapostoSostituito);
    }

    private BollCfgTipoMetadatiService getService() {

	return new FakeBollCfgTipoMetadatiService();
    }

    private BollCfgTipoMetadatiService getServiceConMetadatiPerBollettazione() {

	FakeBollCfgTipoMetadatiService service = new FakeBollCfgTipoMetadatiService();
	service.aggiungiMetadato("BOLLETTAZIONE", "BOLLETTAZIONE " + segnapostoBollettazione);
	service.aggiungiMetadato("OGGETTO", "BOLLETTAZIONE DI ISTANZE");
	return service;
    }
}
