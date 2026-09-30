package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcoliDAO extends BaseDAO<CcIcalcoli, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcoli> findAll(Integer firstResult, Integer maxResult);
}
