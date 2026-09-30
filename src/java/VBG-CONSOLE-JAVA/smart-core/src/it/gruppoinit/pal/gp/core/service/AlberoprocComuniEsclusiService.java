package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocComuniEsclusiId;
import it.gruppoinit.pal.gp.core.features.alberoproc.esclusioni.ComuniEsclusi;

public interface AlberoprocComuniEsclusiService extends BaseService<AlberoprocComuniEsclusi, AlberoprocComuniEsclusiId> {

    List<ComuniEsclusi> findByAlberoProc(String scCodice);

    void delete(Integer codiceInterventoProc, String codiceComune);

    void aggiungiEnti(Integer codiceInterventoProc, String[] comuni);

    boolean entiEsclusiPresenti(String scCodice);
}
