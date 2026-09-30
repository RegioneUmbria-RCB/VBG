package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisogBackDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisogBack;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocTipisogBackService extends BaseService<AlberoprocTipisogBack, PkId> {

    /**
     * @see AlberoprocTipisogBackDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocTipisogBack> findAll(Integer firstResult, Integer maxResult);
    
    
    public List<AlberoprocTipisogBack> findByAlberoproc(Integer codiceAlberoproc);
}
