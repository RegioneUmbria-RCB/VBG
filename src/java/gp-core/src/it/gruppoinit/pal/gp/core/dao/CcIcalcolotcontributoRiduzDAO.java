package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcolotcontributoRiduzDAO extends BaseDAO<CcIcalcolotcontributoRiduz, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<CcIcalcolotcontributoRiduz> findAll(Integer firstResult, Integer maxResult);
}
