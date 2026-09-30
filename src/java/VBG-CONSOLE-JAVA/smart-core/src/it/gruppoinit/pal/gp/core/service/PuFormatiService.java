package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.PuFormatiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.PuFormati;

import java.util.List;

/**
 * 
 * @author 
 */
public interface PuFormatiService extends BaseService<PuFormati, PkId> {

    /**
     * @see PuFormatiDAO#findAll(Integer, Integer)
     */
    public List<PuFormati> findAll(Integer firstResult, Integer maxResult);
}
