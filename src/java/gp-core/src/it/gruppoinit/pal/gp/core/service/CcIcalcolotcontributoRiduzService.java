package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcolotcontributoRiduzDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface CcIcalcolotcontributoRiduzService extends BaseService<CcIcalcolotcontributoRiduz, PkId> {

    /**
     * @see CcIcalcolotcontributoRiduzDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcolotcontributoRiduz> findAll(Integer firstResult, Integer maxResult);
}
