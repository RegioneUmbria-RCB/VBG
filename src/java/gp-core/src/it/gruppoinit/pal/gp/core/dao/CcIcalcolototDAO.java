package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcolotot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcolototDAO extends BaseDAO<CcIcalcolotot, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcolotot> findAll(Integer firstResult, Integer maxResult);
}
