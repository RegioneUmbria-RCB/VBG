package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlberoprocLeggiService extends BaseService<AlberoprocLeggi, PkId> {

    /**
     * Metodo che restituisce la lista di AlberoprocLeggi filtrati per il codice Alberoproc
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocLeggi> findByAlberoProc(Integer codice);
}
