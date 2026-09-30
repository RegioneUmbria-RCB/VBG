package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocBolkesteinDAO extends BaseDAO<AlberoprocBolkestein, PkId> {

    /**
     * Metodo che restituisce la lista di AlberoprocBolkestein filtrati per il codice Alberoproc
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocBolkestein> findByAlberoProc(Integer codice);
}
