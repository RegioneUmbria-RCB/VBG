package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieVotiBase;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieVotiBaseDAO extends BaseDAO<CommedilizieVotiBase, Integer> {

    /**
     * Lista dei voti base
     * 
     */
    public List<CommedilizieVotiBase> findAll(Integer firstResult, Integer maxResult);
}
