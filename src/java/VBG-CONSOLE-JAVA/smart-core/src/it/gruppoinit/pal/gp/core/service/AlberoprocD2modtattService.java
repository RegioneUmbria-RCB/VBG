package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocD2modtattDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtatt;
import it.gruppoinit.pal.gp.core.domain.AlberoprocD2modtattId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocD2modtattService extends BaseService<AlberoprocD2modtatt, AlberoprocD2modtattId> {

    /**
     * @see AlberoprocD2modtattDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocD2modtatt> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista filtrata per albero proc
     * 
     * @param codiceAlberoproc
     * @return
     */
    public List<AlberoprocD2modtatt> findByAlberoProc(Integer codiceAlberoproc);
}
