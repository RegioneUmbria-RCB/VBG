package it.gruppoinit.pal.gp.areariservata.domain;

import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.init.sigepro.rte.types.AltriSoggettiType;

public class AltriSoggettiTypeHelper {

    private AltriSoggettiType soggetto;
    private Tipisoggetto tipoSoggetto;

    public AltriSoggettiTypeHelper(AltriSoggettiType soggetto) {

	this.soggetto = soggetto;
	this.tipoSoggetto = new Tipisoggetto();
    }

    public AltriSoggettiType getSoggetto() {

	return soggetto;
    }

    public void setSoggetto(AltriSoggettiType soggetto) {

	this.soggetto = soggetto;
    }

    public Tipisoggetto getTipoSoggetto() {

	return tipoSoggetto;
    }

    public void setTipoSoggetto(Tipisoggetto tipoSoggetto) {

	this.tipoSoggetto = tipoSoggetto;
    }
}
