package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwAnagrafeinterdettiDAO;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdetti;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdettiId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwAnagrafeinterdettiService extends BaseService<VwAnagrafeinterdetti, VwAnagrafeinterdettiId> {

    /**
     * @see VwAnagrafeinterdettiDAO#findAll(Integer, Integer)
     */
    public List<VwAnagrafeinterdetti> findAll(Integer firstResult, Integer maxResult);
}
