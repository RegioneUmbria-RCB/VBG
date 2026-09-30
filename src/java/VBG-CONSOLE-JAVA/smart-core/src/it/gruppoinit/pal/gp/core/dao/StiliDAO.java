package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Stili;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface StiliDAO extends BaseDAO<Stili, Integer> {

    public List<Stili> findAll(Integer firstResult, Integer maxResult);
}
