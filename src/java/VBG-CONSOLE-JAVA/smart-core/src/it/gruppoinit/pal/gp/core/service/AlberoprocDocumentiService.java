package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AlberoprocDocumentiService extends BaseService<AlberoprocDocumenti, PkId> {

    /**
     * Metodo che restituisce la lista di AlberoprocDocumenti filtrati per il codice Alberoproc
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocDocumenti> findByAlberoProc(Integer codice);

    /**
     * Determina il valore massimo del campo "ordine"
     * 
     * @return
     */
    public int findMaxOrder();
}
