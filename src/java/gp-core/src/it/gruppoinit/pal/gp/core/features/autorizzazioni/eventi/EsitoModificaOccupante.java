package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public class EsitoModificaOccupante extends BaseEsitoModifica {

    public void addEsito(EsitoElaborazioneEvento esito) {

	getEsiti().add(esito);
    }
}
