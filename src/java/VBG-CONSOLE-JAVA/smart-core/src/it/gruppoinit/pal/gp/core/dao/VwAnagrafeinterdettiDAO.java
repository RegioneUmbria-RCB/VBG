package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdetti;
import it.gruppoinit.pal.gp.core.domain.VwAnagrafeinterdettiId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwAnagrafeinterdettiDAO extends BaseDAO<VwAnagrafeinterdetti, VwAnagrafeinterdettiId> {

    /**
     * Lista delle anagrafiche interdette filtrate per idcomune
     * 
     */
    public List<VwAnagrafeinterdetti> findAll(Integer firstResult, Integer maxResult);
}
