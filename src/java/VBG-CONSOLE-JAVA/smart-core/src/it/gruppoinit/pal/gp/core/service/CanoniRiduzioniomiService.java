package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniomiDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioniomi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniRiduzioniomiService extends BaseService<CanoniRiduzioniomi, PkId> {

    /**
     * @see CanoniRiduzioniomiDAO#findAll(Integer, Integer)
     */
    public List<CanoniRiduzioniomi> findAll(Integer firstResult, Integer maxResult);
}
