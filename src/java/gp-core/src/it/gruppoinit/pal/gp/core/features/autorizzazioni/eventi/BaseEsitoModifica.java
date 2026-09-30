package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public class BaseEsitoModifica {

    enum ESITO {
	WARNING,
	ERROR
    }

    private List<EsitoElaborazioneEvento> esiti = null;

    public boolean isErroreOWarning() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isErroreOWarning()) {
		return true;
	    }
	}
	return false;
    }

    public List<EsitoElaborazioneEvento> getEsiti() {

	if (esiti == null) {
	    esiti = new ArrayList<EsitoElaborazioneEvento>();
	}
	return esiti;
    }

    public boolean isErrore() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isErrore()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isWarning() {

	for (EsitoElaborazioneEvento esito : getEsiti()) {
	    if (esito.isWarning()) {
		return true;
	    }
	}
	return false;
    }
}
