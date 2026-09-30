package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Domandefrontalbero;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface DomandefrontalberoDAO extends BaseDAO<Domandefrontalbero, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<Domandefrontalbero> findAll(Integer firstResult, Integer maxResult);
}
