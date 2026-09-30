package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;
import it.gruppoinit.pal.gp.core.service.BaseService;

import java.util.List;

/**
 * 
 * @author
 */
public interface SorteggitestatainfoService extends BaseService<Sorteggitestatainfo, PkId> {

    /**
     * @see SorteggitestatainfoDAO#findAll(Integer, Integer)
     */
    public List<Sorteggitestatainfo> findAll(Integer firstResult, Integer maxResult);
}
