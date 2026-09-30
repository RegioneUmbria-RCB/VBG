package it.gruppoinit.pal.gp.core.features.sorteggi;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;

public class SorteggioResponse {

    private Sorteggitestata testata;
    private List<SorteggioDettaglioDTO> dettagli;

    public Sorteggitestata getTestata() {

	return testata;
    }

    public List<SorteggioDettaglioDTO> getDettagli() {

	return dettagli;
    }

    public SorteggioResponse(Sorteggitestata testata, List<SorteggioDettaglioDTO> dettagli) {

	this.testata = testata;
	this.dettagli = dettagli;
    }
}
