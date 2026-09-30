package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OIndiciterritorialiDAO;
import it.gruppoinit.pal.gp.core.domain.OIndiciterritoriali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OIndiciterritorialiService extends BaseService<OIndiciterritoriali, PkId> {

    /**
     * @see OIndiciterritorialiDAO#findAll(Integer, Integer)
     */
    public List<OIndiciterritoriali> findAll(Integer firstResult, Integer maxResult);
}
