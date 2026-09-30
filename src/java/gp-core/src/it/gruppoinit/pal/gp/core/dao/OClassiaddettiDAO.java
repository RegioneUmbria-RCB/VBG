package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.OClassiaddetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OClassiaddettiDAO extends BaseDAO<OClassiaddetti, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<OClassiaddetti> findAll(Integer firstResult, Integer maxResult);
}
