package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocLimitiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLimiti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author francescop
 */
public interface AlberoprocLimitiService extends BaseService<AlberoprocLimiti, PkId> {

    /**
     * @see AlberoprocLimitiDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocLimiti> findAll(Integer firstResult, Integer maxResult);
}
