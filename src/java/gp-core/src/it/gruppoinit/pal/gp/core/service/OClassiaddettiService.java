package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OClassiaddettiDAO;
import it.gruppoinit.pal.gp.core.domain.OClassiaddetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OClassiaddettiService extends BaseService<OClassiaddetti, PkId> {

    /**
     * @see OClassiaddettiDAO#findAll(Integer, Integer)
     */
    public List<OClassiaddetti> findAll(Integer firstResult, Integer maxResult);
}
