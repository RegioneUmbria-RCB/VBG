package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CommedilizieVotiBaseDAO;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;

import java.util.List;

/**
 * 
 * @author
 */
public interface CommedilizieVotiBaseService extends BaseService<CommedilizieVotiBase, Integer> {

    /**
     * @see CommedilizieVotiBaseDAO#findAll(Integer, Integer)
     */
    public List<CommedilizieVotiBase> findAll(Integer firstResult, Integer maxResult);
}
