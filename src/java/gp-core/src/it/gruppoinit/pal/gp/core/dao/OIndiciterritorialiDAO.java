package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OIndiciterritoriali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIndiciterritorialiDAO extends BaseDAO<OIndiciterritoriali, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OIndiciterritoriali> findAll(Integer firstResult, Integer maxResult);
}
