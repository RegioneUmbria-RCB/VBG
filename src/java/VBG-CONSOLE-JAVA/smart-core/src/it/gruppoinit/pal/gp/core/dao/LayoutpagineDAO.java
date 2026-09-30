package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Layoutpagine;
import it.gruppoinit.pal.gp.core.domain.LayoutpagineId;

import java.util.List;

/**
 * 
 * @author
 */
public interface LayoutpagineDAO extends BaseDAO<Layoutpagine, LayoutpagineId> {

    /**
     * tutti i record del software corrente
     * 
     */
    public List<Layoutpagine> findAll(Integer firstResult, Integer maxResult);
}
