package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CanoniConfigaree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniConfigareeDAO extends BaseDAO<CanoniConfigaree, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CanoniConfigaree> findAll(Integer firstResult, Integer maxResult);
}
