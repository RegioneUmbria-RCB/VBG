package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import it.gruppoinit.pal.gp.core.features.buslightyear.model.EsitoElaborazioneEvento;

public interface IVerificaCheckSubentriService {

    EsitoElaborazioneEvento checkPossoSubentrare(CheckSubentroRequest request);
}
