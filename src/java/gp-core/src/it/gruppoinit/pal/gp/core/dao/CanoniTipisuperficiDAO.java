package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniTipisuperfici;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniTipisuperficiDAO extends BaseDAO<CanoniTipisuperfici, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniTipisuperfici> findAll(Integer firstResult, Integer maxResult);
}
