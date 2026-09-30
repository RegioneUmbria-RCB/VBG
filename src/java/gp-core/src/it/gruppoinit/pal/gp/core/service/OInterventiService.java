package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OInterventiDAO;
import it.gruppoinit.pal.gp.core.domain.OInterventi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface OInterventiService extends BaseService<OInterventi, PkId> {

    /**
     * @see OInterventiDAO#findAll(Integer, Integer)
     */
    public List<OInterventi> findAll(Integer firstResult, Integer maxResult);
}
