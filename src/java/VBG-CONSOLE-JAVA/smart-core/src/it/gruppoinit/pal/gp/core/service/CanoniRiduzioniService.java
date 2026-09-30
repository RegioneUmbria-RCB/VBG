package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CanoniRiduzioniDAO;
import it.gruppoinit.pal.gp.core.domain.CanoniRiduzioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CanoniRiduzioniService extends BaseService<CanoniRiduzioni, PkId> {

    /**
     * @see CanoniRiduzioniDAO#findAll(Integer, Integer)
     */
    public List<CanoniRiduzioni> findAll(Integer firstResult, Integer maxResult);
}
