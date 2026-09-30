package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface LivelloServizioDAO extends BaseDAO<LivelloServizio, PkId> {

    public List<LivelloServizio> findAll(Integer firstResult, Integer maxResult);
}
